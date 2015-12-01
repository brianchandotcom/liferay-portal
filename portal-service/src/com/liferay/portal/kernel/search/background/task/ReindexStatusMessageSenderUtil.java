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

package com.liferay.portal.kernel.search.background.task;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Andrew Betts
 */
@ProviderType
public class ReindexStatusMessageSenderUtil {

	public static ReindexStatusMessageSender
		getReindexStatusMessageSender() {

		PortalRuntimePermission.checkGetBeanProperty(
			ReindexStatusMessageSenderUtil.class);

		return _reindexStatusMessageSender;
	}

	public static void sendStatusMessage(
		String className, long progress, long count) {

		getReindexStatusMessageSender().sendStatusMessage(
			className, progress, count);
	}

	public static void sendStatusMessage(
		String phase, long companyId, long[] companyIds) {

		getReindexStatusMessageSender().sendStatusMessage(
			phase, companyId, companyIds);
	}

	public void setReindexStatusMessageSender(
		ReindexStatusMessageSender reindexStatusMessageSender) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_reindexStatusMessageSender = reindexStatusMessageSender;
	}

	private static ReindexStatusMessageSender _reindexStatusMessageSender;

}