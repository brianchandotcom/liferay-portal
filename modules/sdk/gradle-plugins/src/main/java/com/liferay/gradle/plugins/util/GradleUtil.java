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

package com.liferay.gradle.plugins.util;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.artifacts.ModuleVersionSelector;

/**
 * @author Andrea Di Giorgi
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static boolean isPortal(
		ModuleVersionSelector moduleVersionSelector) {

		String group = moduleVersionSelector.getGroup();

		if (!group.equals("com.liferay")) {
			return false;
		}

		String name = moduleVersionSelector.getName();

		if (name.equals("com.liferay.portal-impl") ||
			name.equals("com.liferay.portal-kernel") ||
			name.equals("com.liferay.portal-test") ||
			name.equals("com.liferay.portal-test-internal") ||
			name.equals("com.liferay.portal-web") ||
			name.equals("com.liferay.util-bridges") ||
			name.equals("com.liferay.util-java") ||
			name.equals("com.liferay.util-slf4j") ||
			name.equals("com.liferay.util-taglib")) {

			return true;
		}

		return false;
	}

	public static Map<String, String> toStringMap(Map<String, ?> map) {
		Map<String, String> stringMap = new HashMap<>();

		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = toString(entry.getValue());

			stringMap.put(key, value);
		}

		return stringMap;
	}

}