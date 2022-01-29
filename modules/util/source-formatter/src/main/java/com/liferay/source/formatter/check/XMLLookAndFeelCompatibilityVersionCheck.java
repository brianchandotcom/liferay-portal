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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Peter Shin
 */
public class XMLLookAndFeelCompatibilityVersionCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException {

		if (fileName.endsWith("-look-and-feel.xml")) {
			_checkCompatibility(fileName, absolutePath, content);
		}

		return content;
	}

	private void _checkCompatibility(
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException {

		if (!isPortalSource() || !isModulesApp(absolutePath, false)) {
			return;
		}

		boolean privateApp = isModulesApp(absolutePath, true);

		String portalVersion = getPortalVersion(privateApp);

		if (Validator.isNull(portalVersion)) {
			return;
		}

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> compatibilityElements = rootElement.elements(
			"compatibility");

		for (Element compatibilityElement : compatibilityElements) {
			if (!_hasPortalVersions(compatibilityElement, portalVersion)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Missing version: <version>", portalVersion,
						"+</version>"));
			}
		}
	}

	private boolean _hasPortalVersions(
		Element compatibilityElement, String portalVersion) {

		List<Element> versionElements = compatibilityElement.elements(
			"version");

		for (Element versionElement : versionElements) {
			String version = versionElement.getText();

			if (StringUtil.equalsIgnoreCase(version, portalVersion + "+")) {
				return true;
			}
		}

		return false;
	}

}