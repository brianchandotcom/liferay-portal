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

package com.liferay.commerce.shipping.engine.remote.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.shipping.engine.remote.internal.configuration.RemoteCommerceShippingEngineConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.math.BigDecimal;

import java.net.URISyntaxException;

import java.nio.charset.StandardCharsets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
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
	property = "commerce.shipping.engine.key=" + RemoteCommerceShippingEngine.KEY,
	service = CommerceShippingEngine.class
)
public class RemoteCommerceShippingEngine implements CommerceShippingEngine {

	public static final String KEY = "remote";

	@Override
	public String getCommerceShippingOptionLabel(String name, Locale locale) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<CommerceShippingOption> getCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException {

		try (CloseableHttpResponse closeableHttpResponse =
				_closeableHttpClient.execute(
					_getHttpGet(commerceContext, commerceOrder, locale))) {

			if (_log.isTraceEnabled()) {
				StatusLine statusLine = closeableHttpResponse.getStatusLine();

				int statusCode = statusLine.getStatusCode();

				_log.trace("Server returned status " + statusCode);
			}

			return _getCommerceShippingOptions(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(), StandardCharsets.UTF_8));
		}
		catch (Exception exception) {
			throw new CommerceShippingEngineException(exception);
		}
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			_getResourceBundle(locale), "remote-description");
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), "remote");
	}

	@Activate
	protected void activate() {
		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		_poolingHttpClientConnectionManager =
			new PoolingHttpClientConnectionManager();

		httpClientBuilder.setConnectionManager(
			_poolingHttpClientConnectionManager);

		_poolingHttpClientConnectionManager.setMaxTotal(20);
		_poolingHttpClientConnectionManager.setValidateAfterInactivity(30000);

		httpClientBuilder.useSystemProperties();

		_closeableHttpClient = httpClientBuilder.build();
	}

	@Deactivate
	protected void deactivate() {
		if (_closeableHttpClient != null) {
			try {
				_closeableHttpClient.close();
			}
			catch (IOException ioException) {
				_log.error("Unable to close client", ioException);
			}

			_closeableHttpClient = null;
		}

		if (_poolingHttpClientConnectionManager != null) {
			_poolingHttpClientConnectionManager.close();

			_poolingHttpClientConnectionManager = null;
		}
	}

	protected CommerceChannel getCommerceChannel(long commerceChannelId)
		throws PortalException {

		return _commerceChannelService.getCommerceChannel(commerceChannelId);
	}

	protected RemoteCommerceShippingEngineConfiguration
			getRemoteCommerceShippingEngineConfiguration(long channelGroupId)
		throws CommerceShippingEngineException {

		try {
			return _configurationProvider.getConfiguration(
				RemoteCommerceShippingEngineConfiguration.class,
				new GroupServiceSettingsLocator(
					channelGroupId,
					RemoteCommerceShippingEngineConfiguration.class.getName()));
		}
		catch (ConfigurationException configurationException) {
			throw new CommerceShippingEngineException(configurationException);
		}
	}

	private void _addCommerceAddressParameters(
			CommerceAddress commerceAddress, String prefix,
			URIBuilder uriBuilder)
		throws PortalException {

		_addParameter(
			prefix + "AddressCity", commerceAddress.getCity(), uriBuilder);

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		_addParameter(
			prefix + "AddressCountryISOCode",
			commerceCountry.getThreeLettersISOCode(), uriBuilder);

		_addParameter(
			prefix + "AddressExternalReferenceCode",
			commerceAddress.getExternalReferenceCode(), uriBuilder);

		_addParameter(
			prefix + "AddressId",
			String.valueOf(commerceAddress.getCommerceAddressId()), uriBuilder);
		_addParameter(
			prefix + "AddressLatitude",
			String.valueOf(commerceAddress.getLatitude()), uriBuilder);
		_addParameter(
			prefix + "AddressLongitude",
			String.valueOf(commerceAddress.getLongitude()), uriBuilder);
		_addParameter(
			prefix + "AddressPhoneNumber", commerceAddress.getPhoneNumber(),
			uriBuilder);

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		if (commerceRegion != null) {
			_addParameter(
				prefix + "AddressRegionISOCode", commerceRegion.getCode(),
				uriBuilder);
		}

		_addParameter(
			prefix + "AddressStreet1", commerceAddress.getStreet1(),
			uriBuilder);
		_addParameter(
			prefix + "AddressStreet2", commerceAddress.getStreet2(),
			uriBuilder);
		_addParameter(
			prefix + "AddressStreet3", commerceAddress.getStreet3(),
			uriBuilder);
		_addParameter(
			prefix + "AddressZip", commerceAddress.getZip(), uriBuilder);
	}

	private void _addCommerceOrderParameters(
			CommerceOrder commerceOrder, URIBuilder uriBuilder)
		throws PortalException {

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		_addParameter(
			"orderAccountExternalReferenceCode",
			commerceAccount.getExternalReferenceCode(), uriBuilder);
		_addParameter(
			"orderAccountId",
			String.valueOf(commerceAccount.getCommerceAccountId()), uriBuilder);

		_addParameter(
			"orderAdvanceStatus", commerceOrder.getAdvanceStatus(), uriBuilder);

		_addCommerceAddressParameters(
			commerceOrder.getBillingAddress(), "orderBilling", uriBuilder);

		_addParameter(
			"orderCouponCode", commerceOrder.getCouponCode(), uriBuilder);
		_addParameter(
			"orderExternalReferenceCode",
			commerceOrder.getExternalReferenceCode(), uriBuilder);
		_addParameter(
			"orderId", String.valueOf(commerceOrder.getCommerceOrderId()),
			uriBuilder);

		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (commerceOrder.getLastPriceUpdateDate() != null) {
			_addParameter(
				"orderLastPriceUpdateDate",
				dateFormat.format(commerceOrder.getLastPriceUpdateDate()),
				uriBuilder);
		}

		if (commerceOrder.getOrderDate() != null) {
			_addParameter(
				"orderDate", dateFormat.format(commerceOrder.getOrderDate()),
				uriBuilder);
		}

		_addParameter(
			"orderStatus", String.valueOf(commerceOrder.getOrderStatus()),
			uriBuilder);
		_addParameter(
			"orderPaymentMethod", commerceOrder.getCommercePaymentMethodKey(),
			uriBuilder);
		_addParameter(
			"orderPaymentStatus",
			String.valueOf(commerceOrder.getPaymentStatus()), uriBuilder);
		_addParameter(
			"orderPurchaseOrderNumber", commerceOrder.getPurchaseOrderNumber(),
			uriBuilder);

		if (commerceOrder.getRequestedDeliveryDate() != null) {
			_addParameter(
				"orderRequestedDeliveryDate",
				dateFormat.format(commerceOrder.getRequestedDeliveryDate()),
				uriBuilder);
		}

		_addCommerceAddressParameters(
			commerceOrder.getShippingAddress(), "orderShipping", uriBuilder);

		_addParameter(
			"orderShippingAmount",
			String.valueOf(commerceOrder.getShippingAmount()), uriBuilder);
		_addParameter(
			"orderShippingDiscountAmount",
			String.valueOf(commerceOrder.getShippingDiscountAmount()),
			uriBuilder);
		_addParameter(
			"orderShippingDiscountPercentageLevel1",
			String.valueOf(commerceOrder.getShippingDiscountPercentageLevel1()),
			uriBuilder);
		_addParameter(
			"orderShippingDiscountPercentageLevel2",
			String.valueOf(commerceOrder.getShippingDiscountPercentageLevel2()),
			uriBuilder);
		_addParameter(
			"orderShippingDiscountPercentageLevel3",
			String.valueOf(commerceOrder.getShippingDiscountPercentageLevel3()),
			uriBuilder);
		_addParameter(
			"orderShippingDiscountPercentageLevel4",
			String.valueOf(commerceOrder.getShippingDiscountPercentageLevel4()),
			uriBuilder);

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		_addParameter(
			"orderShippingMethod", commerceShippingMethod.getEngineKey(),
			uriBuilder);

		_addParameter(
			"orderSubtotal", String.valueOf(commerceOrder.getSubtotal()),
			uriBuilder);
		_addParameter(
			"orderSubtotalDiscountAmount",
			String.valueOf(commerceOrder.getSubtotalDiscountAmount()),
			uriBuilder);
		_addParameter(
			"orderSubtotalDiscountPercentageLevel1",
			String.valueOf(commerceOrder.getSubtotalDiscountPercentageLevel1()),
			uriBuilder);
		_addParameter(
			"orderSubtotalDiscountPercentageLevel2",
			String.valueOf(commerceOrder.getSubtotalDiscountPercentageLevel2()),
			uriBuilder);
		_addParameter(
			"orderSubtotalDiscountPercentageLevel3",
			String.valueOf(commerceOrder.getSubtotalDiscountPercentageLevel3()),
			uriBuilder);
		_addParameter(
			"orderSubtotalDiscountPercentageLevel4",
			String.valueOf(commerceOrder.getSubtotalDiscountPercentageLevel4()),
			uriBuilder);
		_addParameter(
			"orderTaxAmount", String.valueOf(commerceOrder.getTaxAmount()),
			uriBuilder);
		_addParameter(
			"orderTotal", String.valueOf(commerceOrder.getTotal()), uriBuilder);
		_addParameter(
			"orderTotalDiscountAmount",
			String.valueOf(commerceOrder.getTotalDiscountAmount()), uriBuilder);
		_addParameter(
			"orderTotalDiscountPercentageLevel1",
			String.valueOf(commerceOrder.getTotalDiscountPercentageLevel1()),
			uriBuilder);
		_addParameter(
			"orderTotalDiscountPercentageLevel2",
			String.valueOf(commerceOrder.getTotalDiscountPercentageLevel2()),
			uriBuilder);
		_addParameter(
			"orderTotalDiscountPercentageLevel3",
			String.valueOf(commerceOrder.getTotalDiscountPercentageLevel3()),
			uriBuilder);
		_addParameter(
			"orderTotalDiscountPercentageLevel4",
			String.valueOf(commerceOrder.getTotalDiscountPercentageLevel4()),
			uriBuilder);
		_addParameter(
			"orderTransactionId", commerceOrder.getTransactionId(), uriBuilder);
	}

	private void _addParameter(
		String parameterName, String parameterValue, URIBuilder uriBuilder) {

		if (Validator.isNotNull(parameterValue)) {
			uriBuilder.addParameter(parameterName, parameterValue);
		}
	}

	private List<CommerceShippingOption> _getCommerceShippingOptions(
			String result)
		throws IOException {

		List<CommerceShippingOption> commerceShippingOptions =
			new ArrayList<>();

		JsonNode rootJsonNode = _objectMapper.readTree(result);

		for (JsonNode jsonNode : rootJsonNode) {
			JsonNode nameJsonNode = jsonNode.get("name");
			JsonNode labelJsonNode = jsonNode.get("label");
			JsonNode amountJsonNode = jsonNode.get("amount");

			CommerceShippingOption commerceShippingOption =
				new CommerceShippingOption(
					nameJsonNode.textValue(), labelJsonNode.textValue(),
					BigDecimal.valueOf(amountJsonNode.doubleValue()));

			commerceShippingOptions.add(commerceShippingOption);
		}

		return commerceShippingOptions;
	}

	private HttpGet _getHttpGet(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws PortalException, URISyntaxException {

		RemoteCommerceShippingEngineConfiguration
			commerceShippingEngineConfiguration =
				getRemoteCommerceShippingEngineConfiguration(
					commerceContext.getCommerceChannelGroupId());

		URIBuilder uriBuilder = new URIBuilder(
			commerceShippingEngineConfiguration.shippingOptionsEndpointURL());

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		_addParameter(
			"accountExternalReferenceCode",
			commerceAccount.getExternalReferenceCode(), uriBuilder);
		_addParameter(
			"accountId", String.valueOf(commerceAccount.getCommerceAccountId()),
			uriBuilder);

		CommerceChannel commerceChannel = getCommerceChannel(
			commerceContext.getCommerceChannelId());

		_addParameter(
			"channelCurrencyCode", commerceChannel.getCommerceCurrencyCode(),
			uriBuilder);
		_addParameter(
			"channelExternalReferenceCode",
			commerceChannel.getExternalReferenceCode(), uriBuilder);
		_addParameter(
			"channelId", String.valueOf(commerceChannel.getCommerceChannelId()),
			uriBuilder);
		_addParameter(
			"channelType", String.valueOf(commerceChannel.getType()),
			uriBuilder);

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		_addParameter("currencyCode", commerceCurrency.getCode(), uriBuilder);

		_addParameter(
			"siteType", String.valueOf(commerceContext.getCommerceSiteType()),
			uriBuilder);

		_addCommerceOrderParameters(commerceOrder, uriBuilder);

		_addParameter("locale", locale.toString(), uriBuilder);

		HttpGet httpGet = new HttpGet(uriBuilder.build());

		if (Validator.isNotNull(
				commerceShippingEngineConfiguration.
					shippingOptionsEndpointAuthorizationToken())) {

			String shippingOptionsEndpointAuthorizationToken =
				commerceShippingEngineConfiguration.
					shippingOptionsEndpointAuthorizationToken();

			httpGet.addHeader(
				"Authorization",
				"token " + shippingOptionsEndpointAuthorizationToken);
		}

		return httpGet;
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteCommerceShippingEngine.class);

	private CloseableHttpClient _closeableHttpClient;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final ObjectMapper _objectMapper = new ObjectMapper();
	private PoolingHttpClientConnectionManager
		_poolingHttpClientConnectionManager;

}