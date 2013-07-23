/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class DefaultExportImportBackgroundTaskStatusMessageTranslator
	implements BackgroundTaskStatusMessageTranslator {

	@Override
	public void translate(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String messageType = message.getString("messageType");

		if (messageType.equals("layout")) {
			translateLayoutMessage(backgroundTaskStatus, message);
		}
		else if (messageType.equals("portlet")) {
			translatePortletMessage(backgroundTaskStatus, message);
		}
		else if (messageType.equals("stagedModel")) {
			translateStagedModelMessage(backgroundTaskStatus, message);
		}
	}

	protected long getTotal(Map<String, LongWrapper> modelCounters) {
		if (modelCounters == null) {
			return 0;
		}

		long total = 0;

		for (Map.Entry<String, LongWrapper> entry : modelCounters.entrySet()) {
			total += (entry.getValue()).getValue();
		}

		return total;
	}

	protected synchronized void translateLayoutMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		Map<String, LongWrapper> modelAdditionCounters =
			(Map<String, LongWrapper>)message.get("modelAdditionCounters");

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCounters",
			new HashMap<String, LongWrapper>(modelAdditionCounters));

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCount", getTotal(modelAdditionCounters));

		Map<String, LongWrapper> modelDeletionCounters =
			(Map<String, LongWrapper>)message.get("modelDeletionCounters");

		backgroundTaskStatus.setAttribute(
			"allModelDeletionCounters",
			new HashMap<String, LongWrapper>(modelDeletionCounters));

		backgroundTaskStatus.setAttribute(
			"allModelDeletionCount", getTotal(modelDeletionCounters));
	}

	protected synchronized void translatePortletMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		backgroundTaskStatus.clearAttributes();

		Map<String, LongWrapper> modelAdditionCounters =
			(Map<String, LongWrapper>)message.get("modelAdditionCounters");

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCounters",
			new HashMap<String, LongWrapper>(modelAdditionCounters));

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCount", getTotal(modelAdditionCounters));

		Map<String, LongWrapper> modelDeletionCounters =
			(Map<String, LongWrapper>)message.get("modelDeletionCounters");

		backgroundTaskStatus.setAttribute(
			"allModelDeletionCounters",
			new HashMap<String, LongWrapper>(modelDeletionCounters));

		backgroundTaskStatus.setAttribute(
			"allModelDeletionCount", getTotal(modelDeletionCounters));

		String portletId = message.getString("portletId");

		backgroundTaskStatus.setAttribute("portletId", portletId);
	}

	protected synchronized void translateStagedModelMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String portletId = (String)backgroundTaskStatus.getAttribute(
			"portletId");

		if (Validator.isNull(portletId)) {
			return;
		}

		Map<String, LongWrapper> modelAdditionCounters =
			(Map<String, LongWrapper>)message.get("modelAdditionCounters");

		backgroundTaskStatus.setAttribute(
			"currentModelAdditionCounters",
			new HashMap<String, LongWrapper>(modelAdditionCounters));

		backgroundTaskStatus.setAttribute(
			"currentModelAdditionCount", getTotal(modelAdditionCounters));

		Map<String, LongWrapper> modelDeletionCounters =
			(Map<String, LongWrapper>)message.get("modelDeletionCounters");

		backgroundTaskStatus.setAttribute(
			"currentModelDeletionCounters",
			new HashMap<String, LongWrapper>(modelDeletionCounters));

		backgroundTaskStatus.setAttribute(
			"currentModelDeletionCount", getTotal(modelDeletionCounters));

		String stagedModelName = message.getString("stagedModelName");

		backgroundTaskStatus.setAttribute("stagedModelName", stagedModelName);

		String stagedModelType = message.getString("stagedModelType");

		backgroundTaskStatus.setAttribute("stagedModelType", stagedModelType);

		String uuid = message.getString("uuid");

		backgroundTaskStatus.setAttribute("uuid", uuid);
	}

}