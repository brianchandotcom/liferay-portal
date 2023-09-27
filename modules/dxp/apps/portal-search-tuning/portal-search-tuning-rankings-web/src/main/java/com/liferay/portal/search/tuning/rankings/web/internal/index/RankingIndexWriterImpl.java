/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = RankingIndexWriter.class)
public class RankingIndexWriterImpl implements RankingIndexWriter {

	@Override
	public String create(RankingIndexName rankingIndexName, Ranking ranking) {
		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(
				new IndexDocumentRequest(
					rankingIndexName.getIndexName(),
					_rankingToDocumentTranslator.translate(ranking)));

		return indexDocumentResponse.getUid();
	}

	@Override
	public void remove(
		RankingIndexName rankingIndexName, String rankingDocumentId) {

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			rankingIndexName.getIndexName(), rankingDocumentId);

		deleteDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	@Override
	public void update(RankingIndexName rankingIndexName, Ranking ranking) {
		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			rankingIndexName.getIndexName(), ranking.getRankingDocumentId(),
			_rankingToDocumentTranslator.translate(ranking));

		indexDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Reference
	private RankingToDocumentTranslator _rankingToDocumentTranslator;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}