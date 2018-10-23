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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;

/**
 * @author Iván Zaera Avellón
 */
public class ResourceDescriptor {

	public ResourceDescriptor(JSPackage jsPackage, String packagePath) {
		_jsPackage = jsPackage;
		_packagePath = packagePath;
	}

	public JSPackage getJsPackage() {
		return _jsPackage;
	}

	public String getPackagePath() {
		return _packagePath;
	}

	@Override
	public String toString() {
		return _jsPackage.getResolvedId() + _jsPackage;
	}

	private final JSPackage _jsPackage;
	private final String _packagePath;

}