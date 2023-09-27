/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.background.task;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Andrew Betts
 */
public class ReindexStatusMessageSenderUtil {

	public static void sendStatusMessage(
		String className, long count, long total) {

		_reindexStatusMessageSender.sendStatusMessage(className, count, total);
	}

	public static void sendStatusMessage(
		String phase, long companyId, long[] companyIds) {

		_reindexStatusMessageSender.sendStatusMessage(
			phase, companyId, companyIds);
	}

	private static volatile ReindexStatusMessageSender
		_reindexStatusMessageSender =
			ServiceProxyFactory.newServiceTrackedInstance(
				ReindexStatusMessageSender.class,
				ReindexStatusMessageSenderUtil.class,
				"_reindexStatusMessageSender", false);

}