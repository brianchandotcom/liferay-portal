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

package com.liferay.portal.kernel.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.StagedModel;

/**
 * @author Michael C. Han
 */
@ProviderType
public class PortletDataHandlerStatusMessageSenderUtil {

	public static PortletDataHandlerStatusMessageSender
		getPortletDataHandlerStatusMessageSender() {

		PortalRuntimePermission.checkGetBeanProperty(
			PortletDataHandlerStatusMessageSenderUtil.class);

		return _dataHandlerStatusMessageSender;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #sendStatusMessage(String,
	 *             String[], ManifestSummary)}
	 */
	@Deprecated
	public static void sendStatusMessage(
		String messageType, ManifestSummary manifestSummary) {

		getPortletDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, manifestSummary);
	}

	public static void sendStatusMessage(
		String messageType, String portletId, ManifestSummary manifestSummary) {

		getPortletDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, portletId, manifestSummary);
	}

	public static void sendStatusMessage(
		String messageType, String[] portletIds,
		ManifestSummary manifestSummary) {

		getPortletDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, portletIds, manifestSummary);
	}

	public static <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		getPortletDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, stagedModel, manifestSummary);
	}

	public void setPortletDataHandlerStatusMessageSender(
		PortletDataHandlerStatusMessageSender
			portletDataHandlerStatusMessageSender) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_dataHandlerStatusMessageSender = portletDataHandlerStatusMessageSender;
	}

	private static PortletDataHandlerStatusMessageSender
		_dataHandlerStatusMessageSender;

}