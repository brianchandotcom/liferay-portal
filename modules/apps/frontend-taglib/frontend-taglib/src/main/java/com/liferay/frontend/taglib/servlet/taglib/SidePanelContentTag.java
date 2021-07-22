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
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Fabio Diego Mastrorilli
 */
public class SidePanelContentTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		HttpServletRequest httpServletRequest = getRequest();

		setNamespacedAttribute(
			httpServletRequest, "screenNavigatorKey", _screenNavigatorKey);
		setNamespacedAttribute(
			httpServletRequest, "screenNavigatorModelBean",
			_screenNavigatorModelBean);
		setNamespacedAttribute(
			httpServletRequest, "screenNavigatorPortletURL",
			_screenNavigatorPortletURL);
		setNamespacedAttribute(
			httpServletRequest, "showCloseButton", _showCloseButton);
		setNamespacedAttribute(httpServletRequest, "sidePanelId", _sidePanelId);
		setNamespacedAttribute(httpServletRequest, "title", _title);

		super.doStartTag();

		return EVAL_BODY_INCLUDE;
	}

	public String getScreenNavigatorKey() {
		return _screenNavigatorKey;
	}

	public Object getScreenNavigatorModelBean() {
		return _screenNavigatorModelBean;
	}

	public PortletURL getScreenNavigatorPortletURL() {
		return _screenNavigatorPortletURL;
	}

	public boolean getShowCloseButton() {
		return _showCloseButton;
	}

	public String getSidePanelId() {
		return _sidePanelId;
	}

	public String getTitle() {
		return _title;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setScreenNavigatorKey(String screenNavigatorKey) {
		_screenNavigatorKey = screenNavigatorKey;
	}

	public void setScreenNavigatorModelBean(Object screenNavigatorModelBean) {
		_screenNavigatorModelBean = screenNavigatorModelBean;
	}

	public void setScreenNavigatorPortletURL(
		PortletURL screenNavigatorPortletURL) {

		_screenNavigatorPortletURL = screenNavigatorPortletURL;
	}

	public void setShowCloseButton(boolean showCloseButton) {
		_showCloseButton = showCloseButton;
	}

	public void setSidePanelId(String sidePanelId) {
		_sidePanelId = sidePanelId;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_screenNavigatorKey = null;
		_screenNavigatorModelBean = null;
		_screenNavigatorPortletURL = null;
		_showCloseButton = false;
		_sidePanelId = null;
		_title = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:side-panel-content:";

	private static final String _END_PAGE = "/side_panel_content/end.jsp";

	private static final String _START_PAGE = "/side_panel_content/start.jsp";

	private String _screenNavigatorKey;
	private Object _screenNavigatorModelBean;
	private PortletURL _screenNavigatorPortletURL;
	private boolean _showCloseButton;
	private String _sidePanelId;
	private String _title;

}