/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.helper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.opensaml.saml.common.messaging.context.SAMLBindingContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Bowerman
 */
@Component(service = RelayStateHelper.class)
public class RelayStateHelper {

	public String getRelayState(SAMLBindingContext samlBindingContext) {
		String relayState = _relayStates.remove(
			samlBindingContext.getRelayState());

		if ((relayState == null) && _log.isWarnEnabled()) {
			_log.warn(
				"Unable to get relay state for key " +
					samlBindingContext.getRelayState());
		}

		return relayState;
	}

	public void setRelayState(
		SAMLBindingContext samlBindingContext, String relayState) {

		if (relayState == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Relay state is null. Setting blank relay state instead.");

				relayState = StringPool.BLANK;
			}
		}

		String uuid = _portalUUID.generate();

		_relayStates.put(uuid, relayState);

		samlBindingContext.setRelayState(uuid);
	}

	@Activate
	protected void activate() {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

		cacheBuilder.expireAfterWrite(10, TimeUnit.MINUTES);

		Cache<String, String> cache = cacheBuilder.build();

		_relayStates = cache.asMap();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RelayStateHelper.class);

	@Reference
	private PortalUUID _portalUUID;

	private ConcurrentMap<String, String> _relayStates;

}