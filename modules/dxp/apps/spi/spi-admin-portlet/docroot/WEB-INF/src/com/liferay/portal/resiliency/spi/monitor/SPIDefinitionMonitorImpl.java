/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.monitor;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.util.SPIAdminConstants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Michael C. Han
 */
public class SPIDefinitionMonitorImpl implements SPIDefinitionMonitor {

	public void afterPropertiesSet() {
		_monitorThread = new Thread(new SPIMonitor(), "SPI Monitor Thread");

		_monitorThread.setDaemon(true);

		_monitorThread.start();
	}

	public void destroy() {
		_stopped = true;

		_spiDefinitions.clear();

		_monitorThread.interrupt();
	}

	public void performHealthCheck() {
		Lock readLock = _readWriteLock.readLock();

		readLock.lock();

		Set<Long> spiDefinitionIds = new HashSet<>();

		try {
			for (SPIDefinition spiDefinition : _spiDefinitions.values()) {
				if (spiDefinition.isAlive()) {
					continue;
				}

				spiDefinitionIds.add(spiDefinition.getSpiDefinitionId());

				Message message = new Message();

				message.put(
					"spiDefinitionId", spiDefinition.getSpiDefinitionId());
				message.put("status", SPIAdminConstants.STATUS_STOPPED);

				MessageBusUtil.sendMessage(
					_SPI_STATUS_DESTINATION_NAME, message);
			}
		}
		finally {
			readLock.unlock();
		}

		if (spiDefinitionIds.isEmpty()) {
			return;
		}

		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			for (Long spiDefinitionId : spiDefinitionIds) {
				_spiDefinitions.remove(spiDefinitionId);
			}
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void register(SPIDefinition spiDefinition) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			if (_spiDefinitions.containsKey(
					spiDefinition.getSpiDefinitionId())) {

				return;
			}

			_spiDefinitions.put(
				spiDefinition.getSpiDefinitionId(), spiDefinition);
		}
		finally {
			lock.unlock();
		}
	}

	public void setInterval(long interval) {
		_interval = interval;
	}

	@Override
	public void unregister() {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_spiDefinitions.clear();
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void unregister(long spiDefinitionId) {
		Lock lock = _readWriteLock.writeLock();

		lock.lock();

		try {
			_spiDefinitions.remove(spiDefinitionId);
		}
		finally {
			lock.unlock();
		}
	}

	private static final String _SPI_STATUS_DESTINATION_NAME =
		"liferay/spi_status";

	private long _interval = 60000;
	private Thread _monitorThread;
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock();
	private final Map<Long, SPIDefinition> _spiDefinitions = new HashMap<>();
	private boolean _stopped;

	private class SPIMonitor implements Runnable {

		@Override
		public void run() {
			do {
				try {
					Thread.sleep(_interval);
				}
				catch (InterruptedException ie) {
				}

				performHealthCheck();
			}
			while (!_stopped);
		}

	}

}