/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.json.storage.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Preston Crary
 */
public class NoSuchJSONStorageEntryException extends NoSuchModelException {

	public NoSuchJSONStorageEntryException() {
	}

	public NoSuchJSONStorageEntryException(String msg) {
		super(msg);
	}

	public NoSuchJSONStorageEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchJSONStorageEntryException(Throwable throwable) {
		super(throwable);
	}

}