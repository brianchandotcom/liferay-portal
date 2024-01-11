/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.jmx;

import com.liferay.portal.monitoring.internal.statistics.portlet.PortletSummaryStatistics;
import com.liferay.portal.monitoring.internal.statistics.portlet.ResourceRequestSummaryStatistics;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	enabled = false,
	property = {
		"jmx.objectname=com.liferay.portal.monitoring:classification=portlet_statistic,name=ResourceRequestPortletManager",
		"jmx.objectname.cache.key=ResourceRequestPortletManager"
	},
	service = DynamicMBean.class
)
public class ResourceRequestPortletManager extends BasePortletManager {

	public ResourceRequestPortletManager() throws NotCompliantMBeanException {
		super(PortletManagerMBean.class);
	}

	@Activate
	protected void activate() {
		_portletSummaryStatistics = new ResourceRequestSummaryStatistics(
			serverStatisticsHelper);
	}

	@Override
	protected PortletSummaryStatistics getPortletSummaryStatistics() {
		return _portletSummaryStatistics;
	}

	private PortletSummaryStatistics _portletSummaryStatistics;

}