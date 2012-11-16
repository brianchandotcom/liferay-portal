/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

/**
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 */
public class HtmlUtil {

	public static String escape(String html) {
		return getHtml().escape(html);
	}

	public static String escape(String html, int mode) {
		return getHtml().escape(html, mode);
	}

	public static String escapeAttribute(String attribute) {
		return getHtml().escapeAttribute(attribute);
	}

	public static String escapeCSS(String css) {
		return getHtml().escapeCSS(css);
	}

	public static String escapeHREF(String href) {
		return getHtml().escapeHREF(href);
	}

	public static String escapeJS(String js) {
		return getHtml().escapeJS(js);
	}

	public static String escapeURL(String url) {
		return getHtml().escapeURL(url);
	}

	public static String escapeXPath(String xPath) {
		return getHtml().escapeXPath(xPath);
	}

	public static String escapeXPathAttribute(String xPathAttribute) {
		return getHtml().escapeXPathAttribute(xPathAttribute);
	}

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

	public static String render(String html) {
		return getHtml().render(html);
	}

	public static String replaceMsWordCharacters(String html) {
		return getHtml().replaceMsWordCharacters(html);
	}

	public static String stripBetween(String html, String tag) {
		return getHtml().stripBetween(html, tag);
	}

	public static String stripComments(String html) {
		return getHtml().stripComments(html);
	}

	public static String stripHtml(String html) {
		return getHtml().stripHtml(html);
	}

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