/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.util;

import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingToDocumentTranslatorUtil;

/**
 * @author André de Oliveira
 */
public class RankingIndexWriterUtil {

	public static String create(
		Ranking ranking, RankingIndexName rankingIndexName,
		SearchEngineAdapter searchEngineAdapter) {

		IndexDocumentResponse indexDocumentResponse =
			searchEngineAdapter.execute(
				new IndexDocumentRequest(
					rankingIndexName.getIndexName(),
					RankingToDocumentTranslatorUtil.translate(ranking)));

		return indexDocumentResponse.getUid();
	}

	public static void remove(
		String rankingDocumentId, RankingIndexName rankingIndexName,
		SearchEngineAdapter searchEngineAdapter) {

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			rankingIndexName.getIndexName(), rankingDocumentId);

		deleteDocumentRequest.setRefresh(true);

		searchEngineAdapter.execute(deleteDocumentRequest);
	}

	public static void update(
		Ranking ranking, RankingIndexName rankingIndexName,
		SearchEngineAdapter searchEngineAdapter) {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			rankingIndexName.getIndexName(), ranking.getRankingDocumentId(),
			RankingToDocumentTranslatorUtil.translate(ranking));

		indexDocumentRequest.setRefresh(true);

		searchEngineAdapter.execute(indexDocumentRequest);
	}

}