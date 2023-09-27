/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

import java.net.URL;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents an NPM package inside a {@link JSBundle}.
 *
 * <p>
 * {@link JSModule}s belong to a {@link JSPackage} and {@link JSPackage}s belong
 * to a {@link JSBundle}.
 * </p>
 *
 * @author Iván Zaera
 */
@ProviderType
public interface JSPackage extends JSBundleObject {

	/**
	 * Returns the NPM package's bundle.
	 *
	 * @return the NPM package's bundle
	 */
	public JSBundle getJSBundle();

	public JSModule getJSModule(String packagePath);

	public Collection<JSModuleAlias> getJSModuleAliases();

	/**
	 * Returns the NPM modules contained inside the NPM package.
	 *
	 * @return the NPM modules
	 */
	public Collection<JSModule> getJSModules();

	/**
	 * Returns the dependencies (i.e., other NPM packages) declared by the NPM
	 * package.
	 *
	 * @return the NPM package dependencies
	 */
	public Collection<JSPackageDependency> getJSPackageDependencies();

	/**
	 * Returns the NPM package's dependency (i.e., another NPM package) with the
	 * package name.
	 *
	 * @param  packageName the package name
	 * @return the package dependency or <code>null</code> if no dependency with
	 *         the name exists
	 */
	public JSPackageDependency getJSPackageDependency(String packageName);

	/**
	 * Returns the default module's name declared by the NPM package.
	 *
	 * @return the default module's name
	 */
	public String getMainModuleName();

	public String getResolvedId();

	/**
	 * Returns the resource's URL residing in the NPM package.
	 *
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 * @param  location the resource's path
	 * @return the resource's URL
	 */
	@Deprecated
	public URL getResourceURL(String location);

	/**
	 * Returns the package's NPM version.
	 *
	 * @return the package's NPM version
	 */
	public String getVersion();

}