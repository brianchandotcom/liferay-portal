/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineClusterManager;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Tina Tian
 */
public class ClusterSchedulerEngine
	implements IdentifiableBean, SchedulerEngine,
			   SchedulerEngineClusterManager {

	public static SchedulerEngine createClusterSchedulerEngine(
		SchedulerEngine schedulerEngine) {

		if (PropsValues.CLUSTER_LINK_ENABLED) {
			schedulerEngine = new ClusterSchedulerEngine(schedulerEngine);
		}

		return schedulerEngine;
	}

	public ClusterSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	@Clusterable
	public void delete(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				removeMemoryClusteredJobs(groupName);

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete jobs in group " + groupName, e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.delete(groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	@Clusterable
	public void delete(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				_memoryClusteredJobs.remove(getFullName(jobName, groupName));

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.delete(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	public SchedulerResponse getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				return (SchedulerResponse)callMaster(
					_getScheduledJobMethodKey, jobName, groupName);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to get job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJob(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}
	}

	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return Collections.emptyList();
		}

		try {
			if (isMemorySchedulerSlave()) {
				return (List<SchedulerResponse>)callMaster(
					_getScheduledJobsMethodKey1);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get jobs", e);
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs();
		}
		finally {
			_readLock.unlock();
		}
	}

	public List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return Collections.emptyList();
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				return (List<SchedulerResponse>)callMaster(
					_getScheduledJobsMethodKey2, groupName);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to get jobs in group " + groupName, e);
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs(groupName);
		}
		finally {
			_readLock.unlock();
		}
	}

	public void initialize() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

			_readLock = readWriteLock.readLock();
			_writeLock = readWriteLock.writeLock();

			_localClusterNodeAddress = getSerializedString(
				ClusterExecutorUtil.getLocalClusterNodeAddress());

			_clusterEventListener = new MemorySchedulerClusterEventListener();

			ClusterExecutorUtil.addClusterEventListener(_clusterEventListener);

			if (!isMemorySchedulerClusterLockOwner(
					lockMemorySchedulerCluster(null))) {

				initMemoryClusteredJobs();
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to initialize scheduler", e);
		}
	}

	@Clusterable
	public void pause(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				updateMemoryClusteredJobs(groupName, TriggerState.PAUSED);

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to pause jobs in group " + groupName, e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.pause(groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	@Clusterable
	public void pause(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				updateMemoryClusteredJob(
					jobName, groupName, TriggerState.PAUSED);

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to pause job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.pause(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	@Clusterable
	public void resume(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				updateMemoryClusteredJobs(groupName, TriggerState.NORMAL);

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to resume jobs in group " + groupName, e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.resume(groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	@Clusterable
	public void resume(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				updateMemoryClusteredJob(
					jobName, groupName, TriggerState.NORMAL);

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to resume job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.resume(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	@Clusterable
	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		String groupName = trigger.getGroupName();
		String jobName = trigger.getJobName();

		try {
			if (isMemorySchedulerSlave(groupName)) {
				SchedulerResponse schedulerResponse = new SchedulerResponse();

				schedulerResponse.setDescription(description);
				schedulerResponse.setDestinationName(destinationName);
				schedulerResponse.setGroupName(groupName);
				schedulerResponse.setJobName(jobName);
				schedulerResponse.setMessage(message);
				schedulerResponse.setTrigger(trigger);

				_memoryClusteredJobs.put(
					getFullName(jobName, groupName),
					new ObjectValuePair<SchedulerResponse, TriggerState>(
						schedulerResponse, TriggerState.NORMAL));

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to schedule job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.schedule(
				trigger, description, destinationName, message);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public void shutdown() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			ClusterExecutorUtil.removeClusterEventListener(
				_clusterEventListener);

			LockLocalServiceUtil.unlock(
				_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, _localClusterNodeAddress,
				PropsValues.MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to shutdown scheduler", e);
		}

		_schedulerEngine.shutdown();
	}

	public void start() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		_schedulerEngine.start();
	}

	@Clusterable
	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to suppress error for job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.suppressError(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	@Clusterable
	public void unschedule(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				removeMemoryClusteredJobs(groupName);

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to unschedule jobs in group " + groupName, e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.unschedule(groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	@Clusterable
	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (isMemorySchedulerSlave(groupName)) {
				_memoryClusteredJobs.remove(getFullName(jobName, groupName));

				return;
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to unschedule job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.unschedule(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	@Clusterable
	public void update(Trigger trigger) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		String jobName = trigger.getJobName();
		String groupName = trigger.getGroupName();

		try {
			if (isMemorySchedulerSlave(groupName)) {
				for (ObjectValuePair<SchedulerResponse, TriggerState>
						memoryClusteredJob : _memoryClusteredJobs.values()) {

					SchedulerResponse schedulerResponse =
						memoryClusteredJob.getKey();

					if (jobName.equals(schedulerResponse.getJobName()) &&
						groupName.equals(schedulerResponse.getGroupName())) {

						schedulerResponse.setTrigger(trigger);

						return;
					}
				}

				throw new Exception(
					"Unable to update trigger for memory clustered job");
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to update job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}

		_readLock.lock();

		try {
			_schedulerEngine.update(trigger);
		}
		finally {
			_readLock.unlock();
		}

		skipClusterInvoking(groupName);
	}

	public Lock updateMemorySchedulerClusterMaster() throws SchedulerException {
		try {
			Lock lock = lockMemorySchedulerCluster(null);

			Address address = (Address)getDeserializedObject(lock.getOwner());

			if (ClusterExecutorUtil.isClusterNodeAlive(address)) {
				return lock;
			}

			return lockMemorySchedulerCluster(lock.getOwner());
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to update memory scheduler cluster master", e);
		}
	}

	protected Object callMaster(MethodKey methodKey, Object... arguments)
		throws Exception {

		MethodHandler methodHandler = new MethodHandler(methodKey, arguments);

		Lock lock = updateMemorySchedulerClusterMaster();

		Address address = (Address)getDeserializedObject(lock.getOwner());

		if (address.equals(ClusterExecutorUtil.getLocalClusterNodeAddress())) {
			if (methodKey == _getScheduledJobsMethodKey3) {
				return methodHandler.invoke(false);
			}
			else {
				return methodHandler.invoke(schedulerEngine);
			}
		}

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			methodHandler, address);

		clusterRequest.setBeanIdentifier(_beanIdentifier);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		try {
			ClusterNodeResponses clusterNodeResponses =
				futureClusterResponses.get(20, TimeUnit.SECONDS);

			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(address);

			return clusterNodeResponse.getResult();
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to load scheduled jobs from cluster node " +
					address.getDescription(),
				e);
		}
	}

	protected Object getDeserializedObject(String string) throws Exception {
		byte[] bytes = Base64.decode(string);

		UnsyncByteArrayInputStream byteArrayInputStream =
			new UnsyncByteArrayInputStream(bytes);

		ObjectInputStream objectInputStream = new ObjectInputStream(
			byteArrayInputStream);

		Object object = objectInputStream.readObject();

		objectInputStream.close();

		return object;
	}

	protected String getFullName(String jobName, String groupName) {
		return groupName.concat(StringPool.PERIOD).concat(jobName);
	}

	protected String getSerializedString(Object object) throws Exception {
		UnsyncByteArrayOutputStream byteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			byteArrayOutputStream);

		objectOutputStream.writeObject(object);
		objectOutputStream.close();

		byte[] bytes = byteArrayOutputStream.toByteArray();

		return Base64.encode(bytes);
	}

	protected StorageType getStorageType(String groupName) {
		int pos = groupName.indexOf(CharPool.POUND);

		String storageTypeString = groupName.substring(0, pos);

		return StorageType.valueOf(storageTypeString);
	}

	protected void initMemoryClusteredJobs() throws Exception {
		List<SchedulerResponse> schedulerResponses =
			(List<SchedulerResponse>)callMaster(
				_getScheduledJobsMethodKey3, StorageType.MEMORY_CLUSTERED);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			Trigger oldTrigger = schedulerResponse.getTrigger();

			String jobName = schedulerResponse.getJobName();
			String groupName = SchedulerEngineUtil.namespaceGroupName(
				schedulerResponse.getGroupName(), StorageType.MEMORY_CLUSTERED);

			Trigger newTrigger = TriggerFactoryUtil.buildTrigger(
				oldTrigger.getTriggerType(), jobName, groupName,
				oldTrigger.getStartDate(), oldTrigger.getEndDate(),
				oldTrigger.getTriggerContent());

			schedulerResponse.setTrigger(newTrigger);

			TriggerState triggerState = SchedulerEngineUtil.getJobState(
				schedulerResponse);

			Message message = schedulerResponse.getMessage();

			message.remove(JOB_STATE);

			_memoryClusteredJobs.put(
				getFullName(jobName, groupName),
				new ObjectValuePair<SchedulerResponse, TriggerState>(
					schedulerResponse, triggerState));
		}
	}

	protected boolean isMemorySchedulerClusterLockOwner(Lock lock)
		throws Exception {

		boolean master = _localClusterNodeAddress.equals(lock.getOwner());

		if (master == _master) {
			return master;
		}

		if (!_master) {
			_master = master;

			return _master;
		}

		_localClusterNodeAddress = getSerializedString(
			ClusterExecutorUtil.getLocalClusterNodeAddress());

		for (ObjectValuePair<SchedulerResponse, TriggerState>
				memoryClusteredJob : _memoryClusteredJobs.values()) {

			SchedulerResponse schedulerResponse = memoryClusteredJob.getKey();

			_schedulerEngine.delete(
				schedulerResponse.getJobName(),
				schedulerResponse.getGroupName());
		}

		initMemoryClusteredJobs();

		if (_log.isInfoEnabled()) {
			_log.info("Another node is now the memory scheduler master");
		}

		_master = master;

		return master;
	}

	protected boolean isMemorySchedulerSlave() throws Exception {
		return isMemorySchedulerSlave(null);
	}

	protected boolean isMemorySchedulerSlave(String groupName)
		throws Exception {

		if (groupName != null) {
			StorageType storageType = getStorageType(groupName);

			if (!storageType.equals(StorageType.MEMORY_CLUSTERED)) {
				return false;
			}
		}

		Lock lock = lockMemorySchedulerCluster(null);

		if (isMemorySchedulerClusterLockOwner(lock)) {
			return false;
		}

		return true;
	}

	protected Lock lockMemorySchedulerCluster(String owner) throws Exception {
		Lock lock = null;

		while (true) {
			try {
				if (owner == null) {
					lock = LockLocalServiceUtil.lock(
						_LOCK_CLASS_NAME, _LOCK_CLASS_NAME,
						_localClusterNodeAddress,
						PropsValues.
							MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED);
				}
				else {
					lock = LockLocalServiceUtil.lock(
						_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, owner,
						_localClusterNodeAddress,
						PropsValues.
							MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED);
				}

				break;
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to obtain memory scheduler cluster lock. " +
							"Trying again.");
				}
			}
		}

		if (!lock.isNew()) {
			return lock;
		}

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
					schedulerResponse.getMessage());

				TriggerState triggerState = memoryClusteredJob.getValue();

				if (triggerState.equals(TriggerState.PAUSED)) {
					_schedulerEngine.pause(
						schedulerResponse.getJobName(),
						schedulerResponse.getGroupName());
				}
			}
		}
		finally {
			ProxyModeThreadLocal.setForceSync(forceSync);

			_writeLock.unlock();
		}

		return lock;
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

	protected void skipClusterInvoking(String groupName)
		throws SchedulerException {

		StorageType storageType = getStorageType(groupName);

		if (storageType.equals(StorageType.PERSISTED)) {
			SchedulerException schedulerException = new SchedulerException();

			schedulerException.setSwallowable(true);

			throw schedulerException;
		}
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

	@BeanReference(
		name="com.liferay.portal.scheduler.ClusterSchedulerEngineService")
	protected SchedulerEngine schedulerEngine;

	private static final String _LOCK_CLASS_NAME =
		SchedulerEngine.class.getName();

	private static Log _log = LogFactoryUtil.getLog(
		ClusterSchedulerEngine.class);

	private static MethodKey _getScheduledJobMethodKey = new MethodKey(
		SchedulerEngine.class.getName(), "getScheduledJob", String.class,
		String.class);
	private static MethodKey _getScheduledJobsMethodKey1 = new MethodKey(
		SchedulerEngine.class.getName(), "getScheduledJobs");
	private static MethodKey _getScheduledJobsMethodKey2 = new MethodKey(
		SchedulerEngine.class.getName(), "getScheduledJobs", String.class);
	private static MethodKey _getScheduledJobsMethodKey3 = new MethodKey(
		SchedulerEngineUtil.class.getName(), "getScheduledJobs",
		StorageType.class);

	private String _beanIdentifier;
	private ClusterEventListener _clusterEventListener;
	private volatile String _localClusterNodeAddress;
	private volatile boolean _master;
	private Map<String, ObjectValuePair<SchedulerResponse, TriggerState>>
		_memoryClusteredJobs = new ConcurrentHashMap
			<String, ObjectValuePair<SchedulerResponse, TriggerState>>();
	private java.util.concurrent.locks.Lock _readLock;
	private SchedulerEngine _schedulerEngine;
	private java.util.concurrent.locks.Lock _writeLock;

	private class MemorySchedulerClusterEventListener
		implements ClusterEventListener {

		public void processClusterEvent(ClusterEvent clusterEvent) {
			try {
				Lock lock = updateMemorySchedulerClusterMaster();

				isMemorySchedulerClusterLockOwner(lock);
			}
			catch (Exception e) {
				_log.error("Unable to update memory scheduler cluster lock", e);
			}
		}

	}

}