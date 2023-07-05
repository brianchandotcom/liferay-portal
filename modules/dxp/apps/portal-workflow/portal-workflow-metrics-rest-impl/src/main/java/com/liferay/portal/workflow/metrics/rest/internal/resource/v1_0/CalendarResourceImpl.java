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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Calendar;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.CalendarResource;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendarRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/calendar.properties",
	scope = ServiceScope.PROTOTYPE, service = CalendarResource.class
)
public class CalendarResourceImpl extends BaseCalendarResourceImpl {

	@Override
	public Page<Calendar> getCalendarsPage() {
		List<Calendar> calendars = new ArrayList<>();

		for (WorkflowMetricsSLACalendar workflowMetricsSLACalendar :
				_workflowMetricsSLACalendarRegistry.
					getWorkflowMetricsSLACalendars()) {

			calendars.add(
				new Calendar() {
					{
						defaultCalendar = Objects.equals(
							workflowMetricsSLACalendar.getKey(),
							WorkflowMetricsSLACalendar.DEFAULT_KEY);
						key = workflowMetricsSLACalendar.getKey();
						title = workflowMetricsSLACalendar.getTitle(
							contextAcceptLanguage.getPreferredLocale());
					}
				});
		}

		return Page.of(calendars);
	}

	@Reference
	private WorkflowMetricsSLACalendarRegistry
		_workflowMetricsSLACalendarRegistry;

}