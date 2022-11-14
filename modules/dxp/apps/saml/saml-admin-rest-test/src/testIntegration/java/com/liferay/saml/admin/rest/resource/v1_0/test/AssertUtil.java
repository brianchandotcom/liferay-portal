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

package com.liferay.saml.admin.rest.resource.v1_0.test;

import com.liferay.saml.runtime.metadata.LocalEntityManager;

import org.junit.Assert;

/**
 * @author Stian Sigvartsen
 */
public class AssertUtil {

	public static void assertEncryptionCredentialExceptionMessage(
		String actual) {

		String expected = String.format(
			_CREDENTIAL_EXCEPTION_MESSAGE_FORMAT,
			LocalEntityManager.CertificateUsage.ENCRYPTION);

		Assert.assertEquals(
			expected,
			actual.substring(Math.max(actual.length() - expected.length(), 0)));
	}

	public static void assertSigningCredentialExceptionMessage(String actual) {
		String expected = String.format(
			_CREDENTIAL_EXCEPTION_MESSAGE_FORMAT,
			LocalEntityManager.CertificateUsage.SIGNING);

		Assert.assertEquals(
			expected,
			actual.substring(Math.max(actual.length() - expected.length(), 0)));
	}

	private static final String _CREDENTIAL_EXCEPTION_MESSAGE_FORMAT =
		"Unable to authenticate with the %s certificate. Verify that the " +
			"SAML KeyStore contains a certificate for the entity ID and that " +
				"it is protected by the provided key credential password.";

}