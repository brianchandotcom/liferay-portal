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

package com.liferay.portal.spring.extender.internal.bean;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.spring.extender.internal.context.ModuleApplicationContext;

import javax.sql.DataSource;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Preston Crary
 */
public class ModuleDataSourceFactory implements ApplicationContextAware {

	public DataSource getDataSource() {
		DataSource dataSource = _dataSources.getService(_symbolicName);

		if (dataSource == null) {
			dataSource = InfrastructureUtil.getDataSource();
		}

		return dataSource;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		ModuleApplicationContext moduleApplicationContext =
			(ModuleApplicationContext)applicationContext;

		BundleContext bundleContext =
			moduleApplicationContext.getBundleContext();

		Bundle bundle = bundleContext.getBundle();

		_symbolicName = bundle.getSymbolicName();
	}

	private static final ServiceTrackerMap<String, DataSource> _dataSources;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ModuleDataSourceFactory.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_dataSources = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DataSource.class, null,
			(serviceReference, emitter) -> {
				Bundle serviceReferenceBundle = serviceReference.getBundle();

				emitter.emit(serviceReferenceBundle.getSymbolicName());
			});
	}

	private String _symbolicName;

}