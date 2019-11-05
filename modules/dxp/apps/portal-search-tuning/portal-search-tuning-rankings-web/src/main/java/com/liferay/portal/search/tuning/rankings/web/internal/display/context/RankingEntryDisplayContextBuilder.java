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

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;

import java.util.List;

/**
 * @author Bryan Engler
 */
public class RankingEntryDisplayContextBuilder {

	public RankingEntryDisplayContextBuilder(Ranking ranking) {
		_ranking = ranking;
	}

	public RankingEntryDisplayContext build() {
		RankingEntryDisplayContext rankingEntryDisplayContext =
			new RankingEntryDisplayContext();

		_setAliases(rankingEntryDisplayContext);
		_setHiddenResultsCount(rankingEntryDisplayContext);
		_setInactive(rankingEntryDisplayContext);
		_setIndex(rankingEntryDisplayContext);
		_setNameForDisplay(rankingEntryDisplayContext);
		_setPinnedResultsCount(rankingEntryDisplayContext);
		_setUid(rankingEntryDisplayContext);

		return rankingEntryDisplayContext;
	}

	protected static String getSizeString(List<?> list) {
		return String.valueOf(list.size());
	}

	private void _setAliases(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setAliases(
			StringUtil.merge(
				_ranking.getAliases(), StringPool.COMMA_AND_SPACE));
	}

	private void _setHiddenResultsCount(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setHiddenResultsCount(
			getSizeString(_ranking.getBlockIds()));
	}

	private void _setInactive(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setInactive(_ranking.isInactive());
	}

	private void _setIndex(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setIndex(_ranking.getIndex());
	}

	private void _setNameForDisplay(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setKeywords(_ranking.getNameForDisplay());
	}

	private void _setPinnedResultsCount(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setPinnedResultsCount(
			getSizeString(_ranking.getPins()));
	}

	private void _setUid(
		RankingEntryDisplayContext rankingEntryDisplayContext) {

		rankingEntryDisplayContext.setUid(_ranking.getId());
	}

	private final Ranking _ranking;

}