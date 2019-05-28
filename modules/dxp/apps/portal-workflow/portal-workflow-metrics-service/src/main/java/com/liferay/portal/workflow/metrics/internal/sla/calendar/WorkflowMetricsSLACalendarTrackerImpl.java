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

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendarTracker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = false, service = WorkflowMetricsSLACalendarTracker.class)
public class WorkflowMetricsSLACalendarTrackerImpl
	implements WorkflowMetricsSLACalendarTracker {

	@Override
	public WorkflowMetricsSLACalendar getWorkflowMetricsSLACalendar(
		String key) {

		return _workflowMetricsSLACalendars.getOrDefault(
			key, _defaultWorkflowMetricsSLACalendar);
	}

	@Override
	public Map<String, String> getWorkflowMetricsSLACalendarTitles(
		Locale locale) {

		return Stream.of(
			_workflowMetricsSLACalendars.entrySet()
		).flatMap(
			Set::stream
		).collect(
			Collectors.toMap(
				entry -> entry.getKey(),
				entry -> {
					WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
						entry.getValue();

					return workflowMetricsSLACalendar.getTitle(locale);
				})
		);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addWorkflowMetricsSLACalendar(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar,
		Map<String, Object> properties) {

		String key = MapUtil.getString(properties, "sla.calendar.key");

		_workflowMetricsSLACalendars.put(key, workflowMetricsSLACalendar);
	}

	@Deactivate
	protected void deactivate() {
		_workflowMetricsSLACalendars.clear();
	}

	protected void removeWorkflowMetricsSLACalendar(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar,
		Map<String, Object> properties) {

		String key = MapUtil.getString(properties, "sla.calendar.key");

		_workflowMetricsSLACalendars.remove(key);
	}

	@Reference(target = "(sla.calendar.key=default)")
	private WorkflowMetricsSLACalendar _defaultWorkflowMetricsSLACalendar;

	private final Map<String, WorkflowMetricsSLACalendar>
		_workflowMetricsSLACalendars = new HashMap<>();

}