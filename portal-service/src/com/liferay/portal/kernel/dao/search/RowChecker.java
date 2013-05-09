/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class RowChecker {

	public static final String ALIGN = "left";

	public static final String ALL_ROW_IDS = "allRowIds";

	public static final int COLSPAN = 1;

	public static final String CSS_CLASS = StringPool.BLANK;

	public static final String FORM_NAME = "fm";

	public static final String ROW_IDS = "rowIds";

	public static final String VALIGN = "middle";

	public RowChecker(PortletResponse portletResponse) {
		_portletResponse = portletResponse;
		_allRowIds = _portletResponse.getNamespace() + ALL_ROW_IDS;
		_formName = _portletResponse.getNamespace() + FORM_NAME;
		_rowIds = _portletResponse.getNamespace() + ROW_IDS;
	}

	public String getAlign() {
		return _align;
	}

	public String getAllRowIds() {
		return _allRowIds;
	}

	public String getAllRowsCheckBox() {
		return getAllRowsCheckbox(_allRowIds, StringUtil.quote(_rowIds));
	}

	public String getAllRowsId() {
		return getAllRowIds();
	}

	public int getColspan() {
		return _colspan;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getFormName() {
		return _formName;
	}

	public String getRowCheckBox(
		HttpServletRequest request, boolean checked, boolean disabled,
		String primaryKey) {

		return getRowCheckBox(
			checked, disabled, _rowIds, primaryKey, StringUtil.quote(_rowIds),
			StringUtil.quote(_allRowIds), StringPool.BLANK);
	}

	public String getRowId() {
		return getRowIds();
	}

	public String getRowIds() {
		return _rowIds;
	}

	public String getValign() {
		return _valign;
	}

	public boolean isChecked(Object obj) {
		return false;
	}

	public boolean isDisabled(Object obj) {
		return false;
	}

	public void setAlign(String align) {
		_align = align;
	}

	public void setAllRowIds(String allRowIds) {
		_allRowIds = getNamespacedValue(allRowIds);
	}

	public void setColspan(int colspan) {
		_colspan = colspan;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setFormName(String formName) {
		_formName = getNamespacedValue(formName);
	}

	public void setRowIds(String rowIds) {
		_rowIds = getNamespacedValue(rowIds);
	}

	public void setValign(String valign) {
		_valign = valign;
	}

	protected String getAllRowsCheckbox(String name, String checkBoxRowIds) {
		if (Validator.isNull(name)) {
			return StringPool.BLANK;
		}
		else {
			StringBuilder sb = new StringBuilder(9);

			sb.append("<input name=\"");
			sb.append(name);
			sb.append("\" type=\"checkbox\" ");
			sb.append("onClick=\"Liferay.Util.checkAll(");
			sb.append("AUI().one(this).ancestor('");
			sb.append(".table'), ");
			sb.append(checkBoxRowIds);
			sb.append(", this, 'tr:not(.lfr-template)'");
			sb.append(");\">");

			return sb.toString();
		}
	}

	protected String getNamespacedValue(String value) {
		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}
		else {
			if (!value.startsWith(_portletResponse.getNamespace())) {
				value = _portletResponse.getNamespace() + value;
			}

			return value;
		}
	}

	protected String getRowCheckBox(
		boolean checked, boolean disabled, String name, String value,
		String checkBoxRowIds, String checkBoxAllRowIds,
		String checkBoxPostOnClick) {

		StringBundler sb = new StringBundler();

		sb.append("<input ");

		if (checked) {
			sb.append("checked ");
		}

		if (disabled) {
			sb.append("disabled ");
		}

		sb.append("name=\"");
		sb.append(name);
		sb.append("\" type=\"checkbox\" value=\"");
		sb.append(value);
		sb.append("\" ");

		if (Validator.isNotNull(_allRowIds)) {
			sb.append("onClick=\"Liferay.Util.checkAllBox(");
			sb.append("AUI().one(this).ancestor('");
			sb.append(".table'), ");
			sb.append(checkBoxRowIds);
			sb.append(", ");
			sb.append(checkBoxAllRowIds);
			sb.append(");");
			sb.append("AUI().one(this).ancestor('tr:not(.lfr-template)').toggleClass('");
			sb.append("info');");

			if (Validator.isNotNull(checkBoxPostOnClick)) {
				sb.append(checkBoxPostOnClick);
			}

			sb.append("\"");
		}

		sb.append(">");

		return sb.toString();
	}

	private String _align = ALIGN;
	private String _allRowIds;
	private int _colspan = COLSPAN;
	private String _cssClass = CSS_CLASS;
	private String _formName;
	private PortletResponse _portletResponse;
	private String _rowIds;
	private String _valign = VALIGN;

}