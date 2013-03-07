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

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class BaseConverter {

	public BaseConverter(SeleniumBuilderContext seleniumBuilderContext) {
		_seleniumBuilderContext = seleniumBuilderContext;
	}

	protected Map<String, Object> getContext() {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put("seleniumBuilderContext", _seleniumBuilderContext);

		return context;
	}

	protected Element getFunctionRootElement(String functionName) {
		return _seleniumBuilderContext.getFunctionRootElement(functionName);
	}

	protected String processTemplate(String name) throws Exception {
		return processTemplate(name, getContext());
	}

	protected String processTemplate(String name, Map<String, Object> context)
		throws Exception {

		return StringUtil.strip(
			FreeMarkerUtil.process(_TPL_ROOT + name, context), '\r');
	}

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/seleniumbuilder/dependencies/";

	private SeleniumBuilderContext _seleniumBuilderContext;

}