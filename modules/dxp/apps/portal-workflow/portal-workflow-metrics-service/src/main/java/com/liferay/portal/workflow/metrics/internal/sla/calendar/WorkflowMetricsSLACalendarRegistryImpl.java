/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.sla.calendar;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendarRegistry;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rafael Praxedes
 */
@Component(service = WorkflowMetricsSLACalendarRegistry.class)
public class WorkflowMetricsSLACalendarRegistryImpl
	implements WorkflowMetricsSLACalendarRegistry {

	@Override
	public WorkflowMetricsSLACalendar getWorkflowMetricsSLACalendar(
		String key) {

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			_serviceTrackerMap.getService(key);

		if (workflowMetricsSLACalendar != null) {
			return workflowMetricsSLACalendar;
		}

		return _serviceTrackerMap.getService(
			WorkflowMetricsSLACalendar.DEFAULT_KEY);
	}

	@Override
	public Collection<WorkflowMetricsSLACalendar>
		getWorkflowMetricsSLACalendars() {

		return _serviceTrackerMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, WorkflowMetricsSLACalendar.class, null,
			ServiceReferenceMapperFactory.createFromFunction(
				bundleContext, WorkflowMetricsSLACalendar::getKey));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, WorkflowMetricsSLACalendar>
		_serviceTrackerMap;

}