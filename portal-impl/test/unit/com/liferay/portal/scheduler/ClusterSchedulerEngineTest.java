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

package com.liferay.portal.scheduler;

import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.servlet.PluginContextLifecycleThreadLocal;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvMethodRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.io.Serializable;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class ClusterSchedulerEngineTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		_mockClusterMasterExecutor = new MockClusterMasterExecutor();

		ClusterMasterExecutorUtil clusterMasterExecutorUtil =
			new ClusterMasterExecutorUtil();

		clusterMasterExecutorUtil.setClusterMasterExecutor(
			_mockClusterMasterExecutor);

		_mockSchedulerEngine = new MockSchedulerEngine();

		_clusterSchedulerEngine = new ClusterSchedulerEngine(
			_mockSchedulerEngine);

		_memoryClusteredJobs = ReflectionTestUtil.getFieldValue(
			_clusterSchedulerEngine, "_memoryClusteredJobs");

		SchedulerEngineHelperImpl schedulerEngineHelperImpl =
			new SchedulerEngineHelperImpl();

		schedulerEngineHelperImpl.setSchedulerEngine(_clusterSchedulerEngine);

		SchedulerEngineHelperUtil schedulerEngineHelperUtil =
			new SchedulerEngineHelperUtil();

		schedulerEngineHelperUtil.setSchedulerEngineHelper(
			schedulerEngineHelperImpl);

		Class<? extends ClusterInvokeAcceptor> clusterInvokeAcceptorClass =
			(Class<? extends ClusterInvokeAcceptor>)Class.forName(
				ClusterSchedulerEngine.class.getName() +
					"$SchedulerClusterInvokeAcceptor");

		Constructor<? extends ClusterInvokeAcceptor> constructor =
			clusterInvokeAcceptorClass.getDeclaredConstructor();

		constructor.setAccessible(true);

		_clusterInvokeAcceptor = constructor.newInstance();
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkEnabledAdvice.class,
			EnableSchedulerEnabledAdvice.class
		}
	)
	@Test
	public void testCreateClusterSchedulerEngine1() {
		SchedulerEngine schedulerEngine =
			ClusterSchedulerEngine.createClusterSchedulerEngine(
				_mockSchedulerEngine);

		Assert.assertNotSame(_mockSchedulerEngine, schedulerEngine);
	}

	@AdviseWith(
		adviceClasses = {
			EnableClusterLinkEnabledAdvice.class,
			DisableSchedulerEnabledAdvice.class
		}
	)
	@Test
	public void testCreateClusterSchedulerEngine2() {
		SchedulerEngine schedulerEngine =
			ClusterSchedulerEngine.createClusterSchedulerEngine(
				_mockSchedulerEngine);

		Assert.assertSame(_mockSchedulerEngine, schedulerEngine);
	}

	@AdviseWith(
		adviceClasses = {
			DisableClusterLinkEnabledAdvice.class,
			EnableSchedulerEnabledAdvice.class
		}
	)
	@Test
	public void testCreateClusterSchedulerEngine3() {
		SchedulerEngine schedulerEngine =
			ClusterSchedulerEngine.createClusterSchedulerEngine(
				_mockSchedulerEngine);

		Assert.assertSame(_mockSchedulerEngine, schedulerEngine);
	}

	@AdviseWith(
		adviceClasses = {
			DisableClusterLinkEnabledAdvice.class,
			DisableSchedulerEnabledAdvice.class
		}
	)
	@Test
	public void testCreateClusterSchedulerEngine4() {
		SchedulerEngine schedulerEngine =
			ClusterSchedulerEngine.createClusterSchedulerEngine(
				_mockSchedulerEngine);

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
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, not existed group

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = _getMemoryClusteredJobs(_NOT_EXISTED_GROUP_NAME);

		Assert.assertTrue(schedulerResponses.isEmpty());

		_clusterSchedulerEngine.delete(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = _getMemoryClusteredJobs(_NOT_EXISTED_GROUP_NAME);

		Assert.assertTrue(schedulerResponses.isEmpty());
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
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testGetSchedulerJobs() throws SchedulerException {
		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(4, 2);

		_clusterSchedulerEngine.start();

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(6, schedulerResponses.size());
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testGetSetBeanIdentifier() {
		String beanIdentifier = "BeanIdentifier";

		_clusterSchedulerEngine.setBeanIdentifier(beanIdentifier);

		Assert.assertEquals(
			beanIdentifier, _clusterSchedulerEngine.getBeanIdentifier());
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testMasterToSlave() throws SchedulerException {

		// Test 1, with log disabled

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(4, 2);

		_clusterSchedulerEngine.start();

		Assert.assertTrue(ClusterMasterExecutorUtil.isMaster());

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(4, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterSchedulerEngine.class.getName(), Level.OFF);

		_mockClusterMasterExecutor.reset(false, 4, 2);

		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener =
				_mockClusterMasterExecutor.
					getClusterMasterTokenTransitionListener();

		clusterMasterTokenTransitionListener.masterTokenReleased();

		List<LogRecord> logRecords = captureHandler.getLogRecords();

		Assert.assertTrue(logRecords.isEmpty());
		Assert.assertFalse(ClusterMasterExecutorUtil.isMaster());

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(4, _memoryClusteredJobs.size());

		// Test 2, with log enabled

		_mockClusterMasterExecutor.reset(true, 0, 0);

		_mockSchedulerEngine.resetJobs(4, 2);

		_clusterSchedulerEngine.start();

		_memoryClusteredJobs.clear();

		Assert.assertTrue(ClusterMasterExecutorUtil.isMaster());

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
		Assert.assertFalse(ClusterMasterExecutorUtil.isMaster());

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(4, _memoryClusteredJobs.size());
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

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		_assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.pause(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.PAUSED);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 3, PERSISTED job by jobName and groupName

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, PERSISTED jobs by groupName

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.pause(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.PAUSED);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
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

		SchedulerResponse schedulerResponse = _getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		schedulerResponse = _getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.PAUSED);

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

		schedulerResponse = _getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = _getMemoryClusteredJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		_clusterSchedulerEngine.pause(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = _getMemoryClusteredJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.PAUSED);
		}

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.resume(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = _getMemoryClusteredJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 3, not existed job

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));
		Assert.assertNull(
			_getMemoryClusteredJob(_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME));

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));
		Assert.assertNull(
			_getMemoryClusteredJob(_TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME));
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, not existed group

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = _getMemoryClusteredJobs(_NOT_EXISTED_GROUP_NAME);

		Assert.assertTrue(schedulerResponses.isEmpty());

		_clusterSchedulerEngine.pause(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_NOT_EXISTED_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = _getMemoryClusteredJobs(_NOT_EXISTED_GROUP_NAME);

		Assert.assertTrue(schedulerResponses.isEmpty());
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

		Trigger trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_PREFIX + "new",
			_MEMORY_CLUSTER_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL);

		_clusterSchedulerEngine.schedule(
			trigger, StringPool.BLANK, StringPool.BLANK, new Message(),
			StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(2, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, PERSISTED job

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.PERSISTED);

		Assert.assertEquals(1, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_PREFIX + "new",
			_PERSISTENT_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL);

		_clusterSchedulerEngine.schedule(
			trigger, StringPool.BLANK, StringPool.BLANK, new Message(),
			StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.PERSISTED);

		Assert.assertEquals(2, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
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

		Trigger trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_PREFIX + "new",
			_MEMORY_CLUSTER_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL);

		_clusterSchedulerEngine.schedule(
			trigger, StringPool.BLANK, StringPool.BLANK, new Message(),
			StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(2, _memoryClusteredJobs.size());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@NewEnv(type = NewEnv.Type.NONE)
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

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testSlaveToMaster() throws SchedulerException {

		// Test 1, with log disabled

		_mockClusterMasterExecutor.reset(false, 4, 0);

		_mockSchedulerEngine.resetJobs(0, 0);

		_clusterSchedulerEngine.start();

		Assert.assertFalse(ClusterMasterExecutorUtil.isMaster());

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertEquals(4, _memoryClusteredJobs.size());

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterSchedulerEngine.class.getName(), Level.OFF);

		_mockClusterMasterExecutor.reset(true, 0, 0);

		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener =
				_mockClusterMasterExecutor.
					getClusterMasterTokenTransitionListener();

		clusterMasterTokenTransitionListener.masterTokenAcquired();

		List<LogRecord> logRecords = captureHandler.getLogRecords();

		Assert.assertTrue(logRecords.isEmpty());
		Assert.assertTrue(ClusterMasterExecutorUtil.isMaster());

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(4, schedulerResponses.size());

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED);

		_assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		// Test 2, with log enabled

		_mockClusterMasterExecutor.reset(false, 4, 0);

		_mockSchedulerEngine.resetJobs(0, 0);

		_clusterSchedulerEngine.start();

		Assert.assertFalse(ClusterMasterExecutorUtil.isMaster());

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
		Assert.assertTrue(ClusterMasterExecutorUtil.isMaster());

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertEquals(4, schedulerResponses.size());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
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

		_assertSuppressErrorValue(schedulerResponse, null);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		_assertSuppressErrorValue(schedulerResponse, Boolean.TRUE);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, PERSISTED job by jobName and groupName

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertSuppressErrorValue(schedulerResponse, null);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertSuppressErrorValue(schedulerResponse, Boolean.TRUE);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
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

		SchedulerResponse schedulerResponse = _getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertSuppressErrorValue(schedulerResponse, null);

		_clusterSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		schedulerResponse = _getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertSuppressErrorValue(schedulerResponse, null);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));
	}

	@AdviseWith(adviceClasses = {ClusterableContextThreadLocalAdvice.class})
	@Test
	public void testThreadLocal() throws SchedulerException {

		// Test 1, PERSISTED when portal is starting

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED when portal is starting

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		_clusterSchedulerEngine.start();

		// Test 3, PERSISTED when portal is started

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, MEMORY_CLUSTERED when portal is started

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 5, PERSISTED when plugin is starting

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(true);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 6, MEMORY_CLUSTERED when plugin is starting

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(true);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 7, PERSISTED when plugin is destroying

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(true);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 8, MEMORY_CLUSTERED when plugin is destroying

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(true);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 9, PERSISTED when cluster invoke is enabled

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 10, PERSISTED when cluster invoke is disabled

		ClusterInvokeThreadLocal.setEnabled(false);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.PERSISTED);

		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 11, MEMORY_CLUSTERED when cluster invoke is enabled

		ClusterInvokeThreadLocal.setEnabled(true);
		PluginContextLifecycleThreadLocal.setInitializing(false);
		PluginContextLifecycleThreadLocal.setDestroying(false);

		_clusterSchedulerEngine.setClusterableThreadLocal(
			StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 12, MEMORY_CLUSTERED when cluster invoke is disabled

		ClusterInvokeThreadLocal.setEnabled(false);
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

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		_assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			if (curSchedulerResponse.getJobName().equals(_TEST_JOB_NAME_0)) {
				_assertTriggerState(
					curSchedulerResponse, TriggerState.UNSCHEDULED);
			}
			else {
				_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
			}
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.unschedule(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.UNSCHEDULED);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 3, PERSISTED job by jobName and groupName

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertFalse(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 4, PERSISTED jobs by groupName

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			if (curSchedulerResponse.getJobName().equals(_TEST_JOB_NAME_0)) {
				_assertTriggerState(
					curSchedulerResponse, TriggerState.UNSCHEDULED);
			}
			else {
				_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
			}
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		_clusterSchedulerEngine.unschedule(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_PERSISTENT_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.UNSCHEDULED);
		}

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
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

		SchedulerResponse schedulerResponse = _getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_clusterSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));
		Assert.assertNull(
			_getMemoryClusteredJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME));
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, MEMORY_CLUSTERED jobs by groupName

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());

		schedulerResponses = _getMemoryClusteredJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		_clusterSchedulerEngine.unschedule(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

		Assert.assertTrue(schedulerResponses.isEmpty());
		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
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

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		Trigger trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL * 2);

		_clusterSchedulerEngine.update(trigger, StorageType.MEMORY_CLUSTERED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
			StorageType.MEMORY_CLUSTERED);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL * 2);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
		Assert.assertTrue(
			_clusterInvokeAcceptor.accept(
				ClusterableContextThreadLocalAdvice.getAndClearThreadLocals()));

		// Test 2, PERSISTED job by jobName and groupName

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());

		trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			null, null, _DEFAULT_INTERVAL * 2);

		_clusterSchedulerEngine.update(trigger, StorageType.PERSISTED);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTENT_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL * 2);

		Assert.assertTrue(_memoryClusteredJobs.isEmpty());
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

		SchedulerResponse schedulerResponse = _getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL);

		Trigger trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL * 2);

		_clusterSchedulerEngine.update(trigger, StorageType.MEMORY_CLUSTERED);

		Assert.assertNull(
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME,
				StorageType.MEMORY_CLUSTERED));

		schedulerResponse = _getMemoryClusteredJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL * 2);

		// Test 2, with not existed group name

		trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_0, _NOT_EXISTED_GROUP_NAME, null,
			null, _DEFAULT_INTERVAL * 2);

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

		trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_PREFIX, _NOT_EXISTED_GROUP_NAME,
			null, null, _DEFAULT_INTERVAL * 2);

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
	public final AspectJNewEnvMethodRule aspectJNewEnvMethodRule =
		new AspectJNewEnvMethodRule();

	@Aspect
	public static class ClusterableContextThreadLocalAdvice {

		public static Map<String, Serializable> getAndClearThreadLocals() {
			Map<String, Serializable> threadLocal =
				new HashMap<String, Serializable>(_threadLocals);

			_threadLocals.clear();

			return threadLocal;
		}

		@Around(
			"execution(void com.liferay.portal.cluster." +
				"ClusterableContextThreadLocal.putThreadLocalContext(" +
					"java.lang.String, java.io.Serializable)) && " +
						"args(key, value)")
		public void loadIndexesFromCluster(String key, Serializable value) {
			_threadLocals.put(key, value);
		}

		private static Map<String, Serializable> _threadLocals =
			new HashMap<String, Serializable>();

	}

	@Aspect
	public static class DisableClusterLinkEnabledAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object clusterLinkEnabled(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class DisableSchedulerEnabledAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.SCHEDULER_ENABLED)")
		public Object schedulerEnabled(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class EnableClusterLinkEnabledAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object clusterLinkEnabled(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	@Aspect
	public static class EnableSchedulerEnabledAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.SCHEDULER_ENABLED)")
		public Object schedulerEnabled(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	private void _assertSuppressErrorValue(
		SchedulerResponse schedulerResponse, Object expectedValue) {

		Message message = schedulerResponse.getMessage();

		Assert.assertEquals(expectedValue, message.get(_SUPPRESS_ERROR));
	}

	private void _assertTriggerContent(
		SchedulerResponse schedulerResponse, long expectedInterval) {

		Trigger trigger = schedulerResponse.getTrigger();

		Assert.assertEquals(expectedInterval, trigger.getTriggerContent());
	}

	private void _assertTriggerState(
		SchedulerResponse schedulerResponse,
		TriggerState expectedTriggerState) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		Assert.assertEquals(expectedTriggerState, jobState.getTriggerState());
	}

	private SchedulerResponse _getMemoryClusteredJob(
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

	private List<SchedulerResponse> _getMemoryClusteredJobs(String groupName) {
		List<SchedulerResponse> schedulerResponses =
			new ArrayList<SchedulerResponse>();

		for (ObjectValuePair<SchedulerResponse, TriggerState> objectValuePair :
				_memoryClusteredJobs.values()) {

			SchedulerResponse schedulerResponse = objectValuePair.getKey();

			if (groupName.equals(schedulerResponse.getGroupName())) {
				schedulerResponses.add(
					_getMemoryClusteredJob(
						schedulerResponse.getJobName(), groupName));
			}
		}

		return schedulerResponses;
	}

	private static final long _DEFAULT_INTERVAL = 20000;

	private static final String _MEMORY_CLUSTER_TEST_GROUP_NAME =
		"memory.cluster.test.group";

	private static final String _NOT_EXISTED_GROUP_NAME =
		"not.existed.test.group";

	private static final String _PERSISTENT_TEST_GROUP_NAME =
		"persistent.test.group";

	private static final String _SUPPRESS_ERROR = "suppressError";

	private static final String _TEST_JOB_NAME_0 = "test.job.0";

	private static final String _TEST_JOB_NAME_PREFIX = "test.job.";

	private static MethodKey _getScheduledJobsMethodKey = new MethodKey(
		SchedulerEngineHelperUtil.class, "getScheduledJobs", StorageType.class);

	private ClusterInvokeAcceptor _clusterInvokeAcceptor;
	private ClusterSchedulerEngine _clusterSchedulerEngine;
	private Map<String, ObjectValuePair<SchedulerResponse, TriggerState>>
		_memoryClusteredJobs;
	private MockClusterMasterExecutor _mockClusterMasterExecutor;
	private MockSchedulerEngine _mockSchedulerEngine;

	private static class MockClusterMasterExecutor
		implements ClusterMasterExecutor {

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
				new DefaultNoticeableFuture<T>();

			defaultNoticeableFuture.set(result);

			return defaultNoticeableFuture;
		}

		public ClusterMasterTokenTransitionListener
			getClusterMasterTokenTransitionListener() {

			return _clusterMasterTokenTransitionListener;
		}

		@Override
		public void initialize() {
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
		public void registerClusterMasterTokenTransitionListener(
			ClusterMasterTokenTransitionListener
				clusterMasterTokenAcquisitionListener) {

			_clusterMasterTokenTransitionListener =
				clusterMasterTokenAcquisitionListener;
		}

		public void reset(
			boolean master, int memoryClusterJobs, int persistentJobs) {

			_master = master;

			_mockSchedulerEngine.resetJobs(memoryClusterJobs, persistentJobs);
		}

		public void setException(boolean exception) {
			_exception = exception;
		}

		@Override
		public void unregisterClusterMasterTokenTransitionListener(
			ClusterMasterTokenTransitionListener
				clusterMasterTokenAcquisitionListener) {

			if (_clusterMasterTokenTransitionListener ==
					clusterMasterTokenAcquisitionListener) {

				_clusterMasterTokenTransitionListener = null;
			}
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
			return new ArrayList<SchedulerResponse>(_defaultJobs.values());
		}

		@Override
		public List<SchedulerResponse> getScheduledJobs(
			StorageType storageType) {

			List<SchedulerResponse> schedulerResponses =
				new ArrayList<SchedulerResponse>();

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

			List<SchedulerResponse> schedulerResponses =
				new ArrayList<SchedulerResponse>();

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
				trigger = TriggerFactoryUtil.buildTrigger(
					TriggerType.SIMPLE, jobName, groupName, null, null,
					_DEFAULT_INTERVAL);
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
			new HashMap<String, SchedulerResponse>();

	}

}