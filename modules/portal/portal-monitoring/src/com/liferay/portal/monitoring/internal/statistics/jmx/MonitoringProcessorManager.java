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

import com.liferay.portal.kernel.monitoring.Level;
import com.liferay.portal.kernel.monitoring.MonitoringControl;

import java.util.Set;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"object-name=com.liferay.portal.monitoring:classification=monitoring_service,name=MonitoringProcessorManager",
		"object-name-cache-key=MonitoringProcessorManager"
	},
	service = DynamicMBean.class
)
public class MonitoringProcessorManager extends StandardMBean
	implements MonitoringProcessorManagerMBean {

	public MonitoringProcessorManager() throws NotCompliantMBeanException {
		super(MonitoringProcessorManagerMBean.class);
	}

	@Override
	public String getLevel(String namespace) {
		Level level = _monitoringControl.getLevel(namespace);

		if (level == null) {
			level = Level.OFF;
		}

		return level.toString();
	}

	@Override
	public String[] getNamespaces() {
		Set<String> namespaces = _monitoringControl.getNamespaces();

		return namespaces.toArray(new String[namespaces.size()]);
	}

	@Override
	public void setLevel(String namespace, String levelName) {
		Level level = Level.valueOf(levelName);

		_monitoringControl.setLevel(namespace, level);
	}

	@Reference
	protected void setMonitoringControl(MonitoringControl monitoringControl) {

		_monitoringControl = monitoringControl;
	}

	private MonitoringControl _monitoringControl;

}