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

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

/**
 * @author Nícolas Moura
 */
public class UpgradeBNDIncludeResourceCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/bnd.bnd")) {
			return content;
		}

		String definition = BNDSourceUtil.getDefinition(
			content, "-includeresource");

		if (definition == null) {
			definition = BNDSourceUtil.getDefinition(
				content, "Include-Resource");
		}

		return StringUtil.removeSubstring(content, definition);
	}

}