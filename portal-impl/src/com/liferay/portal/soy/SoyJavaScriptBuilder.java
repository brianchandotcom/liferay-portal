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

package com.liferay.portal.soy;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.jssrc.SoyJsSrcOptions;
import com.google.template.soy.jssrc.SoyJsSrcOptions.CodeStyle;

import java.security.PrivilegedActionException;

import java.util.List;

/**
 * @author Bruno Basto
 */

public class SoyJavaScriptBuilder {

	public static List<String> build(SoyTemplate template)
		throws PrivilegedActionException {

		SoyFileSet soyFileSet = template.getSoyFileSet();

		SoyJsSrcOptions jsSrcOptions = new SoyJsSrcOptions();

		jsSrcOptions.setCodeStyle(CodeStyle.STRINGBUILDER);
		jsSrcOptions.setShouldProvideRequireSoyNamespaces(false);
		jsSrcOptions.setShouldDeclareTopLevelNamespaces(true);
		jsSrcOptions.setBidiGlobalDir(0);

		return soyFileSet.compileToJsSrc(jsSrcOptions, null);
	}

}