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
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.scheduler.JobStateSerializeUtil;
import com.liferay.portal.scheduler.quartz.QuartzTrigger;
import com.liferay.portal.scheduler.quartz.internal.job.MessageSenderJob;

import java.util.ArrayList;
import java.util.Date;
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

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
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
 * @author Edward C. Han
 */
@Component(
	enabled = false, immediate = true,
	service = {QuartzSchedulerEngine.class, SchedulerEngine.class}
)
public class QuartzSchedulerEngine implements SchedulerEngine {

	@Override
	public void delete(String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			Set<JobKey> jobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				scheduler.deleteJob(jobKey);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete jobs in group " + groupName, e);
		}
	}

	@Override
	public void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			jobName = fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			JobKey jobKey = new JobKey(jobName, groupName);

			scheduler.deleteJob(jobKey);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
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
			Scheduler scheduler = getScheduler(storageType);

			jobName = fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

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
		catch (Exception e) {
			throw new SchedulerException("Unable to get jobs", e);
		}
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			List<String> groupNames = scheduler.getJobGroupNames();

			List<SchedulerResponse> schedulerResponses = new ArrayList<>();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(
					getScheduledJobs(scheduler, groupName, storageType));
			}

			return schedulerResponses;
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to get jobs with type " + storageType, e);
		}
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			return getScheduledJobs(scheduler, groupName, storageType);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to get jobs in group " + groupName, e);
		}
	}

	@Override
	public void pause(String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			Set<JobKey> jobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			scheduler.pauseJobs(GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				updateJobState(scheduler, jobKey, TriggerState.PAUSED, false);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to pause jobs in group " + groupName, e);
		}
	}

	@Override
	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			jobName = fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			JobKey jobKey = new JobKey(jobName, groupName);

			scheduler.pauseJob(jobKey);

			updateJobState(scheduler, jobKey, TriggerState.PAUSED, false);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to pause job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}
	}

	@Override
	public void resume(String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			Set<JobKey> jobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals(groupName));

			scheduler.resumeJobs(GroupMatcher.jobGroupEquals(groupName));

			for (JobKey jobKey : jobKeys) {
				updateJobState(scheduler, jobKey, TriggerState.NORMAL, false);
			}
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to resume jobs in group " + groupName, e);
		}
	}

	@Override
	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			jobName = fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

			JobKey jobKey = new JobKey(jobName, groupName);

			scheduler.resumeJob(jobKey);

			updateJobState(scheduler, jobKey, TriggerState.NORMAL, false);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to resume job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}
	}

	@Override
	public void schedule(
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			String description, String destination, Message message,
			StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			Trigger quartzTrigger = (Trigger)trigger.getWrappedTrigger();

			if (quartzTrigger == null) {
				return;
			}

			description = fixMaxLength(
				description, _descriptionMaxLength, storageType);

			message = message.clone();

			message.put(SchedulerEngine.GROUP_NAME, trigger.getGroupName());
			message.put(SchedulerEngine.JOB_NAME, trigger.getJobName());

			schedule(
				scheduler, storageType, quartzTrigger, description, destination,
				message);
		}
		catch (RuntimeException re) {
			if (PortalRunMode.isTestMode()) {
				StackTraceElement[] stackTraceElements = re.getStackTrace();

				for (StackTraceElement stackTraceElement : stackTraceElements) {
					String className = stackTraceElement.getClassName();

					if (className.contains(ServerDetector.class.getName())) {
						if (_log.isInfoEnabled()) {
							_log.info(re, re);
						}

						return;
					}

					throw new SchedulerException("Unable to schedule job", re);
				}
			}
			else {
				throw new SchedulerException("Unable to schedule job", re);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to schedule job", e);
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
		catch (Exception e) {
			throw new SchedulerException("Unable to shutdown scheduler", e);
		}
	}

	@Override
	public void start() throws SchedulerException {
		try {
			_persistedScheduler.start();

			initJobState();

			_memoryScheduler.start();
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to start scheduler", e);
		}
	}

	@Override
	public void suppressError(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			jobName = fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

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

	@Override
	public void unschedule(String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

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

	@Override
	public void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			jobName = fixMaxLength(jobName, _jobNameMaxLength, storageType);
			groupName = fixMaxLength(
				groupName, _groupNameMaxLength, storageType);

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

	@Override
	public void update(
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			StorageType storageType)
		throws SchedulerException {

		try {
			Scheduler scheduler = getScheduler(storageType);

			update(scheduler, trigger, storageType);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to update trigger", e);
		}
	}

	@Activate
	protected void activate() {
		_schedulerEngineEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.SCHEDULER_ENABLED));

		if (!_schedulerEngineEnabled) {
			return;
		}

		try {
			_persistedScheduler = initializeScheduler(
				"persisted.scheduler.", true);

			_memoryScheduler = initializeScheduler("memory.scheduler.", false);
		}
		catch (Exception e) {
			_log.error("Unable to initialize engine", e);
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
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to deactivate scheduler", e);
			}
		}
	}

	protected String fixMaxLength(
		String argument, int maxLength, StorageType storageType) {

		if ((argument == null) || (storageType != StorageType.PERSISTED)) {
			return argument;
		}

		if (argument.length() > maxLength) {
			argument = argument.substring(0, maxLength);
		}

		return argument;
	}

	protected JobState getJobState(JobDataMap jobDataMap) {
		Map<String, Object> jobStateMap = (Map<String, Object>)jobDataMap.get(
			SchedulerEngine.JOB_STATE);

		return JobStateSerializeUtil.deserialize(jobStateMap);
	}

	protected Message getMessage(JobDataMap jobDataMap) {
		String messageJSON = (String)jobDataMap.get(SchedulerEngine.MESSAGE);

		if (_jsonFactory == null) {
			throw new IllegalStateException("JSON factory not initialized");
		}

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

		String description = jobDataMap.getString(SchedulerEngine.DESCRIPTION);

		schedulerResponse.setDescription(description);

		String destinationName = jobDataMap.getString(
			SchedulerEngine.DESTINATION_NAME);

		schedulerResponse.setDestinationName(destinationName);

		Message message = getMessage(jobDataMap);

		JobState jobState = getJobState(jobDataMap);

		message.put(SchedulerEngine.JOB_STATE, jobState);

		schedulerResponse.setMessage(message);

		StorageType storageType = StorageType.valueOf(
			jobDataMap.getString(SchedulerEngine.STORAGE_TYPE));

		schedulerResponse.setStorageType(storageType);

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

		groupName = fixMaxLength(groupName, _groupNameMaxLength, storageType);

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

	protected Scheduler getScheduler(StorageType storageType) {
		if (storageType == StorageType.PERSISTED) {
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

				if (_schedulerEngineHelper != null) {
					_schedulerEngineHelper.auditSchedulerJobs(
						message, TriggerState.EXPIRED);
				}

				_persistedScheduler.deleteJob(jobKey);
			}
		}
	}

	protected void schedule(
			Scheduler scheduler, StorageType storageType, Trigger trigger,
			String description, String destinationName, Message message)
		throws Exception {

		if (_jsonFactory == null) {
			throw new IllegalStateException("JSON factory not initialized");
		}

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

			JobState jobState = new JobState(
				TriggerState.NORMAL,
				message.getInteger(SchedulerEngine.EXCEPTIONS_MAX_SIZE));

			jobDataMap.put(
				SchedulerEngine.JOB_STATE,
				JobStateSerializeUtil.serialize(jobState));

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

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setJsonFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;

		_descriptionMaxLength = GetterUtil.getInteger(
			_props.get(PropsKeys.SCHEDULER_DESCRIPTION_MAX_LENGTH), 120);

		_groupNameMaxLength = GetterUtil.getInteger(
			_props.get(PropsKeys.SCHEDULER_GROUP_NAME_MAX_LENGTH), 80);

		_jobNameMaxLength = GetterUtil.getInteger(
			_props.get(PropsKeys.SCHEDULER_JOB_NAME_MAX_LENGTH), 80);
	}

	@Reference(unbind = "-")
	protected void setQuartzTriggerFactory(
		QuartzTriggerFactory quartzTriggerFactory) {

		_quartzTriggerFactory = quartzTriggerFactory;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.portal.scheduler.quartz)(release.schema.version=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	protected void unschedule(Scheduler scheduler, JobKey jobKey)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		TriggerKey triggerKey = new TriggerKey(
			jobKey.getName(), jobKey.getGroup());

		if (jobDetail == null) {
			return;
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		JobState jobState = getJobState(jobDataMap);

		Trigger trigger = scheduler.getTrigger(triggerKey);

		if (trigger == null) {
			return;
		}

		jobState.setTriggerDate(SchedulerEngine.END_TIME, new Date());
		jobState.setTriggerDate(
			SchedulerEngine.FINAL_FIRE_TIME, trigger.getPreviousFireTime());
		jobState.setTriggerDate(SchedulerEngine.NEXT_FIRE_TIME, null);
		jobState.setTriggerDate(
			SchedulerEngine.PREVIOUS_FIRE_TIME, trigger.getPreviousFireTime());
		jobState.setTriggerDate(
			SchedulerEngine.START_TIME, trigger.getStartTime());

		jobState.setTriggerState(TriggerState.UNSCHEDULED);

		jobState.clearExceptions();

		jobDataMap.put(
			SchedulerEngine.JOB_STATE,
			JobStateSerializeUtil.serialize(jobState));

		scheduler.unscheduleJob(triggerKey);

		scheduler.addJob(jobDetail, true);
	}

	protected void unsetJsonFactory(JSONFactory jsonFactory) {
		_jsonFactory = null;
	}

	protected void unsetPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = null;
	}

	protected void unsetSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = null;
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

			updateJobState(scheduler, jobKey, TriggerState.NORMAL, true);
		}
	}

	protected void updateJobState(
			Scheduler scheduler, JobKey jobKey, TriggerState triggerState,
			boolean suppressError)
		throws Exception {

		JobDetail jobDetail = scheduler.getJobDetail(jobKey);

		if (jobDetail == null) {
			return;
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		JobState jobState = getJobState(jobDataMap);

		if (triggerState != null) {
			jobState.setTriggerState(triggerState);
		}

		if (suppressError) {
			jobState.clearExceptions();
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
	private JSONFactory _jsonFactory;
	private Scheduler _memoryScheduler;
	private MessageBus _messageBus;
	private Scheduler _persistedScheduler;
	private PortletLocalService _portletLocalService;
	private Props _props;
	private QuartzTriggerFactory _quartzTriggerFactory;
	private volatile boolean _schedulerEngineEnabled;
	private SchedulerEngineHelper _schedulerEngineHelper;

}