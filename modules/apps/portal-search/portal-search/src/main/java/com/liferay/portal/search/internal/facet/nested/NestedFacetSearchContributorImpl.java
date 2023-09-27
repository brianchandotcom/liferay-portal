/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.nested;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.nested.NestedFacetFactory;
import com.liferay.portal.search.facet.nested.NestedFacetSearchContributor;
import com.liferay.portal.search.internal.facet.NestedFacetImpl;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Díaz
 */
@Component(service = NestedFacetSearchContributor.class)
public class NestedFacetSearchContributorImpl
	implements NestedFacetSearchContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<NestedFacetBuilder> nestedFacetBuilderConsumer) {

		Facet facet = searchRequestBuilder.withSearchContextGet(
			searchContext -> {
				NestedFacetBuilderImpl nestedFacetBuilderImpl =
					new NestedFacetBuilderImpl(searchContext);

				nestedFacetBuilderConsumer.accept(nestedFacetBuilderImpl);

				return nestedFacetBuilderImpl.build();
			});

		searchRequestBuilder.withFacetContext(
			facetContext -> facetContext.addFacet(facet));
	}

	@Reference
	protected Aggregations aggregations;

	@Reference
	protected NestedFacetFactory nestedFacetFactory;

	@Reference
	protected Queries queries;

	private class NestedFacetBuilderImpl implements NestedFacetBuilder {

		public NestedFacetBuilderImpl(SearchContext searchContext) {
			_searchContext = searchContext;
		}

		@Override
		public NestedFacetBuilder aggregationName(String aggregationName) {
			_aggregationName = aggregationName;

			return this;
		}

		public Facet build() {
			NestedFacetImpl nestedFacetImpl =
				(NestedFacetImpl)nestedFacetFactory.newInstance(_searchContext);

			nestedFacetImpl.setAggregationName(_aggregationName);
			nestedFacetImpl.setFacetConfiguration(
				buildFacetConfiguration(nestedFacetImpl));
			nestedFacetImpl.setFieldName(_fieldToAggregate);
			nestedFacetImpl.setFilterField(_filterField);
			nestedFacetImpl.setFilterValue(_filterValue);
			nestedFacetImpl.setPath(_path);

			nestedFacetImpl.select(_selectedValues);

			return nestedFacetImpl;
		}

		@Override
		public NestedFacetBuilder fieldToAggregate(String fieldToAggregate) {
			_fieldToAggregate = fieldToAggregate;

			return this;
		}

		@Override
		public NestedFacetBuilder filterField(String filterField) {
			_filterField = filterField;

			return this;
		}

		@Override
		public NestedFacetBuilder filterValue(String filterValue) {
			_filterValue = filterValue;

			return this;
		}

		@Override
		public NestedFacetBuilder frequencyThreshold(int frequencyThreshold) {
			_frequencyThreshold = frequencyThreshold;

			return this;
		}

		@Override
		public NestedFacetBuilder maxTerms(int maxTerms) {
			_maxTerms = maxTerms;

			return this;
		}

		@Override
		public NestedFacetBuilder path(String path) {
			_path = path;

			return this;
		}

		@Override
		public NestedFacetBuilder selectedValues(String... selectedValues) {
			_selectedValues = selectedValues;

			return this;
		}

		protected FacetConfiguration buildFacetConfiguration(Facet facet) {
			FacetConfiguration facetConfiguration = new FacetConfiguration();

			facetConfiguration.setFieldName(facet.getFieldName());
			facetConfiguration.setOrder("OrderHitsDesc");
			facetConfiguration.setStatic(false);
			facetConfiguration.setWeight(1.1);

			JSONObject jsonObject = facetConfiguration.getData();

			jsonObject.put(
				"frequencyThreshold", _frequencyThreshold
			).put(
				"maxTerms", _maxTerms
			);

			return facetConfiguration;
		}

		private String _aggregationName;
		private String _fieldToAggregate;
		private String _filterField;
		private String _filterValue;
		private int _frequencyThreshold;
		private int _maxTerms;
		private String _path;
		private final SearchContext _searchContext;
		private String[] _selectedValues;

	}

}