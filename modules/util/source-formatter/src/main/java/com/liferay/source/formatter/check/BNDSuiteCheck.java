/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.util.List;

/**
 * @author Peter Shin
 */
public class BNDSuiteCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws ReflectiveOperationException {

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

		if (content.matches("(?s).*Liferay-Releng-Deprecated:\\s*true.*")) {
			content = BNDSourceUtil.updateInstruction(
				content, "Liferay-Releng-Suite", StringPool.BLANK);

			if ((ReleaseInfo.getBuildNumber() >=
					ReleaseInfo.RELEASE_7_1_0_BUILD_NUMBER) &&
				content.matches(
					"(?s).*Liferay-Releng-Marketplace:\\s*false.*")) {

				addMessage(
					fileName,
					"Deprecated apps that are not published on Marketplace " +
						"should be moved to the archived folder");
			}
		}

		List<String> allowedLiferayRelengSuiteNames = getAttributeValues(
			_ALLOWED_LIFERAY_RELENG_SUITE_NAMES_KEY, absolutePath);

		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			if (!line.contains("Liferay-Releng-Suite")) {
				continue;
			}

			String s = StringUtil.removeSubstring(
				line, "Liferay-Releng-Suite:");

			String value = StringUtil.toLowerCase(s.trim());

			if (Validator.isNull(value)) {
				content = BNDSourceUtil.updateInstruction(
					content, "Liferay-Releng-Fix-Delivery-Method",
					StringPool.BLANK);
				content = BNDSourceUtil.updateInstruction(
					content, "Liferay-Releng-Portal-Required", "false");

				continue;
			}

			if (!allowedLiferayRelengSuiteNames.isEmpty() &&
				!allowedLiferayRelengSuiteNames.contains(value)) {

				String message = StringBundler.concat(
					"The 'Liferay-Releng-Suite' can be blank or one of the ",
					"following values '",
					StringUtil.merge(allowedLiferayRelengSuiteNames, ", "),
					"'");

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

	private static final String _ALLOWED_FILE_NAMES_KEY = "allowedFileNames";

	private static final String _ALLOWED_LIFERAY_RELENG_SUITE_NAMES_KEY =
		"allowedLiferayRelengSuiteNames";

}