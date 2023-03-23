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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Rodolfo Roza Miranda
 */
public class BrowserModuleNameMapper {

	public static String mapModuleName(
		NPMRegistry npmRegistry, String moduleName) {

		return mapModuleName(npmRegistry, moduleName, null);
	}

	public static String mapModuleName(
		NPMRegistry npmRegistry, String moduleName,
		Map<String, String> dependenciesMap) {

		String mappedModuleName = moduleName;

		if (dependenciesMap != null) {
			mappedModuleName = _map(
				moduleName, dependenciesMap, dependenciesMap);
		}

		return npmRegistry.mapModuleName(mappedModuleName);
	}

	private static String _map(
		String moduleName, Map<String, String> exactMatchMap,
		Map<String, String> partialMatchMap) {

		String mappedModuleName = exactMatchMap.get(moduleName);

		if (Validator.isNotNull(mappedModuleName)) {
			return mappedModuleName;
		}

		for (Map.Entry<String, String> entry : partialMatchMap.entrySet()) {
			String resolvedId = entry.getKey();

			if (resolvedId.equals(moduleName) ||
				moduleName.startsWith(resolvedId + StringPool.SLASH)) {

				return entry.getValue() +
					moduleName.substring(resolvedId.length());
			}
		}

		return moduleName;
	}

}