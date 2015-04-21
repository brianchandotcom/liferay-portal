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

package com.liferay.portal.service;

import com.liferay.portal.backgroundtask.BaseBackgroundTaskTestCase;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.lar.backgroundtask.PortletStagingBackgroundTaskExecutor;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BackgroundTaskImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.net.URL;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class BackgroundTaskLocalServiceTest extends BaseBackgroundTaskTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser(_group.getGroupId());

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Destination destination = messageBus.getDestination(
			DestinationNames.BACKGROUND_TASK);

		destination.copyMessageListeners(_destination);

		destination.unregisterMessageListeners();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		Destination destination = messageBus.getDestination(
			DestinationNames.BACKGROUND_TASK);

		_destination.copyMessageListeners(destination);

		BackgroundTaskLocalServiceUtil.deleteGroupBackgroundTasks(
			_group.getGroupId());
	}

	@Test
	public void testAddBackgroundTask() throws Exception {
		initalizeThreadLocals();

		Map<String, Serializable> taskContextMap = getTaskContextMap();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				_user.getUserId(), _group.getGroupId(), _BACKGROUND_TASK_NAME,
				null, _TASK_EXECUTOR_CLASS, taskContextMap,
				new ServiceContext());

		Assert.assertNotNull(backgroundTask);
		Assert.assertEquals(_user.getUserId(), backgroundTask.getUserId());
		Assert.assertEquals(_group.getGroupId(), backgroundTask.getGroupId());
		Assert.assertEquals(_BACKGROUND_TASK_NAME, backgroundTask.getName());
		Assert.assertEquals(
			_TASK_EXECUTOR_CLASS.getCanonicalName(),
			backgroundTask.getTaskExecutorClassName());
		AssertUtils.assertEquals(
			taskContextMap, backgroundTask.getTaskContextMap());

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)backgroundTask.getTaskContextMap().get(
				"threadLocalValues");

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testAddBackgroundTaskAttachmentFromFile() throws Exception {
		initalizeThreadLocals();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				_user.getUserId(), _group.getGroupId(), _BACKGROUND_TASK_NAME,
				null, _TASK_EXECUTOR_CLASS, getTaskContextMap(),
				new ServiceContext());

		Assert.assertEquals(backgroundTask.getAttachmentsFileEntriesCount(), 0);

		Class<?> clazz = getClass();

		URL url = clazz.getResource(_RESOURCE_NAME);

		File file = new File(url.toURI());

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			_user.getUserId(), backgroundTask.getBackgroundTaskId(),
			RandomTestUtil.randomString(), file);

		backgroundTask = BackgroundTaskLocalServiceUtil.fetchBackgroundTask(
			backgroundTask.getBackgroundTaskId());

		Assert.assertEquals(backgroundTask.getAttachmentsFileEntriesCount(), 1);

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)backgroundTask.getTaskContextMap().get(
				"threadLocalValues");

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testAddBackgroundTaskAttachmentFromInputStream()
		throws Exception {

		initalizeThreadLocals();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				_user.getUserId(), _group.getGroupId(), _BACKGROUND_TASK_NAME,
				null, _TASK_EXECUTOR_CLASS, getTaskContextMap(),
				new ServiceContext());

		Assert.assertEquals(backgroundTask.getAttachmentsFileEntriesCount(), 0);

		String fileName = RandomTestUtil.randomString();

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(_RESOURCE_NAME);

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			_user.getUserId(), backgroundTask.getBackgroundTaskId(), fileName,
			inputStream);

		backgroundTask = BackgroundTaskLocalServiceUtil.fetchBackgroundTask(
			backgroundTask.getBackgroundTaskId());

		Assert.assertEquals(backgroundTask.getAttachmentsFileEntriesCount(), 1);

		List<FileEntry> attachmentsFileEntries =
			backgroundTask.getAttachmentsFileEntries();

		FileEntry attachment = attachmentsFileEntries.get(0);

		Assert.assertEquals(fileName, attachment.getTitle());

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)backgroundTask.getTaskContextMap().get(
				"threadLocalValues");

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testAddBackgroundTaskFromBackgroundTask() throws Exception {
		initalizeThreadLocals();

		BackgroundTask backgroundTaskImpl = new BackgroundTaskImpl();

		backgroundTaskImpl.setUserId(_user.getUserId());
		backgroundTaskImpl.setGroupId(_group.getGroupId());
		backgroundTaskImpl.setName(_BACKGROUND_TASK_NAME);
		backgroundTaskImpl.setTaskExecutorClassName(
			_TASK_EXECUTOR_CLASS.getCanonicalName());

		Map<String, Serializable> taskContextMap = getTaskContextMap();

		backgroundTaskImpl.setTaskContextMap(taskContextMap);

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				backgroundTaskImpl);

		Assert.assertNotNull(backgroundTask);
		Assert.assertEquals(_user.getUserId(), backgroundTask.getUserId());
		Assert.assertEquals(_group.getGroupId(), backgroundTask.getGroupId());
		Assert.assertEquals(_BACKGROUND_TASK_NAME, backgroundTask.getName());
		Assert.assertEquals(
			_TASK_EXECUTOR_CLASS.getCanonicalName(),
			backgroundTask.getTaskExecutorClassName());
		AssertUtils.assertEquals(
			taskContextMap, backgroundTask.getTaskContextMap());

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)backgroundTask.getTaskContextMap().get(
				"threadLocalValues");

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testAmendBackgroundTaskWithInvalidBackgroundTaskId()
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				0, null, BackgroundTaskConstants.STATUS_NEW, serviceContext);

		Assert.assertNull(backgroundTask);
	}

	@Test
	public void testAmendBackgroundTaskWithStatusFailed() throws Exception {
		initalizeThreadLocals();

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				_user.getUserId(), _group.getGroupId(), _BACKGROUND_TASK_NAME,
				null, _TASK_EXECUTOR_CLASS, getTaskContextMap(),
				serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_NEW, backgroundTask.getStatus());

		backgroundTask = BackgroundTaskLocalServiceUtil.amendBackgroundTask(
			backgroundTask.getBackgroundTaskId(),
			backgroundTask.getTaskContextMap(),
			BackgroundTaskConstants.STATUS_FAILED, serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_FAILED, backgroundTask.getStatus());
		Assert.assertTrue(backgroundTask.isCompleted());

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)backgroundTask.getTaskContextMap().get(
				"threadLocalValues");

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testAmendBackgroundTaskWithStatusInProgress() throws Exception {
		initalizeThreadLocals();

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				_user.getUserId(), _group.getGroupId(), _BACKGROUND_TASK_NAME,
				null, _TASK_EXECUTOR_CLASS, getTaskContextMap(),
				serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_NEW, backgroundTask.getStatus());

		backgroundTask = BackgroundTaskLocalServiceUtil.amendBackgroundTask(
			backgroundTask.getBackgroundTaskId(),
			backgroundTask.getTaskContextMap(),
			BackgroundTaskConstants.STATUS_IN_PROGRESS, serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_IN_PROGRESS,
			backgroundTask.getStatus());
		Assert.assertFalse(backgroundTask.isCompleted());

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)backgroundTask.getTaskContextMap().get(
				"threadLocalValues");

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testAmendBackgroundTaskWithStatusSuccessful() throws Exception {
		initalizeThreadLocals();

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				_user.getUserId(), _group.getGroupId(), _BACKGROUND_TASK_NAME,
				null, _TASK_EXECUTOR_CLASS, getTaskContextMap(),
				serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_NEW, backgroundTask.getStatus());

		backgroundTask = BackgroundTaskLocalServiceUtil.amendBackgroundTask(
			backgroundTask.getBackgroundTaskId(),
			backgroundTask.getTaskContextMap(),
			BackgroundTaskConstants.STATUS_SUCCESSFUL, serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_SUCCESSFUL,
			backgroundTask.getStatus());
		Assert.assertTrue(backgroundTask.isCompleted());

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)backgroundTask.getTaskContextMap().get(
				"threadLocalValues");

		assertThreadLocalValues(threadLocalValues);
	}

	@Test
	public void testAmendBackgroundTaskWithTaskContextMap() throws Exception {
		initalizeThreadLocals();

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				_user.getUserId(), _group.getGroupId(), _BACKGROUND_TASK_NAME,
				null, _TASK_EXECUTOR_CLASS, getTaskContextMap(),
				serviceContext);

		Map<String, Serializable> taskContextMap = getTaskContextMap();

		backgroundTask = BackgroundTaskLocalServiceUtil.amendBackgroundTask(
			backgroundTask.getBackgroundTaskId(), taskContextMap,
			backgroundTask.getStatus(), serviceContext);

		AssertUtils.assertEquals(
			backgroundTask.getTaskContextMap(), taskContextMap);

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)backgroundTask.getTaskContextMap().get(
				"threadLocalValues");

		assertThreadLocalValues(threadLocalValues);
	}

	protected Map<String, Serializable> getTaskContextMap() throws Exception {
		Map<String, Serializable> taskContext = new HashMap<>();

		taskContext.put("param1", RandomTestUtil.randomBoolean());
		taskContext.put("param2", RandomTestUtil.randomString());
		taskContext.put("param3", RandomTestUtil.randomInt());
		taskContext.put("param4", new Date());

		return taskContext;
	}

	private static final String _BACKGROUND_TASK_NAME =
		BackgroundTaskLocalServiceTest.class.getName();

	private static final String _RESOURCE_NAME =
		"/com/liferay/portal/util/dependencies/test.txt";

	private static final Class<?> _TASK_EXECUTOR_CLASS =
		PortletStagingBackgroundTaskExecutor.class;

	private final Destination _destination = new SynchronousDestination();

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}