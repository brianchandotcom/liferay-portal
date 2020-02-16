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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class BaseTagAttributesCheck extends BaseFileCheck {

	protected abstract Tag doFormatLineBreaks(Tag tag, String absolutePath);

	protected String formatIncorrectLineBreak(String fileName, String content) {
		Matcher matcher = _incorrectLineBreakPattern.matcher(content);

		while (matcher.find()) {
			String s = stripQuotes(matcher.group(3));

			if (s.contains(">")) {
				continue;
			}

			if (getLevel(matcher.group(), "<", ">") != 0) {
				addMessage(
					fileName,
					"There should be a line break after '" + matcher.group(2) +
						"'",
					getLineNumber(content, matcher.start(2)));

				continue;
			}

			return StringUtil.replaceFirst(
				content, StringPool.SPACE, "\n\t" + matcher.group(1),
				matcher.start());
		}

		return content;
	}

	protected Tag formatLineBreaks(
		Tag tag, String absolutePath, boolean forceSingleLine) {

		if (forceSingleLine) {
			tag.setMultiLine(false);

			return tag;
		}

		Map<String, String> attributesMap = tag.getAttributesMap();

		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			String attributeValue = entry.getValue();

			if (attributeValue.contains(StringPool.NEW_LINE)) {
				tag.setMultiLine(true);

				return tag;
			}
		}

		return doFormatLineBreaks(tag, absolutePath);
	}

	protected String formatMultiLinesTagAttributes(
			String absolutePath, String content, boolean escapeQuotes)
		throws Exception {

		Matcher matcher = _multilineTagPattern.matcher(content);

		while (matcher.find()) {
			if (matcher.start() != 0) {
				char c = content.charAt(matcher.start() - 1);

				if (c != CharPool.NEW_LINE) {
					continue;
				}
			}

			String tag = matcher.group(1);

			if (getLevel(_getStrippedTag(tag, "\"", "'"), "<", ">") != 0) {
				continue;
			}

			String beforeClosingTagChar = matcher.group(3);

			if (!beforeClosingTagChar.equals(StringPool.NEW_LINE) &&
				!beforeClosingTagChar.equals(StringPool.TAB)) {

				String closingTag = matcher.group(4);

				String whitespace = matcher.group(2);

				String indent = StringUtil.removeChar(
					whitespace, CharPool.SPACE);

				return StringUtil.replaceFirst(
					content, closingTag, "\n" + indent + closingTag,
					matcher.start(3));
			}

			String newTag = formatTagAttributes(
				absolutePath, tag, escapeQuotes, false);

			if (!tag.equals(newTag)) {
				return StringUtil.replace(content, tag, newTag);
			}
		}

		return content;
	}

	protected String formatTagAttributes(
			String absolutePath, String s, boolean escapeQuotes,
			boolean forceSingleLine)
		throws Exception {

		Tag tag = _parseTag(s, escapeQuotes);

		if (tag == null) {
			return s;
		}

		tag = formatTagAttributeType(tag);

		tag = sortHTMLTagAttributes(tag);

		if (isPortalSource() || isSubrepository()) {
			tag = formatLineBreaks(tag, absolutePath, forceSingleLine);
		}

		return tag.toString();
	}

	protected Tag formatTagAttributeType(Tag tag) throws Exception {
		return tag;
	}

	protected Tag sortHTMLTagAttributes(Tag tag) {
		String tagName = tag.getName();

		if (tagName.equals("liferay-ui:tabs")) {
			return tag;
		}

		Map<String, String> attributesMap = tag.getAttributesMap();

		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			String attributeValue = entry.getValue();

			if (attributeValue.matches("([-a-z0-9]+ )+[-a-z0-9]+")) {
				List<String> htmlAttributes = ListUtil.fromArray(
					StringUtil.split(attributeValue, StringPool.SPACE));

				Collections.sort(htmlAttributes);

				tag.putAttribute(
					entry.getKey(),
					StringUtil.merge(htmlAttributes, StringPool.SPACE));
			}
			else if (attributeValue.matches("([-a-z0-9]+,)+[-a-z0-9]+")) {
				String attributeName = entry.getKey();

				if (!tagName.equals("aui:script") ||
					!attributeName.equals("use")) {

					continue;
				}

				List<String> htmlAttributes = ListUtil.fromArray(
					StringUtil.split(attributeValue, StringPool.COMMA));

				Collections.sort(htmlAttributes);

				tag.putAttribute(
					entry.getKey(),
					StringUtil.merge(htmlAttributes, StringPool.COMMA));
			}
		}

		return tag;
	}

	protected class Tag {

		public Tag(
			String name, String indent, boolean multiLine,
			boolean escapeQuotes) {

			_name = name;
			_indent = indent;
			_multiLine = multiLine;
			_escapeQuotes = escapeQuotes;
		}

		public Map<String, String> getAttributesMap() {
			return _attributesMap;
		}

		public String getName() {
			return _name;
		}

		public void putAttribute(String attributeName, String attributeValue) {
			_attributesMap.put(attributeName, attributeValue);
		}

		public void setClosingTag(String closingTag) {
			_closingTag = closingTag;
		}

		public void setMultiLine(boolean multiLine) {
			_multiLine = multiLine;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler();

			sb.append(_indent);
			sb.append(StringPool.LESS_THAN);
			sb.append(_name);

			for (Map.Entry<String, String> entry : _attributesMap.entrySet()) {
				if (_multiLine) {
					sb.append(StringPool.NEW_LINE);
					sb.append(_indent);
					sb.append(StringPool.TAB);
				}
				else {
					sb.append(StringPool.SPACE);
				}

				sb.append(entry.getKey());

				sb.append(StringPool.EQUAL);

				String delimeter = null;

				String attributeValue = entry.getValue();

				if (_escapeQuotes ||
					!attributeValue.contains(StringPool.QUOTE) ||
					!_name.contains(StringPool.COLON)) {

					delimeter = StringPool.QUOTE;
				}
				else {
					delimeter = StringPool.APOSTROPHE;
				}

				sb.append(delimeter);

				if (!_escapeQuotes) {
					sb.append(attributeValue);
				}
				else {
					sb.append(
						StringUtil.replace(
							attributeValue, CharPool.QUOTE, "&quot;"));
				}

				sb.append(delimeter);
			}

			if (_multiLine) {
				sb.append(StringPool.NEW_LINE);
				sb.append(_indent);
			}
			else if (_closingTag.equals("/>")) {
				sb.append(StringPool.SPACE);
			}

			sb.append(_closingTag);

			return sb.toString();
		}

		private Map<String, String> _attributesMap = new TreeMap<>(
			new NaturalOrderStringComparator());
		private String _closingTag;
		private final boolean _escapeQuotes;
		private final String _indent;
		private boolean _multiLine;
		private final String _name;

	}

	private String _getStrippedTag(String tag, String... quotes) {
		for (String quote : quotes) {
			while (true) {
				int x = tag.indexOf(quote + "<%=");

				if (x == -1) {
					break;
				}

				int y = tag.indexOf("%>" + quote, x);

				if (y == -1) {
					return tag;
				}

				tag = tag.substring(0, x) + tag.substring(y + 3);
			}
		}

		return tag;
	}

	private boolean _isValidAttributName(String attributeName) {
		if (Validator.isNull(attributeName)) {
			return false;
		}

		Matcher matcher = _attributeNamePattern.matcher(attributeName);

		return matcher.matches();
	}

	private Tag _parseTag(String s, boolean escapeQuotes) {
		String indent = SourceUtil.getIndent(s);

		s = StringUtil.trim(s);

		boolean multiLine = false;

		int x = -1;

		if (s.contains(StringPool.NEW_LINE)) {
			multiLine = true;

			x = s.indexOf(CharPool.NEW_LINE);
		}
		else {
			x = s.indexOf(CharPool.SPACE);
		}

		if (x == -1) {
			return null;
		}

		String tagName = s.substring(1, x);

		Tag tag = new Tag(tagName, indent, multiLine, escapeQuotes);

		s = s.substring(x + 1);

		while (true) {
			x = s.indexOf(CharPool.EQUAL);

			if (x == -1) {
				return null;
			}

			String attributeName = StringUtil.trim(s.substring(0, x));

			if (!_isValidAttributName(attributeName)) {
				return null;
			}

			s = StringUtil.trimLeading(s.substring(x + 1));

			char delimeter = s.charAt(0);

			if ((delimeter != CharPool.APOSTROPHE) &&
				(delimeter != CharPool.QUOTE)) {

				return null;
			}

			s = s.substring(1);

			x = -1;

			while (true) {
				x = s.indexOf(delimeter, x + 1);

				if (x == -1) {
					return null;
				}

				String attributeValue = s.substring(0, x);

				if ((attributeValue.startsWith("<%") &&
					 (getLevel(attributeValue, "<%", "%>") == 0)) ||
					(!attributeValue.startsWith("<%") &&
					 (getLevel(attributeValue, "<", ">") == 0))) {

					tag.putAttribute(attributeName, attributeValue);

					s = StringUtil.trim(s.substring(x + 1));

					if (s.equals(">") || s.equals("/>")) {
						tag.setClosingTag(s);

						return tag;
					}

					break;
				}
			}
		}
	}

	private static final Pattern _attributeNamePattern = Pattern.compile(
		"[a-z]+[-_:a-zA-Z0-9]*");
	private static final Pattern _incorrectLineBreakPattern = Pattern.compile(
		"\n(\t*)(<\\w[-_:\\w]*) (.*)[\"']\n[\\s\\S]*?>\n");
	private static final Pattern _multilineTagPattern = Pattern.compile(
		"(([ \t]*)<[-\\w:]+\n.*?([^%])(/?>))(\n|$)", Pattern.DOTALL);

}