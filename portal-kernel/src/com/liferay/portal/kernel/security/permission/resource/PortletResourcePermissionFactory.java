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

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.portal.kernel.internal.security.permission.resource.DefaultPortletResourcePermission;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Preston Crary
 */
public class PortletResourcePermissionFactory {

	public static PortletResourcePermission create(
		String resourceName,
		PortletResourcePermissionLogic... portletResourcePermissionLogics) {

		return new DefaultPortletResourcePermission(
			resourceName, portletResourcePermissionLogics);
	}

	public static PortletResourcePermission getInstance(
		Class<? extends BaseService> declaringServiceClass, String fieldName,
		String resourceName) {

		return ServiceProxyFactory.newServiceTrackedInstance(
			PortletResourcePermission.class, declaringServiceClass, fieldName,
			"(resource.name=" + resourceName + ")", true);
	}

}