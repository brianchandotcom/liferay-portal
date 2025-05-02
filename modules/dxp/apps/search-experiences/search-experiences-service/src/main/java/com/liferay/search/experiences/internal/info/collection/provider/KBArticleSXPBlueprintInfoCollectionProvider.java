/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.info.collection.provider;

import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.Collections;
import java.util.List;

/**
 * @author Joshua Cords
 */
public class KBArticleSXPBlueprintInfoCollectionProvider
	extends SXPBlueprintInfoCollectionProvider<KBArticle>
	implements FilteredInfoCollectionProvider<KBArticle>,
			   SingleFormVariationInfoCollectionProvider<KBArticle> {

	public KBArticleSXPBlueprintInfoCollectionProvider(
		AssetHelper assetHelper, KBArticleLocalService kbArticleLocalService,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory,
		SXPBlueprint sxpBlueprint) {

		super(assetHelper, searcher, searchRequestBuilderFactory, sxpBlueprint);

		_kbArticleLocalService = kbArticleLocalService;
	}

	@Override
	public InfoPage<KBArticle> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			SearchResponse searchResponse = getCollectionQuerySearchResponse(
				collectionQuery);

			return InfoPage.of(
				_getKBArticles(searchResponse.getSearchHits()),
				collectionQuery.getPagination(), searchResponse.getTotalHits());
		}
		catch (Exception exception) {
			_log.error("Unable to get knowledge base articles", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	private List<KBArticle> _getKBArticles(SearchHits searchHits)
		throws PortalException {

		return TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				return _kbArticleLocalService.getLatestKBArticle(
					document.getLong(Field.ENTRY_CLASS_PK));
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleSXPBlueprintInfoCollectionProvider.class);

	private final KBArticleLocalService _kbArticleLocalService;

}