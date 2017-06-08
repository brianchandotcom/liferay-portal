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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Adolfo Pérez
 * @author André de Oliveira
 */
public class DefaultSearchResultManagerImpl extends BaseSearchResultManager {

	public DefaultSearchResultManagerImpl(SummaryFactory summaryFactory) {
		_summaryFactory = summaryFactory;
	}

	@Override
	public SearchResult createSearchResult(Document document) {
		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));
		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return new SearchResult(entryClassName, entryClassPK);
	}

	@Override
	public void updateSearchResult(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));
		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		searchResult.setSummary(
			_summaryFactory.getSummary(
				document, entryClassName, entryClassPK, locale, portletRequest,
				portletResponse));
	}

	@Override
	protected void addRelatedModel(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {
	}

	@Override
	protected SummaryFactory getSummaryFactory() {
		return _summaryFactory;
	}

	private final SummaryFactory _summaryFactory;

}