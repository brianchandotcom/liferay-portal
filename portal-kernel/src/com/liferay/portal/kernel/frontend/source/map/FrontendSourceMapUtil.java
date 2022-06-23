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

package com.liferay.portal.kernel.frontend.source.map;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.charset.StandardCharsets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Chi Le
 */
public class FrontendSourceMapUtil {

	public static String stripCSSSourceMapping(String text) {
		if (text == null) {
			return text;
		}

		Matcher matcher = _cssPattern.matcher(text);

		return matcher.replaceAll(StringPool.BLANK);
	}

	public static String stripJSSourceMapping(String text) {
		if (text == null) {
			return text;
		}

		Matcher matcher = _jsPattern.matcher(text);

		return matcher.replaceAll(StringPool.BLANK);
	}

	public static void transferCSS(
			InputStream inputStream, OutputStream outputStream)
		throws IOException {

		String text = StringUtil.read(inputStream);

		if (text != null) {
			text = stripCSSSourceMapping(text);

			outputStream.write(text.getBytes(StandardCharsets.UTF_8));
		}
	}

	public static void transferJS(
			InputStream inputStream, OutputStream outputStream)
		throws IOException {

		String text = StringUtil.read(inputStream);

		if (text != null) {
			text = stripJSSourceMapping(text);

			outputStream.write(text.getBytes(StandardCharsets.UTF_8));
		}
	}

	private static final Pattern _cssPattern = Pattern.compile(
		"\\/\\*#\\s*sourceMappingURL=.+?\\.css\\.map\\s*\\*\\/");
	private static final Pattern _jsPattern = Pattern.compile(
		"\\/\\/#\\s*sourceMappingURL=.+?\\.js\\.map$");

}