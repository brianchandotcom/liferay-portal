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

package com.liferay.portal.search.tuning.blueprints.ipstack.internal.dataprovider;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.engine.cache.JSONDataProviderCache;
import com.liferay.portal.search.tuning.blueprints.engine.spi.dataprovider.GeoLocationDataProvider;
import com.liferay.portal.search.tuning.blueprints.ipstack.internal.configuration.IPStackConfiguration;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

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
	configurationPid = "com.liferay.portal.search.tuning.blueprints.ipstack.internal.configuration.IPStackConfiguration",
	immediate = true, property = "name=ipstack",
	service = GeoLocationDataProvider.class
)
public class IPStackDataProvider implements GeoLocationDataProvider {

	@Override
	public Optional<JSONObject> getGeoLocationData(
		String ipAddress, Messages messages) {

		return Optional.ofNullable(
			getIpStackDataJSONObject(ipAddress, messages));
	}

	@Override
	public Optional<GeoLocationPoint> getGeoLocationPoint(
		String ipAddress, Messages messages) {

		JSONObject jsonObject = getIpStackDataJSONObject(ipAddress, messages);

		if (jsonObject != null) {
			double latitude = jsonObject.getDouble("latitude");
			double longitude = jsonObject.getDouble("longitude");

			return Optional.of(new GeoLocationPoint(latitude, longitude));
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

	protected JSONObject getIpStackDataJSONObject(
		String ipAddress1, Messages messages) {

		String ipAddress2 = getIpAddress(ipAddress1);

		if (!_ipStackConfiguration.isEnabled() ||
			!_validateConfiguration(messages) ||
			!_validateIPAddress(ipAddress2, messages)) {

			return null;
		}

		JSONObject ipStackDataJsonObject1 = _getCachedIPStackDataJSONObject(
			ipAddress1);

		if (ipStackDataJsonObject1 != null) {
			return ipStackDataJsonObject1;
		}

		JSONObject ipStackDataJsonObject2 = _fetchIPStackDataJSONObject(
			ipAddress2, messages);

		if (!_validateJSONResponseData(ipStackDataJsonObject2, messages)) {
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

		sb.append(_ipStackConfiguration.apiURL());
		sb.append("/");
		sb.append(ipAddress);
		sb.append("?access_key=");
		sb.append(_ipStackConfiguration.apiKey());

		return sb.toString();
	}

	private JSONObject _createIPStackDataJSONObject(
		String rawData, Messages messages) {

		if (Validator.isBlank(rawData)) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.empty-response"
				).msg(
					"IPStack response was empty"
				).severity(
					Severity.ERROR
				).build());

			return null;
		}

		try {
			return _jsonFactory.createJSONObject(rawData);
		}
		catch (JSONException jsonException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.invalid-response-format"
				).msg(
					jsonException.getMessage()
				).severity(
					Severity.ERROR
				).throwable(
					jsonException
				).build());

			_log.error(jsonException.getMessage(), jsonException);
		}

		return null;
	}

	private JSONObject _fetchIPStackDataJSONObject(
		String ipAddress, Messages messages) {

		String url = _buildURL(ipAddress);

		try {
			return _createIPStackDataJSONObject(
				_http.URLtoString(url), messages);
		}
		catch (IOException ioException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.network-error"
				).msg(
					ioException.getMessage()
				).severity(
					Severity.ERROR
				).throwable(
					ioException
				).build());

			_log.error(ioException.getMessage(), ioException);
		}

		return null;
	}

	private JSONObject _getCachedIPStackDataJSONObject(String ipAddress) {
		return _jsonDataProviderCache.getJSONObject(ipAddress);
	}

	private boolean _validateConfiguration(Messages messages) {
		boolean valid = true;

		if (Validator.isBlank(_ipStackConfiguration.apiKey())) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.api-key-not-configured"
				).msg(
					"IPStack API key is not configured"
				).severity(
					Severity.ERROR
				).build());

			valid = false;
		}

		if (Validator.isBlank(_ipStackConfiguration.apiURL())) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.api-url-not-configured"
				).msg(
					"IPStack API url is not configured"
				).severity(
					Severity.ERROR
				).build());

			valid = false;
		}

		return valid;
	}

	private boolean _validateIPAddress(String ipAddress, Messages messages) {
		ipAddress = StringUtil.trim(ipAddress);

		if (Validator.isBlank(ipAddress)) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.empty-ip-address"
				).msg(
					"No IP address provided"
				).severity(
					Severity.ERROR
				).build());

			return false;
		}

		Inet4Address address;

		try {
			address = (Inet4Address)InetAddress.getByName(ipAddress);
		}
		catch (UnknownHostException unknownHostException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.invalid-ip"
				).msg(
					unknownHostException.getMessage()
				).severity(
					Severity.ERROR
				).throwable(
					unknownHostException
				).build());

			_log.error(unknownHostException.getMessage(), unknownHostException);

			return false;
		}
		catch (SecurityException securityException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.security-exception"
				).msg(
					securityException.getMessage()
				).severity(
					Severity.ERROR
				).throwable(
					securityException
				).build());

			_log.error(securityException.getMessage(), securityException);

			return false;
		}

		if (address.isSiteLocalAddress() || address.isAnyLocalAddress() ||
			address.isLinkLocalAddress() || address.isLoopbackAddress() ||
			address.isMulticastAddress()) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.no-geolocation-data-for-private-ip-address"
				).msg(
					"Geolocation data is unavailable a private IP address"
				).rootValue(
					ipAddress
				).severity(
					Severity.ERROR
				).build());

			return false;
		}

		return true;
	}

	private boolean _validateJSONResponseData(
		JSONObject jsonObject, Messages messages) {

		if ((jsonObject == null) || !jsonObject.has("latitude") ||
			!jsonObject.has("longitude")) {

			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"ipstack.error.invalid-response-data"
				).msg(
					"Invalid IPStack response data"
				).rootObject(
					jsonObject
				).severity(
					Severity.ERROR
				).build());

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IPStackDataProvider.class);

	@Reference
	private Http _http;

	private volatile IPStackConfiguration _ipStackConfiguration;

	@Reference
	private JSONDataProviderCache _jsonDataProviderCache;

	@Reference
	private JSONFactory _jsonFactory;

}