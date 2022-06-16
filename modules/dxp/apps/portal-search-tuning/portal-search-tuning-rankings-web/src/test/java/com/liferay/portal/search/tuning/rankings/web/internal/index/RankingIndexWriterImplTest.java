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

import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexWriterImplTest extends BaseRankingsIndexTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingIndexWriterImpl = new RankingIndexWriterImpl();

		_rankingIndexWriterImpl.setRankingToDocumentTranslator(
			_rankingToDocumentTranslator);
		_rankingIndexWriterImpl.setSearchEngineAdapter(searchEngineAdapter);
	}

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
			_rankingIndexWriterImpl.create(
				Mockito.mock(RankingIndexName.class),
				Mockito.mock(Ranking.class)));
	}

	@Test
	public void testRemove() {
		setUpSearchEngineAdapter(Mockito.mock(DeleteDocumentResponse.class));

		_rankingIndexWriterImpl.remove(
			Mockito.mock(RankingIndexName.class), "id");
		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			(DeleteDocumentRequest)Mockito.any()
		);
	}

	@Test
	public void testUpdate() {
		setUpSearchEngineAdapter(Mockito.mock(IndexDocumentResponse.class));

		_rankingIndexWriterImpl.update(
			Mockito.mock(RankingIndexName.class), Mockito.mock(Ranking.class));
		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			(IndexDocumentRequest)Mockito.any()
		);
	}

	private RankingIndexWriterImpl _rankingIndexWriterImpl;
	private final RankingToDocumentTranslator _rankingToDocumentTranslator =
		Mockito.mock(RankingToDocumentTranslator.class);

}