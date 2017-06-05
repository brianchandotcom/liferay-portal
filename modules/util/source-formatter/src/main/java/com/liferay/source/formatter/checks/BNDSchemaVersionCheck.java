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

import com.liferay.portal.kernel.util.CharPool;

import java.io.File;

/**
 * @author Hugo Huijser
 */
public class BNDSchemaVersionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkMissingSchemaVersion(fileName, absolutePath, content);

		// LPS-61288

		if (fileName.endsWith("-web/bnd.bnd") &&
			content.contains("Liferay-Require-SchemaVersion: 1.0.0")) {

			addMessage(
				fileName,
				"Do not include the header Liferay-Require-SchemaVersion in " +
					"web modules",
				"bnd_schema_version.markdown");
		}

		return content;
	}

	private void _checkMissingSchemaVersion(
		String fileName, String absolutePath, String content) {

		if (content.contains("Liferay-Require-SchemaVersion:") ||
			!content.contains("Liferay-Service: true")) {

			return;
		}

		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		File serviceXMLfile = new File(
			absolutePath.substring(0, pos + 1) + "service.xml");

		if (serviceXMLfile.exists()) {
			addMessage(
				fileName, "Missing 'Liferay-Require-SchemaVersion'",
				"bnd_schema_version.markdown");
		}
	}

}