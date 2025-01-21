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
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mateus Santana
 */
@Ignore
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

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = ObjectEntryPerformanceTest.class;

		Properties properties = PropertiesUtil.load(
			clazz.getResourceAsStream(
				"dependencies/object-entry-performance.properties"),
			"UTF-8");

		_objectEntryCount = GetterUtil.getInteger(
			properties.getProperty("object.entries.count"));
	}

	@Test
	public void testGetObjectEntriesByObjectEntryLocalService()
		throws Exception {

		_publishCustomObjectDefinitionByObjectDefinitionLocalService();

		_addObjectEntriesByObjectEntryManager(_objectEntryCount);

		try (Closeable closeable = new PerformanceTimer(
				60000,
				"Get all the Object Entries by ObjectEntryLocalService with " +
					"CustomObjectDefinitionId:" +
						_customObjectDefinition.getObjectDefinitionId())) {

			_objectEntries = _objectEntryLocalService.getObjectEntries(
				0, _customObjectDefinition.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}

		_deleteObjectEntriesByObjectEntryLocalService();
	}

	@Test
	public void testImportAndDeleteObjectEntryByObjectRestAPI()
		throws Exception {

		_setUpCompanyForObjectRestAPITest();
		_setUpObjectFolderFromJSON();
		_testImportObjectEntryByObjectRestAPI();
		_testDeleteObjectEntryByObjectRestAPI();
	}

	private void _addObjectEntriesByObjectEntryManager(Integer numberOfEntries)
		throws Exception {

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				_customObjectDefinition.getStorageType());

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, TestPropsValues.getUser());

		for (int counter = 0; counter < numberOfEntries; counter++) {
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
	}

	private String _createObjectEntryJSON() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < _objectEntryCount; i++) {
			jsonArray.put(JSONUtil.put("alpha", "foo"));
		}

		return jsonArray.toString();
	}

	private void _deleteObjectEntriesByObjectEntryLocalService()
		throws Exception {

		for (com.liferay.object.model.ObjectEntry objectEntry :
				_objectEntries) {

			_objectEntryLocalService.deleteObjectEntry(objectEntry);
		}
	}

	private JSONArray _getObjectEntryIdJSONArray() throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.userNameAndPassword(_getUserNameAndPassword());
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			_getPath("/o/c/foos/?fields=id&pageSize=" + _objectEntryCount));

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

	private void _publishCustomObjectDefinitionByObjectDefinitionLocalService()
		throws Exception {

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
	}

	private void _setUpCompanyForObjectRestAPITest() throws Exception {
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

	private void _setUpObjectFolderFromJSON() throws Exception {
		ObjectFolderResource.Builder builder =
			_objectFolderResourceFactory.create();

		ObjectFolderResource objectFolderResource = builder.user(
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
	}

	private void _testDeleteObjectEntryByObjectRestAPI() throws Exception {
		try (PerformanceTimer performanceTimer = new PerformanceTimer(
				7000,
				StringBundler.concat(
					" Delete ", _objectEntryCount, " Object Entries"))) {

			JSONArray jsonArray = _getObjectEntryIdJSONArray();

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(jsonArray.toString(), "application/json");
			httpInvoker.userNameAndPassword(_getUserNameAndPassword());
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);
			httpInvoker.path(_getPath(_PATH_SUFFIX));

			httpInvoker.invoke();

			long currentObjectEntryCount = _objectEntryCount;

			while (currentObjectEntryCount != 0) {
				currentObjectEntryCount =
					_objectEntryLocalService.getObjectEntriesCount(
						_customObjectDefinition.getObjectDefinitionId());
			}
		}
	}

	private void _testImportObjectEntryByObjectRestAPI() throws Exception {
		try (PerformanceTimer performanceTimer = new PerformanceTimer(
				12000,
				StringBundler.concat(
					" Import ", _objectEntryCount, " Object Entries"))) {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(_createObjectEntryJSON(), "application/json");
			httpInvoker.userNameAndPassword(_getUserNameAndPassword());
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
			httpInvoker.path(_getPath(_PATH_SUFFIX));

			httpInvoker.invoke();

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
	}

	private static final int _CUSTOM_OBJECT_DEFINITION_STATUS = 0;

	private static final int _OBJECT_DEFINITION_LIST_INDEX = 0;

	private static final String _PATH_SUFFIX = "/o/c/foos/batch";

	private static final String _VIRTUAL_HOST_NAME = "www.able.com";

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

	private List<com.liferay.object.model.ObjectEntry> _objectEntries;
	private int _objectEntryCount;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Inject
	private ObjectFolderResource.Factory _objectFolderResourceFactory;

}