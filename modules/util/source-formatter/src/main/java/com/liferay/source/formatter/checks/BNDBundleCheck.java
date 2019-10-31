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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class BNDBundleCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/app.bnd")) {
			return content;
		}

		List<String> allowedFileNames = getAttributeValues(
			_ALLOWED_FILE_NAMES_KEY, absolutePath);

		for (String allowedFileName : allowedFileNames) {
			if (absolutePath.endsWith(allowedFileName)) {
				return content;
			}
		}

		if (!content.matches(
				"(?s).*Liferay-Releng-App-Title: " +
					Pattern.quote("${liferay.releng.app.title.prefix}") +
						" \\S+.*")) {

			String appTitle = _getAppTitle(absolutePath);

			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-App-Title",
				"${liferay.releng.app.title.prefix} " + appTitle);
		}

		if (content.matches("(?s).*Liferay-Releng-Deprecated:\\s*true.*")) {
			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-Bundle", "false");
		}

		content = BNDSourceUtil.updateInstruction(
			content, "Liferay-Releng-Public", "${liferay.releng.public}");
		content = BNDSourceUtil.updateInstruction(
			content, "Liferay-Releng-Restart-Required", "true");
		content = BNDSourceUtil.updateInstruction(
			content, "Liferay-Releng-Support-Url", "http://www.liferay.com");
		content = BNDSourceUtil.updateInstruction(
			content, "Liferay-Releng-Supported", "${liferay.releng.supported}");

		for (String instruction : _REQUIRED_INSTRUCTIONS) {
			if (!content.contains(instruction + ":")) {
				content = StringBundler.concat(content, "\n", instruction, ":");
			}
		}

		return content;
	}

	private String _getAppTitle(String absolutePath) {
		int pos = absolutePath.lastIndexOf(StringPool.SLASH);

		if (pos == -1) {
			return StringPool.BLANK;
		}

		String dirName = absolutePath.substring(0, pos);

		pos = dirName.lastIndexOf(StringPool.SLASH);

		if (pos == -1) {
			return StringPool.BLANK;
		}

		String shortDirName = dirName.substring(pos + 1);

		if (shortDirName.startsWith("com-liferay-")) {
			shortDirName = StringUtil.replaceFirst(
				shortDirName, "com-liferay-", StringPool.BLANK);
		}

		return TextFormatter.format(shortDirName, TextFormatter.J);
	}

	private static final String _ALLOWED_FILE_NAMES_KEY = "allowedFileNames";

	private static final String[] _REQUIRED_INSTRUCTIONS = {
		"Liferay-Releng-App-Description", "Liferay-Releng-App-Title",
		"Liferay-Releng-Bundle", "Liferay-Releng-Category",
		"Liferay-Releng-Demo-Url", "Liferay-Releng-Deprecated",
		"Liferay-Releng-Fix-Delivery-Method", "Liferay-Releng-Labs",
		"Liferay-Releng-Marketplace", "Liferay-Releng-Portal-Required",
		"Liferay-Releng-Public", "Liferay-Releng-Restart-Required",
		"Liferay-Releng-Support-Url", "Liferay-Releng-Supported"
	};

}