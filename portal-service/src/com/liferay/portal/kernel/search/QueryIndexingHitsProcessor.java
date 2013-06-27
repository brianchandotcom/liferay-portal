/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Locale;

/**
 * @author Michael C. Han
 * @author Josef Sustacek
 */
public class QueryIndexingHitsProcessor implements HitsProcessor {

	@Override
	public boolean process(SearchContext searchContext, Hits hits)
		throws SearchException {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (!queryConfig.isQueryIndexingEnabled()) {
			return true;
		}

		int queryIndexingThreshold = queryConfig.getQueryIndexingThreshold();

		if (hits.getLength() >= queryIndexingThreshold) {
			addDocument(
				searchContext.getCompanyId(), searchContext.getKeywords(),
				searchContext.getLocale());
		}

		return true;
	}

	public void setDocument(Document document) {
		_document = document;
	}

	protected void addDocument(long companyId, String keywords, Locale locale)
		throws SearchException {

		Document document = (Document)_document.clone();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.KEYWORD_SEARCH, keywords);
		document.addKeyword(Field.LANGUAGE_ID, LocaleUtil.toLanguageId(locale));
		document.addKeyword(Field.PORTLET_ID, PortletKeys.SEARCH);

		SearchEngineUtil.addDocument(
			SearchEngineUtil.getDefaultSearchEngineId(), companyId, document);
	}

	private Document _document;

}