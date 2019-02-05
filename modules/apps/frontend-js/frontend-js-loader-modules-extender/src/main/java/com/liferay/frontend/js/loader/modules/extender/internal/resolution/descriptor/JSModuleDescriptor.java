package com.liferay.frontend.js.loader.modules.extender.internal.resolution.descriptor;

import com.liferay.frontend.js.loader.modules.extender.internal.resolution.ModuleDescriptor;
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
public class JSModuleDescriptor implements ModuleDescriptor {

	public JSModuleDescriptor(JSModule jsModule, NPMRegistry npmRegistry) {
		_jsModule = jsModule;
		_npmRegistry = npmRegistry;
	}

	@Override
	public String getName() {
		return _jsModule.getResolvedId();
	}

	@Override
	public Collection<String> getDependencies() {
		return _jsModule.getDependencies();
	}

	@Override
	public Map<String, String> getMappings() {
		JSPackage jsPackage = _jsModule.getJSPackage();

		Map<String, String> contextMap = new ConcurrentHashMap<>();

		for (String dependencyPackageName : _jsModule.getDependencyPackageNames()) {

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
		return _jsModule.getResolvedURL();
	}

	private final JSModule _jsModule;
	private final NPMRegistry _npmRegistry;

}
