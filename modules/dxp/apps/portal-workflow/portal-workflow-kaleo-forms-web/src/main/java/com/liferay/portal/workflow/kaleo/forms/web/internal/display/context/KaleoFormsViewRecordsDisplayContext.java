/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.web.internal.display.context;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordCreateDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsWebKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class KaleoFormsViewRecordsDisplayContext {

	public KaleoFormsViewRecordsDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;

		_kaleoProcess = (KaleoProcess)_liferayPortletRequest.getAttribute(
			KaleoFormsWebKeys.KALEO_PROCESS);

		_ddlRecordSet = _kaleoProcess.getDDLRecordSet();
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

	public List<DDMFormField> getDDMFormFields() throws PortalException {
		if (_ddmFormFields != null) {
			return _ddmFormFields;
		}

		DDMStructure ddmStructure = _ddlRecordSet.getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			if (isDDMFormFieldTransient(ddmFormField)) {
				continue;
			}

			ddmFormFields.add(ddmFormField);
		}

		int totalColumns = _TOTAL_COLUMNS;

		if (ddmFormFields.size() < totalColumns) {
			totalColumns = ddmFormFields.size();
		}

		_ddmFormFields = ddmFormFields.subList(0, totalColumns);

		return _ddmFormFields;
	}

	public String getDisplayStyle() {
		return "list";
	}

	public KaleoProcess getKaleoProcess() {
		return _kaleoProcess;
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

	private static final int _TOTAL_COLUMNS = 5;

	private final DDLRecordSet _ddlRecordSet;
	private List<DDMFormField> _ddmFormFields;
	private final KaleoProcess _kaleoProcess;
	private final LiferayPortletRequest _liferayPortletRequest;

}