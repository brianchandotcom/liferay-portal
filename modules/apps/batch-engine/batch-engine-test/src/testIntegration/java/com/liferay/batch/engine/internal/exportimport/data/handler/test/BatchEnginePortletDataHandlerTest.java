/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.staging.StagingGroupHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Vendel Toreki
 */
@FeatureFlags("LPD-35914")
@RunWith(Arquillian.class)
public class BatchEnginePortletDataHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule liferayIntegrationTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(),
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
					RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_ATTACHMENT,
					Arrays.asList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.
								NAME_ACCEPTED_FILE_EXTENSIONS
						).value(
							"txt"
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_FILE_SOURCE
						).value(
							ObjectFieldSettingConstants.VALUE_USER_COMPUTER
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
						).value(
							"100"
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
					Arrays.asList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_UNIQUE_VALUES
						).value(
							Boolean.TRUE.toString()
						).build()),
					false)),
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_objectEntry1 = _addObjectEntry(
			_objectDefinition1, RandomTestUtil.randomString());
		_objectEntry2 = _addObjectEntry(
			_objectDefinition1, RandomTestUtil.randomString());
		_objectEntry3 = _addObjectEntry(
			_objectDefinition1, RandomTestUtil.randomString());

		Group companyGroup = _stagingGroupHelper.fetchCompanyGroup(
			_objectDefinition1.getCompanyId());

		_companyGroupId = companyGroup.getGroupId();

		_larFile = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), _companyGroupId, false,
							new long[0], _getExportParameterMap())));
	}

	@Test
	@TestInfo("LPD-50142")
	public void testExportImportCompanyGroup() throws Exception {
		_objectEntryLocalService.deleteObjectEntry(_objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry2);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry3);

		_importLayouts();

		Assert.assertNotNull(
			_objectEntryLocalService.getObjectEntry(
				_objectEntry1.getExternalReferenceCode(),
				_objectDefinition1.getObjectDefinitionId()));
		Assert.assertNotNull(
			_objectEntryLocalService.getObjectEntry(
				_objectEntry2.getExternalReferenceCode(),
				_objectDefinition1.getObjectDefinitionId()));
		Assert.assertNotNull(
			_objectEntryLocalService.getObjectEntry(
				_objectEntry3.getExternalReferenceCode(),
				_objectDefinition1.getObjectDefinitionId()));
	}

	@Test
	public void testExportImportCompanyGroupWithError() throws Exception {
		String objectFieldValue = (String)_objectEntry2.getValues(
		).get(
			_OBJECT_FIELD_NAME_TEXT
		);

		_objectEntryLocalService.deleteObjectEntry(_objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry2);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry3);

		ObjectEntry duplicateObjectEntry = _addObjectEntry(
			_objectDefinition1, objectFieldValue);

		try {
			try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
					"com.liferay.exportimport.internal.lifecycle." +
						"LoggerExportImportLifecycleListener",
					LoggerTestUtil.OFF);
				LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
					"com.liferay.batch.engine.internal." +
						"BatchEngineImportTaskExecutorImpl",
					LoggerTestUtil.OFF)) {

				_importLayouts();
			}

			Assert.fail();
		}
		catch (PortletDataException portletDataException) {
			String message = portletDataException.getMessage();

			Assert.assertTrue(message.contains(objectFieldValue));
		}

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, _objectDefinition1.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 2, objectEntries.size());

		duplicateObjectEntry = _objectEntryLocalService.getObjectEntry(
			duplicateObjectEntry.getExternalReferenceCode(),
			_objectDefinition1.getObjectDefinitionId());

		Assert.assertNotEquals(
			_objectEntry2.getExternalReferenceCode(),
			duplicateObjectEntry.getExternalReferenceCode());
	}

	@Test
	@TestInfo("LPD-50142")
	public void testExportIndividualDeletionsCompanyGroup() throws Exception {
		_objectEntryLocalService.deleteObjectEntry(_objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry2);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry3);

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(),
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
					false)),
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_objectEntry4 = _addObjectEntry(
			_objectDefinition2, RandomTestUtil.randomString());

		_objectEntryLocalService.deleteObjectEntry(_objectEntry4);

		Map<String, String[]> parameterMap =
			_getExportIndividualDeletionsParameterMap(
				Collections.singletonList(_objectDefinition1));

		File file = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), _companyGroupId, false,
							new long[0], parameterMap)));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				_objectEntry1.getExternalReferenceCode(),
				_objectEntry2.getExternalReferenceCode(),
				_objectEntry3.getExternalReferenceCode()
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				_companyGroupId, file
			).toString(),
			JSONCompareMode.STRICT);

		parameterMap = _getExportIndividualDeletionsParameterMap(
			Collections.singletonList(_objectDefinition2));

		file = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), _companyGroupId, true,
							new long[0], parameterMap)));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				_objectEntry4.getExternalReferenceCode()
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				_companyGroupId, file
			).toString(),
			JSONCompareMode.STRICT);

		parameterMap = _getExportIndividualDeletionsParameterMap(
			Arrays.asList(_objectDefinition1, _objectDefinition2));

		file = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), _companyGroupId, false,
							new long[0], parameterMap)));

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				_objectEntry1.getExternalReferenceCode(),
				_objectEntry2.getExternalReferenceCode(),
				_objectEntry3.getExternalReferenceCode(),
				_objectEntry4.getExternalReferenceCode()
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				_companyGroupId, file
			).toString(),
			JSONCompareMode.STRICT);
	}

	private ObjectEntry _addObjectEntry(
			ObjectDefinition objectDefinition, Serializable objectFieldValue)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0L,
			_objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.put(
				_OBJECT_FIELD_NAME_TEXT, objectFieldValue
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private JSONArray _getClassExternalReferenceCodesJSONArray(
			long groupId, File larFile)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(larFile)) {
			ZipEntry zipEntry = zipFile.getEntry(
				"group/" + groupId + "/deletion-system-events.xml");

			if (zipEntry == null) {
				throw new FileNotFoundException();
			}

			Document document = _saxReader.read(
				zipFile.getInputStream(zipEntry));

			Element rootElement = document.getRootElement();

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			for (Element deletionSystemEventElement :
					rootElement.elements("deletion-system-event")) {

				String classExternalReferenceCode =
					deletionSystemEventElement.attributeValue(
						"class-external-reference-code");

				jsonArray.put(classExternalReferenceCode);
			}

			return jsonArray;
		}
	}

	private Map<String, String[]> _getExportIndividualDeletionsParameterMap(
		List<ObjectDefinition> objectDefinitions) {

		Map<String, String[]> parameterMap = HashMapBuilder.put(
			"DELETIONS", new String[] {"true"}
		).put(
			"PERMISSIONS", new String[] {"false"}
		).put(
			"PORTLET_DATA", new String[] {"true"}
		).build();

		objectDefinitions.forEach(
			objectDefinition -> parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA + "_" +
					objectDefinition.getPortletId(),
				new String[] {Boolean.TRUE.toString()}));

		return parameterMap;
	}

	private Map<String, String[]> _getExportParameterMap() {
		return HashMapBuilder.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" +
				_objectDefinition1.getPortletId(),
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	private void _importLayouts() throws Exception {
		_exportImportLocalService.importLayouts(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(), _companyGroupId, false,
							null, _getExportParameterMap())),
			_larFile);
	}

	private static final String _OBJECT_FIELD_NAME_ATTACHMENT =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_TEXT =
		"x" + RandomTestUtil.randomString();

	private long _companyGroupId;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	private File _larFile;
	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;
	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;
	private ObjectEntry _objectEntry3;
	private ObjectEntry _objectEntry4;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private SAXReader _saxReader;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

}