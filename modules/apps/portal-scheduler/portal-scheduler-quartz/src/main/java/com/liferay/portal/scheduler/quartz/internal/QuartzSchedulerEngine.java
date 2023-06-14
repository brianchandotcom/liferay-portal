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

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.JobStateSerializeUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.scheduler.quartz.internal.job.MessageSenderJob;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import org.quartz.Calendar;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.ListenerManager;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.UpdateLockRowSemaphore;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.listeners.TriggerListenerSupport;
import org.quartz.spi.OperableTrigger;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Wesley Gong
 * @author Tina Tian
 * @author Edward C. Han
 */
@Component(
	enabled = false, property = "scheduler.engine.proxy=false",
	service = SchedulerEngine.class
)
public class QuartzSchedulerEngine implements SchedulerEngine {

	@Override
	public void delete(String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = _getScheduler(storageType);

			groupName = _fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			Set<JobKey> jobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				scheduler.deleteJob(jobKey);
			}
		}
		catch (Exception exception) {
			throw new SchedulerException(
				"Unable to delete jobs in group " + groupName, exception);
		}
	}

	@Override
	public void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = _getScheduler(storageType);

			jobName = _fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = _fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			JobKey jobKey = new JobKey(jobName, groupName);

			scheduler.deleteJob(jobKey);
		}
		catch (Exception exception) {
			throw new SchedulerException(
				StringBundler.concat(
					"Unable to delete job {jobName=", jobName, ", groupName=",
					groupName, "}"),
				exception);
		}
	}

	public int getDescriptionMaxLength() {
		return _descriptionMaxLength;
	}

	public int getGroupNameMaxLength() {
		return _groupNameMaxLength;
	}

	public int getJobNameMaxLength() {
		return _jobNameMaxLength;
	}

	@Override
	public SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = _getScheduler(storageType);

			jobName = _fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = _fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			JobKey jobKey = new JobKey(jobName, groupName);

			return getScheduledJob(scheduler, jobKey);
		}
		catch (Exception exception) {
			throw new SchedulerException(
				StringBundler.concat(
					"Unable to get job {jobName=", jobName, ", groupName=",
					groupName, "}"),
				exception);
		}
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		try {
			List<String> groupNames = _persistedScheduler.getJobGroupNames();

			List<SchedulerResponse> schedulerResponses = new ArrayList<>();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(
					getScheduledJobs(_persistedScheduler, groupName, null));
			}

			groupNames = _memoryScheduler.getJobGroupNames();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(
					getScheduledJobs(_memoryScheduler, groupName, null));
			}

			return schedulerResponses;
		}
		catch (Exception exception) {
			throw new SchedulerException("Unable to get jobs", exception);
		}
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = _getScheduler(storageType);

			List<String> groupNames = scheduler.getJobGroupNames();

			List<SchedulerResponse> schedulerResponses = new ArrayList<>();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(
					getScheduledJobs(scheduler, groupName, storageType));
			}

			return schedulerResponses;
		}
		catch (Exception exception) {
			throw new SchedulerException(
				"Unable to get jobs with type " + storageType, exception);
		}
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			return getScheduledJobs(
				_getScheduler(storageType), groupName, storageType);
		}
		catch (Exception exception) {
			throw new SchedulerException(
				"Unable to get jobs in group " + groupName, exception);
		}
	}

	@Override
	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = _getScheduler(storageType);

			jobName = _fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = _fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			JobKey jobKey = new JobKey(jobName, groupName);

			scheduler.pauseJob(jobKey);

			_updateJobState(scheduler, jobKey, TriggerState.PAUSED);
		}
		catch (Exception exception) {
			throw new SchedulerException(
				StringBundler.concat(
					"Unable to pause job {jobName=", jobName, ", groupName=",
					groupName, "}"),
				exception);
		}
	}

	@Override
	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = _getScheduler(storageType);

			jobName = _fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = _fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			JobKey jobKey = new JobKey(jobName, groupName);

			scheduler.resumeJob(jobKey);

			_updateJobState(scheduler, jobKey, TriggerState.NORMAL);
		}
		catch (Exception exception) {
			throw new SchedulerException(
				StringBundler.concat(
					"Unable to resume job {jobName=", jobName, ", groupName=",
					groupName, "}"),
				exception);
		}
	}

	public void run(
			long companyId, String jobName, String groupName,
			StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse = getScheduledJob(
			jobName, groupName, storageType);

		Message message = schedulerResponse.getMessage();

		message.put(
			SchedulerEngine.DESTINATION_NAME,
			schedulerResponse.getDestinationName());
		message.put(SchedulerEngine.GROUP_NAME, groupName);
		message.put(SchedulerEngine.JOB_NAME, jobName);
		message.put("companyId", companyId);

		_messageBus.sendMessage(
			schedulerResponse.getDestinationName(), message);
	}

	@Override
	public void schedule(
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			String description, String destination, Message message,
			StorageType storageType)
		throws SchedulerException {

		try {
			Trigger quartzTrigger = (Trigger)trigger.getWrappedTrigger();

			if (quartzTrigger == null) {
				return;
			}

			Scheduler scheduler = _getScheduler(storageType);

			description = _fixMaxLength(
				description, _descriptionMaxLength, storageType);

			message = message.clone();

			message.put(SchedulerEngine.GROUP_NAME, trigger.getGroupName());
			message.put(SchedulerEngine.JOB_NAME, trigger.getJobName());

			schedule(
				scheduler, storageType, quartzTrigger, description, destination,
				message);
		}
		catch (RuntimeException runtimeException) {
			if (PortalRunMode.isTestMode()) {
				StackTraceElement[] stackTraceElements =
					runtimeException.getStackTrace();

				for (StackTraceElement stackTraceElement : stackTraceElements) {
					String className = stackTraceElement.getClassName();

					if (className.contains(ServerDetector.class.getName())) {
						if (_log.isInfoEnabled()) {
							_log.info(runtimeException);
						}

						return;
					}

					throw new SchedulerException(
						"Unable to schedule job", runtimeException);
				}
			}
			else {
				throw new SchedulerException(
					"Unable to schedule job", runtimeException);
			}
		}
		catch (Exception exception) {
			throw new SchedulerException("Unable to schedule job", exception);
		}
	}

	@Override
	public void shutdown() throws SchedulerException {
		try {
			if (!_persistedScheduler.isInStandbyMode()) {
				_persistedScheduler.standby();
			}

			if (!_memoryScheduler.isInStandbyMode()) {
				_memoryScheduler.standby();
			}
		}
		catch (Exception exception) {
			throw new SchedulerException(
				"Unable to shutdown scheduler", exception);
		}
	}

	@Override
	public void start() throws SchedulerException {
		try {
			_persistedScheduler.start();

			initJobState();

			_memoryScheduler.start();
		}
		catch (Exception exception) {
			throw new SchedulerException(
				"Unable to start scheduler", exception);
		}
	}

	@Override
	public void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = _getScheduler(storageType);

			jobName = _fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = _fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			JobKey jobKey = new JobKey(jobName, groupName);

			unschedule(scheduler, jobKey);
		}
		catch (Exception exception) {
			throw new SchedulerException(
				StringBundler.concat(
					"Unable to unschedule job {jobName=", jobName,
					", groupName=", groupName, "}"),
				exception);
		}
	}

	@Override
	public void validateTrigger(
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			StorageType storageType)
		throws SchedulerException {

		Trigger quartzTrigger = (Trigger)trigger.getWrappedTrigger();

		if (quartzTrigger == null) {
			return;
		}

		Scheduler scheduler = _getScheduler(storageType);

		Calendar calendar = null;

		try {
			calendar = scheduler.getCalendar(quartzTrigger.getCalendarName());
		}
		catch (org.quartz.SchedulerException schedulerException) {
			throw new SchedulerException(
				"Unable to validate trigger \"" + quartzTrigger.getKey() + "\"",
				schedulerException);
		}

		List<Date> dates = TriggerUtils.computeFireTimes(
			(OperableTrigger)quartzTrigger, calendar, 1);

		if (!dates.isEmpty()) {
			return;
		}

		throw new SchedulerException(
			"Based on configured schedule, the given trigger \"" +
				quartzTrigger.getKey() + "\" will never fire.");
	}

	@Activate
	protected void activate() {
		_descriptionMaxLength = GetterUtil.getInteger(
			_props.get(PropsKeys.SCHEDULER_DESCRIPTION_MAX_LENGTH), 120);
		_groupNameMaxLength = GetterUtil.getInteger(
			_props.get(PropsKeys.SCHEDULER_GROUP_NAME_MAX_LENGTH), 80);
		_jobNameMaxLength = GetterUtil.getInteger(
			_props.get(PropsKeys.SCHEDULER_JOB_NAME_MAX_LENGTH), 80);

		_schedulerEngineEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.SCHEDULER_ENABLED));

		if (!_schedulerEngineEnabled) {
			return;
		}

		try {
			_persistedScheduler = _initializeScheduler(
				"persisted.scheduler.", true);

			_memoryScheduler = _initializeScheduler("memory.scheduler.", false);
		}
		catch (Exception exception) {
			_log.error("Unable to initialize engine", exception);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (!_schedulerEngineEnabled) {
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
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to deactivate scheduler", exception);
			}
		}
	}

	protected Message getMessage(JobDataMap jobDataMap) {
		String messageJSON = (String)jobDataMap.get(SchedulerEngine.MESSAGE);

		return (Message)_jsonFactory.deserialize(messageJSON);
	}

	protected SchedulerResponse getScheduledJob(
			Scheduler scheduler, JobKey jobKey)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		if (jobDetail == null) {
			return null;
		}

		SchedulerResponse schedulerResponse = new SchedulerResponse();

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		schedulerResponse.setDescription(
			jobDataMap.getString(SchedulerEngine.DESCRIPTION));
		schedulerResponse.setDestinationName(
			jobDataMap.getString(SchedulerEngine.DESTINATION_NAME));

		Message message = getMessage(jobDataMap);

		message.put(SchedulerEngine.JOB_STATE, _getJobState(jobDataMap));

		schedulerResponse.setMessage(message);

		schedulerResponse.setStorageType(
			StorageType.valueOf(
				jobDataMap.getString(SchedulerEngine.STORAGE_TYPE)));

		String jobName = jobKey.getName();
		String groupName = jobKey.getGroup();

		TriggerKey triggerKey = new TriggerKey(jobName, groupName);

		Trigger trigger = scheduler.getTrigger(triggerKey);

		if (trigger == null) {
			schedulerResponse.setGroupName(groupName);
			schedulerResponse.setJobName(jobName);

			return schedulerResponse;
		}

		message.put(SchedulerEngine.END_TIME, trigger.getEndTime());
		message.put(
			SchedulerEngine.FINAL_FIRE_TIME, trigger.getFinalFireTime());
		message.put(SchedulerEngine.NEXT_FIRE_TIME, trigger.getNextFireTime());
		message.put(
			SchedulerEngine.PREVIOUS_FIRE_TIME, trigger.getPreviousFireTime());
		message.put(SchedulerEngine.START_TIME, trigger.getStartTime());

		schedulerResponse.setTrigger(new QuartzTrigger(trigger));

		return schedulerResponse;
	}

	protected List<SchedulerResponse> getScheduledJobs(
			Scheduler scheduler, String groupName, StorageType storageType)
		throws Exception {

		groupName = _fixMaxLength(groupName, _groupNameMaxLength, storageType);

		List<SchedulerResponse> schedulerResponses = new ArrayList<>();

		Set<JobKey> jobKeys = scheduler.getJobKeys(
			GroupMatcher.jobGroupEquals(groupName));

		for (JobKey jobKey : jobKeys) {
			SchedulerResponse schedulerResponse = getScheduledJob(
				scheduler, jobKey);

			if ((schedulerResponse != null) &&
				((storageType == null) ||
				 (storageType == schedulerResponse.getStorageType()))) {

				schedulerResponses.add(schedulerResponse);
			}
		}

		return schedulerResponses;
	}

	protected void initJobState() throws Exception {
		List<String> groupNames = _persistedScheduler.getJobGroupNames();

		for (String groupName : groupNames) {
			Set<JobKey> jobKeys = _persistedScheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				Trigger trigger = _persistedScheduler.getTrigger(
					new TriggerKey(jobKey.getName(), jobKey.getGroup()));

				if (trigger != null) {
					continue;
				}

				if (_schedulerEngineHelper != null) {
					JobDetail jobDetail = _persistedScheduler.getJobDetail(
						jobKey);

					_schedulerEngineHelper.auditSchedulerJobs(
						getMessage(jobDetail.getJobDataMap()),
						TriggerState.EXPIRED);
				}

				_persistedScheduler.deleteJob(jobKey);
			}
		}
	}

	protected void schedule(
			Scheduler scheduler, StorageType storageType, Trigger trigger,
			String description, String destinationName, Message message)
		throws Exception {

		try {
			JobBuilder jobBuilder = JobBuilder.newJob(MessageSenderJob.class);

			jobBuilder.withIdentity(trigger.getJobKey());

			jobBuilder.storeDurably();

			JobDetail jobDetail = jobBuilder.build();

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(SchedulerEngine.DESCRIPTION, description);
			jobDataMap.put(SchedulerEngine.DESTINATION_NAME, destinationName);
			jobDataMap.put(
				SchedulerEngine.MESSAGE, _jsonFactory.serialize(message));
			jobDataMap.put(
				SchedulerEngine.STORAGE_TYPE, storageType.toString());

			JobState jobState = new JobState(TriggerState.NORMAL);

			jobDataMap.put(
				SchedulerEngine.JOB_STATE,
				JobStateSerializeUtil.serialize(jobState));

			try {
				scheduler.scheduleJob(jobDetail, trigger);
			}
			catch (JobPersistenceException jobPersistenceException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Scheduler job " + trigger.getJobKey() +
							" already exists",
						jobPersistenceException);
				}
			}
		}
		catch (ObjectAlreadyExistsException objectAlreadyExistsException) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Message is already scheduled",
					objectAlreadyExistsException);
			}
		}
	}

	protected void unschedule(Scheduler scheduler, JobKey jobKey)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		if (jobDetail == null) {
			return;
		}

		TriggerKey triggerKey = new TriggerKey(
			jobKey.getName(), jobKey.getGroup());

		Trigger trigger = scheduler.getTrigger(triggerKey);

		if (trigger == null) {
			return;
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		JobState jobState = _getJobState(jobDataMap);

		jobState.setTriggerDate(SchedulerEngine.END_TIME, new Date());
		jobState.setTriggerDate(
			SchedulerEngine.FINAL_FIRE_TIME, trigger.getPreviousFireTime());
		jobState.setTriggerDate(SchedulerEngine.NEXT_FIRE_TIME, null);
		jobState.setTriggerDate(
			SchedulerEngine.PREVIOUS_FIRE_TIME, trigger.getPreviousFireTime());
		jobState.setTriggerDate(
			SchedulerEngine.START_TIME, trigger.getStartTime());
		jobState.setTriggerState(TriggerState.UNSCHEDULED);

		jobDataMap.put(
			SchedulerEngine.JOB_STATE,
			JobStateSerializeUtil.serialize(jobState));

		scheduler.unscheduleJob(triggerKey);

		scheduler.addJob(jobDetail, true);
	}

	protected void update(
			Scheduler scheduler,
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			StorageType storageType)
		throws Exception {

		Trigger quartzTrigger = (Trigger)trigger.getWrappedTrigger();

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

			synchronized (this) {
				scheduler.deleteJob(jobKey);
				scheduler.scheduleJob(jobDetail, quartzTrigger);
			}

			_updateJobState(scheduler, jobKey, TriggerState.NORMAL);
		}
	}

	private String _fixMaxLength(
		String argument, int maxLength, StorageType storageType) {

		if ((argument == null) || (storageType != StorageType.PERSISTED)) {
			return argument;
		}

		if (argument.length() > maxLength) {
			argument = argument.substring(0, maxLength);
		}

		return argument;
	}

	private JobState _getJobState(JobDataMap jobDataMap) {
		Map<String, Object> jobStateMap = (Map<String, Object>)jobDataMap.get(
			SchedulerEngine.JOB_STATE);

		return JobStateSerializeUtil.deserialize(jobStateMap);
	}

	private Scheduler _getScheduler(StorageType storageType) {
		if (storageType == StorageType.PERSISTED) {
			return _persistedScheduler;
		}

		return _memoryScheduler;
	}

	private Scheduler _initializeScheduler(
			String propertiesPrefix, boolean useQuartzCluster)
		throws Exception {

		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

		Properties properties = _props.getProperties(propertiesPrefix, true);

		if (useQuartzCluster) {
			DB db = DBManagerUtil.getDB();

			DBType dbType = db.getDBType();

			if (dbType == DBType.SQLSERVER) {
				String lockHandlerClassName = properties.getProperty(
					"org.quartz.jobStore.lockHandler.class");

				if (Validator.isNull(lockHandlerClassName)) {
					properties.setProperty(
						"org.quartz.jobStore.lockHandler.class",
						UpdateLockRowSemaphore.class.getName());
				}
			}

			if (GetterUtil.getBoolean(
					_props.get(PropsKeys.CLUSTER_LINK_ENABLED))) {

				if (dbType == DBType.HYPERSONIC) {
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

		Scheduler scheduler = schedulerFactory.getScheduler();

		SchedulerContext schedulerContext = scheduler.getContext();

		schedulerContext.put("jSONFactory", _jsonFactory);
		schedulerContext.put("messageBus", _messageBus);

		ListenerManager listenerManager = scheduler.getListenerManager();

		listenerManager.addTriggerListener(new TriggerListenerSupportImpl());

		return scheduler;
	}

	private void _updateJobState(
			Scheduler scheduler, JobKey jobKey, TriggerState triggerState)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		if (jobDetail == null) {
			return;
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		JobState jobState = _getJobState(jobDataMap);

		if (triggerState != null) {
			jobState.setTriggerState(triggerState);
		}

		jobDataMap.put(
			SchedulerEngine.JOB_STATE,
			JobStateSerializeUtil.serialize(jobState));

		scheduler.addJob(jobDetail, true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		QuartzSchedulerEngine.class);

	private int _descriptionMaxLength;
	private int _groupNameMaxLength;
	private int _jobNameMaxLength;

	@Reference
	private JSONFactory _jsonFactory;

	private Scheduler _memoryScheduler;

	@Reference
	private MessageBus _messageBus;

	private Scheduler _persistedScheduler;

	@Reference
	private Props _props;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.portal.scheduler.quartz)(release.schema.version=1.0.0))"
	)
	private Release _release;

	private volatile boolean _schedulerEngineEnabled;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile SchedulerEngineHelper _schedulerEngineHelper;

	private class TriggerListenerSupportImpl extends TriggerListenerSupport {

		@Override
		public String getName() {
			return TriggerListenerSupportImpl.class.getName();
		}

		@Override
		public void triggerComplete(
			Trigger trigger, JobExecutionContext jobExecutionContext,
			Trigger.CompletedExecutionInstruction
				completedExecutionInstruction) {

			if (jobExecutionContext.getNextFireTime() != null) {
				return;
			}

			TriggerKey triggerKey = trigger.getKey();

			JobDetail jobDetail = jobExecutionContext.getJobDetail();

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			try {
				_schedulerEngineHelper.delete(
					triggerKey.getName(), triggerKey.getGroup(),
					StorageType.valueOf(
						jobDataMap.getString(SchedulerEngine.STORAGE_TYPE)));
			}
			catch (SchedulerException schedulerException) {
				_log.error(
					"Unable to delete job " + triggerKey, schedulerException);
			}
		}

		@Override
		public void triggerFired(
			Trigger trigger, JobExecutionContext jobExecutionContext) {

			JobDetail jobDetail = jobExecutionContext.getJobDetail();

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			Message message = new Message();

			message.setValues(new HashMap<>(jobDataMap.getWrappedMap()));

			try {
				_schedulerEngineHelper.auditSchedulerJobs(
					message, TriggerState.NORMAL);
			}
			catch (SchedulerException schedulerException) {
				_log.error(
					"Unable to send audit message for scheduler job " +
						trigger.getKey(),
					schedulerException);
			}
		}

	}

}