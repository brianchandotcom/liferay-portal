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

package com.liferay.exportimport.api.preferencesprocessor;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mate Thurzo
 */
public class ExportImportPreferencesProcessorRegistryUtil {

	public static ExportImportPreferencesProcessor
		getExportImportPreferencesProcessor(String portletName) {

		return _instance._getExportImportPreferencesProcessor(portletName);
	}

	public static List<ExportImportPreferencesProcessor>
		getExportImportPreferencesProcessors() {

			return _instance._getExportImportPreferencesProcessors();
	}

	public static void register(
		ExportImportPreferencesProcessor exportImportPreferencesProcessor) {

		_instance._register(exportImportPreferencesProcessor);
	}

	public static void unregister(
		ExportImportPreferencesProcessor exportImportPreferencesProcessor) {

		_instance._unregister(exportImportPreferencesProcessor);
	}

	public static void unregister(
		List<ExportImportPreferencesProcessor>
			exportImportPreferencesProcessors) {

		for (ExportImportPreferencesProcessor exportImportPreferencesProcessor :
				exportImportPreferencesProcessors) {

			unregister(exportImportPreferencesProcessor);
		}
	}

	private ExportImportPreferencesProcessorRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<ExportImportPreferencesProcessor>)(Class<?>)
				ExportImportPreferencesProcessor.class,
			new ExportImportPreferencesProcessorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private ExportImportPreferencesProcessor
		_getExportImportPreferencesProcessor(String portletName) {

		return _exportImportPreferencesProcessors.get(portletName);
	}

	private List<ExportImportPreferencesProcessor>
		_getExportImportPreferencesProcessors() {

			Collection<ExportImportPreferencesProcessor> values =
				_exportImportPreferencesProcessors.values();

			return ListUtil.fromCollection(values);
	}

	private void _register(
		ExportImportPreferencesProcessor exportImportPreferencesProcessor) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<ExportImportPreferencesProcessor>
			serviceRegistration = registry.registerService(
				(Class<ExportImportPreferencesProcessor>)(Class<?>)
					ExportImportPreferencesProcessor.class,
				exportImportPreferencesProcessor);

		_serviceRegistrations.put(
			exportImportPreferencesProcessor, serviceRegistration);
	}

	private void _unregister(
		ExportImportPreferencesProcessor exportImportPreferencesProcessor) {

		ServiceRegistration<ExportImportPreferencesProcessor>
			serviceRegistration = _serviceRegistrations.remove(
				exportImportPreferencesProcessor);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final ExportImportPreferencesProcessorRegistryUtil
		_instance = new ExportImportPreferencesProcessorRegistryUtil();

	private final Map<String, ExportImportPreferencesProcessor>
		_exportImportPreferencesProcessors = new ConcurrentHashMap<>();
	private final ServiceRegistrationMap<ExportImportPreferencesProcessor>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final
		ServiceTracker
			<ExportImportPreferencesProcessor,
				ExportImportPreferencesProcessor> _serviceTracker;

	private class ExportImportPreferencesProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ExportImportPreferencesProcessor,
				ExportImportPreferencesProcessor> {

		@Override
		public ExportImportPreferencesProcessor addingService(
			ServiceReference<ExportImportPreferencesProcessor>
				serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ExportImportPreferencesProcessor exportImportPreferencesProcessor =
				registry.getService(serviceReference);

			String portletName = GetterUtil.getString(
				serviceReference.getProperty("javax.portlet.name"));

			_exportImportPreferencesProcessors.put(
				portletName, exportImportPreferencesProcessor);

			return exportImportPreferencesProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ExportImportPreferencesProcessor> serviceReference,
			ExportImportPreferencesProcessor exportImportPreferencesProcessor) {
		}

		@Override
		public void removedService(
			ServiceReference<ExportImportPreferencesProcessor> serviceReference,
			ExportImportPreferencesProcessor exportImportPreferencesProcessor) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String portletName = GetterUtil.getString(
				serviceReference.getProperty("javax.portlet.name"));

			_exportImportPreferencesProcessors.remove(portletName);
		}

	}

}