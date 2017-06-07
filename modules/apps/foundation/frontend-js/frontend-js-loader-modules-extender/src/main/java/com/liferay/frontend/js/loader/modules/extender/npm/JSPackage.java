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

package com.liferay.frontend.js.loader.modules.extender.npm;

import java.net.URL;

import java.util.Collection;

/**
 * A description of an NPM package inside a {@link JSBundle}.
 * @author Iván Zaera
 */
public interface JSPackage extends JSBundleObject {

	/**
	 * Get the bundle where this package belongs.
	 */
	public JSBundle getJSBundle();

	public Collection<JSModuleAlias> getJSModuleAliases();

	/**
	 * Get the list of NPM modules contained inside the NPM package described by
	 * this object.
	 */
	public Collection<JSModule> getJSModules();

	/**
	 * Get the list of dependencies (other NPM packages) declared by this
	 * package.
	 */
	public Collection<JSPackageDependency> getJSPackageDependencies();

	/**
	 * Get a dependency of this package (another NPM package) looked up by its
	 * name.
	 * @param packageName the package name
	 * @return a {@link JSPackageDependency} or null if no dependency with the
	 *         given name exists
	 */
	public JSPackageDependency getJSPackageDependency(String packageName);

	/**
	 * Get the name of the default module declared by this package.
	 */
	public String getMainModuleName();

	/**
	 * Get the URL of a resource living inside this package.
	 * @param location the path to the resource
	 */
	public URL getResourceURL(String location);

	/**
	 * Get the NPM version of this package.
	 */
	public String getVersion();

}