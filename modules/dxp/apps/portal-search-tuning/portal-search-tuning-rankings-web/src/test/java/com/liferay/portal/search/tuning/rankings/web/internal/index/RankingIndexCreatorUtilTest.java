/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.engine.adapter.document.DocumentResponse;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexCreatorUtilTest extends BaseRankingsIndexTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreate() {
		setUpSearchEngineAdapter((DocumentResponse)null);

		RankingIndexCreatorUtil.create(
			searchEngineAdapter, Mockito.mock(RankingIndexName.class));

		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			(CreateIndexRequest)Mockito.any()
		);
	}

	@Test
	public void testDelete() {
		setUpSearchEngineAdapter((DocumentResponse)null);

		RankingIndexCreatorUtil.deleteIfExists(
			searchEngineAdapter, Mockito.mock(RankingIndexName.class));

		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			Mockito.any(DeleteIndexRequest.class)
		);
	}

}