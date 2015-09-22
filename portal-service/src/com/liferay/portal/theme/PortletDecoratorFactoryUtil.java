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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.PortletDecorator;

/**
 * @author Eduardo Garcia
 */
public class PortletDecoratorFactoryUtil {

	public static PortletDecorator getDefaultPortletDecorator() {
		return getPortletDecoratorFactory().getDefaultPortletDecorator();
	}

	public static String getDefaultPortletDecoratorCssClass() {
		return getPortletDecoratorFactory().
			getDefaultPortletDecoratorCssClass();
	}

	public static String getDefaultPortletDecoratorId() {
		return getPortletDecoratorFactory().getDefaultPortletDecoratorId();
	}

	public static PortletDecorator getPortletDecorator() {
		return getPortletDecoratorFactory().getPortletDecorator();
	}

	public static PortletDecorator getPortletDecorator(
		String portletDecoratorId) {

		return getPortletDecoratorFactory().getPortletDecorator(
			portletDecoratorId);
	}

	public static PortletDecorator getPortletDecorator(
		String portletDecoratorId, String name, String cssClass) {

		return getPortletDecoratorFactory().getPortletDecorator(
			portletDecoratorId, name, cssClass);
	}

	public static PortletDecoratorFactory getPortletDecoratorFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletDecoratorFactoryUtil.class);

		return _portletDecoratorFactory;
	}

	public void setPortletDecoratorFactory(
		PortletDecoratorFactory portletDecoratorFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletDecoratorFactory = portletDecoratorFactory;
	}

	private static PortletDecoratorFactory _portletDecoratorFactory;

}