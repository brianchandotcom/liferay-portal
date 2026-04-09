/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.Serializable;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Magdalena Jedraszak
 */
public class LayoutExportBackgroundTaskExecutorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_backgroundTaskManager = Mockito.mock(BackgroundTaskManager.class);
		_exportImportLocalService = Mockito.mock(
			ExportImportLocalService.class);

		_layoutExportBackgroundTaskExecutor =
			new LayoutExportBackgroundTaskExecutor();

		ReflectionTestUtil.setFieldValue(
			_layoutExportBackgroundTaskExecutor, "_backgroundTaskManager",
			_backgroundTaskManager);

		ReflectionTestUtil.setFieldValue(
			_layoutExportBackgroundTaskExecutor, "_exportImportLocalService",
			_exportImportLocalService);

		_timeMockedStatic = Mockito.mockStatic(Time.class);

		_timeMockedStatic.when(
			Time::getTimestamp
		).thenReturn(
			"999988887777"
		);
	}

	@After
	public void tearDown() {
		_timeMockedStatic.close();
	}

	@Test
	public void test() throws Exception {
		BackgroundTask backgroundTask = Mockito.mock(BackgroundTask.class);
		long backgroundTaskId = RandomTestUtil.randomLong();

		Mockito.when(
			backgroundTask.getBackgroundTaskId()
		).thenReturn(
			backgroundTaskId
		);

		long userId = RandomTestUtil.randomLong();

		Map<String, Serializable> settingsMap =
			HashMapBuilder.<String, Serializable>put(
				"userId", userId
			).build();

		ExportImportConfiguration exportImportConfiguration = Mockito.mock(
			ExportImportConfiguration.class);

		Mockito.when(
			exportImportConfiguration.getSettingsMap()
		).thenReturn(
			settingsMap
		);

		Mockito.when(
			exportImportConfiguration.getName()
		).thenReturn(
			"My Test Layout Export"
		);

		LayoutExportBackgroundTaskExecutor layoutExportBackgroundTaskExecutor =
			Mockito.spy(_layoutExportBackgroundTaskExecutor);

		Mockito.doReturn(
			exportImportConfiguration
		).when(
			layoutExportBackgroundTaskExecutor
		).getExportImportConfiguration(
			backgroundTask
		);

		File larFile = new File("file.lar");

		Mockito.when(
			_exportImportLocalService.exportLayoutsAsFile(
				exportImportConfiguration)
		).thenReturn(
			larFile
		);

		Assert.assertEquals(
			BackgroundTaskResult.SUCCESS,
			layoutExportBackgroundTaskExecutor.execute(backgroundTask));

		ArgumentCaptor<String> sourceFileNameCaptor = ArgumentCaptor.forClass(
			String.class);
		ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			_backgroundTaskManager
		).addBackgroundTaskAttachment(
			Mockito.eq(userId), Mockito.eq(backgroundTaskId),
			sourceFileNameCaptor.capture(), titleCaptor.capture(),
			Mockito.eq(larFile)
		);

		Assert.assertEquals(
			"My_Test_Layout_Export.lar", titleCaptor.getValue());

		Assert.assertEquals(
			"My_Test_Layout_Export-999988887777.lar",
			sourceFileNameCaptor.getValue());
	}

	private BackgroundTaskManager _backgroundTaskManager;
	private ExportImportLocalService _exportImportLocalService;
	private LayoutExportBackgroundTaskExecutor
		_layoutExportBackgroundTaskExecutor;
	private MockedStatic<Time> _timeMockedStatic;

}