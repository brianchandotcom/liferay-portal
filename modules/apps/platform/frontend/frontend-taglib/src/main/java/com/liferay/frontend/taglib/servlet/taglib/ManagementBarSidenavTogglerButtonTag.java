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

	public void setSidenavId(String sidenavId) {
		_sidenavId = sidenavId;
	}

	@Override
	protected void cleanUp() {
		_sidenavId = null;

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

		setNamespacedAttribute(request, "sidenavId", _sidenavId);

		super.setAttributes(request);
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:management-bar-sidenav-toggler-button:";

	private static final String _PAGE =
		"/management_bar_sidenav_toggler_button/page.jsp";

	private String _sidenavId;

}