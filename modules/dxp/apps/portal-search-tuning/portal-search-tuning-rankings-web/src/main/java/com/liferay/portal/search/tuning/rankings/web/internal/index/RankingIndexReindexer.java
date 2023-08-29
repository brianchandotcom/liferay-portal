/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.index.SyncReindexManager;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = IndexReindexer.class)
public class RankingIndexReindexer implements IndexReindexer {

	@Override
	public void reindex(long[] companyIds) throws Exception {
		reindex(companyIds, null);
	}

	@Override
	public void reindex(long[] companyIds, String executionMode)
		throws Exception {

		if (!searchCapabilities.isResultRankingsSupported()) {
			return;
		}

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

			Date date = null;

			if (_isExecuteSyncReindex(executionMode)) {
				date = new Date();

				Thread.sleep(1000);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Deleting index " + rankingIndexName.getIndexName());
				}

				try {
					rankingIndexCreator.delete(rankingIndexName);
				}
				catch (RuntimeException runtimeException) {
					_log.error(
						"Unable to delete index " +
							rankingIndexName.getIndexName(),
						runtimeException);
				}
			}

			if (!_isExecuteSyncReindex(executionMode)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Creating index " + rankingIndexName.getIndexName());
				}

				rankingIndexCreator.create(rankingIndexName);
			}

			for (long classPK : classPKs) {
				rankingIndexWriter.create(
					rankingIndexName, _buildRanking(classPK));
			}

			if (_isExecuteSyncReindex(executionMode)) {
				SyncReindexManager syncReindexManager =
					_syncReindexManagerSnapshot.get();

				syncReindexManager.deleteStaleDocuments(
					rankingIndexName.getIndexName(), date,
					Collections.emptySet());
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

	@Reference
	protected SearchCapabilities searchCapabilities;

	private Ranking _buildRanking(long classPK) throws Exception {
		JSONObject jsonObject = jsonStorageEntryLocalService.getJSONObject(
			classNameLocalService.getClassNameId(Ranking.class), classPK);

		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		rankingBuilder.aliases(
			JSONUtil.toStringList(jsonObject.getJSONArray("aliases"))
		).groupExternalReferenceCode(
			jsonObject.getString("groupExternalReferenceCode")
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
		).sxpBlueprintExternalReferenceCode(
			jsonObject.getString("sxpBlueprintExternalReferenceCode")
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

	private boolean _isExecuteSyncReindex(String executionMode) {
		if ((_syncReindexManagerSnapshot.get() != null) &&
			(executionMode != null) && executionMode.equals("sync")) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingIndexReindexer.class);

	private static final Snapshot<SyncReindexManager>
		_syncReindexManagerSnapshot = new Snapshot<>(
			RankingIndexReindexer.class, SyncReindexManager.class, null, true);

}