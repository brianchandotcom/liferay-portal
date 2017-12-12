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

package com.liferay.commerce.payment.engine.paypal.internal;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.exception.CommercePaymentEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.engine.paypal.internal.configuration.PayPalCommercePaymentEngineGroupServiceConfiguration;
import com.liferay.commerce.payment.engine.paypal.internal.constants.PayPalCommercePaymentEngineConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;

import java.util.ArrayList;
import java.util.Collections;
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
	immediate = true, property = "commerce.payment.engine.key=paypal",
	service = CommercePaymentEngine.class
)
public class PayPalCommercePaymentEngine implements CommercePaymentEngine {

	@Override
	public void cancelPayment(
			CommerceOrder commerceOrder, Map<String, String[]> parameterMap)
		throws CommercePaymentEngineException {
	}

	@Override
	public void completePayment(
			CommerceOrder commerceOrder, Map<String, String[]> parameterMap)
		throws CommercePaymentEngineException {

		try {
			_completePayment(commerceOrder, parameterMap);
		}
		catch (CommercePaymentEngineException cpee) {
			throw cpee;
		}
		catch (Exception e) {
			throw new CommercePaymentEngineException(e);
		}
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "paypal-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "paypal");
	}

	@Override
	public void renderConfiguration(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PayPalCommercePaymentEngineGroupServiceConfiguration
			paypalCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					PayPalCommercePaymentEngineGroupServiceConfiguration.class,
					new ParameterMapSettingsLocator(
						renderRequest.getParameterMap(),
						new GroupServiceSettingsLocator(
							themeDisplay.getScopeGroupId(),
							PayPalCommercePaymentEngineConstants.
								SERVICE_NAME)));

		renderRequest.setAttribute(
			PayPalCommercePaymentEngineGroupServiceConfiguration.class.
				getName(),
			paypalCommercePaymentEngineGroupServiceConfiguration);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/configuration.jsp");
	}

	@Override
	public String startPayment(
			CommerceOrder commerceOrder, String cancelURL, String returnURL,
			Locale locale)
		throws CommercePaymentEngineException {

		try {
			return _getPaymentURL(commerceOrder, cancelURL, returnURL, locale);
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
				PayPalCommercePaymentEngineConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			modifiableSettings.setValue(entry.getKey(), entry.getValue());
		}

		modifiableSettings.store();
	}

	private void _completePayment(
			CommerceOrder commerceOrder, Map<String, String[]> parameterMap)
		throws Exception {

		String payerId = MapUtil.getString(parameterMap, "PayerID");
		String paymentId = MapUtil.getString(parameterMap, "paymentId");

		APIContext apiContext = _getAPIContext(commerceOrder);

		Payment payment = new Payment();

		payment.setId(paymentId);

		PaymentExecution paymentExecution = new PaymentExecution();

		paymentExecution.setPayerId(payerId);

		payment.execute(apiContext, paymentExecution);
	}

	private Amount _getAmount(
		CommerceOrder commerceOrder, CommerceCurrency commerceCurrency) {

		Amount amount = new Amount();

		amount.setCurrency(StringUtil.toUpperCase(commerceCurrency.getCode()));

		Details details = new Details();

		details.setShipping(String.valueOf(commerceOrder.getShippingPrice()));
		details.setSubtotal(String.valueOf(commerceOrder.getSubtotal()));

		amount.setDetails(details);

		amount.setTotal(String.valueOf(commerceOrder.getTotal()));

		return amount;
	}

	private APIContext _getAPIContext(CommerceOrder commerceOrder)
		throws Exception {

		PayPalCommercePaymentEngineGroupServiceConfiguration
			payPalCommercePaymentEngineGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					PayPalCommercePaymentEngineGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						commerceOrder.getGroupId(),
						PayPalCommercePaymentEngineConstants.SERVICE_NAME));

		return new APIContext(
			payPalCommercePaymentEngineGroupServiceConfiguration.clientId(),
			payPalCommercePaymentEngineGroupServiceConfiguration.clientSecret(),
			payPalCommercePaymentEngineGroupServiceConfiguration.mode());
	}

	private ItemList _getItemList(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency,
			Locale locale)
		throws PortalException {

		ItemList itemList = new ItemList();

		itemList.setItems(_getItems(commerceOrder, commerceCurrency, locale));

		CommerceAddress commerceAddress = commerceOrder.getShippingAddress();

		if (commerceAddress != null) {
			itemList.setShippingAddress(_getShippingAddress(commerceAddress));
		}

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		if (commerceShippingMethod != null) {
			itemList.setShippingMethod(commerceShippingMethod.getName(locale));
		}

		return itemList;
	}

	private List<Item> _getItems(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency,
			Locale locale)
		throws PortalException {

		String languageId = LanguageUtil.getLanguageId(locale);

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		List<Item> items = new ArrayList<>(commerceOrderItems.size());

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			Item item = new Item();

			CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();
			CPInstance cpInstance = commerceOrderItem.getCPInstance();

			item.setCurrency(
				StringUtil.toUpperCase(commerceCurrency.getCode()));
			item.setDescription(cpDefinition.getShortDescription(languageId));
			item.setName(cpDefinition.getTitle(languageId));
			item.setPrice(String.valueOf(commerceOrderItem.getPrice()));
			item.setQuantity(String.valueOf(commerceOrderItem.getQuantity()));
			item.setSku(cpInstance.getSku());

			items.add(item);
		}

		return items;
	}

	private String _getPaymentURL(
			CommerceOrder commerceOrder, String cancelURL, String returnURL,
			Locale locale)
		throws Exception {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				commerceOrder.getGroupId());

		if (commerceCurrency == null) {
			throw new CommercePaymentEngineException.MustSetPrimaryCurrency();
		}

		APIContext apiContext = _getAPIContext(commerceOrder);

		Payment payment = new Payment();

		payment.setIntent("sale");

		Payer payer = new Payer();

		payer.setPaymentMethod("paypal");

		payment.setPayer(payer);

		RedirectUrls redirectUrls = new RedirectUrls();

		redirectUrls.setCancelUrl(cancelURL);
		redirectUrls.setReturnUrl(returnURL);

		payment.setRedirectUrls(redirectUrls);

		payment.setTransactions(
			_getTransactions(commerceOrder, commerceCurrency, locale));

		payment = payment.create(apiContext);

		String url = null;

		for (Links links : payment.getLinks()) {
			if ("approval_url".equals(links.getRel())) {
				url = links.getHref();

				break;
			}
		}

		if (Validator.isNull(url)) {
			throw new CommercePaymentEngineException(
				"Unable to get PayPal payment URL");
		}

		url = _http.addParameter(url, "useraction", "commit");

		return url;
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private ShippingAddress _getShippingAddress(CommerceAddress commerceAddress)
		throws PortalException {

		ShippingAddress shippingAddress = new ShippingAddress();

		shippingAddress.setCity(commerceAddress.getCity());

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		shippingAddress.setCountryCode(commerceCountry.getTwoLettersISOCode());

		shippingAddress.setLine1(commerceAddress.getStreet1());
		shippingAddress.setLine2(commerceAddress.getStreet2());
		shippingAddress.setPostalCode(commerceAddress.getZip());
		shippingAddress.setRecipientName(commerceAddress.getName());

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		if (commerceRegion != null) {
			shippingAddress.setState(commerceRegion.getCode());
		}

		return shippingAddress;
	}

	private List<Transaction> _getTransactions(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency,
			Locale locale)
		throws PortalException {

		Transaction transaction = new Transaction();

		transaction.setAmount(_getAmount(commerceOrder, commerceCurrency));
		transaction.setItemList(
			_getItemList(commerceOrder, commerceCurrency, locale));

		return Collections.singletonList(transaction);
	}

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
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.engine.paypal)"
	)
	private ServletContext _servletContext;

	@Reference
	private SettingsFactory _settingsFactory;

}