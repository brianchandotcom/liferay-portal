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

package com.liferay.portal.service;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 * @author Connor McKay
 */
public class PersistedModelLocalServiceRegistryImpl
	implements PersistedModelLocalServiceRegistry {

	public PersistedModelLocalServiceRegistryImpl() {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			SystemBundleUtil.createFilter(
				StringBundler.concat(
					"(&(model.class.name=*)(objectClass=",
					PersistedModelLocalService.class.getName(), "))")),
			new PersistenceModelLocalServiceServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	@Override
	public PersistedModelLocalService getPersistedModelLocalService(
		String className) {

		return _persistedModelLocalServices.get(className);
	}

	@Override
	public List<PersistedModelLocalService> getPersistedModelLocalServices() {
		return ListUtil.fromMapValues(_persistedModelLocalServices);
	}

	@Override
	public void register(
		String className,
		PersistedModelLocalService persistedModelLocalService) {

		PersistedModelLocalService oldPersistedModelLocalService =
			_persistedModelLocalServices.put(
				className, persistedModelLocalService);

		if ((oldPersistedModelLocalService != null) && _log.isWarnEnabled()) {
			_log.warn("Duplicate class name " + className);
		}
	}

	@Override
	public void unregister(String className) {
		_persistedModelLocalServices.remove(className);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PersistedModelLocalServiceRegistryImpl.class);

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private final Map<String, PersistedModelLocalService>
		_persistedModelLocalServices = new ConcurrentHashMap<>();
	private final ServiceTracker<?, ?> _serviceTracker;

	private class PersistenceModelLocalServiceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PersistedModelLocalService, String> {

		@Override
		public String addingService(
			ServiceReference<PersistedModelLocalService> serviceReference) {

			String className = (String)serviceReference.getProperty(
				"model.class.name");

			PersistedModelLocalService persistedModelLocalService =
				_bundleContext.getService(serviceReference);

			PersistedModelLocalServiceRegistryImpl.this.register(
				className, persistedModelLocalService);

			return className;
		}

		@Override
		public void modifiedService(
			ServiceReference<PersistedModelLocalService> serviceReference,
			String className) {

			if (!Objects.equals(
					serviceReference.getProperty("model.class.name"),
					className)) {

				removedService(serviceReference, className);

				addingService(serviceReference);
			}
		}

		@Override
		public void removedService(
			ServiceReference<PersistedModelLocalService> serviceReference,
			String className) {

			PersistedModelLocalServiceRegistryImpl.this.unregister(className);

			_bundleContext.ungetService(serviceReference);
		}

	}

}