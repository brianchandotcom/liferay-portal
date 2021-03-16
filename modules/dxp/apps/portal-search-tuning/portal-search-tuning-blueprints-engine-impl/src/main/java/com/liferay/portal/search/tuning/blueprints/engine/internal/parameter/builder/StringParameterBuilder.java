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
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.parameter.CustomParameterConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.BlueprintValueUtil;
import com.liferay.portal.search.tuning.blueprints.engine.internal.util.BlueprintsAttributesHelper;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter;
import com.liferay.portal.search.tuning.blueprints.message.Messages;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=string", service = ParameterBuilder.class
)
public class StringParameterBuilder implements ParameterBuilder {

	@Override
	public Optional<Parameter> build(
		BlueprintsAttributes blueprintsAttributes,
		JSONObject configurationJSONObject, Messages messages) {

		String parameterName = configurationJSONObject.getString(
			CustomParameterConfigurationKeys.PARAMETER_NAME.getJsonKey());

		Optional<String> optional = _getValueOptional(
			blueprintsAttributes, configurationJSONObject, parameterName);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(
			new StringParameter(
				parameterName, "${parameter." + parameterName + "}",
				optional.get()));
	}

	private Optional<String> _getValueOptional(
		BlueprintsAttributes blueprintsAttributes,
		JSONObject configurationJSONObject, String parameterName) {

		Optional<String> optional =
			_blueprintsAttributesHelper.getStringOptional(
				blueprintsAttributes, parameterName);

		if (!optional.isPresent()) {
			optional = BlueprintValueUtil.toStringOptional(
				configurationJSONObject.getString(
					CustomParameterConfigurationKeys.DEFAULT.getJsonKey()));
		}

		return optional;
	}

	@Reference
	private BlueprintsAttributesHelper _blueprintsAttributesHelper;

}