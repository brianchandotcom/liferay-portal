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

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.ExpectedDBType;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class LayoutCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		long ctCollectionId = _counterLocalService.increment();

		_ctCollection = _ctCollectionLocalService.createCTCollection(
			ctCollectionId);

		_ctCollection.setUserId(TestPropsValues.getUserId());
		_ctCollection.setName(String.valueOf(ctCollectionId));

		_ctCollection = _ctCollectionLocalService.updateCTCollection(
			_ctCollection);

		_layoutClassNameId = _classNameLocalService.getClassNameId(
			Layout.class);

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddLayout() throws Exception {
		Layout layout = null;

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			layout = LayoutTestUtil.addLayout(_group);

			Assert.assertEquals(
				layout, _layoutLocalService.fetchLayout(layout.getPlid()));

			try (SafeClosable safeClosable2 =
					CTCollectionThreadLocal.setCTCollectionId(
						CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				Assert.assertNull(
					_layoutLocalService.fetchLayout(layout.getPlid()));
			}
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), _layoutClassNameId,
			layout.getPlid());

		Assert.assertNotNull(ctEntry);

		Assert.assertEquals(
			CTConstants.CT_CHANGE_TYPE_ADDITION, ctEntry.getChangeType());
	}

	@Test
	public void testModifyLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		String originalFriendlyURL = layout.getFriendlyURL();

		try (SafeClosable safeClosable1 =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			Assert.assertEquals(
				layout, _layoutLocalService.fetchLayout(layout.getPlid()));

			layout.setFriendlyURL("/testModifyLayout");

			layout = _layoutLocalService.updateLayout(layout);

			try (SafeClosable safeClosable2 =
					CTCollectionThreadLocal.setCTCollectionId(
						CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				Layout productionLayout = _layoutLocalService.fetchLayout(
					layout.getPlid());

				Assert.assertEquals(
					originalFriendlyURL, productionLayout.getFriendlyURL());
			}
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), _layoutClassNameId,
			layout.getPlid());

		Assert.assertNotNull(ctEntry);

		Assert.assertEquals(
			CTConstants.CT_CHANGE_TYPE_MODIFICATION, ctEntry.getChangeType());
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog = "Error for batch element",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.DB2,
				expectedLog = "Batch failure.",
				expectedType = ExpectedType.CONTAINS
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.HYPERSONIC,
				expectedLog = "integrity constraint violation: unique constraint or index violation: IX_B556968F",
				expectedType = ExpectedType.EXACT
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MARIADB,
				expectedLog = "Deadlock found when trying to get lock; try restarting transaction",
				expectedType = ExpectedType.EXACT
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MARIADB,
				expectedLog = "Duplicate entry",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog = "Deadlock found when trying to get lock; try restarting transaction",
				expectedType = ExpectedType.EXACT
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.MYSQL,
				expectedLog = "Duplicate entry",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.ORACLE,
				expectedLog = "ORA-00001: unique constraint",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog = "Batch entry 0 insert into Lock_ ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog = "ERROR: current transaction is aborted, commands ignored until end of transaction block",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.POSTGRESQL,
				expectedLog = "ERROR: duplicate key value violates unique constraint ",
				expectedType = ExpectedType.PREFIX
			),
			@ExpectedLog(
				expectedDBType = ExpectedDBType.SYBASE,
				expectedLog = "Attempt to insert duplicate key row",
				expectedType = ExpectedType.CONTAINS
			)
		},
		level = "ERROR", loggerClass = JDBCExceptionReporter.class
	)
	@Test
	public void testPublishLayoutWithConflictingConstraints() throws Exception {
		String friendlyURL = "/testModifyLayout";

		Layout layout1 = LayoutTestUtil.addLayout(_group);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			layout1.setFriendlyURL(friendlyURL);

			layout1 = _layoutLocalService.updateLayout(layout1);
		}

		Layout layout2 = LayoutTestUtil.addLayout(_group);

		layout2.setFriendlyURL(friendlyURL);

		layout2 = _layoutLocalService.updateLayout(layout2);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.background.task.internal.messaging." +
						"BackgroundTaskMessageListener",
					Level.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Unable to execute background task", loggingEvent.getMessage());
		}

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			Layout layout = _layoutLocalService.fetchLayout(layout1.getPlid());

			Assert.assertNotNull(layout);

			Assert.assertEquals(layout.getFriendlyURL(), friendlyURL);
		}

		Layout layout = _layoutLocalService.fetchLayout(layout2.getPlid());

		Assert.assertNotNull(layout);

		Assert.assertEquals(layout.getFriendlyURL(), friendlyURL);
	}

	@Test
	public void testPublishLayoutWithConflictingUpdate() throws Exception {
		String ctFriendlyURL = "/testCTLayout";
		String conflictingFriendlyURL = "/testConflictingLayout";

		Layout layout = LayoutTestUtil.addLayout(_group);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			layout.setFriendlyURL(ctFriendlyURL);

			_layoutLocalService.updateLayout(layout);
		}

		layout.setFriendlyURL(conflictingFriendlyURL);

		layout = _layoutLocalService.updateLayout(layout);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.background.task.internal.messaging." +
						"BackgroundTaskMessageListener",
					Level.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			ThrowableInformation throwableInformation =
				loggingEvent.getThrowableInformation();

			Assert.assertNotNull(throwableInformation);

			Throwable throwable = throwableInformation.getThrowable();

			Assert.assertNotNull(throwable);

			String message = throwable.getMessage();

			Assert.assertTrue(
				message, message.contains("MVCC version mismatch between "));
		}

		layout = _layoutLocalService.fetchLayout(layout.getPlid());

		Assert.assertNotNull(layout);

		Assert.assertEquals(layout.getFriendlyURL(), conflictingFriendlyURL);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			layout = _layoutLocalService.fetchLayout(layout.getPlid());

			Assert.assertNotNull(layout);

			Assert.assertEquals(layout.getFriendlyURL(), ctFriendlyURL);
		}
	}

	@Test
	public void testPublishModifiedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			layout.setFriendlyURL("/testModifyLayout");

			layout = _layoutLocalService.updateLayout(layout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Layout productionLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotNull(productionLayout);

		Assert.assertEquals(
			layout.getFriendlyURL(), productionLayout.getFriendlyURL());
	}

	@Test
	public void testPublishModifiedLayoutWithTargetDeleted() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			layout.setFriendlyURL("/testModifyLayout");

			layout = _layoutLocalService.updateLayout(layout);
		}

		_layoutLocalService.deleteLayout(layout);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.background.task.internal.messaging." +
						"BackgroundTaskMessageListener",
					Level.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 1, loggingEvents.size());

			LoggingEvent loggingEvent = loggingEvents.get(0);

			ThrowableInformation throwableInformation =
				loggingEvent.getThrowableInformation();

			Assert.assertNotNull(throwableInformation);

			Throwable throwable = throwableInformation.getThrowable();

			Assert.assertNotNull(throwable);

			String message = throwable.toString();

			Assert.assertTrue(
				message, message.contains("Size mismatch between "));
		}
	}

	@Test
	public void testPublishNewLayout() throws Exception {
		Layout layout = null;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			layout = LayoutTestUtil.addLayout(_group);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Assert.assertEquals(
			layout, _layoutLocalService.fetchLayout(layout.getPlid()));
	}

	@Test
	public void testPublishRemovedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			_layoutLocalService.deleteLayout(layout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Assert.assertNull(_layoutLocalService.fetchLayout(layout.getPlid()));
	}

	@Test
	public void testRemoveLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			layout = _layoutLocalService.deleteLayout(layout);

			Assert.assertNull(
				_layoutLocalService.fetchLayout(layout.getPlid()));

			try (SafeClosable safeClosable2 =
					CTCollectionThreadLocal.setCTCollectionId(
						CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				Assert.assertEquals(
					layout, _layoutLocalService.fetchLayout(layout.getPlid()));
			}
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), _layoutClassNameId,
			layout.getPlid());

		Assert.assertNotNull(ctEntry);

		Assert.assertEquals(
			CTConstants.CT_CHANGE_TYPE_DELETION, ctEntry.getChangeType());
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	private static long _layoutClassNameId;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

	@DeleteAfterTestRun
	private Group _group;

}