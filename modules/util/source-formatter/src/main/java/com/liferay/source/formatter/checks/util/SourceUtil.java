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

package com.liferay.source.formatter.checks.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.portal.xml.SAXReaderFactory;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * @author Hugo Huijser
 */
public class SourceUtil {

	public static String getAbsolutePath(File file) {
		return _getAbsolutePath(file.toPath());
	}

	public static String getAbsolutePath(String fileName) {
		return _getAbsolutePath(Paths.get(fileName));
	}

	public static String getIndent(String s) {
		StringBundler sb = new StringBundler(s.length());

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != CharPool.TAB) {
				break;
			}

			sb.append(CharPool.TAB);
		}

		return sb.toString();
	}

	public static int getLevel(String s) {
		return getLevel(
			s, new String[] {StringPool.OPEN_PARENTHESIS},
			new String[] {StringPool.CLOSE_PARENTHESIS}, 0);
	}

	public static int getLevel(
		String s, String increaseLevelString, String decreaseLevelString) {

		return getLevel(
			s, new String[] {increaseLevelString},
			new String[] {decreaseLevelString}, 0);
	}

	public static int getLevel(
		String s, String[] increaseLevelStrings,
		String[] decreaseLevelStrings) {

		return getLevel(s, increaseLevelStrings, decreaseLevelStrings, 0);
	}

	public static int getLevel(
		String s, String[] increaseLevelStrings, String[] decreaseLevelStrings,
		int startLevel) {

		int level = startLevel;

		for (String increaseLevelString : increaseLevelStrings) {
			level = _adjustLevel(level, s, increaseLevelString, 1);
		}

		for (String decreaseLevelString : decreaseLevelStrings) {
			level = _adjustLevel(level, s, decreaseLevelString, -1);
		}

		return level;
	}

	public static Document readXML(String content) throws Exception {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		return saxReader.read(new UnsyncStringReader(content));
	}

	private static int _adjustLevel(
		int level, String text, String s, int diff) {

		String[] lines = StringUtil.splitLines(text);

		forLoop:
		for (String line : lines) {
			line = StringUtil.trim(line);

			if (line.startsWith("//")) {
				continue;
			}

			int x = -1;

			while (true) {
				x = line.indexOf(s, x + 1);

				if (x == -1) {
					continue forLoop;
				}

				if (!ToolsUtil.isInsideQuotes(line, x)) {
					level += diff;
				}
			}
		}

		return level;
	}

	private static String _getAbsolutePath(Path filePath) {
		filePath = filePath.toAbsolutePath();

		filePath = filePath.normalize();

		return StringUtil.replace(
			filePath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}