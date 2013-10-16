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

		if (hits.getLength() >= queryConfig.getQueryIndexingThreshold()) {
			addDocument(
				searchContext.getCompanyId(), searchContext.getKeywords(),
				searchContext.getLocale());
		}

		return true;
	}

	protected void addDocument(long companyId, String keywords, Locale locale)
		throws SearchException {

		SearchEngineUtil.indexKeyword(
			companyId, keywords, 0, SuggestionConstants.TYPE_QUERY_SUGGESTION,
			locale);
	}

}