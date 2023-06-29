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

package com.liferay.portal.vulcan.extension;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier de Arcos
 */
public class EntityExtensionThreadLocal {

	public static Map<String, Serializable> getExtendedProperties() {
		return _extendedPropertiesThreadLocal.get();
	}

	public static Map<String, Serializable> getExtendedProperties(
		Object object) {

		Map<Object, Map<String, Serializable>> objectExtendedProperties =
			_objectExtendedPropertiesThreadLocal.get();

		if (objectExtendedProperties.containsKey(object)) {
			return objectExtendedProperties.get(object);
		}

		return null;
	}

	public static void setExtendedProperties(
		Map<String, Serializable> extendedProperties) {

		_extendedPropertiesThreadLocal.set(extendedProperties);
	}

	public static void setExtendedProperties(
		Map<String, Serializable> extendedProperties, Object object) {

		Map<Object, Map<String, Serializable>> objectExtendedProperties =
			_objectExtendedPropertiesThreadLocal.get();

		if (objectExtendedProperties == null) {
			objectExtendedProperties = new HashMap<>();
		}

		objectExtendedProperties.put(object, extendedProperties);

		_objectExtendedPropertiesThreadLocal.set(objectExtendedProperties);
	}

	private static final ThreadLocal<Map<String, Serializable>>
		_extendedPropertiesThreadLocal = new CentralizedThreadLocal<>(
			EntityExtensionThreadLocal.class +
				"._extendedPropertiesThreadLocal");
	private static final ThreadLocal<Map<Object, Map<String, Serializable>>>
		_objectExtendedPropertiesThreadLocal = new CentralizedThreadLocal<>(
			EntityExtensionThreadLocal.class.getName() +
				"._objectExtendedPropertiesThreadLocal",
			HashMap::new);

}