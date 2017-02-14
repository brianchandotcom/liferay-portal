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

package com.liferay.exportimport.kernel.lifecycle;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleListenerFactoryUtil {

	public static ExportImportLifecycleListener create(
		EventAwareExportImportLifecycleListener
			processAwareExportImportLifecycleListener) {

		return _exportImportLifecycleListenerFactory.create(
			processAwareExportImportLifecycleListener);
	}

	public static ExportImportLifecycleListener create(
		ProcessAwareExportImportLifecycleListener
			processAwareExportImportLifecycleListener) {

		return _exportImportLifecycleListenerFactory.create(
			processAwareExportImportLifecycleListener);
	}

	private static volatile ExportImportLifecycleListenerFactory
		_exportImportLifecycleListenerFactory =
			ServiceProxyFactory.newServiceTrackedInstance(
				ExportImportLifecycleListenerFactory.class,
				ExportImportLifecycleListenerFactoryUtil.class,
				"_exportImportLifecycleListenerFactory", false);

}