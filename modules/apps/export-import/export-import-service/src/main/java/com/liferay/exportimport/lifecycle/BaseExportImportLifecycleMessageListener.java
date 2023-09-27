/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import java.util.Set;

/**
 * @author Daniel Kocsis
 */
public abstract class BaseExportImportLifecycleMessageListener
	extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		Set<ExportImportLifecycleListener> exportImportLifecycleListeners =
			getExportImportLifecycleListeners(message);

		ExportImportLifecycleEvent exportImportLifecycleEvent =
			(ExportImportLifecycleEvent)message.get(
				"exportImportLifecycleEvent");

		for (ExportImportLifecycleListener exportImportLifecycleListener :
				exportImportLifecycleListeners) {

			try {
				exportImportLifecycleListener.onExportImportLifecycleEvent(
					exportImportLifecycleEvent);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to call " +
							exportImportLifecycleListener.getClass(),
						exception);
				}
			}
		}
	}

	protected abstract Set<ExportImportLifecycleListener>
		getExportImportLifecycleListeners(Message message);

	private static final Log _log = LogFactoryUtil.getLog(
		BaseExportImportLifecycleMessageListener.class);

}