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

package com.liferay.expando.taglib.servlet.taglib;

import com.liferay.expando.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class CustomAttributeListTag extends IncludeTag {

	public Set<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getIgnoreAttributeNames() {
		return _ignoreAttributeNames;
	}

	public boolean isEditable() {
		return _editable;
	}

	public boolean isLabel() {
		return _label;
	}

	public void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
	}

	public void setIgnoreAttributeNames(String ignoreAttributeNames) {
		_ignoreAttributeNames = ignoreAttributeNames;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_availableLocales = null;
		_className = null;
		_classPK = 0;
		_editable = false;
		_ignoreAttributeNames = null;
		_label = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-expando:custom-attribute-list:availableLocales",
			_availableLocales);
		httpServletRequest.setAttribute(
			"liferay-expando:custom-attribute-list:className", _className);
		httpServletRequest.setAttribute(
			"liferay-expando:custom-attribute-list:classPK",
			String.valueOf(_classPK));
		httpServletRequest.setAttribute(
			"liferay-expando:custom-attribute-list:editable",
			String.valueOf(_editable));
		httpServletRequest.setAttribute(
			"liferay-expando:custom-attribute-list:ignoreAttributeNames",
			_ignoreAttributeNames);
		httpServletRequest.setAttribute(
			"liferay-expando:custom-attribute-list:label",
			String.valueOf(_label));
	}

	private static final String _PAGE = "/custom_attribute_list/page.jsp";

	private Set<Locale> _availableLocales;
	private String _className;
	private long _classPK;
	private boolean _editable;
	private String _ignoreAttributeNames;
	private boolean _label;

}