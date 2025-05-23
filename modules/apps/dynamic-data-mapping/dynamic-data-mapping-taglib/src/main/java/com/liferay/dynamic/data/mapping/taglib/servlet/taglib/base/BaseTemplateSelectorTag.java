/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base;

import com.liferay.dynamic.data.mapping.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.taglib.util.IncludeTag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

import java.util.List;

/**
 * @author Bruno Basto
 */
public abstract class BaseTemplateSelectorTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getClassName() {
		return _className;
	}

	public String getDefaultDisplayStyle() {
		return _defaultDisplayStyle;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public List<String> getDisplayStyles() {
		return _displayStyles;
	}

	public String getIcon() {
		return _icon;
	}

	public String getLabel() {
		return _label;
	}

	public String getRefreshURL() {
		return _refreshURL;
	}

	public boolean getShowEmptyOption() {
		return _showEmptyOption;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDefaultDisplayStyle(String defaultDisplayStyle) {
		_defaultDisplayStyle = defaultDisplayStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setDisplayStyles(List<String> displayStyles) {
		_displayStyles = displayStyles;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setRefreshURL(String refreshURL) {
		_refreshURL = refreshURL;
	}

	public void setShowEmptyOption(boolean showEmptyOption) {
		_showEmptyOption = showEmptyOption;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_defaultDisplayStyle = StringPool.BLANK;
		_displayStyle = null;
		_displayStyleGroupId = 0;
		_displayStyles = null;
		_icon = null;
		_label = "display-template";
		_refreshURL = null;
		_showEmptyOption = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setNamespacedAttribute(httpServletRequest, "className", _className);
		setNamespacedAttribute(
			httpServletRequest, "defaultDisplayStyle", _defaultDisplayStyle);
		setNamespacedAttribute(
			httpServletRequest, "displayStyle", _displayStyle);
		setNamespacedAttribute(
			httpServletRequest, "displayStyleGroupId", _displayStyleGroupId);
		setNamespacedAttribute(
			httpServletRequest, "displayStyles", _displayStyles);
		setNamespacedAttribute(httpServletRequest, "icon", _icon);
		setNamespacedAttribute(httpServletRequest, "label", _label);
		setNamespacedAttribute(httpServletRequest, "refreshURL", _refreshURL);
		setNamespacedAttribute(
			httpServletRequest, "showEmptyOption", _showEmptyOption);
	}

	protected static final String ATTRIBUTE_NAMESPACE =
		"liferay-ddm:template-selector:";

	private static final String _PAGE = "/template_selector/page.jsp";

	private String _className;
	private String _defaultDisplayStyle = StringPool.BLANK;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private List<String> _displayStyles;
	private String _icon;
	private String _label = "display-template";
	private String _refreshURL;
	private boolean _showEmptyOption;

}