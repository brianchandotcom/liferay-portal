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

package com.liferay.commerce.internal.search;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CommerceOrderItemIndexer extends BaseIndexer<CommerceOrderItem> {

	public static final String CLASS_NAME = CommerceOrderItem.class.getName();

	public static final String FIELD_COMMERCE_ORDER_ID = "commerceOrderId";

	public static final String FIELD_CP_DEFINITION_ID = "CPDefinitionId";

	public static final String FIELD_FINAL_PRICE = "finalPrice";

	public static final String FIELD_PARENT_COMMERCE_ORDER_ITEM_ID =
		"parentCommerceOrderItemId";

	public static final String FIELD_QUANTITY = "quantity";

	public static final String FIELD_SKU = CPField.SKU;

	public static final String FIELD_UNIT_PRICE = "unitPrice";

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long commerceOrderId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_COMMERCE_ORDER_ID));

		contextBooleanFilter.addRequiredTerm(
			FIELD_COMMERCE_ORDER_ID, commerceOrderId);

		Long parentCommerceOrderItemId = (Long)searchContext.getAttribute(
			FIELD_PARENT_COMMERCE_ORDER_ITEM_ID);

		if (parentCommerceOrderItemId != null) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_PARENT_COMMERCE_ORDER_ITEM_ID, parentCommerceOrderItemId);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, FIELD_SKU, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.NAME, true);

		String keywords = searchContext.getKeywords();

		if (Validator.isNotNull(keywords)) {
			try {
				keywords = StringUtil.toLowerCase(keywords);

				searchQuery.add(
					_getTrailingWildcardQuery(FIELD_SKU, keywords),
					BooleanClauseOccur.SHOULD);
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	@Override
	protected void doDelete(CommerceOrderItem commerceOrderItem)
		throws Exception {

		deleteDocument(
			commerceOrderItem.getCompanyId(),
			commerceOrderItem.getCommerceOrderItemId());
	}

	@Override
	protected Document doGetDocument(CommerceOrderItem commerceOrderItem)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing order item " + commerceOrderItem);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceOrderItem);

		document.addLocalizedKeyword(
			Field.NAME, commerceOrderItem.getNameMap());
		document.addKeyword(FIELD_SKU, commerceOrderItem.getSku());
		document.addNumber(
			FIELD_COMMERCE_ORDER_ID, commerceOrderItem.getCommerceOrderId());
		document.addKeyword(
			FIELD_CP_DEFINITION_ID, commerceOrderItem.getCPDefinitionId());
		document.addNumber(
			FIELD_FINAL_PRICE, commerceOrderItem.getFinalPrice());
		document.addNumber(
			FIELD_PARENT_COMMERCE_ORDER_ITEM_ID,
			commerceOrderItem.getParentCommerceOrderItemId());
		document.addNumber(FIELD_QUANTITY, commerceOrderItem.getQuantity());
		document.addNumber(FIELD_UNIT_PRICE, commerceOrderItem.getUnitPrice());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceOrderItem + " indexed successfully");
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
	protected void doReindex(CommerceOrderItem commerceOrderItem)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceOrderItem.getCompanyId(),
			getDocument(commerceOrderItem), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_commerceOrderItemLocalService.getCommerceOrderItem(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceOrderItems(companyId);
	}

	protected void reindexCommerceOrderItems(long companyId) throws Exception {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceOrderItemLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommerceOrderItem commerceOrderItem) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commerceOrderItem));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce order item " +
								commerceOrderItem.getCommerceOrderItemId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private WildcardQuery _getTrailingWildcardQuery(
		String field, String value) {

		return new WildcardQueryImpl(field, value + StringPool.STAR);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemIndexer.class);

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}