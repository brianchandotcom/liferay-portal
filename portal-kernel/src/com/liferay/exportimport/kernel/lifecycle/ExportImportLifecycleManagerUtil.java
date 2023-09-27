/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lifecycle;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class ExportImportLifecycleManagerUtil {

	public static void fireExportImportLifecycleEvent(
		int code, int processFlag, String processId,
		Serializable... arguments) {

		_exportImportLifecycleManager.fireExportImportLifecycleEvent(
			code, processFlag, processId, arguments);
	}

	private static volatile ExportImportLifecycleManager
		_exportImportLifecycleManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				ExportImportLifecycleManager.class,
				ExportImportLifecycleManagerUtil.class,
				"_exportImportLifecycleManager", false);

}