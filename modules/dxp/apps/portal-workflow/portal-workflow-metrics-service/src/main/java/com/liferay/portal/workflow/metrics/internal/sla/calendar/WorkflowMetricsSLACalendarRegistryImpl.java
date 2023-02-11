/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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