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

import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JSPUnusedJSPF extends BaseJSPTermsCheck {

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