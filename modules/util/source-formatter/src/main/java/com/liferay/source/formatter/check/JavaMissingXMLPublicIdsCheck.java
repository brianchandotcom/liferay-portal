/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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