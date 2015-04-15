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

import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationParameterMapFactory;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lar.LayoutExporter;
import com.liferay.portal.lar.LayoutImporter;
import com.liferay.portal.lar.PortletExporter;
import com.liferay.portal.lar.PortletImporter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
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

/**
 * @author Daniel Kocsis
 */
@Sync(cleanTransaction = true)
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

		_parameterMap =
			ExportImportConfigurationParameterMapFactory.buildParameterMap();
	}

	@Test
	public void testFailedLayoutExport() throws Exception {
		LayoutExporter layoutExporter = LayoutExporter.getInstance();

		try {
			layoutExporter.exportLayoutsAsFile(
				0, new long[0], _parameterMap, new Date(), new Date());
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t, t);
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
				TestPropsValues.getUserId(), 0, _parameterMap, null);
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t, t);
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
				RandomTestUtil.nextInt(), new long[0], _parameterMap);
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t, t);
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
				StringPool.BLANK, _parameterMap, new Date(), new Date());
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t, t);
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
				_parameterMap, null);
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t, t);
			}
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_FAILED));
	}

	@Test
	public void testFailedPortletLocalPublishing() throws Exception {
		User user = TestPropsValues.getUser();

		try {
			StagingUtil.publishPortlet(
				user.getUserId(), _group.getGroupId(), _liveGroup.getGroupId(),
				0, 0, StringPool.BLANK, _parameterMap);
		}
		catch (Throwable t) {
			if (_log.isInfoEnabled()) {
				_log.info(t, t);
			}
		}

		Assert.assertTrue(
			_firedExportImportLifecycleEventsMap.containsKey(
				ExportImportLifecycleConstants.
					EVENT_PUBLICATION_PORTLET_LOCAL_FAILED));
	}

	@Test
	public void testSuccessfulLayoutLocalPublishing() throws Exception {
		LayoutTestUtil.addLayout(_group);

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_liveGroup.getGroupId(), null, _parameterMap);

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