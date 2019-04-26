/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceRecordTestHelper;
import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceTestHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 * @author Pedro Queiroz
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceRecordStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Ignore
	@Override
	@Test
	public void testCleanStagedModelDataHandler() throws Exception {
		super.testCleanStagedModelDataHandler();
	}

	@Test
	public void testVersionMatchingAfterExportImport() throws Exception {
		String fieldName = "Text";

		DDMFormInstanceRecordTestHelper ddmFormInstanceRecordTestHelper =
			new DDMFormInstanceRecordTestHelper(
				stagingGroup, addFormInstanceWithTextField(fieldName));

		DDMFormValues ddmFormValues =
			ddmFormInstanceRecordTestHelper.createEmptyDDMFormValues();

		DDMFormFieldValue ddmFormFieldValue = createTextDDMFormFieldValue(
			ddmFormValues.getDefaultLocale(), fieldName, "text 1");

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordTestHelper.addDDMFormInstanceRecord(
				ddmFormValues);

		String version = "2.0";

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecord.getFormInstanceRecordVersion();

		ddmFormInstanceRecord.setVersion(version);
		ddmFormInstanceRecordVersion.setVersion(version);

		DDMFormInstanceRecordVersionLocalServiceUtil.
			updateDDMFormInstanceRecordVersion(ddmFormInstanceRecordVersion);
		DDMFormInstanceRecordLocalServiceUtil.updateDDMFormInstanceRecord(
			ddmFormInstanceRecord);

		exportImportStagedModel(ddmFormInstanceRecord);

		DDMFormInstanceRecord importedDDMFormInstanceRecord =
			DDMFormInstanceRecordLocalServiceUtil.
				getDDMFormInstanceRecordByUuidAndGroupId(
					ddmFormInstanceRecord.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals(
			ddmFormInstanceRecord.getVersion(),
			importedDDMFormInstanceRecord.getVersion());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new LinkedHashMap<>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDMFormInstance.class.getName());

		DDMFormInstanceTestHelper ddmFormInstanceTestHelper =
			new DDMFormInstanceTestHelper(group);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceTestHelper.addDDMFormInstance(ddmStructure);

		addDependentStagedModel(
			dependentStagedModelsMap, DDMFormInstance.class, ddmFormInstance);

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		return dependentStagedModelsMap;
	}

	protected DDMFormInstance addFormInstanceWithTextField(String fieldName)
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField textDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			fieldName, true, false, false);

		ddmForm.addDDMFormField(textDDMFormField);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), DDMFormInstance.class.getName(),
			ddmForm);

		DDMFormInstanceTestHelper ddmFormInstanceTestHelper =
			new DDMFormInstanceTestHelper(stagingGroup);

		return ddmFormInstanceTestHelper.addDDMFormInstance(ddmStructure);
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMFormInstance.class.getSimpleName());

		DDMFormInstance ddmFormInstance =
			(DDMFormInstance)dependentStagedModels.get(0);

		DDMFormInstanceRecordTestHelper ddmFormInstanceRecordTestHelper =
			new DDMFormInstanceRecordTestHelper(group, ddmFormInstance);

		return ddmFormInstanceRecordTestHelper.addDDMFormInstanceRecord();
	}

	protected DDMFormFieldValue createTextDDMFormFieldValue(
		Locale locale, String fieldName, String fieldValue) {

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(locale, fieldValue);

		return DDMFormValuesTestUtil.createDDMFormFieldValue(
			fieldName, localizedValue);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return DDMFormInstanceRecordLocalServiceUtil.
			getDDMFormInstanceRecordByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DDMFormInstanceRecord.class;
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> ddmStructureDependentStagedModels =
			dependentStagedModelsMap.get(DDMStructure.class.getSimpleName());

		Assert.assertEquals(
			ddmStructureDependentStagedModels.toString(), 1,
			ddmStructureDependentStagedModels.size());

		DDMStructure ddmStructure =
			(DDMStructure)ddmStructureDependentStagedModels.get(0);

		DDMStructureLocalServiceUtil.getDDMStructureByUuidAndGroupId(
			ddmStructure.getUuid(), group.getGroupId());

		List<StagedModel> formInstanceDependentStagedModels =
			dependentStagedModelsMap.get(DDMFormInstance.class.getSimpleName());

		Assert.assertEquals(
			formInstanceDependentStagedModels.toString(), 1,
			formInstanceDependentStagedModels.size());

		DDMFormInstance formInstance =
			(DDMFormInstance)formInstanceDependentStagedModels.get(0);

		DDMFormInstanceLocalServiceUtil.getDDMFormInstanceByUuidAndGroupId(
			formInstance.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		DDMFormInstanceRecord ddmFormInstanceRecord =
			(DDMFormInstanceRecord)stagedModel;
		DDMFormInstanceRecord importedDDMFormInstanceRecord =
			(DDMFormInstanceRecord)importedStagedModel;

		Assert.assertEquals(
			ddmFormInstanceRecord.getDDMFormValues(),
			importedDDMFormInstanceRecord.getDDMFormValues());
	}

}