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

package com.liferay.search.experiences.internal.blueprint.data.provider;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.search.experiences.configuration.OpenWeatherMapConfiguration;
import com.liferay.search.experiences.internal.blueprint.data.provider.cache.JSONDataProviderCache;
import com.liferay.search.experiences.internal.problem.ProblemUtil;

import java.io.IOException;

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
	configurationPid = "com.liferay.search.experiences.configuration.OpenWeatherMapConfiguration",
	immediate = true, property = "name=openweathermap",
	service = WeatherDataProvider.class
)
public class OpenWeatherMapWeatherDataProvider implements WeatherDataProvider {

	public Optional<JSONObject> getWeatherData(
		GeoLocationPoint geoLocationPoint) {

		if (!_validateConfiguration()) {
			return null;
		}

		String cacheKey = _getCacheKey(geoLocationPoint);

		JSONObject weatherDataJsonObject1 = _getCachedWeatherJSONObject(
			cacheKey);

		if (weatherDataJsonObject1 != null) {
			return Optional.of(weatherDataJsonObject1);
		}

		JSONObject weatherDataJsonObject2 = _fetchOpenWeatherMapDataJSONObject(
			geoLocationPoint);

		if (!_validateJSONResponseData(weatherDataJsonObject2)) {
			return Optional.empty();
		}

		_jsonDataProviderCache.put(
			cacheKey, weatherDataJsonObject2,
			_openWeatherMapConfiguration.cacheTimeout());

		if (_log.isDebugEnabled()) {
			_log.debug("OpenWeatherMap data: " + weatherDataJsonObject2);
		}

		return Optional.of(weatherDataJsonObject2);
	}

	public boolean isEnabled() {
		return _openWeatherMapConfiguration.isEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_openWeatherMapConfiguration = ConfigurableUtil.createConfigurable(
			OpenWeatherMapConfiguration.class, properties);
	}

	private String _buildURL(GeoLocationPoint geoLocationPoint) {
		StringBundler sb = new StringBundler(7);

		sb.append(_openWeatherMapConfiguration.apiURL());
		sb.append("?lat=");
		sb.append(String.valueOf(geoLocationPoint.getLatitude()));
		sb.append("&lon=");
		sb.append(String.valueOf(geoLocationPoint.getLongitude()));
		sb.append("&units=metric&format=json&APPID=");
		sb.append(_openWeatherMapConfiguration.apiKey());

		return sb.toString();
	}

	private JSONObject _createOpenWeatherMapDataJSONObject(String rawData) {
		if (Validator.isBlank(rawData)) {
			ProblemUtil.addError(
				getClass().getName(), "empty-response", rawData, null, null,
				new Throwable(
					"OpenWeatherMap returned an empty response. Source URL [ " +
						_openWeatherMapConfiguration.apiURL() + " ]"));

			return null;
		}

		try {
			return _jsonFactory.createJSONObject(rawData);
		}
		catch (JSONException jsonException) {
			_log.error(jsonException.getMessage(), jsonException);

			ProblemUtil.addError(
				getClass().getName(), "invalid-response-format", rawData, null,
				null, jsonException);
		}

		return null;
	}

	private JSONObject _fetchOpenWeatherMapDataJSONObject(
		GeoLocationPoint geoLocationPoint) {

		String url = _buildURL(geoLocationPoint);

		try {
			return _createOpenWeatherMapDataJSONObject(_http.URLtoString(url));
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage(), ioException);

			ProblemUtil.addError(
				getClass().getName(), "invalid-response-format",
				geoLocationPoint.toString(), null, null, ioException);
		}

		return null;
	}

	private JSONObject _getCachedWeatherJSONObject(String cacheKey) {
		return _jsonDataProviderCache.getJSONObject(cacheKey);
	}

	private String _getCacheKey(GeoLocationPoint geoLocationPoint) {
		return String.valueOf(geoLocationPoint.getLatitude()) + "-" +
			String.valueOf(geoLocationPoint.getLongitude());
	}

	private boolean _validateConfiguration() {
		boolean valid = true;

		if (Validator.isBlank(_openWeatherMapConfiguration.apiKey())) {
			ProblemUtil.addError(
				getClass().getName(), "api-key-not-set", null, null, null,
				new Throwable("OpenWeatherMap API key not set"));

			valid = false;
		}

		if (Validator.isBlank(_openWeatherMapConfiguration.apiURL())) {
			ProblemUtil.addError(
				getClass().getName(), "api-url-not-set", null, null, null,
				new Throwable("OpenWeatherMap API url not set"));

			valid = false;
		}

		return valid;
	}

	private boolean _validateJSONResponseData(JSONObject jsonObject) {
		if ((jsonObject == null) || !jsonObject.has("weather")) {
			ProblemUtil.addError(
				getClass().getName(), "invalid-response-data", jsonObject, null,
				null, new Throwable("Invalid response data"));

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenWeatherMapWeatherDataProvider.class);

	@Reference
	private Http _http;

	@Reference
	private JSONDataProviderCache _jsonDataProviderCache;

	@Reference
	private JSONFactory _jsonFactory;

	private volatile OpenWeatherMapConfiguration _openWeatherMapConfiguration;

}