/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.concurrent.Callable;

/**
 * @author Daniel Kocsis
 */
public class ExportImportProcessCallbackRegistryUtil {

	public static void registerCallback(
		String processId, Callable<?> callable) {

		ExportImportProcessCallbackRegistry
			exportImportProcessCallbackRegistry =
				_exportImportProcessCommitCallbackRegistrySnapshot.get();

		exportImportProcessCallbackRegistry.registerCallback(
			processId, callable);
	}

	private static final Snapshot<ExportImportProcessCallbackRegistry>
		_exportImportProcessCommitCallbackRegistrySnapshot = new Snapshot<>(
			ExportImportProcessCallbackRegistryUtil.class,
			ExportImportProcessCallbackRegistry.class);

}