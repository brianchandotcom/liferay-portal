/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.portal.kernel.search.filter.RangeTermFilter;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = RangeTermFilterTranslator.class)
public class RangeTermFilterTranslatorImpl
	implements RangeTermFilterTranslator {

	@Override
	public Query translate(RangeTermFilter rangeTermFilter) {
		return TermRangeQuery.newStringRange(
			rangeTermFilter.getField(), rangeTermFilter.getLowerBound(),
			rangeTermFilter.getUpperBound(), rangeTermFilter.isIncludesLower(),
			rangeTermFilter.isIncludesUpper());
	}

}