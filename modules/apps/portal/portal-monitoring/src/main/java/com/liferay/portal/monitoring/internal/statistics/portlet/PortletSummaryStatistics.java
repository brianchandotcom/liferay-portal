/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.portlet;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.monitoring.internal.statistics.SummaryStatistics;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface PortletSummaryStatistics extends SummaryStatistics {

	public long getAverageTimeByPortlet(String portletId)
		throws MonitoringException;

	public long getAverageTimeByPortlet(String portletId, long companyId)
		throws MonitoringException;

	public long getAverageTimeByPortlet(String portletId, String webId)
		throws MonitoringException;

	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException;

	public long getErrorCountByPortlet(String portletId, long companyId)
		throws MonitoringException;

	public long getErrorCountByPortlet(String portletId, String webId)
		throws MonitoringException;

	public long getMaxTimeByPortlet(String portletId)
		throws MonitoringException;

	public long getMaxTimeByPortlet(String portletId, long companyId)
		throws MonitoringException;

	public long getMaxTimeByPortlet(String portletId, String webId)
		throws MonitoringException;

	public long getMinTimeByPortlet(String portletId)
		throws MonitoringException;

	public long getMinTimeByPortlet(String portletId, long companyId)
		throws MonitoringException;

	public long getMinTimeByPortlet(String portletId, String webId)
		throws MonitoringException;

	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException;

	public long getRequestCountByPortlet(String portletId, long companyId)
		throws MonitoringException;

	public long getRequestCountByPortlet(String portletId, String webId)
		throws MonitoringException;

	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException;

	public long getSuccessCountByPortlet(String portletId, long companyId)
		throws MonitoringException;

	public long getSuccessCountByPortlet(String portletId, String webId)
		throws MonitoringException;

	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException;

	public long getTimeoutCountByPortlet(String portletId, long companyId)
		throws MonitoringException;

	public long getTimeoutCountByPortlet(String portletId, String webId)
		throws MonitoringException;

}