/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;

/**
 * @author Shuyang Zhou
 */
public class AutoDeleteFileInputStream extends FileInputStream {

	public AutoDeleteFileInputStream(File file) throws FileNotFoundException {
		super(file);

		_file = file;
	}

	@Override
	public void close() throws IOException {
		if (_closed) {
			return;
		}

		_closed = true;

		super.close();

		Files.deleteIfExists(_file.toPath());
	}

	private boolean _closed;
	private final File _file;

}