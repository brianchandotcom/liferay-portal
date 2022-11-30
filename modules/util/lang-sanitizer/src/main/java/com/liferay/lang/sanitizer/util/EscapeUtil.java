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

package com.liferay.lang.sanitizer.util;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Seiphon Wang
 */
public class EscapeUtil {

	public static String escapeTag(String content) {
		Matcher matcher = _tagPattern.matcher(content);

		StringBuffer stringBuffer = new StringBuffer();

		while (matcher.find()) {
			String replacement = matcher.group();

			replacement = replacement.replaceAll(">", "&gt;");
			replacement = replacement.replaceAll("<", "&lt;");

			matcher.appendReplacement(stringBuffer, replacement);
		}

		matcher.appendTail(stringBuffer);

		return stringBuffer.toString();
	}

	public static String formatTagForm(String content) {
		Matcher matcher = _tagPattern.matcher(content);

		Set<String> matchedTags = new HashSet<>();

		while (matcher.find()) {
			matchedTags.add(matcher.group());
		}

		if (!matchedTags.isEmpty()) {
			if (matchedTags.contains("<br />")) {
				content = content.replaceAll("<br />", "<br>");
			}

			if (matchedTags.contains("<a {0}>")) {
				content = content.replaceAll("<a \\{0\\}>", "<a>");
			}
		}

		return content;
	}

	public static Map<String, String> getEscapedCharacterMap() {
		return HashMapBuilder.put(
			"&#039;", "'"
		).put(
			"&#39;", "'"
		).put(
			"&#149;", "•"
		).put(
			"&amp;", "&"
		).put(
			"&gt;", ">"
		).put(
			"&hellip;", "…"
		).put(
			"&laquo;", "«"
		).put(
			"&lt;", "<"
		).put(
			"&quot;", "\""
		).put(
			"&raquo;", "»"
		).put(
			"&reg;", "®"
		).put(
			"&trade;", "™"
		).build();
	}

	public static String unEscape(String content) {
		Map<String, String> escapedCharacterMap = getEscapedCharacterMap();

		Set<String> keys = escapedCharacterMap.keySet();

		for (String key : keys) {
			if (content.contains(key)) {
				content = content.replaceAll(key, escapedCharacterMap.get(key));
			}
		}

		return content;
	}

	public static String unEscapeQuot(String content) {
		return content.replaceAll("&quot;", "\"");
	}

	public static String unEscapeTag(String content) {
		content = content.replaceAll("&gt;", ">");
		content = content.replaceAll("&lt;", "<");

		return content;
	}

	private static final Pattern _tagPattern = Pattern.compile("<.+?>");

}