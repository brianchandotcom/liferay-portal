/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

/**
 * @author Kevin Lee
 */
public class WrapperClassLoader extends ClassLoader {

	public WrapperClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	@Override
	public boolean equals(Object object) {
		return _classLoader.equals(object);
	}

	@Override
	public int hashCode() {
		return _classLoader.hashCode();
	}

	private final ClassLoader _classLoader;

}