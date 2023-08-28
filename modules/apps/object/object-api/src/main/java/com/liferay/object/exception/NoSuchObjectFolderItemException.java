/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Murilo Stodolni
 */
public class NoSuchObjectFolderItemException extends NoSuchModelException {

	public NoSuchObjectFolderItemException() {
	}

	public NoSuchObjectFolderItemException(String msg) {
		super(msg);
	}

	public NoSuchObjectFolderItemException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchObjectFolderItemException(Throwable throwable) {
		super(throwable);
	}

}