/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

/**
 * @author Iván Zaera
 */
public class JSModuleAlias {

	public JSModuleAlias(JSPackage jsPackage, String moduleName, String alias) {
		_jsPackage = jsPackage;
		_moduleName = moduleName;
		_alias = alias;
	}

	public String getAlias() {
		return _alias;
	}

	public JSPackage getJsPackage() {
		return _jsPackage;
	}

	public String getModuleName() {
		return _moduleName;
	}

	private final String _alias;
	private final JSPackage _jsPackage;
	private final String _moduleName;

}