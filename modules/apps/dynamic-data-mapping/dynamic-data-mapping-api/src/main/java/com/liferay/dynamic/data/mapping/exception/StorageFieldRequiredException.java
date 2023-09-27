/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class StorageFieldRequiredException extends StorageException {

	public StorageFieldRequiredException() {
	}

	public StorageFieldRequiredException(String msg) {
		super(msg);
	}

	public StorageFieldRequiredException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public StorageFieldRequiredException(Throwable throwable) {
		super(throwable);
	}

}