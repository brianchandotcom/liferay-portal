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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

		if (!absolutePath.endsWith("/app.bnd") ||
			!content.matches("(?s).*Liferay-Releng-Bundle:\\s*true.*")) {

			return content;
		}

		for (String allowedFileName : _allowedFileNames) {
			if (absolutePath.endsWith(allowedFileName)) {
				return content;
			}
		}

		if (!content.matches(
				"(?s).*Liferay-Releng-Fix-Delivery-Method:\\s*core.*")) {

			String message = StringBundler.concat(
				"If the 'app.bnd' file contains 'Liferay-Releng-Bundle: ",
				"true', it must have 'Liferay-Releng-Fix-Delivery-Method: ",
				"core'");

			addMessage(fileName, message);
		}

		if (!content.matches("(?s).*Liferay-Releng-Marketplace:\\s*true.*")) {
			String message = StringBundler.concat(
				"If the 'app.bnd' file contains 'Liferay-Releng-Bundle: ",
				"true', it must have 'Liferay-Releng-Marketplace: true'");

			addMessage(fileName, message);
		}

		if (!content.matches(
				"(?s).*Liferay-Releng-Portal-Required:\\s*true.*")) {

			String message = StringBundler.concat(
				"If the 'app.bnd' file contains 'Liferay-Releng-Bundle: ",
				"true', it must have 'Liferay-Releng-Portal-Required: true'");

			addMessage(fileName, message);
		}

		if (!content.matches("(?s).*Liferay-Releng-Suite:[^\\S\\n]*\\S+.*")) {
			String message = StringBundler.concat(
				"If the 'app.bnd' file contains 'Liferay-Releng-Bundle: ",
				"true', it must define a 'Liferay-Releng-Suite'");

			addMessage(fileName, message);
		}

		return content;
	}

	private final List<String> _allowedFileNames = new ArrayList<>();

}