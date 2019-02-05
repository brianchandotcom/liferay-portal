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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor;

import com.liferay.frontend.js.loader.modules.extender.internal.resolution.ModuleDescriptor;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringBundler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSModuleDescriptor implements ModuleDescriptor {

	public JSModuleDescriptor(JSModule jsModule, NPMRegistry npmRegistry) {
		_jsModule = jsModule;

		_setMappings(npmRegistry);
	}

	@Override
	public Collection<String> getDependencies() {
		return _jsModule.getDependencies();
	}

	@Override
	public Map<String, String> getMappings() {
		return _mappings;
	}

	@Override
	public String getName() {
		return _jsModule.getResolvedId();
	}

	@Override
	public String getPath() {
		return _jsModule.getResolvedURL();
	}

	private void _setMappings(NPMRegistry npmRegistry) {
		JSPackage jsPackage = _jsModule.getJSPackage();

		for (String dependencyPackageName :
				_jsModule.getDependencyPackageNames()) {

			if (dependencyPackageName == null) {
				continue;
			}

			if (dependencyPackageName.equals(jsPackage.getName())) {
				_mappings.put(dependencyPackageName, jsPackage.getResolvedId());
			}
			else {
				JSPackageDependency jsPackageDependency =
					jsPackage.getJSPackageDependency(dependencyPackageName);

				if (jsPackageDependency == null) {
					String errorMessage = StringBundler.concat(
						":ERROR:Missing version constraints for ",
						dependencyPackageName, " in package.json of ",
						jsPackage.getResolvedId());

					_mappings.put(dependencyPackageName, errorMessage);
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

						_mappings.put(dependencyPackageName, errorMessage);
					}
					else {
						_mappings.put(
							dependencyJSPackage.getName(),
							dependencyJSPackage.getResolvedId());
					}
				}
			}
		}
	}

	private final JSModule _jsModule;
	private final Map<String, String> _mappings = new HashMap<>();

}