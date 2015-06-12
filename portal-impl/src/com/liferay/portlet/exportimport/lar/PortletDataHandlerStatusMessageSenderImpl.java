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
import com.liferay.portal.model.StagedModel;

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
				new PortletDataHandlerBackgroundTaskStatusMessage(
					messageType, portletId, manifestSummary);

		_backgroundTaskStatusMessageSender.setBackgroundTaskStatusMessage(
			portletDataHandlerBackgroundTaskStatusMessage);
	}

	@Override
	public void sendStatusMessage(
		String messageType, String[] portletIds,
		ManifestSummary manifestSummary) {

		PortletDataHandlerBackgroundTaskStatusMessage
			portletDataHandlerBackgroundTaskStatusMessage =
				new PortletDataHandlerBackgroundTaskStatusMessage(
					messageType, portletIds, manifestSummary);

		_backgroundTaskStatusMessageSender.setBackgroundTaskStatusMessage(
			portletDataHandlerBackgroundTaskStatusMessage);
	}

	@Override
	public <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		PortletDataHandlerBackgroundTaskStatusMessage
			portletDataHandlerBackgroundTaskStatusMessage =
				new PortletDataHandlerBackgroundTaskStatusMessage(
					messageType, stagedModel, manifestSummary);

		_backgroundTaskStatusMessageSender.setBackgroundTaskStatusMessage(
			portletDataHandlerBackgroundTaskStatusMessage);
	}

	public void setBackgroundTaskStatusMessageSender(
		BackgroundTaskStatusMessageSender backgroundTaskStatusMessageSender) {

		_backgroundTaskStatusMessageSender = backgroundTaskStatusMessageSender;
	}

	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

}