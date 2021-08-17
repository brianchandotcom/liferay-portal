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

package com.liferay.portal.kernel.nio.intraband.welder.test;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class WelderTestUtil {

	public static void assertConnectted(
			final ScatteringByteChannel scatteringByteChannel,
			final GatheringByteChannel gatheringByteChannel)
		throws Exception {

		Random random = new Random();

		final byte[] data = new byte[1024 * 1024];

		random.nextBytes(data);

		FutureTask<Void> writeFutureTask = new FutureTask<Void>(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					ByteBuffer byteBuffer = ByteBuffer.wrap(data);

					while (byteBuffer.hasRemaining()) {
						gatheringByteChannel.write(byteBuffer);
					}

					return null;
				}

			});

		Thread writeThread = new Thread(writeFutureTask);

		writeThread.start();

		FutureTask<byte[]> readFutureTask = new FutureTask<byte[]>(
			new Callable<byte[]>() {

				@Override
				public byte[] call() throws Exception {
					ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);

					while (byteBuffer.hasRemaining()) {
						scatteringByteChannel.read(byteBuffer);
					}

					return byteBuffer.array();
				}

			});

		Thread readThread = new Thread(readFutureTask);

		readThread.start();

		writeFutureTask.get();

		Assert.assertArrayEquals(data, readFutureTask.get());
	}

	public static <T extends Welder> T transform(T welder) throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream)) {

			objectOutputStream.writeObject(welder);
		}

		ByteBuffer byteBuffer =
			unsyncByteArrayOutputStream.unsafeGetByteBuffer();

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(
				byteBuffer.array(), 0, byteBuffer.remaining());

		ObjectInputStream objectInputStream = new ObjectInputStream(
			unsyncByteArrayInputStream);

		return (T)objectInputStream.readObject();
	}

}