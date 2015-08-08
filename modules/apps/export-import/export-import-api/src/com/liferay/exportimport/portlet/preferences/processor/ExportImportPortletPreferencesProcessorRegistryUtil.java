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

package com.liferay.exportimport.portlet.preferences.processor;

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
public class ExportImportPortletPreferencesProcessorRegistryUtil {

	public static ExportImportPortletPreferencesProcessor
		getExportImportPortletPreferencesProcessor(String portletName) {

		return _instance._getExportImportPortletPreferencesProcessor(
			portletName);
	}

	public static List<ExportImportPortletPreferencesProcessor>
		getExportImportPortletPreferencesProcessors() {

		return _instance._getExportImportPortletPreferencesProcessors();
	}

	public static void register(
		ExportImportPortletPreferencesProcessor
			exportImportPortletPreferencesProcessor) {

		_instance._register(exportImportPortletPreferencesProcessor);
	}

	public static void unregister(
		ExportImportPortletPreferencesProcessor
			exportImportPortletPreferencesProcessor) {

		_instance._unregister(exportImportPortletPreferencesProcessor);
	}

	public static void unregister(
		List<ExportImportPortletPreferencesProcessor>
			exportImportPortletPreferencesProcessors) {

		for (ExportImportPortletPreferencesProcessor
				exportImportPortletPreferencesProcessor :
					exportImportPortletPreferencesProcessors) {

			unregister(exportImportPortletPreferencesProcessor);
		}
	}

	private ExportImportPortletPreferencesProcessorRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<ExportImportPortletPreferencesProcessor>)(Class<?>)
				ExportImportPortletPreferencesProcessor.class,
			new ExportImportPortletPreferencesProcessorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private ExportImportPortletPreferencesProcessor
		_getExportImportPortletPreferencesProcessor(String portletName) {

		return _exportImportPortletPreferencesProcessors.get(portletName);
	}

	private List<ExportImportPortletPreferencesProcessor>
		_getExportImportPortletPreferencesProcessors() {

		Collection<ExportImportPortletPreferencesProcessor> values =
			_exportImportPortletPreferencesProcessors.values();

		return ListUtil.fromCollection(values);
	}

	private void _register(
		ExportImportPortletPreferencesProcessor
			exportImportPortletPreferencesProcessor) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<ExportImportPortletPreferencesProcessor>
			serviceRegistration = registry.registerService(
				(Class<ExportImportPortletPreferencesProcessor>)(Class<?>)
					ExportImportPortletPreferencesProcessor.class,
				exportImportPortletPreferencesProcessor);

		_serviceRegistrations.put(
			exportImportPortletPreferencesProcessor, serviceRegistration);
	}

	private void _unregister(
		ExportImportPortletPreferencesProcessor
			exportImportPortletPreferencesProcessor) {

		ServiceRegistration<ExportImportPortletPreferencesProcessor>
			serviceRegistration = _serviceRegistrations.remove(
				exportImportPortletPreferencesProcessor);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final ExportImportPortletPreferencesProcessorRegistryUtil
		_instance = new ExportImportPortletPreferencesProcessorRegistryUtil();

	private final Map<String, ExportImportPortletPreferencesProcessor>
		_exportImportPortletPreferencesProcessors = new ConcurrentHashMap<>();
	private final ServiceRegistrationMap
		<ExportImportPortletPreferencesProcessor> _serviceRegistrations =
			new ServiceRegistrationMapImpl<>();
	private final
		ServiceTracker
			<ExportImportPortletPreferencesProcessor,
				ExportImportPortletPreferencesProcessor> _serviceTracker;

	private class ExportImportPortletPreferencesProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ExportImportPortletPreferencesProcessor,
				ExportImportPortletPreferencesProcessor> {

		@Override
		public ExportImportPortletPreferencesProcessor addingService(
			ServiceReference<ExportImportPortletPreferencesProcessor>
				serviceReference) {

			String portletName = GetterUtil.getString(
				serviceReference.getProperty("javax.portlet.name"));

			Registry registry = RegistryUtil.getRegistry();

			ExportImportPortletPreferencesProcessor
				exportImportPortletPreferencesProcessor = registry.getService(
					serviceReference);

			_exportImportPortletPreferencesProcessors.put(
				portletName, exportImportPortletPreferencesProcessor);

			return exportImportPortletPreferencesProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ExportImportPortletPreferencesProcessor>
				serviceReference,
			ExportImportPortletPreferencesProcessor
				exportImportPortletPreferencesProcessor) {
		}

		@Override
		public void removedService(
			ServiceReference<ExportImportPortletPreferencesProcessor>
				serviceReference,
			ExportImportPortletPreferencesProcessor
				exportImportPortletPreferencesProcessor) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String portletName = GetterUtil.getString(
				serviceReference.getProperty("javax.portlet.name"));

			_exportImportPortletPreferencesProcessors.remove(portletName);
		}

	}

}