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

package com.liferay.portlet.blogs.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseResourcePermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.blogs.model.BlogsEntry;

/**
 * @author Jorge Ferrer
 */
@OSGiBeanProperties(property = {"resource.name=com.liferay.portlet.blogs"})
public class BlogsPermission extends BaseResourcePermissionChecker {

	public static final String RESOURCE_NAME = "com.liferay.portlet.blogs";

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), RESOURCE_NAME, groupId,
				actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.EDIT);

		return contains(
			permissionChecker, RESOURCE_NAME, portletId, classPK, actionId);
	}

	@Override
	public Boolean checkResource(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		return contains(permissionChecker, classPK, actionId);
	}

}