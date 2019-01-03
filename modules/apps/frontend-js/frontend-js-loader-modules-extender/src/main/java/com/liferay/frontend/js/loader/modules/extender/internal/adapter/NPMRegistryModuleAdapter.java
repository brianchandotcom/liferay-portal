package com.liferay.frontend.js.loader.modules.extender.internal.adapter;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rodolfo Roza Miranda
 */
public class NPMRegistryModuleAdapter implements JSModuleAdapter {

	public NPMRegistryModuleAdapter(
		JSModule module, NPMRegistry npmRegistry,
		Portal portal) {
		_module = module;
		_npmRegistry = npmRegistry;
		_portal = portal;
	}

	@Override
	public String getAlias() {
		return _module.getResolvedId();
	}

	@Override
	public Collection<String> getDependencies() {
		return _module.getDependencies();
	}

	@Override
	public Map<String, String> getMap() {
		JSPackage jsPackage = _module.getJSPackage();

		Map<String, String> contextMap = new ConcurrentHashMap<>();

		for (String dependencyPackageName : _module.getDependencyPackageNames()) {

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
					String errorMessage =
						":ERROR:Missing version constraints for " +
						dependencyPackageName +
						" in package.json of " +
						jsPackage.getResolvedId();

					contextMap.put(dependencyPackageName, errorMessage);
				}
				else {
					JSPackage packageDependency =
						_npmRegistry.resolveJSPackageDependency(dependency);

					if (packageDependency == null) {
						String errorMessage = ":ERROR:Package " +
											  dependencyPackageName +
											  " which is a dependency of " +
											  jsPackage.getResolvedId() +
											  " is not deployed in the server";

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
	public String getPath() {
		String pathModule = _portal.getPathModule();
		String resolvedPath = "/js/resolved-module/";
		return pathModule + resolvedPath + _module.getResolvedId();
	}

	private final JSModule _module;
	private final NPMRegistry _npmRegistry;
	private final Portal _portal;
}
