/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
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

		ReflectionTestUtil.setFieldValue(
			_rankingIndexWriterImpl, "_rankingToDocumentTranslator",
			_rankingToDocumentTranslator);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexWriterImpl, "_searchEngineAdapter",
			searchEngineAdapter);
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