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

package com.liferay.commerce.payment.engine.authorize.net.internal;

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
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.engine.authorize.net.internal.configuration.AuthorizeNetCommercePaymentEngineGroupServiceConfiguration;
import com.liferay.commerce.payment.engine.authorize.net.internal.constants.AuthorizeNetCommercePaymentEngineConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ArrayOfLineItem;
import net.authorize.api.contract.v1.ArrayOfSetting;
import net.authorize.api.contract.v1.CustomerAddressType;
import net.authorize.api.contract.v1.ExtendedAmountType;
import net.authorize.api.contract.v1.GetHostedPaymentPageRequest;
import net.authorize.api.contract.v1.GetHostedPaymentPageResponse;
import net.authorize.api.contract.v1.LineItemType;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.SettingType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.GetHostedPaymentPageController;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true, property = "commerce.payment.engine.key=authorize-net",
	service = CommercePaymentEngine.class
)
public class AuthorizeNetCommercePaymentEngine
	implements CommercePaymentEngine {

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

		return LanguageUtil.get(resourceBundle, "authorize-net-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "authorize-net");
	}

	@Override
	public void renderConfiguration(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AuthorizeNetCommercePaymentEngineGroupServiceConfiguration
			authorizeNetCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					AuthorizeNetCommercePaymentEngineGroupServiceConfiguration.
						class,
					new ParameterMapSettingsLocator(
						renderRequest.getParameterMap(),
						new GroupServiceSettingsLocator(
							themeDisplay.getScopeGroupId(),
							AuthorizeNetCommercePaymentEngineConstants.
								SERVICE_NAME)));

		renderRequest.setAttribute(
			AuthorizeNetCommercePaymentEngineGroupServiceConfiguration.class.
				getName(),
			authorizeNetCommercePaymentEngineGroupServiceConfiguration);

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
				AuthorizeNetCommercePaymentEngineConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	private void _addSetting(
		List<SettingType> settings, String name, JSONObject jsonObject) {

		SettingType setting = new SettingType();

		setting.setSettingName(name);
		setting.setSettingValue(jsonObject.toJSONString());

		settings.add(setting);
	}

	private String _fixURL(String url) {

		// See https://community.developer.authorize.net/t5/Integration-and-Testing/Unanticipated-Error-Occured-Hosted-Payment/m-p/57815#M32503

		return StringUtil.replace(
			url, new String[] {StringPool.PERCENT, StringPool.AMPERSAND},
			new String[] {"%25", "%26"});
	}

	private CustomerAddressType _getCustomerAddress(
			CommerceAddress commerceAddress, Locale locale)
		throws PortalException {

		if (commerceAddress == null) {
			return null;
		}

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();
		String languageId = LanguageUtil.getLanguageId(locale);

		CustomerAddressType customerAddress = new CustomerAddressType();

		StringBundler sb = new StringBundler(5);

		sb.append(commerceAddress.getStreet1());

		if (Validator.isNotNull(commerceAddress.getStreet2())) {
			sb.append(_ADDRESS_SEPARATOR);
			sb.append(commerceAddress.getStreet2());
		}

		if (Validator.isNotNull(commerceAddress.getStreet3())) {
			sb.append(_ADDRESS_SEPARATOR);
			sb.append(commerceAddress.getStreet3());
		}

		customerAddress.setAddress(sb.toString());

		customerAddress.setCity(commerceAddress.getCity());

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		customerAddress.setCountry(commerceCountry.getName(languageId));

		String[] names = fullNameGenerator.splitFullName(
			commerceAddress.getName());

		customerAddress.setFirstName(names[0]);
		customerAddress.setLastName(names[2]);

		customerAddress.setPhoneNumber(commerceAddress.getPhoneNumber());

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		if (commerceRegion != null) {
			customerAddress.setState(commerceRegion.getName());
		}

		customerAddress.setZip(commerceAddress.getZip());

		return customerAddress;
	}

	private ArrayOfSetting _getHostedPaymentSettings(
			long groupId, String cancelURL, String returnURL,
			AuthorizeNetCommercePaymentEngineGroupServiceConfiguration
				authorizeNetCommercePaymentEngineGroupServiceConfiguration,
			Locale locale)
		throws PortalException {

		ArrayOfSetting arrayOfSetting = new ArrayOfSetting();

		List<SettingType> settings = arrayOfSetting.getSetting();

		// Billing address

		JSONObject hostedPaymentBillingAddressOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentBillingAddressOptionsJSONObject.put("show", false);

		_addSetting(
			settings, "hostedPaymentBillingAddressOptions",
			hostedPaymentBillingAddressOptionsJSONObject);

		// Payment

		JSONObject hostedPaymentPaymentOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentPaymentOptionsJSONObject.put(
			"cardCodeRequired",
			authorizeNetCommercePaymentEngineGroupServiceConfiguration.
				requireCardCodeVerification());
		hostedPaymentPaymentOptionsJSONObject.put(
			"showBankAccount",
			authorizeNetCommercePaymentEngineGroupServiceConfiguration.
				showBankAccount());
		hostedPaymentPaymentOptionsJSONObject.put(
			"showCreditCard",
			authorizeNetCommercePaymentEngineGroupServiceConfiguration.
				showCreditCard());

		_addSetting(
			settings, "hostedPaymentPaymentOptions",
			hostedPaymentPaymentOptionsJSONObject);

		// Order

		JSONObject hostedPaymentOrderOptionsJSONObject =
			_jsonFactory.createJSONObject();

		Group group = _groupLocalService.getGroup(groupId);

		hostedPaymentOrderOptionsJSONObject.put(
			"merchantName", group.getDescriptiveName(locale));

		hostedPaymentOrderOptionsJSONObject.put(
			"show",
			authorizeNetCommercePaymentEngineGroupServiceConfiguration.
				showStoreName());

		_addSetting(
			settings, "hostedPaymentOrderOptions",
			hostedPaymentOrderOptionsJSONObject);

		// Return

		JSONObject hostedPaymentReturnOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentReturnOptionsJSONObject.put(
			"cancelUrl", _fixURL(cancelURL));
		hostedPaymentReturnOptionsJSONObject.put("url", _fixURL(returnURL));

		_addSetting(
			settings, "hostedPaymentReturnOptions",
			hostedPaymentReturnOptionsJSONObject);

		// Security

		JSONObject hostedPaymentSecurityOptionsJSONObject =
			_jsonFactory.createJSONObject();

		hostedPaymentSecurityOptionsJSONObject.put(
			"captcha",
			authorizeNetCommercePaymentEngineGroupServiceConfiguration.
				requireCaptcha());

		_addSetting(
			settings, "hostedPaymentSecurityOptions",
			hostedPaymentSecurityOptionsJSONObject);

		return arrayOfSetting;
	}

	private ArrayOfLineItem _getLineItems(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		ArrayOfLineItem arrayOfLineItems = new ArrayOfLineItem();

		List<LineItemType> lineItems = arrayOfLineItems.getLineItem();

		String languageId = LanguageUtil.getLanguageId(locale);

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

			LineItemType lineItem = new LineItemType();

			lineItem.setDescription(
				cpDefinition.getShortDescription(languageId));
			lineItem.setItemId(commerceOrderItem.getSku());
			lineItem.setName(commerceOrderItem.getTitle(languageId));
			lineItem.setQuantity(
				BigDecimal.valueOf(commerceOrderItem.getQuantity()));

			double unitPrice =
				commerceOrderItem.getPrice() / commerceOrderItem.getQuantity();

			lineItem.setUnitPrice(BigDecimal.valueOf(unitPrice));

			lineItems.add(lineItem);
		}

		return arrayOfLineItems;
	}

	private MerchantAuthenticationType _getMerchantAuthentication(
		AuthorizeNetCommercePaymentEngineGroupServiceConfiguration
			authorizeNetCommercePaymentEngineGroupServiceConfiguration) {

		MerchantAuthenticationType merchantAuthentication =
			new MerchantAuthenticationType();

		merchantAuthentication.setName(
			authorizeNetCommercePaymentEngineGroupServiceConfiguration.
				apiLoginId());
		merchantAuthentication.setTransactionKey(
			authorizeNetCommercePaymentEngineGroupServiceConfiguration.
				transactionKey());

		return merchantAuthentication;
	}

	private String _getRedirectHTML(
		String token, Environment environment, Locale locale) {

		String html = ContentUtil.get(
			AuthorizeNetCommercePaymentEngine.class.getClassLoader(),
			"com/liferay/commerce/payment/engine/authorize/net/internal" +
				"/dependencies/redirect_html.tmpl");

		String title = LanguageUtil.get(
			_getResourceBundle(locale), "redirecting-to-authorize-net");

		String url = "https://accept.authorize.net/payment/payment";

		if (environment == Environment.SANDBOX) {
			url = "https://test.authorize.net/payment/payment";
		}

		return StringUtil.replace(
			html, new String[] {"[$TITLE$]", "[$TOKEN$]", "[$URL$]"},
			new String[] {title, token, url});
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private ExtendedAmountType _getShipping(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		ExtendedAmountType extendedAmount = new ExtendedAmountType();

		extendedAmount.setAmount(
			BigDecimal.valueOf(commerceOrder.getShippingPrice()));

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		if (commerceShippingMethod != null) {
			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			String label =
				commerceShippingEngine.getCommerceShippingOptionLabel(
					commerceOrder.getShippingOptionName(), locale);

			extendedAmount.setName(label);
		}

		return extendedAmount;
	}

	private TransactionRequestType _getTransactionRequest(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency,
			Locale locale)
		throws PortalException {

		TransactionRequestType transactionRequest =
			new TransactionRequestType();

		transactionRequest.setAmount(
			BigDecimal.valueOf(commerceOrder.getTotal()));
		transactionRequest.setBillTo(
			_getCustomerAddress(commerceOrder.getBillingAddress(), locale));
		transactionRequest.setCurrencyCode(commerceCurrency.getCode());
		transactionRequest.setLineItems(_getLineItems(commerceOrder, locale));
		transactionRequest.setShipping(_getShipping(commerceOrder, locale));
		transactionRequest.setShipTo(
			_getCustomerAddress(commerceOrder.getShippingAddress(), locale));
		transactionRequest.setTransactionType(
			TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());

		return transactionRequest;
	}

	private CommercePaymentEngineResult.StartPayment _startPayment(
			CommerceOrder commerceOrder, String cancelURL, String returnURL,
			ServiceContext serviceContext)
		throws Exception {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				commerceOrder.getGroupId());

		if (commerceCurrency == null) {
			throw new CommercePaymentEngineException.MustSetPrimaryCurrency();
		}

		long groupId = commerceOrder.getGroupId();
		Locale locale = serviceContext.getLocale();

		AuthorizeNetCommercePaymentEngineGroupServiceConfiguration
			authorizeNetCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					AuthorizeNetCommercePaymentEngineGroupServiceConfiguration.
						class,
					new GroupServiceSettingsLocator(
						groupId,
						AuthorizeNetCommercePaymentEngineConstants.
							SERVICE_NAME));

		GetHostedPaymentPageRequest getHostedPaymentPageRequest =
			new GetHostedPaymentPageRequest();

		getHostedPaymentPageRequest.setHostedPaymentSettings(
			_getHostedPaymentSettings(
				groupId, cancelURL, returnURL,
				authorizeNetCommercePaymentEngineGroupServiceConfiguration,
				locale));
		getHostedPaymentPageRequest.setMerchantAuthentication(
			_getMerchantAuthentication(
				authorizeNetCommercePaymentEngineGroupServiceConfiguration));
		getHostedPaymentPageRequest.setRefId(
			String.valueOf(commerceOrder.getCommerceOrderId()));
		getHostedPaymentPageRequest.setTransactionRequest(
			_getTransactionRequest(commerceOrder, commerceCurrency, locale));

		GetHostedPaymentPageController getHostedPaymentPageController =
			new GetHostedPaymentPageController(getHostedPaymentPageRequest);

		Environment environment = Environment.valueOf(
			StringUtil.toUpperCase(
				authorizeNetCommercePaymentEngineGroupServiceConfiguration.
					environment()));

		GetHostedPaymentPageResponse getHostedPaymentPageResponse =
			getHostedPaymentPageController.executeWithApiResponse(environment);

		String token = getHostedPaymentPageResponse.getToken();

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("token", token);

		return new CommercePaymentEngineResult.StartPayment(
			jsonObject.toJSONString(),
			_getRedirectHTML(token, environment, locale));
	}

	private static final char _ADDRESS_SEPARATOR = CharPool.SPACE;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.engine.authorize.net)"
	)
	private ServletContext _servletContext;

	@Reference
	private SettingsFactory _settingsFactory;

}