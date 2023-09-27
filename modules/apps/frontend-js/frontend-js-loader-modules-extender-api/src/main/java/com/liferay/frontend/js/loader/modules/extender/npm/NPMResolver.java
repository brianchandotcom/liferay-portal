/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

/**
 * @author Iván Zaera Avellón
 */
public interface NPMResolver {

	/**
	 * @throws UnsupportedOperationException if the associated bundle does not contain AMD modules
	 * @review
	 */
	public JSPackage getDependencyJSPackage(String packageName);

	/**
	 * @throws UnsupportedOperationException if the associated bundle does not contain AMD modules
	 * @review
	 */
	public JSPackage getJSPackage();

	/**
	 * @throws UnsupportedOperationException if the associated bundle does not contain AMD modules
	 * @review
	 */
	public String resolveModuleName(String moduleName);

}