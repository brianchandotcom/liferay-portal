/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.multipart;

import java.io.InputStream;

/**
 * @author Javier Gamarra
 */
public class BinaryFile {

	public BinaryFile(
		String contentType, String fileName, InputStream inputStream,
		long size) {

		_contentType = contentType;
		_fileName = fileName;
		_inputStream = inputStream;
		_size = size;
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