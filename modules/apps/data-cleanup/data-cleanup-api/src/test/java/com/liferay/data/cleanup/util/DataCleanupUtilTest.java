/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.util;

import com.liferay.data.cleanup.DataCleanup;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DataCleanupUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testModuleDataCleanup() {
		_testDataCleanup(
			DataCleanup.MODULE_DATA_CLEANUP,
			DataCleanupUtil::getModuleDataCleanups);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterWrongTypeDataCleanup() {
		DataCleanupUtil.registerDataCleanup(
			new DataCleanup() {

				@Override
				public String getDescription() {
					return null;
				}

				@Override
				public String getLabel() {
					return null;
				}

				@Override
				public String getServletContextName() {
					return null;
				}

				@Override
				public String getType() {
					return "wrong type";
				}

				@Override
				protected void doCleanup() {
				}

			});
	}

	@Test
	public void testSystemDataCleanup() {
		_testDataCleanup(
			DataCleanup.SYSTEM_DATA_CLEANUP,
			DataCleanupUtil::getSystemDataCleanups);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnregisterWrongTypeDataCleanup() {
		DataCleanupUtil.unregisterDataCleanup(
			new DataCleanup() {

				@Override
				public String getDescription() {
					return null;
				}

				@Override
				public String getLabel() {
					return null;
				}

				@Override
				public String getServletContextName() {
					return null;
				}

				@Override
				public String getType() {
					return "wrong type";
				}

				@Override
				protected void doCleanup() {
				}

			});
	}

	private void _testDataCleanup(
		String type, Supplier<List<DataCleanup>> dataCleanupsSupplier) {

		DataCleanup dataCleanup = new DataCleanup() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public String getLabel() {
				return null;
			}

			@Override
			public String getServletContextName() {
				return null;
			}

			@Override
			public String getType() {
				return type;
			}

			@Override
			protected void doCleanup() {
			}

		};

		DataCleanupUtil.registerDataCleanup(dataCleanup);

		List<DataCleanup> dataCleanups = dataCleanupsSupplier.get();

		Assert.assertEquals(dataCleanups.toString(), 1, dataCleanups.size());

		Assert.assertEquals(dataCleanup, dataCleanups.get(0));

		DataCleanupUtil.unregisterDataCleanup(dataCleanup);

		dataCleanups = dataCleanupsSupplier.get();

		Assert.assertTrue(dataCleanups.isEmpty());
	}

}