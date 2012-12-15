/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.welder.fifo;

import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.channels.FileChannel;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class FIFOWelder implements Welder {

	public FIFOWelder() throws IOException {
		String tempFolderName = System.getProperty("java.io.tmpdir");

		long id = ID_COUNTER.getAndIncrement();

		inputFIFOFile = new File(tempFolderName, "FIFO-INPUT-" + id);
		outputFIFOFile = new File(tempFolderName, "FIFO-OUTPUT-" + id);

		try {
			FIFOUtil.createFIFO(inputFIFOFile);
			FIFOUtil.createFIFO(outputFIFOFile);
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	// Warning! Don't try to abstract a common method for weldClient() and
	// weldServer(), the file open order matters, reverse them will cause
	// deadlock.

	public RegistrationReference weldClient(IntraBand intraBand)
		throws IOException {

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(
				inputFIFOFile);
			FileChannel writeFileChannel = fileOutputStream.getChannel();

			FileInputStream fileInputStream = new AutoRemoveFileInputStream(
				outputFIFOFile);

			FileChannel readFileChannel = fileInputStream.getChannel();

			return intraBand.registerChannel(readFileChannel, writeFileChannel);
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	public RegistrationReference weldServer(IntraBand intraBand)
		throws IOException {

		try {
			FileInputStream fileInputStream = new AutoRemoveFileInputStream(
				inputFIFOFile);

			FileChannel readFileChannel = fileInputStream.getChannel();

			FileOutputStream fileOutputStream = new FileOutputStream(
				outputFIFOFile);

			FileChannel writeFileChannel = fileOutputStream.getChannel();

			return intraBand.registerChannel(readFileChannel, writeFileChannel);
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected static final AtomicLong ID_COUNTER = new AtomicLong(
		System.currentTimeMillis());

	// Input/Output is relative to server, it is contrary on client side

	protected final File inputFIFOFile;
	protected final File outputFIFOFile;

	protected static class AutoRemoveFileInputStream extends FileInputStream {

		public AutoRemoveFileInputStream(File file)
			throws FileNotFoundException {

			super(file);
			_file = file;
		}

		@Override
		public void close() throws IOException {
			super.close();

			if (!_file.delete()) {
				_file.deleteOnExit();
			}
		}

		private final File _file;

	}

}