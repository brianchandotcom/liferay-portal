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

package com.liferay.search.experiences.internal.blueprint.parameter.visitor;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.blueprint.parameter.BooleanSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.FloatSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public class ToSubstitutionStringVisitor
	implements SXPParameter.ToStringVisitor {

	public String visit(
		BooleanSXPParameter parameter, Map<String, String> options) {

		return GetterUtil.getString(parameter.getValue());
	}

	public String visit(DateSXPParameter parameter, Map<String, String> options)
		throws Exception {

		Date date = parameter.getValue();
		
		if ((options == null) || (options.get("date_format") == null)) {			
			return date.toString();
		}

		if (options.containsKey("modifier")) {
			date = _modifyDate(date, options.get("modifier"));
		}

		String dateFormatString = options.get("date_format");

		if (dateFormatString.equals("timestamp")) {
			return String.valueOf(date.getTime());
		}

		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);

		return dateFormat.format(date);
	}

	public String visit(
			DoubleSXPParameter parameter, Map<String, String> options)
		throws Exception {

		return GetterUtil.getString(parameter.getValue());
	}

	public String visit(
		FloatSXPParameter parameter, Map<String, String> options) {

		return GetterUtil.getString(parameter.getValue());
	}

	public String visit(
		IntegerArraySXPParameter parameter, Map<String, String> options) {

		return Arrays.toString(parameter.getValue());
	}

	public String visit(
		IntegerSXPParameter parameter, Map<String, String> options) {

		return GetterUtil.getString(parameter.getValue());
	}

	public String visit(
		LongArraySXPParameter parameter, Map<String, String> options) {

		return Arrays.toString(parameter.getValue());
	}

	public String visit(
		LongSXPParameter parameter, Map<String, String> options) {

		return GetterUtil.getString(parameter.getValue());
	}

	public String visit(
		StringArraySXPParameter parameter, Map<String, String> options) {

		List<String> list = Arrays.asList(parameter.getValue());

		Stream<String> stream = list.stream();

		return stream.collect(Collectors.joining(",", "[", "]"));
	}

	@Override
	public String visit(
			StringSXPParameter parameter, Map<String, String> options)
		throws Exception {

		return parameter.getValue();
	}

	private Date _modifyDate(Date date, String option) throws Exception {
		if (Validator.isBlank(option) ||
			!option.matches("^[\\+|\\-][0-9]+[h|d|w|M|y]")) {

			return date;
		}

		char operator = option.charAt(0);

		char unit = option.charAt(option.length() - 1);

		option = option.replaceAll("\\D+", "");

		long amount = GetterUtil.getLong(option);

		if (operator == '-') {
			amount *= -1;
		}
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime1 = instant.atZone(
			ZoneId.systemDefault());

		LocalDateTime localDateTime = zonedDateTime1.toLocalDateTime();

		if (unit == 'h') {
			localDateTime = localDateTime.plusHours(amount);
		}
		else if (unit == 'd') {
			localDateTime = localDateTime.plusDays(amount);
		}
		else if (unit == 'w') {
			localDateTime = localDateTime.plusWeeks(amount);
		}
		else if (unit == 'M') {
			localDateTime = localDateTime.plusMonths(amount);
		}
		else if (unit == 'y') {
			localDateTime = localDateTime.plusYears(amount);
		}

		ZonedDateTime zonedDateTime2 = localDateTime.atZone(
			ZoneId.systemDefault());

		return Date.from(zonedDateTime2.toInstant());
	}
}