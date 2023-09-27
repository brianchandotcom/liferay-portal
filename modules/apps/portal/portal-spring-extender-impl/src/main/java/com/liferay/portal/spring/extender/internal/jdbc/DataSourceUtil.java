/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.extender.internal.jdbc;

import com.liferay.portal.kernel.dao.jdbc.DataSourceProvider;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.sql.DataSource;

/**
 * @author Preston Crary
 */
public class DataSourceUtil {

	public static DataSource getDataSource(ClassLoader extendeeClassLoader) {
		ServiceLoader<DataSourceProvider> serviceLoader = ServiceLoader.load(
			DataSourceProvider.class, extendeeClassLoader);

		Iterator<DataSourceProvider> iterator = serviceLoader.iterator();

		if (iterator.hasNext()) {
			DataSourceProvider dataSourceProvider = iterator.next();

			return dataSourceProvider.getDataSource();
		}

		return InfrastructureUtil.getDataSource();
	}

}