/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.cluster.AddressImpl;
import com.liferay.portal.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.cluster.ClusterableContextThreadLocal;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.ClusterResponseCallback;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.impl.LockImpl;
import com.liferay.portal.service.LockLocalService;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.impl.LockLocalServiceImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class ClusterSchedulerEngineTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		Field field = ReflectionUtil.getDeclaredField(
			LockLocalServiceUtil.class, "_service");

		LockLocalService lockLocalService = new MockLockLocalService();

		field.set(null, lockLocalService);

		field = ClusterableContextThreadLocal.class.getDeclaredField(
			"_contextThreadLocal");

		field.setAccessible(true);

		_threadLocalContext =
			(ThreadLocal<HashMap<String, Serializable>>)field.get(null);

		Method method = ClusterSchedulerEngine.class.getDeclaredMethod(
			"delete", String.class);

		Clusterable clusterable = method.getAnnotation(Clusterable.class);

		Class<? extends ClusterInvokeAcceptor> clusterInvokeAcceptorClass =
			clusterable.acceptor();

		Constructor<? extends ClusterInvokeAcceptor> constructor =
			clusterInvokeAcceptorClass.getDeclaredConstructor();

		constructor.setAccessible(true);

		_clusterInvokeAcceptor = constructor.newInstance();
	}

	@After
	public void tearDown() throws Exception {
		if (_clusterSchedulerEngine != null) {
			_clusterSchedulerEngine.shutdown();
		}

		ClusterExecutorUtil.destroy();
	}

	@Test
	public void testDelete1() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 4, 2);

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(6, schedulerResponses.size());

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(5, schedulerResponses.size());

		_clusterSchedulerEngine.delete(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(2, schedulerResponses.size());

		try {
			_clusterSchedulerEngine.delete(
				_INVALID_JOB_NAME, _INVALID_MEMORY_CLUSTER_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}

		try {
			_clusterSchedulerEngine.delete(_INVALID_MEMORY_CLUSTER_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}
	}

	@Test
	public void testDelete2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 4, 2);

		Map<String, SchedulerResponse> schedulerResponses =
			_getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertEquals(4, schedulerResponses.size());

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertEquals(3, schedulerResponses.size());

		_clusterSchedulerEngine.delete(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertTrue(schedulerResponses.isEmpty());
	}

	@Test
	public void testGetSchedulerJobs1() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 2, 2);

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		Assert.assertNotNull(schedulerResponse);

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME);

		Assert.assertEquals(2, schedulerResponses.size());

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(4, schedulerResponses.size());

		try {
			_clusterSchedulerEngine.getScheduledJob(
				_INVALID_JOB_NAME, _INVALID_PERSISTENT_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}

		try {
			_clusterSchedulerEngine.getScheduledJobs(
				_INVALID_PERSISTENT_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}

		_clusterSchedulerEngine.delete(_MEMORY_CLUSTER_TEST_GROUP_NAME);
		_clusterSchedulerEngine.delete(_PERSISTENT_TEST_GROUP_NAME);

		try {
			_clusterSchedulerEngine.getScheduledJobs();

			Assert.fail();
		}
		catch (SchedulerException se) {
		}
	}

	@Test
	public void testGetSchedulerJobs2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 2, 2);

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		Assert.assertNotNull(schedulerResponse);

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME);

		Assert.assertEquals(2, schedulerResponses.size());

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(4, schedulerResponses.size());
	}

	@Test
	public void testMasterToSlave1() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 2, 2);

		Assert.assertTrue(_isMaster(_clusterSchedulerEngine));

		Map<String, SchedulerResponse> schedulerResponseMap =
			_getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertTrue(schedulerResponseMap.isEmpty());

		String newMaster = _clusterSchedulerEngine.getSerializedString(
			MockClusterExecutor._anotherAddress);

		MockLockLocalService.setLock(newMaster);

		Assert.assertFalse(_isMaster(_clusterSchedulerEngine));

		_clusterSchedulerEngine.getScheduledJobs();

		schedulerResponseMap = _getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertEquals(2, schedulerResponseMap.size());
	}

	@Test
	public void testMasterToSlave2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 2, 2);

		Assert.assertTrue(_isMaster(_clusterSchedulerEngine));

		Map<String, SchedulerResponse> schedulerResponseMap =
			_getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertTrue(schedulerResponseMap.isEmpty());

		String newMaster = _clusterSchedulerEngine.getSerializedString(
			MockClusterExecutor._anotherAddress);

		MockLockLocalService.setLock(newMaster);

		Assert.assertFalse(_isMaster(_clusterSchedulerEngine));

		MockClusterExecutor.fireClusterEventListener();

		schedulerResponseMap = _getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertEquals(2, schedulerResponseMap.size());
	}

	@Test
	public void testPauseAndResume1() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 2, 2);

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		_clusterSchedulerEngine.resume(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		_clusterSchedulerEngine.pause(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.PAUSED);
		}

		_clusterSchedulerEngine.resume(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		try {
			_clusterSchedulerEngine.pause(
				_INVALID_JOB_NAME, _INVALID_PERSISTENT_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}

		try {
			_clusterSchedulerEngine.resume(
				_INVALID_JOB_NAME, _INVALID_PERSISTENT_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}

		try {
			_clusterSchedulerEngine.pause(_INVALID_PERSISTENT_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}

		try {
			_clusterSchedulerEngine.resume(_INVALID_PERSISTENT_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}
	}

	@Test
	public void testPauseAndResume2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 2, 2);

		SchedulerResponse schedulerResponse = _getMemoryClusteredJob(
			_clusterSchedulerEngine, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponse = _getMemoryClusteredJob(
			_clusterSchedulerEngine, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		_clusterSchedulerEngine.resume(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponse = _getMemoryClusteredJob(
			_clusterSchedulerEngine, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		List<SchedulerResponse> schedulerResponses = _getMemoryClusteredJobs(
			_clusterSchedulerEngine, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		_clusterSchedulerEngine.pause(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _getMemoryClusteredJobs(
			_clusterSchedulerEngine, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.PAUSED);
		}

		_clusterSchedulerEngine.resume(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _getMemoryClusteredJobs(
			_clusterSchedulerEngine, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}
	}

	@Test
	public void testSchedule1() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 1, 0);

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(1, schedulerResponses.size());

		Trigger trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_PREFIX + "new",
			_MEMORY_CLUSTER_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL);

		_clusterSchedulerEngine.schedule(
			trigger, StringPool.BLANK, StringPool.BLANK, new Message());

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(2, schedulerResponses.size());

		trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _INVALID_JOB_NAME, _PERSISTENT_TEST_GROUP_NAME,
			null, null, _DEFAULT_INTERVAL);

		try {
			_clusterSchedulerEngine.schedule(
				trigger, StringPool.BLANK, StringPool.BLANK, new Message());

			Assert.fail();
		}
		catch (SchedulerException se) {
		}
	}

	@Test
	public void testSchedule2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 1, 0);

		Map<String, SchedulerResponse> schedulerResponses =
			_getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertEquals(1, schedulerResponses.size());

		Trigger trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_PREFIX + "new",
			_MEMORY_CLUSTER_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL);

		_clusterSchedulerEngine.schedule(
			trigger, StringPool.BLANK, StringPool.BLANK, new Message());

		schedulerResponses = _getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertEquals(2, schedulerResponses.size());
	}

	@Test
	public void testSetClusterableThreadLocal1() throws Exception {

		// Persisted storage type

		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 4, 2);

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_PREFIX + CharPool.NUMBER_0,
			_PERSISTENT_TEST_GROUP_NAME);

		Map<String, Serializable> context = _collectThreadLocalContext();

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(_clusterInvokeAcceptor.accept(context));

		ClusterInvokeThreadLocal.setEnabled(true);

		// Plugin is initializing

		PluginContextLifecycleThreadLocal.setInitializing(true);

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_PREFIX + CharPool.NUMBER_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		PluginContextLifecycleThreadLocal.setInitializing(false);

		context = _collectThreadLocalContext();

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(_clusterInvokeAcceptor.accept(context));

		ClusterInvokeThreadLocal.setEnabled(true);

		// Plugin is destroying

		PluginContextLifecycleThreadLocal.setDestroying(true);

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_PREFIX + CharPool.NUMBER_1,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		PluginContextLifecycleThreadLocal.setDestroying(false);

		context = _collectThreadLocalContext();

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(_clusterInvokeAcceptor.accept(context));

		ClusterInvokeThreadLocal.setEnabled(true);

		// Portal is not ready

		_clusterSchedulerEngine.shutdown();

		_clusterSchedulerEngine.delete(
			_TEST_JOB_NAME_PREFIX + CharPool.NUMBER_2,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		context = _collectThreadLocalContext();

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertFalse(_clusterInvokeAcceptor.accept(context));

		ClusterInvokeThreadLocal.setEnabled(true);
	}

	@Test
	public void testSetClusterableThreadLocal2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 2, 2);

		_clusterSchedulerEngine.delete(_PERSISTENT_TEST_GROUP_NAME);

		Map<String, Serializable> context = _collectThreadLocalContext();

		Assert.assertTrue(_clusterInvokeAcceptor.accept(context));

		_clusterSchedulerEngine.delete(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		context = _collectThreadLocalContext();

		ClusterInvokeThreadLocal.setEnabled(false);

		Assert.assertTrue(_clusterInvokeAcceptor.accept(context));

		ClusterInvokeThreadLocal.setEnabled(true);
	}

	@Test
	public void testSlaveToMaster() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 2, 0);

		Assert.assertFalse(_isMaster(_clusterSchedulerEngine));

		Map<String, SchedulerResponse> schedulerResponseMap =
			_getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertEquals(2, schedulerResponseMap.size());

		String newMaster = _clusterSchedulerEngine.getSerializedString(
			ClusterExecutorUtil.getLocalClusterNodeAddress());

		MockLockLocalService.setLock(newMaster);

		Assert.assertTrue(_isMaster(_clusterSchedulerEngine));

		_clusterSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_clusterSchedulerEngine.getScheduledJobs();

		schedulerResponseMap = _getMemoryClusteredJobs(_clusterSchedulerEngine);

		Assert.assertTrue(schedulerResponseMap.isEmpty());
	}

	@Test
	public void testSuppressError1() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 1, 0);

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertSuppressErrorValue(schedulerResponse, null);

		_clusterSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertSuppressErrorValue(schedulerResponse, Boolean.TRUE);

		try {
			_clusterSchedulerEngine.suppressError(
				_INVALID_JOB_NAME, _MEMORY_CLUSTER_TEST_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}
	}

	@Test
	public void testSuppressError2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 1, 0);

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertSuppressErrorValue(schedulerResponse, null);

		_clusterSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertSuppressErrorValue(schedulerResponse, null);
	}

	@Test
	public void testUnschedule1() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 2, 0);

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_clusterSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);

		List<SchedulerResponse> schedulerResponses =
			_clusterSchedulerEngine.getScheduledJobs(
				_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			String jobName = curSchedulerResponse.getJobName();

			if (jobName.equals(_TEST_JOB_NAME_0)) {
				_assertTriggerState(
					curSchedulerResponse, TriggerState.UNSCHEDULED);
			}
			else {
				_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
			}
		}

		_clusterSchedulerEngine.unschedule(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _clusterSchedulerEngine.getScheduledJobs(
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.UNSCHEDULED);
		}

		try {
			_clusterSchedulerEngine.unschedule(
				_INVALID_JOB_NAME, _INVALID_MEMORY_CLUSTER_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}

		try {
			_clusterSchedulerEngine.unschedule(
				_INVALID_MEMORY_CLUSTER_GROUP_NAME);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}
	}

	@Test
	public void testUnschedule2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 4, 0);

		SchedulerResponse schedulerResponse = _getMemoryClusteredJob(
			_clusterSchedulerEngine, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_clusterSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponse = _getMemoryClusteredJob(
			_clusterSchedulerEngine, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		Assert.assertNull(schedulerResponse);

		List<SchedulerResponse> schedulerResponses = _getMemoryClusteredJobs(
			_clusterSchedulerEngine, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.NORMAL);
		}

		_clusterSchedulerEngine.unschedule(_MEMORY_CLUSTER_TEST_GROUP_NAME);

		schedulerResponses = _getMemoryClusteredJobs(
			_clusterSchedulerEngine, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		for (SchedulerResponse curSchedulerResponse : schedulerResponses) {
			_assertTriggerState(curSchedulerResponse, TriggerState.UNSCHEDULED);
		}
	}

	@Test
	public void testUpdate1() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(true, 2, 0);

		SchedulerResponse schedulerResponse =
			_clusterSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL);

		Trigger trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL * 2);

		_clusterSchedulerEngine.update(trigger);

		schedulerResponse = _clusterSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL * 2);

		trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _INVALID_JOB_NAME, _PERSISTENT_TEST_GROUP_NAME,
			null, null, _DEFAULT_INTERVAL);

		try {
			_clusterSchedulerEngine.update(trigger);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}
	}

	@Test
	public void testUpdate2() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 2, 0);

		SchedulerResponse schedulerResponse = _getMemoryClusteredJob(
			_clusterSchedulerEngine, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL);

		Trigger trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME, null, null, _DEFAULT_INTERVAL * 2);

		_clusterSchedulerEngine.update(trigger);

		schedulerResponse = _getMemoryClusteredJob(
			_clusterSchedulerEngine, _TEST_JOB_NAME_0,
			_MEMORY_CLUSTER_TEST_GROUP_NAME);

		_assertTriggerContent(schedulerResponse, _DEFAULT_INTERVAL * 2);

		trigger = TriggerFactoryUtil.buildTrigger(
			TriggerType.SIMPLE, _TEST_JOB_NAME_0,
			_INVALID_MEMORY_CLUSTER_GROUP_NAME, null, null, _DEFAULT_INTERVAL);

		try {
			_clusterSchedulerEngine.update(trigger);

			Assert.fail();
		}
		catch (SchedulerException se) {
		}
	}

	@Test
	public void testUpdateMemorySchedulerClusterMaster() throws Exception {
		_clusterSchedulerEngine = _getClusterSchedulerEngine(false, 2, 0);

		Assert.assertFalse(_isMaster(_clusterSchedulerEngine));

		MockClusterExecutor.removeClusterNode(
			MockClusterExecutor._anotherAddress);

		_clusterSchedulerEngine.updateMemorySchedulerClusterMaster();

		Assert.assertTrue(_isMaster(_clusterSchedulerEngine));
	}

	private void _assertSuppressErrorValue(
		SchedulerResponse schedulerResponse, Object expectedValue) {

		Message message = schedulerResponse.getMessage();

		Object object = message.get(_SUPPRESS_ERROR);

		Assert.assertEquals(expectedValue, object);
	}

	private void _assertTriggerContent(
		SchedulerResponse schedulerResponse, long expectedInterval) {

		Trigger trigger = schedulerResponse.getTrigger();

		long interval = (Long)trigger.getTriggerContent();

		Assert.assertEquals(expectedInterval, interval);
	}

	private void _assertTriggerState(
		SchedulerResponse schedulerResponse,
		TriggerState expectedTriggerState) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		Assert.assertEquals(expectedTriggerState, triggerState);
	}

	private Map<String, Serializable> _collectThreadLocalContext()
		throws Exception {

		Map<String, Serializable> context = _threadLocalContext.get();

		_threadLocalContext.remove();

		return context;
	}

	private ClusterSchedulerEngine _getClusterSchedulerEngine(
			boolean master, int memoryClusterJobs, int persistentJobs)
		throws Exception {

		MockSchedulerEngine mockSchedulerEngine = new MockSchedulerEngine(
			memoryClusterJobs, persistentJobs);

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(
			true, mockSchedulerEngine);

		ClusterExecutorUtil clusterExecutorUtil = new ClusterExecutorUtil();

		clusterExecutorUtil.setClusterExecutor(mockClusterExecutor);

		ClusterSchedulerEngine clusterSchedulerEngine =
			new ClusterSchedulerEngine(mockSchedulerEngine);

		Address masterAddress = null;

		if (master) {
			masterAddress = ClusterExecutorUtil.getLocalClusterNodeAddress();
		}
		else {
			masterAddress = MockClusterExecutor._anotherAddress;
		}

		MockLockLocalService.setLock(
			clusterSchedulerEngine.getSerializedString(masterAddress));

		SchedulerEngineHelperImpl schedulerEngineHelperImpl =
			new SchedulerEngineHelperImpl();

		schedulerEngineHelperImpl.setSchedulerEngine(clusterSchedulerEngine);

		SchedulerEngineHelperUtil schedulerEngineHelperUtil =
			new SchedulerEngineHelperUtil();

		schedulerEngineHelperUtil.setSchedulerEngineHelper(
			schedulerEngineHelperImpl);

		clusterSchedulerEngine.initialize();

		clusterSchedulerEngine.start();

		return clusterSchedulerEngine;
	}

	private String _getFullName(String jobName, String groupName) {
		return groupName.concat(StringPool.PERIOD).concat(jobName);
	}

	private SchedulerResponse _getMemoryClusteredJob(
			ClusterSchedulerEngine clusterSchedulerEngine, String jobName,
			String groupName)
		throws Exception {

		Map<String, SchedulerResponse> allJobs = _getMemoryClusteredJobs(
			clusterSchedulerEngine);

		String key = _getFullName(jobName, groupName);

		return allJobs.get(key);
	}

	private Map<String, SchedulerResponse> _getMemoryClusteredJobs(
			ClusterSchedulerEngine clusterSchedulerEngine)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			ClusterSchedulerEngine.class, "_memoryClusteredJobs");

		Map<String, ObjectValuePair<SchedulerResponse, TriggerState>>
			memoryJobs =
				(Map<String, ObjectValuePair<SchedulerResponse, TriggerState>>)
					field.get(clusterSchedulerEngine);

		if (memoryJobs.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, SchedulerResponse> schedulerResponses =
			new HashMap<String, SchedulerResponse>(memoryJobs.size());

		for (String key : memoryJobs.keySet()) {
			ObjectValuePair<SchedulerResponse, TriggerState> value =
				memoryJobs.get(key);

			SchedulerResponse schedulerResponse = value.getKey();

			Message message = schedulerResponse.getMessage();

			TriggerState triggerState = value.getValue();

			message.put(SchedulerEngine.JOB_STATE, new JobState(triggerState));

			schedulerResponses.put(key, schedulerResponse);
		}

		return schedulerResponses;
	}

	private List<SchedulerResponse> _getMemoryClusteredJobs(
			ClusterSchedulerEngine clusterSchedulerEngine, String groupName)
		throws Exception {

		List<SchedulerResponse> schedulerResponses =
			new ArrayList<SchedulerResponse>();

		Map<String, SchedulerResponse> allJobs = _getMemoryClusteredJobs(
			clusterSchedulerEngine);

		for (String key : allJobs.keySet()) {
			if (key.contains(groupName)) {
				schedulerResponses.add(allJobs.get(key));
			}
		}

		return schedulerResponses;
	}

	private boolean _isMaster(ClusterSchedulerEngine clusterSchedulerEngine)
		throws Exception {

		Field localClusterNodeAddressField = ReflectionUtil.getDeclaredField(
			ClusterSchedulerEngine.class, "_localClusterNodeAddress");

		String localClusterNodeAddress =
			(String)localClusterNodeAddressField.get(clusterSchedulerEngine);

		Lock lock = MockLockLocalService.getLock();

		return localClusterNodeAddress.equals(lock.getOwner());
	}

	private static final long _DEFAULT_INTERVAL = 20000;

	private static final String _INVALID_JOB_NAME = "wrong.job.name";

	private static final String _INVALID_MEMORY_CLUSTER_GROUP_NAME =
		"MEMORY_CLUSTERED#invalid.group";

	private static final String _INVALID_PERSISTENT_GROUP_NAME =
		"PERSISTED#invalid.group";

	private static final String _MEMORY_CLUSTER_TEST_GROUP_NAME =
		"MEMORY_CLUSTERED#memory.cluster.test.group";

	private static final String _PERSISTENT_TEST_GROUP_NAME =
		"PERSISTED#persistent.test.group";

	private static final String _SUPPRESS_ERROR = "suppressError";

	private static final String _TEST_JOB_NAME_0 = "test.job.0";

	private static final String _TEST_JOB_NAME_PREFIX = "test.job.";

	private static ClusterInvokeAcceptor _clusterInvokeAcceptor;
	private static MethodKey _getScheduledJobMethodKey = new MethodKey(
		SchedulerEngineHelperUtil.class, "getScheduledJob", String.class,
		String.class, StorageType.class);
	private static MethodKey _getScheduledJobsMethodKey1 = new MethodKey(
		SchedulerEngineHelperUtil.class, "getScheduledJobs");
	private static MethodKey _getScheduledJobsMethodKey2 = new MethodKey(
		SchedulerEngineHelperUtil.class, "getScheduledJobs", String.class,
		StorageType.class);
	private static MethodKey _getScheduledJobsMethodKey3 = new MethodKey(
		SchedulerEngineHelperUtil.class, "getScheduledJobs", StorageType.class);
	private static ThreadLocal<HashMap<String, Serializable>>
		_threadLocalContext;

	private ClusterSchedulerEngine _clusterSchedulerEngine;

	private static class MockAddress implements org.jgroups.Address {

		@SuppressWarnings("unused")
		public MockAddress() {
		}

		public MockAddress(long timestamp) {
			_timestamp = timestamp;
		}

		@Override
		public int hashCode() {
			return 11 * (int)_timestamp;
		}

		@Override
		public boolean equals(Object obj) {
			MockAddress mockAddress = (MockAddress)obj;

			if (_timestamp == mockAddress.getTimestamp()) {
				return true;
			}

			return false;
		}

		@Override
		public int compareTo(org.jgroups.Address jGroupsAddress) {
			return 0;
		}

		public long getTimestamp() {
			return _timestamp;
		}

		@Override
		public void readExternal(ObjectInput objectInput) throws IOException {
			_timestamp = objectInput.readLong();
		}

		@Override
		public void readFrom(DataInput dataInput) throws Exception {
			_timestamp = dataInput.readLong();
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public void writeExternal(ObjectOutput objectOutput)
			throws IOException {

			objectOutput.writeLong(_timestamp);
		}

		@Override
		public void writeTo(DataOutput dataOutput) throws Exception {
			dataOutput.writeLong(_timestamp);
		}

		private long _timestamp;

	}

	private static class MockClusterExecutor implements ClusterExecutor {

		public static void fireClusterEventListener() {
			for (ClusterEventListener clusterEventListener :
					_clusterEventListeners) {

				clusterEventListener.processClusterEvent(null);
			}
		}

		public static void removeClusterNode(Address address) {
			_addresses.remove(address);
		}

		public MockClusterExecutor(
			boolean enabled, MockSchedulerEngine mockSchedulerEngine) {

			_enabled = enabled;
			_mockSchedulerEngine = mockSchedulerEngine;

			long timestamp = System.currentTimeMillis();

			_localAddress = new AddressImpl(new MockAddress(timestamp));
			_anotherAddress= new AddressImpl(new MockAddress(timestamp + 1000));

			_addresses.add(_localAddress);
			_addresses.add(_anotherAddress);
		}

		@Override
		public void addClusterEventListener(
			ClusterEventListener clusterEventListener) {

			_clusterEventListeners.add(clusterEventListener);
		}

		@Override
		public void destroy() {
			_addresses.clear();
		}

		@Override
		public FutureClusterResponses execute(ClusterRequest clusterRequest) {
			List<Address> addresses = new ArrayList<Address>();

			Collection<Address> clusterNodeAddresses =
				clusterRequest.getTargetClusterNodeAddresses();

			if (clusterNodeAddresses != null) {
				addresses.addAll(clusterNodeAddresses);
			}

			FutureClusterResponses futureClusterResponses =
				new FutureClusterResponses(addresses);

			for (Address address : addresses) {
				ClusterNodeResponse clusterNodeResponse =
					new ClusterNodeResponse();

				clusterNodeResponse.setAddress(address);
				clusterNodeResponse.setClusterMessageType(
					ClusterMessageType.EXECUTE);
				clusterNodeResponse.setMulticast(clusterRequest.isMulticast());
				clusterNodeResponse.setUuid(clusterRequest.getUuid());

				MockAddress mockAddress = (MockAddress)address.getRealAddress();

				try {
					clusterNodeResponse.setClusterNode(
						new ClusterNode(
							String.valueOf(mockAddress.getTimestamp()),
							InetAddress.getLocalHost()));

					clusterNodeResponse.setResult(
						_invoke(clusterRequest.getMethodHandler()));
				}
				catch (Exception e) {
				}

				futureClusterResponses.addClusterNodeResponse(
					clusterNodeResponse);
			}

			return futureClusterResponses;
		}

		@Override
		public void execute(
			ClusterRequest clusterRequest,
			ClusterResponseCallback clusterResponseCallback) {

			FutureClusterResponses futureClusterResponses = execute(
				clusterRequest);

			try {
				clusterResponseCallback.callback(futureClusterResponses.get());
			}
			catch (InterruptedException ie) {
			}
		}

		@Override
		public void execute(
			ClusterRequest clusterRequest,
			ClusterResponseCallback clusterResponseCallback, long timeout,
			TimeUnit timeUnit) {

			FutureClusterResponses futureClusterResponses = execute(
				clusterRequest);

			try {
				clusterResponseCallback.callback(
					futureClusterResponses.get(timeout, timeUnit));
			}
			catch (Exception e) {
			}
		}

		@Override
		public List<ClusterEventListener> getClusterEventListeners() {
			return Collections.unmodifiableList(_clusterEventListeners);
		}

		@Override
		public List<Address> getClusterNodeAddresses() {
			return Collections.unmodifiableList(_addresses);
		}

		@Override
		public List<ClusterNode> getClusterNodes() {
			return Collections.emptyList();
		}

		@Override
		public ClusterNode getLocalClusterNode() {
			return null;
		}

		@Override
		public Address getLocalClusterNodeAddress() {
			return _localAddress;
		}

		@Override
		public void initialize() {
		}

		@Override
		public boolean isClusterNodeAlive(Address address) {
			return _addresses.contains(address);
		}

		@Override
		public boolean isClusterNodeAlive(String clusterNodeId) {
			return false;
		}

		@Override
		public boolean isEnabled() {
			return _enabled;
		}

		@Override
		public void removeClusterEventListener(
			ClusterEventListener clusterEventListener) {

			_clusterEventListeners.remove(clusterEventListener);
		}

		private Object _invoke(MethodHandler methodHandler)
			throws SchedulerException {

			MethodKey methodKey = methodHandler.getMethodKey();

			if (methodKey.equals(_getScheduledJobMethodKey)) {
				String groupName = (String)methodHandler.getArguments()[1];
				StorageType storageType =
					(StorageType)methodHandler.getArguments()[2];

				return _mockSchedulerEngine.getScheduledJob(
					(String)methodHandler.getArguments()[0],
					_namespaceGroupName(groupName, storageType));
			}
			else if (methodKey.equals(_getScheduledJobsMethodKey1)) {
				return _mockSchedulerEngine.getScheduledJobs();
			}
			else if (methodKey.equals(_getScheduledJobsMethodKey2)) {
				String groupName = (String)methodHandler.getArguments()[0];
				StorageType storageType =
					(StorageType)methodHandler.getArguments()[1];

				return _mockSchedulerEngine.getScheduledJobs(
					_namespaceGroupName(groupName, storageType));
			}
			else if (methodKey.equals(_getScheduledJobsMethodKey3)) {
				StorageType storageType =
					(StorageType)methodHandler.getArguments()[0];

				return _mockSchedulerEngine.getScheduledJobs(storageType);
			}

			return null;
		}

		private String _namespaceGroupName(
			String groupName, StorageType storageType) {

			String namespaceGroupName = storageType.toString();

			namespaceGroupName = namespaceGroupName.concat(
				StringPool.POUND).concat(groupName);

			return namespaceGroupName;
		}

		private static List<Address> _addresses = new ArrayList<Address>();
		private static Address _anotherAddress;
		private static List<ClusterEventListener> _clusterEventListeners =
			new ArrayList<ClusterEventListener>();

		private boolean _enabled;
		private Address _localAddress;
		private MockSchedulerEngine _mockSchedulerEngine;

	}

	private static class MockLockLocalService extends LockLocalServiceImpl {

		public static void setLock(String owner) {
			Lock lock = new LockImpl();

			lock.setOwner(owner);

			_lock = lock;
		}

		public static Lock getLock() {
			return _lock;
		}

		@Override
		public Lock lock(
			String className, String key, String owner,
			boolean retrieveFromCache) {

			if (_lock == null) {
				Lock lock = new LockImpl();

				lock.setKey(key);
				lock.setOwner(owner);

				_lock = lock;
			}

			return _lock;
		}

		@Override
		public Lock lock(
			String className, String key, String expectedOwner,
			String updatedOwner, boolean retrieveFromCache) {

			Lock lock = new LockImpl();

			lock.setKey(key);
			lock.setOwner(updatedOwner);

			_lock = lock;

			return lock;
		}

		@Override
		public void unlock(
			String className, String key, String owner,
			boolean retrieveFromCache) {

			_lock = null;
		}

		private static Lock _lock;

	}

	private class MockSchedulerEngine implements SchedulerEngine {

		public MockSchedulerEngine(int memoryClusterJobs, int persistentJobs) {
			_defaultJobs = new HashMap<String, SchedulerResponse>();

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
		public void delete(String groupName) throws SchedulerException {
			boolean removed = false;

			Set<Map.Entry<String, SchedulerResponse>> set =
				_defaultJobs.entrySet();

			Iterator<Map.Entry<String, SchedulerResponse>> iterator =
				set.iterator();

			while (iterator.hasNext()) {
				Map.Entry<String, SchedulerResponse> entry = iterator.next();

				String key = entry.getKey();

				if (key.contains(groupName)) {
					iterator.remove();

					removed = true;
				}
			}

			if (removed) {
				return;
			}

			throw new SchedulerException("No jobs in group " + groupName);
		}

		@Override
		public void delete(String jobName, String groupName)
			throws SchedulerException {

			SchedulerResponse schedulerResponse = _defaultJobs.remove(
				_getFullName(jobName, groupName));

			if (schedulerResponse != null) {
				return;
			}

			throw new SchedulerException(
				"No jobs with name " + jobName + "in group " + groupName);
		}

		@Override
		public SchedulerResponse getScheduledJob(
				String jobName, String groupName)
			throws SchedulerException {

			SchedulerResponse schedulerResponse = _defaultJobs.get(
				_getFullName(jobName, groupName));

			if (schedulerResponse != null) {
				return schedulerResponse;
			}

			throw new SchedulerException(
				"No jobs with name " + jobName + "in group " + groupName);
		}

		@Override
		public List<SchedulerResponse> getScheduledJobs()
			throws SchedulerException {

			if (_defaultJobs.isEmpty()) {
				throw new SchedulerException("No jobs found");
			}

			return new ArrayList<SchedulerResponse>(_defaultJobs.values());
		}

		@Override
		public List<SchedulerResponse> getScheduledJobs(String groupName)
			throws SchedulerException {

			List<SchedulerResponse> schedulerResponses =
				new ArrayList<SchedulerResponse>();

			for (String key : _defaultJobs.keySet()) {
				if (key.contains(groupName)) {
					schedulerResponses.add(_defaultJobs.get(key));
				}
			}

			if (schedulerResponses.isEmpty()) {
				throw new SchedulerException("No jobs in group " + groupName);
			}

			return schedulerResponses;
		}

		public List<SchedulerResponse> getScheduledJobs(StorageType storageType)
			throws SchedulerException {

			List<SchedulerResponse> schedulerResponses =
				new ArrayList<SchedulerResponse>();

			for (SchedulerResponse schedulerResponse : _defaultJobs.values()) {
				if (storageType.equals(schedulerResponse.getStorageType())) {
					schedulerResponses.add(schedulerResponse);
				}
			}

			if (schedulerResponses.isEmpty()) {
				throw new SchedulerException(
					"No jobs with type " + storageType);
			}

			return schedulerResponses;
		}

		@Override
		public void pause(String groupName) throws SchedulerException {
			List<SchedulerResponse> schedulerResponses = getScheduledJobs(
				groupName);

			for (SchedulerResponse schedulerResponse : schedulerResponses) {
				Message message = schedulerResponse.getMessage();

				message.put(
					SchedulerEngine.JOB_STATE,
					new JobState(TriggerState.PAUSED));
			}
		}

		@Override
		public void pause(String jobName, String groupName)
			throws SchedulerException {

			SchedulerResponse schedulerResponse = getScheduledJob(
				jobName, groupName);

			Message message = schedulerResponse.getMessage();

			message.put(
				SchedulerEngine.JOB_STATE, new JobState(TriggerState.PAUSED));
		}

		@Override
		public void resume(String groupName) throws SchedulerException {
			List<SchedulerResponse> schedulerResponses = getScheduledJobs(
				groupName);

			for (SchedulerResponse schedulerResponse : schedulerResponses) {
				Message message = schedulerResponse.getMessage();

				message.put(
					SchedulerEngine.JOB_STATE,
					new JobState(TriggerState.NORMAL));
			}
		}

		@Override
		public void resume(String jobName, String groupName)
			throws SchedulerException {

			SchedulerResponse schedulerResponse = getScheduledJob(
				jobName, groupName);

			Message message = schedulerResponse.getMessage();

			message.put(
				SchedulerEngine.JOB_STATE, new JobState(TriggerState.NORMAL));
		}

		@Override
		public void schedule(
				Trigger trigger, String description, String destinationName,
				Message message)
			throws SchedulerException {

			String jobName = trigger.getJobName();

			if (!jobName.startsWith(_TEST_JOB_NAME_PREFIX)) {
				throw new SchedulerException("Invalid job name " + jobName);
			}

			String groupName = trigger.getGroupName();

			StorageType storageType = _getStorageType(groupName);

			_addJobs(
				trigger.getJobName(), groupName, storageType, trigger, message);
		}

		@Override
		public void shutdown() {
			_defaultJobs.clear();
		}

		@Override
		public void start() {
		}

		@Override
		public void suppressError(String jobName, String groupName)
			throws SchedulerException {

			SchedulerResponse schedulerResponse = getScheduledJob(
				jobName, groupName);

			Message message = schedulerResponse.getMessage();

			message.put(_SUPPRESS_ERROR, Boolean.TRUE);
		}

		@Override
		public void unschedule(String groupName) throws SchedulerException {
			List<SchedulerResponse> schedulerResponses = getScheduledJobs(
				groupName);

			for (SchedulerResponse schedulerResponse : schedulerResponses) {
				Message message = schedulerResponse.getMessage();

				message.put(
					SchedulerEngine.JOB_STATE,
					new JobState(TriggerState.UNSCHEDULED));
			}
		}

		@Override
		public void unschedule(String jobName, String groupName)
			throws SchedulerException {

			SchedulerResponse schedulerResponse = getScheduledJob(
				jobName, groupName);

			Message message = schedulerResponse.getMessage();

			message.put(
				SchedulerEngine.JOB_STATE,
				new JobState(TriggerState.UNSCHEDULED));
		}

		@Override
		public void update(Trigger trigger) throws SchedulerException {
			SchedulerResponse schedulerResponse = getScheduledJob(
				trigger.getJobName(), trigger.getGroupName());

			schedulerResponse.setTrigger(trigger);
		}

		private SchedulerResponse _addJobs(
			String jobName, String groupName, StorageType storageType,
			Trigger trigger, Message message) {

			SchedulerResponse schedulerResponse = new SchedulerResponse();

			schedulerResponse.setDestinationName(
				DestinationNames.SCHEDULER_DISPATCH);
			schedulerResponse.setGroupName(_getOriginalGroupName(groupName));
			schedulerResponse.setJobName(jobName);

			if (message == null) {
				message = new Message();
			}

			message.put(
				SchedulerEngine.JOB_STATE, new JobState(TriggerState.NORMAL));

			schedulerResponse.setMessage(message);

			schedulerResponse.setStorageType(storageType);

			if (trigger == null) {
				try {
					trigger = TriggerFactoryUtil.buildTrigger(
						TriggerType.SIMPLE, jobName,
						_getOriginalGroupName(groupName), null, null,
						_DEFAULT_INTERVAL);
				}
				catch (Exception e) {
				}
			}

			schedulerResponse.setTrigger(trigger);

			_defaultJobs.put(
				_getFullName(jobName, groupName), schedulerResponse);

			return schedulerResponse;
		}

		private String _getOriginalGroupName(String groupName) {
			int pos = groupName.indexOf(CharPool.POUND);

			return groupName.substring(pos + 1);
		}

		private StorageType _getStorageType(String groupName) {
			int pos = groupName.indexOf(CharPool.POUND);

			String storageTypeString = groupName.substring(0, pos);

			return StorageType.valueOf(storageTypeString);
		}

		private Map<String, SchedulerResponse> _defaultJobs;

	}

}