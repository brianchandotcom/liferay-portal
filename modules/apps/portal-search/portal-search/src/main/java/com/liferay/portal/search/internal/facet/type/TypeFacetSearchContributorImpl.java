/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.type;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.type.AssetEntriesFacetFactory;
import com.liferay.portal.search.facet.type.TypeFacetSearchContributor;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = TypeFacetSearchContributor.class)
public class TypeFacetSearchContributorImpl
	implements TypeFacetSearchContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<TypeFacetBuilder> typeFacetBuilderConsumer) {

		Facet facet = searchRequestBuilder.withSearchContextGet(
			searchContext -> {
				TypeFacetBuilderImpl typeFacetBuilderImpl =
					new TypeFacetBuilderImpl(searchContext);

				typeFacetBuilderConsumer.accept(typeFacetBuilderImpl);

				return typeFacetBuilderImpl.build();
			});

		searchRequestBuilder.withFacetContext(
			facetContext -> facetContext.addFacet(facet));
	}

	@Reference
	private AssetEntriesFacetFactory _assetEntriesFacetFactory;

	private class TypeFacetBuilderImpl implements TypeFacetBuilder {

		public TypeFacetBuilderImpl(SearchContext searchContext) {
			_searchContext = searchContext;
		}

		@Override
		public TypeFacetBuilder aggregationName(String portletId) {
			_aggregationName = portletId;

			return this;
		}

		public Facet build() {
			Facet facet = _assetEntriesFacetFactory.newInstance(_searchContext);

			facet.setAggregationName(_aggregationName);
			facet.setFacetConfiguration(buildFacetConfiguration(facet));

			facet.select(_selectedEntryClassNames);

			return facet;
		}

		@Override
		public TypeFacetBuilder frequencyThreshold(int frequencyThreshold) {
			_frequencyThreshold = frequencyThreshold;

			return this;
		}

		@Override
		public TypeFacetBuilder selectedEntryClassNames(
			String... selectedEntryClassNames) {

			_selectedEntryClassNames = selectedEntryClassNames;

			return this;
		}

		protected FacetConfiguration buildFacetConfiguration(Facet facet) {
			FacetConfiguration facetConfiguration = new FacetConfiguration();

			facetConfiguration.setFieldName(facet.getFieldName());
			facetConfiguration.setLabel("any-asset");
			facetConfiguration.setOrder("OrderHitsDesc");
			facetConfiguration.setStatic(false);
			facetConfiguration.setWeight(1.6);

			JSONObject jsonObject = facetConfiguration.getData();

			jsonObject.put("frequencyThreshold", _frequencyThreshold);

			return facetConfiguration;
		}

		private String _aggregationName;
		private int _frequencyThreshold;
		private final SearchContext _searchContext;
		private String[] _selectedEntryClassNames;

	}

}