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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.internal.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.internal.configuration.IpstackConfiguration;
import com.liferay.search.experiences.internal.configuration.OpenWeatherMapConfiguration;
import com.liferay.search.experiences.internal.web.cache.IpstackWebCacheItem;
import com.liferay.search.experiences.internal.web.cache.OpenWeatherMapWebCacheItem;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class OpenWeatherMapSXPParameterContributor
	implements SXPParameterContributor {

	public OpenWeatherMapSXPParameterContributor(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Override
	public void contribute(
		SearchContext searchContext, SXPBlueprint sxpBlueprint,
		Set<SXPParameter> sxpParameters) {

		OpenWeatherMapConfiguration openWeatherMapConfiguration =
			_getOpenWeatherMapConfiguration(searchContext.getCompanyId());

		if (!openWeatherMapConfiguration.enabled()) {
			return;
		}

		String ipAddress = (String)searchContext.getAttribute(
			"search.experiences.ip.address");

		if (Validator.isNull(ipAddress)) {
			return;
		}

		JSONObject jsonObject1 = IpstackWebCacheItem.get(
			ipAddress, _getIpstackConfiguration(searchContext.getCompanyId()));

		if (jsonObject1.length() == 0) {
			return;
		}

		String latitude = jsonObject1.getString("latitude");
		String longitude = jsonObject1.getString("longitude");

		JSONObject jsonObject2 = OpenWeatherMapWebCacheItem.get(
			latitude, longitude, openWeatherMapConfiguration);

		if (jsonObject2.length() == 0) {
			return;
		}

		sxpParameters.add(
			new DoubleSXPParameter(
				"openweathermap.temp", true,
				JSONUtil.getValueAsDouble(
					jsonObject2, "JSONObject/main", "Object/temp")));

		sxpParameters.add(
			new StringSXPParameter(
				"openweathermap.weather_description", true,
				JSONUtil.getValueAsString(
					jsonObject2, "JSONArray/weather", "JSONObject/0",
					"Object/description")));

		sxpParameters.add(
			new IntegerSXPParameter(
				"openweathermap.weather_id", true,
				JSONUtil.getValueAsInt(
					jsonObject2, "JSONArray/weather", "JSONObject/0",
					"Object/id")));

		sxpParameters.add(
			new StringSXPParameter(
				"openweathermap.weather_main", true,
				JSONUtil.getValueAsString(
					jsonObject2, "JSONArray/weather", "JSONObject/0",
					"Object/main")));

		sxpParameters.add(
			new DoubleSXPParameter(
				"openweathermap.wind_speed", true,
				JSONUtil.getValueAsDouble(
					jsonObject2, "JSONObject/wind", "Object/speed")));
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		return "weather";
	}

	@Override
	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions(long companyId) {

		OpenWeatherMapConfiguration openWeatherMapConfiguration =
			_getOpenWeatherMapConfiguration(companyId);

		if (!openWeatherMapConfiguration.enabled()) {
			return Collections.<SXPParameterContributorDefinition>emptyList();
		}

		return Arrays.asList(
			new SXPParameterContributorDefinition(
				DoubleSXPParameter.class, "temperature", "openweathermap.temp"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "description",
				"openweathermap.weather_description"),
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "weather-condition-id",
				"openweathermap.weather_id"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "weather-condition-name",
				"openweathermap.weather_main"),
			new SXPParameterContributorDefinition(
				DoubleSXPParameter.class, "wind-speed",
				"openweathermap.wind_speed"));
	}

	private IpstackConfiguration _getIpstackConfiguration(long companyId) {
		try {
			return _configurationProvider.getCompanyConfiguration(
				IpstackConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	private OpenWeatherMapConfiguration _getOpenWeatherMapConfiguration(
		long companyId) {

		try {
			return _configurationProvider.getCompanyConfiguration(
				OpenWeatherMapConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	private final ConfigurationProvider _configurationProvider;

}