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

package com.liferay.map.taglib.internal.servlet;

import com.liferay.map.MapProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Jürgen Kappler
 */
public class ServletContextUtil {

	public static GroupLocalService getGroupLocalService() {
		return _groupLocalServiceSnapshot.get();
	}

	public static MapProvider getMapProvider(String mapProviderKey) {
		return _mapProviders.getService(mapProviderKey);
	}

	public static Collection<MapProvider> getMapProviders() {
		return _mapProviders.values();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	private static final Snapshot<GroupLocalService>
		_groupLocalServiceSnapshot = new Snapshot<>(
			ServletContextUtil.class, GroupLocalService.class);
	private static final ServiceTrackerMap<String, MapProvider> _mapProviders;
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.map.taglib)");

	static {
		Bundle bundle = FrameworkUtil.getBundle(ServletContextUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_mapProviders = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, MapProvider.class, null,
			ServiceReferenceMapperFactory.createFromFunction(
				bundleContext, MapProvider::getKey));
	}

}