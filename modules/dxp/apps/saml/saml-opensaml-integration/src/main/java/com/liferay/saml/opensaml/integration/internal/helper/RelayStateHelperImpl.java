/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.helper;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.saml.helper.RelayStateHelper;

import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Bowerman
 */
@Component(service = RelayStateHelper.class)
public class RelayStateHelperImpl implements RelayStateHelper {

	@Override
	public String getRedirectFromRelayStateToken(String relayStateToken) {
		if (Validator.isNotNull(relayStateToken) &&
			relayStateToken.startsWith("RDR_")) {

			return _relayStateTokensToRedirectsPortalCache.get(relayStateToken);
		}

		return relayStateToken;
	}

	@Override
	public String getRelayStateTokenFromRedirect(String redirect) {
		String relayStateToken = _redirectsToRelayStateTokensPortalCache.get(
			redirect);

		if (relayStateToken != null) {
			String mappedRedirect = _relayStateTokensToRedirectsPortalCache.get(
				relayStateToken);

			if (Objects.equals(redirect, mappedRedirect)) {
				return relayStateToken;
			}

			_redirectsToRelayStateTokensPortalCache.remove(redirect);
			_relayStateTokensToRedirectsPortalCache.remove(relayStateToken);
		}

		relayStateToken = "RDR_" + PortalUUIDUtil.generate();

		_redirectsToRelayStateTokensPortalCache.put(redirect, relayStateToken);

		_relayStateTokensToRedirectsPortalCache.put(relayStateToken, redirect);

		return relayStateToken;
	}

	@Activate
	protected void activate() {
		_redirectsToRelayStateTokensPortalCache =
			(PortalCache<String, String>)_singleVMPool.getPortalCache(
				RelayStateHelperImpl.class.getName() +
					"_redirectsToRelayStateTokens");
		_relayStateTokensToRedirectsPortalCache =
			(PortalCache<String, String>)_singleVMPool.getPortalCache(
				RelayStateHelperImpl.class.getName() +
					"_relayStateTokensToRedirects");
	}

	private PortalCache<String, String> _redirectsToRelayStateTokensPortalCache;
	private PortalCache<String, String> _relayStateTokensToRedirectsPortalCache;

	@Reference
	private SingleVMPool _singleVMPool;

}