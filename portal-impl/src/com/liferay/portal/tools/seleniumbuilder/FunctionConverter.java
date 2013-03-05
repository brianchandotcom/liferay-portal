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

import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Michael Hashimoto
 */
public class FunctionConverter extends BaseConverter {

	public FunctionConverter(SeleniumBuilderContext seleniumBuilderContext) {
		_seleniumBuilderContext = seleniumBuilderContext;
	}

	public void convert(String functionName) {
		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(_getFunctionPackageName(functionName));
		sb.append(";\n\n");

		sb.append("import com.liferay.portalweb.portal.util.liferayselenium.");
		sb.append("LiferaySelenium;\n");

		sb.append("public class ");
		sb.append(_getFunctionSimpleClassName(functionName));
		sb.append(" extends BaseFunctions {\n\n");

		sb.append("public ");
		sb.append(_getFunctionSimpleClassName(functionName));
		sb.append("(LiferaySelenium liferaySelenium) {\n");

		sb.append("super(liferaySelenium);\n");

		sb.append("}\n");

		sb.append("}");
	}

	private String _getFunctionPackageName(String functionName) {
		return _seleniumBuilderContext.getFunctionPackageName(functionName);
	}

	private String _getFunctionSimpleClassName(String functionName) {
		return _seleniumBuilderContext.getFunctionSimpleClassName(functionName);
	}

	private SeleniumBuilderContext _seleniumBuilderContext;

}