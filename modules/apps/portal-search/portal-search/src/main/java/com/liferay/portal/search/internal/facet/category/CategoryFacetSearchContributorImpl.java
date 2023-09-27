/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.category;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.category.CategoryFacetFactory;
import com.liferay.portal.search.facet.category.CategoryFacetSearchContributor;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = CategoryFacetSearchContributor.class)
public class CategoryFacetSearchContributorImpl
	implements CategoryFacetSearchContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<CategoryFacetBuilder> categoryFacetBuilderConsumer) {

		Facet facet = searchRequestBuilder.withSearchContextGet(
			searchContext -> {
				CategoryFacetBuilderImpl categoryFacetBuilderImpl =
					new CategoryFacetBuilderImpl(searchContext);

				categoryFacetBuilderConsumer.accept(categoryFacetBuilderImpl);

				return categoryFacetBuilderImpl.build();
			});

		searchRequestBuilder.withFacetContext(
			facetContext -> facetContext.addFacet(facet));
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CategoryFacetFactory _categoryFacetFactory;

	private class CategoryFacetBuilderImpl implements CategoryFacetBuilder {

		public CategoryFacetBuilderImpl(SearchContext searchContext) {
			_searchContext = searchContext;
		}

		@Override
		public CategoryFacetBuilder aggregationName(String aggregationName) {
			_aggregationName = aggregationName;

			return this;
		}

		public Facet build() {
			Facet facet = _categoryFacetFactory.newInstance(_searchContext);

			facet.setAggregationName(_aggregationName);
			facet.setFacetConfiguration(buildFacetConfiguration(facet));

			if (_selectedCategoryIds != null) {
				String fieldName = facet.getFieldName();

				if (fieldName.equals("assetVocabularyCategoryIds")) {
					facet.select(_getSelections(_selectedCategoryIds));
				}
				else {
					facet.select(ArrayUtil.toStringArray(_selectedCategoryIds));
				}
			}

			return facet;
		}

		@Override
		public CategoryFacetBuilder frequencyThreshold(int frequencyThreshold) {
			_frequencyThreshold = frequencyThreshold;

			return this;
		}

		@Override
		public CategoryFacetBuilder maxTerms(int maxTerms) {
			_maxTerms = maxTerms;

			return this;
		}

		@Override
		public CategoryFacetBuilder selectedCategoryIds(
			long... selectedCategoryIds) {

			_selectedCategoryIds = selectedCategoryIds;

			return this;
		}

		@Override
		public CategoryFacetBuilder vocabularyIds(String[] vocabularyIds) {
			_vocabularyIds = vocabularyIds;

			return this;
		}

		protected FacetConfiguration buildFacetConfiguration(Facet facet) {
			FacetConfiguration facetConfiguration = new FacetConfiguration();

			facetConfiguration.setFieldName(facet.getFieldName());
			facetConfiguration.setLabel("any-category");
			facetConfiguration.setOrder("OrderHitsDesc");
			facetConfiguration.setStatic(false);
			facetConfiguration.setWeight(1.6);

			JSONObject jsonObject = facetConfiguration.getData();

			jsonObject.put(
				"frequencyThreshold", _frequencyThreshold
			).put(
				"include", _getIncludeRegexString(facet.getFieldName())
			).put(
				"maxTerms", _maxTerms
			);

			return facetConfiguration;
		}

		private String _getIncludeRegexString(String fieldName) {
			if (ArrayUtil.isEmpty(_vocabularyIds) ||
				fieldName.equals("assetCategoryIds")) {

				return null;
			}

			StringBundler sb = new StringBundler(_vocabularyIds.length * 5);

			for (String vocabularyId : _vocabularyIds) {
				sb.append(vocabularyId);
				sb.append(StringPool.DASH);
				sb.append(StringPool.PERIOD);
				sb.append(StringPool.STAR);
				sb.append(StringPool.PIPE);
			}

			if (sb.index() > 0) {
				sb.setIndex(sb.index() - 1);

				return sb.toString();
			}

			return null;
		}

		private String[] _getSelections(long[] selectedCategoryIds) {
			String[] selections = new String[selectedCategoryIds.length];

			for (int i = 0; i < selectedCategoryIds.length; i++) {
				AssetCategory assetCategory =
					_assetCategoryLocalService.fetchAssetCategory(
						selectedCategoryIds[i]);

				selections[i] =
					assetCategory.getVocabularyId() + StringPool.DASH +
						assetCategory.getCategoryId();
			}

			return selections;
		}

		private String _aggregationName;
		private int _frequencyThreshold;
		private int _maxTerms;
		private final SearchContext _searchContext;
		private long[] _selectedCategoryIds;
		private String[] _vocabularyIds;

	}

}