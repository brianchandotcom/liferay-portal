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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

/**
 * @author Alan Huang
 */
public class JavaMissingXMLPublicIdsCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith(
				"/modules/sdk/ant-bnd/src/main/java/com/liferay/ant/bnd" +
					"/social/SocialAnalyzerPlugin.java") &&
			!fileName.endsWith(
				"/portal-impl/src/com/liferay/portal/util" +
					"/EntityResolver.java") &&
			!fileName.endsWith(
				"/portal-impl/src/com/liferay/portlet/social/util" +
					"/SocialConfigurationImpl.java")) {

			return content;
		}

		File releasePropertiesFile = new File(
			getPortalDir(), _RELEASE_PROPERTIES_FILE_NAME);

		if (!releasePropertiesFile.exists()) {
			return content;
		}

		Properties properties = new Properties();

		properties.load(new FileInputStream(releasePropertiesFile));

		String lpVersion = properties.getProperty("lp.version");

		if (lpVersion == null) {
			return content;
		}

		if (content.indexOf(lpVersion + "//EN") == -1) {
			addMessage(
				fileName,
				"Missing public id '" + lpVersion + "' for check XML files");
		}

		return content;
	}

	private static final String _RELEASE_PROPERTIES_FILE_NAME =
		"release.properties";

}