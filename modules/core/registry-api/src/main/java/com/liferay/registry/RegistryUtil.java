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

package com.liferay.registry;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Raymond Augé
 */
public class RegistryUtil {

	public static Registry getRegistry() {
		if (_registry == null) {
			throw new NullPointerException("A registry instance was never set");
		}

		return _registry;
	}

	public static void setRegistry(Registry registry) {
		_registry = registry;
	}

	private static Registry _registry;

	static {
		ServiceLoader<Registry> serviceLoader = ServiceLoader.load(
			Registry.class, RegistryUtil.class.getClassLoader());

		Iterator<Registry> iterator = serviceLoader.iterator();

		if (iterator.hasNext()) {
			_registry = iterator.next();
		}
	}

}