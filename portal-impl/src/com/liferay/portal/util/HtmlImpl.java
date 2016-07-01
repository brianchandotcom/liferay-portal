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

package com.liferay.portal.util;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

/**
 * Provides the implementation of the HTML utility interface for escaping,
 * rendering, replacing, and stripping HTML text. This class uses XSS
 * recommendations from <a
 * href="http://www.owasp.org/index.php/Cross_Site_Scripting#How_to_Protect_Yourself">http://www.owasp.org/index.php/Cross_Site_Scripting#How_to_Protect_Yourself</a>
 * when escaping HTML text.
 *
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

	/**
	 * Generates a string with the data-* attributes generated from the keys and
	 * values of a map. For example, a map containing
	 * <code>{key1=value1;key2=value2}</code> is returned as the string
	 * <code>data-key1=value1 data-key2=value2</code>.
	 *
	 * @param  data the map of values to convert to data-* attributes
	 * @return a string with the data attributes, or <code>null</code> if the
	 *         map is <code>null</code>
	 */
	@Override
	public String buildData(Map<String, Object> data) {
		if ((data == null) || data.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(data.size() * 5);

		for (Map.Entry<String, Object> entry : data.entrySet()) {
			sb.append("data-");
			sb.append(entry.getKey());
			sb.append("=\"");
			sb.append(escapeAttribute(String.valueOf(entry.getValue())));
			sb.append("\" ");
		}

		return sb.toString();
	}

	/**
	 * Escapes the text so that it is safe to use in an HTML context.
	 *
	 * @param  text the text to escape
	 * @return the escaped HTML text, or <code>null</code> if the text is
	 *         <code>null</code>
	 */
	@Override
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

			if (c == '<') {
				replacement = "&lt;";
			}
			else if (c == '>') {
				replacement = "&gt;";
			}
			else if (c == '&') {
				replacement = "&amp;";
			}
			else if (c == '"') {
				replacement = "&#34;";
			}
			else if (c == '\'') {
				replacement = "&#39;";
			}
			else if (c == '\u00bb') {
				replacement = "&#187;";
			}
			else if (c == '\u2013') {
				replacement = "&#x2013;";
			}
			else if (c == '\u2014') {
				replacement = "&#x2014;";
			}
			else if (c == '\u2028') {
				replacement = "&#x2028;";
			}
			else if (!_isValidXmlCharacter(c) ||
					 _isUnicodeCompatibilityCharacter(c)) {

				replacement = StringPool.SPACE;
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

		if (lastReplacementIndex < text.length()) {
			sb.append(text.substring(lastReplacementIndex));
		}

		return sb.toString();
	}

	/**
	 * Escapes the input text as a hexadecimal value, based on the mode (type).
	 * The encoding types include: {@link #ESCAPE_MODE_ATTRIBUTE}, {@link
	 * #ESCAPE_MODE_CSS}, {@link #ESCAPE_MODE_JS}, {@link #ESCAPE_MODE_TEXT},
	 * and {@link #ESCAPE_MODE_URL}.
	 *
	 * <p>
	 * Note that <code>escape(text, ESCAPE_MODE_TEXT)</code> returns the same as
	 * <code>escape(text)</code>.
	 * </p>
	 *
	 * @param  text the text to escape
	 * @param  mode the encoding type
	 * @return the escaped hexadecimal value of the input text, based on the
	 *         mode, or <code>null</code> if the text is <code>null</code>
	 */
	@Override
	public String escape(String text, int mode) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		String prefix = StringPool.BLANK;
		String postfix = StringPool.BLANK;

		if (mode == ESCAPE_MODE_ATTRIBUTE) {
			prefix = "&#x";
			postfix = StringPool.SEMICOLON;
		}
		else if (mode == ESCAPE_MODE_CSS) {
			prefix = StringPool.BACK_SLASH;
		}
		else if (mode == ESCAPE_MODE_JS) {
			prefix = "\\x";
		}
		else if (mode == ESCAPE_MODE_URL) {
			return HttpUtil.encodeURL(text, true);
		}
		else {
			return escape(text);
		}

		StringBuilder sb = new StringBuilder(text.length());

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if ((mode == ESCAPE_MODE_ATTRIBUTE) &&
				(!_isValidXmlCharacter(c) ||
				 _isUnicodeCompatibilityCharacter(c))) {

				sb.append(StringPool.SPACE);
			}
			else if ((c > 255) || (c == CharPool.DASH) ||
					 (c == CharPool.UNDERLINE) ||
					 Character.isLetterOrDigit(c)) {

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

				if ((mode == ESCAPE_MODE_CSS) && (i < (text.length() - 1))) {
					char nextChar = text.charAt(i + 1);

					if ((nextChar >= CharPool.NUMBER_0) &&
						(nextChar <= CharPool.NUMBER_9)) {

						sb.append(StringPool.SPACE);
					}
				}
			}
		}

		if ((mode != ESCAPE_MODE_ATTRIBUTE) && (sb.length() == text.length())) {
			return text;
		}

		return sb.toString();
	}

	/**
	 * Escapes the attribute value so that it is safe to use as an attribute
	 * value.
	 *
	 * @param  attribute the attribute to escape
	 * @return the escaped attribute value, or <code>null</code> if the
	 *         attribute value is <code>null</code>
	 */
	@Override
	public String escapeAttribute(String attribute) {
		return escape(attribute, ESCAPE_MODE_ATTRIBUTE);
	}

	/**
	 * Escapes the CSS value so that it is safe to use in a CSS context.
	 *
	 * @param  css the CSS value to escape
	 * @return the escaped CSS value, or <code>null</code> if the CSS value is
	 *         <code>null</code>
	 */
	@Override
	public String escapeCSS(String css) {
		return escape(css, ESCAPE_MODE_CSS);
	}

	/**
	 * Escapes the HREF attribute so that it is safe to use as an HREF
	 * attribute.
	 *
	 * @param  href the HREF attribute to escape
	 * @return the escaped HREF attribute, or <code>null</code> if the HREF
	 *         attribute is <code>null</code>
	 */
	@Override
	public String escapeHREF(String href) {
		if (href == null) {
			return null;
		}

		if (href.length() == 0) {
			return StringPool.BLANK;
		}

		int index = href.indexOf(StringPool.COLON);

		if (index == 4) {
			String protocol = StringUtil.toLowerCase(href.substring(0, 4));

			if (protocol.equals("data")) {
				href = StringUtil.replaceFirst(href, CharPool.COLON, "%3a");
			}
		}
		else if (index == 10) {
			String protocol = StringUtil.toLowerCase(href.substring(0, 10));

			if (protocol.equals("javascript")) {
				href = StringUtil.replaceFirst(href, CharPool.COLON, "%3a");
			}
		}

		return escapeAttribute(href);
	}

	/**
	 * Escapes the JavaScript value so that it is safe to use in a JavaScript
	 * context.
	 *
	 * @param  js the JavaScript value to escape
	 * @return the escaped JavaScript value, or <code>null</code> if the
	 *         JavaScript value is <code>null</code>
	 */
	@Override
	public String escapeJS(String js) {
		return escape(js, ESCAPE_MODE_JS);
	}

	@Override
	public String escapeJSLink(String link) {
		if (Validator.isNull(link)) {
			return StringPool.BLANK;
		}

		if (link.indexOf(StringPool.COLON) == 10) {
			String protocol = StringUtil.toLowerCase(link.substring(0, 10));

			if (protocol.equals("javascript")) {
				link = StringUtil.replaceFirst(link, CharPool.COLON, "%3a");
			}
		}

		return link;
	}

	/**
	 * Escapes the URL value so that it is safe to use as a URL.
	 *
	 * @param  url the URL value to escape
	 * @return the escaped URL value, or <code>null</code> if the URL value is
	 *         <code>null</code>
	 */
	@Override
	public String escapeURL(String url) {
		return escape(url, ESCAPE_MODE_URL);
	}

	@Override
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

	@Override
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

	/**
	 * Extracts the raw text from the HTML input, compressing its whitespace and
	 * removing all attributes, scripts, and styles.
	 *
	 * <p>
	 * For example, raw text returned by this method can be stored in a search
	 * index.
	 * </p>
	 *
	 * @param  html the HTML text
	 * @return the raw text from the HTML input, or <code>null</code> if the
	 *         HTML input is <code>null</code>
	 */
	@Override
	public String extractText(String html) {
		if (html == null) {
			return null;
		}

		Source source = new Source(html);

		TextExtractor textExtractor = source.getTextExtractor();

		return textExtractor.toString();
	}

	@Override
	public String fromInputSafe(String text) {
		return StringUtil.replace(text, "&amp;", "&");
	}

	@Override
	public String getAUICompatibleId(String text) {
		if (Validator.isNull(text)) {
			return text;
		}

		StringBundler sb = null;

		int lastReplacementIndex = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (((c <= 127) && (Validator.isChar(c) || Validator.isDigit(c))) ||
				((c > 127) && (c != CharPool.FIGURE_SPACE) &&
				 (c != CharPool.NARROW_NO_BREAK_SPACE) &&
				 (c != CharPool.NO_BREAK_SPACE))) {

				continue;
			}

			if (sb == null) {
				sb = new StringBundler();
			}

			if (i > lastReplacementIndex) {
				sb.append(text.substring(lastReplacementIndex, i));
			}

			sb.append(CharPool.UNDERLINE);

			if (c != CharPool.UNDERLINE) {
				sb.append(StringUtil.toHexString(c));
			}

			sb.append(CharPool.UNDERLINE);

			lastReplacementIndex = i + 1;
		}

		if (sb == null) {
			return text;
		}

		if (lastReplacementIndex < text.length()) {
			sb.append(text.substring(lastReplacementIndex));
		}

		return sb.toString();
	}

	/**
	 * Renders the HTML content into text. This provides a human readable
	 * version of the content that is modeled on the way Mozilla
	 * Thunderbird&reg; and other email clients provide an automatic conversion
	 * of HTML content to text in their alternative MIME encoding of emails.
	 *
	 * <p>
	 * Using the default settings, the output complies with the
	 * <code>Text/Plain; Format=Flowed (DelSp=No)</code> protocol described in
	 * <a href="http://tools.ietf.org/html/rfc3676">RFC-3676</a>.
	 * </p>
	 *
	 * @param  html the HTML text
	 * @return the rendered HTML text, or <code>null</code> if the HTML text is
	 *         <code>null</code>
	 */
	@Override
	public String render(String html) {
		if (html == null) {
			return null;
		}

		Source source = new Source(html);

		Renderer renderer = source.getRenderer();

		return renderer.toString();
	}

	/**
	 * Replaces all Microsoft&reg; Word Unicode characters with plain HTML
	 * entities or characters.
	 *
	 * @param      text the text
	 * @return     the converted text, or <code>null</code> if the text is
	 *             <code>null</code>
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceMsWordCharacters(String text) {
		return StringUtil.replace(text, _MS_WORD_UNICODE, _MS_WORD_HTML);
	}

	/**
	 * Replaces all new lines or carriage returns with the <code><br /></code>
	 * HTML tag.
	 *
	 * @param  html the text
	 * @return the converted text, or <code>null</code> if the text is
	 *         <code>null</code>
	 */
	@Override
	public String replaceNewLine(String html) {
		if (html == null) {
			return null;
		}

		html = StringUtil.replace(html, StringPool.RETURN_NEW_LINE, "<br />");

		return StringUtil.replace(html, CharPool.NEW_LINE, "<br />");
	}

	/**
	 * Strips all content delimited by the tag out of the text.
	 *
	 * <p>
	 * If the tag appears multiple times, all occurrences (including the tag)
	 * are stripped. The tag may have attributes. In order for this method to
	 * recognize the tag, it must consist of a separate opening and closing tag.
	 * Self-closing tags remain in the result.
	 * </p>
	 *
	 * @param  text the text
	 * @param  tag the tag used for delimiting, which should only be the tag's
	 *         name (e.g. no &lt;)
	 * @return the text, without the stripped tag and its contents, or
	 *         <code>null</code> if the text is <code>null</code>
	 */
	@Override
	public String stripBetween(String text, String tag) {
		return StringUtil.stripBetween(text, "<" + tag, "</" + tag + ">");
	}

	/**
	 * Strips all XML comments out of the text.
	 *
	 * @param  text the text
	 * @return the text, without the stripped XML comments, or <code>null</code>
	 *         if the text is <code>null</code>
	 */
	@Override
	public String stripComments(String text) {
		return StringUtil.stripBetween(text, "<!--", "-->");
	}

	@Override
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

	/**
	 * Encodes the text so that it's safe to use as an HTML input field value.
	 *
	 * <p>
	 * For example, the <code>&</code> character is replaced by
	 * <code>&amp;amp;</code>.
	 * </p>
	 *
	 * @param  text the text
	 * @return the encoded text that is safe to use as an HTML input field
	 *         value, or <code>null</code> if the text is <code>null</code>
	 */
	@Override
	public String toInputSafe(String text) {
		return StringUtil.replace(
			text, new char[] {'&', '\"'}, new String[] {"&amp;", "&quot;"});
	}

	@Override
	public String unescape(String text) {
		return StringUtil.replace(text, "&", ";", _unescapeMap);
	}

	@Override
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

	@Override
	public String wordBreak(String text, int columns) {
		StringBundler sb = new StringBundler();

		int length = 0;
		int lastWrite = 0;
		int pos = 0;

		Matcher matcher = _pattern.matcher(text);

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
			char item = '\0';

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

		while (true) {
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

	private boolean _isUnicodeCompatibilityCharacter(char c) {
		if (((c >= '\u007f') && (c <= '\u0084')) ||
			((c >= '\u0086') && (c <= '\u009f')) ||
			((c >= '\ufdd0') && (c <= '\ufdef'))) {

			return true;
		}

		return false;
	}

	private boolean _isValidXmlCharacter(char c) {
		if ((c == '\u0009') || (c == CharPool.NEW_LINE) ||
			(c == CharPool.RETURN) || ((c >= '\u0020') && (c <= '\ud7ff')) ||
			((c >= '\ue000') && (c <= '\ufffd'))) {

			return true;
		}

		return false;
	}

	private static final String[] _MS_WORD_HTML = new String[] {
		"&reg;", StringPool.APOSTROPHE, StringPool.QUOTE, StringPool.QUOTE
	};

	private static final String[] _MS_WORD_UNICODE =
		new String[] {"\u00ae", "\u2019", "\u201c", "\u201d"};

	private static final char[] _TAG_SCRIPT = {'s', 'c', 'r', 'i', 'p', 't'};

	private static final char[] _TAG_STYLE = {'s', 't', 'y', 'l', 'e'};

	// See http://www.w3.org/TR/xpath20/#lexical-structure

	private static final char[] _XPATH_TOKENS = {
		'(', ')', '[', ']', '.', '@', ',', ':', '/', '|', '+', '-', '=', '!',
		'<', '>', '*', '$', '"', '"', ' ', 9, 10, 13, 133, 8232
	};

	private static final Map<String, String> _unescapeMap = new HashMap<>();

	static {
		_unescapeMap.put("lt", "<");
		_unescapeMap.put("gt", ">");
		_unescapeMap.put("amp", "&");
		_unescapeMap.put("rsquo", "\u2019");
		_unescapeMap.put("#34", "\"");
		_unescapeMap.put("#39", "'");
		_unescapeMap.put("#40", "(");
		_unescapeMap.put("#41", ")");
		_unescapeMap.put("#44", ",");
		_unescapeMap.put("#35", "#");
		_unescapeMap.put("#37", "%");
		_unescapeMap.put("#59", ";");
		_unescapeMap.put("#61", "=");
		_unescapeMap.put("#43", "+");
		_unescapeMap.put("#45", "-");
	}

	private final Pattern _pattern = Pattern.compile("([\\s<&]|$)");

}