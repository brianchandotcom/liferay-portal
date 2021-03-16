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

package com.liferay.portal.search.tuning.blueprints.engine.internal.parameter.contributor;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.BlueprintsAttributesHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.DateParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.IntegerParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataBuilder;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDefinition;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter;
import com.liferay.portal.search.tuning.blueprints.engine.spi.parameter.ParameterContributor;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import java.util.ArrayList;
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
	service = ParameterContributor.class
)
public class TimeParameterContributor implements ParameterContributor {

	@Override
	public void contribute(
		ParameterDataBuilder parameterDataBuilder, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		TimeZone timeZone = _getTimeZone(blueprintsAttributes);

		_addParameters(parameterDataBuilder, blueprintsAttributes, timeZone);
	}

	@Override
	public String getCategoryNameKey() {
		return "time";
	}

	@Override
	public List<ParameterDefinition> getParameterDefinitions() {
		List<ParameterDefinition> parameterDefinitions = new ArrayList<>();

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_DATE.getKey()),
				DateParameter.class.getName(),
				"core.parameter.time.current-date"));
		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_DAY_OF_MONTH.getKey()),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-day-of-month"));
		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_DAY_OF_WEEK.getKey()),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-day-of-week"));
		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_DAY_OF_YEAR.getKey()),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-day-of-year"));
		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_HOUR.getKey()),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-hour"));
		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_YEAR.getKey()),
				IntegerParameter.class.getName(),
				"core.parameter.time.current-year"));
		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.TIME_OF_DAY.getKey()),
				StringParameter.class.getName(),
				"core.parameter.time.time-of-day"));
		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.TIMEZONE_LOCALE_NAME.getKey()),
				StringParameter.class.getName(),
				"core.parameter.time.timezone-locale-name"));

		return parameterDefinitions;
	}

	private void _addParameters(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes, TimeZone timeZone) {

		LocalDateTime localDateTime = LocalDateTime.now(timeZone.toZoneId());

		ZonedDateTime zonedDateTime = localDateTime.atZone(timeZone.toZoneId());

		Date now = Date.from(zonedDateTime.toInstant());

		parameterDataBuilder.addParameter(
			new DateParameter(
				ReservedParameterNames.CURRENT_DATE.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_DATE.getKey()),
				now));

		parameterDataBuilder.addParameter(
			new IntegerParameter(
				ReservedParameterNames.CURRENT_DAY_OF_MONTH.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_DAY_OF_MONTH.getKey()),
				localDateTime.getDayOfMonth()));

		DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

		parameterDataBuilder.addParameter(
			new IntegerParameter(
				ReservedParameterNames.CURRENT_DAY_OF_WEEK.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_DAY_OF_WEEK.getKey()),
				dayOfWeek.getValue()));

		parameterDataBuilder.addParameter(
			new IntegerParameter(
				ReservedParameterNames.CURRENT_DAY_OF_YEAR.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_DAY_OF_YEAR.getKey()),
				localDateTime.getDayOfYear()));

		parameterDataBuilder.addParameter(
			new IntegerParameter(
				ReservedParameterNames.CURRENT_HOUR.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_HOUR.getKey()),
				localDateTime.getHour()));

		parameterDataBuilder.addParameter(
			new IntegerParameter(
				ReservedParameterNames.CURRENT_YEAR.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.CURRENT_YEAR.getKey()),
				localDateTime.getYear()));

		parameterDataBuilder.addParameter(
			new StringParameter(
				ReservedParameterNames.TIME_OF_DAY.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.TIME_OF_DAY.getKey()),
				_getTimeOfTheDay(localDateTime.toLocalTime())));

		parameterDataBuilder.addParameter(
			new StringParameter(
				ReservedParameterNames.TIMEZONE_LOCALE_NAME.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.TIMEZONE_LOCALE_NAME.getKey()),
				timeZone.getDisplayName(blueprintsAttributes.getLocale())));
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

	private TimeZone _getTimeZone(BlueprintsAttributes blueprintsAttributes) {
		Optional<String> optional =
			_blueprintsAttributesHelper.getStringOptional(
				blueprintsAttributes,
				ReservedParameterNames.TIMEZONE_ID.getKey());

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
	private BlueprintsAttributesHelper _blueprintsAttributesHelper;

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}