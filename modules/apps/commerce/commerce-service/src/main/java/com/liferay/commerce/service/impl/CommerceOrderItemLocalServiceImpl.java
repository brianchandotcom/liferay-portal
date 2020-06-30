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
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.exception.CommerceOrderItemRequestedDeliveryDateException;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.exception.GuestCartItemMaxAllowedException;
import com.liferay.commerce.exception.NoSuchOrderItemException;
import com.liferay.commerce.internal.search.CommerceOrderItemIndexer;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.price.CommerceProductPrice;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceCalculationFactory;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.DDMFormValuesUtil;
import com.liferay.commerce.service.base.CommerceOrderItemLocalServiceBaseImpl;
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
		User user = userLocalService.getUser(serviceContext.getUserId());

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpInstance.getCPDefinitionId());

		validate(
			serviceContext.getLocale(), commerceOrder, cpDefinition, cpInstance,
			quantity);

		updateWorkflow(commerceOrder, serviceContext);

		CommerceProductPriceCalculation commerceProductPriceCalculation =
			_commerceProductPriceCalculationFactory.
				getCommerceProductPriceCalculation();

		CommerceProductPrice commerceProductPrice =
			commerceProductPriceCalculation.getCommerceProductPrice(
				cpInstanceId, quantity, false, commerceContext);

		CommerceMoney unitPriceMoney = commerceProductPrice.getUnitPrice();
		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();
		CommerceMoney unitPromoPriceMoney =
			commerceProductPrice.getUnitPromoPrice();

		long commerceOrderItemId = counterLocalService.increment();

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.create(commerceOrderItemId);

		commerceOrderItem.setGroupId(commerceOrder.getGroupId());
		commerceOrderItem.setCompanyId(user.getCompanyId());
		commerceOrderItem.setUserId(user.getUserId());
		commerceOrderItem.setUserName(user.getFullName());
		commerceOrderItem.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());
		commerceOrderItem.setCProductId(cpDefinition.getCProductId());
		commerceOrderItem.setCPInstanceId(cpInstanceId);
		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setShippedQuantity(shippedQuantity);
		commerceOrderItem.setJson(json);
		commerceOrderItem.setUnitPrice(unitPriceMoney.getPrice());

		BigDecimal promoPrice = BigDecimal.ZERO;

		if (unitPromoPriceMoney != null) {
			promoPrice = unitPromoPriceMoney.getPrice();
		}

		commerceOrderItem.setPromoPrice(promoPrice);
		commerceOrderItem.setFinalPrice(finalPriceMoney.getPrice());
		commerceOrderItem.setNameMap(cpDefinition.getNameMap());
		commerceOrderItem.setSku(cpInstance.getSku());
		commerceOrderItem.setManuallyAdjusted(false);
		commerceOrderItem.setExpandoBridgeAttributes(serviceContext);

		_setCommerceOrderItemDiscountValue(
			commerceOrderItem, commerceProductPrice.getDiscountValue());

		boolean subscription = false;

		if (cpDefinition.isSubscriptionEnabled() ||
			cpDefinition.isDeliverySubscriptionEnabled()) {

			subscription = true;
		}

		if (cpInstance.isOverrideSubscriptionInfo() &&
			(cpInstance.isSubscriptionEnabled() ||
			 cpInstance.isDeliverySubscriptionEnabled())) {

			subscription = true;
		}

		commerceOrderItem.setSubscription(subscription);

		commerceOrderItem = commerceOrderItemPersistence.update(
			commerceOrderItem);

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

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		updateWorkflow(commerceOrder, serviceContext);

		return commerceOrderItem;
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
			commerceOrderItemLocalService.deleteCommerceOrderItem(
				commerceOrderItem);
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
	public BaseModelSearchResult<CommerceOrderItem> search(
			long commerceOrderId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			commerceOrderId, start, end, sort);

		searchContext.setKeywords(keywords);

		return searchCommerceOrderItems(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceOrderItem> search(
			long commerceOrderId, String sku, String name, boolean andOperator,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			commerceOrderId, start, end, sort);

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

		CommerceProductPriceCalculation commerceProductPriceCalculation =
			_commerceProductPriceCalculationFactory.
				getCommerceProductPriceCalculation();

		CommerceProductPrice commerceProductPrice =
			commerceProductPriceCalculation.getCommerceProductPrice(
				commerceOrderItem.getCPInstanceId(), quantity, false,
				commerceContext);

		CommerceMoney unitPriceMoney = commerceProductPrice.getUnitPrice();
		CommerceMoney unitPromoPriceMoney =
			commerceProductPrice.getUnitPromoPrice();
		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		validate(
			serviceContext.getLocale(), commerceOrderItem.getCommerceOrder(),
			commerceOrderItem.getCPDefinition(),
			commerceOrderItem.fetchCPInstance(), quantity);

		updateWorkflow(commerceOrderItem.getCommerceOrder(), serviceContext);

		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setJson(json);
		commerceOrderItem.setUnitPrice(unitPriceMoney.getPrice());

		BigDecimal promoPrice = BigDecimal.ZERO;

		if (unitPromoPriceMoney != null) {
			promoPrice = unitPromoPriceMoney.getPrice();
		}

		commerceOrderItem.setPromoPrice(promoPrice);
		commerceOrderItem.setFinalPrice(finalPriceMoney.getPrice());
		commerceOrderItem.setExpandoBridgeAttributes(serviceContext);

		_setCommerceOrderItemDiscountValue(
			commerceOrderItem, commerceProductPrice.getDiscountValue());

		commerceOrderItem = commerceOrderItemPersistence.update(
			commerceOrderItem);

		commerceOrderLocalService.recalculatePrice(
			commerceOrderItem.getCommerceOrderId(), commerceContext);

		return commerceOrderItem;
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

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if ((cpInstance == null) || commerceOrderItem.isManuallyAdjusted()) {
			return commerceOrderItem;
		}

		CommerceProductPriceCalculation commerceProductPriceCalculation =
			_commerceProductPriceCalculationFactory.
				getCommerceProductPriceCalculation();

		CommerceProductPrice commerceProductPrice =
			commerceProductPriceCalculation.getCommerceProductPrice(
				commerceOrderItem.getCPInstanceId(),
				commerceOrderItem.getQuantity(), false, commerceContext);

		CommerceMoney unitPriceMoney = commerceProductPrice.getUnitPrice();
		CommerceMoney unitPromoPriceMoney =
			commerceProductPrice.getUnitPromoPrice();
		CommerceMoney finalPriceMoney = commerceProductPrice.getFinalPrice();

		commerceOrderItem.setUnitPrice(unitPriceMoney.getPrice());

		BigDecimal promoPrice = BigDecimal.ZERO;

		if (unitPromoPriceMoney != null) {
			promoPrice = unitPromoPriceMoney.getPrice();
		}

		commerceOrderItem.setPromoPrice(promoPrice);
		commerceOrderItem.setFinalPrice(finalPriceMoney.getPrice());

		_setCommerceOrderItemDiscountValue(
			commerceOrderItem, commerceProductPrice.getDiscountValue());

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
			long commerceOrderItemId, BigDecimal unitPrice, int quantity)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setUnitPrice(unitPrice);
		commerceOrderItem.setManuallyAdjusted(true);

		return commerceOrderItemPersistence.update(commerceOrderItem);
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
			if (Objects.equals(json, commerceOrderItem.getJson()) ||
				(Objects.equals(json, "[]") &&
				 Validator.isBlank(commerceOrderItem.getJson()))) {

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
			long commerceOrderId, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		searchContext.setAttribute(
			CommerceOrderItemIndexer.FIELD_COMMERCE_ORDER_ID, commerceOrderId);
		searchContext.setCompanyId(commerceOrder.getCompanyId());
		searchContext.setEnd(end);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

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

	private String _getCPInstanceOptionValueRelsJSONString(long cpInstanceId)
		throws PortalException {

		JSONArray jsonArray = DDMFormValuesUtil.toJSONArray(
			_cpDefinitionOptionRelLocalService.
				getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
					cpInstanceId));

		return jsonArray.toString();
	}

	private void _setCommerceOrderItemDiscountValue(
		CommerceOrderItem commerceOrderItem,
		CommerceDiscountValue commerceDiscountValue) {

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

			if (percentages.length >= 1) {
				discountPercentageLevel1 = percentages[0];
			}

			if (percentages.length >= 2) {
				discountPercentageLevel1 = percentages[1];
			}

			if (percentages.length >= 3) {
				discountPercentageLevel1 = percentages[2];
			}

			if (percentages.length >= 4) {
				discountPercentageLevel1 = percentages[3];
			}
		}

		commerceOrderItem.setDiscountAmount(discountAmount);
		commerceOrderItem.setDiscountPercentageLevel1(discountPercentageLevel1);
		commerceOrderItem.setDiscountPercentageLevel2(discountPercentageLevel2);
		commerceOrderItem.setDiscountPercentageLevel3(discountPercentageLevel3);
		commerceOrderItem.setDiscountPercentageLevel4(discountPercentageLevel4);
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID
	};

	@ServiceReference(type = CommerceInventoryBookedQuantityLocalService.class)
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@ServiceReference(type = CommerceInventoryWarehouseItemLocalService.class)
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@ServiceReference(type = CommerceOrderConfiguration.class)
	private CommerceOrderConfiguration _commerceOrderConfiguration;

	@ServiceReference(type = CommerceOrderValidatorRegistry.class)
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@ServiceReference(type = CommerceProductPriceCalculationFactory.class)
	private CommerceProductPriceCalculationFactory
		_commerceProductPriceCalculationFactory;

	@ServiceReference(type = CommerceShippingHelper.class)
	private CommerceShippingHelper _commerceShippingHelper;

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@ServiceReference(type = CPDefinitionOptionRelLocalService.class)
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceLocalService;

}