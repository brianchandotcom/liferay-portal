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

package com.liferay.commerce.payment.method.remote.internal;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.exception.CommercePaymentEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.remote.internal.configuration.RemoteCommercePaymentMethodConfiguration;
import com.liferay.commerce.payment.method.remote.internal.constants.RemoteCommercePaymentMethodConstants;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.petra.apache.http.components.URIBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.payment.engine.method.key=" + RemoteCommercePaymentMethod.KEY,
	service = CommercePaymentMethod.class
)
public class RemoteCommercePaymentMethod implements CommercePaymentMethod {

	public static final String KEY = "remote";

	@Override
	public boolean activateRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return GetterUtil.getBoolean(
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						activateRecurringPaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult authorizePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						authorizePaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult cancelPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						cancelPaymentEndpointURL())));
	}

	@Override
	public boolean cancelRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return GetterUtil.getBoolean(
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						cancelRecurringPaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult capturePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						capturePaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult completePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						completePaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult completeRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						completeRecurringPaymentEndpointURL())));
	}

	@Override
	public String getDescription(Locale locale) {
		return _getResource(locale, "remote-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, KEY);
	}

	@Override
	public int getPaymentType() {
		return CommercePaymentConstants.
			COMMERCE_PAYMENT_METHOD_TYPE_ONLINE_REDIRECT;
	}

	@Override
	public String getServletPath() {
		return RemoteCommercePaymentMethodConstants.SERVLET_PATH;
	}

	@Override
	public boolean getSubscriptionValidity(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return GetterUtil.getBoolean(
			_execute(
				_getGetHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					remoteCommercePaymentMethodConfiguration.
						subscriptionValidityEndpointURL(),
					"transactionId",
					commercePaymentRequest.getTransactionId())));
	}

	@Override
	public boolean isAuthorizeEnabled() {
		return true;
	}

	@Override
	public boolean isCancelEnabled() {
		return true;
	}

	@Override
	public boolean isCaptureEnabled() {
		return true;
	}

	@Override
	public boolean isCompleteEnabled() {
		return true;
	}

	@Override
	public boolean isCompleteRecurringEnabled() {
		return true;
	}

	@Override
	public boolean isPartialRefundEnabled() {
		return true;
	}

	@Override
	public boolean isProcessPaymentEnabled() {
		return true;
	}

	@Override
	public boolean isProcessRecurringEnabled() {
		return true;
	}

	@Override
	public boolean isRefundEnabled() {
		return true;
	}

	@Override
	public boolean isVoidEnabled() {
		return true;
	}

	@Override
	public CommercePaymentResult partiallyRefundPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						partiallyRefundPaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult processPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						processPaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult processRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						processRecurringPaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult refundPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						refundPaymentEndpointURL())));
	}

	@Override
	public boolean suspendRecurringPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return GetterUtil.getBoolean(
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						suspendRecurringPaymentEndpointURL())));
	}

	@Override
	public CommercePaymentResult voidTransaction(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration =
				getRemoteCommercePaymentMethodConfiguration(
					commercePaymentRequest.getCommerceOrderId());

		return _toCommercePaymentResult(
			commercePaymentRequest.getCommerceOrderId(),
			_execute(
				_getPostHttpOptions(
					remoteCommercePaymentMethodConfiguration.
						endpointAuthorizationToken(),
					commercePaymentRequest,
					remoteCommercePaymentMethodConfiguration.
						voidTransactionEndpointURL())));
	}

	protected CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException {

		return _commerceOrderLocalService.getCommerceOrder(commerceOrderId);
	}

	protected RemoteCommercePaymentMethodConfiguration
			getRemoteCommercePaymentMethodConfiguration(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder(commerceOrderId);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		try {
			return _configurationProvider.getConfiguration(
				RemoteCommercePaymentMethodConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getSiteGroupId(),
					RemoteCommercePaymentMethodConfiguration.class.getName()));
		}
		catch (ConfigurationException configurationException) {
			throw new CommercePaymentEngineException(configurationException);
		}
	}

	protected String toContent(CommercePaymentRequest commercePaymentRequest)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		Locale locale = commercePaymentRequest.getLocale();

		jsonObject.put(
			"amount", commercePaymentRequest.getAmount()
		).put(
			"cancelUrl", commercePaymentRequest.getCancelUrl()
		).put(
			"locale", locale.toString()
		).put(
			"order",
			_toOrder(
				getCommerceOrder(commercePaymentRequest.getCommerceOrderId()))
		).put(
			"returnUrl", commercePaymentRequest.getReturnUrl()
		).put(
			"transactionId", commercePaymentRequest.getTransactionId()
		);

		return jsonObject.toJSONString();
	}

	private String _execute(Http.Options options) throws Exception {
		String json = _http.URLtoString(options);

		if (_log.isDebugEnabled()) {
			Http.Response response = options.getResponse();

			_log.debug("Response code " + response.getResponseCode());
		}

		return json;
	}

	private Http.Options _getGetHttpOptions(
			String authorizationToken, String uriString, String... parameters)
		throws Exception {

		Http.Options options = new Http.Options();

		if (Validator.isNotNull(authorizationToken)) {
			options.addHeader("Authorization", "token " + authorizationToken);
		}

		URIBuilder.URIBuilderWrapper uriBuilderWrapper = URIBuilder.create(
			uriString);

		for (int i = 0; i < parameters.length; i = i + 2) {
			uriBuilderWrapper.addParameter(parameters[i], parameters[i + 1]);
		}

		URI uri = uriBuilderWrapper.build();

		options.setLocation(uri.toString());

		return options;
	}

	private Http.Options _getPostHttpOptions(
			String authorizationToken,
			CommercePaymentRequest commercePaymentRequest, String uri)
		throws Exception {

		Http.Options options = new Http.Options();

		options.setBody(
			toContent(commercePaymentRequest), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		if (Validator.isNotNull(authorizationToken)) {
			options.addHeader("Authorization", "token " + authorizationToken);
		}

		options.setLocation(uri);

		options.setPost(true);

		return options;
	}

	private String _getResource(Locale locale, String key) {
		if (locale == null) {
			locale = LocaleUtil.getSiteDefault();
		}

		return LanguageUtil.get(_getResourceBundle(locale), key);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private BillingAddress _toBillingAddress(CommerceAddress commerceAddress)
		throws PortalException {

		BillingAddress billingAddress = new BillingAddress();

		billingAddress.setCity(commerceAddress.getCity());

		Country country = commerceAddress.getCountry();

		billingAddress.setCountryISOCode(country.getA3());

		billingAddress.setExternalReferenceCode(
			commerceAddress.getExternalReferenceCode());
		billingAddress.setId(commerceAddress.getCommerceAddressId());
		billingAddress.setLatitude(commerceAddress.getLatitude());
		billingAddress.setLongitude(commerceAddress.getLongitude());
		billingAddress.setName(commerceAddress.getName());
		billingAddress.setPhoneNumber(commerceAddress.getPhoneNumber());

		Region region = commerceAddress.getRegion();

		billingAddress.setRegionISOCode(region.getRegionCode());

		billingAddress.setStreet1(commerceAddress.getStreet1());
		billingAddress.setStreet2(commerceAddress.getStreet2());
		billingAddress.setStreet3(commerceAddress.getStreet3());
		billingAddress.setZip(commerceAddress.getZip());

		return billingAddress;
	}

	private CommercePaymentResult _toCommercePaymentResult(
			long commerceOrderId, String result)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(result);

		JSONArray resultMessagesJSONArray = jsonObject.getJSONArray(
			"resultMessages");

		List<String> resultMessages = new ArrayList<>();

		for (int i = 0; i < resultMessagesJSONArray.length(); i++) {
			resultMessages.add(resultMessagesJSONArray.getString(i));
		}

		return new CommercePaymentResult(
			jsonObject.getString("authTransactionId"), commerceOrderId,
			jsonObject.getInt("newPaymentStatus"),
			jsonObject.getBoolean("onlineRedirect"),
			jsonObject.getString("redirectUrl"),
			jsonObject.getString("refundId"), resultMessages,
			jsonObject.getBoolean("success"));
	}

	private double _toDouble(BigDecimal value) {
		return value.doubleValue();
	}

	private Object _toOrder(CommerceOrder commerceOrder)
		throws PortalException {

		Order order = new Order();

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		order.setAccountExternalReferenceCode(
			commerceAccount.getExternalReferenceCode());
		order.setAccountId(commerceAccount.getCommerceAccountId());

		order.setAdvanceStatus(commerceOrder.getAdvanceStatus());

		order.setBillingAddress(
			_toBillingAddress(commerceOrder.getBillingAddress()));

		order.setCouponCode(commerceOrder.getCouponCode());

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		order.setCurrencyCode(commerceCurrency.getCode());

		order.setExternalReferenceCode(
			commerceOrder.getExternalReferenceCode());
		order.setId(commerceOrder.getCommerceOrderId());
		order.setLastPriceUpdateDate(commerceOrder.getLastPriceUpdateDate());
		order.setOrderDate(commerceOrder.getOrderDate());
		order.setOrderStatus(commerceOrder.getOrderStatus());
		order.setPaymentMethod(commerceOrder.getCommercePaymentMethodKey());
		order.setPaymentStatus(commerceOrder.getPaymentStatus());
		order.setPurchaseOrderNumber(commerceOrder.getPurchaseOrderNumber());
		order.setRequestedDeliveryDate(
			commerceOrder.getRequestedDeliveryDate());

		order.setShippingAddress(
			_toShippingAddress(commerceOrder.getShippingAddress()));

		order.setShippingAmount(commerceOrder.getShippingAmount());
		order.setShippingDiscountAmount(
			_toDouble(commerceOrder.getShippingDiscountAmount()));
		order.setShippingDiscountPercentageLevel1(
			_toDouble(commerceOrder.getShippingDiscountPercentageLevel1()));
		order.setShippingDiscountPercentageLevel2(
			_toDouble(commerceOrder.getShippingDiscountPercentageLevel2()));
		order.setShippingDiscountPercentageLevel3(
			_toDouble(commerceOrder.getShippingDiscountPercentageLevel3()));
		order.setShippingDiscountPercentageLevel4(
			_toDouble(commerceOrder.getShippingDiscountPercentageLevel4()));

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		order.setShippingMethod(commerceShippingMethod.getEngineKey());

		order.setShippingOption(commerceOrder.getShippingOptionName());
		order.setSubtotal(commerceOrder.getSubtotal());
		order.setSubtotalDiscountAmount(
			_toDouble(commerceOrder.getSubtotalDiscountAmount()));
		order.setSubtotalDiscountPercentageLevel1(
			_toDouble(commerceOrder.getSubtotalDiscountPercentageLevel1()));
		order.setSubtotalDiscountPercentageLevel2(
			_toDouble(commerceOrder.getSubtotalDiscountPercentageLevel2()));
		order.setSubtotalDiscountPercentageLevel3(
			_toDouble(commerceOrder.getSubtotalDiscountPercentageLevel3()));
		order.setSubtotalDiscountPercentageLevel4(
			_toDouble(commerceOrder.getSubtotalDiscountPercentageLevel4()));
		order.setTaxAmountValue(_toDouble(commerceOrder.getTaxAmount()));
		order.setTotal(commerceOrder.getTotal());
		order.setTotalDiscountAmount(
			_toDouble(commerceOrder.getTotalDiscountAmount()));
		order.setTotalDiscountPercentageLevel1(
			_toDouble(commerceOrder.getTotalDiscountPercentageLevel1()));
		order.setTotalDiscountPercentageLevel2(
			_toDouble(commerceOrder.getTotalDiscountPercentageLevel2()));
		order.setTotalDiscountPercentageLevel3(
			_toDouble(commerceOrder.getTotalDiscountPercentageLevel3()));
		order.setTotalDiscountPercentageLevel4(
			_toDouble(commerceOrder.getTotalDiscountPercentageLevel4()));

		order.setTransactionId(commerceOrder.getTransactionId());

		return order;
	}

	private ShippingAddress _toShippingAddress(CommerceAddress commerceAddress)
		throws PortalException {

		ShippingAddress shippingAddress = new ShippingAddress();

		shippingAddress.setCity(commerceAddress.getCity());

		Country country = commerceAddress.getCountry();

		shippingAddress.setCountryISOCode(country.getA3());

		shippingAddress.setExternalReferenceCode(
			commerceAddress.getExternalReferenceCode());
		shippingAddress.setId(commerceAddress.getCommerceAddressId());
		shippingAddress.setLatitude(commerceAddress.getLatitude());
		shippingAddress.setLongitude(commerceAddress.getLongitude());
		shippingAddress.setName(commerceAddress.getName());
		shippingAddress.setPhoneNumber(commerceAddress.getPhoneNumber());

		Region region = commerceAddress.getRegion();

		shippingAddress.setRegionISOCode(region.getRegionCode());

		shippingAddress.setStreet1(commerceAddress.getStreet1());
		shippingAddress.setStreet2(commerceAddress.getStreet2());
		shippingAddress.setStreet3(commerceAddress.getStreet3());
		shippingAddress.setZip(commerceAddress.getZip());

		return shippingAddress;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteCommercePaymentMethod.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}