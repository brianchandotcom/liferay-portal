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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaForLoopCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String javaTermContent = _formatForLoop(
			javaTerm.getContent(), fileName, fileContent,
			_COLLECTION_TYPE_ARRAY, javaTerm.getLineNumber());

		return _formatForLoop(
			javaTermContent, fileName, fileContent, _COLLECTION_TYPE_LIST,
			javaTerm.getLineNumber());
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _formatForLoop(
		String javaTermContent, String fileName, String fileContent, int type,
		int lineNumber) {

		Pattern pattern1 = _getForLoopPattern(type);

		Matcher matcher1 = pattern1.matcher(javaTermContent);

		while (matcher1.find()) {
			String countVarName = matcher1.group(1);

			if (!countVarName.equals(matcher1.group(2)) ||
				!countVarName.equals(matcher1.group(4))) {

				continue;
			}

			String insideLoopContent = _getInsideLoopContent(
				javaTermContent, matcher1.end());

			if (insideLoopContent == null) {
				continue;
			}

			String collectionVarName = matcher1.group(3);

			if (type == _COLLECTION_TYPE_LIST) {
				Pattern pattern2 = Pattern.compile(
					">\\s+" + collectionVarName + "\\W");

				Matcher matcher2 = pattern2.matcher(fileContent);

				if (!matcher2.find()) {
					continue;
				}
			}

			if (insideLoopContent.matches(
					"(?s).*\\." + collectionVarName + "\\W.*")) {

				continue;
			}

			int varNameCount = _getOccurenceCount(
				insideLoopContent, "\\W" + countVarName + "\\W");

			if (varNameCount == 0) {
				continue;
			}

			int retrieveFromCollectionCount = _getOccurenceCount(
				insideLoopContent,
				_getRetrieveFromCollectionRegex(
					countVarName, collectionVarName, type));

			if (varNameCount != retrieveFromCollectionCount) {
				continue;
			}

			if (type == _COLLECTION_TYPE_ARRAY) {
				int reassignVarCount = _getOccurenceCount(
					insideLoopContent,
					StringBundler.concat(
						"\\W", collectionVarName, "\\[", countVarName,
						"\\](\\s+[+-]?\\=\\s|--|\\+\\+)"));

				if (reassignVarCount > 0) {
					continue;
				}
			}

			Pattern pattern2 = Pattern.compile(
				_getAssignVarRegex(countVarName, collectionVarName, type));

			Matcher matcher2 = pattern2.matcher(insideLoopContent);

			if (!matcher2.find() || (varNameCount > 1)) {
				addMessage(
					fileName, "Use Enhanced For-Loop",
					lineNumber - 1 +
						getLineNumber(javaTermContent, matcher1.start()));

				continue;
			}

			StringBundler sb = new StringBundler(7);

			sb.append("\tfor (");
			sb.append(matcher2.group(1));
			sb.append(StringPool.SPACE);
			sb.append(matcher2.group(2));
			sb.append(" : ");
			sb.append(collectionVarName);
			sb.append(") {\n");

			javaTermContent = StringUtil.replaceFirst(
				javaTermContent, matcher1.group(), sb.toString(),
				matcher1.start());

			javaTermContent = StringUtil.replaceFirst(
				javaTermContent, matcher2.group(), StringPool.BLANK,
				matcher1.start());

			return javaTermContent;
		}

		return javaTermContent;
	}

	private String _getAssignVarRegex(
		String countVarName, String collectionVarName, int type) {

		if (type == _COLLECTION_TYPE_ARRAY) {
			return StringBundler.concat(
				"\t+(\\w[\\w\\s\\[\\]<>,\\.\\?]*)\\s+(\\w+)\\s+=\\s+",
				collectionVarName, "\\[", countVarName, "\\];\n");
		}

		return StringBundler.concat(
			"\t+(\\w[\\w\\s\\[\\]<>,\\.\\?]*)\\s+(\\w+)\\s+=\\s+",
			collectionVarName, "\\.get\\(", countVarName, "\\);\n");
	}

	private Pattern _getForLoopPattern(int type) {
		if (type == _COLLECTION_TYPE_ARRAY) {
			return _arrayPattern;
		}

		return _listPattern;
	}

	private String _getInsideLoopContent(String s, int x) {
		int y = x;

		while (true) {
			y = s.indexOf("}", y + 1);

			if (y == -1) {
				return null;
			}

			if (ToolsUtil.isInsideQuotes(s, y)) {
				continue;
			}

			String insideLoopContent = s.substring(x, y);

			if (getLevel(insideLoopContent, "{", "}") == 0) {
				return insideLoopContent;
			}
		}
	}

	private int _getOccurenceCount(
		String s, String regex, String... arguments) {

		for (int i = 0; i < arguments.length; i++) {
			String argument = arguments[i];

			regex = StringUtil.replace(regex, "{" + i + "}", argument);
		}

		int count = 0;

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(s);

		while (matcher.find()) {
			count++;
		}

		return count;
	}

	private String _getRetrieveFromCollectionRegex(
		String countVarName, String collectionVarName, int type) {

		if (type == _COLLECTION_TYPE_ARRAY) {
			return StringBundler.concat(
				"\\W", collectionVarName, "\\[", countVarName, "\\]");
		}

		return StringBundler.concat(
			"\\W", collectionVarName, "\\.get\\(", countVarName, "\\)");
	}

	private static final int _COLLECTION_TYPE_ARRAY = 0;

	private static final int _COLLECTION_TYPE_LIST = 1;

	private static final Pattern _arrayPattern = Pattern.compile(
		"\tfor \\(int (\\w+) = 0;\\s+(\\w+) < (\\w+)\\.length;\\s+(\\w+)\\+" +
			"\\+\\) \\{\n");
	private static final Pattern _listPattern = Pattern.compile(
		"\tfor \\(int (\\w+) = 0;\\s+(\\w+) < (\\w+)\\.size\\(\\);\\s+(\\w+)" +
			"\\+\\+\\) \\{\n");

}