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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.osgi.web.servlet.jsp.compiler.test.servlet.PrecompileTestServlet;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import javax.portlet.Portlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class JspPrecompileTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_bundle = FrameworkUtil.getBundle(JspPrecompileTest.class);

		BundleContext bundleContext = _bundle.getBundleContext();

		_bundle = bundleContext.installBundle(
			JspPrecompilePortlet.PORTLET_NAME, _createTestBundle());

		_bundle.start();

		StringBundler sb = new StringBundler(5);

		sb.append(PropsValues.LIFERAY_HOME);
		sb.append("/work/");
		sb.append(_bundle.getSymbolicName());
		sb.append(StringPool.DASH);
		sb.append(_bundle.getVersion());

		_parentWorkDir = sb.toString();

		File file = new File(_parentWorkDir);

		file.mkdir();
	}

	@AfterClass
	public static void tearDownClass() throws BundleException {
		FileUtil.deltree(_parentWorkDir);

		_bundle.uninstall();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(_group);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		String columnId = columns.get(0);

		layoutTypePortlet.addPortletId(
			TestPropsValues.getUserId(), JspPrecompilePortlet.PORTLET_NAME,
			columnId, -1, false);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	@After
	public void tearDown() throws PortalException {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testPrecompiledJsp() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append(_parentWorkDir);
		sb.append(StringPool.SLASH);
		sb.append(_JSP_PATH);
		sb.append(_PRECOMPILE_JSP_CLASS);

		File file = new File(sb.toString());

		File parentFile = file.getParentFile();

		parentFile.mkdirs();

		file.createNewFile();

		try (InputStream inputStream =
				PrecompileTestServlet.class.getResourceAsStream(
					PrecompileTestServlet.class.getSimpleName() + ".class");
			OutputStream outputStream = new FileOutputStream(file)) {

			ClassReader classReader = new ClassReader(inputStream);

			ClassNode classNode = new ClassNode();

			classReader.accept(classNode, 0);

			classNode.name = _JSP_PATH.concat(
				_PRECOMPILE_JSP_CLASS.substring(
					0, _PRECOMPILE_JSP_CLASS.indexOf(StringPool.PERIOD)));

			ClassWriter classWriter = new ClassWriter(0);

			classNode.accept(classWriter);

			outputStream.write(classWriter.toByteArray());
		}

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_JSP_COMPILER_CLASS_NAME, Level.DEBUG)) {

			_doJspTest(_PRECOMPILE_JSP);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			sb.setIndex(0);

			sb.append("Compiling JSP: ");
			sb.append(_JSP_PACKAGE);
			sb.append(
				StringUtil.replace(
					_PRECOMPILE_JSP, CharPool.PERIOD, CharPool.UNDERLINE));

			Assert.assertFalse(
				"JSP was compiled at runtime",
				_containsLog(loggingEvents, sb.toString()));
		}
	}

	@Test
	public void testRuntimeCompiledJsp() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_JSP_COMPILER_CLASS_NAME, Level.DEBUG)) {

			_doJspTest(_RUNTIME_COMPILE_JSP);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			StringBundler sb = new StringBundler(3);

			sb.append("Compiling JSP: ");
			sb.append(_JSP_PACKAGE);
			sb.append(
				StringUtil.replace(
					_RUNTIME_COMPILE_JSP, CharPool.PERIOD, CharPool.UNDERLINE));

			Assert.assertTrue(
				"No JSP was compiled at runtime",
				_containsLog(loggingEvents, sb.toString()));
		}
	}

	private static String _buildImportPackage(Class<?>... classes) {
		if (ArrayUtil.isEmpty(classes)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(classes.length * 2);

		Set<Package> packages = new HashSet<>();

		for (Class<?> clazz : classes) {
			Package pkg = clazz.getPackage();

			if (packages.add(pkg)) {
				sb.append(pkg.getName());
				sb.append(StringPool.COMMA);
			}
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static InputStream _createTestBundle() throws IOException {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(
					Constants.BUNDLE_ACTIVATOR,
					JspPrecompileBundleActivator.class.getName());
				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");

				Package pkg = JspPrecompileTest.class.getPackage();

				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME, pkg.getName() + ".bundle");
				attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
				attributes.putValue(
					Constants.IMPORT_PACKAGE,
					_buildImportPackage(
						BundleActivator.class, HttpServletRequest.class,
						MVCPortlet.class, PortalUtil.class, Portlet.class));
				attributes.putValue("Manifest-Version", "2");

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();

				_writeClasses(
					jarOutputStream, JspPrecompileBundleActivator.class,
					JspPrecompilePortlet.class);

				ClassLoader classLoader =
					JspPrecompileTest.class.getClassLoader();

				String path =
					"META-INF/resources/".concat(_RUNTIME_COMPILE_JSP);

				jarOutputStream.putNextEntry(new ZipEntry(path));

				try (InputStream inputStream = classLoader.getResourceAsStream(
						path);
					OutputStream outputStream = StreamUtil.uncloseable(
						jarOutputStream)) {

					StreamUtil.transfer(inputStream, outputStream);
				}

				jarOutputStream.closeEntry();

				jarOutputStream.putNextEntry(
					new ZipEntry(
						"META-INF/resources/".concat(_PRECOMPILE_JSP)));

				jarOutputStream.closeEntry();
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private static void _writeClasses(
			JarOutputStream jarOutputStream, Class<?>... classes)
		throws IOException {

		ClassLoader classLoader = JspPrecompileTest.class.getClassLoader();

		for (Class<?> clazz : classes) {
			String className = clazz.getName();

			String path = StringUtil.replace(
				className, CharPool.PERIOD, CharPool.SLASH);

			String resourcePath = path.concat(".class");

			jarOutputStream.putNextEntry(new ZipEntry(resourcePath));

			try (InputStream inputStream = classLoader.getResourceAsStream(
					resourcePath);
				OutputStream outputStream = StreamUtil.uncloseable(
					jarOutputStream)) {

				StreamUtil.transfer(inputStream, outputStream);
			}

			jarOutputStream.closeEntry();
		}
	}

	private boolean _containsLog(
		List<LoggingEvent> loggingEvents, String expected) {

		for (LoggingEvent loggingEvent : loggingEvents) {
			String message = loggingEvent.getRenderedMessage();

			if (message.equals(expected)) {
				return true;
			}
		}

		return false;
	}

	private void _doJspTest(String jsp) throws IOException {
		StringBundler sb = new StringBundler(6);

		sb.append("http://localhost:8080/web");
		sb.append(_group.getFriendlyURL());
		sb.append(StringPool.QUESTION);
		sb.append(JspPrecompilePortlet.JSP_PARAMETER);
		sb.append("=/");
		sb.append(jsp);

		URL url = new URL(sb.toString());

		try (InputStream inputStream = url.openStream()) {
			if (jsp.equals(_RUNTIME_COMPILE_JSP)) {
				_verifyCompiledJsp(inputStream, "Runtime Compiled");
			}
			else if (jsp.equals(_PRECOMPILE_JSP)) {
				_verifyCompiledJsp(inputStream, "Precompiled");
			}
		}
	}

	private void _verifyCompiledJsp(InputStream inputStream, String string)
		throws IOException {

		try (InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(inputStreamReader)) {

			String line = null;

			while (true) {
				line = unsyncBufferedReader.readLine();

				Assert.assertNotNull(line);

				if (line.contains(string)) {
					return;
				}
			}
		}
	}

	private static final String _JSP_COMPILER_CLASS_NAME =
		"com.liferay.portal.osgi.web.servlet.jsp.compiler.internal.JspCompiler";

	private static final String _JSP_PACKAGE = "org.apache.jsp.";

	private static final String _JSP_PATH = StringUtil.replace(
		_JSP_PACKAGE, CharPool.PERIOD, CharPool.SLASH);

	private static final String _PRECOMPILE_JSP = "PrecompileTestServlet.jsp";

	private static final String _PRECOMPILE_JSP_CLASS =
		"PrecompileTestServlet_jsp.class";

	private static final String _RUNTIME_COMPILE_JSP = "runtime.jsp";

	private static Bundle _bundle;
	private static String _parentWorkDir;

	private Group _group;

}