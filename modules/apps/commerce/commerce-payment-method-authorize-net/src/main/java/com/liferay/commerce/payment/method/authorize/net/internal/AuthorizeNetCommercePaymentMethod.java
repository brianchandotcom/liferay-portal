/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.authorize.net.internal;

import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.constants.CommercePaymentMethodConstants;
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
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
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
			CommerceOrderPaymentConstants.STATUS_COMPLETED, false, null, null,
			Collections.emptyList(), true);
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(
			_getResourceBundle(locale), "authorize-net-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(_getResourceBundle(locale), KEY);
	}

	@Override
	public int getPaymentType() {
		return CommercePaymentMethodConstants.TYPE_ONLINE_REDIRECT;
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

		AuthorizeNetGroupServiceConfiguration
			authorizeNetGroupServiceConfiguration =
				_getAuthorizeNetGroupServiceConfiguration(
					commerceOrder.getGroupId());

		Environment environment = Environment.valueOf(
			StringUtil.toUpperCase(
				authorizeNetGroupServiceConfiguration.environment()));

		ApiOperationBase.setEnvironment(environment);

		MerchantAuthenticationType merchantAuthenticationType =
			new MerchantAuthenticationType();

		merchantAuthenticationType.setName(
			authorizeNetGroupServiceConfiguration.apiLoginId());
		merchantAuthenticationType.setTransactionKey(
			authorizeNetGroupServiceConfiguration.transactionKey());

		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		GetHostedPaymentPageRequest getHostedPaymentPageRequest =
			new GetHostedPaymentPageRequest();

		getHostedPaymentPageRequest.setHostedPaymentSettings(
			_getArrayOfSetting(
				commerceOrder.getGroupId(),
				authorizeNetCommercePaymentRequest.getCancelUrl(),
				authorizeNetCommercePaymentRequest.getReturnUrl()));
		getHostedPaymentPageRequest.setTransactionRequest(
			_getTransactionRequestType(commerceOrder));

		GetHostedPaymentPageController getHostedPaymentPageController =
			new GetHostedPaymentPageController(getHostedPaymentPageRequest);

		getHostedPaymentPageController.execute();

		GetHostedPaymentPageResponse getHostedPaymentPageResponse =
			getHostedPaymentPageController.getApiResponse();

		if ((getHostedPaymentPageResponse != null) &&
			(getHostedPaymentPageResponse.getToken() != null)) {

			String token = getHostedPaymentPageResponse.getToken();

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

			MessagesType messagesType =
				getHostedPaymentPageResponse.getMessages();

			List<MessagesType.Message> messages = messagesType.getMessage();

			for (MessagesType.Message message : messages) {
				resultMessages.add(message.getText());
			}

			return new CommercePaymentResult(
				token, authorizeNetCommercePaymentRequest.getCommerceOrderId(),
				CommerceOrderPaymentConstants.STATUS_PENDING, true, url, null,
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

		AuthorizeNetGroupServiceConfiguration
			authorizeNetGroupServiceConfiguration =
				_getAuthorizeNetGroupServiceConfiguration(groupId);

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
			hostedPaymentReturnOptionsJSONObject.toString());

		JSONObject hostedPaymentPaymentOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentPaymentOptionsJSONObject.put(
			"cardCodeRequired",
			authorizeNetGroupServiceConfiguration.requireCardCodeVerification()
		).put(
			"showBankAccount",
			authorizeNetGroupServiceConfiguration.showBankAccount()
		).put(
			"showCreditCard",
			authorizeNetGroupServiceConfiguration.showCreditCard()
		);

		_addSetting(
			settings, "hostedPaymentPaymentOptions",
			hostedPaymentPaymentOptionsJSONObject.toString());

		JSONObject hostedPaymentSecurityOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentSecurityOptionsJSONObject.put(
			"captcha", authorizeNetGroupServiceConfiguration.requireCaptcha());

		_addSetting(
			settings, "hostedPaymentSecurityOptions",
			hostedPaymentSecurityOptionsJSONObject.toString());

		JSONObject hostedPaymentShippingAddressOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentShippingAddressOptionsJSONObject.put(
			"required", false
		).put(
			"show", false
		);

		_addSetting(
			settings, "hostedPaymentShippingAddressOptions",
			hostedPaymentShippingAddressOptionsJSONObject.toString());

		JSONObject hostedPaymentBillingAddressOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentBillingAddressOptionsJSONObject.put(
			"required", false
		).put(
			"show", false
		);

		_addSetting(
			settings, "hostedPaymentBillingAddressOptions",
			hostedPaymentBillingAddressOptionsJSONObject.toString());

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
			hostedPaymentCustomerOptionsJSJSONObject.toString());

		JSONObject hostedPaymentOrderOptionsJSONObject =
			_jsonFactory.createJSONObject();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(groupId);

		hostedPaymentOrderOptionsJSONObject.put(
			"merchantName", commerceChannel.getName()
		).put(
			"show", authorizeNetGroupServiceConfiguration.showStoreName()
		);

		_addSetting(
			settings, "hostedPaymentOrderOptions",
			hostedPaymentOrderOptionsJSONObject.toString());

		return arrayOfSetting;
	}

	private AuthorizeNetGroupServiceConfiguration
			_getAuthorizeNetGroupServiceConfiguration(long groupId)
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
	private Language _language;

	@Reference
	private Portal _portal;

}