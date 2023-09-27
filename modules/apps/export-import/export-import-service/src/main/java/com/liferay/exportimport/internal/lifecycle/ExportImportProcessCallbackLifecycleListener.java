/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lifecycle;

import com.liferay.exportimport.internal.lar.ExportImportProcessCallbackUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ProcessAwareExportImportLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(service = ExportImportLifecycleListener.class)
public class ExportImportProcessCallbackLifecycleListener
	implements ProcessAwareExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	public void onProcessFailed(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		ExportImportProcessCallbackUtil.popCallbackList(
			exportImportLifecycleEvent.getProcessId());
	}

	@Override
	public void onProcessStarted(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		ExportImportProcessCallbackUtil.pushCallbackList(
			exportImportLifecycleEvent.getProcessId());
	}

	@Override
	public void onProcessSucceeded(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		List<Callable<?>> callables =
			ExportImportProcessCallbackUtil.popCallbackList(
				exportImportLifecycleEvent.getProcessId());

		for (Callable<?> callable : callables) {
			try {
				callable.call();
			}
			catch (Exception exception) {
				_log.error(
					"Unable to execute export import process callback",
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportProcessCallbackLifecycleListener.class);

}