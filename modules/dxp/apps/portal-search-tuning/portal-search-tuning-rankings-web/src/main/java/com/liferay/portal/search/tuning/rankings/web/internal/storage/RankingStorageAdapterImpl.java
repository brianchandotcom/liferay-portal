/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.search.tuning.rankings.index.RankingBuilderFactory;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.storage.RankingStorageAdapter;
import com.liferay.portal.search.tuning.rankings.web.internal.storage.helper.RankingJSONStorageHelper;
import com.liferay.portal.search.tuning.rankings.web.internal.util.RankingIndexWriterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = RankingStorageAdapter.class)
public class RankingStorageAdapterImpl implements RankingStorageAdapter {

	@Override
	public String create(Ranking ranking, RankingIndexName rankingIndexName) {
		String rankingDocumentId = rankingJSONStorageHelper.addJSONStorageEntry(
			ranking);

		Ranking.Builder rankingBuilder = rankingBuilderFactory.builder(ranking);

		rankingBuilder.rankingDocumentId(rankingDocumentId);

		RankingIndexWriterUtil.create(
			rankingBuilder.build(), rankingIndexName, _searchEngineAdapter);

		return rankingDocumentId;
	}

	@Override
	public void delete(
			String rankingDocumentId, RankingIndexName rankingIndexName)
		throws PortalException {

		rankingJSONStorageHelper.deleteJSONStorageEntry(rankingDocumentId);

		RankingIndexWriterUtil.remove(
			rankingDocumentId, rankingIndexName, _searchEngineAdapter);
	}

	@Override
	public void update(Ranking ranking, RankingIndexName rankingIndexName)
		throws PortalException {

		rankingJSONStorageHelper.updateJSONStorageEntry(ranking);

		RankingIndexWriterUtil.update(
			ranking, rankingIndexName, _searchEngineAdapter);
	}

	@Reference
	protected RankingBuilderFactory rankingBuilderFactory;

	@Reference
	protected RankingJSONStorageHelper rankingJSONStorageHelper;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}