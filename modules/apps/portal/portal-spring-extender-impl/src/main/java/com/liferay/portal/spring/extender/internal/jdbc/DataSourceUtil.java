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