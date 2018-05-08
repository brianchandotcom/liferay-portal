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

package com.liferay.portal.monitoring.internal.statistics.jmx;

import com.liferay.portal.monitoring.internal.statistics.portlet.ResourceRequestSummaryStatistics;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"jmx.objectname=com.liferay.portal.monitoring:classification=portlet_statistic,name=ResourceRequestPortletManager",
		"jmx.objectname.cache.key=ResourceRequestPortletManager"
	},
	service = DynamicMBean.class
)
public class ResourceRequestPortletManager extends PortletManager {

	public ResourceRequestPortletManager() throws NotCompliantMBeanException {
		super(PortletManagerMBean.class);
	}

	@Reference(unbind = "-")
	protected void setResourceRequestSummaryStatistics(
		ResourceRequestSummaryStatistics resourceRequestSummaryStatistics) {

		super.setPortletSummaryStatistics(resourceRequestSummaryStatistics);
	}

}