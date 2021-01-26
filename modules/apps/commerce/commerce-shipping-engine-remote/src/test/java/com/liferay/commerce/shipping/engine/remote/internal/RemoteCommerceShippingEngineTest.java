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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.shipping.engine.remote.internal.configuration.RemoteCommerceShippingEngineConfiguration;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Ivica Cardic
 */
public class RemoteCommerceShippingEngineTest {

	@Before
	public void setUp() throws Exception {
		_remoteCommerceShippingEngine = Mockito.spy(
			new RemoteCommerceShippingEngine());

		_remoteCommerceShippingEngine.activate();

		Mockito.doReturn(
			_getRemoteCommerceShippingEngineConfiguration()
		).when(
			_remoteCommerceShippingEngine
		).getRemoteCommerceShippingEngineConfiguration(
			Mockito.anyLong()
		);

		_commerceChannel = _getCommerceChannel();

		Mockito.doReturn(
			_commerceChannel
		).when(
			_remoteCommerceShippingEngine
		).getCommerceChannel(
			Mockito.anyLong()
		);

		_startHttpServer();
	}

	@After
	public void tearDown() {
		_httpServer.stop(0);

		_recordedParameterMap.clear();

		_remoteCommerceShippingEngine.deactivate();
	}

	@Test
	public void testCommerceShippingOptions() throws PortalException {
		CommerceContext commerceContext = _getCommerceContext();
		CommerceOrder commerceOrder = _getCommerceOrder();

		List<CommerceShippingOption> commerceShippingOptions =
			_remoteCommerceShippingEngine.getCommerceShippingOptions(
				commerceContext, commerceOrder, LocaleUtil.ENGLISH);

		CommerceShippingOption commerceShippingOption =
			commerceShippingOptions.get(0);

		Assert.assertEquals("label", commerceShippingOption.getLabel());
		Assert.assertEquals("name", commerceShippingOption.getName());
		Assert.assertEquals(
			commerceShippingOption.getAmount(), BigDecimal.valueOf(118.8));

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		Assert.assertEquals(
			commerceAccount.getExternalReferenceCode(),
			_recordedParameterMap.get("accountExternalReferenceCode"));
		Assert.assertEquals(
			commerceAccount.getCommerceAccountId(),
			GetterUtil.getLong(_recordedParameterMap.get("accountId")));

		Assert.assertEquals(
			_commerceChannel.getCommerceCurrencyCode(),
			_recordedParameterMap.get("channelCurrencyCode"));
		Assert.assertEquals(
			_commerceChannel.getExternalReferenceCode(),
			_recordedParameterMap.get("channelExternalReferenceCode"));
		Assert.assertEquals(
			_commerceChannel.getGroupId(),
			GetterUtil.getLong(_recordedParameterMap.get("channelGroupId")));
		Assert.assertEquals(
			_commerceChannel.getCommerceChannelId(),
			GetterUtil.getLong(_recordedParameterMap.get("channelId")));
		Assert.assertEquals(
			_commerceChannel.getType(),
			_recordedParameterMap.get("channelType"));

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		Assert.assertEquals(
			commerceCurrency.getCode(),
			_recordedParameterMap.get("currencyCode"));

		Assert.assertEquals(
			commerceContext.getCommerceSiteType(),
			GetterUtil.getLong(_recordedParameterMap.get("siteType")));

		_assertCommerceOrder(commerceOrder);

		Assert.assertEquals(
			LocaleUtil.ENGLISH.toString(), _recordedParameterMap.get("locale"));
	}

	private void _assertCommerceAddress(
			CommerceAddress commerceAddress, String prefix)
		throws PortalException {

		Assert.assertEquals(
			commerceAddress.getCity(),
			_recordedParameterMap.get(prefix + "AddressCity"));

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		Assert.assertEquals(
			String.valueOf(commerceCountry.getThreeLettersISOCode()),
			_recordedParameterMap.get(prefix + "AddressCountryISOCode"));

		Assert.assertEquals(
			commerceAddress.getExternalReferenceCode(),
			_recordedParameterMap.get(prefix + "AddressExternalReferenceCode"));
		Assert.assertEquals(
			commerceAddress.getCommerceAddressId(),
			GetterUtil.getLong(
				_recordedParameterMap.get(prefix + "AddressId")));
		Assert.assertEquals(
			String.valueOf(commerceAddress.getLatitude()),
			_recordedParameterMap.get(prefix + "AddressLatitude"));
		Assert.assertEquals(
			String.valueOf(commerceAddress.getLongitude()),
			_recordedParameterMap.get(prefix + "AddressLongitude"));
		Assert.assertEquals(
			commerceAddress.getPhoneNumber(),
			_recordedParameterMap.get(prefix + "AddressPhoneNumber"));

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		Assert.assertEquals(
			commerceRegion.getCode(),
			_recordedParameterMap.get(prefix + "AddressRegionISOCode"));

		Assert.assertEquals(
			commerceAddress.getStreet1(),
			_recordedParameterMap.get(prefix + "AddressStreet1"));
		Assert.assertEquals(
			commerceAddress.getStreet2(),
			_recordedParameterMap.get(prefix + "AddressStreet2"));
		Assert.assertEquals(
			commerceAddress.getStreet3(),
			_recordedParameterMap.get(prefix + "AddressStreet3"));
		Assert.assertEquals(
			commerceAddress.getZip(),
			_recordedParameterMap.get(prefix + "AddressZip"));
	}

	private void _assertCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		Assert.assertEquals(
			commerceAccount.getExternalReferenceCode(),
			_recordedParameterMap.get("orderAccountExternalReferenceCode"));
		Assert.assertEquals(
			String.valueOf(commerceAccount.getCommerceAccountId()),
			_recordedParameterMap.get("orderAccountId"));

		Assert.assertEquals(
			commerceOrder.getAdvanceStatus(),
			_recordedParameterMap.get("orderAdvanceStatus"));

		_assertCommerceAddress(
			commerceOrder.getBillingAddress(), "orderBilling");

		Assert.assertEquals(
			commerceOrder.getCouponCode(),
			_recordedParameterMap.get("orderCouponCode"));
		Assert.assertEquals(
			commerceOrder.getExternalReferenceCode(),
			_recordedParameterMap.get("orderExternalReferenceCode"));
		Assert.assertEquals(
			commerceOrder.getCommerceOrderId(),
			GetterUtil.getLong(_recordedParameterMap.get("orderId")));

		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		Assert.assertEquals(
			dateFormat.format(commerceOrder.getLastPriceUpdateDate()),
			_recordedParameterMap.get("orderLastPriceUpdateDate"));

		Assert.assertEquals(
			dateFormat.format(commerceOrder.getOrderDate()),
			_recordedParameterMap.get("orderDate"));

		Assert.assertEquals(
			commerceOrder.getOrderStatus(),
			GetterUtil.getInteger(_recordedParameterMap.get("orderStatus")));
		Assert.assertEquals(
			commerceOrder.getCommercePaymentMethodKey(),
			_recordedParameterMap.get("orderPaymentMethod"));
		Assert.assertEquals(
			commerceOrder.getPaymentStatus(),
			GetterUtil.getInteger(
				_recordedParameterMap.get("orderPaymentStatus")));
		Assert.assertEquals(
			commerceOrder.getPurchaseOrderNumber(),
			_recordedParameterMap.get("orderPurchaseOrderNumber"));
		Assert.assertEquals(
			dateFormat.format(commerceOrder.getRequestedDeliveryDate()),
			_recordedParameterMap.get("orderRequestedDeliveryDate"));

		_assertCommerceAddress(
			commerceOrder.getShippingAddress(), "orderShipping");

		Assert.assertEquals(
			commerceOrder.getShippingAmount(),
			new BigDecimal(_recordedParameterMap.get("orderShippingAmount")));
		Assert.assertEquals(
			commerceOrder.getShippingDiscountAmount(),
			new BigDecimal(
				_recordedParameterMap.get("orderShippingDiscountAmount")));
		Assert.assertEquals(
			commerceOrder.getShippingDiscountPercentageLevel1(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderShippingDiscountPercentageLevel1")));
		Assert.assertEquals(
			commerceOrder.getShippingDiscountPercentageLevel2(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderShippingDiscountPercentageLevel2")));
		Assert.assertEquals(
			commerceOrder.getShippingDiscountPercentageLevel3(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderShippingDiscountPercentageLevel3")));
		Assert.assertEquals(
			commerceOrder.getShippingDiscountPercentageLevel4(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderShippingDiscountPercentageLevel4")));

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		Assert.assertEquals(
			commerceShippingMethod.getEngineKey(),
			_recordedParameterMap.get("orderShippingMethod"));

		Assert.assertEquals(
			commerceOrder.getSubtotal(),
			new BigDecimal(_recordedParameterMap.get("orderSubtotal")));
		Assert.assertEquals(
			commerceOrder.getSubtotalDiscountAmount(),
			new BigDecimal(
				_recordedParameterMap.get("orderSubtotalDiscountAmount")));
		Assert.assertEquals(
			commerceOrder.getSubtotalDiscountPercentageLevel1(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderSubtotalDiscountPercentageLevel1")));
		Assert.assertEquals(
			commerceOrder.getSubtotalDiscountPercentageLevel2(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderSubtotalDiscountPercentageLevel2")));
		Assert.assertEquals(
			commerceOrder.getSubtotalDiscountPercentageLevel3(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderSubtotalDiscountPercentageLevel3")));
		Assert.assertEquals(
			commerceOrder.getSubtotalDiscountPercentageLevel4(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderSubtotalDiscountPercentageLevel4")));

		Assert.assertEquals(
			commerceOrder.getTaxAmount(),
			new BigDecimal(_recordedParameterMap.get("orderTaxAmount")));
		Assert.assertEquals(
			commerceOrder.getTotal(),
			new BigDecimal(_recordedParameterMap.get("orderTotal")));
		Assert.assertEquals(
			commerceOrder.getTotalDiscountAmount(),
			new BigDecimal(
				_recordedParameterMap.get("orderTotalDiscountAmount")));
		Assert.assertEquals(
			commerceOrder.getTotalDiscountPercentageLevel1(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderTotalDiscountPercentageLevel1")));
		Assert.assertEquals(
			commerceOrder.getTotalDiscountPercentageLevel2(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderTotalDiscountPercentageLevel2")));
		Assert.assertEquals(
			commerceOrder.getTotalDiscountPercentageLevel3(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderTotalDiscountPercentageLevel3")));
		Assert.assertEquals(
			commerceOrder.getTotalDiscountPercentageLevel4(),
			new BigDecimal(
				_recordedParameterMap.get(
					"orderTotalDiscountPercentageLevel4")));

		Assert.assertEquals(
			commerceOrder.getTransactionId(),
			_recordedParameterMap.get("orderTransactionId"));
	}

	private CommerceAccount _getCommerceAccount() {
		CommerceAccount commerceAccount = Mockito.mock(CommerceAccount.class);

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

		return commerceAccount;
	}

	private CommerceAddress _getCommerceAddress(
			String city, long commerceAddressId,
			String commerceCountryThreeLettersISOCode,
			String commerceRegionCode, String externalReferenceCode,
			double latitude, double longitude, String phoneNumber,
			String street1, String street2, String street3, String zip)
		throws PortalException {

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

	private CommerceChannel _getCommerceChannel() {
		CommerceChannel commerceChannel = Mockito.mock(CommerceChannel.class);

		Mockito.when(
			commerceChannel.getExternalReferenceCode()
		).thenReturn(
			"contextChannelExternalReferenceCode"
		);

		Mockito.when(
			commerceChannel.getCommerceCurrencyCode()
		).thenReturn(
			"contextChannelCurrencyCode"
		);

		Mockito.when(
			commerceChannel.getCommerceChannelId()
		).thenReturn(
			11L
		);

		Mockito.when(
			commerceChannel.getType()
		).thenReturn(
			"contextChannelType"
		);

		return commerceChannel;
	}

	private CommerceContext _getCommerceContext() {
		return new CommerceContext() {

			@Override
			public CommerceAccount getCommerceAccount() throws PortalException {
				return _getCommerceAccount();
			}

			@Override
			public long[] getCommerceAccountGroupIds() throws PortalException {
				return new long[] {4, 5};
			}

			@Override
			public long getCommerceChannelGroupId() throws PortalException {
				return 6;
			}

			@Override
			public long getCommerceChannelId() throws PortalException {
				return 7;
			}

			@Override
			public CommerceCurrency getCommerceCurrency()
				throws PortalException {

				return _getCommerceCurrency();
			}

			@Override
			public CommerceOrder getCommerceOrder() throws PortalException {
				return _getCommerceOrder();
			}

			@Override
			public int getCommerceSiteType() {
				return 9;
			}

		};
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

		return commerceCurrency;
	}

	private CommerceOrder _getCommerceOrder() throws PortalException {
		CommerceOrder commerceOrder = Mockito.mock(CommerceOrder.class);

		CommerceAccount commerceAccount = _getCommerceAccount();

		Mockito.when(
			commerceOrder.getCommerceAccount()
		).thenReturn(
			commerceAccount
		);

		Mockito.when(
			commerceOrder.getAdvanceStatus()
		).thenReturn(
			"orderAdvanceStatus"
		);

		CommerceAddress commerceOrderBillingAddress = _getCommerceAddress(
			"orderBillingAddressCity", 13, "USD", "CA",
			"orderBillingAddressExternalReferenceCode", 12.3, 45.7,
			"orderBillingAddressPhoneNumber", "orderBillingAddressStreet1",
			"orderBillingAddressStreet2", "orderBillingAddressStreet3",
			"orderBillingAddressZip");

		Mockito.when(
			commerceOrder.getBillingAddress()
		).thenReturn(
			commerceOrderBillingAddress
		);

		Mockito.when(
			commerceOrder.getCouponCode()
		).thenReturn(
			"orderCouponCode"
		);

		Mockito.when(
			commerceOrder.getExternalReferenceCode()
		).thenReturn(
			"orderExternalReferenceCode"
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
			"orderPaymentMethodKey"
		);

		Mockito.when(
			commerceOrder.getPaymentStatus()
		).thenReturn(
			65
		);

		Mockito.when(
			commerceOrder.getPurchaseOrderNumber()
		).thenReturn(
			"orderPurchaseOrderNumber"
		);

		Mockito.when(
			commerceOrder.getRequestedDeliveryDate()
		).thenReturn(
			new Date()
		);

		CommerceAddress commerceOrderShippingAddress = _getCommerceAddress(
			"orderShippingAddressCity", 14, "BOL", "BO",
			"orderShippingAddressExternalReferenceCode", 22.72, 11.87,
			"orderShippingAddressPhoneNumber", "orderShippingAddressStreet1",
			"orderShippingAddressStreet2", "orderShippingAddressStreet3",
			"orderShippingAddressZip");

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
			"orderTransactionId"
		);

		return commerceOrder;
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

		return commerceShippingMethod;
	}

	private RemoteCommerceShippingEngineConfiguration
		_getRemoteCommerceShippingEngineConfiguration() {

		RemoteCommerceShippingEngineConfiguration
			remoteCommerceShippingEngineConfiguration = Mockito.mock(
				RemoteCommerceShippingEngineConfiguration.class);

		Mockito.when(
			remoteCommerceShippingEngineConfiguration.
				shippingOptionsEndpointURL()
		).thenReturn(
			"http://localhost:" + _PORT + "/commerce/shipping-options"
		);

		return remoteCommerceShippingEngineConfiguration;
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

	private void _startHttpServer() throws Exception {
		_httpServer = HttpServer.create(new InetSocketAddress(_PORT), 0);

		HttpContext httpContext = _httpServer.createContext(
			"/commerce/shipping-options");

		httpContext.setHandler(new ShippingOptionsHttpHandler());

		_httpServer.start();
	}

	private static final int _PORT = 4250;

	private CommerceChannel _commerceChannel;
	private HttpServer _httpServer;
	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final Map<String, String> _recordedParameterMap = new HashMap<>();
	private RemoteCommerceShippingEngine _remoteCommerceShippingEngine;
	private final List<CommerceShippingOption>
		_returnedCommerceShippingOptions =
			new ArrayList<CommerceShippingOption>() {
				{
					add(
						new CommerceShippingOption(
							"name", "label", BigDecimal.valueOf(118.8)));
				}
			};

	private class ShippingOptionsHttpHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			_recordQueryParameters(httpExchange.getRequestURI());

			String payload = _objectMapper.writeValueAsString(
				_returnedCommerceShippingOptions);

			byte[] bytes = payload.getBytes();

			httpExchange.sendResponseHeaders(200, bytes.length);

			try (OutputStream outputStream = httpExchange.getResponseBody()) {
				outputStream.write(bytes);

				outputStream.flush();
			}
		}

	}

}