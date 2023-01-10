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

import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;

/**
 * @author André de Oliveira
 */
public interface RankingIndexReader {

	public Ranking fetch(RankingIndexName rankingIndexName, String id);

	public Ranking fetchByQueryString(
		RankingIndexName rankingIndexName, String queryString);

	public boolean isExists(RankingIndexName rankingIndexName);

}