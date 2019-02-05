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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rodolfo Roza Miranda
 */
public class JSModuleDescriptor implements ModuleDescriptor {

	public JSModuleDescriptor(JSModule jsModule, NPMRegistry npmRegistry) {
		_jsModule = jsModule;
		_npmRegistry = npmRegistry;
	}

	@Override
	public Collection<String> getDependencies() {
		return _jsModule.getDependencies();
	}

	@Override
	public Map<String, String> getMappings() {
		JSPackage jsPackage = _jsModule.getJSPackage();

		Map<String, String> contextMap = new ConcurrentHashMap<>();

		for (String dependencyPackageName :
				_jsModule.getDependencyPackageNames()) {

			if (dependencyPackageName == null) {
				continue;
			}

			if (dependencyPackageName.equals(jsPackage.getName())) {
				contextMap.put(
					dependencyPackageName, jsPackage.getResolvedId());
			}
			else {
				JSPackageDependency dependency =
					jsPackage.getJSPackageDependency(dependencyPackageName);

				if (dependency == null) {
					String errorMessage = StringBundler.concat(
						":ERROR:Missing version constraints for ",
						dependencyPackageName, " in package.json of ",
						jsPackage.getResolvedId());

					contextMap.put(dependencyPackageName, errorMessage);
				}
				else {
					JSPackage packageDependency =
						_npmRegistry.resolveJSPackageDependency(dependency);

					if (packageDependency == null) {
						String errorMessage = StringBundler.concat(
							":ERROR:Package ", dependencyPackageName,
							" which is a dependency of ",
							jsPackage.getResolvedId(),
							" is not deployed in the server");

						contextMap.put(dependencyPackageName, errorMessage);
					}
					else {
						contextMap.put(
							packageDependency.getName(),
							packageDependency.getResolvedId());
					}
				}
			}
		}

		return contextMap;
	}

	@Override
	public String getName() {
		return _jsModule.getResolvedId();
	}

	@Override
	public String getPath() {
		return _jsModule.getResolvedURL();
	}

	private final JSModule _jsModule;
	private final NPMRegistry _npmRegistry;

}