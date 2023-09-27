/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.comparator.ElementComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLPortletPreferencesFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("portlet-preferences.xml")) {
			_checkPortletPreferencesXML(fileName, content);
		}

		return content;
	}

	private void _checkPortletPreferencesXML(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		checkElementOrder(
			fileName, document.getRootElement(), "preference", null,
			new PortletPreferenceElementComparator());

		Matcher matcher = _incorrectDefaultPreferencesFileNamePattern.matcher(
			fileName);

		if (matcher.find()) {
			String correctFileName =
				matcher.group(1) + "-default-portlet-preferences.xml";

			addMessage(fileName, "Rename file to " + correctFileName);
		}
	}

	private static final Pattern _incorrectDefaultPreferencesFileNamePattern =
		Pattern.compile("/default-([\\w-]+)-portlet-preferences\\.xml$");

	private class PortletPreferenceElementComparator extends ElementComparator {

		@Override
		public String getElementName(Element preferenceElement) {
			Element nameElement = preferenceElement.element(getNameAttribute());

			return nameElement.getStringValue();
		}

	}

}