/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.portal.search.filter.RangeFilter;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(service = RangeFilterTranslator.class)
public class RangeFilterTranslatorImpl implements RangeFilterTranslator {

	@Override
	public Query translate(RangeFilter rangeFilter) {
		return TermRangeQuery.newStringRange(
			rangeFilter.getFieldName(), rangeFilter.getFrom(),
			rangeFilter.getTo(), rangeFilter.isIncludeLower(),
			rangeFilter.isIncludeUpper());
	}

}