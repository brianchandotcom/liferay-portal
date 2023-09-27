/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import java.sql.Connection;

import javax.sql.DataSource;

import org.quartz.utils.ConnectionProvider;

/**
 * @author Bruno Farache
 */
public class QuartzConnectionProvider implements ConnectionProvider {

	@Override
	public Connection getConnection() {
		Connection connection = null;

		try {
			DataSource dataSource = InfrastructureUtil.getDataSource();

			connection = dataSource.getConnection();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return connection;
	}

	@Override
	public void shutdown() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		QuartzConnectionProvider.class);

}