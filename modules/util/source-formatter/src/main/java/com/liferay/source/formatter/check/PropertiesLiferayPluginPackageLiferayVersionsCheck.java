/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class PropertiesLiferayPluginPackageLiferayVersionsCheck
	extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/liferay-plugin-package.properties")) {
			return _fixIncorrectLiferayVersions(absolutePath, content);
		}

		return content;
	}

	protected String getLiferayVersion(String absolutePath) throws Exception {
		return getPortalVersion(isModulesApp(absolutePath, true));
	}

	protected boolean isSkipFix(String absolutePath) {
		if (!isModulesApp(absolutePath, false) || !isPortalSource()) {
			return true;
		}

		return false;
	}

	private String _fixIncorrectLiferayVersions(
			String absolutePath, String content)
		throws Exception {

		if (isSkipFix(absolutePath)) {
			return content;
		}

		Matcher matcher = _liferayVersionsPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String liferayVersion = getLiferayVersion(absolutePath);

		if (Validator.isNull(liferayVersion)) {
			return content;
		}

		return StringUtil.replace(
			content, "liferay-versions=" + matcher.group(1),
			"liferay-versions=" + liferayVersion + "+", matcher.start());
	}

	private static final Pattern _liferayVersionsPattern = Pattern.compile(
		"\nliferay-versions=(.*)\n");

}