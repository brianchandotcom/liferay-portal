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

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.search.experiences.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.internal.blueprint.data.provider.GeoLocationDataProvider;
import com.liferay.search.experiences.internal.blueprint.data.provider.WeatherDataProvider;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Petteri Karttunen
 */
public class OpenWeatherMapSXPParameterContributor
	implements SXPParameterContributor {

	public OpenWeatherMapSXPParameterContributor(
		GeoLocationDataProvider geoLocationDataProvider,
		WeatherDataProvider weatherDataProvider) {

		_geoLocationDataProvider = geoLocationDataProvider;
		_weatherDataProvider = weatherDataProvider;

		_configuration = _getConfiguration();
	}

	@Override
	public void contribute(
		SearchContext searchContext, SXPBlueprint sxpBlueprint,
		Set<SXPParameter> sxpParameters) {

		if (!_isEnabled()) {
			return;
		}

		String ipAddress = (String)searchContext.getAttribute(
			"search.experiences.ipaddress");

		if (Validator.isBlank(ipAddress)) {
			return;
		}

		Optional<GeoLocationPoint> geoLocationPointOptional =
			_geoLocationDataProvider.getGeoLocationPoint(ipAddress);

		if (!geoLocationPointOptional.isPresent()) {
			return;
		}

		_contribute(geoLocationPointOptional.get(), sxpParameters);
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		return "weather";
	}

	@Override
	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions() {

		if (!_isEnabled()) {
			return Collections.<SXPParameterContributorDefinition>emptyList();
		}

		return Arrays.asList(
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "weather-id",
				"openweathermap.weather_id"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "weather-name}",
				"openweathermap.weather_name"),
			new SXPParameterContributorDefinition(
				DoubleSXPParameter.class, "temperature",
				"openweathermap.temperature"));
	}

	private void _contribute(
		GeoLocationPoint geoLocationPoint, Set<SXPParameter> sxpParameters) {

		Optional<JSONObject> weatherDataJSONObjectOptional =
			_weatherDataProvider.getWeatherData(geoLocationPoint);

		if (!weatherDataJSONObjectOptional.isPresent()) {
			return;
		}

		JSONObject weatherDataJSONObject = weatherDataJSONObjectOptional.get();

		JSONArray weatherJSONArray = weatherDataJSONObject.getJSONArray(
			"weather");

		JSONObject weatherJSONObject = weatherJSONArray.getJSONObject(0);

		if (weatherJSONObject == null) {
			return;
		}

		sxpParameters.add(
			new IntegerSXPParameter(
				"openweathermap.weather_id", true,
				weatherJSONObject.getInt("id")));

		sxpParameters.add(
			new StringSXPParameter(
				"openweathermap.weather_name", true,
				weatherJSONObject.getString("main")));

		sxpParameters.add(
			new DoubleSXPParameter(
				"openweathermap.temperature", true,
				weatherJSONObject.getDouble("temp")));
	}

	private Configuration _getConfiguration() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			return configurationAdmin.getConfiguration(
				"com.liferay.search.experiences.configuration." +
					"OpenWeatherMapConfiguration",
				StringPool.QUESTION);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	private boolean _isEnabled() {
		Dictionary<String, Object> dictionary = _configuration.getProperties();

		return GetterUtil.getBoolean(dictionary.get("is-enabled"));
	}

	private final Configuration _configuration;
	private final GeoLocationDataProvider _geoLocationDataProvider;
	private final WeatherDataProvider _weatherDataProvider;

}