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

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendar;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = false, property = "sla.calendar.key=default",
	service = WorkflowMetricsSLACalendar.class
)
public class DefaultWorkflowMetricsSLACalendar
	implements WorkflowMetricsSLACalendar {

	@Override
	public Duration getDuration(
		LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {

		return Duration.between(startLocalDateTime, endLocalDateTime);
	}

	@Override
	public LocalDateTime getOverdueLocalDateTime(
		LocalDateTime nowLocalDateTime, Duration remainingDuration) {

		return nowLocalDateTime.plus(
			remainingDuration.toMillis(), ChronoUnit.MILLIS);
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getBundle(
				locale, DefaultWorkflowMetricsSLACalendar.class),
			"default-calendar-title");
	}

	@Reference
	private Language _language;

}