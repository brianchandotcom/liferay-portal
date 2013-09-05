/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		super.close();

		if (!_file.delete()) {
			_file.deleteOnExit();
		}
	}

	private final File _file;

}