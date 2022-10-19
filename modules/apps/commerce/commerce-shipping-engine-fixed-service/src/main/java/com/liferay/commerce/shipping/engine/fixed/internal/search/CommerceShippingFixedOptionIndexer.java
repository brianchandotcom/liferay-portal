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

package com.liferay.commerce.shipping.engine.fixed.internal.search;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
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
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(immediate = true, service = Indexer.class)
public class CommerceShippingFixedOptionIndexer
	extends BaseIndexer<CommerceShippingFixedOption> {

	public static final String CLASS_NAME =
		CommerceShippingFixedOption.class.getName();

	public CommerceShippingFixedOptionIndexer() {
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long commerceShippingMethodId = (long)searchContext.getAttribute(
			"commerceShippingMethodId");

		if (commerceShippingMethodId != -1) {
			TermFilter termFilter = new TermFilter(
				"commerceShippingMethodId",
				String.valueOf(commerceShippingMethodId));

			contextBooleanFilter.add(termFilter, BooleanClauseOccur.MUST);
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
		addSearchLocalizedTerm(searchQuery, searchContext, Field.NAME, true);
		addSearchTerm(
			searchQuery, searchContext, "commerceShippingMethodId", false);
		addSearchTerm(searchQuery, searchContext, "description", false);
		addSearchTerm(searchQuery, searchContext, "key", true);
	}

	@Override
	protected void doDelete(
			CommerceShippingFixedOption commerceShippingFixedOption)
		throws Exception {

		deleteDocument(
			commerceShippingFixedOption.getCompanyId(),
			commerceShippingFixedOption.getCommerceShippingFixedOptionId());
	}

	@Override
	protected Document doGetDocument(
			CommerceShippingFixedOption commerceShippingFixedOption)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing shipping fixed option " +
					commerceShippingFixedOption);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, commerceShippingFixedOption);

		document.addKeyword(
			Field.DESCRIPTION, commerceShippingFixedOption.getDescription());
		document.addKeyword(Field.NAME, commerceShippingFixedOption.getName());
		document.addKeyword(
			"commerceShippingMethodId",
			commerceShippingFixedOption.getCommerceShippingMethodId());
		document.addKeyword("key", commerceShippingFixedOption.getKey());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceShippingFixedOption +
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
			CommerceShippingFixedOption commerceShippingFixedOption)
		throws Exception {

		_indexWriterHelper.updateDocument(
			commerceShippingFixedOption.getCompanyId(),
			getDocument(commerceShippingFixedOption));
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexCommerceShippingFixedOptions(companyId);
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

	private void _reindexCommerceShippingFixedOptions(long companyId)
		throws Exception {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceShippingFixedOptionLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommerceShippingFixedOption commerceShippingFixedOption) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commerceShippingFixedOption));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						long commerceShippingFixedOptionId =
							commerceShippingFixedOption.
								getCommerceShippingFixedOptionId();

						_log.warn(
							"Unable to index commerce shipping fixed option " +
								commerceShippingFixedOptionId,
							portalException);
					}
				}
			});

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionIndexer.class);

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}