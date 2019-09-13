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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 * @author Marco Leo
 */
public class ScreenNavigationTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		if (ListUtil.isEmpty(_screenNavigationCategories)) {
			return SKIP_PAGE;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ScreenNavigationRegistry screenNavigationRegistry =
			ServletContextUtil.getScreenNavigationRegistry();

		_screenNavigationCategories =
			screenNavigationRegistry.getScreenNavigationCategories(
				_key, themeDisplay.getUser(), getModelContext());

		return super.doStartTag();
	}

	public String getContainerCssClass() {
		return _containerCssClass;
	}

	public String getContainerWrapperCssClass() {
		return _containerWrapperCssClass;
	}

	public Object getContext() {
		return _context;
	}

	public String getFullContainerCssClass() {
		return _fullContainerCssClass;
	}

	public String getHeaderContainerCssClass() {
		return _headerContainerCssClass;
	}

	public String getId() {
		return _id;
	}

	public String getKey() {
		return _key;
	}

	public String getMenubarCssClass() {
		return _menubarCssClass;
	}

	public Object getModelBean() {
		return _modelBean;
	}

	public Object getModelContext() {
		if (Validator.isNotNull(_modelBean)) {
			return _modelBean;
		}

		return _context;
	}

	public String getNavCssClass() {
		return _navCssClass;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public boolean isInverted() {
		return _inverted;
	}

	public void setContainerCssClass(String containerCssClass) {
		_containerCssClass = containerCssClass;
	}

	public void setContainerWrapperCssClass(String containerWrapperCssClass) {
		_containerWrapperCssClass = containerWrapperCssClass;
	}

	public void setContext(Object context) {
		_context = context;
	}

	public void setFullContainerCssClass(String fullContainerCssClass) {
		_fullContainerCssClass = fullContainerCssClass;
	}

	public void setHeaderContainerCssClass(String headerContainerCssClass) {
		_headerContainerCssClass = headerContainerCssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setInverted(boolean inverted) {
		_inverted = inverted;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setMenubarCssClass(String menubarCssClass) {
		_menubarCssClass = menubarCssClass;
	}

	public void setModelBean(Object modelBean) {
		_modelBean = modelBean;
	}

	public void setNavCssClass(String navCssClass) {
		_navCssClass = navCssClass;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_containerCssClass = "col-md-9";
		_containerWrapperCssClass = "container";
		_context = null;
		_fullContainerCssClass = "col-md-12";
		_headerContainerCssClass = "container";
		_id = null;
		_inverted = false;
		_key = null;
		_menubarCssClass =
			"menubar menubar-transparent menubar-vertical-expand-md";
		_modelBean = null;
		_navCssClass = "col-md-3";
		_portletURL = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:containerCssClass",
			_containerCssClass);
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:fullContainerCssClass",
			_fullContainerCssClass);
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:headerContainerCssClass",
			_headerContainerCssClass);

		String id = _id;

		if (Validator.isNotNull(id)) {
			PortletResponse portletResponse =
				(PortletResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			String namespace = StringPool.BLANK;

			if (portletResponse != null) {
				namespace = portletResponse.getNamespace();
			}

			id = PortalUtil.getUniqueElementId(
				getOriginalServletRequest(), namespace, id);
		}
		else {
			id = PortalUtil.generateRandomKey(
				httpServletRequest, ScreenNavigationTag.class.getName());
		}

		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:id", id);

		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:containerWrapperCssClass",
			_containerWrapperCssClass);
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:inverted", _inverted);
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:menubarCssClass",
			_menubarCssClass);
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:navCssClass", _navCssClass);
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:portletURL", _portletURL);
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:screenNavigationCategories",
			_screenNavigationCategories);
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:screenNavigationEntries",
			_getScreenNavigationEntries());
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:" +
				"selectedScreenNavigationCategory",
			_getSelectedScreenNavigationCategory());
		httpServletRequest.setAttribute(
			"liferay-frontend:screen-navigation:selectedScreenNavigationEntry",
			_getSelectedScreenNavigationEntry());
	}

	private String _getDefaultScreenNavigationCategoryKey() {
		if (ListUtil.isEmpty(_screenNavigationCategories)) {
			return null;
		}

		ScreenNavigationCategory screenNavigationCategory =
			_screenNavigationCategories.get(0);

		return screenNavigationCategory.getCategoryKey();
	}

	private String _getDefaultScreenNavigationEntryKey() {
		List<ScreenNavigationEntry> screenNavigationEntries =
			_getScreenNavigationEntries();

		if (ListUtil.isEmpty(screenNavigationEntries)) {
			return null;
		}

		ScreenNavigationEntry screenNavigationEntry =
			screenNavigationEntries.get(0);

		return screenNavigationEntry.getEntryKey();
	}

	private List<ScreenNavigationEntry> _getScreenNavigationEntries() {
		ScreenNavigationCategory selectedScreenNavigationCategory =
			_getSelectedScreenNavigationCategory();

		if (selectedScreenNavigationCategory == null) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ScreenNavigationRegistry screenNavigationRegistry =
			ServletContextUtil.getScreenNavigationRegistry();

		return screenNavigationRegistry.getScreenNavigationEntries(
			selectedScreenNavigationCategory, themeDisplay.getUser(),
			getModelContext());
	}

	private ScreenNavigationCategory _getSelectedScreenNavigationCategory() {
		String screenNavigationCategoryKey = ParamUtil.getString(
			request, "screenNavigationCategoryKey",
			_getDefaultScreenNavigationCategoryKey());

		for (ScreenNavigationCategory screenNavigationCategory :
				_screenNavigationCategories) {

			if (Objects.equals(
					screenNavigationCategory.getCategoryKey(),
					screenNavigationCategoryKey)) {

				return screenNavigationCategory;
			}
		}

		return null;
	}

	private ScreenNavigationEntry _getSelectedScreenNavigationEntry() {
		String screenNavigationEntryKey = ParamUtil.getString(
			request, "screenNavigationEntryKey");

		if (Validator.isNull(screenNavigationEntryKey)) {
			screenNavigationEntryKey = _getDefaultScreenNavigationEntryKey();
		}

		List<ScreenNavigationEntry> screenNavigationEntries =
			_getScreenNavigationEntries();

		if (ListUtil.isEmpty(screenNavigationEntries)) {
			return null;
		}

		for (ScreenNavigationEntry screenNavigationEntry :
				screenNavigationEntries) {

			if (Objects.equals(
					screenNavigationEntry.getEntryKey(),
					screenNavigationEntryKey)) {

				return screenNavigationEntry;
			}
		}

		return null;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/screen_navigation/page.jsp";

	private String _containerCssClass = "col-md-9";
	private String _containerWrapperCssClass = "container";
	private Object _context;
	private String _fullContainerCssClass = "col-md-12";
	private String _headerContainerCssClass = "container";
	private String _id;
	private boolean _inverted;
	private String _key;
	private String _menubarCssClass =
		"menubar menubar-transparent menubar-vertical-expand-md";
	private Object _modelBean;
	private String _navCssClass = "col-md-3";
	private PortletURL _portletURL;
	private List<ScreenNavigationCategory> _screenNavigationCategories;

}