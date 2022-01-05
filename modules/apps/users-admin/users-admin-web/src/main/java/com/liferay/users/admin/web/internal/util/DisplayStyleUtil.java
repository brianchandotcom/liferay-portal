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

package com.liferay.users.admin.web.internal.util;

import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.PortletRequest;

/**
 * @author Drew Brokke
 */
public class DisplayStyleUtil {

	public static String getDisplayStyle(
		PortletRequest portletRequest, String defaultDisplayStyle) {

		return SearchDisplayStyleUtil.getDisplayStyle(
			PortalUtil.getHttpServletRequest(portletRequest),
			UsersAdminPortletKeys.USERS_ADMIN, defaultDisplayStyle, true);
	}

}