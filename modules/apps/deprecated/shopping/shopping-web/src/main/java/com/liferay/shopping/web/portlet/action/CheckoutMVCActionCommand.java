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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.shopping.configuration.ShoppingGroupServiceOverriddenConfiguration;
import com.liferay.shopping.constants.ShoppingConstants;
import com.liferay.shopping.constants.ShoppingPortletKeys;
import com.liferay.shopping.exception.BillingCityException;
import com.liferay.shopping.exception.BillingCountryException;
import com.liferay.shopping.exception.BillingEmailAddressException;
import com.liferay.shopping.exception.BillingFirstNameException;
import com.liferay.shopping.exception.BillingLastNameException;
import com.liferay.shopping.exception.BillingPhoneException;
import com.liferay.shopping.exception.BillingStateException;
import com.liferay.shopping.exception.BillingStreetException;
import com.liferay.shopping.exception.BillingZipException;
import com.liferay.shopping.exception.CCExpirationException;
import com.liferay.shopping.exception.CCNameException;
import com.liferay.shopping.exception.CCNumberException;
import com.liferay.shopping.exception.CCTypeException;
import com.liferay.shopping.exception.NoSuchOrderException;
import com.liferay.shopping.exception.ShippingCityException;
import com.liferay.shopping.exception.ShippingCountryException;
import com.liferay.shopping.exception.ShippingEmailAddressException;
import com.liferay.shopping.exception.ShippingFirstNameException;
import com.liferay.shopping.exception.ShippingLastNameException;
import com.liferay.shopping.exception.ShippingPhoneException;
import com.liferay.shopping.exception.ShippingStateException;
import com.liferay.shopping.exception.ShippingStreetException;
import com.liferay.shopping.exception.ShippingZipException;
import com.liferay.shopping.model.ShoppingCart;
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.service.ShoppingOrderLocalService;
import com.liferay.shopping.util.ShoppingUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN,
		"mvc.command.name=/shopping/checkout"
	},
	service = MVCActionCommand.class
)
public class CheckoutMVCActionCommand extends BaseMVCActionCommand {

	protected void checkout(ActionRequest actionRequest) throws Exception {
		if (!hasLatestOrder(actionRequest)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			_shoppingOrderLocalService.addLatestOrder(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		if (redirectToLogin(actionRequest, actionResponse)) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.CHECKOUT)) {
			checkout(actionRequest);

			actionResponse.setRenderParameter("mvcPath", "/checkout_first.jsp");
		}
		else if (!hasLatestOrder(actionRequest)) {
			actionResponse.setRenderParameter("mvcPath", "/checkout_third.jsp");
		}
		else if (cmd.equals(Constants.SAVE)) {
			saveLatestOrder(actionRequest, actionResponse);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			try {
				updateLatestOrder(actionRequest);

				actionResponse.setRenderParameter(
					"mvcPath", "checkout_second.jsp");
			}
			catch (Exception e) {
				if (e instanceof BillingCityException ||
					e instanceof BillingCountryException ||
					e instanceof BillingEmailAddressException ||
					e instanceof BillingFirstNameException ||
					e instanceof BillingLastNameException ||
					e instanceof BillingPhoneException ||
					e instanceof BillingStateException ||
					e instanceof BillingStreetException ||
					e instanceof BillingZipException ||
					e instanceof CCExpirationException ||
					e instanceof CCNameException ||
					e instanceof CCNumberException ||
					e instanceof CCTypeException ||
					e instanceof ShippingCityException ||
					e instanceof ShippingCountryException ||
					e instanceof ShippingEmailAddressException ||
					e instanceof ShippingFirstNameException ||
					e instanceof ShippingLastNameException ||
					e instanceof ShippingPhoneException ||
					e instanceof ShippingStateException ||
					e instanceof ShippingStreetException ||
					e instanceof ShippingZipException) {

					SessionErrors.add(actionRequest, e.getClass());

					actionResponse.setRenderParameter(
						"mvcPath", "/checkout_first.jsp");
				}
				else if (e instanceof PrincipalException) {
					actionResponse.setRenderParameter("mvcPath", "/error.jsp");
				}
				else {
					throw e;
				}
			}
		}
		else if (cmd.equals(Constants.VIEW)) {
			actionResponse.setRenderParameter("mvcPath", "/checkout_third.jsp");
		}
		else {
			actionResponse.setRenderParameter("mvcPath", "/checkout_first.jsp");
		}
	}

	protected void forwardCheckout(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ShoppingOrder order)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ShoppingCart cart = ShoppingUtil.getCart(actionRequest);

		ShoppingGroupServiceOverriddenConfiguration
			shoppingGroupServiceOverriddenConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					ShoppingGroupServiceOverriddenConfiguration.class,
					new GroupServiceSettingsLocator(
						themeDisplay.getScopeGroupId(),
						ShoppingConstants.SERVICE_NAME));

		String returnURL = ShoppingUtil.getPayPalReturnURL(
			((ActionResponseImpl)actionResponse).createActionURL(), order);
		String notifyURL = ShoppingUtil.getPayPalNotifyURL(themeDisplay);

		if (shoppingGroupServiceOverriddenConfiguration.usePayPal()) {
			double total = ShoppingUtil.calculateTotal(
				cart.getItems(), order.getBillingState(), cart.getCoupon(),
				cart.getAltShipping(), cart.isInsure());

			String redirectURL = ShoppingUtil.getPayPalRedirectURL(
				shoppingGroupServiceOverriddenConfiguration, order, total,
				returnURL, notifyURL);

			actionResponse.sendRedirect(redirectURL);
		}
		else {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			_shoppingOrderLocalService.sendEmail(
				order, "confirmation", serviceContext);

			actionResponse.sendRedirect(returnURL);
		}
	}

	protected boolean hasLatestOrder(ActionRequest actionRequest)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			_shoppingOrderLocalService.getLatestOrder(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId());

			return true;
		}
		catch (NoSuchOrderException nsoe) {
			return false;
		}
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void saveLatestOrder(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ShoppingCart cart = ShoppingUtil.getCart(actionRequest);

		ShoppingOrder order = _shoppingOrderLocalService.saveLatestOrder(cart);

		forwardCheckout(actionRequest, actionResponse, order);
	}

	@Reference(unbind = "-")
	protected void setShoppingOrderLocalService(
		ShoppingOrderLocalService shoppingOrderLocalService) {

		_shoppingOrderLocalService = shoppingOrderLocalService;
	}

	protected void updateLatestOrder(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String billingFirstName = ParamUtil.getString(
			actionRequest, "billingFirstName");
		String billingLastName = ParamUtil.getString(
			actionRequest, "billingLastName");
		String billingEmailAddress = ParamUtil.getString(
			actionRequest, "billingEmailAddress");
		String billingCompany = ParamUtil.getString(
			actionRequest, "billingCompany");
		String billingStreet = ParamUtil.getString(
			actionRequest, "billingStreet");
		String billingCity = ParamUtil.getString(actionRequest, "billingCity");

		String billingStateSel = ParamUtil.getString(
			actionRequest, "billingStateSel");
		String billingState = billingStateSel;

		if (Validator.isNull(billingStateSel)) {
			billingState = ParamUtil.getString(actionRequest, "billingState");
		}

		String billingZip = ParamUtil.getString(actionRequest, "billingZip");
		String billingCountry = ParamUtil.getString(
			actionRequest, "billingCountry");
		String billingPhone = ParamUtil.getString(
			actionRequest, "billingPhone");

		boolean shipToBilling = ParamUtil.getBoolean(
			actionRequest, "shipToBilling");
		String shippingFirstName = ParamUtil.getString(
			actionRequest, "shippingFirstName");
		String shippingLastName = ParamUtil.getString(
			actionRequest, "shippingLastName");
		String shippingEmailAddress = ParamUtil.getString(
			actionRequest, "shippingEmailAddress");
		String shippingCompany = ParamUtil.getString(
			actionRequest, "shippingCompany");
		String shippingStreet = ParamUtil.getString(
			actionRequest, "shippingStreet");
		String shippingCity = ParamUtil.getString(
			actionRequest, "shippingCity");

		String shippingStateSel = ParamUtil.getString(
			actionRequest, "shippingStateSel");
		String shippingState = shippingStateSel;

		if (Validator.isNull(shippingStateSel)) {
			shippingState = ParamUtil.getString(actionRequest, "shippingState");
		}

		String shippingZip = ParamUtil.getString(actionRequest, "shippingZip");
		String shippingCountry = ParamUtil.getString(
			actionRequest, "shippingCountry");
		String shippingPhone = ParamUtil.getString(
			actionRequest, "shippingPhone");

		String ccName = ParamUtil.getString(actionRequest, "ccName");
		String ccType = ParamUtil.getString(actionRequest, "ccType");
		String ccNumber = ParamUtil.getString(actionRequest, "ccNumber");
		int ccExpMonth = ParamUtil.getInteger(actionRequest, "ccExpMonth");
		int ccExpYear = ParamUtil.getInteger(actionRequest, "ccExpYear");
		String ccVerNumber = ParamUtil.getString(actionRequest, "ccVerNumber");

		String comments = ParamUtil.getString(actionRequest, "comments");

		_shoppingOrderLocalService.updateLatestOrder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private ShoppingOrderLocalService _shoppingOrderLocalService;

}