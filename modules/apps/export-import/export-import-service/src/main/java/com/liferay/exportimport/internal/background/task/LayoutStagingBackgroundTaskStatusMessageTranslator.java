/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;

/**
 * @author Daniel Kocsis
 */
public class LayoutStagingBackgroundTaskStatusMessageTranslator
	extends DefaultExportImportBackgroundTaskStatusMessageTranslator {

	protected long getAllModelAdditionCountersTotal(
		BackgroundTaskStatus backgroundTaskStatus) {

		long allModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
		long currentModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				"currentModelAdditionCountersTotal"));

		return allModelAdditionCountersTotal +
			currentModelAdditionCountersTotal;
	}

	@Override
	protected synchronized void translateLayoutMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String phase = GetterUtil.getString(
			backgroundTaskStatus.getAttribute("phase"));

		if (Validator.isNull(phase)) {
			clearBackgroundTaskStatus(backgroundTaskStatus);

			phase = Constants.EXPORT;
		}
		else {
			phase = Constants.IMPORT;
		}

		backgroundTaskStatus.setAttribute("phase", phase);

		super.translateLayoutMessage(backgroundTaskStatus, message);

		if (phase.equals(Constants.IMPORT)) {
			backgroundTaskStatus.setAttribute(
				"allModelAdditionCountersTotal",
				getAllModelAdditionCountersTotal(backgroundTaskStatus));
			backgroundTaskStatus.setAttribute(
				"allPortletAdditionCounter",
				_getAllPortletAdditionCounter(backgroundTaskStatus));
			backgroundTaskStatus.setAttribute(
				"allPortletModelAdditionCounters",
				new HashMap<String, LongWrapper>());
			backgroundTaskStatus.setAttribute(
				"currentPortletModelAdditionCounters",
				new HashMap<String, LongWrapper>());
		}
	}

	private long _getAllPortletAdditionCounter(
		BackgroundTaskStatus backgroundTaskStatus) {

		long allPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));
		long currentPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

		return allPortletAdditionCounter + currentPortletAdditionCounter;
	}

}