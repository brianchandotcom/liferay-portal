/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.runtime.exception;

import java.security.GeneralSecurityException;
import java.security.UnrecoverableKeyException;

/**
 * @author Stian Sigvartsen
 */
public class CredentialAuthException extends SecurityException {

	public static class GeneralCredentialAuthException
		extends CredentialAuthException {

		public GeneralCredentialAuthException(
			String message, GeneralSecurityException generalSecurityException) {

			super(message, generalSecurityException);
		}

	}

	public static class InvalidCredentialPassword
		extends CredentialAuthException {

		public InvalidCredentialPassword(
			String message,
			UnrecoverableKeyException unrecoverableKeyException) {

			super(message, unrecoverableKeyException);
		}

	}

	public static class InvalidKeyStore extends CredentialAuthException {

		public InvalidKeyStore(
			String message, GeneralSecurityException generalSecurityException) {

			super(message, generalSecurityException);
		}

	}

	public static class InvalidKeyStorePassword
		extends CredentialAuthException {

		public InvalidKeyStorePassword(
			String message,
			UnrecoverableKeyException unrecoverableKeyException) {

			super(message, unrecoverableKeyException);
		}

	}

	private CredentialAuthException(
		String message, GeneralSecurityException generalSecurityException) {

		super(
			String.format(
				"%s: %s", message, generalSecurityException.getMessage()),
			generalSecurityException);
	}

}