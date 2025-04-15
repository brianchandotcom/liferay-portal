/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.integration.CommercePaymentIntegration;
import com.liferay.commerce.payment.integration.CommercePaymentIntegrationRegistry;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierLocalService;
import com.liferay.commerce.payment.util.comparator.CommercePaymentMethodPriorityComparator;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.comparator.CommerceShippingMethodPriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 * @author Crescenzo Rega
 */
@Component(service = ModelListener.class)
public class CommerceOrderModelListener
	extends BaseModelListener<CommerceOrder> {

	@Override
	public void onAfterUpdate(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder) {

		try {
			if ((originalCommerceOrder.getOrderStatus() !=
					commerceOrder.getOrderStatus()) &&
				(commerceOrder.getOrderStatus() ==
					CommerceOrderConstants.ORDER_STATUS_SHIPPED)) {

				_commerceOrderEngine.checkCommerceOrderShipmentStatus(
					commerceOrder, true);
			}

			ListUtil.isNotEmptyForEach(
				originalCommerceOrder.getCustomerCommerceOrderIds(),
				customerCommerceOrderId -> {
					try {
						CommerceOrder customerCommerceOrder =
							_commerceOrderLocalService.getCommerceOrder(
								customerCommerceOrderId);

						_updateOrderStatus(
							customerCommerceOrder,
							commerceOrder.getOrderStatus(),
							originalCommerceOrder.getOrderStatus());

						if (_updateShippingAmount(
								customerCommerceOrder,
								commerceOrder.getShippingAmount(),
								originalCommerceOrder.getShippingAmount()) ||
							_updateShippingDiscountAmount(
								customerCommerceOrder,
								commerceOrder.getShippingDiscountAmount(),
								originalCommerceOrder.
									getShippingDiscountAmount()) ||
							_updateSubtotal(
								customerCommerceOrder,
								commerceOrder.getSubtotal(),
								originalCommerceOrder.getSubtotal()) ||
							_updateSubtotalDiscountAmount(
								customerCommerceOrder,
								commerceOrder.getSubtotalDiscountAmount(),
								originalCommerceOrder.
									getSubtotalDiscountAmount()) ||
							_updateTaxAmount(
								customerCommerceOrder,
								commerceOrder.getTaxAmount(),
								originalCommerceOrder.getTaxAmount()) ||
							_updateTotal(
								customerCommerceOrder, commerceOrder.getTotal(),
								originalCommerceOrder.getTotal()) ||
							_updateTotalDiscountAmount(
								customerCommerceOrder,
								commerceOrder.getTotalDiscountAmount(),
								originalCommerceOrder.
									getTotalDiscountAmount())) {

							_commerceOrderLocalService.updateCommerceOrder(
								customerCommerceOrder);
						}
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException);
						}
					}
				});
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	@Override
	public void onBeforeCreate(CommerceOrder commerceOrder)
		throws ModelListenerException {

		try {
			User user = _userLocalService.getUser(commerceOrder.getUserId());

			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					commerceOrder.getGroupId());

			_setBillingAddress(commerceChannel, commerceOrder);
			_setShippingAddress(commerceChannel, commerceOrder);
			_setPaymentIntegration(commerceChannel, commerceOrder, user);
			_setShippingOption(commerceChannel, commerceOrder, user);
			_setDeliveryTerm(commerceChannel, commerceOrder, user);
			_setPaymentTerm(commerceChannel, commerceOrder, user);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private List<CommercePaymentMethodGroupRel>
		_filterCommercePaymentMethodGroupRels(
			long commerceOrderTypeId,
			List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels,
			boolean subscriptionOrder, User user) {

		if (ListUtil.isEmpty(commercePaymentMethodGroupRels)) {
			return Collections.emptyList();
		}

		List<CommercePaymentMethodGroupRel>
			filteredCommercePaymentMethodGroupRels = new LinkedList<>();

		ListUtil.sort(
			commercePaymentMethodGroupRels,
			new CommercePaymentMethodPriorityComparator());

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			List<CommercePaymentMethodGroupRelQualifier>
				commercePaymentMethodGroupRelQualifiers =
					_commercePaymentMethodGroupRelQualifierLocalService.
						getCommercePaymentMethodGroupRelQualifiers(
							CommerceOrderType.class.getName(),
							commercePaymentMethodGroupRel.
								getCommercePaymentMethodGroupRelId());

			if ((commerceOrderTypeId > 0) &&
				ListUtil.isNotEmpty(commercePaymentMethodGroupRelQualifiers) &&
				!ListUtil.exists(
					commercePaymentMethodGroupRelQualifiers,
					commercePaymentMethodGroupRelQualifier -> {
						long classPK =
							commercePaymentMethodGroupRelQualifier.getClassPK();

						return classPK == commerceOrderTypeId;
					})) {

				continue;
			}

			CommercePaymentIntegration commercePaymentIntegration =
				_commercePaymentIntegrationRegistry.
					getCommercePaymentIntegration(
						commercePaymentMethodGroupRel.
							getPaymentIntegrationKey());
			CommercePaymentMethod commercePaymentMethod =
				_commercePaymentMethodRegistry.getCommercePaymentMethod(
					commercePaymentMethodGroupRel.getPaymentIntegrationKey());
			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			if (((commercePaymentIntegration == null) &&
				 (commercePaymentMethod == null)) ||
				!permissionChecker.hasPermission(
					commercePaymentMethodGroupRel.getGroupId(),
					CommercePaymentMethodGroupRel.class.getName(),
					commercePaymentMethodGroupRel.
						getCommercePaymentMethodGroupRelId(),
					ActionKeys.VIEW) ||
				((commercePaymentMethod == null) && subscriptionOrder) ||
				((commercePaymentMethod != null) && subscriptionOrder &&
				 !commercePaymentMethod.isProcessRecurringEnabled()) ||
				((commercePaymentMethod != null) && !subscriptionOrder &&
				 !commercePaymentMethod.isProcessPaymentEnabled())) {

				continue;
			}

			filteredCommercePaymentMethodGroupRels.add(
				commercePaymentMethodGroupRel);
		}

		return filteredCommercePaymentMethodGroupRels;
	}

	private void _setBillingAddress(
			CommerceChannel commerceChannel, CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder.getBillingAddressId() > 0) {
			return;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceOrder.getCommerceAccountId(),
					commerceChannel.getCommerceChannelId(),
					CommerceChannelAccountEntryRelConstants.
						TYPE_BILLING_ADDRESS);

		if (commerceChannelAccountEntryRel == null) {
			return;
		}

		CommerceAddress commerceAddress =
			_commerceAddressLocalService.getCommerceAddress(
				commerceChannelAccountEntryRel.getClassPK());

		if (commerceAddress == null) {
			return;
		}

		List<CommerceAddress> billingCommerceAddresses =
			_commerceAddressLocalService.getBillingCommerceAddresses(
				commerceChannel.getCommerceChannelId(),
				AccountEntry.class.getName(),
				commerceOrder.getCommerceAccountId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (billingCommerceAddresses.contains(commerceAddress)) {
			commerceOrder.setBillingAddressId(
				commerceAddress.getCommerceAddressId());
		}
	}

	private void _setDeliveryTerm(
		CommerceChannel commerceChannel, CommerceOrder commerceOrder,
		User user) {

		if (commerceOrder.getDeliveryCommerceTermEntryId() > 0) {
			return;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceOrder.getCommerceAccountId(),
					commerceChannel.getCommerceChannelId(),
					CommerceChannelAccountEntryRelConstants.TYPE_DELIVERY_TERM);

		if (commerceChannelAccountEntryRel == null) {
			return;
		}

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				commerceChannelAccountEntryRel.getClassPK());

		if ((commerceTermEntry == null) || !commerceTermEntry.isActive()) {
			return;
		}

		commerceOrder.setDeliveryCommerceTermEntryId(
			commerceTermEntry.getCommerceTermEntryId());
		commerceOrder.setDeliveryCommerceTermEntryDescription(
			commerceTermEntry.getDescription(user.getLanguageId(), true));
		commerceOrder.setDeliveryCommerceTermEntryName(
			commerceTermEntry.getLabel(user.getLanguageId(), true));
	}

	private void _setPaymentIntegration(
			CommerceChannel commerceChannel, CommerceOrder commerceOrder,
			User user)
		throws PortalException {

		if (Validator.isNotNull(commerceOrder.getCommercePaymentMethodKey())) {
			return;
		}

		CommerceAddress commerceAddress = commerceOrder.getBillingAddress();

		if (commerceAddress == null) {
			commerceAddress = commerceOrder.getShippingAddress();

			if (commerceAddress == null) {
				return;
			}
		}

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			_filterCommercePaymentMethodGroupRels(
				commerceOrder.getCommerceOrderTypeId(),
				_commercePaymentMethodGroupRelLocalService.
					getCommercePaymentMethodGroupRels(
						commerceOrder.getGroupId(),
						commerceAddress.getCountryId(), true),
				commerceOrder.isSubscriptionOrder(), user);

		if (ListUtil.isEmpty(commercePaymentMethodGroupRels)) {
			return;
		}

		if (commercePaymentMethodGroupRels.size() == 1) {
			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
				commercePaymentMethodGroupRels.get(0);

			commerceOrder.setCommercePaymentMethodKey(
				commercePaymentMethodGroupRel.getPaymentIntegrationKey());
		}

		AccountEntry accountEntry = commerceOrder.getAccountEntry();

		if (accountEntry == null) {
			return;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					accountEntry.getAccountEntryId(),
					commerceChannel.getCommerceChannelId(),
					CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);

		if (commerceChannelAccountEntryRel == null) {
			return;
		}

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				fetchCommercePaymentMethodGroupRel(
					commerceChannelAccountEntryRel.getClassPK());

		if ((commercePaymentMethodGroupRel != null) &&
			commercePaymentMethodGroupRel.isActive() &&
			commercePaymentMethodGroupRels.contains(
				commercePaymentMethodGroupRel) &&
			Validator.isNull(commerceOrder.getCommercePaymentMethodKey())) {

			commerceOrder.setCommercePaymentMethodKey(
				commercePaymentMethodGroupRel.getPaymentIntegrationKey());
		}
	}

	private void _setPaymentTerm(
		CommerceChannel commerceChannel, CommerceOrder commerceOrder,
		User user) {

		if (commerceOrder.getPaymentCommerceTermEntryId() > 0) {
			return;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceOrder.getCommerceAccountId(),
					commerceChannel.getCommerceChannelId(),
					CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT_TERM);

		if (commerceChannelAccountEntryRel == null) {
			return;
		}

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				commerceChannelAccountEntryRel.getClassPK());

		if ((commerceTermEntry == null) || !commerceTermEntry.isActive()) {
			return;
		}

		commerceOrder.setPaymentCommerceTermEntryId(
			commerceTermEntry.getCommerceTermEntryId());
		commerceOrder.setPaymentCommerceTermEntryDescription(
			commerceTermEntry.getDescription(user.getLanguageId(), true));
		commerceOrder.setPaymentCommerceTermEntryName(
			commerceTermEntry.getLabel(user.getLanguageId(), true));
	}

	private void _setShippingAddress(
			CommerceChannel commerceChannel, CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder.getShippingAddressId() > 0) {
			return;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceOrder.getCommerceAccountId(),
					commerceChannel.getCommerceChannelId(),
					CommerceChannelAccountEntryRelConstants.
						TYPE_SHIPPING_ADDRESS);

		if (commerceChannelAccountEntryRel == null) {
			return;
		}

		CommerceAddress commerceAddress =
			_commerceAddressLocalService.getCommerceAddress(
				commerceChannelAccountEntryRel.getClassPK());

		if (commerceAddress == null) {
			return;
		}

		List<CommerceAddress> shippingCommerceAddresses =
			_commerceAddressLocalService.getShippingCommerceAddresses(
				commerceChannel.getCommerceChannelId(),
				AccountEntry.class.getName(),
				commerceOrder.getCommerceAccountId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (shippingCommerceAddresses.contains(commerceAddress)) {
			commerceOrder.setShippingAddressId(
				commerceAddress.getCommerceAddressId());
		}
	}

	private void _setShippingOption(
			CommerceChannel commerceChannel, CommerceOrder commerceOrder,
			User user)
		throws PortalException {

		if ((commerceOrder.getCommerceShippingMethodId() > 0) &&
			Validator.isNotNull(commerceOrder.getShippingOptionName())) {

			return;
		}

		AccountEntry accountEntry = commerceOrder.getAccountEntry();

		if (accountEntry.isPersonalAccount()) {
			return;
		}

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					fetchCommerceShippingOptionAccountEntryRel(
						accountEntry.getAccountEntryId(),
						commerceChannel.getCommerceChannelId());

		if (commerceShippingOptionAccountEntryRel == null) {
			return;
		}

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodLocalService.getCommerceShippingMethods(
				commerceOrder.getGroupId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				CommerceShippingMethodPriorityComparator.getInstance(false));

		if (ListUtil.isEmpty(commerceShippingMethods)) {
			return;
		}

		CommerceContext commerceContext = _commerceContextFactory.create(
			accountEntry.getAccountEntryId(), commerceChannel.getGroupId(),
			commerceOrder.getCommerceCurrencyCode(),
			commerceOrder.getCommerceOrderId(), commerceOrder.getCompanyId());

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			if (commerceShippingEngine == null) {
				continue;
			}

			List<CommerceShippingOption> commerceShippingOptions =
				commerceShippingEngine.getEnabledCommerceShippingOptions(
					commerceContext, commerceOrder, user.getLocale());

			if (ListUtil.isEmpty(commerceShippingOptions)) {
				continue;
			}

			CommerceShippingOption defaultCommerceShippingOption = null;

			for (CommerceShippingOption commerceShippingOption :
					commerceShippingOptions) {

				String key = commerceShippingOption.getKey();

				if (key.equals(
						commerceShippingOptionAccountEntryRel.
							getCommerceShippingOptionKey())) {

					defaultCommerceShippingOption = commerceShippingOption;

					break;
				}
			}

			if (defaultCommerceShippingOption != null) {
				commerceOrder.setCommerceShippingMethodId(
					commerceShippingMethod.getCommerceShippingMethodId());
				commerceOrder.setShippingOptionName(
					defaultCommerceShippingOption.getKey());
			}
		}
	}

	private boolean _transitionOrderStatusCompleted(CommerceOrder commerceOrder)
		throws PortalException {

		List<Long> supplierCommerceOrderIds =
			commerceOrder.getSupplierCommerceOrderIds();

		if (ListUtil.isEmpty(supplierCommerceOrderIds)) {
			return false;
		}

		CommerceOrder firstSupplierCommerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				supplierCommerceOrderIds.get(0));

		int orderStatus = firstSupplierCommerceOrder.getOrderStatus();

		if ((supplierCommerceOrderIds.size() == 1) &&
			(orderStatus != commerceOrder.getOrderStatus())) {

			return true;
		}

		for (Long supplierCommerceOrderId : supplierCommerceOrderIds) {
			CommerceOrder supplierCommerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					supplierCommerceOrderId);

			if (orderStatus != supplierCommerceOrder.getOrderStatus()) {
				return false;
			}
		}

		return true;
	}

	private void _updateOrderStatus(
			CommerceOrder customerCommerceOrder, int newOrderStatus,
			int originalOrderStatus)
		throws PortalException {

		if (originalOrderStatus == newOrderStatus) {
			return;
		}

		_commerceOrderEngine.checkCommerceOrderShipmentStatus(
			customerCommerceOrder, false);

		if ((newOrderStatus == CommerceOrderConstants.ORDER_STATUS_COMPLETED) &&
			_transitionOrderStatusCompleted(customerCommerceOrder)) {

			_commerceOrderEngine.transitionCommerceOrder(
				customerCommerceOrder, newOrderStatus, 0, false);
		}
	}

	private boolean _updateShippingAmount(
		CommerceOrder customerCommerceOrder, BigDecimal newShippingAmount,
		BigDecimal originalShippingAmount) {

		int compareShippingAmount = originalShippingAmount.compareTo(
			newShippingAmount);

		if (compareShippingAmount == 0) {
			return false;
		}

		BigDecimal customerShippingAmount =
			customerCommerceOrder.getShippingAmount();

		BigDecimal subtractOriginalValue = customerShippingAmount.subtract(
			originalShippingAmount);

		customerCommerceOrder.setShippingAmount(
			subtractOriginalValue.add(newShippingAmount));

		return true;
	}

	private boolean _updateShippingDiscountAmount(
		CommerceOrder customerCommerceOrder,
		BigDecimal newShippingDiscountAmount,
		BigDecimal originalShippingDiscountAmount) {

		int compareShippingDiscountAmount =
			originalShippingDiscountAmount.compareTo(newShippingDiscountAmount);

		if (compareShippingDiscountAmount == 0) {
			return false;
		}

		BigDecimal customerShippingDiscountAmount =
			customerCommerceOrder.getShippingDiscountAmount();

		BigDecimal subtractOriginalValue =
			customerShippingDiscountAmount.subtract(
				originalShippingDiscountAmount);

		customerCommerceOrder.setShippingDiscountAmount(
			subtractOriginalValue.add(newShippingDiscountAmount));

		return true;
	}

	private boolean _updateSubtotal(
		CommerceOrder customerCommerceOrder, BigDecimal newSubtotal,
		BigDecimal originalSubtotal) {

		int compareSubtotal = originalSubtotal.compareTo(newSubtotal);

		if (compareSubtotal == 0) {
			return false;
		}

		BigDecimal customerSubtotal = customerCommerceOrder.getSubtotal();

		BigDecimal subtractOriginalValue = customerSubtotal.subtract(
			originalSubtotal);

		customerCommerceOrder.setSubtotal(
			subtractOriginalValue.add(newSubtotal));

		return true;
	}

	private boolean _updateSubtotalDiscountAmount(
		CommerceOrder customerCommerceOrder,
		BigDecimal newSubtotalDiscountAmount,
		BigDecimal originalSubtotalDiscountAmount) {

		int compareSubtotalDiscountAmount =
			originalSubtotalDiscountAmount.compareTo(newSubtotalDiscountAmount);

		if (compareSubtotalDiscountAmount == 0) {
			return false;
		}

		BigDecimal customerSubtotalDiscountAmount =
			customerCommerceOrder.getSubtotalDiscountAmount();

		BigDecimal subtractOriginalValue =
			customerSubtotalDiscountAmount.subtract(
				originalSubtotalDiscountAmount);

		customerCommerceOrder.setSubtotalDiscountAmount(
			subtractOriginalValue.add(newSubtotalDiscountAmount));

		return true;
	}

	private boolean _updateTaxAmount(
		CommerceOrder customerCommerceOrder, BigDecimal newTaxAmount,
		BigDecimal originalTaxAmount) {

		int compareTaxAmount = originalTaxAmount.compareTo(newTaxAmount);

		if (compareTaxAmount == 0) {
			return false;
		}

		BigDecimal customerTaxAmount = customerCommerceOrder.getTaxAmount();

		BigDecimal subtractOriginalValue = customerTaxAmount.subtract(
			originalTaxAmount);

		customerCommerceOrder.setTaxAmount(
			subtractOriginalValue.add(newTaxAmount));

		return true;
	}

	private boolean _updateTotal(
		CommerceOrder customerCommerceOrder, BigDecimal newTotal,
		BigDecimal originalTotal) {

		int compareTotal = originalTotal.compareTo(newTotal);

		if (compareTotal == 0) {
			return false;
		}

		BigDecimal customerTotal = customerCommerceOrder.getTotal();

		BigDecimal subtractOriginalValue = customerTotal.subtract(
			originalTotal);

		customerCommerceOrder.setTotal(subtractOriginalValue.add(newTotal));

		return true;
	}

	private boolean _updateTotalDiscountAmount(
		CommerceOrder customerCommerceOrder, BigDecimal newTotalDiscountAmount,
		BigDecimal originalTotalDiscountAmount) {

		int compareTotalDiscountAmount = originalTotalDiscountAmount.compareTo(
			newTotalDiscountAmount);

		if (compareTotalDiscountAmount != 0) {
			return false;
		}

		BigDecimal customerTotalDiscountAmount =
			customerCommerceOrder.getTotalDiscountAmount();

		BigDecimal subtractOriginalValue = customerTotalDiscountAmount.subtract(
			originalTotalDiscountAmount);

		customerCommerceOrder.setTotalDiscountAmount(
			subtractOriginalValue.add(newTotalDiscountAmount));

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderModelListener.class);

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommercePaymentIntegrationRegistry
		_commercePaymentIntegrationRegistry;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommercePaymentMethodGroupRelQualifierLocalService
		_commercePaymentMethodGroupRelQualifierLocalService;

	@Reference
	private CommercePaymentMethodRegistry _commercePaymentMethodRegistry;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

}