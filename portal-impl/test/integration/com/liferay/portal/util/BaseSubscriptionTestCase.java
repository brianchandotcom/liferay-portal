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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionTestCase extends BaseMailTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		group = GroupTestUtil.addGroup();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	public void testSubscriptionBaseModelWhenInContainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(containerModelId);

		addSubscriptionBaseModel(baseModelId);

		updateEntry(baseModelId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long baseModelId = addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionBaseModel(baseModelId);

		updateEntry(baseModelId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionContainerModelWhenInContainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(containerModelId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionContainerModelWhenInRootContainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(0, logRecords.size());
	}

	@Test
	public void testSubscriptionContainerModelWhenInSubcontainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInContainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(containerModelId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInRootContainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInSubcontainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	protected abstract long addBaseModel(long containerModelId)
		throws Exception;

	protected long addContainerModel(long containerModelId) throws Exception {
		return 0;
	};

	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		return;
	}

	protected abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

	protected long updateEntry(long baseModelId) throws Exception {
		return 0;
	};

	protected Group group;

	private static final long _PARENT_CONTAINER_MODEL_ID_DEFAULT = 0;

}