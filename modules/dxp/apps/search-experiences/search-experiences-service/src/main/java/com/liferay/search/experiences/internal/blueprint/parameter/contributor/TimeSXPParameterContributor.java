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

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributionDefinition;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=time",
	service = SXPParameterContributor.class
)
public class TimeSXPParameterContributor implements SXPParameterContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		_addParameters(searchRequestBuilder, sxpParameterDataBuilder);
	}

	@Override
	public String getCategoryNameKey() {
		return "time";
	}

	@Override
	public List<SXPParameterContributionDefinition>
		getSXPParameterContributionDefinitions() {

		return ListUtil.fromArray(
			new SXPParameterContributionDefinition(
				DateSXPParameter.class.getName(), "current-date",
				"time.current_date"),
			new SXPParameterContributionDefinition(
				IntegerSXPParameter.class.getName(), "current-day-of-month",
				"time.current_day_of_month"),
			new SXPParameterContributionDefinition(
				IntegerSXPParameter.class.getName(), "current-day-of-week",
				"time.current_day_of_week"),
			new SXPParameterContributionDefinition(
				IntegerSXPParameter.class.getName(), "current-day-of-year",
				"time.current_day_of_year"),
			new SXPParameterContributionDefinition(
				IntegerSXPParameter.class.getName(), "current-hour",
				"time.current_hour"),
			new SXPParameterContributionDefinition(
				IntegerSXPParameter.class.getName(), "current-year",
				"time.current_year"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "time-of-day",
				"time.time_of_day"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "timezone-locale-name",
				"time.timezone_locale_name"));
	}

	private void _addParameters(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		TimeZone timeZone = _getTimeZone(searchRequestBuilder);

		LocalDateTime localDateTime = LocalDateTime.now(timeZone.toZoneId());

		ZonedDateTime zonedDateTime = localDateTime.atZone(timeZone.toZoneId());

		Date now = Date.from(zonedDateTime.toInstant());

		sxpParameterDataBuilder.addSXPParameter(
			new DateSXPParameter("time.current_date", true, now));

		sxpParameterDataBuilder.addSXPParameter(
			new IntegerSXPParameter(
				"time.current_day_of_month", true,
				localDateTime.getDayOfMonth()));

		DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

		sxpParameterDataBuilder.addSXPParameter(
			new IntegerSXPParameter(
				"time.current_day_of_week", true, dayOfWeek.getValue()));

		sxpParameterDataBuilder.addSXPParameter(
			new IntegerSXPParameter(
				"time.current_day_of_year", true,
				localDateTime.getDayOfYear()));

		sxpParameterDataBuilder.addSXPParameter(
			new IntegerSXPParameter(
				"time.current_hour", true, localDateTime.getHour()));

		sxpParameterDataBuilder.addSXPParameter(
			new IntegerSXPParameter(
				"time.current_year", true, localDateTime.getYear()));

		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter(
				"time.time_of_day", true,
				_getTimeOfTheDay(localDateTime.toLocalTime())));

		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter(
				"time.timezone_locale_name", true,
				timeZone.getDisplayName(
					SearchContextUtil.getLocale(searchRequestBuilder))));
	}

	private String _getTimeOfTheDay(LocalTime localTime) {
		if (_isTimebetween(localTime, _MORNING, _AFTER_NOON)) {
			return "morning";
		}
		else if (_isTimebetween(localTime, _AFTER_NOON, _EVENING)) {
			return "afternoon";
		}
		else if (_isTimebetween(localTime, _EVENING, _NIGHT)) {
			return "evening";
		}
		else {
			return "night";
		}
	}

	private TimeZone _getTimeZone(SearchRequestBuilder searchRequestBuilder) {
		TimeZone timeZone = SearchContextUtil.getTimeZone(searchRequestBuilder);

		if (!Objects.isNull(timeZone)) {
			return timeZone;
		}

		return TimeZoneUtil.getDefault();
	}

	private boolean _isTimebetween(
		LocalTime time, LocalTime start, LocalTime end) {

		if (!time.isBefore(start) && time.isBefore(end)) {
			return true;
		}

		return false;
	}

	private static final LocalTime _AFTER_NOON = LocalTime.of(12, 0, 0);

	private static final LocalTime _EVENING = LocalTime.of(17, 0, 0);

	private static final LocalTime _MORNING = LocalTime.of(4, 0, 0);

	private static final LocalTime _NIGHT = LocalTime.of(20, 0, 0);

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}