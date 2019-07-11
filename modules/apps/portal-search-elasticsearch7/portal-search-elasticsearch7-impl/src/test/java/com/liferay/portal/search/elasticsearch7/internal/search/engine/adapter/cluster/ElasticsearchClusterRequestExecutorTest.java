/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchClusterRequestExecutorTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_clusterRequestExecutor = new ElasticsearchClusterRequestExecutor() {
			{
				setHealthClusterRequestExecutor(_healthClusterRequestExecutor);
				setStateClusterRequestExecutor(_stateClusterRequestExecutor);
				setStatsClusterRequestExecutor(_statsClusterRequestExecutor);
			}
		};
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

	@Mock
	private HealthClusterRequestExecutor _healthClusterRequestExecutor;

	@Mock
	private StateClusterRequestExecutor _stateClusterRequestExecutor;

	@Mock
	private StatsClusterRequestExecutor _statsClusterRequestExecutor;

}