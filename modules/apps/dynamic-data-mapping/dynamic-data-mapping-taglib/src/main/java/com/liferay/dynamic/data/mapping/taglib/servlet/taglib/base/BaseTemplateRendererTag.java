/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base;

import com.liferay.dynamic.data.mapping.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Bruno Basto
 */
public abstract class BaseTemplateRendererTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getClassName() {
		return _className;
	}

	public Map<String, Object> getContextObjects() {
		return _contextObjects;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public List<?> getEntries() {
		return _entries;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setContextObjects(Map<String, Object> contextObjects) {
		_contextObjects = contextObjects;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setEntries(List<?> entries) {
		_entries = entries;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_contextObjects = new HashMap<>();
		_displayStyle = null;
		_displayStyleGroupId = 0;
		_entries = null;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setNamespacedAttribute(httpServletRequest, "className", _className);
		setNamespacedAttribute(
			httpServletRequest, "contextObjects", _contextObjects);
		setNamespacedAttribute(
			httpServletRequest, "displayStyle", _displayStyle);
		setNamespacedAttribute(
			httpServletRequest, "displayStyleGroupId", _displayStyleGroupId);
		setNamespacedAttribute(httpServletRequest, "entries", _entries);
	}

	protected static final String ATTRIBUTE_NAMESPACE =
		"liferay-ddm:template-renderer:";

	private static final String _START_PAGE = "/template_renderer/start.jsp";

	private String _className;
	private Map<String, Object> _contextObjects = new HashMap<>();
	private String _displayStyle;
	private long _displayStyleGroupId;
	private List<?> _entries;

}