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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class ManagementBarButtonTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return EVAL_BODY_INCLUDE;
	}

	public String getId() {
		return _id;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setIconCssClass(String iconCssClass) {
		_iconCssClass = iconCssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		_active = false;
		_cssClass = StringPool.BLANK;
		_data = null;
		_disabled = null;
		_href = null;
		_icon = StringPool.BLANK;
		_iconCssClass = StringPool.BLANK;
		_id = StringPool.BLANK;
		_label = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected boolean isDisabled() {
		ManagementBarTag managementBarTag =
			(ManagementBarTag)findAncestorWithClass(
				this, ManagementBarTag.class);

		boolean disabled = false;

		if (_disabled != null) {
			disabled = _disabled;
		}
		else if (managementBarTag != null) {
			disabled = managementBarTag.isDisabled();
		}

		return disabled;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "active", _active);
		setNamespacedAttribute(request, "cssClass", _cssClass);

		if (_data == null) {
			_data = new HashMap<>();
		}

		_data.put("qa-id", _label + "Button");

		setNamespacedAttribute(request, "data", _data);

		setNamespacedAttribute(request, "disabled", _disabled);
		setNamespacedAttribute(request, "href", _href);
		setNamespacedAttribute(request, "icon", _icon);
		setNamespacedAttribute(request, "iconCssClass", _iconCssClass);
		setNamespacedAttribute(request, "id", _id);
		setNamespacedAttribute(request, "label", _label);
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:management-bar-button:";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/management_bar_button/page.jsp";

	private boolean _active;
	private String _cssClass = StringPool.BLANK;
	private Map<String, Object> _data;
	private Boolean _disabled;
	private String _href;
	private String _icon = StringPool.BLANK;
	private String _iconCssClass = StringPool.BLANK;
	private String _id = StringPool.BLANK;
	private String _label = StringPool.BLANK;

}