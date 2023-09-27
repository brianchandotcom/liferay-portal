/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import com.liferay.frontend.js.loader.modules.extender.internal.resolution.adapter.JSBrowserModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class BrowserModulesMap {

	public BrowserModulesMap(
		BrowserModulesResolution browserModulesResolution,
		NPMRegistry npmRegistry) {

		_browserModulesResolution = browserModulesResolution;
		_npmRegistry = npmRegistry;
	}

	public JSBrowserModule get(String resolvedModuleId) {
		JSBrowserModule jsBrowserModule = _map.get(resolvedModuleId);

		if (jsBrowserModule == null) {
			JSModule jsModule = _npmRegistry.getResolvedJSModule(
				resolvedModuleId);

			if (jsModule != null) {
				jsBrowserModule = new JSBrowserModule(
					_browserModulesResolution, jsModule, _npmRegistry);

				_map.put(jsBrowserModule.getName(), jsBrowserModule);
			}
		}

		return jsBrowserModule;
	}

	private final BrowserModulesResolution _browserModulesResolution;
	private final Map<String, JSBrowserModule> _map = new HashMap<>();
	private final NPMRegistry _npmRegistry;

}