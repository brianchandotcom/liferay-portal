/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.util.Objects;

/**
 * @author Kyle Miho
 */
public class UpgradeBNDDeclarativeServicesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/bnd.bnd") ||
			!Objects.equals(
				BNDSourceUtil.getDefinition(content, "Liferay-Service"),
				"Liferay-Service: true")) {

			return content;
		}

		if (!Objects.equals(
				BNDSourceUtil.getDefinitionValue(
					content, "-dsannotations-options"),
				"inherit")) {

			return BNDSourceUtil.updateInstruction(
				content, "-dsannotations-options", "inherit");
		}

		return content;
	}

}