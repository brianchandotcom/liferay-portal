/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.kernel.monitoring.Level;
import com.liferay.portal.kernel.monitoring.MonitoringProcessor;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class MonitoringProcessorManager
	implements MonitoringProcessorManagerMBean {

	@Override
	public String getLevel(String namespace) {
		Level level = _monitoringProcessor.getLevel(namespace);

		if (level == null) {
			level = Level.OFF;
		}

		return level.toString();
	}

	@Override
	public String[] getNamespaces() {
		Set<String> namespaces = _monitoringProcessor.getNamespaces();

		return namespaces.toArray(new String[namespaces.size()]);
	}

	@Override
	public void setLevel(String namespace, String levelName) {
		Level level = Level.valueOf(levelName);

		_monitoringProcessor.setLevel(namespace, level);
	}

	public void setMonitoringProcessor(
		MonitoringProcessor monitoringProcessor) {

		_monitoringProcessor = monitoringProcessor;
	}

	private MonitoringProcessor _monitoringProcessor;

}