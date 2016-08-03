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

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Gergely Mathe
 * @author Mate Thurzo
 */
@ProviderType
public class ExportImportContentProcessorRegistryUtil {

	public static List<ExportImportContentProcessor<?, ?>>
		getExportImportContentProcessors() {

		return _instance._getExportImportContentProcessors();
	}

	public static List<ExportImportContentProcessor<?, ?>>
		getExportImportContentProcessors(String className) {

		return _instance._getExportImportContentProcessors(className);
	}

	private ExportImportContentProcessorRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			ExportImportContentProcessorRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext, ExportImportContentProcessor.class,
			new ExportImportContentProcessorServiceTrackerCustomizer());
	}

	private List<ExportImportContentProcessor<?, ?>>
		_getExportImportContentProcessors() {

		List<ExportImportContentProcessor<?, ?>>
			allExportImportContentProcessors = new ArrayList<>();

		for (List<ExportImportContentProcessor<?, ?>>
				exportImportContentProcessors :
					_exportImportContentProcessors.values()) {

			allExportImportContentProcessors.addAll(
				exportImportContentProcessors);
		}

		return allExportImportContentProcessors;
	}

	private List<ExportImportContentProcessor<?, ?>>
		_getExportImportContentProcessors(String className) {

		return _exportImportContentProcessors.get(className);
	}

	private static final ExportImportContentProcessorRegistryUtil _instance =
		new ExportImportContentProcessorRegistryUtil();

	private final BundleContext _bundleContext;
	private final Map<String, ArrayList<ExportImportContentProcessor<?, ?>>>
		_exportImportContentProcessors = new ConcurrentHashMap<>();
	private final ServiceTracker
		<ExportImportContentProcessor, ExportImportContentProcessor>
			_serviceTracker;

	private class ExportImportContentProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ExportImportContentProcessor, ExportImportContentProcessor> {

		@Override
		public ExportImportContentProcessor addingService(
			ServiceReference<ExportImportContentProcessor> serviceReference) {

			ExportImportContentProcessor exportImportContentProcessor =
				_bundleContext.getService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				int order = GetterUtil.getInteger(
					serviceReference.getProperty("content.processor.order"), 1);

				ArrayList<ExportImportContentProcessor<?, ?>>
					exportImportContentProcessorList =
						_exportImportContentProcessors.get(modelClassName);

				if (exportImportContentProcessorList == null) {
					exportImportContentProcessorList = new ArrayList<>();
				}

				int capacity = exportImportContentProcessorList.size() + 1;

				exportImportContentProcessorList.ensureCapacity(capacity);

				order = Math.max(0, order);

				order = Math.min(capacity, order) - 1;

				exportImportContentProcessorList.add(
					order, exportImportContentProcessor);

				_exportImportContentProcessors.put(
					modelClassName, exportImportContentProcessorList);
			}

			return exportImportContentProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ExportImportContentProcessor> serviceReference,
			ExportImportContentProcessor exportImportContentProcessor) {

			removedService(serviceReference, exportImportContentProcessor);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ExportImportContentProcessor> serviceReference,
			ExportImportContentProcessor exportImportContentProcessor) {

			_bundleContext.ungetService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				List<ExportImportContentProcessor<?, ?>>
					exportImportContentProcessorList =
						_exportImportContentProcessors.get(modelClassName);

				exportImportContentProcessorList.remove(
					exportImportContentProcessor);

				if (exportImportContentProcessorList.isEmpty()) {
					_exportImportContentProcessors.remove(modelClassName);
				}
			}
		}

	}

}