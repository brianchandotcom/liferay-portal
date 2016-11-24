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
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Andrew Betts
 */
@ProviderType
public class ReindexStatusMessageSenderUtil {

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #_getReindexStatusMessageSender()}
	 */
	@Deprecated
	public static ReindexStatusMessageSender
		getReindexStatusMessageSender() {

		return _getReindexStatusMessageSender();
	}

	public static void sendStatusMessage(
		String className, long count, long total) {

		_getReindexStatusMessageSender().sendStatusMessage(
			className, count, total);
	}

	public static void sendStatusMessage(
		String phase, long companyId, long[] companyIds) {

		_getReindexStatusMessageSender().sendStatusMessage(
			phase, companyId, companyIds);
	}

	private static ReindexStatusMessageSender
		_getReindexStatusMessageSender() {

		PortalRuntimePermission.checkGetBeanProperty(
			ReindexStatusMessageSenderUtil.class);

		return _reindexStatusMessageSender;
	}

	private static volatile ReindexStatusMessageSender
		_reindexStatusMessageSender =
			ServiceProxyFactory.newServiceTrackedInstance(
				ReindexStatusMessageSender.class,
				ReindexStatusMessageSenderUtil.class,
				"_reindexStatusMessageSender", false);

}