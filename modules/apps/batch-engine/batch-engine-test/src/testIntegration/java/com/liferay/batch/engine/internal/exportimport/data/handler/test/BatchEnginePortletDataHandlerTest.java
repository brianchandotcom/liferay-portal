/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
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
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Vendel Toreki
 * @author Petteri Karttunen
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-35914"), @FeatureFlag("LPD-39967")}
)
@RunWith(Arquillian.class)
public class BatchEnginePortletDataHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule liferayIntegrationTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		_invokeFeatureFlagListeners(true);
	}

	@AfterClass
	public static void tearDownClass() {
		_invokeFeatureFlagListeners(false);
	}

	@Test
	@TestInfo("LPD-51604")
	public void testEnableLocalStaging() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.exportimport.internal.lifecycle." +
					"LoggerExportImportLifecycleListener",
				LoggerTestUtil.ERROR)) {

			_stagingLocalService.enableLocalStaging(
				TestPropsValues.getUserId(), group, false, false,
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId()));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			for (LogEntry logEntry : logEntries) {
				String message = logEntry.getMessage();

				Assert.assertFalse(
					message,
					message.contains(
						"Portlet export failed for portlet com_liferay_object" +
							"_web_internal_object_definitions_portlet" +
								"_ObjectDefinitionsPortlet"));
			}

			Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());
		}
	}

	@Test
	@TestInfo("LPD-50142")
	public void testExportImportCompanyGroupObjectEntries() throws Exception {
		_testExportImportObjectEntriesToSameGroup(
			_stagingGroupHelper.fetchCompanyGroup(
				TestPropsValues.getCompanyId()),
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	@Test
	public void testExportImportCompanyGroupObjectEntriesWithError()
		throws Exception {

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, 0L, objectDefinition);

		File larFile = _exportLayouts(
			false, group.getGroupId(), false, objectDefinition);

		_deleteObjectEntries(objectEntries);

		ObjectEntry objectEntry = objectEntries[1];

		Map<String, Serializable> values = objectEntry.getValues();

		ObjectEntry duplicateObjectEntry = _addObjectEntry(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition,
			values.get(_OBJECT_FIELD_NAME_TEXT));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.batch.engine.internal.strategy." +
					"OnErrorContinueBatchEngineImportStrategy",
				LoggerTestUtil.OFF)) {

			_importLayouts(
				false, larFile, group.getGroupId(), objectDefinition);
		}

		List<ObjectEntry> objectEntriesList =
			_objectEntryLocalService.getObjectEntries(
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			objectEntriesList.toString(), 3, objectEntriesList.size());

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				objectEntry.getExternalReferenceCode(),
				objectDefinition.getObjectDefinitionId()));
		Assert.assertNotEquals(
			objectEntry.getExternalReferenceCode(),
			duplicateObjectEntry.getExternalReferenceCode());
		Assert.assertTrue(
			ListUtil.exists(
				_batchEngineImportTaskLocalService.getBatchEngineImportTasks(
					BatchEngineTaskExecuteStatus.COMPLETED.toString()),
				batchEngineImportTask -> Objects.equals(
					batchEngineImportTask.getTaskItemDelegateName(),
					objectDefinition.getName())));
	}

	@Ignore("LPD-40798")
	@Test
	public void testExportImportSiteObjectEntriesToOtherSite()
		throws Exception {

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_SITE);

		Group group1 = GroupTestUtil.addGroup();

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, group1.getGroupId(), objectDefinition);

		File larFile = _exportLayouts(
			false, group1.getGroupId(), false, objectDefinition);

		Group group2 = GroupTestUtil.addGroup();

		_importLayouts(false, larFile, group2.getGroupId(), objectDefinition);

		List<ObjectEntry> objectEntriesList =
			_objectEntryLocalService.getObjectEntries(
				group2.getGroupId(), objectDefinition.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			Arrays.toString(objectEntries), objectEntriesList.size(),
			objectEntries.length);
	}

	@Test
	public void testExportImportSiteObjectEntriesToSameSite() throws Exception {
		_testExportImportObjectEntriesToSameGroup(
			GroupTestUtil.addGroup(), ObjectDefinitionConstants.SCOPE_SITE);
	}

	@Test
	@TestInfo("LPD-50142")
	public void testExportIndividualDeletionsCompanyGroup() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		ObjectDefinition objectDefinition1 = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition1);

		_deleteObjectEntries(objectEntries);

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
						false)),
				ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry objectEntry = _addObjectEntry(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition2,
			RandomTestUtil.randomString());

		_deleteObjectEntries(objectEntry);

		File file = _exportLayouts(
			true, group.getGroupId(), false, objectDefinition1);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				_getExternalReferenceCodes(objectEntries)
			).toString(),
			_getExternalReferenceCodesJSON(
				objectDefinition1.getName(), file, group.getGroupId()),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				file, group.getGroupId()
			).toString(),
			JSONCompareMode.STRICT);

		file = _exportLayouts(
			true, group.getGroupId(), true, objectDefinition2);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				objectEntry.getExternalReferenceCode()
			).toString(),
			_getExternalReferenceCodesJSON(
				objectDefinition2.getName(), file, group.getGroupId()),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				file, group.getGroupId()
			).toString(),
			JSONCompareMode.STRICT);

		file = _exportLayouts(
			true, group.getGroupId(), false, objectDefinition1,
			objectDefinition2);

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				_getExternalReferenceCodes(objectEntries)
			).toString(),
			_getExternalReferenceCodesJSON(
				objectDefinition1.getName(), file, group.getGroupId()),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
				objectEntry.getExternalReferenceCode()
			).toString(),
			_getExternalReferenceCodesJSON(
				objectDefinition2.getName(), file, group.getGroupId()),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.putAll(
			).toString(),
			_getClassExternalReferenceCodesJSONArray(
				file, group.getGroupId()
			).toString(),
			JSONCompareMode.STRICT);
	}

	@Test
	@TestInfo("LPD-49421")
	public void testImportIndividualDeletionsCompanyGroup() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		ObjectDefinition objectDefinition = _addObjectDefinition(
			ObjectDefinitionConstants.SCOPE_COMPANY);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, GroupConstants.DEFAULT_PARENT_GROUP_ID, objectDefinition);

		File larFile1 = _exportLayouts(
			false, group.getGroupId(), false, objectDefinition);

		_deleteObjectEntries(objectEntries[0], objectEntries[1]);

		File larFile2 = _exportLayouts(
			true, group.getGroupId(), false, objectDefinition);

		_deleteObjectEntries(objectEntries[2]);

		_importLayouts(false, larFile1, group.getGroupId(), objectDefinition);

		_assertObjectEntries(
			objectDefinition.getObjectDefinitionId(), objectEntries);

		_importLayouts(false, larFile2, group.getGroupId(), objectDefinition);

		_assertObjectEntries(
			objectDefinition.getObjectDefinitionId(), objectEntries);

		_importLayouts(true, larFile2, group.getGroupId(), objectDefinition);

		_assertObjectEntries(
			objectDefinition.getObjectDefinitionId(), objectEntries[2]);
		_assertNull(
			objectDefinition.getObjectDefinitionId(), objectEntries[0],
			objectEntries[1]);
	}

	private static void _invokeFeatureFlagListeners(boolean enabled) {
		try (ServiceTrackerList<FeatureFlagListener> featureFlagListeners =
				ServiceTrackerListFactory.open(
					SystemBundleUtil.getBundleContext(),
					FeatureFlagListener.class, "(featureFlagKey=LPD-35914)")) {

			for (FeatureFlagListener featureFlagListener :
					featureFlagListeners) {

				featureFlagListener.onValue(
					CompanyConstants.SYSTEM, "LPD-35914", enabled);
			}
		}
	}

	private ObjectDefinition _addObjectDefinition(String scope)
		throws Exception {

		return ObjectDefinitionTestUtil.publishObjectDefinition(
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
			scope);
	}

	private ObjectEntry[] _addObjectEntries(
			int count, long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		ObjectEntry[] objectEntries = new ObjectEntry[count];

		for (int i = 0; i < count; i++) {
			objectEntries[i] = _addObjectEntry(
				groupId, objectDefinition, RandomTestUtil.randomString());
		}

		return objectEntries;
	}

	private ObjectEntry _addObjectEntry(
			long groupId, ObjectDefinition objectDefinition,
			Serializable objectFieldValue)
		throws Exception {

		FileEntry tempFileEntry1 = _addTempFileEntry(objectDefinition);

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), groupId,
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_ATTACHMENT, tempFileEntry1.getFileEntryId()
			).put(
				_OBJECT_FIELD_NAME_TEXT, objectFieldValue
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private FileEntry _addTempFileEntry(ObjectDefinition objectDefinition)
		throws Exception {

		return TempFileEntryUtil.addTempFileEntry(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			objectDefinition.getPortletId(),
			TempFileEntryUtil.getTempFileName(
				_OBJECT_FIELD_VALUE_ATTACHMENT + ".txt"),
			FileUtil.createTempFile(_OBJECT_FIELD_VALUE_ATTACHMENT.getBytes()),
			ContentTypes.TEXT_PLAIN);
	}

	private void _assertNull(
		long objectDefinitionId, ObjectEntry... objectEntries) {

		for (ObjectEntry objectEntry : objectEntries) {
			Assert.assertNull(
				_objectEntryLocalService.fetchObjectEntry(
					objectEntry.getExternalReferenceCode(),
					objectDefinitionId));
		}
	}

	private void _assertObjectEntries(
			long objectDefinitionId, ObjectEntry... objectEntries)
		throws Exception {

		for (ObjectEntry objectEntry : objectEntries) {
			ObjectEntry importedObjectEntry =
				_objectEntryLocalService.getObjectEntry(
					objectEntry.getExternalReferenceCode(), objectDefinitionId);

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
				MapUtil.getLong(
					importedObjectEntry.getValues(),
					_OBJECT_FIELD_NAME_ATTACHMENT));

			Assert.assertEquals(
				_OBJECT_FIELD_VALUE_ATTACHMENT,
				StringUtil.read(dlFileEntry.getContentStream()));
		}
	}

	private void _deleteObjectEntries(ObjectEntry... objectEntries)
		throws Exception {

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryLocalService.deleteObjectEntry(objectEntry);
		}
	}

	private File _exportLayouts(
			boolean deletions, long groupId, boolean privateLayouts,
			ObjectDefinition... objectDefinitions)
		throws Exception {

		return _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), groupId, privateLayouts,
							new long[0],
							_getExportImportParameterMap(
								deletions, Arrays.asList(objectDefinitions)))));
	}

	private String _getBatchFileNameWithPath(String fileName, long groupId) {
		return StringBundler.concat(
			"group/", groupId, StringPool.FORWARD_SLASH, fileName);
	}

	private JSONArray _getClassExternalReferenceCodesJSONArray(
			File file, long groupId)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry(
				_getBatchFileNameWithPath(
					"deletion-system-events.xml", groupId));

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

	private Map<String, String[]> _getExportImportParameterMap(
		boolean deletions, List<ObjectDefinition> objectDefinitions) {

		Map<String, String[]> parameterMap = HashMapBuilder.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.toString(deletions)}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();

		objectDefinitions.forEach(
			objectDefinition -> parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA + "_" +
					objectDefinition.getPortletId(),
				new String[] {Boolean.TRUE.toString()}));

		return parameterMap;
	}

	private String[] _getExternalReferenceCodes(ObjectEntry... objectEntries) {
		String[] externalReferenceCodes = new String[objectEntries.length];

		for (int i = 0; i < objectEntries.length; i++) {
			externalReferenceCodes[i] =
				objectEntries[i].getExternalReferenceCode();
		}

		return externalReferenceCodes;
	}

	private String _getExternalReferenceCodesJSON(
			String className, File file, long groupId)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry(
				_getBatchFileNameWithPath(
					className + "_deletions.json", groupId));

			if (zipEntry == null) {
				throw new FileNotFoundException();
			}

			JSONArray jsonArray1 = JSONFactoryUtil.createJSONArray();

			JSONArray jsonArray2 = JSONFactoryUtil.createJSONArray(
				StringUtil.read(zipFile.getInputStream(zipEntry)));

			for (int i = 0; i < jsonArray2.length(); i++) {
				JSONObject jsonObject = jsonArray2.getJSONObject(i);

				jsonArray1.put(jsonObject.getString("externalReferenceCode"));
			}

			return jsonArray1.toString();
		}
	}

	private long _getObjectEntryGroupId(long groupId, String scope) {
		if (Objects.equals(ObjectDefinitionConstants.SCOPE_COMPANY, scope)) {
			return GroupConstants.DEFAULT_PARENT_GROUP_ID;
		}

		return groupId;
	}

	private void _importLayouts(
			boolean deletions, File file, long groupId,
			ObjectDefinition... objectDefinitions)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(), groupId, false, null,
							_getExportImportParameterMap(
								deletions, Arrays.asList(objectDefinitions))));

		if (deletions) {
			_exportImportLocalService.importLayoutsDataDeletions(
				exportImportConfiguration, file);
		}

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, file);
	}

	private void _testExportImportObjectEntriesToSameGroup(
			Group group, String scope)
		throws Exception {

		ObjectDefinition objectDefinition = _addObjectDefinition(scope);

		ObjectEntry[] objectEntries = _addObjectEntries(
			3, _getObjectEntryGroupId(group.getGroupId(), scope),
			objectDefinition);

		File larFile = _exportLayouts(
			false, group.getGroupId(), false, objectDefinition);

		_deleteObjectEntries(objectEntries);

		_importLayouts(false, larFile, group.getGroupId(), objectDefinition);

		_assertObjectEntries(
			objectDefinition.getObjectDefinitionId(), objectEntries);
	}

	private static final String _OBJECT_FIELD_NAME_ATTACHMENT =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_TEXT =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_ATTACHMENT =
		RandomTestUtil.randomString();

	@Inject
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private SAXReader _saxReader;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

	@Inject
	private StagingLocalService _stagingLocalService;

}