/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.dependency.checker;

import org.gradle.api.GradleException;

/**
 * @author Andrea Di Giorgi
 */
public class DependencyCheckerException extends GradleException {

	public DependencyCheckerException(String message) {
		super(message);
	}

}