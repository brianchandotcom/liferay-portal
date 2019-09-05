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

package com.liferay.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lifecycle.EventAwareExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListenerFactory;
import com.liferay.exportimport.kernel.lifecycle.ProcessAwareExportImportLifecycleListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true, service = ExportImportLifecycleListenerFactory.class
)
public class DefaultExportImportLifecycleListernerFactory
	implements ExportImportLifecycleListenerFactory {

	@Override
	public ExportImportLifecycleListener create(
		EventAwareExportImportLifecycleListener lifecycleListener) {

		return new DefaultEventAwareExportImportLifecycleListener(
			lifecycleListener);
	}

	@Override
	public ExportImportLifecycleListener create(
		ProcessAwareExportImportLifecycleListener lifecycleListener) {

		return new DefaultProcessAwareExportImportLifecycleListener(
			lifecycleListener);
	}

}