/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.portal.search.filter.DateRangeFilter;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = DateRangeFilterTranslator.class)
public class DateRangeFilterTranslatorImpl
	implements DateRangeFilterTranslator {

	@Override
	public Query translate(DateRangeFilter dateRangeFilter) {
		return TermRangeQuery.newStringRange(
			dateRangeFilter.getFieldName(), dateRangeFilter.getFrom(),
			dateRangeFilter.getTo(), dateRangeFilter.isIncludeLower(),
			dateRangeFilter.isIncludeUpper());
	}

}