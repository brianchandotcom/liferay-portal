/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class XMLServiceMVCCEnabledCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/service.xml") ||
			absolutePath.contains("/gradleTest/") ||
			absolutePath.contains("-test-")) {

			return content;
		}

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return content;
		}

		Element rootElement = document.getRootElement();

		if (rootElement.attributeValue("mvcc-enabled") != null) {
			return content;
		}

		List<String> allowedFileNames = getAttributeValues(
			_ALLOWED_MISSING_MVCC_ENABLED_FILE_NAMES_KEY, absolutePath);

		for (String allowedFileName : allowedFileNames) {
			if (absolutePath.endsWith(allowedFileName)) {
				return content;
			}
		}

		addMessage(
			fileName,
			"Attribute \"mvcc-enabled\" should always be set in service.xml. " +
				"Preferably, set \"mvcc-enabled=\"true\"\".");

		return content;
	}

	private static final String _ALLOWED_MISSING_MVCC_ENABLED_FILE_NAMES_KEY =
		"allowedMissingMVCCEnabledFileNames";

}