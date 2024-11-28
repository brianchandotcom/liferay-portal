/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.zip.ZipInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ExportTaskResourceTest extends BaseTaskResourceTestCase {

	@Test
	public void testPostExportTask() throws Exception {
		long companyId = 0;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineExportTaskExecutorImpl",
				LoggerTestUtil.ERROR)) {

			_testPostExportTask("COMPLETED", null, objectDefinition);

			JSONObject companyJSONObject = HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"domain", "able.com"
				).put(
					"portalInstanceId", "able.com"
				).put(
					"virtualHost", "www.able.com"
				).toString(),
				"headless-portal-instances/v1.0/portal-instances",
				Http.Method.POST);

			companyId = companyJSONObject.getLong("companyId");

			User user = UserTestUtil.getAdminUser(companyId);

			ObjectDefinition objectDefinition2 =
				ObjectDefinitionTestUtil.publishObjectDefinition(
					Collections.singletonList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING,
							OBJECT_FIELD_NAME_TEXT)),
					ObjectDefinitionConstants.SCOPE_COMPANY, user.getUserId());

			_testPostExportTask("FAILED", null, objectDefinition2);

			HTTPTestUtil.customize(
			).withBaseURL(
				"http://www.able.com:8080"
			).withCredentials(
				"test@able.com", PropsValues.DEFAULT_ADMIN_PASSWORD
			).apply(
				() -> {
					_testPostExportTask("COMPLETED", null, objectDefinition2);
					_testPostExportTask("FAILED", null, objectDefinition);
				}
			);
		}
		finally {
			if (companyId != 0) {
				_companyLocalService.deleteCompany(companyId);
			}
		}
	}

	@Test
	public void testPostExportTaskWithFilter() throws Exception {
		ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, OBJECT_FIELD_NAME_TEXT, "Object3");

		ObjectEntry objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, OBJECT_FIELD_NAME_TEXT, "TestObject1");
		ObjectEntry objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, OBJECT_FIELD_NAME_TEXT, "TestObject2");

		String filterString =
			"contains(" + OBJECT_FIELD_NAME_TEXT + ", 'Test')";

		JSONObject jsonObject = _testPostExportTask(
			"COMPLETED", "filter=" + URLCodec.encodeURL(filterString),
			objectDefinition);

		Assert.assertEquals(2, jsonObject.getInt("processedItemsCount"));

		ZipInputStream zipInputStream = new ZipInputStream(
			HTTPTestUtil.invokeToInputStream(
				null,
				StringBundler.concat(
					"headless-batch-engine/v1.0/export-task",
					"/by-external-reference-code/",
					jsonObject.getString("externalReferenceCode"), "/content"),
				Http.Method.GET));

		zipInputStream.getNextEntry();

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"externalReferenceCode",
					objectEntry1.getExternalReferenceCode()),
				JSONUtil.put(
					"externalReferenceCode",
					objectEntry2.getExternalReferenceCode())
			).toString(),
			StringUtil.read(zipInputStream), JSONCompareMode.LENIENT);
	}

	private JSONObject _testPostExportTask(
			String expectedExecuteStatus, String queryParameters,
			ObjectDefinition objectDefinition)
		throws Exception {

		String endpoint = StringBundler.concat(
			"headless-batch-engine/v1.0/export-task",
			"/com.liferay.object.rest.dto.v1_0.ObjectEntry/json?",
			"taskItemDelegateName=", objectDefinition.getName());

		if (queryParameters != null) {
			endpoint = endpoint + "&" + queryParameters;
		}

		JSONObject jsonObject = waitForFinish(
			expectedExecuteStatus, false,
			HTTPTestUtil.invokeToJSONObject(null, endpoint, Http.Method.POST));

		return HTTPTestUtil.invokeToJSONObject(
			null,
			ENDPOINT_EXPORT_TASK_BY_ERC +
				jsonObject.getString("externalReferenceCode"),
			Http.Method.GET);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}