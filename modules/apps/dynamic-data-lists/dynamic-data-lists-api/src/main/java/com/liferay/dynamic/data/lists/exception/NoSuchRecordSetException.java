/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * Thrown when the system is unable to find a required DDL Record Set.
 *
 * @author Brian Wing Shun Chan
 */
public class NoSuchRecordSetException extends NoSuchModelException {

	public NoSuchRecordSetException() {
	}

	public NoSuchRecordSetException(String msg) {
		super(msg);
	}

	public NoSuchRecordSetException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchRecordSetException(Throwable throwable) {
		super(throwable);
	}

}