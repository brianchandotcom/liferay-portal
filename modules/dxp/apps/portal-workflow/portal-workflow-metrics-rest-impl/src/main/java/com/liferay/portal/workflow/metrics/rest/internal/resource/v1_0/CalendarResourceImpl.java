/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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