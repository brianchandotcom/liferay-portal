/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.resolution.adapter;

import com.liferay.frontend.js.loader.modules.extender.internal.resolution.BrowserModule;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.BrowserModulesResolution;
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

	public JSBrowserModule(
		BrowserModulesResolution browserModulesResolution, JSModule jsModule,
		NPMRegistry npmRegistry) {

		_browserModulesResolution = browserModulesResolution;
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
					_browserModulesResolution.addError(
						StringBundler.concat(
							"Missing version constraints for '",
							dependencyPackageName, "' in package.json of '",
							jsPackage.getResolvedId(), "' (required from its '",
							_jsModule.getName(), "' module)"));
				}
				else {
					JSPackage dependencyJSPackage =
						npmRegistry.resolveJSPackageDependency(
							jsPackageDependency);

					if (dependencyJSPackage == null) {
						_browserModulesResolution.addError(
							StringBundler.concat(
								"Package '", dependencyPackageName,
								"' which is a dependency of '",
								jsPackage.getResolvedId(),
								"' is not deployed in the server (required ",
								"from its '", _jsModule.getResolvedId(),
								"' module)"));
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

	private final BrowserModulesResolution _browserModulesResolution;
	private final Map<String, String> _dependenciesMap = new HashMap<>();
	private final JSModule _jsModule;

}