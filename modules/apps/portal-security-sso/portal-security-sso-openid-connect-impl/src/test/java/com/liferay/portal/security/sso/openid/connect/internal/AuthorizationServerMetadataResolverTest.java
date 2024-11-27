/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.cache.test.util.TestPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Istvan Sajtos
 */
public class AuthorizationServerMetadataResolverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMetadataIsCached() throws Exception {
		Field field =
			AuthorizationServerMetadataResolver.class.getDeclaredField(
				"_oidcProviderMetadataPortalCache");

		field.setAccessible(true);
		field.set(_authorizationServerMetadataResolver, _portalCache);

		String authServerWellKnownURI =
			"https://accounts.google.com/.well-known/openid-configuration";

		int metadataCacheInSecs = 90;
		long oAuthClientEntryId = RandomTestUtil.randomLong();

		OIDCProviderMetadata oidcProviderMetadata1 =
			_authorizationServerMetadataResolver.resolveOIDCProviderMetadata(
				authServerWellKnownURI, metadataCacheInSecs,
				oAuthClientEntryId);

		OIDCProviderMetadata oidcProviderMetadata2 =
			_authorizationServerMetadataResolver.resolveOIDCProviderMetadata(
				authServerWellKnownURI, metadataCacheInSecs,
				oAuthClientEntryId);

		Assert.assertTrue(oidcProviderMetadata1 == oidcProviderMetadata2);
	}

	private final AuthorizationServerMetadataResolver
		_authorizationServerMetadataResolver =
			new AuthorizationServerMetadataResolver();
	private final PortalCache<String, OIDCProviderMetadata> _portalCache =
		new TestPortalCache<>(
			AuthorizationServerMetadataResolverTest.class.getName());

}