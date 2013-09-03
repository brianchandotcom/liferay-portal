/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.webdav.methods;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Map;

/**
 * @author Fabio Pezzutto
 */
public class MethodFactoryUtil {

	public static MethodFactory getMethodFactory(String type) {
		return _methodFactories.get(type);
	}

	public static void removeMethodFactory(String type) {
		_methodFactories.remove(type);
	}

	public static void setMethodFactory(
			String type, MethodFactory davMethodFactory) {

		_methodFactories.put(type, davMethodFactory);
	}

	public void setMethodFactories(Map<String, MethodFactory> methodFactories) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_methodFactories = methodFactories;
	}

	private static Map<String, MethodFactory> _methodFactories;

}