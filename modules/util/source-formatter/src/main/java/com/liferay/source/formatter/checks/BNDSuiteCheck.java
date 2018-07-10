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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Peter Shin
 */
public class BNDSuiteCheck extends BaseFileCheck {

	public void setAllowedFileNames(String allowedFileNames) {
		Collections.addAll(
			_allowedFileNames, StringUtil.split(allowedFileNames));
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/app.bnd")) {
			return content;
		}

		for (String allowedFileName : _allowedFileNames) {
			if (absolutePath.endsWith(allowedFileName)) {
				return content;
			}
		}

		if (content.matches("(?s).*Liferay-Releng-Deprecated:\\s*true.*")) {
			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-Suite", StringPool.BLANK);
		}

		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			if (!line.contains("Liferay-Releng-Suite")) {
				continue;
			}

			String s = StringUtil.replace(
				line, "Liferay-Releng-Suite:", StringPool.BLANK);

			String value = StringUtil.toLowerCase(s.trim());

			if (Validator.isNull(value)) {
				content = BNDSourceUtil.updateInstruction(
					content, "Liferay-Releng-Fix-Delivery-Method",
					StringPool.BLANK);
				content = BNDSourceUtil.updateInstruction(
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

			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-Bundle", "true");
			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-Fix-Delivery-Method", "core");
			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-Marketplace", "true");
			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-Portal-Required", "true");
			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-Suite", value);
		}

		if (!content.contains("Liferay-Releng-Suite:")) {
			content = content + "\nLiferay-Releng-Suite:";
		}

		return content;
	}

	private static final String[] _SUITES = {
		"collaboration", "forms-and-workflow", "foundation", "static",
		"web-experience"
	};

	private final List<String> _allowedFileNames = new ArrayList<>();

}