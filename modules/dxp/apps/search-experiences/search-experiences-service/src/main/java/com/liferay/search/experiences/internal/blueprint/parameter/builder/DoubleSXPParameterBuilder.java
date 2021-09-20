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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SXPValueUtil;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=double",
	service = SXPParameterBuilder.class
)
public class DoubleSXPParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(JSONObject jsonObject, 
			SearchRequestBuilder searchRequestBuilder) {
		
		String parameterName = jsonObject.getString("parameter_name");

		Optional<Double> optional = _getValueOptional(jsonObject, parameterName, searchRequestBuilder);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new DoubleSXPParameter(
				parameterName, true,
				_getAdjustedValue(jsonObject, optional.get())));
	}

	protected double _getAdjustedValue(JSONObject jsonObject, Double value) {
		
		Optional<Double> minValue = SXPValueUtil.stringToDoubleOptional(
			jsonObject.getString("min_value"));

		if (minValue.isPresent() &&
			(Double.compare(value, minValue.get()) < 0)) {

			if (_log.isWarnEnabled()) {
				_log.warn(minValue.get() + " is below the minimum. Setting min value");
			}

			return minValue.get();
		}

		Optional<Double> maxValue = SXPValueUtil.stringToDoubleOptional(
			jsonObject.getString("max_value"));

		if (maxValue.isPresent() &&
			(Double.compare(value, maxValue.get()) > 0)) {

			if (_log.isWarnEnabled()) {
				_log.warn(maxValue.get() + " is above the maximum. Setting max value");
			}

			return maxValue.get();
		}

		return value;
	}
	
	private Optional<Double> _getValueOptional(JSONObject jsonObject,  String parameterName,
			SearchRequestBuilder searchRequestBuilder) {

		Double value = SearchContextUtil.getDoubleAttribute(parameterName, searchRequestBuilder);
		
		if (Validator.isNotNull(value)) {
			return Optional.of(value);
		}
		
		if (jsonObject.has("default")) {
			return Optional.of(GetterUtil.getDouble("default"));
		}

		return Optional.empty();

	}

	private static final Log _log = LogFactoryUtil.getLog(
		DoubleSXPParameterBuilder.class);

}