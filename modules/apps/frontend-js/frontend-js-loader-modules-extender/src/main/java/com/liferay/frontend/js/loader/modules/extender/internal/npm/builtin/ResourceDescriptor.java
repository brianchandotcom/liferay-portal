/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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