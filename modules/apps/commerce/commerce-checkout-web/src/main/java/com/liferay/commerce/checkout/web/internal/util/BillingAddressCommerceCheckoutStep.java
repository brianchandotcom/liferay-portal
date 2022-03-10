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

package com.liferay.commerce.checkout.web.internal.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.checkout.helper.CommerceCheckoutStepHttpHelper;
import com.liferay.commerce.checkout.web.internal.display.context.BillingAddressCheckoutStepDisplayContext;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.exception.CommerceAddressCityException;
import com.liferay.commerce.exception.CommerceAddressCountryException;
import com.liferay.commerce.exception.CommerceAddressNameException;
import com.liferay.commerce.exception.CommerceAddressStreetException;
import com.liferay.commerce.exception.CommerceAddressZipException;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderDefaultBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.checkout.step.name=" + BillingAddressCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=30"
	},
	service = CommerceCheckoutStep.class
)
public class BillingAddressCommerceCheckoutStep
	extends BaseCommerceCheckoutStep {

	public static final String NAME = "billing-address";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		if (!commerceOrder.isGuestOrder() &&
			!commerceAccount.isPersonalAccount()) {

			long defaultBillingAddressId =
				commerceAccount.getDefaultBillingAddressId();

			long billingAddressId = commerceOrder.getBillingAddressId();

			if ((defaultBillingAddressId <= 0) && (billingAddressId <= 0) &&
				_hasViewBillingAddressPermission(
					httpServletRequest, commerceAccount)) {

				return true;
			}

			if (commerceOrder.isOpen() && (defaultBillingAddressId > 0) &&
				(defaultBillingAddressId != billingAddressId)) {

				commerceOrderService.updateBillingAddress(
					commerceOrder.getCommerceOrderId(),
					defaultBillingAddressId);
			}

			if (_hasViewBillingAddressPermission(
					httpServletRequest, commerceAccount)) {

				return false;
			}
		}

		return _commerceCheckoutStepHttpHelper.
			isActiveBillingAddressCommerceCheckoutStep(
				httpServletRequest, commerceOrder);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			AddressCommerceCheckoutStepUtil addressCommerceCheckoutStepUtil =
				new AddressCommerceCheckoutStepUtil(
					commerceAccountLocalService,
					CommerceAddressConstants.ADDRESS_TYPE_BILLING,
					commerceOrderService, commerceAddressService,
					commerceOrderModelResourcePermission);

			addressCommerceCheckoutStepUtil.updateCommerceOrderAddress(
				actionRequest,
				CommerceCheckoutWebKeys.BILLING_ADDRESS_PARAM_NAME);
		}
		catch (Exception exception) {
			if (exception instanceof CommerceAddressCityException ||
				exception instanceof CommerceAddressCountryException ||
				exception instanceof CommerceAddressNameException ||
				exception instanceof CommerceAddressStreetException ||
				exception instanceof CommerceAddressZipException ||
				exception instanceof CommerceOrderBillingAddressException ||
				exception instanceof CommerceOrderShippingAddressException) {

				SessionErrors.add(actionRequest, exception.getClass());

				return;
			}

			throw exception;
		}
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		BillingAddressCheckoutStepDisplayContext
			billingAddressCheckoutStepDisplayContext =
				new BillingAddressCheckoutStepDisplayContext(
					accountRoleLocalService,
					_accountEntryModelResourcePermission,
					commerceAddressService, httpServletRequest,
					_portletResourcePermission);

		CommerceOrder commerceOrder =
			billingAddressCheckoutStepDisplayContext.getCommerceOrder();

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		if ((commerceAccount.getDefaultBillingAddressId() <= 0) &&
			(commerceOrder.getBillingAddressId() <= 0) &&
			_hasViewBillingAddressPermission(
				httpServletRequest, commerceAccount)) {

			httpServletRequest.setAttribute(
				CommerceCheckoutWebKeys.SHOW_ERROR_NO_BILLING_ADDRESS,
				Boolean.TRUE);

			SessionMessages.add(
				httpServletRequest,
				_portal.getPortletId(httpServletRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);

			SessionErrors.add(
				httpServletRequest,
				CommerceOrderDefaultBillingAddressException.class);
		}

		if (!commerceOrder.isOpen()) {
			httpServletRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_ORDER_DETAIL_URL,
				_commerceCheckoutStepHttpHelper.getOrderDetailURL(
					httpServletRequest, commerceOrder));

			jspRenderer.renderJSP(
				httpServletRequest, httpServletResponse, "/error.jsp");
		}
		else {
			httpServletRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
				billingAddressCheckoutStepDisplayContext);

			jspRenderer.renderJSP(
				httpServletRequest, httpServletResponse,
				"/checkout_step/address.jsp");
		}
	}

	@Override
	public boolean showControls(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		try {
			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			if ((commerceAccount.getDefaultBillingAddressId() <= 0) &&
				(commerceOrder.getBillingAddressId() <= 0) &&
				_hasViewBillingAddressPermission(
					httpServletRequest, commerceAccount)) {

				return false;
			}
		}
		catch (Exception exception) {
			return false;
		}

		if (!commerceOrder.isOpen()) {
			return false;
		}

		return super.showControls(httpServletRequest, httpServletResponse);
	}

	@Reference
	protected AccountRoleLocalService accountRoleLocalService;

	@Reference
	protected CommerceAccountLocalService commerceAccountLocalService;

	@Reference
	protected CommerceAddressService commerceAddressService;

	@Reference
	protected CommerceOrderLocalService commerceOrderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	protected ModelResourcePermission<CommerceOrder>
		commerceOrderModelResourcePermission;

	@Reference
	protected CommerceOrderService commerceOrderService;

	@Reference
	protected JSPRenderer jspRenderer;

	private PermissionChecker _getPermissionChecker(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return PermissionCheckerFactoryUtil.create(themeDisplay.getUser());
	}

	private boolean _hasViewBillingAddressPermission(
			HttpServletRequest httpServletRequest,
			CommerceAccount commerceAccount)
		throws PortalException {

		return !_portletResourcePermission.contains(
			_getPermissionChecker(httpServletRequest),
			commerceAccount.getCommerceAccountGroup(),
			CommerceWebKeys.VIEW_BILLING_ADDRESS);
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private CommerceCheckoutStepHttpHelper _commerceCheckoutStepHttpHelper;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}