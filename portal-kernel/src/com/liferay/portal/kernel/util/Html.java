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
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 */
public interface Html {

	public String buildData(Map<String, Object> data);

	public String escape(String text);

	public String escapeAttribute(String attribute);

	public String escapeCSS(String css);

	public String escapeHREF(String href);

	public String escapeJS(String js);

	public String escapeJSLink(String link);

	public String escapeURL(String url);

	public String escapeXPath(String xPath);

	public String escapeXPathAttribute(String xPathAttribute);

	public String extractText(String html);

	public String fromInputSafe(String text);

	public String getAUICompatibleId(String text);

	public String render(String html);

	public String replaceNewLine(String html);

	public String stripBetween(String text, String tag);

	public String stripComments(String text);

	public String stripHtml(String text);

	public String toInputSafe(String text);

	public String unescape(String text);

	public String unescapeCDATA(String text);

	public String wordBreak(String text, int columns);

}