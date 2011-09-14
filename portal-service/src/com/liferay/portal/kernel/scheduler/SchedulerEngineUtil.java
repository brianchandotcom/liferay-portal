/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class SchedulerEngineUtil {

	public static void addScriptingJob(
			Trigger trigger, StorageType storageType, String description,
			String language, String script, int exceptionsMaxSize)
		throws SchedulerException {

		_instance._addScriptingJob(
			trigger, storageType, description, language, script,
			exceptionsMaxSize);
	}

	public static void delete(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		_instance._delete(schedulerEntry, storageType);
	}

	public static void delete(String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._delete(groupName, storageType);
	}

	public static void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._delete(jobName, groupName, storageType);
	}

	public static Date getEndTime(SchedulerResponse schedulerResponse) {
		return _instance._getEndTime(schedulerResponse);
	}

	public static Date getEndTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getEndTime(jobName, groupName, storageType);
	}

	public static Date getFinalFireTime(SchedulerResponse schedulerResponse) {
		return _instance._getFinalFireTime(schedulerResponse);
	}

	public static Date getFinalFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getFinalFireTime(jobName, groupName, storageType);
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
		SchedulerResponse schedulerResponse) {

		return _instance._getJobExceptions(schedulerResponse);
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getJobExceptions(jobName, groupName, storageType);
	}

	public static TriggerState getJobState(
		SchedulerResponse schedulerResponse) {

		return _instance._getJobState(schedulerResponse);
	}

	public static TriggerState getJobState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getJobState(jobName, groupName, storageType);
	}

	public static Date getNextFireTime(SchedulerResponse schedulerResponse) {
		return _instance._getNextFireTime(schedulerResponse);
	}

	public static Date getNextFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getNextFireTime(jobName, groupName, storageType);
	}

	public static Date getPreviousFireTime(
		SchedulerResponse schedulerResponse) {

		return _instance._getPreviousFireTime(schedulerResponse);
	}

	public static Date getPreviousFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getPreviousFireTime(jobName, groupName, storageType);
	}

	public static SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getScheduledJob(jobName, groupName, storageType);
	}

	public static List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return _instance._getScheduledJobs();
	}

	public static List<SchedulerResponse> getScheduledJobs(
			StorageType storageType)
		throws SchedulerException {

		return _instance._getScheduledJobs(storageType);
	}

	public static List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getScheduledJobs(groupName, storageType);
	}

	public static Date getStartTime(SchedulerResponse schedulerResponse) {
		return _instance._getStartTime(schedulerResponse);
	}

	public static Date getStartTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _instance._getStartTime(jobName, groupName, storageType);
	}

	public static String namespaceGroupName(
		String groupName, StorageType storageType) {

		return _instance._namespaceGroupName(groupName, storageType);
	}

	public static void pause(String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._pause(groupName, storageType);
	}

	public static void pause(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._pause(jobName, groupName, storageType);
	}

	public static void resume(String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._resume(groupName, storageType);
	}

	public static void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._resume(jobName, groupName, storageType);
	}

	public static void schedule(
			SchedulerEntry schedulerEntry, StorageType storageType,
			ClassLoader classLoader, int exceptionsMaxSize)
		throws SchedulerException {

		_instance._schedule(
			schedulerEntry, storageType, classLoader, exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message, int exceptionsMaxSize)
		throws SchedulerException {

		_instance._schedule(
			trigger, storageType, description, destinationName, message,
			exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload, int exceptionsMaxSize)
		throws SchedulerException {

		_instance._schedule(
			trigger, storageType, description, destinationName, payload,
			exceptionsMaxSize);
	}

	public static void shutdown() throws SchedulerException {
		_instance._shutdown();
	}

	public static void start() throws SchedulerException {
		_instance._start();
	}

	public static void suppressError(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._suppressError(jobName, groupName, storageType);
	}

	public static void unschedule(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		_instance._unschedule(schedulerEntry, storageType);
	}

	public static void unschedule(String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._unschedule(groupName, storageType);
	}

	public static void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_instance._unschedule(jobName, groupName, storageType);
	}

	public static void update(
			String jobName, String groupName, StorageType storageType,
			String description, String language, String script,
			int exceptionsMaxSize)
		throws SchedulerException {

		_instance._update(
			jobName, groupName, storageType, description, language, script,
			exceptionsMaxSize);
	}

	public static void update(Trigger trigger, StorageType storageType)
		throws SchedulerException {

		_instance._update(trigger, storageType);
	}

	public static void updateMemorySchedulerClusterMaster()
		throws SchedulerException {

		_instance._updateMemorySchedulerClusterMaster();
	}

	public void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		_instance._schedulerEngine = schedulerEngine;

		if (schedulerEngine instanceof SchedulerEngineClusterManager) {
			_instance._schedulerEngineClusterManager =
				(SchedulerEngineClusterManager)schedulerEngine;
		}
	}

	private void _addScriptingJob(
			Trigger trigger, StorageType storageType, String description,
			String language, String script, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.put(SchedulerEngine.LANGUAGE, language);
		message.put(SchedulerEngine.SCRIPT, script);

		_schedule(
			trigger, storageType, description,
			DestinationNames.SCHEDULER_SCRIPTING, message, exceptionsMaxSize);
	}

	private void _delete(SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		Trigger trigger = schedulerEntry.getTrigger();

		_delete(trigger.getJobName(), trigger.getGroupName(), storageType);
	}

	private void _delete(String groupName, StorageType storageType)
		throws SchedulerException {

		_unregisterMessageListener(groupName, storageType);

		_schedulerEngine.delete(_namespaceGroupName(groupName, storageType));
	}

	private void _delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_unregisterMessageListener(jobName, groupName, storageType);

		_schedulerEngine.delete(
			jobName, _namespaceGroupName(groupName, storageType));
	}

	private Date _getEndTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.END_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(SchedulerEngine.END_TIME);
		}
	}

	private Date _getEndTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return _getEndTime(schedulerResponse);
		}

		return null;
	}

	private Date _getFinalFireTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.FINAL_FIRE_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(
				SchedulerEngine.FINAL_FIRE_TIME);
		}
	}

	private Date _getFinalFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return _getFinalFireTime(schedulerResponse);
		}

		return null;
	}

	private ObjectValuePair<Exception, Date>[] _getJobExceptions(
		SchedulerResponse schedulerResponse) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		return jobState.getExceptions();
	}

	private ObjectValuePair<Exception, Date>[] _getJobExceptions(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return _getJobExceptions(schedulerResponse);
		}

		return null;
	}

	private TriggerState _getJobState(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		return jobState.getTriggerState();
	}

	private TriggerState _getJobState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return _getJobState(schedulerResponse);
		}

		return null;
	}

	private Date _getNextFireTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.NEXT_FIRE_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(
				SchedulerEngine.NEXT_FIRE_TIME);
		}
	}

	private Date _getNextFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return _getNextFireTime(schedulerResponse);
		}

		return null;
	}

	private Date _getPreviousFireTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.PREVIOUS_FIRE_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(
				SchedulerEngine.PREVIOUS_FIRE_TIME);
		}
	}

	private Date _getPreviousFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return _getPreviousFireTime(schedulerResponse);
		}

		return null;
	}

	private SchedulerResponse _getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJob(
			jobName, _namespaceGroupName(groupName, storageType));
	}

	private List<SchedulerResponse> _getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs();
	}

	private List<SchedulerResponse> _getScheduledJobs(StorageType storageType)
		throws SchedulerException {

		List<SchedulerResponse> schedulerResponses =
			new ArrayList<SchedulerResponse>();

		for (SchedulerResponse schedulerResponse :
				_schedulerEngine.getScheduledJobs()) {

			if (storageType.equals(schedulerResponse.getStorageType())) {
				schedulerResponses.add(schedulerResponse);
			}
		}

		return schedulerResponses;
	}

	private List<SchedulerResponse> _getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(
			_namespaceGroupName(groupName, storageType));
	}

	private MessageListener _getSchedulerEventListener(
			SchedulerEntry schedulerEntry, ClassLoader classLoader)
		throws SchedulerException {

		try {
			MessageListener schedulerEventListener =
				(MessageListener)classLoader.loadClass(
					schedulerEntry.getEventListenerClass()).newInstance();

			return (MessageListener)ProxyUtil.newProxyInstance(
				classLoader, new Class[] {MessageListener.class},
				new ClassLoaderBeanHandler(
					schedulerEventListener, classLoader));
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	private Date _getStartTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if ((triggerState.equals(TriggerState.NORMAL)) ||
			(triggerState.equals(TriggerState.PAUSED))) {

			return (Date)message.get(SchedulerEngine.START_TIME);
		}
		else {
			return jobState.getTriggerTimeInfomation(
				SchedulerEngine.START_TIME);
		}
	}

	private Date _getStartTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse != null) {
			return _getStartTime(schedulerResponse);
		}

		return null;
	}

	private String _namespaceGroupName(
		String groupName, StorageType storageType) {

		return storageType.toString().concat(StringPool.POUND).concat(
			groupName);
	}

	private void _pause(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.pause(_namespaceGroupName(groupName, storageType));
	}

	private void _pause(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.pause(
			jobName, _namespaceGroupName(groupName, storageType));
	}

	private void _resume(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.resume(_namespaceGroupName(groupName, storageType));
	}

	private void _resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.resume(
			jobName, _namespaceGroupName(groupName, storageType));
	}

	private void _schedule(
			SchedulerEntry schedulerEntry, StorageType storageType,
			ClassLoader classLoader, int exceptionsMaxSize)
		throws SchedulerException {

		SchedulerEventMessageListenerWrapper schedulerEventListenerWrapper =
			new SchedulerEventMessageListenerWrapper();

		schedulerEventListenerWrapper.setClassName(
			schedulerEntry.getEventListenerClass());
		schedulerEventListenerWrapper.setMessageListener(
			_getSchedulerEventListener(schedulerEntry, classLoader));

		schedulerEventListenerWrapper.afterPropertiesSet();

		schedulerEntry.setEventListener(schedulerEventListenerWrapper);

		MessageBusUtil.registerMessageListener(
			DestinationNames.SCHEDULER_DISPATCH, schedulerEventListenerWrapper);

		Message message = new Message();

		message.put(
			SchedulerEngine.CONTEXT_PATH, schedulerEntry.getContextPath());
		message.put(
			SchedulerEngine.MESSAGE_LISTENER_UUID,
			schedulerEventListenerWrapper.getMessageListenerUUID());

		_schedule(
			schedulerEntry.getTrigger(), storageType,
			schedulerEntry.getDescription(),
			DestinationNames.SCHEDULER_DISPATCH, message, exceptionsMaxSize);
	}

	private void _schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message, int exceptionsMaxSize)
		throws SchedulerException {

		if (message == null) {
			message = new Message();
		}

		message.put(SchedulerEngine.EXCEPTIONS_MAX_SIZE, exceptionsMaxSize);

		trigger = TriggerFactoryUtil.buildTrigger(
			trigger.getTriggerType(), trigger.getJobName(),
			_namespaceGroupName(trigger.getGroupName(), storageType),
			trigger.getStartDate(), trigger.getEndDate(),
			trigger.getTriggerContent());

		_schedulerEngine.schedule(
			trigger, description, destinationName, message);
	}

	private void _schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.setPayload(payload);

		_schedule(
			trigger, storageType, description, destinationName, message,
			exceptionsMaxSize);
	}

	private void _shutdown() throws SchedulerException {
		_schedulerEngine.shutdown();
	}

	private void _start() throws SchedulerException {
		_schedulerEngine.start();
	}

	private void _suppressError(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.suppressError(
			jobName, _namespaceGroupName(groupName, storageType));
	}

	private void _unregisterMessageListener(
		SchedulerResponse schedulerResponse) {

		if (schedulerResponse == null) {
			return;
		}

		String destinationName = schedulerResponse.getDestinationName();

		if (!destinationName.equals(DestinationNames.SCHEDULER_DISPATCH)) {
			return;
		}

		Message message = schedulerResponse.getMessage();

		String messageListenerUUID = message.getString(
			SchedulerEngine.MESSAGE_LISTENER_UUID);

		if (messageListenerUUID == null) {
			return;
		}

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Destination destination = messageBus.getDestination(
			DestinationNames.SCHEDULER_DISPATCH);

		Set<MessageListener> messageListeners =
			destination.getMessageListeners();

		for (MessageListener messageListener : messageListeners) {
			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)messageListener;

			SchedulerEventMessageListenerWrapper schedulerMessageListener =
				(SchedulerEventMessageListenerWrapper)
					invokerMessageListener.getMessageListener();

			if (messageListenerUUID.equals(
					schedulerMessageListener.getMessageListenerUUID())) {

				MessageBusUtil.unregisterMessageListener(
					DestinationNames.SCHEDULER_DISPATCH,
					schedulerMessageListener);

				return;
			}
		}
	}

	private void _unregisterMessageListener(
			String groupName, StorageType storageType)
		throws SchedulerException {

		List<SchedulerResponse> schedulerResponses = _getScheduledJobs(
			groupName, storageType);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_unregisterMessageListener(schedulerResponse);
		}
	}

	private void _unregisterMessageListener(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		_unregisterMessageListener(schedulerResponse);
	}

	private void _unschedule(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		Trigger trigger = schedulerEntry.getTrigger();

		_unschedule(trigger.getJobName(), trigger.getGroupName(), storageType);
	}

	private void _unschedule(String groupName, StorageType storageType)
		throws SchedulerException {

		_unregisterMessageListener(groupName, storageType);

		_schedulerEngine.unschedule(
			_namespaceGroupName(groupName, storageType));
	}

	private void _unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_unregisterMessageListener(jobName, groupName, storageType);

		_schedulerEngine.unschedule(
			jobName, _namespaceGroupName(groupName, storageType));
	}

	private void _update(
			String jobName, String groupName, StorageType storageType,
			String description, String language, String script,
			int exceptionsMaxSize)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = _getScheduledJob(
			jobName, groupName, storageType);

		if (schedulerResponse == null) {
			return;
		}

		Trigger trigger = schedulerResponse.getTrigger();

		if (trigger == null) {
			return;
		}

		Message message = schedulerResponse.getMessage();

		if (message == null) {
			return;
		}

		_unregisterMessageListener(schedulerResponse);

		_addScriptingJob(
			trigger, storageType, description, language, script,
			exceptionsMaxSize);
	}

	private void _update(Trigger trigger, StorageType storageType)
		throws SchedulerException {

		trigger = TriggerFactoryUtil.buildTrigger(
			trigger.getTriggerType(), trigger.getJobName(),
			_namespaceGroupName(trigger.getGroupName(), storageType),
			trigger.getStartDate(), trigger.getEndDate(),
			trigger.getTriggerContent());

		_schedulerEngine.update(trigger);
	}

	private void _updateMemorySchedulerClusterMaster()
		throws SchedulerException {

		if (_schedulerEngineClusterManager == null) {
			_log.error(
				"Unable to update memory scheduler cluster master because " +
					"the portal is not using a clustered scheduler engine");

			return;
		}

		_schedulerEngineClusterManager.updateMemorySchedulerClusterMaster();
	}

	private static Log _log = LogFactoryUtil.getLog(SchedulerEngineUtil.class);

	private static SchedulerEngineUtil _instance = new SchedulerEngineUtil();

	private SchedulerEngine _schedulerEngine;
	private SchedulerEngineClusterManager _schedulerEngineClusterManager;

}