/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.geolocation.microsoft.internal.provider;

import com.liferay.geolocation.exception.GeolocationException;
import com.liferay.geolocation.microsoft.internal.configuration.MicrosoftGeolocationConfiguration;
import com.liferay.geolocation.microsoft.internal.model.MicrosoftGeolocationAddress;
import com.liferay.geolocation.microsoft.internal.model.MicrosoftGeolocationPosition;
import com.liferay.geolocation.model.GeolocationAddress;
import com.liferay.geolocation.model.GeolocationPosition;
import com.liferay.geolocation.provider.GeolocationProvider;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.geolocation.microsoft.internal.configuration.MicrosoftGeolocationConfiguration",
	immediate = true, service = GeolocationProvider.class
)
public class MicrosoftGeolocationProvider implements GeolocationProvider {

	@Override
	public GeolocationAddress getGeolocationAddress(
			GeolocationPosition geolocationPosition)
		throws GeolocationException {

		try {
			return _getGeolocationAddress(geolocationPosition);
		}
		catch (GeolocationException ge) {
			throw ge;
		}
		catch (Exception e) {
			throw new GeolocationException(e);
		}
	}

	@Override
	public GeolocationAddress getGeolocationAddress(String ipAddress)
		throws GeolocationException {

		throw new UnsupportedOperationException();
	}

	@Override
	public GeolocationPosition getGeolocationPosition(
			GeolocationAddress geolocationAddress)
		throws GeolocationException {

		try {
			return _getGeolocationPosition(geolocationAddress);
		}
		catch (GeolocationException ge) {
			throw ge;
		}
		catch (Exception e) {
			throw new GeolocationException(e);
		}
	}

	@Override
	public GeolocationPosition getGeolocationPosition(String ipAddress)
		throws GeolocationException {

		throw new UnsupportedOperationException();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		MicrosoftGeolocationConfiguration microsoftGeolocationConfiguration =
			ConfigurableUtil.createConfigurable(
				MicrosoftGeolocationConfiguration.class, properties);

		_bingApiKey = microsoftGeolocationConfiguration.bingApiKey();
	}

	@Deactivate
	protected void deactivate() {
		_bingApiKey = null;
	}

	private void _addParameter(StringBundler sb, String name, String value) {
		if (Validator.isNull(value)) {
			return;
		}

		sb.append(name);
		sb.append(CharPool.EQUAL);
		sb.append(URLCodec.encodeURL(value));
		sb.append(CharPool.AMPERSAND);
	}

	private GeolocationAddress _getGeolocationAddress(
			GeolocationPosition geolocationPosition)
		throws Exception {

		if (Validator.isNull(_bingApiKey)) {
			throw new GeolocationException(
				"Microsoft Bing API key is not configured properly");
		}

		String url = _getUrl(geolocationPosition);

		JSONObject resourceJSONObject = _getResourceJSONObject(url);

		JSONObject addressJSONObject = resourceJSONObject.getJSONObject(
			"address");

		return new MicrosoftGeolocationAddress(
			addressJSONObject.getString("countryRegionIso2"),
			addressJSONObject.getString("adminDistrict"),
			addressJSONObject.getString("locality"),
			addressJSONObject.getString("addressLine"),
			addressJSONObject.getString("postalCode"));
	}

	private GeolocationPosition _getGeolocationPosition(
			GeolocationAddress geolocationAddress)
		throws Exception {

		if (Validator.isNull(_bingApiKey)) {
			throw new GeolocationException(
				"Microsoft Bing API key is not configured properly");
		}

		String url = _getUrl(geolocationAddress);

		JSONObject resourceJSONObject = _getResourceJSONObject(url);

		JSONObject pointJSONObject = resourceJSONObject.getJSONObject("point");

		JSONArray coordinatesJSONArray = pointJSONObject.getJSONArray(
			"coordinates");

		return new MicrosoftGeolocationPosition(
			coordinatesJSONArray.getDouble(0),
			coordinatesJSONArray.getDouble(1));
	}

	private JSONObject _getResourceJSONObject(String url) throws Exception {
		Http.Options options = new Http.Options();

		options.setLocation(url);

		String json = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode != HttpServletResponse.SC_OK) {
			throw new GeolocationException(
				"Microsoft geolocation returned an error code (" +
					responseCode + StringPool.CLOSE_PARENTHESIS);
		}

		String xMsBmWsInfo = response.getHeader("X-MS-BM-WS-INFO");

		if (Validator.isNotNull(xMsBmWsInfo) && xMsBmWsInfo.equals("1")) {
			throw new GeolocationException(
				"Microsoft geolocation is temporarily unavailable");
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		JSONArray resourceSetsJSONArray = jsonObject.getJSONArray(
			"resourceSets");

		JSONObject resourceSetJSONObject = resourceSetsJSONArray.getJSONObject(
			0);

		JSONArray resourcesJSONArray = resourceSetJSONObject.getJSONArray(
			"resources");

		if (resourcesJSONArray.length() == 0) {
			throw new GeolocationException(
				"Microsoft geolocation did not return a result");
		}

		return resourcesJSONArray.getJSONObject(0);
	}

	private String _getUrl(GeolocationAddress geolocationAddress) {
		StringBundler sb = new StringBundler();

		sb.append(_LOCATIONS_BASE_URL);
		sb.append(StringPool.QUESTION);

		_addParameter(sb, "addressLine", geolocationAddress.getStreet());
		_addParameter(sb, "adminDistrict", geolocationAddress.getRegionCode());
		_addParameter(sb, "countryRegion", geolocationAddress.getCountryCode());
		_addParameter(sb, "key", _bingApiKey);
		_addParameter(sb, "locality", geolocationAddress.getCity());
		_addParameter(sb, "postalCode", geolocationAddress.getZip());

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private String _getUrl(GeolocationPosition geolocationPosition) {
		StringBundler sb = new StringBundler();

		sb.append(_LOCATIONS_BASE_URL);
		sb.append(geolocationPosition.getLatitude());
		sb.append(StringPool.COMMA);
		sb.append(geolocationPosition.getLongitude());
		sb.append(StringPool.QUESTION);

		_addParameter(sb, "key", _bingApiKey);
		_addParameter(sb, "incl", "ciso2");

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static final String _LOCATIONS_BASE_URL =
		"https://dev.virtualearth.net/REST/v1/Locations/";

	private volatile String _bingApiKey;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}