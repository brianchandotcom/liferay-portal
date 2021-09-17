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

package com.liferay.search.experiences.internal.parameter.contributor;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.search.experiences.attributes.SXPAttributes;
import com.liferay.search.experiences.internal.attributes.util.SXPAttributeValueHelper;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.parameter.DateParameter;
import com.liferay.search.experiences.parameter.IntegerParameter;
import com.liferay.search.experiences.parameter.SXPParameterContributor;
import com.liferay.search.experiences.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.parameter.SXPParameterDefinition;
import com.liferay.search.experiences.parameter.StringParameter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, SXPBlueprint sxpBlueprint) {

		TimeZone timeZone = _getTimeZone(sxpAttributes);

		_addParameters(sxpParameterDataBuilder, sxpAttributes, timeZone);
	}

	@Override
	public String getCategoryNameKey() {
		return "time";
	}

	@Override
	public List<SXPParameterDefinition> getParameterDefinitions() {
		return ListUtil.fromArray(
			new SXPParameterDefinition(
				_getTemplateVariableName("current_date"),
				DateParameter.class.getName(),
				"core.parameter.time.current-date"),
			new SXPParameterDefinition(
				_getTemplateVariableName("current_day_of_month"),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-day-of-month"),
			new SXPParameterDefinition(
				_getTemplateVariableName("current_day_of_week"),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-day-of-week"),
			new SXPParameterDefinition(
				_getTemplateVariableName("current_day_of_year"),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-day-of-year"),
			new SXPParameterDefinition(
				_getTemplateVariableName("current_hour"),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-hour"),
			new SXPParameterDefinition(
				_getTemplateVariableName("current_year"),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-year"),
			new SXPParameterDefinition(
				_getTemplateVariableName("time_of_day"),
				StringParameter.class.getName(),
				"core.parameter.time.time-of-day"),
			new SXPParameterDefinition(
				_getTemplateVariableName("timezone_locale_name"),
				StringParameter.class.getName(),
				"core.parameter.time.timezone-locale-name"));
	}

	private void _addParameters(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, TimeZone timeZone) {

		LocalDateTime localDateTime = LocalDateTime.now(timeZone.toZoneId());

		ZonedDateTime zonedDateTime = localDateTime.atZone(timeZone.toZoneId());

		Date now = Date.from(zonedDateTime.toInstant());

		sxpParameterDataBuilder.addParameter(
			new DateParameter(
				"current_date", _getTemplateVariableName("current_date"), now));

		sxpParameterDataBuilder.addParameter(
			new IntegerParameter(
				"current_day_of_month",
				_getTemplateVariableName("current_day_of_month"),
				localDateTime.getDayOfMonth()));

		DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

		sxpParameterDataBuilder.addParameter(
			new IntegerParameter(
				"current_day_of_week",
				_getTemplateVariableName("current_day_of_week"),
				dayOfWeek.getValue()));

		sxpParameterDataBuilder.addParameter(
			new IntegerParameter(
				"current_day_of_year",
				_getTemplateVariableName("current_day_of_year"),
				localDateTime.getDayOfYear()));

		sxpParameterDataBuilder.addParameter(
			new IntegerParameter(
				"current_hour", _getTemplateVariableName("current_hour"),
				localDateTime.getHour()));

		sxpParameterDataBuilder.addParameter(
			new IntegerParameter(
				"current_year", _getTemplateVariableName("current_year"),
				localDateTime.getYear()));

		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"time_of_day", _getTemplateVariableName("time_of_day"),
				_getTimeOfTheDay(localDateTime.toLocalTime())));

		sxpParameterDataBuilder.addParameter(
			new StringParameter(
				"timezone_locale_name",
				_getTemplateVariableName("timezone_locale_name"),
				timeZone.getDisplayName(sxpAttributes.getLocale())));
	}

	private String _getTemplateVariableName(String key) {
		StringBundler sb = new StringBundler(3);

		sb.append("${time.");
		sb.append(key);
		sb.append("}");

		return sb.toString();
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

	private TimeZone _getTimeZone(SXPAttributes sxpAttributes) {
		Optional<String> optional = _sxpAttributeValuesHelper.getStringOptional(
			sxpAttributes, "timezone_id");

		if (optional.isPresent()) {
			return TimeZoneUtil.getTimeZone(optional.get());
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
	private SXPAttributeValueHelper _sxpAttributeValuesHelper;

	@Reference
	private UserLocalService _userLocalService;

}