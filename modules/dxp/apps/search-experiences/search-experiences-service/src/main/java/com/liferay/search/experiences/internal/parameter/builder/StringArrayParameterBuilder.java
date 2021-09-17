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
import com.liferay.search.experiences.attributes.SXPAttributes;
import com.liferay.search.experiences.internal.attributes.util.SXPAttributeValueHelper;
import com.liferay.search.experiences.internal.util.SXPJSONUtil;
import com.liferay.search.experiences.parameter.SXPParameter;
import com.liferay.search.experiences.parameter.StringArrayParameter;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=string_array",
	service = SXPParameterBuilder.class
)
public class StringArrayParameterBuilder implements SXPParameterBuilder {

	@Override
	public Optional<SXPParameter> build(
		SXPAttributes sxpAttributes, JSONObject jsonObject) {

		String parameterName = jsonObject.getString("parameter_name");

		Optional<String[]> valueOptional = _getValueOptional(
			sxpAttributes, jsonObject, parameterName);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new StringArrayParameter(
				parameterName, "${parameter." + parameterName + "}",
				valueOptional.get()));
	}

	private Optional<String[]> _getValueOptional(
		SXPAttributes sxpAttributes, JSONObject jsonObject,
		String parameterName) {

		Optional<String[]> optional =
			_sxpAttributeValueHelper.getStringArrayOptional(
				sxpAttributes, parameterName);

		if (!optional.isPresent()) {
			optional = SXPJSONUtil.getStringArray(jsonObject, "default");
		}

		return optional;
	}

	@Reference
	private SXPAttributeValueHelper _sxpAttributeValueHelper;

}