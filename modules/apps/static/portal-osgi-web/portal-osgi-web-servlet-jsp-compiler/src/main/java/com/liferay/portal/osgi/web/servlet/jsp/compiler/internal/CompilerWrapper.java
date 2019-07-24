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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;

import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.compiler.Compiler;
import org.apache.jasper.compiler.JspRuntimeContext;
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

	@Override
	public void compile(boolean compileClass) throws Exception {
		String className = ctxt.getFullClassName();

		String classNamePath = className.replace(
			CharPool.PERIOD, CharPool.SLASH);

		classNamePath = classNamePath.concat(".class");

		JspRuntimeContext jspRuntimeContext = ctxt.getRuntimeContext();

		ClassLoader classLoader = jspRuntimeContext.getParentClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				classNamePath)) {

			if (inputStream != null) {
				try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
						new UnsyncByteArrayOutputStream()) {

					StreamUtil.transfer(
						inputStream, unsyncByteArrayOutputStream, false);

					jspRuntimeContext.setBytecode(
						className, unsyncByteArrayOutputStream.toByteArray());

					return;
				}
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		super.compile(compileClass);
	}

	@Override
	public boolean isOutDated() {
		String className = ctxt.getFullClassName();

		String classNamePath = className.replace(
			CharPool.PERIOD, CharPool.SLASH);

		classNamePath = classNamePath.concat(".class");

		JspRuntimeContext jspRuntimeContext = ctxt.getRuntimeContext();

		ClassLoader classLoader = jspRuntimeContext.getParentClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				classNamePath)) {

			if (inputStream != null) {
				return false;
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return super.isOutDated();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompilerWrapper.class);

}