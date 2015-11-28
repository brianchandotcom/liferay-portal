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

package com.liferay.portlet.exportimport.lar;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.PortletLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class PortletDataHandlerStatusMessageSenderImpl
	implements PortletDataHandlerStatusMessageSender {

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #sendStatusMessage(String,
	 *             String[], ManifestSummary)}
	 */
	@Deprecated
	@Override
	public void sendStatusMessage(
		String messageType, ManifestSummary manifestSummary) {

		sendStatusMessage(messageType, (String[])null, manifestSummary);
	}

	@Override
	public void sendStatusMessage(
		String messageType, String portletId, ManifestSummary manifestSummary) {

		PortletDataHandlerBackgroundTaskStatusMessage
			portletDataHandlerBackgroundTaskStatusMessage =
				new PortletDataHandlerBackgroundTaskStatusMessage();

		init(
			portletDataHandlerBackgroundTaskStatusMessage, messageType,
			manifestSummary);

		portletDataHandlerBackgroundTaskStatusMessage.put(
			"portletId", portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if (portlet != null) {
			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			long portletModelAdditionCountersTotal =
				portletDataHandler.getExportModelCount(manifestSummary);

			if (portletModelAdditionCountersTotal < 0) {
				portletModelAdditionCountersTotal = 0;
			}

			portletDataHandlerBackgroundTaskStatusMessage.put(
				"portletModelAdditionCountersTotal",
				portletModelAdditionCountersTotal);
		}

		_backgroundTaskStatusMessageSender.setBackgroundTaskStatusMessage(
			portletDataHandlerBackgroundTaskStatusMessage);
	}

	@Override
	public void sendStatusMessage(
		String messageType, String[] portletIds,
		ManifestSummary manifestSummary) {

		PortletDataHandlerBackgroundTaskStatusMessage
			portletDataHandlerBackgroundTaskStatusMessage =
				new PortletDataHandlerBackgroundTaskStatusMessage();

		init(
			portletDataHandlerBackgroundTaskStatusMessage, messageType,
			manifestSummary);

		portletDataHandlerBackgroundTaskStatusMessage.put(
			"portletIds", portletIds);

		_backgroundTaskStatusMessageSender.setBackgroundTaskStatusMessage(
			portletDataHandlerBackgroundTaskStatusMessage);
	}

	@Override
	public <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		PortletDataHandlerBackgroundTaskStatusMessage
			portletDataHandlerBackgroundTaskStatusMessage =
				new PortletDataHandlerBackgroundTaskStatusMessage();

		init(
			portletDataHandlerBackgroundTaskStatusMessage, messageType,
			manifestSummary);

		StagedModelDataHandler<T> stagedModelDataHandler =
			(StagedModelDataHandler<T>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					stagedModel.getModelClassName());

		portletDataHandlerBackgroundTaskStatusMessage.put(
			"stagedModelName",
			stagedModelDataHandler.getDisplayName(stagedModel));

		portletDataHandlerBackgroundTaskStatusMessage.put(
			"stagedModelType",
			String.valueOf(stagedModel.getStagedModelType()));

		portletDataHandlerBackgroundTaskStatusMessage.put(
			"uuid", stagedModel.getUuid());

		_backgroundTaskStatusMessageSender.setBackgroundTaskStatusMessage(
			portletDataHandlerBackgroundTaskStatusMessage);
	}

	public void setBackgroundTaskStatusMessageSender(
		BackgroundTaskStatusMessageSender backgroundTaskStatusMessageSender) {

		_backgroundTaskStatusMessageSender = backgroundTaskStatusMessageSender;
	}

	protected void init(
		Message message, String messageType, ManifestSummary manifestSummary) {

		message.put("messageType", messageType);

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		message.put(
			"modelAdditionCounters", new HashMap<>(modelAdditionCounters));

		Map<String, LongWrapper> modelDeletionCounters =
			manifestSummary.getModelDeletionCounters();

		message.put(
			"modelDeletionCounters", new HashMap<>(modelDeletionCounters));
	}

	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

}