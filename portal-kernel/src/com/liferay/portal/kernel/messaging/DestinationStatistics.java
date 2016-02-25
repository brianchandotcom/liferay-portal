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

package com.liferay.portal.kernel.messaging;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DestinationStatistics {

	public int getActiveThreadCount() {
		return _activeThreadCount;
	}

	public int getCurrentThreadCount() {
		return _currentThreadCount;
	}

	public int getLargestThreadCount() {
		return _largestThreadCount;
	}

	public int getMaxThreadPoolSize() {
		return _maxThreadPoolSize;
	}

	public int getMinThreadPoolSize() {
		return _minThreadPoolSize;
	}

	public long getPendingMessageCount() {
		return _pendingMessageCount;
	}

	public long getSentMessageCount() {
		return _sentMessageCount;
	}

	public void setActiveThreadCount(int activeThreadCount) {
		_activeThreadCount = activeThreadCount;
	}

	public void setCurrentThreadCount(int currentThreadCount) {
		_currentThreadCount = currentThreadCount;
	}

	public void setLargestThreadCount(int largestThreadCount) {
		_largestThreadCount = largestThreadCount;
	}

	public void setMaxThreadPoolSize(int maxThreadPoolSize) {
		_maxThreadPoolSize = maxThreadPoolSize;
	}

	public void setMinThreadPoolSize(int minThreadPoolSize) {
		_minThreadPoolSize = minThreadPoolSize;
	}

	public void setPendingMessageCount(long pendingMessageCount) {
		_pendingMessageCount = pendingMessageCount;
	}

	public void setSentMessageCount(long sentMessageCount) {
		_sentMessageCount = sentMessageCount;
	}

	private int _activeThreadCount;
	private int _currentThreadCount;
	private int _largestThreadCount;
	private int _maxThreadPoolSize;
	private int _minThreadPoolSize;
	private long _pendingMessageCount;
	private long _sentMessageCount;

}