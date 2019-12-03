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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.portal.xml.SAXReaderFactory;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * @author Hugo Huijser
 */
public class SourceUtil {

	public static boolean containsUnquoted(String s, String text) {
		int x = -1;

		while (true) {
			x = s.indexOf(text, x + 1);

			if (x == -1) {
				return false;
			}

			if (!ToolsUtil.isInsideQuotes(s, x)) {
				return true;
			}
		}
	}

	public static String getAbsolutePath(File file) {
		return getAbsolutePath(file.toPath());
	}

	public static String getAbsolutePath(Path filePath) {
		filePath = filePath.toAbsolutePath();

		filePath = filePath.normalize();

		return StringUtil.replace(
			filePath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

	public static String getAbsolutePath(String fileName) {
		return getAbsolutePath(Paths.get(fileName));
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

	public static String getLine(String content, int lineNumber) {
		int nextLineStartPos = getLineStartPos(content, lineNumber);

		if (nextLineStartPos == -1) {
			return null;
		}

		int nextLineEndPos = content.indexOf(
			CharPool.NEW_LINE, nextLineStartPos);

		if (nextLineEndPos == -1) {
			return content.substring(nextLineStartPos);
		}

		return content.substring(nextLineStartPos, nextLineEndPos);
	}

	public static int getLineNumber(String content, int pos) {
		return StringUtil.count(content, 0, pos, CharPool.NEW_LINE) + 1;
	}

	public static int getLineStartPos(String content, int lineNumber) {
		if (lineNumber <= 0) {
			return -1;
		}

		if (lineNumber == 1) {
			return 0;
		}

		int x = -1;

		for (int i = 1; i < lineNumber; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
	}

	public static int[] getMultiLinePositions(
		String content, Pattern multiLinePattern) {

		List<Integer> multiLinePositions = new ArrayList<>();

		Matcher matcher = multiLinePattern.matcher(content);

		while (matcher.find()) {
			multiLinePositions.add(getLineNumber(content, matcher.start()));
			multiLinePositions.add(getLineNumber(content, matcher.end() - 1));
		}

		return ArrayUtil.toIntArray(multiLinePositions);
	}

	public static String getTitleCase(String s, String... exceptions) {
		String[] words = s.split("\\s+");

		if (ArrayUtil.isEmpty(words)) {
			return s;
		}

		StringBundler sb = new StringBundler(words.length * 2);

		outerLoop:
		for (int i = 0; i < words.length; i++) {
			String word = words[i];

			if (Validator.isNull(word)) {
				continue;
			}

			for (String exception : exceptions) {
				if (StringUtil.equalsIgnoreCase(exception, word)) {
					sb.append(exception);
					sb.append(CharPool.SPACE);

					continue outerLoop;
				}
			}

			if ((i != 0) && (i != words.length)) {
				String lowerCaseWord = StringUtil.toLowerCase(word);

				if (ArrayUtil.contains(_ARTICLES, lowerCaseWord) ||
					ArrayUtil.contains(_CONJUNCTIONS, lowerCaseWord) ||
					ArrayUtil.contains(_PREPOSITIONS, lowerCaseWord)) {

					sb.append(lowerCaseWord);
					sb.append(CharPool.SPACE);

					continue;
				}
			}

			if (Character.isUpperCase(word.charAt(0))) {
				sb.append(word);
			}
			else {
				sb.append(StringUtil.upperCaseFirstLetter(word));
			}

			sb.append(CharPool.SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	public static boolean isInsideMultiLines(
		int lineNumber, int[] multiLinePositions) {

		for (int i = 0; i < (multiLinePositions.length - 1); i += 2) {
			if (lineNumber < multiLinePositions[i]) {
				return false;
			}

			if (lineNumber <= multiLinePositions[i + 1]) {
				return true;
			}
		}

		return false;
	}

	public static boolean isXML(String content) {
		try {
			readXML(content);

			return true;
		}
		catch (DocumentException de) {
			return false;
		}
	}

	public static Document readXML(File file) throws DocumentException {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		return saxReader.read(file);
	}

	public static Document readXML(String content) throws DocumentException {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		return saxReader.read(new UnsyncStringReader(content));
	}

	private static final String[] _ARTICLES = {"a", "an", "the"};

	private static final String[] _CONJUNCTIONS = {
		"and", "but", "for", "nor", "or", "yet"
	};

	private static final String[] _PREPOSITIONS = {
		"a", "abaft", "aboard", "about", "above", "absent", "across", "afore",
		"after", "against", "along", "alongside", "amid", "amidst", "among",
		"amongst", "an", "apropos", "apud", "around", "as", "aside", "astride",
		"at", "athwart", "atop", "barring", "before", "behind", "below",
		"beneath", "beside", "besides", "between", "beyond", "but", "by",
		"circa", "concerning", "despite", "down", "during", "except",
		"excluding", "failing", "for", "from", "given", "in", "including",
		"inside", "into", "lest", "mid", "midst", "modulo", "near", "next",
		"notwithstanding", "of", "off", "on", "onto", "opposite", "out",
		"outside", "over", "pace", "past", "per", "plus", "pro", "qua",
		"regarding", "sans", "since", "through", "throughout", "thru",
		"thruout", "till", "to", "toward", "towards", "under", "underneath",
		"unlike", "until", "unto", "up", "upon", "v", "versus", "via", "vice",
		"vs", "with", "within", "without", "worth"
	};

}