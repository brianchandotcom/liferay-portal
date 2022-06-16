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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class DuplicateQueryStringsDetectorImplTest
	extends BaseRankingsIndexTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_duplicateQueryStringsDetectorImpl =
			new DuplicateQueryStringsDetectorImpl();

		ReflectionTestUtil.setFieldValue(
			_duplicateQueryStringsDetectorImpl, "queries", queries);
		ReflectionTestUtil.setFieldValue(
			_duplicateQueryStringsDetectorImpl, "searchEngineAdapter",
			searchEngineAdapter);
	}

	@Test
	public void testBuilder() {
		Assert.assertNotNull(_duplicateQueryStringsDetectorImpl.builder());
	}

	@Test
	public void testDetect() {
		DuplicateQueryStringsDetector.Criteria criteria = Mockito.mock(
			DuplicateQueryStringsDetector.Criteria.class);

		List<String> queryStrings = new ArrayList<>(
			Arrays.asList("queryStrings"));

		Mockito.doReturn(
			queryStrings
		).when(
			criteria
		).getQueryStrings();

		Mockito.doReturn(
			Mockito.mock(RankingIndexName.class)
		).when(
			criteria
		).getRankingIndexName();

		setUpQueries();
		setUpSearchEngineAdapter(setUpSearchHits(queryStrings));

		Assert.assertEquals(
			queryStrings, _duplicateQueryStringsDetectorImpl.detect(criteria));
	}

	@Test
	public void testDetectCriteriaQueryStringsEmpty() {
		Assert.assertEquals(
			Collections.emptyList(),
			_duplicateQueryStringsDetectorImpl.detect(
				Mockito.mock(DuplicateQueryStringsDetector.Criteria.class)));
	}

	private DuplicateQueryStringsDetectorImpl
		_duplicateQueryStringsDetectorImpl;

}