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

package com.liferay.wiki.web.portlet.configuration.icon;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.taglib.security.PermissionsURLTag;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;

import javax.portlet.PortletRequest;

/**
 * @author Roberto Díaz
 */
public class PagePermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public PagePermissionsPortletConfigurationIcon(
		PortletRequest portletRequest, WikiPage page) {

		super(portletRequest);

		_page = page;
	}

	@Override
	public String getMessage() {
		return "permissions";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			url = PermissionsURLTag.doTag(
				null, WikiPage.class.getName(), _page.getTitle(), null,
				String.valueOf(_page.getResourcePrimKey()),
				LiferayWindowState.POP_UP.toString(), null,
				themeDisplay.getRequest());
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public boolean isShow() {
		try {
			return WikiNodePermissionChecker.contains(
				themeDisplay.getPermissionChecker(), _page.getNodeId(),
				ActionKeys.PERMISSIONS);
		}
		catch (PortalException pe) {
		}

		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	private final WikiPage _page;

}