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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class BNDBundleCheck extends BaseFileCheck {

	public void setAllowedFileNames(String allowedFileNames) {
		Collections.addAll(
			_allowedFileNames, StringUtil.split(allowedFileNames));
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!absolutePath.endsWith("/app.bnd")) {
			return content;
		}

		for (String allowedFileName : _allowedFileNames) {
			if (absolutePath.endsWith(allowedFileName)) {
				return content;
			}
		}

		if (!content.matches(
				"(?s).*Liferay-Releng-App-Title: " +
					Pattern.quote("${liferay.releng.app.title.prefix}") +
						" \\S+.*")) {

			String appTitle = _getAppTitle(absolutePath);

			content = _updateInstruction(
				content, "Liferay-Releng-App-Title",
				"${liferay.releng.app.title.prefix} " + appTitle);
		}

		if (content.matches("(?s).*Liferay-Releng-Deprecated:\\s*true.*")) {
			content = _updateInstruction(
				content, "Liferay-Releng-Bundle", "false");
			content = _updateInstruction(
				content, "Liferay-Releng-Suite", StringPool.BLANK);
		}

		content = _updateInstruction(
			content, "Liferay-Releng-Public", "${liferay.releng.public}");
		content = _updateInstruction(
			content, "Liferay-Releng-Restart-Required", "true");
		content = _updateInstruction(
			content, "Liferay-Releng-Support-Url", "http://www.liferay.com");
		content = _updateInstruction(
			content, "Liferay-Releng-Supported", "${liferay.releng.supported}");

		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			if (!line.contains("Liferay-Releng-Suite")) {
				continue;
			}

			String s = StringUtil.replace(
				line, "Liferay-Releng-Suite:", StringPool.BLANK);

			String value = StringUtil.toLowerCase(s.trim());

			if (Validator.isNull(value)) {
				content = _updateInstruction(
					content, "Liferay-Releng-Bundle", "false");
				content = _updateInstruction(
					content, "Liferay-Releng-Fix-Delivery-Method",
					StringPool.BLANK);
				content = _updateInstruction(
					content, "Liferay-Releng-Portal-Required", "false");

				continue;
			}

			if (!ArrayUtil.contains(_SUITES, value)) {
				String message = StringBundler.concat(
					"The 'Liferay-Releng-Suite' can be blank or one of the ",
					"following values ", StringUtil.merge(_SUITES, ", "));

				addMessage(fileName, message);

				continue;
			}

			content = _updateInstruction(
				content, "Liferay-Releng-Bundle", "true");
			content = _updateInstruction(
				content, "Liferay-Releng-Fix-Delivery-Method", "core");
			content = _updateInstruction(
				content, "Liferay-Releng-Marketplace", "true");
			content = _updateInstruction(
				content, "Liferay-Releng-Portal-Required", "true");
			content = _updateInstruction(
				content, "Liferay-Releng-Suite", value);
		}

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

	private String _updateInstruction(
		String content, String header, String value) {

		String instruction = header + StringPool.COLON;

		if (Validator.isNotNull(value)) {
			instruction = instruction + StringPool.SPACE + value;
		}

		if (!content.contains(header)) {
			return content + StringPool.NEW_LINE + instruction;
		}

		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			if (line.contains(header)) {
				content = StringUtil.replaceFirst(content, line, instruction);
			}
		}

		return content;
	}

	private static final String[] _REQUIRED_INSTRUCTIONS = {
		"Liferay-Releng-App-Description", "Liferay-Releng-App-Title",
		"Liferay-Releng-Bundle", "Liferay-Releng-Category",
		"Liferay-Releng-Demo-Url", "Liferay-Releng-Deprecated",
		"Liferay-Releng-Fix-Delivery-Method", "Liferay-Releng-Labs",
		"Liferay-Releng-Marketplace", "Liferay-Releng-Portal-Required",
		"Liferay-Releng-Public", "Liferay-Releng-Restart-Required",
		"Liferay-Releng-Suite", "Liferay-Releng-Support-Url",
		"Liferay-Releng-Supported"
	};

	private static final String[] _SUITES = {
		"collaboration", "forms-and-workflow", "foundation", "static",
		"web-experience"
	};

	private final List<String> _allowedFileNames = new ArrayList<>();

}