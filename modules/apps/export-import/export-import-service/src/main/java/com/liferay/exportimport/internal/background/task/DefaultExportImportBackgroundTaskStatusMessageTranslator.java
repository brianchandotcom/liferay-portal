/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageTranslator;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MapUtil;
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
			_translateStagedModelMessage(backgroundTaskStatus, message);
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

	protected synchronized void translateLayoutMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		Map<String, LongWrapper> modelAdditionCounters =
			(Map<String, LongWrapper>)message.get("modelAdditionCounters");

		backgroundTaskStatus.setAttribute(
			"allModelAdditionCountersTotal", _getTotal(modelAdditionCounters));

		long allPortletAdditionCounter = 0;

		String[] portletIds = (String[])message.get("portletIds");

		if (portletIds != null) {
			allPortletAdditionCounter = portletIds.length;
		}

		backgroundTaskStatus.setAttribute(
			"allPortletAdditionCounter", allPortletAdditionCounter);
	}

	protected synchronized void translatePortletMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String portletId = message.getString("portletId");

		HashMap<String, Long> allPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"allPortletModelAdditionCounters");

		long portletModelAdditionCountersTotal = GetterUtil.getLong(
			message.get("portletModelAdditionCountersTotal"));

		allPortletModelAdditionCounters.put(
			portletId, portletModelAdditionCountersTotal);

		backgroundTaskStatus.setAttribute(
			"allPortletModelAdditionCounters", allPortletModelAdditionCounters);

		long allPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allPortletAdditionCounter"));
		long currentPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("currentPortletAdditionCounter"));

		if (currentPortletAdditionCounter < allPortletAdditionCounter) {
			backgroundTaskStatus.setAttribute(
				"currentPortletAdditionCounter",
				++currentPortletAdditionCounter);
		}

		HashMap<String, Long> currentPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"currentPortletModelAdditionCounters");

		currentPortletModelAdditionCounters.put(portletId, 0L);

		backgroundTaskStatus.setAttribute(
			"currentPortletModelAdditionCounters",
			currentPortletModelAdditionCounters);

		backgroundTaskStatus.setAttribute("portletId", portletId);
		backgroundTaskStatus.setAttribute("stagedModelName", StringPool.BLANK);
		backgroundTaskStatus.setAttribute("stagedModelType", StringPool.BLANK);
		backgroundTaskStatus.setAttribute("uuid", StringPool.BLANK);
	}

	private long _getTotal(Map<String, LongWrapper> modelCounters) {
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

	private synchronized void _translateStagedModelMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String portletId = (String)backgroundTaskStatus.getAttribute(
			"portletId");

		if (Validator.isNull(portletId)) {
			return;
		}

		long allModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));
		long currentModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				"currentModelAdditionCountersTotal"));

		Map<String, Long> allPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"allPortletModelAdditionCounters");

		long allPortletModelAdditionCounter = MapUtil.getLong(
			allPortletModelAdditionCounters, portletId);

		HashMap<String, Long> currentPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"currentPortletModelAdditionCounters");

		long currentPortletModelAdditionCounter = MapUtil.getLong(
			currentPortletModelAdditionCounters, portletId);

		if ((allModelAdditionCountersTotal >
				currentModelAdditionCountersTotal) &&
			(allPortletModelAdditionCounter >
				currentPortletModelAdditionCounter)) {

			backgroundTaskStatus.setAttribute(
				"currentModelAdditionCountersTotal",
				++currentModelAdditionCountersTotal);

			currentPortletModelAdditionCounters.put(
				portletId, ++currentPortletModelAdditionCounter);

			backgroundTaskStatus.setAttribute(
				"currentPortletModelAdditionCounters",
				currentPortletModelAdditionCounters);
		}

		backgroundTaskStatus.setAttribute(
			"stagedModelName", message.getString("stagedModelName"));
		backgroundTaskStatus.setAttribute(
			"stagedModelType", message.getString("stagedModelType"));
		backgroundTaskStatus.setAttribute("uuid", message.getString("uuid"));
	}

}