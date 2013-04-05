/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class BaseConverter {

	public BaseConverter(SeleniumBuilderContext seleniumBuilderContext) {
		this.seleniumBuilderContext = seleniumBuilderContext;

		this.seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(
			seleniumBuilderContext.getBaseDir());
	}

	protected Map<String, Object> getContext() {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put("seleniumBuilderContext", seleniumBuilderContext);
		context.put("seleniumBuilderFileUtil", seleniumBuilderFileUtil);

		return context;
	}

	protected String processTemplate(String name) throws Exception {
		return processTemplate(name, getContext());
	}

	protected String processTemplate(String name, Map<String, Object> context)
		throws Exception {

		return seleniumBuilderFileUtil.processTemplate(name, context);
	}

	protected SeleniumBuilderContext seleniumBuilderContext;
	protected SeleniumBuilderFileUtil seleniumBuilderFileUtil;

}