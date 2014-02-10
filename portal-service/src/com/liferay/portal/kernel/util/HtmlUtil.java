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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import net.htmlparser.jericho.Renderer;

/**
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 */
public class HtmlUtil {

	/**
	 * Escape given String using XSS recommendations from
	 * {@link http://www.owasp.org/index.php/Cross_Site_Scripting#How_to_Protect_Yourself}
	 * 
	 * @param text The text to be HTML-encoded
	 * @return the sanitized text that is safe to use in an HTML context
	 */

	public static String escape(String html) {
		return getHtml().escape(html);
	}

	/**
	 * Escape into Hex-values for the given encoding type 
	 * (CSS, JS, ATTRIBUTE, URL, TEXT).
	 * Note that escape(text, ESCAPE_MODE_TEXT) is the same as escape(text). 
	 * 
	 * @see escapeCSS, escapeAttribute, escapeURL, escapeJS
	 * @param text the text to escape
	 * @param type one of the ESCAPE_MODE_* constants in this class
	 * @return hex-encoded value of input text for the given use
	 */

	public static String escape(String html, int mode) {
		return getHtml().escape(html, mode);
	}

	/**
	 * escape the given text so that it can safely be used as an 
	 * attribute value
	 */

	public static String escapeAttribute(String attribute) {
		return getHtml().escapeAttribute(attribute);
	}

	/**
	 * escape the given text so that it can safely be used in CSS 
	 */

	public static String escapeCSS(String css) {
		return getHtml().escapeCSS(css);
	}

	/**
	 * escape the given text so that it can safely be used as a href
	 * attribute. 
	 */

	public static String escapeHREF(String href) {
		return getHtml().escapeHREF(href);
	}
	
	/**
	 * escape the given text so that it can safely be used in javascript
	 */

	public static String escapeJS(String js) {
		return getHtml().escapeJS(js);
	}

	/**
	 * escape the given text so that it can safely be used as a URL
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
	 * extracts the raw text from given HTML input, e.g. to store in a search index.
	 * Also compresses whitespace as much as possible.
	 * @see net.htmlparser.jericho.TextExtractor
	 * 
	 * @param html the html text to process
	 * @return the text content without attributes, scripts and styles
	 */

	public static String extractText(String html) {
		return getHtml().extractText(html);
	}

	public static String fromInputSafe(String html) {
		return getHtml().fromInputSafe(html);
	}

	public static Html getHtml() {
		PortalRuntimePermission.checkGetBeanProperty(HtmlUtil.class);

		return _html;
	}
	
	/**
	 *  Performs a simple rendering of HTML markup into text.
	 *  This provides a human readable version of the segment content that 
	 *  is modelled on the way Mozilla Thunderbird and other email clients 
	 *  provide an automatic conversion of HTML content to text in their 
	 *  alternative MIME encoding of emails.
	 *  
	 *  The output using default settings complies with the 
	 *  "text/plain; format=flowed" (DelSp=No) protocol described in 
	 *  RFC3676.
	 *  
	 *   @see Renderer
	 *   @param html the content to render
	 *   @return the rendered content
	 */
	
	public static String render(String html) {
		return getHtml().render(html);
	}

	/**
	 * replaces a few fancy unicode characters that MS-Word tends to use
	 * with some plain HTML entities or characters.
	 */

	public static String replaceMsWordCharacters(String html) {
		return getHtml().replaceMsWordCharacters(html);
	}

	/**
	 * replaces all newlines (or carriage-return/new-lines) with "<br />"
	 */

	public static String replaceNewLine(String html) {
		return getHtml().replaceNewLine(html);
	}

	/**
	 * Strips all content delimited by the given tag out of the text. 
	 * If the tag appears multiple times, all occurrences (including 
	 * the tag) will be stripped. The tag may have attributes. In order
	 * to match, the tag must consist of a separate opening and closing 
	 * tag. Self-closing tags will remain in the result
	 * 
	 * @param text the text to be stripped
	 * @param tag the tag used for delimiting - give just the tag's name, no &lt; etc.
	 * @return the stripped text, without the given tag and the contents of the tag.
	 */
	
	public static String stripBetween(String html, String tag) {
		return getHtml().stripBetween(html, tag);
	}

	/**
	 * Strips all xml comments
	 * 
	 * @param text the text to be stripped
	 */

	public static String stripComments(String html) {
		return getHtml().stripComments(html);
	}

	public static String stripHtml(String html) {
		return getHtml().stripHtml(html);
	}

	/**
	 * Encodes text so that it's safe to be used in HTML input fields as value
	 * 
	 * @param text the text to be used as value
	 * @return the text that can be used as input field value
	 */
	
	public static String toInputSafe(String html) {
		return getHtml().toInputSafe(html);
	}

	public static String unescape(String html) {
		return getHtml().unescape(html);
	}

	public static String unescapeCDATA(String html) {
		return getHtml().unescapeCDATA(html);
	}

	public static String wordBreak(String html, int columns) {
		return getHtml().wordBreak(html, columns);
	}

	public void setHtml(Html html) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_html = html;
	}

	private static Html _html;

}