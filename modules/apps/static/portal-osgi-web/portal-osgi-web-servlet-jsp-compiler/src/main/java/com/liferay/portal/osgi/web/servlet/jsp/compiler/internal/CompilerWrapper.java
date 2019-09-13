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
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.Options;
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

		JSPClassInfo jspClassInfo = _jspClassInfos.get(className);

		if (jspClassInfo != null) {
			JspRuntimeContext jspRuntimeContext = ctxt.getRuntimeContext();

			jspRuntimeContext.setBytecode(className, jspClassInfo.getBytes());

			return;
		}

		super.compile(compileClass);
	}

	@Override
	public boolean isOutDated() {
		String className = ctxt.getFullClassName();

		URL url = _getClassURL(className);

		if (url == null) {
			return super.isOutDated();
		}

		try {
			long lastModified = URLUtil.getLastModifiedTime(url);

			JSPClassInfo jSPClassInfo = _jspClassInfos.get(className);

			if ((jSPClassInfo != null) &&
				(lastModified <= jSPClassInfo.getLastModified())) {

				return false;
			}

			URLConnection urlConnection = url.openConnection();

			try (InputStream inputStream = urlConnection.getInputStream();
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream()) {

				StreamUtil.transfer(
					inputStream, unsyncByteArrayOutputStream, false);

				byte[] bytes = unsyncByteArrayOutputStream.toByteArray();

				String protocol = url.getProtocol();

				_jspClassInfos.put(
					className,
					new JSPClassInfo(
						bytes, protocol.equals("file"), lastModified));

				return true;
			}
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to determine if " + className + " is outdated", ioe);
		}

		return super.isOutDated();
	}

	private URL _getClassURL(String className) {
		String classNamePath = className.replace(
			CharPool.PERIOD, File.separatorChar);

		classNamePath = classNamePath.concat(".class");

		URL url = null;

		if (PropsValues.WORK_DIR_OVERRIDE_ENABLED) {
			Options options = ctxt.getOptions();

			File scratchDir = options.getScratchDir();

			File classFile = new File(scratchDir, classNamePath);

			if (classFile.exists()) {
				URI uri = classFile.toURI();

				try {
					url = uri.toURL();
				}
				catch (MalformedURLException murle) {
					if (_log.isWarnEnabled()) {
						_log.warn(murle, murle);
					}
				}
			}
		}

		if (url == null) {
			JSPClassInfo jspClassInfo = _jspClassInfos.get(className);

			if ((jspClassInfo != null) && jspClassInfo.isOverride()) {
				_jspClassInfos.remove(className);
			}

			JspRuntimeContext jspRuntimeContext = ctxt.getRuntimeContext();

			ClassLoader classLoader = jspRuntimeContext.getParentClassLoader();

			try {
				Enumeration<URL> enumeration = classLoader.getResources(
					"/META-INF/resources" + ctxt.getJspFile());

				if (enumeration.hasMoreElements()) {
					enumeration.nextElement();

					if (enumeration.hasMoreElements()) {
						return null;
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			url = classLoader.getResource(classNamePath);
		}

		return url;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompilerWrapper.class);

	private final Map<String, JSPClassInfo> _jspClassInfos =
		new ConcurrentHashMap<>();

	private class JSPClassInfo {

		public byte[] getBytes() {
			return _bytes;
		}

		public long getLastModified() {
			return _lastModified;
		}

		public boolean isOverride() {
			return _override;
		}

		private JSPClassInfo(
			byte[] bytes, boolean override, long lastModified) {

			_bytes = bytes;
			_override = override;
			_lastModified = lastModified;
		}

		private final byte[] _bytes;
		private final long _lastModified;
		private final boolean _override;

	}

}