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

package com.liferay.commerce.tax.engine.remote.internal;

import com.liferay.commerce.exception.CommerceTaxEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.tax.CommerceTaxCalculateRequest;
import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.tax.CommerceTaxValue;
import com.liferay.commerce.tax.engine.remote.internal.configuration.RemoteCommerceTaxConfiguration;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.petra.apache.http.components.URIBuilder;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.math.BigDecimal;

import java.net.URISyntaxException;

import java.nio.charset.StandardCharsets;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.tax.engine.key=" + RemoteCommerceTaxEngine.KEY,
	service = CommerceTaxEngine.class
)
public class RemoteCommerceTaxEngine implements CommerceTaxEngine {

	public static final String KEY = "remote";

	@Override
	public CommerceTaxValue getCommerceTaxValue(
			CommerceTaxCalculateRequest commerceTaxCalculateRequest)
		throws CommerceTaxEngineException {


		try {
			Http.Options options = _getHttpOptions(
				commerceTaxCalculateRequest);

			String json = _http.URLtoString(options);

			if (_log.isDebugEnabled()) {
				Http.Response response = options.getResponse();

				_log.debug(
					"Reponse code " + response.getResponseCode());
			}

			return _getCommerceTaxValue(json);
		}
		catch (Exception exception) {
			throw new CommerceTaxEngineException(exception);
		}
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			_getResourceBundle(locale), "remote-description");
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), KEY);
	}

	private Map<String, String> _getCommerceAddressParameters(
			long commerceAddressId, String prefix)
		throws Exception {

		CommerceAddress commerceAddress =
			_commerceAddressService.getCommerceAddress(commerceAddressId);

		return HashMapBuilder.put(
			prefix + "AddressCity", commerceAddress.getCity()
		).put(
			prefix + "AddressCountryISOCode",
			() -> {
				Country country = commerceAddress.getCountry();

				return country.getA3();
			}
		).put(
			prefix + "AddressExternalReferenceCode",
			commerceAddress.getExternalReferenceCode()
		).put(
			prefix + "AddressId", String.valueOf(commerceAddressId)
		).put(
			prefix + "AddressLatitude",
			String.valueOf(commerceAddress.getLatitude())
		).put(
			prefix + "AddressLongitude",
			String.valueOf(commerceAddress.getLongitude())
		).put(
			prefix + "AddressPhoneNumber", commerceAddress.getPhoneNumber()
		).put(
			prefix + "AddressRegionISOCode",
			() -> {
				Region region = commerceAddress.getRegion();

				return region.getRegionCode();
			}
		).put(
			prefix + "AddressStreet1", commerceAddress.getStreet1()
		).put(
			prefix + "AddressStreet2", commerceAddress.getStreet2()
		).put(
			prefix + "AddressStreet3", commerceAddress.getStreet3()
		).put(
			prefix + "AddressType", String.valueOf(commerceAddress.getType())
		).put(
			prefix + "AddressZip", commerceAddress.getZip()
		).build();
	}

	private CommerceTaxValue _getCommerceTaxValue(String json)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(json);

		return new CommerceTaxValue(
			jsonObject.getString("name"), jsonObject.getString("label"),
			BigDecimal.valueOf(jsonObject.getDouble("amount")));
	}

	private Http.Options _getHttpOptions(
			CommerceTaxCalculateRequest commerceTaxCalculateRequest)
		throws Exception {

		RemoteCommerceTaxConfiguration remoteCommerceTaxConfiguration =
			_configurationProvider.getConfiguration(
				RemoteCommerceTaxConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceTaxCalculateRequest.getChannelGroupId(),
					RemoteCommerceTaxConfiguration.class.getName()));

		HttpGet httpGet = new HttpGet(
			URIBuilder.create(
				remoteCommerceTaxConfiguration.taxValueEndpointURL()
			).addParameters(
				_getCommerceAddressParameters(
					commerceTaxCalculateRequest.getCommerceBillingAddressId(),
					"billing")
			).addParameter(
				"percentage",
				String.valueOf(commerceTaxCalculateRequest.isPercentage())
			).addParameter(
				"price", String.valueOf(commerceTaxCalculateRequest.getPrice())
			).addParameters(
				_getCommerceAddressParameters(
					commerceTaxCalculateRequest.getCommerceShippingAddressId(),
					"shipping")
			).addParameter(
				"taxCategoryId",
				String.valueOf(commerceTaxCalculateRequest.getTaxCategoryId())
			).addParameter(
				"taxMethod",
				() -> {
					CommerceTaxMethod commerceTaxMethod =
						_commerceTaxMethodService.getCommerceTaxMethod(
							commerceTaxCalculateRequest.
								getCommerceTaxMethodId());

					return commerceTaxMethod.getEngineKey();
				}
			).addParameter(
				"taxMethodPercentage",
				() -> {
					CommerceTaxMethod commerceTaxMethod =
						_commerceTaxMethodService.getCommerceTaxMethod(
							commerceTaxCalculateRequest.
								getCommerceTaxMethodId());

					return String.valueOf(commerceTaxMethod.isPercentage());
				}
			).build());

		if (Validator.isNotNull(
				remoteCommerceTaxConfiguration.
					taxValueEndpointAuthorizationToken())) {

			String taxValueEndpointAuthorizationToken =
				remoteCommerceTaxConfiguration.
					taxValueEndpointAuthorizationToken();

			httpGet.addHeader(
				"Authorization", "token " + taxValueEndpointAuthorizationToken);
		}

		return httpGet;
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteCommerceTaxEngine.class);

	private CloseableHttpClient _closeableHttpClient;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	private PoolingHttpClientConnectionManager
		_poolingHttpClientConnectionManager;

}
