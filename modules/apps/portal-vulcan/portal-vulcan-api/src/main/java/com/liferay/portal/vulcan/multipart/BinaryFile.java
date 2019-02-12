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

package com.liferay.portal.vulcan.multipart;

import java.io.InputStream;

/**
 * @author Javier Gamarra
 */
public class BinaryFile {

	public BinaryFile(
		InputStream inputStream, long size, String contentType,
		String fileName) {

		_inputStream = inputStream;
		_size = size;
		_contentType = contentType;
		_fileName = fileName;
	}

	public String getContentType() {
		return _contentType;
	}

	public String getFileName() {
		return _fileName;
	}

	public InputStream getInputStream() {
		return _inputStream;
	}

	public long getSize() {
		return _size;
	}

	private final String _contentType;
	private final String _fileName;
	private final InputStream _inputStream;
	private final long _size;

}