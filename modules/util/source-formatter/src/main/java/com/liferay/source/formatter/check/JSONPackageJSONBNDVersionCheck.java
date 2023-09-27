/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.util.FileUtil;

import java.io.IOException;

import org.json.JSONObject;

/**
 * @author Hugo Huijser
 */
public class JSONPackageJSONBNDVersionCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/package.json") ||
			(!absolutePath.contains("/modules/apps/") &&
			 !absolutePath.contains("/modules/dxp/apps/") &&
			 !absolutePath.contains("/modules/private/apps/"))) {

			return content;
		}

		JSONObject jsonObject = new JSONObject(content);

		if (jsonObject.isNull("version")) {
			return content;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);

		if (!FileUtil.exists(fileName.substring(0, x + 1) + "bnd.bnd")) {
			return content;
		}

		String version = jsonObject.getString("version");

		BNDSettings bndSettings = getBNDSettings(fileName);

		String bndReleaseVersion = bndSettings.getReleaseVersion();

		if (!version.equals(bndReleaseVersion)) {
			return StringUtil.replaceFirst(
				content, "\"version\": \"" + version + "\"",
				"\"version\": \"" + bndReleaseVersion + "\"");
		}

		return content;
	}

}