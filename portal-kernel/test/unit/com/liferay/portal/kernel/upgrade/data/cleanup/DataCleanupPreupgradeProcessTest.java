/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.data.cleanup;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jorge Avalos
 */
public class DataCleanupPreupgradeProcessTest {

	@Test
	public void testGetLayeredDataCleanupPreupgradeProcessesWithCircularDependency() {
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess1 =
			_createDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess2 =
			_createDataCleanupPreupgradeProcess();

		Map<DataCleanupPreupgradeProcess, List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesMap =
				HashMapBuilder.
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>>put(
						dataCleanupPreupgradeProcess1,
						DataCleanupPreupgradeProcess.dependsOn(
							dataCleanupPreupgradeProcess2)
					).put(
						dataCleanupPreupgradeProcess2,
						DataCleanupPreupgradeProcess.dependsOn(
							dataCleanupPreupgradeProcess1)
					).build();

		try {
			DataCleanupPreupgradeProcess.
				getLayeredDataCleanupPreupgradeProcesses(
					dataCleanupPreupgradeProcessesMap);

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Circular dependency", illegalStateException.getMessage());
		}
	}

	@Test
	public void testGetLayeredDataCleanupPreupgradeProcessesWithDiamondDependency() {
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess1 =
			_createDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess2 =
			_createDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess3 =
			_createDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess4 =
			_createDataCleanupPreupgradeProcess();

		Map<DataCleanupPreupgradeProcess, List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesMap =
				HashMapBuilder.
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>>put(
						dataCleanupPreupgradeProcess1,
						DataCleanupPreupgradeProcess.dependsOn()
					).put(
						dataCleanupPreupgradeProcess2,
						DataCleanupPreupgradeProcess.dependsOn(
							dataCleanupPreupgradeProcess1)
					).put(
						dataCleanupPreupgradeProcess3,
						DataCleanupPreupgradeProcess.dependsOn(
							dataCleanupPreupgradeProcess1)
					).put(
						dataCleanupPreupgradeProcess4,
						DataCleanupPreupgradeProcess.dependsOn(
							dataCleanupPreupgradeProcess2,
							dataCleanupPreupgradeProcess3)
					).build();

		List<List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesList =
				DataCleanupPreupgradeProcess.
					getLayeredDataCleanupPreupgradeProcesses(
						dataCleanupPreupgradeProcessesMap);

		Assert.assertEquals(
			dataCleanupPreupgradeProcessesList.toString(), 3,
			dataCleanupPreupgradeProcessesList.size());

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses1 =
			dataCleanupPreupgradeProcessesList.get(0);

		Assert.assertEquals(
			dataCleanupPreupgradeProcesses1.toString(), 1,
			dataCleanupPreupgradeProcesses1.size());
		Assert.assertSame(
			dataCleanupPreupgradeProcess1,
			dataCleanupPreupgradeProcesses1.get(0));

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses2 =
			dataCleanupPreupgradeProcessesList.get(1);

		Assert.assertEquals(
			dataCleanupPreupgradeProcesses2.toString(), 2,
			dataCleanupPreupgradeProcesses2.size());
		Assert.assertTrue(
			dataCleanupPreupgradeProcesses2.contains(
				dataCleanupPreupgradeProcess2));
		Assert.assertTrue(
			dataCleanupPreupgradeProcesses2.contains(
				dataCleanupPreupgradeProcess3));

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses3 =
			dataCleanupPreupgradeProcessesList.get(2);

		Assert.assertEquals(
			dataCleanupPreupgradeProcesses3.toString(), 1,
			dataCleanupPreupgradeProcesses3.size());
		Assert.assertSame(
			dataCleanupPreupgradeProcess4,
			dataCleanupPreupgradeProcesses3.get(0));
	}

	@Test
	public void testGetLayeredDataCleanupPreupgradeProcessesWithLinearChain() {
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess1 =
			_createDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess2 =
			_createDataCleanupPreupgradeProcess();
		DataCleanupPreupgradeProcess dataCleanupPreupgradeProcess3 =
			_createDataCleanupPreupgradeProcess();

		Map<DataCleanupPreupgradeProcess, List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesMap =
				HashMapBuilder.
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>>put(
						dataCleanupPreupgradeProcess1,
						DataCleanupPreupgradeProcess.dependsOn()
					).put(
						dataCleanupPreupgradeProcess2,
						DataCleanupPreupgradeProcess.dependsOn(
							dataCleanupPreupgradeProcess1)
					).put(
						dataCleanupPreupgradeProcess3,
						DataCleanupPreupgradeProcess.dependsOn(
							dataCleanupPreupgradeProcess2)
					).build();

		List<List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesList =
				DataCleanupPreupgradeProcess.
					getLayeredDataCleanupPreupgradeProcesses(
						dataCleanupPreupgradeProcessesMap);

		Assert.assertEquals(
			dataCleanupPreupgradeProcessesList.toString(), 3,
			dataCleanupPreupgradeProcessesList.size());

		for (List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses :
				dataCleanupPreupgradeProcessesList) {

			Assert.assertEquals(
				dataCleanupPreupgradeProcesses.toString(), 1,
				dataCleanupPreupgradeProcesses.size());
		}

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses1 =
			dataCleanupPreupgradeProcessesList.get(0);

		Assert.assertSame(
			dataCleanupPreupgradeProcess1,
			dataCleanupPreupgradeProcesses1.get(0));

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses2 =
			dataCleanupPreupgradeProcessesList.get(1);

		Assert.assertSame(
			dataCleanupPreupgradeProcess2,
			dataCleanupPreupgradeProcesses2.get(0));

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses3 =
			dataCleanupPreupgradeProcessesList.get(2);

		Assert.assertSame(
			dataCleanupPreupgradeProcess3,
			dataCleanupPreupgradeProcesses3.get(0));
	}

	@Test
	public void testGetLayeredDataCleanupPreupgradeProcessesWithMissingDependency() {
		Map<DataCleanupPreupgradeProcess, List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesMap =
				HashMapBuilder.
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>>put(
						_createDataCleanupPreupgradeProcess(),
						DataCleanupPreupgradeProcess.dependsOn(
							_createDataCleanupPreupgradeProcess())
					).build();

		try {
			DataCleanupPreupgradeProcess.
				getLayeredDataCleanupPreupgradeProcesses(
					dataCleanupPreupgradeProcessesMap);

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			String message = illegalStateException.getMessage();

			Assert.assertTrue(message.startsWith("Missing dependency "));
		}
	}

	@Test
	public void testGetLayeredDataCleanupPreupgradeProcessesWithNoDependencies() {
		Map<DataCleanupPreupgradeProcess, List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesMap =
				HashMapBuilder.
					<DataCleanupPreupgradeProcess,
					 List<DataCleanupPreupgradeProcess>>put(
						_createDataCleanupPreupgradeProcess(),
						DataCleanupPreupgradeProcess.dependsOn()
					).put(
						_createDataCleanupPreupgradeProcess(),
						DataCleanupPreupgradeProcess.dependsOn()
					).put(
						_createDataCleanupPreupgradeProcess(),
						DataCleanupPreupgradeProcess.dependsOn()
					).build();

		List<List<DataCleanupPreupgradeProcess>>
			dataCleanupPreupgradeProcessesList =
				DataCleanupPreupgradeProcess.
					getLayeredDataCleanupPreupgradeProcesses(
						dataCleanupPreupgradeProcessesMap);

		Assert.assertEquals(
			dataCleanupPreupgradeProcessesList.toString(), 1,
			dataCleanupPreupgradeProcessesList.size());

		List<DataCleanupPreupgradeProcess> dataCleanupPreupgradeProcesses1 =
			dataCleanupPreupgradeProcessesList.get(0);

		Assert.assertEquals(
			dataCleanupPreupgradeProcesses1.toString(), 3,
			dataCleanupPreupgradeProcesses1.size());
	}

	private DataCleanupPreupgradeProcess _createDataCleanupPreupgradeProcess() {
		return new DataCleanupPreupgradeProcess() {

			@Override
			protected void doUpgrade() {
			}

		};
	}

}