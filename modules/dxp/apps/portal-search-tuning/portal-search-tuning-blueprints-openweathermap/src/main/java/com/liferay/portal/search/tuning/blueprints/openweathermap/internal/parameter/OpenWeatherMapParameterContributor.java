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

package com.liferay.portal.search.tuning.blueprints.openweathermap.internal.parameter;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.DoubleParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.IntegerParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataBuilder;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDefinition;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter;
import com.liferay.portal.search.tuning.blueprints.engine.spi.dataprovider.GeoLocationDataProvider;
import com.liferay.portal.search.tuning.blueprints.engine.spi.parameter.ParameterContributor;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.openweathermap.internal.configuration.OpenWeatherMapConfiguration;
import com.liferay.portal.search.tuning.blueprints.openweathermap.internal.dataprovider.OpenWeatherMapDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.blueprints.openweathermap.internal.configuration.OpenWeatherMapConfiguration",
	immediate = true, property = "name=openweathermap",
	service = ParameterContributor.class
)
public class OpenWeatherMapParameterContributor
	implements ParameterContributor {

	@Override
	public void contribute(
		ParameterDataBuilder parameterDataBuilder, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		_contribute(
			parameterDataBuilder, _getIpAddress(blueprintsAttributes),
			messages);
	}

	@Override
	public String getCategoryNameKey() {
		return "weather";
	}

	@Override
	public List<ParameterDefinition> getParameterDefinitions() {
		List<ParameterDefinition> parameterDefinitions = new ArrayList<>();

		if (!_openWeatherMapConfiguration.isEnabled()) {
			return parameterDefinitions;
		}

		parameterDefinitions.add(
			new ParameterDefinition(
				"${openweathermap.weather_id}",
				IntegerParameter.class.getName(),
				"openweathermap.parameter.weather-id"));

		parameterDefinitions.add(
			new ParameterDefinition(
				"${openweathermap.weather_name}",
				StringParameter.class.getName(),
				"openweathermap.parameter.weather-name"));

		parameterDefinitions.add(
			new ParameterDefinition(
				"${openweathermap.temperature}",
				DoubleParameter.class.getName(),
				"openweathermap.parameter.temperature"));

		return parameterDefinitions;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_openWeatherMapConfiguration = ConfigurableUtil.createConfigurable(
			OpenWeatherMapConfiguration.class, properties);
	}

	private void _contribute(
		ParameterDataBuilder parameterDataBuilder, String ipAddress,
		Messages messages) {

		Optional<GeoLocationPoint> geoLocationPointOptional =
			_geoLocationDataProvider.getGeoLocationPoint(ipAddress, messages);

		if (!geoLocationPointOptional.isPresent()) {
			return;
		}

		GeoLocationPoint geoLocationPoint = geoLocationPointOptional.get();

		JSONObject weatherDataJSONObject =
			_openWeatherMapDataProvider.getWeatherDataJSONObject(
				messages, geoLocationPoint);

		if (weatherDataJSONObject == null) {
			return;
		}

		JSONArray weatherJSONArray = weatherDataJSONObject.getJSONArray(
			"weather");

		JSONObject weatherJSONObject = weatherJSONArray.getJSONObject(0);

		if (weatherJSONObject == null) {
			return;
		}

		parameterDataBuilder.addParameter(
			new IntegerParameter(
				"openweathermap.weather_id", "${openweathermap.weather_id}",
				weatherJSONObject.getInt("id")));

		parameterDataBuilder.addParameter(
			new StringParameter(
				"openweathermap.weather_name", "${openweathermap.weather_name}",
				weatherJSONObject.getString("main")));

		parameterDataBuilder.addParameter(
			new DoubleParameter(
				"openweathermap.temperature", "${openweathermap.temperature}",
				weatherJSONObject.getDouble("temp")));
	}

	private String _getIpAddress(BlueprintsAttributes blueprintsAttributes) {
		Optional<Object> valueOptional =
			blueprintsAttributes.getAttributeOptional(
				ReservedParameterNames.IP_ADDRESS.getKey());

		return GetterUtil.getString(valueOptional.orElse(null));
	}

	@Reference
	private GeoLocationDataProvider _geoLocationDataProvider;

	private volatile OpenWeatherMapConfiguration _openWeatherMapConfiguration;

	@Reference
	private OpenWeatherMapDataProvider _openWeatherMapDataProvider;

}