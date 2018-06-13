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
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true)
public class ServletContextUtil {

	public static GroupLocalService getGroupLocalService() {
		return _instance._groupLocalService;
	}

	public static MapProvider getMapProvider(String mapProviderKey) {
		return _instance._mapProviders.getService(mapProviderKey);
	}

	public static Collection<MapProvider> getMapProviders() {
		return _instance._mapProviders.values();
	}

	public static final ServletContext getServletContext() {
		return _instance._getServletContext();
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_mapProviders = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, MapProvider.class, null,
			new ServiceReferenceMapper<String, MapProvider>() {

				@Override
				public void map(
					ServiceReference<MapProvider> serviceReference,
					ServiceReferenceMapper.Emitter<String> emitter) {

					MapProvider mapProvider = bundleContext.getService(
						serviceReference);

					emitter.emit(mapProvider.getKey());

					bundleContext.ungetService(serviceReference);
				}

			});

		_instance = this;
	}

	@Deactivate
	protected void deactivate() {
		_instance = null;

		_mapProviders.close();
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.map.taglib)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _getServletContext() {
		return _servletContext;
	}

	private static ServletContextUtil _instance;

	@Reference
	private GroupLocalService _groupLocalService;

	private ServiceTrackerMap<String, MapProvider> _mapProviders;
	private ServletContext _servletContext;

}