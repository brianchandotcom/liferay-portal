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

package com.liferay.portal.messaging.internal.jmx;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface DestinationStatisticsManagerMBean {

	public int getActiveThreadCount();

	public int getCurrentThreadCount();

	public int getLargestThreadCount();

	public String getLastRefresh();

	public int getMaxThreadPoolSize();

	public int getMinThreadPoolSize();

	public long getPendingMessageCount();

	public long getSentMessageCount();

	public boolean isAutoRefresh();

	public void refresh();

	public void setAutoRefresh(boolean autoRefresh);

}