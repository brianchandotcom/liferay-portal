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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.StorageTypeAware;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
public class SchedulerEntryRegistry {

	public SchedulerEntryRegistry() {
		if (!PropsValues.SCHEDULER_ENABLED) {
			_serviceTracker = null;

			return;
		}

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=*)(objectClass=" +
				SchedulerEntry.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new SchedulerEntryServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerEntryRegistry.class);

	private final ServiceTracker<SchedulerEntry, SchedulerEntry>
		_serviceTracker;

	private class SchedulerEntryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<SchedulerEntry, SchedulerEntry> {

		@Override
		public SchedulerEntry addingService(
			ServiceReference<SchedulerEntry> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			SchedulerEntry schedulerEntry = registry.getService(
				serviceReference);

			StorageType storageType = StorageType.MEMORY_CLUSTERED;

			if (schedulerEntry instanceof StorageTypeAware) {
				StorageTypeAware storageTypeAware =
					(StorageTypeAware)schedulerEntry;

				storageType = storageTypeAware.getStorageType();
			}

			addTrigger(schedulerEntry, serviceReference);

			String portletId = (String)serviceReference.getProperty(
				"javax.portlet.name");

			try {
				SchedulerEngineHelperUtil.schedule(
					schedulerEntry, storageType, portletId, 0);

				return schedulerEntry;
			}
			catch (SchedulerException e) {
				_log.error(e, e);
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<SchedulerEntry> serviceReference,
			SchedulerEntry schedulerEntry) {
		}

		@Override
		public void removedService(
			ServiceReference<SchedulerEntry> serviceReference,
			SchedulerEntry schedulerEntry) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			StorageType storageType = StorageType.MEMORY_CLUSTERED;

			if (schedulerEntry instanceof StorageTypeAware) {
				StorageTypeAware storageTypeAware =
					(StorageTypeAware)schedulerEntry;

				storageType = storageTypeAware.getStorageType();
			}

			try {
				SchedulerEngineHelperUtil.unschedule(
					schedulerEntry, storageType);
			}
			catch (SchedulerException e) {
				_log.error(e, e);
			}
		}

		protected void addTrigger(
			SchedulerEntry schedulerEntry,
			ServiceReference<SchedulerEntry> serviceReference) {

			String propertyKey = schedulerEntry.getPropertyKey();

			if (Validator.isNull(propertyKey)) {
				return;
			}

			long bundleId = GetterUtil.getLong(
				serviceReference.getProperty("bundle.id"), -1);

			String triggerValue = null;

			if (bundleId != 0) {
				Class<?> clazz = schedulerEntry.getClass();

				ClassLoader classloader = clazz.getClassLoader();

				triggerValue = getPluginPropertyValue(classloader, propertyKey);
			}
			else {
				triggerValue = PrefsPropsUtil.getString(propertyKey);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Scheduler property key " + propertyKey +
						" has trigger value " + triggerValue);
			}

			if (Validator.isNotNull(triggerValue)) {
				schedulerEntry.setTriggerValue(triggerValue);
			}
		}

		protected String getPluginPropertyValue(
			ClassLoader classLoader, String propertyKey) {

			Configuration configuration =
				ConfigurationFactoryUtil.getConfiguration(
					classLoader, "portlet");

			return configuration.get(propertyKey);
		}

	}

}