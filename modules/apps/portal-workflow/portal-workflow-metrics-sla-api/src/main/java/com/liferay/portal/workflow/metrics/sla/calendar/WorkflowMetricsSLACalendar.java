/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.sla.calendar;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.Locale;

/**
 * @author Inácio Nery
 */
public interface WorkflowMetricsSLACalendar {

	public static final String DEFAULT_KEY = "default";

	public Duration getDuration(
		LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime);

	public String getKey();

	public LocalDateTime getOverdueLocalDateTime(
		LocalDateTime nowLocalDateTime, Duration remainingDuration);

	public String getTitle(Locale locale);

}