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

package com.liferay.commerce.payment.engine.worldpay.internal;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.exception.CommercePaymentEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommercePaymentEngineResult;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.payment.engine.worldpay.internal.configuration.WorldpayCommercePaymentEngineGroupServiceConfiguration;
import com.liferay.commerce.payment.engine.worldpay.internal.constants.WorldpayCommercePaymentEngineConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import com.worldpay.gateway.clearwater.client.core.dto.CountryCode;
import com.worldpay.gateway.clearwater.client.core.dto.CurrencyCode;
import com.worldpay.gateway.clearwater.client.core.dto.common.Address;
import com.worldpay.gateway.clearwater.client.core.dto.common.DeliveryAddress;
import com.worldpay.gateway.clearwater.client.core.dto.common.MerchantUrlConfig;
import com.worldpay.gateway.clearwater.client.core.dto.common.PaymentPageConfig;
import com.worldpay.gateway.clearwater.client.core.dto.request.OrderRequest;
import com.worldpay.gateway.clearwater.client.core.dto.request.ThreeDSecureInfo;
import com.worldpay.gateway.clearwater.client.core.dto.response.OrderResponse;
import com.worldpay.sdk.OrderService;
import com.worldpay.sdk.WorldpayRestClient;
import com.worldpay.sdk.util.JsonParser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true, property = "commerce.payment.engine.key=worldpay",
	service = CommercePaymentEngine.class
)
public class WorldpayCommercePaymentEngine implements CommercePaymentEngine {

	@Override
	public CommercePaymentEngineResult cancelPayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws CommercePaymentEngineException {

		return new CommercePaymentEngineResult(null);
	}

	@Override
	public CommercePaymentEngineResult completePayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws CommercePaymentEngineException {

		return new CommercePaymentEngineResult(null);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "worldpay-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "worldpay");
	}

	@Override
	public void renderConfiguration(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		WorldpayCommercePaymentEngineGroupServiceConfiguration
			worldpayCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					WorldpayCommercePaymentEngineGroupServiceConfiguration.
						class,
					new ParameterMapSettingsLocator(
						renderRequest.getParameterMap(),
						new GroupServiceSettingsLocator(
							themeDisplay.getScopeGroupId(),
							WorldpayCommercePaymentEngineConstants.
								SERVICE_NAME)));

		renderRequest.setAttribute(
			WorldpayCommercePaymentEngineGroupServiceConfiguration.class.
				getName(),
			worldpayCommercePaymentEngineGroupServiceConfiguration);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/configuration.jsp");
	}

	@Override
	public CommercePaymentEngineResult.StartPayment startPayment(
			CommerceOrder commerceOrder, String cancelURL, String returnURL,
			ServiceContext serviceContext)
		throws CommercePaymentEngineException {

		try {
			return _startPayment(
				commerceOrder, cancelURL, returnURL, serviceContext);
		}
		catch (CommercePaymentEngineException cpee) {
			throw cpee;
		}
		catch (Exception e) {
			throw new CommercePaymentEngineException(e);
		}
	}

	@Override
	public void updateConfiguration(
			Map<String, String> parameterMap, ServiceContext serviceContext)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				serviceContext.getScopeGroupId(),
				WorldpayCommercePaymentEngineConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	private Address _getAddress(CommerceAddress commerceAddress)
		throws PortalException {

		if (commerceAddress == null) {
			return null;
		}

		Address address = new Address();

		address.setAddress1(commerceAddress.getStreet1());
		address.setAddress2(commerceAddress.getStreet2());
		address.setAddress3(commerceAddress.getStreet3());
		address.setCity(commerceAddress.getCity());
		address.setCountryCode(
			_getCountryCode(commerceAddress.getCommerceCountry()));
		address.setPostalCode(commerceAddress.getZip());

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		if (commerceRegion != null) {
			address.setState(commerceRegion.getCode());
		}

		address.setTelephoneNumber(commerceAddress.getPhoneNumber());

		return address;
	}

	private int _getAmount(
		CommerceOrder commerceOrder, CurrencyCode currencyCode) {

		double amount =
			commerceOrder.getTotal() * Math.pow(10, currencyCode.getExponent());

		return (int)amount;
	}

	private CountryCode _getCountryCode(CommerceCountry commerceCountry) {
		String code = commerceCountry.getThreeLettersISOCode();

		if (Validator.isNotNull(code)) {
			try {
				return CountryCode.fromThreeDigitCountryCode(code);
			}
			catch (IllegalArgumentException iae) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get Worldpay country code " + code, iae);
				}
			}
		}

		code = commerceCountry.getTwoLettersISOCode();

		if (Validator.isNotNull(code)) {
			try {
				return CountryCode.fromValue(code);
			}
			catch (IllegalArgumentException iae) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get Worldpay country code " + code, iae);
				}
			}
		}

		return null;
	}

	private CountryCode _getCountryCode(Locale locale) {
		String country = locale.getCountry();

		if (country.length() == 3) {
			return CountryCode.fromThreeDigitCountryCode(country);
		}

		return CountryCode.fromValue(country);
	}

	private DeliveryAddress _getDeliveryAddress(CommerceAddress commerceAddress)
		throws PortalException {

		if (commerceAddress == null) {
			return null;
		}

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String[] names = fullNameGenerator.splitFullName(
			commerceAddress.getName());

		DeliveryAddress deliveryAddress = new DeliveryAddress(
			names[0], names[2]);

		deliveryAddress.setAddress1(commerceAddress.getStreet1());
		deliveryAddress.setAddress2(commerceAddress.getStreet2());
		deliveryAddress.setAddress3(commerceAddress.getStreet3());
		deliveryAddress.setCity(commerceAddress.getCity());
		deliveryAddress.setCountryCode(
			_getCountryCode(commerceAddress.getCommerceCountry()));
		deliveryAddress.setPostalCode(commerceAddress.getZip());

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		if (commerceRegion != null) {
			deliveryAddress.setState(commerceRegion.getCode());
		}

		deliveryAddress.setTelephoneNumber(commerceAddress.getPhoneNumber());

		return deliveryAddress;
	}

	private String _getOrderDescription(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		StringBundler sb = new StringBundler(commerceOrderItems.size() * 2 - 1);

		String languageId = LanguageUtil.getLanguageId(locale);

		for (int i = 0; i < commerceOrderItems.size(); i++) {
			CommerceOrderItem commerceOrderItem = commerceOrderItems.get(i);

			if (i > 0) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

			sb.append(cpDefinition.getTitle(languageId));
		}

		return sb.toString();
	}

	private OrderRequest _getOrderRequest(
			CommerceOrder commerceOrder, String cancelURL, String returnURL,
			ServiceContext serviceContext,
			WorldpayCommercePaymentEngineGroupServiceConfiguration
				worldpayCommercePaymentEngineGroupServiceConfiguration)
		throws PortalException {

		OrderRequest orderRequest = new OrderRequest();

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				commerceOrder.getGroupId());

		if (commerceCurrency == null) {
			throw new CommercePaymentEngineException.MustSetPrimaryCurrency();
		}

		CurrencyCode currencyCode = CurrencyCode.fromValue(
			commerceCurrency.getCode());

		CommerceAddress billingAddress = commerceOrder.getBillingAddress();
		Locale locale = serviceContext.getLocale();
		User user = _userLocalService.getUser(serviceContext.getUserId());

		orderRequest.setAmount(_getAmount(commerceOrder, currencyCode));
		orderRequest.setAuthorizeOnly(Boolean.FALSE);
		orderRequest.setBillingAddress(_getAddress(billingAddress));
		orderRequest.setCurrencyCode(currencyCode);
		orderRequest.setCustomerOrderCode(
			String.valueOf(commerceOrder.getCommerceOrderId()));
		orderRequest.setDeliveryAddress(
			_getDeliveryAddress(commerceOrder.getShippingAddress()));
		orderRequest.setMerchantUrlConfig(
			new MerchantUrlConfig(returnURL, returnURL, returnURL, cancelURL));

		if (billingAddress != null) {
			orderRequest.setName(billingAddress.getName());
		}

		orderRequest.setOrderDescription(
			_getOrderDescription(commerceOrder, locale));
		orderRequest.setPaymentPageConfig(
			_getPaymentPageConfig(
				worldpayCommercePaymentEngineGroupServiceConfiguration));
		orderRequest.setShopperCountryCode(_getCountryCode(locale));
		orderRequest.setShopperEmailAddress(user.getEmailAddress());
		orderRequest.setShopperLanguageCode(serviceContext.getLanguageId());
		orderRequest.setThreeDSecureInfo(_getThreeDSecureInfo(serviceContext));

		return orderRequest;
	}

	private PaymentPageConfig _getPaymentPageConfig(
		WorldpayCommercePaymentEngineGroupServiceConfiguration
			worldpayCommercePaymentEngineGroupServiceConfiguration) {

		String[] paymentMethodCodes = StringUtil.split(
			worldpayCommercePaymentEngineGroupServiceConfiguration.
				paymentMethodCodes());

		if (ArrayUtil.isEmpty(paymentMethodCodes)) {
			paymentMethodCodes = new String[] {_PAYMENT_METHOD_CODE_ALL};
		}

		return new PaymentPageConfig(
			worldpayCommercePaymentEngineGroupServiceConfiguration.
				installationId(),
			Arrays.asList(paymentMethodCodes));
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private ThreeDSecureInfo _getThreeDSecureInfo(
		ServiceContext serviceContext) {

		ThreeDSecureInfo threeDSecureInfo = new ThreeDSecureInfo();

		threeDSecureInfo.setShopperAcceptHeader(
			MapUtil.getString(serviceContext.getHeaders(), HttpHeaders.ACCEPT));
		threeDSecureInfo.setShopperIpAddress(serviceContext.getRemoteAddr());
		threeDSecureInfo.setShopperUserAgent(serviceContext.getUserAgent());

		return threeDSecureInfo;
	}

	private CommercePaymentEngineResult.StartPayment _startPayment(
			CommerceOrder commerceOrder, String cancelURL, String returnURL,
			ServiceContext serviceContext)
		throws Exception {

		WorldpayCommercePaymentEngineGroupServiceConfiguration
			worldpayCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					WorldpayCommercePaymentEngineGroupServiceConfiguration.
						class,
					new GroupServiceSettingsLocator(
						commerceOrder.getGroupId(),
						WorldpayCommercePaymentEngineConstants.SERVICE_NAME));

		WorldpayRestClient worldpayRestClient = new WorldpayRestClient(
			worldpayCommercePaymentEngineGroupServiceConfiguration.
				serviceKey());

		OrderService orderService = worldpayRestClient.getOrderService();

		OrderRequest orderRequest = _getOrderRequest(
			commerceOrder, cancelURL, returnURL, serviceContext,
			worldpayCommercePaymentEngineGroupServiceConfiguration);

		OrderResponse orderResponse = orderService.create(orderRequest);

		String json = JsonParser.toJson(orderResponse);

		return new CommercePaymentEngineResult.StartPayment(
			json, orderResponse.getRedirectURL());
	}

	private static final String _PAYMENT_METHOD_CODE_ALL = "ALL";

	private static final Log _log = LogFactoryUtil.getLog(
		WorldpayCommercePaymentEngine.class);

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.engine.worldpay)"
	)
	private ServletContext _servletContext;

	@Reference
	private SettingsFactory _settingsFactory;

	@Reference
	private UserLocalService _userLocalService;

}