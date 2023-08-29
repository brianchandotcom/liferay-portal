/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.helper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.saml.helper.RelayStateHelper;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Bowerman
 */
@Component(service = RelayStateHelper.class)
public class RelayStateHelperImpl implements RelayStateHelper {

	public String getRedirectFromRelayStateToken(String relayStateToken) {
		return _relayStateTokensToRedirects.get(relayStateToken);
	}

	public String getRelayStateTokenFromRedirect(String redirect) {
		String relayStateToken = _redirectsToRelayStateTokens.get(redirect);

		if (relayStateToken != null) {
			String mappedRedirect = _relayStateTokensToRedirects.get(
				relayStateToken);

			if (Objects.equals(redirect, mappedRedirect)) {
				return relayStateToken;
			}

			_redirectsToRelayStateTokens.remove(redirect);
			_relayStateTokensToRedirects.remove(relayStateToken);
		}

		relayStateToken = _portalUUID.generate();

		_redirectsToRelayStateTokens.put(redirect, relayStateToken);

		_relayStateTokensToRedirects.put(relayStateToken, redirect);

		return relayStateToken;
	}

	@Activate
	protected void activate() {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

		cacheBuilder.expireAfterAccess(10, TimeUnit.MINUTES);

		Cache<String, String> redirectsToRelayStateTokensCache =
			cacheBuilder.build();
		Cache<String, String> relayStateTokensToRedirectsCache =
			cacheBuilder.build();

		_redirectsToRelayStateTokens = redirectsToRelayStateTokensCache.asMap();
		_relayStateTokensToRedirects = relayStateTokensToRedirectsCache.asMap();
	}

	@Reference
	private PortalUUID _portalUUID;

	private ConcurrentMap<String, String> _redirectsToRelayStateTokens;
	private ConcurrentMap<String, String> _relayStateTokensToRedirects;

}