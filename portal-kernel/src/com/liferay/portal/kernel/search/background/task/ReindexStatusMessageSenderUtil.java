/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.background.task;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Andrew Betts
 */
public class ReindexStatusMessageSenderUtil {

	public static void sendStatusMessage(
		String className, long count, long total) {

		ReindexStatusMessageSender reindexStatusMessageSender =
			_reindexStatusMessageSenderSnapshot.get();

		reindexStatusMessageSender.sendStatusMessage(className, count, total);
	}

	public static void sendStatusMessage(
		String phase, long companyId, long[] companyIds) {

		ReindexStatusMessageSender reindexStatusMessageSender =
			_reindexStatusMessageSenderSnapshot.get();

		reindexStatusMessageSender.sendStatusMessage(
			phase, companyId, companyIds);
	}

	private static final Snapshot<ReindexStatusMessageSender>
		_reindexStatusMessageSenderSnapshot = new Snapshot<>(
			ReindexStatusMessageSenderUtil.class,
			ReindexStatusMessageSender.class);

}