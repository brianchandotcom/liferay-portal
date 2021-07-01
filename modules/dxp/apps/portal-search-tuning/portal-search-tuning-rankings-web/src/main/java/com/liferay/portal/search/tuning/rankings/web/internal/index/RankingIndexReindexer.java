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

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;
import com.liferay.search.tuning.rankings.service.STRankingsEntryLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "model.class.name=com.liferay.search.tuning.rankings.model.STRankingsEntry",
	service = IndexReindexer.class
)
public class RankingIndexReindexer implements IndexReindexer {

	public String getModelClassName() {
		return STRankingsEntry.class.getName();
	}

	public void reindex(long[] companyIds) {
		for (long companyId : companyIds) {
			List<STRankingsEntry> stRankingsEntries =
				stRankingsEntryLocalService.getRankingsByCompanyId(companyId);

			RankingIndexName rankingIndexName =
				rankingIndexNameBuilder.getRankingIndexName(companyId);

			if (ListUtil.isEmpty(stRankingsEntries)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Not reindexing ", rankingIndexName.getIndexName(),
							" because the database has no STRankingsEntry ",
							"entries"));
				}

				continue;
			}

			try {
				rankingIndexCreator.delete(rankingIndexName);
			}
			catch (RuntimeException runtimeException) {
				_log.error(
					"Unable to delete index " +
						rankingIndexName.getIndexName());
			}

			rankingIndexCreator.create(rankingIndexName);

			for (STRankingsEntry stRankingsEntry : stRankingsEntries) {
				rankingIndexWriter.create(
					rankingIndexName, _translate(stRankingsEntry));
			}
		}
	}

	@Reference
	protected RankingIndexCreator rankingIndexCreator;

	@Reference
	protected RankingIndexNameBuilder rankingIndexNameBuilder;

	@Reference
	protected RankingIndexWriter rankingIndexWriter;

	@Reference
	protected STRankingsEntryLocalService stRankingsEntryLocalService;

	private List<Ranking.Pin> _getPins(Map<Integer, String> pinnedDocumentIds) {
		List<Ranking.Pin> pins = new ArrayList<>();

		for (Map.Entry<Integer, String> entry : pinnedDocumentIds.entrySet()) {
			pins.add(new Ranking.Pin(entry.getKey(), entry.getValue()));
		}

		return pins;
	}

	private Ranking _translate(STRankingsEntry stRankingsEntry) {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.aliases(
			stRankingsEntry.getAliases()
		).hiddenDocumentIds(
			stRankingsEntry.getHiddenDocumentIds()
		).rankingDocumentId(
			stRankingsEntry.getRankingDocumentId()
		).inactive(
			stRankingsEntry.getInactive()
		).indexName(
			stRankingsEntry.getIndexName()
		).name(
			stRankingsEntry.getName()
		).pins(
			_getPins(stRankingsEntry.getPinnedDocumentIds())
		).queryString(
			stRankingsEntry.getQueryString()
		);

		return rankingBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingIndexReindexer.class);

}