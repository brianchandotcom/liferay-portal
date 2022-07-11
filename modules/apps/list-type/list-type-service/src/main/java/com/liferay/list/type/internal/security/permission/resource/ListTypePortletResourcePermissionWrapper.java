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

package com.liferay.list.type.internal.security.permission.resource;

import com.liferay.list.type.constants.ListTypeConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "resource.name=" + ListTypeConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class ListTypePortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			ListTypeConstants.RESOURCE_NAME,
			new ListTypePortletResourcePermissionLogic());
	}

	private static class ListTypePortletResourcePermissionLogic
		implements PortletResourcePermissionLogic {

		@Override
		public Boolean contains(
			PermissionChecker permissionChecker, String name, Group group,
			String actionId) {

			if (permissionChecker.hasPermission(group, name, 0, actionId)) {
				return true;
			}

			return false;
		}

	}

}