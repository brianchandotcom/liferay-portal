/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.module.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class ServiceTrackerFieldUpdaterCustomizer<S, T>
	implements ServiceTrackerCustomizer<S, T> {

	public ServiceTrackerFieldUpdaterCustomizer(
		Field serviceField, Object serviceHolder, T dummyTrackedService) {

		if (!Modifier.isVolatile(serviceField.getModifiers())) {
			throw new IllegalArgumentException(
				serviceField + " is not volatile");
		}

		_serviceField = serviceField;

		if (serviceHolder == null) {
			_serviceHolderReference = null;
		}
		else {
			_serviceHolderReference = new WeakReference<>(serviceHolder);
		}

		_dummyTrackedService = dummyTrackedService;
	}

	@Override
	public final T addingService(ServiceReference<S> serviceReference) {
		T trackedService = doAddingService(serviceReference);

		if (trackedService != null) {
			_trackedServices.put(serviceReference, trackedService);

			_updateService();
		}

		return trackedService;
	}

	@Override
	public final void modifiedService(
		ServiceReference<S> serviceReference, T service) {

		doModifiedService(serviceReference, service);

		_updateService();
	}

	@Override
	public final void removedService(
		ServiceReference<S> serviceReference, T service) {

		if (_trackedServices.remove(serviceReference, service)) {
			_updateService();
		}

		doRemovedService(serviceReference, service);
	}

	protected void afterServiceUpdate(T oldService, T newService) {
	}

	protected void beforeServiceUpdate(T oldService, T newService) {
	}

	protected T doAddingService(ServiceReference<S> serviceReference) {
		return (T)_bundleContext.getService(serviceReference);
	}

	protected void doModifiedService(
		ServiceReference<S> serviceReference, T service) {
	}

	protected void doRemovedService(
		ServiceReference<S> serviceReference, T service) {

		_bundleContext.ungetService(serviceReference);
	}

	protected void doServiceUpdate(T newService) {
		Object serviceHolder = null;

		if (_serviceHolderReference != null) {
			serviceHolder = _serviceHolderReference.get();

			if (serviceHolder == null) {
				return;
			}
		}

		try {
			T oldService = (T)_serviceField.get(serviceHolder);

			if (newService != oldService) {
				beforeServiceUpdate(oldService, newService);

				_serviceField.set(serviceHolder, newService);

				afterServiceUpdate(oldService, newService);
			}
		}
		catch (IllegalAccessException illegalAccessException) {
			throw new RuntimeException(illegalAccessException);
		}
	}

	private void _updateService() {
		T service = _dummyTrackedService;

		Set<Map.Entry<ServiceReference<S>, T>> entrySet =
			_trackedServices.entrySet();

		Map.Entry<ServiceReference<S>, T> maxEntry = null;

		for (Map.Entry<ServiceReference<S>, T> entry : entrySet) {
			if (maxEntry == null) {
				maxEntry = entry;
			}
			else {
				ServiceReference<S> serviceReference = entry.getKey();

				if (serviceReference.compareTo(maxEntry.getKey()) > 0) {
					maxEntry = entry;
				}
			}
		}

		if (maxEntry != null) {
			service = maxEntry.getValue();
		}

		doServiceUpdate(service);
	}

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private final T _dummyTrackedService;
	private final Field _serviceField;
	private final Reference<?> _serviceHolderReference;
	private final Map<ServiceReference<S>, T> _trackedServices =
		new ConcurrentHashMap<>();

}