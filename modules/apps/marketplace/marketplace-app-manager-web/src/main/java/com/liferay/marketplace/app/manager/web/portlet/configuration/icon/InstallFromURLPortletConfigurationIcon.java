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

package com.liferay.marketplace.app.manager.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Douglas Wong
 */
public class InstallFromURLPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public InstallFromURLPortletConfigurationIcon(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getMessage() {
		return "install-from-url";
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}