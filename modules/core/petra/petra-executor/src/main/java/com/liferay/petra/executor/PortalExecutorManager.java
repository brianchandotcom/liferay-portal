/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.executor;

import com.liferay.petra.concurrent.NoticeableExecutorService;

/**
 * @author Shuyang Zhou
 */
public interface PortalExecutorManager {

	public NoticeableExecutorService getPortalExecutor(String name);

	public NoticeableExecutorService getPortalExecutor(
		String name, boolean createIfAbsent);

	public NoticeableExecutorService registerPortalExecutor(
		String name, NoticeableExecutorService noticeableExecutorService);

}