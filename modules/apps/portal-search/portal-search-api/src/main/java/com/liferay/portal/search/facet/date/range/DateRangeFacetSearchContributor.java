/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.date.range;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Petteri Karttunen
 */
@ProviderType
public interface DateRangeFacetSearchContributor {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<DateRangeFacetBuilder> dateRangeFacetBuilderConsumer);

	@ProviderType
	public interface DateRangeFacetBuilder {

		public DateRangeFacetBuilder aggregationName(String aggregationName);

		public DateRangeFacetBuilder field(String field);

		public DateRangeFacetBuilder format(String format);

		public DateRangeFacetBuilder frequencyThreshold(int frequencyThreshold);

		public DateRangeFacetBuilder maxTerms(int maxTerms);

		public DateRangeFacetBuilder order(String order);

		public DateRangeFacetBuilder rangesJSONArray(JSONArray rangesJSONArray);

		public DateRangeFacetBuilder selectedRanges(String... selectedRanges);

	}

}