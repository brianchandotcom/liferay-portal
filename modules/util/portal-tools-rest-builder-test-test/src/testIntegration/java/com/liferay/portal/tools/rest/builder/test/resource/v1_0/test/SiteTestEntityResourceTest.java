/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.SiteTestEntity;
import com.liferay.portal.tools.rest.builder.test.client.resource.v1_0.SiteTestEntityResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.liferay.portal.util.PropsValues;


/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class SiteTestEntityResourceTest
	extends BaseSiteTestEntityResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description"};
	}

	@Override
	@Test
	public void testPatchSiteTestEntity() throws Exception {
		super.testPatchSiteTestEntity();

		_testPatchSiteTestEntityBatch();
	}

	private void _testPatchSiteTestEntityBatch() throws Exception {
		SiteTestEntity postSiteTestEntity = new SiteTestEntity();

		SiteTestEntity notExistingPostSiteTestEntity = new SiteTestEntity();

		postSiteTestEntity.setDescription(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));

		SiteTestEntity siteTestEntity =
			siteTestEntityResource.postSiteSiteTestEntity(
				testGroup.getGroupId(), postSiteTestEntity);

		postSiteTestEntity.setId(siteTestEntity.getId());

		postSiteTestEntity.setDescription(RandomTestUtil.randomString());

		notExistingPostSiteTestEntity.setDescription(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
		notExistingPostSiteTestEntity.setId(siteTestEntity.getId() + 1);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal.strategy." +
					"OnErrorContinueBatchEngineImportStrategy",
				LoggerTestUtil.ERROR)) {

			_waitForFinish(
				"COMPLETED", true,
				JSONFactoryUtil.createJSONObject(
					createSiteTestEntityResourceWithParameters(
						new String[] {
							"updateStrategy", "UPDATE", "importStrategy",
							"ON_ERROR_CONTINUE"
						}
					).putSiteTestEntityBatchHttpResponse(
						null, 
						JSONUtil.putAll(
							JSONFactoryUtil.createJSONObject(
								String.valueOf(postSiteTestEntity)),
							JSONFactoryUtil.createJSONObject(
								String.valueOf(notExistingPostSiteTestEntity)))
					).getContent()));
		}

		siteTestEntity = siteTestEntityResource.getSiteTestEntity(
			siteTestEntity.getId());

		Assert.assertEquals(
			postSiteTestEntity.getId(), siteTestEntity.getId());
		Assert.assertEquals(
			postSiteTestEntity.getDescription(),
			siteTestEntity.getDescription());
	}

	private JSONObject _waitForFinish(
			String expectedExecuteStatus, boolean importTask,
			JSONObject jsonObject)
		throws Exception {

		String endpoint = StringBundler.concat(
			"headless-batch-engine/v1.0/",
			importTask ? "import-task" : "export-task",
			"/by-external-reference-code/");

		while (true) {
			jsonObject = HTTPTestUtil.invokeToJSONObject(
				null, endpoint + jsonObject.getString("externalReferenceCode"),
				Http.Method.GET);

			String executeStatus = jsonObject.getString("executeStatus");

			if (StringUtil.equals(executeStatus, "COMPLETED") ||
				StringUtil.equals(executeStatus, "FAILED")) {

				Assert.assertEquals(expectedExecuteStatus, executeStatus);

				return jsonObject;
			}
		}
	}

	private SiteTestEntityResource createSiteTestEntityResourceWithParameters(
			String[] parameters)
		throws Exception {

		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		User testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		SiteTestEntityResource siteTestEntityResource = SiteTestEntityResource.builder(
		).authentication(
			testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			parameters
		).build();

		return siteTestEntityResource;
	}
}