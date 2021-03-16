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

package com.liferay.portal.search.tuning.blueprints.facets.internal.request.handler;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringArrayParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.facets.spi.request.FacetRequestHandler;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseFacetRequestHandler implements FacetRequestHandler {

	@Override
	public Optional<Parameter> getParameterOptional(
		BlueprintsAttributes blueprintsAttributes, Messages messages,
		JSONObject configurationJSONObject) {

		String parameterName = configurationJSONObject.getString(
			FacetConfigurationKeys.PARAMETER_NAME.getJsonKey());

		Optional<Object> valueOptional =
			blueprintsAttributes.getAttributeOptional(parameterName);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		boolean multiValue = configurationJSONObject.getBoolean(
			FacetConfigurationKeys.MULTI_VALUE.getJsonKey(), true);

		if (multiValue) {
			return _getMultiValueParameter(parameterName, valueOptional.get());
		}

		return _getSingleValueParameter(parameterName, valueOptional.get());
	}

	private Optional<Parameter> _getMultiValueParameter(
		String parameterName, Object value) {

		String[] arr = GetterUtil.getStringValues(value);

		if (arr.length > 0) {
			Parameter parameter = new StringArrayParameter(
				parameterName, null, arr);

			return Optional.of(parameter);
		}

		return Optional.empty();
	}

	private Optional<Parameter> _getSingleValueParameter(
		String parameterName, Object value) {

		String s = GetterUtil.getString(value);

		if (!Validator.isBlank(s)) {
			Parameter parameter = new StringParameter(parameterName, null, s);

			return Optional.of(parameter);
		}

		return Optional.empty();
	}

}