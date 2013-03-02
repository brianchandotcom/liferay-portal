/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.kernel.util.FileUtil;

/**
 * @author Michael Hashimoto
 */
public class FunctionConverter extends BaseConverter {

	public FunctionConverter(SeleniumBuilderContext seleniumBuilderContext) {
		_seleniumBuilderContext = seleniumBuilderContext;
	}

	public void generateFunction(String functionName) throws Exception {
		String baseDir = _getBaseDir();
		String functionFileName = _getFunctionFileName(functionName);

		if (!FileUtil.exists(baseDir + "/" + functionFileName)) {
			return;
		}
	}

	private String _getBaseDir() {
		return _seleniumBuilderContext.getBaseDir();
	}

	private String _getFunctionFileName(String functionName) {
		return _seleniumBuilderContext.getFunctionFileName(functionName);
	}

	private SeleniumBuilderContext _seleniumBuilderContext;

}