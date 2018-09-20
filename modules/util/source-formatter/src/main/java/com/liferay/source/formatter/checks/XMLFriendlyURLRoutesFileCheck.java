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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLFriendlyURLRoutesFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		if (fileName.endsWith("routes.xml")) {
			_checkDTDVersion(fileName, content);
			_checkOrder(fileName, content);
		}

		return content;
	}

	private void _checkDTDVersion(String fileName, String content) {
		Matcher matcher = _doctypePattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		String mainMajorReleaseVersion = _getMainMajorReleaseVersion();

		if (!mainMajorReleaseVersion.equals(matcher.group(1)) ||
			!mainMajorReleaseVersion.equals(matcher.group(2))) {

			addMessage(
				fileName,
				"Major version for dtd should be '" + mainMajorReleaseVersion +
					"'",
				getLineNumber(content, matcher.start()));
		}
	}

	private void _checkOrder(String fileName, String content)
		throws DocumentException {

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
	}

	private String _getMainMajorReleaseVersion() {
		String releaseVersion = ReleaseInfo.getVersion();

		int pos = releaseVersion.indexOf(CharPool.PERIOD);

		return releaseVersion.substring(0, pos);
	}

	private static final Pattern _doctypePattern = Pattern.compile(
		"DOCTYPE routes PUBLIC \"-//Liferay//DTD Friendly URL Routes " +
			"([0-9]+)\\.[0-9]+\\.[0-9]+//EN\" \"http://www.liferay.com/dtd/" +
				"liferay-friendly-url-routes_([0-9]+)_[0-9]+_[0-9]+\\.dtd");

}