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

package com.liferay.lcs.util;

import com.liferay.lcs.advisor.LCSAlertAdvisor;
import com.liferay.lcs.advisor.LCSClusterEntryTokenAdvisor;
import com.liferay.lcs.advisor.UptimeMonitoringAdvisor;
import com.liferay.lcs.exception.CompressionException;
import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.messaging.scheduler.MessageListenerSchedulerService;
import com.liferay.lcs.runnable.LCSConnectorRunnable;
import com.liferay.lcs.runnable.LCSThreadFactory;
import com.liferay.lcs.service.LCSGatewayService;
import com.liferay.lcs.task.CommandMessageTask;
import com.liferay.lcs.task.HandshakeTask;
import com.liferay.lcs.task.HeartbeatTask;
import com.liferay.lcs.task.LicenseManagerTask;
import com.liferay.lcs.task.SignOffTask;
import com.liferay.lcs.task.UptimeMonitoringTask;
import com.liferay.lcs.task.scheduler.TaskSchedulerService;
import com.liferay.petra.json.web.service.client.JSONWebServiceException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.ref.WeakReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Igor Beslic
 * @author Ivica Cardic
 */
public class LCSConnectionManagerImpl implements LCSConnectionManager {

	@Override
	public void deleteMessages(String key) throws JSONWebServiceException {
		try {
			_lcsGatewayService.deleteMessages(key);
		}
		catch (JSONWebServiceException jsonwse) {
			_processJSONWebServiceException(jsonwse);
		}
	}

	public void destroy() {
		if (_log.isTraceEnabled()) {
			_log.trace("Destroying " + this);
		}

		_shutdownRequested = true;

		LCSConnectorRunnable lcsConnectorRunnable =
			_lcsConnectorRunnableWeakReference.get();

		if (lcsConnectorRunnable != null) {
			lcsConnectorRunnable.stop();
		}

		LCSUtil.processLCSPortletState(LCSPortletState.PLUGIN_ABSENT);

		Future<?> future = stop(false, false, true);

		try {
			if (future != null) {
				future.get();
			}
		}
		catch (Exception e) {
			_log.error("Unable to stop communication with LCS gateway", e);
		}

		if (ClusterExecutorUtil.isEnabled()) {
			try {
				while (!_scheduledFutures.isEmpty()) {
					Thread.sleep(1000);
				}

				Thread.sleep(1000);
			}
			catch (InterruptedException ie) {
				if (_log.isTraceEnabled()) {
					_log.trace(
						"Interrupted while waiting for scheduled features to " +
							"complete");
				}
			}
		}

		_scheduledExecutorService.shutdown();

		try {
			if (!_scheduledExecutorService.awaitTermination(
					5, TimeUnit.SECONDS)) {

				_scheduledExecutorService.shutdownNow();
			}
		}
		catch (InterruptedException ie) {
			_scheduledExecutorService.shutdownNow();
		}

		if (_log.isTraceEnabled()) {
			_log.trace("Destroyed " + this);
		}
	}

	@Override
	public Map<String, String> getLCSConnectionMetadata() {
		return _lcsConnectionMetadata;
	}

	@Override
	public List<Message> getMessages(String key)
		throws JSONWebServiceException {

		try {
			return _lcsGatewayService.getMessages(key);
		}
		catch (JSONWebServiceException jsonwse) {
			_processJSONWebServiceException(jsonwse);
		}

		return Collections.emptyList();
	}

	@Override
	public boolean isLCSGatewayAvailable() {
		return _lcsGatewayAvailable;
	}

	@Override
	public synchronized boolean isReady() {
		return _ready;
	}

	@Override
	public boolean isShutdownRequested() {
		return _shutdownRequested;
	}

	@Override
	public void onHandshakeSuccess() {
		if (_log.isTraceEnabled()) {
			_log.trace("Handshake success");
		}

		setReady(true);

		LCSUtil.processLCSPortletState(LCSPortletState.NO_SUBSCRIPTION);

		_lcsConnectionMetadata.put(
			"handshakeTime", String.valueOf(System.currentTimeMillis()));
		_lcsConnectionMetadata.put(
			"jvmMetricsTaskInterval", String.valueOf(60000));
		_lcsConnectionMetadata.put(
			"messageTaskInterval", String.valueOf(10000));

		if (_log.isTraceEnabled()) {
			_log.trace("Scheduling command message task");
		}

		_scheduledFutures.add(
			_scheduledExecutorService.scheduleAtFixedRate(
				_commandMessageTask,
				LCSConstants.COMMAND_MESSAGE_TASK_SCHEDULE_PERIOD,
				LCSConstants.COMMAND_MESSAGE_TASK_SCHEDULE_PERIOD,
				TimeUnit.SECONDS));

		if (_log.isTraceEnabled()) {
			_log.trace("Scheduling heartbeat task");
		}

		_scheduledFutures.add(
			_scheduledExecutorService.scheduleAtFixedRate(
				_heartbeatTask, _heartbeatInterval, _heartbeatInterval,
				TimeUnit.MILLISECONDS));

		if (_log.isTraceEnabled()) {
			_log.trace("Scheduling license manager task");
		}

		_scheduledFutures.add(
			_scheduledExecutorService.scheduleAtFixedRate(
				_licenseManagerTask, 0L, 2L, TimeUnit.MINUTES));

		_lcsAlertAdvisor.clear();

		_lcsAlertAdvisor.add(LCSAlert.SUCCESS_CONNECTION_TO_LCS_VALID);
	}

	@Override
	public void onPortletDeployed() {
		try {
			_uptimeMonitoringAdvisor.init();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_scheduleUptimeMonitoringTask();

		_executeLCSConnectorRunnable(false);
	}

	@Override
	public void putLCSConnectionMetadata(String key, String value) {
		_lcsConnectionMetadata.put(key, value);
	}

	@Override
	public void sendMessage(Message message)
		throws CompressionException, JSONWebServiceException {

		try {
			_lcsGatewayService.sendMessage(message);

			_lcsConnectionMetadata.put(
				"lastMessageSent", String.valueOf(System.currentTimeMillis()));
		}
		catch (JSONWebServiceException jsonwse) {
			_processJSONWebServiceException(jsonwse);
		}
	}

	public void setCommandMessageTask(CommandMessageTask commandMessageTask) {
		_commandMessageTask = commandMessageTask;
	}

	public void setHandshakeTask(HandshakeTask handshakeTask) {
		_handshakeTask = handshakeTask;
	}

	public void setHandshakeWaitTime(long handshakeWaitTime) {
		_lcsConnectionMetadata.put(
			"handshakeWaitTime", String.valueOf(handshakeWaitTime));
	}

	public void setHeartbeatInterval(long heartbeatInterval) {
		_heartbeatInterval = heartbeatInterval;

		_lcsConnectionMetadata.put(
			"heartbeatInterval", String.valueOf(heartbeatInterval));
	}

	public void setHeartbeatTask(HeartbeatTask heartbeatTask) {
		_heartbeatTask = heartbeatTask;
	}

	public void setLCSAlertAdvisor(LCSAlertAdvisor lcsAlertAdvisor) {
		_lcsAlertAdvisor = lcsAlertAdvisor;
	}

	public void setLCSClusterEntryTokenAdvisor(
		LCSClusterEntryTokenAdvisor lcsClusterEntryTokenAdvisor) {

		_lcsClusterEntryTokenAdvisor = lcsClusterEntryTokenAdvisor;
	}

	@Override
	public void setLCSGatewayAvailable(boolean lcsGatewayAvailable) {
		_lcsGatewayAvailable = lcsGatewayAvailable;
	}

	public void setLCSGatewayService(LCSGatewayService lcsGatewayService) {
		_lcsGatewayService = lcsGatewayService;
	}

	public void setLicenseManagerTask(LicenseManagerTask licenseManagerTask) {
		_licenseManagerTask = licenseManagerTask;
	}

	public void setMessageListenerSchedulerService(
		MessageListenerSchedulerService messageListenerSchedulerService) {

		_messageListenerSchedulerService = messageListenerSchedulerService;
	}

	@Override
	public synchronized void setReady(boolean ready) {
		_ready = ready;
	}

	@Override
	public void setShutdownRequested(boolean shutdownRequested) {
		_shutdownRequested = shutdownRequested;
	}

	public void setSignOffTask(SignOffTask signOffTask) {
		_signOffTask = signOffTask;
	}

	public void setTaskSchedulerService(
		TaskSchedulerService taskSchedulerService) {

		_taskSchedulerService = taskSchedulerService;
	}

	public void setUptimeMonitoringAdvisor(
		UptimeMonitoringAdvisor uptimeMonitoringAdvisor) {

		_uptimeMonitoringAdvisor = uptimeMonitoringAdvisor;
	}

	@Override
	public Future<?> start() {
		if (isReady()) {
			return null;
		}

		return _scheduledExecutorService.submit(_handshakeTask);
	}

	@Override
	public Future<?> stop(
		boolean delayReconnect, boolean signedOff,
		boolean serverManuallyShutdown) {

		if (!isReady()) {
			return null;
		}

		setReady(false);

		_cancelSchedulers();

		LCSUtil.processLCSPortletState(LCSPortletState.NO_CONNECTION);

		if (signedOff) {
			_executeLCSConnectorRunnable(delayReconnect);

			return null;
		}

		_signOffTask.setServerManuallyShutdown(serverManuallyShutdown);

		return _scheduledExecutorService.submit(_signOffTask);
	}

	protected synchronized void handleLCSGatewayUnavailable() {
		if (!isLCSGatewayAvailable()) {
			return;
		}

		setLCSGatewayAvailable(false);

		if (!isReady()) {
			return;
		}

		setReady(false);

		LCSUtil.processLCSPortletState(LCSPortletState.NO_CONNECTION);

		_cancelSchedulers();

		_executeLCSConnectorRunnable(true);
	}

	private void _cancelSchedulers() {
		_messageListenerSchedulerService.unscheduleAllMessageListeners();

		_taskSchedulerService.unscheduleAllTasks();

		for (ScheduledFuture<?> scheduledFuture : _scheduledFutures) {
			while (!scheduledFuture.isCancelled()) {
				scheduledFuture.cancel(true);
			}
		}

		_scheduledFutures.clear();

		if (_log.isDebugEnabled()) {
			_log.debug("All LCS schedulers canceled");
		}
	}

	private void _executeLCSConnectorRunnable(boolean delayRun) {
		LCSConnectorRunnable lcsConnectorRunnable = new LCSConnectorRunnable(
			delayRun);

		lcsConnectorRunnable.setLCSAlertAdvisor(_lcsAlertAdvisor);
		lcsConnectorRunnable.setLCSClusterEntryTokenAdvisor(
			_lcsClusterEntryTokenAdvisor);
		lcsConnectorRunnable.setLCSConnectionManager(this);

		_scheduledExecutorService.execute(lcsConnectorRunnable);

		if (_lcsConnectorRunnableWeakReference != null) {
			_lcsConnectorRunnableWeakReference.clear();
		}

		_lcsConnectorRunnableWeakReference = new WeakReference<>(
			lcsConnectorRunnable);

		if (_log.isTraceEnabled()) {
			_log.trace(lcsConnectorRunnable + " scheduled");
		}
	}

	private void _processJSONWebServiceException(
			JSONWebServiceException jsonwse)
		throws JSONWebServiceException {

		if (_log.isDebugEnabled()) {
			_log.debug(jsonwse.getMessage(), jsonwse);
		}

		if (_shutdownRequested) {
			if (_log.isTraceEnabled()) {
				_log.trace("Shut down requested, skip exception handler");
			}

			return;
		}

		if (jsonwse instanceof JSONWebServiceTransportException) {
			if (_log.isWarnEnabled()) {
				_log.warn("LCS gateway is unavailable");
			}

			if (jsonwse.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"LCS portlet is not authorized to access LCS " +
							"gateway. Will attempt to reauthorize");
				}
			}

			handleLCSGatewayUnavailable();
		}

		throw jsonwse;
	}

	private void _scheduleUptimeMonitoringTask() {
		UptimeMonitoringTask uptimeMonitoringTask = new UptimeMonitoringTask();

		uptimeMonitoringTask.setUptimeMonitoringAdvisor(
			_uptimeMonitoringAdvisor);

		_scheduledExecutorService.scheduleAtFixedRate(
			uptimeMonitoringTask, 1, 1, TimeUnit.MINUTES);

		if (_log.isTraceEnabled()) {
			_log.trace(uptimeMonitoringTask.toString() + " scheduled");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSConnectionManagerImpl.class);

	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(10, new LCSThreadFactory());

	private CommandMessageTask _commandMessageTask;
	private HandshakeTask _handshakeTask;
	private long _heartbeatInterval;
	private HeartbeatTask _heartbeatTask;
	private LCSAlertAdvisor _lcsAlertAdvisor;
	private LCSClusterEntryTokenAdvisor _lcsClusterEntryTokenAdvisor;
	private final Map<String, String> _lcsConnectionMetadata = new HashMap<>();
	private WeakReference<LCSConnectorRunnable>
		_lcsConnectorRunnableWeakReference;
	private volatile boolean _lcsGatewayAvailable;
	private LCSGatewayService _lcsGatewayService;
	private LicenseManagerTask _licenseManagerTask;
	private MessageListenerSchedulerService _messageListenerSchedulerService;
	private volatile boolean _ready;
	private final List<ScheduledFuture<?>> _scheduledFutures =
		new ArrayList<>();
	private volatile boolean _shutdownRequested;
	private SignOffTask _signOffTask;
	private TaskSchedulerService _taskSchedulerService;
	private UptimeMonitoringAdvisor _uptimeMonitoringAdvisor;

}