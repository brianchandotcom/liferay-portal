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

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.lar.backgroundtask.PortletStagingBackgroundTaskExecutor;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BackgroundTaskImpl;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.UserTestUtil;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.net.URL;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class BackgroundTaskLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser("BackgroundUser", _group.getGroupId());
	}

	@Test
	public void testAddBackgroundTask() throws Exception {
		long userId = _user.getUserId();

		long groupId = _group.getGroupId();

		Map<String, Serializable> taskContextMap = getTaskContextMap();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				userId, groupId, _BACKGROUND_TASK_NAME, null,
				_TASK_EXECUTOR_CLASS, taskContextMap, new ServiceContext());

		Assert.assertNotNull(backgroundTask);
		Assert.assertEquals(userId, backgroundTask.getUserId());
		Assert.assertEquals(groupId, backgroundTask.getGroupId());
		Assert.assertEquals(_BACKGROUND_TASK_NAME, backgroundTask.getName());

		Assert.assertEquals(
			_TASK_EXECUTOR_CLASS.getCanonicalName(),
			backgroundTask.getTaskExecutorClassName());

		assertMapEquals(taskContextMap, backgroundTask.getTaskContextMap());
	}

	@Test
	public void testAddBackgroundTaskAttachmentByFile() throws Exception {
		long userId = _user.getUserId();

		long groupId = _group.getGroupId();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				userId, groupId, _BACKGROUND_TASK_NAME, null,
				_TASK_EXECUTOR_CLASS, getTaskContextMap(),
				new ServiceContext());

		Assert.assertEquals(backgroundTask.getAttachmentsFileEntriesCount(), 0);

		URL url = getClass().getResource(_FILE_PATH);

		File file = new File(url.toURI());

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), _FILE_NAME, file);

		BackgroundTask backgroundTaskWithAttachedFile =
			BackgroundTaskLocalServiceUtil.fetchBackgroundTask(
				backgroundTask.getBackgroundTaskId());

		Assert.assertEquals(
			backgroundTaskWithAttachedFile.getAttachmentsFileEntriesCount(), 1);
	}

	@Test
	public void testAddBackgroundTaskAttachmentByInputStream()
		throws Exception {

		long userId = _user.getUserId();

		long groupId = _group.getGroupId();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				userId, groupId, _BACKGROUND_TASK_NAME, null,
				_TASK_EXECUTOR_CLASS, getTaskContextMap(),
				new ServiceContext());

		Assert.assertEquals(backgroundTask.getAttachmentsFileEntriesCount(), 0);

		InputStream inputStream = getClass().getResourceAsStream(_FILE_PATH);

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, backgroundTask.getBackgroundTaskId(), _FILE_NAME,
			inputStream);

		BackgroundTask backgroundTaskWithAttachedFile =
			BackgroundTaskLocalServiceUtil.fetchBackgroundTask(
				backgroundTask.getBackgroundTaskId());

		Assert.assertEquals(
			backgroundTaskWithAttachedFile.getAttachmentsFileEntriesCount(), 1);
	}

	@Test
	public void testAddBackgroundTaskWithBackgroundTask() throws Exception {
		long userId = _user.getUserId();

		long groupId = _group.getGroupId();

		BackgroundTask backgroundTaskImpl = new BackgroundTaskImpl();

		backgroundTaskImpl.setUserId(userId);
		backgroundTaskImpl.setGroupId(groupId);
		backgroundTaskImpl.setName(_BACKGROUND_TASK_NAME);
		backgroundTaskImpl.setTaskExecutorClassName(
			_TASK_EXECUTOR_CLASS.getCanonicalName());

		Map<String, Serializable> taskContextMap = getTaskContextMap();

		String taskContext = JSONFactoryUtil.serialize(taskContextMap);

		backgroundTaskImpl.setTaskContext(taskContext);

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				backgroundTaskImpl);

		Assert.assertNotNull(backgroundTask);
		Assert.assertEquals(userId, backgroundTask.getUserId());
		Assert.assertEquals(groupId, backgroundTask.getGroupId());
		Assert.assertEquals(_BACKGROUND_TASK_NAME, backgroundTask.getName());
		Assert.assertEquals(
			_TASK_EXECUTOR_CLASS.getCanonicalName(),
			backgroundTask.getTaskExecutorClassName());

		assertMapEquals(taskContextMap, backgroundTask.getTaskContextMap());
	}

	@Test
	public void testAmendBackgroundTaskFailedStatus() throws Exception {
		long userId = _user.getUserId();

		long groupId = _group.getGroupId();

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				userId, groupId, _BACKGROUND_TASK_NAME, null,
				_TASK_EXECUTOR_CLASS, getTaskContextMap(), serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_NEW, backgroundTask.getStatus());

		BackgroundTask ammendBackgroundTask =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
			backgroundTask.getBackgroundTaskId(),
			backgroundTask.getTaskContextMap(),
			BackgroundTaskConstants.STATUS_FAILED, serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_FAILED,
			ammendBackgroundTask.getStatus());

		Assert.assertTrue(ammendBackgroundTask.isCompleted());
	}

	@Test
	public void testAmendBackgroundTaskNotFailedNotSuccessful()
		throws Exception {

		long userId = _user.getUserId();

		long groupId = _group.getGroupId();

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				userId, groupId, _BACKGROUND_TASK_NAME, null,
				_TASK_EXECUTOR_CLASS, getTaskContextMap(), serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_NEW, backgroundTask.getStatus());

		BackgroundTask ammendBackgroundTask =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				backgroundTask.getBackgroundTaskId(),
				backgroundTask.getTaskContextMap(),
				BackgroundTaskConstants.STATUS_IN_PROGRESS, serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_IN_PROGRESS,
			ammendBackgroundTask.getStatus());

		Assert.assertFalse(ammendBackgroundTask.isCompleted());
	}

	@Test
	public void testAmendBackgroundTaskSuccessfulStatus() throws Exception {
		long userId = _user.getUserId();

		long groupId = _group.getGroupId();

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				userId, groupId, _BACKGROUND_TASK_NAME, null,
				_TASK_EXECUTOR_CLASS, getTaskContextMap(), serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_NEW, backgroundTask.getStatus());

		BackgroundTask ammendBackgroundTask =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				backgroundTask.getBackgroundTaskId(),
				backgroundTask.getTaskContextMap(),
				BackgroundTaskConstants.STATUS_SUCCESSFUL, serviceContext);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_SUCCESSFUL,
			ammendBackgroundTask.getStatus());

		Assert.assertTrue(ammendBackgroundTask.isCompleted());
	}

	@Test
	public void testAmendBackgroundTaskTaskContextMap() throws Exception {
		long userId = _user.getUserId();

		long groupId = _group.getGroupId();

		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask backgroundTask =
			BackgroundTaskLocalServiceUtil.addBackgroundTask(
				userId, groupId, _BACKGROUND_TASK_NAME, null,
				_TASK_EXECUTOR_CLASS, getTaskContextMap(), serviceContext);

		Map map = getTaskContextMap();

		BackgroundTask ammendBackgroundTask =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				backgroundTask.getBackgroundTaskId(), map,
				backgroundTask.getStatus(), serviceContext);

		assertMapEquals(ammendBackgroundTask.getTaskContextMap(), map);
	}

	@Test
	public void testAmendBackgroundTaskNullBackgroundTaskId() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		BackgroundTask amendBackgroundTask =
			BackgroundTaskLocalServiceUtil.amendBackgroundTask(
				33L, null, 0, serviceContext);

		Assert.assertNull(amendBackgroundTask);
	}

	protected void assertMapEquals(Map map1, Map map2) {
		Assert.assertEquals(map1.size(), map2.size());

		for (Object object :map1.keySet()) {
			Assert.assertEquals(map1.get(object), map2.get(object));
		}
	}

	protected Map<String, Serializable> getTaskContextMap() throws Exception {
		Map<String, Serializable> taskContext =
			new HashMap<String, Serializable>();

		taskContext.put("Param1", ServiceTestUtil.randomBoolean());
		taskContext.put("Param2", ServiceTestUtil.randomString());
		taskContext.put("Param3", ServiceTestUtil.randomInt());
		taskContext.put("Param4", new Date());

		return taskContext;
	}

	private final static String _BACKGROUND_TASK_NAME = "Name";

	private final static String _FILE_NAME = "backgroundTaskAttachmentTest.txt";

	private final static String _FILE_PATH =
		"/com/liferay/portal/util/dependencies/test.txt";

	private final static Class _TASK_EXECUTOR_CLASS =
		PortletStagingBackgroundTaskExecutor.class;

	private Group _group;
	private User _user;

}