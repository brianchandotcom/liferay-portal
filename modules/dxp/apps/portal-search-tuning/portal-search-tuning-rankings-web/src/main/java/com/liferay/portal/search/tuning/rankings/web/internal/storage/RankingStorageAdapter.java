/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.storage;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexWriter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;
import com.liferay.search.tuning.rankings.service.STRankingsEntryLocalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = RankingStorageAdapter.class)
public class RankingStorageAdapter {

	public String create(RankingIndexName rankingIndexName, Ranking ranking) {
		STRankingsEntry stRankingsEntry =
			stRankingsEntryLocalService.addSTRankingsEntry(
				rankingIndexName.getIndexName(), ranking.getName(),
				ranking.getQueryString());

		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder(
			ranking);

		rankingBuilder.rankingDocumentId(
			stRankingsEntry.getRankingDocumentId());

		rankingIndexWriter.create(rankingIndexName, rankingBuilder.build());

		return stRankingsEntry.getRankingDocumentId();
	}

	public void delete(
			RankingIndexName rankingIndexName, String rankingDocumentId)
		throws PortalException {

		stRankingsEntryLocalService.deleteSTRankingsEntry(
			_getRankingId(rankingDocumentId));

		rankingIndexWriter.remove(rankingIndexName, rankingDocumentId);
	}

	public void update(RankingIndexName rankingIndexName, Ranking ranking)
		throws PortalException {

		stRankingsEntryLocalService.updateSTRankingsEntry(
			_getRankingId(ranking.getRankingDocumentId()), ranking.getAliases(),
			ranking.getHiddenDocumentIds(), ranking.isInactive(),
			ranking.getName(), _getDocumentIdsMap(ranking.getPins()));

		rankingIndexWriter.update(rankingIndexName, ranking);
	}

	@Reference
	protected RankingIndexWriter rankingIndexWriter;

	@Reference
	protected STRankingsEntryLocalService stRankingsEntryLocalService;

	private Map<Integer, String> _getDocumentIdsMap(List<Ranking.Pin> pins) {
		Map<Integer, String> documentIdsMap = new HashMap<>();

		for (Ranking.Pin pin : pins) {
			documentIdsMap.put(pin.getPosition(), pin.getDocumentId());
		}

		return documentIdsMap;
	}

	private long _getRankingId(String rankingDocumentId)
		throws PortalException {

		String[] parts = StringUtil.split(rankingDocumentId, "_PORTLET_");

		if (parts.length != 2) {
			_log.error(
				StringBundler.concat(
					"Non-standard rankingDocumentId: ", rankingDocumentId,
					". Rankings may need to be imported to the database via ",
					"the RankingsDatabaseImporter groovy script before they ",
					"can be edited or deleted."));

			throw new PortalException();
		}

		return Long.valueOf(parts[1]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingStorageAdapter.class);

}