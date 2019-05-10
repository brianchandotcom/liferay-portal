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

package com.liferay.portal.search.facet.site;

import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface SiteFacetSearchContributor {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<SiteFacetBuilder> siteFacetBuilderConsumer);

	@ProviderType
	public interface SiteFacetBuilder {

		public SiteFacetBuilder aggregationName(String aggregationName);

		public SiteFacetBuilder frequencyThreshold(int frequencyThreshold);

		public SiteFacetBuilder maxTerms(int maxTerms);

		public SiteFacetBuilder selectedGroupIds(String... selectedGroupIds);

	}

}