/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public String fromInputSafe(String text);

	public String getAUICompatibleId(String text);

	public String replaceNewLine(String html);

	public String stripBetween(String text, String tag);

	public String stripComments(String text);

	public String stripHtml(String text);

	public String toInputSafe(String text);

	public String unescape(String text);

	public String unescapeCDATA(String text);

	public String wordBreak(String text, int columns);

}