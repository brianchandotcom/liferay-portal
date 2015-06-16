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

		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.ALL_MODEL_ADDITION_COUNTERS_TOTAL,
			0L);
		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.ALL_PORTLET_ADDITION_COUNTER, 0L);
		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.ALL_PORTLET_MODEL_ADDITION_COUNTERS,
			new HashMap<String, LongWrapper>());
		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.
				CURRENT_MODEL_ADDITION_COUNTERS_TOTAL,
			0L);
		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.CURRENT_PORTLET_ADDITION_COUNTER,
			0L);
		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.
				CURRENT_PORTLET_MODEL_ADDITION_COUNTERS,
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

		Map<String, LongWrapper> modelAdditionCounters =
			(Map<String, LongWrapper>)message.get(
				StagingBackgroundTaskConstants.MODEL_ADDITION_COUNTERS);

		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.ALL_MODEL_ADDITION_COUNTERS_TOTAL,
			getTotal(modelAdditionCounters));

		long allPortletAdditionCounter = 0;

		String[] portletIds = (String[])message.get("portletIds");

		if (portletIds != null) {
			allPortletAdditionCounter = portletIds.length;
		}

		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.ALL_PORTLET_ADDITION_COUNTER,
			allPortletAdditionCounter);
	}

	protected synchronized void translatePortletMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String portletId = message.getString("portletId");

		HashMap<String, Long> allPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					ALL_PORTLET_MODEL_ADDITION_COUNTERS);

		long portletModelAdditionCountersTotal = GetterUtil.getLong(
			message.get(
				StagingBackgroundTaskConstants.
					PORTLET_MODEL_ADDITION_COUNTERS_TOTAL));

		allPortletModelAdditionCounters.put(
			portletId, portletModelAdditionCountersTotal);

		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.ALL_PORTLET_MODEL_ADDITION_COUNTERS,
			allPortletModelAdditionCounters);

		long allPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.ALL_PORTLET_ADDITION_COUNTER));
		long currentPortletAdditionCounter = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_PORTLET_ADDITION_COUNTER));

		if (currentPortletAdditionCounter < allPortletAdditionCounter) {
			backgroundTaskStatus.setAttribute(
				StagingBackgroundTaskConstants.CURRENT_PORTLET_ADDITION_COUNTER,
				++currentPortletAdditionCounter);
		}

		HashMap<String, Long> currentPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_PORTLET_MODEL_ADDITION_COUNTERS);

		currentPortletModelAdditionCounters.put(portletId, 0L);

		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.
				CURRENT_PORTLET_MODEL_ADDITION_COUNTERS,
			currentPortletModelAdditionCounters);

		backgroundTaskStatus.setAttribute("portletId", portletId);
		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.STAGED_MODEL_NAME, StringPool.BLANK);
		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.STAGED_MODEL_TYPE, StringPool.BLANK);
		backgroundTaskStatus.setAttribute("uuid", StringPool.BLANK);
	}

	protected synchronized void translateStagedModelMessage(
		BackgroundTaskStatus backgroundTaskStatus, Message message) {

		String portletId = (String)backgroundTaskStatus.getAttribute(
			"portletId");

		if (Validator.isNull(portletId)) {
			return;
		}

		long allModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					ALL_MODEL_ADDITION_COUNTERS_TOTAL));
		long currentModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_MODEL_ADDITION_COUNTERS_TOTAL));

		Map<String, Long> allPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					ALL_PORTLET_MODEL_ADDITION_COUNTERS);

		long allPortletModelAdditionCounter = MapUtil.getLong(
			allPortletModelAdditionCounters, portletId);

		HashMap<String, Long> currentPortletModelAdditionCounters =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_PORTLET_MODEL_ADDITION_COUNTERS);

		long currentPortletModelAdditionCounter = MapUtil.getLong(
			currentPortletModelAdditionCounters, portletId);

		if ((allModelAdditionCountersTotal >
				currentModelAdditionCountersTotal) &&
			(allPortletModelAdditionCounter >
				currentPortletModelAdditionCounter)) {

			backgroundTaskStatus.setAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_MODEL_ADDITION_COUNTERS_TOTAL,
				++currentModelAdditionCountersTotal);

			currentPortletModelAdditionCounters.put(
				portletId, ++currentPortletModelAdditionCounter);

			backgroundTaskStatus.setAttribute(
				StagingBackgroundTaskConstants.
					CURRENT_PORTLET_MODEL_ADDITION_COUNTERS,
				currentPortletModelAdditionCounters);
		}

		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.STAGED_MODEL_NAME,
			message.getString(
				StagingBackgroundTaskConstants.STAGED_MODEL_NAME));
		backgroundTaskStatus.setAttribute(
			StagingBackgroundTaskConstants.STAGED_MODEL_TYPE,
			message.getString(
				StagingBackgroundTaskConstants.STAGED_MODEL_TYPE));
		backgroundTaskStatus.setAttribute("uuid", message.getString("uuid"));
	}

}