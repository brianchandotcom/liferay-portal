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

package com.liferay.portal.kernel.webdav.methods;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MethodFactoryRegistryUtil {

	public static MethodFactory getDefaultMethodFactory() {
		return getMethodFactoryRegistry().getDefaultMethodFactory();
	}

	public static MethodFactory getMethodFactory(String className) {
		return getMethodFactoryRegistry().getMethodFactory(className);
	}

	public static List<MethodFactory> getMethodFactoryFactories() {
		return getMethodFactoryRegistry().getMethodFactories();
	}

	public static MethodFactoryRegistry getMethodFactoryRegistry() {
		return _methodFactoryRegistry;
	}

	public static void registerMethodFactory(MethodFactory methodFactory) {
		getMethodFactoryRegistry().registerMethodFactory(methodFactory);
	}

	public static void unregisterMethodFactory(MethodFactory methodFactory) {
		getMethodFactoryRegistry().unregisterMethodFactory(methodFactory);
	}

	public void setMethodFactoryRegistry(
		MethodFactoryRegistry methodFactoryRegistry) {

		_methodFactoryRegistry = methodFactoryRegistry;
	}

	private static MethodFactoryRegistry _methodFactoryRegistry;

}