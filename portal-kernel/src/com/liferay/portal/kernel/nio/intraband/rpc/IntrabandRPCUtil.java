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

package com.liferay.portal.kernel.nio.intraband.rpc;

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.IOException;
import java.io.Serializable;

import java.util.EnumSet;

/**
 * @author Shuyang Zhou
 */
public class IntrabandRPCUtil {

	public static <V extends Serializable> NoticeableFuture<V> execute(
		RegistrationReference registrationReference,
		ProcessCallable<V> processCallable) {

		Intraband intraband = registrationReference.getIntraband();

		SystemDataType systemDataType = SystemDataType.RPC;

		Serializer serializer = new Serializer();

		serializer.writeObject(processCallable);

		Datagram datagram = Datagram.createRequestDatagram(
			systemDataType.getValue(), serializer.toByteBuffer());

		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		intraband.sendDatagram(
			registrationReference, datagram, null, repliedEnumSet,
			new FutureCompletionHandler<V>(defaultNoticeableFuture));

		return defaultNoticeableFuture;
	}

	protected static EnumSet<CompletionHandler.CompletionType> repliedEnumSet =
		EnumSet.of(CompletionHandler.CompletionType.REPLIED);

	protected static class FutureCompletionHandler<V extends Serializable>
		implements CompletionHandler<Object> {

		@Override
		public void delivered(Object attachment) {
		}

		@Override
		public void failed(Object attachment, IOException ioe) {
			_defaultNoticeableFuture.setException(ioe);
		}

		@Override
		public void replied(Object attachment, Datagram datagram) {
			Deserializer deserializer = new Deserializer(
				datagram.getDataByteBuffer());

			try {
				RPCResponse rpcResponse = deserializer.readObject();

				Exception exception = rpcResponse.getException();

				if (exception != null) {
					_defaultNoticeableFuture.setException(exception);
				}
				else {
					_defaultNoticeableFuture.set((V)rpcResponse.getResult());
				}
			}
			catch (ClassNotFoundException cnfe) {
				_defaultNoticeableFuture.setException(cnfe);
			}
		}

		@Override
		public void submitted(Object attachment) {
		}

		@Override
		public void timedOut(Object attachment) {
			_defaultNoticeableFuture.cancel(true);
		}

		protected FutureCompletionHandler(
			DefaultNoticeableFuture<V> defaultNoticeableFuture) {

			_defaultNoticeableFuture = defaultNoticeableFuture;
		}

		private final DefaultNoticeableFuture<V> _defaultNoticeableFuture;

	}

}