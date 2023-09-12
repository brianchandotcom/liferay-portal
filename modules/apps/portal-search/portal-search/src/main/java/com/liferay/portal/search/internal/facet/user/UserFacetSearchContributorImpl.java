/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.user;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.user.UserFacetFactory;
import com.liferay.portal.search.facet.user.UserFacetSearchContributor;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = UserFacetSearchContributor.class)
public class UserFacetSearchContributorImpl
	implements UserFacetSearchContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<UserFacetBuilder> userFacetBuilderConsumer) {

		Facet facet = searchRequestBuilder.withSearchContextGet(
			searchContext -> {
				UserFacetBuilderImpl userFacetBuilderImpl =
					new UserFacetBuilderImpl(searchContext);

				userFacetBuilderConsumer.accept(userFacetBuilderImpl);

				return userFacetBuilderImpl.build();
			});

		searchRequestBuilder.withFacetContext(
			facetContext -> facetContext.addFacet(facet));
	}

	@Reference
	private UserFacetFactory _userFacetFactory;

	private class UserFacetBuilderImpl implements UserFacetBuilder {

		public UserFacetBuilderImpl(SearchContext searchContext) {
			_searchContext = searchContext;
		}

		@Override
		public UserFacetBuilder aggregationName(String aggregationName) {
			_aggregationName = aggregationName;

			return this;
		}

		public Facet build() {
			Facet facet = _userFacetFactory.newInstance(_searchContext);

			facet.setAggregationName(_aggregationName);
			facet.setFacetConfiguration(buildFacetConfiguration(facet));

			if (_selectedUserIds != null) {
				facet.select(ArrayUtil.toStringArray(_selectedUserIds));
			}

			return facet;
		}

		@Override
		public UserFacetBuilder frequencyThreshold(int frequencyThreshold) {
			_frequencyThreshold = frequencyThreshold;

			return this;
		}

		@Override
		public UserFacetBuilder maxTerms(int maxTerms) {
			_maxTerms = maxTerms;

			return this;
		}

		@Override
		public UserFacetBuilder selectedUserIds(long... selectedUserIds) {
			_selectedUserIds = selectedUserIds;

			return this;
		}

		protected FacetConfiguration buildFacetConfiguration(Facet facet) {
			FacetConfiguration facetConfiguration = new FacetConfiguration();

			facetConfiguration.setFieldName(facet.getFieldName());
			facetConfiguration.setLabel("any-user");
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
		private int _frequencyThreshold;
		private int _maxTerms;
		private final SearchContext _searchContext;
		private long[] _selectedUserIds;

	}

}