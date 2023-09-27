/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchRepositoryEntryException extends NoSuchModelException {

	public NoSuchRepositoryEntryException() {
	}

	public NoSuchRepositoryEntryException(String msg) {
		super(msg);
	}

	public NoSuchRepositoryEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchRepositoryEntryException(Throwable throwable) {
		super(throwable);
	}

}