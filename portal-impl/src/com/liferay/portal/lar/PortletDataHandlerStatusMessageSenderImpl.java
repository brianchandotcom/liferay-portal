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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerStatusMessageSender;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactoryUtil;
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

	public void afterPropertiesSet() {
		if (_singleDestinationMessageSender == null) {
			_singleDestinationMessageSender =
				SingleDestinationMessageSenderFactoryUtil.
					createSingleDestinationMessageSender(_destinationName);
		}
	}

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

		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		Message message = createMessage(messageType, manifestSummary);

		message.put("portletId", portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if (portlet != null) {
			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			long portletModelAdditionCountersTotal =
				portletDataHandler.getExportModelCount(manifestSummary);

			if (portletModelAdditionCountersTotal < 0) {
				portletModelAdditionCountersTotal = 0;
			}

			message.put(
				"portletModelAdditionCountersTotal",
				portletModelAdditionCountersTotal);
		}

		_singleDestinationMessageSender.send(message);
	}

	@Override
	public void sendStatusMessage(
		String messageType, String[] portletIds,
		ManifestSummary manifestSummary) {

		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		Message message = createMessage(messageType, manifestSummary);

		message.put("portletIds", portletIds);

		_singleDestinationMessageSender.send(message);
	}

	@Override
	public <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		if (!BackgroundTaskThreadLocal.hasBackgroundTask()) {
			return;
		}

		Message message = createMessage(messageType, manifestSummary);

		StagedModelDataHandler<T> stagedModelDataHandler =
			(StagedModelDataHandler<T>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					stagedModel.getModelClassName());

		message.put(
			"stagedModelName",
			stagedModelDataHandler.getDisplayName(stagedModel));

		message.put(
			"stagedModelType",
			String.valueOf(stagedModel.getStagedModelType()));
		message.put("uuid", stagedModel.getUuid());

		_singleDestinationMessageSender.send(message);
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setDestinationName(String)})
	 */
	@Deprecated
	public void setSingleDestinationMessageSender(
		SingleDestinationMessageSender singleDestinationMessageSender) {

		_singleDestinationMessageSender = singleDestinationMessageSender;
	}

	protected Message createMessage(
		String messageType, ManifestSummary manifestSummary) {

		Message message = new Message();

		message.put(
			"backgroundTaskId",
			BackgroundTaskThreadLocal.getBackgroundTaskId());
		message.put("messageType", messageType);

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		message.put(
			"modelAdditionCounters",
			new HashMap<String, LongWrapper>(modelAdditionCounters));

		Map<String, LongWrapper> modelDeletionCounters =
			manifestSummary.getModelDeletionCounters();

		message.put(
			"modelDeletionCounters",
			new HashMap<String, LongWrapper>(modelDeletionCounters));

		return message;
	}

	private String _destinationName;
	private SingleDestinationMessageSender _singleDestinationMessageSender;

}