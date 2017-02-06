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

package com.liferay.dynamic.data.lists.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
@Sync
public class DDLRecordStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testExportImportWithDocumentLibraryField() throws Exception {
		String documentLibraryFieldName = "Attachment";

		DDLRecordSet recordSet = addRecordSetWithDocumentLibraryField(
			documentLibraryFieldName);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			stagingGroup, recordSet);

		DDMFormValues ddmFormValues =
			recordTestHelper.createEmptyDDMFormValues();

		DDMFormFieldValue ddmFormFieldValue =
			createDocumentLibraryDDMFormFieldValue(
				ddmFormValues.getDefaultLocale(), documentLibraryFieldName);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDLRecord ddlRecord = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		exportImportStagedModel(ddlRecord);

		DDLRecord importedDDLRecord =
			DDLRecordLocalServiceUtil.getDDLRecordByUuidAndGroupId(
				ddlRecord.getUuid(), liveGroup.getGroupId());

		Assert.assertNotNull(importedDDLRecord);
	}

	@Test
	public void testExportImpotWithEmptyDocumentLibraryField()
		throws Exception {

		String documentLibraryFieldName = "Attachment";

		DDLRecordSet recordSet = addRecordSetWithDocumentLibraryField(
			documentLibraryFieldName);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			stagingGroup, recordSet);

		DDMFormValues ddmFormValues =
			recordTestHelper.createEmptyDDMFormValues();

		DDMFormFieldValue ddmFormFieldValue =
			createEmptyDocumentLibraryDDMFormFieldValue(
				ddmFormValues.getDefaultLocale(), documentLibraryFieldName);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		DDLRecord ddlRecord = recordTestHelper.addRecord(
			ddmFormValues, WorkflowConstants.ACTION_PUBLISH);

		exportImportStagedModel(ddlRecord);

		DDLRecord importedDDLRecord =
			DDLRecordLocalServiceUtil.getDDLRecordByUuidAndGroupId(
				ddlRecord.getUuid(), liveGroup.getGroupId());

		Assert.assertNotNull(importedDDLRecord);
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new LinkedHashMap<>();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDLRecordSet.class.getName());

		DDMTemplate ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(DDLRecordSet.class));

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate1);

		DDMTemplate ddmTemplate2 = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(DDLRecordSet.class));

		addDependentStagedModel(
			dependentStagedModelsMap, DDMTemplate.class, ddmTemplate2);

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			group);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(ddmStructure);

		addDependentStagedModel(
			dependentStagedModelsMap, DDLRecordSet.class, recordSet);

		addDependentStagedModel(
			dependentStagedModelsMap, DDMStructure.class, ddmStructure);

		return dependentStagedModelsMap;
	}

	protected DDLRecordSet addRecordSetWithDocumentLibraryField(
			String documentLibraryFieldName)
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField fileEntryFormField = DDMFormTestUtil.createDDMFormField(
			documentLibraryFieldName, documentLibraryFieldName,
			DDMFormFieldType.DOCUMENT_LIBRARY, "document-library", true, false,
			false);

		ddmForm.addDDMFormField(fileEntryFormField);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), DDLRecordSet.class.getName(), ddmForm);

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			stagingGroup);

		return recordSetTestHelper.addRecordSet(ddmStructure);
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDLRecordSet.class.getSimpleName());

		DDLRecordSet recordSet = (DDLRecordSet)dependentStagedModels.get(0);

		DDLRecordTestHelper recordTestHelper = new DDLRecordTestHelper(
			group, recordSet);

		return recordTestHelper.addRecord();
	}

	protected DDMFormFieldValue createDocumentLibraryDDMFormFieldValue(
			Locale locale, String fieldName)
		throws Exception {

		String fileName = "attachment.txt";

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.TEXT_PLAIN,
			FileUtil.getBytes(getClass(), "dependencies/" + fileName),
			serviceContext);

		DDMFormFieldValue ddmFormFieldValue =
			createEmptyDocumentLibraryDDMFormFieldValue(locale, fieldName);

		JSONObject fieldValueJSONObject = JSONFactoryUtil.createJSONObject();

		fieldValueJSONObject.put(
			"groupId", String.valueOf(fileEntry.getGroupId()));
		fieldValueJSONObject.put("uuid", fileEntry.getUuid());

		Value value = ddmFormFieldValue.getValue();

		value.addString(locale, fieldValueJSONObject.toJSONString());

		return ddmFormFieldValue;
	}

	protected DDMFormFieldValue createEmptyDocumentLibraryDDMFormFieldValue(
			Locale locale, String fieldName)
		throws Exception {

		LocalizedValue localizedValue = new LocalizedValue();

		JSONObject fieldValueJSONObject = JSONFactoryUtil.createJSONObject();

		localizedValue.addString(locale, fieldValueJSONObject.toJSONString());

		return DDMFormValuesTestUtil.createDDMFormFieldValue(
			fieldName, localizedValue);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return DDLRecordLocalServiceUtil.fetchDDLRecordByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return DDLRecord.class;
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

		List<StagedModel> ddmTemplateDependentStagedModels =
			dependentStagedModelsMap.get(DDMTemplate.class.getSimpleName());

		Assert.assertEquals(
			ddmTemplateDependentStagedModels.toString(), 2,
			ddmTemplateDependentStagedModels.size());

		for (StagedModel ddmTemplateDependentStagedModel :
				ddmTemplateDependentStagedModels) {

			DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
				ddmTemplateDependentStagedModel.getUuid(), group.getGroupId());
		}

		List<StagedModel> recordSetDependentStagedModels =
			dependentStagedModelsMap.get(DDLRecordSet.class.getSimpleName());

		Assert.assertEquals(
			recordSetDependentStagedModels.toString(), 1,
			recordSetDependentStagedModels.size());

		DDLRecordSet recordSet =
			(DDLRecordSet)recordSetDependentStagedModels.get(0);

		DDLRecordSetLocalServiceUtil.getDDLRecordSetByUuidAndGroupId(
			recordSet.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		DDLRecord record = (DDLRecord)stagedModel;
		DDLRecord importedRecord = (DDLRecord)importedStagedModel;

		Assert.assertEquals(
			record.getDisplayIndex(), importedRecord.getDisplayIndex());
	}

}