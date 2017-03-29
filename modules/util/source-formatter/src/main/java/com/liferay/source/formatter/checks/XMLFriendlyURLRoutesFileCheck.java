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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLFriendlyURLRoutesFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("routes.xml")) {
			content = _formatFriendlyURLRoutesXML(fileName, content);
		}

		return content;
	}

	private String _formatFriendlyURLRoutesXML(String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> routeElements = rootElement.elements("route");

		ElementComparator elementComparator = new ElementComparator();

		for (Element routeElement : routeElements) {
			checkElementOrder(
				fileName, routeElement, "ignored-parameter", null,
				elementComparator);
			checkElementOrder(
				fileName, routeElement, "implicit-parameter", null,
				elementComparator);
			checkElementOrder(
				fileName, routeElement, "overridden-parameter", null,
				elementComparator);
		}

		int pos = content.indexOf("<routes>\n");

		if (pos == -1) {
			return content;
		}

		StringBundler sb = new StringBundler(9);

		String mainReleaseVersion = _getMainReleaseVersion();

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<!DOCTYPE routes PUBLIC \"-//Liferay//DTD Friendly URL ");
		sb.append("Routes ");
		sb.append(mainReleaseVersion);
		sb.append("//EN\" \"http://www.liferay.com/dtd/");
		sb.append("liferay-friendly-url-routes_");
		sb.append(
			StringUtil.replace(
				mainReleaseVersion, CharPool.PERIOD, CharPool.UNDERLINE));
		sb.append(".dtd\">\n\n");
		sb.append(content.substring(pos));

		return sb.toString();
	}

	private String _getMainReleaseVersion() {
		String releaseVersion = ReleaseInfo.getVersion();

		int pos = releaseVersion.lastIndexOf(CharPool.PERIOD);

		return releaseVersion.substring(0, pos) + ".0";
	}

}