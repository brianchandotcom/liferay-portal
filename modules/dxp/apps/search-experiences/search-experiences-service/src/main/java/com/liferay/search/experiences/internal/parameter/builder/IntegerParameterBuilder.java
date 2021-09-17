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

package com.liferay.search.experiences.internal.parameter.builder;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.search.experiences.attributes.SXPAttributes;
import com.liferay.search.experiences.internal.attributes.util.SXPAttributeValueHelper;
import com.liferay.search.experiences.internal.util.SXPJSONUtil;
import com.liferay.search.experiences.internal.util.SXPValueUtil;
import com.liferay.search.experiences.parameter.IntegerParameter;
import com.liferay.search.experiences.parameter.SXPParameter;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=integer",
	service = SXPParameterBuilder.class
)
public class IntegerParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(
		SXPAttributes sxpAttributes, JSONObject jsonObject) {

		String parameterName = jsonObject.getString("parameter_name");

		Optional<Integer> valueOptional = _getValueOptional(
			sxpAttributes, jsonObject, parameterName);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new IntegerParameter(
				parameterName, "${parameter." + parameterName + "}",
				_getAdjustedValue(valueOptional.get(), jsonObject)));
	}

	private int _getAdjustedValue(int value, JSONObject jsonObject) {
		Optional<Integer> minValue = SXPValueUtil.stringToIntegerOptional(
			jsonObject.getString("min_value"));

		if (minValue.isPresent() &&
			(Integer.compare(value, minValue.get()) < 0)) {

			if (_log.isWarnEnabled()) {
				_log.warn(minValue.get() + " is below the minimum.");
			}

			value = minValue.get();
		}

		Optional<Integer> maxValue = SXPValueUtil.stringToIntegerOptional(
			jsonObject.getString("max_value"));

		if (maxValue.isPresent() &&
			(Integer.compare(value, maxValue.get()) > 0)) {

			if (_log.isWarnEnabled()) {
				_log.warn(maxValue.get() + " is above the maximum.");
			}

			value = maxValue.get();
		}

		return value;
	}

	private Optional<Integer> _getValueOptional(
		SXPAttributes sxpAttributes, JSONObject configurationJSONObject,
		String parameterName) {

		Optional<Integer> optional =
			_sxpAttributeValueHelper.getIntegerOptional(
				sxpAttributes, parameterName);

		if (!optional.isPresent()) {
			optional = SXPJSONUtil.getIntegerOptional(
				configurationJSONObject, "Object/default");
		}

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		return optional;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IntegerParameterBuilder.class);

	@Reference
	private SXPAttributeValueHelper _sxpAttributeValueHelper;

}