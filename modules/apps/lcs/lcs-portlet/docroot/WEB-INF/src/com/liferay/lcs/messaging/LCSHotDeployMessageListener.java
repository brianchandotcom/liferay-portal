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

package com.liferay.lcs.messaging;

import com.liferay.lcs.advisor.LCSClusterEntryTokenAdvisor;
import com.liferay.lcs.advisor.UptimeMonitoringAdvisor;
import com.liferay.lcs.InvalidLCSClusterEntryTokenException;
import com.liferay.lcs.NoLCSClusterEntryTokenException;
import com.liferay.lcs.oauth.OAuthUtil;
import com.liferay.lcs.rest.LCSClusterEntryToken;
import com.liferay.lcs.rest.NoSuchLCSSubscriptionEntryException;
import com.liferay.lcs.sigar.SigarNativeLoader;
import com.liferay.lcs.task.UptimeMonitoringTask;
import com.liferay.lcs.util.ClusterNodeUtil;
import com.liferay.lcs.util.LCSConnectionManagerUtil;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Validator;

import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Igor Beslic
 */
public class LCSHotDeployMessageListener extends HotDeployMessageListener {

	public LCSHotDeployMessageListener() {
		super("lcs-portlet");
	}

	public void setLCSClusterEntryTokenAdvisor(
		LCSClusterEntryTokenAdvisor lcsClusterEntryTokenAdvisor) {

		_lcsClusterEntryTokenAdvisor = lcsClusterEntryTokenAdvisor;
	}

	public void setUptimeMonitoringAdvisor(
		UptimeMonitoringAdvisor uptimeMonitoringAdvisor) {

		_uptimeMonitoringAdvisor = uptimeMonitoringAdvisor;
	}

	@Override
	protected void onDeploy(Message message) {
		try {
			SigarNativeLoader.load();

			boolean hasLCSClusterEntryToken = false;
			boolean hasStoredCredentials = false;

			LCSUtil.checkDefaultPortletPreferences();

			LCSClusterEntryToken lcsClusterEntryToken =
				_lcsClusterEntryTokenAdvisor.processLCSCLusterEntryTokenFile();

			if (lcsClusterEntryToken != null) {
				hasLCSClusterEntryToken = true;
			}

			if (LCSUtil.getCredentialsStatus() == LCSUtil.CREDENTIALS_SET) {
				hasStoredCredentials = true;
			}

			if (hasLCSClusterEntryToken && hasStoredCredentials) {
				_lcsClusterEntryTokenAdvisor.
					checkLCSClusterEntryTokenPreferences(lcsClusterEntryToken);
			}
			else if (!hasLCSClusterEntryToken && !hasStoredCredentials) {
				LCSUtil.sendServiceAvailabilityNotification(
					LCSPortletState.NO_CONNECTION);

				return;
			}

			if (hasLCSClusterEntryToken) {
				_lcsClusterEntryTokenAdvisor.processLCSClusterEntryToken();
			}

			LCSUtil.setUpJSONWebServiceClientCredentials();

			if (!LCSUtil.isLCSPortletAuthorized()) {
				if (hasLCSClusterEntryToken) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"LCS activation token file credentials are " +
								"invalid. Deleting the file.");
					}

					_lcsClusterEntryTokenAdvisor.
						deleteLCSCLusterEntryTokenFile();
				}

				LCSUtil.removeCredentials();

				LCSUtil.sendServiceAvailabilityNotification(
					LCSPortletState.NO_CONNECTION);

				return;
			}

			if (hasLCSClusterEntryToken) {
				_lcsClusterEntryTokenAdvisor.checkLCSClusterEntryTokenId(
					lcsClusterEntryToken.getLcsClusterEntryTokenId());
			}

			if (!LCSUtil.isLCSClusterNodeRegistered()) {
				if (!hasStoredCredentials) {
					String siblingKey = ClusterNodeUtil.registerClusterNode(
						lcsClusterEntryToken.getLcsClusterEntryId());

					ClusterNodeUtil.registerUnregisteredClusterNodes(
						siblingKey);
				}
				else {
					ClusterNodeUtil.registerClusterNode();
				}
			}
			else {
				if (hasLCSClusterEntryToken) {
					_lcsClusterEntryTokenAdvisor.checkLCSClusterEntry(
						lcsClusterEntryToken);
				}
				else {
					LCSUtil.validateLCSClusterNodeLCSClusterEntry();
				}
			}

			LCSUtil.sendServiceAvailabilityNotification(
				LCSPortletState.NO_SUBSCRIPTION);

			LCSConnectionManagerUtil.start();
		}
		catch (Exception e) {
			if ((e instanceof ClusterException) ||
				(e instanceof InvalidLCSClusterEntryTokenException) ||
				(e instanceof JSONWebServiceTransportException) ||
				(e instanceof JSONWebServiceInvocationException) ||
				(e instanceof NoLCSClusterEntryTokenException) ||
				(e instanceof NoSuchLCSSubscriptionEntryException)) {

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

						LCSUtil.removeCredentials();
					}
				}
				else if (e instanceof NoSuchLCSSubscriptionEntryException) {
					if (_log.isWarnEnabled()) {
						ResourceBundle resourceBundle =
							ResourceBundle.getBundle("content.Language");

						_log.warn(
							resourceBundle.getString(
								"exceeded-subscription-number"));
					}
				}
				else if (OAuthUtil.hasOAuthTokenRejectedException(e)) {
					LCSUtil.removeCredentials();

					LCSUtil.sendServiceAvailabilityNotification(
						LCSPortletState.NO_CONNECTION);

					if (_log.isWarnEnabled()) {
						_log.warn(
							"OAuth token rejected. Please redeploy portlet. " +
								"If problem persists, contact support.");
					}

					return;
				}

				if (_log.isInfoEnabled()) {
					_log.info("LCS portlet is not connected");
				}
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("LCS portlet is not connected", e);
				}
			}

			LCSUtil.sendServiceAvailabilityNotification(
				LCSPortletState.NOT_REGISTERED);
		}
		finally {
			UptimeMonitoringTask uptimeMonitoringTask =
				new UptimeMonitoringTask();

			uptimeMonitoringTask.setUptimeMonitoringAdvisor(
				_uptimeMonitoringAdvisor);

			_scheduledExecutorService.scheduleAtFixedRate(
				uptimeMonitoringTask, 1, 1, TimeUnit.MINUTES);

			try {
				_uptimeMonitoringAdvisor.init();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Override
	protected void onUndeploy(Message message) throws Exception {
		SigarNativeLoader.unload();

		_scheduledExecutorService.shutdown();
	}

	private static Log _log = LogFactoryUtil.getLog(
		LCSHotDeployMessageListener.class);

	private LCSClusterEntryTokenAdvisor _lcsClusterEntryTokenAdvisor;
	private ScheduledExecutorService _scheduledExecutorService =
		Executors.newSingleThreadScheduledExecutor();
	private UptimeMonitoringAdvisor _uptimeMonitoringAdvisor;

}