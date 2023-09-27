/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.jmx;

import com.liferay.portal.monitoring.internal.statistics.portlet.PortletSummaryStatistics;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	enabled = false,
	property = {
		"jmx.objectname=com.liferay.portal.monitoring:classification=portlet_statistic,name=RenderRequestPortletManager",
		"jmx.objectname.cache.key=RenderRequestPortletManager"
	},
	service = DynamicMBean.class
)
public class RenderRequestPortletManager extends BasePortletManager {

	public RenderRequestPortletManager() throws NotCompliantMBeanException {
		super(PortletManagerMBean.class);
	}

	@Override
	protected PortletSummaryStatistics getPortletSummaryStatistics() {
		return _portletSummaryStatistics;
	}

	@Reference(
		target = "(component.name=com.liferay.portal.monitoring.internal.statistics.portlet.RenderRequestSummaryStatistics)"
	)
	private PortletSummaryStatistics _portletSummaryStatistics;

}