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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import java.text.DateFormat;

import java.util.Date;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class ReleaseInfoBuilder {

	public static void main(String[] args) {
		new ReleaseInfoBuilder();
	}

	public ReleaseInfoBuilder() {
		try {

			// Get version

			Properties releaseProps = _fileImpl.toProperties(
				"../release.properties");

			String version = releaseProps.getProperty("lp.version");

			File file = new File(
				"../portal-kernel/src/com/liferay/portal/kernel/util" +
					"/ReleaseInfo.java");

			String content = _fileImpl.read(file);

			int x = content.indexOf("String _VERSION = \"");

			x = content.indexOf("\"", x) + 1;

			int y = content.indexOf("\"", x);

			content = content.substring(0, x) + version + content.substring(y);

			// Get build

			x = content.indexOf("String _BUILD = \"");
			x = content.indexOf("\"", x) + 1;

			y = content.indexOf("\"", x);

			int build = GetterUtil.getInteger(content.substring(x, y)) + 1;

			content = content.substring(0, x) + build + content.substring(y);

			// Get date

			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

			String date = dateFormat.format(new Date());

			x = content.indexOf("String _DATE = \"");
			x = content.indexOf("\"", x) + 1;

			y = content.indexOf("\"", x);

			content = content.substring(0, x) + date + content.substring(y);

			// Update ReleaseInfo.java

			_fileImpl.write(file, content);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final FileImpl _fileImpl = FileImpl.getInstance();

}