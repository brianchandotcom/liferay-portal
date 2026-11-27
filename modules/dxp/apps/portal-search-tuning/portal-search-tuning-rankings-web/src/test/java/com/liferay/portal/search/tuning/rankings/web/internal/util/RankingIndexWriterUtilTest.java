/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.util;

import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.BaseRankingsIndexTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexWriterUtilTest extends BaseRankingsIndexTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreate() {
		IndexDocumentResponse indexDocumentResponse = Mockito.mock(
			IndexDocumentResponse.class);

		Mockito.doReturn(
			"uid"
		).when(
			indexDocumentResponse
		).getUid();

		setUpSearchEngineAdapter(indexDocumentResponse);

		Assert.assertEquals(
			"uid",
			RankingIndexWriterUtil.create(
				Mockito.mock(Ranking.class),
				Mockito.mock(RankingIndexName.class), searchEngineAdapter));
	}

	@Test
	public void testRemove() {
		setUpSearchEngineAdapter(Mockito.mock(DeleteDocumentResponse.class));

		RankingIndexWriterUtil.remove(
			"id", Mockito.mock(RankingIndexName.class), searchEngineAdapter);
		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			(DeleteDocumentRequest)Mockito.any()
		);
	}

	@Test
	public void testUpdate() {
		setUpSearchEngineAdapter(Mockito.mock(IndexDocumentResponse.class));

		RankingIndexWriterUtil.update(
			Mockito.mock(Ranking.class), Mockito.mock(RankingIndexName.class),
			searchEngineAdapter);
		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			(IndexDocumentRequest)Mockito.any()
		);
	}

}