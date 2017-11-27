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

package com.liferay.petra.process;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shuyang Zhou
 */
public class CollectorOutputProcessor
	implements OutputProcessor<byte[], byte[]> {

	public static final OutputProcessor<byte[], byte[]> INSTANCE =
		new CollectorOutputProcessor();

	@Override
	public byte[] processStdErr(InputStream stdErrInputStream)
		throws ProcessException {

		return _collect(stdErrInputStream);
	}

	@Override
	public byte[] processStdOut(InputStream stdOutInputStream)
		throws ProcessException {

		return _collect(stdOutInputStream);
	}

	private byte[] _collect(InputStream inputStream) throws ProcessException {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try {
			StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream);
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}

		return unsyncByteArrayOutputStream.toByteArray();
	}

}