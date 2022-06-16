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

package com.liferay.portal.search.tuning.rankings.web.internal.results.builder;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.search.tuning.rankings.web.internal.BaseRankingsWebTestCase;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;

import java.util.Optional;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public abstract class BaseRankingResultsBuilderTestCase
	extends BaseRankingsWebTestCase {

	protected void setUpRankingIndexReader(Optional<Ranking> rankingOptional) {
		Mockito.doReturn(
			rankingOptional
		).when(
			rankingIndexReader
		).fetchOptional(
			Mockito.any(), Mockito.nullable(String.class)
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