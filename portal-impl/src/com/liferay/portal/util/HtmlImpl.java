/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

/**
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 * @author Connor McKay
 * @author Shuyang Zhou
 */
@DoPrivileged
public class HtmlImpl implements Html {

	public static final int ESCAPE_MODE_ATTRIBUTE = 1;

	public static final int ESCAPE_MODE_CSS = 2;

	public static final int ESCAPE_MODE_JS = 3;

	public static final int ESCAPE_MODE_TEXT = 4;

	public static final int ESCAPE_MODE_URL = 5;

	public String escape(String text) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		// Escape using XSS recommendations from
		// http://www.owasp.org/index.php/Cross_Site_Scripting
		// #How_to_Protect_Yourself

		StringBundler sb = null;

		int lastReplacementIndex = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			String replacement = null;

			switch (c) {
				case '<':
					replacement = "&lt;";

					break;

				case '>':
					replacement = "&gt;";

					break;

				case '&':
					replacement = "&amp;";

					break;

				case '"':
					replacement = "&#034;";

					break;

				case '\'':
					replacement = "&#039;";

					break;

				case '\u00bb': // '�'
					replacement = "&#187;";

					break;

				case '\u2013':
					replacement = "&#x2013;";

					break;

				case '\u2014':
					replacement = "&#x2014;";

					break;
			}

			if (replacement != null) {
				if (sb == null) {
					sb = new StringBundler();
				}

				if (i > lastReplacementIndex) {
					sb.append(text.substring(lastReplacementIndex, i));
				}

				sb.append(replacement);

				lastReplacementIndex = i + 1;
			}
		}

		if (sb == null) {
			return text;
		}
		else {
			if (lastReplacementIndex < text.length()) {
				sb.append(text.substring(lastReplacementIndex));
			}

			return sb.toString();
		}
	}

	public String escape(String text, int type) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		String prefix = StringPool.BLANK;
		String postfix = StringPool.BLANK;

		if (type == ESCAPE_MODE_ATTRIBUTE) {
			prefix = "&#x";
			postfix = StringPool.SEMICOLON;
		}
		else if (type == ESCAPE_MODE_CSS) {
			prefix = StringPool.BACK_SLASH;
		}
		else if (type == ESCAPE_MODE_JS) {
			prefix = "\\x";
		}
		else if (type == ESCAPE_MODE_URL) {
			return HttpUtil.encodeURL(text, true);
		}
		else {
			return escape(text);
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (Character.isLetterOrDigit(c) ||
				(c == CharPool.DASH) || (c == CharPool.UNDERLINE)) {

				sb.append(c);
			}
			else {
				sb.append(prefix);

				String hexString = StringUtil.toHexString(c);

				if (hexString.length() == 1) {
					sb.append(StringPool.ASCII_TABLE[48]);
				}

				sb.append(hexString);
				sb.append(postfix);
			}
		}

		if (sb.length() == text.length()) {
			return text;
		}
		else {
			return sb.toString();
		}
	}

	public String escapeAttribute(String attribute) {
		return escape(attribute, ESCAPE_MODE_ATTRIBUTE);
	}

	public String escapeCSS(String css) {
		return escape(css, ESCAPE_MODE_CSS);
	}

	public String escapeHREF(String href) {
		if (href == null) {
			return null;
		}

		if (href.length() == 0) {
			return StringPool.BLANK;
		}

		if (href.indexOf(StringPool.COLON) == 10) {
			String protocol = href.substring(0, 10).toLowerCase();

			if (protocol.equals("javascript")) {
				href = StringUtil.replaceFirst(href, StringPool.COLON, "%3a");
			}
		}

		return escapeAttribute(href);
	}

	public String escapeJS(String js) {
		return escape(js, ESCAPE_MODE_JS);
	}

	public String escapeURL(String url) {
		return escape(url, ESCAPE_MODE_URL);
	}

	public String escapeXPath(String xPath) {
		if (Validator.isNull(xPath)) {
			return xPath;
		}

		StringBuilder sb = new StringBuilder(xPath.length());

		for (int i = 0; i < xPath.length(); i++) {
			char c = xPath.charAt(i);

			boolean hasToken = false;

			for (int j = 0; j < _XPATH_TOKENS.length; j++) {
				if (c == _XPATH_TOKENS[j]) {
					hasToken = true;

					break;
				}
			}

			if (hasToken) {
				sb.append(StringPool.UNDERLINE);
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public String escapeXPathAttribute(String xPathAttribute) {
		boolean hasApostrophe = xPathAttribute.contains(StringPool.APOSTROPHE);
		boolean hasQuote = xPathAttribute.contains(StringPool.QUOTE);

		if (hasQuote && hasApostrophe) {
			String[] parts = xPathAttribute.split(StringPool.APOSTROPHE);

			return "concat('".concat(
				StringUtil.merge(parts, "', \"'\", '")).concat("')");
		}

		if (hasQuote) {
			return StringPool.APOSTROPHE.concat(xPathAttribute).concat(
				StringPool.APOSTROPHE);
		}

		return StringPool.QUOTE.concat(xPathAttribute).concat(StringPool.QUOTE);
	}

	public String extractText(String html) {
		if (html == null) {
			return null;
		}

		Source source = new Source(html);

		TextExtractor textExtractor = source.getTextExtractor();

		return textExtractor.toString();
	}

	public String fromInputSafe(String text) {
		return StringUtil.replace(text, "&amp;", "&");
	}

	public String render(String html) {
		if (html == null) {
			return null;
		}

		Source source = new Source(html);

		Renderer renderer = source.getRenderer();

		return renderer.toString();
	}

	public String replaceMsWordCharacters(String text) {
		return StringUtil.replace(text, _MS_WORD_UNICODE, _MS_WORD_HTML);
	}

	public String stripBetween(String text, String tag) {
		return StringUtil.stripBetween(text, "<" + tag, "</" + tag + ">");
	}

	public String stripComments(String text) {
		return StringUtil.stripBetween(text, "<!--", "-->");
	}

	public String stripHtml(String text) {
		if (text == null) {
			return null;
		}

		text = stripComments(text);

		StringBuilder sb = new StringBuilder(text.length());

		int x = 0;
		int y = text.indexOf("<");

		while (y != -1) {
			sb.append(text.substring(x, y));
			sb.append(StringPool.SPACE);

			// Look for text enclosed by <abc></abc>

			if (isTag(_TAG_SCRIPT, text, y + 1)) {
				y = stripTag(_TAG_SCRIPT, text, y);
			}
			else if (isTag(_TAG_STYLE, text, y + 1)) {
				y = stripTag(_TAG_STYLE, text, y);
			}

			x = text.indexOf(">", y);

			if (x == -1) {
				break;
			}

			x++;

			if (x < y) {

				// <b>Hello</b

				break;
			}

			y = text.indexOf("<", x);
		}

		if (y == -1) {
			sb.append(text.substring(x));
		}

		return sb.toString();
	}

	public String toInputSafe(String text) {
		return StringUtil.replace(
			text,
			new String[] {"&", "\""},
			new String[] {"&amp;", "&quot;"});
	}

	public String unescape(String text) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		// Optimize this

		text = StringUtil.replace(text, "&lt;", "<");
		text = StringUtil.replace(text, "&gt;", ">");
		text = StringUtil.replace(text, "&amp;", "&");
		text = StringUtil.replace(text, "&#034;", "\"");
		text = StringUtil.replace(text, "&#039;", "'");
		text = StringUtil.replace(text, "&#040;", "(");
		text = StringUtil.replace(text, "&#041;", ")");
		text = StringUtil.replace(text, "&#044;", ",");
		text = StringUtil.replace(text, "&#035;", "#");
		text = StringUtil.replace(text, "&#037;", "%");
		text = StringUtil.replace(text, "&#059;", ";");
		text = StringUtil.replace(text, "&#061;", "=");
		text = StringUtil.replace(text, "&#043;", "+");
		text = StringUtil.replace(text, "&#045;", "-");

		return text;
	}

	public String unescapeCDATA(String text) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		text = StringUtil.replace(text, "&lt;![CDATA[", "<![CDATA[");
		text = StringUtil.replace(text, "]]&gt;", "]]>");

		return text;
	}

	public String wordBreak(String text, int columns) {
		StringBundler sb = new StringBundler();

		int length = 0;
		int lastWrite = 0;
		int pos = 0;

		Pattern pattern = Pattern.compile("([\\s<&]|$)");

		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			if (matcher.start() < pos) {
				continue;
			}

			while ((length + matcher.start() - pos) >= columns) {
				pos += columns - length;

				sb.append(text.substring(lastWrite, pos));
				sb.append("<wbr/>&shy;");

				length = 0;
				lastWrite = pos;
			}

			length += matcher.start() - pos;

			String group = matcher.group();

			if (group.equals(StringPool.AMPERSAND)) {
				int x = text.indexOf(StringPool.SEMICOLON, matcher.start());

				if (x != -1) {
					length++;
					pos = x + 1;
				}

				continue;
			}

			if (group.equals(StringPool.LESS_THAN)) {
				int x = text.indexOf(StringPool.GREATER_THAN, matcher.start());

				if (x != -1) {
					pos = x + 1;
				}

				continue;
			}

			if (group.equals(StringPool.SPACE) ||
				group.equals(StringPool.NEW_LINE)) {

				length = 0;
				pos = matcher.start() + 1;
			}
		}

		sb.append(text.substring(lastWrite));

		return sb.toString();
	}

	protected boolean isTag(char[] tag, String text, int pos) {
		if ((pos + tag.length + 1) <= text.length()) {
			char item;

			for (int i = 0; i < tag.length; i++) {
				item = text.charAt(pos++);

				if (Character.toLowerCase(item) != tag[i]) {
					return false;
				}
			}

			item = text.charAt(pos);

			// Check that char after tag is not a letter (i.e. another tag)

			return !Character.isLetter(item);
		}
		else {
			return false;
		}
	}

	protected int stripTag(char[] tag, String text, int pos) {
		int x = pos + _TAG_SCRIPT.length;

		// Find end of the tag

		x = text.indexOf(">", x);

		if (x < 0) {
			return pos;
		}

		// Check if preceding character is / (i.e. is this instance of <abc/>)

		if (text.charAt(x-1) == '/') {
			return pos;
		}

		// Search for the ending </abc> tag

		for (;;) {
			x = text.indexOf("</", x);

			if (x >= 0) {
				if (isTag(tag, text, x + 2)) {
					pos = x;

					break;
				}
				else {

					// Skip past "</"

					x += 2;
				}
			}
			else {
				break;
			}
		}

		return pos;
	}

	private static final String[] _MS_WORD_HTML = new String[] {
		"&reg;", StringPool.APOSTROPHE, StringPool.QUOTE, StringPool.QUOTE
	};

	private static final String[] _MS_WORD_UNICODE = new String[] {
		"\u00ae", "\u2019", "\u201c", "\u201d"
	};

	private static final char[] _TAG_SCRIPT = {'s', 'c', 'r', 'i', 'p', 't'};

	private static final char[] _TAG_STYLE = {'s', 't', 'y', 'l', 'e'};

	// See http://www.w3.org/TR/xpath20/#lexical-structure

	private static final char[] _XPATH_TOKENS = {
		'(', ')', '[', ']', '.', '@', ',', ':', '/', '|', '+', '-', '=', '!',
		'<', '>', '*', '$', '"', '"', ' ', 9, 10, 13, 133, 8232};

}