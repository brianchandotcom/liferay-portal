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

package com.liferay.portal.cluster.multiple.internal.io;

import com.liferay.petra.io.SerializationConstants;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;

/**
 * @author Lance Ji
 */
public class ClusterSerializer {

	public ClusterSerializer() {
		_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
	}

	public ByteBuffer toByteBuffer() {
		return _unsyncByteArrayOutputStream.unsafeGetByteBuffer();
	}

	public void writeObject(Serializable serializable) {
		try {
			_unsyncByteArrayOutputStream.write(
				SerializationConstants.TC_OBJECT);

			ObjectOutputStream objectOutputStream =
				new ClusterAnnotatedObjectOutputStream(
					_unsyncByteArrayOutputStream);

			objectOutputStream.writeObject(serializable);

			objectOutputStream.flush();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to write ordinary serializable object " + serializable,
				ioe);
		}
	}

	private final UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;

	private class ClusterAnnotatedObjectOutputStream
		extends ObjectOutputStream {

		public ClusterAnnotatedObjectOutputStream(OutputStream outputStream)
			throws IOException {

			super(outputStream);
		}

		@Override
		protected void annotateClass(Class<?> clazz) throws IOException {
			ClassLoader classLoader = clazz.getClassLoader();

			String contextName = ClusterClassLoaderPool.getContextName(
				classLoader);

			writeUTF(contextName);
		}

	}

}