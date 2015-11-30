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
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.ArrayList;
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

	public DDLRecordSet getDDLRecordSet() {
		return _ddlRecordSet;
	}

	public List<DDMFormField> getDDMFormFields() {
		if (_ddmFormFields == null) {
			DDMForm ddmForm = _ddmStructure.getDDMForm();

			Map<String, DDMFormField> ddmFormFieldsMap =
				ddmForm.getDDMFormFieldsMap(true);

			List<DDMFormField> ddmFormFields = new ArrayList<>();

			for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
				if (hasValue(ddmFormField)) {
					ddmFormFields.add(ddmFormField);
				}
			}

			int totalColumns = _TOTAL_COLUMNS;

			if (ddmFormFields.size() < totalColumns) {
				totalColumns = ddmFormFields.size();
			}

			_ddmFormFields = ddmFormFields.subList(0, totalColumns);
		}

		return _ddmFormFields;
	}

	public DDMStructure getDDMStructure() {
		return _ddmStructure;
	}

	public String getDisplayStyle() {
		return "list";
	}

	protected boolean hasValue(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (type.equals(DDMFormFieldType.FIELDSET) ||
			type.equals(DDMFormFieldType.SEPARATOR)) {

			return false;
		}

		return true;
	}

	private static final int _TOTAL_COLUMNS = 5;

	private final DDLRecordSet _ddlRecordSet;
	private List<DDMFormField> _ddmFormFields;
	private final DDMStructure _ddmStructure;
	private final LiferayPortletRequest _liferayPortletRequest;

}