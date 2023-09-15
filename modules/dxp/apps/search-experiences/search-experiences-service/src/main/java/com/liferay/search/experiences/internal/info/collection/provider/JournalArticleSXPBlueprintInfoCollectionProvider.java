/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.info.collection.provider;

import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author David Nebinger
 * @author Petteri Karttunen
 */
public class JournalArticleSXPBlueprintInfoCollectionProvider
	extends SXPBlueprintInfoCollectionProvider<JournalArticle>
	implements FilteredInfoCollectionProvider<JournalArticle>,
			   SingleFormVariationInfoCollectionProvider<JournalArticle> {

	public JournalArticleSXPBlueprintInfoCollectionProvider(
		AssetHelper assetHelper, JournalArticleService journalArticleService,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory,
		SXPBlueprint sxpBlueprint) {

		super(assetHelper, searcher, searchRequestBuilderFactory, sxpBlueprint);

		_journalArticleService = journalArticleService;
	}

	@Override
	public InfoPage<JournalArticle> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			Pagination pagination = collectionQuery.getPagination();

			SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(
				collectionQuery, pagination);

			SearchResponse searchResponse = searcher.search(
				searchRequestBuilder.build());

			return InfoPage.of(
				_getJournalArticles(searchResponse.getSearchHits()),
				collectionQuery.getPagination(), searchResponse.getTotalHits());
		}
		catch (Exception exception) {
			_log.error("Unable to get journal articles", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getFormVariationKey() {
		long ddmStructureId = 0;

		CollectionQuery collectionQuery = new CollectionQuery();

		collectionQuery.setPagination(Pagination.of(1, 0));

		SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(
			collectionQuery, collectionQuery.getPagination());

		SearchResponse searchResponse = searcher.search(
			searchRequestBuilder.build());

		SearchHits searchHits = searchResponse.getSearchHits();

		try {
			List<JournalArticle> journalArticles = _getJournalArticles(
				searchHits);

			if (!journalArticles.isEmpty()) {
				JournalArticle journalArticle = journalArticles.get(0);

				ddmStructureId = journalArticle.getDDMStructureId();
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return String.valueOf(ddmStructureId);
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			SingleFormVariationInfoCollectionProvider.super.getKey(),
			StringPool.UNDERLINE, sxpBlueprint.getCompanyId(),
			StringPool.UNDERLINE, sxpBlueprint.getExternalReferenceCode(),
			StringPool.UNDERLINE, JournalArticle.class.getName());
	}

	@Override
	public String getLabel(Locale locale) {
		return sxpBlueprint.getTitle(locale);
	}

	private List<JournalArticle> _getJournalArticles(SearchHits searchHits)
		throws PortalException {

		List<JournalArticle> journalArticles = new ArrayList<>();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		for (SearchHit searchHit : searchHitsList) {
			Document document = searchHit.getDocument();

			journalArticles.add(
				_journalArticleService.getLatestArticle(
					document.getLong(Field.ENTRY_CLASS_PK)));
		}

		return journalArticles;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleSXPBlueprintInfoCollectionProvider.class);

	private final JournalArticleService _journalArticleService;

}