/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class HtmlUtil_IW {
	public static HtmlUtil_IW getInstance() {
		return _instance;
	}

	public java.lang.String buildData(
		java.util.Map<java.lang.String, java.lang.Object> data) {
		return HtmlUtil.buildData(data);
	}

	public java.lang.String escape(java.lang.String text) {
		return HtmlUtil.escape(text);
	}

	public java.lang.String escapeAttribute(java.lang.String attribute) {
		return HtmlUtil.escapeAttribute(attribute);
	}

	public java.lang.String escapeCSS(java.lang.String css) {
		return HtmlUtil.escapeCSS(css);
	}

	public java.lang.String escapeHREF(java.lang.String href) {
		return HtmlUtil.escapeHREF(href);
	}

	public java.lang.String escapeJS(java.lang.String js) {
		return HtmlUtil.escapeJS(js);
	}

	public java.lang.String escapeJSLink(java.lang.String link) {
		return HtmlUtil.escapeJSLink(link);
	}

	public java.lang.String escapeURL(java.lang.String url) {
		return HtmlUtil.escapeURL(url);
	}

	public java.lang.String escapeXPath(java.lang.String xPath) {
		return HtmlUtil.escapeXPath(xPath);
	}

	public java.lang.String escapeXPathAttribute(
		java.lang.String xPathAttribute) {
		return HtmlUtil.escapeXPathAttribute(xPathAttribute);
	}

	public java.lang.String fromInputSafe(java.lang.String text) {
		return HtmlUtil.fromInputSafe(text);
	}

	public java.lang.String getAUICompatibleId(java.lang.String html) {
		return HtmlUtil.getAUICompatibleId(html);
	}

	public java.lang.String replaceNewLine(java.lang.String html) {
		return HtmlUtil.replaceNewLine(html);
	}

	public java.lang.String stripBetween(java.lang.String text,
		java.lang.String tag) {
		return HtmlUtil.stripBetween(text, tag);
	}

	public java.lang.String stripComments(java.lang.String text) {
		return HtmlUtil.stripComments(text);
	}

	public java.lang.String stripHtml(java.lang.String text) {
		return HtmlUtil.stripHtml(text);
	}

	public java.lang.String toInputSafe(java.lang.String text) {
		return HtmlUtil.toInputSafe(text);
	}

	public java.lang.String unescape(java.lang.String text) {
		return HtmlUtil.unescape(text);
	}

	public java.lang.String unescapeCDATA(java.lang.String text) {
		return HtmlUtil.unescapeCDATA(text);
	}

	public java.lang.String wordBreak(java.lang.String text, int columns) {
		return HtmlUtil.wordBreak(text, columns);
	}

	private HtmlUtil_IW() {
	}

	private static HtmlUtil_IW _instance = new HtmlUtil_IW();
}