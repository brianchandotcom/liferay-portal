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

package com.liferay.portal.kernel.search.highlight;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tibor Lipusz
 */
public class HighlightUtil {

	public static final String HIGHLIGHT_TAG_CLOSE = "</liferay-hl>";

	public static final String HIGHLIGHT_TAG_OPEN = "<liferay-hl>";

	public static final String[] HIGHLIGHTS =
		{"<span class=\"highlight\">", "</span>"};

	public static void addSnippet(
		Document document, Set<String> queryTerms, String snippet,
		String snippetFieldName) {

		if (!snippet.equals(StringPool.BLANK)) {
			Matcher matcher = _pattern.matcher(snippet);

			while (matcher.find()) {
				queryTerms.add(matcher.group(1));
			}

			snippet = StringUtil.replace(
				snippet, HIGHLIGHT_TAG_OPEN, StringPool.BLANK);
			snippet = StringUtil.replace(
				snippet, HIGHLIGHT_TAG_CLOSE, StringPool.BLANK);
		}

		document.addText(
			Field.SNIPPET.concat(StringPool.UNDERLINE).concat(snippetFieldName),
			snippet);
	}

	public static String highlight(String s, String[] queryTerms) {
		return highlight(s, queryTerms, HIGHLIGHTS[0], HIGHLIGHTS[1]);
	}

	public static String highlight(
		String s, String[] queryTerms, String highlight1, String highlight2) {

		if (Validator.isNull(s) || ArrayUtil.isEmpty(queryTerms)) {
			return s;
		}

		StringBundler sb = new StringBundler(2 * queryTerms.length - 1);

		for (int i = 0; i < queryTerms.length; i++) {
			sb.append(Pattern.quote(queryTerms[i].trim()));

			if ((i + 1) < queryTerms.length) {
				sb.append(StringPool.PIPE);
			}
		}

		int flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

		Pattern pattern = Pattern.compile(sb.toString(), flags);

		return _highlight(s, pattern, highlight1, highlight2);
	}

	private static String _highlight(
		String s, Pattern pattern, String highlight1, String highlight2) {

		StringTokenizer st = new StringTokenizer(s);

		if (st.countTokens() == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * st.countTokens() - 1);

		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			Matcher matcher = pattern.matcher(token);

			if (matcher.find()) {
				StringBuffer hightlighted = new StringBuffer();

				while (true) {
					matcher.appendReplacement(
						hightlighted,
						highlight1 + matcher.group() + highlight2);

					if (!matcher.find()) {
						break;
					}
				}

				matcher.appendTail(hightlighted);

				sb.append(hightlighted);
			}
			else {
				sb.append(token);
			}

			if (st.hasMoreTokens()) {
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private static final Pattern _pattern = Pattern.compile(
		HIGHLIGHT_TAG_OPEN + "(.*?)" + HIGHLIGHT_TAG_CLOSE);

}