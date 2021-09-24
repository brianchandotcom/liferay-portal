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

package com.liferay.exportimport.kernel.controller;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

/**
 * @author Daniel Kocsis
 */
public class ExportImportControllerRegistryUtil {

	public static ExportController getExportController(String className) {
		return (ExportController)_exportControllers.getService(className);
	}

	public static List<ExportImportController> getExportImportControllers() {
		return new ArrayList<>(_exportControllers.values());
	}

	public static ImportController getImportController(String className) {
		return (ImportController)_importControllers.getService(className);
	}

	private ExportImportControllerRegistryUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, ExportImportController>
		_exportControllers = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, ExportImportController.class, null,
			(serviceReference, emitter) -> {
				ExportImportController exportImportController =
					_bundleContext.getService(serviceReference);

				if (exportImportController instanceof ExportController) {
					for (String modelClassName :
							StringUtil.asList(
								serviceReference.getProperty(
									"model.class.name"))) {

						emitter.emit(modelClassName);
					}
				}

				_bundleContext.ungetService(serviceReference);
			});

	private static final ServiceTrackerMap<String, ExportImportController>
		_importControllers = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, ExportImportController.class, null,
			(serviceReference, emitter) -> {
				ExportImportController exportImportController =
					_bundleContext.getService(serviceReference);

				if (exportImportController instanceof ImportController) {
					for (String modelClassName :
							StringUtil.asList(
								serviceReference.getProperty(
									"model.class.name"))) {

						emitter.emit(modelClassName);
					}
				}

				_bundleContext.ungetService(serviceReference);
			});

}