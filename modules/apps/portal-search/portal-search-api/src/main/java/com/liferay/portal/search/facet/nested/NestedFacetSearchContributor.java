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

package com.liferay.portal.search.facet.nested;

import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Díaz
 */
@ProviderType
public interface NestedFacetSearchContributor {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<NestedFacetBuilder> nestedFacetBuilderConsumer);

	@ProviderType
	public interface NestedFacetBuilder {

		public NestedFacetBuilder aggregationName(String aggregationName);

		public NestedFacetBuilder fieldToAggregate(String fieldToAggregate);

		public NestedFacetBuilder filterField(String filterField);

		public NestedFacetBuilder filterValue(String filterValue);

		public NestedFacetBuilder frequencyThreshold(int frequencyThreshold);

		public NestedFacetBuilder maxTerms(int maxTerms);

		public NestedFacetBuilder path(String path);

		public NestedFacetBuilder selectedValues(String... selectedValues);

	}

}