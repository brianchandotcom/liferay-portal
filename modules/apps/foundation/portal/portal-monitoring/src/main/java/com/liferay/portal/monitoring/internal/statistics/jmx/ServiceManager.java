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

import com.liferay.portal.monitoring.internal.statistics.service.ServerStatistics;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	enabled = false, immediate = true,
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

		return _serverStatistics.getErrorCount(
			className, methodName, parameterTypes);
	}

	@Override
	public long getMaxTime(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatistics.getMaxTime(
			className, methodName, parameterTypes);
	}

	@Override
	public long getMinTime(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatistics.getMinTime(
			className, methodName, parameterTypes);
	}

	@Override
	public long getRequestCount(
		String className, String methodName, String[] parameterTypes) {

		return _serverStatistics.getRequestCount(
			className, methodName, parameterTypes);
	}

	@Reference(unbind = "-")
	protected void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	private ServerStatistics _serverStatistics;

}