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

import com.liferay.lcs.advisor.LCSClusterEntryTokenAdvisor;
import com.liferay.lcs.exception.LCSExceptionSender;
import com.liferay.lcs.messaging.Message;
import com.liferay.lcs.service.LCSGatewayService;
import com.liferay.lcs.task.CommandMessageTask;
import com.liferay.lcs.task.HandshakeTask;
import com.liferay.lcs.task.HeartbeatTask;
import com.liferay.lcs.task.SignOffTask;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

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
	public void deleteMessages(String key) throws PortalException {
		try {
			_lcsGatewayService.deleteMessages(key);
		}
		catch (SystemException se) {
			if (se.getCause() instanceof JSONWebServiceTransportException) {
				handleLCSGatewayUnavailable(
					(JSONWebServiceTransportException)se.getCause());
			}
			else {
				throw se;
			}
		}
	}

	@Override
	public void deregister() {
		LCSUtil.sendServiceAvailabilityNotification(
			LCSPortletState.NOT_REGISTERED);

		stop(true, false);

		LCSUtil.deletePortletPreferences();

		_lcsClusterEntryTokenAdvisor.deleteLCSCLusterEntryTokenFile();

		KeyGeneratorUtil.clearCache();
	}

	public void destroy() {
		_cancelRunnable = true;

		Future<?> future = stop(false, true);

		try {
			future.get();
		}
		catch (Exception e) {
		}

		_scheduledExecutorService.shutdown();

		try {
			if (!_scheduledExecutorService.awaitTermination(
					5, TimeUnit.SECONDS)) {

				_scheduledExecutorService.shutdownNow();
			}
		}
		catch (final InterruptedException ie) {
			_scheduledExecutorService.shutdownNow();
		}
	}

	@Override
	public Map<String, String> getLCSConnectionMetadata() {
		return _lcsConnectionMetadata;
	}

	@Override
	public List<Message> getMessages(String key) throws PortalException {
		try {
			return _lcsGatewayService.getMessages(key);
		}
		catch (SystemException se) {
			if (se.getCause() instanceof JSONWebServiceTransportException) {
				handleLCSGatewayUnavailable(
					(JSONWebServiceTransportException)se.getCause());

				return Collections.emptyList();
			}

			throw se;
		}
	}

	public boolean isHandshakeExpired() {
		return _handshakeExpired;
	}

	public boolean isLCSGatewayAvailable() {
		return _lcsGatewayAvailable;
	}

	@Override
	public synchronized boolean isPending() {
		return _pending;
	}

	@Override
	public synchronized boolean isReady() {
		return _ready;
	}

	@Override
	public void onHandshakeSuccess() {
		_lcsConnectionMetadata.put(
			"handshakeTime", String.valueOf(System.currentTimeMillis()));
		_lcsConnectionMetadata.put(
			"jvmMetricsTaskInterval", String.valueOf(60000));
		_lcsConnectionMetadata.put(
			"messageTaskInterval", String.valueOf(10000));

		_scheduledFutures.add(
			_scheduledExecutorService.scheduleAtFixedRate(
				_commandMessageTask,
				LCSConstants.COMMAND_MESSAGE_TASK_SCHEDULE_PERIOD,
				LCSConstants.COMMAND_MESSAGE_TASK_SCHEDULE_PERIOD,
				TimeUnit.SECONDS));
		_scheduledFutures.add(
			_scheduledExecutorService.scheduleAtFixedRate(
				_heartbeatTask, _heartbeatInterval, _heartbeatInterval,
				TimeUnit.MILLISECONDS));
	}

	@Override
	public void onSignOff() {
		for (ScheduledFuture<?> scheduledFuture : _scheduledFutures) {
			while (!scheduledFuture.isCancelled()) {
				scheduledFuture.cancel(true);
			}
		}

		_scheduledFutures.clear();
	}

	@Override
	public void putLCSConnectionMetadata(String key, String value) {
		_lcsConnectionMetadata.put(key, value);
	}

	@Override
	public Future<?> restart() {
		Future future = stop(false, false);

		try {
			if (future != null) {
				future.get();
			}
		}
		catch (Exception e) {
			_log.error("Unable to stop communication with LCS gateway", e);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Recovering connection to LCS gateway after unsuccessful " +
						"stop");
			}
		}

		return start();
	}

	public void sendMessage(Message message) throws PortalException {
		try {
			_lcsGatewayService.sendMessage(message);

			_lcsConnectionMetadata.put(
				"lastMessageSent", String.valueOf(System.currentTimeMillis()));
		}
		catch (SystemException se) {
			if (se.getCause() instanceof JSONWebServiceTransportException) {
				handleLCSGatewayUnavailable(
					(JSONWebServiceTransportException)se.getCause());
			}
			else {
				throw se;
			}
		}
	}

	public void setCommandMessageTask(CommandMessageTask commandMessageTask) {
		_commandMessageTask = commandMessageTask;
	}

	public void setHandshakeExpired(boolean handshakeExpired) {
		_handshakeExpired = handshakeExpired;
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

	public void setLCSClusterEntryTokenAdvisor(
		LCSClusterEntryTokenAdvisor lcsClusterEntryTokenAdvisor) {

		_lcsClusterEntryTokenAdvisor = lcsClusterEntryTokenAdvisor;
	}

	public void setLCSExceptionSender(LCSExceptionSender lcsExceptionSender) {
		_lcsExceptionSender = lcsExceptionSender;
	}

	public void setLCSGatewayAvailable(boolean lcsGatewayAvailable) {
		_lcsGatewayAvailable = lcsGatewayAvailable;
	}

	public void setLCSGatewayService(LCSGatewayService lcsGatewayService) {
		_lcsGatewayService = lcsGatewayService;
	}

	public void setLCSGatewayUnavailableWaitTime(
		int lcsGatewayUnavailableWaitTime) {

		_lcsGatewayUnavailableWaitTime = lcsGatewayUnavailableWaitTime;
	}

	@Override
	public synchronized void setPending(boolean pending) {
		_pending = pending;
	}

	@Override
	public synchronized void setReady(boolean ready) {
		_ready = ready;
	}

	public void setSignOffTask(SignOffTask signOffTask) {
		_signOffTask = signOffTask;
	}

	@Override
	public Future<?> start() {
		if (isReady() || isPending()) {
			return null;
		}

		setHandshakeExpired(false);
		setPending(true);

		Future<?> future = _scheduledExecutorService.submit(_handshakeTask);

		return future;
	}

	@Override
	public Future<?> stop() {
		return stop(false, false);
	}

	public Future<?> stop(boolean deregister, boolean serverManuallyShutdown) {
		if (!isReady()) {
			return null;
		}

		setPending(true);
		setReady(false);

		Future<?> future = clean(deregister, serverManuallyShutdown);

		return future;
	}

	protected Future<?> clean(
		boolean deregister, boolean serverManuallyShutdown) {

		_signOffTask.setDeregister(deregister);
		_signOffTask.setServerManuallyShutdown(serverManuallyShutdown);

		return _scheduledExecutorService.submit(_signOffTask);
	}

	protected synchronized void handleLCSGatewayUnavailable(
		JSONWebServiceTransportException jsonwste) {

		String message =
			"Stopping communication because LCS gateway is unavailable with " +
				"status " + jsonwste.getStatus();

		_lcsExceptionSender.sendMessage(message, jsonwste);

		if (_log.isWarnEnabled()) {
			_log.warn(message);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(jsonwste.getMessage(), jsonwste);
		}

		setHandshakeExpired(false);
		setLCSGatewayAvailable(false);
		setPending(false);

		if (!isReady()) {
			return;
		}

		if ((_lcsGatewayUnavailableFuture != null) &&
			!_lcsGatewayUnavailableFuture.isDone()) {

			setReady(false);

			return;
		}

		setReady(false);

		_lcsGatewayUnavailableFuture = _scheduledExecutorService.submit(
			new LCSGatewayUnavailableRunnable(jsonwste));
	}

	private static Log _log = LogFactoryUtil.getLog(
		LCSConnectionManagerImpl.class);

	private static ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(10);

	private boolean _cancelRunnable;
	private CommandMessageTask _commandMessageTask;
	private boolean _handshakeExpired;
	private HandshakeTask _handshakeTask;
	private long _heartbeatInterval;
	private HeartbeatTask _heartbeatTask;
	private LCSClusterEntryTokenAdvisor _lcsClusterEntryTokenAdvisor;
	private Map<String, String> _lcsConnectionMetadata = new HashMap<>();
	private LCSExceptionSender _lcsExceptionSender;
	private boolean _lcsGatewayAvailable;
	private LCSGatewayService _lcsGatewayService;
	private Future _lcsGatewayUnavailableFuture;
	private int _lcsGatewayUnavailableWaitTime;
	private boolean _pending;
	private boolean _ready;
	private List<ScheduledFuture<?>> _scheduledFutures = new ArrayList<>();
	private SignOffTask _signOffTask;

	private class LCSGatewayUnavailableRunnable implements Runnable {

		public LCSGatewayUnavailableRunnable(
			JSONWebServiceTransportException jsonwste) {

			_jsonwste = jsonwste;
		}

		@Override
		public void run() {
			if (_log.isWarnEnabled()) {
				_log.warn("Recovering connection to LCS gateway");
			}

			if (_jsonwste.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
				int i = 0;

				while (!_cancelRunnable && !isReady()) {
					_log.warn("Starting handshake");

					Future<?> future = clean(true, false);

					try {
						future.get();
					}
					catch (Exception e) {
					}

					float multiplier = _multipliers[i];

					i = getNextIndex(i);

					try {
						TimeUnit.MILLISECONDS.sleep(
							(long) (multiplier *
								_lcsGatewayUnavailableWaitTime));
					}
					catch (InterruptedException ie) {
					}

					future = start();

					try {
						future.get();
					}
					catch (Exception e) {
					}
				}
			}
			else {
				boolean lcsGatewayAvailable = false;

				int i = 0;

				do {
					_log.warn("Checking for LCS gateway availability");

					lcsGatewayAvailable =
						_lcsGatewayService.testLCSGatewayAvailability();

					if (_log.isWarnEnabled()) {
						if (lcsGatewayAvailable) {
							_log.warn("LCS gateway is available");
						}
						else {
							_log.warn("LCS gateway is unavailable");
						}
					}

					float multiplier = _multipliers[i];

					i = getNextIndex(i);

					try {
						TimeUnit.MILLISECONDS.sleep(
							(long) (multiplier *
								_lcsGatewayUnavailableWaitTime));
					}
					catch (InterruptedException ie) {
					}
				}
				while (!_cancelRunnable && !isReady() && !lcsGatewayAvailable);

				if (_cancelRunnable || isReady()) {
					return;
				}

				long lastMessageSent = GetterUtil.getLong(
					_lcsConnectionMetadata.get("lastMessageSent"));

				long period = System.currentTimeMillis() - lastMessageSent;

				if (period > (2 * _heartbeatInterval)) {
					_log.warn("Starting handshake");

					Future<?> future = clean(true, false);

					try {
						future.get();
					}
					catch (Exception e) {
					}

					future = start();

					try {
						future.get();
					}
					catch (Exception e) {
					}
				}
				else {
					setLCSGatewayAvailable(true);
					setReady(true);
				}
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Recovered connection to LCS gateway");
			}
		}

		protected int getNextIndex(int i) {
			if (i < (_multipliers.length - 1)) {
				i = i + 1;
			}

			return i;
		}

		private final JSONWebServiceTransportException _jsonwste;
		private final float[] _multipliers = {0.5f, 1, 2, 5};

	}

}