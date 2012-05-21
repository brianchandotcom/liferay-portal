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

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.RecurrenceSerializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 * @author Raymond Augé
 */
public class SchedulerEngineUtil {

	public static void addJob(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message,
			String messageListenerClassName, String portletId,
			int exceptionsMaxSize)
		throws SchedulerException {

		getInstance()._addJob(
			trigger, storageType, description, destinationName, message,
			messageListenerClassName, portletId, exceptionsMaxSize);
	}

	public static void addJob(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload,
			String messageListenerClassName, String portletId,
			int exceptionsMaxSize)
		throws SchedulerException {

		getInstance()._addJob(
			trigger, storageType, description, destinationName, payload,
			messageListenerClassName, portletId, exceptionsMaxSize);
	}

	public static void addScriptingJob(
			Trigger trigger, StorageType storageType, String description,
			String language, String script, int exceptionsMaxSize)
		throws SchedulerException {

		getInstance()._addScriptingJob(
			trigger, storageType, description, language, script,
			exceptionsMaxSize);
	}

	public static void auditSchedulerJobs(
			Message message, TriggerState triggerState)
		throws SchedulerException {

		getInstance()._auditSchedulerJobs(message, triggerState);
	}

	public static void delete(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		getInstance()._delete(schedulerEntry, storageType);
	}

	public static void delete(String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._delete(groupName, storageType);
	}

	public static void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._delete(jobName, groupName, storageType);
	}

	public static String getCronText(
		Calendar calendar, boolean timeZoneSensitive) {

		return getInstance()._getCronText(calendar, timeZoneSensitive);
	}

	public static String getCronText(
		PortletRequest portletRequest, Calendar calendar,
		boolean timeZoneSensitive, int recurrenceType) {

		return getInstance()._getCronText(
			portletRequest, calendar, timeZoneSensitive, recurrenceType);
	}

	public static Date getEndTime(SchedulerResponse schedulerResponse) {
		return getInstance()._getEndTime(schedulerResponse);
	}

	public static Date getEndTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getEndTime(jobName, groupName, storageType);
	}

	public static Date getFinalFireTime(SchedulerResponse schedulerResponse) {
		return getInstance()._getFinalFireTime(schedulerResponse);
	}

	public static Date getFinalFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getFinalFireTime(jobName, groupName, storageType);
	}

	public static SchedulerEngineUtil getInstance() {
		PortalRuntimePermission.checkGetBeanProperty(SchedulerEngineUtil.class);

		return _instance;
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
		SchedulerResponse schedulerResponse) {

		return getInstance()._getJobExceptions(schedulerResponse);
	}

	public static ObjectValuePair<Exception, Date>[] getJobExceptions(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getJobExceptions(jobName, groupName, storageType);
	}

	public static TriggerState getJobState(
		SchedulerResponse schedulerResponse) {

		return getInstance()._getJobState(schedulerResponse);
	}

	public static TriggerState getJobState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getJobState(jobName, groupName, storageType);
	}

	public static Date getNextFireTime(SchedulerResponse schedulerResponse) {
		return getInstance()._getNextFireTime(schedulerResponse);
	}

	public static Date getNextFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getNextFireTime(jobName, groupName, storageType);
	}

	public static Date getPreviousFireTime(
		SchedulerResponse schedulerResponse) {

		return getInstance()._getPreviousFireTime(schedulerResponse);
	}

	public static Date getPreviousFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getPreviousFireTime(
			jobName, groupName, storageType);
	}

	public static SchedulerResponse getScheduledJob(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getScheduledJob(jobName, groupName, storageType);
	}

	public static List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return getInstance()._getScheduledJobs();
	}

	public static List<SchedulerResponse> getScheduledJobs(
			StorageType storageType)
		throws SchedulerException {

		return getInstance()._getScheduledJobs(storageType);
	}

	public static List<SchedulerResponse> getScheduledJobs(
			String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getScheduledJobs(groupName, storageType);
	}

	public static Date getStartTime(SchedulerResponse schedulerResponse) {
		return getInstance()._getStartTime(schedulerResponse);
	}

	public static Date getStartTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return getInstance()._getStartTime(jobName, groupName, storageType);
	}

	public static void initialize() throws SchedulerException {
		getInstance()._initialize();

		SchedulerLifecycle schedulerLifecycle = new SchedulerLifecycle();

		schedulerLifecycle.registerPortalLifecycle(PortalLifecycle.METHOD_INIT);
	}

	public static String namespaceGroupName(
		String groupName, StorageType storageType) {

		return getInstance()._namespaceGroupName(groupName, storageType);
	}

	public static void pause(String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._pause(groupName, storageType);
	}

	public static void pause(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._pause(jobName, groupName, storageType);
	}

	public static void resume(String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._resume(groupName, storageType);
	}

	public static void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._resume(jobName, groupName, storageType);
	}

	public static void schedule(
			SchedulerEntry schedulerEntry, StorageType storageType,
			String portletId, int exceptionsMaxSize)
		throws SchedulerException {

		getInstance()._schedule(
			schedulerEntry, storageType, portletId, exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message, int exceptionsMaxSize)
		throws SchedulerException {

		getInstance()._schedule(
			trigger, storageType, description, destinationName, message,
			exceptionsMaxSize);
	}

	public static void schedule(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload, int exceptionsMaxSize)
		throws SchedulerException {

		getInstance()._schedule(
			trigger, storageType, description, destinationName, payload,
			exceptionsMaxSize);
	}

	public static void shutdown() throws SchedulerException {
		getInstance()._shutdown();
	}

	public static void start() throws SchedulerException {
		getInstance()._start();
	}

	public static void suppressError(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._suppressError(jobName, groupName, storageType);
	}

	public static void unschedule(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		getInstance()._unschedule(schedulerEntry, storageType);
	}

	public static void unschedule(String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._unschedule(groupName, storageType);
	}

	public static void unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		getInstance()._unschedule(jobName, groupName, storageType);
	}

	public static void update(
			String jobName, String groupName, StorageType storageType,
			String description, String language, String script,
			int exceptionsMaxSize)
		throws SchedulerException {

		getInstance()._update(
			jobName, groupName, storageType, description, language, script,
			exceptionsMaxSize);
	}

	public static void update(Trigger trigger, StorageType storageType)
		throws SchedulerException {

		getInstance()._update(trigger, storageType);
	}

	public static void updateMemorySchedulerClusterMaster()
		throws SchedulerException {

		getInstance()._updateMemorySchedulerClusterMaster();
	}

	public void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_instance._schedulerEngine = schedulerEngine;

		if (schedulerEngine instanceof SchedulerEngineClusterManager) {
			_instance._schedulerEngineClusterManager =
				(SchedulerEngineClusterManager)schedulerEngine;
		}
	}

	private void _addJob(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Message message,
			String messageListenerClassName, String portletId,
			int exceptionsMaxSize)
		throws SchedulerException {

		if (message == null) {
			message = new Message();
		}

		message.put(
			SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME,
			messageListenerClassName);
		message.put(SchedulerEngine.PORTLET_ID, portletId);

		_schedule(
			trigger, storageType, description, destinationName, message,
			exceptionsMaxSize);
	}

	private void _addJob(
			Trigger trigger, StorageType storageType, String description,
			String destinationName, Object payload,
			String messageListenerClassName, String portletId,
			int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.setPayload(payload);

		_addJob(
			trigger, storageType, description, destinationName, message,
			messageListenerClassName, portletId, exceptionsMaxSize);
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

	private void _addWeeklyDayPos(
		PortletRequest portletRequest, List<DayAndPosition> list, int day) {

		if (ParamUtil.getBoolean(portletRequest, "weeklyDayPos" + day)) {
			list.add(new DayAndPosition(day, 0));
		}
	}

	private void _auditSchedulerJobs(Message message, TriggerState triggerState)
		throws SchedulerException {

		try {
			AuditMessage auditMessage = new AuditMessage(
				SchedulerEngine.AUDIT_ACTION, CompanyConstants.SYSTEM, 0,
				StringPool.BLANK, SchedulerEngine.class.getName(), "0",
				triggerState.toString(), new Date(),
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(message)));

			auditMessage.setServerName(InetAddressUtil.getLocalHostName());
			auditMessage.setServerPort(PortalUtil.getPortalPort(false));

			AuditRouterUtil.route(auditMessage);
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	private void _delete(SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		Trigger trigger = schedulerEntry.getTrigger();

		_delete(trigger.getJobName(), trigger.getGroupName(), storageType);
	}

	private void _delete(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.delete(_namespaceGroupName(groupName, storageType));
	}

	private void _delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.delete(
			jobName, _namespaceGroupName(groupName, storageType));
	}

	private String _getCronText(Calendar calendar, boolean timeZoneSensitive) {
		return _getCronText(
			null, calendar, timeZoneSensitive, Recurrence.NO_RECURRENCE);
	}

	private String _getCronText(
		PortletRequest portletRequest, Calendar calendar,
		boolean timeZoneSensitive, int recurrenceType) {

		Calendar recurrenceCalendar = null;

		if (timeZoneSensitive) {
			recurrenceCalendar = CalendarFactoryUtil.getCalendar();

			recurrenceCalendar.setTime(calendar.getTime());
		}
		else {
			recurrenceCalendar = (Calendar)calendar.clone();
		}

		Recurrence recurrence = new Recurrence(
			recurrenceCalendar, new Duration(1, 0, 0, 0), recurrenceType);

		recurrence.setWeekStart(Calendar.SUNDAY);

		if (recurrenceType == Recurrence.DAILY) {
			int dailyType = ParamUtil.getInteger(portletRequest, "dailyType");

			if (dailyType == 0) {
				int dailyInterval = ParamUtil.getInteger(
					portletRequest, "dailyInterval", 1);

				recurrence.setInterval(dailyInterval);
			}
			else {
				DayAndPosition[] dayPos = {
					new DayAndPosition(Calendar.MONDAY, 0),
					new DayAndPosition(Calendar.TUESDAY, 0),
					new DayAndPosition(Calendar.WEDNESDAY, 0),
					new DayAndPosition(Calendar.THURSDAY, 0),
					new DayAndPosition(Calendar.FRIDAY, 0)};

				recurrence.setByDay(dayPos);
			}
		}
		else if (recurrenceType == Recurrence.WEEKLY) {
			int weeklyInterval = ParamUtil.getInteger(
				portletRequest, "weeklyInterval", 1);

			recurrence.setInterval(weeklyInterval);

			List<DayAndPosition> dayPos = new ArrayList<DayAndPosition>();

			_addWeeklyDayPos(portletRequest, dayPos, Calendar.SUNDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.MONDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.TUESDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.WEDNESDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.THURSDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.FRIDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.SATURDAY);

			if (dayPos.size() == 0) {
				dayPos.add(new DayAndPosition(Calendar.MONDAY, 0));
			}

			recurrence.setByDay(dayPos.toArray(new DayAndPosition[0]));
		}
		else if (recurrenceType == Recurrence.MONTHLY) {
			int monthlyType = ParamUtil.getInteger(
				portletRequest, "monthlyType");

			if (monthlyType == 0) {
				int monthlyDay = ParamUtil.getInteger(
					portletRequest, "monthlyDay0", 1);

				recurrence.setByMonthDay(new int[] {monthlyDay});

				int monthlyInterval = ParamUtil.getInteger(
					portletRequest, "monthlyInterval0", 1);

				recurrence.setInterval(monthlyInterval);
			}
			else {
				int monthlyPos = ParamUtil.getInteger(
					portletRequest, "monthlyPos");
				int monthlyDay = ParamUtil.getInteger(
					portletRequest, "monthlyDay1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(monthlyDay, monthlyPos)};

				recurrence.setByDay(dayPos);

				int monthlyInterval = ParamUtil.getInteger(
					portletRequest, "monthlyInterval1", 1);

				recurrence.setInterval(monthlyInterval);
			}
		}
		else if (recurrenceType == Recurrence.YEARLY) {
			int yearlyType = ParamUtil.getInteger(portletRequest, "yearlyType");

			if (yearlyType == 0) {
				int yearlyMonth = ParamUtil.getInteger(
					portletRequest, "yearlyMonth0");
				int yearlyDay = ParamUtil.getInteger(
					portletRequest, "yearlyDay0", 1);

				recurrence.setByMonth(new int[] {yearlyMonth});
				recurrence.setByMonthDay(new int[] {yearlyDay});

				int yearlyInterval = ParamUtil.getInteger(
					portletRequest, "yearlyInterval0", 1);

				recurrence.setInterval(yearlyInterval);
			}
			else {
				int yearlyPos = ParamUtil.getInteger(
					portletRequest, "yearlyPos");
				int yearlyDay = ParamUtil.getInteger(
					portletRequest, "yearlyDay1");
				int yearlyMonth = ParamUtil.getInteger(
					portletRequest, "yearlyMonth1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(yearlyDay, yearlyPos)};

				recurrence.setByDay(dayPos);

				recurrence.setByMonth(new int[] {yearlyMonth});

				int yearlyInterval = ParamUtil.getInteger(
					portletRequest, "yearlyInterval1", 1);

				recurrence.setInterval(yearlyInterval);
			}
		}

		return RecurrenceSerializer.toCronText(recurrence);
	}

	private Date _getEndTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.END_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.END_TIME);
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

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.FINAL_FIRE_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.FINAL_FIRE_TIME);
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

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.NEXT_FIRE_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.NEXT_FIRE_TIME);
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

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.PREVIOUS_FIRE_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.PREVIOUS_FIRE_TIME);
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

	private Date _getStartTime(SchedulerResponse schedulerResponse) {
		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		if (triggerState.equals(TriggerState.NORMAL) ||
			triggerState.equals(TriggerState.PAUSED)) {

			return (Date)message.get(SchedulerEngine.START_TIME);
		}
		else {
			return jobState.getTriggerDate(SchedulerEngine.START_TIME);
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

	private void _initialize() throws SchedulerException {
		if (_schedulerEngineClusterManager != null) {
			_schedulerEngineClusterManager.initialize();
		}
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
			String portletId, int exceptionsMaxSize)
		throws SchedulerException {

		Message message = new Message();

		message.put(
			SchedulerEngine.CONTEXT_PATH, schedulerEntry.getContextPath());
		message.put(
			SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME,
			schedulerEntry.getEventListenerClass());
		message.put(SchedulerEngine.PORTLET_ID, portletId);

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

	private void _unschedule(
			SchedulerEntry schedulerEntry, StorageType storageType)
		throws SchedulerException {

		Trigger trigger = schedulerEntry.getTrigger();

		_unschedule(trigger.getJobName(), trigger.getGroupName(), storageType);
	}

	private void _unschedule(String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngine.unschedule(
			_namespaceGroupName(groupName, storageType));
	}

	private void _unschedule(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

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