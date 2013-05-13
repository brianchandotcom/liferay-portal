/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.messaging.jmx;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DestinationStatisticsManager
	implements DestinationStatisticsManagerMBean {

	public static ObjectName createObjectName(String destinationName) {
		try {
			return new ObjectName(_OBJECT_NAME_PREFIX + destinationName);
		}
		catch (MalformedObjectNameException mone) {
			throw new IllegalStateException(mone);
		}
	}

	public DestinationStatisticsManager(Destination destination) {
		_destination = destination;
	}

	@Override
	public int getActiveThreadCount() {
		if (_autoRefresh) {
			refresh();
		}

		return _destinationStatistics.getActiveThreadCount();
	}

	@Override
	public int getCurrentThreadCount() {
		if (_autoRefresh || (_destinationStatistics == null)) {
			refresh();
		}

		return _destinationStatistics.getCurrentThreadCount();
	}

	@Override
	public int getLargestThreadCount() {
		if (_autoRefresh || (_destinationStatistics == null)) {
			refresh();
		}

		return _destinationStatistics.getLargestThreadCount();
	}

	@Override
	public String getLastRefresh() {
		return String.valueOf(_lastRefresh);
	}

	@Override
	public int getMaxThreadPoolSize() {
		if (_autoRefresh || (_destinationStatistics == null)) {
			refresh();
		}

		return _destinationStatistics.getMaxThreadPoolSize();
	}

	@Override
	public int getMinThreadPoolSize() {
		if (_autoRefresh || (_destinationStatistics == null)) {
			refresh();
		}

		return _destinationStatistics.getMinThreadPoolSize();
	}

	@Override
	public long getPendingMessageCount() {
		if (_autoRefresh || (_destinationStatistics == null)) {
			refresh();
		}

		return _destinationStatistics.getPendingMessageCount();
	}

	@Override
	public long getSentMessageCount() {
		if (_autoRefresh || (_destinationStatistics == null)) {
			refresh();
		}

		return _destinationStatistics.getSentMessageCount();
	}

	@Override
	public boolean isAutoRefresh() {
		return _autoRefresh;
	}

	@Override
	public void refresh() {
		if (System.currentTimeMillis() > _lastRefresh) {
			_lastRefresh = System.currentTimeMillis();
			_destinationStatistics = _destination.getDestinationStatistics();
		}
	}

	@Override
	public void setAutoRefresh(boolean autoRefresh) {
		_autoRefresh = autoRefresh;
	}

	private static final String _OBJECT_NAME_PREFIX =
		"Liferay:product=Portal,type=MessagingDestinationStatistics,name=";

	private boolean _autoRefresh;
	private Destination _destination;
	private DestinationStatistics _destinationStatistics;
	private long _lastRefresh;

}