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

package com.liferay.portal.search.internal.result;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultManager;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.search.result.SearchResultContributor;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adolfo Pérez
 * @author André de Oliveira
 */
@Component(immediate = true, service = SearchResultManager.class)
public class SearchResultManagerImpl implements SearchResultManager {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void addSearchResultContributor(
		SearchResultContributor searchResultContributor) {

		_searchResultContributors.put(
			searchResultContributor.getEntryClassName(),
			searchResultContributor);
	}

	@Override
	public SearchResult createSearchResult(Document document)
		throws PortalException {

		SearchResultContributor searchResultContributor =
			getSearchResultContributor(document);

		if (searchResultContributor == null) {
			return createSearchResultWithEntryClass(document);
		}

		if (isClassPresent(document)) {
			return createSearchResultWithClass(document);
		}

		return createSearchResultWithEntryClass(document);
	}

	public void removeSearchResultContributor(
		SearchResultContributor searchResultContributor) {

		_searchResultContributors.remove(
			searchResultContributor.getEntryClassName());
	}

	@Reference(unbind = "-")
	public void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	public void setSummaryFactory(SummaryFactory newSummaryFactory) {
		_summaryFactory = newSummaryFactory;
	}

	@Override
	public void updateSearchResult(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		SearchResultContributor searchResultContributor =
			getSearchResultContributor(document);

		if ((searchResultContributor != null) && isClassPresent(document)) {
			searchResultContributor.addRelatedModel(
				searchResult, document, locale, portletRequest,
				portletResponse);

			if (searchResult.getSummary() == null) {
				searchResult.setSummary(
					getSummaryWithClass(searchResult, locale));
			}

			return;
		}

		searchResult.setSummary(
			getSummaryWithEntryClass(
				document, locale, portletRequest, portletResponse));
	}

	protected SearchResult createSearchResultWithClass(Document document)
		throws PortalException {

		long classNameId = GetterUtil.getLong(
			document.get(Field.CLASS_NAME_ID));

		ClassName className = _classNameLocalService.getClassName(classNameId);

		if (className == null) {
			throw new PortalException(
				"Unable to get class name from class name ID " + classNameId);
		}

		long classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));

		return new SearchResult(className.getClassName(), classPK);
	}

	protected SearchResult createSearchResultWithEntryClass(Document document) {
		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));
		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return new SearchResult(entryClassName, entryClassPK);
	}

	protected SearchResultContributor getSearchResultContributor(
		Document document) {

		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));

		return _searchResultContributors.get(entryClassName);
	}

	protected Summary getSummaryWithClass(
			SearchResult searchResult, Locale locale)
		throws PortalException {

		return _summaryFactory.getSummary(
			searchResult.getClassName(), searchResult.getClassPK(), locale);
	}

	protected Summary getSummaryWithEntryClass(
			Document document, Locale locale, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException {

		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));
		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return _summaryFactory.getSummary(
			document, entryClassName, entryClassPK, locale, portletRequest,
			portletResponse);
	}

	protected boolean isClassPresent(Document document) {
		long classNameId = GetterUtil.getLong(
			document.get(Field.CLASS_NAME_ID));
		long classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));

		if ((classNameId > 0) && (classPK > 0)) {
			return true;
		}

		return false;
	}

	private ClassNameLocalService _classNameLocalService;
	private final HashMap<String, SearchResultContributor>
		_searchResultContributors = new HashMap<>();
	private SummaryFactory _summaryFactory;

}