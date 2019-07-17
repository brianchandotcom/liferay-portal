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

package com.liferay.user.groups.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ActionUtil {

	public static UserGroup getUserGroup(HttpServletRequest httpServletRequest)
		throws Exception {

		long userGroupId = ParamUtil.getLong(httpServletRequest, "userGroupId");

		UserGroup userGroup = null;

		if (userGroupId > 0) {
			userGroup = UserGroupServiceUtil.fetchUserGroup(userGroupId);
		}

		return userGroup;
	}

	public static UserGroup getUserGroup(PortletRequest portletRequest)
		throws Exception {

		return getUserGroup(PortalUtil.getHttpServletRequest(portletRequest));
	}

}