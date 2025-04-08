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

import java.util.ArrayList;
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
							originalCommerceOrder, commerceOrder,
							customerCommerceOrder);

						if (_updateShippingAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateShippingDiscountAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateSubtotal(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateSubtotalDiscountAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateTaxAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateTotal(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder) ||
							_updateTotalDiscountAmount(
								originalCommerceOrder, commerceOrder,
								customerCommerceOrder)) {

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

			_setBillingAddress(commerceOrder, commerceChannel);

			_setShippingAddress(commerceOrder, commerceChannel);

			_setPaymentIntegration(user, commerceOrder, commerceChannel);

			_setShippingOption(user, commerceOrder, commerceChannel);

			_setDeliveryTerm(user, commerceOrder, commerceChannel);

			_setPaymentTerm(user, commerceOrder, commerceChannel);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private List<CommercePaymentMethodGroupRel>
		_filterCommercePaymentMethodGroupRels(
			User user,
			List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels,
			long commerceOrderTypeId, boolean subscriptionOrder) {

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

			CommercePaymentMethod commercePaymentMethod =
				_commercePaymentMethodRegistry.getCommercePaymentMethod(
					commercePaymentMethodGroupRel.getPaymentIntegrationKey());

			CommercePaymentIntegration commercePaymentIntegration =
				_commercePaymentIntegrationRegistry.
					getCommercePaymentIntegration(
						commercePaymentMethodGroupRel.
							getPaymentIntegrationKey());

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			if (((commercePaymentMethod == null) &&
				 (commercePaymentIntegration == null)) ||
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
			CommerceOrder commerceOrder, CommerceChannel commerceChannel)
		throws PortalException {

		if (commerceOrder.getBillingAddressId() <= 0) {
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				_commerceChannelAccountEntryRelLocalService.
					fetchCommerceChannelAccountEntryRel(
						commerceOrder.getCommerceAccountId(),
						commerceChannel.getCommerceChannelId(),
						CommerceChannelAccountEntryRelConstants.
							TYPE_BILLING_ADDRESS);

			if (commerceChannelAccountEntryRel != null) {
				List<CommerceAddress> billingCommerceAddresses =
					_commerceAddressLocalService.getBillingCommerceAddresses(
						commerceChannel.getCommerceChannelId(),
						AccountEntry.class.getName(),
						commerceOrder.getCommerceAccountId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS);

				CommerceAddress commerceAddress =
					_commerceAddressLocalService.getCommerceAddress(
						commerceChannelAccountEntryRel.getClassPK());

				if ((commerceAddress != null) &&
					billingCommerceAddresses.contains(commerceAddress)) {

					commerceOrder.setBillingAddressId(
						commerceAddress.getCommerceAddressId());
				}
			}
		}
	}

	private void _setDeliveryTerm(
		User user, CommerceOrder commerceOrder,
		CommerceChannel commerceChannel) {

		if (commerceOrder.getDeliveryCommerceTermEntryId() <= 0) {
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				_commerceChannelAccountEntryRelLocalService.
					fetchCommerceChannelAccountEntryRel(
						commerceOrder.getCommerceAccountId(),
						commerceChannel.getCommerceChannelId(),
						CommerceChannelAccountEntryRelConstants.
							TYPE_DELIVERY_TERM);

			if (commerceChannelAccountEntryRel != null) {
				CommerceTermEntry commerceTermEntry =
					_commerceTermEntryLocalService.fetchCommerceTermEntry(
						commerceChannelAccountEntryRel.getClassPK());

				if (commerceTermEntry != null) {
					commerceOrder.setDeliveryCommerceTermEntryId(
						commerceTermEntry.getCommerceTermEntryId());
					commerceOrder.setDeliveryCommerceTermEntryDescription(
						commerceTermEntry.getDescription(
							user.getLanguageId(), true));
					commerceOrder.setDeliveryCommerceTermEntryName(
						commerceTermEntry.getLabel(user.getLanguageId(), true));
				}
			}
		}
	}

	private void _setPaymentIntegration(
			User user, CommerceOrder commerceOrder,
			CommerceChannel commerceChannel)
		throws PortalException {

		if (Validator.isNotNull(commerceOrder.getCommercePaymentMethodKey())) {
			return;
		}

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			new ArrayList<>();

		CommerceAddress commerceAddress = commerceOrder.getBillingAddress();

		if (commerceAddress == null) {
			commerceAddress = commerceOrder.getShippingAddress();
		}

		if (commerceAddress != null) {
			commercePaymentMethodGroupRels.addAll(
				_commercePaymentMethodGroupRelLocalService.
					getCommercePaymentMethodGroupRels(
						commerceOrder.getGroupId(),
						commerceAddress.getCountryId(), true));
		}
		else {
			return;
		}

		commercePaymentMethodGroupRels = _filterCommercePaymentMethodGroupRels(
			user, commercePaymentMethodGroupRels,
			commerceOrder.getCommerceOrderTypeId(),
			commerceOrder.isSubscriptionOrder());

		if (commercePaymentMethodGroupRels.size() == 1) {
			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
				commercePaymentMethodGroupRels.get(0);

			commerceOrder.setCommercePaymentMethodKey(
				commercePaymentMethodGroupRel.getPaymentIntegrationKey());
		}

		AccountEntry accountEntry = commerceOrder.getAccountEntry();

		if ((accountEntry != null) &&
			!commercePaymentMethodGroupRels.isEmpty()) {

			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				_commerceChannelAccountEntryRelLocalService.
					fetchCommerceChannelAccountEntryRel(
						accountEntry.getAccountEntryId(),
						commerceChannel.getCommerceChannelId(),
						CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);

			if (commerceChannelAccountEntryRel != null) {
				CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
					_commercePaymentMethodGroupRelLocalService.
						fetchCommercePaymentMethodGroupRel(
							commerceChannelAccountEntryRel.getClassPK());

				if ((commercePaymentMethodGroupRel != null) &&
					commercePaymentMethodGroupRel.isActive() &&
					commercePaymentMethodGroupRels.contains(
						commercePaymentMethodGroupRel) &&
					Validator.isNull(
						commerceOrder.getCommercePaymentMethodKey())) {

					commerceOrder.setCommercePaymentMethodKey(
						commercePaymentMethodGroupRel.
							getPaymentIntegrationKey());
				}
			}
		}
	}

	private void _setPaymentTerm(
		User user, CommerceOrder commerceOrder,
		CommerceChannel commerceChannel) {

		if (commerceOrder.getPaymentCommerceTermEntryId() <= 0) {
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				_commerceChannelAccountEntryRelLocalService.
					fetchCommerceChannelAccountEntryRel(
						commerceOrder.getCommerceAccountId(),
						commerceChannel.getCommerceChannelId(),
						CommerceChannelAccountEntryRelConstants.
							TYPE_PAYMENT_TERM);

			if (commerceChannelAccountEntryRel != null) {
				CommerceTermEntry commerceTermEntry =
					_commerceTermEntryLocalService.fetchCommerceTermEntry(
						commerceChannelAccountEntryRel.getClassPK());

				if (commerceTermEntry != null) {
					commerceOrder.setPaymentCommerceTermEntryId(
						commerceTermEntry.getCommerceTermEntryId());
					commerceOrder.setPaymentCommerceTermEntryDescription(
						commerceTermEntry.getDescription(
							user.getLanguageId(), true));
					commerceOrder.setPaymentCommerceTermEntryName(
						commerceTermEntry.getLabel(user.getLanguageId(), true));
				}
			}
		}
	}

	private void _setShippingAddress(
			CommerceOrder commerceOrder, CommerceChannel commerceChannel)
		throws PortalException {

		if (commerceOrder.getShippingAddressId() <= 0) {
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
				_commerceChannelAccountEntryRelLocalService.
					fetchCommerceChannelAccountEntryRel(
						commerceOrder.getCommerceAccountId(),
						commerceChannel.getCommerceChannelId(),
						CommerceChannelAccountEntryRelConstants.
							TYPE_SHIPPING_ADDRESS);

			if (commerceChannelAccountEntryRel != null) {
				List<CommerceAddress> shippingCommerceAddresses =
					_commerceAddressLocalService.getShippingCommerceAddresses(
						commerceChannel.getCommerceChannelId(),
						AccountEntry.class.getName(),
						commerceOrder.getCommerceAccountId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS);

				CommerceAddress commerceAddress =
					_commerceAddressLocalService.getCommerceAddress(
						commerceChannelAccountEntryRel.getClassPK());

				if ((commerceAddress != null) &&
					shippingCommerceAddresses.contains(commerceAddress)) {

					commerceOrder.setShippingAddressId(
						commerceAddress.getCommerceAddressId());
				}
			}
		}
	}

	private void _setShippingOption(
			User user, CommerceOrder commerceOrder,
			CommerceChannel commerceChannel)
		throws PortalException {

		if ((commerceOrder.getCommerceShippingMethodId() > 0) &&
			Validator.isNotNull(commerceOrder.getShippingOptionName())) {

			return;
		}

		AccountEntry accountEntry = commerceOrder.getAccountEntry();

		if (accountEntry.isPersonalAccount()) {
			return;
		}

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodLocalService.getCommerceShippingMethods(
				commerceOrder.getGroupId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				CommerceShippingMethodPriorityComparator.getInstance(false));

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					fetchCommerceShippingOptionAccountEntryRel(
						accountEntry.getAccountEntryId(),
						commerceChannel.getCommerceChannelId());

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			CommerceShippingEngine commerceShippingEngine =
				_commerceShippingEngineRegistry.getCommerceShippingEngine(
					commerceShippingMethod.getEngineKey());

			CommerceContext commerceContext = _commerceContextFactory.create(
				accountEntry.getAccountEntryId(), commerceChannel.getGroupId(),
				commerceOrder.getCommerceCurrencyCode(),
				commerceOrder.getCommerceOrderId(),
				commerceOrder.getCompanyId());

			List<CommerceShippingOption> commerceShippingOptions =
				commerceShippingEngine.getEnabledCommerceShippingOptions(
					commerceContext, commerceOrder, user.getLocale());

			if (commerceShippingOptions.isEmpty()) {
				continue;
			}

			if (commerceShippingOptionAccountEntryRel != null) {
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
	}

	private boolean _transitionOrderStatusCompleted(CommerceOrder commerceOrder)
		throws PortalException {

		List<Long> supplierCommerceOrderIds =
			commerceOrder.getSupplierCommerceOrderIds();

		if (ListUtil.isEmpty(supplierCommerceOrderIds)) {
			return false;
		}

		Long firstSupplierCommerceOrderId = supplierCommerceOrderIds.get(0);

		CommerceOrder firstSupplierCommerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				firstSupplierCommerceOrderId);

		int orderStatus = firstSupplierCommerceOrder.getOrderStatus();

		if ((supplierCommerceOrderIds.size() == 1) &&
			(orderStatus != commerceOrder.getOrderStatus())) {

			return true;
		}

		for (int i = 1; i < supplierCommerceOrderIds.size(); i++) {
			CommerceOrder supplierCommerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					supplierCommerceOrderIds.get(i));

			if (orderStatus != supplierCommerceOrder.getOrderStatus()) {
				return false;
			}
		}

		return true;
	}

	private void _updateOrderStatus(
			CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
			CommerceOrder customerCommerceOrder)
		throws PortalException {

		int newOrderStatus = commerceOrder.getOrderStatus();
		int originalOrderStatus = originalCommerceOrder.getOrderStatus();

		if (originalOrderStatus != newOrderStatus) {
			_commerceOrderEngine.checkCommerceOrderShipmentStatus(
				customerCommerceOrder, false);

			if ((newOrderStatus ==
					CommerceOrderConstants.ORDER_STATUS_COMPLETED) &&
				_transitionOrderStatusCompleted(customerCommerceOrder)) {

				_commerceOrderEngine.transitionCommerceOrder(
					customerCommerceOrder, newOrderStatus, 0, false);
			}
		}
	}

	private boolean _updateShippingAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalShippingAmount =
			originalCommerceOrder.getShippingAmount();
		BigDecimal newShippingAmount = commerceOrder.getShippingAmount();

		int compareShippingAmount = originalShippingAmount.compareTo(
			newShippingAmount);

		if (compareShippingAmount != 0) {
			BigDecimal customerShippingAmount =
				customerCommerceOrder.getShippingAmount();

			BigDecimal subtractOriginalValue = customerShippingAmount.subtract(
				originalShippingAmount);

			customerCommerceOrder.setShippingAmount(
				subtractOriginalValue.add(newShippingAmount));

			return true;
		}

		return false;
	}

	private boolean _updateShippingDiscountAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalShippingDiscountAmount =
			originalCommerceOrder.getShippingDiscountAmount();
		BigDecimal newShippingDiscountAmount =
			commerceOrder.getShippingDiscountAmount();

		int compareShippingDiscountAmount =
			originalShippingDiscountAmount.compareTo(newShippingDiscountAmount);

		if (compareShippingDiscountAmount != 0) {
			BigDecimal customerShippingDiscountAmount =
				customerCommerceOrder.getShippingDiscountAmount();

			BigDecimal subtractOriginalValue =
				customerShippingDiscountAmount.subtract(
					originalShippingDiscountAmount);

			customerCommerceOrder.setShippingDiscountAmount(
				subtractOriginalValue.add(newShippingDiscountAmount));

			return true;
		}

		return false;
	}

	private boolean _updateSubtotal(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalSubtotal = originalCommerceOrder.getSubtotal();
		BigDecimal newSubtotal = commerceOrder.getSubtotal();

		int compareSubtotal = originalSubtotal.compareTo(newSubtotal);

		if (compareSubtotal != 0) {
			BigDecimal customerSubtotal = customerCommerceOrder.getSubtotal();

			BigDecimal subtractOriginalValue = customerSubtotal.subtract(
				originalSubtotal);

			customerCommerceOrder.setSubtotal(
				subtractOriginalValue.add(newSubtotal));

			return true;
		}

		return false;
	}

	private boolean _updateSubtotalDiscountAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalSubtotalDiscountAmount =
			originalCommerceOrder.getSubtotalDiscountAmount();

		BigDecimal newSubtotalDiscountAmount =
			commerceOrder.getSubtotalDiscountAmount();

		int compareSubtotalDiscountAmount =
			originalSubtotalDiscountAmount.compareTo(newSubtotalDiscountAmount);

		if (compareSubtotalDiscountAmount != 0) {
			BigDecimal customerSubtotalDiscountAmount =
				customerCommerceOrder.getSubtotalDiscountAmount();

			BigDecimal subtractOriginalValue =
				customerSubtotalDiscountAmount.subtract(
					originalSubtotalDiscountAmount);

			customerCommerceOrder.setSubtotalDiscountAmount(
				subtractOriginalValue.add(newSubtotalDiscountAmount));

			return true;
		}

		return false;
	}

	private boolean _updateTaxAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalTaxAmount = originalCommerceOrder.getTaxAmount();
		BigDecimal newTaxAmount = commerceOrder.getTaxAmount();

		int compareTaxAmount = originalTaxAmount.compareTo(newTaxAmount);

		if (compareTaxAmount != 0) {
			BigDecimal customerTaxAmount = customerCommerceOrder.getTaxAmount();

			BigDecimal subtractOriginalValue = customerTaxAmount.subtract(
				originalTaxAmount);

			customerCommerceOrder.setTaxAmount(
				subtractOriginalValue.add(newTaxAmount));

			return true;
		}

		return false;
	}

	private boolean _updateTotal(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalTotal = originalCommerceOrder.getTotal();
		BigDecimal newTotal = commerceOrder.getTotal();

		int compareTotal = originalTotal.compareTo(newTotal);

		if (compareTotal != 0) {
			BigDecimal customerTotal = customerCommerceOrder.getTotal();

			BigDecimal subtractOriginalValue = customerTotal.subtract(
				originalTotal);

			customerCommerceOrder.setTotal(subtractOriginalValue.add(newTotal));

			return true;
		}

		return false;
	}

	private boolean _updateTotalDiscountAmount(
		CommerceOrder originalCommerceOrder, CommerceOrder commerceOrder,
		CommerceOrder customerCommerceOrder) {

		BigDecimal originalTotalDiscountAmount =
			originalCommerceOrder.getTotalDiscountAmount();
		BigDecimal newTotalDiscountAmount =
			commerceOrder.getTotalDiscountAmount();

		int compareTotalDiscountAmount = originalTotalDiscountAmount.compareTo(
			newTotalDiscountAmount);

		if (compareTotalDiscountAmount != 0) {
			BigDecimal customerTotalDiscountAmount =
				customerCommerceOrder.getTotalDiscountAmount();

			BigDecimal subtractOriginalValue =
				customerTotalDiscountAmount.subtract(
					originalTotalDiscountAmount);

			customerCommerceOrder.setTotalDiscountAmount(
				subtractOriginalValue.add(newTotalDiscountAmount));

			return true;
		}

		return false;
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