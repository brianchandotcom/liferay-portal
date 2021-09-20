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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=time_range",
	service = SXPParameterBuilder.class
)
public class TimeRangeSXPParameterBuilder  implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(JSONObject jsonObject, 
			SearchRequestBuilder searchRequestBuilder) {

		String parameterName = jsonObject.getString("parameter_name");

		String value = SearchContextUtil.getStringAttribute(parameterName, searchRequestBuilder);

		if (Validator.isBlank(value)) {
			return Optional.empty();
		}

		Date timeFrom = _getTimeFrom(value);

		if (timeFrom == null) {
			return Optional.empty();
		}

		return Optional.of(
			new DateSXPParameter(
				parameterName, true, timeFrom));
	}

	private Date _getTimeFrom(String value) {
		Date timeFrom = null;

		Calendar calendar = Calendar.getInstance();

		if (value.equals("last-day")) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);

			timeFrom = calendar.getTime();
		}
		else if (value.equals("last-hour")) {
			calendar.add(Calendar.HOUR_OF_DAY, -1);

			timeFrom = calendar.getTime();
		}
		else if (value.equals("last-month")) {
			calendar.add(Calendar.MONTH, -1);

			timeFrom = calendar.getTime();
		}
		else if (value.equals("last-week")) {
			calendar.add(Calendar.WEEK_OF_MONTH, -1);

			timeFrom = calendar.getTime();
		}
		else if (value.equals("last-year")) {
			calendar.add(Calendar.YEAR, -1);

			timeFrom = calendar.getTime();
		}

		return timeFrom;
	}
}