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

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
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
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CommerceAddressIndexer extends BaseIndexer<CommerceAddress> {

	public static final String CLASS_NAME = CommerceAddress.class.getName();

	public static final String FIELD_CITY = "city";

	public static final String FIELD_COUNTRY_NAME = "countryName";

	public static final String FIELD_REGION_NAME = "regionName";

	public static final String FIELD_ZIP = "zip";

	public CommerceAddressIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID, Field.UID);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long classNameId = GetterUtil.getLong(
			searchContext.getAttribute(Field.CLASS_NAME_ID));

		if (classNameId > 0) {
			contextBooleanFilter.addTerm(
				Field.CLASS_NAME_ID, String.valueOf(classNameId),
				BooleanClauseOccur.MUST);
		}

		long classPK = GetterUtil.getLong(
			searchContext.getAttribute(Field.CLASS_PK));

		if (classPK > 0) {
			contextBooleanFilter.addTerm(
				Field.CLASS_PK, String.valueOf(classPK),
				BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, FIELD_CITY, false);
		addSearchTerm(searchQuery, searchContext, FIELD_COUNTRY_NAME, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchTerm(searchQuery, searchContext, FIELD_REGION_NAME, false);
		addSearchTerm(searchQuery, searchContext, FIELD_ZIP, false);
	}

	@Override
	protected void doDelete(CommerceAddress commerceAddress) throws Exception {
		deleteDocument(
			commerceAddress.getCompanyId(),
			commerceAddress.getCommerceAddressId());
	}

	@Override
	protected Document doGetDocument(CommerceAddress commerceAddress)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing commerce address " + commerceAddress);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceAddress);

		document.addText(FIELD_CITY, commerceAddress.getCity());
		document.addText(Field.NAME, commerceAddress.getName());
		document.addNumber(FIELD_ZIP, commerceAddress.getZip());

		CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();

		document.addText(FIELD_COUNTRY_NAME, commerceCountry.getName());

		CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

		document.addText(FIELD_REGION_NAME, commerceRegion.getName());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + commerceAddress + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.ENTRY_CLASS_PK, Field.NAME);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommerceAddress commerceAddress) throws Exception {
		Document document = getDocument(commerceAddress);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceAddress.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommerceAddress commerceAddress =
			_commerceAddressLocalService.getCommerceAddress(classPK);

		doReindex(commerceAddress);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceAddresses(companyId);
	}

	protected void reindexCommerceAddresses(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceAddressLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CommerceAddress>() {

				@Override
				public void performAction(CommerceAddress commerceAddress) {
					try {
						Document document = getDocument(commerceAddress);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce address " +
									commerceAddress.getCommerceAddressId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAddressIndexer.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}