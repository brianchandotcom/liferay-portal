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

package com.liferay.commerce.geocoder.bing.internal;

import com.liferay.commerce.exception.CommerceGeocoderException;
import com.liferay.commerce.geocoder.bing.internal.configuration.BingCommerceGeocoderConfiguration;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceGeocoder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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
 */
@Component(
	configurationPid = "com.liferay.commerce.geocoder.bing.internal.configuration.BingCommerceGeocoderConfiguration",
	immediate = true
)
public class BingCommerceGeocoder implements CommerceGeocoder {

	@Override
	public double[] getCoordinates(
			String street, String city, String zip,
			CommerceRegion commerceRegion, CommerceCountry commerceCountry)
		throws CommerceGeocoderException {

		try {
			return _getCoordinates(
				street, city, zip, commerceRegion, commerceCountry);
		}
		catch (CommerceGeocoderException cge) {
			throw cge;
		}
		catch (Exception e) {
			throw new CommerceGeocoderException(e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		BingCommerceGeocoderConfiguration bingCommerceGeocoderConfiguration =
			ConfigurableUtil.createConfigurable(
				BingCommerceGeocoderConfiguration.class, properties);

		_apiKey = bingCommerceGeocoderConfiguration.apiKey();
	}

	@Deactivate
	protected void deactivate() {
		_apiKey = null;
	}

	private void _addParameter(StringBundler sb, String name, String value) {
		if (Validator.isNull(value)) {
			return;
		}

		sb.append(name);
		sb.append(CharPool.EQUAL);
		sb.append(_http.encodeURL(value));
		sb.append(CharPool.AMPERSAND);
	}

	private double[] _getCoordinates(
			String street, String city, String zip,
			CommerceRegion commerceRegion, CommerceCountry commerceCountry)
		throws Exception {

		if (Validator.isNull(_apiKey)) {
			throw new CommerceGeocoderException(
				"Bing commerce geocoder is not configured properly");
		}

		Http.Options options = new Http.Options();

		String url = _getUrl(
			street, city, zip, commerceRegion, commerceCountry);

		options.setLocation(url);

		String json = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode != HttpServletResponse.SC_OK) {
			throw new CommerceGeocoderException(
				"Bing commerce geocoder returned an error code (" +
					responseCode + StringPool.CLOSE_PARENTHESIS);
		}

		String xMsBmWsInfo = response.getHeader("X-MS-BM-WS-INFO");

		if (Validator.isNotNull(xMsBmWsInfo) && xMsBmWsInfo.equals("1")) {
			throw new CommerceGeocoderException(
				"Bing commerce geocoder is temporarily unavailable");
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		JSONArray resourceSetsJSONArray = jsonObject.getJSONArray(
			"resourceSets");

		JSONObject resourceSetJSONObject = resourceSetsJSONArray.getJSONObject(
			0);

		JSONArray resourcesJSONArray = resourceSetJSONObject.getJSONArray(
			"resources");

		if (resourcesJSONArray.length() == 0) {
			throw new CommerceGeocoderException(
				"Bing commerce geocoder did not return any result");
		}

		JSONObject resourceJSONObject = resourcesJSONArray.getJSONObject(0);

		JSONObject pointJSONObject = resourceJSONObject.getJSONObject("point");

		JSONArray coordinatesJSONArray = pointJSONObject.getJSONArray(
			"coordinates");

		return new double[] {
			coordinatesJSONArray.getDouble(0), coordinatesJSONArray.getDouble(1)
		};
	}

	private String _getUrl(
		String street, String city, String zip, CommerceRegion commerceRegion,
		CommerceCountry commerceCountry) {

		StringBundler sb = new StringBundler(28);

		sb.append("https://dev.virtualearth.net/REST/v1/Locations?");

		_addParameter(sb, "key", _apiKey);
		_addParameter(sb, "addressLine", street);
		_addParameter(sb, "locality", city);
		_addParameter(sb, "postalCode", zip);

		if (commerceRegion != null) {
			_addParameter(sb, "adminDistrict", commerceRegion.getCode());
		}

		if (commerceCountry != null) {
			_addParameter(
				sb, "countryRegion", commerceCountry.getTwoLettersISOCode());
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private volatile String _apiKey;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}