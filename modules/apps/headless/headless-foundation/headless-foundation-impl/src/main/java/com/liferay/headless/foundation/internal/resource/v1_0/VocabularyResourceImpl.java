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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.internal.dto.v1_0.VocabularyImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.foundation.internal.odata.entity.v1_0.VocabularyEntityModel;
import com.liferay.headless.foundation.resource.v1_0.VocabularyResource;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/vocabulary.properties",
	scope = ServiceScope.PROTOTYPE, service = VocabularyResource.class
)
public class VocabularyResourceImpl
	extends BaseVocabularyResourceImpl implements EntityModelResource {

	@Override
	public boolean deleteVocabulary(Long vocabularyId) throws Exception {
		_assetVocabularyService.deleteVocabulary(vocabularyId);

		return true;
	}

	@Override
	public Page<Vocabulary> getContentSpaceVocabulariesPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<AssetVocabulary> assetVocabularies = new ArrayList<>();

		Hits hits = SearchUtil.getHits(
			filter, _indexerRegistry.nullSafeGetIndexer(AssetVocabulary.class),
			pagination,
			booleanQuery -> {
			},
			queryConfig -> {
				queryConfig.setSelectedFieldNames(Field.ASSET_VOCABULARY_ID);
			},
			searchContext -> {
				searchContext.setCompanyId(company.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});
			},
			_searchResultPermissionFilterFactory, sorts);

		for (Document document : hits.getDocs()) {
			AssetVocabulary vocabulary = _assetVocabularyService.getVocabulary(
				GetterUtil.getLong(document.get(Field.ASSET_VOCABULARY_ID)));

			assetVocabularies.add(vocabulary);
		}

		return Page.of(
			transform(assetVocabularies, this::_toVocabulary), pagination,
			assetVocabularies.size());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _vocabularyEntityModel;
	}

	@Override
	public Vocabulary getVocabulary(Long vocabularyId) throws Exception {
		return _toVocabulary(
			_assetVocabularyService.getVocabulary(vocabularyId));
	}

	@Override
	public Vocabulary postContentSpaceVocabulary(
			Long contentSpaceId, Vocabulary vocabulary)
		throws Exception {

		return _toVocabulary(
			_assetVocabularyService.addVocabulary(
				contentSpaceId, null,
				Collections.singletonMap(
					acceptLanguage.getPreferredLocale(), vocabulary.getName()),
				Collections.singletonMap(
					acceptLanguage.getPreferredLocale(),
					vocabulary.getDescription()),
				null, new ServiceContext()));
	}

	@Override
	public Vocabulary putVocabulary(Long vocabularyId, Vocabulary vocabulary)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		return _toVocabulary(
			_assetVocabularyService.updateVocabulary(
				assetVocabulary.getVocabularyId(), null,
				LocalizedMapUtil.merge(
					assetVocabulary.getTitleMap(),
					new AbstractMap.SimpleEntry<>(
						acceptLanguage.getPreferredLocale(),
						vocabulary.getName())),
				LocalizedMapUtil.merge(
					assetVocabulary.getDescriptionMap(),
					new AbstractMap.SimpleEntry<>(
						acceptLanguage.getPreferredLocale(),
						vocabulary.getDescription())),
				null, new ServiceContext()));
	}

	private Vocabulary _toVocabulary(AssetVocabulary assetVocabulary)
		throws Exception {

		return new VocabularyImpl() {
			{
				availableLanguages = assetVocabulary.getAvailableLanguageIds();
				contentSpace = assetVocabulary.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(assetVocabulary.getUserId()));
				dateCreated = assetVocabulary.getCreateDate();
				dateModified = assetVocabulary.getModifiedDate();
				description = assetVocabulary.getDescription(
					acceptLanguage.getPreferredLocale());
				hasCategories = ListUtil.isEmpty(
					assetVocabulary.getCategories());
				id = assetVocabulary.getVocabularyId();
				name = assetVocabulary.getTitle(
					acceptLanguage.getPreferredLocale());
			}
		};
	}

	private static final VocabularyEntityModel _vocabularyEntityModel =
		new VocabularyEntityModel();

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	@Reference
	private UserLocalService _userLocalService;

}