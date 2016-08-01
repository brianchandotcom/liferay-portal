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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

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

	public List<ManagementBarFilterItem> getManagementBarFilterItems() {
		return _managementBarFilterItems;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setNavigationKeys(String[] navigationKeys) {
		_navigationKeys = navigationKeys;
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
		_disabled = null;
		_managementBarFilterItems = new ArrayList<>();
		_label = null;
		_navigationKeys = null;
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
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-frontend:management-bar-navigation:disabled",
			isDisabled());

		if (_managementBarFilterItems == null) {
			_managementBarFilterItems = new ArrayList<>();
		}

		String navigationKey = ParamUtil.getString(request, _navigationParam);

		if (ArrayUtil.isNotEmpty(_navigationKeys)) {
			for (String curNavigationKey : _navigationKeys) {
				_portletURL.setParameter(_navigationParam, curNavigationKey);

				ManagementBarFilterItem managementBarFilterItem =
					new ManagementBarFilterItem(
						curNavigationKey.equals(navigationKey),
						curNavigationKey, _portletURL.toString());

				_managementBarFilterItems.add(managementBarFilterItem);
			}
		}

		request.setAttribute(
			"liferay-frontend:management-bar-navigation:" +
				"managementBarFilterItems",
			_managementBarFilterItems);

		if (Validator.isNull(_label)) {
			ManagementBarFilterItem managementBarFilterItem =
				_managementBarFilterItems.get(0);

			_label = ParamUtil.getString(
				request, _navigationParam, managementBarFilterItem.getLabel());
		}

		request.setAttribute(
			"liferay-frontend:management-bar-navigation:label", _label);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/management_bar_navigation/page.jsp";

	private Boolean _disabled;
	private String _label;
	private List<ManagementBarFilterItem> _managementBarFilterItems =
		new ArrayList<>();
	private String[] _navigationKeys;
	private String _navigationParam = "navigation";
	private PortletURL _portletURL;

}