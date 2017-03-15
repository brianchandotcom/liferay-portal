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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.Collections;

/**
 * @author Hugo Huijser
 */
public class JavaDataAccessConnectionCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		String packagePath = JavaSourceUtil.getPackagePath(content);

		if (packagePath.startsWith("com.liferay.portal.kernel.upgrade") ||
			packagePath.startsWith("com.liferay.portal.kernel.verify") ||
			packagePath.startsWith("com.liferay.portal.upgrade") ||
			packagePath.startsWith("com.liferay.portal.verify")) {

			content = StringUtil.replace(
				content, "DataAccess.getConnection",
				"DataAccess.getUpgradeOptimizedConnection");
		}

		return new Tuple(content, Collections.emptySet());
	}

}