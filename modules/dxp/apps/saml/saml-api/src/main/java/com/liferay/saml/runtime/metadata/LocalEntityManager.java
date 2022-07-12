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

package com.liferay.saml.runtime.metadata;

import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.exception.CredentialAuthException;
import com.liferay.saml.runtime.exception.CredentialException;

import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface LocalEntityManager {

	public void authenticateLocalEntityCertificate(
			String certificateKeyPassword, CertificateUsage certificateUsage,
			String entityId)
		throws CredentialAuthException, CredentialException;

	public void deleteLocalEntityCertificate(CertificateUsage certificateUsage)
		throws KeyStoreException;

	public default String getEncodedLocalEntityCertificate()
		throws SamlException {

		return getEncodedLocalEntityCertificate(CertificateUsage.SIGNING);
	}

	public String getEncodedLocalEntityCertificate(
			CertificateUsage certificateUsage)
		throws SamlException;

	public default X509Certificate getLocalEntityCertificate()
		throws SamlException {

		return getLocalEntityCertificate(CertificateUsage.SIGNING);
	}

	public X509Certificate getLocalEntityCertificate(
			CertificateUsage certificateUsage)
		throws SamlException;

	public String getLocalEntityId();

	public boolean hasDefaultIdpRole();

	public void storeLocalEntityCertificate(
			PrivateKey privateKey, String certificateKeyPassword,
			X509Certificate x509Certificate, CertificateUsage certificateUsage)
		throws Exception;

	public static enum CertificateUsage {

		ENCRYPTION, SIGNING

	}

}