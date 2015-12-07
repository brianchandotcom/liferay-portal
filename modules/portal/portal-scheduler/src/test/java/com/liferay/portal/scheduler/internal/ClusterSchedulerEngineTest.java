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

package com.liferay.portal.scheduler.internal;

import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.BaseDestination;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.servlet.PluginContextLifecycleThreadLocal;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.service.component.ComponentContext;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class ClusterSchedulerEngineTest {

	@Before
	public void setUp() throws Exception {
		setUpProps();

		setUpClusterLink();
		setUpClusterSchedulerEngine();
		setUpClusterInvokeAcceptor();
		setUpDestinationFactory();
		setUpComponentContext();
		setUpPortalUUIDUtil();
		setUpSchedulerEngineHelper(setUpJSONFactory());

		_schedulerEngineHelperImpl.setSchedulerEngine(_clusterSchedulerEngine);

		_clusterSchedulerEngine.setSchedulerEngineHelper(
			_schedulerEngineHelperImpl);
	}

	@Test
	public void testCreateClusterSchedulerEngine1() throws Exception {
		_schedulerEngineHelperImpl.setSchedulerEngine(_mockSchedulerEngine);

		_schedulerEngineHelperImpl.activate(_componentContext);

		SchedulerEngine schedulerEngine =
			_schedulerEngineHelperImpl.getSchedulerEngine();

		Assert.assertNotSame(_mockSchedulerEngine, schedulerEngine);
		Assert.assertTrue(ProxyUtil.isProxyClass(schedulerEngine.getClass()));
	}

	@Test
	public void testCreateClusterSchedulerEngine2() throws Exception {
		Mockito.when(
			_props.get(PropsKeys.SCHEDULER_ENABLED)
		).thenReturn(
			"false"
		);

		_schedulerEngineHelperImpl.setSchedulerEngine(_mockSchedulerEngine);

		_schedulerEngineHelperImpl.activate(_componentContext);

		SchedulerEngine schedulerEngine =
			_schedulerEngineHelperImpl.getSchedulerEngine();

		Assert.assertSame(_mockSchedulerEngine, schedulerEngine);
	}

	@Test
	public void testCreateClusterSchedulerEngine3() throws Exception {
		Mockito.when(
			_clusterLink.isEnabled()
		).thenReturn(
			false
		);

		_schedulerEngineHelperImpl.setSchedulerEngine(_mockSchedulerEngine);

		_schedulerEngineHelperImpl.activate(_componentContext);

		SchedulerEngine schedulerEngine =
			_schedulerEngineHelperImpl.getSchedulerEngine();

		Assert.assertSame(_mockSchedulerEngine, schedulerEngine);
	}

	@Test
	public void testCreateClusterSchedulerEngine4() throws Exception {
		Mockito.when(
			_clusterLink.isEnabled()
		).thenReturn(
			false
		);

		Mockito.when(
			_props.get(PropsKeys.SCHEDULER_ENABLED)
		).thenReturn(
			"false"
		);

		_schedulerEngineHelperImpl.setSchedulerEngine(_mockSchedulerEngine);

		_schedulerEngineHelperImpl.activate(_componentContext);

		SchedulerEngine schedulerEngine =
			_schedulerEngineHelperImpl.getSchedulerEngine();

		Assert.assertSame(_mockSchedulerEngine, schedulerEngine);
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testDeleteOnMaster() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job by jobName and groupName

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(4, 4);

		_clusterSchedulerEngine.start();

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(4, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(3, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		_clusterSchedulerEngine.delete(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 3, PERSISTED job by jobName and groupName

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.PERSISTED);

		Assert.assertEquals(4, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.PERSISTED);

		Assert.assertEquals(3, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, PERSISTED jobs by groupName

		_clusterSchedulerEngine.delete(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.PERSISTED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testDeleteOnSlave() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job by jobName and groupName

		_mockClusterMasterExecutor.reset(false, 4, 0);

		_mockSchedulerEngine.resetJobs(0, 4);

		_clusterSchedulerEngine.start();

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(4, _memoryClusteredJobs.size());

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(3, _memoryClusteredJobs.size());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, not existed group

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = getMemoryClusteredJobs(_NOT_EXISTED_GROUP_NAME);

		Assert.assertTrue(schedulerResponses.isEmpty());

		_clusterSchedulerEngine.delete(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = getMemoryClusteredJobs(_NOT_EXISTED_GROUP_NAME);

		Assert.assertTrue(schedulerResponses.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 3, MEMORY_CLUSTERED jobs by groupName

		_clusterSchedulerEngine.delete(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@Test
	public void testGetSchedulerJobs() throws SchedulerException {
		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(4, 2);

		_clusterSchedulerEngine.start();

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(6, schedulerResponses.size());
	}

	@Test
	public void testMasterToSlave() throws SchedulerException {

		// Test 1, with log disabled

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(4, 2);

		_clusterSchedulerEngine.start();

		Assert.assertTrue(_mockClusterMasterExecutor.isMaster());

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(4, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterSchedulerEngine.class.getName(), Level.OFF)) {

			_mockClusterMasterExecutor.reset(false, 4, 2);

			ClusterMasterTokenTransitionListener
				clusterMasterTokenTransitionListener =
					_mockClusterMasterExecutor.
						getClusterMasterTokenTransitionListener();

			clusterMasterTokenTransitionListener.masterTokenReleased();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
			Assert.assertFalse(_mockClusterMasterExecutor.isMaster());

			schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

			Assert.assertTrue(schedulerResponses.isEmpty());
			Assert.assertEquals(4, _memoryClusteredJobs.size());

			// Test 2, with log enabled

			_mockClusterMasterExecutor.reset(true, 0, 0);

			_mockSchedulerEngine.resetJobs(4, 2);

			_clusterSchedulerEngine.start();

			_memoryClusteredJobs.clear();

			Assert.assertTrue(_mockClusterMasterExecutor.isMaster());

			schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

			Assert.assertEquals(4, schedulerResponses.size());
			Assert.assertTrue(_memoryClusteredJobs.isEmpty());

			logRecords = captureHandler.resetLogLevel(Level.ALL);

			_mockClusterMasterExecutor.reset(false, 4, 2);

			clusterMasterTokenTransitionListener =
				_mockClusterMasterExecutor.
					getClusterMasterTokenTransitionListener();

			clusterMasterTokenTransitionListener.masterTokenReleased();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"MEMORY_CLUSTERED jobs stopped running on this node",
				logRecord.getMessage());
			Assert.assertFalse(_mockClusterMasterExecutor.isMaster());

			schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

			Assert.assertTrue(schedulerResponses.isEmpty());
			Assert.assertEquals(4, _memoryClusteredJobs.size());
		}
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testPauseAndResumeOnMaster() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job by jobName and groupName

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(4, 4);

		_clusterSchedulerEngine.start();

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.pause(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.PAUSED);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 3, PERSISTED job by jobName and groupName

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, PERSISTED jobs by groupName

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.pause(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.PAUSED);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testPauseAndResumeOnSlave() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job by jobName and groupName

		_mockClusterMasterExecutor.reset(false, 4, 0);

		_mockSchedulerEngine.resetJobs(0, 4);

		_clusterSchedulerEngine.start();

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		SchedulerResponse schedulerResponse = getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		schedulerResponse = getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		schedulerResponse = getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = getMemoryClusteredJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		_clusterSchedulerEngine.pause(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = getMemoryClusteredJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.PAUSED);
		}

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = getMemoryClusteredJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 3, not existed job

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));
		Assert.assertNull(
			getMemoryClusteredJob(_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME));

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));
		Assert.assertNull(
			getMemoryClusteredJob(_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME));

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, not existed group

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = getMemoryClusteredJobs(_NOT_EXISTED_GROUP_NAME);

		Assert.assertTrue(schedulerResponses.isEmpty());

		_clusterSchedulerEngine.pause(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = getMemoryClusteredJobs(_NOT_EXISTED_GROUP_NAME);

		Assert.assertTrue(schedulerResponses.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testScheduleOnMaster() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(1, 1);

		_clusterSchedulerEngine.start();

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(1, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		Trigger trigger = getTrigger(
			_TEST_JOB_NAME_PREFIX + "new", _MEMORY_CLUSTER_TEST_GROUP_NAME,
			_DEFAULT_INTERVAL);

		_clusterSchedulerEngine.schedule(
			trigger, StringPool.BLANK, StringPool.BLANK, new Message(),
			StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(2, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, PERSISTED job

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.PERSISTED);

		Assert.assertEquals(1, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		trigger = getTrigger(
			_TEST_JOB_NAME_PREFIX + "new", _PERSISTENT_TEST_GROUP_NAME,
			_DEFAULT_INTERVAL);

		_clusterSchedulerEngine.schedule(
			trigger, StringPool.BLANK, StringPool.BLANK, new Message(),
			StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.PERSISTED);

		Assert.assertEquals(2, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testScheduleOnSlave() throws SchedulerException {
		_mockClusterMasterExecutor.reset(false, 1, 0);

		_mockSchedulerEngine.resetJobs(0, 0);

		_clusterSchedulerEngine.start();

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(1, _memoryClusteredJobs.size());

		Trigger trigger = getTrigger(
			_TEST_JOB_NAME_PREFIX + "new", _MEMORY_CLUSTER_TEST_GROUP_NAME,
			_DEFAULT_INTERVAL);

		_clusterSchedulerEngine.schedule(
			trigger, StringPool.BLANK, StringPool.BLANK, new Message(),
			StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(2, _memoryClusteredJobs.size());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@Test
	public void testShutdown() throws SchedulerException {
		_clusterSchedulerEngine.start();

		Assert.assertNotNull(
			_mockClusterMasterExecutor.
				getClusterMasterTokenTransitionListener());

		_clusterSchedulerEngine.shutdown();

		Assert.assertNull(
			_mockClusterMasterExecutor.
				getClusterMasterTokenTransitionListener());
	}

	@Test
	public void testSlaveToMaster() throws SchedulerException {

		// Test 1, with log disabled

		_mockClusterMasterExecutor.reset(false, 4, 0);

		_mockSchedulerEngine.resetJobs(0, 0);

		_clusterSchedulerEngine.start();

		Assert.assertFalse(_mockClusterMasterExecutor.isMaster());

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(4, _memoryClusteredJobs.size());

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterSchedulerEngine.class.getName(), Level.OFF)) {

			_mockClusterMasterExecutor.reset(true, 0, 0);

			ClusterMasterTokenTransitionListener
				clusterMasterTokenTransitionListener =
					_mockClusterMasterExecutor.
						getClusterMasterTokenTransitionListener();

			clusterMasterTokenTransitionListener.masterTokenAcquired();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
			Assert.assertTrue(_mockClusterMasterExecutor.isMaster());

			schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

			Assert.assertEquals(4, schedulerResponses.size());

			SchedulerResponse schedulerResponse =
				_clusterSchedulerEngine.getScheduledJob(
					_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
					StorageType.MEMORY_CLUSTERED);

			assertTriggerState(schedulerResponse, TriggerState.PAUSED);

			Assert.assertTrue(_memoryClusteredJobs.isEmpty());

			// Test 2, with log enabled

			_mockClusterMasterExecutor.reset(false, 4, 0);

			_mockSchedulerEngine.resetJobs(0, 0);

			_clusterSchedulerEngine.start();

			Assert.assertFalse(_mockClusterMasterExecutor.isMaster());

			schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

			Assert.assertTrue(schedulerResponses.isEmpty());
			Assert.assertEquals(4, _memoryClusteredJobs.size());

			logRecords = captureHandler.resetLogLevel(Level.ALL);

			_mockClusterMasterExecutor.reset(true, 0, 0);

			clusterMasterTokenTransitionListener =
				_mockClusterMasterExecutor.
					getClusterMasterTokenTransitionListener();

			clusterMasterTokenTransitionListener.masterTokenAcquired();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"MEMORY_CLUSTERED jobs are running on this node",
				logRecord.getMessage());
			Assert.assertTrue(_mockClusterMasterExecutor.isMaster());

			schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

			Assert.assertEquals(4, schedulerResponses.size());
			Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		}
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testStart() {
		_mockClusterMasterExecutor.reset(false, 0, 0);

		_mockClusterMasterExecutor.setException(true);

		try {
			_clusterSchedulerEngine.start();

			Assert.fail();
		}
		catch (SchedulerException se) {
			Assert.assertEquals(
				"Unable to initialize scheduler", se.getMessage());
		}
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testSuppressErrorOnMaster() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job by jobName and groupName

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(1, 1);

		_clusterSchedulerEngine.start();

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED);

		assertSuppressErrorValue(schedulerResponse, null);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		assertSuppressErrorValue(schedulerResponse, Boolean.TRUE);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, PERSISTED job by jobName and groupName

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertSuppressErrorValue(schedulerResponse, null);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertSuppressErrorValue(schedulerResponse, Boolean.TRUE);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testSuppressErrorOnSlave() throws SchedulerException {
		_mockClusterMasterExecutor.reset(false, 1, 0);

		_mockSchedulerEngine.resetJobs(0, 0);

		_clusterSchedulerEngine.start();

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		SchedulerResponse schedulerResponse = getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		assertSuppressErrorValue(schedulerResponse, null);

		_clusterSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		schedulerResponse = getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		assertSuppressErrorValue(schedulerResponse, null);

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testThreadLocal() throws SchedulerException {

		// Test 1, PERSISTED when portal is starting

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED when portal is starting

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.start();

		// Test 3, PERSISTED when portal is started

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, MEMORY_CLUSTERED when portal is started

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 5, PERSISTED when plugin is starting

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(true);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 6, MEMORY_CLUSTERED when plugin is starting

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(true);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 7, PERSISTED when plugin is destroying

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(true);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 8, MEMORY_CLUSTERED when plugin is destroying

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(true);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 9, PERSISTED when cluster invoke is disabled

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 10, PERSISTED when cluster invoke is enabled

		ClusterInvokeThreadLocal.setEnabled(true);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 11, MEMORY_CLUSTERED when cluster invoke is disabled

		ClusterInvokeThreadLocal.setEnabled(false);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 12, MEMORY_CLUSTERED when cluster invoke is enabled

		ClusterInvokeThreadLocal.setEnabled(true);

		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testUnscheduleOnMaster() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job by jobName and groupName

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(4, 4);

		_clusterSchedulerEngine.start();

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			if (curSchedulerResponse.getJobName().equals(_TEST_JOB_NAME_0)) {
				assertTriggerState(
					curSchedulerResponse, TriggerState.UNSCHEDULED);
			}
			else {
				assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
			}
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.unschedule(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.UNSCHEDULED);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 3, PERSISTED job by jobName and groupName

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, PERSISTED jobs by groupName

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			if (curSchedulerResponse.getJobName().equals(_TEST_JOB_NAME_0)) {
				assertTriggerState(
					curSchedulerResponse, TriggerState.UNSCHEDULED);
			}
			else {
				assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
			}
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.unschedule(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.UNSCHEDULED);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testUnscheduleOnSlave() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job by jobName and groupName

		_mockClusterMasterExecutor.reset(false, 4, 0);

		_mockSchedulerEngine.resetJobs(0, 4);

		_clusterSchedulerEngine.start();

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		SchedulerResponse schedulerResponse = getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_clusterSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));
		Assert.assertNull(
			getMemoryClusteredJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME));

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = getMemoryClusteredJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		_clusterSchedulerEngine.unschedule(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testUpdateOnMaster() throws SchedulerException {

		// Test 1, MEMORY_CLUSTERED job by jobName and groupName

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(1, 1);

		_clusterSchedulerEngine.start();

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED);

		assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		Trigger trigger = getTrigger(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			_DEFAULT_INTERVAL * 2);

		_clusterSchedulerEngine.update(trigger, StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL * 2);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, PERSISTED job by jobName and groupName

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		trigger = getTrigger(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			_DEFAULT_INTERVAL * 2);

		_clusterSchedulerEngine.update(trigger, StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL * 2);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testUpdateOnSlave() throws SchedulerException {

		// Test 1, without exception

		_mockClusterMasterExecutor.reset(false, 1, 0);

		_mockSchedulerEngine.resetJobs(0, 0);

		_clusterSchedulerEngine.start();

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		SchedulerResponse schedulerResponse = getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL);

		Trigger trigger = getTrigger(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			_DEFAULT_INTERVAL * 2);

		_clusterSchedulerEngine.update(trigger, StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		schedulerResponse = getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL * 2);

		// Test 2, with not existed group name

		trigger = getTrigger(
			_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME, _DEFAULT_INTERVAL * 2);

		try {
			_clusterSchedulerEngine.update(
				trigger, StorageType.MEMORY_CLUSTERED);

			Assert.fail();
		}
		catch (SchedulerException se) {
			Assert.assertEquals(
				"Unable to update trigger for memory clustered job",
				se.getMessage());
		}

		// Test 3, with not existed job name

		trigger = getTrigger(
			_TEST_JOB_NAME_PREFIX, _NOT_EXISTED_GROUP_NAME,
			_DEFAULT_INTERVAL * 2);

		try {
			_clusterSchedulerEngine.update(
				trigger, StorageType.MEMORY_CLUSTERED);

			Assert.fail();
		}
		catch (SchedulerException se) {
			Assert.assertEquals(
				"Unable to update trigger for memory clustered job",
				se.getMessage());
		}
	}

	@Rule
	public final AspectJNewEnvTestRule aspectJNewEnvTestRule =
		AspectJNewEnvTestRule.INSTANCE;

	@Aspect
	public static class ClusterableContextThreadLocalAdvice {

		public static Map<String, Serializable> getAndClearThreadLocals() {
			Map<String, Serializable> threadLocal = new HashMap<>(
				_threadLocals);

			_threadLocals.clear();

			return threadLocal;
		}

		@Around(
			"execution(void com.liferay.portal.kernel.cluster." +
				"ClusterableContextThreadLocal.putThreadLocalContext(" +
					"java.lang.String, java.io.Serializable)) && " +
						"args(key, value)"
		)
		public void loadIndexesFromCluster(String key, Serializable value) {
			_threadLocals.put(key, value);
		}

		private static final Map<String, Serializable> _threadLocals =
			new HashMap<>();

	}

	protected static Trigger getTrigger(
		String jobName, String groupName, int interval) {

		return new MockTrigger(
			jobName, groupName, null, null, interval, TimeUnit.SECOND);
	}

	protected void assertSuppressErrorValue(
		SchedulerResponse schedulerResponse, Object expectedValue) {

		Message message = schedulerResponse.getMessage();

		Assert.assertEquals(expectedValue, message.get(_SUPPRESS_ERROR));
	}

	protected void assertTriggerContent(
		SchedulerResponse schedulerResponse, int expectedInterval) {

		MockTrigger mockTrigger = (MockTrigger)schedulerResponse.getTrigger();

		Assert.assertEquals(expectedInterval, mockTrigger.getInterval());
	}

	protected void assertTriggerState(
		SchedulerResponse schedulerResponse,
		TriggerState expectedTriggerState) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		Assert.assertEquals(expectedTriggerState, jobState.getTriggerState());
	}

	protected SchedulerResponse getMemoryClusteredJob(
		String jobName, String groupName) {

		ObjectValuePair<SchedulerResponse, TriggerState> objectValuePair =
			_memoryClusteredJobs.get(
				groupName.concat(StringPool.PERIOD).concat(jobName));

		if (objectValuePair == null) {
			return null;
		}

		SchedulerResponse schedulerResponse = objectValuePair.getKey();

		Message message = schedulerResponse.getMessage();

		message.put(
			SchedulerEngine.JOB_STATE,
			new JobState(objectValuePair.getValue()));

		return schedulerResponse;
	}

	protected List<SchedulerResponse> getMemoryClusteredJobs(String groupName) {
		List<SchedulerResponse> schedulerResponses = new ArrayList<>();

		for (ObjectValuePair<SchedulerResponse, TriggerState> objectValuePair :
				_memoryClusteredJobs.values()) {

			SchedulerResponse schedulerResponse = objectValuePair.getKey();

			if (groupName.equals(schedulerResponse.getGroupName())) {
				schedulerResponses.add(
					getMemoryClusteredJob(
						schedulerResponse.getJobName(), groupName));
			}
		}

		return schedulerResponses;
	}

	protected void setUpClusterInvokeAcceptor() throws Exception {
		Class<? extends ClusterInvokeAcceptor> clusterInvokeAcceptorClass =
			(Class<? extends ClusterInvokeAcceptor>)Class.forName(
				SchedulerClusterInvokeAcceptor.class.getName());

		Constructor<? extends ClusterInvokeAcceptor> constructor =
			clusterInvokeAcceptorClass.getDeclaredConstructor();

		constructor.setAccessible(true);

		_clusterInvokeAcceptor = constructor.newInstance();
	}

	protected void setUpClusterLink() throws Exception {
		_clusterLink = Mockito.mock(ClusterLink.class);

		Mockito.when(
			_clusterLink.isEnabled()
		).thenReturn(
			true
		);
	}

	protected void setUpClusterSchedulerEngine() {
		_mockSchedulerEngine = new MockSchedulerEngine();

		_clusterSchedulerEngine = new ClusterSchedulerEngine(
			_mockSchedulerEngine);

		_clusterSchedulerEngine.setClusterMasterExecutor(
			_mockClusterMasterExecutor);
		_clusterSchedulerEngine.setProps(_props);

		_memoryClusteredJobs = ReflectionTestUtil.getFieldValue(
			_clusterSchedulerEngine, "_memoryClusteredJobs");
	}

	protected void setUpComponentContext() throws Exception {
		_componentContext = Mockito.mock(ComponentContext.class);

		BundleContext bundleContext = Mockito.mock(BundleContext.class);

		Mockito.when(
			bundleContext.createFilter(Mockito.anyString())
		).thenReturn(
			Mockito.mock(Filter.class)
		);

		Mockito.when(
			_componentContext.getBundleContext()
		).thenReturn(
			bundleContext
		);
	}

	protected void setUpDestinationFactory() {
		_destinationFactory = new DestinationFactory() {

			@Override
			public Destination createDestination(
				DestinationConfiguration destinationConfiguration) {

				return new BaseDestination() {};
			}

			@Override
			public Collection<String> getDestinationTypes() {
				return null;
			}

		};
	}

	protected JSONFactory setUpJSONFactory() {
		JSONFactory jsonFactory = Mockito.mock(JSONFactory.class);

		Mockito.when(
			jsonFactory.deserialize(Mockito.anyString())
		).then(
			new Answer<Object>() {

				@Override
				public Object answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					String base64 = (String)invocationOnMock.getArguments()[0];

					byte[] bytes = Base64.decode(base64);

					ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

					ObjectInputStream ois = new ObjectInputStream(bais);

					return ois.readObject();
				}

			}
		);

		Mockito.when(
			jsonFactory.serialize(Mockito.anyObject())
		).then(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object obj = invocationOnMock.getArguments()[0];

					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					ObjectOutputStream oos = new ObjectOutputStream(baos);

					oos.writeObject(obj);

					byte[] bytes = baos.toByteArray();

					oos.close();

					return Base64.encode(bytes);
				}

			}
		);

		return jsonFactory;
	}

	protected void setUpPortalUUIDUtil() {
		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		PortalUUID portalUUID = Mockito.mock(PortalUUID.class);

		Mockito.when(
			portalUUID.generate()
		).then(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					UUID uuid = new UUID(
						SecureRandomUtil.nextLong(),
						SecureRandomUtil.nextLong());

					return uuid.toString();
				}

			}
		);

		portalUUIDUtil.setPortalUUID(portalUUID);
	}

	protected void setUpProps() {
		_props = Mockito.mock(Props.class);

		Mockito.when(
			_props.get(PropsKeys.SCHEDULER_ENABLED)
		).thenReturn(
			"true"
		);
	}

	protected void setUpSchedulerEngineHelper(JSONFactory jsonFactory) {
		_schedulerEngineHelperImpl = new SchedulerEngineHelperImpl();

		_schedulerEngineHelperImpl.setClusterMasterExecutor(
			_mockClusterMasterExecutor);
		_schedulerEngineHelperImpl.setJsonFactory(jsonFactory);
		_schedulerEngineHelperImpl.setProps(_props);

		_schedulerEngineHelperImpl.setClusterLink(_clusterLink);
		_schedulerEngineHelperImpl.setDestinationFactory(_destinationFactory);
	}

	private static final int _DEFAULT_INTERVAL = 20;

	private static final String _MEMORY_CLUSTER_TEST_GROUP_NAME =
		"memory.cluster.test.group";

	private static final String _NOT_EXISTED_GROUP_NAME =
		"not.existed.test.group";

	private static final String _PERSISTENT_TEST_GROUP_NAME =
		"persistent.test.group";

	private static final String _SUPPRESS_ERROR = "suppressError";

	private static final String _TEST_JOB_NAME_0 = "test.job.0";

	private static final String _TEST_JOB_NAME_PREFIX = "test.job.";

	private static final MethodKey _getScheduledJobsMethodKey = new MethodKey(
		SchedulerEngineHelperUtil.class, "getScheduledJobs", StorageType.class);

	private ClusterInvokeAcceptor _clusterInvokeAcceptor;
	private ClusterLink _clusterLink;
	private ClusterSchedulerEngine _clusterSchedulerEngine;
	private ComponentContext _componentContext;
	private DestinationFactory _destinationFactory;
	private Map<String, ObjectValuePair<SchedulerResponse, TriggerState>>
		_memoryClusteredJobs;
	private final MockClusterMasterExecutor _mockClusterMasterExecutor =
		new MockClusterMasterExecutor();
	private MockSchedulerEngine _mockSchedulerEngine;
	private Props _props;
	private SchedulerEngineHelperImpl _schedulerEngineHelperImpl;

	private static class MockClusterMasterExecutor
		implements ClusterMasterExecutor {

		@Override
		public void addClusterMasterTokenTransitionListener(
			ClusterMasterTokenTransitionListener
				clusterMasterTokenAcquisitionListener) {

			_clusterMasterTokenTransitionListener =
				clusterMasterTokenAcquisitionListener;
		}

		@Override
		public <T> NoticeableFuture<T> executeOnMaster(
			MethodHandler methodHandler) {

			if (_exception) {
				throw new SystemException();
			}

			T result = null;

			MethodKey methodKey = methodHandler.getMethodKey();

			if (methodKey.equals(_getScheduledJobsMethodKey)) {
				StorageType storageType =
					(StorageType)methodHandler.getArguments()[0];

				result = (T)_mockSchedulerEngine.getScheduledJobs(storageType);
			}

			DefaultNoticeableFuture<T> defaultNoticeableFuture =
				new DefaultNoticeableFuture<>();

			defaultNoticeableFuture.set(result);

			return defaultNoticeableFuture;
		}

		public ClusterMasterTokenTransitionListener
			getClusterMasterTokenTransitionListener() {

			return _clusterMasterTokenTransitionListener;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public boolean isMaster() {
			return _master;
		}

		@Override
		public void removeClusterMasterTokenTransitionListener(
			ClusterMasterTokenTransitionListener
				clusterMasterTokenAcquisitionListener) {

			if (_clusterMasterTokenTransitionListener ==
					clusterMasterTokenAcquisitionListener) {

				_clusterMasterTokenTransitionListener = null;
			}
		}

		public void reset(
			boolean master, int memoryClusterJobs, int persistentJobs) {

			_master = master;

			_mockSchedulerEngine.resetJobs(memoryClusterJobs, persistentJobs);
		}

		public void setException(boolean exception) {
			_exception = exception;
		}

		private ClusterMasterTokenTransitionListener
			_clusterMasterTokenTransitionListener;
		private boolean _exception;
		private boolean _master;
		private final MockSchedulerEngine _mockSchedulerEngine =
			new MockSchedulerEngine();

	}

	private static class MockSchedulerEngine implements SchedulerEngine {

		@Override
		public void delete(String groupName, StorageType storageType) {
			Set<String> keySet = _defaultJobs.keySet();

			Iterator<String> iterator = keySet.iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();

				if (key.contains(groupName) &&
					key.contains(storageType.toString())) {

					iterator.remove();
				}
			}
		}

		@Override
		public void delete(
			String jobName, String groupName, StorageType storageType) {

			_defaultJobs.remove(_getFullName(jobName, groupName, storageType));
		}

		@Override
		public SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType) {

			return _defaultJobs.get(
				_getFullName(jobName, groupName, storageType));
		}

		@Override
		public List<SchedulerResponse> getScheduledJobs() {
			return new ArrayList<>(_defaultJobs.values());
		}

		@Override
		public List<SchedulerResponse> getScheduledJobs(
			StorageType storageType) {

			List<SchedulerResponse> schedulerResponses = new ArrayList<>();

			for (SchedulerResponse schedulerResponse : _defaultJobs.values()) {
				if (storageType == schedulerResponse.getStorageType()) {
					schedulerResponses.add(schedulerResponse);
				}
			}

			return schedulerResponses;
		}

		@Override
		public List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType) {

			List<SchedulerResponse> schedulerResponses = new ArrayList<>();

			for (Map.Entry<String, SchedulerResponse> entry :
					_defaultJobs.entrySet()) {

				String key = entry.getKey();

				if (key.contains(groupName) &&
					key.contains(storageType.toString())) {

					schedulerResponses.add(entry.getValue());
				}
			}

			return schedulerResponses;
		}

		@Override
		public void pause(String groupName, StorageType storageType) {
			for (SchedulerResponse schedulerResponse :
					getScheduledJobs(groupName, storageType)) {

				Message message = schedulerResponse.getMessage();

				message.put(
					SchedulerEngine.JOB_STATE,
					new JobState(TriggerState.PAUSED));
			}
		}

		@Override
		public void pause(
			String jobName, String groupName, StorageType storageType) {

			SchedulerResponse schedulerResponse = getScheduledJob(
				jobName, groupName, storageType);

			Message message = schedulerResponse.getMessage();

			message.put(
				SchedulerEngine.JOB_STATE, new JobState(TriggerState.PAUSED));
		}

		public void resetJobs(int memoryClusterJobs, int persistentJobs) {
			_defaultJobs.clear();

			for (int i = 0; i < memoryClusterJobs; i++) {
				_addJobs(
					_TEST_JOB_NAME_PREFIX.concat(String.valueOf(i)),
					_MEMORY_CLUSTER_TEST_GROUP_NAME,
					StorageType.MEMORY_CLUSTERED, null, null);
			}

			for (int i = 0; i < persistentJobs; i++) {
				_addJobs(
					_TEST_JOB_NAME_PREFIX.concat(String.valueOf(i)),
					_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED, null,
					null);
			}
		}

		@Override
		public void resume(String groupName, StorageType storageType) {
			for (SchedulerResponse schedulerResponse :
					getScheduledJobs(groupName, storageType)) {

				Message message = schedulerResponse.getMessage();

				message.put(
					SchedulerEngine.JOB_STATE,
					new JobState(TriggerState.NORMAL));
			}
		}

		@Override
		public void resume(
			String jobName, String groupName, StorageType storageType) {

			SchedulerResponse schedulerResponse = getScheduledJob(
				jobName, groupName, storageType);

			Message message = schedulerResponse.getMessage();

			message.put(
				SchedulerEngine.JOB_STATE, new JobState(TriggerState.NORMAL));
		}

		@Override
		public void schedule(
				Trigger trigger, String description, String destinationName,
				Message message, StorageType storageType)
			throws SchedulerException {

			String jobName = trigger.getJobName();

			if (!jobName.startsWith(_TEST_JOB_NAME_PREFIX)) {
				throw new SchedulerException("Invalid job name " + jobName);
			}

			_addJobs(
				trigger.getJobName(), trigger.getGroupName(), storageType,
				trigger, message);
		}

		@Override
		public void shutdown() {
			_defaultJobs.clear();
		}

		@Override
		public void start() {
		}

		@Override
		public void suppressError(
			String jobName, String groupName, StorageType storageType) {

			SchedulerResponse schedulerResponse = getScheduledJob(
				jobName, groupName, storageType);

			Message message = schedulerResponse.getMessage();

			message.put(_SUPPRESS_ERROR, Boolean.TRUE);
		}

		@Override
		public void unschedule(String groupName, StorageType storageType) {
			for (SchedulerResponse schedulerResponse :
					getScheduledJobs(groupName, storageType)) {

				Message message = schedulerResponse.getMessage();

				message.put(
					SchedulerEngine.JOB_STATE,
					new JobState(TriggerState.UNSCHEDULED));
			}
		}

		@Override
		public void unschedule(
			String jobName, String groupName, StorageType storageType) {

			SchedulerResponse schedulerResponse = getScheduledJob(
				jobName, groupName, storageType);

			Message message = schedulerResponse.getMessage();

			message.put(
				SchedulerEngine.JOB_STATE,
				new JobState(TriggerState.UNSCHEDULED));
		}

		@Override
		public void update(Trigger trigger, StorageType storageType) {
			SchedulerResponse schedulerResponse = getScheduledJob(
				trigger.getJobName(), trigger.getGroupName(), storageType);

			schedulerResponse.setTrigger(trigger);
		}

		private SchedulerResponse _addJobs(
			String jobName, String groupName, StorageType storageType,
			Trigger trigger, Message message) {

			SchedulerResponse schedulerResponse = new SchedulerResponse();

			schedulerResponse.setDestinationName(
				DestinationNames.SCHEDULER_DISPATCH);
			schedulerResponse.setGroupName(groupName);
			schedulerResponse.setJobName(jobName);

			if (message == null) {
				message = new Message();
			}

			message.put(
				SchedulerEngine.JOB_STATE, new JobState(TriggerState.NORMAL));

			schedulerResponse.setMessage(message);
			schedulerResponse.setStorageType(storageType);

			if (trigger == null) {
				trigger = getTrigger(jobName, groupName, _DEFAULT_INTERVAL);
			}

			schedulerResponse.setTrigger(trigger);

			_defaultJobs.put(
				_getFullName(jobName, groupName, storageType),
				schedulerResponse);

			return schedulerResponse;
		}

		private String _getFullName(
			String jobName, String groupName, StorageType storageType) {

			return groupName + StringPool.PERIOD + jobName + storageType;
		}

		private final Map<String, SchedulerResponse> _defaultJobs =
			new HashMap<>();

	}

	private static class MockTrigger implements Trigger {

		public MockTrigger(
			String jobName, String groupName, Date startDate, Date endDate,
			int interval, TimeUnit timeUnit) {

			_jobName = jobName;
			_groupName = groupName;
			_startDate = startDate;
			_endDate = endDate;
			_interval = interval;
			_timeUnit = timeUnit;
		}

		@Override
		public Date getEndDate() {
			return _endDate;
		}

		@Override
		public String getGroupName() {
			return _groupName;
		}

		public int getInterval() {
			return _interval;
		}

		@Override
		public String getJobName() {
			return _jobName;
		}

		@Override
		public Date getStartDate() {
			return _startDate;
		}

		public TimeUnit getTimeUnit() {
			return _timeUnit;
		}

		@Override
		public Serializable getWrappedTrigger() {
			return null;
		}

		private final Date _endDate;
		private final String _groupName;
		private final int _interval;
		private final String _jobName;
		private final Date _startDate;
		private final TimeUnit _timeUnit;

	}

}