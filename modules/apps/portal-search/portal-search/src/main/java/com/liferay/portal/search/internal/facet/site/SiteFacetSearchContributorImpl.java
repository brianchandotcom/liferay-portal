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

package com.liferay.portal.search.internal.facet.site;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.site.SiteFacetFactory;
import com.liferay.portal.search.facet.site.SiteFacetSearchContributor;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = SiteFacetSearchContributor.class)
public class SiteFacetSearchContributorImpl
	implements SiteFacetSearchContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<SiteFacetBuilder> siteFacetBuilderConsumer) {

		Facet facet = searchRequestBuilder.withSearchContextGet(
			searchContext -> {
				SiteFacetBuilderImpl siteFacetBuilderImpl =
					new SiteFacetBuilderImpl(searchContext);

				siteFacetBuilderConsumer.accept(siteFacetBuilderImpl);

				return siteFacetBuilderImpl.build();
			});

		searchRequestBuilder.withFacetContext(
			facetContext -> facetContext.addFacet(facet));
	}

	@Reference(unbind = "-")
	protected void setSiteFacetFactory(SiteFacetFactory siteFacetFactory) {
		_siteFacetFactory = siteFacetFactory;
	}

	private SiteFacetFactory _siteFacetFactory;

	private class SiteFacetBuilderImpl implements SiteFacetBuilder {

		public SiteFacetBuilderImpl(SearchContext searchContext) {
			_searchContext = searchContext;
		}

		@Override
		public SiteFacetBuilder aggregationName(String aggregationName) {
			_aggregationName = aggregationName;

			return this;
		}

		public Facet build() {
			Facet facet = _siteFacetFactory.newInstance(_searchContext);

			facet.setAggregationName(_aggregationName);
			facet.setFacetConfiguration(buildFacetConfiguration(facet));

			if (ArrayUtil.isNotEmpty(_selectedGroupIds)) {
				facet.select(_selectedGroupIds);
			}

			return facet;
		}

		@Override
		public SiteFacetBuilder frequencyThreshold(int frequencyThreshold) {
			_frequencyThreshold = frequencyThreshold;

			return this;
		}

		@Override
		public SiteFacetBuilder maxTerms(int maxTerms) {
			_maxTerms = maxTerms;

			return this;
		}

		@Override
		public SiteFacetBuilder selectedGroupIds(String... selectedGroupIds) {
			_selectedGroupIds = selectedGroupIds;

			return this;
		}

		protected FacetConfiguration buildFacetConfiguration(Facet facet) {
			FacetConfiguration facetConfiguration = new FacetConfiguration();

			facetConfiguration.setFieldName(facet.getFieldName());
			facetConfiguration.setLabel("any-site");
			facetConfiguration.setOrder("OrderHitsDesc");
			facetConfiguration.setStatic(false);
			facetConfiguration.setWeight(1.6);

			JSONObject jsonObject = facetConfiguration.getData();

			jsonObject.put(
				"frequencyThreshold", _frequencyThreshold
			).put(
				"maxTerms", _maxTerms
			);

			return facetConfiguration;
		}

		private String _aggregationName;
		private int _frequencyThreshold;
		private int _maxTerms;
		private final SearchContext _searchContext;
		private String[] _selectedGroupIds;

	}

}