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