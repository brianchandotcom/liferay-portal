/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

/**
 * @author Alan Huang
 */
public class JSPJakartaTransformCheck extends BaseJakartaTransformCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith(".jsp") && !fileName.endsWith(".jspf") &&
			!fileName.endsWith(".jspx")) {

			return content;
		}

		content = replace(content);

		return replaceTaglibURIs(content);
	}

}