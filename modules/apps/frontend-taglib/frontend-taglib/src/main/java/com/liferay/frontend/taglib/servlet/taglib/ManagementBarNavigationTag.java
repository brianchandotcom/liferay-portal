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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Sergio González
 */
public class ManagementBarNavigationTag extends IncludeTag implements BodyTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public String getLabel() {
		return _label;
	}

	public List<ManagementBarFilterItem> getManagementBarFilterItems() {
		return _managementBarFilterItems;
	}

	public Map<String, String> getNavigationKeys() {
		return _navigationKeys;
	}

	public String getNavigationParam() {
		return _navigationParam;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setNavigationKeys(Map<String, String> navigationKeys) {
		_navigationKeys = navigationKeys;
	}

	public void setNavigationKeys(String[] navigationKeys) {
		for (String navigationKey : navigationKeys) {
			_navigationKeys.put(navigationKey, navigationKey);
		}
	}

	public void setNavigationParam(String navigationParam) {
		_navigationParam = navigationParam;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_disabled = null;
		_label = null;
		_managementBarFilterItems = new ArrayList<>();
		_navigationKeys = new LinkedHashMap<>();
		_navigationParam = "navigation";
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
	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-frontend:management-bar-navigation:disabled",
			isDisabled());

		if (_managementBarFilterItems == null) {
			_managementBarFilterItems = new ArrayList<>();
		}

		String navigationKey = ParamUtil.getString(
			httpServletRequest, _navigationParam);

		for (Map.Entry<String, String> entry : _navigationKeys.entrySet()) {
			String label = entry.getKey();

			String value = entry.getValue();

			_portletURL.setParameter(_navigationParam, value);

			boolean active = value.equals(navigationKey);

			if (active && Validator.isNull(_label)) {
				_label = label;
			}

			ManagementBarFilterItem managementBarFilterItem =
				new ManagementBarFilterItem(
					active, label, _portletURL.toString());

			_managementBarFilterItems.add(managementBarFilterItem);
		}

		httpServletRequest.setAttribute(
			"liferay-frontend:management-bar-navigation:" +
				"managementBarFilterItems",
			_managementBarFilterItems);

		if (Validator.isNull(_label)) {
			ManagementBarFilterItem managementBarFilterItem =
				_managementBarFilterItems.get(0);

			_label = managementBarFilterItem.getLabel();

			for (ManagementBarFilterItem curManagementBarFilterItem :
					_managementBarFilterItems) {

				if (curManagementBarFilterItem.isActive()) {
					_label = curManagementBarFilterItem.getLabel();

					break;
				}
			}
		}

		httpServletRequest.setAttribute(
			"liferay-frontend:management-bar-navigation:label", _label);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/management_bar_navigation/page.jsp";

	private Boolean _disabled;
	private String _label;
	private List<ManagementBarFilterItem> _managementBarFilterItems =
		new ArrayList<>();
	private Map<String, String> _navigationKeys = new LinkedHashMap<>();
	private String _navigationParam = "navigation";
	private PortletURL _portletURL;

}