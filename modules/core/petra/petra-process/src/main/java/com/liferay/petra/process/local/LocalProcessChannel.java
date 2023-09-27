/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process.local;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.FutureListener;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessChannel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
class LocalProcessChannel<T extends Serializable> implements ProcessChannel<T> {

	LocalProcessChannel(
		NoticeableFuture<T> noticeableFuture,
		ObjectOutputStream objectOutputStream,
		AsyncBroker<Long, Serializable> asyncBroker) {

		_noticeableFuture = noticeableFuture;
		_objectOutputStream = objectOutputStream;
		_asyncBroker = asyncBroker;

		_noticeableFuture.addFutureListener(
			new FutureListener<T>() {

				@Override
				public void complete(Future<T> future) {
					try {
						_objectOutputStream.close();
					}
					catch (IOException ioException) {
					}
					finally {
						Map<Long, NoticeableFuture<Serializable>> map =
							_asyncBroker.getOpenBids();

						for (NoticeableFuture<Serializable> noticeableFuture :
								map.values()) {

							noticeableFuture.cancel(true);
						}
					}
				}

			});
	}

	@Override
	public NoticeableFuture<T> getProcessNoticeableFuture() {
		return _noticeableFuture;
	}

	@Override
	public <V extends Serializable> NoticeableFuture<V> write(
		ProcessCallable<V> processCallable) {

		long id = _idGenerator.getAndIncrement();

		NoticeableFuture<Serializable> noticeableFuture = _asyncBroker.post(id);

		try {
			synchronized (_objectOutputStream) {
				_objectOutputStream.writeObject(
					new RequestProcessCallable<V>(id, processCallable));

				_objectOutputStream.flush();
			}
		}
		catch (IOException ioException) {
			_asyncBroker.takeWithException(id, ioException);
		}

		return (NoticeableFuture<V>)noticeableFuture;
	}

	private final AsyncBroker<Long, Serializable> _asyncBroker;
	private final AtomicLong _idGenerator = new AtomicLong();
	private final NoticeableFuture<T> _noticeableFuture;
	private final ObjectOutputStream _objectOutputStream;

}