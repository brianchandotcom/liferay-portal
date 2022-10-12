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
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.util.CommerceBigDecimalUtil;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.commerce.util.comparator.CommerceShippingMethodPriorityComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
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
@Component(immediate = true, service = CommerceCheckoutStepHttpHelper.class)
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

		CommerceAddress defaultBillingCommerceAddress = null;
		CommerceAddress defaultShippingCommerceAddress = null;

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		if (commerceAccount != null) {
			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(
					commerceAccount.getCommerceAccountId());

			if (accountEntry != null) {
				CommerceChannel commerceChannel =
					_commerceChannelLocalService.
						getCommerceChannelByOrderGroupId(
							commerceOrder.getGroupId());

				CommerceChannelAccountEntryRel
					billingAddressCommerceChannelAccountEntryRel =
						_commerceChannelAccountEntryRelLocalService.
							fetchCommerceChannelAccountEntryRel(
								accountEntry.getAccountEntryId(),
								commerceChannel.getCommerceChannelId(),
								CommerceChannelAccountEntryRelConstants.
									TYPE_BILLING_ADDRESS);

				if (billingAddressCommerceChannelAccountEntryRel != null) {
					defaultBillingCommerceAddress =
						_commerceAddressService.getCommerceAddress(
							billingAddressCommerceChannelAccountEntryRel.
								getClassPK());
				}

				CommerceChannelAccountEntryRel
					shippingAddressCommerceChannelAccountEntryRel =
						_commerceChannelAccountEntryRelLocalService.
							fetchCommerceChannelAccountEntryRel(
								accountEntry.getAccountEntryId(),
								commerceChannel.getCommerceChannelId(),
								CommerceChannelAccountEntryRelConstants.
									TYPE_SHIPPING_ADDRESS);

				if (shippingAddressCommerceChannelAccountEntryRel != null) {
					defaultShippingCommerceAddress =
						_commerceAddressService.getCommerceAddress(
							shippingAddressCommerceChannelAccountEntryRel.
								getClassPK());
				}
			}
		}

		long defaultBillingCommerceAddressId = 0;
		long defaultShippingCommerceAddressId = 0;

		if (defaultBillingCommerceAddress != null) {
			defaultBillingCommerceAddressId =
				defaultBillingCommerceAddress.getCommerceAddressId();
		}

		if (defaultShippingCommerceAddress != null) {
			defaultShippingCommerceAddressId =
				defaultShippingCommerceAddress.getCommerceAddressId();
		}

		CommerceAddress shippingAddress = commerceOrder.getShippingAddress();
		CommerceAddress billingAddress = commerceOrder.getBillingAddress();

		if (((commerceAccount != null) &&
			 (defaultBillingCommerceAddressId != 0) &&
			 (defaultShippingCommerceAddressId != 0) &&
			 (defaultBillingCommerceAddressId ==
				 defaultShippingCommerceAddressId) &&
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

		if (!commerceOrder.isOpen() ||
			(commerceOrder.getCommerceShippingMethodId() <= 0)) {

			return false;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel = null;

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount != null) {
			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(
					commerceAccount.getCommerceAccountId());

			if (accountEntry != null) {
				commerceChannelAccountEntryRel =
					_commerceChannelAccountEntryRelLocalService.
						fetchCommerceChannelAccountEntryRel(
							accountEntry.getAccountEntryId(),
							commerceContext.getCommerceChannelId(),
							CommerceChannelAccountEntryRelConstants.
								TYPE_DELIVERY_TERM);

				if ((commerceChannelAccountEntryRel != null) &&
					commerceChannelAccountEntryRel.isOverrideEligibility()) {

					CommerceTermEntry commerceTermEntry =
						_commerceTermEntryLocalService.getCommerceTermEntry(
							commerceChannelAccountEntryRel.getClassPK());

					_commerceOrderLocalService.updateTermsAndConditions(
						commerceOrder.getCommerceOrderId(),
						commerceTermEntry.getCommerceTermEntryId(), 0,
						languageId);

					return false;
				}
			}
		}

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.getCommerceShippingMethod(
				commerceOrder.getCommerceShippingMethodId());

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

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

			if (shippingOptionName.equals(commerceShippingOption.getKey())) {
				CommerceShippingFixedOption commerceShippingFixedOption =
					_commerceShippingFixedOptionLocalService.
						fetchCommerceShippingFixedOption(
							commerceOrder.getCompanyId(),
							commerceShippingOption.getKey());

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
			if (commerceOrder.getDeliveryCommerceTermEntryId() > 0) {
				return false;
			}

			CommerceTermEntry commerceTermEntry =
				deliveryCommerceTermEntries.get(0);

			_commerceOrderLocalService.updateTermsAndConditions(
				commerceOrder.getCommerceOrderId(),
				commerceTermEntry.getCommerceTermEntryId(), 0, languageId);

			return false;
		}

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				commerceOrder.getDeliveryCommerceTermEntryId());

		if ((commerceTermEntry == null) &&
			(commerceChannelAccountEntryRel != null)) {

			commerceTermEntry =
				_commerceTermEntryLocalService.fetchCommerceTermEntry(
					commerceChannelAccountEntryRel.getClassPK());
		}

		if ((commerceTermEntry != null) && commerceTermEntry.isActive() &&
			deliveryCommerceTermEntries.contains(commerceTermEntry)) {

			_commerceOrderLocalService.updateTermsAndConditions(
				commerceOrder.getCommerceOrderId(),
				commerceTermEntry.getCommerceTermEntryId(), 0,
				_language.getLanguageId(_portal.getLocale(httpServletRequest)));
		}
		else {
			commerceTermEntry = deliveryCommerceTermEntries.get(0);

			_commerceOrderLocalService.updateTermsAndConditions(
				commerceOrder.getCommerceOrderId(),
				commerceTermEntry.getCommerceTermEntryId(), 0, languageId);
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

		if (commercePaymentMethodGroupRelsCount <= 0) {
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
			_updateCommerceOrder(
				commerceOrder, StringPool.BLANK, httpServletRequest);

			return false;
		}

		if (commercePaymentMethods.size() == 1) {
			CommercePaymentMethod commercePaymentMethod =
				commercePaymentMethods.get(0);

			_updateCommerceOrder(
				commerceOrder, commercePaymentMethod.getKey(),
				httpServletRequest);

			return false;
		}

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount != null) {
			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(
					commerceAccount.getCommerceAccountId());

			if (accountEntry != null) {
				if (Validator.isNull(
						commerceOrder.getCommercePaymentMethodKey())) {

					CommerceChannelAccountEntryRel
						commerceChannelAccountEntryRel =
							_commerceChannelAccountEntryRelService.
								fetchCommerceChannelAccountEntryRel(
									accountEntry.getAccountEntryId(),
									commerceContext.getCommerceChannelId(),
									CommerceChannelAccountEntryRelConstants.
										TYPE_PAYMENT);

					CommercePaymentMethod commercePaymentMethod =
						commercePaymentMethods.get(0);

					if (commerceChannelAccountEntryRel != null) {
						CommercePaymentMethodGroupRel
							commercePaymentMethodGroupRel =
								_commercePaymentMethodGroupRelLocalService.
									fetchCommercePaymentMethodGroupRel(
										commerceChannelAccountEntryRel.
											getClassPK());

						if ((commercePaymentMethodGroupRel != null) &&
							commercePaymentMethodGroupRel.isActive()) {

							Stream<CommercePaymentMethod>
								commercePaymentMethodsStream =
									commercePaymentMethods.stream();

							commercePaymentMethod =
								commercePaymentMethodsStream.filter(
									curCommercePaymentMethod -> {
										String key =
											curCommercePaymentMethod.getKey();

										return key.equals(
											commercePaymentMethodGroupRel.
												getEngineKey());
									}
								).findFirst(
								).orElse(
									commercePaymentMethods.get(0)
								);
						}
					}

					_updateCommerceOrder(
						commerceOrder, commercePaymentMethod.getKey(),
						httpServletRequest);
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
						commerceOrder, commercePaymentMethod.getKey(),
						httpServletRequest);
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
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest,
			String languageId)
		throws PortalException {

		String commercePaymentMethodKey =
			commerceOrder.getCommercePaymentMethodKey();

		if (!commerceOrder.isOpen() ||
			Validator.isNull(commercePaymentMethodKey)) {

			return false;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel = null;

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount != null) {
			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(
					commerceAccount.getCommerceAccountId());

			if (accountEntry != null) {
				commerceChannelAccountEntryRel =
					_commerceChannelAccountEntryRelLocalService.
						fetchCommerceChannelAccountEntryRel(
							accountEntry.getAccountEntryId(),
							commerceContext.getCommerceChannelId(),
							CommerceChannelAccountEntryRelConstants.
								TYPE_PAYMENT_TERM);

				if ((commerceChannelAccountEntryRel != null) &&
					commerceChannelAccountEntryRel.isOverrideEligibility()) {

					CommerceTermEntry commerceTermEntry =
						_commerceTermEntryLocalService.getCommerceTermEntry(
							commerceChannelAccountEntryRel.getClassPK());

					_commerceOrderLocalService.updateTermsAndConditions(
						commerceOrder.getCommerceOrderId(), 0,
						commerceTermEntry.getCommerceTermEntryId(), languageId);

					return false;
				}
			}
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
			if (commerceOrder.getPaymentCommerceTermEntryId() > 0) {
				return false;
			}

			CommerceTermEntry commerceTermEntry =
				paymentCommerceTermEntries.get(0);

			_commerceOrderLocalService.updateTermsAndConditions(
				commerceOrder.getCommerceOrderId(), 0,
				commerceTermEntry.getCommerceTermEntryId(), languageId);

			return false;
		}

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				commerceOrder.getPaymentCommerceTermEntryId());

		if ((commerceTermEntry == null) &&
			(commerceChannelAccountEntryRel != null)) {

			commerceTermEntry =
				_commerceTermEntryLocalService.fetchCommerceTermEntry(
					commerceChannelAccountEntryRel.getClassPK());
		}

		if ((commerceTermEntry != null) && commerceTermEntry.isActive() &&
			paymentCommerceTermEntries.contains(commerceTermEntry)) {

			_commerceOrderLocalService.updateTermsAndConditions(
				commerceOrder.getCommerceOrderId(), 0,
				commerceTermEntry.getCommerceTermEntryId(), languageId);
		}
		else {
			commerceTermEntry = paymentCommerceTermEntries.get(0);

			_commerceOrderLocalService.updateTermsAndConditions(
				commerceOrder.getCommerceOrderId(), 0,
				commerceTermEntry.getCommerceTermEntryId(), languageId);
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

		if (!commerceOrder.isOpen() ||
			!_commerceShippingHelper.isShippable(commerceOrder) ||
			_commerceShippingHelper.isFreeShipping(commerceOrder)) {

			return false;
		}

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodLocalService.getCommerceShippingMethods(
				commerceOrder.getGroupId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new CommerceShippingMethodPriorityComparator());

		CommerceShippingOption singleCommerceShippingOption =
			_getSingleCommerceShippingOption(
				commerceContext, commerceOrder, commerceShippingMethods,
				httpServletRequest);

		if (singleCommerceShippingOption != null) {
			_updateCommerceOrder(
				commerceContext, commerceOrder,
				singleCommerceShippingOption.getCommerceShippingMethodKey(),
				singleCommerceShippingOption.getKey(), httpServletRequest);

			return false;
		}

		if (commerceOrder.getCommerceShippingMethodId() > 0) {
			CommerceShippingMethod commerceShippingMethod =
				_commerceShippingMethodLocalService.getCommerceShippingMethod(
					commerceOrder.getCommerceShippingMethodId());

			if (commerceShippingMethod.isActive()) {
				return _hasCommerceOrderPermission(
					CommerceOrderActionKeys.
						MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS,
					commerceOrder, httpServletRequest);
			}
		}

		if (commerceShippingMethods.isEmpty()) {
			_updateCommerceOrder(
				commerceContext, commerceOrder, StringPool.BLANK,
				StringPool.BLANK, httpServletRequest);

			return false;
		}

		if (!commerceOrder.isGuestOrder()) {
			commerceOrder = _updateCommerceOrderCommerceShippingMethod(
				commerceContext, commerceOrder, commerceShippingMethods,
				httpServletRequest);
		}

		return _hasCommerceOrderPermission(
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS,
			commerceOrder, httpServletRequest);
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

	private CommerceShippingOption _getSingleCommerceShippingOption(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			List<CommerceShippingMethod> commerceShippingMethods,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		if (commerceShippingMethods.size() == 1) {
			CommerceShippingMethod commerceShippingMethod =
				commerceShippingMethods.get(0);

			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			List<CommerceShippingOption> commerceShippingOptions =
				commerceShippingEngine.getEnabledCommerceShippingOptions(
					commerceContext, commerceOrder,
					_portal.getLocale(httpServletRequest));

			if (commerceShippingOptions.size() == 1) {
				return commerceShippingOptions.get(0);
			}
		}

		return null;
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

	private CommerceOrder _updateCommerceOrder(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			String commerceShippingMethodKey, String commerceShippingOptionKey,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceAddress commerceAddress = commerceOrder.getBillingAddress();

		if (commerceAddress == null) {
			commerceAddress = commerceOrder.getShippingAddress();
		}

		if (commerceAddress == null) {
			return commerceOrder;
		}

		try {
			CommerceOrder updatedCommerceOrder = TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					long commerceShippingMethodId = 0;

					CommerceShippingMethod commerceShippingMethod =
						_commerceShippingMethodLocalService.
							fetchCommerceShippingMethod(
								commerceContext.getCommerceChannelGroupId(),
								commerceShippingMethodKey);

					if (commerceShippingMethod != null) {
						commerceShippingMethodId =
							commerceShippingMethod.
								getCommerceShippingMethodId();
					}

					_commerceOrderLocalService.updateCommerceShippingMethod(
						commerceOrder.getCommerceOrderId(),
						commerceShippingMethodId, commerceShippingOptionKey,
						commerceContext, _portal.getLocale(httpServletRequest));

					return _commerceOrderLocalService.recalculatePrice(
						commerceOrder.getCommerceOrderId(), commerceContext);
				});

			httpServletRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER, updatedCommerceOrder);

			return updatedCommerceOrder;
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	private void _updateCommerceOrder(
			CommerceOrder commerceOrder, String commercePaymentMethodKey,
			HttpServletRequest httpServletRequest)
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

		commerceOrder =
			_commerceOrderLocalService.updateCommercePaymentMethodKey(
				commerceOrder.getCommerceOrderId(), commercePaymentMethodKey);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);
	}

	private CommerceOrder _updateCommerceOrderCommerceShippingMethod(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			List<CommerceShippingMethod> commerceShippingMethods,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		if (commerceAccount.isPersonalAccount()) {
			return commerceOrder;
		}

		CommerceShippingOption highestPriorityCommerceShippingOption = null;

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			commerceAccount.getCommerceAccountId());

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					fetchCommerceShippingOptionAccountEntryRel(
						accountEntry.getAccountEntryId(),
						commerceContext.getCommerceChannelId());

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			List<CommerceShippingOption> commerceShippingOptions =
				commerceShippingEngine.getEnabledCommerceShippingOptions(
					commerceContext, commerceOrder,
					_portal.getLocale(httpServletRequest));

			if (commerceShippingOptions.isEmpty()) {
				continue;
			}

			if (commerceShippingOptionAccountEntryRel != null) {
				Stream<CommerceShippingOption> commerceShippingOptionsStream =
					commerceShippingOptions.stream();

				CommerceShippingOption defaultCommerceShippingOption =
					commerceShippingOptionsStream.filter(
						commerceShippingOption -> {
							String key = commerceShippingOption.getKey();

							return key.equals(
								commerceShippingOptionAccountEntryRel.
									getCommerceShippingOptionKey());
						}
					).findFirst(
					).orElse(
						null
					);

				if (defaultCommerceShippingOption != null) {
					return _updateCommerceOrder(
						commerceContext, commerceOrder,
						commerceShippingMethod.getEngineKey(),
						defaultCommerceShippingOption.getKey(),
						httpServletRequest);
				}
			}

			if (highestPriorityCommerceShippingOption == null) {
				highestPriorityCommerceShippingOption =
					commerceShippingOptions.get(0);

				if (commerceShippingOptionAccountEntryRel == null) {
					break;
				}
			}
		}

		if (highestPriorityCommerceShippingOption != null) {
			return _updateCommerceOrder(
				commerceContext, commerceOrder,
				highestPriorityCommerceShippingOption.
					getCommerceShippingMethodKey(),
				highestPriorityCommerceShippingOption.getKey(),
				httpServletRequest);
		}

		return commerceOrder;
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	@Reference
	private CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _commerceOrderPortletResourcePermission;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

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
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}