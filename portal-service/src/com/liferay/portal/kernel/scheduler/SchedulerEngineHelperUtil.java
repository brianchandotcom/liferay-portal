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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Michael C. Han
 */
public class SchedulerEngineHelperUtil {
	public static void addJob(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message,
			String messageListenerClassName, String portletId,
			int exceptionsMaxSize)
		throws SchedulerException {

		_schedulerEngineHelper.addJob(
			trigger, storageType, description, destinationName, message,
			messageListenerClassName, portletId, exceptionsMaxSize);
	}

	public static void addJob(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload,
			String messageListenerClassName, String portletId,
			int exceptionsMaxSize)
		throws SchedulerException {

		_schedulerEngineHelper.addJob(
			trigger, storageType, description, destinationName, payload,
			messageListenerClassName, portletId, exceptionsMaxSize);
	}

	public static void addScriptingJob(
			Trigger trigger, StorageType storageType, String description,
			String language, String script, int exceptionsMaxSize)
		throws SchedulerException {

		_schedulerEngineHelper.addScriptingJob(
			trigger, storageType, description, language, script,
			exceptionsMaxSize);
	}

	public static void auditSchedulerJobs(
			Message message, TriggerState triggerState)
		throws SchedulerException {

		_schedulerEngineHelper.auditSchedulerJobs(message, triggerState);
	}

	public static void delete(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.delete(schedulerEntry, storageType);
	}

	public static void delete(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.delete(groupName, storageType);
	}

	public static void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.delete(jobName, groupName, storageType);
	}

	public static String getCronText(
		Calendar calendar, boolean timeZoneSensitive) {

		return _schedulerEngineHelper.getCronText(calendar, timeZoneSensitive);
	}

	public static String getCronText(
		PortletRequest portletRequest, Calendar calendar,
		boolean timeZoneSensitive, int recurrenceType) {

		return _schedulerEngineHelper.getCronText(
			portletRequest, calendar, timeZoneSensitive, recurrenceType);
	}

	public static Date getEndTime(SchedulerResponse schedulerResponse) {
		return _schedulerEngineHelper.getEndTime(schedulerResponse);
	}

	public static Date getEndTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getEndTime(jobName, groupName, storageType);
	}

	public static Date getFinalFireTime(SchedulerResponse schedulerResponse) {
		return _schedulerEngineHelper.getFinalFireTime(schedulerResponse);
	}

	public static Date getFinalFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getFinalFireTime(jobName, groupName, storageType);
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
		SchedulerResponse schedulerResponse) {

		return _schedulerEngineHelper.getJobExceptions(schedulerResponse);
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getJobExceptions(jobName, groupName, storageType);
	}

	public static TriggerState getJobState(
		SchedulerResponse schedulerResponse) {

		return _schedulerEngineHelper.getJobState(schedulerResponse);
	}

	public static TriggerState getJobState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getJobState(jobName, groupName, storageType);
	}

	public static Date getNextFireTime(SchedulerResponse schedulerResponse) {
		return _schedulerEngineHelper.getNextFireTime(schedulerResponse);
	}

	public static Date getNextFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getNextFireTime(jobName, groupName, storageType);
	}

	public static Date getPreviousFireTime(
		SchedulerResponse schedulerResponse) {

		return _schedulerEngineHelper.getPreviousFireTime(schedulerResponse);
	}

	public static Date getPreviousFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getPreviousFireTime(
			jobName, groupName, storageType);
	}

	public static SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJob(jobName, groupName, storageType);
	}

	public static List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJobs();
	}

	public static List<SchedulerResponse> getScheduledJobs(
			StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJobs(storageType);
	}

	public static List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getScheduledJobs(groupName, storageType);
	}

	public static Date getStartTime(SchedulerResponse schedulerResponse) {
		return _schedulerEngineHelper.getStartTime(schedulerResponse);
	}

	public static Date getStartTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getStartTime(jobName, groupName, storageType);
	}

	public static void initialize() throws SchedulerException {
		_schedulerEngineHelper.initialize();
	}

	public static String namespaceGroupName(
		String groupName, StorageType storageType) {

		return _schedulerEngineHelper.namespaceGroupName(groupName, storageType);
	}

	public static void pause(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.pause(groupName, storageType);
	}

	public static void pause(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.pause(jobName, groupName, storageType);
	}

	public static void resume(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.resume(groupName, storageType);
	}

	public static void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.resume(jobName, groupName, storageType);
	}

	public static void schedule(
			SchedulerEntry schedulerEntry, StorageType storageType,
			String portletId, int exceptionsMaxSize)
		throws SchedulerException {

		_schedulerEngineHelper.schedule(
			schedulerEntry, storageType, portletId, exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message, int exceptionsMaxSize)
		throws SchedulerException {

		_schedulerEngineHelper.schedule(
			trigger, storageType, description, destinationName, message,
			exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload, int exceptionsMaxSize)
		throws SchedulerException {

		_schedulerEngineHelper.schedule(
			trigger, storageType, description, destinationName, payload,
			exceptionsMaxSize);
	}

	public static void shutdown() throws SchedulerException {
		_schedulerEngineHelper.shutdown();
	}

	public static void start() throws SchedulerException {
		_schedulerEngineHelper.start();
	}

	public static void suppressError(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.suppressError(jobName, groupName, storageType);
	}

	public static void unschedule(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.unschedule(schedulerEntry, storageType);
	}

	public static void unschedule(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.unschedule(groupName, storageType);
	}

	public static void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.unschedule(jobName, groupName, storageType);
	}

	public static void update(
			String jobName, String groupName, StorageType storageType,
			String description, String language, String script,
			int exceptionsMaxSize)
		throws SchedulerException {

		_schedulerEngineHelper.update(
			jobName, groupName, storageType, description, language, script,
			exceptionsMaxSize);
	}

	public static void update(Trigger trigger, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.update(trigger, storageType);
	}

	public static void updateMemorySchedulerClusterMaster()
		throws SchedulerException {

		_schedulerEngineHelper.updateMemorySchedulerClusterMaster();
	}

	public void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	private static SchedulerEngineHelper _schedulerEngineHelper;
}