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
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=string",
	service = SXPParameterBuilder.class
)
public class StringSXPParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(JSONObject jsonObject, 
			SearchRequestBuilder searchRequestBuilder) {

		String parameterName = jsonObject.getString("parameter_name");

		Optional<String> optional = _getValueOptional(jsonObject, parameterName, searchRequestBuilder);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new StringSXPParameter(
				parameterName, true,
				optional.get()));
	}

	private Optional<String> _getValueOptional(JSONObject jsonObject,  String parameterName,
			SearchRequestBuilder searchRequestBuilder) {

		String value = SearchContextUtil.getStringAttribute(parameterName, searchRequestBuilder);
		
		if (!Validator.isBlank(value)) {
			return Optional.of(value);
		}
		
		if (jsonObject.has("default")) {
			return Optional.of(jsonObject.getString("default"));
		}

		return Optional.empty();
	}

}