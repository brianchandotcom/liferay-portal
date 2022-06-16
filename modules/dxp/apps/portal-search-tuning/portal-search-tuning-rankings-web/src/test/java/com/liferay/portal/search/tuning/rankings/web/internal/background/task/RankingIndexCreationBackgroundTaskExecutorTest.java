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

package com.liferay.portal.search.tuning.rankings.web.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
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
public class RankingIndexCreationBackgroundTaskExecutorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_rankingIndexCreationBackgroundTaskExecutor =
			new RankingIndexCreationBackgroundTaskExecutor();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationBackgroundTaskExecutor,
			"_singleIndexToMultipleIndexImporter",
			_singleIndexToMultipleIndexImporter);
	}

	@Test
	public void testClone() {
		Assert.assertEquals(
			_rankingIndexCreationBackgroundTaskExecutor,
			_rankingIndexCreationBackgroundTaskExecutor.clone());
	}

	@Test
	public void testExecute() throws Exception {
		Mockito.doNothing(
		).when(
			_singleIndexToMultipleIndexImporter
		).importRankings();

		Assert.assertEquals(
			BackgroundTaskResult.SUCCESS,
			_rankingIndexCreationBackgroundTaskExecutor.execute(
				Mockito.mock(BackgroundTask.class)));
	}

	@Test
	public void testGetBackgroundTaskDisplay() {
		Assert.assertNull(
			_rankingIndexCreationBackgroundTaskExecutor.
				getBackgroundTaskDisplay(Mockito.mock(BackgroundTask.class)));
	}

	private RankingIndexCreationBackgroundTaskExecutor
		_rankingIndexCreationBackgroundTaskExecutor;
	private final SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter = Mockito.mock(
			SingleIndexToMultipleIndexImporter.class);

}