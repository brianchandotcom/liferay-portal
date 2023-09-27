/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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