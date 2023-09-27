/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchEntrySegmentsEntryRelException extends NoSuchModelException {

	public NoSuchEntrySegmentsEntryRelException() {
	}

	public NoSuchEntrySegmentsEntryRelException(String msg) {
		super(msg);
	}

	public NoSuchEntrySegmentsEntryRelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchEntrySegmentsEntryRelException(Throwable throwable) {
		super(throwable);
	}

}