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
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MethodCallsOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortMethodCalls(content);
	}

	private boolean _isAllowedVariableType(
		String content, String variableName,
		String[] variableTypeRegexStrings) {

		if (variableTypeRegexStrings.length == 0) {
			return true;
		}

		for (String variableTypeRegex : variableTypeRegexStrings) {
			if (variableName.matches(variableTypeRegex)) {
				return true;
			}

			StringBundler sb = new StringBundler(5);

			sb.append("\\W");
			sb.append(variableTypeRegex);
			sb.append("\\s+");
			sb.append(variableName);
			sb.append("\\W");

			Pattern pattern = Pattern.compile(sb.toString());

			Matcher matcher = pattern.matcher(content);

			if (matcher.find()) {
				return true;
			}

			sb = new StringBundler(4);

			sb.append("\\W");
			sb.append(variableName);
			sb.append(" =\\s+new ");
			sb.append(variableTypeRegex);

			pattern = Pattern.compile(sb.toString());

			matcher = pattern.matcher(content);

			if (matcher.find()) {
				return true;
			}
		}

		return false;
	}

	private String _sortChainedMethodCall(
		String content, String methodName, int expectedParameterCount,
		String... variableTypeRegexStrings) {

		if (!content.contains("." + methodName + "(")) {
			return content;
		}

		Pattern pattern = Pattern.compile("\t(\\w+)\\." + methodName + "\\(");

		Matcher matcher = pattern.matcher(content);

		PutOrSetParameterNameComparator putOrSetParameterNameComparator =
			new PutOrSetParameterNameComparator();

		while (matcher.find()) {
			if (!_isAllowedVariableType(
					content, matcher.group(1), variableTypeRegexStrings)) {

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

	private String _sortMethodCall(
		String content, String methodName, String... variableTypeRegexStrings) {

		if (!content.contains("." + methodName + "(")) {
			return content;
		}

		Pattern codeBlockPattern = Pattern.compile(
			"(\t+(\\w*)\\." + methodName + "\\(\\s*\".*?\\);\n)+",
			Pattern.DOTALL);

		Matcher codeBlockMatcher = codeBlockPattern.matcher(content);

		PutOrSetParameterNameComparator putOrSetParameterNameComparator =
			new PutOrSetParameterNameComparator();

		while (codeBlockMatcher.find()) {
			if (!_isAllowedVariableType(
					content, codeBlockMatcher.group(2),
					variableTypeRegexStrings)) {

				continue;
			}

			String codeBlock = codeBlockMatcher.group();

			Pattern singleLineMethodCallPattern = Pattern.compile(
				"\t*\\w*\\." + methodName + "\\((.*?)\\);\n", Pattern.DOTALL);

			Matcher singleLineMatcher = singleLineMethodCallPattern.matcher(
				codeBlock);

			String previousParameters = null;
			String previousPutOrSetParameterName = null;

			while (singleLineMatcher.find()) {
				String parameters = singleLineMatcher.group(1);

				List<String> parametersList = JavaSourceUtil.splitParameters(
					parameters);

				String putOrSetParameterName = parametersList.get(0);

				if (previousPutOrSetParameterName != null) {
					int compare = putOrSetParameterNameComparator.compare(
						previousPutOrSetParameterName, putOrSetParameterName);

					if (compare > 0) {
						String newCodeBlock = StringUtil.replaceFirst(
							codeBlock, previousParameters, parameters);

						newCodeBlock = StringUtil.replaceLast(
							newCodeBlock, parameters, previousParameters);

						return StringUtil.replace(
							content, codeBlock, newCodeBlock);
					}
				}

				previousParameters = parameters;
				previousPutOrSetParameterName = putOrSetParameterName;
			}
		}

		return content;
	}

	private String _sortMethodCalls(String content) {
		content = _sortChainedMethodCall(
			content, "put", 2, "JSONObject", "JSONUtil");

		content = _sortMethodCall(
			content, "add", "ConcurrentSkipListSet(<.*>|\\(\\))",
			"HashSet(<.*>|\\(\\))", "TreeSet(<.*>|\\(\\))");
		content = _sortMethodCall(
			content, "put", "ConcurrentHashMap(<.*>|\\(\\))",
			"HashMap(<.*>|\\(\\))", "JSONObject", "SortedMap(<.*>|\\(\\))",
			"TreeMap(<.*>|\\(\\))");
		content = _sortMethodCall(content, "setAttribute");

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