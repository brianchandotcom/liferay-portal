/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Murilo Stodolni
 */
public class NoSuchObjectFolderException extends NoSuchModelException {

	public NoSuchObjectFolderException() {
	}

	public NoSuchObjectFolderException(String msg) {
		super(msg);
	}

	public NoSuchObjectFolderException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchObjectFolderException(Throwable throwable) {
		super(throwable);
	}

}