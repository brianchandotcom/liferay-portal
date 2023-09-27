/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.internal.facet;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.category.CategoryFacetSearchContributor;
import com.liferay.portal.search.facet.custom.CustomFacetSearchContributor;
import com.liferay.portal.search.facet.date.range.DateRangeFacetSearchContributor;
import com.liferay.portal.search.facet.folder.FolderFacetSearchContributor;
import com.liferay.portal.search.facet.nested.NestedFacetSearchContributor;
import com.liferay.portal.search.facet.site.SiteFacetSearchContributor;
import com.liferay.portal.search.facet.tag.TagFacetSearchContributor;
import com.liferay.portal.search.facet.type.TypeFacetSearchContributor;
import com.liferay.portal.search.facet.user.UserFacetSearchContributor;
import com.liferay.portal.search.rest.dto.v1_0.FacetConfiguration;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = FacetRequestContributor.class)
public class FacetRequestContributor {

	public void contribute(
		FacetConfiguration[] facetConfigurations,
		SearchRequestBuilder searchRequestBuilder) {

		for (FacetConfiguration facetConfiguration : facetConfigurations) {
			_setProperties(facetConfiguration);

			if (StringUtil.equals("category", facetConfiguration.getName()) ||
				StringUtil.equals("vocabulary", facetConfiguration.getName())) {

				_contributeCategoryFacet(
					facetConfiguration, searchRequestBuilder);
			}
			else if (StringUtil.equals(
						"custom", facetConfiguration.getName())) {

				_contributeCustomFacet(
					facetConfiguration, searchRequestBuilder);
			}
			else if (StringUtil.equals(
						"date-range", facetConfiguration.getName())) {

				_contributeDateRangeFacet(
					facetConfiguration, searchRequestBuilder);
			}
			else if (StringUtil.equals(
						"folder", facetConfiguration.getName())) {

				_contributeFolderFacet(
					facetConfiguration, searchRequestBuilder);
			}
			else if (StringUtil.equals(
						"nested", facetConfiguration.getName())) {

				_contributeNestedFacet(
					facetConfiguration, searchRequestBuilder);
			}
			else if (StringUtil.equals("site", facetConfiguration.getName())) {
				_contributeSiteFacet(facetConfiguration, searchRequestBuilder);
			}
			else if (StringUtil.equals("tag", facetConfiguration.getName())) {
				_contributeTagFacet(facetConfiguration, searchRequestBuilder);
			}
			else if (StringUtil.equals("type", facetConfiguration.getName())) {
				_contributeTypeFacet(facetConfiguration, searchRequestBuilder);
			}
			else if (StringUtil.equals("user", facetConfiguration.getName())) {
				_contributeUserFacet(facetConfiguration, searchRequestBuilder);
			}
		}
	}

	private void _contributeCategoryFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		_categoryFacetSearchContributor.contribute(
			searchRequestBuilder,
			categoryFacetBuilder -> categoryFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).maxTerms(
				facetConfiguration.getMaxTerms()
			).selectedCategoryIds(
				_toLongArray(facetConfiguration.getValues())
			).vocabularyIds(
				_getVocabularyIdsAttribute(facetConfiguration)
			));
	}

	private void _contributeCustomFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		if (!_hasAttributes(facetConfiguration, "field")) {
			return;
		}

		_customFacetSearchContributor.contribute(
			searchRequestBuilder,
			customFacetBuilder -> customFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).fieldToAggregate(
				GetterUtil.getString(_getAttribute(facetConfiguration, "field"))
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).maxTerms(
				facetConfiguration.getMaxTerms()
			).selectedValues(
				_toStringArray(facetConfiguration.getValues())
			));
	}

	private void _contributeDateRangeFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		if (!_hasAttributes(facetConfiguration, "field", "format", "ranges")) {
			return;
		}

		_dateRangeFacetSearchContributor.contribute(
			searchRequestBuilder,
			dateRangeFacetBuilder -> dateRangeFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).field(
				GetterUtil.getString(_getAttribute(facetConfiguration, "field"))
			).format(
				GetterUtil.getString(
					_getAttribute(facetConfiguration, "format"))
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).maxTerms(
				facetConfiguration.getMaxTerms()
			).rangesJSONArray(
				_jsonFactory.createJSONArray(
					(List<Map<String, Object>>)_getAttribute(
						facetConfiguration, "ranges"))
			).selectedRanges(
				_toStringArray(facetConfiguration.getValues())
			));
	}

	private void _contributeFolderFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		_folderFacetSearchContributor.contribute(
			searchRequestBuilder,
			folderFacetBuilder -> folderFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).maxTerms(
				facetConfiguration.getMaxTerms()
			).selectedFolderIds(
				_toLongArray(facetConfiguration.getValues())
			));
	}

	private void _contributeNestedFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		if (!_hasAttributes(
				facetConfiguration, "field", "filterField", "filterValue",
				"path")) {

			return;
		}

		_nestedFacetSearchContributor.contribute(
			searchRequestBuilder,
			nestedFacetBuilder -> nestedFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).fieldToAggregate(
				GetterUtil.getString(_getAttribute(facetConfiguration, "field"))
			).filterField(
				GetterUtil.getString(
					_getAttribute(facetConfiguration, "filterField"))
			).filterValue(
				GetterUtil.getString(
					_getAttribute(facetConfiguration, "filterValue"))
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).maxTerms(
				facetConfiguration.getMaxTerms()
			).path(
				GetterUtil.getString(_getAttribute(facetConfiguration, "path"))
			).selectedValues(
				_toStringArray(facetConfiguration.getValues())
			));
	}

	private void _contributeSiteFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		_siteFacetSearchContributor.contribute(
			searchRequestBuilder,
			siteFacetBuilder -> siteFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).maxTerms(
				facetConfiguration.getMaxTerms()
			).selectedGroupIds(
				_toStringArray(facetConfiguration.getValues())
			));
	}

	private void _contributeTagFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		_tagFacetSearchContributor.contribute(
			searchRequestBuilder,
			tagFacetBuilder -> tagFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).maxTerms(
				facetConfiguration.getMaxTerms()
			).selectedTagNames(
				_toStringArray(facetConfiguration.getValues())
			));
	}

	private void _contributeTypeFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		_typeFacetSearchContributor.contribute(
			searchRequestBuilder,
			typeFacetBuilder -> typeFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).selectedEntryClassNames(
				_toStringArray(facetConfiguration.getValues())
			));
	}

	private void _contributeUserFacet(
		FacetConfiguration facetConfiguration,
		SearchRequestBuilder searchRequestBuilder) {

		_userFacetSearchContributor.contribute(
			searchRequestBuilder,
			userFacetBuilder -> userFacetBuilder.aggregationName(
				facetConfiguration.getAggregationName()
			).frequencyThreshold(
				facetConfiguration.getFrequencyThreshold()
			).maxTerms(
				facetConfiguration.getMaxTerms()
			).selectedUserIds(
				_toLongArray(facetConfiguration.getValues())
			));
	}

	private Object _getAttribute(
		FacetConfiguration facetConfiguration, String key) {

		Map<String, Object> attributes = facetConfiguration.getAttributes();

		return attributes.get(key);
	}

	private String[] _getVocabularyIdsAttribute(
		FacetConfiguration facetConfiguration) {

		if (!_hasAttributes(facetConfiguration, "vocabularyIds")) {
			return new String[0];
		}

		Map<String, Object> attributes = facetConfiguration.getAttributes();

		List<String> vocabularyIds = (List)attributes.get("vocabularyIds");

		return vocabularyIds.toArray(new String[0]);
	}

	private boolean _hasAttributes(
		FacetConfiguration facetConfiguration, String... keys) {

		Map<String, Object> attributes = facetConfiguration.getAttributes();

		if (MapUtil.isEmpty(attributes)) {
			return false;
		}

		for (String key : keys) {
			if (!attributes.containsKey(key)) {
				return false;
			}
		}

		return true;
	}

	private void _setProperties(FacetConfiguration facetConfiguration) {
		if (Validator.isBlank(facetConfiguration.getAggregationName())) {
			facetConfiguration.setAggregationName(facetConfiguration.getName());
		}

		facetConfiguration.setFrequencyThreshold(
			_toInt(1, facetConfiguration.getFrequencyThreshold(), 0));
		facetConfiguration.setMaxTerms(
			_toInt(10, facetConfiguration.getMaxTerms(), 0));
	}

	private int _toInt(int defaultValue, Integer value, int minValue) {
		if ((value == null) || (value < minValue)) {
			return defaultValue;
		}

		return value;
	}

	private long[] _toLongArray(Object[] values) {
		if (!ArrayUtil.isEmpty(values)) {
			return ListUtil.toLongArray(
				Arrays.asList(values), GetterUtil::getLong);
		}

		return new long[0];
	}

	private String[] _toStringArray(Object[] values) {
		if (!ArrayUtil.isEmpty(values)) {
			return ArrayUtil.toStringArray(values);
		}

		return new String[0];
	}

	@Reference
	private CategoryFacetSearchContributor _categoryFacetSearchContributor;

	@Reference
	private CustomFacetSearchContributor _customFacetSearchContributor;

	@Reference
	private DateRangeFacetSearchContributor _dateRangeFacetSearchContributor;

	@Reference
	private FolderFacetSearchContributor _folderFacetSearchContributor;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NestedFacetSearchContributor _nestedFacetSearchContributor;

	@Reference
	private SiteFacetSearchContributor _siteFacetSearchContributor;

	@Reference
	private TagFacetSearchContributor _tagFacetSearchContributor;

	@Reference
	private TypeFacetSearchContributor _typeFacetSearchContributor;

	@Reference
	private UserFacetSearchContributor _userFacetSearchContributor;

}