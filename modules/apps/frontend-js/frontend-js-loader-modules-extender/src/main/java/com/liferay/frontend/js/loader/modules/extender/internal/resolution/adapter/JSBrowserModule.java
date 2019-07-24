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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution.adapter;

import com.liferay.frontend.js.loader.modules.extender.internal.resolution.BrowserModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the browser's domain equivalent of {@link JSModule} in server's
 * domain.
 *
 * @author Rodolfo Roza Miranda
 * @review
 */
public class JSBrowserModule implements BrowserModule {

	public JSBrowserModule(JSModule jsModule, NPMRegistry npmRegistry) {
		_jsModule = jsModule;

		_populateDependenciesMap(npmRegistry);
	}

	@Override
	public Collection<String> getDependencies() {
		return _jsModule.getDependencies();
	}

	@Override
	public Map<String, String> getDependenciesMap() {
		return _dependenciesMap;
	}

	@Override
	public JSONObject getFlagsJSONObject() {
		return _jsModule.getFlagsJSONObject();
	}

	@Override
	public String getName() {
		return _jsModule.getResolvedId();
	}

	@Override
	public String getPath() {
		return _jsModule.getResolvedURL();
	}

	private void _populateDependenciesMap(NPMRegistry npmRegistry) {
		JSPackage jsPackage = _jsModule.getJSPackage();

		for (String dependencyPackageName :
				_jsModule.getDependencyPackageNames()) {

			if (dependencyPackageName == null) {
				continue;
			}

			if (dependencyPackageName.equals(jsPackage.getName())) {
				_dependenciesMap.put(
					dependencyPackageName, jsPackage.getResolvedId());
			}
			else {
				JSPackageDependency jsPackageDependency =
					jsPackage.getJSPackageDependency(dependencyPackageName);

				if (jsPackageDependency == null) {
					String errorMessage = StringBundler.concat(
						":ERROR:Missing version constraints for ",
						dependencyPackageName, " in package.json of ",
						jsPackage.getResolvedId());

					_dependenciesMap.put(dependencyPackageName, errorMessage);
				}
				else {
					JSPackage dependencyJSPackage =
						npmRegistry.resolveJSPackageDependency(
							jsPackageDependency);

					if (dependencyJSPackage == null) {
						String errorMessage = StringBundler.concat(
							":ERROR:Package ", dependencyPackageName,
							" which is a dependency of ",
							jsPackage.getResolvedId(),
							" is not deployed in the server");

						_dependenciesMap.put(
							dependencyPackageName, errorMessage);
					}
					else {
						_dependenciesMap.put(
							dependencyJSPackage.getName(),
							dependencyJSPackage.getResolvedId());
					}
				}
			}
		}
	}

	private final Map<String, String> _dependenciesMap = new HashMap<>();
	private final JSModule _jsModule;

}