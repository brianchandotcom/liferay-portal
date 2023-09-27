/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexWriter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.storage.helper.RankingJSONStorageHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = RankingStorageAdapter.class)
public class RankingStorageAdapter {

	public String create(Ranking ranking, RankingIndexName rankingIndexName) {
		String rankingDocumentId = rankingJSONStorageHelper.addJSONStorageEntry(
			ranking);

		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder(
			ranking);

		rankingBuilder.rankingDocumentId(rankingDocumentId);

		rankingIndexWriter.create(rankingIndexName, rankingBuilder.build());

		return rankingDocumentId;
	}

	public void delete(
			String rankingDocumentId, RankingIndexName rankingIndexName)
		throws PortalException {

		rankingJSONStorageHelper.deleteJSONStorageEntry(rankingDocumentId);

		rankingIndexWriter.remove(rankingIndexName, rankingDocumentId);
	}

	public void update(Ranking ranking, RankingIndexName rankingIndexName)
		throws PortalException {

		rankingJSONStorageHelper.updateJSONStorageEntry(ranking);

		rankingIndexWriter.update(rankingIndexName, ranking);
	}

	@Reference
	protected RankingIndexWriter rankingIndexWriter;

	@Reference
	protected RankingJSONStorageHelper rankingJSONStorageHelper;

}