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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.compiler.Compiler;
import org.apache.jasper.servlet.JspServletWrapper;

/**
 * @author Matthew Tambara
 */
public class CompilerWrapper extends Compiler {

	public CompilerWrapper(
			JspCompilationContext jspCompilationContext,
			JspServletWrapper jspServletWrapper, boolean jspcMode)
		throws JasperException {

		super(jspCompilationContext, jspServletWrapper, jspcMode);
	}

}