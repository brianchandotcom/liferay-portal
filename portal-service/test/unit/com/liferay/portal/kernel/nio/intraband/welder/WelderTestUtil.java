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

package com.liferay.portal.kernel.nio.intraband.welder;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.nio.ByteBuffer;

/**
 * @author Shuyang Zhou
 */
public class WelderTestUtil {

	public static <T extends Welder> T transform(T welder) throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			unsyncByteArrayOutputStream);

		objectOutputStream.writeObject(welder);

		objectOutputStream.close();

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