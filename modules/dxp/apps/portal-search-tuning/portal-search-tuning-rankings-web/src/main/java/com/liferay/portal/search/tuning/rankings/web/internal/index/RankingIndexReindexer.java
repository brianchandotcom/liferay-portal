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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.tuning.rankings.service.RankingLocalService;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "model.class.name=com.liferay.portal.search.tuning.rankings.model.Ranking",
	service = IndexReindexer.class
)
public class RankingIndexReindexer implements IndexReindexer {

	public String getModelClassName() {
		return com.liferay.portal.search.tuning.rankings.model.Ranking.class.
			getName();
	}

	public void reindex(long[] companyIds) {
		for (long companyId : companyIds) {
			List<com.liferay.portal.search.tuning.rankings.model.Ranking>
				rankings = rankingLocalService.getRankingsByCompanyId(
					companyId);

			RankingIndexName rankingIndexName =
				rankingIndexNameBuilder.getRankingIndexName(companyId);

			if (ListUtil.isEmpty(rankings)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Not reindexing " + rankingIndexName.getIndexName() +
							" because the database has no Ranking entries");
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

			for (com.liferay.portal.search.tuning.rankings.model.Ranking
					ranking : rankings) {

				rankingIndexWriter.create(
					rankingIndexName, _translate(ranking));
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
	protected RankingLocalService rankingLocalService;

	private List<Ranking.Pin> _getPins(Map<Integer, String> pinnedDocumentIds) {
		List<Ranking.Pin> pins = new ArrayList<>();

		for (Map.Entry<Integer, String> entry : pinnedDocumentIds.entrySet()) {
			pins.add(new Ranking.Pin(entry.getKey(), entry.getValue()));
		}

		return pins;
	}

	private Ranking _translate(
		com.liferay.portal.search.tuning.rankings.model.Ranking ranking) {

		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.aliases(
			ranking.getAliases()
		).hiddenDocumentIds(
			ranking.getHiddenDocumentIds()
		).rankingDocumentId(
			ranking.getRankingDocumentId()
		).inactive(
			ranking.getInactive()
		).indexName(
			ranking.getIndexName()
		).name(
			ranking.getName()
		).pins(
			_getPins(ranking.getPinnedDocumentIds())
		).queryString(
			ranking.getQueryString()
		);

		return rankingBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingIndexReindexer.class);

}