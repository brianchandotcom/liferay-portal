/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.cache.test.util.TestPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

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
	public void testResolveOIDCProviderMetadata() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_authorizationServerMetadataResolver,
			"_oidcProviderMetadataPortalCache", _portalCache);

		String authServerWellKnownURI =
			"https://accounts.google.com/.well-known/openid-configuration";
		long companyId = RandomTestUtil.randomLong();
		int metadataCacheInSeconds = 90;
		long oAuthClientEntryId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			_authorizationServerMetadataResolver.resolveOIDCProviderMetadata(
				authServerWellKnownURI, companyId, metadataCacheInSeconds,
				oAuthClientEntryId),
			_authorizationServerMetadataResolver.resolveOIDCProviderMetadata(
				authServerWellKnownURI, companyId, metadataCacheInSeconds,
				oAuthClientEntryId));
	}

	private final AuthorizationServerMetadataResolver
		_authorizationServerMetadataResolver =
			new AuthorizationServerMetadataResolver();
	private final PortalCache<String, OIDCProviderMetadata> _portalCache =
		new TestPortalCache<>(
			AuthorizationServerMetadataResolverTest.class.getName());

}