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
import com.liferay.search.experiences.parameter.LongArrayParameter;
import com.liferay.search.experiences.parameter.SXPParameter;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=long_array",
	service = SXPParameterBuilder.class
)
public class LongArrayParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(
		SXPAttributes sxpAttributes, JSONObject jsonObject) {

		String parameterName = jsonObject.getString("parameter_name");

		Optional<Long[]> longArrayOptional =
			_sxpAttributeValueHelper.getLongArrayOptional(
				sxpAttributes, parameterName);

		if (longArrayOptional.isPresent()) {
			return _toParameter(longArrayOptional.get(), parameterName);
		}

		Optional<String[]> stringArrayOptional =
			_sxpAttributeValueHelper.getStringArrayOptional(
				sxpAttributes, parameterName);

		if (stringArrayOptional.isPresent()) {
			Long[] longArray = _toLongArray(stringArrayOptional.get());

			if (longArray != null) {
				return _toParameter(longArray, parameterName);
			}
		}

		Optional<Long[]> defaultValueOptional =
			SXPJSONUtil.getLongArrayOptional(jsonObject, parameterName);

		if (defaultValueOptional.isPresent()) {
			return _toParameter(defaultValueOptional.get(), parameterName);
		}

		return Optional.empty();
	}

	private Long[] _toLongArray(String[] arr) {
		try {
			Stream<String> stream = Arrays.stream(arr);

			return stream.map(
				Long::valueOf
			).toArray(
				Long[]::new
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
		Long[] arr, String parameterName) {

		return Optional.of(
			new LongArrayParameter(
				parameterName, "${parameter." + parameterName + "}", arr));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LongArrayParameterBuilder.class);

	@Reference
	private SXPAttributeValueHelper _sxpAttributeValueHelper;

}