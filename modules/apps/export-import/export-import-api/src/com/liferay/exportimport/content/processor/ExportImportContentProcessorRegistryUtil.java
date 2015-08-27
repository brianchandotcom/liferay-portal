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

package com.liferay.exportimport.content.processor;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.registry.util.StringPlus;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gergely Mathe
 */
@ProviderType
public class ExportImportContentProcessorRegistryUtil {

	public static ExportImportContentProcessor
		getExportImportContentProcessor(String className) {

		return _instance._getExportImportContentProcessor(className);
	}

	public static List<ExportImportContentProcessor>
		getExportImportContentProcessors() {

		return _instance._getExportImportContentProcessors();
	}

	public static void register(
		ExportImportContentProcessor exportImportContentProcessor) {

		_instance._register(exportImportContentProcessor);
	}

	public static void unregister(
		ExportImportContentProcessor exportImportContentProcessor) {

		_instance._unregister(exportImportContentProcessor);
	}

	public static void unregister(
		List<ExportImportContentProcessor> exportImportContentProcessors) {

		for (ExportImportContentProcessor exportImportContentProcessor :
				exportImportContentProcessors) {

			unregister(exportImportContentProcessor);
		}
	}

	private ExportImportContentProcessorRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<ExportImportContentProcessor>)(Class)
				ExportImportContentProcessor.class,
			new ExportImportContentProcessorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private ExportImportContentProcessor _getExportImportContentProcessor(
		String className) {

		return _exportImportContentProcessors.get(className);
	}

	private List<ExportImportContentProcessor>
		_getExportImportContentProcessors() {

		Collection<ExportImportContentProcessor> values =
			_exportImportContentProcessors.values();

		return ListUtil.fromCollection(values);
	}

	private void _register(
		ExportImportContentProcessor exportImportContentProcessor) {

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<ExportImportContentProcessor>
			serviceRegistration = registry.registerService(
				(Class<ExportImportContentProcessor>)(Class)
					ExportImportContentProcessor.class,
				exportImportContentProcessor);

		_serviceRegistrations.put(
			exportImportContentProcessor, serviceRegistration);
	}

	private void _unregister(
		ExportImportContentProcessor exportImportContentProcessor) {

		ServiceRegistration<ExportImportContentProcessor>
			serviceRegistration = _serviceRegistrations.remove(
				exportImportContentProcessor);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final ExportImportContentProcessorRegistryUtil _instance =
		new ExportImportContentProcessorRegistryUtil();

	private final Map<String, ExportImportContentProcessor>
		_exportImportContentProcessors = new ConcurrentHashMap<>();
	private final ServiceRegistrationMap<ExportImportContentProcessor>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final ServiceTracker
		<ExportImportContentProcessor, ExportImportContentProcessor>
			_serviceTracker;

	private class ExportImportContentProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ExportImportContentProcessor, ExportImportContentProcessor> {

		@Override
		public ExportImportContentProcessor addingService(
			ServiceReference<ExportImportContentProcessor> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ExportImportContentProcessor exportImportContentProcessor =
				registry.getService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				_exportImportContentProcessors.put(
					modelClassName, exportImportContentProcessor);
			}

			return exportImportContentProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ExportImportContentProcessor> serviceReference,
			ExportImportContentProcessor exportImportContentProcessor) {
		}

		@Override
		public void removedService(
			ServiceReference<ExportImportContentProcessor> serviceReference,
			ExportImportContentProcessor exportImportContentProcessor) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				_exportImportContentProcessors.remove(modelClassName);
			}
		}

	}

}