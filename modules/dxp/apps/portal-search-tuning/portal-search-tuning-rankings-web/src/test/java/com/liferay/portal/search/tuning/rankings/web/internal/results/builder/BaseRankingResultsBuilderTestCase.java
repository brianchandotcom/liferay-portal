/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.results.builder;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public abstract class BaseRankingResultsBuilderTestCase
	extends BaseRankingsWebTestCase {

	protected void setUpRankingIndexReader(Ranking ranking) {
		Mockito.doReturn(
			ranking
		).when(
			rankingIndexReader
		).fetch(
			Mockito.nullable(String.class), Mockito.any()
		);
	}

	protected static ObjectMapper mapper = new ObjectMapper();

	protected RankingIndexName rankingIndexName = Mockito.mock(
		RankingIndexName.class);
	protected RankingIndexReader rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	protected ResourceActions resourceActions = Mockito.mock(
		ResourceActions.class);

}