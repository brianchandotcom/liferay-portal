/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.report;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.internal.recorder.UpgradeRecorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Mariano Álvaro Sáiz
 */
public class UpgradeReportTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
		_dbUpgraderMockedStatic.close();
	}

	@Test
	public void testGetReportDataDiagnostics() {
		Mockito.when(
			_upgradeRecorder.getUpgradeProcessMessages()
		).thenReturn(
			HashMapBuilder.<String, ArrayList<String>>put(
				UpgradeProcess.class.getName(),
				new ArrayList<String>() {
					{
						add(
							"Completed upgrade process " +
								"fr.test.TestUpgradeProcess in 30000000 ms");
						add(
							"Completed com.test.TestUpgradeProcess in " +
								"20000000 ms");
					}
				}
			).build()
		);

		Map<String, Object> reportDataDiagnostics = ReflectionTestUtil.invoke(
			new UpgradeReport(), "_getReportDataDiagnostics",
			new Class<?>[] {UpgradeRecorder.class}, _upgradeRecorder);

		List<?> runningUpgradeProcesses = (List<?>)reportDataDiagnostics.get(
			"longest.upgrade.processes");

		Assert.assertTrue(runningUpgradeProcesses.size() == 2);

		Assert.assertEquals(
			"fr.test.TestUpgradeProcess took 30000000 ms to complete\n",
			runningUpgradeProcesses.get(
				0
			).toString());
		Assert.assertEquals(
			"com.test.TestUpgradeProcess took 20000000 ms to complete\n",
			runningUpgradeProcesses.get(
				1
			).toString());
	}

	private static final MockedStatic<DBUpgrader> _dbUpgraderMockedStatic =
		Mockito.mockStatic(DBUpgrader.class);

	@Mock
	private UpgradeRecorder _upgradeRecorder;

}