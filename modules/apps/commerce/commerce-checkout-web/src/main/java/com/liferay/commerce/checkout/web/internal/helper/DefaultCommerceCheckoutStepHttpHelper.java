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

package com.liferay.commerce.checkout.web.internal.helper;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.checkout.helper.CommerceCheckoutStepHttpHelper;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.util.CommerceBigDecimalUtil;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceCheckoutStepHttpHelper.class
)
public class DefaultCommerceCheckoutStepHttpHelper
	implements CommerceCheckoutStepHttpHelper {

	@Override
	public String getOrderDetailURL(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException {

		PortletURL portletURL =
			_commerceOrderHttpHelper.getCommerceCartPortletURL(
				httpServletRequest, commerceOrder);

		if (portletURL == null) {
			return StringPool.BLANK;
		}

		return portletURL.toString();
	}

	@Override
	public boolean isActiveBillingAddressCommerceCheckoutStep(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException {

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();
		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();
		CommerceAddress billingAddress = commerceOrder.getBillingAddress();

		if (((commerceAccount != null) &&
			 (commerceAccount.getDefaultBillingAddressId() != 0) &&
			 (commerceAccount.getDefaultShippingAddressId() != 0) &&
			 (commerceAccount.getDefaultBillingAddressId() ==
				 commerceAccount.getDefaultShippingAddressId()) &&
			 (billingAddress == null) && (shippingAddress == null) &&
			 _commerceShippingHelper.isShippable(commerceOrder)) ||
			((billingAddress != null) && (shippingAddress != null) &&
			 (billingAddress.getCommerceAddressId() ==
				 shippingAddress.getCommerceAddressId()))) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isActiveDeliveryTermCommerceCheckoutStep(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder,
			String languageId)
		throws PortalException {

		if (commerceOrder.getCommerceShippingMethodId() < 1) {
			return false;
		}

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.getCommerceShippingMethod(
				commerceOrder.getCommerceShippingMethodId());

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				commerceContext, commerceOrder, themeDisplay.getLocale());

		String shippingOptionName = commerceOrder.getShippingOptionName();

		List<CommerceTermEntry> deliveryCommerceTermEntries = null;

		for (CommerceShippingOption commerceShippingOption :
				commerceShippingOptions) {

			if (shippingOptionName.equals(commerceShippingOption.getName())) {
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

		if (ListUtil.isEmpty(deliveryCommerceTermEntries)) {
			return false;
		}

		if (deliveryCommerceTermEntries.size() == 1) {
			if (commerceOrder.isOpen()) {
				CommerceTermEntry commerceTermEntry =
					deliveryCommerceTermEntries.get(0);

				_commerceOrderService.updateTermsAndConditions(
					commerceOrder.getCommerceOrderId(),
					commerceTermEntry.getCommerceTermEntryId(), 0, languageId);
			}

			return false;
		}

		if (commerceOrder.isOpen()) {
			CommerceAccount commerceAccount =
				commerceContext.getCommerceAccount();

			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(
					commerceAccount.getCommerceAccountId());

			CommerceTermEntry commerceTermEntry =
				_commerceTermEntryLocalService.fetchCommerceTermEntry(
					accountEntry.getDefaultDeliveryCTermEntryId());

			if ((commerceTermEntry != null) && commerceTermEntry.isActive() &&
				deliveryCommerceTermEntries.contains(commerceTermEntry)) {

				_commerceOrderService.updateTermsAndConditions(
					commerceOrder.getCommerceOrderId(),
					accountEntry.getDefaultDeliveryCTermEntryId(), 0,
					LanguageUtil.getLanguageId(httpServletRequest.getLocale()));
			}
			else {
				commerceTermEntry = deliveryCommerceTermEntries.get(0);

				_commerceOrderService.updateTermsAndConditions(
					commerceOrder.getCommerceOrderId(),
					commerceTermEntry.getCommerceTermEntryId(), 0, languageId);
			}
		}

		return true;
	}

	@Override
	public boolean isActivePaymentMethodCommerceCheckoutStep(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException {

		long commercePaymentMethodGroupRelsCount =
			_commercePaymentEngine.getCommercePaymentMethodGroupRelsCount(
				commerceOrder.getGroupId());

		if (commercePaymentMethodGroupRelsCount < 1) {
			return false;
		}

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceOrderPrice commerceOrderPrice =
			_commerceOrderPriceCalculation.getCommerceOrderPrice(
				commerceOrder, commerceContext);

		CommerceMoney orderPriceTotalCommerceMoney =
			commerceOrderPrice.getTotal();

		if (CommerceBigDecimalUtil.isZero(
				orderPriceTotalCommerceMoney.getPrice())) {

			return false;
		}

		List<CommercePaymentMethod> commercePaymentMethods =
			_commercePaymentEngine.getEnabledCommercePaymentMethodsForOrder(
				commerceOrder.getGroupId(), commerceOrder.getCommerceOrderId());

		if (commercePaymentMethods.isEmpty()) {
			return false;
		}

		if (commercePaymentMethods.size() == 1) {
			CommercePaymentMethod commercePaymentMethod =
				commercePaymentMethods.get(0);

			_updateCommerceOrder(
				httpServletRequest, commerceOrder,
				commercePaymentMethod.getKey());

			return false;
		}

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		if (commerceAccount != null) {
			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(
					commerceAccount.getCommerceAccountId());

			if ((accountEntry != null) &&
				(accountEntry.getDefaultCPaymentMethodKey() != null)) {

				if (Validator.isNull(
						commerceOrder.getCommercePaymentMethodKey())) {

					Stream<CommercePaymentMethod> commercePaymentMethodsStream =
						commercePaymentMethods.stream();

					CommercePaymentMethod commercePaymentMethod =
						commercePaymentMethodsStream.filter(
							curCommercePaymentMethod -> {
								String key = curCommercePaymentMethod.getKey();

								return key.equals(
									accountEntry.getDefaultCPaymentMethodKey());
							}
						).findFirst(
						).orElse(
							commercePaymentMethods.get(0)
						);

					_updateCommerceOrder(
						httpServletRequest, commerceOrder,
						commercePaymentMethod.getKey());
				}

				if (!_hasCommerceOrderPermission(
						CommerceOrderActionKeys.
							MANAGE_COMMERCE_ORDER_PAYMENT_METHODS,
						commerceOrder, httpServletRequest)) {

					return false;
				}
			}
			else {
				if (Validator.isNull(
						commerceOrder.getCommercePaymentMethodKey())) {

					CommercePaymentMethod commercePaymentMethod =
						commercePaymentMethods.get(0);

					_updateCommerceOrder(
						httpServletRequest, commerceOrder,
						commercePaymentMethod.getKey());
				}

				if (!_hasCommerceOrderPermission(
						CommerceOrderActionKeys.
							MANAGE_COMMERCE_ORDER_PAYMENT_METHODS,
						commerceOrder, httpServletRequest)) {

					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean isActivePaymentTermCommerceCheckoutStep(
			CommerceOrder commerceOrder, String languageId)
		throws PortalException {

		String commercePaymentMethodKey =
			commerceOrder.getCommercePaymentMethodKey();

		if (Validator.isNull(commercePaymentMethodKey)) {
			return false;
		}

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				getCommercePaymentMethodGroupRel(
					commerceOrder.getGroupId(), commercePaymentMethodKey);

		List<CommerceTermEntry> paymentCommerceTermEntries =
			_commerceTermEntryLocalService.getPaymentCommerceTermEntries(
				commerceOrder.getCompanyId(),
				commerceOrder.getCommerceOrderTypeId(),
				commercePaymentMethodGroupRel.
					getCommercePaymentMethodGroupRelId());

		if (paymentCommerceTermEntries.isEmpty()) {
			return false;
		}

		if (paymentCommerceTermEntries.size() == 1) {
			if (commerceOrder.isOpen()) {
				CommerceTermEntry commerceTermEntry =
					paymentCommerceTermEntries.get(0);

				_commerceOrderService.updateTermsAndConditions(
					commerceOrder.getCommerceOrderId(), 0,
					commerceTermEntry.getCommerceTermEntryId(), languageId);
			}

			return false;
		}

		if (commerceOrder.isOpen()) {
			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			AccountEntry accountEntry =
				_accountEntryLocalService.getAccountEntry(
					commerceAccount.getCommerceAccountId());

			CommerceTermEntry commerceTermEntry =
				_commerceTermEntryLocalService.fetchCommerceTermEntry(
					accountEntry.getDefaultPaymentCTermEntryId());

			if ((commerceTermEntry != null) && commerceTermEntry.isActive() &&
				paymentCommerceTermEntries.contains(commerceTermEntry)) {

				_commerceOrderService.updateTermsAndConditions(
					commerceOrder.getCommerceOrderId(), 0,
					accountEntry.getDefaultPaymentCTermEntryId(), languageId);
			}
			else {
				commerceTermEntry = paymentCommerceTermEntries.get(0);

				_commerceOrderService.updateTermsAndConditions(
					commerceOrder.getCommerceOrderId(), 0,
					commerceTermEntry.getCommerceTermEntryId(), languageId);
			}
		}

		return true;
	}

	@Override
	public boolean isActiveShippingMethodCommerceCheckoutStep(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		if (!_commerceShippingHelper.isShippable(commerceOrder) ||
			_commerceShippingHelper.isFreeShipping(commerceOrder)) {

			return false;
		}

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodLocalService.getCommerceShippingMethods(
				commerceOrder.getGroupId(), true);

		if (commerceShippingMethods.isEmpty()) {
			return false;
		}

		if (commerceShippingMethods.size() == 1) {
			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceShippingMethod commerceShippingMethod =
				commerceShippingMethods.get(0);

			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			List<CommerceShippingOption> commerceShippingOptions =
				commerceShippingEngine.getCommerceShippingOptions(
					commerceContext, commerceOrder,
					_portal.getLocale(httpServletRequest));

			if (commerceShippingOptions.size() == 1) {
				if (commerceOrder.isOpen()) {
					_updateCommerceOrder(
						commerceContext, commerceOrder,
						commerceShippingMethod.getCommerceShippingMethodId(),
						commerceShippingOptions.get(0), httpServletRequest);
				}

				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isCommercePaymentComplete(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder)
		throws PortalException {

		if (CommerceBigDecimalUtil.isZero(commerceOrder.getTotal())) {
			return true;
		}

		return false;
	}

	private boolean _hasCommerceOrderPermission(
			String actionId, CommerceOrder commerceOrder,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!commerceOrder.isGuestOrder() &&
			!commerceAccount.isPersonalAccount() &&
			!_commerceOrderPortletResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				commerceAccount.getCommerceAccountGroupId(), actionId)) {

			return false;
		}

		return true;
	}

	private void _updateCommerceOrder(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			long commerceShippingMethodId,
			CommerceShippingOption commerceShippingOption,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceAddress commerceAddress = commerceOrder.getBillingAddress();

		if (commerceAddress == null) {
			commerceAddress = commerceOrder.getShippingAddress();
		}

		if (commerceAddress == null) {
			return;
		}

		commerceOrder = _commerceOrderService.updateCommerceShippingMethod(
			commerceOrder.getCommerceOrderId(), commerceShippingMethodId,
			commerceShippingOption.getName(), commerceContext,
			_portal.getLocale(httpServletRequest));

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);
	}

	private void _updateCommerceOrder(
			HttpServletRequest httpServletRequest, CommerceOrder commerceOrder,
			String commercePaymentMethodKey)
		throws PortalException {

		if (!commerceOrder.isOpen()) {
			return;
		}

		CommerceAddress commerceAddress = commerceOrder.getBillingAddress();

		if (commerceAddress == null) {
			commerceAddress = commerceOrder.getShippingAddress();
		}

		if ((commerceAddress == null) ||
			commercePaymentMethodKey.equals(
				commerceOrder.getCommercePaymentMethodKey())) {

			return;
		}

		commerceOrder = _commerceOrderService.updateCommercePaymentMethodKey(
			commerceOrder.getCommerceOrderId(), commercePaymentMethodKey);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _commerceOrderPortletResourcePermission;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference
	private Portal _portal;

}