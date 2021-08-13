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

package com.liferay.portal.security.sso.openid.connect.internal.messaging;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.internal.constants.OpenIdConnectDestinationNames;
import com.liferay.portal.security.sso.openid.connect.internal.session.manager.OfflineOpenIdConnectSessionManager;
import com.liferay.portal.security.sso.openid.connect.internal.util.OpenIdConnectTokenRequestUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;
import com.liferay.portal.security.sso.openid.connect.session.manager.OpenIdConnectSessionManager;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	immediate = true,
	property = "destination.name=" + OpenIdConnectDestinationNames.OPENID_CONNECT_TOKEN_REFRESH,
	service = MessageListener.class
)
public class OpenIdConnectTokenRefreshMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createSerialDestinationConfiguration(
				OpenIdConnectDestinationNames.OPENID_CONNECT_TOKEN_REFRESH);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build();

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination, dictionary);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			Destination destination = _bundleContext.getService(
				_serviceRegistration.getReference());

			_serviceRegistration.unregister();

			destination.destroy();
		}

		_bundleContext = null;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_requestTokenRefresh((int)message.getPayload());
	}

	private void _requestTokenRefresh(long openIdConnectSessionId)
		throws Exception {

		OpenIdConnectSession openIdConnectSession = null;

		try {
			openIdConnectSession =
				_openIdConnectSessionLocalService.getOpenIdConnectSession(
					openIdConnectSessionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find OpenId Connect session " +
						openIdConnectSessionId,
					portalException);
			}

			return;
		}

		if (openIdConnectSession.getRefreshToken() == null) {
			return;
		}

		OfflineOpenIdConnectSessionManager offlineOpenIdConnectSessionManager =
			(OfflineOpenIdConnectSessionManager)_openIdConnectSessionManager;

		RefreshToken refreshToken = new RefreshToken(
			openIdConnectSession.getRefreshToken());

		OIDCTokens oidcTokens = OpenIdConnectTokenRequestUtil.request(
			_openIdConnectProviderRegistry.findOpenIdConnectProvider(
				CompanyThreadLocal.getCompanyId(),
				openIdConnectSession.getProviderName()),
			refreshToken);

		offlineOpenIdConnectSessionManager.extendOpenIdConnectSession(
			openIdConnectSessionId, oidcTokens);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectTokenRefreshMessageListener.class);

	private volatile BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private OpenIdConnectProviderRegistry
		<OIDCClientMetadata, OIDCProviderMetadata>
			_openIdConnectProviderRegistry;

	@Reference
	private OpenIdConnectSessionLocalService _openIdConnectSessionLocalService;

	@Reference
	private OpenIdConnectSessionManager _openIdConnectSessionManager;

	private ServiceRegistration<Destination> _serviceRegistration;

}