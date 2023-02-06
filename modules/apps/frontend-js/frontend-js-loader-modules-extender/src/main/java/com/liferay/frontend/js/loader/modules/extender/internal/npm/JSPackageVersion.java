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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.github.yuchi.semver.Version;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;

/**
 * @author Iván Zaera
 */
public class JSPackageVersion {

	public JSPackageVersion(JSPackage jsPackage) {
		this.jsPackage = jsPackage;

		version = Version.from(jsPackage.getVersion(), true);
	}

	public Version getVersion() {
		return version;
	}

	public final JSPackage jsPackage;
	public final Version version;

}