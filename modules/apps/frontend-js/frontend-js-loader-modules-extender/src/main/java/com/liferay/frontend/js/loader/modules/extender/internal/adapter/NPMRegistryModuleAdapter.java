package com.liferay.frontend.js.loader.modules.extender.internal.adapter;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Rodolfo Roza Miranda
 */
public class NPMRegistryModuleAdapter implements JSModuleAdapter {

	public NPMRegistryModuleAdapter(JSModule module, NPMRegistry npmRegistry) {
		_module = module;
		_npmRegistry = npmRegistry;
	}

	@Override
	public String getAlias() {
		return _module.getResolvedId();
	}

	@Override
	public Set<String> getDependencies() {
		return new HashSet<>(_module.getDependencies());
	}

	@Override
	public Map<String, String> getMap() {
		JSPackage jsPackage = _module.getJSPackage();

		HashMap<String, String> contextMap = new HashMap<>();

		for (String dependencyPackageName : _module.getDependencyPackageNames()) {

			if (dependencyPackageName == null) {
				continue;
			}

			if (dependencyPackageName.equals(jsPackage.getName())) {
				contextMap.put(dependencyPackageName, jsPackage.getResolvedId());
			}
			else {
				JSPackageDependency dependency = jsPackage.getJSPackageDependency(dependencyPackageName);

				if (dependency == null) {
					String errorMessage = ":ERROR:Missing version constraints for " +
						dependencyPackageName +
						" in package.json of " +
						jsPackage.getResolvedId();

					contextMap.put(dependencyPackageName, errorMessage);
				}
				else {
					JSPackage packageDependency = _npmRegistry.resolveJSPackageDependency(dependency);

					if (packageDependency == null) {
						String errorMessage = ":ERROR:Package " +
							dependencyPackageName +
							" which is a dependency of " +
							jsPackage.getResolvedId() +
							" is not deployed in the server";

						contextMap.put(dependencyPackageName, errorMessage);
					}
					else {
						contextMap.put(packageDependency.getName(), packageDependency.getResolvedId());
					}
				}
			}
		}

		return contextMap;
	}

	private final JSModule _module;

	private final NPMRegistry _npmRegistry;
}
