/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Hugo Huijser
 */
public class JSPModuleIllegalImportsCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (content.contains("import=\"com.liferay.registry.Registry")) {
			addMessage(
				fileName,
				"Do not use com.liferay.registry.Registry in modules");
		}

		if (content.contains("import=\"com.liferay.util.ContentUtil")) {
			addMessage(
				fileName, "Do not use com.liferay.util.ContentUtil in modules");
		}

		return content;
	}

}