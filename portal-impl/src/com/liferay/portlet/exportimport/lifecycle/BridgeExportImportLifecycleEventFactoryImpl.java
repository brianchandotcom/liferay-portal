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

package com.liferay.portlet.exportimport.lifecycle;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.Serializable;

/**
 * @author Daniel Kocsis
 */
public class BridgeExportImportLifecycleEventFactoryImpl
	implements ExportImportLifecycleEventFactory {

	public BridgeExportImportLifecycleEventFactoryImpl() {
		this(new DummyExportImportLifecycleEventFactoryImpl());
	}

	public BridgeExportImportLifecycleEventFactoryImpl(
		ExportImportLifecycleEventFactory
			defaultExportImportLifecycleEventFactory) {

		_defaultExportImportLifecycleEventFactory =
			defaultExportImportLifecycleEventFactory;

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ExportImportLifecycleEventFactory.class);

		_serviceTracker.open();
	}

	@Override
	public ExportImportLifecycleEvent create(
		int code, int processFlag, Serializable... attributes) {

		return getExportImportLifecycleEventFactory().create(
			code, processFlag, attributes);
	}

	protected ExportImportLifecycleEventFactory
		getExportImportLifecycleEventFactory() {

		if (_serviceTracker.isEmpty()) {
			return _defaultExportImportLifecycleEventFactory;
		}

		return _serviceTracker.getService();
	}

	private final ExportImportLifecycleEventFactory
		_defaultExportImportLifecycleEventFactory;
	private final ServiceTracker
		<ExportImportLifecycleEventFactory, ExportImportLifecycleEventFactory>
			_serviceTracker;

}