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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.configuration.CommerceOrderConfiguration;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.exception.CommerceOrderItemRequestedDeliveryDateException;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.exception.GuestCartItemMaxAllowedException;
import com.liferay.commerce.exception.NoSuchOrderItemException;
import com.liferay.commerce.exception.ProductBundleException;
import com.liferay.commerce.internal.search.CommerceOrderItemIndexer;
import com.liferay.commerce.internal.util.CommercePriceConverterUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.type.constants.CommerceInventoryAuditTypeConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceCalculationFactory;
import com.liferay.commerce.price.CommerceProductPriceImpl;
import com.liferay.commerce.price.CommerceProductPriceRequest;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.JsonHelper;
import com.liferay.commerce.service.base.CommerceOrderItemLocalServiceBaseImpl;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Ethan Bustad
 * @author Igor Beslic
 */
public class CommerceOrderItemLocalServiceImpl
	extends CommerceOrderItemLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem addCommerceOrderItem(
			long commerceOrderId, long cpInstanceId, int quantity,
			int shippedQuantity, String json, CommerceContext commerceContext,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(json)) {
			json = _getCPInstanceOptionValueRelsJSONString(cpInstanceId);
		}

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		updateWorkflow(commerceOrder, serviceContext);

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceOrderItem commerceOrderItem = _createCommerceOrderItem(
			commerceOrder.getGroupId(), user, commerceOrder, cpInstance, json,
			0, quantity, shippedQuantity, commerceContext, serviceContext);

		commerceOrderItem = commerceOrderItemPersistence.update(
			commerceOrderItem);

		List<CommerceOptionValue> commerceOptionValues =
			_commerceOptionValueHelper.getCPDefinitionCommerceOptionValues(
				cpInstance.getCPDefinitionId(), json);

		for (CommerceOptionValue commerceOptionValue : commerceOptionValues) {
			if (Validator.isNull(commerceOptionValue.getPriceType())) {
				continue;
			}

			if (_isStaticPriceType(commerceOptionValue.getPriceType()) &&
				(commerceOptionValue.getCPInstanceId() <= 0)) {

				continue;
			}

			CPInstance commerceOptionValueCPInstance =
				_cpInstanceLocalService.getCPInstance(
					commerceOptionValue.getCPInstanceId());

			int currentQuantity = quantity * commerceOptionValue.getQuantity();

			CommerceOrderItem childCommerceOrderItem = _createCommerceOrderItem(
				commerceOrder.getGroupId(), user, commerceOrder,
				commerceOptionValueCPInstance, commerceOptionValue.toJSON(),
				commerceOrderItem.getCommerceOrderItemId(), currentQuantity, 0,
				commerceContext, serviceContext);

			if (!_isStaticPriceType(commerceOptionValue.getPriceType())) {
				childCommerceOrderItem = commerceOrderItemPersistence.update(
					childCommerceOrderItem);

				continue;
			}

			CommerceProductPrice commerceProductPrice =
				_getStaticCommerceProductPrice(
					commerceOptionValue.getCPInstanceId(), currentQuantity,
					commerceOptionValue.getPrice(),
					childCommerceOrderItem.getCommerceOrder(),
					commerceContext.getCommerceCurrency());

			_setCommerceOrderItemPrice(
				childCommerceOrderItem, commerceProductPrice);

			_setCommerceOrderItemDiscountValue(
				childCommerceOrderItem, commerceProductPrice.getDiscountValue(),
				false);

			_setCommerceOrderItemDiscountValue(
				childCommerceOrderItem,
				commerceProductPrice.getDiscountValueWithTaxAmount(), true);

			commerceOrderItemPersistence.update(childCommerceOrderItem);
		}

		commerceOrderLocalService.recalculatePrice(
			commerceOrderItem.getCommerceOrderId(), commerceContext);

		return commerceOrderItem;
	}

	@Override
	public int countSubscriptionCommerceOrderItems(long commerceOrderId) {
		return commerceOrderItemPersistence.countByC_S(commerceOrderId, true);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrderItem deleteCommerceOrderItem(
			CommerceOrderItem commerceOrderItem)
		throws PortalException {

		validateParentCommerceOrderId(commerceOrderItem);

		return _deleteCommerceOrderItem(commerceOrderItem);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrderItem deleteCommerceOrderItem(
			CommerceOrderItem commerceOrderItem,
			CommerceContext commerceContext)
		throws PortalException {

		// Commerce order item

		commerceOrderItemLocalService.deleteCommerceOrderItem(
			commerceOrderItem);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		if (_commerceShippingHelper.isFreeShipping(commerceOrder)) {
			commerceOrderLocalService.updateShippingMethod(
				commerceOrder.getCommerceOrderId(), 0, null, BigDecimal.ZERO,
				commerceContext);
		}

		commerceOrderLocalService.recalculatePrice(
			commerceOrder.getCommerceOrderId(), commerceContext);

		return commerceOrderItem;
	}

	@Override
	public CommerceOrderItem deleteCommerceOrderItem(long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		return commerceOrderItemLocalService.deleteCommerceOrderItem(
			commerceOrderItem);
	}

	@Override
	public void deleteCommerceOrderItems(long commerceOrderId)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrderItemPersistence.findByCommerceOrderId(
				commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (commerceOrderItem.hasParentCommerceOrderItem()) {
				continue;
			}

			deleteCommerceOrderItem(commerceOrderItem);
		}
	}

	@Override
	public void deleteCommerceOrderItemsByCPInstanceId(long cpInstanceId)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrderItemPersistence.findByCPInstanceId(cpInstanceId);

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			deleteCommerceOrderItem(commerceOrderItem);
		}
	}

	@Override
	public CommerceOrderItem fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceOrderItemPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public CommerceOrderItem fetchCommerceOrderItemByBookedQuantityId(
		long bookedQuantityId) {

		return commerceOrderItemPersistence.fetchByBookedQuantityId(
			bookedQuantityId);
	}

	@Override
	public List<CommerceOrderItem> getAvailableForShipmentCommerceOrderItems(
		long commerceOrderId) {

		return commerceOrderItemFinder.findByAvailableQuantity(commerceOrderId);
	}

	@Override
	public List<CommerceOrderItem> getChildCommerceOrderItems(
		long parentCommerceOrderItemId) {

		return commerceOrderItemPersistence.findByParentCommerceOrderItemId(
			parentCommerceOrderItemId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemQuantity(
			long commerceOrderItemId, long commerceInventoryWarehouseId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, commerceOrderItem.getSku());

		if (commerceInventoryWarehouseItem == null) {
			return 0;
		}

		return commerceInventoryWarehouseItem.getQuantity();
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end) {

		return commerceOrderItemPersistence.findByCommerceOrderId(
			commerceOrderId, start, end);
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return commerceOrderItemPersistence.findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, long cpInstanceId, int start, int end) {

		return commerceOrderItemPersistence.findByC_I(
			commerceOrderId, cpInstanceId, start, end);
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, long cpInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return commerceOrderItemPersistence.findByC_I(
			commerceOrderId, cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceOrderItem> getCommerceOrderItems(
		long groupId, long commerceAccountId, int[] orderStatuses, int start,
		int end) {

		return commerceOrderItemFinder.findByG_A_O(
			groupId, commerceAccountId, orderStatuses, start, end);
	}

	@Override
	public int getCommerceOrderItemsCount(long commerceOrderId) {
		return commerceOrderItemPersistence.countByCommerceOrderId(
			commerceOrderId);
	}

	@Override
	public int getCommerceOrderItemsCount(
		long commerceOrderId, long cpInstanceId) {

		return commerceOrderItemPersistence.countByC_I(
			commerceOrderId, cpInstanceId);
	}

	@Override
	public int getCommerceOrderItemsCount(
		long groupId, long commerceAccountId, int[] orderStatuses) {

		return commerceOrderItemFinder.countByG_A_O(
			groupId, commerceAccountId, orderStatuses);
	}

	@Override
	public int getCommerceOrderItemsQuantity(long commerceOrderId) {
		return commerceOrderItemFinder.getCommerceOrderItemsQuantity(
			commerceOrderId);
	}

	@Override
	public List<CommerceOrderItem> getSubscriptionCommerceOrderItems(
		long commerceOrderId) {

		return commerceOrderItemPersistence.findByC_S(commerceOrderId, true);
	}

	@Override
	public CommerceOrderItem incrementShippedQuantity(
			long commerceOrderItemId, int shippedQuantity)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		shippedQuantity =
			commerceOrderItem.getShippedQuantity() + shippedQuantity;

		if (shippedQuantity < 0) {
			shippedQuantity = 0;
		}

		commerceOrderItem.setShippedQuantity(shippedQuantity);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Override
	public BaseModelSearchResult<CommerceOrderItem> searchCommerceOrderItems(
			long commerceOrderId, long parentCommerceOrderItemId,
			String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			commerceOrderId, parentCommerceOrderItemId, start, end, sort);

		searchContext.setKeywords(keywords);

		return searchCommerceOrderItems(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceOrderItem> searchCommerceOrderItems(
			long commerceOrderId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			commerceOrderId, null, start, end, sort);

		searchContext.setKeywords(keywords);

		return searchCommerceOrderItems(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceOrderItem> searchCommerceOrderItems(
			long commerceOrderId, String sku, String name, boolean andOperator,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			commerceOrderId, null, start, end, sort);

		searchContext.setAndSearch(andOperator);
		searchContext.setAttribute(CommerceOrderItemIndexer.FIELD_SKU, sku);
		searchContext.setAttribute(Field.NAME, name);

		return searchCommerceOrderItems(searchContext);
	}

	@Override
	public CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, int quantity,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		return commerceOrderItemLocalService.updateCommerceOrderItem(
			commerceOrderItemId, quantity, commerceOrderItem.getJson(),
			commerceContext, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, int quantity, String json,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		validateParentCommerceOrderId(commerceOrderItem);

		List<CommerceOrderItem> childCommerceOrderItems =
			commerceOrderItemPersistence.findByParentCommerceOrderItemId(
				commerceOrderItemId);

		if (childCommerceOrderItems.isEmpty()) {
			return _updateCommerceOrderItem(
				commerceOrderItemId, quantity, json, null, commerceContext,
				serviceContext);
		}

		List<CommerceOptionValue> commerceOptionValues =
			_commerceOptionValueHelper.toCommerceOptionValues(json);

		for (CommerceOrderItem childCommerceOrderItem :
				childCommerceOrderItems) {

			CommerceOptionValue commerceOptionValue =
				_commerceOptionValueHelper.toCommerceOptionValue(
					childCommerceOrderItem.getJson());

			CommerceOptionValue matchedCommerceOptionValue =
				commerceOptionValue.getFirstMatch(commerceOptionValues);

			if (matchedCommerceOptionValue == null) {
				throw new NoSuchOrderItemException(
					"Child commerce order item does not match any JSON item");
			}

			int currentQuantity = quantity * commerceOptionValue.getQuantity();

			if (!_isStaticPriceType(commerceOptionValue.getPriceType())) {
				_updateCommerceOrderItem(
					childCommerceOrderItem.getCommerceOrderItemId(),
					currentQuantity, childCommerceOrderItem.getJson(), null,
					commerceContext, serviceContext);

				continue;
			}

			CommerceProductPrice staticCommerceProductPrice =
				_getStaticCommerceProductPrice(
					commerceOptionValue.getCPInstanceId(), currentQuantity,
					commerceOptionValue.getPrice(),
					childCommerceOrderItem.getCommerceOrder(),
					commerceContext.getCommerceCurrency());

			_updateCommerceOrderItem(
				childCommerceOrderItem.getCommerceOrderItemId(),
				currentQuantity, childCommerceOrderItem.getJson(),
				staticCommerceProductPrice, commerceContext, serviceContext);
		}

		return _updateCommerceOrderItem(
			commerceOrderItemId, quantity, json, null, commerceContext,
			serviceContext);
	}

	@Override
	public CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, long bookedQuantityId)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		commerceOrderItem.setBookedQuantityId(bookedQuantityId);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCommerceOrderItemDeliveryDate(
			long commerceOrderItemId, Date requestedDeliveryDate)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		if ((requestedDeliveryDate != null) &&
			requestedDeliveryDate.before(new Date())) {

			throw new CommerceOrderItemRequestedDeliveryDateException();
		}

		commerceOrderItem.setRequestedDeliveryDate(requestedDeliveryDate);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCommerceOrderItemInfo(
			long commerceOrderItemId, String deliveryGroup,
			long shippingAddressId, String printedNote)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		commerceOrderItem.setDeliveryGroup(deliveryGroup);
		commerceOrderItem.setShippingAddressId(shippingAddressId);
		commerceOrderItem.setPrintedNote(printedNote);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCommerceOrderItemInfo(
			long commerceOrderItemId, String deliveryGroup,
			long shippingAddressId, String printedNote,
			int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
			int requestedDeliveryDateYear)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		Date requestedDeliveryDate = PortalUtil.getDate(
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear);

		if ((requestedDeliveryDate != null) &&
			requestedDeliveryDate.before(new Date())) {

			throw new CommerceOrderItemRequestedDeliveryDateException();
		}

		commerceOrderItem.setDeliveryGroup(deliveryGroup);
		commerceOrderItem.setShippingAddressId(shippingAddressId);
		commerceOrderItem.setPrintedNote(printedNote);
		commerceOrderItem.setRequestedDeliveryDate(requestedDeliveryDate);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceOrderItem updateCommerceOrderItemInfo(
			long commerceOrderItemId, String deliveryGroup,
			long shippingAddressId, String printedNote,
			int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
			int requestedDeliveryDateYear, int requestedDeliveryDateHour,
			int requestedDeliveryDateMinute, ServiceContext serviceContext)
		throws PortalException {

		return commerceOrderItemLocalService.updateCommerceOrderItemInfo(
			commerceOrderItemId, deliveryGroup, shippingAddressId, printedNote,
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCommerceOrderItemPrice(
			long commerceOrderItemId, CommerceContext commerceContext)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		if (commerceOrderItem.isManuallyAdjusted() ||
			(commerceOrderItem.getParentCommerceOrderItemId() != 0)) {

			return commerceOrderItem;
		}

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if (cpInstance == null) {
			return commerceOrderItem;
		}

		List<CommerceOrderItem> childCommerceOrderItems =
			commerceOrderItemPersistence.findByParentCommerceOrderItemId(
				commerceOrderItemId);

		for (CommerceOrderItem childCommerceOrderItem :
				childCommerceOrderItems) {

			CommerceOptionValue commerceOptionValue =
				_commerceOptionValueHelper.toCommerceOptionValue(
					childCommerceOrderItem.getJson());

			if (!_isStaticPriceType(commerceOptionValue.getPriceType())) {
				_setCommerceOrderItemPrice(
					childCommerceOrderItem, null, commerceContext);
			}
			else {
				_setCommerceOrderItemPrice(
					childCommerceOrderItem,
					_getStaticCommerceProductPrice(
						commerceOptionValue.getCPInstanceId(),
						childCommerceOrderItem.getQuantity(),
						commerceOptionValue.getPrice(),
						childCommerceOrderItem.getCommerceOrder(),
						commerceContext.getCommerceCurrency()),
					commerceContext);
			}

			commerceOrderItemPersistence.update(childCommerceOrderItem);
		}

		_setCommerceOrderItemPrice(commerceOrderItem, null, commerceContext);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCommerceOrderItemPrices(
			long commerceOrderItemId, BigDecimal unitPrice,
			BigDecimal promoPrice, BigDecimal discountAmount,
			BigDecimal finalPrice, BigDecimal discountPercentageLevel1,
			BigDecimal discountPercentageLevel2,
			BigDecimal discountPercentageLevel3,
			BigDecimal discountPercentageLevel4)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		validateParentCommerceOrderId(commerceOrderItem);

		commerceOrderItem.setUnitPrice(unitPrice);
		commerceOrderItem.setPromoPrice(promoPrice);
		commerceOrderItem.setDiscountAmount(discountAmount);
		commerceOrderItem.setFinalPrice(finalPrice);
		commerceOrderItem.setDiscountPercentageLevel1(discountPercentageLevel1);
		commerceOrderItem.setDiscountPercentageLevel2(discountPercentageLevel2);
		commerceOrderItem.setDiscountPercentageLevel3(discountPercentageLevel3);
		commerceOrderItem.setDiscountPercentageLevel4(discountPercentageLevel4);
		commerceOrderItem.setManuallyAdjusted(true);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCommerceOrderItemPrices(
			long commerceOrderItemId, BigDecimal unitPrice,
			BigDecimal promoPrice, BigDecimal discountAmount,
			BigDecimal finalPrice, BigDecimal discountPercentageLevel1,
			BigDecimal discountPercentageLevel2,
			BigDecimal discountPercentageLevel3,
			BigDecimal discountPercentageLevel4,
			BigDecimal unitPriceWithTaxAmount,
			BigDecimal promoPriceWithTaxAmount,
			BigDecimal discountAmountWithTaxAmount,
			BigDecimal finalPriceWithTaxAmount,
			BigDecimal discountPercentageLevel1WithTaxAmount,
			BigDecimal discountPercentageLevel2WithTaxAmount,
			BigDecimal discountPercentageLevel3WithTaxAmount,
			BigDecimal discountPercentageLevel4WithTaxAmount)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		commerceOrderItem.setUnitPrice(unitPrice);
		commerceOrderItem.setPromoPrice(promoPrice);
		commerceOrderItem.setDiscountAmount(discountAmount);
		commerceOrderItem.setFinalPrice(finalPrice);
		commerceOrderItem.setDiscountPercentageLevel1(discountPercentageLevel1);
		commerceOrderItem.setDiscountPercentageLevel2(discountPercentageLevel2);
		commerceOrderItem.setDiscountPercentageLevel3(discountPercentageLevel3);
		commerceOrderItem.setDiscountPercentageLevel4(discountPercentageLevel4);
		commerceOrderItem.setUnitPriceWithTaxAmount(unitPriceWithTaxAmount);
		commerceOrderItem.setPromoPriceWithTaxAmount(promoPriceWithTaxAmount);
		commerceOrderItem.setDiscountWithTaxAmount(discountAmountWithTaxAmount);
		commerceOrderItem.setFinalPriceWithTaxAmount(finalPriceWithTaxAmount);
		commerceOrderItem.setDiscountPercentageLevel1WithTaxAmount(
			discountPercentageLevel1WithTaxAmount);
		commerceOrderItem.setDiscountPercentageLevel2WithTaxAmount(
			discountPercentageLevel2WithTaxAmount);
		commerceOrderItem.setDiscountPercentageLevel3WithTaxAmount(
			discountPercentageLevel3WithTaxAmount);
		commerceOrderItem.setDiscountPercentageLevel4WithTaxAmount(
			discountPercentageLevel4WithTaxAmount);
		commerceOrderItem.setManuallyAdjusted(true);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceOrderItem updateCommerceOrderItemUnitPrice(
			long commerceOrderItemId, BigDecimal unitPrice)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		commerceOrderItem.setUnitPrice(unitPrice);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Override
	public CommerceOrderItem updateCommerceOrderItemUnitPrice(
			long userId, long commerceOrderItemId, BigDecimal unitPrice,
			int quantity)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		_updateBookedQuantity(
			userId, commerceOrderItem, commerceOrderItem.getBookedQuantityId(),
			quantity, commerceOrderItem.getQuantity());

		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setUnitPrice(unitPrice);
		commerceOrderItem.setManuallyAdjusted(true);

		return commerceOrderItemPersistence.update(commerceOrderItem);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCustomFields(
			long commerceOrderItemId, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItemId);

		commerceOrderItem.setExpandoBridgeAttributes(serviceContext);

		return commerceOrderItemLocalService.updateCommerceOrderItem(
			commerceOrderItem);
	}

	@Override
	public CommerceOrderItem upsertCommerceOrderItem(
			long commerceOrderId, long cpInstanceId, int quantity,
			int shippedQuantity, CommerceContext commerceContext,
			ServiceContext serviceContext)
		throws PortalException {

		String cpInstanceOptionValueRelJSONString =
			_getCPInstanceOptionValueRelsJSONString(cpInstanceId);

		return commerceOrderItemLocalService.upsertCommerceOrderItem(
			commerceOrderId, cpInstanceId, quantity, 0,
			cpInstanceOptionValueRelJSONString, commerceContext,
			serviceContext);
	}

	@Override
	public CommerceOrderItem upsertCommerceOrderItem(
			long commerceOrderId, long cpInstanceId, int quantity,
			int shippedQuantity, String json, CommerceContext commerceContext,
			ServiceContext serviceContext)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems = getCommerceOrderItems(
			commerceOrderId, cpInstanceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if ((commerceOrderItem.getParentCommerceOrderItemId() == 0) &&
				_jsonMatches(json, commerceOrderItem.getJson())) {

				return commerceOrderItemLocalService.updateCommerceOrderItem(
					commerceOrderItem.getCommerceOrderItemId(),
					commerceOrderItem.getQuantity() + quantity,
					commerceOrderItem.getJson(), commerceContext,
					serviceContext);
			}
		}

		return commerceOrderItemLocalService.addCommerceOrderItem(
			commerceOrderId, cpInstanceId, quantity, 0, json, commerceContext,
			serviceContext);
	}

	protected SearchContext buildSearchContext(
			long commerceOrderId, Long parentCommerceOrderItemId, int start,
			int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		searchContext.setAttribute(
			CommerceOrderItemIndexer.FIELD_COMMERCE_ORDER_ID, commerceOrderId);

		if (parentCommerceOrderItemId != null) {
			searchContext.setAttribute(
				CommerceOrderItemIndexer.FIELD_PARENT_COMMERCE_ORDER_ITEM_ID,
				parentCommerceOrderItemId);
		}

		searchContext.setCompanyId(commerceOrder.getCompanyId());
		searchContext.setEnd(end);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<CommerceOrderItem> getCommerceOrderItems(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceOrderItem> commerceOrderItems = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commerceOrderItemId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceOrderItem commerceOrderItem = fetchCommerceOrderItem(
				commerceOrderItemId);

			if (commerceOrderItem == null) {
				commerceOrderItems = null;

				Indexer<CommerceOrderItem> indexer =
					IndexerRegistryUtil.getIndexer(CommerceOrderItem.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceOrderItems != null) {
				commerceOrderItems.add(commerceOrderItem);
			}
		}

		return commerceOrderItems;
	}

	protected BaseModelSearchResult<CommerceOrderItem> searchCommerceOrderItems(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceOrderItem> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceOrderItem.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceOrderItem> commerceOrderItems = getCommerceOrderItems(
				hits);

			if (commerceOrderItems != null) {
				return new BaseModelSearchResult<>(
					commerceOrderItems, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void updateWorkflow(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws PortalException {

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				commerceOrder.getCompanyId(), commerceOrder.getGroupId(),
				CommerceOrder.class.getName(), 0,
				CommerceOrderConstants.TYPE_PK_APPROVAL, true);

		if ((workflowDefinitionLink != null) && commerceOrder.isApproved()) {
			commerceOrderLocalService.updateStatus(
				serviceContext.getUserId(), commerceOrder.getCommerceOrderId(),
				WorkflowConstants.STATUS_DRAFT, serviceContext,
				Collections.emptyMap());
		}
	}

	protected void validate(
			Locale locale, CommerceOrder commerceOrder,
			CPDefinition cpDefinition, CPInstance cpInstance, int quantity)
		throws PortalException {

		if (commerceOrder.getUserId() == 0) {
			int count = commerceOrderItemPersistence.countByCommerceOrderId(
				commerceOrder.getCommerceOrderId());

			if (count >=
					_commerceOrderConfiguration.guestCartItemMaxAllowed()) {

				throw new GuestCartItemMaxAllowedException();
			}
		}

		if ((cpDefinition != null) && (cpInstance != null) &&
			(cpDefinition.getCPDefinitionId() !=
				cpInstance.getCPDefinitionId())) {

			throw new NoSuchCPInstanceException(
				StringBundler.concat(
					"CPInstance ", cpInstance.getCPInstanceId(),
					" belongs to a different CPDefinition than ",
					cpDefinition.getCPDefinitionId()));
		}

		if (!ExportImportThreadLocal.isImportInProcess()) {
			List<CommerceOrderValidatorResult> commerceCartValidatorResults =
				_commerceOrderValidatorRegistry.validate(
					locale, commerceOrder, cpInstance, quantity);

			if (!commerceCartValidatorResults.isEmpty()) {
				throw new CommerceOrderValidatorException(
					commerceCartValidatorResults);
			}
		}
	}

	protected void validateParentCommerceOrderId(
			CommerceOrderItem commerceOrderItem)
		throws PortalException {

		if (commerceOrderItem.getParentCommerceOrderItemId() != 0) {
			throw new ProductBundleException(
				StringBundler.concat(
					"Operation not allowed on an item ",
					commerceOrderItem.getCommerceOrderItemId(),
					" because it is a child commerce order item ",
					commerceOrderItem.getParentCommerceOrderItemId()));
		}
	}

	private CommerceOrderItem _createCommerceOrderItem(
			long groupId, User user, CommerceOrder commerceOrder,
			CPInstance cpInstance, String json, long parentCommerceOrderItemId,
			int quantity, int shippedQuantity, CommerceContext commerceContext,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpInstance.getCPDefinitionId());

		validate(
			serviceContext.getLocale(), commerceOrder, cpDefinition, cpInstance,
			quantity);

		long commerceOrderItemId = counterLocalService.increment();

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.create(commerceOrderItemId);

		commerceOrderItem.setGroupId(groupId);
		commerceOrderItem.setCompanyId(user.getCompanyId());
		commerceOrderItem.setUserId(user.getUserId());
		commerceOrderItem.setUserName(user.getFullName());
		commerceOrderItem.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());
		commerceOrderItem.setCProductId(cpDefinition.getCProductId());
		commerceOrderItem.setCPInstanceId(cpInstance.getCPInstanceId());
		commerceOrderItem.setParentCommerceOrderItemId(
			parentCommerceOrderItemId);
		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setShippedQuantity(shippedQuantity);
		commerceOrderItem.setJson(json);

		CommerceProductPrice commerceProductPrice = _getCommerceProductPrice(
			cpInstance.getCPDefinitionId(), cpInstance.getCPInstanceId(), json,
			quantity, commerceContext);

		_setCommerceOrderItemPrice(commerceOrderItem, commerceProductPrice);

		commerceOrderItem.setNameMap(cpDefinition.getNameMap());
		commerceOrderItem.setSku(cpInstance.getSku());
		commerceOrderItem.setManuallyAdjusted(false);
		commerceOrderItem.setExpandoBridgeAttributes(serviceContext);

		_setCommerceOrderItemDiscountValue(
			commerceOrderItem, commerceProductPrice.getDiscountValue(), false);

		_setCommerceOrderItemDiscountValue(
			commerceOrderItem,
			commerceProductPrice.getDiscountValueWithTaxAmount(), true);

		commerceOrderItem.setSubscription(_isSubscription(cpInstance));

		_setSubscriptionInfo(commerceOrderItem, cpInstance);

		commerceOrderItem.setShippable(cpDefinition.isShippable());
		commerceOrderItem.setFreeShipping(cpDefinition.isFreeShipping());
		commerceOrderItem.setShipSeparately(cpDefinition.isShipSeparately());
		commerceOrderItem.setShippingExtraPrice(
			cpDefinition.getShippingExtraPrice());

		_setDimensions(commerceOrderItem, cpInstance);

		return commerceOrderItem;
	}

	private void _deleteBundleChildrenOrderItems(long commerceOrderItemId)
		throws PortalException {

		List<CommerceOrderItem> childCommerceOrderItems =
			commerceOrderItemPersistence.findByParentCommerceOrderItemId(
				commerceOrderItemId);

		for (CommerceOrderItem childCommerceOrderItem :
				childCommerceOrderItems) {

			childCommerceOrderItem.setParentCommerceOrderItemId(0);

			commerceOrderItemLocalService.deleteCommerceOrderItem(
				childCommerceOrderItem);
		}
	}

	private CommerceOrderItem _deleteCommerceOrderItem(
			CommerceOrderItem commerceOrderItem)
		throws PortalException {

		// Bundle order items

		_deleteBundleChildrenOrderItems(
			commerceOrderItem.getCommerceOrderItemId());

		// Commerce order item

		commerceOrderItemPersistence.remove(commerceOrderItem);

		// Booked quantities

		if (commerceOrderItem.getBookedQuantityId() > 0) {
			CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
				_commerceInventoryBookedQuantityLocalService.
					fetchCommerceInventoryBookedQuantity(
						commerceOrderItem.getBookedQuantityId());

			if (commerceInventoryBookedQuantity != null) {
				_commerceInventoryBookedQuantityLocalService.
					deleteCommerceInventoryBookedQuantity(
						commerceInventoryBookedQuantity);
			}
		}

		// Expando

		expandoRowLocalService.deleteRows(
			commerceOrderItem.getCommerceOrderItemId());

		updateWorkflow(
			commerceOrderItem.getCommerceOrder(),
			ServiceContextThreadLocal.getServiceContext());

		return commerceOrderItem;
	}

	private CommerceProductPrice _getCommerceProductPrice(
			long cpDefinitionId, long cpInstanceId, String json, int quantity,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceProductPriceCalculation commerceProductPriceCalculation =
			_commerceProductPriceCalculationFactory.
				getCommerceProductPriceCalculation();

		CommerceProductPriceRequest commerceProductPriceRequest =
			new CommerceProductPriceRequest();

		commerceProductPriceRequest.setCpInstanceId(cpInstanceId);
		commerceProductPriceRequest.setQuantity(quantity);
		commerceProductPriceRequest.setSecure(false);
		commerceProductPriceRequest.setCommerceContext(commerceContext);
		commerceProductPriceRequest.setCommerceOptionValues(
			_getStaticOptionValuesNotLinkedToSku(cpDefinitionId, json));
		commerceProductPriceRequest.setCalculateTax(true);

		return commerceProductPriceCalculation.getCommerceProductPrice(
			commerceProductPriceRequest);
	}

	private BigDecimal _getConvertedPrice(
			long cpInstanceId, BigDecimal price, CommerceOrder commerceOrder)
		throws PortalException {

		return CommercePriceConverterUtil.getConvertedPrice(
			commerceOrder.getGroupId(), cpInstanceId,
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(), price, false,
			_commerceTaxCalculation);
	}

	private String _getCPInstanceOptionValueRelsJSONString(long cpInstanceId)
		throws PortalException {

		JSONArray jsonArray = _jsonHelper.toJSONArray(
			_cpDefinitionOptionRelLocalService.
				getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
					cpInstanceId));

		return jsonArray.toString();
	}

	private CommerceProductPrice _getStaticCommerceProductPrice(
			long cpInstanceId, int quantity, BigDecimal optionValuePrice,
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency)
		throws PortalException {

		CommerceProductPriceImpl commerceProductPriceImpl =
			new CommerceProductPriceImpl();

		if (optionValuePrice == null) {
			optionValuePrice = BigDecimal.ZERO;
		}

		commerceProductPriceImpl.setUnitPrice(
			_commerceMoneyFactory.create(commerceCurrency, optionValuePrice));

		commerceProductPriceImpl.setUnitPromoPrice(
			_commerceMoneyFactory.create(commerceCurrency, BigDecimal.ZERO));
		commerceProductPriceImpl.setUnitPromoPriceWithTaxAmount(
			_commerceMoneyFactory.create(commerceCurrency, BigDecimal.ZERO));

		BigDecimal unitPriceWithTaxAmount = optionValuePrice;

		BigDecimal finalPriceWithTaxAmount = optionValuePrice;

		if (cpInstanceId > 0) {
			unitPriceWithTaxAmount = _getConvertedPrice(
				cpInstanceId, optionValuePrice, commerceOrder);

			optionValuePrice = optionValuePrice.multiply(
				BigDecimal.valueOf(quantity));

			finalPriceWithTaxAmount = _getConvertedPrice(
				cpInstanceId, optionValuePrice, commerceOrder);
		}

		commerceProductPriceImpl.setUnitPriceWithTaxAmount(
			_commerceMoneyFactory.create(
				commerceCurrency, unitPriceWithTaxAmount));

		commerceProductPriceImpl.setFinalPrice(
			_commerceMoneyFactory.create(commerceCurrency, optionValuePrice));

		commerceProductPriceImpl.setFinalPriceWithTaxAmount(
			_commerceMoneyFactory.create(
				commerceCurrency, finalPriceWithTaxAmount));

		commerceProductPriceImpl.setCommerceDiscountValue(null);
		commerceProductPriceImpl.setQuantity(quantity);

		return commerceProductPriceImpl;
	}

	private List<CommerceOptionValue> _getStaticOptionValuesNotLinkedToSku(
			long cpDefinitionId, String jsonArrayString)
		throws PortalException {

		List<CommerceOptionValue> commerceOptionValues =
			_commerceOptionValueHelper.getCPDefinitionCommerceOptionValues(
				cpDefinitionId, jsonArrayString);

		Stream<CommerceOptionValue> commerceOptionValuesStream =
			commerceOptionValues.stream();

		Stream<CommerceOptionValue> commerceOptionValuesFiltered =
			commerceOptionValuesStream.filter(
				commerceOptionValue ->
					_isStaticPriceType(commerceOptionValue.getPriceType()) &&
					(commerceOptionValue.getCPInstanceId() == 0));

		return commerceOptionValuesFiltered.collect(Collectors.toList());
	}

	private boolean _isStaticPriceType(Object value) {
		if (Objects.equals(
				value, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC)) {

			return true;
		}

		return false;
	}

	private boolean _isSubscription(CPInstance cpInstance)
		throws PortalException {

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if (cpDefinition.isSubscriptionEnabled() ||
			cpDefinition.isDeliverySubscriptionEnabled()) {

			return true;
		}

		if (cpInstance.isOverrideSubscriptionInfo() &&
			(cpInstance.isSubscriptionEnabled() ||
			 cpInstance.isDeliverySubscriptionEnabled())) {

			return true;
		}

		return false;
	}

	private boolean _jsonMatches(String json1, String json2) {
		if (_jsonHelper.isEmpty(json1) && _jsonHelper.isEmpty(json2)) {
			return true;
		}

		if (_jsonHelper.equals(json1, json2)) {
			return true;
		}

		return false;
	}

	private void _setCommerceOrderItemDiscountValue(
		CommerceOrderItem commerceOrderItem,
		CommerceDiscountValue commerceDiscountValue, boolean includeTax) {

		BigDecimal discountAmount = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel1 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel2 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel3 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel4 = BigDecimal.ZERO;

		if (commerceDiscountValue != null) {
			CommerceMoney discountAmountCommerceMoney =
				commerceDiscountValue.getDiscountAmount();

			discountAmount = discountAmountCommerceMoney.getPrice();

			BigDecimal[] percentages = commerceDiscountValue.getPercentages();

			if ((percentages.length >= 1) && (percentages[0] != null)) {
				discountPercentageLevel1 = percentages[0];
			}

			if ((percentages.length >= 2) && (percentages[1] != null)) {
				discountPercentageLevel2 = percentages[1];
			}

			if ((percentages.length >= 3) && (percentages[2] != null)) {
				discountPercentageLevel3 = percentages[2];
			}

			if ((percentages.length >= 4) && (percentages[3] != null)) {
				discountPercentageLevel4 = percentages[3];
			}
		}

		if (includeTax) {
			commerceOrderItem.setDiscountWithTaxAmount(discountAmount);
			commerceOrderItem.setDiscountPercentageLevel1WithTaxAmount(
				discountPercentageLevel1);
			commerceOrderItem.setDiscountPercentageLevel2WithTaxAmount(
				discountPercentageLevel2);
			commerceOrderItem.setDiscountPercentageLevel3WithTaxAmount(
				discountPercentageLevel3);
			commerceOrderItem.setDiscountPercentageLevel4WithTaxAmount(
				discountPercentageLevel4);
		}
		else {
			commerceOrderItem.setDiscountAmount(discountAmount);
			commerceOrderItem.setDiscountPercentageLevel1(
				discountPercentageLevel1);
			commerceOrderItem.setDiscountPercentageLevel2(
				discountPercentageLevel2);
			commerceOrderItem.setDiscountPercentageLevel3(
				discountPercentageLevel3);
			commerceOrderItem.setDiscountPercentageLevel4(
				discountPercentageLevel4);
		}
	}

	private void _setCommerceOrderItemPrice(
		CommerceOrderItem commerceOrderItem,
		CommerceProductPrice commerceProductPrice) {

		CommerceMoney unitPriceCommerceMoney =
			commerceProductPrice.getUnitPrice();

		commerceOrderItem.setUnitPrice(unitPriceCommerceMoney.getPrice());

		BigDecimal promoPrice = BigDecimal.ZERO;
		BigDecimal promoPriceWithTaxAmount = BigDecimal.ZERO;

		CommerceMoney unitPromoPriceCommerceMoney =
			commerceProductPrice.getUnitPromoPrice();

		if (!unitPromoPriceCommerceMoney.isEmpty()) {
			promoPrice = unitPromoPriceCommerceMoney.getPrice();
		}

		CommerceMoney unitPromoPriceWithTaxAmountCommerceMoney =
			commerceProductPrice.getUnitPromoPriceWithTaxAmount();

		if (!unitPromoPriceWithTaxAmountCommerceMoney.isEmpty()) {
			promoPriceWithTaxAmount =
				unitPromoPriceWithTaxAmountCommerceMoney.getPrice();
		}

		commerceOrderItem.setPromoPrice(promoPrice);
		commerceOrderItem.setPromoPriceWithTaxAmount(promoPriceWithTaxAmount);

		CommerceMoney finalPriceCommerceMoney =
			commerceProductPrice.getFinalPrice();

		commerceOrderItem.setFinalPrice(finalPriceCommerceMoney.getPrice());

		CommerceMoney unitPriceWithTaxAmountCommerceMoney =
			commerceProductPrice.getUnitPriceWithTaxAmount();

		if (unitPriceWithTaxAmountCommerceMoney != null) {
			commerceOrderItem.setUnitPriceWithTaxAmount(
				unitPriceWithTaxAmountCommerceMoney.getPrice());
		}

		CommerceMoney finalPriceWithTaxAmountCommerceMoney =
			commerceProductPrice.getFinalPriceWithTaxAmount();

		if (finalPriceWithTaxAmountCommerceMoney != null) {
			commerceOrderItem.setFinalPriceWithTaxAmount(
				finalPriceWithTaxAmountCommerceMoney.getPrice());
		}

		commerceOrderItem.setCommercePriceListId(
			commerceProductPrice.getCommercePriceListId());
	}

	private void _setCommerceOrderItemPrice(
			CommerceOrderItem commerceOrderItem,
			CommerceProductPrice commerceProductPrice,
			CommerceContext commerceContext)
		throws PortalException {

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if ((cpInstance == null) || commerceOrderItem.isManuallyAdjusted()) {
			return;
		}

		if (commerceProductPrice == null) {
			commerceProductPrice = _getCommerceProductPrice(
				commerceOrderItem.getCPDefinitionId(),
				commerceOrderItem.getCPInstanceId(),
				commerceOrderItem.getJson(), commerceOrderItem.getQuantity(),
				commerceContext);
		}

		_setCommerceOrderItemPrice(commerceOrderItem, commerceProductPrice);

		_setCommerceOrderItemDiscountValue(
			commerceOrderItem, commerceProductPrice.getDiscountValue(), false);

		_setCommerceOrderItemDiscountValue(
			commerceOrderItem,
			commerceProductPrice.getDiscountValueWithTaxAmount(), true);
	}

	private void _setDimensions(
			CommerceOrderItem commerceOrderItem, CPInstance cpInstance)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpInstance.getCPDefinitionId());

		commerceOrderItem.setWidth(cpInstance.getWidth());

		if (commerceOrderItem.getWidth() <= 0) {
			commerceOrderItem.setWidth(cpDefinition.getWidth());
		}

		commerceOrderItem.setHeight(cpInstance.getHeight());

		if (commerceOrderItem.getHeight() <= 0) {
			commerceOrderItem.setWidth(cpDefinition.getHeight());
		}

		commerceOrderItem.setDepth(cpInstance.getDepth());

		if (commerceOrderItem.getDepth() <= 0) {
			commerceOrderItem.setWidth(cpDefinition.getDepth());
		}

		commerceOrderItem.setWeight(cpInstance.getWeight());

		if (commerceOrderItem.getWeight() <= 0) {
			commerceOrderItem.setWidth(cpDefinition.getWeight());
		}
	}

	private void _setSubscriptionInfo(
			CommerceOrderItem commerceOrderItem, CPInstance cpInstance)
		throws PortalException {

		if (cpInstance.isOverrideSubscriptionInfo()) {
			commerceOrderItem.setSubscriptionLength(
				cpInstance.getSubscriptionLength());
			commerceOrderItem.setSubscriptionType(
				cpInstance.getSubscriptionType());
			commerceOrderItem.setSubscriptionTypeSettings(
				cpInstance.getSubscriptionTypeSettings());
			commerceOrderItem.setMaxSubscriptionCycles(
				cpInstance.getMaxSubscriptionCycles());
			commerceOrderItem.setDeliverySubscriptionLength(
				cpInstance.getDeliverySubscriptionLength());
			commerceOrderItem.setDeliverySubscriptionType(
				cpInstance.getDeliverySubscriptionType());
			commerceOrderItem.setDeliverySubscriptionTypeSettings(
				cpInstance.getDeliverySubscriptionTypeSettings());
			commerceOrderItem.setDeliveryMaxSubscriptionCycles(
				cpInstance.getDeliveryMaxSubscriptionCycles());
		}
		else {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(
					cpInstance.getCPDefinitionId());

			commerceOrderItem.setSubscriptionLength(
				cpDefinition.getSubscriptionLength());
			commerceOrderItem.setSubscriptionType(
				cpDefinition.getSubscriptionType());
			commerceOrderItem.setSubscriptionTypeSettings(
				cpDefinition.getSubscriptionTypeSettings());
			commerceOrderItem.setMaxSubscriptionCycles(
				cpDefinition.getMaxSubscriptionCycles());
			commerceOrderItem.setDeliverySubscriptionLength(
				cpDefinition.getDeliverySubscriptionLength());
			commerceOrderItem.setDeliverySubscriptionType(
				cpDefinition.getDeliverySubscriptionType());
			commerceOrderItem.setDeliverySubscriptionTypeSettings(
				cpDefinition.getDeliverySubscriptionTypeSettings());
			commerceOrderItem.setDeliveryMaxSubscriptionCycles(
				cpDefinition.getDeliveryMaxSubscriptionCycles());
		}
	}

	private void _updateBookedQuantity(
			long userId, CommerceOrderItem commerceOrderItem,
			long bookedQuantityId, int quantity, int oldQuantity)
		throws PortalException {

		if ((oldQuantity != quantity) && (bookedQuantityId > 0)) {
			CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
				_commerceInventoryBookedQuantityLocalService.
					fetchCommerceInventoryBookedQuantity(bookedQuantityId);

			if (commerceInventoryBookedQuantity != null) {
				_commerceInventoryBookedQuantityLocalService.
					updateCommerceInventoryBookedQuantity(
						userId,
						commerceInventoryBookedQuantity.
							getCommerceInventoryBookedQuantityId(),
						quantity,
						HashMapBuilder.put(
							CommerceInventoryAuditTypeConstants.ORDER_ID,
							String.valueOf(
								commerceOrderItem.getCommerceOrderId())
						).put(
							CommerceInventoryAuditTypeConstants.ORDER_ITEM_ID,
							String.valueOf(
								commerceOrderItem.getCommerceOrderItemId())
						).build(),
						commerceInventoryBookedQuantity.getMvccVersion());
			}
		}
	}

	private CommerceOrderItem _updateCommerceOrderItem(
			long commerceOrderItemId, int quantity, String json,
			CommerceProductPrice commerceProductPrice,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		_updateBookedQuantity(
			serviceContext.getUserId(), commerceOrderItem,
			commerceOrderItem.getBookedQuantityId(), quantity,
			commerceOrderItem.getQuantity());

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		validate(
			serviceContext.getLocale(), commerceOrder,
			commerceOrderItem.getCPDefinition(),
			commerceOrderItem.fetchCPInstance(), quantity);

		updateWorkflow(commerceOrder, serviceContext);

		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setJson(json);

		if (commerceOrder.isOpen()) {
			if (commerceProductPrice == null) {
				commerceProductPrice = _getCommerceProductPrice(
					commerceOrderItem.getCPDefinitionId(),
					commerceOrderItem.getCPInstanceId(),
					commerceOrderItem.getJson(), quantity, commerceContext);
			}

			_setCommerceOrderItemPrice(commerceOrderItem, commerceProductPrice);

			_setCommerceOrderItemDiscountValue(
				commerceOrderItem, commerceProductPrice.getDiscountValue(),
				false);

			_setCommerceOrderItemDiscountValue(
				commerceOrderItem,
				commerceProductPrice.getDiscountValueWithTaxAmount(), true);
		}

		commerceOrderItem.setExpandoBridgeAttributes(serviceContext);

		commerceOrderItem = commerceOrderItemPersistence.update(
			commerceOrderItem);

		if (commerceOrder.isOpen()) {
			commerceOrderLocalService.recalculatePrice(
				commerceOrderItem.getCommerceOrderId(), commerceContext);
		}

		return commerceOrderItem;
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.UID
	};

	@ServiceReference(type = CommerceInventoryBookedQuantityLocalService.class)
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@ServiceReference(type = CommerceInventoryWarehouseItemLocalService.class)
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@ServiceReference(type = CommerceMoneyFactory.class)
	private CommerceMoneyFactory _commerceMoneyFactory;

	@ServiceReference(type = CommerceOptionValueHelper.class)
	private CommerceOptionValueHelper _commerceOptionValueHelper;

	@ServiceReference(type = CommerceOrderConfiguration.class)
	private CommerceOrderConfiguration _commerceOrderConfiguration;

	@ServiceReference(type = CommerceOrderValidatorRegistry.class)
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@ServiceReference(type = CommerceProductPriceCalculationFactory.class)
	private CommerceProductPriceCalculationFactory
		_commerceProductPriceCalculationFactory;

	@ServiceReference(type = CommerceShippingHelper.class)
	private CommerceShippingHelper _commerceShippingHelper;

	@ServiceReference(type = CommerceTaxCalculation.class)
	private CommerceTaxCalculation _commerceTaxCalculation;

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@ServiceReference(type = CPDefinitionOptionRelLocalService.class)
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceLocalService;

	@ServiceReference(type = JsonHelper.class)
	private JsonHelper _jsonHelper;

}