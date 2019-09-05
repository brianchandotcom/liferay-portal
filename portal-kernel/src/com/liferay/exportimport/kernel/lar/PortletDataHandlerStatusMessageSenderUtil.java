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

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 */
public class PortletDataHandlerStatusMessageSenderUtil {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #_getPortletDataHandlerStatusMessageSender()}
	 */
	@Deprecated
	public static PortletDataHandlerStatusMessageSender
		getPortletDataHandlerStatusMessageSender() {

		return _getPortletDataHandlerStatusMessageSender();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #sendStatusMessage(String, String[], ManifestSummary)}
	 */
	@Deprecated
	public static void sendStatusMessage(
		String messageType, ManifestSummary manifestSummary) {

		_getPortletDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, manifestSummary);
	}

	public static void sendStatusMessage(
		String messageType, String portletId, ManifestSummary manifestSummary) {

		_getPortletDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, portletId, manifestSummary);
	}

	public static void sendStatusMessage(
		String messageType, String[] portletIds,
		ManifestSummary manifestSummary) {

		_getPortletDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, portletIds, manifestSummary);
	}

	public static <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		_getPortletDataHandlerStatusMessageSender().sendStatusMessage(
			messageType, stagedModel, manifestSummary);
	}

	private static PortletDataHandlerStatusMessageSender
		_getPortletDataHandlerStatusMessageSender() {

		return _dataHandlerStatusMessageSender;
	}

	private static volatile PortletDataHandlerStatusMessageSender
		_dataHandlerStatusMessageSender =
			ServiceProxyFactory.newServiceTrackedInstance(
				PortletDataHandlerStatusMessageSender.class,
				PortletDataHandlerStatusMessageSenderUtil.class,
				"_dataHandlerStatusMessageSender", false);

}