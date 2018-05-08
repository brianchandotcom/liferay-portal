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

package com.liferay.portal.remote.cxf.common.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Sierra Andrés
 */
public class ExtensionManager {

	public Map<Class<?>, Object> getExtensions() {
		return _extensions;
	}

	protected void addExtension(
		Map<String, Object> properties, Object extension) {

		Class<?> extensionClass = (Class<?>)properties.get(
			"soap.extension.class");

		_extensions.put(extensionClass, extension);
	}

	protected void removeExtension(Object extension) {
	}

	private final Map<Class<?>, Object> _extensions = new HashMap<>();

}