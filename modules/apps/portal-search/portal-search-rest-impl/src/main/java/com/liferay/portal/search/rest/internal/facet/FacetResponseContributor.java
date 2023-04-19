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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.rest.dto.v1_0.Facet;
import com.liferay.portal.search.rest.dto.v1_0.SearchResponse;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = FacetResponseContributor.class)
public class FacetResponseContributor {

	public void contribute(
		long companyId, Facet[] facets, Locale locale,
		com.liferay.portal.search.searcher.SearchResponse portalSearchResponse,
		SearchResponse searchResponse, long userId) {

		Map<String, Object> facetResults = _toFacets(
			companyId, facets, locale, portalSearchResponse, userId);

		if (MapUtil.isNotEmpty(facetResults)) {
			searchResponse.setFacets(facetResults);
		}
	}

	private Object _getAttribute(Facet facet, String key) {
		Map<String, Object> attributes = facet.getAttributes();

		if ((attributes == null) || !attributes.containsKey(key)) {
			return null;
		}

		return attributes.get(key);
	}

	private String _getCategoryDisplayName(Locale locale, long categoryId) {
		if (!_hasViewPermissionToAssetCategory(categoryId)) {
			return null;
		}

		AssetCategory assetCategory = _assetCategoryLocalService.fetchCategory(
			categoryId);

		if (assetCategory != null) {
			return assetCategory.getTitle(locale);
		}

		return null;
	}

	private String _getDisplayName(
		long companyId, Facet facet, Locale locale,
		com.liferay.portal.search.searcher.SearchResponse portalSearchResponse,
		String term, long userId) {

		if (StringUtil.equals("category", facet.getName())) {
			if (term.contains("-")) {
				String[] termParts = term.split("-");

				return _getCategoryDisplayName(
					locale, GetterUtil.getLong(termParts[1]));
			}

			return _getCategoryDisplayName(locale, GetterUtil.getLong(term));
		}
		else if (StringUtil.equals("folder", facet.getName())) {
			return _getFolderDisplayName(
				companyId, locale, GetterUtil.getLong(term), userId);
		}
		else if (StringUtil.equals("site", facet.getName())) {
			if (!portalSearchResponse.withSearchContextGet(
					searchContext -> ArrayUtil.contains(
						searchContext.getGroupIds(),
						GetterUtil.getLong(term)))) {

				return null;
			}

			return _getSiteDisplayName(GetterUtil.getLong(term), locale);
		}
		else if (StringUtil.equals("type", facet.getName())) {
			return _getTypeDisplayName(locale, term);
		}

		return term;
	}

	private String _getFolderDisplayName(
		long companyId, Locale locale, long folderId, long userId) {

		if (folderId == 0) {
			return null;
		}

		com.liferay.portal.search.searcher.SearchResponse searchResponse =
			_searchFolder(companyId, folderId, locale, userId);

		SearchHits searchHits = searchResponse.getSearchHits();

		if (searchHits.getTotalHits() == 0) {
			return null;
		}

		List<SearchHit> hits = searchHits.getSearchHits();

		SearchHit searchHit = hits.get(0);

		Document document = searchHit.getDocument();

		Map<String, Field> fieldsMap = document.getFields();

		for (Map.Entry<String, Field> entry : fieldsMap.entrySet()) {
			if (StringUtil.startsWith(
					entry.getKey(),
					com.liferay.portal.kernel.search.Field.TITLE)) {

				Field field = entry.getValue();

				return (String)field.getValue();
			}
		}

		return null;
	}

	private String _getSiteDisplayName(long groupId, Locale locale) {
		Group group = _groupLocalService.fetchGroup(groupId);

		try {
			String name = group.getDescriptiveName(locale);

			if (group.isStagingGroup()) {
				name = StringBundler.concat(
					name, StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
					_language.get(locale, "staged"),
					StringPool.CLOSE_PARENTHESIS);
			}

			return name;
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	private String _getTerm(Facet facet, String term) {
		if (StringUtil.equals("category", facet.getName()) &&
			term.contains("-")) {

			String[] termParts = term.split("-");

			return termParts[1];
		}

		return term;
	}

	private String _getTypeDisplayName(Locale locale, String className) {
		if (className.startsWith(ObjectDefinition.class.getName() + "#")) {
			String[] parts = StringUtil.split(className, "#");

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					Long.valueOf(parts[1]));

			if (objectDefinition != null) {
				return objectDefinition.getLabel(locale);
			}
		}
		else {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			if (assetRendererFactory != null) {
				return assetRendererFactory.getTypeName(locale);
			}
		}

		return null;
	}

	private boolean _hasViewPermissionToAssetCategory(long categoryId) {
		try {
			return AssetCategoryPermission.contains(
				PermissionThreadLocal.getPermissionChecker(), categoryId,
				ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private com.liferay.portal.search.searcher.SearchResponse _searchFolder(
		long companyId, long folderId, Locale locale, long userId) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.modelIndexerClassNames(
			"com.liferay.bookmarks.model.BookmarksFolder",
			"com.liferay.document.library.kernel.model.DLFolder",
			"com.liferay.journal.model.JournalFolder"
		).emptySearchEnabled(
			true
		).size(
			1
		).fields(
			_localization.getLocalizedName(
				com.liferay.portal.kernel.search.Field.TITLE,
				_language.getLanguageId(locale))
		).withSearchContext(
			searchContext -> {
				searchContext.setCompanyId(companyId);
				searchContext.setFolderIds(new long[] {folderId});
				searchContext.setUserId(userId);
			}
		);

		return _searcher.search(searchRequestBuilder.build());
	}

	private JSONArray _toAssetCategoryTreeJSONArray(
		Facet facet, Locale locale, List<TermCollector> termCollectors) {

		Map<Long, AssetCategoryTree> assetCategoryTrees = new HashMap<>();

		for (int i = 0; i < termCollectors.size(); i++) {
			TermCollector termCollector = termCollectors.get(i);

			if ((facet.getFrequencyThreshold() >
					termCollector.getFrequency()) ||
				(i >= facet.getMaxTerms())) {

				continue;
			}

			try {
				String[] termParts = StringUtil.split(
					termCollector.getTerm(), "-");

				long categoryId = GetterUtil.getLong(termParts[1]);

				long vocabularyId = GetterUtil.getLong(termParts[0]);

				AssetVocabulary assetVocabulary =
					_assetVocabularyLocalService.getVocabulary(vocabularyId);

				AssetCategoryTree assetCategoryTree = assetCategoryTrees.get(
					assetVocabulary.getVocabularyId());

				if (assetCategoryTree == null) {
					assetCategoryTree = new AssetCategoryTree(
						assetVocabulary, locale);

					assetCategoryTrees.put(vocabularyId, assetCategoryTree);
				}

				assetCategoryTree.addCategory(
					categoryId, termCollector.getFrequency());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		return _jsonFactory.createJSONArray(assetCategoryTrees.values());
	}

	private Map<String, Object> _toFacets(
		long companyId, Facet[] facets, Locale locale,
		com.liferay.portal.search.searcher.SearchResponse portalSearchResponse,
		long userId) {

		if (ArrayUtil.isEmpty(facets)) {
			return null;
		}

		Map<String, Object> facetResults = new HashMap<>();

		for (Facet facet : facets) {
			com.liferay.portal.kernel.search.facet.Facet portalFacet =
				portalSearchResponse.withFacetContextGet(
					facetContext -> facetContext.getFacet(facet.getName()));

			if (portalFacet == null) {
				continue;
			}

			FacetCollector facetCollector = portalFacet.getFacetCollector();

			List<TermCollector> termCollectors =
				facetCollector.getTermCollectors();

			if (ListUtil.isEmpty(termCollectors)) {
				continue;
			}

			JSONArray termJSONArray = _toTermJSONArray(
				companyId, facet, locale, portalFacet, portalSearchResponse,
				termCollectors, userId);

			if (termJSONArray.length() > 0) {
				facetResults.put(facet.getAggregationName(), termJSONArray);
			}
		}

		return facetResults;
	}

	private JSONArray _toTermJSONArray(
		long companyId, Facet facet, Locale locale,
		com.liferay.portal.kernel.search.facet.Facet portalFacet,
		com.liferay.portal.search.searcher.SearchResponse portalSearchResponse,
		List<TermCollector> termCollectors, long userId) {

		if (StringUtil.equals("category", facet.getName()) &&
			StringUtil.equals(
				portalFacet.getFieldName(), "assetVocabularyCategoryIds") &&
			StringUtil.equals((String)_getAttribute(facet, "mode"), "tree")) {

			return _toAssetCategoryTreeJSONArray(facet, locale, termCollectors);
		}

		JSONArray termJSONArray = _jsonFactory.createJSONArray();

		for (int i = 0; i < termCollectors.size(); i++) {
			TermCollector termCollector = termCollectors.get(i);

			if ((facet.getFrequencyThreshold() >
					termCollector.getFrequency()) ||
				(i >= facet.getMaxTerms())) {

				continue;
			}

			String displayName = _getDisplayName(
				companyId, facet, locale, portalSearchResponse,
				termCollector.getTerm(), userId);

			if (Validator.isBlank(displayName)) {
				continue;
			}

			termJSONArray.put(
				JSONUtil.put(
					"displayName", displayName
				).put(
					"frequency", termCollector.getFrequency()
				).put(
					"term", _getTerm(facet, termCollector.getTerm())
				));
		}

		return termJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacetResponseContributor.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}