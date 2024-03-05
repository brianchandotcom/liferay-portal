/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;

/**
 * @author André de Oliveira
 */
public class RankingIndexWriter {

	public RankingIndexWriter(
		DocumentBuilderFactory documentBuilderFactory,
		SearchEngineAdapter searchEngineAdapter) {

		_documentBuilderFactory = documentBuilderFactory;
		_searchEngineAdapter = searchEngineAdapter;
	}

	public String create(RankingIndexName rankingIndexName, Ranking ranking) {
		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(
				new IndexDocumentRequest(
					rankingIndexName.getIndexName(),
					RankingToDocumentTranslatorUtil.translate(
						_documentBuilderFactory, ranking)));

		return indexDocumentResponse.getUid();
	}

	public void remove(
		RankingIndexName rankingIndexName, String rankingDocumentId) {

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			rankingIndexName.getIndexName(), rankingDocumentId);

		deleteDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	public void update(RankingIndexName rankingIndexName, Ranking ranking) {
		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			rankingIndexName.getIndexName(), ranking.getRankingDocumentId(),
			RankingToDocumentTranslatorUtil.translate(
				_documentBuilderFactory, ranking));

		indexDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	private final DocumentBuilderFactory _documentBuilderFactory;
	private final SearchEngineAdapter _searchEngineAdapter;

}