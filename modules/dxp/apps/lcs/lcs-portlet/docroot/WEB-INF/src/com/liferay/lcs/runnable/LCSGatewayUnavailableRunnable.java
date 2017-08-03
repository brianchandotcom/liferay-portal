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

import com.liferay.lcs.service.LCSGatewayService;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.lcs.util.PortletPropsValues;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 * @author Mladen Cikara
 * @author Igor Beslic
 */
public class LCSGatewayUnavailableRunnable implements Runnable {

	public LCSGatewayUnavailableRunnable(
		long heartbeatInterval, JSONWebServiceTransportException jsonwste,
		LCSConnectionManager lcsConnectionManager,
		LCSGatewayService lcsGatewayService) {

		_heartbeatInterval = heartbeatInterval;
		_jsonwste = jsonwste;
		_lcsConnectionManager = lcsConnectionManager;
		_lcsGatewayService = lcsGatewayService;

		if (_log.isTraceEnabled()) {
			_log.trace("Initialized " + this);
		}
	}

	@Override
	public void run() {
		if (_log.isInfoEnabled()) {
			_log.info("Recovering connection to LCS gateway");
		}

		if (_jsonwste.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
			for (int i = 0; i < _multipliers.length; i++) {
				if (_lcsConnectionManager.isShutdownRequested()) {
					if (_log.isDebugEnabled()) {
						_log.debug("Shutdown requested, terminating thread");
					}

					return;
				}

				if (_signInToLCSGateway()) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Recovered connection to LCS gateway, " +
								"terminating thread");
					}

					return;
				}

				try {
					TimeUnit.MILLISECONDS.sleep(
						(long)(_multipliers[i] *
							_LCS_GATEWAY_UNAVAILABLE_WAIT_TIME));
				}
				catch (InterruptedException ie) {
					if (_log.isDebugEnabled()) {
						_log.error("Unable to sleep", ie);
					}
				}
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Access to gateway is forbidden, terminating thread");
			}
		}
		else {
			boolean singedIn = false;

			while (!singedIn) {
				_checkLCSGatewayAvailability();

				if (_lcsConnectionManager.isShutdownRequested()) {
					if (_log.isDebugEnabled()) {
						_log.debug("Shutdown requested, terminating thread");
					}

					return;
				}

				singedIn = _signInToLCSGateway();

				if (!singedIn) {
					if (_log.isDebugEnabled()) {
						int seconds =
							(int)(_multipliers[0] *
								_LCS_GATEWAY_UNAVAILABLE_WAIT_TIME / 1000);

						_log.debug(
							"Unable to complete handshake, retry in " +
								seconds + " seconds");
					}
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Recovered connection to LCS gateway, terminating thread");
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		if (_log.isTraceEnabled()) {
			_log.trace("Finalized " + this);
		}
	}

	protected int getNextIndex(int i) {
		if (i < (_multipliers.length - 1)) {
			i = i + 1;
		}

		return i;
	}

	private void _checkLCSGatewayAvailability() {
		boolean lcsGatewayAvailable = false;

		int i = 0;

		while (!_lcsConnectionManager.isShutdownRequested() &&
			   !lcsGatewayAvailable) {

			float multiplier = _multipliers[i];

			i = getNextIndex(i);

			try {
				TimeUnit.MILLISECONDS.sleep(
					(long)(multiplier * _LCS_GATEWAY_UNAVAILABLE_WAIT_TIME));
			}
			catch (InterruptedException ie) {
				if (_log.isDebugEnabled()) {
					_log.error("Unable to sleep", ie);
				}
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Checking for LCS gateway availability");
			}

			lcsGatewayAvailable =
				_lcsGatewayService.testLCSGatewayAvailability();

			if (lcsGatewayAvailable) {
				if (_log.isInfoEnabled()) {
					_log.info("LCS gateway is available");
				}
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("LCS gateway is unavailable");
				}
			}
		}
	}

	private long _getLastMessageSent() {
		Map<String, String> lcsConnectionMetadata =
			_lcsConnectionManager.getLCSConnectionMetadata();

		return GetterUtil.getLong(lcsConnectionMetadata.get("lastMessageSent"));
	}

	private boolean _signInToLCSGateway() {
		if (_log.isTraceEnabled()) {
			_log.trace("Sign in to LCS gateway");
		}

		Future<?> future = _lcsConnectionManager.start();

		try {
			future.get();

			if (_log.isInfoEnabled()) {
				_log.info("Recovered connection to LCS gateway");
			}

			return true;
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to complete handshake", e);
			}

			LCSUtil.processLCSPortletState(LCSPortletState.NO_CONNECTION);
		}

		return false;
	}

	private static final long _LCS_GATEWAY_UNAVAILABLE_WAIT_TIME =
		PortletPropsValues.COMMUNICATION_LCS_GATEWAY_UNAVAILABLE_WAIT_TIME;

	private static final Log _log = LogFactoryUtil.getLog(
		LCSGatewayUnavailableRunnable.class);

	private final long _heartbeatInterval;
	private final JSONWebServiceTransportException _jsonwste;
	private final LCSConnectionManager _lcsConnectionManager;
	private final LCSGatewayService _lcsGatewayService;
	private final float[] _multipliers = {0.5F, 1, 2, 5};

}