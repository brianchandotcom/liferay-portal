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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.StagedModel;

/**
 * @author Michael C. Han
 */
public class DataHandlerStatusMessageSenderUtil {

	public static DataHandlerStatusMessageSender
		getDataHandlerStatusMessageSender() {

		PortalRuntimePermission.checkGetBeanProperty(
			DataHandlerStatusMessageSenderUtil.class);

		return _dataHandlerStatusMessageSender;
	}

	public static void sendStatusMessage(
		String messageType, String portletId, ManifestSummary manifestSummary) {

		getDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, portletId, manifestSummary);
	}

	public static <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		getDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, stagedModel, manifestSummary);
	}

	public void setDataHandlerStatusMessageSender(
		DataHandlerStatusMessageSender dataHandlerStatusMessageSender) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_dataHandlerStatusMessageSender = dataHandlerStatusMessageSender;
	}

	private static DataHandlerStatusMessageSender
		_dataHandlerStatusMessageSender;

}