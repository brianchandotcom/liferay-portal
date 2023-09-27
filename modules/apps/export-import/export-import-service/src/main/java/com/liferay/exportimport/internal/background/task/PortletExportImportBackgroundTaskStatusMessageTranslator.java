/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Daniel Kocsis
 */
public class PortletExportImportBackgroundTaskStatusMessageTranslator
	extends DefaultExportImportBackgroundTaskStatusMessageTranslator {

	@Override
	protected synchronized void translatePortletMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		clearBackgroundTaskStatus(backgroundTaskStatus);

		long portletModelAdditionCountersTotal = GetterUtil.getLong(
			message.get("portletModelAdditionCountersTotal"));

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCountersTotal", portletModelAdditionCountersTotal);

		super.translatePortletMessage(backgroundTaskStatus, message);
	}

}