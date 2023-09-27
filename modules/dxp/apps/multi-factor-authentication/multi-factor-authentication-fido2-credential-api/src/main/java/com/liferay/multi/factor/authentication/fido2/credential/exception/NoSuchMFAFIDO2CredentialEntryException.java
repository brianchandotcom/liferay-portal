/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Arthur Chan
 */
public class NoSuchMFAFIDO2CredentialEntryException
	extends NoSuchModelException {

	public NoSuchMFAFIDO2CredentialEntryException() {
	}

	public NoSuchMFAFIDO2CredentialEntryException(String msg) {
		super(msg);
	}

	public NoSuchMFAFIDO2CredentialEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchMFAFIDO2CredentialEntryException(Throwable throwable) {
		super(throwable);
	}

}