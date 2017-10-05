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

package com.liferay.css.builder.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Andrea Di Giorgi
 */
public class FileTestUtil {

	public static void changeContentInPath(
			Path path, String s, String replacement)
		throws Exception {

		Charset charset = StandardCharsets.UTF_8;

		String content = new String(Files.readAllBytes(path), charset);

		content = content.replace(s, replacement);

		Files.write(path, content.getBytes(charset));
	}

	public static String read(String fileName) throws Exception {
		Path path = Paths.get(fileName);

		String s = new String(Files.readAllBytes(path), StringPool.UTF8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

}
