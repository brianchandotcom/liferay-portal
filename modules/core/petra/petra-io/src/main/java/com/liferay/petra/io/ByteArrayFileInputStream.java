/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io;

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

		_file = file;

		_fileSize = file.length();

		_threshold = threshold;
		_deleteOnClose = deleteOnClose;
	}

	@Override
	public int available() throws IOException {
		if (_data != null) {
			return _data.length - _index;
		}
		else if (_fileInputStream != null) {
			return _fileInputStream.available();
		}

		return 0;
	}

	@Override
	public void close() throws IOException {
		try {
			if (_fileInputStream != null) {
				_fileInputStream.close();
			}
		}
		finally {
			_data = null;
			_fileInputStream = null;

			if (_deleteOnClose && (_file != null)) {
				_file.delete();
			}

			_file = null;
		}
	}

	public File getFile() {
		return _file;
	}

	@Override
	public void mark(int readLimit) {
		_markIndex = _index;
	}

	@Override
	public boolean markSupported() {
		if (_fileSize < _threshold) {
			return true;
		}

		return false;
	}

	@Override
	public int read() throws IOException {
		if (_fileSize < _threshold) {
			_initData();

			if (_index < _data.length) {
				return _data[_index++] & 0xff;
			}

			return -1;
		}

		_initFileInputStream();

		return _fileInputStream.read();
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

		if (_fileSize < _threshold) {
			_initData();

			if (_index >= _data.length) {
				return -1;
			}

			int read = length;

			if ((_index + read) > _data.length) {
				read = _data.length - _index;
			}

			System.arraycopy(_data, _index, bytes, offset, read);

			_index += read;

			return read;
		}

		_initFileInputStream();

		return _fileInputStream.read(bytes, offset, length);
	}

	@Override
	public void reset() throws IOException {
		if (_data != null) {
			_index = _markIndex;
		}
		else if (_fileInputStream != null) {
			_fileInputStream.close();

			_fileInputStream = null;
		}
	}

	@Override
	public long skip(long skip) throws IOException {
		if (skip < 0) {
			return 0;
		}

		if (_fileSize < _threshold) {
			_initData();

			if ((skip + _index) > _data.length) {
				skip = _data.length - _index;
			}

			_index += skip;

			return skip;
		}

		_initFileInputStream();

		return _fileInputStream.skip(skip);
	}

	private void _initData() throws IOException {
		if (_data != null) {
			return;
		}

		int arraySize = (int)_fileSize;

		_data = new byte[arraySize];

		FileInputStream fileInputStream = new FileInputStream(_file);

		int offset = 0;
		int length = 0;

		try {
			while (offset < arraySize) {
				length = fileInputStream.read(
					_data, offset, arraySize - offset);

				offset += length;
			}
		}
		finally {
			fileInputStream.close();
		}
	}

	private void _initFileInputStream() throws IOException {
		if (_fileInputStream == null) {
			_fileInputStream = new FileInputStream(_file);
		}
	}

	private byte[] _data;
	private boolean _deleteOnClose;
	private File _file;
	private FileInputStream _fileInputStream;
	private long _fileSize;
	private int _index;
	private int _markIndex;
	private int _threshold;

}