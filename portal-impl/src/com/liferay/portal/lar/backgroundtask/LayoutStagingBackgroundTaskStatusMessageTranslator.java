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

package com.liferay.portal.lar.backgroundtask;

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
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					ALL_MODEL_ADDITION_COUNTERS_TOTAL));
		long currentModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_MODEL_ADDITION_COUNTERS_TOTAL));

		return allModelAdditionCountersTotal +
			currentModelAdditionCountersTotal;
	}

	protected long getAllPortletAdditionCounter(
		BackgroundTaskStatus backgroundTaskStatus) {

		long allPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.ALL_PORTLET_ADDITION_COUNTER));
		long currentPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_PORTLET_ADDITION_COUNTER));

		return allPortletAdditionCounter + currentPortletAdditionCounter;
	}

	@Override
	protected synchronized void translateLayoutMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String phase = GetterUtil.getString(
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.PHASE));

		if (Validator.isNull(phase)) {
			clearBackgroundTaskStatus(backgroundTaskStatus);

			phase = Constants.EXPORT;
		}
		else {
			phase = Constants.IMPORT;
		}

		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.PHASE, phase);

		super.translateLayoutMessage(backgroundTaskStatus, message);

		if (phase.equals(Constants.IMPORT)) {
			backgroundTaskStatus.setAttribute(
				StagingBackgroundTaskConstants.
					ALL_MODEL_ADDITION_COUNTERS_TOTAL,
				getAllModelAdditionCountersTotal(backgroundTaskStatus));
			backgroundTaskStatus.setAttribute(
				StagingBackgroundTaskConstants.ALL_PORTLET_ADDITION_COUNTER,
				getAllPortletAdditionCounter(backgroundTaskStatus));
			backgroundTaskStatus.setAttribute(
				StagingBackgroundTaskConstants.
					ALL_PORTLET_MODEL_ADDITION_COUNTERS,
				new HashMap<String, LongWrapper>());
			backgroundTaskStatus.setAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_PORTLET_MODEL_ADDITION_COUNTERS,
				new HashMap<String, LongWrapper>());
		}
	}

}