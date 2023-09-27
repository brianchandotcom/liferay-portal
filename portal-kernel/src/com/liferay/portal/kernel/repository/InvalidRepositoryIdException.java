/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository;

/**
 * @author Alexander Chow
 */
public class InvalidRepositoryIdException extends RepositoryException {

	public InvalidRepositoryIdException() {
	}

	public InvalidRepositoryIdException(String msg) {
		super(msg);
	}

	public InvalidRepositoryIdException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public InvalidRepositoryIdException(Throwable throwable) {
		super(throwable);
	}

}