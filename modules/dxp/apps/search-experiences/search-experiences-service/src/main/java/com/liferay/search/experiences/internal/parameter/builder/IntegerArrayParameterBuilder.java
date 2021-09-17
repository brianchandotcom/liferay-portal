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
import com.liferay.search.experiences.blueprints.engine.parameter.IntegerArrayParameter;
import com.liferay.search.experiences.problems.ProblemsHolderBuilder;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=integer_array",
	service = ParameterBuilder.class
)
public class IntegerArrayParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(
		SXPAttributes sxpAttributes, JSONObject jsonObject,
		ProblemsHolderBuilder problemsHolderBuilder) {

		String parameterName = jsonObject.getString("parameter_name");

		Optional<Integer[]> integerArrayOptional =
			_sxpAttributeValuesHelper.getIntegerArrayOptional(
				sxpAttributes, parameterName);

		if (integerArrayOptional.isPresent()) {
			return _toParameter(integerArrayOptional.get(), parameterName);
		}

		Optional<String[]> stringArrayOptional =
			_sxpAttributeValuesHelper.getStringArrayOptional(
				sxpAttributes, parameterName);

		if (stringArrayOptional.isPresent()) {
			Integer[] integerArray = _toIntegerArray(stringArrayOptional.get());

			if (integerArray != null) {
				return _toParameter(integerArray, parameterName);
			}
		}

		Optional<Integer[]> defaultValueOptional =
			BlueprintJSONUtil.getIntegerArrayOptional(
				jsonObject, parameterName);

		if (defaultValueOptional.isPresent()) {
			return _toParameter(defaultValueOptional.get(), parameterName);
		}

		return Optional.empty();
	}

	private Integer[] _toIntegerArray(String[] arr) {
		try {
			Stream<String> stream = Arrays.stream(arr);

			return stream.map(
				Integer::valueOf
			).toArray(
				Integer[]::new
			);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					numberFormatException.getMessage(), numberFormatException);
			}
		}

		return null;
	}

	private Optional<SXPParameter> _toParameter(
		Integer[] arr, String parameterName) {

		return Optional.of(
			new IntegerArrayParameter(
				parameterName, "${parameter." + parameterName + "}", arr));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IntegerArrayParameterBuilder.class);

	@Reference
	private SXPAttributeValueHelper _sxpAttributeValuesHelper;

}