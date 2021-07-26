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

package com.liferay.data.cleanup.internal.upgrade.util;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.io.IOException;

import java.lang.reflect.Method;

import java.util.Dictionary;
import java.util.Objects;

import org.apache.felix.cm.PersistenceManager;

/**
 * @author Kevin Lee
 */
public class ConfigurationUtil {

	public static void resetConfiguration(
			PersistenceManager persistenceManager, Class<?> clazz)
		throws IOException {

		Dictionary<String, Object> properties = persistenceManager.load(
			clazz.getName());

		if (properties == null) {
			return;
		}

		Dictionary<String, Object> newProperties =
			HashMapDictionaryBuilder.<String, Object>putAll(
				properties
			).build();

		for (Method method : clazz.getMethods()) {
			if (!method.isAnnotationPresent(Meta.AD.class) ||
				!Objects.equals(method.getReturnType(), Boolean.TYPE)) {

				continue;
			}

			if (properties.get(method.getName()) != null) {
				newProperties.put(method.getName(), false);
			}
		}

		persistenceManager.store(clazz.getName(), newProperties);
	}

}