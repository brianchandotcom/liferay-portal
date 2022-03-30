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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Hugo Huijser
 */
public class ClassUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		URL.setURLStreamHandlerFactory(
			protocol -> {
				if (protocol.equals("vfs") || protocol.equals("zip") ||
					protocol.equals("bundleresource") ||
					protocol.equals("wsjar")) {

					return new URLStreamHandler() {

						protected URLConnection openConnection(URL url) {
							return new URLConnection(url) {

								public void connect() {
									throw new UnsupportedOperationException(
										"protocol not supported");
								}

							};
						}

					};
				}

				return null;
			});
	}

	@Test
	public void testConstructor() {
		new ClassUtil();
	}

	@Test
	public void testGetClasses() throws Exception {
		_testGetClasses("@Annotation", "Annotation");
		_testGetClasses("@AnnotationClass.Annotation", "AnnotationClass");
		_testGetClasses(" @Annotation", "Annotation");
		_testGetClasses("@Annotation({A}", "A", "Annotation");
		_testGetClasses("@Annotation({A})", "A", "Annotation");
		_testGetClasses("@Annotation({A\n}))", "A", "Annotation");
		_testGetClasses("@Annotation({A.class})", "A", "Annotation");
		_testGetClasses("@Annotation({A.class\n})", "A", "Annotation");
		_testGetClasses(
			"@Annotation({A.class,\nB.class\n})", "A", "B", "Annotation");
		_testGetClasses(
			"@Annotation({A.class,B.class})", "A", "B", "Annotation");
		_testGetClasses(
			"@Annotation({A.class,B.class,C.class})", "A", "B", "C",
			"Annotation");
		_testGetClasses(
			"@AnnotationClass.Annotation({A.class})", "A", "AnnotationClass");
		_testGetClasses(
			"@AnnotationClass.Annotation({A.class,B.class})", "A", "B",
			"AnnotationClass");
		_testGetClasses(
			"@AnnotationClass.Annotation({A.class,B.class,C.class})", "A", "B",
			"C", "AnnotationClass");
		_testGetClasses("class A", "A");
		_testGetClasses("class \u00C0", "\u00C0");
		_testGetClasses("class 0");
		_testGetClasses("class \n");
		_testGetClasses("class A.B.C", "A", "C");
		_testGetClasses("enum A", "A");
		_testGetClasses("interface A", "A");
		_testGetClasses("@interface A", "A");
		_testGetClasses(" @%");
		_testGetClasses("A");
	}

	@Test
	public void testGetClassesWithFile() throws IOException {
		_testGetClassesWithFile("TestClass.java", null);
		_testGetClassesWithFile(
			"TestClass.java", "@Annotation({A.class,B.class} TestClass", "A",
			"B", "Annotation");
		_testGetClassesWithFile(
			"TestClass.txt", "@Annotation({A.class,B.class} TestClass", "A",
			"B", "Annotation", "TestClass");
	}

	@Test
	public void testGetClassName() {
		Assert.assertEquals(
			"java.lang.Object", ClassUtil.getClassName(new Object()));
		Assert.assertNull(ClassUtil.getClassName(null));
	}

	@Test
	public void testGetParentPath() {
		ClassLoader classLoader = ClassUtilTest.class.getClassLoader();

		String className = "java/lang/String.class";

		URL url = classLoader.getResource(className);

		URI uri = ReflectionTestUtil.invoke(
			ClassUtil.class, "_getPathURIFromURL", new Class<?>[] {URL.class},
			url);

		Path path = Paths.get(uri);

		String expectedParentPath = StringUtil.replace(
			path.toString(), CharPool.BACK_SLASH, CharPool.SLASH);

		int pos = expectedParentPath.indexOf(className);

		expectedParentPath = expectedParentPath.substring(0, pos);

		Assert.assertEquals(
			expectedParentPath,
			ClassUtil.getParentPath(classLoader, "java.lang.String.class"));
		Assert.assertEquals(
			expectedParentPath,
			ClassUtil.getParentPath(classLoader, "java.lang.String"));

		// Test log output

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				ClassUtil.class.getName(), Level.FINE)) {

			ClassUtil.getParentPath(classLoader, "java.lang.String");

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 3, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Class name java.lang.String", logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals("URI " + uri, logEntry.getMessage());

			logEntry = logEntries.get(2);

			Assert.assertEquals(
				"Parent path " + expectedParentPath, logEntry.getMessage());
		}
	}

	@Test
	public void testGetPathURIFromURL() throws Exception {

		// Tomcat

		_testGetPathURIFromURL("jar:file:", "jar:file:/");
		_testGetPathURIFromURL(
			new URL(
				"file:/opt/liferay/tomcat/classes/javax/servlet/Servlet.class"),
			"/opt/liferay/tomcat/classes/javax/servlet/Servlet.class");
		_testGetPathURIFromURL(
			new URL(
				"file:/C:/Liferay/tomcat/classes/javax/servlet/Servlet.class"),
			"/C:/Liferay/tomcat/classes/javax/servlet/Servlet.class");

		// Weblogic

		_testGetPathURIFromURL("zip:", "zip:");

		// Websphere

		_testGetPathURIFromURL("wsjar:file:", "wsjar:file:/");
		_testGetPathURIFromURL(
			new URL(
				"bundleresource://266.fwk-486185329/javax/servlet/Servlet." +
					"class"),
			"/javax/servlet/Servlet.class");

		// Wildfly

		_testGetPathURIFromURL("vfs:", "vfs:/");

		// logging

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				ClassUtil.class.getName(), Level.FINE)) {

			ReflectionTestUtil.invoke(
				ClassUtil.class, "_getPathURIFromURL",
				new Class<?>[] {URL.class},
				new URL(
					"jar:file:/opt/liferay/tomcat/lib/servlet-api.jar" +
						"!/javax/servlet/Servlet.class"));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"URI file:/opt/liferay/tomcat/lib/servlet-api.jar!/javax" +
					"/servlet/Servlet.class",
				logEntry.getMessage());
		}
	}

	@Test
	public void testGetPathURIFromURLWithIllegalCharacter() {
		try {
			ReflectionTestUtil.invoke(
				ClassUtil.class, "_getPathURIFromURL",
				new Class<?>[] {URL.class},
				new URL(
					"jar:file:/[opt/liferay/tomcat/lib/servlet-api.jar" +
						"!/javax/servlet/Servlet.class"));

			Assert.fail(
				"SystemException caused by URISyntaxException should be " +
					"thrown because of the illegal character '['");
		}
		catch (Exception exception) {
			Assert.assertSame(SystemException.class, exception.getClass());

			Throwable throwable = exception.getCause();

			Assert.assertSame(URISyntaxException.class, throwable.getClass());
		}
	}

	@Test
	public void testGetPathURIFromURLWithUnknownProtocol() {
		try {
			ReflectionTestUtil.invoke(
				ClassUtil.class, "_getPathURIFromURL",
				new Class<?>[] {URL.class},
				new URL(
					"jar", null, -1,
					"unknown:/opt/liferay/tomcat/lib/servlet-api.jar!/javax" +
						"/servlet/Servlet.class",
					null));

			Assert.fail(
				"SystemException caused by MalformedURLException should be " +
					"thrown because of the unknown protocol");
		}
		catch (Exception exception) {
			Assert.assertSame(SystemException.class, exception.getClass());

			Throwable throwable = exception.getCause();

			Assert.assertSame(
				MalformedURLException.class, throwable.getClass());
		}
	}

	@Test
	public void testIsSubclass() {
		Assert.assertTrue(
			"ArrayList should be considered sub class of itself",
			ClassUtil.isSubclass(ArrayList.class, ArrayList.class));
		Assert.assertFalse(
			"ArrayList should not be sub class of null",
			ClassUtil.isSubclass(ArrayList.class, (Class<?>)null));
		Assert.assertFalse(
			"null should not be sub class of any class or interface",
			ClassUtil.isSubclass(null, ArrayList.class));
		Assert.assertTrue(
			"ArrayList should be sub class of abstract class AbstractList",
			ClassUtil.isSubclass(ArrayList.class, AbstractList.class));
		Assert.assertTrue(
			"ArrayList should be sub class of interface List",
			ClassUtil.isSubclass(ArrayList.class, List.class));
		Assert.assertFalse(
			"ArrayList should be sub class of interface Set",
			ClassUtil.isSubclass(ArrayList.class, Set.class));

		Assert.assertTrue(
			"ArrayList should be considered sub class of itself",
			ClassUtil.isSubclass(ArrayList.class, ArrayList.class.getName()));
		Assert.assertFalse(
			"ArrayList should not be sub class of null",
			ClassUtil.isSubclass(ArrayList.class, (String)null));
		Assert.assertFalse(
			"null should not be sub class of any class or interface",
			ClassUtil.isSubclass(null, ArrayList.class.getName()));
		Assert.assertTrue(
			"ArrayList should be sub class of abstract class AbstractList",
			ClassUtil.isSubclass(
				ArrayList.class, AbstractList.class.getName()));
		Assert.assertTrue(
			"ArrayList should be sub class of interface List",
			ClassUtil.isSubclass(ArrayList.class, List.class.getName()));
		Assert.assertFalse(
			"ArrayList should be sub class of interface Set",
			ClassUtil.isSubclass(ArrayList.class, Set.class.getName()));
	}

	private void _testGetClasses(String content, String... expectedClasses)
		throws Exception {

		Set<String> expectedClassNames = new HashSet<>();

		Collections.addAll(expectedClassNames, expectedClasses);

		Set<String> actualClassNames = ClassUtil.getClasses(
			new StringReader(content), null);

		Assert.assertEquals(expectedClassNames, actualClassNames);
	}

	private void _testGetClassesWithFile(
			String fileName, String fileContent, String... expectedClasses)
		throws IOException {

		Path tempDirPath = Files.createTempDirectory(
			ClassUtilTest.class.getName());

		File file = new File(tempDirPath.toFile(), fileName);

		if (fileContent == null) {
			file.createNewFile();
		}
		else {
			Files.write(file.toPath(), Collections.singleton(fileContent));
		}

		Set<String> expectedClassNames = new HashSet<>();

		if (expectedClasses != null) {
			Collections.addAll(expectedClassNames, expectedClasses);
		}

		Set<String> actualClassNames = ClassUtil.getClasses(file);

		Assert.assertEquals(expectedClassNames, actualClassNames);
	}

	private void _testGetPathURIFromURL(
			String linuxProtocol, String windowsProtocol)
		throws Exception {

		_testGetPathURIFromURL(
			new URL(
				linuxProtocol + "/opt/liferay/tomcat/lib/servlet-api.jar" +
					"!/javax/servlet/Servlet.class"),
			"/opt/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		_testGetPathURIFromURL(
			new URL(
				linuxProtocol + "/opt/with%20space/tomcat/lib/servlet-api.jar" +
					"!/javax/servlet/Servlet.class"),
			"/opt/with space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		_testGetPathURIFromURL(
			new URL(
				windowsProtocol + "C:/Liferay/tomcat/lib/servlet-api.jar" +
					"!/javax/servlet/Servlet.class"),
			"/C:/Liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		_testGetPathURIFromURL(
			new URL(
				windowsProtocol + "C:/With%20Space/tomcat/lib/servlet-api.jar" +
					"!/javax/servlet/Servlet.class"),
			"/C:/With Space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
	}

	private void _testGetPathURIFromURL(URL url, String expectedPath) {
		URI uri = ReflectionTestUtil.invoke(
			ClassUtil.class, "_getPathURIFromURL", new Class<?>[] {URL.class},
			url);

		Assert.assertEquals(expectedPath, uri.getPath());
	}

}