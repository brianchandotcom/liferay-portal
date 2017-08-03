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

package com.liferay.lcs.task;

import com.liferay.lcs.advisor.LCSAlertAdvisor;
import com.liferay.lcs.advisor.UptimeMonitoringAdvisor;
import com.liferay.lcs.exception.LCSHandshakeException;
import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.HandshakeMessage;
import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.messaging.ResponseMessage;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSAlert;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSPatcherUtil;
import com.liferay.lcs.util.LCSPortletPreferencesUtil;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.lcs.util.comparator.MessagePriorityComparator;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.monitoring.PortalMonitoringControl;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.servlet.LiferayFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.portlet.PortletPreferences;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 * @author Marko Cikos
 */
public class HandshakeTask implements Task {

	public HandshakeTask() {
		if (_log.isTraceEnabled()) {
			_log.trace("Initialized " + this);
		}
	}

	@Override
	public void run() {
		try {
			doRun();
		}
		catch (Exception e) {
			_lcsConnectionManager.setReady(false);

			throw new RuntimeException(e);
		}
	}

	public void setHandshakeReplyReads(int handshakeReplyReads) {
		_handshakeReplyReads = handshakeReplyReads;
	}

	public void setHandshakeWaitTime(long handshakeWaitTime) {
		_handshakeWaitTime = handshakeWaitTime;
	}

	public void setHeartbeatInterval(long heartbeatInterval) {
		_heartbeatInterval = heartbeatInterval;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSAlertAdvisor(LCSAlertAdvisor lcsAlertAdvisor) {
		_lcsAlertAdvisor = lcsAlertAdvisor;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	public void setUptimeMonitoringAdvisor(
		UptimeMonitoringAdvisor uptimeMonitoringAdvisor) {

		_uptimeMonitoringAdvisor = uptimeMonitoringAdvisor;
	}

	protected void doRun() throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Initiate handshake");
		}

		_lcsConnectionManager.setLCSGatewayAvailable(true);

		String key = _keyGenerator.getKey();

		_lcsConnectionManager.deleteMessages(key);

		if (!_lcsConnectionManager.isLCSGatewayAvailable()) {
			throw new LCSHandshakeException("LCS Gateway unavailable");
		}

		HandshakeMessage handshakeMessage = new HandshakeMessage();

		handshakeMessage.put(
			Message.KEY_BUILD_NUMBER, ReleaseInfo.getBuildNumber());
		handshakeMessage.put(
			Message.KEY_CLUSTER_EXECUTOR_ENABLED,
			ClusterExecutorUtil.isEnabled());

		Map<Long, String> companyIdsWebIds = new HashMap<>();

		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (Company company : companies) {
			companyIdsWebIds.put(company.getCompanyId(), company.getWebId());
		}

		handshakeMessage.put(Message.KEY_COMPANY_IDS_WEB_IDS, companyIdsWebIds);

		handshakeMessage.put(Message.KEY_HASH_CODE, key.hashCode());
		handshakeMessage.put(
			Message.KEY_HEARTBEAT_INTERVAL, String.valueOf(_heartbeatInterval));

		if (LCSPatcherUtil.isConfigured()) {
			handshakeMessage.put(
				Message.KEY_PATCHING_TOOL_STATUS,
				LCSConstants.PATCHING_TOOL_AVAILABLE);
		}
		else {
			handshakeMessage.put(
				Message.KEY_PATCHING_TOOL_STATUS,
				LCSConstants.PATCHING_TOOL_UNAVAILABLE);
		}

		handshakeMessage.put(
			Message.KEY_PATCHING_TOOL_VERSION,
			LCSPatcherUtil.getPatchingToolVersion());
		handshakeMessage.put(
			Message.KEY_LCS_PORTLET_BUILD_NUMBER,
			LCSUtil.getLCSPortletBuildNumber());

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<PortalMonitoringControl> serviceReference =
			bundleContext.getServiceReference(PortalMonitoringControl.class);

		LiferayFilter liferayFilter = (LiferayFilter)bundleContext.getService(
			serviceReference);

		if (liferayFilter.isFilterEnabled()) {
			handshakeMessage.put(
				Message.KEY_MONITORING_STATUS,
				LCSConstants.MONITORING_AVAILABLE);
		}
		else {
			handshakeMessage.put(
				Message.KEY_MONITORING_STATUS,
				LCSConstants.MONITORING_UNAVAILABLE);
		}

		PortletPreferences jxPortletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

		boolean metricsLCSServiceEnabled = GetterUtil.getBoolean(
			jxPortletPreferences.getValue(
				LCSConstants.METRICS_LCS_SERVICE_ENABLED,
				Boolean.FALSE.toString()));

		if (metricsLCSServiceEnabled) {
			handshakeMessage.put(
				Message.KEY_METRICS_LCS_SERVICE_STATUS,
				LCSConstants.METRICS_LCS_SERVICE_AVAILABLE);
			handshakeMessage.put(
				Message.KEY_SITE_NAMES_LCS_SERVICE_STATUS,
				LCSConstants.SITE_NAMES_LCS_SERVICE_AVAILABLE);
		}
		else {
			handshakeMessage.put(
				Message.KEY_METRICS_LCS_SERVICE_STATUS,
				LCSConstants.METRICS_LCS_SERVICE_UNAVAILABLE);
			handshakeMessage.put(
				Message.KEY_SITE_NAMES_LCS_SERVICE_STATUS,
				LCSConstants.SITE_NAMES_LCS_SERVICE_UNAVAILABLE);
		}

		boolean patchesLCSServiceEnabled = GetterUtil.getBoolean(
			jxPortletPreferences.getValue(
				LCSConstants.PATCHES_LCS_SERVICE_ENABLED,
				Boolean.FALSE.toString()));

		if (patchesLCSServiceEnabled) {
			handshakeMessage.put(
				Message.KEY_PATCHES_LCS_SERVICE_STATUS,
				LCSConstants.PATCHES_LCS_SERVICE_AVAILABLE);
		}
		else {
			handshakeMessage.put(
				Message.KEY_PATCHES_LCS_SERVICE_STATUS,
				LCSConstants.PATCHES_LCS_SERVICE_UNAVAILABLE);
		}

		handshakeMessage.put(
			Message.KEY_PORTAL_EDITION, LCSUtil.getPortalEdition());

		handshakeMessage.setKey(key);

		boolean portalPropertiesLCSServiceEnabled = GetterUtil.getBoolean(
			jxPortletPreferences.getValue(
				LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED,
				Boolean.FALSE.toString()));

		if (portalPropertiesLCSServiceEnabled) {
			handshakeMessage.put(
				Message.KEY_PORTAL_PROPERTIES_LCS_SERVICE_STATUS,
				LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_AVAILABLE);
		}
		else {
			handshakeMessage.put(
				Message.KEY_PORTAL_PROPERTIES_LCS_SERVICE_STATUS,
				LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_UNAVAILABLE);
		}

		List<Map<String, Long>> uptimes = _uptimeMonitoringAdvisor.getUptimes();

		_uptimeMonitoringAdvisor.resetCurrentUptimeEndTime(uptimes);

		handshakeMessage.put(Message.KEY_UPTIMES, uptimes);

		_lcsConnectionManager.sendMessage(handshakeMessage);

		int attempt = 0;
		List<Message> delayedMessages = new ArrayList<>();
		List<Message> receivedMessages = null;

		while (true) {
			if (attempt++ > _handshakeReplyReads) {
				throw new LCSHandshakeException(
					"Unable to establish a connection after " +
						_handshakeReplyReads + " handshakes");
			}

			receivedMessages = _lcsConnectionManager.getMessages(key);

			if (receivedMessages.isEmpty()) {
				try {
					TimeUnit.MILLISECONDS.sleep(
						_handshakeWaitTime / _handshakeReplyReads);
				}
				catch (InterruptedException ie) {
				}

				continue;
			}

			if (processResponse(receivedMessages, delayedMessages)) {
				break;
			}
		}

		Collections.sort(delayedMessages, new MessagePriorityComparator());

		for (Message delayedMessage : delayedMessages) {
			if (delayedMessage instanceof CommandMessage) {
				MessageBusUtil.sendMessage(
					"liferay/lcs_commands", delayedMessage);
			}
			else {
				_log.error(
					"There are no handlers for message " + delayedMessage);
			}
		}

		_lcsConnectionManager.onHandshakeSuccess();

		_uptimeMonitoringAdvisor.resetUptimes();

		_lcsConnectionManager.setReady(true);

		LCSUtil.processLCSPortletState(LCSPortletState.NO_SUBSCRIPTION);

		if (_log.isInfoEnabled()) {
			_log.info("Established connection");
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		if (_log.isTraceEnabled()) {
			_log.trace("Finalized " + this);
		}
	}

	protected boolean isNewLCSPortletBuildNumber(Message receivedMessage) {
		int latestLCSPortletBuildNumber = GetterUtil.getInteger(
			receivedMessage.get(Message.KEY_LATEST_LCS_PORTLET_BUILD_NUMBER));

		if (latestLCSPortletBuildNumber > LCSUtil.getLCSPortletBuildNumber()) {
			return true;
		}

		return false;
	}

	protected boolean processResponse(
		List<Message> receivedMessages, List<Message> delayedMessages) {

		boolean receivedHandshakeResponse = false;

		for (Message receivedMessage : receivedMessages) {
			if (!(receivedMessage instanceof ResponseMessage)) {
				delayedMessages.add(receivedMessage);

				continue;
			}

			ResponseMessage responseMessage = (ResponseMessage)receivedMessage;

			if (!CommandMessage.COMMAND_TYPE_INITIATE_HANDSHAKE.equals(
					responseMessage.getCommandType())) {

				continue;
			}

			if (responseMessage.contains(Message.KEY_ERROR)) {
				throw new LCSHandshakeException(
					(String)responseMessage.get(Message.KEY_ERROR));
			}

			if (responseMessage.contains(Message.KEY_HANDSHAKE_EXPIRED_ERROR)) {
				_lcsAlertAdvisor.add(LCSAlert.WARNING_HANDSHAKE_EXPIRED);

				throw new LCSHandshakeException(
					"Handshake expired. Check that the server is " +
						"synchronized with an NTP server.");
			}

			receivedHandshakeResponse = true;

			if (Validator.isNotNull(
					receivedMessage.get(
						Message.KEY_LATEST_LCS_PORTLET_BUILD_NUMBER))) {

				_lcsConnectionManager.putLCSConnectionMetadata(
					"newLCSPortletBuildNumber",
					String.valueOf(
						isNewLCSPortletBuildNumber(receivedMessage)));
			}
		}

		return receivedHandshakeResponse;
	}

	private static final Log _log = LogFactoryUtil.getLog(HandshakeTask.class);

	private int _handshakeReplyReads;
	private long _handshakeWaitTime;
	private long _heartbeatInterval;
	private KeyGenerator _keyGenerator;
	private LCSAlertAdvisor _lcsAlertAdvisor;
	private LCSConnectionManager _lcsConnectionManager;
	private UptimeMonitoringAdvisor _uptimeMonitoringAdvisor;

}