/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.kernel.model;

import java.io.InputStream;

/**
 * @author Barrie Selack
 * @author Brian Wing Shun Chan
 */
public class FileAttachment {

	public FileAttachment(String fileName, InputStream inputStream) {
		_fileName = fileName;
		_inputStream = inputStream;
	}

	public String getFileName() {
		return _fileName;
	}

	public InputStream getInputStream() {
		return _inputStream;
	}

	private final String _fileName;
	private final InputStream _inputStream;

}