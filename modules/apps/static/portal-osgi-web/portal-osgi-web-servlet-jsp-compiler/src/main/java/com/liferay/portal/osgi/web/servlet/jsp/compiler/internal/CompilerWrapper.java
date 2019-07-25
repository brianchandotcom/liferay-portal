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
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.URLUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

		ObjectValuePair<Long, byte[]> objectValuePair = _bytes.get(className);

		if (objectValuePair != null) {
			JspRuntimeContext jspRuntimeContext = ctxt.getRuntimeContext();

			jspRuntimeContext.setBytecode(
				className, objectValuePair.getValue());

			return;
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

		URL url = classLoader.getResource(classNamePath);

		if (url == null) {
			return super.isOutDated();
		}

		try {
			long lastModified = URLUtil.getLastModifiedTime(url);

			ObjectValuePair<Long, byte[]> objectValuePair = _bytes.get(
				className);

			if (objectValuePair != null) {
				if (lastModified <= objectValuePair.getKey()) {
					return false;
				}
			}

			URLConnection urlConnection = url.openConnection();

			try (InputStream inputStream = urlConnection.getInputStream()) {
				try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
						new UnsyncByteArrayOutputStream()) {

					StreamUtil.transfer(
						inputStream, unsyncByteArrayOutputStream, false);

					byte[] bytes = unsyncByteArrayOutputStream.toByteArray();

					_bytes.put(
						className, new ObjectValuePair<>(lastModified, bytes));

					return true;
				}
			}
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to determine if " + className + " is outdated", ioe);
		}

		return super.isOutDated();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompilerWrapper.class);

	private final Map<String, ObjectValuePair<Long, byte[]>> _bytes =
		new ConcurrentHashMap<>();

}