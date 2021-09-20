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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SXPJSONUtil;
import com.liferay.search.experiences.internal.blueprint.util.SXPValueUtil;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=integer",
	service = SXPParameterBuilder.class
)
public class IntegerSXPParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(JSONObject jsonObject, 
			SearchRequestBuilder searchRequestBuilder) {

		String parameterName = jsonObject.getString("parameter_name");

		Optional<Integer> optional = _getValueOptional(jsonObject, parameterName, searchRequestBuilder);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new IntegerSXPParameter(
				parameterName, true,
				_getAdjustedValue(optional.get(), jsonObject)));
	}

	private int _getAdjustedValue(int value, JSONObject jsonObject) {
		Optional<Integer> minValue = SXPValueUtil.stringToIntegerOptional(
			jsonObject.getString("min_value"));

		if (minValue.isPresent() &&
			(Integer.compare(value, minValue.get()) < 0)) {

			if (_log.isWarnEnabled()) {
				_log.warn(minValue.get() + " is below the minimum. Set to min");
			}

			return minValue.get();
		}

		Optional<Integer> maxValue = SXPValueUtil.stringToIntegerOptional(
			jsonObject.getString("max_value"));

		if (maxValue.isPresent() &&
			(Integer.compare(value, maxValue.get()) > 0)) {

			if (_log.isWarnEnabled()) {
				_log.warn(maxValue.get() + " is above the maximum. Set to max");
			}

			return maxValue.get();
		}

		return value;
	}
	
	private Optional<Integer> _getValueOptional(JSONObject jsonObject,  String parameterName,
			SearchRequestBuilder searchRequestBuilder) {

		Integer value = SearchContextUtil.getIntegerAttribute(parameterName, searchRequestBuilder);

		if (!Validator.isNotNull(value)) {
			return Optional.of(value);
		}
		
		if (jsonObject.has("default")) {
			return SXPJSONUtil.getIntegerOptional(jsonObject, "Object/default");
		}

		return Optional.empty();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IntegerSXPParameterBuilder.class);
}