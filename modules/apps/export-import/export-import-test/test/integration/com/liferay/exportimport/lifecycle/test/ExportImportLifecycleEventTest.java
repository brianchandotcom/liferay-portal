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

package com.liferay.exportimport.lifecycle.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEventListenerRegistryUtil;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleListener;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portlet.exportimport.service.ExportImportLocalServiceUtil;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class ExportImportLifecycleEventTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_liveGroup = GroupTestUtil.addGroup();

		ExportImportLifecycleEventListenerRegistryUtil.register(
			new MockExportImportLifecycleListener());

		_firedExportImportLifecycleEventsMap = new HashMap<>();

		_parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap();
	}

	@Test
	public void testFailedLayoutExport() throws Exception {
		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUserId(), 0, false, new long[0],
					_parameterMap, Locale.US, TimeZoneUtil.GMT);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		try {
			ExportImportLocalServiceUtil.exportLayoutsAsFile(
				exportImportConfiguration);

			Assert.fail();
		}
		catch (NoSuchGroupException nsge) {
			Assert.assertEquals(
				"No Group exists with the primary key 0", nsge.getMessage());
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_FAILED));
	}

	@Test
	public void testFailedLayoutImport() throws Exception {
		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					TestPropsValues.getUserId(), 0, false, new long[0],
					_parameterMap, Locale.US, TimeZoneUtil.GMT);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		try {
			ExportImportLocalServiceUtil.importLayouts(
				exportImportConfiguration, (File)null);

			Assert.fail();
		}
		catch (NoSuchGroupException nsge) {
			Assert.assertEquals(
				"No Group exists with the primary key 0", nsge.getMessage());
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_FAILED));
	}

	@Test
	public void testFailedLayoutLocalPublishing() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.background.task.internal.messaging." +
						"BackgroundTaskMessageListener",
					Level.ERROR)) {

			StagingUtil.publishLayouts(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.nextInt(), false, new long[0], _parameterMap);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Unable to execute background task", loggingEvent.getMessage());

			ThrowableInformation throwableInformation =
				loggingEvent.getThrowableInformation();

			Throwable throwable = throwableInformation.getThrowable();

			Assert.assertSame(
				NoSuchLayoutSetException.class, throwable.getClass());
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED));
	}

	@Test
	public void testFailedPortletExport() throws Exception {
		long plid = RandomTestUtil.nextLong();

		Map<String, Serializable> exportPortletSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildExportPortletSettingsMap(
					TestPropsValues.getUserId(), plid, _group.getGroupId(),
					StringPool.BLANK, _parameterMap, Locale.US,
					TimeZoneUtil.GMT, StringPool.BLANK);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_PORTLET,
					exportPortletSettingsMap);

		try {
			ExportImportLocalServiceUtil.exportPortletInfoAsFile(
				exportImportConfiguration);

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
			Assert.assertEquals(
				"No Layout exists with the primary key " + plid,
				nsle.getMessage());
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED));
	}

	@Test
	public void testFailedPortletImport() throws Exception {
		Map<String, Serializable> importPortletSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportPortletSettingsMap(
					TestPropsValues.getUserId(), 0, _group.getGroupId(),
					StringPool.BLANK, _parameterMap, Locale.US,
					TimeZoneUtil.GMT);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET,
					importPortletSettingsMap);

		try {
			ExportImportLocalServiceUtil.importPortletInfo(
				exportImportConfiguration, (File)null);

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
			Assert.assertEquals(
				"No Layout exists with the primary key 0", nsle.getMessage());
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_FAILED));
	}

	@Test
	public void testFailedPortletLocalPublishing() throws Exception {
		User user = TestPropsValues.getUser();

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.background.task.internal.messaging." +
						"BackgroundTaskMessageListener",
					Level.ERROR)) {

			StagingUtil.publishPortlet(
				user.getUserId(), _group.getGroupId(), _liveGroup.getGroupId(),
				0, 0, StringPool.BLANK, _parameterMap);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			LoggingEvent loggingEvent = loggingEvents.get(0);

			Assert.assertEquals(
				"Unable to execute background task", loggingEvent.getMessage());

			ThrowableInformation throwableInformation =
				loggingEvent.getThrowableInformation();

			Throwable throwable = throwableInformation.getThrowable();

			Assert.assertSame(
				NoSuchLayoutException.class, throwable.getClass());
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_LOCAL_FAILED));
	}

	@Test
	public void testSuccessfulLayoutLocalPublishing() throws Exception {
		LayoutTestUtil.addLayout(_group, false);

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_liveGroup.getGroupId(), false, null, _parameterMap);

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_STARTED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_SUCCEEDED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_STARTED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_SUCCEEDED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_STARTED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_SUCCEEDED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_STARTED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_SUCCEEDED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_STAGED_MODEL_EXPORT_STARTED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_STAGED_MODEL_EXPORT_SUCCEEDED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_STAGED_MODEL_IMPORT_STARTED));
		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_STAGED_MODEL_IMPORT_SUCCEEDED));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportLifecycleEventTest.class);

	private Map<Integer, ExportImportLifecycleEvent>
		_firedExportImportLifecycleEventsMap;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private Map<String, String[]> _parameterMap;

	private class MockExportImportLifecycleListener
		implements ExportImportLifecycleListener {

		@Override
		public boolean isParallel() {
			return false;
		}

		@Override
		public void onExportImportLifecycleEvent(
				ExportImportLifecycleEvent exportImportLifecycleEvent)
			throws Exception {

			Assert.assertNotNull(exportImportLifecycleEvent);

			_firedExportImportLifecycleEventsMap.put(
				exportImportLifecycleEvent.getCode(),
				exportImportLifecycleEvent);
		}

	}

}