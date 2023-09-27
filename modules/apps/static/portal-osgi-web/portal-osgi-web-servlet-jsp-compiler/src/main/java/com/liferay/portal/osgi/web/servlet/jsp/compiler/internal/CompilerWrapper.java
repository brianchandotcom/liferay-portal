/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

import org.apache.jasper.EmbeddedServletOptions;
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
		catch (IOException ioException) {
			_log.error(
				"Unable to determine if " + className + " is outdated",
				ioException);
		}

		return super.isOutDated();
	}

	private URL _getClassURL(String className) {
		String classNamePath = StringUtil.replace(
			className, CharPool.PERIOD, File.separatorChar);

		classNamePath = classNamePath.concat(".class");

		Options options = ctxt.getOptions();
		URL url = null;

		if (PropsValues.WORK_DIR_OVERRIDE_ENABLED) {
			File classFile = new File(options.getScratchDir(), classNamePath);

			if (classFile.exists()) {
				URI uri = classFile.toURI();

				try {
					url = uri.toURL();
				}
				catch (MalformedURLException malformedURLException) {
					if (_log.isWarnEnabled()) {
						_log.warn(malformedURLException);
					}
				}
			}
		}

		if (url == null) {
			EmbeddedServletOptions embeddedServletOptions =
				(EmbeddedServletOptions)options;

			if (Boolean.valueOf(
					embeddedServletOptions.getProperty("hasFragment"))) {

				return null;
			}

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
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
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