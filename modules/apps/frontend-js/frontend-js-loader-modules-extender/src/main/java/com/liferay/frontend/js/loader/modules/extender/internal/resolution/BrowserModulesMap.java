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