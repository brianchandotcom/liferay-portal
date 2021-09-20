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
import com.liferay.search.experiences.blueprint.parameter.LongSXPParameter;
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
	immediate = true, property = "name=long",
	service = SXPParameterBuilder.class
)
public class LongSXPParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(JSONObject jsonObject, 
			SearchRequestBuilder searchRequestBuilder) {

		String parameterName = jsonObject.getString(
			"parameter_name");

		Optional<Long> optional = _getValueOptional(jsonObject, parameterName, searchRequestBuilder);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new LongSXPParameter(
				parameterName, true,
				_getAdjustedValue(
						optional.get(), jsonObject)));
	}

	private long _getAdjustedValue(
		long value, JSONObject configurationJSONObject) {

		Optional<Long> minValue = SXPValueUtil.stringToLongOptional(
			configurationJSONObject.getString("min_value"));

		if (minValue.isPresent() && (Long.compare(value, minValue.get()) < 0)) {
			if (_log.isWarnEnabled()) {
				_log.warn(minValue.get() + " is below the minimum.");
			}

			return minValue.get();
		}

		Optional<Long> maxValue = SXPValueUtil.stringToLongOptional(
			configurationJSONObject.getString("max_value"));

		if (maxValue.isPresent() && (Long.compare(value, maxValue.get()) > 0)) {
			if (_log.isWarnEnabled()) {
				_log.warn(maxValue.get() + " is above the maximum.");
			}

			return maxValue.get();
		}

		return value;
	}

	private Optional<Long> _getValueOptional(JSONObject jsonObject,  String parameterName,
			SearchRequestBuilder searchRequestBuilder) {

		Long value = SearchContextUtil.getLongAttribute(parameterName, searchRequestBuilder);

		if (!Validator.isNotNull(value)) {
			return Optional.of(value);
		}
		
		if (jsonObject.has("default")) {
			return SXPJSONUtil.getLongOptional(jsonObject, "Object/default");
		}

		return Optional.empty();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LongSXPParameterBuilder.class);

}