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

package com.liferay.frontend.taglib.clay.internal;

import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSource;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(immediate = true, service = {})
public class ClayTagDataSourceProvider {

	public static <T> ClayTagDataSource<T> getClayTagDataSource(
		String clayTagDataSourceKey) {

		if (_clayTagDataSourceProvider == null) {
			_log.error(
				"Unable to get list of Clay tag data sources for key " +
					clayTagDataSourceKey);

			return null;
		}

		ServiceTrackerMap<String, ClayTagDataSource<?>> clayTagDataSources =
			_clayTagDataSourceProvider._clayTagDataSources;

		return (ClayTagDataSource<T>)clayTagDataSources.getService(
			clayTagDataSourceKey);
	}

	public ClayTagDataSourceProvider() {
		_clayTagDataSourceProvider = this;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_clayTagDataSources = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<ClayTagDataSource<?>>)(Class<?>)ClayTagDataSource.class,
			"(clay.tag.data.source.key=*)",
			new PropertyServiceReferenceMapper<>("clay.tag.data.source.key"),
			new PropertyServiceReferenceComparator<>("service.ranking"));
	}

	@Deactivate
	protected void deactivate() {
		_clayTagDataSources.close();

		_clayTagDataSources = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClayTagDataSourceProvider.class);

	private static ClayTagDataSourceProvider _clayTagDataSourceProvider;

	private ServiceTrackerMap<String, ClayTagDataSource<?>> _clayTagDataSources;

}