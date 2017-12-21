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

package com.liferay.portal.monitoring.internal.statistics;

import com.liferay.portal.kernel.monitoring.MonitoringException;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface SummaryStatistics {

	public long getAverageTime() throws MonitoringException;

	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException;

	public long getAverageTimeByCompany(String webId)
		throws MonitoringException;

	public long getErrorCount() throws MonitoringException;

	public long getErrorCountByCompany(long companyId)
		throws MonitoringException;

	public long getErrorCountByCompany(String webId) throws MonitoringException;

	public long getMaxTime() throws MonitoringException;

	public long getMaxTimeByCompany(long companyId) throws MonitoringException;

	public long getMaxTimeByCompany(String webId) throws MonitoringException;

	public long getMinTime() throws MonitoringException;

	public long getMinTimeByCompany(long companyId) throws MonitoringException;

	public long getMinTimeByCompany(String webId) throws MonitoringException;

	public long getRequestCount() throws MonitoringException;

	public long getRequestCountByCompany(long companyId)
		throws MonitoringException;

	public long getRequestCountByCompany(String webId)
		throws MonitoringException;

	public long getSuccessCount() throws MonitoringException;

	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException;

	public long getSuccessCountByCompany(String webId)
		throws MonitoringException;

	public long getTimeoutCount() throws MonitoringException;

	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException;

	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException;

}