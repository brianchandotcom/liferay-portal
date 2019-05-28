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

package com.liferay.portal.kernel.util;

import java.util.Map;

/**
 * Provides utility methods for escaping, rendering, replacing, and stripping
 * HTML text. This class uses XSS recommendations from <a
 * href="http://www.owasp.org/index.php/Cross_Site_Scripting#How_to_Protect_Yourself">http://www.owasp.org/index.php/Cross_Site_Scripting#How_to_Protect_Yourself</a>
 * when escaping HTML text.
 *
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 */
public class HtmlUtil {

	public static String buildData(Map<String, Object> data) {
		return getHtml().buildData(data);
	}

	/**
	 * Escapes the text so that it is safe to use in an HTML context.
	 *
	 * @param  text the text to escape
	 * @return the escaped HTML text, or <code>null</code> if the text is
	 *         <code>null</code>
	 */
	public static String escape(String text) {
		return getHtml().escape(text);
	}

	/**
	 * Escapes the input text as a hexadecimal value, based on the mode (type).
	 *
	 * @param  text the text to escape
	 * @param  mode the encoding type
	 * @return the escaped hexadecimal value of the input text, based on the
	 *         mode, or <code>null</code> if the text is <code>null</code>
	 * @see    com.liferay.portal.util.HtmlImpl#escape(String, int)
	 * @deprecated As of Mueller (7.2.x), replaced by
	 *			   {@link #escapeAttribute(String)}, {@link #escapeCSS(String)},
	 *			   {@link #escapeJS(String)}, {@link #escape(String)},
	 *			   {@link #escapeURL(String)}
	 */
	@Deprecated
	public static String escape(String text, int mode) {
		return getHtml().escape(text, mode);
	}

	/**
	 * Escapes the attribute value so that it is safe to use as an attribute
	 * value.
	 *
	 * @param  attribute the attribute to escape
	 * @return the escaped attribute value, or <code>null</code> if the
	 *         attribute value is <code>null</code>
	 */
	public static String escapeAttribute(String attribute) {
		return getHtml().escapeAttribute(attribute);
	}

	/**
	 * Escapes the CSS value so that it is safe to use in a CSS context.
	 *
	 * @param  css the CSS value to escape
	 * @return the escaped CSS value, or <code>null</code> if the CSS value is
	 *         <code>null</code>
	 */
	public static String escapeCSS(String css) {
		return getHtml().escapeCSS(css);
	}

	/**
	 * Escapes the HREF attribute so that it is safe to use as an HREF
	 * attribute.
	 *
	 * @param  href the HREF attribute to escape
	 * @return the escaped HREF attribute, or <code>null</code> if the HREF
	 *         attribute is <code>null</code>
	 */
	public static String escapeHREF(String href) {
		return getHtml().escapeHREF(href);
	}

	/**
	 * Escapes the JavaScript value so that it is safe to use in a JavaScript
	 * context.
	 *
	 * @param  js the JavaScript value to escape
	 * @return the escaped JavaScript value, or <code>null</code> if the
	 *         JavaScript value is <code>null</code>
	 */
	public static String escapeJS(String js) {
		return getHtml().escapeJS(js);
	}

	public static String escapeJSLink(String link) {
		return getHtml().escapeJSLink(link);
	}

	/**
	 * Escapes the URL value so that it is safe to use as a URL.
	 *
	 * @param  url the URL value to escape
	 * @return the escaped URL value, or <code>null</code> if the URL value is
	 *         <code>null</code>
	 */
	public static String escapeURL(String url) {
		return getHtml().escapeURL(url);
	}

	public static String escapeXPath(String xPath) {
		return getHtml().escapeXPath(xPath);
	}

	public static String escapeXPathAttribute(String xPathAttribute) {
		return getHtml().escapeXPathAttribute(xPathAttribute);
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
	public static String extractText(String html) {
		return getHtml().extractText(html);
	}

	public static String fromInputSafe(String text) {
		return getHtml().fromInputSafe(text);
	}

	public static String getAUICompatibleId(String html) {
		return getHtml().getAUICompatibleId(html);
	}

	public static Html getHtml() {
		return _html;
	}

	/**
	 * Renders the HTML content into text. This provides a human readable
	 * version of the segment content that is modeled on the way Mozilla
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
	public static String render(String html) {
		return getHtml().render(html);
	}

	/**
	 * Replaces all new lines or carriage returns with the <code><br /></code>
	 * HTML tag.
	 *
	 * @param  html the text
	 * @return the converted text, or <code>null</code> if the HTML text is
	 *         <code>null</code>
	 */
	public static String replaceNewLine(String html) {
		return getHtml().replaceNewLine(html);
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
	public static String stripBetween(String text, String tag) {
		return getHtml().stripBetween(text, tag);
	}

	/**
	 * Strips all XML comments out of the text.
	 *
	 * @param  text the text
	 * @return the text, without the stripped XML comments, or <code>null</code>
	 *         if the text is <code>null</code>
	 */
	public static String stripComments(String text) {
		return getHtml().stripComments(text);
	}

	public static String stripHtml(String text) {
		return getHtml().stripHtml(text);
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
	public static String toInputSafe(String text) {
		return getHtml().toInputSafe(text);
	}

	public static String unescape(String text) {
		return getHtml().unescape(text);
	}

	public static String unescapeCDATA(String text) {
		return getHtml().unescapeCDATA(text);
	}

	public static String wordBreak(String text, int columns) {
		return getHtml().wordBreak(text, columns);
	}

	public void setHtml(Html html) {
		_html = html;
	}

	private static Html _html;

}