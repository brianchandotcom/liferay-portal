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

package com.liferay.portlet.exportimport.lar;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.concurrent.Callable;

/**
 * @author Daniel Kocsis
 */
public class ExportImportProcessCallbackRegistryBaseImpl
	implements ExportImportProcessCallbackRegistry {

	public ExportImportProcessCallbackRegistryBaseImpl() {
		this(new DummyExportImportProcessCallbackRegistryImpl());
	}

	public ExportImportProcessCallbackRegistryBaseImpl(
		ExportImportProcessCallbackRegistry
			defaultExportImportProcessCallbackRegistry) {

		_defaultExportImportProcessCallbackRegistry =
			defaultExportImportProcessCallbackRegistry;

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ExportImportProcessCallbackRegistry.class);

		_serviceTracker.open();
	}

	@Override
	public void registerCallback(Callable<?> callable) {
		getExportImportProcessCallbackRegistry().registerCallback(callable);
	}

	protected ExportImportProcessCallbackRegistry
		getExportImportProcessCallbackRegistry() {

		if (_serviceTracker.isEmpty()) {
			return _defaultExportImportProcessCallbackRegistry;
		}

		return _serviceTracker.getService();
	}

	private final ExportImportProcessCallbackRegistry
		_defaultExportImportProcessCallbackRegistry;
	private final ServiceTracker
		<ExportImportProcessCallbackRegistry,
			ExportImportProcessCallbackRegistry> _serviceTracker;

}