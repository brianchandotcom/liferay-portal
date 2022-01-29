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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

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
		throws IOException {

		if (fileName.endsWith("/liferay-plugin-package.properties")) {
			return _fixIncorrectLiferayVersions(absolutePath, content);
		}

		return content;
	}

	private String _fixIncorrectLiferayVersions(
			String absolutePath, String content)
		throws IOException {

		if (!isPortalSource() || !isModulesApp(absolutePath, false)) {
			return content;
		}

		Matcher matcher = _liferayVersionsPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		boolean privateApp = isModulesApp(absolutePath, true);

		String portalVersion = getPortalVersion(privateApp);

		if (Validator.isNull(portalVersion)) {
			return content;
		}

		return StringUtil.replace(
			content, "liferay-versions=" + matcher.group(1),
			"liferay-versions=" + portalVersion + "+", matcher.start());
	}

	private static final Pattern _liferayVersionsPattern = Pattern.compile(
		"\nliferay-versions=(.*)\n");

}