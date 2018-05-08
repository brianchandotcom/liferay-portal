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
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.exception.CommerceOrderPriceException;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.exception.GuestCartItemMaxAllowedException;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.internal.search.CommerceOrderItemIndexer;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.service.base.CommerceOrderItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemLocalServiceImpl
	extends CommerceOrderItemLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem addCommerceOrderItem(
			long commerceOrderId, long cpInstanceId, int quantity,
			int shippedQuantity, String json, BigDecimal price,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		if (Validator.isNull(json) || json.equals("[]")) {
			json = cpInstance.getDDMContent();
		}

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpInstance.getCPDefinitionId());

		if (price == null) {
			CommerceMoney commerceMoney =
				_commercePriceCalculation.getFinalPrice(
					cpInstanceId, quantity, true, true, commerceContext);

			price = commerceMoney.getPrice();
		}

		validate(commerceOrder, cpDefinition, cpInstance, quantity, price);

		long commerceOrderItemId = counterLocalService.increment();

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.create(commerceOrderItemId);

		commerceOrderItem.setGroupId(commerceOrder.getGroupId());
		commerceOrderItem.setCompanyId(user.getCompanyId());
		commerceOrderItem.setUserId(user.getUserId());
		commerceOrderItem.setUserName(user.getFullName());
		commerceOrderItem.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());
		commerceOrderItem.setCPInstanceId(cpInstanceId);
		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setShippedQuantity(shippedQuantity);
		commerceOrderItem.setJson(json);
		commerceOrderItem.setPrice(price);
		commerceOrderItem.setNameMap(cpDefinition.getNameMap());
		commerceOrderItem.setSku(cpInstance.getSku());
		commerceOrderItem.setExpandoBridgeAttributes(serviceContext);

		commerceOrderItemPersistence.update(commerceOrderItem);

		return commerceOrderItem;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrderItem deleteCommerceOrderItem(
			CommerceOrderItem commerceOrderItem)
		throws PortalException {

		validateCommerceOrder(commerceOrderItem.getCommerceOrder());

		// Commerce order item

		commerceOrderItemPersistence.remove(commerceOrderItem);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceOrderItem.getCommerceOrderItemId());

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
		long commerceWarehouseId, long commerceAddressId, int start, int end) {

		return commerceOrderItemFinder.findByC_C(
			commerceWarehouseId, commerceAddressId, start, end);
	}

	@Override
	public int getCommerceOrderItemsCount(long commerceOrderId) {
		return commerceOrderItemPersistence.countByCommerceOrderId(
			commerceOrderId);
	}

	@Override
	public int getCommerceOrderItemsQuantity(long commerceOrderId) {
		return commerceOrderItemFinder.getCommerceOrderItemsQuantity(
			commerceOrderId);
	}

	@Override
	public int getCommerceWarehouseItemQuantity(
			long commerceOrderItemId, long commerceWarehouseId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemLocalService.fetchCommerceWarehouseItem(
				commerceWarehouseId, commerceOrderItem.getCPInstanceId());

		if (commerceWarehouseItem == null) {
			return 0;
		}

		return commerceWarehouseItem.getQuantity();
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId, int orderStatus) {
		return commerceOrderItemFinder.getCPInstanceQuantity(
			cpInstanceId, orderStatus);
	}

	@Override
	public CommerceOrderItem incrementShippedQuantity(
			long commerceOrderItemId, int shippedQuantity)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		shippedQuantity =
			commerceOrderItem.getShippedQuantity() + shippedQuantity;

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
			long commerceOrderItemId, int quantity)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		CPInstance cpInstance = commerceOrderItem.getCPInstance();

		BigDecimal price = cpInstance.getPrice();

		price = price.multiply(new BigDecimal(quantity));

		return updateCommerceOrderItem(
			commerceOrderItemId, quantity, commerceOrderItem.getJson(), price);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderItem updateCommerceOrderItem(
			long commerceOrderItemId, int quantity, String json,
			BigDecimal price)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemPersistence.findByPrimaryKey(commerceOrderItemId);

		int newQuantity = quantity - commerceOrderItem.getQuantity();

		validate(
			commerceOrderItem.getCommerceOrder(),
			commerceOrderItem.getCPDefinition(),
			commerceOrderItem.getCPInstance(), newQuantity, price);

		commerceOrderItem.setQuantity(quantity);
		commerceOrderItem.setJson(json);
		commerceOrderItem.setPrice(price);

		commerceOrderItemPersistence.update(commerceOrderItem);

		return commerceOrderItem;
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

	protected void validate(
			CommerceOrder commerceOrder, CPDefinition cpDefinition,
			CPInstance cpInstance, int quantity, BigDecimal price)
		throws PortalException {

		validateCommerceOrder(commerceOrder);

		if (commerceOrder.getUserId() == 0) {
			int count = commerceOrderItemPersistence.countByCommerceOrderId(
				commerceOrder.getCommerceOrderId());

			if (count >=
					_commerceOrderConfiguration.guestCartItemMaxAllowed()) {

				throw new GuestCartItemMaxAllowedException();
			}
		}

		if (cpInstance.getCPDefinitionId() !=
				cpDefinition.getCPDefinitionId()) {

			throw new NoSuchCPInstanceException(
				"CPInstance " + cpInstance.getCPInstanceId() +
					" belongs to a different CPDefinition than " +
						cpDefinition.getCPDefinitionId());
		}

		if (price == null) {
			throw new CommerceOrderPriceException();
		}

		List<CommerceOrderValidatorResult> commerceCartValidatorResults =
			_commerceOrderValidatorRegistry.validate(cpInstance, quantity);

		if (!commerceCartValidatorResults.isEmpty()) {
			throw new CommerceOrderValidatorException(
				commerceCartValidatorResults);
		}
	}

	protected void validateCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException {

		if (!commerceOrder.isOpen()) {
			throw new NoSuchOrderException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID};

	@ServiceReference(type = CommerceOrderConfiguration.class)
	private CommerceOrderConfiguration _commerceOrderConfiguration;

	@ServiceReference(type = CommerceOrderValidatorRegistry.class)
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@ServiceReference(type = CommercePriceCalculation.class)
	private CommercePriceCalculation _commercePriceCalculation;

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceLocalService;

}