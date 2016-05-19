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

package com.liferay.dynamic.data.lists.web.display.context;

import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordCreateDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class DDLViewRecordsDisplayContext {

	public DDLViewRecordsDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			long formDDMTemplateId)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;

		_ddlRecordSet = (DDLRecordSet)_liferayPortletRequest.getAttribute(
			DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

		_ddmStructure = _ddlRecordSet.getDDMStructure(formDDMTemplateId);
	}

	public OrderByComparator<DDLRecord> getDDLRecordOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDLRecord> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new DDLRecordModifiedDateComparator(orderByAsc);
		}
		else {
			orderByComparator = new DDLRecordCreateDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public DDLRecordSet getDDLRecordSet() {
		return _ddlRecordSet;
	}

	public List<DDMFormField> getDDMFormFields() {
		if (_ddmFormFields == null) {
			DDMForm ddmForm = _ddmStructure.getDDMForm();

			List<DDMFormField> ddmFormFields = new ArrayList<>();

			for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
				loadDDMFormFieldsList(ddmFormField, ddmFormFields);
			}

			int totalColumns = _TOTAL_COLUMNS;

			if (ddmFormFields.size() < totalColumns) {
				totalColumns = ddmFormFields.size();
			}

			_ddmFormFields = ddmFormFields.subList(0, totalColumns);
		}

		return _ddmFormFields;
	}

	public Map<String, List<DDMFormFieldValue>> getDDMFormFieldValuesMap(
			DDLRecordVersion recordVersion)
		throws StorageException {

		DDMFormValues ddmFormValues = recordVersion.getDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			new LinkedHashMap<>();

		for (DDMFormFieldValue ddmFormFieldValue :ddmFormFieldValues) {
			loadDDMFormFieldValuesMap(ddmFormFieldValue, ddmFormFieldValuesMap);
		}

		return ddmFormFieldValuesMap;
	}

	public DDMStructure getDDMStructure() {
		return _ddmStructure;
	}

	public String getDisplayStyle() {
		return "list";
	}

	public String getOrderByCol() {
		String orderByCol = ParamUtil.getString(
			_liferayPortletRequest, "orderByCol", "modified-date");

		return orderByCol;
	}

	public String getOrderByType() {
		String orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "asc");

		return orderByType;
	}

	protected boolean isDDMFormFieldTransient(DDMFormField ddmFormField) {
		if (Validator.isNull(ddmFormField.getDataType())) {
			return true;
		}

		return false;
	}

	protected void loadDDMFormFieldsList(
		DDMFormField ddmFormField, List<DDMFormField> ddmFormFields) {

		if (isDDMFormFieldTransient(ddmFormField)) {
			for (DDMFormField nestedDDMFormField :
					ddmFormField.getNestedDDMFormFields()) {

				loadDDMFormFieldsList(nestedDDMFormField, ddmFormFields);
			}
		}
		else {
			ddmFormFields.add(ddmFormField);
		}
	}

	protected void loadDDMFormFieldValuesMap(
		DDMFormFieldValue ddmFormFieldValue,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap) {

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			ddmFormFieldValue.getName());

		if (ddmFormFieldValues == null) {
			ddmFormFieldValues = new ArrayList<>();

			ddmFormFieldValuesMap.put(
				ddmFormFieldValue.getName(), ddmFormFieldValues);
		}

		ddmFormFieldValues.add(ddmFormFieldValue);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			loadDDMFormFieldValuesMap(
				nestedDDMFormFieldValue, ddmFormFieldValuesMap);
		}
	}

	private static final int _TOTAL_COLUMNS = 5;

	private final DDLRecordSet _ddlRecordSet;
	private List<DDMFormField> _ddmFormFields;
	private final DDMStructure _ddmStructure;
	private final LiferayPortletRequest _liferayPortletRequest;

}