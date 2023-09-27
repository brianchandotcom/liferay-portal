/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.dependency.checker.internal;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseDependencyCheckerImpl implements DependencyChecker {

	@Override
	public boolean isThrowError() {
		return _throwError;
	}

	@Override
	public void setThrowError(boolean throwError) {
		_throwError = throwError;
	}

	private boolean _throwError;

}