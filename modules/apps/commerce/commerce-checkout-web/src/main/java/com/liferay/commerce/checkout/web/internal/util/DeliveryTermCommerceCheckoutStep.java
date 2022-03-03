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
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.checkout.helper.CommerceCheckoutStepHttpHelper;
import com.liferay.commerce.checkout.web.internal.display.context.TermCommerceCheckoutStepDisplayContext;
import com.liferay.commerce.configuration.CommerceOrderCheckoutConfiguration;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.checkout.step.name=" + DeliveryTermCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=50"
	},
	service = CommerceCheckoutStep.class
)
public class DeliveryTermCommerceCheckoutStep extends BaseCommerceCheckoutStep {

	public static final String NAME = "delivery-terms";

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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(themeDisplay.getUser());

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		if (!commerceOrder.isGuestOrder() &&
			!commerceAccount.isPersonalAccount() &&
			!_portletResourcePermission.contains(
				permissionChecker, commerceAccount.getCommerceAccountGroup(),
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_PAYMENT_TERMS)) {

			return false;
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		CommerceOrderCheckoutConfiguration commerceOrderCheckoutConfiguration =
			ConfigurationProviderUtil.getConfiguration(
				CommerceOrderCheckoutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER));

		return _commerceCheckoutStepHttpHelper.
			isActiveDeliveryTermCommerceCheckoutStep(
				httpServletRequest, commerceOrder,
				LanguageUtil.getLanguageId(httpServletRequest.getLocale()),
				commerceOrderCheckoutConfiguration.
					viewDeliveryTermCheckoutStepEnabled());
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String commerceDeliveryTermId = ParamUtil.getString(
			actionRequest, "commerceDeliveryTermId");

		if (!Validator.isNumber(commerceDeliveryTermId)) {
			SessionErrors.add(actionRequest, "deliveryTermsInvalid");

			return;
		}

		CommerceOrder commerceOrder = (CommerceOrder)actionRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);

		commerceOrder = _commerceOrderLocalService.updateTermsAndConditions(
			commerceOrder.getCommerceOrderId(),
			Long.valueOf(commerceDeliveryTermId), 0,
			LanguageUtil.getLanguageId(actionRequest.getLocale()));

		actionRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			commerceAccount.getCommerceAccountId());

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				accountEntry.getDefaultDeliveryCTermEntryId());

		if ((commerceTermEntry != null) && commerceTermEntry.isActive()) {
			CommerceOrder commerceOrder =
				(CommerceOrder)httpServletRequest.getAttribute(
					CommerceCheckoutWebKeys.COMMERCE_ORDER);

			CommerceShippingMethod commerceShippingMethod =
				_commerceShippingMethodLocalService.getCommerceShippingMethod(
					commerceOrder.getCommerceShippingMethodId());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			List<CommerceTermEntry> deliveryCommerceTermEntries =
				Collections.emptyList();

			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			List<CommerceShippingOption> commerceShippingOptions =
				commerceShippingEngine.getCommerceShippingOptions(
					commerceContext, commerceOrder, themeDisplay.getLocale());

			String shippingOptionName = commerceOrder.getShippingOptionName();

			for (CommerceShippingOption commerceShippingOption :
					commerceShippingOptions) {

				if (shippingOptionName.equals(
						commerceShippingOption.getName())) {

					CommerceShippingFixedOption commerceShippingFixedOption =
						_commerceShippingFixedOptionLocalService.
							fetchCommerceShippingFixedOption(
								commerceOrder.getCompanyId(),
								commerceShippingOption.getName());

					if (commerceShippingFixedOption != null) {
						deliveryCommerceTermEntries =
							_commerceTermEntryLocalService.
								getDeliveryCommerceTermEntries(
									commerceOrder.getCompanyId(),
									commerceOrder.getCommerceOrderTypeId(),
									commerceShippingFixedOption.
										getCommerceShippingFixedOptionId());
					}
				}
			}

			if (deliveryCommerceTermEntries.contains(commerceTermEntry)) {
				commerceOrder =
					_commerceOrderLocalService.updateTermsAndConditions(
						commerceOrder.getCommerceOrderId(),
						accountEntry.getDefaultDeliveryCTermEntryId(), 0,
						LanguageUtil.getLanguageId(
							httpServletRequest.getLocale()));

				httpServletRequest.setAttribute(
					CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);
			}
		}

		TermCommerceCheckoutStepDisplayContext
			termCommerceCheckoutStepDisplayContext =
				new TermCommerceCheckoutStepDisplayContext(
					_commercePaymentMethodGroupRelLocalService,
					_commerceShippingEngineRegistry,
					_commerceShippingFixedOptionLocalService,
					_commerceShippingMethodLocalService,
					_commerceTermEntryLocalService, httpServletRequest);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			termCommerceCheckoutStepDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/delivery_term.jsp");
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceCheckoutStepHttpHelper _commerceCheckoutStepHttpHelper;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}