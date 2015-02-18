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

package com.liferay.portal.lar.lifecycle;

import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEventListenerRegistryUtil;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.lar.LayoutExporter;
import com.liferay.portal.lar.LayoutImporter;
import com.liferay.portal.lar.PortletExporter;
import com.liferay.portal.lar.PortletImporter;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Daniel Kocsis
 */
@Sync
public class ExportImportLifecycleEventTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_liveGroup = GroupTestUtil.addGroup();

		ExportImportLifecycleEventListenerRegistryUtil.register(
			new MockExportImportLifecycleListener());

		_firedExportImportLifecycleEventsMap = new HashMap<>();
	}

	@Test
	public void testFailedLayoutExport() throws Exception {
		LayoutExporter layoutExporter = LayoutExporter.getInstance();

		try {
			layoutExporter.exportLayoutsAsFile(
				0, false, new long[0], StagingUtil.getStagingParameters(),
				new Date(), new Date());
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t);
			}
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_LAYOUT_EXPORT_FAILED));
	}

	@Test
	public void testFailedLayoutImport() throws Exception {
		LayoutImporter layoutImporter = LayoutImporter.getInstance();

		try {
			layoutImporter.importLayouts(
				TestPropsValues.getUserId(), 0, false,
				StagingUtil.getStagingParameters(), null);
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t);
			}
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_FAILED));
	}

	@Test
	public void testFailedLayoutLocalPublishing() throws Exception {
		try {
			StagingUtil.publishLayouts(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.nextInt(), false, new long[0],
				StagingUtil.getStagingParameters(), new Date(), new Date());
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t);
			}
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_LAYOUT_LOCAL_FAILED));
	}

	@Test
	public void testFailedPortletExport() throws Exception {
		PortletExporter portletExporter = PortletExporter.getInstance();

		try {
			portletExporter.exportPortletInfoAsFile(
				RandomTestUtil.nextLong(), _group.getGroupId(),
				StringPool.BLANK, StagingUtil.getStagingParameters(),
				new Date(), new Date());
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t);
			}
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED));
	}

	@Test
	public void testFailedPortletImport() throws Exception {
		PortletImporter portletImporter = PortletImporter.getInstance();

		try {
			portletImporter.importPortletInfo(
				TestPropsValues.getUserId(), 0, 0, StringPool.BLANK,
				StagingUtil.getStagingParameters(), null);
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t);
			}
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_FAILED));
	}

	@Test
	public void testFailedPortletLocalPublishing() throws Exception {
		PortletRequestImpl portletRequest = PowerMockito.mock(
			PortletRequestImpl.class);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.USER_ID, TestPropsValues.getUserId());

		when(
			portletRequest.getHttpServletRequest()
		).thenReturn(
			mockHttpServletRequest
		);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getDefault());

		when(
			portletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		try {
			StagingUtil.copyPortlet(
				portletRequest, _group.getGroupId(), _liveGroup.getGroupId(), 0,
				0, StringPool.BLANK);
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t);
			}
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_LOCAL_FAILED));
	}

	@Test
	public void testSuccessfulLayoutLocalPublishing() throws Exception {
		Date endDate = new Date();
		Date startDate = new Date(endDate.getTime() - Time.HOUR);

		LayoutTestUtil.addLayout(_group, false);

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_liveGroup.getGroupId(), false, (long[])null,
			StagingUtil.getStagingParameters(), startDate, endDate);

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