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

package com.liferay.search.experiences.blueprints.engine.internal.parameter.builder;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.search.experiences.blueprints.engine.internal.util.BlueprintJSONUtil;
import com.liferay.search.experiences.blueprints.engine.internal.util.BlueprintValueUtil;
import com.liferay.search.experiences.blueprints.engine.parameter.LongParameter;
import com.liferay.search.experiences.problems.ProblemsHolderBuilder;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=long", service = ParameterBuilder.class
)
public class LongParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(
		SXPAttributes sxpAttributes, JSONObject configurationJSONObject,
		ProblemsHolderBuilder problemsHolderBuilder) {

		String parameterName = configurationJSONObject.getString(
			"parameter_name");

		Optional<Long> valueOptional = _getValueOptional(
			sxpAttributes, configurationJSONObject, parameterName);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new LongParameter(
				parameterName, "${parameter." + parameterName + "}",
				_getAdjustedValue(
					valueOptional.get(), configurationJSONObject)));
	}

	private long _getAdjustedValue(
		long value, JSONObject configurationJSONObject) {

		Optional<Long> minValue = BlueprintValueUtil.stringToLongOptional(
			configurationJSONObject.getString("min_value"));

		if (minValue.isPresent() && (Long.compare(value, minValue.get()) < 0)) {
			if (_log.isWarnEnabled()) {
				_log.warn(minValue.get() + " is below the minimum.");
			}

			value = minValue.get();
		}

		Optional<Long> maxValue = BlueprintValueUtil.stringToLongOptional(
			configurationJSONObject.getString("max_value"));

		if (maxValue.isPresent() && (Long.compare(value, maxValue.get()) > 0)) {
			if (_log.isWarnEnabled()) {
				_log.warn(maxValue.get() + " is above the maximum.");
			}

			value = maxValue.get();
		}

		return value;
	}

	private Optional<Long> _getValueOptional(
		SXPAttributes sxpAttributes, JSONObject configurationJSONObject,
		String parameterName) {

		Optional<Long> optional = _sxpAttributeValuesHelper.getLongOptional(
			sxpAttributes, parameterName);

		if (!optional.isPresent()) {
			optional = BlueprintJSONUtil.getLongOptional(
				configurationJSONObject, "Object/default");
		}

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		return optional;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LongParameterBuilder.class);

	@Reference
	private SXPAttributeValueHelper _sxpAttributeValuesHelper;

}