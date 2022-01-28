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

package com.liferay.oauth2.provider.rest.internal.endpoint.access.token.grant.handler;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.util.PropsImpl;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.rs.security.jose.jwk.JsonWebKey;
import org.apache.cxf.rs.security.jose.jwk.JsonWebKeys;
import org.apache.cxf.rs.security.jose.jwk.JwkUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.osgi.service.cm.ConfigurationException;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Stian Sigvartsen
 */
@PrepareForTest(JwkUtils.class)
@RunWith(PowerMockRunner.class)
public class LiferayJWTBearerGrantHandlerTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		PropsUtil.setProps(new PropsImpl());

		_liferayJWTBearerGrantHandler = new LiferayJWTBearerGrantHandler();

		_liferayJWTBearerGrantHandler.activate(
			HashMapBuilder.<String, Object>put(
				"oauth2.allow.jwt.bearer.grant", true
			).build());

		PowerMockito.mockStatic(JwkUtils.class);
		when(
			JwkUtils.readJwkSet(Mockito.anyString())
		).thenReturn(
			new JsonWebKeys(
				new JsonWebKey(
					HashMapBuilder.<String, Object>put(
						"test_kid", StringPool.BLANK
					).build()))
		);
	}

	@Test
	public void testMergedSystemAndInstanceConfiguration()
		throws ConfigurationException {

		_liferayJWTBearerGrantHandler.updated(
			_SYSTEM_ISSUER1_PID, _buildDictionary(0, "system_issuer1"));

		Assert.assertEquals(
			_getIssuersSet(1), SetUtil.fromArray("system_issuer1"));

		_liferayJWTBearerGrantHandler.updated(
			_INSTANCE1_ISSUER1_PID, _buildDictionary(1, "instance1_issuer1"));

		Assert.assertEquals(
			_getIssuersSet(1),
			SetUtil.fromArray("system_issuer1", "instance1_issuer1"));

		_liferayJWTBearerGrantHandler.updated(
			_INSTANCE2_ISSUER1_PID, _buildDictionary(2, "instance2_issuer1"));

		Assert.assertEquals(
			_getIssuersSet(2),
			SetUtil.fromArray("system_issuer1", "instance2_issuer1"));

		_liferayJWTBearerGrantHandler.updated(
			_INSTANCE1_ISSUER1_PID,
			_buildDictionary(1, "instance1_issuer1_edit"));
		Assert.assertEquals(
			_getIssuersSet(1),
			SetUtil.fromArray("system_issuer1", "instance1_issuer1_edit"));

		_liferayJWTBearerGrantHandler.deleted(_SYSTEM_ISSUER1_PID);

		Assert.assertEquals(
			_getIssuersSet(1), SetUtil.fromArray("instance1_issuer1_edit"));
		Assert.assertEquals(
			_getIssuersSet(2), SetUtil.fromArray("instance2_issuer1"));
	}

	@Test
	public void testUpdateExistingInstanceConfiguration()
		throws ConfigurationException {

		_liferayJWTBearerGrantHandler.updated(
			_INSTANCE1_ISSUER1_PID,
			_buildDictionary(1, _INSTANCE1_ISSUER1_PID));

		Assert.assertEquals(
			_getIssuersSet(1), SetUtil.fromArray(_INSTANCE1_ISSUER1_PID));

		_liferayJWTBearerGrantHandler.updated(
			_INSTANCE1_ISSUER1_PID,
			_buildDictionary(1, "instance1_issuer1_edit"));

		Assert.assertEquals(
			_getIssuersSet(1), SetUtil.fromArray("instance1_issuer1_edit"));

		_liferayJWTBearerGrantHandler.deleted(_INSTANCE1_ISSUER1_PID);

		Assert.assertEquals(_getIssuersSet(1), Collections.emptySet());
	}

	@Test
	public void testUpdateExistingSystemConfiguration()
		throws ConfigurationException {

		_liferayJWTBearerGrantHandler.updated(
			_SYSTEM_ISSUER1_PID, _buildDictionary(0, "system_issuer1"));

		Assert.assertEquals(
			_getIssuersSet(1), SetUtil.fromArray("system_issuer1"));

		_liferayJWTBearerGrantHandler.updated(
			_SYSTEM_ISSUER1_PID, _buildDictionary(0, "system_issuer1_edit"));

		Assert.assertEquals(
			_getIssuersSet(1), SetUtil.fromArray("system_issuer1_edit"));

		_liferayJWTBearerGrantHandler.deleted(_SYSTEM_ISSUER1_PID);

		Assert.assertEquals(_getIssuersSet(1), Collections.emptySet());
	}

	private Dictionary<String, Object> _buildDictionary(
		long companyId, String issuer) {

		return _buildDictionary(
			companyId, issuer, StringPool.BLANK, StringPool.BLANK);
	}

	private Dictionary<String, Object> _buildDictionary(
		long companyId, String issuer, String webKeySet, String authType) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"companyId", companyId
		).put(
			"oauth2.in.assertion.issuer", issuer
		).put(
			"oauth2.in.assertion.signature.json.web.key.set", webKeySet
		).put(
			"oauth2.in.assertion.user.auth.type", authType
		).build();
	}

	private Set<String> _getIssuersSet(long companyId) {
		Map<String, ?> jwsSignatureVerifiers =
			_liferayJWTBearerGrantHandler.getJwsSignatureVerifiers(companyId);

		return jwsSignatureVerifiers.keySet();
	}

	private static final String _INSTANCE1_ISSUER1_PID = "instance1_pid1";

	private static final String _INSTANCE2_ISSUER1_PID = "instance2_pid1";

	private static final String _SYSTEM_ISSUER1_PID = "system_pid1";

	private LiferayJWTBearerGrantHandler _liferayJWTBearerGrantHandler;

}