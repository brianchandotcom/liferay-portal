/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchClusterRequestExecutorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_clusterRequestExecutor = new ElasticsearchClusterRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_healthClusterRequestExecutor",
			_healthClusterRequestExecutor);
		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_stateClusterRequestExecutor",
			_stateClusterRequestExecutor);
		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_statsClusterRequestExecutor",
			_statsClusterRequestExecutor);
	}

	@Test
	public void testExecuteHealthClusterRequest() {
		HealthClusterRequest healthClusterRequest = new HealthClusterRequest(
			new String[] {RandomTestUtil.randomString()});

		_clusterRequestExecutor.execute(healthClusterRequest);

		Mockito.verify(
			_healthClusterRequestExecutor
		).execute(
			healthClusterRequest
		);
	}

	@Test
	public void testExecuteStateClusterRequest() {
		StateClusterRequest stateClusterRequest = new StateClusterRequest(
			new String[] {RandomTestUtil.randomString()});

		_clusterRequestExecutor.execute(stateClusterRequest);

		Mockito.verify(
			_stateClusterRequestExecutor
		).execute(
			stateClusterRequest
		);
	}

	@Test
	public void testExecuteStatsClusterRequest() {
		StatsClusterRequest statsClusterRequest = new StatsClusterRequest(
			new String[] {RandomTestUtil.randomString()});

		_clusterRequestExecutor.execute(statsClusterRequest);

		Mockito.verify(
			_statsClusterRequestExecutor
		).execute(
			statsClusterRequest
		);
	}

	private ClusterRequestExecutor _clusterRequestExecutor;
	private final HealthClusterRequestExecutor _healthClusterRequestExecutor =
		Mockito.mock(HealthClusterRequestExecutor.class);
	private final StateClusterRequestExecutor _stateClusterRequestExecutor =
		Mockito.mock(StateClusterRequestExecutor.class);
	private final StatsClusterRequestExecutor _statsClusterRequestExecutor =
		Mockito.mock(StatsClusterRequestExecutor.class);

}