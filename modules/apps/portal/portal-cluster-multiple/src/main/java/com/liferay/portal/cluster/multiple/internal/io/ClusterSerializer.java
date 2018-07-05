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

/**
 * @author Lance Ji
 */
public class ClusterSerializer {

	public static byte[] writeObject(Serializable serializable) {
		try {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			unsyncByteArrayOutputStream.write(SerializationConstants.TC_OBJECT);

			ObjectOutputStream objectOutputStream =
				new ClusterAnnotatedObjectOutputStream(
					unsyncByteArrayOutputStream);

			objectOutputStream.writeObject(serializable);

			objectOutputStream.flush();

			return unsyncByteArrayOutputStream.unsafeGetByteArray();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to write ordinary serializable object " + serializable,
				ioe);
		}
	}

	private static class ClusterAnnotatedObjectOutputStream
		extends ObjectOutputStream {

		@Override
		protected void annotateClass(Class<?> clazz) throws IOException {
			ClassLoader classLoader = clazz.getClassLoader();

			String contextName = ClusterClassLoaderPool.getContextName(
				classLoader);

			writeUTF(contextName);
		}

		private ClusterAnnotatedObjectOutputStream(OutputStream outputStream)
			throws IOException {

			super(outputStream);
		}

	}

}