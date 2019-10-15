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

package com.liferay.saml.runtime.certificate;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import java.util.Date;
import java.util.Optional;

/**
 * @author Michael C. Han
 */
public interface CertificateTool {

	public X509Certificate generateCertificate(
			KeyPair keyPair, CertificateEntityId issuerCertificateEntityId,
			CertificateEntityId subjectCertificateEntityId, Date startDate,
			Date endDate, String signatureAlgorithm)
		throws CertificateException;

	public KeyPair generateKeyPair(String algorithm, int keySize)
		throws NoSuchAlgorithmException;

	public String getFingerprint(
			String algorithm, X509Certificate x509Certificate)
		throws CertificateException, NoSuchAlgorithmException;

	public String getSerialNumber(X509Certificate x509Certificate);

	public Optional<String> getSubjectName(X509Certificate x509Certificate);

}