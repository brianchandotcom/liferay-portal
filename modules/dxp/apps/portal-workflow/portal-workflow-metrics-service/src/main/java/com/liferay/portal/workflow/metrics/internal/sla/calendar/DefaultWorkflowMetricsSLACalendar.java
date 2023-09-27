/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.sla.calendar;

import com.liferay.portal.kernel.language.Language;
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
@Component(immediate = false, service = WorkflowMetricsSLACalendar.class)
public class DefaultWorkflowMetricsSLACalendar
	implements WorkflowMetricsSLACalendar {

	@Override
	public Duration getDuration(
		LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) {

		return Duration.between(startLocalDateTime, endLocalDateTime);
	}

	@Override
	public String getKey() {
		return WorkflowMetricsSLACalendar.DEFAULT_KEY;
	}

	@Override
	public LocalDateTime getOverdueLocalDateTime(
		LocalDateTime nowLocalDateTime, Duration remainingDuration) {

		return nowLocalDateTime.plus(
			remainingDuration.toMillis(), ChronoUnit.MILLIS);
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "default-calendar-title");
	}

	@Reference
	private Language _language;

}