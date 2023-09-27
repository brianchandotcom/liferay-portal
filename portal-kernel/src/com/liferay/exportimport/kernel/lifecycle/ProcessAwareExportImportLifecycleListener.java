/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lifecycle;

/**
 * @author Daniel Kocsis
 */
public interface ProcessAwareExportImportLifecycleListener
	extends ExportImportLifecycleListener {

	@Override
	public default void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {
	}

	public void onProcessFailed(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception;

	public void onProcessStarted(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception;

	public void onProcessSucceeded(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception;

}