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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.Type;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPTagAttributesCheck extends TagAttributesCheck {

	public JSPTagAttributesCheck(
		Set<String> primitiveTagAttributeDataTypes,
		Map<String, JavaClass> tagJavaClassesMap) {

		_primitiveTagAttributeDataTypes = primitiveTagAttributeDataTypes;
		_tagJavaClassesMap = tagJavaClassesMap;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		content = _formatSingleLineTagAttribues(fileName, content);

		content = _formatMultiLinesTagAttribues(fileName, content);

		return content;
	}

	@Override
	protected String formatTagAttributeType(
			String line, String tagName, String attributeAndValue)
		throws Exception {

		if (attributeAndValue.matches(
				".*=\"<%= Boolean\\.(FALSE|TRUE) %>\".*")) {

			String newAttributeAndValue = StringUtil.replace(
				attributeAndValue,
				new String[] {
					"=\"<%= Boolean.FALSE %>\"", "=\"<%= Boolean.TRUE %>\""
				},
				new String[] {"=\"<%= false %>\"", "=\"<%= true %>\""});

			return StringUtil.replace(
				line, attributeAndValue, newAttributeAndValue);
		}

		if (!isPortalSource() && !isSubrepository()) {
			return line;
		}

		if (!attributeAndValue.endsWith(StringPool.QUOTE) ||
			attributeAndValue.contains("\"<%=")) {

			return line;
		}

		JavaClass tagJavaClass = _tagJavaClassesMap.get(tagName);

		if (tagJavaClass == null) {
			return line;
		}

		int pos = attributeAndValue.indexOf("=\"");

		String attribute = attributeAndValue.substring(0, pos);

		String setAttributeMethodName =
			"set" + TextFormatter.format(attribute, TextFormatter.G);

		for (String dataType : _primitiveTagAttributeDataTypes) {
			Type javaType = new Type(dataType);

			JavaMethod setAttributeMethod = null;

			while (true) {

				// com.thoughtworks.qdox.model.JavaClass is not thread-safe and
				// can throw NPE as a result of a race condition

				try {
					setAttributeMethod = tagJavaClass.getMethodBySignature(
						setAttributeMethodName, new Type[] {javaType}, true);

					break;
				}
				catch (Exception e) {
				}
			}

			if (setAttributeMethod != null) {
				String value = attributeAndValue.substring(
					pos + 2, attributeAndValue.length() - 1);

				if (!_isValidTagAttributeValue(value, dataType)) {
					return line;
				}

				String newAttributeAndValue = StringUtil.replace(
					attributeAndValue,
					StringPool.QUOTE + value + StringPool.QUOTE,
					"\"<%= " + value + " %>\"");

				return StringUtil.replace(
					line, attributeAndValue, newAttributeAndValue);
			}
		}

		if (!attributeAndValue.matches(".*=\"(false|true)\".*")) {
			return line;
		}

		JavaMethod setAttributeMethod = tagJavaClass.getMethodBySignature(
			setAttributeMethodName, new Type[] {new Type("java.lang.String")},
			true);

		if (setAttributeMethod == null) {
			return line;
		}

		String newAttributeAndValue = StringUtil.replace(
			attributeAndValue, new String[] {"=\"false\"", "=\"true\""},
			new String[] {
				"=\"<%= Boolean.FALSE.toString() %>\"",
				"=\"<%= Boolean.TRUE.toString() %>\""
			});

		return StringUtil.replace(
			line, attributeAndValue, newAttributeAndValue);
	}

	@Override
	protected String sortHTMLTagAttributes(
		String line, String value, String attributeAndValue) {

		if (!value.matches("([-a-z0-9]+ )+[-a-z0-9]+")) {
			return line;
		}

		List<String> htmlAttributes = ListUtil.fromArray(
			StringUtil.split(value, StringPool.SPACE));

		Collections.sort(htmlAttributes);

		String newValue = StringUtil.merge(htmlAttributes, StringPool.SPACE);

		if (value.equals(newValue)) {
			return line;
		}

		String newAttributeAndValue = StringUtil.replace(
			attributeAndValue, value, newValue);

		return StringUtil.replace(
			line, attributeAndValue, newAttributeAndValue);
	}

	private String _formatMultiLinesTagAttribues(
			String fileName, String content)
		throws Exception {

		Matcher matcher = _multilineTagPattern.matcher(content);

		while (matcher.find()) {
			char beforeClosingTagChar = content.charAt(matcher.start(2) - 1);

			if ((beforeClosingTagChar != CharPool.NEW_LINE) &&
				(beforeClosingTagChar != CharPool.TAB)) {

				String closingTag = matcher.group(2);

				String whitespace = matcher.group(1);

				String tabs = StringUtil.removeChar(
					whitespace, CharPool.NEW_LINE);

				return StringUtil.replaceFirst(
					content, closingTag, "\n" + tabs + closingTag,
					matcher.start(2));
			}

			String tag = matcher.group();

			String singlelineTag = StringUtil.removeChar(
				StringUtil.trim(tag), CharPool.TAB);

			singlelineTag = StringUtil.replace(
				singlelineTag, CharPool.NEW_LINE, CharPool.SPACE);

			String newTag = formatTagAttributes(
				fileName, tag, singlelineTag,
				getLineCount(content, matcher.end(1)), false);

			if (!tag.equals(newTag)) {
				return StringUtil.replace(content, tag, newTag);
			}
		}

		return content;
	}

	private String _formatSingleLineTagAttribues(
			String fileName, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.matches("<\\w+ .*>.*")) {
					line = formatTagAttributes(
						fileName, line, trimmedLine, lineCount, false);
				}

				Matcher matcher = _jspTaglibPattern.matcher(line);

				while (matcher.find()) {
					line = formatTagAttributes(
						fileName, line, line.substring(matcher.start()),
						lineCount, false);
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	private boolean _isValidTagAttributeValue(String value, String dataType) {
		if (dataType.endsWith("Boolean") || dataType.equals("boolean")) {
			return Validator.isBoolean(value);
		}

		if (dataType.endsWith("Double") || dataType.equals("double")) {
			try {
				Double.parseDouble(value);
			}
			catch (NumberFormatException nfe) {
				return false;
			}

			return true;
		}

		if (dataType.endsWith("Integer") || dataType.equals("int") ||
			dataType.endsWith("Long") || dataType.equals("long")) {

			return Validator.isNumber(value);
		}

		return false;
	}

	private final Pattern _jspTaglibPattern = Pattern.compile(
		"<[-\\w]+:[-\\w]+ .");
	private final Pattern _multilineTagPattern = Pattern.compile(
		"(\\s+)<[-\\w]+:[-\\w]+\n.*?(/?>)(\n|$)", Pattern.DOTALL);
	private final Set<String> _primitiveTagAttributeDataTypes;
	private final Map<String, JavaClass> _tagJavaClassesMap;

}