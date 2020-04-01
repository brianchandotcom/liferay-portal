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

package com.liferay.redirect.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.text.Format;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.redirect.model.RedirectNotFoundEntry",
	service = ModelPreFilterContributor.class
)
public class RedirectNotFoundEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		Boolean ignored = (Boolean)searchContext.getAttribute("ignored");

		if (ignored != null) {
			booleanFilter.addRequiredTerm("ignored", ignored);
		}

		Date minModifiedDate = (Date)searchContext.getAttribute(
			"minModifiedDate");

		if (minModifiedDate != null) {
			DateRangeFilterBuilder dateRangeFilterBuilder =
				_filterBuilders.dateRangeFilterBuilder();

			dateRangeFilterBuilder.setFieldName(Field.MODIFIED_DATE);

			Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
				PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

			dateRangeFilterBuilder.setFrom(dateFormat.format(minModifiedDate));

			booleanFilter.add(
				dateRangeFilterBuilder.build(), BooleanClauseOccur.MUST);
		}
	}

	@Reference
	private FilterBuilders _filterBuilders;

}