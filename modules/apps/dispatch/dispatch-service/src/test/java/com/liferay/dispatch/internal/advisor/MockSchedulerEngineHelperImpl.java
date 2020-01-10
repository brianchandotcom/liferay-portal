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

package com.liferay.dispatch.internal.advisor;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletRequest;

/**
 * @author Igor Beslic
 */
public class MockSchedulerEngineHelperImpl implements SchedulerEngineHelper {

	@Override
	public void addScriptingJob(
		Trigger trigger, StorageType storageType, String description,
		String language, String script, int exceptionsMaxSize) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void auditSchedulerJobs(Message message, TriggerState triggerState) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(SchedulerEntry schedulerEntry, StorageType storageType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String groupName, StorageType storageType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		String jobKey = _getJobKey(jobName, groupName, storageType.name());

		if (!_schedulerResponses.containsKey(jobKey)) {
			throw new SchedulerException("No such scheduled job configuration");
		}

		_schedulerResponses.remove(jobKey);
	}

	@Override
	public String getCronText(Calendar calendar, boolean timeZoneSensitive) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCronText(
		PortletRequest portletRequest, Calendar calendar,
		boolean timeZoneSensitive, int recurrenceType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Date getEndTime(SchedulerResponse schedulerResponse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getEndTime(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Date getFinalFireTime(SchedulerResponse schedulerResponse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getFinalFireTime(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectValuePair<Exception, Date>[] getJobExceptions(
		SchedulerResponse schedulerResponse) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ObjectValuePair<Exception, Date>[] getJobExceptions(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public TriggerState getJobState(SchedulerResponse schedulerResponse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TriggerState getJobState(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Date getNextFireTime(SchedulerResponse schedulerResponse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getNextFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		String jobKey = _getJobKey(jobName, groupName, storageType.name());

		if (!_schedulerResponses.containsKey(jobKey)) {
			throw new SchedulerException("No such scheduled job configuration");
		}

		SchedulerResponse schedulerResponse = _schedulerResponses.get(jobKey);

		Trigger trigger = schedulerResponse.getTrigger();

		return trigger.getFireDateAfter(trigger.getStartDate());
	}

	@Override
	public Date getPreviousFireTime(SchedulerResponse schedulerResponse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getPreviousFireTime(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		String jobKey = _getJobKey(jobName, groupName, storageType.name());

		if (!_schedulerResponses.containsKey(jobKey)) {
			throw new SchedulerException("No such scheduled job configuration");
		}

		SchedulerResponse schedulerResponse = _schedulerResponses.get(jobKey);

		MockTriggerFactoryImpl.MockTrigger trigger =
			(MockTriggerFactoryImpl.MockTrigger)schedulerResponse.getTrigger();

		return trigger.getFireDateBefore(trigger.getEndDate());
	}

	@Override
	public SchedulerResponse getScheduledJob(
		String jobName, String groupName, StorageType storageType) {

		return _schedulerResponses.get(
			_getJobKey(jobName, groupName, storageType.name()));
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(StorageType storageType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SchedulerResponse> getScheduledJobs(
		String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Date getStartTime(SchedulerResponse schedulerResponse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getStartTime(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void pause(String groupName, StorageType storageType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void pause(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void register(
		MessageListener messageListener, SchedulerEntry schedulerEntry,
		String destinationName) {
	}

	@Override
	public void resume(String groupName, StorageType storageType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resume(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void schedule(
		Trigger trigger, StorageType storageType, String description,
		String destinationName, Message message, int exceptionsMaxSize) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void schedule(
		Trigger trigger, StorageType storageType, String description,
		String destinationName, Object payload, int exceptionsMaxSize) {

		SchedulerResponse schedulerResponse = new SchedulerResponse();

		schedulerResponse.setGroupName(trigger.getGroupName());
		schedulerResponse.setJobName(trigger.getJobName());
		schedulerResponse.setStorageType(storageType);
		schedulerResponse.setTrigger(trigger);

		_schedulerResponses.put(
			_getJobKey(
				trigger.getJobName(), trigger.getGroupName(),
				storageType.name()),
			schedulerResponse);
	}

	@Override
	public void shutdown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void start() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void suppressError(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void unregister(MessageListener messageListener) {
	}

	@Override
	public void unschedule(
		SchedulerEntry schedulerEntry, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void unschedule(String groupName, StorageType storageType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void unschedule(
		String jobName, String groupName, StorageType storageType) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void update(
		String jobName, String groupName, StorageType storageType,
		String description, String language, String script,
		int exceptionsMaxSize) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Trigger trigger, StorageType storageType) {
		throw new UnsupportedOperationException();
	}

	private String _getJobKey(
		String jobName, String groupName, String storageName) {

		StringBundler sb = new StringBundler(3);

		sb.append(groupName);
		sb.append(jobName);
		sb.append(storageName);

		return sb.toString();
	}

	private final Map<String, SchedulerResponse> _schedulerResponses =
		new ConcurrentHashMap<>();

}