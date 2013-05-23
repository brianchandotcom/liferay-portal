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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.JobStateSerializeUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.scheduler.job.MessageSenderJob;
import com.liferay.portal.service.QuartzLocalService;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.UpdateLockRowSemaphore;
import org.quartz.impl.matchers.GroupMatcher;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Wesley Gong
 * @author Tina Tian
 */
public class QuartzSchedulerEngine implements SchedulerEngine {

	public void afterPropertiesSet() {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			quartzLocalService.checkQuartzTables();

			_persistedScheduler = initializeScheduler(
				"persisted.scheduler.", true);

			_memoryScheduler = initializeScheduler("memory.scheduler.", false);
		}
		catch (Exception e) {
			_log.error("Unable to initialize engine", e);
		}
	}

	public void delete(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			Set<JobKey> jobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				unregisterMessageListener(scheduler, jobKey);

				scheduler.deleteJob(jobKey);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete jobs in group " + groupName, e);
		}
	}

	public void delete(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			JobKey jobKey = new JobKey(jobName, groupName);

			unregisterMessageListener(scheduler, jobKey);

			scheduler.deleteJob(jobKey);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}
	}

	public void destroy() {
		try {
			shutdown();
		}
		catch (SchedulerException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to shutdown", se);
			}
		}
	}

	public SchedulerResponse getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			JobKey jobKey = new JobKey(jobName, groupName);

			return getScheduledJob(scheduler, jobKey);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to get job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}
	}

	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return Collections.emptyList();
		}

		try {
			List<String> groupNames = _persistedScheduler.getJobGroupNames();

			List<SchedulerResponse> schedulerResponses =
				new ArrayList<SchedulerResponse>();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(
					getScheduledJobs(_persistedScheduler, groupName));
			}

			groupNames = _memoryScheduler.getJobGroupNames();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(
					getScheduledJobs(_memoryScheduler, groupName));
			}

			return schedulerResponses;
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get jobs", e);
		}
	}

	public List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return Collections.emptyList();
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			return getScheduledJobs(scheduler, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to get jobs in group " + groupName, e);
		}
	}

	public void pause(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			Set<JobKey> jobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				updateJobState(scheduler, jobKey, TriggerState.PAUSED, false);
			}

			scheduler.pauseJobs(GroupMatcher.jobGroupEquals(groupName));
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to pause jobs in group " + groupName, e);
		}
	}

	public void pause(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			JobKey jobKey = new JobKey(jobName, groupName);

			updateJobState(scheduler, jobKey, TriggerState.PAUSED, false);

			scheduler.pauseJob(jobKey);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to pause job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}
	}

	public void resume(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			Set<JobKey> jobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				updateJobState(scheduler, jobKey, TriggerState.NORMAL, false);
			}

			scheduler.resumeJobs(GroupMatcher.jobGroupEquals(groupName));
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to resume jobs in group " + groupName, e);
		}
	}

	public void resume(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			JobKey jobKey = new JobKey(jobName, groupName);

			updateJobState(scheduler, jobKey, TriggerState.NORMAL, false);

			scheduler.resumeJob(jobKey);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to resume job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}
	}

	public void schedule(
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			String description, String destination, Message message)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(trigger.getGroupName());

			StorageType storageType = getStorageType(trigger.getGroupName());

			trigger = TriggerFactoryUtil.buildTrigger(
				trigger.getTriggerType(), trigger.getJobName(),
				getOriginalGroupName(trigger.getGroupName()),
				trigger.getStartDate(), trigger.getEndDate(),
				trigger.getTriggerContent());

			Trigger quartzTrigger = getQuartzTrigger(trigger);

			if (quartzTrigger == null) {
				return;
			}

			description = fixMaxLength(description, DESCRIPTION_MAX_LENGTH);

			if (message == null) {
				message = new Message();
			}
			else {
				message = message.clone();
			}

			registerMessageListeners(
				trigger.getJobName(), trigger.getGroupName(), destination,
				message);

			schedule(
				scheduler, storageType, quartzTrigger, description, destination,
				message);
		}
		catch (RuntimeException re) {

			// ServerDetector will throw an exception when JobSchedulerImpl is
			// initialized in a test environment

		}
		catch (Exception e) {
			throw new SchedulerException("Unable to schedule job", e);
		}
	}

	public void shutdown() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (!_persistedScheduler.isShutdown()) {
				_persistedScheduler.shutdown(false);
			}

			if (!_memoryScheduler.isShutdown()) {
				_memoryScheduler.shutdown(false);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to shutdown scheduler", e);
		}
	}

	public void start() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			_persistedScheduler.start();

			initJobState();

			_memoryScheduler.start();
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to start scheduler", e);
		}
	}

	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			JobKey jobKey = new JobKey(jobName, groupName);

			updateJobState(scheduler, jobKey, null, true);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to suppress error for job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}
	}

	public void unschedule(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			Set<JobKey> jobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				unschedule(scheduler, jobKey);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to unschedule jobs in group " + groupName, e);
		}
	}

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
			groupName = fixMaxLength(
				getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

			JobKey jobKey = new JobKey(jobName, groupName);

			unschedule(scheduler, jobKey);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to unschedule job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}
	}

	public void update(com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(trigger.getGroupName());

			trigger = TriggerFactoryUtil.buildTrigger(
				trigger.getTriggerType(), trigger.getJobName(),
				getOriginalGroupName(trigger.getGroupName()),
				trigger.getStartDate(), trigger.getEndDate(),
				trigger.getTriggerContent());

			update(scheduler, trigger);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to update trigger", e);
		}
	}

	protected String fixMaxLength(String argument, int maxLength) {
		if (argument == null) {
			return null;
		}

		if (argument.length() > maxLength) {
			argument = argument.substring(0, maxLength);
		}

		return argument;
	}

	protected String getFullName(String jobName, String groupName) {
		return groupName.concat(StringPool.PERIOD).concat(jobName);
	}

	protected JobState getJobState(JobDataMap jobDataMap) {
		Map<String, Object> jobStateMap = (Map<String, Object>)jobDataMap.get(
			JOB_STATE);

		return JobStateSerializeUtil.deserialize(jobStateMap);
	}

	protected Message getMessage(JobDataMap jobDataMap) {
		String messageJSON = (String)jobDataMap.get(MESSAGE);

		return (Message)JSONFactoryUtil.deserialize(messageJSON);
	}

	protected MessageListener getMessageListener(
			String messageListenerClassName, ClassLoader classLoader)
		throws SchedulerException {

		MessageListener schedulerEventListener = null;

		try {
			Class<? extends MessageListener> clazz =
				(Class<? extends MessageListener>)classLoader.loadClass(
					messageListenerClassName);

			schedulerEventListener = clazz.newInstance();

			schedulerEventListener =
				(MessageListener)ProxyUtil.newProxyInstance(
					classLoader, new Class<?>[] {MessageListener.class},
					new ClassLoaderBeanHandler(
						schedulerEventListener, classLoader));
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to register message listener with name " +
					messageListenerClassName,
				e);
		}

		return schedulerEventListener;
	}

	protected String getOriginalGroupName(String groupName) {
		int pos = groupName.indexOf(CharPool.POUND);

		return groupName.substring(pos + 1);
	}

	protected Trigger getQuartzTrigger(
			com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		if (trigger == null) {
			return null;
		}

		Date endDate = trigger.getEndDate();
		String jobName = fixMaxLength(
			trigger.getJobName(), JOB_NAME_MAX_LENGTH);
		String groupName = fixMaxLength(
			trigger.getGroupName(), GROUP_NAME_MAX_LENGTH);

		Date startDate = trigger.getStartDate();

		if (startDate == null) {
			startDate = new Date(System.currentTimeMillis());
		}

		Trigger quartzTrigger = null;

		TriggerType triggerType = trigger.getTriggerType();

		if (triggerType.equals(TriggerType.CRON)) {
			TriggerBuilder<Trigger>triggerBuilder = TriggerBuilder.newTrigger();

			triggerBuilder.endAt(endDate);
			triggerBuilder.forJob(jobName, groupName);
			triggerBuilder.startAt(startDate);
			triggerBuilder.withIdentity(jobName, groupName);

			CronScheduleBuilder cronScheduleBuilder =
				CronScheduleBuilder.cronSchedule(
					(String)trigger.getTriggerContent());

			triggerBuilder.withSchedule(cronScheduleBuilder);

			quartzTrigger = triggerBuilder.build();
		}
		else if (triggerType.equals(TriggerType.SIMPLE)) {
			long interval = (Long)trigger.getTriggerContent();

			if (interval <= 0) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Not scheduling " + trigger.getJobName() +
							" because interval is less than or equal to 0");
				}

				return null;
			}

			TriggerBuilder<Trigger>triggerBuilder = TriggerBuilder.newTrigger();

			triggerBuilder.endAt(endDate);
			triggerBuilder.forJob(jobName, groupName);
			triggerBuilder.startAt(startDate);
			triggerBuilder.withIdentity(jobName, groupName);

			SimpleScheduleBuilder simpleScheduleBuilder =
				SimpleScheduleBuilder.simpleSchedule();

			simpleScheduleBuilder.withIntervalInMilliseconds(interval);
			simpleScheduleBuilder.withRepeatCount(
				SimpleTrigger.REPEAT_INDEFINITELY);

			triggerBuilder.withSchedule(simpleScheduleBuilder);

			quartzTrigger = triggerBuilder.build();
		}
		else {
			throw new SchedulerException(
				"Unknown trigger type " + trigger.getTriggerType());
		}

		return quartzTrigger;
	}

	protected SchedulerResponse getScheduledJob(
			Scheduler scheduler, JobKey jobKey)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		if (jobDetail == null) {
			return null;
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		String description = jobDataMap.getString(DESCRIPTION);
		String destinationName = jobDataMap.getString(DESTINATION_NAME);
		Message message = getMessage(jobDataMap);
		StorageType storageType = StorageType.valueOf(
			jobDataMap.getString(STORAGE_TYPE));

		SchedulerResponse schedulerResponse = null;

		String jobName = jobKey.getName();
		String groupName = jobKey.getGroup();

		TriggerKey triggerKey = new TriggerKey(jobName, groupName);

		Trigger trigger = scheduler.getTrigger(triggerKey);

		JobState jobState = getJobState(jobDataMap);

		message.put(JOB_STATE, jobState);

		if (trigger == null) {
			schedulerResponse = new SchedulerResponse();

			schedulerResponse.setDescription(description);
			schedulerResponse.setDestinationName(destinationName);
			schedulerResponse.setGroupName(groupName);
			schedulerResponse.setJobName(jobName);
			schedulerResponse.setMessage(message);
			schedulerResponse.setStorageType(storageType);
		}
		else {
			message.put(END_TIME, trigger.getEndTime());
			message.put(FINAL_FIRE_TIME, trigger.getFinalFireTime());
			message.put(NEXT_FIRE_TIME, trigger.getNextFireTime());
			message.put(PREVIOUS_FIRE_TIME, trigger.getPreviousFireTime());
			message.put(START_TIME, trigger.getStartTime());

			if (CronTrigger.class.isAssignableFrom(trigger.getClass())) {
				CronTrigger cronTrigger = CronTrigger.class.cast(trigger);

				schedulerResponse = new SchedulerResponse();

				schedulerResponse.setDescription(description);
				schedulerResponse.setDestinationName(destinationName);
				schedulerResponse.setMessage(message);
				schedulerResponse.setStorageType(storageType);
				schedulerResponse.setTrigger(
					new com.liferay.portal.kernel.scheduler.CronTrigger(
						jobName, groupName, cronTrigger.getStartTime(),
						cronTrigger.getEndTime(),
						cronTrigger.getCronExpression()));
			}
			else if (SimpleTrigger.class.isAssignableFrom(trigger.getClass())) {
				SimpleTrigger simpleTrigger = SimpleTrigger.class.cast(trigger);

				schedulerResponse = new SchedulerResponse();

				schedulerResponse.setDescription(description);
				schedulerResponse.setDestinationName(destinationName);
				schedulerResponse.setMessage(message);
				schedulerResponse.setStorageType(storageType);
				schedulerResponse.setTrigger(
					new IntervalTrigger(
						jobName, groupName, simpleTrigger.getStartTime(),
						simpleTrigger.getEndTime(),
						simpleTrigger.getRepeatInterval()));
			}
		}

		return schedulerResponse;
	}

	protected List<SchedulerResponse> getScheduledJobs(
			Scheduler scheduler, String groupName)
		throws Exception {

		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		List<SchedulerResponse> schedulerResponses =
			new ArrayList<SchedulerResponse>();

		Set<JobKey> jobKeys = scheduler.getJobKeys(
			GroupMatcher.jobGroupEquals(groupName));

		for (JobKey jobKey : jobKeys) {
			SchedulerResponse schedulerResponse = getScheduledJob(
				scheduler, jobKey);

			if (schedulerResponse != null) {
				schedulerResponses.add(schedulerResponse);
			}
		}

		return schedulerResponses;
	}

	protected Scheduler getScheduler(String groupName) throws Exception {
		if (groupName.startsWith(StorageType.PERSISTED.toString())) {
			return _persistedScheduler;
		}
		else {
			return _memoryScheduler;
		}
	}

	protected StorageType getStorageType(String groupName) {
		int pos = groupName.indexOf(CharPool.POUND);

		String storageTypeString = groupName.substring(0, pos);

		return StorageType.valueOf(storageTypeString);
	}

	protected Scheduler initializeScheduler(
			String propertiesPrefix, boolean useQuartzCluster)
		throws Exception {

		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

		Properties properties = PropsUtil.getProperties(propertiesPrefix, true);

		if (useQuartzCluster) {
			DB db = DBFactoryUtil.getDB();

			String dbType = db.getType();

			if (dbType.equals(DB.TYPE_SQLSERVER)) {
				properties.setProperty(
					"org.quartz.jobStore.lockHandler.class",
					UpdateLockRowSemaphore.class.getName());
			}

			if (PropsValues.CLUSTER_LINK_ENABLED) {
				if (dbType.equals(DB.TYPE_HYPERSONIC)) {
					_log.error("Unable to cluster scheduler on Hypersonic");
				}
				else {
					properties.put(
						"org.quartz.jobStore.isClustered",
						Boolean.TRUE.toString());
				}
			}
		}

		schedulerFactory.initialize(properties);

		return schedulerFactory.getScheduler();
	}

	protected void initJobState() throws Exception {
		List<String> groupNames = _persistedScheduler.getJobGroupNames();

		for (String groupName : groupNames) {
			Set<JobKey> jobkeys = _persistedScheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobkeys) {
				Trigger trigger = _persistedScheduler.getTrigger(
					new TriggerKey(jobKey.getName(), jobKey.getGroup()));

				if (trigger != null) {
					continue;
				}

				JobDetail jobDetail = _persistedScheduler.getJobDetail(jobKey);

				JobDataMap jobDataMap = jobDetail.getJobDataMap();

				Message message = getMessage(jobDataMap);

				message.put(JOB_NAME, jobKey.getName());
				message.put(GROUP_NAME, jobKey.getGroup());

				SchedulerEngineHelperUtil.auditSchedulerJobs(
					message, TriggerState.EXPIRED);

				_persistedScheduler.deleteJob(jobKey);
			}
		}
	}

	protected void registerMessageListeners(
			String jobName, String groupName, String destinationName,
			Message message)
		throws SchedulerException {

		String messageListenerClassName = message.getString(
			MESSAGE_LISTENER_CLASS_NAME);

		if (Validator.isNull(messageListenerClassName)) {
			return;
		}

		String portletId = message.getString(PORTLET_ID);

		ClassLoader classLoader = null;

		if (Validator.isNull(portletId)) {
			classLoader = ClassLoaderUtil.getPortalClassLoader();
		}
		else {
			classLoader = PortletClassLoaderUtil.getClassLoader(portletId);

			if (classLoader == null) {

				// No class loader found for the portlet ID, try getting the
				// class loader where we assume the portlet ID is really a
				// servlet context name

				classLoader = ClassLoaderPool.getClassLoader(portletId);
			}
		}

		if (classLoader == null) {
			throw new SchedulerException(
				"Unable to find class loader for portlet " + portletId);
		}

		MessageListener schedulerEventListener = getMessageListener(
			messageListenerClassName, classLoader);

		SchedulerEventMessageListenerWrapper schedulerEventListenerWrapper =
			new SchedulerEventMessageListenerWrapper();

		schedulerEventListenerWrapper.setGroupName(groupName);
		schedulerEventListenerWrapper.setJobName(jobName);
		schedulerEventListenerWrapper.setMessageListener(
			schedulerEventListener);

		schedulerEventListenerWrapper.afterPropertiesSet();

		MessageBusUtil.registerMessageListener(
			destinationName, schedulerEventListenerWrapper);

		message.put(
			MESSAGE_LISTENER_UUID,
			schedulerEventListenerWrapper.getMessageListenerUUID());

		message.put(RECEIVER_KEY, getFullName(jobName, groupName));
	}

	protected void schedule(
			Scheduler scheduler, StorageType storageType, Trigger trigger,
			String description, String destinationName, Message message)
		throws Exception {

		try {
			JobBuilder jobBuilder = JobBuilder.newJob(MessageSenderJob.class);

			jobBuilder.withIdentity(trigger.getJobKey());

			JobDetail jobDetail = jobBuilder.build();

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(DESCRIPTION, description);
			jobDataMap.put(DESTINATION_NAME, destinationName);
			jobDataMap.put(MESSAGE, JSONFactoryUtil.serialize(message));
			jobDataMap.put(STORAGE_TYPE, storageType.toString());

			JobState jobState = new JobState(
				TriggerState.NORMAL, message.getInteger(EXCEPTIONS_MAX_SIZE));

			jobDataMap.put(
				JOB_STATE, JobStateSerializeUtil.serialize(jobState));

			unregisterMessageListener(scheduler, trigger.getJobKey());

			synchronized (this) {
				scheduler.deleteJob(trigger.getJobKey());
				scheduler.scheduleJob(jobDetail, trigger);
			}
		}
		catch (ObjectAlreadyExistsException oaee) {
			if (_log.isInfoEnabled()) {
				_log.info("Message is already scheduled");
			}
		}
	}

	protected void unregisterMessageListener(Scheduler scheduler, JobKey jobKey)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		if (jobDetail == null) {
			return;
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		if (jobDataMap == null) {
			return;
		}

		Message message = getMessage(jobDataMap);

		String messageListenerUUID = message.getString(MESSAGE_LISTENER_UUID);

		if (messageListenerUUID == null) {
			return;
		}

		String destinationName = jobDataMap.getString(DESTINATION_NAME);

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Destination destination = messageBus.getDestination(destinationName);

		Set<MessageListener> messageListeners =
			destination.getMessageListeners();

		for (MessageListener messageListener : messageListeners) {
			if (!(messageListener instanceof InvokerMessageListener)) {
				continue;
			}

			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)messageListener;

			messageListener = invokerMessageListener.getMessageListener();

			if (!(messageListener instanceof
					SchedulerEventMessageListenerWrapper)) {

				continue;
			}

			SchedulerEventMessageListenerWrapper schedulerMessageListener =
				(SchedulerEventMessageListenerWrapper)messageListener;

			if (messageListenerUUID.equals(
					schedulerMessageListener.getMessageListenerUUID())) {

				messageBus.unregisterMessageListener(
					destinationName, schedulerMessageListener);

				return;
			}
		}
	}

	protected void unschedule(Scheduler scheduler, JobKey jobKey)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		TriggerKey triggerKey = new TriggerKey(
			jobKey.getName(), jobKey.getGroup());

		if (jobDetail == null) {
			return;
		}

		unregisterMessageListener(scheduler, jobKey);

		if (scheduler == _memoryScheduler) {
			scheduler.unscheduleJob(triggerKey);

			return;
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		JobState jobState = getJobState(jobDataMap);

		Trigger trigger = scheduler.getTrigger(triggerKey);

		jobState.setTriggerDate(END_TIME, new Date());
		jobState.setTriggerDate(FINAL_FIRE_TIME, trigger.getPreviousFireTime());
		jobState.setTriggerDate(NEXT_FIRE_TIME, null);
		jobState.setTriggerDate(
			PREVIOUS_FIRE_TIME, trigger.getPreviousFireTime());
		jobState.setTriggerDate(START_TIME, trigger.getStartTime());

		jobState.setTriggerState(TriggerState.UNSCHEDULED);

		jobState.clearExceptions();

		jobDataMap.put(JOB_STATE, JobStateSerializeUtil.serialize(jobState));

		scheduler.unscheduleJob(triggerKey);

		scheduler.addJob(jobDetail, true);
	}

	protected void update(
			Scheduler scheduler,
			com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws Exception {

		Trigger quartzTrigger = getQuartzTrigger(trigger);

		if (quartzTrigger == null) {
			return;
		}

		TriggerKey triggerKey = quartzTrigger.getKey();

		if (scheduler.getTrigger(triggerKey) != null) {
			scheduler.rescheduleJob(triggerKey, quartzTrigger);
		}
		else {
			JobKey jobKey = quartzTrigger.getJobKey();

			JobDetail jobDetail = scheduler.getJobDetail(jobKey);

			if (jobDetail == null) {
				return;
			}

			updateJobState(scheduler, jobKey, TriggerState.NORMAL, true);

			synchronized (this) {
				scheduler.deleteJob(jobKey);
				scheduler.scheduleJob(jobDetail, quartzTrigger);
			}
		}
	}

	protected void updateJobState(
			Scheduler scheduler, JobKey jobKey, TriggerState triggerState,
			boolean suppressError)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		JobState jobState = getJobState(jobDataMap);

		if (triggerState != null) {
			jobState.setTriggerState(triggerState);
		}

		if (suppressError) {
			jobState.clearExceptions();
		}

		jobDataMap.put(JOB_STATE, JobStateSerializeUtil.serialize(jobState));

		scheduler.addJob(jobDetail, true);
	}

	@BeanReference(name = "com.liferay.portal.service.QuartzLocalService")
	protected QuartzLocalService quartzLocalService;

	private static Log _log = LogFactoryUtil.getLog(
		QuartzSchedulerEngine.class);

	private Scheduler _memoryScheduler;
	private Scheduler _persistedScheduler;

}