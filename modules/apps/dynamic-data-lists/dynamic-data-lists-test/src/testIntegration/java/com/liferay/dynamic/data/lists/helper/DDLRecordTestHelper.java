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

package com.liferay.dynamic.data.lists.helper;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
public class DDLRecordTestHelper {

	public DDLRecordTestHelper(Group group, DDLRecordSet recordSet)
		throws Exception {

		_group = group;
		_recordSet = recordSet;
	}

	public DDLRecord addRecord() throws Exception {
		DDMForm ddmForm = getDDMForm();

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm,
			DDMFormValuesTestUtil.createAvailableLocales(LocaleUtil.US),
			LocaleUtil.US);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			if (ddmFormField.isLocalizable()) {
				ddmFormValues.addDDMFormFieldValue(
					createLocalizedDDMFormFieldValue(
						ddmFormField.getName(), RandomTestUtil.randomString()));
			}
			else {
				ddmFormValues.addDDMFormFieldValue(
					createUnlocalizedDDMFormFieldValue(
						ddmFormField.getName(), RandomTestUtil.randomString()));
			}
		}

		return addRecord(ddmFormValues, WorkflowConstants.ACTION_PUBLISH);
	}

	public DDLRecord addRecord(DDMFormValues ddmFormValues, int workflowAction)
		throws Exception {

		return DDLRecordLocalServiceUtil.addRecord(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_recordSet.getRecordSetId(),
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
			DDLRecordTestUtil.getServiceContext(workflowAction));
	}

	public DDMFormValues createEmptyDDMFormValues() throws PortalException {
		return DDMFormValuesTestUtil.createDDMFormValues(getDDMForm());
	}

	public DDLRecordSet getRecordSet() {
		return _recordSet;
	}

	public DDLRecord updateRecord(
			long recordId, boolean majorVersion, int displayIndex,
			DDMFormValues ddmFormValues, int workflowAction)
		throws Exception {

		return DDLRecordLocalServiceUtil.updateRecord(
			TestPropsValues.getUserId(), recordId, majorVersion, displayIndex,
			ddmFormValues, DDLRecordTestUtil.getServiceContext(workflowAction));
	}

	protected DDMFormFieldValue createLocalizedDDMFormFieldValue(
		String name, String enValue) {

		return DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
			name, enValue);
	}

	protected DDMFormFieldValue createUnlocalizedDDMFormFieldValue(
		String name, String value) {

		return DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
			name, value);
	}

	protected DDMForm getDDMForm() throws PortalException {
		DDMStructure ddmStructure = _recordSet.getDDMStructure();

		return ddmStructure.getDDMForm();
	}

	private final Group _group;
	private final DDLRecordSet _recordSet;

}