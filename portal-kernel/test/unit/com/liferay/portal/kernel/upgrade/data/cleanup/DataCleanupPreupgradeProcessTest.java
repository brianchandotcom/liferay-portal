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
	public void testGetWavedDataCleanupPreupgradeProcessesWithCircularDependency() {
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
			DataCleanupPreupgradeProcess.getWavedDataCleanupPreupgradeProcesses(
				dataCleanupPreupgradeProcessesMap);

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Circular dependency", illegalStateException.getMessage());
		}
	}

	@Test
	public void testGetWavedDataCleanupPreupgradeProcessesWithDiamondDependency() {
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

		List<List<DataCleanupPreupgradeProcess>> waves =
			DataCleanupPreupgradeProcess.getWavedDataCleanupPreupgradeProcesses(
				dataCleanupPreupgradeProcessesMap);

		Assert.assertEquals(waves.toString(), 3, waves.size());

		List<DataCleanupPreupgradeProcess> wave1 = waves.get(0);

		Assert.assertEquals(wave1.toString(), 1, wave1.size());
		Assert.assertSame(dataCleanupPreupgradeProcess1, wave1.get(0));

		List<DataCleanupPreupgradeProcess> wave2 = waves.get(1);

		Assert.assertEquals(wave2.toString(), 2, wave2.size());
		Assert.assertTrue(wave2.contains(dataCleanupPreupgradeProcess2));
		Assert.assertTrue(wave2.contains(dataCleanupPreupgradeProcess3));

		List<DataCleanupPreupgradeProcess> wave3 = waves.get(2);

		Assert.assertEquals(wave3.toString(), 1, wave3.size());
		Assert.assertSame(dataCleanupPreupgradeProcess4, wave3.get(0));
	}

	@Test
	public void testGetWavedDataCleanupPreupgradeProcessesWithLinearChain() {
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

		List<List<DataCleanupPreupgradeProcess>> waves =
			DataCleanupPreupgradeProcess.getWavedDataCleanupPreupgradeProcesses(
				dataCleanupPreupgradeProcessesMap);

		Assert.assertEquals(waves.toString(), 3, waves.size());

		for (List<DataCleanupPreupgradeProcess> wave : waves) {
			Assert.assertEquals(wave.toString(), 1, wave.size());
		}

		List<DataCleanupPreupgradeProcess> wave1 = waves.get(0);

		Assert.assertSame(dataCleanupPreupgradeProcess1, wave1.get(0));

		List<DataCleanupPreupgradeProcess> wave2 = waves.get(1);

		Assert.assertSame(dataCleanupPreupgradeProcess2, wave2.get(0));

		List<DataCleanupPreupgradeProcess> wave3 = waves.get(2);

		Assert.assertSame(dataCleanupPreupgradeProcess3, wave3.get(0));
	}

	@Test
	public void testGetWavedDataCleanupPreupgradeProcessesWithMissingDependency() {
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
			DataCleanupPreupgradeProcess.getWavedDataCleanupPreupgradeProcesses(
				dataCleanupPreupgradeProcessesMap);

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			String message = illegalStateException.getMessage();

			Assert.assertTrue(message.startsWith("Missing dependency "));
		}
	}

	@Test
	public void testGetWavedDataCleanupPreupgradeProcessesWithNoDependencies() {
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

		List<List<DataCleanupPreupgradeProcess>> waves =
			DataCleanupPreupgradeProcess.getWavedDataCleanupPreupgradeProcesses(
				dataCleanupPreupgradeProcessesMap);

		Assert.assertEquals(waves.toString(), 1, waves.size());

		List<DataCleanupPreupgradeProcess> wave1 = waves.get(0);

		Assert.assertEquals(wave1.toString(), 3, wave1.size());
	}

	private DataCleanupPreupgradeProcess _createDataCleanupPreupgradeProcess() {
		return new DataCleanupPreupgradeProcess() {

			@Override
			protected void doUpgrade() {
			}

		};
	}

}