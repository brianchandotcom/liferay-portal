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
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
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
import com.liferay.portal.util.PropsValues;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ExportTaskResourceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExportTaskInDifferentCompany() throws Exception {
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

			_testExportTask("COMPLETED", objectDefinition1);

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

			_testExportTask("FAILED", objectDefinition2);

			HTTPTestUtil.customize(
			).withBaseURL(
				"http://www.able.com:8080"
			).withCredentials(
				"test@able.com", PropsValues.DEFAULT_ADMIN_PASSWORD
			).apply(
				() -> {
					_testExportTask("COMPLETED", objectDefinition2);
					_testExportTask("FAILED", objectDefinition1);
				}
			);

			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition1);
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition2);

			_companyLocalService.deleteCompany(jsonObject.getLong("companyId"));
		}
	}

	private void _testExportTask(
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
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}