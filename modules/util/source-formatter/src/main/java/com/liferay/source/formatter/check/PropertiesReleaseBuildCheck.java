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

import java.io.IOException;
import java.io.StringReader;

import java.util.Properties;

/**
 * @author Hugo Huijser
 */
public class PropertiesReleaseBuildCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/release.properties")) {
			return content;
		}

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		String releaseInfoBuildValue = properties.getProperty(
			"release.info.build");

		if (releaseInfoBuildValue == null) {
			return content;
		}

		String releaseInfoContent = getPortalContent(
			"portal-kernel/src/com/liferay/portal/kernel/util/ReleaseInfo.java",
			absolutePath);

		if (!releaseInfoContent.contains(releaseInfoBuildValue)) {
			addMessage(
				fileName,
				"release.info.build '" + releaseInfoBuildValue +
					"' does not exist in ReleaseInfo.java");
		}

		return content;
	}

}