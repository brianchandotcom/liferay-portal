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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MethodCallsOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortMethodCalls(fileName, content);
	}

	private String _getSortedCodeBlock(String codeBlock, String methodCall) {
		String previousParameters = null;
		String previousPutOrSetParameterName = null;

		PutOrSetParameterNameComparator putOrSetParameterNameComparator =
			new PutOrSetParameterNameComparator();

		int x = 0;

		while (true) {
			String s = StringUtil.trim(codeBlock.substring(x));

			if (!s.startsWith(methodCall)) {
				return codeBlock;
			}

			String parameters = null;

			x = codeBlock.indexOf(CharPool.OPEN_PARENTHESIS, x + 1);

			int y = x;

			while (true) {
				y = codeBlock.indexOf(CharPool.CLOSE_PARENTHESIS, y + 1);

				if (y == -1) {
					return codeBlock;
				}

				s = codeBlock.substring(x, y + 1);

				if ((ToolsUtil.getLevel(s, "(", ")") == 0) &&
					(ToolsUtil.getLevel(s, "{", "}") == 0)) {

					if (codeBlock.charAt(y + 1) != CharPool.SEMICOLON) {
						return codeBlock;
					}

					parameters = codeBlock.substring(x + 1, y);

					x = y + 2;

					break;
				}
			}

			List<String> parametersList = JavaSourceUtil.splitParameters(
				parameters);

			String putOrSetParameterName = parametersList.get(0);

			if (previousPutOrSetParameterName != null) {
				int compare = putOrSetParameterNameComparator.compare(
					previousPutOrSetParameterName, putOrSetParameterName);

				if (compare > 0) {
					String sortedCodeBlock = StringUtil.replaceFirst(
						codeBlock, previousParameters, parameters);

					return StringUtil.replaceLast(
						sortedCodeBlock, parameters, previousParameters);
				}
			}

			previousParameters = parameters;
			previousPutOrSetParameterName = putOrSetParameterName;
		}
	}

	private boolean _isAllowedVariableType(
		String content, String variableName, String[] variableTypeNames) {

		if (variableTypeNames.length == 0) {
			return true;
		}

		for (String variableTypeName : variableTypeNames) {
			if (variableName.matches(variableTypeName)) {
				return true;
			}

			StringBundler sb = new StringBundler(5);

			sb.append("\\W");
			sb.append(variableTypeName);
			sb.append("(<.*>|\\(\\))?\\s+");
			sb.append(variableName);
			sb.append("\\W");

			Pattern pattern = Pattern.compile(sb.toString());

			Matcher matcher = pattern.matcher(content);

			if (matcher.find()) {
				return true;
			}

			sb = new StringBundler(5);

			sb.append("\\W");
			sb.append(variableName);
			sb.append(" =\\s+new ");
			sb.append(variableTypeName);
			sb.append("(<.*>|\\(\\))");

			pattern = Pattern.compile(sb.toString());

			matcher = pattern.matcher(content);

			if (matcher.find()) {
				return true;
			}
		}

		return false;
	}

	private String _sortAnonymousClassMethodCalls(
		String content, String methodName, String... variableTypeNames) {

		for (String variableTypeName : variableTypeNames) {
			Pattern pattern = Pattern.compile(
				"\\Wnew " + variableTypeName + "[(<][^;]*?\\) \\{\n");

			Matcher matcher = pattern.matcher(content);

			while (matcher.find()) {
				int lineNumber = getLineNumber(content, matcher.end() - 1);

				if (!Objects.equals(
						StringUtil.trim(getLine(content, lineNumber + 1)),
						"{")) {

					continue;
				}

				int x = getLineStartPos(content, lineNumber + 2);

				int y = content.indexOf("\t}\n", x);

				if (y == -1) {
					continue;
				}

				int z = content.indexOf("\n\n", x);

				if ((z != -1) && (z < y)) {
					y = z;
				}

				String codeBlock = content.substring(x, y);

				String sortedCodeBlock = _getSortedCodeBlock(
					codeBlock, methodName + "(");

				if (!codeBlock.equals(sortedCodeBlock)) {
					return StringUtil.replaceFirst(
						content, codeBlock, sortedCodeBlock, matcher.start());
				}
			}
		}

		return content;
	}

	private String _sortChainedMethodCalls(
		String content, String methodName, int expectedParameterCount,
		String... variableTypeNames) {

		if (!content.contains("." + methodName + "(")) {
			return content;
		}

		Pattern pattern = Pattern.compile(
			"\\W(\\w+)\\.(<[\\w\\[\\]\\?<>, ]*>)?" + methodName + "\\(");

		Matcher matcher = pattern.matcher(content);

		PutOrSetParameterNameComparator putOrSetParameterNameComparator =
			new PutOrSetParameterNameComparator();

		while (matcher.find()) {
			if (!_isAllowedVariableType(
					content, matcher.group(1), variableTypeNames)) {

				continue;
			}

			String previousParameters = null;
			String previousPutOrSetParameterName = null;

			int x = matcher.end() - 1;

			while (true) {
				String parameters = null;

				int y = x;

				while (true) {
					y = content.indexOf(")", y + 1);

					if (y == -1) {
						return content;
					}

					if (getLevel(content.substring(x, y + 1)) == 0) {
						parameters = content.substring(x + 1, y);

						break;
					}
				}

				List<String> parametersList = JavaSourceUtil.splitParameters(
					parameters);

				if (parametersList.size() != expectedParameterCount) {
					break;
				}

				String putOrSetParameterName = parametersList.get(0);

				if (previousPutOrSetParameterName != null) {
					int compare = putOrSetParameterNameComparator.compare(
						previousPutOrSetParameterName, putOrSetParameterName);

					if (compare > 0) {
						String codeBlock = content.substring(
							matcher.start(), y + 1);

						String newCodeBlock = StringUtil.replaceFirst(
							codeBlock, previousParameters, parameters);

						newCodeBlock = StringUtil.replaceLast(
							newCodeBlock, parameters, previousParameters);

						return StringUtil.replaceFirst(
							content, codeBlock, newCodeBlock, matcher.start());
					}
				}

				String s = StringUtil.trim(content.substring(y + 1));

				if (!s.startsWith("." + methodName + "(")) {
					break;
				}

				previousParameters = parameters;
				previousPutOrSetParameterName = putOrSetParameterName;

				x = content.indexOf("(", y + 1);
			}
		}

		return content;
	}

	private String _sortMethodCalls(String fileName, String content) {
		content = _sortChainedMethodCalls(
			content, "put", 2, "ConcurrentHashMapBuilder", "HashMapBuilder",
			"JSONObject", "JSONUtil", "SoyContext", "TreeMapBuilder");

		content = _sortMethodCalls(
			fileName, content, "add", "ConcurrentSkipListSet", "HashSet",
			"TreeSet");
		content = _sortMethodCalls(
			fileName, content, "put", "ConcurrentHashMap", "HashMap",
			"JSONObject", "SortedMap", "TreeMap");
		content = _sortMethodCalls(fileName, content, "setAttribute");

		return content;
	}

	private String _sortMethodCalls(
		String fileName, String content, String methodName,
		String... variableTypeNames) {

		content = _sortAnonymousClassMethodCalls(
			content, methodName, variableTypeNames);

		if (!content.contains("." + methodName + "(")) {
			return content;
		}

		Pattern pattern = Pattern.compile(
			"[^;]\n\t+((\\w*)\\." + methodName + "\\()");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			if (!_isAllowedVariableType(
					content, matcher.group(2), variableTypeNames) ||
				((fileName.endsWith(".jsp") || fileName.endsWith(".jspf")) &&
				 !JSPSourceUtil.isJavaSource(content, matcher.start()))) {

				continue;
			}

			int x = content.indexOf("\n\n", matcher.end());

			if (x == -1) {
				x = content.length();
			}

			String codeBlock = content.substring(matcher.start() + 2, x);

			String sortedCodeBlock = _getSortedCodeBlock(
				codeBlock, matcher.group(1));

			if (!codeBlock.equals(sortedCodeBlock)) {
				return StringUtil.replaceFirst(
					content, codeBlock, sortedCodeBlock, matcher.start());
			}
		}

		return content;
	}

	private class PutOrSetParameterNameComparator
		extends NaturalOrderStringComparator {

		@Override
		public int compare(
			String putOrSetParameterName1, String putOrSetParameterName2) {

			String strippedParameterName1 = stripQuotes(putOrSetParameterName1);
			String strippedParameterName2 = stripQuotes(putOrSetParameterName2);

			if (strippedParameterName1.contains(StringPool.OPEN_PARENTHESIS) ||
				strippedParameterName2.contains(StringPool.OPEN_PARENTHESIS)) {

				return 0;
			}

			Matcher matcher = _multipleLineParameterNamePattern.matcher(
				putOrSetParameterName1);

			if (matcher.find()) {
				putOrSetParameterName1 = matcher.replaceAll(StringPool.BLANK);
			}

			matcher = _multipleLineParameterNamePattern.matcher(
				putOrSetParameterName2);

			if (matcher.find()) {
				putOrSetParameterName2 = matcher.replaceAll(StringPool.BLANK);
			}

			if (putOrSetParameterName1.matches("\".*\"") &&
				putOrSetParameterName2.matches("\".*\"")) {

				String strippedQuotes1 = putOrSetParameterName1.substring(
					1, putOrSetParameterName1.length() - 1);
				String strippedQuotes2 = putOrSetParameterName2.substring(
					1, putOrSetParameterName2.length() - 1);

				return super.compare(strippedQuotes1, strippedQuotes2);
			}

			int value = super.compare(
				putOrSetParameterName1, putOrSetParameterName2);

			if (putOrSetParameterName1.startsWith(StringPool.QUOTE) ^
				putOrSetParameterName2.startsWith(StringPool.QUOTE)) {

				return -value;
			}

			return value;
		}

		private final Pattern _multipleLineParameterNamePattern =
			Pattern.compile("\" \\+\n\t+\"");

	}

}