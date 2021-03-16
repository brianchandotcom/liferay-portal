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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringArrayParameter;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.facets.spi.request.FacetRequestHandler;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=value_aggregations",
	service = FacetRequestHandler.class
)
public class ValueAggregationsFacetRequestHandler
	extends BaseFacetRequestHandler implements FacetRequestHandler {

	public Optional<Parameter> getParameterOptional(
		BlueprintsAttributes blueprintsAttributes, Messages messages,
		JSONObject configurationJSONObject) {

		if (!_validateConfiguration(configurationJSONObject, messages)) {
			Optional.empty();
		}

		String parameterName = configurationJSONObject.getString(
			FacetConfigurationKeys.PARAMETER_NAME.getJsonKey());

		Optional<Object> valueOptional =
			blueprintsAttributes.getAttributeOptional(parameterName);

		if (!valueOptional.isPresent()) {
			return Optional.empty();
		}

		String[] valueArray;

		boolean multiValue = configurationJSONObject.getBoolean(
			FacetConfigurationKeys.MULTI_VALUE.getJsonKey(), true);

		if (multiValue) {
			valueArray = GetterUtil.getStringValues(valueOptional.get());
		}
		else {
			valueArray = new String[] {
				GetterUtil.getString(valueOptional.get())
			};
		}

		List<String> values = _getValues(valueArray, configurationJSONObject);

		if (values.isEmpty()) {
			return Optional.empty();
		}

		Stream<String> stream = values.stream();

		Parameter parameter = new StringArrayParameter(
			parameterName, null, stream.toArray(String[]::new));

		return Optional.of(parameter);
	}

	private List<String> _getValues(
		String[] valueArray, JSONObject configurationJSONObject) {

		List<String> values = new ArrayList<>();

		JSONObject handlerParametersJSONObject =
			configurationJSONObject.getJSONObject(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey());

		JSONArray valueAggregationsJSONArray =
			handlerParametersJSONObject.getJSONArray("aggregations");

		for (String requestValue : valueArray) {
			JSONArray translatedValuesJSONArray = null;

			for (int i = 0; i < valueAggregationsJSONArray.length(); i++) {
				try {
					JSONObject jsonObject =
						valueAggregationsJSONArray.getJSONObject(i);

					if (jsonObject.getString(
							"key"
						).equals(
							requestValue
						)) {

						translatedValuesJSONArray = jsonObject.getJSONArray(
							"values");

						break;
					}
				}
				catch (Exception exception) {
					_log.error(exception.getMessage(), exception);
				}
			}

			if ((translatedValuesJSONArray != null) &&
				(translatedValuesJSONArray.length() > 0)) {

				Collections.addAll(
					values, JSONUtil.toStringArray(translatedValuesJSONArray));
			}
			else {
				values.add(requestValue);
			}
		}

		return values;
	}

	private boolean _validateConfiguration(
		JSONObject configurationJSONObject, Messages messages) {

		boolean valid = true;

		JSONObject handlerParametersJSONObject =
			configurationJSONObject.getJSONObject(
				FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey());

		if (handlerParametersJSONObject == null) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.undefined-handler-parameters"
				).msg(
					"Facet handler parameters are not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					FacetConfigurationKeys.HANDLER_PARAMETERS.getJsonKey()
				).rootValue(
					null
				).severity(
					Severity.ERROR
				).build());

			valid = false;
		}

		if ((handlerParametersJSONObject == null) ||
			!handlerParametersJSONObject.has("aggregations")) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"facets.error.undefined-handler-mappings"
				).msg(
					"Facet handler mappings are not defined"
				).rootObject(
					configurationJSONObject
				).rootProperty(
					"mappings"
				).rootValue(
					null
				).severity(
					Severity.ERROR
				).build());

			valid = false;
		}

		return valid;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ValueAggregationsFacetRequestHandler.class);

}