/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.headless.object.client.dto.v1_0.ObjectEntryFolder;
import com.liferay.headless.object.client.resource.v1_0.ObjectEntryFolderResource;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Jhosseph Gonzalez
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class ImportTaskResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_testGroup = GroupTestUtil.addGroup();

		_testCompany = CompanyLocalServiceUtil.getCompany(
			_testGroup.getCompanyId());

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			_testCompany.getCompanyId());

		_importTaskResource = ImportTaskResource.builder(
		).authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			_testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameter(
			"siteExternalReferenceCode", _testGroup.getExternalReferenceCode()
		).parameter(
			"taskItemDelegateName", "depot-object-entry-folder"
		).build();

		_objectEntryFolderResource = ObjectEntryFolderResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			_testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(_testGroup);
	}

	@Test
	public void testPostImportTask() throws Exception {
		String objectEntryFolderExternalReferenceCode =
			RandomTestUtil.randomString();

		// With "createStrategy" UPSERT

		JSONObject jsonObject = JSONUtil.put(
			"externalReferenceCode", objectEntryFolderExternalReferenceCode
		).put(
			"label", RandomTestUtil.randomString()
		).put(
			"title", RandomTestUtil.randomString()
		);

		ObjectEntryFolder objectEntryFolder = _postImportTask(
			"UPSERT", jsonObject);

		JSONAssert.assertEquals(
			jsonObject.toString(), objectEntryFolder.toString(),
			JSONCompareMode.LENIENT);

		jsonObject = JSONUtil.put(
			"externalReferenceCode", objectEntryFolderExternalReferenceCode
		).put(
			"label", RandomTestUtil.randomString()
		).put(
			"title", RandomTestUtil.randomString()
		);

		objectEntryFolder = _postImportTask("UPSERT", jsonObject);

		JSONAssert.assertEquals(
			jsonObject.toString(), objectEntryFolder.toString(),
			JSONCompareMode.LENIENT);

		// With "createStrategy" INSERT

		jsonObject = JSONUtil.put(
			"externalReferenceCode", RandomTestUtil.randomString()
		).put(
			"label", RandomTestUtil.randomString()
		).put(
			"title", RandomTestUtil.randomString()
		);

		objectEntryFolder = _postImportTask("INSERT", jsonObject);

		JSONAssert.assertEquals(
			jsonObject.toString(), objectEntryFolder.toString(),
			JSONCompareMode.LENIENT);
	}

	private ObjectEntryFolder _postImportTask(
			String createStrategy, JSONObject jsonObject)
		throws Exception {

		ImportTask importTask = _importTaskResource.postImportTask(
			"com.liferay.headless.object.dto.v1_0.ObjectEntryFolder", null,
			null, null, createStrategy, null, null, null, null,
			JSONUtil.putAll(
				jsonObject
			).toString());

		_waitForFinish("COMPLETED", JSONUtil.put("id", importTask.getId()));

		return _objectEntryFolderResource.
			getScopeScopeKeyObjectEntryFolderByExternalReferenceCode(
				String.valueOf(_testGroup.getGroupId()),
				jsonObject.getString("externalReferenceCode"));
	}

	private JSONObject _waitForFinish(
			String expectedExecuteStatus, JSONObject jsonObject)
		throws Exception {

		while (true) {
			ImportTask importTask = _importTaskResource.getImportTask(
				jsonObject.getLong("id"));

			ImportTask.ExecuteStatus executeStatus =
				importTask.getExecuteStatus();

			if (StringUtil.equals(executeStatus.getValue(), "COMPLETED") ||
				StringUtil.equals(executeStatus.getValue(), "FAILED")) {

				Assert.assertEquals(
					expectedExecuteStatus, executeStatus.getValue());

				return jsonObject;
			}
		}
	}

	private ImportTaskResource _importTaskResource;
	private ObjectEntryFolderResource _objectEntryFolderResource;
	private Company _testCompany;
	private User _testCompanyAdminUser;
	private Group _testGroup;

}