/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.client.http.HttpInvoker;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolder;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFolderResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.Closeable;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mateus Santana
 */
@RunWith(Arquillian.class)
public class ObjectEntryPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	public static void assume() {
		Assume.assumeTrue(Validator.isNull(System.getenv("JENKINS_HOME")));
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class<?> clazz = ObjectEntryPerformanceTest.class;

		Properties properties = PropertiesUtil.load(
			clazz.getResourceAsStream(
				"dependencies/object-entry-performance.properties"),
			"UTF-8");

		_objectEntryCount = GetterUtil.getInteger(
			properties.getProperty("object.entries.count"));

		_objectEntryDeletionExpectedMaxTime = GetterUtil.getInteger(
			properties.getProperty("object.entries.deletion.expected.maxTime"));

		_objectEntryImportExpectedMaxTime = GetterUtil.getInteger(
			properties.getProperty("object.entries.import.expected.maxTime"));
	}

	@Test
	public void testGetObjectEntries() throws Exception {
		_customObjectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				false,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, "Performance",
						"performance")));

		_customObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_customObjectDefinition.getObjectDefinitionId());

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				_customObjectDefinition.getStorageType());

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, TestPropsValues.getUser());

		for (int i = 0; i < _objectEntryCount; i++) {
			objectEntryManager.addObjectEntry(
				dtoConverterContext, _customObjectDefinition,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							"performance", RandomTestUtil.randomString()
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);
		}

		try (Closeable closeable = new PerformanceTimer(
				60000,
				"Get all the Object Entries by ObjectEntryLocalService with " +
					"CustomObjectDefinitionId:" +
						_customObjectDefinition.getObjectDefinitionId())) {

			_objectEntryLocalService.getObjectEntries(
				0, _customObjectDefinition.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
	}

	@Test
	public void testImportAndDeleteByRestAPI() throws Throwable {
		TransactionConfig.Builder transactionConfigBuilder =
			new TransactionConfig.Builder();

		_company = TransactionInvokerUtil.invoke(
			transactionConfigBuilder.setPropagation(
				Propagation.REQUIRED
			).setRollbackForClasses(
				Exception.class
			).build(),
			() -> {
				Company company = CompanyLocalServiceUtil.addCompany(
					null, _VIRTUAL_HOST_NAME, _VIRTUAL_HOST_NAME,
					_VIRTUAL_HOST_NAME, 0, true, true, null, null, null, null,
					null, null);

				PortalInstances.initCompany(company);

				return company;
			});

		ObjectFolderResource.Builder objectFolderResourceBuilder =
			_objectFolderResourceFactory.create();

		ObjectFolderResource objectFolderResource =
			objectFolderResourceBuilder.user(
				UserTestUtil.getAdminUser(_company.getCompanyId())
			).build();

		objectFolderResource.setContextCompany(_company);

		JSONObject objectFolderJSONObject = _jsonFactory.createJSONObject(
			StringUtil.read(
				ObjectEntryPerformanceTest.class.getClassLoader(),
				"/com/liferay/object/web/internal/object/definitions/portlet" +
					"/action/test/dependencies/test-object-folder-4.json"));

		ObjectFolder objectFolder = ObjectFolder.toDTO(
			objectFolderJSONObject.toString());

		objectFolder.setName("SampleObjectFolder");

		objectFolderResource.putObjectFolderByExternalReferenceCode(
			objectFolder.getExternalReferenceCode(), objectFolder);

		try (PerformanceTimer performanceTimer1 = new PerformanceTimer(
				_objectEntryImportExpectedMaxTime,
				StringBundler.concat(
					" Import ", _objectEntryCount, " Object Entries"))) {

			_invokeHttp(
				_createObjectEntryJSON(), HttpInvoker.HttpMethod.POST,
				_getPath(_PATH_SUFFIX));

			List<ObjectDefinition> objectDefinitions =
				_objectDefinitionLocalService.getCustomObjectDefinitions(
					_CUSTOM_OBJECT_DEFINITION_STATUS);

			_customObjectDefinition = objectDefinitions.get(
				_OBJECT_DEFINITION_LIST_INDEX);

			long currentObjectEntryCount = 0;

			while (currentObjectEntryCount < _objectEntryCount) {
				currentObjectEntryCount =
					_objectEntryLocalService.getObjectEntriesCount(
						_customObjectDefinition.getObjectDefinitionId());
			}
		}

		try (PerformanceTimer performanceTimer = new PerformanceTimer(
				_objectEntryDeletionExpectedMaxTime,
				StringBundler.concat(
					" Delete ", _objectEntryCount, " Object Entries"))) {

			HttpInvoker.HttpResponse httpResponse = _invokeHttp(
				null, HttpInvoker.HttpMethod.GET,
				_getPath("/o/c/foos/?fields=id&pageSize=" + _objectEntryCount));

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				httpResponse.getContent());

			JSONArray jsonArray = (JSONArray)jsonObject.get("items");

			_invokeHttp(
				jsonArray.toString(), HttpInvoker.HttpMethod.DELETE,
				_getPath(_PATH_SUFFIX));

			long currentObjectEntryCount = _objectEntryCount;

			while (currentObjectEntryCount != 0) {
				currentObjectEntryCount =
					_objectEntryLocalService.getObjectEntriesCount(
						_customObjectDefinition.getObjectDefinitionId());
			}
		}
	}

	private String _createObjectEntryJSON() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < _objectEntryCount; i++) {
			jsonArray.put(JSONUtil.put("alpha", "foo"));
		}

		return jsonArray.toString();
	}

	private String _getPath(String pathSuffix) {
		return StringBundler.concat(
			"http://", _VIRTUAL_HOST_NAME, ":8080", pathSuffix);
	}

	private HttpInvoker.HttpResponse _invokeHttp(
			String bodyJSON, HttpInvoker.HttpMethod httpMethod, String path)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		if (bodyJSON != null) {
			httpInvoker.body(bodyJSON, "application/json");
		}

		httpInvoker.httpMethod(httpMethod);
		httpInvoker.path(path);
		httpInvoker.userNameAndPassword(
			StringBundler.concat(
				"test@", _VIRTUAL_HOST_NAME, ":",
				PropsValues.DEFAULT_ADMIN_PASSWORD));

		return httpInvoker.invoke();
	}

	private static final int _CUSTOM_OBJECT_DEFINITION_STATUS = 0;

	private static final int _OBJECT_DEFINITION_LIST_INDEX = 0;

	private static final String _PATH_SUFFIX = "/o/c/foos/batch";

	private static final String _VIRTUAL_HOST_NAME = "www.able.com";

	private static int _objectEntryCount;
	private static int _objectEntryDeletionExpectedMaxTime;
	private static int _objectEntryImportExpectedMaxTime;

	@DeleteAfterTestRun
	private Company _company;

	@DeleteAfterTestRun
	private ObjectDefinition _customObjectDefinition;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Inject
	private ObjectFolderResource.Factory _objectFolderResourceFactory;

}