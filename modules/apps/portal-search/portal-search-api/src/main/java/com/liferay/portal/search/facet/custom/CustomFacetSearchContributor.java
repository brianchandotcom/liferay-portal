/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.custom;

import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface CustomFacetSearchContributor {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<CustomFacetBuilder> customFacetBuilderConsumer);

	@ProviderType
	public interface CustomFacetBuilder {

		public CustomFacetBuilder aggregationName(String aggregationName);

		public CustomFacetBuilder fieldToAggregate(String fieldToAggregate);

		public CustomFacetBuilder frequencyThreshold(int frequencyThreshold);

		public CustomFacetBuilder maxTerms(int maxTerms);

		public CustomFacetBuilder selectedValues(String... selectedValues);

	}

}