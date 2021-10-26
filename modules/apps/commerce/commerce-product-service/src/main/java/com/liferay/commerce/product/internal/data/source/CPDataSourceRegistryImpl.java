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

package com.liferay.commerce.product.internal.data.source;

import com.liferay.commerce.product.data.source.CPDataSource;
import com.liferay.commerce.product.data.source.CPDataSourceRegistry;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true, service = CPDataSourceRegistry.class
)
public class CPDataSourceRegistryImpl implements CPDataSourceRegistry {

	@Override
	public CPDataSource getCPDataSource(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		for (CPDataSource cpDataSource : _serviceTrackerList) {
			if (key.equals(cpDataSource.getName())) {
				return cpDataSource;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"No commerce product data source registered with key " + key);
		}

		return null;
	}

	@Override
	public List<CPDataSource> getCPDataSources() {
		List<CPDataSource> cpDataSources = new ArrayList<>();

		for (CPDataSource cpDataSource : _serviceTrackerList) {
			if (Validator.isNotNull(cpDataSource.getName())) {
				cpDataSources.add(cpDataSource);
			}
		}

		return Collections.unmodifiableList(cpDataSources);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, CPDataSource.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDataSourceRegistryImpl.class);

	@Reference
	private Portal _portal;

	private ServiceTrackerList<CPDataSource> _serviceTrackerList;

}