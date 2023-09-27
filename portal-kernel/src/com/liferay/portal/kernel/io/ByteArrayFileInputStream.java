/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shuyang Zhou
 */
public class ByteArrayFileInputStream extends InputStream {

	public ByteArrayFileInputStream(File file, int threshold) {
		this(file, threshold, false);
	}

	public ByteArrayFileInputStream(
		File file, int threshold, boolean deleteOnClose) {

		if (!file.exists() || !file.isFile()) {
			throw new IllegalArgumentException(
				"File " + file.getAbsolutePath() + " does not exist");
		}

		this.file = file;

		fileSize = file.length();

		this.threshold = threshold;
		this.deleteOnClose = deleteOnClose;
	}

	@Override
	public int available() throws IOException {
		if (data != null) {
			return data.length - index;
		}
		else if (fileInputStream != null) {
			return fileInputStream.available();
		}

		return 0;
	}

	@Override
	public void close() throws IOException {
		try {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		finally {
			data = null;
			fileInputStream = null;

			if (deleteOnClose && (file != null)) {
				file.delete();
			}

			file = null;
		}
	}

	public File getFile() {
		return file;
	}

	@Override
	public void mark(int readLimit) {
		markIndex = index;
	}

	@Override
	public boolean markSupported() {
		if (fileSize < threshold) {
			return true;
		}

		return false;
	}

	@Override
	public int read() throws IOException {
		if (fileSize < threshold) {
			initData();

			if (index < data.length) {
				return data[index++] & 0xff;
			}

			return -1;
		}

		initFileInputStream();

		return fileInputStream.read();
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		return read(bytes, 0, bytes.length);
	}

	@Override
	public int read(byte[] bytes, int offset, int length) throws IOException {
		if (length <= 0) {
			return 0;
		}

		if (fileSize < threshold) {
			initData();

			if (index >= data.length) {
				return -1;
			}

			int read = length;

			if ((index + read) > data.length) {
				read = data.length - index;
			}

			System.arraycopy(data, index, bytes, offset, read);

			index += read;

			return read;
		}

		initFileInputStream();

		return fileInputStream.read(bytes, offset, length);
	}

	@Override
	public void reset() throws IOException {
		if (data != null) {
			index = markIndex;
		}
		else if (fileInputStream != null) {
			fileInputStream.close();

			fileInputStream = null;
		}
	}

	@Override
	public long skip(long skip) throws IOException {
		if (skip < 0) {
			return 0;
		}

		if (fileSize < threshold) {
			initData();

			if ((skip + index) > data.length) {
				skip = data.length - index;
			}

			index += skip;

			return skip;
		}

		initFileInputStream();

		return fileInputStream.skip(skip);
	}

	protected void initData() throws IOException {
		if (data != null) {
			return;
		}

		int arraySize = (int)fileSize;

		data = new byte[arraySize];

		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			int offset = 0;
			int length = 0;

			while (offset < arraySize) {
				length = fileInputStream.read(data, offset, arraySize - offset);

				offset += length;
			}
		}
	}

	protected void initFileInputStream() throws IOException {
		if (fileInputStream == null) {
			fileInputStream = new FileInputStream(file);
		}
	}

	protected byte[] data;
	protected boolean deleteOnClose;
	protected File file;
	protected FileInputStream fileInputStream;
	protected long fileSize;
	protected int index;
	protected int markIndex;
	protected int threshold;

}