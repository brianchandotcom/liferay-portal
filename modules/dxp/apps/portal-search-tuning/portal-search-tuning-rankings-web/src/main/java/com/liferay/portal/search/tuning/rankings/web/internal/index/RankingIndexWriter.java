/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;

/**
 * @author André de Oliveira
 */
public interface RankingIndexWriter {

	public String create(RankingIndexName rankingIndexName, Ranking ranking);

	public void remove(
		RankingIndexName rankingIndexName, String rankingDocumentId);

	public void update(RankingIndexName rankingIndexName, Ranking ranking);

}