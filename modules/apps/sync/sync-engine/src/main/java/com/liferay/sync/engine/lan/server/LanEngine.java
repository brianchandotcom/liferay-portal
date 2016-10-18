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

package com.liferay.sync.engine.lan.server;

import com.liferay.sync.engine.lan.server.discovery.LanDiscoveryBroadcaster;
import com.liferay.sync.engine.lan.server.discovery.LanDiscoveryListener;
import com.liferay.sync.engine.lan.server.file.LanFileServer;
import com.liferay.sync.engine.lan.session.LanSession;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncLanClientService;
import com.liferay.sync.engine.util.PropsValues;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanEngine {

	public static ScheduledExecutorService getScheduledExecutorService() {
		return _scheduledExecutorService;
	}

	public static synchronized void start() {
		_logger.info("Starting LAN engine");

		_scheduledExecutorService =
			Executors.newSingleThreadScheduledExecutor();

		if (_lanFileServer == null) {
			_lanFileServer = new LanFileServer();
		}

		try {
			SyncAccountService.registerModelListener(
				_lanFileServer.getSyncAccountListener());
			SyncAccountService.registerModelListener(
				LanSession.getSyncAccountListener());

			_lanFileServer.start();
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			return;
		}

		if (_lanDiscoveryBroadcaster == null) {
			_lanDiscoveryBroadcaster = new LanDiscoveryBroadcaster();
		}

		Runnable broadcastRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					_lanDiscoveryBroadcaster.broadcast(
						_lanFileServer.getPort());
				}
				catch (Exception e) {
					String message = e.getMessage();

					if (!message.equals("Network is unreachable")) {
						_logger.error(e.getMessage(), e);
					}
				}
			}

		};

		_scheduledExecutorService.scheduleWithFixedDelay(
			broadcastRunnable, 0,
			PropsValues.SYNC_LAN_SERVER_BROADCAST_INTERVAL,
			TimeUnit.MILLISECONDS);

		if (_lanDiscoveryListener == null) {
			_lanDiscoveryListener = new LanDiscoveryListener();
		}

		Runnable discoveryListenerRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					_lanDiscoveryListener.listen();
				}
				catch (Exception e) {
					_logger.error(e.getMessage(), e);
				}
			}

		};

		_scheduledExecutorService.submit(discoveryListenerRunnable);

		Runnable expireSyncLanClientsRunnable = new Runnable() {

			@Override
			public void run() {
				SyncLanClientService.deleteSyncLanClients(
					System.currentTimeMillis() -
						PropsValues.SYNC_LAN_SERVER_BROADCAST_INTERVAL * 3);
			}

		};

		_scheduledExecutorService.scheduleWithFixedDelay(
			expireSyncLanClientsRunnable,
			PropsValues.SYNC_LAN_SERVER_BROADCAST_INTERVAL * 3,
			PropsValues.SYNC_LAN_SERVER_BROADCAST_INTERVAL,
			TimeUnit.MILLISECONDS);
	}

	public static synchronized void stop() {
		_logger.info("Stopping LAN engine");

		if (_scheduledExecutorService != null) {
			_scheduledExecutorService.shutdownNow();
		}

		if (_lanDiscoveryListener != null) {
			_lanDiscoveryListener.shutdown();
		}

		if (_lanDiscoveryBroadcaster != null) {
			_lanDiscoveryBroadcaster.shutdown();
		}

		if (_lanFileServer != null) {
			SyncAccountService.unregisterModelListener(
				_lanFileServer.getSyncAccountListener());
			SyncAccountService.unregisterModelListener(
				LanSession.getSyncAccountListener());

			_lanFileServer.stop();
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanEngine.class);

	private static LanDiscoveryBroadcaster _lanDiscoveryBroadcaster;
	private static LanDiscoveryListener _lanDiscoveryListener;
	private static LanFileServer _lanFileServer;
	private static ScheduledExecutorService _scheduledExecutorService;

}