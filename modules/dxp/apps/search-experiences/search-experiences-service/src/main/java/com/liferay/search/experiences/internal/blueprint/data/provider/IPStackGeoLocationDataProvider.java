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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.search.experiences.configuration.IPStackConfiguration;
import com.liferay.search.experiences.internal.blueprint.data.provider.cache.JSONDataProviderCache;
import com.liferay.search.experiences.internal.problem.ProblemUtil;

import java.io.IOException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
	configurationPid = "com.liferay.search.experiences.configuration.IPStackConfiguration",
	immediate = true, property = "name=ipstack",
	service = GeoLocationDataProvider.class
)
public class IPStackGeoLocationDataProvider implements GeoLocationDataProvider {

	@Override
	public Optional<JSONObject> getGeoLocationData(String ipAddress) {
		return Optional.ofNullable(getIpStackDataJSONObject(ipAddress));
	}

	@Override
	public Optional<GeoLocationPoint> getGeoLocationPoint(String ipAddress) {
		JSONObject jsonObject = getIpStackDataJSONObject(ipAddress);

		if (jsonObject != null) {
			double latitude = jsonObject.getDouble("latitude");
			double longitude = jsonObject.getDouble("longitude");

			return Optional.of(
				_geoBuilders.geoLocationPoint(latitude, longitude));
		}

		return Optional.empty();
	}

	public boolean isEnabled() {
		return _ipStackConfiguration.isEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ipStackConfiguration = ConfigurableUtil.createConfigurable(
			IPStackConfiguration.class, properties);
	}

	protected String getIpAddress(String ipAddress) {
		String testIPAddress = StringUtil.trim(
			_ipStackConfiguration.testIpAddress());

		if (!Validator.isBlank(testIPAddress)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Using IPStack test IP address " + testIPAddress);
			}

			return testIPAddress;
		}

		return ipAddress;
	}

	protected JSONObject getIpStackDataJSONObject(String ipAddress1) {
		String ipAddress2 = getIpAddress(ipAddress1);

		if (!_ipStackConfiguration.isEnabled() || !_validateConfiguration() ||
			!_validateIPAddress(ipAddress2)) {

			return null;
		}

		JSONObject ipStackDataJsonObject1 = _getCachedIPStackDataJSONObject(
			ipAddress2);

		if (ipStackDataJsonObject1 != null) {
			return ipStackDataJsonObject1;
		}

		JSONObject ipStackDataJsonObject2 = _fetchIPStackDataJSONObject(
			ipAddress2);

		if (!_validateJSONResponseData(ipStackDataJsonObject2)) {
			return null;
		}

		_jsonDataProviderCache.put(
			ipAddress2, ipStackDataJsonObject2,
			_ipStackConfiguration.cacheTimeout());

		if (_log.isDebugEnabled()) {
			_log.debug("IPStack data: " + ipStackDataJsonObject2);
		}

		return ipStackDataJsonObject2;
	}

	private String _buildURL(String ipAddress) {
		StringBundler sb = new StringBundler(5);

		String apiURL = _ipStackConfiguration.apiURL();

		sb.append(apiURL);

		if (!apiURL.endsWith("/")) {
			sb.append("/");
		}

		sb.append(ipAddress);
		sb.append("?access_key=");
		sb.append(_ipStackConfiguration.apiKey());

		return sb.toString();
	}

	private JSONObject _createIPStackDataJSONObject(String rawData) {
		if (Validator.isBlank(rawData)) {
			ProblemUtil.addError(
				getClass().getName(), "empty-response", null, null, null,
				new Throwable(
					"IPStack returned an empty response. Source URL [ " +
						_ipStackConfiguration.apiURL() + " ]"));

			return null;
		}

		try {
			return _jsonFactory.createJSONObject(rawData);
		}
		catch (JSONException jsonException) {
			_log.error(jsonException.getMessage(), jsonException);

			ProblemUtil.addError(
				getClass().getName(), "invalid-response-format", null, null,
				null, jsonException);
		}

		return null;
	}

	private JSONObject _fetchIPStackDataJSONObject(String ipAddress) {
		String url = _buildURL(ipAddress);

		try {
			return _createIPStackDataJSONObject(_http.URLtoString(url));
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage(), ioException);

			ProblemUtil.addError(
				getClass().getName(), "network-error", null, null, ipAddress,
				ioException);
		}

		return null;
	}

	private JSONObject _getCachedIPStackDataJSONObject(String ipAddress) {
		return _jsonDataProviderCache.getJSONObject(ipAddress);
	}

	private boolean _validateConfiguration() {
		boolean valid = true;

		if (Validator.isBlank(_ipStackConfiguration.apiKey())) {
			ProblemUtil.addError(
				getClass().getName(), "api-key-not-set", null, null, null,
				new Throwable("IPStack API key not set"));

			valid = false;
		}

		if (Validator.isBlank(_ipStackConfiguration.apiURL())) {
			ProblemUtil.addError(
				getClass().getName(), "api-url-not-set", null, null, null,
				new Throwable("IPStack API url not set"));

			valid = false;
		}

		return valid;
	}

	private boolean _validateIPAddress(String ipAddress) {
		ipAddress = StringUtil.trim(ipAddress);

		if (Validator.isBlank(ipAddress)) {
			ProblemUtil.addError(
				getClass().getName(), "ip-address-was-empty", null, null,
				ipAddress, new Throwable("IP address was empty"));

			return false;
		}

		Inet4Address address;

		try {
			address = (Inet4Address)InetAddress.getByName(ipAddress);
		}
		catch (UnknownHostException unknownHostException) {
			_log.error(unknownHostException.getMessage(), unknownHostException);

			ProblemUtil.addError(
				getClass().getName(), "invalid-ip-address", null, null,
				ipAddress, unknownHostException);

			return false;
		}
		catch (SecurityException securityException) {
			_log.error(securityException.getMessage(), securityException);

			ProblemUtil.addError(
				getClass().getName(), "security-exception", null, null,
				ipAddress, securityException);

			return false;
		}

		if (address.isSiteLocalAddress() || address.isAnyLocalAddress() ||
			address.isLinkLocalAddress() || address.isLoopbackAddress() ||
			address.isMulticastAddress()) {

			ProblemUtil.addError(
				getClass().getName(),
				"geolocation-data-unavailable-for-this-ip-address", null, null,
				ipAddress,
				new Throwable(
					"Geolocation data unavailable for this IP address [ " +
						ipAddress + " ]"));

			return false;
		}

		return true;
	}

	private boolean _validateJSONResponseData(JSONObject jsonObject) {
		if ((jsonObject == null) || !jsonObject.has("latitude") ||
			!jsonObject.has("longitude")) {

			ProblemUtil.addError(
				getClass().getName(), "invalid-response-data", jsonObject, null,
				null,
				new Throwable(
					"IPStack response data was invalid [ " + jsonObject +
						" ]"));

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IPStackGeoLocationDataProvider.class);

	@Reference
	private GeoBuilders _geoBuilders;

	@Reference
	private Http _http;

	private volatile IPStackConfiguration _ipStackConfiguration;

	@Reference
	private JSONDataProviderCache _jsonDataProviderCache;

	@Reference
	private JSONFactory _jsonFactory;

}