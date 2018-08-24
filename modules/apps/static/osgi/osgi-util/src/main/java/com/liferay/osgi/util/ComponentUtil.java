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

package com.liferay.osgi.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tina Tian
 */
public class ComponentUtil {

	public static <T> void enableComponents(
		Class<T> referenceClass, String filterString,
		ComponentContext componentContext, Class<?>... componentClasses) {

		AwaitReferenceServiceTrackerCustomizer<T>
			awaitReferenceServiceTrackerCustomizer =
				new AwaitReferenceServiceTrackerCustomizer<>(
					componentContext, componentClasses);

		ServiceTracker<T, T> serviceTracker = null;

		if (Validator.isNull(filterString)) {
			serviceTracker = ServiceTrackerFactory.create(
				componentContext.getBundleContext(), referenceClass,
				awaitReferenceServiceTrackerCustomizer);
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append("(&(objectClass=");
			sb.append(referenceClass.getName());
			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(filterString);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			serviceTracker = ServiceTrackerFactory.create(
				componentContext.getBundleContext(), sb.toString(),
				awaitReferenceServiceTrackerCustomizer);
		}

		awaitReferenceServiceTrackerCustomizer.setServiceTracker(
			serviceTracker);

		serviceTracker.open();
	}

	private static class AwaitReferenceServiceTrackerCustomizer<T>
		implements ServiceTrackerCustomizer<T, T> {

		@Override
		public T addingService(ServiceReference<T> serviceReference) {
			for (Class<?> componentClass : _componentClasses) {
				_componentContext.enableComponent(componentClass.getName());
			}

			_serviceTracker.close();

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<T> serviceReference, T object) {
		}

		@Override
		public void removedService(
			ServiceReference<T> serviceReference, T object) {
		}

		public void setServiceTracker(ServiceTracker<T, T> serviceTracker) {
			_serviceTracker = serviceTracker;
		}

		private AwaitReferenceServiceTrackerCustomizer(
			ComponentContext componentContext, Class<?>[] componentClasses) {

			_componentContext = componentContext;
			_componentClasses = componentClasses;
		}

		private final Class<?>[] _componentClasses;
		private final ComponentContext _componentContext;
		private ServiceTracker<T, T> _serviceTracker;

	}

}