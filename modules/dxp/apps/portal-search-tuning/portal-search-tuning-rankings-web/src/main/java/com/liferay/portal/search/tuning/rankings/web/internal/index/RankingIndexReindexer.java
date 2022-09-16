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

import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = {IndexReindexer.class, RankingIndexReindexer.class})
public class RankingIndexReindexer implements IndexReindexer {

	@Override
	public void reindex(long[] companyIds) throws Exception {
		for (long companyId : companyIds) {
			List<Long> classPKs = jsonStorageEntryLocalService.getClassPKs(
				companyId, classNameLocalService.getClassNameId(Ranking.class),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			RankingIndexName rankingIndexName =
				rankingIndexNameBuilder.getRankingIndexName(companyId);

			if (ListUtil.isEmpty(classPKs)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Not reindexing ", rankingIndexName.getIndexName(),
							" because the database has no ranking entries"));
				}

				continue;
			}

			try {
				rankingIndexCreator.delete(rankingIndexName);
			}
			catch (RuntimeException runtimeException) {
				_log.error(
					"Unable to delete index " + rankingIndexName.getIndexName(),
					runtimeException);
			}

			rankingIndexCreator.create(rankingIndexName);

			for (long classPK : classPKs) {
				rankingIndexWriter.create(
					rankingIndexName, _buildRanking(classPK));
			}
		}
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected JSONStorageEntryLocalService jsonStorageEntryLocalService;

	@Reference
	protected RankingIndexCreator rankingIndexCreator;

	@Reference
	protected RankingIndexNameBuilder rankingIndexNameBuilder;

	@Reference
	protected RankingIndexWriter rankingIndexWriter;

	private Ranking _buildRanking(long classPK) throws Exception {
		JSONObject jsonObject = jsonStorageEntryLocalService.getJSONObject(
			classNameLocalService.getClassNameId(Ranking.class), classPK);

		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.aliases(
			JSONUtil.toStringList(jsonObject.getJSONArray("aliases"))
		).hiddenDocumentIds(
			JSONUtil.toStringList(jsonObject.getJSONArray("hiddenDocumentIds"))
		).rankingDocumentId(
			jsonObject.getString("rankingDocumentId")
		).inactive(
			jsonObject.getBoolean("inactive")
		).indexName(
			jsonObject.getString("indexName")
		).name(
			jsonObject.getString("name")
		).pins(
			_getPins(jsonObject.getJSONArray("pins"))
		).queryString(
			jsonObject.getString("queryString")
		);

		return rankingBuilder.build();
	}

	private List<Ranking.Pin> _getPins(JSONArray jsonArray) throws Exception {
		List<Ranking.Pin> pins = new ArrayList<>();

		JSONUtil.toList(
			jsonArray,
			jsonObject -> pins.add(
				new Ranking.Pin(
					jsonObject.getInt("position"),
					jsonObject.getString("documentId"))));

		return pins;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingIndexReindexer.class);

}