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
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.internal.scheduler.OpenIdConnectTokenRefreshScheduler;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(immediate = true, service = OfflineOpenIdConnectSessionManager.class)
public class OfflineOpenIdConnectSessionManager {

	public void endOpenIdConnectSession(long openIdConnectSessionId) {
		try {
			_openIdConnectSessionLocalService.deleteOpenIdConnectSession(
				openIdConnectSessionId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

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

	public void extendOpenIdConnectSession(
		long openIdConnectSessionId, OIDCTokens oidcTokens) {

		OpenIdConnectSession openIdConnectSession = _getOpenIdConnectSession(
			openIdConnectSessionId);

		if (openIdConnectSession == null) {
			return;
		}

		AccessToken oldAccessToken = _getAccessToken(openIdConnectSession);

		AccessToken accessToken = oidcTokens.getAccessToken();

		_updateOpenIdConnectSession(
			accessToken, oidcTokens.getRefreshToken(), openIdConnectSession);

		if ((openIdConnectSession.getRefreshToken() != null) &&
			(oldAccessToken.getLifetime() != accessToken.getLifetime())) {

			try {
				_openIdConnectTokenRefreshScheduler.reschedule(
					accessToken.getLifetime(),
					openIdConnectSession.getModifiedDate(),
					openIdConnectSession.getOpenIdConnectSessionId());
			}
			catch (SchedulerException schedulerException) {
				if (_log.isWarnEnabled()) {
					_log.warn(schedulerException, schedulerException);
				}
			}
		}
	}

	public AccessToken getAccessToken(long openIdConnectSessionId) {
		OpenIdConnectSession openIdConnectSession = _getOpenIdConnectSession(
			openIdConnectSessionId);

		if (openIdConnectSession == null) {
			return null;
		}

		return _getAccessToken(openIdConnectSession);
	}

	public OIDCTokens getOIDCTokens(long openIdConnectSessionId) {
		OpenIdConnectSession openIdConnectSession = _getOpenIdConnectSession(
			openIdConnectSessionId);

		if (openIdConnectSession == null) {
			return null;
		}

		AccessToken accessToken;

		try {
			accessToken = AccessToken.parse(
				JSONObjectUtils.parse(openIdConnectSession.getAccessToken()));
		}
		catch (ParseException parseException) {
			if (_log.isWarnEnabled()) {
				_log.warn(parseException, parseException);
			}

			return null;
		}

		if (openIdConnectSession.getRefreshToken() == null) {
			if (openIdConnectSession.getIdToken() == null) {
				return new OIDCTokens(accessToken, null);
			}

			return new OIDCTokens(
				openIdConnectSession.getIdToken(), accessToken, null);
		}

		RefreshToken refreshToken = new RefreshToken(
			openIdConnectSession.getRefreshToken());

		if (openIdConnectSession.getIdToken() == null) {
			return new OIDCTokens(accessToken, refreshToken);
		}

		return new OIDCTokens(
			openIdConnectSession.getIdToken(), accessToken, refreshToken);
	}

	public String getProviderName(long openIdConnectSessionId) {
		OpenIdConnectSession openIdConnectSession = _getOpenIdConnectSession(
			openIdConnectSessionId);

		if (openIdConnectSession == null) {
			return null;
		}

		return openIdConnectSession.getProviderName();
	}

	public RefreshToken getRefreshToken(long openIdConnectSessionId) {
		OpenIdConnectSession openIdConnectSession = _getOpenIdConnectSession(
			openIdConnectSessionId);

		if (openIdConnectSession == null) {
			return null;
		}

		if (openIdConnectSession.getRefreshToken() == null) {
			return null;
		}

		return new RefreshToken(openIdConnectSession.getRefreshToken());
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
		long lifeTime = accessToken.getLifetime() * Time.SECOND;
		Date modifiedDate = openIdConnectSession.getModifiedDate();

		if ((currentTime - modifiedDate.getTime()) < lifeTime) {
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
					openIdConnectSession.getModifiedDate(),
					openIdConnectSession.getOpenIdConnectSessionId());
			}
			catch (SchedulerException schedulerException) {
				if (_log.isWarnEnabled()) {
					_log.warn(schedulerException, schedulerException);
				}
			}
		}

		return openIdConnectSession.getOpenIdConnectSessionId();
	}

	@Deactivate
	protected void deactivate() throws Exception {
		for (OpenIdConnectSession openIdConnectSession :
				_openIdConnectSessionLocalService.getOpenIdConnectSessions(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			_openIdConnectSessionLocalService.deleteOpenIdConnectSession(
				openIdConnectSession);
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

	private OpenIdConnectSession _getOpenIdConnectSession(
		long openIdConnectSessionId) {

		OpenIdConnectSession openIdConnectSession =
			_openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				openIdConnectSessionId);

		if (openIdConnectSession == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find openIdConnectSession with Id " +
						openIdConnectSessionId);
			}
		}

		return openIdConnectSession;
	}

	private void _updateOpenIdConnectSession(
		AccessToken accessToken, RefreshToken refreshToken,
		OpenIdConnectSession openIdConnectSession) {

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
			accessToken, refreshToken, openIdConnectSession);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OfflineOpenIdConnectSessionManager.class);

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private OpenIdConnectSessionLocalService _openIdConnectSessionLocalService;

	@Reference
	private OpenIdConnectTokenRefreshScheduler
		_openIdConnectTokenRefreshScheduler;

}