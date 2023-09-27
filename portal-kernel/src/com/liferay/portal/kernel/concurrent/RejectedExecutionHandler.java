/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

/**
 * Provides the same interface as {@link
 * java.util.concurrent.RejectedExecutionHandler}.
 *
 * @author Shuyang Zhou
 * @see    java.util.concurrent.RejectedExecutionHandler
 */
public interface RejectedExecutionHandler {

	/**
	 * @see java.util.concurrent.RejectedExecutionHandler#rejectedExecution(
	 *      Runnable, java.util.concurrent.ThreadPoolExecutor)
	 */
	public void rejectedExecution(
		Runnable runnable, ThreadPoolExecutor threadPoolExecutor);

}