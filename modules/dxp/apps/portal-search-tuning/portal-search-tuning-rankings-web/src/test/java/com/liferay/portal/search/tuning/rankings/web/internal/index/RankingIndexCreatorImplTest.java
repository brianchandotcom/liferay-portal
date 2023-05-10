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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.document.DocumentResponse;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexCreatorImplTest extends BaseRankingsIndexTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingIndexCreatorImpl = new RankingIndexCreatorImpl();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreatorImpl, "_jsonFactory", new JSONFactoryImpl());

		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreatorImpl, "_searchEngineAdapter",
			searchEngineAdapter);
	}

	@Test
	public void testCreate() {
		setUpSearchEngineAdapter((DocumentResponse)null);

		_rankingIndexCreatorImpl.create(Mockito.mock(RankingIndexName.class));

		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			(CreateIndexRequest)Mockito.any()
		);
	}

	@Test
	public void testDelete() {
		setUpSearchEngineAdapter((DocumentResponse)null);

		_rankingIndexCreatorImpl.delete(Mockito.mock(RankingIndexName.class));

		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			Mockito.any(DeleteIndexRequest.class)
		);
	}

	private RankingIndexCreatorImpl _rankingIndexCreatorImpl;

}