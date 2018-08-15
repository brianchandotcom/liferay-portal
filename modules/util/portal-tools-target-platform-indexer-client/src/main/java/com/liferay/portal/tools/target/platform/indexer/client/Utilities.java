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

package com.liferay.portal.tools.target.platform.indexer.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/**
 * @author Shuyang Zhou
 */
public class Utilities {

	public static List<File> listFiles(String dir, String glob)
		throws IOException {

		Path path = Paths.get(dir);

		if (Files.notExists(path)) {
			return Collections.emptyList();
		}

		List<File> files = new ArrayList<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				path, glob)) {

			Iterator<Path> iterator = directoryStream.iterator();

			while (iterator.hasNext()) {
				Path lpkgPath = iterator.next();

				files.add(lpkgPath.toFile());
			}
		}

		return files;
	}

	public static String readURL(URL url) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (InputStream inputStream = url.openStream()) {
			byte[] buffer = new byte[1024];

			int length = -1;

			while ((length = inputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, length);
			}
		}

		return byteArrayOutputStream.toString("UTF-8");
	}

	public static String toChecksum(URI uri) throws Exception {
		String content = readURL(uri.toURL());

		Matcher matcher = _incrementPattern.matcher(content);

		if (matcher.find()) {
			String start = content.substring(0, matcher.start(1));
			String end = content.substring(matcher.end(1));

			content = start.concat(end);
		}

		CRC32 crc32 = new CRC32();

		crc32.update(content.getBytes(StandardCharsets.UTF_8));

		return Long.toHexString(crc32.getValue());
	}

	public static String toIntegrityKey(URI uri) {
		String integrityKey = uri.getPath();

		int index = integrityKey.lastIndexOf('/');

		if (index != -1) {
			integrityKey = integrityKey.substring(index + 1);
		}

		return integrityKey;
	}

	private static final Pattern _incrementPattern = Pattern.compile(
		"<repository( increment=\"\\d*\")");

}