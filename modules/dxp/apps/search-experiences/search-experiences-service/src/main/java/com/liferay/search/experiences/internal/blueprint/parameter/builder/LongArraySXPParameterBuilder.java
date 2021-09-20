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
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=long_array",
	service = SXPParameterBuilder.class
)
public class LongArraySXPParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(JSONObject jsonObject, 
			SearchRequestBuilder searchRequestBuilder) {

		String parameterName = jsonObject.getString("parameter_name");

		Long[] longArray1 = SearchContextUtil.getLongArrayAttribute(parameterName, searchRequestBuilder);
		
		if (!Objects.isNull(longArray1)) {
			return _toParameter(longArray1, parameterName);
		}

		String[] stringArray = SearchContextUtil.getStringArrayAttribute(parameterName, searchRequestBuilder);

		if (!Objects.isNull(stringArray)) {
			Long[] longArray2 = _toLongArray(stringArray);

			if (longArray2 != null) {
				return _toParameter(longArray2, parameterName);
			}
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
			new LongArraySXPParameter(
				parameterName, true, arr));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LongArraySXPParameterBuilder.class);

}