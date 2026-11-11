/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.migration.importer.jdbc;

import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.List;

import org.junit.Test;

import org.mockito.MockedConstruction;
import org.mockito.Mockito;

/**
 * @author István András Dézsi
 */
public class DataSourceFactoryUtilTest {

	@Test
	@TestInfo("LPD-110028")
	public void testInitDataSource() throws Exception {
		try (MockedConstruction<HikariConfig> hikariConfigMockedConstruction =
				Mockito.mockConstruction(HikariConfig.class);
			MockedConstruction<HikariDataSource>
				hikariDataSourceMockedConstruction = Mockito.mockConstruction(
					HikariDataSource.class)) {

			DataSourceFactoryUtil.initDataSource(
				"jdbc:mysql://localhost/lportal", RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

			List<HikariConfig> hikariConfigs =
				hikariConfigMockedConstruction.constructed();

			HikariConfig hikariConfig = hikariConfigs.get(0);

			Mockito.verify(
				hikariConfig
			).setDriverClassName(
				"org.mariadb.jdbc.Driver"
			);

			Mockito.verify(
				hikariConfig
			).setJdbcUrl(
				"jdbc:mariadb://localhost/lportal"
			);
		}
	}

}