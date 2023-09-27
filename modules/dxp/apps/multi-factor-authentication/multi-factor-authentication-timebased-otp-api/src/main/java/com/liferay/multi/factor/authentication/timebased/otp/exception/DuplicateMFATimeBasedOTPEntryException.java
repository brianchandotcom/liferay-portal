/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Arthur Chan
 */
public class DuplicateMFATimeBasedOTPEntryException extends PortalException {

	public DuplicateMFATimeBasedOTPEntryException() {
	}

	public DuplicateMFATimeBasedOTPEntryException(String msg) {
		super(msg);
	}

	public DuplicateMFATimeBasedOTPEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateMFATimeBasedOTPEntryException(Throwable throwable) {
		super(throwable);
	}

}