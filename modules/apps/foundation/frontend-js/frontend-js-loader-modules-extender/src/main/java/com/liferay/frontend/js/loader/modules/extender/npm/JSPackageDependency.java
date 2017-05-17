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
 * @author Iván Zaera
 */
public class JSPackageDependency {

	public JSPackageDependency(
		JSPackage jsPackage, String packageName, String versionConstraints) {

		_jsPackage = jsPackage;
		_packageName = packageName;
		_versionConstraints = versionConstraints;
	}

	public JSPackage getJSPackage() {
		return _jsPackage;
	}

	public String getPackageName() {
		return _packageName;
	}

	public String getVersionConstraints() {
		return _versionConstraints;
	}

	private final JSPackage _jsPackage;
	private final String _packageName;
	private final String _versionConstraints;

}