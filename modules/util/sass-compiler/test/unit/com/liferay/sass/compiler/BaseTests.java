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

package com.liferay.sass.compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Gregory Amerson
 */
public class BaseTests {

	protected String readFileContents(String filename) throws Exception {
		return readStreamToString(
			new FileInputStream(new File(filename))).replaceAll("\\r", "");
	}

	protected String readStreamToString(InputStream contents) throws Exception {
		if (contents == null) {
			return null;
		}

		final char[] buffer = new char[0x10000];

		StringBuilder out = new StringBuilder();

		Reader in = new InputStreamReader(contents, "UTF-8");

		int read;
		do {
			read = in.read(buffer, 0, buffer.length);

			if (read > 0) {
				out.append(buffer, 0, read);
			}
		}
		while (read >= 0);

		contents.close();

		return out.toString();
	}

	protected String stripNewLines(String str) {
		return str.replaceAll("\\n|\\r", "").replaceAll("\\s+", " ");
	}

}