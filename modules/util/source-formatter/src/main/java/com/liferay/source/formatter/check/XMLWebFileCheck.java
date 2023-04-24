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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Hugo Huijser
 */
public class XMLWebFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (fileName.endsWith("portal-web/docroot/WEB-INF/web.xml")) {
			content = _formatSecurityConstraints(
				content, _getURLPatterns(absolutePath));
		}
		else if (fileName.endsWith(
					"portal-web/docroot/WEB-INF/shielded-container-web.xml")) {

			content = _formatServletMappings(
				content, _getURLPatterns(absolutePath));
		}

		return content;
	}

	private String _formatSecurityConstraints(
		String content, Set<String> urlPatterns) {

		int x = content.indexOf("<security-constraint>");

		if (x == -1) {
			return content;
		}

		x = content.indexOf("<web-resource-collection>", x);

		if (x == -1) {
			return content;
		}

		x = content.indexOf("<url-pattern>", x);

		if (x == -1) {
			return content;
		}

		int y = content.indexOf("</web-resource-collection>", x - 3);

		if (y == -1) {
			return content;
		}

		y = content.lastIndexOf("</url-pattern>", y);

		if (y == -1) {
			return content;
		}

		StringBundler sb = new StringBundler((3 * urlPatterns.size()) + 1);

		sb.append("\t\t\t<url-pattern>/c/portal/protected</url-pattern>\n");

		for (String urlPattern : urlPatterns) {
			sb.append("\t\t\t<url-pattern>/");
			sb.append(urlPattern);
			sb.append("/c/portal/protected</url-pattern>\n");
		}

		return StringBundler.concat(
			content.substring(0, x - 3), sb, content.substring(y + 15));
	}

	private String _formatServletMappings(
		String content, Set<String> urlPatterns) {

		StringBundler sb = new StringBundler(6 * urlPatterns.size());

		for (String urlPattern : urlPatterns) {
			sb.append("\t<servlet-mapping>\n");
			sb.append("\t\t<servlet-name>I18n Servlet</servlet-name>\n");
			sb.append("\t\t<url-pattern>/");
			sb.append(urlPattern);
			sb.append("/*</url-pattern>\n");
			sb.append("\t</servlet-mapping>\n");
		}

		int x = content.indexOf("<servlet-mapping>");

		if (x == -1) {
			return content;
		}

		x = content.indexOf("<servlet-name>I18n Servlet</servlet-name>", x);

		if (x == -1) {
			return content;
		}

		x = content.lastIndexOf("<servlet-mapping>", x) - 1;

		int y = content.lastIndexOf(
			"<servlet-name>I18n Servlet</servlet-name>");

		if (y == -1) {
			return content;
		}

		y = content.indexOf("</servlet-mapping>", y);

		if (y == -1) {
			return content;
		}

		return StringBundler.concat(
			content.substring(0, x), sb, content.substring(y + 19));
	}

	private Set<String> _getURLPatterns(String absolutePath)
		throws IOException {

		Properties properties = new Properties();

		PropertiesUtil.load(
			properties,
			getPortalContent(
				"portal-impl/src/portal.properties", absolutePath));

		String[] locales = StringUtil.split(
			properties.getProperty(PropsKeys.LOCALES));

		Arrays.sort(locales);

		Set<String> urlPatterns = new TreeSet<>();

		for (String locale : locales) {
			int pos = locale.indexOf(CharPool.UNDERLINE);

			String languageCode = locale.substring(0, pos);

			urlPatterns.add(languageCode);

			urlPatterns.add(locale);

			urlPatterns.add(
				StringUtil.replace(locale, CharPool.UNDERLINE, CharPool.DASH));
		}

		return urlPatterns;
	}

}