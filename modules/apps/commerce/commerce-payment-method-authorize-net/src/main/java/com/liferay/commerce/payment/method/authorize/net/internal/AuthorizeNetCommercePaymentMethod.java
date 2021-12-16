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

package com.liferay.commerce.payment.method.authorize.net.internal;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.constants.CommercePaymentConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.authorize.net.internal.configuration.AuthorizeNetGroupServiceConfiguration;
import com.liferay.commerce.payment.method.authorize.net.internal.constants.AuthorizeNetCommercePaymentMethodConstants;
import com.liferay.commerce.payment.request.CommercePaymentRequest;
import com.liferay.commerce.payment.result.CommercePaymentResult;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ArrayOfSetting;
import net.authorize.api.contract.v1.GetHostedPaymentPageRequest;
import net.authorize.api.contract.v1.GetHostedPaymentPageResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessagesType;
import net.authorize.api.contract.v1.SettingType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.GetHostedPaymentPageController;
import net.authorize.api.controller.base.ApiOperationBase;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.payment.engine.method.key=" + AuthorizeNetCommercePaymentMethod.KEY,
	service = CommercePaymentMethod.class
)
public class AuthorizeNetCommercePaymentMethod
	implements CommercePaymentMethod {

	public static final String KEY = "authorize-net";

	@Override
	public CommercePaymentResult cancelPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		return new CommercePaymentResult(
			commercePaymentRequest.getTransactionId(),
			commercePaymentRequest.getCommerceOrderId(),
			CommerceOrderPaymentConstants.STATUS_CANCELLED, false, null, null,
			Collections.emptyList(), true);
	}

	@Override
	public CommercePaymentResult completePayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		AuthorizeNetCommercePaymentRequest authorizeNetCommercePaymentRequest =
			(AuthorizeNetCommercePaymentRequest)commercePaymentRequest;

		return new CommercePaymentResult(
			commercePaymentRequest.getTransactionId(),
			authorizeNetCommercePaymentRequest.getCommerceOrderId(),
			CommerceOrderConstants.PAYMENT_STATUS_PAID, false, null, null,
			Collections.emptyList(), true);
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			_getResourceBundle(locale), "authorize-net-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), KEY);
	}

	@Override
	public int getPaymentType() {
		return CommercePaymentConstants.
			COMMERCE_PAYMENT_METHOD_TYPE_ONLINE_REDIRECT;
	}

	@Override
	public String getServletPath() {
		return AuthorizeNetCommercePaymentMethodConstants.
			COMPLETE_PAYMENT_SERVLET_PATH;
	}

	@Override
	public boolean isCancelEnabled() {
		return true;
	}

	@Override
	public boolean isCompleteEnabled() {
		return true;
	}

	@Override
	public boolean isProcessPaymentEnabled() {
		return true;
	}

	@Override
	public CommercePaymentResult processPayment(
			CommercePaymentRequest commercePaymentRequest)
		throws Exception {

		AuthorizeNetCommercePaymentRequest authorizeNetCommercePaymentRequest =
			(AuthorizeNetCommercePaymentRequest)commercePaymentRequest;

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			authorizeNetCommercePaymentRequest.getCommerceOrderId());

		AuthorizeNetGroupServiceConfiguration configuration = _getConfiguration(
			commerceOrder.getGroupId());

		Environment environment = Environment.valueOf(
			StringUtil.toUpperCase(configuration.environment()));

		ApiOperationBase.setEnvironment(environment);

		MerchantAuthenticationType merchantAuthenticationType =
			new MerchantAuthenticationType();

		merchantAuthenticationType.setName(configuration.apiLoginId());
		merchantAuthenticationType.setTransactionKey(
			configuration.transactionKey());

		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		GetHostedPaymentPageRequest getHostedPaymentPageRequest =
			new GetHostedPaymentPageRequest();

		getHostedPaymentPageRequest.setTransactionRequest(
			_getTransactionRequestType(commerceOrder));

		ArrayOfSetting arrayOfSetting = _getArrayOfSetting(
			commerceOrder.getGroupId(),
			authorizeNetCommercePaymentRequest.getCancelUrl(),
			authorizeNetCommercePaymentRequest.getReturnUrl());

		getHostedPaymentPageRequest.setHostedPaymentSettings(arrayOfSetting);

		GetHostedPaymentPageController controller =
			new GetHostedPaymentPageController(getHostedPaymentPageRequest);

		controller.execute();

		GetHostedPaymentPageResponse response = controller.getApiResponse();

		if ((response != null) && (response.getToken() != null)) {
			String token = response.getToken();

			String redirectURL =
				AuthorizeNetCommercePaymentMethodConstants.SANDBOX_REDIRECT_URL;

			String environmentName = environment.name();

			if (environmentName.equals(Environment.PRODUCTION.name())) {
				redirectURL =
					AuthorizeNetCommercePaymentMethodConstants.
						PRODUCTION_REDIRECT_URL;
			}

			String url = StringBundler.concat(
				_getServletUrl(authorizeNetCommercePaymentRequest),
				"?redirectURL=", URLCodec.encodeURL(redirectURL), "&token=",
				URLEncoder.encode(token, StringPool.UTF8));

			List<String> resultMessages = new ArrayList<>();

			MessagesType responseMessages = response.getMessages();

			List<MessagesType.Message> messages = responseMessages.getMessage();

			for (MessagesType.Message message : messages) {
				resultMessages.add(message.getText());
			}

			return new CommercePaymentResult(
				token, authorizeNetCommercePaymentRequest.getCommerceOrderId(),
				CommerceOrderConstants.PAYMENT_STATUS_PENDING, true, url, null,
				resultMessages, true);
		}

		return new CommercePaymentResult(
			commercePaymentRequest.getTransactionId(),
			commerceOrder.getCommerceOrderId(), -1, false, null, null,
			Collections.emptyList(), false);
	}

	private void _addSetting(
		List<SettingType> settings, String name, String value) {

		SettingType billingAddress = new SettingType();

		billingAddress.setSettingName(name);
		billingAddress.setSettingValue(value);

		settings.add(billingAddress);
	}

	private String _fixURL(String url) {

		// See https://community.developer.authorize.net/t5/
		// Integration-and-Testing/Unanticipated-Error-Occured-Hosted-Payment
		// /m-p/57815#M32503

		return StringUtil.replace(
			url, new String[] {StringPool.PERCENT, StringPool.AMPERSAND},
			new String[] {"%25", "%26"});
	}

	private ArrayOfSetting _getArrayOfSetting(
			long groupId, String cancelURL, String returnURL)
		throws Exception {

		AuthorizeNetGroupServiceConfiguration configuration = _getConfiguration(
			groupId);

		ArrayOfSetting arrayOfSetting = new ArrayOfSetting();

		List<SettingType> settings = arrayOfSetting.getSetting();

		JSONObject hostedPaymentReturnOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentReturnOptionsJSONObject.put(
			"cancelUrl", _fixURL(cancelURL)
		).put(
			"cancelUrlText", "Cancel"
		).put(
			"showReceipt", true
		).put(
			"url", _fixURL(returnURL)
		).put(
			"urlText", "Continue"
		);

		_addSetting(
			settings, "hostedPaymentReturnOptions",
			hostedPaymentReturnOptionsJSONObject.toJSONString());

		JSONObject hostedPaymentPaymentOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentPaymentOptionsJSONObject.put(
			"cardCodeRequired", configuration.requireCardCodeVerification()
		).put(
			"showBankAccount", configuration.showBankAccount()
		).put(
			"showCreditCard", configuration.showCreditCard()
		);

		_addSetting(
			settings, "hostedPaymentPaymentOptions",
			hostedPaymentPaymentOptionsJSONObject.toJSONString());

		JSONObject hostedPaymentSecurityOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentSecurityOptionsJSONObject.put(
			"captcha", configuration.requireCaptcha());

		_addSetting(
			settings, "hostedPaymentSecurityOptions",
			hostedPaymentSecurityOptionsJSONObject.toJSONString());

		JSONObject hostedPaymentShippingAddressOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentShippingAddressOptionsJSONObject.put(
			"required", false
		).put(
			"show", false
		);

		_addSetting(
			settings, "hostedPaymentShippingAddressOptions",
			hostedPaymentShippingAddressOptionsJSONObject.toJSONString());

		JSONObject hostedPaymentBillingAddressOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentBillingAddressOptionsJSONObject.put(
			"required", false
		).put(
			"show", false
		);

		_addSetting(
			settings, "hostedPaymentBillingAddressOptions",
			hostedPaymentBillingAddressOptionsJSONObject.toJSONString());

		JSONObject hostedPaymentCustomerOptionsJSJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentCustomerOptionsJSJSONObject.put(
			"addPaymentProfile", false
		).put(
			"requiredEmail", false
		).put(
			"showEmail", false
		);

		_addSetting(
			settings, "hostedPaymentCustomerOptions",
			hostedPaymentCustomerOptionsJSJSONObject.toJSONString());

		JSONObject hostedPaymentOrderOptionsJSONObject =
			_jsonFactory.createJSONObject();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(groupId);

		hostedPaymentOrderOptionsJSONObject.put(
			"merchantName", commerceChannel.getName()
		).put(
			"show", configuration.showStoreName()
		);

		_addSetting(
			settings, "hostedPaymentOrderOptions",
			hostedPaymentOrderOptionsJSONObject.toJSONString());

		return arrayOfSetting;
	}

	private AuthorizeNetGroupServiceConfiguration _getConfiguration(
			long groupId)
		throws Exception {

		return _configurationProvider.getConfiguration(
			AuthorizeNetGroupServiceConfiguration.class,
			new GroupServiceSettingsLocator(
				groupId,
				AuthorizeNetCommercePaymentMethodConstants.SERVICE_NAME));
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private String _getServletUrl(
		AuthorizeNetCommercePaymentRequest authorizeNetCommercePaymentRequest) {

		return StringBundler.concat(
			_portal.getPortalURL(
				authorizeNetCommercePaymentRequest.getHttpServletRequest()),
			_portal.getPathModule(), StringPool.SLASH,
			AuthorizeNetCommercePaymentMethodConstants.
				START_PAYMENT_SERVLET_PATH);
	}

	private TransactionRequestType _getTransactionRequestType(
			CommerceOrder commerceOrder)
		throws Exception {

		TransactionRequestType transactionRequestType =
			new TransactionRequestType();

		transactionRequestType.setTransactionType(
			TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());

		BigDecimal amount = commerceOrder.getTotal();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		transactionRequestType.setAmount(
			amount.setScale(
				commerceCurrency.getMaxFractionDigits(),
				RoundingMode.valueOf(commerceCurrency.getRoundingMode())));

		return transactionRequestType;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}