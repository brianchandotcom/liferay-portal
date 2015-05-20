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

import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.cluster.BaseClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.cluster.ClusterableContextThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.servlet.PluginContextLifecycleThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Tina Tian
 */
public class ClusterSchedulerEngine
	implements IdentifiableBean, SchedulerEngine {

	public ClusterSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;

		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

		_readLock = readWriteLock.readLock();
		_writeLock = readWriteLock.writeLock();
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void delete(String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				removeMemoryClusteredJobs(groupName);
			}
			else {
				_schedulerEngine.delete(groupName, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				_memoryClusteredJobs.remove(getFullName(jobName, groupName));
			}
			else {
				_schedulerEngine.delete(jobName, groupName, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	@Clusterable(onMaster = true)
	@Override
	public SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJob(
				jobName, groupName, storageType);
		}
		finally {
			_readLock.unlock();
		}
	}

	@Clusterable(onMaster = true)
	@Override
	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs();
		}
		finally {
			_readLock.unlock();
		}
	}

	@Clusterable(onMaster = true)
	@Override
	public List<SchedulerResponse> getScheduledJobs(StorageType storageType)
		throws SchedulerException {

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs(storageType);
		}
		finally {
			_readLock.unlock();
		}
	}

	@Clusterable(onMaster = true)
	@Override
	public List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs(groupName, storageType);
		}
		finally {
			_readLock.unlock();
		}
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void pause(String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				updateMemoryClusteredJobs(groupName, TriggerState.PAUSED);
			}
			else {
				_schedulerEngine.pause(groupName, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				updateMemoryClusteredJob(
					jobName, groupName, TriggerState.PAUSED);
			}
			else {
				_schedulerEngine.pause(jobName, groupName, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void resume(String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				updateMemoryClusteredJobs(groupName, TriggerState.NORMAL);
			}
			else {
				_schedulerEngine.resume(groupName, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				updateMemoryClusteredJob(
					jobName, groupName, TriggerState.NORMAL);
			}
			else {
				_schedulerEngine.resume(jobName, groupName, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message, StorageType storageType)
		throws SchedulerException {

		String groupName = trigger.getGroupName();
		String jobName = trigger.getJobName();

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				SchedulerResponse schedulerResponse = new SchedulerResponse();

				schedulerResponse.setDescription(description);
				schedulerResponse.setDestinationName(destinationName);
				schedulerResponse.setGroupName(groupName);
				schedulerResponse.setJobName(jobName);
				schedulerResponse.setMessage(message);
				schedulerResponse.setTrigger(trigger);
				schedulerResponse.setStorageType(storageType);

				_memoryClusteredJobs.put(
					getFullName(jobName, groupName),
					new ObjectValuePair<SchedulerResponse, TriggerState>(
						schedulerResponse, TriggerState.NORMAL));
			}
			else {
				_schedulerEngine.schedule(
					trigger, description, destinationName, message,
					storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public void shutdown() throws SchedulerException {
		_portalReady = false;

		_clusterMasterExecutor.removeClusterMasterTokenTransitionListener(
			_schedulerClusterMasterTokenTransitionListener);

		_schedulerEngine.shutdown();
	}

	@Override
	public void start() throws SchedulerException {
		try {
			if (!_clusterMasterExecutor.isMaster()) {
				initMemoryClusteredJobs();
			}

			_schedulerClusterMasterTokenTransitionListener =
				new SchedulerClusterMasterTokenTransitionListener();

			_clusterMasterExecutor.addClusterMasterTokenTransitionListener(
				_schedulerClusterMasterTokenTransitionListener);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to initialize scheduler", e);
		}

		_schedulerEngine.start();

		_portalReady = true;
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void suppressError(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		if (!memoryClusteredSlaveJob) {
			_readLock.lock();

			try {
				_schedulerEngine.suppressError(jobName, groupName, storageType);
			}
			finally {
				_readLock.unlock();
			}
		}

		setClusterableThreadLocal(storageType);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void unschedule(String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				removeMemoryClusteredJobs(groupName);
			}
			else {
				_schedulerEngine.unschedule(groupName, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				_memoryClusteredJobs.remove(getFullName(jobName, groupName));
			}
			else {
				_schedulerEngine.unschedule(jobName, groupName, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void update(Trigger trigger, StorageType storageType)
		throws SchedulerException {

		String jobName = trigger.getJobName();
		String groupName = trigger.getGroupName();

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(
			storageType);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				boolean updated = false;

				for (ObjectValuePair<SchedulerResponse, TriggerState>
						memoryClusteredJob : _memoryClusteredJobs.values()) {

					SchedulerResponse schedulerResponse =
						memoryClusteredJob.getKey();

					if (jobName.equals(schedulerResponse.getJobName()) &&
						groupName.equals(schedulerResponse.getGroupName())) {

						schedulerResponse.setTrigger(trigger);

						updated = true;

						break;
					}
				}

				if (!updated) {
					throw new SchedulerException(
						"Unable to update trigger for memory clustered job");
				}
			}
			else {
				_schedulerEngine.update(trigger, storageType);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(storageType);
	}

	protected String getFullName(String jobName, String groupName) {
		return groupName.concat(StringPool.PERIOD).concat(jobName);
	}

	protected void initMemoryClusteredJobs() throws Exception {
		MethodHandler methodHandler = new MethodHandler(
			_getScheduledJobsMethodKey, StorageType.MEMORY_CLUSTERED);

		Future<List<SchedulerResponse>> future =
			_clusterMasterExecutor.executeOnMaster(methodHandler);

		List<SchedulerResponse> schedulerResponses = future.get(
			GetterUtil.getLong(
				_props.get(PropsKeys.CLUSTERABLE_ADVICE_CALL_MASTER_TIMEOUT)),
			TimeUnit.SECONDS);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			String jobName = schedulerResponse.getJobName();
			String groupName = schedulerResponse.getGroupName();

			TriggerState triggerState = _schedulerEngineHelper.getJobState(
				schedulerResponse);

			Message message = schedulerResponse.getMessage();

			message.remove(JOB_STATE);

			_memoryClusteredJobs.put(
				getFullName(jobName, groupName),
				new ObjectValuePair<>(schedulerResponse, triggerState));
		}
	}

	protected boolean isMemoryClusteredSlaveJob(StorageType storageType) {
		if ((storageType != StorageType.MEMORY_CLUSTERED) ||
			_clusterMasterExecutor.isMaster()) {

			return false;
		}

		return true;
	}

	protected void removeMemoryClusteredJobs(String groupName) {
		Set<Map.Entry<String, ObjectValuePair<SchedulerResponse, TriggerState>>>
			memoryClusteredJobs = _memoryClusteredJobs.entrySet();

		Iterator
			<Map.Entry<String,
				ObjectValuePair<SchedulerResponse, TriggerState>>> itr =
					memoryClusteredJobs.iterator();

		while (itr.hasNext()) {
			Map.Entry <String, ObjectValuePair<SchedulerResponse, TriggerState>>
				entry = itr.next();

			ObjectValuePair<SchedulerResponse, TriggerState>
				memoryClusteredJob = entry.getValue();

			SchedulerResponse schedulerResponse = memoryClusteredJob.getKey();

			if (groupName.equals(schedulerResponse.getGroupName())) {
				itr.remove();
			}
		}
	}

	protected void setClusterableThreadLocal(StorageType storageType) {
		ClusterableContextThreadLocal.putThreadLocalContext(
			STORAGE_TYPE, storageType);
		ClusterableContextThreadLocal.putThreadLocalContext(
			PORTAL_READY, _portalReady);

		boolean pluginReady = true;

		if (PluginContextLifecycleThreadLocal.isInitializing() ||
			PluginContextLifecycleThreadLocal.isDestroying()) {

			pluginReady = false;
		}

		ClusterableContextThreadLocal.putThreadLocalContext(
			PLUGIN_READY, pluginReady);
	}

	protected void setClusterMasterExecutor(
		ClusterMasterExecutor clusterMasterExecutor) {

		_clusterMasterExecutor = clusterMasterExecutor;
	}

	protected void setProps(Props props) {
		_props = props;
	}

	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	protected void updateMemoryClusteredJob(
		String jobName, String groupName, TriggerState triggerState) {

		ObjectValuePair<SchedulerResponse, TriggerState>
			memoryClusteredJob = _memoryClusteredJobs.get(
				getFullName(jobName, groupName));

		if (memoryClusteredJob != null) {
			memoryClusteredJob.setValue(triggerState);
		}
	}

	protected void updateMemoryClusteredJobs(
		String groupName, TriggerState triggerState) {

		for (ObjectValuePair<SchedulerResponse, TriggerState>
				memoryClusteredJob : _memoryClusteredJobs.values()) {

			SchedulerResponse schedulerResponse = memoryClusteredJob.getKey();

			if (groupName.equals(schedulerResponse.getGroupName())) {
				memoryClusteredJob.setValue(triggerState);
			}
		}
	}

	protected static final String PLUGIN_READY = "plugin.ready";

	protected static final String PORTAL_READY = "portal.ready";

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterSchedulerEngine.class);

	private static final MethodKey _getScheduledJobsMethodKey = new MethodKey(
		SchedulerEngineHelperUtil.class, "getScheduledJobs", StorageType.class);

	private String _beanIdentifier;
	private ClusterMasterExecutor _clusterMasterExecutor;
	private final Map<String, ObjectValuePair<SchedulerResponse, TriggerState>>
		_memoryClusteredJobs = new ConcurrentHashMap<>();
	private boolean _portalReady;
	private Props _props;
	private final java.util.concurrent.locks.Lock _readLock;
	private ClusterMasterTokenTransitionListener
		_schedulerClusterMasterTokenTransitionListener;
	private final SchedulerEngine _schedulerEngine;
	private SchedulerEngineHelper _schedulerEngineHelper;
	private final java.util.concurrent.locks.Lock _writeLock;

	private class SchedulerClusterMasterTokenTransitionListener
		extends BaseClusterMasterTokenTransitionListener {

		@Override
		protected void doMasterTokenAcquired() throws Exception {
			boolean forceSync = ProxyModeThreadLocal.isForceSync();

			ProxyModeThreadLocal.setForceSync(true);

			_writeLock.lock();

			try {
				for (ObjectValuePair<SchedulerResponse, TriggerState>
						memoryClusteredJob : _memoryClusteredJobs.values()) {

					SchedulerResponse schedulerResponse =
						memoryClusteredJob.getKey();

					_schedulerEngine.schedule(
						schedulerResponse.getTrigger(),
						schedulerResponse.getDescription(),
						schedulerResponse.getDestinationName(),
						schedulerResponse.getMessage(),
						schedulerResponse.getStorageType());

					TriggerState triggerState = memoryClusteredJob.getValue();

					if (triggerState.equals(TriggerState.PAUSED)) {
						_schedulerEngine.pause(
							schedulerResponse.getJobName(),
							schedulerResponse.getGroupName(),
							schedulerResponse.getStorageType());
					}
				}

				_memoryClusteredJobs.clear();

				if (_log.isInfoEnabled()) {
					_log.info("MEMORY_CLUSTERED jobs are running on this node");
				}
			}
			finally {
				ProxyModeThreadLocal.setForceSync(forceSync);

				_writeLock.unlock();
			}
		}

		@Override
		protected void doMasterTokenReleased() throws Exception {
			_writeLock.lock();

			try {
				for (SchedulerResponse schedulerResponse :
						_schedulerEngine.getScheduledJobs()) {

					if (StorageType.MEMORY_CLUSTERED ==
							schedulerResponse.getStorageType()) {

						_schedulerEngine.delete(
							schedulerResponse.getJobName(),
							schedulerResponse.getGroupName(),
							schedulerResponse.getStorageType());
					}
				}

				initMemoryClusteredJobs();

				if (_log.isInfoEnabled()) {
					_log.info(
						"MEMORY_CLUSTERED jobs stopped running on this node");
				}
			}
			finally {
				_writeLock.unlock();
			}
		}

	}

}