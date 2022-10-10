/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.runtime.exception;

import java.security.GeneralSecurityException;
import java.security.UnrecoverableKeyException;

/**
 * @author Stian Sigvartsen
 */
public class CredentialAuthException extends SecurityException {

	private CredentialAuthException(
		String message, GeneralSecurityException generalSecurityException) {

		super(
			String.format(
				"%s. %s", message, generalSecurityException.getMessage()),
			generalSecurityException);
	}

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

}