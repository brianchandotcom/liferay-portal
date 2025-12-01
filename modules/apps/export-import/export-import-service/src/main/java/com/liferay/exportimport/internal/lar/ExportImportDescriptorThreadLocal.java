/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Carlos Correa
 */
public class ExportImportDescriptorThreadLocal {

	public static
		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			getExportImportDescriptor() {

		return _exportImportDescriptor.get();
	}

	public static SafeCloseable setExportImportDescriptorWithSafeCloseable(
		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			exportImportDescriptor) {

		return _exportImportDescriptor.setWithSafeCloseable(
			exportImportDescriptor);
	}

	private static final CentralizedThreadLocal
		<ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor>
			_exportImportDescriptor = new CentralizedThreadLocal<>(
				ExportImportDescriptorThreadLocal.class +
					"._exportImportDescriptor",
				() -> null);

}