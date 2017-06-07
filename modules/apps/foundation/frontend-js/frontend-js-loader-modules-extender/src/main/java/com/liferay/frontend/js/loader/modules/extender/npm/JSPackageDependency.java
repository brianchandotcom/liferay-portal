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

/**
 * A description of an NPM package dependency (another NPM package).
 * @author Iván Zaera
 */
public class JSPackageDependency {

	/**
	 *
	 * @param jsPackage the package declaring this dependency
	 * @param packageName the package name of the dependency
	 * @param versionConstraints an NPM semantic version expression defining the
	 *                           suitable versions for this dependency
	 */
	public JSPackageDependency(
		JSPackage jsPackage, String packageName, String versionConstraints) {

		_jsPackage = jsPackage;
		_packageName = packageName;
		_versionConstraints = versionConstraints;
	}

	/**
	 * Get the NPM package declaring this dependency.
	 */
	public JSPackage getJSPackage() {
		return _jsPackage;
	}

	/**
	 * Get the package name of the dependency.
	 */
	public String getPackageName() {
		return _packageName;
	}

	/**
	 * Get the NPM semantic version expression defining the suitable versions
	 * for this dependency.
	 */
	public String getVersionConstraints() {
		return _versionConstraints;
	}

	private final JSPackage _jsPackage;
	private final String _packageName;
	private final String _versionConstraints;

}