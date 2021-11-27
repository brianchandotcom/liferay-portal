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

package com.liferay.portal.security.sso.openid.connect.internal.session.manager;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectProviderRegistry;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.internal.constants.OpenIdConnectDestinationNames;
import com.liferay.portal.security.sso.openid.connect.internal.scheduler.OpenIdConnectTokenRefreshScheduler;
import com.liferay.portal.security.sso.openid.connect.internal.util.OpenIdConnectTokenRequestUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.util.Date;
import java.util.Dictionary;

import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(immediate = true, service = OfflineOpenIdConnectSessionManager.class)
public class OfflineOpenIdConnectSessionManager {

	public void endOpenIdConnectSession(long openIdConnectSessionId) {
		OpenIdConnectSession openIdConnectSession =
			_openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				openIdConnectSessionId);

		if (openIdConnectSession == null) {
			return;
		}

		_openIdConnectSessionLocalService.deleteOpenIdConnectSession(
			openIdConnectSession);

		try {
			_openIdConnectTokenRefreshScheduler.unschedule(
				openIdConnectSessionId);
		}
		catch (SchedulerException schedulerException) {
			if (_log.isWarnEnabled()) {
				_log.warn(schedulerException, schedulerException);
			}
		}
	}

	public boolean isOpenIdConnectSession(HttpSession httpSession) {
		if (httpSession == null) {
			return false;
		}

		Long openIdConnectSessionId = (Long)httpSession.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION_ID);

		if (openIdConnectSessionId != null) {
			return true;
		}

		return false;
	}

	public boolean isOpenIdConnectSessionExpired(HttpSession httpSession) {
		Long openIdConnectSessionId = (Long)httpSession.getAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION_ID);

		if (openIdConnectSessionId == null) {
			return true;
		}

		OpenIdConnectSession openIdConnectSession =
			_openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				openIdConnectSessionId);

		if (openIdConnectSession == null) {
			endOpenIdConnectSession(openIdConnectSessionId);

			return true;
		}

		AccessToken accessToken = _getAccessToken(openIdConnectSession);

		long currentTime = System.currentTimeMillis();
		long lifetime = accessToken.getLifetime() * Time.SECOND;
		Date modifiedDate = openIdConnectSession.getModifiedDate();

		if ((currentTime - modifiedDate.getTime()) < lifetime) {
			return false;
		}

		return true;
	}

	public long startOpenIdConnectSession(
		OIDCTokens oidcTokens, String providerName) {

		OpenIdConnectSession openIdConnectSession =
			_openIdConnectSessionLocalService.createOpenIdConnectSession(
				_counterLocalService.increment(
					OpenIdConnectSession.class.getName()));

		AccessToken accessToken = oidcTokens.getAccessToken();

		_updateOpenIdConnectSession(
			accessToken, oidcTokens.getIDTokenString(),
			oidcTokens.getRefreshToken(), openIdConnectSession, providerName);

		if (openIdConnectSession.getRefreshToken() != null) {
			try {
				_openIdConnectTokenRefreshScheduler.schedule(
					accessToken.getLifetime(),
					openIdConnectSession.getOpenIdConnectSessionId(),
					openIdConnectSession.getModifiedDate());
			}
			catch (SchedulerException schedulerException) {
				if (_log.isWarnEnabled()) {
					_log.warn(schedulerException, schedulerException);
				}
			}
		}

		return openIdConnectSession.getOpenIdConnectSessionId();
	}

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

		_serviceRegistration1 = bundleContext.registerService(
			Destination.class, destination, dictionary);

		_serviceRegistration2 = bundleContext.registerService(
			MessageListener.class, new OpenIdConnectMessageListener(),
			dictionary);
	}

	@Deactivate
	protected void deactivate() throws Exception {
		if (_serviceRegistration1 != null) {
			Destination destination = _bundleContext.getService(
				_serviceRegistration1.getReference());

			_serviceRegistration1.unregister();

			destination.destroy();
		}

		if (_serviceRegistration2 != null) {
			_serviceRegistration2.unregister();
		}

		_bundleContext = null;

		for (OpenIdConnectSession openIdConnectSession :
				_openIdConnectSessionLocalService.getOpenIdConnectSessions(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			_openIdConnectSessionLocalService.deleteOpenIdConnectSession(
				openIdConnectSession);
		}
	}

	private void _extendOpenIdConnectSession(
			OpenIdConnectSession openIdConnectSession)
		throws Exception {

		if (openIdConnectSession.getRefreshToken() == null) {
			return;
		}

		RefreshToken refreshToken = new RefreshToken(
			openIdConnectSession.getRefreshToken());

		OIDCTokens oidcTokens = OpenIdConnectTokenRequestUtil.request(
			_openIdConnectProviderRegistry.findOpenIdConnectProvider(
				CompanyThreadLocal.getCompanyId(),
				openIdConnectSession.getProviderName()),
			refreshToken);

		AccessToken oldAccessToken = _getAccessToken(openIdConnectSession);

		AccessToken accessToken = oidcTokens.getAccessToken();

		_updateOpenIdConnectSession(
			accessToken, openIdConnectSession, oidcTokens.getRefreshToken());

		if ((openIdConnectSession.getRefreshToken() != null) &&
			(oldAccessToken.getLifetime() != accessToken.getLifetime())) {

			try {
				_openIdConnectTokenRefreshScheduler.reschedule(
					accessToken.getLifetime(),
					openIdConnectSession.getOpenIdConnectSessionId(),
					openIdConnectSession.getModifiedDate());
			}
			catch (SchedulerException schedulerException) {
				if (_log.isWarnEnabled()) {
					_log.warn(schedulerException, schedulerException);
				}
			}
		}
	}

	private AccessToken _getAccessToken(
		OpenIdConnectSession openIdConnectSession) {

		try {
			return AccessToken.parse(
				JSONObjectUtils.parse(openIdConnectSession.getAccessToken()));
		}
		catch (ParseException parseException) {
			if (_log.isWarnEnabled()) {
				_log.warn(parseException, parseException);
			}

			return null;
		}
	}

	private void _updateOpenIdConnectSession(
		AccessToken accessToken, OpenIdConnectSession openIdConnectSession,
		RefreshToken refreshToken) {

		openIdConnectSession.setAccessToken(accessToken.toJSONString());

		if (refreshToken != null) {
			openIdConnectSession.setRefreshToken(refreshToken.toString());
		}

		openIdConnectSession.setModifiedDate(new Date());

		_openIdConnectSessionLocalService.updateOpenIdConnectSession(
			openIdConnectSession);
	}

	private void _updateOpenIdConnectSession(
		AccessToken accessToken, String idTokenString,
		RefreshToken refreshToken, OpenIdConnectSession openIdConnectSession,
		String providerName) {

		openIdConnectSession.setIdToken(idTokenString);
		openIdConnectSession.setProviderName(providerName);

		_updateOpenIdConnectSession(
			accessToken, openIdConnectSession, refreshToken);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OfflineOpenIdConnectSessionManager.class);

	private volatile BundleContext _bundleContext;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private OpenIdConnectProviderRegistry
		<OIDCClientMetadata, OIDCProviderMetadata>
			_openIdConnectProviderRegistry;

	@Reference
	private OpenIdConnectSessionLocalService _openIdConnectSessionLocalService;

	@Reference
	private OpenIdConnectTokenRefreshScheduler
		_openIdConnectTokenRefreshScheduler;

	private ServiceRegistration<Destination> _serviceRegistration1;
	private ServiceRegistration<MessageListener> _serviceRegistration2;

	private class OpenIdConnectMessageListener extends BaseMessageListener {

		protected void doReceive(Message message) throws Exception {
			long openIdConnectSessionId = GetterUtil.getLong(
				message.getPayload());

			try {
				_extendOpenIdConnectSession(
					_openIdConnectSessionLocalService.getOpenIdConnectSession(
						openIdConnectSessionId));
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to get OpenId Connect session " +
							openIdConnectSessionId,
						portalException);
				}
			}
		}

	}

}