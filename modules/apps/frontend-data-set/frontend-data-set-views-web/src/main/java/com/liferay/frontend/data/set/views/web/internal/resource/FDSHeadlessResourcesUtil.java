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

package com.liferay.frontend.data.set.views.web.internal.resource;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marko Cikos
 */
@Component(service = {})
public class FDSHeadlessResourcesUtil {

	public static List<FDSHeadlessResource> getFDSHeadlessResources() {
		return new ArrayList<>(_fdsHeadlessResourcesMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		Filter filter = bundleContext.createFilter(
			"(osgi.jaxrs.resource=true)");

		_serviceTracker = new ServiceTracker<>(
			bundleContext, filter,
			new FDSHeadlessResourceServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final Map<String, FDSHeadlessResource>
		_fdsHeadlessResourcesMap = new ConcurrentHashMap<>();
	private static ServiceTracker<Object, Object> _serviceTracker;

	private class FDSHeadlessResourceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			if (entityClassName != null) {
				String[] entityClassNameParts = StringUtil.split(
					entityClassName, StringPool.PERIOD);

				_fdsHeadlessResourcesMap.put(
					entityClassName,
					new FDSHeadlessResource(
						_getFDSHeadlessResourceBundleLabel(serviceReference),
						entityClassName,
						entityClassNameParts[entityClassNameParts.length - 1],
						entityClassNameParts[entityClassNameParts.length - 2].
							replaceAll(
								StringPool.UNDERLINE, StringPool.PERIOD)));
			}

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object object) {

			removedService(serviceReference, object);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object object) {

			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			if (entityClassName != null) {
				_fdsHeadlessResourcesMap.remove(entityClassName);
			}

			_bundleContext.ungetService(serviceReference);
		}

		private FDSHeadlessResourceServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private String _getFDSHeadlessResourceBundleLabel(
			ServiceReference<Object> serviceReference) {

			Object object = _bundleContext.getService(serviceReference);

			Bundle bundle = FrameworkUtil.getBundle(object.getClass());

			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			String bundleName = GetterUtil.getString(
				headers.get(Constants.BUNDLE_NAME));

			return bundleName.substring(
				0, bundleName.lastIndexOf(StringPool.SPACE));
		}

		private final BundleContext _bundleContext;

	}

}