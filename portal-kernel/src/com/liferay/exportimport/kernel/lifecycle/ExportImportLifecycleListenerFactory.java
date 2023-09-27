/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lifecycle;

/**
 * @author Daniel Kocsis
 */
public interface ExportImportLifecycleListenerFactory {

	public ExportImportLifecycleListener create(
		EventAwareExportImportLifecycleListener
			processAwareExportImportLifecycleListener);

	public ExportImportLifecycleListener create(
		ProcessAwareExportImportLifecycleListener
			processAwareExportImportLifecycleListener);

}