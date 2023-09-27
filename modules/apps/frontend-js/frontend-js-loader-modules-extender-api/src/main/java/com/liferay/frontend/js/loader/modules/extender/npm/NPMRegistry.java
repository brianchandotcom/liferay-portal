/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

import java.util.Collection;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera Avellón
 */
@ProviderType
public interface NPMRegistry {

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public void addJSBundleTracker(JSBundleTracker jsBundleTracker);

	public Map<String, String> getGlobalAliases();

	public JSModule getJSModule(String identifier);

	public JSPackage getJSPackage(String identifier);

	public Collection<JSPackage> getJSPackages();

	public JSModule getResolvedJSModule(String identifier);

	public Collection<JSModule> getResolvedJSModules();

	public JSPackage getResolvedJSPackage(String identifier);

	public Collection<JSPackage> getResolvedJSPackages();

	public String mapModuleName(String moduleName);

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public void removeJSBundleTracker(JSBundleTracker jsBundleTracker);

	public JSPackage resolveJSPackageDependency(
		JSPackageDependency jsPackageDependency);

	public NPMRegistryUpdate update();

}