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

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
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

	private ExportImportPortletPreferencesProcessorRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			ExportImportPortletPreferencesProcessorRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext, ExportImportPortletPreferencesProcessor.class,
			new ExportImportPortletPreferencesProcessorServiceTrackerCustomizer());
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

	private static final ExportImportPortletPreferencesProcessorRegistryUtil
		_instance = new ExportImportPortletPreferencesProcessorRegistryUtil();

	private final BundleContext _bundleContext;
	private final Map<String, ExportImportPortletPreferencesProcessor>
		_exportImportPortletPreferencesProcessors = new ConcurrentHashMap<>();
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

			ExportImportPortletPreferencesProcessor
				exportImportPortletPreferencesProcessor =
					_bundleContext.getService(serviceReference);

			String portletName = GetterUtil.getString(
				serviceReference.getProperty("javax.portlet.name"));

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

			removedService(
				serviceReference, exportImportPortletPreferencesProcessor);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ExportImportPortletPreferencesProcessor>
				serviceReference,
			ExportImportPortletPreferencesProcessor
				exportImportPortletPreferencesProcessor) {

			_bundleContext.ungetService(serviceReference);

			String portletName = GetterUtil.getString(
				serviceReference.getProperty("javax.portlet.name"));

			_exportImportPortletPreferencesProcessors.remove(portletName);
		}

	}

}