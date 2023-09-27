/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			serviceTracker = ServiceTrackerFactory.create(
				componentContext.getBundleContext(),
				StringBundler.concat(
					"(&(objectClass=", referenceClass.getName(),
					StringPool.CLOSE_PARENTHESIS, filterString,
					StringPool.CLOSE_PARENTHESIS),
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