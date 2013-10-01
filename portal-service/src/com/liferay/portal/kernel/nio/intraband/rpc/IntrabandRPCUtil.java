/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler.CompletionType;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.io.IOException;
import java.io.Serializable;

import java.util.EnumSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Shuyang Zhou
 */
public class IntrabandRPCUtil {

	public static <V extends Serializable> Future<V> execute(
			RegistrationReference registrationReference,
			ProcessCallable<V> processCallable)
		throws IntrabandRPCException {

		Intraband intraband = registrationReference.getIntraband();

		SystemDataType systemDataType = SystemDataType.RPC;

		Serializer serializer = new Serializer();

		serializer.writeObject(processCallable);

		try {
			Datagram datagram = Datagram.createRequestDatagram(
				systemDataType.getValue(), serializer.toByteBuffer());

			FutureResult<V> futureResult = new FutureResult<V>();

			intraband.sendDatagram(
				registrationReference, datagram, null, REPLIED_ENUM_SET,
				new FutureCompletionHandler<V>(futureResult));

			return futureResult;
		}
		catch (Exception e) {
			throw new IntrabandRPCException(e);
		}
	}

	protected static final Callable<Serializable> EMPTY_CALLABLE =
		new Callable<Serializable>() {

		@Override
		public Serializable call() throws Exception {
			return null;
		}

	};

	protected static final EnumSet<CompletionType> REPLIED_ENUM_SET =
		EnumSet.of(CompletionType.REPLIED);

	protected static class FutureCompletionHandler<V extends Serializable>
		implements CompletionHandler<Object> {

		protected FutureCompletionHandler(FutureResult<V> futureResult) {
			_futureResult = futureResult;
		}

		@Override
		public void delivered(Object attachment) {
		}

		@Override
		public void failed(Object attachment, IOException ioe) {
			_futureResult.setException(ioe);
		}

		@Override
		public void replied(Object attachment, Datagram datagram) {
			Deserializer deserializer = new Deserializer(
				datagram.getDataByteBuffer());

			try {
				V v = deserializer.readObject();

				_futureResult.set(v);
			}
			catch (ClassNotFoundException cnfe) {
				_futureResult.setException(cnfe);
			}
		}

		@Override
		public void submitted(Object attachment) {
		}

		@Override
		public void timeouted(Object attachment) {
			_futureResult.cancel(true);
		}

		private final FutureResult<V> _futureResult;

	}

	protected static class FutureResult<V extends Serializable>
		extends FutureTask<V> {

		protected FutureResult() {
			super((Callable<V>)EMPTY_CALLABLE);
		}

		@Override
		protected void set(V v) {
			super.set(v);
		}

		@Override
		protected void setException(Throwable t) {
			super.setException(t);
		}

	}

}