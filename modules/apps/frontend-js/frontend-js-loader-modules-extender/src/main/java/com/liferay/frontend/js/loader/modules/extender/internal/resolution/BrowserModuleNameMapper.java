/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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