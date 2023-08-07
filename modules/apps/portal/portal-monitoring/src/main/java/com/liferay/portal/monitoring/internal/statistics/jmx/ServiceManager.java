/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.statistics.jmx;

import com.liferay.portal.monitoring.internal.statistics.service.ServerStatisticsHelper;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	enabled = false,
	property = {
		"jmx.objectname=com.liferay.portal.monitoring:classification=service_statistic,name=ServiceManager",
		"jmx.objectname.cache.key=ServiceManager"
	},
	service = DynamicMBean.class
)
public class ServiceManager
	extends StandardMBean implements ServiceManagerMBean {

	public ServiceManager() throws NotCompliantMBeanException {
		super(ServiceManagerMBean.class);
	}

	@Override
	public long getErrorCount(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatisticsHelper.getErrorCount(
			className, methodName, parameterTypes);
	}

	@Override
	public long getMaxTime(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatisticsHelper.getMaxTime(
			className, methodName, parameterTypes);
	}

	@Override
	public long getMinTime(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatisticsHelper.getMinTime(
			className, methodName, parameterTypes);
	}

	@Override
	public long getRequestCount(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatisticsHelper.getRequestCount(
			className, methodName, parameterTypes);
	}

	@Reference
	private ServerStatisticsHelper _serverStatisticsHelper;

}