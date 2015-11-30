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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultManager;
import com.liferay.portal.kernel.search.result.SearchResultTranslator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author André de Oliveira
 */
@Component(immediate = true, service = SearchResultTranslator.class)
public class SearchResultTranslatorImpl implements SearchResultTranslator {

	@Reference(unbind = "-")
	public void setSearchResultManager(
		SearchResultManager searchResultManager) {

		_searchResultManager = searchResultManager;
	}

	@Override
	public List<SearchResult> translate(
		Hits hits, Locale locale, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		List<SearchResult> searchResults = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			try {
				SearchResult searchResult =
					_searchResultManager.createSearchResult(document);

				int index = searchResults.indexOf(searchResult);

				if (index < 0) {
					searchResults.add(searchResult);
				}
				else {
					searchResult = searchResults.get(index);
				}

				String version = document.get(Field.VERSION);

				if (Validator.isNotNull(version)) {
					searchResult.addVersion(version);
				}

				_searchResultManager.updateSearchResult(
					searchResult, document, locale, portletRequest,
					portletResponse);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					long entryClassPK = GetterUtil.getLong(
						document.get(Field.ENTRY_CLASS_PK));

					_log.warn(
						"Search index is stale and contains entry {" +
							entryClassPK + "}",
						e);
				}
			}
		}

		return searchResults;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResultTranslatorImpl.class);

	private volatile SearchResultManager _searchResultManager;

}