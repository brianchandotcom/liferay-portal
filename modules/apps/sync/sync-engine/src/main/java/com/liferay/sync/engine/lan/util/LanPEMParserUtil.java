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

package com.liferay.sync.engine.lan.util;

import java.io.StringReader;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

/**
 * @author Dennis Ju
 */
public class LanPEMParserUtil {

	public static PrivateKey parsePrivateKey(String privateKey)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("-----BEGIN PRIVATE KEY-----\n");
		sb.append(privateKey);

		if (!privateKey.endsWith("\n")) {
			sb.append("\n");
		}

		sb.append("-----END PRIVATE KEY-----");

		PEMParser pemParser = new PEMParser(new StringReader(sb.toString()));

		JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();

		return jcaPEMKeyConverter.getPrivateKey(
			(PrivateKeyInfo)pemParser.readObject());
	}

	public static X509Certificate parseX509Certificate(String certificate)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("-----BEGIN CERTIFICATE-----\n");
		sb.append(certificate);

		if (!certificate.endsWith("\n")) {
			sb.append("\n");
		}

		sb.append("-----END CERTIFICATE-----");

		PEMParser pemParser = new PEMParser(new StringReader(sb.toString()));

		JcaX509CertificateConverter jcaX509CertificateConverter =
			new JcaX509CertificateConverter();

		return jcaX509CertificateConverter.getCertificate(
			(X509CertificateHolder)pemParser.readObject());
	}

}