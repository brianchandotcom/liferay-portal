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

package com.liferay.portal.vulcan.internal.resource.locator;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.resource.locator.ResourceLocator;
import com.liferay.portal.vulcan.resource.locator.ResourceLocatorFactory;

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
			public Object locate(String restContextPath, String resourceName) {
				Builder builder = _serviceTrackerMap.getService(
					restContextPath + StringPool.SLASH + resourceName);

				if (builder == null) {
					return null;
				}

				return builder.build(httpServletRequest, user);
			}

		};
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, null, "resource.locator.key",
			new ServiceTrackerCustomizer<Object, Builder>() {

				@Override
				public Builder addingService(
					ServiceReference<Object> serviceReference) {

					Object resourceFactoryImpl = bundleContext.getService(
						serviceReference);

					Class<?> resourceFactoryImplClass =
						resourceFactoryImpl.getClass();

					try {
						Method createMethod =
							resourceFactoryImplClass.getMethod("create");

						Class<?> builderClass = createMethod.getReturnType();

						return new Builder(
							createMethod, builderClass.getMethod("build"),
							builderClass.getMethod(
								"httpServletRequest", HttpServletRequest.class),
							resourceFactoryImpl,
							builderClass.getMethod("user", User.class));
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

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceLocatorFactoryImpl.class);

	private ServiceTrackerMap<String, Builder> _serviceTrackerMap;

	private static class Builder {

		public Object build(HttpServletRequest httpServletRequest, User user) {
			try {
				Object builder = _createMethod.invoke(_resourceFactoryImpl);

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
			Method createMethod, Method buildMethod,
			Method httpServletRequestMethod, Object resourceFactoryImpl,
			Method userMethod) {

			_createMethod = createMethod;
			_buildMethod = buildMethod;
			_httpServletRequestMethod = httpServletRequestMethod;
			_resourceFactoryImpl = resourceFactoryImpl;
			_userMethod = userMethod;

			createMethod.setAccessible(true);
			buildMethod.setAccessible(true);
			httpServletRequestMethod.setAccessible(true);
			userMethod.setAccessible(true);
		}

		private final Method _buildMethod;
		private final Method _createMethod;
		private final Method _httpServletRequestMethod;
		private final Object _resourceFactoryImpl;
		private final Method _userMethod;

	}

}