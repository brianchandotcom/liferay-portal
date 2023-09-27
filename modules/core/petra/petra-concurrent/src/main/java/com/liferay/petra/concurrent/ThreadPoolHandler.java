/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

/**
 * @author Shuyang Zhou
 */
public interface ThreadPoolHandler {

	public void afterExecute(Runnable runnable, Throwable throwable);

	public void beforeExecute(Thread thread, Runnable runnable);

	public void terminated();

}