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