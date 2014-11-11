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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DefaultMessageBus;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.JobStateSerializeUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.scheduler.SchedulerEngineHelperImpl;
import com.liferay.portal.scheduler.job.MessageSenderJob;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvMethodRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.quartz.Calendar;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class QuartzSchedulerEngineTest {

	@Before
	public void setUp() throws SchedulerException {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		Thread currentThread = Thread.currentThread();

		ClassLoader currentClassLoader = currentThread.getContextClassLoader();

		PortalClassLoaderUtil.setClassLoader(currentClassLoader);

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		PropsUtil.setProps(new PropsImpl());

		ClassLoaderPool.register(_TEST_PORTLET_ID, currentClassLoader);

		MessageBusUtil.init(new DefaultMessageBus(), null, null);

		_testDestination = new SynchronousDestination();

		_testDestination.setName(_TEST_DESTINATION_NAME);

		MessageBusUtil.addDestination(_testDestination);

		_quartzSchedulerEngine = new QuartzSchedulerEngine();

		ReflectionTestUtil.setFieldValue(
			_quartzSchedulerEngine, "_memoryScheduler",
			new MockScheduler(StorageType.MEMORY, _MEMORY_TEST_GROUP_NAME));

		ReflectionTestUtil.setFieldValue(
			_quartzSchedulerEngine, "_persistedScheduler",
			new MockScheduler(
				StorageType.PERSISTED, _PERSISTED_TEST_GROUP_NAME));

		_quartzSchedulerEngine.start();
	}

	@After
	public void tearDown() {
		if (_quartzSchedulerEngine != null) {
			_quartzSchedulerEngine.destroy();
		}
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testDelete1() throws Exception {

		// Delete by group name

		List<SchedulerResponse> schedulerResponses =
			_quartzSchedulerEngine.getScheduledJobs(
				_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER, schedulerResponses.size());

		_quartzSchedulerEngine.delete(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertTrue(schedulerResponses.isEmpty());

		// Delete by job name and group name

		SchedulerResponse schedulerResponse =
			_quartzSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
				StorageType.PERSISTED);

		Assert.assertNotNull(schedulerResponse);

		_quartzSchedulerEngine.delete(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		Assert.assertNull(schedulerResponse);
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testDelete2() throws Exception {
		List<SchedulerResponse> schedulerResponses =
			_quartzSchedulerEngine.getScheduledJobs();

		String testJobName = _TEST_JOB_NAME_PREFIX + "memory";

		Assert.assertEquals(2 * _DEFAULT_JOB_NUMBER, schedulerResponses.size());
		Assert.assertEquals(0, _testDestination.getMessageListenerCount());

		Trigger trigger = new IntervalTrigger(
			testJobName, _MEMORY_TEST_GROUP_NAME, _DEFAULT_INTERVAL);

		Message message = new Message();

		message.put(
			SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME,
			TestMessageListener.class.getName());

		_quartzSchedulerEngine.schedule(
			trigger, StringPool.BLANK, _TEST_DESTINATION_NAME, message,
			StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(
			2 * _DEFAULT_JOB_NUMBER + 1, schedulerResponses.size());
		Assert.assertEquals(1, _testDestination.getMessageListenerCount());

		_quartzSchedulerEngine.delete(
			testJobName, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs();

		Assert.assertEquals(2 * _DEFAULT_JOB_NUMBER, schedulerResponses.size());
		Assert.assertEquals(0, _testDestination.getMessageListenerCount());
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testGetQuartzTrigger1() throws Exception {
		Date startDate = new Date(System.currentTimeMillis() + 10000);

		CronTrigger cronTrigger1 = new CronTrigger(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, null, "0/1 * * * * ?");
		CronTrigger cronTrigger2 = new CronTrigger(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, startDate,
			"0/1 * * * * ?");

		org.quartz.Trigger trigger1 =  _quartzSchedulerEngine.getQuartzTrigger(
			cronTrigger1, StorageType.MEMORY);
		org.quartz.Trigger trigger2 = _quartzSchedulerEngine.getQuartzTrigger(
			cronTrigger2, StorageType.MEMORY);

		Date nextFireDate1 = trigger1.getStartTime();
		Date nextFireDate2 = trigger2.getStartTime();

		Assert.assertTrue(nextFireDate1.before(nextFireDate2));
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testGetQuartzTrigger2() {
		String wrongCronTriggerContent = "bad-cron-trigger-content";

		Trigger cronTrigger = new CronTrigger(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, null,
			wrongCronTriggerContent);

		try {
			_quartzSchedulerEngine.getQuartzTrigger(
				cronTrigger, StorageType.MEMORY);

			Assert.fail();
		}
		catch (Exception e) {
		}
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testGetQuartzTrigger3() throws SchedulerException {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			QuartzSchedulerEngine.class.getName(), Level.FINE);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			IntervalTrigger intervalTrigger = new IntervalTrigger(
				_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, 0);

			org.quartz.Trigger trigger =
				_quartzSchedulerEngine.getQuartzTrigger(
					intervalTrigger, StorageType.MEMORY);

			Assert.assertNull(trigger);

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Not scheduling " + _TEST_JOB_NAME_0 + " because interval " +
					"is less than or equal to 0",
				logRecord.getMessage());
		}
		finally {
			captureHandler.close();
		}
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testGetQuartzTrigger4() throws Exception {
		String jobName = _TEST_JOB_NAME_0;

		while (jobName.length() <= SchedulerEngine.JOB_NAME_MAX_LENGTH) {
			jobName = jobName.concat(_TEST_JOB_NAME_0);
		}

		Trigger intervalTrigger = new IntervalTrigger(
			jobName, _PERSISTED_TEST_GROUP_NAME, _DEFAULT_INTERVAL);

		org.quartz.Trigger trigger = _quartzSchedulerEngine.getQuartzTrigger(
			intervalTrigger, StorageType.PERSISTED);

		Assert.assertFalse(jobName.equals(trigger.getJobKey().getName()));

		intervalTrigger = new IntervalTrigger(
			jobName, _MEMORY_TEST_GROUP_NAME, _DEFAULT_INTERVAL);

		trigger = _quartzSchedulerEngine.getQuartzTrigger(
			intervalTrigger, StorageType.MEMORY);

		Assert.assertTrue(jobName.equals(trigger.getJobKey().getName()));
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testInitJobState() throws Exception {
		SchedulerEngineHelperUtil schedulerEngineHelperUtil =
			new SchedulerEngineHelperUtil();

		schedulerEngineHelperUtil.setSchedulerEngineHelper(
			new SchedulerEngineHelperImpl());

		List<SchedulerResponse> schedulerResponses =
			_quartzSchedulerEngine.getScheduledJobs(
				_PERSISTED_TEST_GROUP_NAME, StorageType.PERSISTED);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER, schedulerResponses.size());

		MockScheduler mockScheduler = ReflectionTestUtil.getFieldValue(
			_quartzSchedulerEngine, "_persistedScheduler");

		mockScheduler.addJob(
			_TEST_JOB_NAME_PREFIX + "persisted", _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED, null);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_PERSISTED_TEST_GROUP_NAME, StorageType.PERSISTED);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER + 1, schedulerResponses.size());

		_quartzSchedulerEngine.initJobState();

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_PERSISTED_TEST_GROUP_NAME, StorageType.PERSISTED);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER, schedulerResponses.size());
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testPauseAndResume1() throws Exception {
		List<SchedulerResponse> schedulerResponses =
			_quartzSchedulerEngine.getScheduledJobs(
				_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_assertTriggerState(schedulerResponse, TriggerState.NORMAL);
		}

		_quartzSchedulerEngine.pause(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_assertTriggerState(schedulerResponse, TriggerState.PAUSED);
		}

		_quartzSchedulerEngine.resume(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_assertTriggerState(schedulerResponse, TriggerState.NORMAL);
		}
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testPauseAndResume2() throws Exception {
		SchedulerResponse schedulerResponse =
			_quartzSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
				StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_quartzSchedulerEngine.pause(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.PAUSED);

		_quartzSchedulerEngine.resume(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testSchedule1() throws Exception {
		List<SchedulerResponse> schedulerResponses =
			_quartzSchedulerEngine.getScheduledJobs(
				_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER, schedulerResponses.size());
		Assert.assertEquals(0, _testDestination.getMessageListenerCount());

		Trigger trigger = new IntervalTrigger(
			_TEST_JOB_NAME_PREFIX + "memory", _MEMORY_TEST_GROUP_NAME,
			_DEFAULT_INTERVAL);

		Message message = new Message();

		message.put(
			SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME,
			TestMessageListener.class.getName());
		message.put(SchedulerEngine.PORTLET_ID, _TEST_PORTLET_ID);

		_quartzSchedulerEngine.schedule(
			trigger, StringPool.BLANK, _TEST_DESTINATION_NAME, message,
			StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER + 1, schedulerResponses.size());
		Assert.assertEquals(1, _testDestination.getMessageListenerCount());
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testSchedule2() throws Exception {
		List<SchedulerResponse> schedulerResponses =
			_quartzSchedulerEngine.getScheduledJobs(
				_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER, schedulerResponses.size());
		Assert.assertEquals(0, _testDestination.getMessageListenerCount());

		Trigger trigger = new IntervalTrigger(
			_TEST_JOB_NAME_PREFIX + "memory", _MEMORY_TEST_GROUP_NAME,
			_DEFAULT_INTERVAL);

		_quartzSchedulerEngine.schedule(
			trigger, StringPool.BLANK, _TEST_DESTINATION_NAME, null,
			StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER + 1, schedulerResponses.size());
		Assert.assertEquals(0, _testDestination.getMessageListenerCount());
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testSchedule3() throws Exception {
		List<SchedulerResponse> schedulerResponses =
			_quartzSchedulerEngine.getScheduledJobs(
				_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER, schedulerResponses.size());

		IntervalTrigger intervalTrigger = new IntervalTrigger(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, 0);

		_quartzSchedulerEngine.schedule(
			intervalTrigger, StringPool.BLANK, _TEST_DESTINATION_NAME, null,
			StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER, schedulerResponses.size());
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testSuppressError() throws Exception {
		SchedulerResponse schedulerResponse =
			_quartzSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		Assert.assertNotNull(jobState.getExceptions());

		_quartzSchedulerEngine.suppressError(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		message = schedulerResponse.getMessage();

		jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		Assert.assertNull(jobState.getExceptions());
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testUnschedule1() throws Exception {

		// Unschedule memory job

		List<SchedulerResponse> schedulerResponses =
			_quartzSchedulerEngine.getScheduledJobs(
				_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertEquals(_DEFAULT_JOB_NUMBER, schedulerResponses.size());

		_quartzSchedulerEngine.unschedule(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);
		}

		// Unschedule persisted job

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_PERSISTED_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_assertTriggerState(schedulerResponse, TriggerState.NORMAL);
		}

		_quartzSchedulerEngine.unschedule(
			_PERSISTED_TEST_GROUP_NAME, StorageType.PERSISTED);

		schedulerResponses = _quartzSchedulerEngine.getScheduledJobs(
			_PERSISTED_TEST_GROUP_NAME, StorageType.PERSISTED);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			_assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);
		}
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testUnschedule2() throws Exception {

		// Unschedule memory job

		SchedulerResponse schedulerResponse =
			_quartzSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_quartzSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		_assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);

		// Unschedule persisted job

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);

		_quartzSchedulerEngine.unschedule(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _PERSISTED_TEST_GROUP_NAME,
			StorageType.PERSISTED);

		_assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testUnschedule3() throws Exception {
		Assert.assertEquals(0, _testDestination.getMessageListenerCount());

		String testJobName = _TEST_JOB_NAME_PREFIX + "memory";

		Trigger trigger = new IntervalTrigger(
			testJobName, _MEMORY_TEST_GROUP_NAME, _DEFAULT_INTERVAL);

		Message message = new Message();

		message.put(
			SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME,
			TestMessageListener.class.getName());

		_quartzSchedulerEngine.schedule(
			trigger, StringPool.BLANK, _TEST_DESTINATION_NAME, message,
			StorageType.MEMORY);

		SchedulerResponse schedulerResponse =
			_quartzSchedulerEngine.getScheduledJob(
				testJobName, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		_assertTriggerState(schedulerResponse, TriggerState.NORMAL);
		Assert.assertEquals(1, _testDestination.getMessageListenerCount());

		_quartzSchedulerEngine.unschedule(
			testJobName, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			testJobName, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		_assertTriggerState(schedulerResponse, TriggerState.UNSCHEDULED);
		Assert.assertEquals(0, _testDestination.getMessageListenerCount());
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testUpdate1() throws Exception {
		SchedulerResponse schedulerResponse =
			_quartzSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Object triggerContent =
			schedulerResponse.getTrigger().getTriggerContent();

		Assert.assertEquals(_DEFAULT_INTERVAL, triggerContent);

		Trigger trigger = new IntervalTrigger(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, new Date(),
			_DEFAULT_INTERVAL * 2);

		_quartzSchedulerEngine.update(trigger, StorageType.MEMORY);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		triggerContent = schedulerResponse.getTrigger().getTriggerContent();

		Assert.assertEquals(_DEFAULT_INTERVAL * 2, triggerContent);
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testUpdate2() throws Exception {
		SchedulerResponse schedulerResponse =
			_quartzSchedulerEngine.getScheduledJob(
				_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Object triggerContent =
			schedulerResponse.getTrigger().getTriggerContent();

		Assert.assertEquals(_DEFAULT_INTERVAL, triggerContent);

		String newTriggerContent = "0 0 12 * * ?";

		Trigger trigger = new CronTrigger(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, newTriggerContent);

		_quartzSchedulerEngine.update(trigger, StorageType.MEMORY);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		triggerContent = schedulerResponse.getTrigger().getTriggerContent();

		Assert.assertEquals(newTriggerContent, triggerContent);
	}

	@AdviseWith(adviceClasses = {EnableSchedulerAdvice.class})
	@Test
	public void testUpdate3() throws SchedulerException {
		MockScheduler mockScheduler = ReflectionTestUtil.getFieldValue(
			_quartzSchedulerEngine, "_memoryScheduler");

		String jobName = _TEST_JOB_NAME_PREFIX + "memory";

		mockScheduler.addJob(
			jobName, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY, null);

		SchedulerResponse schedulerResponse =
			_quartzSchedulerEngine.getScheduledJob(
				jobName, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertNull(schedulerResponse.getTrigger());

		Trigger trigger = new IntervalTrigger(
			jobName, _MEMORY_TEST_GROUP_NAME, new Date(), _DEFAULT_INTERVAL);

		_quartzSchedulerEngine.update(trigger, StorageType.MEMORY);

		schedulerResponse = _quartzSchedulerEngine.getScheduledJob(
			_TEST_JOB_NAME_0, _MEMORY_TEST_GROUP_NAME, StorageType.MEMORY);

		Assert.assertNotNull(schedulerResponse.getTrigger());
	}

	@Rule
	public final AspectJNewEnvMethodRule aspectJNewEnvMethodRule =
		new AspectJNewEnvMethodRule();

	@Aspect
	public static class EnableSchedulerAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.SCHEDULER_ENABLED)")
		public Object enableScheduler(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	public static class TestMessageListener implements MessageListener {

		@Override
		public void receive(Message message) {
		}

	}

	private void _assertTriggerState(
		SchedulerResponse schedulerResponse,
		TriggerState expectedTriggerState) {

		Message message = schedulerResponse.getMessage();

		JobState jobState = (JobState)message.get(SchedulerEngine.JOB_STATE);

		TriggerState triggerState = jobState.getTriggerState();

		Assert.assertEquals(expectedTriggerState, triggerState);
	}

	private static final long _DEFAULT_INTERVAL = 10000;

	private static final int _DEFAULT_JOB_NUMBER = 3;

	private static final String _MEMORY_TEST_GROUP_NAME = "memory.test.group";

	private static final String _PERSISTED_TEST_GROUP_NAME =
		"persisted.test.group";

	private static final String _TEST_DESTINATION_NAME = "liferay/test";

	private static final String _TEST_JOB_NAME_0 = "test.job.0";

	private static final String _TEST_JOB_NAME_PREFIX = "test.job.";

	private static final String _TEST_PORTLET_ID = "testPortletId";

	private QuartzSchedulerEngine _quartzSchedulerEngine;
	private SynchronousDestination _testDestination;

	private class MockScheduler implements Scheduler {

		public MockScheduler(StorageType storageType, String defaultGroupName) {
			for (int i = 0; i < _DEFAULT_JOB_NUMBER; i++) {
				TriggerBuilder<org.quartz.Trigger> triggerBuilder =
					TriggerBuilder.newTrigger();

				triggerBuilder.withIdentity(
					_TEST_JOB_NAME_PREFIX + i, defaultGroupName);
				triggerBuilder.withSchedule(
					SimpleScheduleBuilder.simpleSchedule(
						).withIntervalInMilliseconds(_DEFAULT_INTERVAL));

				org.quartz.Trigger trigger = triggerBuilder.build();

				addJob(
					_TEST_JOB_NAME_PREFIX + i, defaultGroupName, storageType,
					trigger);
			}
		}

		@Override
		public void addCalendar(
			String name, Calendar calendar, boolean replace,
			boolean updateTriggers) {
		}

		@Override
		public void addJob(JobDetail jobDetail, boolean replace) {
			_jobs.put(
				jobDetail.getKey(),
				new Tuple(jobDetail, null, TriggerState.UNSCHEDULED));
		}

		public final void addJob(
			String jobName, String groupName, StorageType storageType,
			org.quartz.Trigger trigger) {

			JobKey jobKey = new JobKey(jobName, groupName);

			JobBuilder jobBuilder = JobBuilder.newJob(MessageSenderJob.class);

			jobBuilder = jobBuilder.withIdentity(jobKey);

			JobDetail jobDetail = jobBuilder.build();

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(
				SchedulerEngine.MESSAGE,
				JSONFactoryUtil.serialize(new Message()));
			jobDataMap.put(
				SchedulerEngine.DESTINATION_NAME, _TEST_DESTINATION_NAME);
			jobDataMap.put(
				SchedulerEngine.STORAGE_TYPE, storageType.toString());

			JobState jobState = new JobState(TriggerState.NORMAL);

			jobState.addException(new Exception(), new Date());

			jobDataMap.put(
				SchedulerEngine.JOB_STATE,
				JobStateSerializeUtil.serialize(jobState));

			_jobs.put(
				jobKey, new Tuple(jobDetail, trigger, TriggerState.NORMAL));
		}

		@Override
		public boolean checkExists(JobKey jobkey) {
			return false;
		}

		@Override
		public boolean checkExists(TriggerKey triggerKey) {
			return false;
		}

		@Override
		public void clear() {
		}

		@Override
		public boolean deleteCalendar(String name) {
			return false;
		}

		@Override
		public boolean deleteJob(JobKey jobKey) {
			_jobs.remove(jobKey);

			return true;
		}

		@Override
		public boolean deleteJobs(List<JobKey> jobKeys) {
			return false;
		}

		@Override
		public Calendar getCalendar(String name) {
			return null;
		}

		@Override
		public List<String> getCalendarNames() {
			return Collections.emptyList();
		}

		@Override
		public SchedulerContext getContext() {
			return null;
		}

		@Override
		public List<JobExecutionContext> getCurrentlyExecutingJobs() {
			return Collections.emptyList();
		}

		@Override
		public JobDetail getJobDetail(JobKey jobKey) {
			Tuple tuple = _jobs.get(jobKey);

			if (tuple == null) {
				return null;
			}

			return (JobDetail)tuple.getObject(0);
		}

		@Override
		public List<String> getJobGroupNames() {
			List<String> groupNames = new ArrayList<String>();

			for (JobKey jobKey : _jobs.keySet()) {
				if (!groupNames.contains(jobKey.getGroup())) {
					groupNames.add(jobKey.getGroup());
				}
			}

			return groupNames;
		}

		@Override
		public Set<JobKey> getJobKeys(GroupMatcher<JobKey> groupMatcher) {
			String groupName = groupMatcher.getCompareToValue();

			Set<JobKey> jobKeys = new HashSet<JobKey>();

			for (JobKey jobKey : _jobs.keySet()) {
				if (jobKey.getGroup().equals(groupName)) {
					jobKeys.add(jobKey);
				}
			}

			return jobKeys;
		}

		@Override
		public ListenerManager getListenerManager() {
			return null;
		}

		@Override
		public SchedulerMetaData getMetaData() {
			return null;
		}

		@Override
		public Set<String> getPausedTriggerGroups() {
			return null;
		}

		@Override
		public String getSchedulerInstanceId() {
			return null;
		}

		@Override
		public String getSchedulerName() {
			return null;
		}

		@Override
		public org.quartz.Trigger getTrigger(TriggerKey triggerKey) {
			Tuple tuple = _jobs.get(
				new JobKey(triggerKey.getName(), triggerKey.getGroup()));

			if (tuple == null) {
				return null;
			}

			return (org.quartz.Trigger)tuple.getObject(1);
		}

		@Override
		public List<String> getTriggerGroupNames() {
			return Collections.emptyList();
		}

		@Override
		public Set<TriggerKey> getTriggerKeys(
			GroupMatcher<TriggerKey> groupMatcher) {

			return null;
		}

		@Override
		public List<? extends org.quartz.Trigger> getTriggersOfJob(
			JobKey jobkey) {

			return Collections.emptyList();
		}

		@Override
		public org.quartz.Trigger.TriggerState getTriggerState(
			TriggerKey triggerKey) {

			return null;
		}

		@Override
		public boolean interrupt(JobKey jobkey) {
			return false;
		}

		@Override
		public boolean interrupt(String fireInstanceId) {
			return false;
		}

		@Override
		public boolean isInStandbyMode() {
			return false;
		}

		@Override
		public boolean isShutdown() {
			return _ready == false;
		}

		@Override
		public boolean isStarted() {
			return _ready;
		}

		@Override
		public void pauseAll() {
		}

		@Override
		public void pauseJob(JobKey jobKey) {
			Tuple tuple = _jobs.get(jobKey);

			if (tuple == null) {
				return;
			}

			_jobs.put(
				jobKey,
				new Tuple(
					tuple.getObject(0), tuple.getObject(1),
					TriggerState.PAUSED));
		}

		@Override
		public void pauseJobs(GroupMatcher<JobKey> groupMatcher) {
			String groupName = groupMatcher.getCompareToValue();

			for (JobKey jobKey : _jobs.keySet()) {
				if (jobKey.getGroup().equals(groupName)) {
					pauseJob(jobKey);
				}
			}
		}

		@Override
		public void pauseTrigger(TriggerKey triggerKey) {
		}

		@Override
		public void pauseTriggers(GroupMatcher<TriggerKey> groupMatcher) {
		}

		@Override
		public Date rescheduleJob(
			TriggerKey triggerKey, org.quartz.Trigger trigger) {

			JobKey jobKey = new JobKey(
				triggerKey.getName(), triggerKey.getGroup());

			Tuple tuple = _jobs.get(jobKey);

			if (tuple == null) {
				return null;
			}

			_jobs.put(
				jobKey,
				new Tuple(tuple.getObject(0), trigger, tuple.getObject(2)));

			return null;
		}

		@Override
		public void resumeAll() {
		}

		@Override
		public void resumeJob(JobKey jobKey) {
			Tuple tuple = _jobs.get(jobKey);

			if (tuple == null) {
				return;
			}

			_jobs.put(
				jobKey,
				new Tuple(
					tuple.getObject(0), tuple.getObject(1),
					TriggerState.NORMAL));
		}

		@Override
		public void resumeJobs(GroupMatcher<JobKey> groupMatcher) {
			String groupName = groupMatcher.getCompareToValue();

			for (JobKey jobKey : _jobs.keySet()) {
				if (jobKey.getGroup().equals(groupName)) {
					resumeJob(jobKey);
				}
			}
		}

		@Override
		public void resumeTrigger(TriggerKey triggerKey) {
		}

		@Override
		public void resumeTriggers(GroupMatcher<TriggerKey> groupMatcher) {
		}

		@Override
		public Date scheduleJob(
			JobDetail jobDetail, org.quartz.Trigger trigger) {

			_jobs.put(
				jobDetail.getKey(),
				new Tuple(jobDetail, trigger, TriggerState.NORMAL));

			return null;
		}

		@Override
		public Date scheduleJob(org.quartz.Trigger trigger) {
			return null;
		}

		@Override
		public void scheduleJobs(
			Map<JobDetail, List<org.quartz.Trigger>> map, boolean replace) {
		}

		@Override
		public void setJobFactory(JobFactory jobFactory) {
		}

		@Override
		public void shutdown() {
			_ready = false;
		}

		@Override
		public void shutdown(boolean waitForJobsToComplete) {
			_ready = false;
		}

		@Override
		public void standby() {
		}

		@Override
		public void start() {
			_ready = true;
		}

		@Override
		public void startDelayed(int seconds) {
		}

		@Override
		public void triggerJob(JobKey jobkey) {
		}

		@Override
		public void triggerJob(JobKey jobkey, JobDataMap jobDataMap) {
		}

		@Override
		public boolean unscheduleJob(TriggerKey triggerKey) {
			_jobs.remove(
				new JobKey(triggerKey.getName(), triggerKey.getGroup()));

			return true;
		}

		@Override
		public boolean unscheduleJobs(List<TriggerKey> list) {
			return false;
		}

		private final Map<JobKey, Tuple> _jobs = new HashMap<JobKey, Tuple>();
		private boolean _ready;

	}

}