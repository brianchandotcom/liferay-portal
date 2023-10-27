/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lifecycle;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class ExportImportLifecycleManagerUtil {

	public static void fireExportImportLifecycleEvent(
		int code, int processFlag, String processId,
		Serializable... arguments) {

		ExportImportLifecycleManager exportImportLifecycleManager =
			_exportImportLifecycleManagerSnapshot.get();

		exportImportLifecycleManager.fireExportImportLifecycleEvent(
			code, processFlag, processId, arguments);
	}

	private static final Snapshot<ExportImportLifecycleManager>
		_exportImportLifecycleManagerSnapshot = new Snapshot<>(
			ExportImportLifecycleManagerUtil.class,
			ExportImportLifecycleManager.class);

}