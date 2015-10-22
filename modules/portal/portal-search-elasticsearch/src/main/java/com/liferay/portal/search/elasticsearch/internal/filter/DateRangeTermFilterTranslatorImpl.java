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

import com.liferay.portal.kernel.search.filter.DateRangeTermFilter;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.search.elasticsearch.filter.DateRangeTermFilterTranslator;

import java.text.Format;
import java.text.ParseException;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.RangeFilterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DateRangeTermFilterTranslator.class)
public class DateRangeTermFilterTranslatorImpl
	implements DateRangeTermFilterTranslator {

	@Override
	public FilterBuilder translate(DateRangeTermFilter dateRangeTermFilter) {
		RangeFilterBuilder rangeFilterBuilder = FilterBuilders.rangeFilter(
			dateRangeTermFilter.getField());

		if (dateRangeTermFilter.isCached() != null) {
			rangeFilterBuilder.cache(dateRangeTermFilter.isCached());
		}

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			dateRangeTermFilter.getDateFormat(),
			dateRangeTermFilter.getTimeZone());

		try {
			rangeFilterBuilder.from(
				format.parseObject(dateRangeTermFilter.getLowerBound()));
			rangeFilterBuilder.includeLower(
				dateRangeTermFilter.isIncludesLower());
			rangeFilterBuilder.includeUpper(
				dateRangeTermFilter.isIncludesUpper());
			rangeFilterBuilder.to(
				format.parseObject(dateRangeTermFilter.getUpperBound()));
		}
		catch (ParseException e) {
			throw new IllegalArgumentException(
				"Invalid date range " + dateRangeTermFilter, e);
		}

		return rangeFilterBuilder;
	}

}