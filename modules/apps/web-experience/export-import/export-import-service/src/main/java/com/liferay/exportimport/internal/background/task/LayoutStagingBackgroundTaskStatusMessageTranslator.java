/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	protected long getAllPortletAdditionCounter(
		BackgroundTaskStatus backgroundTaskStatus) {

		long allPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));
		long currentPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

		return allPortletAdditionCounter + currentPortletAdditionCounter;
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
				getAllPortletAdditionCounter(backgroundTaskStatus));
			backgroundTaskStatus.setAttribute(
				"allPortletModelAdditionCounters",
				new HashMap<String, LongWrapper>());
			backgroundTaskStatus.setAttribute(
				"currentPortletModelAdditionCounters",
				new HashMap<String, LongWrapper>());
		}
	}

}