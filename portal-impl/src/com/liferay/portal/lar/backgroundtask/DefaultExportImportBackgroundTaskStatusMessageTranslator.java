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
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 * @author Daniel Kocsis
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

	protected void clearBackgroundTaskStatus(
		BackgroundTaskStatus backgroundTaskStatus) {

		backgroundTaskStatus.clearAttributes();

		backgroundTaskStatus.setAttribute("allModelAdditionCountersTotal", 0L);
		backgroundTaskStatus.setAttribute("allPortletAdditionCounter", 0L);
		backgroundTaskStatus.setAttribute(
			"allPortletModelAdditionCounters",
			new HashMap<String, LongWrapper>());
		backgroundTaskStatus.setAttribute(
			"currentModelAdditionCountersTotal", 0L);
		backgroundTaskStatus.setAttribute("currentPortletAdditionCounter", 0L);
		backgroundTaskStatus.setAttribute(
			"currentPortletModelAdditionCounters",
			new HashMap<String, LongWrapper>());
	}

	protected long getTotal(Map<String, LongWrapper> modelCounters) {
		if (modelCounters == null) {
			return 0;
		}

		long total = 0;

		for (Map.Entry<String, LongWrapper> entry : modelCounters.entrySet()) {
			LongWrapper longWrapper = entry.getValue();

			total += longWrapper.getValue();
		}

		return total;
	}

	protected synchronized void translateLayoutMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		// Portlets

		String[] portletIds = (String[])message.get("portletIds");
		long portletsNumberTotal = 0;

		if (portletIds != null) {
			portletsNumberTotal = portletIds.length;
		}

		backgroundTaskStatus.setAttribute(
			"allPortletAdditionCounter", portletsNumberTotal);

		// Data

		HashMap<String, LongWrapper> modelAdditionCounters =
			(HashMap<String, LongWrapper>)message.get("modelAdditionCounters");

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCountersTotal", getTotal(modelAdditionCounters));
	}

	protected synchronized void translatePortletMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String portletId = message.getString("portletId");

		backgroundTaskStatus.setAttribute("portletId", portletId);

		// Portlet

		long allPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));
		long currentPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

		if (currentPortletAdditionCounter < allPortletAdditionCounter) {
			backgroundTaskStatus.setAttribute(
				"currentPortletAdditionCounter",
				++currentPortletAdditionCounter);
		}

		// Data

		HashMap<String, Long> currentPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"currentPortletModelAdditionCounters");

		currentPortletModelAdditionCounters.put(portletId, 0L);

		backgroundTaskStatus.setAttribute(
			"currentPortletModelAdditionCounters",
			currentPortletModelAdditionCounters);

		HashMap<String, Long> allPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"allPortletModelAdditionCounters");

		long portletModelAdditionCountersTotal = GetterUtil.getLong(
			message.get("portletModelAdditionCountersTotal"));

		allPortletModelAdditionCounters.put(
			portletId, portletModelAdditionCountersTotal);

		backgroundTaskStatus.setAttribute(
			"allPortletModelAdditionCounters", allPortletModelAdditionCounters);

		// Staged model

		backgroundTaskStatus.setAttribute("stagedModelName", StringPool.BLANK);
		backgroundTaskStatus.setAttribute("stagedModelType", StringPool.BLANK);
		backgroundTaskStatus.setAttribute("uuid", StringPool.BLANK);
	}

	protected synchronized void translateStagedModelMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String portletId = (String)backgroundTaskStatus.getAttribute(
			"portletId");

		if (Validator.isNull(portletId)) {
			return;
		}

		// Model statistics

		HashMap<String, Long> allPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"allPortletModelAdditionCounters");

		long allPortletModelAdditionCounter = MapUtil.getLong(
			allPortletModelAdditionCounters, portletId);

		HashMap<String, Long> currentPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"currentPortletModelAdditionCounters");

		long currentPortletModelAdditionCounter = MapUtil.getLong(
			currentPortletModelAdditionCounters, portletId);

		long allModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
		long currentModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				"currentModelAdditionCountersTotal"));

		if ((currentPortletModelAdditionCounter <
				allPortletModelAdditionCounter) &&
			(currentModelAdditionCountersTotal <
				allModelAdditionCountersTotal)) {

			backgroundTaskStatus.setAttribute(
				"currentModelAdditionCountersTotal",
				++currentModelAdditionCountersTotal);

			currentPortletModelAdditionCounters.put(
				portletId, ++currentPortletModelAdditionCounter);

			backgroundTaskStatus.setAttribute(
				"currentPortletModelAdditionCounters",
				currentPortletModelAdditionCounters);
		}

		// Model attributes

		String stagedModelName = message.getString("stagedModelName");

		backgroundTaskStatus.setAttribute("stagedModelName", stagedModelName);

		String stagedModelType = message.getString("stagedModelType");

		backgroundTaskStatus.setAttribute("stagedModelType", stagedModelType);

		String uuid = message.getString("uuid");

		backgroundTaskStatus.setAttribute("uuid", uuid);
	}

}