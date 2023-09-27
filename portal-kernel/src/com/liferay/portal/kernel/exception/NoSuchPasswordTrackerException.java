/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchPasswordTrackerException extends NoSuchModelException {

	public NoSuchPasswordTrackerException() {
	}

	public NoSuchPasswordTrackerException(String msg) {
		super(msg);
	}

	public NoSuchPasswordTrackerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchPasswordTrackerException(Throwable throwable) {
		super(throwable);
	}

}