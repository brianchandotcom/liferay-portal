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

package com.liferay.lcs.runnable;

import com.liferay.lcs.InvalidLCSClusterEntryTokenException;
import com.liferay.lcs.NoLCSClusterEntryTokenException;
import com.liferay.lcs.advisor.LCSClusterEntryTokenAdvisor;
import com.liferay.lcs.exception.LCSExceptionHandler;
import com.liferay.lcs.exception.LCSHandshakeException;
import com.liferay.lcs.oauth.OAuthUtil;
import com.liferay.lcs.rest.LCSClusterEntryToken;
import com.liferay.lcs.rest.NoSuchLCSSubscriptionEntryException;
import com.liferay.lcs.util.ClusterNodeUtil;
import com.liferay.lcs.util.LCSConnectionManagerUtil;
import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSPortletPreferencesUtil;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.lcs.util.PortletPropsValues;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Mladen Cikara
 */
public class LCSConnectorRunnable implements Runnable {

	public LCSConnectorRunnable() {
		if (_log.isTraceEnabled()) {
			_log.trace("Initialized " + this);
		}
	}

	@Override
	public void run() {
		LCSUtil.processLCSPortletState(
			LCSPortletState.valueOf(
				LCSPortletPreferencesUtil.getValue(
					"lcsPortletState", LCSPortletState.NOT_REGISTERED.name())));

		while (!_stop) {
			try {
				Future<?> future = _activateLCS();

				future.get();

				break;
			}
			catch (Exception e) {
				if (e instanceof ClusterException ||
					e instanceof ExecutionException ||
					e instanceof InvalidLCSClusterEntryTokenException ||
					e instanceof JSONWebServiceInvocationException ||
					e instanceof JSONWebServiceTransportException ||
					e instanceof NoLCSClusterEntryTokenException ||
					e instanceof NoSuchLCSSubscriptionEntryException) {

					if (_log.isDebugEnabled()) {
						_log.debug(e.getMessage(), e);
					}
					else {
						if (_log.isInfoEnabled() &&
							Validator.isNotNull(e.getMessage())) {

							_log.info(e.getMessage());
						}
					}

					if (e instanceof JSONWebServiceInvocationException) {
						JSONWebServiceInvocationException jsonwsie =
							(JSONWebServiceInvocationException)e;

						if (jsonwsie.getStatus() ==
								HttpServletResponse.SC_NOT_ACCEPTABLE) {

							LCSPortletPreferencesUtil.removeCredentials();
						}
					}
					else if (e instanceof NoSuchLCSSubscriptionEntryException) {
						if (_log.isWarnEnabled()) {
							ResourceBundle resourceBundle =
								ResourceBundleUtil.getBundle(
									"content.Language", getClass());

							_log.warn(
								ResourceBundleUtil.getString(
									resourceBundle,
									"exceeded-subscription-number"));
						}
					}
					else if (OAuthUtil.hasOAuthTokenRejectedException(e)) {
						LCSPortletPreferencesUtil.removeCredentials();

						LCSUtil.processLCSPortletState(
							LCSPortletState.NO_CONNECTION);

						if (_log.isWarnEnabled()) {
							_log.warn("A new OAuth token is required");
						}
					}

					if (_log.isInfoEnabled()) {
						_log.info("LCS portlet is not connected");
					}
				}
				else if (e instanceof ExecutionException) {
					if (!(LCSExceptionHandler.getRootCause(e) instanceof
						LCSHandshakeException)) {

						if (_log.isInfoEnabled()) {
							_log.info(
								"Terminating thread because of an " +
									"unrecoverable exception");
						}

						break;
					}
				}
				else {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Terminating thread because LCS portlet is not " +
								"connected",
							e);
					}

					break;
				}

				try {
					if (_log.isInfoEnabled()) {
						_log.info("Retry in 60 seconds");
					}

					Thread.sleep(60000);
				}
				catch (InterruptedException ie) {
					if (_log.isInfoEnabled()) {
						_log.info("Interrupted while waiting for connection");
					}
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Thread terminated");
		}
	}

	public void setLCSClusterEntryTokenAdvisor(
		LCSClusterEntryTokenAdvisor lcsClusterEntryTokenAdvisor) {

		_lcsClusterEntryTokenAdvisor = lcsClusterEntryTokenAdvisor;
	}

	public void stop() {
		_stop = true;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		if (_log.isTraceEnabled()) {
			_log.trace("Finalized " + this);
		}
	}

	private Future<?> _activateLCS() throws Exception {
		_checkDefaultPortletPreferences();

		LCSClusterEntryToken lcsClusterEntryToken =
			_lcsClusterEntryTokenAdvisor.processLCSCLusterEntryTokenFile();

		if (lcsClusterEntryToken == null) {
			LCSPortletPreferencesUtil.removeCredentials();

			throw new NoLCSClusterEntryTokenException(
				"Unable to get LCS cluster entry token");
		}

		if (LCSPortletPreferencesUtil.isCredentialsSet()) {
			_lcsClusterEntryTokenAdvisor.checkLCSClusterEntryTokenPreferences(
				lcsClusterEntryToken);
		}

		_lcsClusterEntryTokenAdvisor.processLCSClusterEntryToken();

		LCSUtil.setUpJSONWebServiceClientCredentials();

		if (!LCSUtil.isLCSPortletAuthorized()) {
			_lcsClusterEntryTokenAdvisor.deleteLCSCLusterEntryTokenFile();

			LCSPortletPreferencesUtil.removeCredentials();

			throw new InvalidLCSClusterEntryTokenException(
				"Invalid LCS activation token file credentials");
		}

		_lcsClusterEntryTokenAdvisor.checkLCSClusterEntryTokenId(
			lcsClusterEntryToken.getLcsClusterEntryTokenId());

		if (!LCSUtil.isLCSClusterNodeRegistered()) {
			ClusterNodeUtil.registerClusterNode(
				lcsClusterEntryToken.getLcsClusterEntryId());
		}
		else {
			_lcsClusterEntryTokenAdvisor.checkLCSClusterEntry(
				lcsClusterEntryToken);
		}

		LCSUtil.processLCSPortletState(LCSPortletState.NO_CONNECTION);

		return LCSConnectionManagerUtil.start();
	}

	private void _checkDefaultPortletPreferences() {
		try {
			javax.portlet.PortletPreferences jxPortletPreferences =
				LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

			if (Validator.isNull(
					jxPortletPreferences.getValue(
						LCSConstants.METRICS_LCS_SERVICE_ENABLED, null)) &&
				Validator.isNotNull(
					PortletPropsValues.METRICS_LCS_SERVICE_ENABLED)) {

				LCSPortletPreferencesUtil.store(
					LCSConstants.METRICS_LCS_SERVICE_ENABLED,
					PortletPropsValues.METRICS_LCS_SERVICE_ENABLED);
			}

			if (Validator.isNull(LCSUtil.getPortalPropertiesBlacklist()) &&
				Validator.isNotNull(
					PortletPropsValues.PORTAL_PROPERTIES_BLACKLIST)) {

				LCSPortletPreferencesUtil.store(
					LCSConstants.PORTAL_PROPERTIES_BLACKLIST,
					PortletPropsValues.PORTAL_PROPERTIES_BLACKLIST);
			}
		}
		catch (Exception e) {
			_log.error("Unable to check default portlet", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSConnectorRunnable.class);

	private LCSClusterEntryTokenAdvisor _lcsClusterEntryTokenAdvisor;
	private boolean _stop;

}