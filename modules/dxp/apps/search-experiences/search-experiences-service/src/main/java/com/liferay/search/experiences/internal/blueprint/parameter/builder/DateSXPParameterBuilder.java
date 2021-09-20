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

package com.liferay.search.experiences.internal.blueprint.parameter.builder;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=date",
	service = SXPParameterBuilder.class
)
public class DateSXPParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(JSONObject jsonObject, 
			SearchRequestBuilder searchRequestBuilder) {

		String parameterName = jsonObject.getString(
			"parameter_name");

		String dateString = SearchContextUtil.getStringAttribute(
				parameterName, searchRequestBuilder);

		if (Validator.isBlank(dateString)) {
			return Optional.empty();
		}

		String timeZoneId = _getTimeZoneId(searchRequestBuilder);

		if (Validator.isBlank(dateString) || Validator.isBlank(timeZoneId)) {
			return Optional.empty();
		}

		Date date = _getDate(dateString, jsonObject, timeZoneId);

		if (date == null) {
			return Optional.empty();
		}

		return Optional.of(
			new DateSXPParameter(
				parameterName, true, date));
	}

	private Date _getDate(
		String dateString, JSONObject jsonObject, 
		String timeZoneId) {

		String dateFormat = jsonObject.getString("date_format");

		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
				dateFormat);

			LocalDate localDate = LocalDate.parse(
				dateString, dateTimeFormatter);

			TimeZone timeZone = TimeZoneUtil.getTimeZone(timeZoneId);

			GregorianCalendar calendar = GregorianCalendar.from(
				localDate.atStartOfDay(timeZone.toZoneId()));

			return calendar.getTime();
		}
		catch (Exception exception) {
			_log.error(
				String.format(
					"Cannot parse date from string '%s'", dateString,
					exception));
		}

		return null;
	}

	private String _getTimeZoneId(
			SearchRequestBuilder searchRequestBuilder) {
		
		return searchRequestBuilder.withSearchContextGet(
				searchContext -> {
					
					TimeZone timeZone = searchContext.getTimeZone();

					if (timeZone != null) {
						return timeZone.getID();
					}
					
					TimeZone defaultTimeZone = TimeZoneUtil.getDefault();
					
					return defaultTimeZone.getID();

				});
		
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateSXPParameterBuilder.class);
}