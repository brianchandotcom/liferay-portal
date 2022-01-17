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

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.internal.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.beans.ExceptionListener;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Petteri Karttunen
 */
public class TimeSXPParameterContributor implements SXPParameterContributor {

	@Override
	public void contribute(
		ExceptionListener exceptionListener, SearchContext searchContext,
		SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters) {

		TimeZone timeZone = searchContext.getTimeZone();

		if (timeZone == null) {
			return;
		}

		LocalDateTime localDateTime = LocalDateTime.now(timeZone.toZoneId());

		ZonedDateTime zonedDateTime = localDateTime.atZone(timeZone.toZoneId());

		sxpParameters.add(
			new DateSXPParameter(
				"time.current_date", true,
				Date.from(zonedDateTime.toInstant())));

		sxpParameters.add(
			new IntegerSXPParameter(
				"time.current_day_of_month", true,
				localDateTime.getDayOfMonth()));

		DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

		sxpParameters.add(
			new IntegerSXPParameter(
				"time.current_day_of_week", true, dayOfWeek.getValue()));

		sxpParameters.add(
			new IntegerSXPParameter(
				"time.current_day_of_year", true,
				localDateTime.getDayOfYear()));
		sxpParameters.add(
			new IntegerSXPParameter(
				"time.current_hour", true, localDateTime.getHour()));
		sxpParameters.add(
			new IntegerSXPParameter(
				"time.current_year", true, localDateTime.getYear()));
		sxpParameters.add(
			new StringSXPParameter(
				"time.time_of_day", true,
				_getTimeOfDay(localDateTime.toLocalTime())));
		sxpParameters.add(
			new StringSXPParameter(
				"time.time_zone_name_localized", true,
				timeZone.getDisplayName(searchContext.getLocale())));
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		return "time";
	}

	@Override
	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions(long companyId) {

		return Arrays.asList(
			new SXPParameterContributorDefinition(
				DateSXPParameter.class, "current-date", "time.current_date"),
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "current-day-of-month",
				"time.current_day_of_month"),
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "current-day-of-week",
				"time.current_day_of_week"),
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "current-day-of-year",
				"time.current_day_of_year"),
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "current-hour", "time.current_hour"),
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "current-year", "time.current_year"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "time-of-day", "time.time_of_day"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "time-zone-name-localized",
				"time.time_zone_name_localized"));
	}

	private String _getTimeOfDay(LocalTime localTime) {
		if (_isBetween(localTime, _LOCAL_TIME_04, _LOCAL_TIME_12)) {
			return "morning";
		}
		else if (_isBetween(localTime, _LOCAL_TIME_12, _LOCAL_TIME_17)) {
			return "afternoon";
		}
		else if (_isBetween(localTime, _LOCAL_TIME_17, _LOCAL_TIME_20)) {
			return "evening";
		}

		return "night";
	}

	private boolean _isBetween(
		LocalTime localTime, LocalTime startLocalTime, LocalTime endLocalTime) {

		if (!localTime.isBefore(startLocalTime) &&
			localTime.isBefore(endLocalTime)) {

			return true;
		}

		return false;
	}

	private static final LocalTime _LOCAL_TIME_04 = LocalTime.of(4, 0, 0);

	private static final LocalTime _LOCAL_TIME_12 = LocalTime.of(12, 0, 0);

	private static final LocalTime _LOCAL_TIME_17 = LocalTime.of(17, 0, 0);

	private static final LocalTime _LOCAL_TIME_20 = LocalTime.of(20, 0, 0);

}