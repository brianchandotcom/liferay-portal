/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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