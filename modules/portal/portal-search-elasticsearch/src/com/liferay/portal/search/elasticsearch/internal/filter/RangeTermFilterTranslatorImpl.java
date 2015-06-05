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

package com.liferay.portal.search.elasticsearch.internal.filter;

import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.search.elasticsearch.filter.RangeTermFilterTranslator;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.RangeFilterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = RangeTermFilterTranslator.class)
public class RangeTermFilterTranslatorImpl
	implements RangeTermFilterTranslator {

	@Override
	public FilterBuilder translate(RangeTermFilter rangeTermFilter) {
		RangeFilterBuilder rangeFilterBuilder = FilterBuilders.rangeFilter(
			rangeTermFilter.getField());

		if (rangeTermFilter.isCached() != null) {
			rangeFilterBuilder.cache(rangeTermFilter.isCached());
		}

		rangeFilterBuilder.from(rangeTermFilter.getLowerBound());
		rangeFilterBuilder.includeLower(rangeTermFilter.isIncludesLower());
		rangeFilterBuilder.includeUpper(rangeTermFilter.isIncludesUpper());
		rangeFilterBuilder.to(rangeTermFilter.getUpperBound());

		return rangeFilterBuilder;
	}

}