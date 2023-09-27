/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Arthur Chan
 */
public class DuplicateMFAFIDO2CredentialEntryException extends PortalException {

	public DuplicateMFAFIDO2CredentialEntryException() {
	}

	public DuplicateMFAFIDO2CredentialEntryException(String msg) {
		super(msg);
	}

	public DuplicateMFAFIDO2CredentialEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateMFAFIDO2CredentialEntryException(Throwable throwable) {
		super(throwable);
	}

}