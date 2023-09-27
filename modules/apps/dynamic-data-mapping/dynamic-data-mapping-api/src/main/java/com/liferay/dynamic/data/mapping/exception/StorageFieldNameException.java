/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class StorageFieldNameException extends StorageException {

	public StorageFieldNameException() {
	}

	public StorageFieldNameException(String msg) {
		super(msg);
	}

	public StorageFieldNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public StorageFieldNameException(Throwable throwable) {
		super(throwable);
	}

}