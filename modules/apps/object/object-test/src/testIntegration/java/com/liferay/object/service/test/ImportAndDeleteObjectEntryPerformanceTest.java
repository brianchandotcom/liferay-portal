/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.client.http.HttpInvoker;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolder;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFolderResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.performance.PerformanceTimer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lily Chi
 */
@RunWith(Arquillian.class)
public class ImportAndDeleteObjectEntryPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(Validator.isNull(System.getenv("JENKINS_HOME")));
	}

	@Test
	public void testImportAndDeleteObjectEntry() throws Exception {
		_setUpCompany();
		_setUpObjectFolder();
		_testImportObjectEntry();
		_testDeleteObjectEntry();
	}

	private String _createObjectEntryJSON() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < _OBJECT_ENTRY_COUNT; i++) {
			jsonArray.put(JSONUtil.put("alpha", "foo"));
		}

		return jsonArray.toString();
	}

	private JSONArray _getObjectEntryIdJSONArray() throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.userNameAndPassword(_getUserNameAndPassword());
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			_getPath("/o/c/foos/?fields=id&pageSize=" + _OBJECT_ENTRY_COUNT));

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		return (JSONArray)jsonObject.get("items");
	}

	private String _getPath(String pathSuffix) {
		return StringBundler.concat(
			"http://", _VIRTUAL_HOST_NAME, ":8080", pathSuffix);
	}

	private String _getUserNameAndPassword() {
		return StringBundler.concat(
			"test@", _VIRTUAL_HOST_NAME, ":",
			PropsValues.DEFAULT_ADMIN_PASSWORD);
	}

	private void _setUpCompany() throws Exception {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRED);
		builder.setRollbackForClasses(Exception.class);

		TransactionConfig transactionConfig = builder.build();

		try {
			_company = TransactionInvokerUtil.invoke(
				transactionConfig,
				() -> {
					Company company = CompanyLocalServiceUtil.addCompany(
						null, _VIRTUAL_HOST_NAME, _VIRTUAL_HOST_NAME,
						_VIRTUAL_HOST_NAME, 0, true, true, null, null, null,
						null, null, null);

					PortalInstances.initCompany(company);

					return company;
				});
		}
		catch (Exception exception) {
			throw exception;
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
	}

	private void _setUpObjectFolder() throws Exception {
		ObjectFolderResource.Builder builder =
			_objectFolderResourceFactory.create();

		ObjectFolderResource objectFolderResource = builder.user(
			UserTestUtil.getAdminUser(_company.getCompanyId())
		).build();

		objectFolderResource.setContextCompany(_company);

		JSONObject objectFolderJSONObject = _jsonFactory.createJSONObject(
			StringUtil.read(
				ImportAndDeleteObjectEntryPerformanceTest.class.
					getClassLoader(),
				"/com/liferay/object/web/internal/object/definitions/portlet" +
					"/action/test/dependencies/test-object-folder-4.json"));

		ObjectFolder objectFolder = ObjectFolder.toDTO(
			objectFolderJSONObject.toString());

		objectFolder.setName("SampleObjectFolder");

		objectFolderResource.putObjectFolderByExternalReferenceCode(
			objectFolder.getExternalReferenceCode(), objectFolder);
	}

	private void _testDeleteObjectEntry() throws Exception {
		try (PerformanceTimer performanceTimer = new PerformanceTimer(
				7000,
				StringBundler.concat(
					" Delete ", _OBJECT_ENTRY_COUNT, " Object Entries"))) {

			JSONArray jsonArray = _getObjectEntryIdJSONArray();

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(jsonArray.toString(), "application/json");
			httpInvoker.userNameAndPassword(_getUserNameAndPassword());
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);
			httpInvoker.path(_getPath(_PATH_SUFFIX));

			httpInvoker.invoke();

			long currentObjectEntryCount = _OBJECT_ENTRY_COUNT;

			while (currentObjectEntryCount != 0) {
				currentObjectEntryCount =
					_objectEntryLocalService.getObjectEntriesCount(
						_objectDefinition.getObjectDefinitionId());
			}
		}
	}

	private void _testImportObjectEntry() throws Exception {
		try (PerformanceTimer performanceTimer = new PerformanceTimer(
				12000,
				StringBundler.concat(
					" Import ", _OBJECT_ENTRY_COUNT, " Object Entries"))) {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(_createObjectEntryJSON(), "application/json");
			httpInvoker.userNameAndPassword(_getUserNameAndPassword());
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
			httpInvoker.path(_getPath(_PATH_SUFFIX));

			httpInvoker.invoke();

			List<ObjectDefinition> objectDefinitions =
				_objectDefinitionLocalService.getCustomObjectDefinitions(
					_CUSTOM_OBJECT_DEFINITION_STATUS);

			_objectDefinition = objectDefinitions.get(
				_OBJECT_DEFINITION_LIST_INDEX);

			long currentObjectEntryCount = 0;

			while (currentObjectEntryCount < _OBJECT_ENTRY_COUNT) {
				currentObjectEntryCount =
					_objectEntryLocalService.getObjectEntriesCount(
						_objectDefinition.getObjectDefinitionId());
			}
		}
	}

	private static final int _CUSTOM_OBJECT_DEFINITION_STATUS = 0;

	private static final int _OBJECT_DEFINITION_LIST_INDEX = 0;

	private static final int _OBJECT_ENTRY_COUNT = 100;

	private static final String _PATH_SUFFIX = "/o/c/foos/batch";

	private static final String _VIRTUAL_HOST_NAME = "www.able.com";

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private JSONFactory _jsonFactory;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFolderResource.Factory _objectFolderResourceFactory;

}