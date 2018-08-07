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

package com.liferay.portal.kernel.model.portlet;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.portlet.PortletRequest;

/**
 * @author Neil Griffin
 */
public class PortletDependencyFactoryUtil {

	public static PortletDependency createPortletDependency(
		String name, String scope, String version) {

		return _portletDependencyFactory.createPortletDependency(
			name, scope, version);
	}

	public static PortletDependency createPortletDependency(
		String name, String scope, String version, String markup,
		PortletRequest portletRequest) {

		return _portletDependencyFactory.createPortletDependency(
			name, scope, version, markup, portletRequest);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static PortletDependencyFactory getPortletDependencyFactory() {
		return _portletDependencyFactory;
	}

	private static volatile PortletDependencyFactory _portletDependencyFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortletDependencyFactory.class, PortletDependencyFactoryUtil.class,
			"_portletDependencyFactory", false);

}