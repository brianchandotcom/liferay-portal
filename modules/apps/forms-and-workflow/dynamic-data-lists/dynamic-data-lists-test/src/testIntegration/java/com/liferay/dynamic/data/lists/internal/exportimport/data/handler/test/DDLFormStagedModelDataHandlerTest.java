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
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.Sync;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
@Sync
public class DDLFormStagedModelDataHandlerTest
	extends DDLRecordSetStagedModelDataHandlerTest {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_recordSetSettingsDDMFormValues =
			createRecordSetSettingsDDMFormValues();
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		DDLRecordSetTestHelper recordSetTestHelper = new DDLRecordSetTestHelper(
			group);

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			DDMStructure.class.getSimpleName());

		DDMStructure ddmStructure = (DDMStructure)dependentStagedModels.get(0);

		DDLRecordSet recordSet = recordSetTestHelper.addRecordSet(
			ddmStructure, DDLRecordSetConstants.SCOPE_FORMS);

		return recordSetTestHelper.updateRecordSet(
			recordSet.getRecordSetId(), _recordSetSettingsDDMFormValues);
	}

	protected DDMFormValues createRecordSetSettingsDDMFormValues() {
		DDMForm recordSetSettingsDDMForm = DDMFormFactory.create(
			DDLRecordSetSettings.class);

		DDMFormValues recordSetSettingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(recordSetSettingsDDMForm);

		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailFromName", "Joe Bloggs"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailFromAddress", "from@liferay.com"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailSubject", "New Form Submission"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"emailToAddress", "to@liferay.com"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"published", "Joe Bloggs"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"redirectURL", "http://www.google.com"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"requireCaptcha", "true"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"sendEmailNotification", "true"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"storageType", "json"));
		recordSetSettingsDDMFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"workflowDefinition", "Single Approver@1"));

		return recordSetSettingsDDMFormValues;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		DDLRecordSet importedRecordSet = (DDLRecordSet)importedStagedModel;

		Assert.assertEquals(
			_recordSetSettingsDDMFormValues,
			importedRecordSet.getSettingsDDMFormValues());
	}

	private DDMFormValues _recordSetSettingsDDMFormValues;

}