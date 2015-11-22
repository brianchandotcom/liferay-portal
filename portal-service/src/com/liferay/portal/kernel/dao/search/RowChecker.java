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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;
import java.util.Map;

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
		return getAllRowsCheckBox(null);
	}

	public String getAllRowsCheckBox(HttpServletRequest request) {
		return getAllRowsCheckbox(
			request, _allRowIds, StringUtil.quote(_rowIds));
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

	public Map<String, Object> getData(Object obj) {
		return _data;
	}

	public String getFormName() {
		return _formName;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by  {@link
	 *             #getRowCheckBox(HttpServletRequest, boolean, boolean,
	 *             String)}
	 */
	@Deprecated
	public String getRowCheckBox(
		boolean checked, boolean disabled, String primaryKey) {

		return getRowCheckBox(null, checked, disabled, primaryKey);
	}

	public String getRowCheckBox(
		HttpServletRequest request, boolean checked, boolean disabled,
		String primaryKey) {

		return getRowCheckBox(
			request, checked, disabled, _rowIds, primaryKey,
			StringUtil.quote(_rowIds), StringUtil.quote(_allRowIds),
			StringPool.BLANK);
	}

	public String getRowId() {
		return getRowIds();
	}

	public String getRowIds() {
		return _rowIds;
	}

	public String getRowSelector() {
		return _rowSelector;
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

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setFormName(String formName) {
		_formName = getNamespacedValue(formName);
	}

	public void setRowIds(String rowIds) {
		_rowIds = getNamespacedValue(rowIds);
	}

	public void setRowSelector(String rowSelector) {
		_rowSelector = getNamespacedValue(rowSelector);
	}

	public void setValign(String valign) {
		_valign = valign;
	}

	protected String getAllRowsCheckbox(
		HttpServletRequest request, String name, String checkBoxRowIds) {

		if (Validator.isNull(name)) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder(10);

		sb.append("<label><input name=\"");
		sb.append(name);
		sb.append("\" title=\"");
		sb.append(LanguageUtil.get(getLocale(request), "select-all"));
		sb.append("\" type=\"checkbox\" ");
		sb.append(HtmlUtil.buildData(_data));
		sb.append("onClick=\"Liferay.Util.checkAll(AUI().one(this).ancestor(");
		sb.append("'.table'), ");
		sb.append(checkBoxRowIds);
		sb.append(", this, 'tr:not(.lfr-template)');\"></label>");

		return sb.toString();
	}

	protected Locale getLocale(HttpServletRequest request) {
		Locale locale = null;

		if (request != null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			locale = themeDisplay.getLocale();
		}
		else {
			locale = LocaleThreadLocal.getThemeDisplayLocale();
		}

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		return locale;
	}

	protected String getNamespacedValue(String value) {
		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		if (!value.startsWith(_portletResponse.getNamespace())) {
			value = _portletResponse.getNamespace() + value;
		}

		return value;
	}

	protected String getOnClick(
		String checkBoxRowIds, String checkBoxAllRowIds,
		String checkBoxPostOnClick) {

		StringBundler sb = new StringBundler(9);

		sb.append("onClick=\"Liferay.Util.rowCheckerCheckAllBox(AUI().");
		sb.append("one(this).ancestor('.table'), AUI().one(this).");
		sb.append("ancestor('tr:not(.lfr-template)'), ");
		sb.append(checkBoxRowIds);
		sb.append(", ");
		sb.append(checkBoxAllRowIds);
		sb.append(", 'info');");

		if (Validator.isNotNull(checkBoxPostOnClick)) {
			sb.append(checkBoxPostOnClick);
		}

		sb.append("\"");

		return sb.toString();
	}

	protected String getRowCheckBox(
		HttpServletRequest request, boolean checked, boolean disabled,
		String name, String value, String checkBoxRowIds,
		String checkBoxAllRowIds, String checkBoxPostOnClick) {

		StringBundler sb = new StringBundler(14);

		sb.append("<label><input ");

		if (checked) {
			sb.append("checked ");
		}

		if (disabled) {
			sb.append("disabled ");
		}

		sb.append("class=\"");
		sb.append(_cssClass);
		sb.append("\" name=\"");
		sb.append(name);
		sb.append("\" title=\"");
		sb.append(LanguageUtil.get(request.getLocale(), "select"));
		sb.append("\" type=\"checkbox\" value=\"");
		sb.append(HtmlUtil.escapeAttribute(value));
		sb.append("\" ");

		if (Validator.isNotNull(_allRowIds)) {
			sb.append(
				getOnClick(
					checkBoxRowIds, checkBoxAllRowIds, checkBoxPostOnClick));
		}

		sb.append("></label>");

		return sb.toString();
	}

	private String _align = ALIGN;
	private String _allRowIds;
	private int _colspan = COLSPAN;
	private String _cssClass = CSS_CLASS;
	private Map<String, Object> _data;
	private String _formName;
	private final PortletResponse _portletResponse;
	private String _rowIds;
	private String _rowSelector;
	private String _valign = VALIGN;

}