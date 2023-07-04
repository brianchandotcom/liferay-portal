/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leonardo Barros
 * @author Pedro Queiroz
 */
@RunWith(Arquillian.class)
@Sync
public class DDMFormInstanceStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_originalCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		_settingsDDMFormValues =
			DDMFormInstanceTestUtil.createSettingsDDMFormValues();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		CompanyThreadLocal.setCompanyId(_originalCompanyId);
	}

	@Test
	public void testImportExportDDMFormInstanceWithObjectStorageType()
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), "text")));

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("text");

		DDMFormField ddmFormField = ddmForm.getDDMFormFields(
		).get(
			0
		);

		ddmFormField.setProperty(
			"objectFieldName",
			JSONUtil.put(
				"text"
			).toString());

		Group group = stagingGroup;

		DDMStructure structure = DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), group.getGroupId(), 0,
			PortalUtil.getClassNameId(DDMFormInstance.class.getName()), null,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			null, ddmForm, DDMUtil.getDefaultDDMFormLayout(ddmForm), "object",
			DDMStructureConstants.TYPE_DEFAULT,
			ServiceContextTestUtil.getServiceContext());

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"objectDefinitionId",
				new UnlocalizedValue(
					JSONUtil.put(
						String.valueOf(objectDefinition.getObjectDefinitionId())
					).toString())));
		settingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"storageType",
				new UnlocalizedValue(
					JSONUtil.put(
						"object"
					).toString())));

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceLocalServiceUtil.addFormInstance(
				structure.getUserId(), structure.getGroupId(),
				structure.getStructureId(), structure.getNameMap(),
				structure.getNameMap(), settingsDDMFormValues,
				ServiceContextTestUtil.getServiceContext(
					group, TestPropsValues.getUserId()));

		Assert.assertEquals("object", ddmFormInstance.getStorageType());

		_assertExportImport(objectDefinition, ddmFormInstance);
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new LinkedHashMap<>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDMFormInstance.class.getName());

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		DDMStructure ddmStructure = (DDMStructure)dependentStagedModels.get(0);

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				ddmStructure, group, _settingsDDMFormValues,
				TestPropsValues.getUserId());

		return DDMFormInstanceTestUtil.updateDDMFormInstance(
			ddmFormInstance.getFormInstanceId(), _settingsDDMFormValues);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return DDMFormInstanceLocalServiceUtil.
			getDDMFormInstanceByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DDMFormInstance.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		DDMFormInstance importedFormInstance =
			(DDMFormInstance)importedStagedModel;

		Assert.assertEquals(
			_settingsDDMFormValues,
			importedFormInstance.getSettingsDDMFormValues());
	}

	private void _assertDDMFormInstanceSettings(
			String expectedObjectDefinitionExternalReferenceCode,
			long expectedObjectDefinitionId, String settings)
		throws Exception {

		JSONObject settingsJSONObject = _jsonFactory.createJSONObject(settings);

		Assert.assertEquals(
			expectedObjectDefinitionExternalReferenceCode,
			settingsJSONObject.getString(
				"objectDefinitionExternalReferenceCode", null));

		JSONArray fieldValuesJSONArray = settingsJSONObject.getJSONArray(
			"fieldValues");

		for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
			JSONObject fieldValueJSONObject =
				fieldValuesJSONArray.getJSONObject(i);

			if (!StringUtil.equals(
					fieldValueJSONObject.getString("name"),
					"objectDefinitionId")) {

				continue;
			}

			Assert.assertEquals(
				expectedObjectDefinitionId,
				_jsonFactory.createJSONArray(
					fieldValueJSONObject.getString("value")
				).getLong(
					0
				));
		}
	}

	private void _assertExportImport(
			ObjectDefinition objectDefinition, StagedModel stagedModel)
		throws Exception {

		initExport();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		initImport();

		_assertDDMFormInstanceSettings(
			objectDefinition.getExternalReferenceCode(),
			objectDefinition.getObjectDefinitionId(),
			portletDataContext.getZipEntryAsString(
				ExportImportPathUtil.getModelPath(
					stagedModel, "settings-ddm-form-values.json")));

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition.getObjectDefinitionId());

		ObjectDefinition objectDefinition2 =
			ObjectDefinitionLocalServiceUtil.addObjectDefinition(
				objectDefinition.getExternalReferenceCode(),
				TestPropsValues.getUserId(), true, false);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, stagedModel);

		DDMFormInstance importedDDMFormInstance =
			(DDMFormInstance)getStagedModel(stagedModel.getUuid(), liveGroup);

		_assertDDMFormInstanceSettings(
			null, objectDefinition2.getObjectDefinitionId(),
			importedDDMFormInstance.getSettings());

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition2.getObjectDefinitionId());
	}

	private static final JSONFactory _jsonFactory = new JSONFactoryImpl();

	private long _originalCompanyId;
	private DDMFormValues _settingsDDMFormValues;

}