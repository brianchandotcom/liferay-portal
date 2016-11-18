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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class ManagementBarSidenavTogglerButtonTag
	extends ManagementBarButtonTag {

	@Override
	public void setHref(String href) {
		_href = href;
	}

	public void setPosition(String position) {
		_position = position;
	}

	/**
	 * @deprecated As of 2.0.0, with no direct replacement
	 */
	@Deprecated
	public void setSidenavId(String sidenavId) {
	}

	public void setType(String type) {
		_type = type;
	}

	public void setTypeMobile(String typeMobile) {
		_typeMobile = typeMobile;
	}

	public void setWidth(String width) {
		_width = width;
	}

	@Override
	protected void cleanUp() {
		_href = null;
		_position = null;
		_type = null;
		_typeMobile = null;
		_width = null;

		super.cleanUp();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		if (Validator.isNull(getId())) {
			setId(StringUtil.randomId());
		}

		setNamespacedAttribute(request, "href", _href);
		setNamespacedAttribute(request, "position", _position);
		setNamespacedAttribute(request, "type", _type);
		setNamespacedAttribute(request, "typeMobile", _typeMobile);
		setNamespacedAttribute(request, "width", _width);

		super.setAttributes(request);
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:management-bar-sidenav-toggler-button:";

	private static final String _PAGE =
		"/management_bar_sidenav_toggler_button/page.jsp";

	private String _href;
	private String _position;
	private String _type;
	private String _typeMobile;
	private String _width;

}