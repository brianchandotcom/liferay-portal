/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Scott Lee
 */
public class DuplicatePasswordPolicyException extends PortalException {

	public DuplicatePasswordPolicyException() {
	}

	public DuplicatePasswordPolicyException(String msg) {
		super(msg);
	}

	public DuplicatePasswordPolicyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicatePasswordPolicyException(Throwable throwable) {
		super(throwable);
	}

}