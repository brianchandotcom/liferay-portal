/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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