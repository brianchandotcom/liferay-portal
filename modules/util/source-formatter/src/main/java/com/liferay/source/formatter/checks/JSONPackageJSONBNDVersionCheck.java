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