/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

/**
 * Represents an NPM package dependency (i.e., another NPM package).
 *
 * @author Iván Zaera
 */
public class JSPackageDependency {

	/**
	 * Constructs a <code>JSPackageDependency</code> with the JS package,
	 * package name, and version constraints.
	 *
	 * @param jsPackage the package declaring the dependency
	 * @param packageName the dependency's package name
	 * @param versionConstraints the NPM semantic version expression defining
	 *        the suitable versions for the dependency
	 */
	public JSPackageDependency(
		JSPackage jsPackage, String packageName, String versionConstraints) {

		_jsPackage = jsPackage;
		_packageName = packageName;
		_versionConstraints = versionConstraints;
	}

	/**
	 * Returns the NPM package declaring the dependency.
	 *
	 * @return the NPM package
	 */
	public JSPackage getJSPackage() {
		return _jsPackage;
	}

	/**
	 * Returns the dependency's package name.
	 *
	 * @return the dependency's package name
	 */
	public String getPackageName() {
		return _packageName;
	}

	/**
	 * Returns the NPM semantic version expression defining the suitable
	 * versions for the dependency.
	 *
	 * @return the NPM semantic version expression
	 */
	public String getVersionConstraints() {
		return _versionConstraints;
	}

	private final JSPackage _jsPackage;
	private final String _packageName;
	private final String _versionConstraints;

}