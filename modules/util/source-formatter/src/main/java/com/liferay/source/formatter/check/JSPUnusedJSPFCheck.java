/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.JSPSourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JSPUnusedJSPFCheck extends BaseJSPTermsCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith(".jspf")) {
			return content;
		}

		populateContentsMap(fileName, content);

		Set<String> referenceFileNames = JSPSourceUtil.getJSPReferenceFileNames(
			fileName, new ArrayList<>(), getContentsMap(), ".*\\.jspf");

		if (referenceFileNames.isEmpty()) {
			addMessage(fileName, "Unused .jspf file");
		}

		return content;
	}

}