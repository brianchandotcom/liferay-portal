/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lifecycle;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleListenerFactoryUtil {

	public static ExportImportLifecycleListener create(
		EventAwareExportImportLifecycleListener
			processAwareExportImportLifecycleListener) {

		ExportImportLifecycleListenerFactory
			exportImportLifecycleListenerFactory =
				_exportImportLifecycleListenerFactorySnapshot.get();

		return exportImportLifecycleListenerFactory.create(
			processAwareExportImportLifecycleListener);
	}

	public static ExportImportLifecycleListener create(
		ProcessAwareExportImportLifecycleListener
			processAwareExportImportLifecycleListener) {

		ExportImportLifecycleListenerFactory
			exportImportLifecycleListenerFactory =
				_exportImportLifecycleListenerFactorySnapshot.get();

		return exportImportLifecycleListenerFactory.create(
			processAwareExportImportLifecycleListener);
	}

	private static final Snapshot<ExportImportLifecycleListenerFactory>
		_exportImportLifecycleListenerFactorySnapshot = new Snapshot<>(
			ExportImportLifecycleListenerFactoryUtil.class,
			ExportImportLifecycleListenerFactory.class);

}