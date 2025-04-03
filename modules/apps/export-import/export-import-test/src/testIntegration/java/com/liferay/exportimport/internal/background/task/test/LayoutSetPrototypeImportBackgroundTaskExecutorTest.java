/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Magdalena Jedraszak
 */
@RunWith(Arquillian.class)
public class LayoutSetPrototypeImportBackgroundTaskExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	@TestInfo("LPS-166515")
	public void testCleanUpPreviousBackgroundTasks() throws Exception {
		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());

		UserTestUtil.setUser(TestPropsValues.getUser());

		group = GroupTestUtil.addGroup();

		File larFile = File.createTempFile("corrupt", ".lar");

		try (Writer writer = new FileWriter(larFile)) {
			writer.write(RandomTestUtil.randomString());
		}

		long backgroundTaskId = 0;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			backgroundTaskId =
				ExportImportLocalServiceUtil.
					importLayoutSetPrototypeInBackground(
						TestPropsValues.getUserId(),
						ExportImportConfigurationLocalServiceUtil.
							addExportImportConfiguration(
								TestPropsValues.getUserId(), group.getGroupId(),
								RandomTestUtil.randomString(),
								RandomTestUtil.randomString(), 0, null,
								WorkflowConstants.STATUS_DRAFT,
								ServiceContextTestUtil.getServiceContext()),
						larFile);
		}

		Thread.sleep(2000);

		BackgroundTask failedBackgroundTask =
			BackgroundTaskManagerUtil.getBackgroundTask(backgroundTaskId);

		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_FAILED,
			failedBackgroundTask.getStatus());

		List<BackgroundTask> backgroundTasks =
			BackgroundTaskManagerUtil.getBackgroundTasks(
				group.getGroupId(),
				BackgroundTaskExecutorNames.
					LAYOUT_SET_PROTOTYPE_IMPORT_BACKGROUND_TASK_EXECUTOR);

		Assert.assertEquals(
			backgroundTasks.toString(), 1, backgroundTasks.size());

		ExportImportConfiguration exportExportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					TestPropsValues.getUserId(), group.getGroupId(),
					StringPool.BLANK, StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), group.getGroupId(), true,
							new long[0],
							LinkedHashMapBuilder.put(
								PortletDataHandlerKeys.PORTLET_CONFIGURATION,
								new String[] {Boolean.TRUE.toString()}
							).build()),
					WorkflowConstants.STATUS_DRAFT,
					ServiceContextTestUtil.getServiceContext());

		larFile = ExportImportServiceUtil.exportLayoutsAsFile(
			exportExportImportConfiguration);

		ExportImportConfiguration importExportExportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					TestPropsValues.getUserId(), group.getGroupId(),
					StringPool.BLANK, StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(), group.getGroupId(), true,
							new long[0],
							LinkedHashMapBuilder.put(
								PortletDataHandlerKeys.PORTLET_CONFIGURATION,
								new String[] {Boolean.TRUE.toString()}
							).build()),
					WorkflowConstants.STATUS_DRAFT,
					ServiceContextTestUtil.getServiceContext());

		ExportImportLocalServiceUtil.importLayoutSetPrototypeInBackground(
			TestPropsValues.getUserId(), importExportExportImportConfiguration,
			larFile);

		Thread.sleep(2000);

		backgroundTasks = BackgroundTaskManagerUtil.getBackgroundTasks(
			group.getGroupId(),
			BackgroundTaskExecutorNames.
				LAYOUT_SET_PROTOTYPE_IMPORT_BACKGROUND_TASK_EXECUTOR);

		Assert.assertEquals(
			backgroundTasks.toString(), 0, backgroundTasks.size());
	}

	@DeleteAfterTestRun
	protected Group group;

}