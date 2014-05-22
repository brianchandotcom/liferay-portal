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

		backgroundTaskStatus.setAttribute("allModelAdditionCountersCurrent", 0);
		backgroundTaskStatus.setAttribute("allModelAdditionCountersTotal", 0);
		backgroundTaskStatus.setAttribute(
			"modelAdditionsByPortletsCurrent",
			new HashMap<String, LongWrapper>());
		backgroundTaskStatus.setAttribute(
			"modelAdditionsByPortletsTotal",
			new HashMap<String, LongWrapper>());
		backgroundTaskStatus.setAttribute("portletsNumberCurrent", 0);
		backgroundTaskStatus.setAttribute("portletsNumberTotal", 0);
	}

	protected long getTotal(Map<String, ?> modelCounters) {
		if (modelCounters == null) {
			return 0;
		}

		long total = 0;

		for (Map.Entry<String, ?> entry : modelCounters.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof LongWrapper) {
				LongWrapper longWrapper = (LongWrapper)value;

				total += longWrapper.getValue();
			}
			else if (value instanceof Long) {
				total += GetterUtil.getLong(value);
			}
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
			"portletsNumberTotal", portletsNumberTotal);

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

		long portletsNumberCurrent = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("portletsNumberCurrent"));
		long portletsNumberTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("portletsNumberTotal"));

		if (portletsNumberCurrent < portletsNumberTotal) {
			backgroundTaskStatus.setAttribute(
				"portletsNumberCurrent", ++portletsNumberCurrent);
		}

		// Data

		HashMap<String, Long> modelAdditionsByPortletsCurrent =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"modelAdditionsByPortletsCurrent");

		modelAdditionsByPortletsCurrent.put(portletId, 0L);

		backgroundTaskStatus.setAttribute(
			"modelAdditionsByPortletsCurrent", modelAdditionsByPortletsCurrent);

		HashMap<String, Long> modelAdditionsByPortletsTotal =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"modelAdditionsByPortletsTotal");

		long modelAdditionByPortlet = GetterUtil.getLong(
			message.get("modelAdditionsByPortlet"));

		modelAdditionsByPortletsTotal.put(portletId, modelAdditionByPortlet);

		backgroundTaskStatus.setAttribute(
			"modelAdditionsByPortletsTotal", modelAdditionsByPortletsTotal);

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

		HashMap<String, Long> modelAdditionsByPortletsCurrent =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"modelAdditionsByPortletsCurrent");

		long modelAdditionsByPortletCurrent = MapUtil.getLong(
			modelAdditionsByPortletsCurrent, portletId);

		HashMap<String, Long> modelAdditionsByPortletsTotal =
			(HashMap<String, Long>)backgroundTaskStatus.getAttribute(
				"modelAdditionsByPortletsTotal");

		long modelAdditionsByPortletTotal = MapUtil.getLong(
			modelAdditionsByPortletsTotal, portletId);

		long allModelAdditionCountersCurrent = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(
				"allModelAdditionCountersCurrent"));
		long allModelAdditionCountersTotal = GetterUtil.getLong(
			backgroundTaskStatus.getAttribute("allModelAdditionCountersTotal"));

		if ((modelAdditionsByPortletCurrent < modelAdditionsByPortletTotal) &&
			(allModelAdditionCountersCurrent < allModelAdditionCountersTotal)) {

			modelAdditionsByPortletsCurrent.put(
				portletId, ++modelAdditionsByPortletCurrent);

			backgroundTaskStatus.setAttribute(
				"allModelAdditionCountersCurrent",
				++allModelAdditionCountersCurrent);
			backgroundTaskStatus.setAttribute(
				"modelAdditionsByPortletsCurrent",
				modelAdditionsByPortletsCurrent);
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