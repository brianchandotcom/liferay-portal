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

import com.liferay.mail.service.MailService;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Jdk14LogFactoryImpl;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.mail.MockMailServiceImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public abstract class BaseSubscriptionTestCase {

	public abstract long addBaseModel(long containerModelId) throws Exception;

	public abstract long addContainerModel(long containerModelId)
		throws Exception;

	public abstract void addSubscriptionBaseModel(long baseModelId)
		throws Exception;

	public abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

	@Before
	public void setUpClass() throws Exception {
		group = GroupTestUtil.addGroup();

		_logFactory = LogFactoryUtil.getLogFactory();

		LogFactoryUtil.setLogFactory(new Jdk14LogFactoryImpl());

		_mailService = MailServiceUtil.getService();

		MailServiceUtil mailServiceUtil = new MailServiceUtil();

		mailServiceUtil.setService(new LoggerMockMailServiceImpl());
	}

	@After
	public void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);

		LogFactoryUtil.setLogFactory(_logFactory);

		MailServiceUtil mailServiceUtil = new MailServiceUtil();

		mailServiceUtil.setService(_mailService);
	}

	@Test
	public void testSubscriptionBaseModelWhenInContainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerModelId = addContainerModel(
			DEFAULT_PARENT_CONTAINER_MODEL_ID);

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

		long baseModelId = addBaseModel(DEFAULT_PARENT_CONTAINER_MODEL_ID);

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
			DEFAULT_PARENT_CONTAINER_MODEL_ID);

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
			DEFAULT_PARENT_CONTAINER_MODEL_ID);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(DEFAULT_PARENT_CONTAINER_MODEL_ID);

		Assert.assertEquals(0, logRecords.size());
	}

	@Test
	public void testSubscriptionContainerModelWhenInSubcontainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerModelId = addContainerModel(
			DEFAULT_PARENT_CONTAINER_MODEL_ID);

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

		addSubscriptionContainerModel(DEFAULT_PARENT_CONTAINER_MODEL_ID);

		long containerModelId = addContainerModel(
			DEFAULT_PARENT_CONTAINER_MODEL_ID);

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

		addSubscriptionContainerModel(DEFAULT_PARENT_CONTAINER_MODEL_ID);

		addBaseModel(DEFAULT_PARENT_CONTAINER_MODEL_ID);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInSubcontainerModel()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		addSubscriptionContainerModel(DEFAULT_PARENT_CONTAINER_MODEL_ID);

		long containerModelId = addContainerModel(
			DEFAULT_PARENT_CONTAINER_MODEL_ID);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	public abstract long updateEntry(long baseModelId) throws Exception;

	protected static final long DEFAULT_PARENT_CONTAINER_MODEL_ID = 0;

	protected Group group;

	private LogFactory _logFactory;
	private MailService _mailService;

	private static class LoggerMockMailServiceImpl extends MockMailServiceImpl {

		public LoggerMockMailServiceImpl() {
			_logger.setLevel(Level.INFO);
		}

		@Override
		public void sendEmail(MailMessage mailMessage) {
			_logger.info("Sending email");
		}

		private Logger _logger = Logger.getLogger(
			LoggerMockMailServiceImpl.class.getName());

	}

}