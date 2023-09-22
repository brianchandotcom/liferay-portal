/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.range;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Petteri Karttunen
 */
@ProviderType
public interface RangeFacetSearchContributor {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<RangeFacetBuilder> rangeFacetBuilderConsumer);

	@ProviderType
	public interface RangeFacetBuilder {

		public RangeFacetBuilder aggregationName(String aggregationName);

		public RangeFacetBuilder field(String field);

		public RangeFacetBuilder format(String format);

		public RangeFacetBuilder frequencyThreshold(int frequencyThreshold);

		public RangeFacetBuilder order(String order);

		public RangeFacetBuilder rangesJSONArray(JSONArray rangesJSONArray);

		public RangeFacetBuilder selectedRanges(String... selectedRanges);

	}

}