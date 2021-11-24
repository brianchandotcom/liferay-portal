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
import com.liferay.portal.search.tuning.rankings.web.internal.storage.helper.RankingJSONStorageHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = RankingStorageAdapter.class)
public class RankingStorageAdapter {

	public String create(RankingIndexName rankingIndexName, Ranking ranking) {
		String rankingDocumentId = rankingJSONStorageHelper.addJSONStorageEntry(
			rankingIndexName.getIndexName(), ranking.getName(),
			ranking.getQueryString());

		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder(
			ranking);

		rankingBuilder.rankingDocumentId(rankingDocumentId);

		rankingIndexWriter.create(rankingIndexName, rankingBuilder.build());

		return rankingDocumentId;
	}

	public void delete(
			RankingIndexName rankingIndexName, String rankingDocumentId)
		throws PortalException {

		rankingJSONStorageHelper.deleteJSONStorageEntry(
			_getClassPK(rankingDocumentId));

		rankingIndexWriter.remove(rankingIndexName, rankingDocumentId);
	}

	public void update(RankingIndexName rankingIndexName, Ranking ranking)
		throws PortalException {

		rankingJSONStorageHelper.updateJSONStorageEntry(
			_getClassPK(ranking.getRankingDocumentId()), ranking.getAliases(),
			ranking.getHiddenDocumentIds(), ranking.isInactive(),
			ranking.getName(), ranking.getPins());

		rankingIndexWriter.update(rankingIndexName, ranking);
	}

	@Reference
	protected RankingIndexWriter rankingIndexWriter;

	@Reference
	protected RankingJSONStorageHelper rankingJSONStorageHelper;

	private long _getClassPK(String rankingDocumentId) throws PortalException {
		String[] parts = StringUtil.split(rankingDocumentId, "_PORTLET_");

		if (parts.length != 2) {
			_log.error(
				StringBundler.concat(
					"Ranking document ID ", rankingDocumentId, " has an ",
					"unexpected format. Rankings may need to be imported to ",
					"the database via the rankings database importer Groovy ",
					"script before they can be edited or deleted."));

			throw new PortalException();
		}

		return Long.valueOf(parts[1]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingStorageAdapter.class);

}