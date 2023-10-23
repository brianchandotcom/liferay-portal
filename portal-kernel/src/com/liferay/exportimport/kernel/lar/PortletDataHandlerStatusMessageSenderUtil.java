/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Michael C. Han
 */
public class PortletDataHandlerStatusMessageSenderUtil {

	public static void sendStatusMessage(
		String messageType, String portletId, ManifestSummary manifestSummary) {

		PortletDataHandlerStatusMessageSender dataHandlerStatusMessageSender =
			_dataHandlerStatusMessageSenderSnapshot.get();

		dataHandlerStatusMessageSender.sendStatusMessage(
			messageType, portletId, manifestSummary);
	}

	public static void sendStatusMessage(
		String messageType, String[] portletIds,
		ManifestSummary manifestSummary) {

		PortletDataHandlerStatusMessageSender dataHandlerStatusMessageSender =
			_dataHandlerStatusMessageSenderSnapshot.get();

		dataHandlerStatusMessageSender.sendStatusMessage(
			messageType, portletIds, manifestSummary);
	}

	public static <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary) {

		PortletDataHandlerStatusMessageSender dataHandlerStatusMessageSender =
			_dataHandlerStatusMessageSenderSnapshot.get();

		dataHandlerStatusMessageSender.sendStatusMessage(
			messageType, stagedModel, manifestSummary);
	}

	private static final Snapshot<PortletDataHandlerStatusMessageSender>
		_dataHandlerStatusMessageSenderSnapshot = new Snapshot<>(
			PortletDataHandlerStatusMessageSenderUtil.class,
			PortletDataHandlerStatusMessageSender.class);

}