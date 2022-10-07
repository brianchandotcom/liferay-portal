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

package com.liferay.portal.vulcan.internal.resource;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.resource.ResourceLocator;
import com.liferay.portal.vulcan.resource.ResourceLocatorFactory;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 * @author Alejandro Tardín
 */
@Component(service = ResourceLocatorFactory.class)
public class ResourceLocatorFactoryImpl implements ResourceLocatorFactory {

	@Override
	public ResourceLocator create(
		HttpServletRequest httpServletRequest, User user) {

		return new ResourceLocator() {

			@Override
			public Object locate(String resourceLocatorKey) {
				ServiceTrackerMap<String, Builder> serviceTrackerMap =
					_getServiceTrackerMap();

				Builder builder = serviceTrackerMap.getService(
					resourceLocatorKey);

				if (builder == null) {
					return null;
				}

				return builder.build(httpServletRequest, user);
			}

		};
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected synchronized void deactivate() {
		ServiceTrackerMap<String, Builder> serviceTrackerMap =
			_serviceTrackerMap;

		if (serviceTrackerMap != null) {
			serviceTrackerMap.close();
		}
	}

	private ServiceTrackerMap<String, Builder> _getServiceTrackerMap() {
		ServiceTrackerMap<String, Builder> serviceTrackerMap =
			_serviceTrackerMap;

		if (serviceTrackerMap != null) {
			return _serviceTrackerMap;
		}

		synchronized (this) {
			if (_serviceTrackerMap != null) {
				return _serviceTrackerMap;
			}

			serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, null, "resource.locator.key",
				new ServiceTrackerCustomizer<Object, Builder>() {

					@Override
					public Builder addingService(
						ServiceReference<Object> serviceReference) {

						Object factoryImpl = _bundleContext.getService(
							serviceReference);

						Class<?> resourceFactoryImplClass =
							factoryImpl.getClass();

						try {
							Method createMethod =
								resourceFactoryImplClass.getMethod("create");

							Class<?> resourceBuilderInterface =
								createMethod.getReturnType();

							return new Builder(
								factoryImpl, createMethod,
								resourceBuilderInterface.getMethod("build"),
								resourceBuilderInterface.getMethod(
									"httpServletRequest",
									HttpServletRequest.class),
								resourceBuilderInterface.getMethod(
									"user", User.class));
						}
						catch (NoSuchMethodException noSuchMethodException) {
							_log.error(noSuchMethodException);

							return null;
						}
					}

					@Override
					public void modifiedService(
						ServiceReference<Object> serviceReference,
						Builder builder) {
					}

					@Override
					public void removedService(
						ServiceReference<Object> serviceReference,
						Builder builder) {

						_bundleContext.ungetService(serviceReference);
					}

				});

			_serviceTrackerMap = serviceTrackerMap;
		}

		return serviceTrackerMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceLocatorFactoryImpl.class);

	private BundleContext _bundleContext;
	private volatile ServiceTrackerMap<String, Builder> _serviceTrackerMap;

	private static class Builder {

		public Object build(HttpServletRequest httpServletRequest, User user) {
			try {
				Object builder = _createMethod.invoke(_factoryImpl);

				if (httpServletRequest != null) {
					_httpServletRequestMethod.invoke(
						builder, httpServletRequest);
				}

				if (user != null) {
					_userMethod.invoke(builder, user);
				}

				return _buildMethod.invoke(builder);
			}
			catch (ReflectiveOperationException reflectiveOperationException) {
				_log.error(reflectiveOperationException);

				return null;
			}
		}

		private Builder(
			Object factoryImpl, Method createMethod, Method buildMethod,
			Method httpServletRequestMethod, Method userMethod) {

			_factoryImpl = factoryImpl;

			_createMethod = createMethod;
			_buildMethod = buildMethod;
			_httpServletRequestMethod = httpServletRequestMethod;
			_userMethod = userMethod;

			_createMethod.setAccessible(true);
			_buildMethod.setAccessible(true);
			_httpServletRequestMethod.setAccessible(true);
			_userMethod.setAccessible(true);
		}

		private final Method _buildMethod;
		private final Method _createMethod;
		private final Object _factoryImpl;
		private final Method _httpServletRequestMethod;
		private final Method _userMethod;

	}

}