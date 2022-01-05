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

package com.liferay.portal.search.elasticsearch7.internal.cluster;

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class ReplicasClusterListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_setEmbeddedCluster(true);
		_setMasterExecutor(true);

		Mockito.when(
			_replicasClusterContext.getClusterSize()
		).thenReturn(
			_REPLICAS + 1
		);

		Mockito.when(
			_replicasClusterContext.getReplicasManager()
		).thenReturn(
			_replicasManager
		);

		Mockito.when(
			_replicasClusterContext.getTargetIndexNames()
		).thenReturn(
			_INDICES
		);

		_replicasClusterListener = new ReplicasClusterListener(
			_replicasClusterContext);
	}

	@Test
	public void testAHappyDay() {
		processClusterEvent();
		_assertReplicasChanged();
	}

	@Test
	public void testLiferayClusterReportsEmpty() {
		Mockito.when(
			_replicasClusterContext.getClusterSize()
		).thenReturn(
			0
		);

		processClusterEvent();

		Mockito.verify(
			_replicasManager
		).updateNumberOfReplicas(
			0, _INDICES
		);
	}

	@Test
	public void testMasterTokenAcquired() {
		masterTokenAcquired();

		_assertReplicasChanged();
	}

	@Test
	public void testMasterTokenReleased() {
		masterTokenReleased();

		_assertReplicasUnchanged();
	}

	@Test
	public void testNonmasterLiferayNodeDoesNothing() {
		_setMasterExecutor(false);

		processClusterEvent();

		_assertReplicasUnchanged();
	}

	@Test
	public void testRemoteElasticsearchClusterIsLeftAlone() {
		_setEmbeddedCluster(false);

		processClusterEvent();

		_assertReplicasUnchanged();
	}

	@Test
	public void testResilientToUpdateFailures() {
		Throwable throwable = new RuntimeException();

		Mockito.doThrow(
			throwable
		).when(
			_replicasManager
		).updateNumberOfReplicas(
			Mockito.anyInt(), (String[])Mockito.anyVararg()
		);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				ReplicasClusterListener.class.getName(), Level.WARNING)) {

			masterTokenAcquired();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to update number of replicas", logEntry.getMessage());
			Assert.assertSame(throwable, logEntry.getThrowable());
		}
	}

	protected void masterTokenAcquired() {
		_replicasClusterListener.masterTokenAcquired();
	}

	protected void masterTokenReleased() {
		_replicasClusterListener.masterTokenReleased();
	}

	protected void processClusterEvent() {
		_replicasClusterListener.processClusterEvent(ClusterEvent.join());
	}

	private void _assertReplicasChanged() {
		Mockito.verify(
			_replicasManager
		).updateNumberOfReplicas(
			_REPLICAS, _INDICES
		);
	}

	private void _assertReplicasUnchanged() {
		Mockito.verify(
			_replicasManager, Mockito.never()
		).updateNumberOfReplicas(
			Mockito.anyInt(), (String[])Mockito.anyVararg()
		);
	}

	private void _setEmbeddedCluster(boolean value) {
		Mockito.when(
			_replicasClusterContext.isEmbeddedOperationMode()
		).thenReturn(
			value
		);
	}

	private void _setMasterExecutor(boolean value) {
		Mockito.when(
			_replicasClusterContext.isMaster()
		).thenReturn(
			value
		);
	}

	private static final String[] _INDICES = {
		RandomTestUtil.randomString(), RandomTestUtil.randomString()
	};

	private static final int _REPLICAS = RandomTestUtil.randomInt() - 1;

	@Mock
	private ReplicasClusterContext _replicasClusterContext;

	private ReplicasClusterListener _replicasClusterListener;

	@Mock
	private ReplicasManager _replicasManager;

}