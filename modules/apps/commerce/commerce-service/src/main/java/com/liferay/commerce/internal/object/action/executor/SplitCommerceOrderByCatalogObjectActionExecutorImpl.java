/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.action.executor;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.constants.CommerceObjectActionExecutorConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.type.constants.CommerceInventoryAuditTypeConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ObjectActionExecutor.class)
public class SplitCommerceOrderByCatalogObjectActionExecutorImpl
	implements ObjectActionExecutor, ObjectDefinitionScoped {

	@Override
	public void execute(
			long companyId, long objectActionId,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				payloadJSONObject.getLong("objectDefinitionId"));

		if (objectDefinition.isSystem() &&
			!FeatureFlagManagerUtil.isEnabled("COMMERCE-11026")) {

			throw new UnsupportedOperationException();
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				long commerceOrderId = payloadJSONObject.getLong("classPK");

				CommerceOrder customerCommerceOrder =
					_commerceOrderLocalService.getCommerceOrder(
						commerceOrderId);

				if (_isSplitted(customerCommerceOrder)) {
					return null;
				}

				Map<CommerceCatalog, List<CommerceOrderItem>>
					commerceOrderItemMap = _getCommerceOrderItemMap(
						customerCommerceOrder.getCommerceOrderItems());

				int numberCommerceOrder = commerceOrderItemMap.size();

				if (numberCommerceOrder > 1) {
					_createSupplierOrders(
						customerCommerceOrder, commerceOrderItemMap);
					_handleBookedQuantity(commerceOrderId);
				}

				return null;
			});
	}

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		if (FeatureFlagManagerUtil.isEnabled("COMMERCE-11026")) {
			return Arrays.asList("CommerceOrder");
		}

		return Arrays.asList("NonexistingObjectDefinition");
	}

	@Override
	public String getKey() {
		return CommerceObjectActionExecutorConstants.
			KEY_SPLIT_COMMERCE_ORDER_BY_CATALOG;
	}

	private void _addSupplierBookedQuantity(List<Long> supplierCommerceOrderIds)
		throws Exception {

		for (Long supplierCommerceOrderId : supplierCommerceOrderIds) {
			CommerceOrder supplierCommerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					supplierCommerceOrderId);

			List<CommerceOrderItem> supplierCommerceOrderItems =
				supplierCommerceOrder.getCommerceOrderItems();

			AccountEntry accountEntry = supplierCommerceOrder.getAccountEntry();

			for (CommerceOrderItem commerceOrderItem :
					supplierCommerceOrderItems) {

				CommerceInventoryBookedQuantity
					commerceInventoryBookedQuantity =
						_commerceInventoryBookedQuantityLocalService.
							addCommerceInventoryBookedQuantity(
								commerceOrderItem.getUserId(), null,
								commerceOrderItem.getQuantity(),
								commerceOrderItem.getSku(), StringPool.BLANK,
								HashMapBuilder.put(
									CommerceInventoryAuditTypeConstants.
										ACCOUNT_NAME,
									accountEntry.getName()
								).put(
									CommerceInventoryAuditTypeConstants.
										ORDER_ID,
									String.valueOf(
										commerceOrderItem.getCommerceOrderId())
								).put(
									CommerceInventoryAuditTypeConstants.
										ORDER_ITEM_ID,
									String.valueOf(
										commerceOrderItem.
											getCommerceOrderItemId())
								).build());

				_commerceOrderItemLocalService.updateCommerceOrderItem(
					commerceOrderItem.getCommerceOrderItemId(),
					commerceInventoryBookedQuantity.
						getCommerceInventoryBookedQuantityId());
			}
		}
	}

	private BigDecimal _calculateSubtotalDiscountAmount(
		CommerceOrder customerCommerceOrder, RoundingMode roundingMode,
		BigDecimal subtotal) {

		BigDecimal newSubtotalDiscountAmount = BigDecimal.ZERO;
		BigDecimal customerSubtotalDiscountAmount =
			customerCommerceOrder.getSubtotalDiscountAmount();

		if (customerSubtotalDiscountAmount.signum() > 0) {
			BigDecimal customerCommerceOrderSubtotal =
				customerCommerceOrder.getSubtotal();

			newSubtotalDiscountAmount = customerSubtotalDiscountAmount.multiply(
				subtotal);
			newSubtotalDiscountAmount = newSubtotalDiscountAmount.divide(
				customerCommerceOrderSubtotal, roundingMode);
		}

		return newSubtotalDiscountAmount;
	}

	private BigDecimal _calculateTotalDiscountAmount(
		CommerceOrder customerCommerceOrder, RoundingMode roundingMode,
		BigDecimal supplierTotal) {

		BigDecimal newTotalDiscountAmount = BigDecimal.ZERO;
		BigDecimal customerTotalDiscountAmount =
			customerCommerceOrder.getTotalDiscountAmount();

		if (customerTotalDiscountAmount.signum() > 0) {
			BigDecimal customerCommerceOrderTotal =
				customerCommerceOrder.getTotal();

			newTotalDiscountAmount = customerTotalDiscountAmount.multiply(
				supplierTotal);
			BigDecimal add = customerCommerceOrderTotal.add(
				customerTotalDiscountAmount);

			newTotalDiscountAmount = newTotalDiscountAmount.divide(
				add, roundingMode);
		}

		return newTotalDiscountAmount;
	}

	private void _createSupplierOrders(
			CommerceOrder customerCommerceOrder,
			Map<CommerceCatalog, List<CommerceOrderItem>> commerceOrderItemMap)
		throws Exception {

		CommerceCurrency commerceCurrency =
			customerCommerceOrder.getCommerceCurrency();

		RoundingMode roundingMode = RoundingMode.valueOf(
			commerceCurrency.getRoundingMode());

		commerceOrderItemMap.forEach(
			(commerceCatalog, commerceOrderItems) -> {
				CommerceOrder supplierCommerceOrder =
					customerCommerceOrder.cloneWithOriginalValues();

				supplierCommerceOrder.setUuid(_portalUUID.generate());
				supplierCommerceOrder.setExternalReferenceCode(
					_portalUUID.generate());

				long newCommerceOrderId = _counterLocalService.increment();

				supplierCommerceOrder.setCommerceOrderId(newCommerceOrderId);

				long accountEntryId = commerceCatalog.getAccountEntryId();

				CommerceChannel commerceChannel = null;

				if (accountEntryId > 0) {
					List<CommerceChannel> commerceChannels =
						_commerceChannelLocalService.
							getCommerceChannelsByAccountEntryId(accountEntryId);

					if (ListUtil.isNotEmpty(commerceChannels)) {
						commerceChannel = commerceChannels.get(0);
					}
				}

				if (commerceChannel != null) {
					supplierCommerceOrder.setGroupId(
						commerceChannel.getGroupId());
				}

				supplierCommerceOrder.setManuallyAdjusted(true);
				supplierCommerceOrder.setShippingAmount(BigDecimal.ZERO);
				supplierCommerceOrder.setShippingDiscountAmount(
					BigDecimal.ZERO);

				BigDecimal subtotal = BigDecimal.ZERO;
				BigDecimal taxAmount = BigDecimal.ZERO;

				for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
					CommerceOrderItem newCommerceOrderItem =
						commerceOrderItem.cloneWithOriginalValues();

					newCommerceOrderItem.setUuid(_portalUUID.generate());
					newCommerceOrderItem.setExternalReferenceCode(
						_portalUUID.generate());
					newCommerceOrderItem.setCommerceOrderItemId(
						_counterLocalService.increment());

					if (commerceChannel != null) {
						newCommerceOrderItem.setGroupId(
							commerceChannel.getGroupId());
					}

					newCommerceOrderItem.setCommerceInventoryBookedQuantityId(
						0);
					newCommerceOrderItem.setCommerceOrderId(newCommerceOrderId);
					newCommerceOrderItem.setCustomerCommerceOrderItemId(
						commerceOrderItem.getCommerceOrderItemId());
					newCommerceOrderItem.setParentCommerceOrderItemId(0);
					newCommerceOrderItem.setDiscountManuallyAdjusted(true);
					newCommerceOrderItem.setManuallyAdjusted(true);
					newCommerceOrderItem.setPriceManuallyAdjusted(true);
					newCommerceOrderItem.
						setUnitOfMeasureIncrementalOrderQuantity(
							commerceOrderItem.
								getUnitOfMeasureIncrementalOrderQuantity());
					newCommerceOrderItem.setUnitOfMeasureKey(
						commerceOrderItem.getUnitOfMeasureKey());

					_commerceOrderItemLocalService.addCommerceOrderItem(
						newCommerceOrderItem);

					BigDecimal finalPrice =
						newCommerceOrderItem.getFinalPrice();

					subtotal = subtotal.add(finalPrice);

					BigDecimal finalPriceWithTaxAmount =
						newCommerceOrderItem.getFinalPriceWithTaxAmount();

					BigDecimal tax = finalPriceWithTaxAmount.subtract(
						finalPrice);

					taxAmount = taxAmount.add(tax);
				}

				supplierCommerceOrder.setSubtotal(subtotal);

				BigDecimal newSubtotalDiscountAmount =
					_calculateSubtotalDiscountAmount(
						customerCommerceOrder, roundingMode, subtotal);

				supplierCommerceOrder.setSubtotalDiscountAmount(
					newSubtotalDiscountAmount);

				supplierCommerceOrder.setTaxAmount(taxAmount);

				BigDecimal supplierTotal = subtotal.add(taxAmount);

				BigDecimal newTotalDiscountAmount =
					_calculateTotalDiscountAmount(
						customerCommerceOrder, roundingMode, supplierTotal);

				supplierCommerceOrder.setTotalDiscountAmount(
					newTotalDiscountAmount);

				supplierTotal = supplierTotal.subtract(
					newSubtotalDiscountAmount);
				supplierTotal = supplierTotal.subtract(newTotalDiscountAmount);

				supplierCommerceOrder.setTotal(supplierTotal);

				_commerceOrderLocalService.addCommerceOrder(
					supplierCommerceOrder);
			});
	}

	private Map<CommerceCatalog, List<CommerceOrderItem>>
		_getCommerceOrderItemMap(List<CommerceOrderItem> commerceOrderItems) {

		Map<CommerceCatalog, List<CommerceOrderItem>> commerceOrderItemMap =
			new HashMap<>();

		ListUtil.isNotEmptyForEach(
			commerceOrderItems,
			commerceOrderItem -> {
				try {
					CPDefinition cpDefinition =
						commerceOrderItem.getCPDefinition();

					CommerceCatalog commerceCatalog =
						cpDefinition.getCommerceCatalog();

					if (commerceOrderItemMap.containsKey(commerceCatalog)) {
						List<CommerceOrderItem> splitCommerceOrderItems =
							commerceOrderItemMap.get(commerceCatalog);

						splitCommerceOrderItems.add(commerceOrderItem);

						commerceOrderItemMap.put(
							commerceCatalog, splitCommerceOrderItems);
					}
					else {
						commerceOrderItemMap.put(
							commerceCatalog,
							ListUtil.toList(commerceOrderItem));
					}
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			});

		return commerceOrderItemMap;
	}

	private void _handleBookedQuantity(long commerceOrderId) throws Exception {
		CommerceOrder customerCommerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		_releaseCustomerBookedQuantity(
			customerCommerceOrder.getCommerceOrderItems());

		_addSupplierBookedQuantity(
			customerCommerceOrder.getSupplierCommerceOrderIds());
	}

	private boolean _isSplitted(CommerceOrder customerCommerceOrder) {
		int supplierCommerceOrderIdsCount =
			customerCommerceOrder.getSupplierCommerceOrderIdsCount();

		int customerCommerceOrderIdsCount =
			customerCommerceOrder.getCustomerCommerceOrderIdsCount();

		if ((supplierCommerceOrderIdsCount > 0) ||
			(customerCommerceOrderIdsCount > 0)) {

			return true;
		}

		return false;
	}

	private void _releaseCustomerBookedQuantity(
			List<CommerceOrderItem> commerceOrderItems)
		throws Exception {

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			_commerceInventoryBookedQuantityLocalService.
				deleteCommerceInventoryBookedQuantity(
					commerceOrderItem.getCommerceInventoryBookedQuantityId());

			commerceOrderItem.setCommerceInventoryBookedQuantityId(0);

			_commerceOrderItemLocalService.updateCommerceOrderItem(
				commerceOrderItem);
		}
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private PortalUUID _portalUUID;

}