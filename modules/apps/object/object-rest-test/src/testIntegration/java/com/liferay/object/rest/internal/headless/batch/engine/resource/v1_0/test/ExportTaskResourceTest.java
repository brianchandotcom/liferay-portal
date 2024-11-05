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
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.Collections;
import java.util.zip.ZipInputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ExportTaskResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testPostExportTask() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineExportTaskExecutorImpl",
				LoggerTestUtil.ERROR)) {

			ObjectDefinition objectDefinition1 =
				ObjectDefinitionTestUtil.publishObjectDefinition(
					Collections.singletonList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING,
							_OBJECT_FIELD_NAME_TEXT)),
					ObjectDefinitionConstants.SCOPE_COMPANY,
					TestPropsValues.getUserId());

			_testPostExportTask("COMPLETED", objectDefinition1);

			JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"domain", "able.com"
				).put(
					"portalInstanceId", "able.com"
				).put(
					"virtualHost", "www.able.com"
				).toString(),
				"headless-portal-instances/v1.0/portal-instances",
				Http.Method.POST);

			User user = UserTestUtil.getAdminUser(
				jsonObject.getLong("companyId"));

			ObjectDefinition objectDefinition2 =
				ObjectDefinitionTestUtil.publishObjectDefinition(
					Collections.singletonList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING,
							_OBJECT_FIELD_NAME_TEXT)),
					ObjectDefinitionConstants.SCOPE_COMPANY, user.getUserId());

			_testPostExportTask("FAILED", objectDefinition2);

			HTTPTestUtil.customize(
			).withBaseURL(
				"http://www.able.com:8080"
			).withCredentials(
				"test@able.com", PropsValues.DEFAULT_ADMIN_PASSWORD
			).apply(
				() -> {
					_testPostExportTask("COMPLETED", objectDefinition2);
					_testPostExportTask("FAILED", objectDefinition1);
				}
			);

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition1);
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition2);

			_companyLocalService.deleteCompany(jsonObject.getLong("companyId"));
		}
	}

	@Test
	public void testPostExportTaskWithFiltering() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal." +
					"BatchEngineExportTaskExecutorImpl",
				LoggerTestUtil.ERROR)) {

			ObjectDefinition objectDefinition =
				ObjectDefinitionTestUtil.publishObjectDefinition(
					Collections.singletonList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING,
							_OBJECT_FIELD_NAME_TEXT)),
					ObjectDefinitionConstants.SCOPE_COMPANY,
					TestPropsValues.getUserId());

			String objectDefinitionRESTContextPath =
				objectDefinition.getRESTContextPath();

			ObjectEntryTestUtil.addObjectEntry(
				objectDefinition, _OBJECT_FIELD_NAME_TEXT, "TestObject1");
			ObjectEntryTestUtil.addObjectEntry(
				objectDefinition, _OBJECT_FIELD_NAME_TEXT, "TestObject2");
			ObjectEntryTestUtil.addObjectEntry(
				objectDefinition, _OBJECT_FIELD_NAME_TEXT, "Object3");

			String queryParametersString = StringBundler.concat(
				"restrictFields=actions&filter=contains%28",
				_OBJECT_FIELD_NAME_TEXT, "%2C%20%27Test%27%29");

			JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					"headless-batch-engine/v1.0/export-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry/JSON?",
					"taskItemDelegateName=", objectDefinition.getName(), "&",
					queryParametersString),
				Http.Method.POST);

			String actualExecuteStatus = null;

			while (true) {
				jsonObject = HTTPTestUtil.invokeToJSONObject(
					null,
					StringBundler.concat(
						"headless-batch-engine/v1.0/export-task",
						"/by-external-reference-code/",
						jsonObject.getString("externalReferenceCode")),
					Http.Method.GET);

				actualExecuteStatus = jsonObject.getString("executeStatus");

				if (StringUtil.equals(actualExecuteStatus, "COMPLETED") ||
					StringUtil.equals(actualExecuteStatus, "FAILED")) {

					break;
				}
			}

			InputStream inputStream = HTTPTestUtil.invokeToInputStream(
				null,
				StringBundler.concat(
					"headless-batch-engine/v1.0/export-task",
					"/by-external-reference-code/",
					jsonObject.getString("externalReferenceCode"), "/content"),
				Http.Method.GET);

			ZipInputStream zipInputStream = new ZipInputStream(inputStream);

			zipInputStream.getNextEntry();

			String responseString = StringUtil.read(zipInputStream);

			JSONObject filteredPageJSONObject = HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					objectDefinitionRESTContextPath, "/?",
					queryParametersString),
				Http.Method.GET);

			JSONArray itemsJSONArray = filteredPageJSONObject.getJSONArray(
				"items");

			JSONAssert.assertEquals(
				itemsJSONArray.toString(), responseString,
				JSONCompareMode.LENIENT);

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	private void _testPostExportTask(
			String expectedExecuteStatus, ObjectDefinition objectDefinition)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				"headless-batch-engine/v1.0/export-task",
				"/com.liferay.object.rest.dto.v1_0.ObjectEntry/json?",
				"taskItemDelegateName=", objectDefinition.getName()),
			Http.Method.POST);

		String actualExecuteStatus = null;

		while (true) {
			jsonObject = HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					"headless-batch-engine/v1.0/export-task",
					"/by-external-reference-code/",
					jsonObject.getString("externalReferenceCode")),
				Http.Method.GET);

			actualExecuteStatus = jsonObject.getString("executeStatus");

			if (StringUtil.equals(actualExecuteStatus, "COMPLETED") ||
				StringUtil.equals(actualExecuteStatus, "FAILED")) {

				break;
			}
		}

		Assert.assertEquals(expectedExecuteStatus, actualExecuteStatus);
	}

	private static final String _OBJECT_FIELD_NAME_TEXT =
		"x" + RandomTestUtil.randomString();

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}