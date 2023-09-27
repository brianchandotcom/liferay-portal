/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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