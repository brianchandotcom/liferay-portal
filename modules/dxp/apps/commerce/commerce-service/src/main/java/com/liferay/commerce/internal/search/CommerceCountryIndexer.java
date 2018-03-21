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

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryLocalService;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CommerceCountryIndexer extends BaseIndexer<CommerceCountry> {

	public static final String CLASS_NAME = CommerceCountry.class.getName();

	public static final String FIELD_ACTIVE = "active";

	public static final String FIELD_NUMERIC_ISO_CODE = "numericISOCode";

	public static final String FIELD_THREE_LETTERS_ISO_CODE =
		"threeLettersISOCode";

	public static final String FIELD_TWO_LETTERS_ISO_CODE = "twoLettersISOCode";

	@Override
	public String getClassName() {
		return CLASS_NAME;
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

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.NAME, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_NUMERIC_ISO_CODE, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_THREE_LETTERS_ISO_CODE, false);
		addSearchTerm(
			searchQuery, searchContext, FIELD_TWO_LETTERS_ISO_CODE, false);
	}

	@Override
	protected void doDelete(CommerceCountry commerceCountry) throws Exception {
		deleteDocument(
			commerceCountry.getCompanyId(),
			commerceCountry.getCommerceCountryId());
	}

	@Override
	protected Document doGetDocument(CommerceCountry commerceCountry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing commerce country " + commerceCountry);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceCountry);

		String commerceCountryDefaultLanguageId =
			commerceCountry.getDefaultLanguageId();

		String[] languageIds = commerceCountry.getAvailableLanguageIds();

		for (String languageId : languageIds) {
			String name = commerceCountry.getName(languageId);

			if (languageId.equals(commerceCountryDefaultLanguageId)) {
				document.addText(Field.NAME, name);
				document.addText("defaultLanguageId", languageId);
			}

			document.addKeyword(FIELD_ACTIVE, commerceCountry.getActive());
			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				name);
			document.addNumber(
				FIELD_NUMERIC_ISO_CODE, commerceCountry.getNumericISOCode());
			document.addText(
				FIELD_THREE_LETTERS_ISO_CODE,
				commerceCountry.getThreeLettersISOCode());
			document.addText(
				FIELD_TWO_LETTERS_ISO_CODE,
				commerceCountry.getTwoLettersISOCode());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + commerceCountry + " indexed successfully");
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
	protected void doReindex(CommerceCountry commerceCountry) throws Exception {
		Document document = getDocument(commerceCountry);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceCountry.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommerceCountry commerceCountry =
			_commerceCountryLocalService.getCommerceCountry(classPK);

		doReindex(commerceCountry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceCountries(companyId);
	}

	protected void reindexCommerceCountries(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceCountryLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CommerceCountry>() {

				@Override
				public void performAction(CommerceCountry commerceCountry) {
					try {
						Document document = getDocument(commerceCountry);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce country " +
									commerceCountry.getCommerceCountryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCountryIndexer.class);

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}