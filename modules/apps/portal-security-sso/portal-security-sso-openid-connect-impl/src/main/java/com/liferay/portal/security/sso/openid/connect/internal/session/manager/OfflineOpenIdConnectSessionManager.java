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
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.internal.AuthorizationServerMetadataResolver;
import com.liferay.portal.security.sso.openid.connect.internal.util.OpenIdConnectTokenRequestUtil;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientInformation;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = OfflineOpenIdConnectSessionManager.class
)
public class OfflineOpenIdConnectSessionManager {

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
			return true;
		}

		Date accessTokenExpirationDate =
			openIdConnectSession.getAccessTokenExpirationDate();

		long currentTime = System.currentTimeMillis();

		if (currentTime <=
				(accessTokenExpirationDate.getTime() -
					_tokenRefreshOffsetMillis)) {

			return false;
		}

		String key = String.valueOf(openIdConnectSessionId);
		String lockOwner = _generateLockOwner();

		Lock lock = _lockManager.lock(
			OpenIdConnectSession.class.getSimpleName(), key, lockOwner);

		if (!lockOwner.equals(lock.getOwner())) {
			return false;
		}

		AccessToken accessToken = _extendOpenIdConnectSession(
			openIdConnectSession);

		_lockManager.unlock(
			OpenIdConnectSession.class.getSimpleName(), key, lockOwner);

		if (accessToken == null) {
			return true;
		}

		return false;
	}

	public long startOpenIdConnectSession(
		String authServerWellKnownURI, String clientId, OIDCTokens oidcTokens,
		long userId) {

		OpenIdConnectSession openIdConnectSession =
			_openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				userId, authServerWellKnownURI, clientId);

		if (openIdConnectSession == null) {
			openIdConnectSession =
				_openIdConnectSessionLocalService.createOpenIdConnectSession(
					_counterLocalService.increment(
						OpenIdConnectSession.class.getName()));
		}

		_updateOpenIdConnectSession(
			oidcTokens.getAccessToken(), authServerWellKnownURI, clientId,
			oidcTokens.getIDTokenString(), oidcTokens.getRefreshToken(),
			openIdConnectSession, userId);

		return openIdConnectSession.getOpenIdConnectSessionId();
	}

	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		OpenIdConnectConfiguration openIdConnectConfiguration =
			ConfigurableUtil.createConfigurable(
				OpenIdConnectConfiguration.class, properties);

		if (openIdConnectConfiguration.tokenRefreshOffset() < 30) {
			throw new IllegalArgumentException(
				"Token refresh offset needs to be at least 30 seconds");
		}

		_tokenRefreshOffsetMillis =
			openIdConnectConfiguration.tokenRefreshOffset() * Time.SECOND;

		_tokenRefreshScheduledInterval =
			openIdConnectConfiguration.tokenRefreshScheduledInterval();

		if (!openIdConnectConfiguration.enabled() ||
			(_tokenRefreshScheduledInterval < 30)) {

			if (_openIdConnectMessageListener != null) {
				_schedulerEngineHelper.unregister(
					_openIdConnectMessageListener);

				_openIdConnectMessageListener = null;
			}

			return;
		}

		_openIdConnectMessageListener = new OpenIdConnectMessageListener(
			_lockManager);

		Trigger trigger = _triggerFactory.createTrigger(
			OpenIdConnectMessageListener.class.getName(),
			OpenIdConnectConstants.SERVICE_NAME, null, null,
			_tokenRefreshScheduledInterval, TimeUnit.SECOND);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			OpenIdConnectMessageListener.class.getName(), trigger);

		_schedulerEngineHelper.register(
			_openIdConnectMessageListener, schedulerEntry,
			DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		if (_openIdConnectMessageListener != null) {
			_schedulerEngineHelper.unregister(_openIdConnectMessageListener);
		}
	}

	private AccessToken _extendOpenIdConnectSession(
		OpenIdConnectSession openIdConnectSession) {

		if (Validator.isNull(openIdConnectSession.getRefreshToken())) {
			_openIdConnectSessionLocalService.deleteOpenIdConnectSession(
				openIdConnectSession);

			return null;
		}

		RefreshToken refreshToken = new RefreshToken(
			openIdConnectSession.getRefreshToken());

		OAuthClientEntry oAuthClientEntry =
			_oAuthClientEntryLocalService.fetchOAuthClientEntry(
				openIdConnectSession.getCompanyId(),
				openIdConnectSession.getAuthServerWellKnownURI(),
				openIdConnectSession.getClientId());

		if (oAuthClientEntry == null) {
			_openIdConnectSessionLocalService.deleteOpenIdConnectSession(
				openIdConnectSession);

			return null;
		}

		try {
			OIDCTokens oidcTokens = OpenIdConnectTokenRequestUtil.request(
				OIDCClientInformation.parse(
					JSONObjectUtils.parse(oAuthClientEntry.getInfoJSON())),
				_authorizationServerMetadataResolver.
					resolveOIDCProviderMetadata(
						openIdConnectSession.getAuthServerWellKnownURI()),
				refreshToken, oAuthClientEntry.getTokenRequestParametersJSON());

			_updateOpenIdConnectSession(
				oidcTokens.getAccessToken(), openIdConnectSession,
				oidcTokens.getRefreshToken());

			return oidcTokens.getAccessToken();
		}
		catch (Exception exception) {
			_openIdConnectSessionLocalService.deleteOpenIdConnectSession(
				openIdConnectSession);
		}

		return null;
	}

	private String _generateLockOwner() {
		ClusterNode clusterNode = _clusterExecutor.getLocalClusterNode();

		Thread currentThread = Thread.currentThread();

		if (clusterNode != null) {
			return clusterNode.getClusterNodeId() + currentThread.getName();
		}

		return currentThread.getName();
	}

	private void _updateOpenIdConnectSession(
		AccessToken accessToken, OpenIdConnectSession openIdConnectSession,
		RefreshToken refreshToken) {

		openIdConnectSession.setAccessToken(accessToken.toJSONString());

		if (refreshToken != null) {
			openIdConnectSession.setRefreshToken(refreshToken.toString());
		}

		long currentTime = System.currentTimeMillis();

		openIdConnectSession.setModifiedDate(new Date(currentTime));

		if (accessToken.getLifetime() > 0) {
			openIdConnectSession.setAccessTokenExpirationDate(
				new Date(
					currentTime + (accessToken.getLifetime() * Time.SECOND)));
		}
		else {
			openIdConnectSession.setAccessTokenExpirationDate(
				new Date(currentTime + Time.HOUR));
		}

		_openIdConnectSessionLocalService.updateOpenIdConnectSession(
			openIdConnectSession);
	}

	private void _updateOpenIdConnectSession(
		AccessToken accessToken, String authServerWellKnownURI, String clientId,
		String idTokenString, RefreshToken refreshToken,
		OpenIdConnectSession openIdConnectSession, long userId) {

		openIdConnectSession.setUserId(userId);
		openIdConnectSession.setAuthServerWellKnownURI(authServerWellKnownURI);
		openIdConnectSession.setClientId(clientId);
		openIdConnectSession.setIdToken(idTokenString);

		_updateOpenIdConnectSession(
			accessToken, openIdConnectSession, refreshToken);
	}

	@Reference
	private AuthorizationServerMetadataResolver
		_authorizationServerMetadataResolver;

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private LockManager _lockManager;

	@Reference
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	private volatile OpenIdConnectMessageListener _openIdConnectMessageListener;

	@Reference
	private OpenIdConnectSessionLocalService _openIdConnectSessionLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private volatile long _tokenRefreshOffsetMillis = 60 * Time.SECOND;
	private volatile int _tokenRefreshScheduledInterval = 480;

	@Reference
	private TriggerFactory _triggerFactory;

	private class OpenIdConnectMessageListener extends BaseMessageListener {

		public OpenIdConnectMessageListener(LockManager lockManager) {
			_lockManager = lockManager;
		}

		@Override
		protected void doReceive(Message message) throws Exception {
			List<OpenIdConnectSession> openIdConnectSessions =
				_openIdConnectSessionLocalService.
					getAccessTokenExpirationDateOpenIdConnectSessions(
						new Date(
							System.currentTimeMillis() +
								_tokenRefreshOffsetMillis),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (OpenIdConnectSession openIdConnectSession :
					openIdConnectSessions) {

				String key = String.valueOf(
					openIdConnectSession.getOpenIdConnectSessionId());
				String lockOwner = _generateLockOwner();

				Lock lock = _lockManager.lock(
					OpenIdConnectSession.class.getSimpleName(), key, lockOwner);

				if (!lockOwner.equals(lock.getOwner())) {
					continue;
				}

				_extendOpenIdConnectSession(openIdConnectSession);

				_lockManager.unlock(
					OpenIdConnectSession.class.getSimpleName(), key, lockOwner);
			}
		}

		private final LockManager _lockManager;

	}

}