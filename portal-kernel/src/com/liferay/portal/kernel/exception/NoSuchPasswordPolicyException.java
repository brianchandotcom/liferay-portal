/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Scott Lee
 */
public class NoSuchPasswordPolicyException extends NoSuchModelException {

	public NoSuchPasswordPolicyException() {
	}

	public NoSuchPasswordPolicyException(String msg) {
		super(msg);
	}

	public NoSuchPasswordPolicyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchPasswordPolicyException(Throwable throwable) {
		super(throwable);
	}

}