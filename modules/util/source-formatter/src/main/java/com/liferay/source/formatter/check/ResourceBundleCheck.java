/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Hugo Huijser
 */
public class ResourceBundleCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath)) {
			return content;
		}

		int pos = content.indexOf("ResourceBundle.getBundle(");

		if (pos != -1) {
			addMessage(
				fileName,
				"Use ResourceBundleUtil.getBundle instead of " +
					"ResourceBundle.getBundle",
				getLineNumber(content, pos));
		}

		pos = content.indexOf("resourceBundle.getString(");

		if (pos != -1) {
			addMessage(
				fileName,
				"Use ResourceBundleUtil.getString instead of " +
					"resourceBundle.getString",
				getLineNumber(content, pos));
		}

		return content;
	}

}