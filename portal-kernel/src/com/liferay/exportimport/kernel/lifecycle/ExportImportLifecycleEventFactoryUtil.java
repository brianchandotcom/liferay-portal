/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lifecycle;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.io.Serializable;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleEventFactoryUtil {

	public static ExportImportLifecycleEvent create(
		int code, int processFlag, String processId,
		Serializable... attributes) {

		ExportImportLifecycleEventFactory exportImportLifecycleEventFactory =
			_exportImportLifecycleEventFactorySnapshot.get();

		return exportImportLifecycleEventFactory.create(
			code, processFlag, processId, attributes);
	}

	private static final Snapshot<ExportImportLifecycleEventFactory>
		_exportImportLifecycleEventFactorySnapshot = new Snapshot<>(
			ExportImportLifecycleEventFactoryUtil.class,
			ExportImportLifecycleEventFactory.class);

}