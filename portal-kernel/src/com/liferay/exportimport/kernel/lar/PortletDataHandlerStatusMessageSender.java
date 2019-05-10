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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface PortletDataHandlerStatusMessageSender {

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #sendStatusMessage(String, String[], ManifestSummary)}
	 */
	@Deprecated
	public void sendStatusMessage(
		String messageType, ManifestSummary manifestSummary);

	public void sendStatusMessage(
		String messageType, String portletId, ManifestSummary manifestSummary);

	public void sendStatusMessage(
		String messageType, String[] portletIds,
		ManifestSummary manifestSummary);

	public <T extends StagedModel> void sendStatusMessage(
		String messageType, T stagedModel, ManifestSummary manifestSummary);

}