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

package com.liferay.portal.vulcan.internal.context;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.context.VulcanContext;
import com.liferay.portal.vulcan.context.VulcanContextInjector;
import com.liferay.portal.vulcan.context.VulcanContextProvider;

import java.lang.reflect.Field;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = VulcanContextInjector.class)
public class VulcanContextInjectorImpl implements VulcanContextInjector {

	@Activate
	@SuppressWarnings("unchecked")
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap)ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, VulcanContextProvider.class, null,
				(serviceReference, emitter) -> {
					VulcanContextProvider<?> vulcanContextProvider =
						bundleContext.getService(serviceReference);

					emitter.emit(vulcanContextProvider.getType());
				});
	}

	@Override
	public void inject(Object target) {
		Class<?> clazz = target.getClass();

		while (clazz != Object.class) {
			for (Field field : clazz.getDeclaredFields()) {
				VulcanContext vulcanContext = field.getAnnotation(
					VulcanContext.class);

				if (vulcanContext == null) {
					continue;
				}

				Class<?> type = field.getType();

				VulcanContextProvider<?> vulcanContextProvider =
					_serviceTrackerMap.getService(type);

				if (vulcanContextProvider == null) {
					_log.error(
						StringBundler.concat(
							"Unknown vulcan context type ", type, " at ",
							field));
				}
				else {
					field.setAccessible(true);

					try {
						field.set(target, vulcanContextProvider.getValue());
					}
					catch (Exception e) {
						_log.error(
							"Unable to inject context value to " + field, e);
					}
				}
			}

			clazz = clazz.getSuperclass();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VulcanContextInjectorImpl.class);

	private ServiceTrackerMap<Class<?>, VulcanContextProvider<?>>
		_serviceTrackerMap;

}