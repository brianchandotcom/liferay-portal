/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class XMLServiceAutoImportDefaultReferencesCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		if (!fileName.endsWith("/service.xml") ||
			(!absolutePath.contains("/modules/apps/") &&
			 !absolutePath.contains("/modules/dxp/apps/"))) {

			return content;
		}

		if (absolutePath.contains("/modules/apps/archived/")) {
			return content;
		}

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return content;
		}

		Element rootElement = document.getRootElement();

		if (GetterUtil.getBoolean(
				rootElement.attributeValue("auto-import-default-references"),
				true)) {

			addMessage(
				fileName,
				"Attribute 'auto-import-default-references' should always be " +
					"'false' in service.xml");
		}

		return content;
	}

}