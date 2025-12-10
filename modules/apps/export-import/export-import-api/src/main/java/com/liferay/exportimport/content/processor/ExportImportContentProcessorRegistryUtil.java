/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.content.processor;

import com.liferay.exportimport.content.processor.constants.ExportImportContentProcessorConstants;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.osgi.util.StringPlus;
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
 * @author Gergely Mathe
 * @author Máté Thurzó
 */
public class ExportImportContentProcessorRegistryUtil {

	public static ExportImportContentProcessor<String>
		getExportImportContentProcessor(String className) {

		return _exportImportContentProcessorRegistryUtil.
			_getExportImportContentProcessor(className);
	}

	public static ExportImportContentProcessor<String>
		getExportImportContentProcessorByContentProcessorType(
			String contentProcessorType) {

		return _exportImportContentProcessorRegistryUtil.
			_getExportImportContentProcessorByContentProcessorType(
				contentProcessorType);
	}

	public static List<ExportImportContentProcessor<String>>
		getExportImportContentProcessors() {

		return _exportImportContentProcessorRegistryUtil.
			_getExportImportContentProcessors();
	}

	private ExportImportContentProcessorRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			ExportImportContentProcessorRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext,
			(Class<ExportImportContentProcessor<String>>)
				(Class<?>)ExportImportContentProcessor.class,
			new ExportImportContentProcessorServiceTrackerCustomizer());
	}

	private ExportImportContentProcessor<String>
		_getExportImportContentProcessor(String className) {

		return _modelClassNameExportImportContentProcessors.get(className);
	}

	private ExportImportContentProcessor<String>
		_getExportImportContentProcessorByContentProcessorType(
			String contentProcessorType) {

		return _contentProcessorTypeExportImportContentProcessors.get(
			contentProcessorType);
	}

	private List<ExportImportContentProcessor<String>>
		_getExportImportContentProcessors() {

		Collection<ExportImportContentProcessor<String>>
			exportImportContentProcessors =
				_modelClassNameExportImportContentProcessors.values();

		return ListUtil.fromCollection(exportImportContentProcessors);
	}

	private static final ExportImportContentProcessorRegistryUtil
		_exportImportContentProcessorRegistryUtil =
			new ExportImportContentProcessorRegistryUtil();

	private final BundleContext _bundleContext;
	private final Map<String, ExportImportContentProcessor<String>>
		_contentProcessorTypeExportImportContentProcessors =
			new ConcurrentHashMap<>();
	private final Map<String, ExportImportContentProcessor<String>>
		_modelClassNameExportImportContentProcessors =
			new ConcurrentHashMap<>();
	private final ServiceTracker
		<ExportImportContentProcessor<String>,
		 ExportImportContentProcessor<String>> _serviceTracker;

	private class ExportImportContentProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ExportImportContentProcessor<String>,
			 ExportImportContentProcessor<String>> {

		@Override
		public ExportImportContentProcessor<String> addingService(
			ServiceReference<ExportImportContentProcessor<String>>
				serviceReference) {

			ExportImportContentProcessor<String> exportImportContentProcessor =
				_bundleContext.getService(serviceReference);

			List<String> contentProcessorTypes = StringPlus.asList(
				serviceReference.getProperty(
					ExportImportContentProcessorConstants.
						CONTENT_PROCESSOR_TYPE));

			for (String processorType : contentProcessorTypes) {
				_contentProcessorTypeExportImportContentProcessors.put(
					processorType, exportImportContentProcessor);
			}

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				_modelClassNameExportImportContentProcessors.put(
					modelClassName, exportImportContentProcessor);
			}

			return exportImportContentProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ExportImportContentProcessor<String>>
				serviceReference,
			ExportImportContentProcessor<String> exportImportContentProcessor) {

			removedService(serviceReference, exportImportContentProcessor);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ExportImportContentProcessor<String>>
				serviceReference,
			ExportImportContentProcessor<String> exportImportContentProcessor) {

			_bundleContext.ungetService(serviceReference);

			List<String> contentProcessorTypes = StringPlus.asList(
				serviceReference.getProperty(
					ExportImportContentProcessorConstants.
						CONTENT_PROCESSOR_TYPE));

			for (String contentProcessorType : contentProcessorTypes) {
				_contentProcessorTypeExportImportContentProcessors.remove(
					contentProcessorType);
			}

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				_modelClassNameExportImportContentProcessors.remove(
					modelClassName);
			}
		}

	}

}