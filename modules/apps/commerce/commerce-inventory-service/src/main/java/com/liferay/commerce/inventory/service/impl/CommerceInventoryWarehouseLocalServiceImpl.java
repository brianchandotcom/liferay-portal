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

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.exception.CommerceInventoryWarehouseActiveException;
import com.liferay.commerce.inventory.exception.CommerceInventoryWarehouseNameException;
import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryWarehouseException;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.internal.search.CommerceInventoryWarehouseIndexer;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.base.CommerceInventoryWarehouseLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
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
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehouseLocalServiceImpl
	extends CommerceInventoryWarehouseLocalServiceBaseImpl {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommerceInventoryWarehouse(String,
	 *             String, String, String, boolean, String, String, String,
	 *             String, String, String, String, double, double,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name, String description, boolean active, String street1,
			String street2, String street3, String city, String zip,
			String commerceRegionCode, String commerceCountryCode,
			double latitude, double longitude, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommerceInventoryWarehouse(
			externalReferenceCode, name, description, active, street1, street2,
			street3, city, zip, commerceRegionCode, commerceCountryCode,
			latitude, longitude, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String externalReferenceCode, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}
		else {
			CommerceInventoryWarehouse commerceInventoryWarehouse =
				fetchCommerceInventoryWarehouseByReferenceCode(
					user.getCompanyId(), externalReferenceCode);

			if (commerceInventoryWarehouse != null) {
				throw new DuplicateCommerceInventoryWarehouseException(
					"Duplicated externalReferenceCode");
			}
		}

		validate(name, active, latitude, longitude);

		long commerceInventoryWarehouseId = counterLocalService.increment();

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehousePersistence.create(
				commerceInventoryWarehouseId);

		commerceInventoryWarehouse.setExternalReferenceCode(
			externalReferenceCode);
		commerceInventoryWarehouse.setCompanyId(user.getCompanyId());
		commerceInventoryWarehouse.setUserId(user.getUserId());
		commerceInventoryWarehouse.setUserName(user.getFullName());
		commerceInventoryWarehouse.setName(name);
		commerceInventoryWarehouse.setDescription(description);
		commerceInventoryWarehouse.setActive(active);
		commerceInventoryWarehouse.setStreet1(street1);
		commerceInventoryWarehouse.setStreet2(street2);
		commerceInventoryWarehouse.setStreet3(street3);
		commerceInventoryWarehouse.setCity(city);
		commerceInventoryWarehouse.setZip(zip);
		commerceInventoryWarehouse.setCommerceRegionCode(commerceRegionCode);
		commerceInventoryWarehouse.setCountryTwoLettersISOCode(
			commerceCountryCode);
		commerceInventoryWarehouse.setLatitude(latitude);
		commerceInventoryWarehouse.setLongitude(longitude);
		commerceInventoryWarehouse.setExpandoBridgeAttributes(serviceContext);

		commerceInventoryWarehouse =
			commerceInventoryWarehousePersistence.update(
				commerceInventoryWarehouse);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
			user.getUserId(), CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(), false,
			false, false);

		return commerceInventoryWarehouse;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceInventoryWarehouse deleteCommerceInventoryWarehouse(
			CommerceInventoryWarehouse commerceInventoryWarehouse)
		throws PortalException {

		commerceInventoryWarehousePersistence.remove(
			commerceInventoryWarehouse);

		// Commerce warehouse items

		commerceInventoryWarehouseItemLocalService.
			deleteCommerceInventoryWarehouseItems(
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

		// Expando

		expandoRowLocalService.deleteRows(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());

		// Resources

		resourceLocalService.deleteResource(
			commerceInventoryWarehouse, ResourceConstants.SCOPE_INDIVIDUAL);

		return commerceInventoryWarehouse;
	}

	@Override
	public CommerceInventoryWarehouse
		fetchCommerceInventoryWarehouseByReferenceCode(
			long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceInventoryWarehousePersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceInventoryWarehouse geolocateCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId, double latitude,
			double longitude)
		throws PortalException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehousePersistence.findByPrimaryKey(
				commerceInventoryWarehouseId);

		commerceInventoryWarehouse.setLatitude(latitude);
		commerceInventoryWarehouse.setLongitude(longitude);

		return commerceInventoryWarehousePersistence.update(
			commerceInventoryWarehouse);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
		long companyId) {

		return commerceInventoryWarehousePersistence.findByCompanyId(companyId);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return commerceInventoryWarehousePersistence.findByC_A(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
		long companyId, boolean active, String commerceCountryCode, int start,
		int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return commerceInventoryWarehousePersistence.findByC_A_C(
			companyId, active, commerceCountryCode, start, end,
			orderByComparator);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
		long companyId, int start, int end,
		OrderByComparator<CommerceInventoryWarehouse> orderByComparator) {

		return commerceInventoryWarehousePersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
		long companyId, long groupId, boolean active) {

		return commerceInventoryWarehouseFinder.findByC_G_A(
			companyId, groupId, active);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
		long groupId, String sku) {

		return commerceInventoryWarehouseFinder.findByG_S(groupId, sku);
	}

	@Override
	public int getCommerceInventoryWarehousesCount(long companyId) {
		return commerceInventoryWarehousePersistence.countByCompanyId(
			companyId);
	}

	@Override
	public int getCommerceInventoryWarehousesCount(
		long companyId, boolean active) {

		return commerceInventoryWarehousePersistence.countByC_A(
			companyId, active);
	}

	@Override
	public int getCommerceInventoryWarehousesCount(
		long companyId, boolean active, String commerceCountryCode) {

		if (Validator.isNotNull(commerceCountryCode)) {
			return commerceInventoryWarehousePersistence.countByC_A_C(
				companyId, active, commerceCountryCode);
		}

		return commerceInventoryWarehouseLocalService.
			getCommerceInventoryWarehousesCount(companyId, active);
	}

	@Override
	public List<CommerceInventoryWarehouse> searchCommerceInventoryWarehouses(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, active, commerceCountryCode, keywords, start, end, sort);

		searchContext.setKeywords(keywords);

		return searchCommerceInventoryWarehouses(searchContext);
	}

	@Override
	public int searchCommerceInventoryWarehousesCount(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, active, commerceCountryCode, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		searchContext.setKeywords(keywords);

		return searchCommerceInventoryWarehousesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceInventoryWarehouse setActive(
			long commerceInventoryWarehouseId, boolean active)
		throws PortalException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehousePersistence.findByPrimaryKey(
				commerceInventoryWarehouseId);

		validate(
			commerceInventoryWarehouse.getName(), active,
			commerceInventoryWarehouse.getLatitude(),
			commerceInventoryWarehouse.getLongitude());

		commerceInventoryWarehouse.setActive(active);

		return commerceInventoryWarehousePersistence.update(
			commerceInventoryWarehouse);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceInventoryWarehouse updateCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			long mvccVersion, ServiceContext serviceContext)
		throws PortalException {

		validate(name, active, latitude, longitude);

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehousePersistence.findByPrimaryKey(
				commerceInventoryWarehouseId);

		if (commerceInventoryWarehouse.getMvccVersion() != mvccVersion) {
			throw new MVCCException();
		}

		commerceInventoryWarehouse.setName(name);
		commerceInventoryWarehouse.setDescription(description);
		commerceInventoryWarehouse.setActive(active);
		commerceInventoryWarehouse.setStreet1(street1);
		commerceInventoryWarehouse.setStreet2(street2);
		commerceInventoryWarehouse.setStreet3(street3);
		commerceInventoryWarehouse.setCity(city);
		commerceInventoryWarehouse.setZip(zip);
		commerceInventoryWarehouse.setCommerceRegionCode(commerceRegionCode);
		commerceInventoryWarehouse.setCountryTwoLettersISOCode(
			commerceCountryCode);
		commerceInventoryWarehouse.setLatitude(latitude);
		commerceInventoryWarehouse.setLongitude(longitude);
		commerceInventoryWarehouse.setExpandoBridgeAttributes(serviceContext);

		return commerceInventoryWarehousePersistence.update(
			commerceInventoryWarehouse);
	}

	protected SearchContext buildSearchContext(
		long companyId, Boolean active, String commerceCountryCode,
		String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				CommerceInventoryWarehouseIndexer.FIELD_CITY, keywords
			).put(
				CommerceInventoryWarehouseIndexer.FIELD_STREET_1, keywords
			).put(
				CommerceInventoryWarehouseIndexer.FIELD_ZIP, keywords
			).put(
				Field.DESCRIPTION, keywords
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build();

		if (active != null) {
			attributes.put(
				CommerceInventoryWarehouseIndexer.FIELD_ACTIVE, active);
		}

		if (Validator.isNotNull(commerceCountryCode)) {
			attributes.put(
				CommerceInventoryWarehouseIndexer.
					FIELD_COUNTRY_TWO_LETTERS_ISO_CODE,
				commerceCountryCode);
		}

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
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

	protected List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long commerceInventoryWarehouseId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceInventoryWarehouse commerceInventoryWarehouse =
				commerceInventoryWarehouseLocalService.
					fetchCommerceInventoryWarehouse(
						commerceInventoryWarehouseId);

			if (commerceInventoryWarehouse == null) {
				commerceInventoryWarehouses = null;

				Indexer<CommerceInventoryWarehouse> indexer =
					IndexerRegistryUtil.getIndexer(
						CommerceInventoryWarehouse.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceInventoryWarehouses != null) {
				commerceInventoryWarehouses.add(commerceInventoryWarehouse);
			}
		}

		return commerceInventoryWarehouses;
	}

	protected List<CommerceInventoryWarehouse>
			searchCommerceInventoryWarehouses(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceInventoryWarehouse> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceInventoryWarehouse.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
				getCommerceInventoryWarehouses(hits);

			if (commerceInventoryWarehouses != null) {
				return commerceInventoryWarehouses;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCommerceInventoryWarehousesCount(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceInventoryWarehouse> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceInventoryWarehouse.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected void validate(
			String name, boolean active, double latitude, double longitude)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CommerceInventoryWarehouseNameException();
		}

		if (active && (latitude == 0) && (longitude == 0)) {
			throw new CommerceInventoryWarehouseActiveException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID
	};

}