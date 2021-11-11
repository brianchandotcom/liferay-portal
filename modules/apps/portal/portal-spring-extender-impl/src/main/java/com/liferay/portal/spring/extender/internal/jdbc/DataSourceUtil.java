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
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

/**
 * @author Preston Crary
 */
public class DataSourceUtil {

	public static DataSource getProviderDataSource(
		ClassLoader extendeeClassLoader) {

		DataSource dataSource = _dataSources.get(extendeeClassLoader);

		if (dataSource != null) {
			return dataSource;
		}

		ServiceLoader<DataSourceProvider> serviceLoader = ServiceLoader.load(
			DataSourceProvider.class, extendeeClassLoader);

		Iterator<DataSourceProvider> iterator = serviceLoader.iterator();

		if (iterator.hasNext()) {
			DataSourceProvider dataSourceProvider = iterator.next();

			return dataSourceProvider.getDataSource();
		}

		return InfrastructureUtil.getDataSource();
	}

	public static DataSource getSpringDataSource(
		ClassLoader extendeeClassLoader) {

		DataSource dataSource = _dataSources.get(extendeeClassLoader);

		if (dataSource != null) {
			return dataSource;
		}

		CountDownLatch countDownLatch =
			_dataSourcesCountDownLatchMap.computeIfAbsent(
				extendeeClassLoader, key -> new CountDownLatch(1));

		try {
			countDownLatch.await();
		}
		catch (InterruptedException interruptedException) {
		}

		return _dataSources.get(extendeeClassLoader);
	}

	public static void setSpringDataSource(
		ClassLoader extendeeClassLoader, DataSource dataSource) {

		_dataSourcesCountDownLatchMap.computeIfPresent(
			extendeeClassLoader,
			(key, countDownLatch) -> {
				countDownLatch.countDown();

				return null;
			});

		_dataSources.put(extendeeClassLoader, dataSource);
	}

	public static void unsetSpringDataSource(ClassLoader extendeeClassLoader) {
		_dataSources.remove(extendeeClassLoader);
	}

	private static final Map<ClassLoader, DataSource> _dataSources =
		new ConcurrentHashMap<>();
	private static final Map<ClassLoader, CountDownLatch>
		_dataSourcesCountDownLatchMap = new ConcurrentHashMap<>();

}