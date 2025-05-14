/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base;

import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Bruno Basto
 */
public abstract class BaseHTMLFieldTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public Field getField() {
		return _field;
	}

	public String getFieldsNamespace() {
		return _fieldsNamespace;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public boolean getRepeatable() {
		return _repeatable;
	}

	public Locale getRequestedLocale() {
		return _requestedLocale;
	}

	public boolean getShowEmptyFieldLabel() {
		return _showEmptyFieldLabel;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setField(Field field) {
		_field = field;
	}

	public void setFieldsNamespace(String fieldsNamespace) {
		_fieldsNamespace = fieldsNamespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	public void setRepeatable(boolean repeatable) {
		_repeatable = repeatable;
	}

	public void setRequestedLocale(Locale requestedLocale) {
		_requestedLocale = requestedLocale;
	}

	public void setShowEmptyFieldLabel(boolean showEmptyFieldLabel) {
		_showEmptyFieldLabel = showEmptyFieldLabel;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_classNameId = 0;
		_classPK = 0;
		_field = null;
		_fieldsNamespace = null;
		_readOnly = false;
		_repeatable = true;
		_requestedLocale = null;
		_showEmptyFieldLabel = true;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setNamespacedAttribute(httpServletRequest, "classNameId", _classNameId);
		setNamespacedAttribute(httpServletRequest, "classPK", _classPK);
		setNamespacedAttribute(httpServletRequest, "field", _field);
		setNamespacedAttribute(
			httpServletRequest, "fieldsNamespace", _fieldsNamespace);
		setNamespacedAttribute(httpServletRequest, "readOnly", _readOnly);
		setNamespacedAttribute(httpServletRequest, "repeatable", _repeatable);
		setNamespacedAttribute(
			httpServletRequest, "requestedLocale", _requestedLocale);
		setNamespacedAttribute(
			httpServletRequest, "showEmptyFieldLabel", _showEmptyFieldLabel);
	}

	protected static final String ATTRIBUTE_NAMESPACE =
		"liferay-ddm:html-field:";

	private static final String _START_PAGE = "/html_field/start.jsp";

	private long _classNameId;
	private long _classPK;
	private Field _field;
	private String _fieldsNamespace;
	private boolean _readOnly;
	private boolean _repeatable = true;
	private Locale _requestedLocale;
	private boolean _showEmptyFieldLabel = true;

}