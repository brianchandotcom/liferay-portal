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

package com.liferay.portal.fabric.local.worker;

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessException;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class EmbeddedProcessChannel<T extends Serializable>
	implements ProcessChannel<T> {

	public EmbeddedProcessChannel(NoticeableFuture<T> noticeableFuture) {
		_noticeableFuture = noticeableFuture;
	}

	@Override
	public NoticeableFuture<T> getProcessNoticeableFuture() {
		return _noticeableFuture;
	}

	@Override
	public <V extends Serializable> NoticeableFuture<V> write(
		ProcessCallable<V> processCallable) {

		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		try {
			defaultNoticeableFuture.set(processCallable.call());
		}
		catch (ProcessException pe) {
			defaultNoticeableFuture.setException(pe);
		}

		return defaultNoticeableFuture;
	}

	private final NoticeableFuture<T> _noticeableFuture;

}