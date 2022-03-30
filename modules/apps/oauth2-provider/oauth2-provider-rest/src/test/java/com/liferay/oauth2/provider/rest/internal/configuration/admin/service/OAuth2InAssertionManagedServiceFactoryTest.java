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

package com.liferay.oauth2.provider.rest.internal.configuration.admin.service;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsImpl;

import java.util.Dictionary;

import org.apache.cxf.rs.security.jose.jwk.JwkUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationException;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Stian Sigvartsen
 * @author Arthur Chan
 */
@PrepareForTest(JwkUtils.class)
@RunWith(PowerMockRunner.class)
public class OAuth2InAssertionManagedServiceFactoryTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		PropsUtil.setProps(new PropsImpl());

		_oAuth2InAssertionManagedServiceFactory =
			new OAuth2InAssertionManagedServiceFactory();

		_oAuth2InAssertionManagedServiceFactory.activate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMergedSystemAndInstanceConfiguration()
		throws ConfigurationException {

		_oAuth2InAssertionManagedServiceFactory.updated(
			_SYSTEM_ISSUER1_PID, _buildDictionary(0, "system_issuer1"));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				1, "system_issuer1", _KID));

		_oAuth2InAssertionManagedServiceFactory.updated(
			_INSTANCE1_ISSUER1_PID, _buildDictionary(1, "instance1_issuer1"));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				1, "system_issuer1", _KID));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				1, "instance1_issuer1", _KID));

		_oAuth2InAssertionManagedServiceFactory.updated(
			_INSTANCE2_ISSUER1_PID, _buildDictionary(2, "instance2_issuer1"));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				2, "system_issuer1", _KID));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				2, "instance2_issuer1", _KID));

		_oAuth2InAssertionManagedServiceFactory.updated(
			_INSTANCE1_ISSUER1_PID,
			_buildDictionary(1, "instance1_issuer1_edit"));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				1, "instance1_issuer1_edit", _KID));

		_oAuth2InAssertionManagedServiceFactory.deleted(_SYSTEM_ISSUER1_PID);

		_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
			1, "system_issuer1", _KID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateExistingInstanceConfiguration()
		throws ConfigurationException {

		_oAuth2InAssertionManagedServiceFactory.updated(
			_INSTANCE1_ISSUER1_PID,
			_buildDictionary(1, _INSTANCE1_ISSUER1_PID));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				1, _INSTANCE1_ISSUER1_PID, _KID));

		_oAuth2InAssertionManagedServiceFactory.updated(
			_INSTANCE1_ISSUER1_PID,
			_buildDictionary(1, "instance1_issuer1_edit"));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				1, "instance1_issuer1_edit", _KID));

		_oAuth2InAssertionManagedServiceFactory.deleted(_INSTANCE1_ISSUER1_PID);

		_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
			1, "instance1_issuer1_edit", _KID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateExistingSystemConfiguration()
		throws ConfigurationException {

		_oAuth2InAssertionManagedServiceFactory.updated(
			_SYSTEM_ISSUER1_PID, _buildDictionary(0, "system_issuer1"));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				1, "system_issuer1", _KID));

		_oAuth2InAssertionManagedServiceFactory.updated(
			_SYSTEM_ISSUER1_PID, _buildDictionary(0, "system_issuer1_edit"));

		Assert.assertNotNull(
			_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
				1, "system_issuer1_edit", _KID));

		_oAuth2InAssertionManagedServiceFactory.deleted(_SYSTEM_ISSUER1_PID);

		_oAuth2InAssertionManagedServiceFactory.getJWSSignatureVerifier(
			1, "system_issuer1_edit", _KID);
	}

	private Dictionary<String, Object> _buildDictionary(
		long companyId, String issuer) {

		return _buildDictionary(
			StringPool.BLANK, companyId, issuer, _TEST_JWKS);
	}

	private Dictionary<String, Object> _buildDictionary(
		String authType, long companyId, String issuer, String jwks) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"companyId", companyId
		).put(
			"oauth2.in.assertion.issuer", issuer
		).put(
			"oauth2.in.assertion.signature.json.web.key.set", jwks
		).put(
			"oauth2.in.assertion.user.auth.type", authType
		).build();
	}

	private static final String _INSTANCE1_ISSUER1_PID = "instance1_pid1";

	private static final String _INSTANCE2_ISSUER1_PID = "instance2_pid1";

	private static final String _KID = "kid";

	private static final String _SYSTEM_ISSUER1_PID = "system_pid1";

	/**
	 * Real jwks is required for creating JWSSignatureVerifier.
	 * No need to use JSONFactory to build json first, because it's fixed for
	 * this test purpose.
	 */
	private static final String _TEST_JWKS = StringBundler.concat(
		"{\"keys\":[{\"kid\":\"", _KID, "\",\"kty\":\"RSA\",\"alg\":\"RS256\",",
		"\"use\":\"sig\",\"n\":\"iQPNG5vpEemG7a8vrE42XJHv5XwnQW2wNDnypmEycAw4d",
		"YhvlUn9MNZnZ7ccOKOMrIpHbudQ4P-2xuV77n8UGbKzj4GJjl3gKbYBEzQz2FW9p3uG0I",
		"j39C7p2puQPpHsEiZD3r6jtOTsdVZNvEj6O0MmRFwgvxKsG8u3pY2d47MWvoX-k-USBDv",
		"V3CSQtUhQPtTyhaU_F7hcdNtaM8nAftTSXmnbT_A8YbxRTbPHQa2y-tE4WckoHSz5uG3V",
		"sVuwD7CA0zCUG0DNyJUbgpi8skpGehGlK5Jq1R9gblRru2NdlxID4DHvBxNG2CB6J8139",
		"YsptvF-Fv5MqNsPlEO6OQ\",\"e\":\"AQAB\",\"x5c\":[\"MIICmzCCAYMCBgF+I6e",
		"qqzANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjIwMTA0MDU1MzE3",
		"WhcNMzIwMTA0MDU1NDU3WjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBA",
		"QUAA4IBDwAwggEKAoIBAQCJA80bm+kR6Ybtry+sTjZcke/lfCdBbbA0OfKmYTJwDDh1iG",
		"+VSf0w1mdntxw4o4ysikdu51Dg/7bG5XvufxQZsrOPgYmOXeAptgETNDPYVb2ne4bQiPf",
		"0Lunam5A+kewSJkPevqO05Ox1Vk28SPo7QyZEXCC/Eqwby7eljZ3jsxa+hf6T5RIEO9Xc",
		"JJC1SFA+1PKFpT8XuFx021ozycB+1NJeadtP8DxhvFFNs8dBrbL60ThZySgdLPm4bdWxW",
		"7APsIDTMJQbQM3IlRuCmLyySkZ6EaUrkmrVH2BuVGu7Y12XEgPgMe8HE0bYIHonzXf1iy",
		"m28X4W/kyo2w+UQ7o5AgMBAAEwDQYJKoZIhvcNAQELBQADggEBACXmJ9xdwOUEpCqgI12",
		"11w9X7aKCHSkfkgWq1oE+o+Ai0df6/d1x3EYYKs/z5qk3rOYK0MTkK/SNI13xsZIJxA7C",
		"Hb8qEm9uMr/2qlbSQvR7udkab42YeBozMYaIxLakFzQnXHupEdcJm9RAbf32/kp8mfFhU",
		"8N7+bKrsYU7vlAnviA3sAqTv3xwLDQVOsFgzJf+s21cZr0YE5olsVShojyEAPwxwgImuq",
		"Uj0raHy4oERQg47E+rma9Xydnt0XV89twAPHloecx87kDQj2YixADUo2B271QKU+hglfI",
		"XCCM/kCGXBeyR0FjIg+xaeidUi1sGwirlyvDe/G1u/QVDA1k=\"],\"x5t\":\"h-GSUC",
		"ljTzsWQZpMnqObXIFKhfw\",\"x5t#S256\":\"9vduT2wB26-VpNtMqvuLwH1at3288b",
		"Sw2mR-7EIXWC4\"}]}");

	private OAuth2InAssertionManagedServiceFactory
		_oAuth2InAssertionManagedServiceFactory;

}