/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchRecordSetVersionException extends NoSuchModelException {

	public NoSuchRecordSetVersionException() {
	}

	public NoSuchRecordSetVersionException(String msg) {
		super(msg);
	}

	public NoSuchRecordSetVersionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchRecordSetVersionException(Throwable throwable) {
		super(throwable);
	}

}