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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.method.remote.internal.configuration.RemoteCommercePaymentMethodConfiguration;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.net.InetSocketAddress;
import java.net.URI;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Ivica Cardic
 */
public class RemoteCommercePaymentMethodTest {

	@Before
	public void setUp() throws Exception {
		_remoteCommercePaymentMethod = Mockito.spy(
			new RemoteCommercePaymentMethod());

		_remoteCommercePaymentMethod.activate();

		Mockito.doReturn(
			_getRemoteCommercePaymentMethodConfiguration()
		).when(
			_remoteCommercePaymentMethod
		).getRemoteCommercePaymentMethodConfiguration(
			Mockito.anyLong()
		);

		_commerceOrder = _getCommerceOrder();

		Mockito.doReturn(
			_commerceOrder
		).when(
			_remoteCommercePaymentMethod
		).getCommerceOrder(
			Mockito.anyLong()
		);
	}

	@After
	public void tearDown() {
		_commercePaymentResult = null;
		_commercePaymentRequest = null;

		_httpServer.stop(0);

		_recordedRequestBody = null;
		_recordedParameterMap.clear();

		_remoteCommercePaymentMethod.deactivate();
	}

	@Test
	public void testActivateRecurringPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest("true".getBytes(), httpExchange),
			"/commerce/activate-recurring-payment");

		Assert.assertTrue(
			_remoteCommercePaymentMethod.activateRecurringPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/activate-recurring-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testAuthorizePayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/authorize-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.authorizePayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/authorize-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testCancelPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/cancel-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.cancelPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals("/commerce/cancel-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testCancelRecurringPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest("true".getBytes(), httpExchange),
			"/commerce/cancel-recurring-payment");

		Assert.assertTrue(
			_remoteCommercePaymentMethod.cancelRecurringPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/cancel-recurring-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testCapturePayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/capture-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.capturePayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/capture-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testCompletePayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/complete-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.completePayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/complete-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testCompleteRecurringPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/complete-recurring-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.completeRecurringPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/complete-recurring-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testGetSubscriptionValidity() throws Exception {
		_startHttpServer(
			httpExchange -> _handleGetRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/subscription-validity");

		_remoteCommercePaymentMethod.getSubscriptionValidity(
			_getCommercePaymentRequest());

		Assert.assertEquals(
			_commercePaymentRequest.getTransactionId(),
			_recordedParameterMap.get("transactionId"));
	}

	@Test
	public void testPartiallyRefundPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/partially-refund-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.partiallyRefundPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/partially-refund-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testProcessPaymentPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/process-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.processPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/process-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testProcessRecurringPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/process-recurring-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.processRecurringPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/process-recurring-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testRefundPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/refund-payment");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.refundPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals("/commerce/refund-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testSuspendRecurringPayment() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest("true".getBytes(), httpExchange),
			"/commerce/suspend-recurring-payment");

		Assert.assertTrue(
			_remoteCommercePaymentMethod.suspendRecurringPayment(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/suspend-recurring-payment", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	@Test
	public void testVoidTransaction() throws Exception {
		_startHttpServer(
			httpExchange -> _handlePostRequest(
				_getCommercePaymentResultContent(), httpExchange),
			"/commerce/void-transaction");

		_assertCommercePaymentResult(
			_remoteCommercePaymentMethod.voidTransaction(
				_getCommercePaymentRequest()));

		Assert.assertEquals(
			"/commerce/void-transaction", _requestURI.toString());

		_assertCommercePaymentRequest();
	}

	private void _assertCommerceBillingAddress(
			BillingAddress billingAddress, CommerceAddress commerceAddress)
		throws Exception {

		Assert.assertEquals(
			commerceAddress.getCity(), billingAddress.getCity());

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		Assert.assertEquals(
			commerceCountry.getThreeLettersISOCode(),
			billingAddress.getCountryISOCode());

		Assert.assertEquals(
			Long.valueOf(commerceAddress.getCommerceAddressId()),
			billingAddress.getId());
		Assert.assertEquals(
			commerceAddress.getExternalReferenceCode(),
			billingAddress.getExternalReferenceCode());
		Assert.assertEquals(
			commerceAddress.getLatitude(), billingAddress.getLatitude(), 0);
		Assert.assertEquals(
			commerceAddress.getLongitude(), billingAddress.getLongitude(), 0);
		Assert.assertEquals(
			commerceAddress.getName(), billingAddress.getName());
		Assert.assertEquals(
			commerceAddress.getPhoneNumber(), billingAddress.getPhoneNumber());

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		Assert.assertEquals(
			String.valueOf(commerceRegion.getCode()),
			billingAddress.getRegionISOCode());

		Assert.assertEquals(
			commerceAddress.getStreet1(), billingAddress.getStreet1());
		Assert.assertEquals(
			commerceAddress.getStreet2(), billingAddress.getStreet2());
		Assert.assertEquals(
			commerceAddress.getStreet3(), billingAddress.getStreet3());
		Assert.assertEquals(commerceAddress.getZip(), billingAddress.getZip());
	}

	private void _assertCommerceOrder(Order order) throws Exception {
		CommerceAccount commerceAccount = _commerceOrder.getCommerceAccount();

		Assert.assertEquals(
			commerceAccount.getExternalReferenceCode(),
			order.getAccountExternalReferenceCode());
		Assert.assertEquals(
			Long.valueOf(commerceAccount.getCommerceAccountId()),
			order.getAccountId());

		Assert.assertEquals(
			_commerceOrder.getAdvanceStatus(), order.getAdvanceStatus());

		_assertCommerceBillingAddress(
			order.getBillingAddress(), _commerceOrder.getBillingAddress());

		CommerceCurrency commerceCurrency =
			_commerceOrder.getCommerceCurrency();

		Assert.assertEquals(
			commerceCurrency.getCode(), order.getCurrencyCode());

		Assert.assertEquals(
			_commerceOrder.getCouponCode(), order.getCouponCode());
		Assert.assertEquals(
			_commerceOrder.getExternalReferenceCode(),
			order.getExternalReferenceCode());
		Assert.assertEquals(
			Long.valueOf(_commerceOrder.getCommerceOrderId()), order.getId());
		Assert.assertEquals(
			_commerceOrder.getLastPriceUpdateDate(),
			order.getLastPriceUpdateDate());
		Assert.assertEquals(
			_commerceOrder.getOrderDate(), order.getOrderDate());
		Assert.assertEquals(
			Integer.valueOf(_commerceOrder.getOrderStatus()),
			order.getOrderStatus());
		Assert.assertEquals(
			_commerceOrder.getCommercePaymentMethodKey(),
			order.getPaymentMethod());
		Assert.assertEquals(
			Integer.valueOf(_commerceOrder.getPaymentStatus()),
			order.getPaymentStatus());
		Assert.assertEquals(
			_commerceOrder.getPurchaseOrderNumber(),
			order.getPurchaseOrderNumber());
		Assert.assertEquals(
			_commerceOrder.getRequestedDeliveryDate(),
			order.getRequestedDeliveryDate());

		_assertCommerceShippingAddress(
			order.getShippingAddress(), _commerceOrder.getShippingAddress());

		Assert.assertEquals(
			_commerceOrder.getShippingAmount(), order.getShippingAmount());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getShippingDiscountAmount()),
			order.getShippingDiscountAmount());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getShippingDiscountPercentageLevel1()),
			order.getShippingDiscountPercentageLevel1());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getShippingDiscountPercentageLevel2()),
			order.getShippingDiscountPercentageLevel2());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getShippingDiscountPercentageLevel3()),
			order.getShippingDiscountPercentageLevel3());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getShippingDiscountPercentageLevel4()),
			order.getShippingDiscountPercentageLevel4());

		CommerceShippingMethod commerceShippingMethod =
			_commerceOrder.getCommerceShippingMethod();

		Assert.assertEquals(
			commerceShippingMethod.getEngineKey(), order.getShippingMethod());

		Assert.assertEquals(_commerceOrder.getSubtotal(), order.getSubtotal());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getSubtotalDiscountAmount()),
			order.getSubtotalDiscountAmount());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getSubtotalDiscountPercentageLevel1()),
			order.getSubtotalDiscountPercentageLevel1());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getSubtotalDiscountPercentageLevel2()),
			order.getSubtotalDiscountPercentageLevel2());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getSubtotalDiscountPercentageLevel3()),
			order.getSubtotalDiscountPercentageLevel3());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getSubtotalDiscountPercentageLevel4()),
			order.getSubtotalDiscountPercentageLevel4());

		Assert.assertEquals(
			_toDouble(_commerceOrder.getTaxAmount()), order.getTaxAmount());
		Assert.assertEquals(_commerceOrder.getTotal(), order.getTotal());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getTotalDiscountAmount()),
			order.getTotalDiscountAmount());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getTotalDiscountPercentageLevel1()),
			order.getTotalDiscountPercentageLevel1());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getTotalDiscountPercentageLevel2()),
			order.getTotalDiscountPercentageLevel2());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getTotalDiscountPercentageLevel3()),
			order.getTotalDiscountPercentageLevel3());
		Assert.assertEquals(
			_toDouble(_commerceOrder.getTotalDiscountPercentageLevel4()),
			order.getTotalDiscountPercentageLevel4());

		Assert.assertEquals(
			_commerceOrder.getTransactionId(), order.getTransactionId());
	}

	private void _assertCommercePaymentRequest() throws Exception {
		JsonNode jsonNode = _objectMapper.readTree(_recordedRequestBody);

		JsonNode amountJsonNode = jsonNode.get("amount");

		Assert.assertEquals(
			_commercePaymentRequest.getAmount(),
			new BigDecimal(amountJsonNode.asText()));

		JsonNode cancelUrlJsonNode = jsonNode.get("cancelUrl");

		Assert.assertEquals(
			_commercePaymentRequest.getCancelUrl(), cancelUrlJsonNode.asText());

		JsonNode localeJsonNode = jsonNode.get("locale");

		Locale locale = _commercePaymentRequest.getLocale();

		Assert.assertEquals(locale.toString(), localeJsonNode.asText());

		JsonNode orderJsonNode = jsonNode.get("order");

		_assertCommerceOrder(
			_objectMapper.convertValue(orderJsonNode, Order.class));

		JsonNode returnUrlJsonNode = jsonNode.get("returnUrl");

		Assert.assertEquals(
			_commercePaymentRequest.getReturnUrl(), returnUrlJsonNode.asText());

		JsonNode transactionIdJsonNode = jsonNode.get("transactionId");

		Assert.assertEquals(
			_commercePaymentRequest.getTransactionId(),
			transactionIdJsonNode.asText());
	}

	private void _assertCommercePaymentResult(
		CommercePaymentResult commercePaymentResult) {

		Assert.assertEquals(
			_commercePaymentResult.getAuthTransactionId(),
			commercePaymentResult.getAuthTransactionId());
		Assert.assertEquals(
			_commercePaymentResult.getCommerceOrderId(),
			commercePaymentResult.getCommerceOrderId());
		Assert.assertEquals(
			_commercePaymentResult.getNewPaymentStatus(),
			commercePaymentResult.getNewPaymentStatus());
		Assert.assertEquals(
			_commercePaymentResult.getRedirectUrl(),
			commercePaymentResult.getRedirectUrl());
		Assert.assertEquals(
			_commercePaymentResult.getRefundId(),
			commercePaymentResult.getRefundId());
		Assert.assertEquals(
			_commercePaymentResult.getResultMessages(),
			commercePaymentResult.getResultMessages());
	}

	private void _assertCommerceShippingAddress(
			ShippingAddress shippingAddress, CommerceAddress commerceAddress)
		throws Exception {

		Assert.assertEquals(
			commerceAddress.getCity(), shippingAddress.getCity());

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		Assert.assertEquals(
			commerceCountry.getThreeLettersISOCode(),
			shippingAddress.getCountryISOCode());

		Assert.assertEquals(
			Long.valueOf(commerceAddress.getCommerceAddressId()),
			shippingAddress.getId());
		Assert.assertEquals(
			commerceAddress.getExternalReferenceCode(),
			shippingAddress.getExternalReferenceCode());
		Assert.assertEquals(
			commerceAddress.getLatitude(), shippingAddress.getLatitude(), 0);
		Assert.assertEquals(
			commerceAddress.getLongitude(), shippingAddress.getLongitude(), 0);
		Assert.assertEquals(
			commerceAddress.getName(), shippingAddress.getName());
		Assert.assertEquals(
			commerceAddress.getPhoneNumber(), shippingAddress.getPhoneNumber());

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		Assert.assertEquals(
			String.valueOf(commerceRegion.getCode()),
			shippingAddress.getRegionISOCode());

		Assert.assertEquals(
			commerceAddress.getStreet1(), shippingAddress.getStreet1());
		Assert.assertEquals(
			commerceAddress.getStreet2(), shippingAddress.getStreet2());
		Assert.assertEquals(
			commerceAddress.getStreet3(), shippingAddress.getStreet3());
		Assert.assertEquals(commerceAddress.getZip(), shippingAddress.getZip());
	}

	private CommerceAccount _getCommerceAccount() {
		CommerceAccount commerceAccount = Mockito.mock(CommerceAccount.class);

		Mockito.when(
			commerceAccount.getEmail()
		).thenReturn(
			"accountEmail"
		);

		Mockito.when(
			commerceAccount.getExternalReferenceCode()
		).thenReturn(
			"accountExternalReferenceCode"
		);

		Mockito.when(
			commerceAccount.getCommerceAccountId()
		).thenReturn(
			12L
		);

		Mockito.when(
			commerceAccount.getName()
		).thenReturn(
			"accountName"
		);

		Mockito.when(
			commerceAccount.getStatus()
		).thenReturn(
			98
		);

		Mockito.when(
			commerceAccount.getTaxId()
		).thenReturn(
			"accountTaxId"
		);

		return commerceAccount;
	}

	private CommerceAddress _getCommerceAddress(
			String city, long commerceAddressId,
			String commerceCountryThreeLettersISOCode,
			String commerceRegionCode, String externalReferenceCode,
			double latitude, double longitude, String name, String phoneNumber,
			String street1, String street2, String street3, int type,
			String zip)
		throws Exception {

		CommerceAddress commerceAddress = Mockito.mock(CommerceAddress.class);

		Mockito.when(
			commerceAddress.getCity()
		).thenReturn(
			city
		);

		Mockito.when(
			commerceAddress.getCommerceAddressId()
		).thenReturn(
			commerceAddressId
		);

		Mockito.when(
			commerceAddress.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		Mockito.when(
			commerceAddress.getLatitude()
		).thenReturn(
			latitude
		);

		Mockito.when(
			commerceAddress.getLongitude()
		).thenReturn(
			longitude
		);

		Mockito.when(
			commerceAddress.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			commerceAddress.getPhoneNumber()
		).thenReturn(
			phoneNumber
		);

		Mockito.when(
			commerceAddress.getStreet1()
		).thenReturn(
			street1
		);

		Mockito.when(
			commerceAddress.getStreet2()
		).thenReturn(
			street2
		);

		Mockito.when(
			commerceAddress.getStreet3()
		).thenReturn(
			street3
		);

		Mockito.when(
			commerceAddress.getType()
		).thenReturn(
			type
		);

		Mockito.when(
			commerceAddress.getZip()
		).thenReturn(
			zip
		);

		CommerceCountry commerceCountry = _getCommerceCountry(
			commerceCountryThreeLettersISOCode);

		Mockito.when(
			commerceAddress.getCommerceCountry()
		).thenReturn(
			commerceCountry
		);

		CommerceRegion commerceRegion = _getCommerceRegion(commerceRegionCode);

		Mockito.when(
			commerceAddress.getCommerceRegion()
		).thenReturn(
			commerceRegion
		);

		return commerceAddress;
	}

	private CommerceCountry _getCommerceCountry(String threeLettersISOCode) {
		CommerceCountry commerceCountry = Mockito.mock(CommerceCountry.class);

		Mockito.when(
			commerceCountry.getThreeLettersISOCode()
		).thenReturn(
			threeLettersISOCode
		);

		return commerceCountry;
	}

	private CommerceCurrency _getCommerceCurrency() {
		CommerceCurrency commerceCurrency = Mockito.mock(
			CommerceCurrency.class);

		Mockito.when(
			commerceCurrency.getCode()
		).thenReturn(
			"currencyCode"
		);

		Mockito.when(
			commerceCurrency.getCommerceCurrencyId()
		).thenReturn(
			8L
		);

		Mockito.when(
			commerceCurrency.getRate()
		).thenReturn(
			new BigDecimal(23)
		);

		Mockito.when(
			commerceCurrency.getRoundingMode()
		).thenReturn(
			"currencyRoundingMode"
		);

		return commerceCurrency;
	}

	private CommerceOrder _getCommerceOrder() throws Exception {
		CommerceOrder commerceOrder = Mockito.mock(CommerceOrder.class);

		CommerceAddress commerceOrderBillingAddress = _getCommerceAddress(
			"billingAddressCity", 13, "USA", "CA", "externalReferenceCode",
			12.3, 45.7, "billingAddressName", "billingAddressPhoneNumber",
			"billingAddressStreet1", "billingAddressStreet2",
			"billingAddressStreet3", 1, "billingAddressZip");

		Mockito.when(
			commerceOrder.getBillingAddress()
		).thenReturn(
			commerceOrderBillingAddress
		);

		CommerceAccount commerceAccount = _getCommerceAccount();

		Mockito.when(
			commerceOrder.getCommerceAccount()
		).thenReturn(
			commerceAccount
		);

		CommerceCurrency commerceCurrency = _getCommerceCurrency();

		Mockito.when(
			commerceOrder.getCommerceCurrency()
		).thenReturn(
			commerceCurrency
		);

		Mockito.when(
			commerceOrder.getCouponCode()
		).thenReturn(
			"couponCode"
		);

		Mockito.when(
			commerceOrder.getExternalReferenceCode()
		).thenReturn(
			"externalReferenceCode"
		);

		Mockito.when(
			commerceOrder.getCommerceOrderId()
		).thenReturn(
			1L
		);

		Mockito.when(
			commerceOrder.getLastPriceUpdateDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			commerceOrder.getOrderDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			commerceOrder.getOrderStatus()
		).thenReturn(
			75
		);

		Mockito.when(
			commerceOrder.getCommercePaymentMethodKey()
		).thenReturn(
			"paymentMethodKey"
		);

		Mockito.when(
			commerceOrder.getPaymentStatus()
		).thenReturn(
			65
		);

		Mockito.when(
			commerceOrder.getPurchaseOrderNumber()
		).thenReturn(
			"purchaseOrderNumber"
		);

		Mockito.when(
			commerceOrder.getRequestedDeliveryDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			commerceOrder.getStatusDate()
		).thenReturn(
			new Date()
		);

		CommerceAddress commerceOrderShippingAddress = _getCommerceAddress(
			"shippingAddressCity", 14, "BES", "BO", "externalReferenceCode",
			22.72, 11.87, "shippingAddressName", "shippingAddressPhoneNumber",
			"shippingAddressStreet1", "shippingAddressStreet2",
			"shippingAddressStreet3", 2, "shippingAddressZip");

		Mockito.when(
			commerceOrder.getShippingAddress()
		).thenReturn(
			commerceOrderShippingAddress
		);

		Mockito.when(
			commerceOrder.getShippingAmount()
		).thenReturn(
			new BigDecimal(123)
		);

		Mockito.when(
			commerceOrder.getShippingDiscountAmount()
		).thenReturn(
			new BigDecimal(124)
		);

		Mockito.when(
			commerceOrder.getShippingDiscountPercentageLevel1()
		).thenReturn(
			new BigDecimal(15)
		);

		Mockito.when(
			commerceOrder.getShippingDiscountPercentageLevel2()
		).thenReturn(
			new BigDecimal(16)
		);

		Mockito.when(
			commerceOrder.getShippingDiscountPercentageLevel3()
		).thenReturn(
			new BigDecimal(17)
		);

		Mockito.when(
			commerceOrder.getShippingDiscountPercentageLevel4()
		).thenReturn(
			new BigDecimal(18)
		);

		CommerceShippingMethod commerceShippingMethod =
			_getCommerceShippingMethod();

		Mockito.when(
			commerceOrder.getCommerceShippingMethod()
		).thenReturn(
			commerceShippingMethod
		);

		Mockito.when(
			commerceOrder.getStatus()
		).thenReturn(
			45
		);

		Mockito.when(
			commerceOrder.getStatusDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			commerceOrder.getSubtotal()
		).thenReturn(
			new BigDecimal(991)
		);

		Mockito.when(
			commerceOrder.getSubtotalDiscountAmount()
		).thenReturn(
			new BigDecimal(891)
		);

		Mockito.when(
			commerceOrder.getSubtotalDiscountPercentageLevel1()
		).thenReturn(
			new BigDecimal(22)
		);

		Mockito.when(
			commerceOrder.getSubtotalDiscountPercentageLevel2()
		).thenReturn(
			new BigDecimal(32)
		);

		Mockito.when(
			commerceOrder.getSubtotalDiscountPercentageLevel3()
		).thenReturn(
			new BigDecimal(42)
		);

		Mockito.when(
			commerceOrder.getSubtotalDiscountPercentageLevel4()
		).thenReturn(
			new BigDecimal(52)
		);

		Mockito.when(
			commerceOrder.getTaxAmount()
		).thenReturn(
			new BigDecimal(764)
		);

		Mockito.when(
			commerceOrder.getTotal()
		).thenReturn(
			new BigDecimal(9256)
		);

		Mockito.when(
			commerceOrder.getTotalDiscountAmount()
		).thenReturn(
			new BigDecimal(8257)
		);

		Mockito.when(
			commerceOrder.getTotalDiscountPercentageLevel1()
		).thenReturn(
			new BigDecimal(29)
		);

		Mockito.when(
			commerceOrder.getTotalDiscountPercentageLevel2()
		).thenReturn(
			new BigDecimal(39)
		);

		Mockito.when(
			commerceOrder.getTotalDiscountPercentageLevel3()
		).thenReturn(
			new BigDecimal(49)
		);

		Mockito.when(
			commerceOrder.getTotalDiscountPercentageLevel4()
		).thenReturn(
			new BigDecimal(59)
		);

		Mockito.when(
			commerceOrder.getTransactionId()
		).thenReturn(
			"transactionId"
		);

		return commerceOrder;
	}

	private CommercePaymentRequest _getCommercePaymentRequest() {
		_commercePaymentRequest = new CommercePaymentRequest(
			new BigDecimal(123), "cancelUrl", 1, LocaleUtil.ENGLISH,
			"returnUrl", "transactionId");

		return _commercePaymentRequest;
	}

	private byte[] _getCommercePaymentResultContent()
		throws JsonProcessingException {

		_commercePaymentResult = new CommercePaymentResult(
			"authTransactionId", 1, _random.nextInt(), true, "redirectUrl",
			"refundId",
			new ArrayList<String>() {
				{
					add("resultMessage1");
					add("resultMessage2");
				}
			},
			true);

		return _objectMapper.writeValueAsBytes(_commercePaymentResult);
	}

	private CommerceRegion _getCommerceRegion(String code) {
		CommerceRegion commerceRegion = Mockito.mock(CommerceRegion.class);

		Mockito.when(
			commerceRegion.getCode()
		).thenReturn(
			code
		);

		return commerceRegion;
	}

	private CommerceShippingMethod _getCommerceShippingMethod() {
		CommerceShippingMethod commerceShippingMethod = Mockito.mock(
			CommerceShippingMethod.class);

		Mockito.when(
			commerceShippingMethod.getEngineKey()
		).thenReturn(
			"shippingMethodEngineKey"
		);

		Mockito.when(
			commerceShippingMethod.getCommerceShippingMethodId()
		).thenReturn(
			15L
		);

		return commerceShippingMethod;
	}

	private RemoteCommercePaymentMethodConfiguration
		_getRemoteCommercePaymentMethodConfiguration() {

		RemoteCommercePaymentMethodConfiguration
			remoteCommercePaymentMethodConfiguration = Mockito.mock(
				RemoteCommercePaymentMethodConfiguration.class);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				activateRecurringPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/activate-recurring-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				authorizePaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/authorize-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.cancelPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/cancel-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				cancelRecurringPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/cancel-recurring-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.capturePaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/capture-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				completePaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/complete-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				completeRecurringPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/complete-recurring-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				partiallyRefundPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/partially-refund-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.processPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/process-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				processRecurringPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/process-recurring-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.refundPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/refund-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				subscriptionValidityEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/subscription-validity"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				suspendRecurringPaymentEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/suspend-recurring-payment"
		);

		Mockito.when(
			remoteCommercePaymentMethodConfiguration.
				voidTransactionEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/void-transaction"
		);

		return remoteCommercePaymentMethodConfiguration;
	}

	private void _handleGetRequest(
			byte[] responseContent, HttpExchange httpExchange)
		throws IOException {

		_recordQueryParameters(httpExchange.getRequestURI());

		httpExchange.sendResponseHeaders(200, responseContent.length);

		try (OutputStream outputStream = httpExchange.getResponseBody()) {
			outputStream.write(responseContent);

			outputStream.flush();
		}
	}

	private void _handlePostRequest(
			byte[] responseContent, HttpExchange httpExchange)
		throws IOException {

		_recordedRequestBody = com.liferay.portal.kernel.util.StringUtil.read(
			httpExchange.getRequestBody());

		_requestURI = httpExchange.getRequestURI();

		httpExchange.sendResponseHeaders(200, responseContent.length);

		try (OutputStream outputStream = httpExchange.getResponseBody()) {
			outputStream.write(responseContent);

			outputStream.flush();
		}
	}

	private void _recordQueryParameters(URI uri) {
		List<String> parametersValues = StringUtil.split(
			uri.getQuery(), CharPool.AMPERSAND);

		for (String parameterValueString : parametersValues) {
			List<String> parameterValue = StringUtil.split(
				parameterValueString, CharPool.EQUAL);

			_recordedParameterMap.put(
				parameterValue.get(0), parameterValue.get(1));
		}
	}

	private void _startHttpServer(HttpHandler httpHandler, String uri)
		throws Exception {

		_httpServer = HttpServer.create(new InetSocketAddress(_PORT), 0);

		HttpContext context = _httpServer.createContext(uri);

		context.setHandler(httpHandler);

		_httpServer.start();
	}

	private Double _toDouble(BigDecimal value) {
		return value.doubleValue();
	}

	private static final int _PORT = 4250;

	private CommerceOrder _commerceOrder;
	private CommercePaymentRequest _commercePaymentRequest;
	private CommercePaymentResult _commercePaymentResult;
	private HttpServer _httpServer;
	private final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			addMixIn(BillingAddress.class, AddressMyMixIn.class);
			addMixIn(ShippingAddress.class, AddressMyMixIn.class);
		}
	};
	private final Random _random = new Random();
	private final Map<String, String> _recordedParameterMap = new HashMap<>();
	private String _recordedRequestBody;
	private RemoteCommercePaymentMethod _remoteCommercePaymentMethod;
	private URI _requestURI;

	private static class AddressMyMixIn {

		@JsonIgnore
		protected String externalReferenceCode;

	}

}