/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.dependency.checker.internal;

/**
 * @author Andrea Di Giorgi
 */
public interface DependencyChecker {

	public void check(String group, String name, String version)
		throws Exception;

	public boolean isThrowError();

	public void setThrowError(boolean throwError);

}