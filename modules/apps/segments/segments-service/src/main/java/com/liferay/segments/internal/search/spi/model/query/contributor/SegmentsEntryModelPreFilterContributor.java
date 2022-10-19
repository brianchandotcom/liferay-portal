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

package com.liferay.segments.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.LinkedHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	property = "indexer.class.name=com.liferay.segments.model.SegmentsEntry",
	service = ModelPreFilterContributor.class
)
public class SegmentsEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		long[] excludedSegmentsEntryIds = (long[])params.get(
			"excludedSegmentsEntryIds");

		if (ArrayUtil.isNotEmpty(excludedSegmentsEntryIds)) {
			TermsFilter entryClassPKTermFilter = new TermsFilter(
				Field.ENTRY_CLASS_PK);

			entryClassPKTermFilter.addValues(
				ArrayUtil.toStringArray(excludedSegmentsEntryIds));

			booleanFilter.add(
				entryClassPKTermFilter, BooleanClauseOccur.MUST_NOT);
		}

		String[] excludedSources = (String[])params.get("excludedSources");

		if (ArrayUtil.isNotEmpty(excludedSources)) {
			TermsFilter sourceTermsFilter = new TermsFilter("source");

			sourceTermsFilter.addValues(
				ArrayUtil.toStringArray(excludedSources));

			booleanFilter.add(sourceTermsFilter, BooleanClauseOccur.MUST_NOT);
		}

		long[] roleIds = (long[])params.get("roleIds");

		if (ArrayUtil.isNotEmpty(roleIds)) {
			TermsFilter roleIdsTermsFilter = new TermsFilter("roleIds");

			roleIdsTermsFilter.addValues(ArrayUtil.toStringArray(roleIds));

			booleanFilter.add(roleIdsTermsFilter, BooleanClauseOccur.MUST);
		}
	}

}