/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository;

/**
 * @author Iván Zaera
 */
public class UndeployedExternalRepositoryException extends RepositoryException {

	public UndeployedExternalRepositoryException(String className) {
		super(
			"Unable to load external repository class " + className +
				" because its plugin is not deployed");

		this.className = className;
	}

	public UndeployedExternalRepositoryException(
		String className, String message) {

		super(message);

		this.className = className;
	}

	public final String className;

}