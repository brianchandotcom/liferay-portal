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

package com.liferay.portal.workflow.metrics.sla.calendar;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.Locale;

/**
 * @author Rafael Praxedes
 */
public interface WorkflowMetricsSLACalendar {

	public Duration getDuration(
		LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime);

	public LocalDateTime getOverdueLocalDateTime(
		LocalDateTime nowLocalDateTime, Duration remainingDuration);

	public String getTitle(Locale locale);

}