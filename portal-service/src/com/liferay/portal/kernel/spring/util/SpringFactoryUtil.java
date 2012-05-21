/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.spring.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class SpringFactoryUtil {

	public static SpringFactory getSpringFactory() {
		PortalRuntimePermission.checkGetBeanProperty(SpringFactoryUtil.class);

		return _springFactory;
	}

	public static Object newBean(String className)
		throws SpringFactoryException {

		return getSpringFactory().newBean(className);
	}

	public static Object newBean(
			String className, Map<String, Object> properties)
		throws SpringFactoryException {

		return getSpringFactory().newBean(className, properties);
	}

	public void setSpringFactory(SpringFactory springFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_springFactory = springFactory;
	}

	private static SpringFactory _springFactory;

}