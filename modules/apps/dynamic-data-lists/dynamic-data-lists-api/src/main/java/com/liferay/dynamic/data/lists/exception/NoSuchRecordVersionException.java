/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * Thrown when the system is unable to find a required DDL Record Version.
 *
 * @author Brian Wing Shun Chan
 */
public class NoSuchRecordVersionException extends NoSuchModelException {

	public NoSuchRecordVersionException() {
	}

	public NoSuchRecordVersionException(String msg) {
		super(msg);
	}

	public NoSuchRecordVersionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchRecordVersionException(Throwable throwable) {
		super(throwable);
	}

}