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

package com.liferay.portal.search.tuning.blueprints.engine.internal.parameter.builder;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.CustomParameterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.BlueprintValueUtil;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.BlueprintsAttributesHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.FloatParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=float", service = ParameterBuilder.class
)
public class FloatParameterBuilder implements ParameterBuilder {

	@Override
	public Optional<Parameter> build(
		BlueprintsAttributes blueprintsAttributes,
		JSONObject configurationJSONObject, Messages messages) {

		String parameterName = configurationJSONObject.getString(
			CustomParameterConfigurationKeys.PARAMETER_NAME.getJsonKey());

		Optional<Float> valueOptional = _getValueOptional(
			blueprintsAttributes, configurationJSONObject, parameterName);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new FloatParameter(
				parameterName, "${parameter." + parameterName + "}",
				_getAdjustedValue(
					valueOptional.get(), configurationJSONObject)));
	}

	private float _getAdjustedValue(
		float value, JSONObject configurationJSONObject) {

		Optional<Float> minValue = BlueprintValueUtil.stringToFloatOptional(
			configurationJSONObject.getString(
				CustomParameterConfigurationKeys.MIN_VALUE.getJsonKey()));

		if (minValue.isPresent() &&
			(Float.compare(value, minValue.get()) < 0)) {

			if (_log.isWarnEnabled()) {
				_log.warn(minValue.get() + " is below the minimum.");
			}

			value = minValue.get();
		}

		Optional<Float> maxValue = BlueprintValueUtil.stringToFloatOptional(
			configurationJSONObject.getString(
				CustomParameterConfigurationKeys.MAX_VALUE.getJsonKey()));

		if (maxValue.isPresent() &&
			(Float.compare(value, maxValue.get()) > 0)) {

			if (_log.isWarnEnabled()) {
				_log.warn(maxValue.get() + " is above the maximum.");
			}

			value = maxValue.get();
		}

		return value;
	}

	private Optional<Float> _getValueOptional(
		BlueprintsAttributes blueprintsAttributes,
		JSONObject configurationJSONObject, String parameterName) {

		Optional<String> valueStringOptional =
			_blueprintsAttributesHelper.getStringOptional(
				blueprintsAttributes, parameterName);

		if (!valueStringOptional.isPresent()) {
			valueStringOptional = BlueprintValueUtil.toStringOptional(
				configurationJSONObject.getString(
					CustomParameterConfigurationKeys.DEFAULT.getJsonKey()));
		}

		if (!valueStringOptional.isPresent()) {
			return Optional.empty();
		}

		return BlueprintValueUtil.stringToFloatOptional(
			valueStringOptional.get());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FloatParameterBuilder.class);

	@Reference
	private BlueprintsAttributesHelper _blueprintsAttributesHelper;

}