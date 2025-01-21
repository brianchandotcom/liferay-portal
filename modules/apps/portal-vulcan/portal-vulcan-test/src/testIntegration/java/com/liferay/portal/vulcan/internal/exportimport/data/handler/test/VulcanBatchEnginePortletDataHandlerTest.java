/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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
@RunWith(Arquillian.class)
public class VulcanBatchEnginePortletDataHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
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

		_objectEntry1 = _addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_TEXT,
			RandomTestUtil.randomString(), TestPropsValues.getUser());

		_objectEntry2 = _addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_TEXT,
			RandomTestUtil.randomString(), TestPropsValues.getUser());

		_objectEntry3 = _addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_TEXT,
			RandomTestUtil.randomString(), TestPropsValues.getUser());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		Group companyGroup = _stagingGroupHelper.fetchCompanyGroup(
			_objectDefinition1.getCompanyId());

		_companyGroupId = companyGroup.getGroupId();
	}

	@Test
	public void testExportImportInstanceLevelOnFailureNothingIsImported()
		throws Exception {

		_exportInstanceLevel();

		String existingValue = (String)_objectEntry2.getValues(
		).get(
			_OBJECT_FIELD_NAME_TEXT
		);

		_objectEntryLocalService.deleteObjectEntry(_objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry2);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry3);

		_objectEntry2 = _addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_TEXT, existingValue,
			TestPropsValues.getUser());

		try {
			_importInstanceLevel();

			Assert.fail("Import process should fail");
		}
		catch (PortletDataException portletDataException) {
			String message = portletDataException.getMessage();

			Assert.assertTrue(message.contains(existingValue));
		}

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, _objectDefinition1.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(objectEntries.toString(), 1, objectEntries.size());
	}

	@Test
	public void testExportImportInstanceLevelSuccess() throws Exception {
		_exportInstanceLevel();

		_objectEntryLocalService.deleteObjectEntry(_objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry2);
		_objectEntryLocalService.deleteObjectEntry(_objectEntry3);

		_importInstanceLevel();

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

	private ObjectEntry _addObjectEntry(
			ObjectDefinition objectDefinition, String objectFieldName,
			Serializable objectFieldValue, User user)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			user.getUserId(), 0L, objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectFieldName, objectFieldValue
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _exportInstanceLevel() throws Exception {
		User user = TestPropsValues.getUser();

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, _companyGroupId, false, new long[0],
					_getExportParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		_larFile = _exportImportLocalService.exportLayoutsAsFile(
			exportImportConfiguration);
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

	private Map<String, String[]> _getImportParameterMap() {
		return _getExportParameterMap();
	}

	private void _importInstanceLevel() throws Exception {
		User user = TestPropsValues.getUser();

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					user, _companyGroupId, false, null,
					_getImportParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, _larFile);
	}

	private static final String _OBJECT_FIELD_NAME_TEXT = "testFieldName";

	private long _companyGroupId;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	private File _larFile;
	private ObjectDefinition _objectDefinition1;
	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;
	private ObjectEntry _objectEntry3;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

}