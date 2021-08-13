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

package com.liferay.commerce.inventory.internal.search;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CommerceInventoryWarehouseIndexer
	extends BaseIndexer<CommerceInventoryWarehouse> {

	public static final String CLASS_NAME =
		CommerceInventoryWarehouse.class.getName();

	public static final String FIELD_ACTIVE = "active";

	public static final String FIELD_CITY = "city";

	public static final String FIELD_COUNTRY_TWO_LETTERS_ISO_CODE =
		"countryTwoLettersISOCode";

	public static final String FIELD_STREET_1 = "street1";

	public static final String FIELD_ZIP = "zip";

	public CommerceInventoryWarehouseIndexer() {
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return _modelResourcePermission.contains(
			permissionChecker, entryClassPK, actionId);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		Boolean active = (Boolean)searchContext.getAttribute(FIELD_ACTIVE);

		if (active != null) {
			contextBooleanFilter.addTerm(
				FIELD_ACTIVE, String.valueOf(active), BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		super.postProcessSearchQuery(
			searchQuery, fullQueryBooleanFilter, searchContext);

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchTerm(searchQuery, searchContext, FIELD_CITY, false);
		addSearchTerm(searchQuery, searchContext, FIELD_STREET_1, false);
		addSearchTerm(searchQuery, searchContext, FIELD_ZIP, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_COUNTRY_TWO_LETTERS_ISO_CODE,
			false);
	}

	@Override
	protected void doDelete(
			CommerceInventoryWarehouse commerceInventoryWarehouse)
		throws Exception {

		deleteDocument(
			commerceInventoryWarehouse.getCompanyId(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
	}

	@Override
	protected Document doGetDocument(
			CommerceInventoryWarehouse commerceInventoryWarehouse)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing inventory warehouse " + commerceInventoryWarehouse);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, commerceInventoryWarehouse);

		document.addKeyword(
			Field.DESCRIPTION, commerceInventoryWarehouse.getDescription());
		document.addNumberSortable(
			Field.ENTRY_CLASS_PK,
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
		document.addKeyword(Field.NAME, commerceInventoryWarehouse.getName());
		document.addKeyword(
			FIELD_ACTIVE, commerceInventoryWarehouse.isActive());
		document.addKeyword(
			FIELD_COUNTRY_TWO_LETTERS_ISO_CODE,
			commerceInventoryWarehouse.getCountryTwoLettersISOCode());
		document.addKeyword(FIELD_CITY, commerceInventoryWarehouse.getCity());
		document.addKeyword(
			FIELD_STREET_1, commerceInventoryWarehouse.getStreet1());
		document.addKeyword(FIELD_ZIP, commerceInventoryWarehouse.getZip());
		document.addNumber(
			"itemsQuantity", getItemsQuantity(commerceInventoryWarehouse));
		document.addNumber(
			"latitude", commerceInventoryWarehouse.getLatitude());
		document.addNumber(
			"longitude", commerceInventoryWarehouse.getLongitude());
		document.addKeyword(
			"regionCode", commerceInventoryWarehouse.getCommerceRegionCode());
		document.addKeyword("street2", commerceInventoryWarehouse.getStreet2());
		document.addKeyword("street3", commerceInventoryWarehouse.getStreet3());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceInventoryWarehouse +
					" indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return null;
	}

	@Override
	protected void doReindex(
			CommerceInventoryWarehouse commerceInventoryWarehouse)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceInventoryWarehouse.getCompanyId(),
			getDocument(commerceInventoryWarehouse), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_commerceInventoryWarehouseLocalService.
				getCommerceInventoryWarehouse(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceInventoryWarehouses(companyId);
	}

	protected int getItemsQuantity(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		int count = 0;

		for (CommerceInventoryWarehouseItem commerceInventoryWarehouseItem :
				commerceInventoryWarehouse.
					getCommerceInventoryWarehouseItems()) {

			count += commerceInventoryWarehouseItem.getQuantity();
		}

		return count;
	}

	@Override
	protected boolean isUseSearchResultPermissionFilter(
		SearchContext searchContext) {

		Boolean useSearchResultPermissionFilter =
			(Boolean)searchContext.getAttribute(
				"useSearchResultPermissionFilter");

		if (useSearchResultPermissionFilter != null) {
			return useSearchResultPermissionFilter;
		}

		return super.isUseSearchResultPermissionFilter(searchContext);
	}

	protected void reindexCommerceInventoryWarehouses(long companyId)
		throws Exception {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceInventoryWarehouseLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommerceInventoryWarehouse commerceInventoryWarehouse) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commerceInventoryWarehouse));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						long commerceInventoryWarehouseId =
							commerceInventoryWarehouse.
								getCommerceInventoryWarehouseId();

						_log.warn(
							"Unable to index commerce inventory warehouse " +
								commerceInventoryWarehouseId,
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryWarehouseIndexer.class);

	@Reference
	private CommerceInventoryWarehouseLocalService
		_commerceInventoryWarehouseLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouse)"
	)
	private ModelResourcePermission<CommerceInventoryWarehouse>
		_modelResourcePermission;

}