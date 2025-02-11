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
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.staging.StagingGroupHelper;

import java.io.File;
import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(),
			Arrays.asList(
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

		_objectEntry1 = _addObjectEntry(RandomTestUtil.randomString());

		_objectEntry2 = _addObjectEntry(RandomTestUtil.randomString());

		_objectEntry3 = _addObjectEntry(RandomTestUtil.randomString());

		Group companyGroup = _stagingGroupHelper.fetchCompanyGroup(
			_objectDefinition.getCompanyId());

		_companyGroupId = companyGroup.getGroupId();
	}

	@Test
	public void testExportImportCompanyGroup() throws Exception {
		File larFile = _exportCompanyGroup();

		_objectEntryLocalService.deleteObjectEntry(_objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry2);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry3);

		_importCompanyGroup(larFile);

		Assert.assertNotNull(
			_objectEntryLocalService.getObjectEntry(
				_objectEntry1.getExternalReferenceCode(),
				_objectDefinition.getObjectDefinitionId()));
		Assert.assertNotNull(
			_objectEntryLocalService.getObjectEntry(
				_objectEntry2.getExternalReferenceCode(),
				_objectDefinition.getObjectDefinitionId()));
		Assert.assertNotNull(
			_objectEntryLocalService.getObjectEntry(
				_objectEntry3.getExternalReferenceCode(),
				_objectDefinition.getObjectDefinitionId()));
	}

	@Test
	public void testExportImportCompanyGroupWithError() throws Exception {
		File larFile = _exportCompanyGroup();

		String existingValue = (String)_objectEntry2.getValues(
		).get(
			_OBJECT_FIELD_NAME_TEXT
		);

		_objectEntryLocalService.deleteObjectEntry(_objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry2);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry3);

		ObjectEntry duplicateObjectEntry = _addObjectEntry(existingValue);

		try {
			try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
					"com.liferay.exportimport.internal.lifecycle." +
						"LoggerExportImportLifecycleListener",
					LoggerTestUtil.OFF);
				LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
					"com.liferay.batch.engine.internal." +
						"BatchEngineImportTaskExecutorImpl",
					LoggerTestUtil.OFF)) {

				_importCompanyGroup(larFile);
			}

			Assert.fail("Import process should fail");
		}
		catch (PortletDataException portletDataException) {
			String message = portletDataException.getMessage();

			Assert.assertTrue(message.contains(existingValue));
		}

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());

		duplicateObjectEntry = _objectEntryLocalService.getObjectEntry(
			duplicateObjectEntry.getExternalReferenceCode(),
			_objectDefinition.getObjectDefinitionId());

		Assert.assertNotEquals(
			_objectEntry2.getExternalReferenceCode(),
			duplicateObjectEntry.getExternalReferenceCode());
	}

	private ObjectEntry _addObjectEntry(Serializable objectFieldValue)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0L,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.put(
				_OBJECT_FIELD_NAME_TEXT, objectFieldValue
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private File _exportCompanyGroup() throws Exception {
		return _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), _companyGroupId, false,
							new long[0], _getExportParameterMap())));
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
				_objectDefinition.getPortletId(),
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	private void _importCompanyGroup(File larFile) throws Exception {
		_exportImportLocalService.importLayouts(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(), _companyGroupId, false,
							null, _getExportParameterMap())),
			larFile);
	}

	private static final String _OBJECT_FIELD_NAME_TEXT =
		"a" + RandomTestUtil.randomString();

	private long _companyGroupId;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	private ObjectDefinition _objectDefinition;
	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;
	private ObjectEntry _objectEntry3;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

}