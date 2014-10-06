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

package com.liferay.portal.fabric.netty.rpc;

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class SyncProcessRPCCallable<T extends Serializable>
	implements RPCCallable<T> {

	public SyncProcessRPCCallable(ProcessCallable<T> processCallable) {
		_processCallable = processCallable;
	}

	@Override
	public NoticeableFuture<T> call() {
		DefaultNoticeableFuture<T> defaultNoticeableFuture =
			new DefaultNoticeableFuture<T>();

		try {
			defaultNoticeableFuture.set(_processCallable.call());
		}
		catch (Throwable t) {
			defaultNoticeableFuture.setException(t);
		}

		return defaultNoticeableFuture;
	}

	private static final long serialVersionUID = 1L;

	private final ProcessCallable<T> _processCallable;

}