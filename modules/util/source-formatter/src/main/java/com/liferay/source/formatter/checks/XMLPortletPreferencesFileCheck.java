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

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLPortletPreferencesFileCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		if (fileName.endsWith("portlet-preferences.xml")) {
			_checkPortletPreferencesXML(
				sourceFormatterMessages, fileName, content);
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkPortletPreferencesXML(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		checkElementOrder(
			sourceFormatterMessages, fileName, document.getRootElement(),
			"preference", null, new PortletPreferenceElementComparator());

		Matcher matcher = _incorrectDefaultPreferencesFileName.matcher(
			fileName);

		if (matcher.find()) {
			String correctFileName =
				matcher.group(1) + "-default-portlet-preferences.xml";

			addMessage(
				sourceFormatterMessages, fileName,
				"Rename file to " + correctFileName);
		}
	}

	private final Pattern _incorrectDefaultPreferencesFileName =
		Pattern.compile("/default-([\\w-]+)-portlet-preferences\\.xml$");

	private class PortletPreferenceElementComparator extends ElementComparator {

		@Override
		public String getElementName(Element preferenceElement) {
			Element nameElement = preferenceElement.element(getNameAttribute());

			return nameElement.getStringValue();
		}

	}

}