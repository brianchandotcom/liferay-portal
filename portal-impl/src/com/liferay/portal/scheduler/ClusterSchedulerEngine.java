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

import com.liferay.portal.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.cluster.ClusterableContextThreadLocal;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.BaseClusterResponseCallback;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
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
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineClusterManager;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.servlet.PluginContextLifecycleThreadLocal;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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

		if (PropsValues.CLUSTER_LINK_ENABLED && PropsValues.SCHEDULER_ENABLED) {
			schedulerEngine = new ClusterSchedulerEngine(schedulerEngine);
		}

		return schedulerEngine;
	}

	public ClusterSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void delete(String groupName) throws SchedulerException {
		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				removeMemoryClusteredJobs(groupName);
			}
			else {
				_schedulerEngine.delete(groupName);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void delete(String jobName, String groupName)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				_memoryClusteredJobs.remove(getFullName(jobName, groupName));
			}
			else {
				_schedulerEngine.delete(jobName, groupName);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	@Override
	public SchedulerResponse getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		ObjectValuePair<String, StorageType> objectValuePair = resolveGroupName(
			groupName);

		StorageType storageType = objectValuePair.getValue();

		if (storageType.equals(StorageType.MEMORY_CLUSTERED)) {
			String masterAddressString = getMasterAddressString(false);

			if (!_localClusterNodeAddress.equals(masterAddressString)) {
				return (SchedulerResponse)callMaster(
					masterAddressString, _getScheduledJobMethodKey, jobName,
					objectValuePair.getKey(), storageType);
			}
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJob(jobName, groupName);
		}
		finally {
			_readLock.unlock();
		}
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		String masterAddressString = getMasterAddressString(false);

		if (!_localClusterNodeAddress.equals(masterAddressString)) {
			return callMaster(masterAddressString, _getScheduledJobsMethodKey1);
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs();
		}
		finally {
			_readLock.unlock();
		}
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		ObjectValuePair<String, StorageType> objectValuePair = resolveGroupName(
			groupName);

		StorageType storageType = objectValuePair.getValue();

		if (storageType.equals(StorageType.MEMORY_CLUSTERED)) {
			String masterAddressString = getMasterAddressString(false);

			if (!_localClusterNodeAddress.equals(masterAddressString)) {
				return callMaster(
					masterAddressString, _getScheduledJobsMethodKey2,
					objectValuePair.getKey(), storageType);
			}
		}

		_readLock.lock();

		try {
			return _schedulerEngine.getScheduledJobs(groupName);
		}
		finally {
			_readLock.unlock();
		}
	}

	@Override
	public void initialize() throws SchedulerException {
		try {
			ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

			_readLock = readWriteLock.readLock();
			_writeLock = readWriteLock.writeLock();

			_localClusterNodeAddress = getSerializedString(
				ClusterExecutorUtil.getLocalClusterNodeAddress());

			_clusterEventListener = new MemorySchedulerClusterEventListener();

			ClusterExecutorUtil.addClusterEventListener(_clusterEventListener);

			String masterAddressString = getMasterAddressString(false);

			if (!_localClusterNodeAddress.equals(masterAddressString)) {
				List<SchedulerResponse> schedulerResponses = callMaster(
					masterAddressString, _getScheduledJobsMethodKey3,
					StorageType.MEMORY_CLUSTERED);

				initMemoryClusteredJobs(schedulerResponses);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to initialize scheduler", e);
		}
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void pause(String groupName) throws SchedulerException {
		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				updateMemoryClusteredJobs(groupName, TriggerState.PAUSED);
			}
			else {
				_schedulerEngine.pause(groupName);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void pause(String jobName, String groupName)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				updateMemoryClusteredJob(
					jobName, groupName, TriggerState.PAUSED);
			}
			else {
				_schedulerEngine.pause(jobName, groupName);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void resume(String groupName) throws SchedulerException {
		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				updateMemoryClusteredJobs(groupName, TriggerState.NORMAL);
			}
			else {
				_schedulerEngine.resume(groupName);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void resume(String jobName, String groupName)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				updateMemoryClusteredJob(
					jobName, groupName, TriggerState.NORMAL);
			}
			else {
				_schedulerEngine.resume(jobName, groupName);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		String groupName = trigger.getGroupName();
		String jobName = trigger.getJobName();

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

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

				_memoryClusteredJobs.put(
					getFullName(jobName, groupName),
					new ObjectValuePair<SchedulerResponse, TriggerState>(
						schedulerResponse, TriggerState.NORMAL));
			}
			else {
				_schedulerEngine.schedule(
					trigger, description, destinationName, message);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public void shutdown() throws SchedulerException {
		_portalReady = false;

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

	@Override
	public void start() throws SchedulerException {
		_schedulerEngine.start();

		_portalReady = true;
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		if (!memoryClusteredSlaveJob) {
			_readLock.lock();

			try {
				_schedulerEngine.suppressError(jobName, groupName);
			}
			finally {
				_readLock.unlock();
			}
		}

		setClusterableThreadLocal(groupName);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void unschedule(String groupName) throws SchedulerException {
		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				removeMemoryClusteredJobs(groupName);
			}
			else {
				_schedulerEngine.unschedule(groupName);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

		_readLock.lock();

		try {
			if (memoryClusteredSlaveJob) {
				_memoryClusteredJobs.remove(getFullName(jobName, groupName));
			}
			else {
				_schedulerEngine.unschedule(jobName, groupName);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Clusterable(acceptor = SchedulerClusterInvokeAcceptor.class)
	@Override
	public void update(Trigger trigger) throws SchedulerException {
		String jobName = trigger.getJobName();
		String groupName = trigger.getGroupName();

		boolean memoryClusteredSlaveJob = isMemoryClusteredSlaveJob(groupName);

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
				_schedulerEngine.update(trigger);
			}
		}
		finally {
			_readLock.unlock();
		}

		setClusterableThreadLocal(groupName);
	}

	@Override
	public Lock updateMemorySchedulerClusterMaster() throws SchedulerException {
		getMasterAddressString(false);

		return null;
	}

	protected <T> T callMaster(
			String masterAddressString, MethodKey methodKey,
			Object... arguments)
		throws SchedulerException {

		MethodHandler methodHandler = new MethodHandler(methodKey, arguments);

		Address address = (Address)getDeserializedObject(masterAddressString);

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			methodHandler, address);

		try {
			FutureClusterResponses futureClusterResponses =
				ClusterExecutorUtil.execute(clusterRequest);

			ClusterNodeResponses clusterNodeResponses =
				futureClusterResponses.get(20, TimeUnit.SECONDS);

			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(address);

			return (T)clusterNodeResponse.getResult();
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to load scheduled jobs from cluster node " +
					address.getDescription(),
				e);
		}
	}

	protected Object getDeserializedObject(String string)
		throws SchedulerException {

		byte[] bytes = Base64.decode(string);

		UnsyncByteArrayInputStream byteArrayInputStream =
			new UnsyncByteArrayInputStream(bytes);

		ObjectInputStream objectInputStream = null;

		try {
			objectInputStream = new ObjectInputStream(byteArrayInputStream);

			Object object = objectInputStream.readObject();

			return object;
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to deserialize object from " + string, e);
		}
		finally {
			try {
				objectInputStream.close();
			}
			catch (Exception e) {
			}
		}
	}

	protected String getFullName(String jobName, String groupName) {
		return groupName.concat(StringPool.PERIOD).concat(jobName);
	}

	protected String getMasterAddressString(boolean asynchronous)
		throws SchedulerException {

		String owner = null;

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

				Address address = (Address)getDeserializedObject(
					lock.getOwner());

				if (ClusterExecutorUtil.isClusterNodeAlive(address)) {
					break;
				}
				else {
					owner = lock.getOwner();
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to obtain memory scheduler cluster lock. " +
							"Trying again.");
				}
			}
		}

		boolean master = _localClusterNodeAddress.equals(lock.getOwner());

		if (master == _master) {
			return lock.getOwner();
		}

		if (master) {
			slaveToMaster();
		}
		else {
			masterToSlave(lock.getOwner(), asynchronous);
		}

		return lock.getOwner();
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

	protected void initMemoryClusteredJobs(
			List<SchedulerResponse> schedulerResponses)
		throws Exception {

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			Trigger oldTrigger = schedulerResponse.getTrigger();

			String jobName = schedulerResponse.getJobName();
			String groupName = SchedulerEngineHelperUtil.namespaceGroupName(
				schedulerResponse.getGroupName(), StorageType.MEMORY_CLUSTERED);

			Trigger newTrigger = TriggerFactoryUtil.buildTrigger(
				oldTrigger.getTriggerType(), jobName, groupName,
				oldTrigger.getStartDate(), oldTrigger.getEndDate(),
				oldTrigger.getTriggerContent());

			schedulerResponse.setTrigger(newTrigger);

			TriggerState triggerState = SchedulerEngineHelperUtil.getJobState(
				schedulerResponse);

			Message message = schedulerResponse.getMessage();

			message.remove(JOB_STATE);

			_memoryClusteredJobs.put(
				getFullName(jobName, groupName),
				new ObjectValuePair<SchedulerResponse, TriggerState>(
					schedulerResponse, triggerState));
		}
	}

	protected boolean isMemoryClusteredSlaveJob(String groupName)
		throws SchedulerException {

		ObjectValuePair<String, StorageType> objectValuePair = resolveGroupName(
			groupName);

		StorageType storageType = objectValuePair.getValue();

		if (!storageType.equals(StorageType.MEMORY_CLUSTERED)) {
			return false;
		}

		String masterAddressString = getMasterAddressString(false);

		if (_localClusterNodeAddress.equals(masterAddressString)) {
			return false;
		}

		return true;
	}

	protected void masterToSlave(
			String masterAddressString, boolean asynchronous)
		throws SchedulerException {

		if (asynchronous) {
			MethodHandler methodHandler = new MethodHandler(
				_getScheduledJobsMethodKey3, StorageType.MEMORY_CLUSTERED);

			Address address = (Address)getDeserializedObject(
				masterAddressString);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler, address);

			try {
				ClusterExecutorUtil.execute(
					clusterRequest,
					new MemorySchedulerClusterResponseCallback(address), 20,
					TimeUnit.SECONDS);

				return;
			}
			catch (Exception e) {
				throw new SchedulerException(
					"Unable to load scheduled jobs from cluster node " +
						address.getDescription(),
					e);
			}
		}

		List<SchedulerResponse> schedulerResponses = callMaster(
			masterAddressString, _getScheduledJobsMethodKey3,
			StorageType.MEMORY_CLUSTERED);

		_doMasterToSlave(schedulerResponses);
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

	protected ObjectValuePair<String, StorageType> resolveGroupName(
		String groupName) {

		int index = groupName.indexOf(CharPool.POUND);

		String storageTypeString = groupName.substring(0, index);

		StorageType storageType = StorageType.valueOf(storageTypeString);

		String orginalGroupName = groupName.substring(index + 1);

		return new ObjectValuePair<String, StorageType>(
			orginalGroupName, storageType);
	}

	protected void setClusterableThreadLocal(String groupName) {
		ObjectValuePair<String, StorageType> objectValuePair = resolveGroupName(
			groupName);

		ClusterableContextThreadLocal.putThreadLocalContext(
			STORAGE_TYPE, objectValuePair.getValue());
		ClusterableContextThreadLocal.putThreadLocalContext(
			_PORTAL_READY, _portalReady);

		boolean pluginReady = true;

		if (PluginContextLifecycleThreadLocal.isInitializing() ||
			PluginContextLifecycleThreadLocal.isDestroying()) {

			pluginReady = false;
		}

		ClusterableContextThreadLocal.putThreadLocalContext(
			_PLUGIN_READY, pluginReady);
	}

	protected void slaveToMaster() throws SchedulerException {
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

			_memoryClusteredJobs.clear();
		}
		finally {
			ProxyModeThreadLocal.setForceSync(forceSync);

			_master = true;

			_writeLock.unlock();
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

	private void _doMasterToSlave(List<SchedulerResponse> schedulerResponses)
		throws SchedulerException {

		_writeLock.lock();

		try {
			for (SchedulerResponse schedulerResponse :
					_schedulerEngine.getScheduledJobs()) {

				if (StorageType.MEMORY_CLUSTERED ==
						schedulerResponse.getStorageType()) {

					String groupName = StorageType.MEMORY_CLUSTERED.toString();

					groupName = groupName.concat(StringPool.POUND).concat(
						schedulerResponse.getGroupName());

					_schedulerEngine.delete(
						schedulerResponse.getJobName(), groupName);
				}
			}

			initMemoryClusteredJobs(schedulerResponses);

			if (_log.isInfoEnabled()) {
				_log.info("Switched current node from master to slave");
			}
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
		finally {
			_master = false;

			_writeLock.unlock();
		}
	}

	private static final String _LOCK_CLASS_NAME =
		SchedulerEngine.class.getName();

	private static final String _PLUGIN_READY = "plugin.ready";

	private static final String _PORTAL_READY = "portal.ready";

	private static Log _log = LogFactoryUtil.getLog(
		ClusterSchedulerEngine.class);

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

	private String _beanIdentifier;
	private ClusterEventListener _clusterEventListener;
	private volatile String _localClusterNodeAddress;
	private volatile boolean _master;
	private Map<String, ObjectValuePair<SchedulerResponse, TriggerState>>
		_memoryClusteredJobs = new ConcurrentHashMap
			<String, ObjectValuePair<SchedulerResponse, TriggerState>>();
	private boolean _portalReady;
	private java.util.concurrent.locks.Lock _readLock;
	private SchedulerEngine _schedulerEngine;
	private java.util.concurrent.locks.Lock _writeLock;

	private static class SchedulerClusterInvokeAcceptor
		implements ClusterInvokeAcceptor {

		@Override
		public boolean accept(Map<String, Serializable> context) {
			if (ClusterInvokeThreadLocal.isEnabled()) {
				return true;
			}

			StorageType storageType = (StorageType)context.get(STORAGE_TYPE);
			boolean portalReady = (Boolean)context.get(_PORTAL_READY);
			boolean pluginReady = (Boolean)context.get(_PLUGIN_READY);

			if (storageType.equals(StorageType.PERSISTED) || !portalReady ||
				!pluginReady) {

				return false;
			}

			return true;
		}

	}

	private class MemorySchedulerClusterEventListener
		implements ClusterEventListener {

		@Override
		public void processClusterEvent(ClusterEvent clusterEvent) {
			try {
				getMasterAddressString(true);
			}
			catch (Exception e) {
				_log.error("Unable to update memory scheduler cluster lock", e);
			}
		}

	}

	private class MemorySchedulerClusterResponseCallback
		extends BaseClusterResponseCallback {

		public MemorySchedulerClusterResponseCallback(Address address) {
			_address = address;
		}

		@Override
		public void callback(ClusterNodeResponses clusterNodeResponses) {
			try {
				ClusterNodeResponse clusterNodeResponse =
					clusterNodeResponses.getClusterResponse(_address);

				List<SchedulerResponse> schedulerResponses =
					(List<SchedulerResponse>)clusterNodeResponse.getResult();

				_doMasterToSlave(schedulerResponses);
			}
			catch (Exception e) {
				_log.error(
					"Unable to load memory clustered jobs from cluster node " +
						_address.getDescription(),
					e);
			}
		}

		@Override
		public void processTimeoutException(TimeoutException timeoutException) {
			_log.error(
				"Unable to load memory clustered jobs from cluster node " +
					_address.getDescription(),
				timeoutException);
		}

		private Address _address;

	}

}