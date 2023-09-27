/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author Shuyang Zhou
 */
public interface NoticeableExecutorService extends ExecutorService {

	@Override
	public <T> NoticeableFuture<T> submit(Callable<T> callable);

	@Override
	public NoticeableFuture<?> submit(Runnable runnable);

	@Override
	public <T> NoticeableFuture<T> submit(Runnable runnable, T result);

	public NoticeableFuture<Void> terminationNoticeableFuture();

}