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
import com.liferay.portal.search.rest.dto.v1_0.Facet;
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
		Facet[] facets, SearchRequestBuilder searchRequestBuilder) {

		for (Facet facet : facets) {
			_setProperties(facet);

			if (StringUtil.equals("category", facet.getName())) {
				_contributeCategoryFacet(facet, searchRequestBuilder);
			}
			else if (StringUtil.equals("custom", facet.getName())) {
				_contributeCustomFacet(facet, searchRequestBuilder);
			}
			else if (StringUtil.equals("date-range", facet.getName())) {
				_contributeDateRangeFacet(facet, searchRequestBuilder);
			}
			else if (StringUtil.equals("folder", facet.getName())) {
				_contributeFolderFacet(facet, searchRequestBuilder);
			}
			else if (StringUtil.equals("nested", facet.getName())) {
				_contributeNestedFacet(facet, searchRequestBuilder);
			}
			else if (StringUtil.equals("site", facet.getName())) {
				_contributeSiteFacet(facet, searchRequestBuilder);
			}
			else if (StringUtil.equals("tag", facet.getName())) {
				_contributeTagFacet(facet, searchRequestBuilder);
			}
			else if (StringUtil.equals("type", facet.getName())) {
				_contributeTypeFacet(facet, searchRequestBuilder);
			}
			else if (StringUtil.equals("user", facet.getName())) {
				_contributeUserFacet(facet, searchRequestBuilder);
			}
		}
	}

	private void _contributeCategoryFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		_categoryFacetSearchContributor.contribute(
			searchRequestBuilder,
			categoryFacetBuilder -> categoryFacetBuilder.aggregationName(
				facet.getAggregationName()
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).maxTerms(
				facet.getMaxTerms()
			).selectedCategoryIds(
				_toLongArray(facet.getValues())
			).vocabularyIds(
				_getVocabularyIdsAttribute(facet)
			));
	}

	private void _contributeCustomFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		if (!_hasAttributes(facet, "field")) {
			return;
		}

		_customFacetSearchContributor.contribute(
			searchRequestBuilder,
			customFacetBuilder -> customFacetBuilder.aggregationName(
				facet.getAggregationName()
			).fieldToAggregate(
				GetterUtil.getString(_getAttribute(facet, "field"))
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).maxTerms(
				facet.getMaxTerms()
			).selectedValues(
				_toStringArray(facet.getValues())
			));
	}

	private void _contributeDateRangeFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		if (!_hasAttributes(facet, "field", "format", "ranges")) {
			return;
		}

		_dateRangeFacetSearchContributor.contribute(
			searchRequestBuilder,
			dateRangeFacetBuilder -> dateRangeFacetBuilder.aggregationName(
				facet.getAggregationName()
			).field(
				GetterUtil.getString(_getAttribute(facet, "field"))
			).format(
				GetterUtil.getString(_getAttribute(facet, "format"))
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).maxTerms(
				facet.getMaxTerms()
			).rangesJSONArray(
				_jsonFactory.createJSONArray(
					(List<Map<String, Object>>)_getAttribute(facet, "ranges"))
			).selectedRanges(
				_toStringArray(facet.getValues())
			));
	}

	private void _contributeFolderFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		_folderFacetSearchContributor.contribute(
			searchRequestBuilder,
			folderFacetBuilder -> folderFacetBuilder.aggregationName(
				facet.getAggregationName()
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).maxTerms(
				facet.getMaxTerms()
			).selectedFolderIds(
				_toLongArray(facet.getValues())
			));
	}

	private void _contributeNestedFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		if (!_hasAttributes(
				facet, "field", "filterField", "filterValue", "path")) {

			return;
		}

		_nestedFacetSearchContributor.contribute(
			searchRequestBuilder,
			nestedFacetBuilder -> nestedFacetBuilder.aggregationName(
				facet.getAggregationName()
			).fieldToAggregate(
				GetterUtil.getString(_getAttribute(facet, "field"))
			).filterField(
				GetterUtil.getString(_getAttribute(facet, "filterField"))
			).filterValue(
				GetterUtil.getString(_getAttribute(facet, "filterValue"))
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).maxTerms(
				facet.getMaxTerms()
			).path(
				GetterUtil.getString(_getAttribute(facet, "path"))
			).selectedValues(
				_toStringArray(facet.getValues())
			));
	}

	private void _contributeSiteFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		_siteFacetSearchContributor.contribute(
			searchRequestBuilder,
			siteFacetBuilder -> siteFacetBuilder.aggregationName(
				facet.getAggregationName()
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).maxTerms(
				facet.getMaxTerms()
			).selectedGroupIds(
				_toStringArray(facet.getValues())
			));
	}

	private void _contributeTagFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		_tagFacetSearchContributor.contribute(
			searchRequestBuilder,
			tagFacetBuilder -> tagFacetBuilder.aggregationName(
				facet.getAggregationName()
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).maxTerms(
				facet.getMaxTerms()
			).selectedTagNames(
				_toStringArray(facet.getValues())
			));
	}

	private void _contributeTypeFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		_typeFacetSearchContributor.contribute(
			searchRequestBuilder,
			typeFacetBuilder -> typeFacetBuilder.aggregationName(
				facet.getAggregationName()
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).selectedEntryClassNames(
				_toStringArray(facet.getValues())
			));
	}

	private void _contributeUserFacet(
		Facet facet, SearchRequestBuilder searchRequestBuilder) {

		_userFacetSearchContributor.contribute(
			searchRequestBuilder,
			userFacetBuilder -> userFacetBuilder.aggregationName(
				facet.getAggregationName()
			).frequencyThreshold(
				facet.getFrequencyThreshold()
			).maxTerms(
				facet.getMaxTerms()
			).selectedUserNames(
				_toStringArray(facet.getValues())
			));
	}

	private Object _getAttribute(Facet facet, String key) {
		Map<String, Object> attributes = facet.getAttributes();

		return attributes.get(key);
	}

	private String[] _getVocabularyIdsAttribute(Facet facet) {
		if (!_hasAttributes(facet, "vocabularyIds")) {
			return new String[0];
		}

		Map<String, Object> attributes = facet.getAttributes();

		List<String> vocabularyIds = (List)attributes.get("vocabularyIds");

		return vocabularyIds.toArray(new String[0]);
	}

	private boolean _hasAttributes(Facet facet, String... keys) {
		Map<String, Object> attributes = facet.getAttributes();

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

	private void _setProperties(Facet facet) {
		if (Validator.isBlank(facet.getAggregationName())) {
			facet.setAggregationName(facet.getName());
		}

		facet.setFrequencyThreshold(
			_toInt(1, facet.getFrequencyThreshold(), 0));
		facet.setMaxTerms(_toInt(10, facet.getMaxTerms(), 0));
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