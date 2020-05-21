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

import java.io.IOException;
import java.io.StringReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Hugo Huijser
 */
public class ClassUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		URL.setURLStreamHandlerFactory(
			protocol -> {
				if (protocol.equals("vfs") || protocol.equals("zip")) {
					return new URLStreamHandler() {

						protected URLConnection openConnection(URL url)
							throws IOException {

							return new URLConnection(url) {

								public void connect() throws IOException {
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
	public void testGetClasses() throws Exception {
		_testGetClasses("@Annotation", "Annotation");
		_testGetClasses("@AnnotationClass.Annotation", "AnnotationClass");
		_testGetClasses("@Annotation({A.class})", "A", "Annotation");
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
	}

	@Test
	public void testGetPathURIFromURLTomcat() throws Exception {
		testGetPathURIFromURL(
			"jar:file:/opt/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/opt/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"jar:file:/opt/with%20space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/opt/with space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"file:/opt/liferay/tomcat/classes/javax/servlet/Servlet.class",
			"/opt/liferay/tomcat/classes/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"jar:file:/C:/Liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/C:/Liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"jar:file:/C:/With%20Space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/C:/With Space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"file:/C:/Liferay/tomcat/classes/javax/servlet/Servlet.class",
			"/C:/Liferay/tomcat/classes/javax/servlet/Servlet.class");
	}

	@Test
	public void testGetPathURIFromURLWeblogic() throws Exception {
		testGetPathURIFromURL(
			"zip:/opt/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/opt/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"zip:/opt/with%20space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/opt/with space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"zip:C:/Liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/C:/Liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"zip:C:/With%20Space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/C:/With Space/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
	}

	@Test
	public void testGetPathURIFromURLWebsphere() throws Exception {
		testGetPathURIFromURL(
			"bundleresource://266.fwk-486185329/javax/servlet/Servlet.class",
			"/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"wsjar:file:/opt/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/opt/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
		testGetPathURIFromURL(
			"wsjar:file:/F:/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class",
			"/F:/liferay/tomcat/lib/servlet-api.jar" +
				"!/javax/servlet/Servlet.class");
	}

	@Test
	public void testGetPathURIFromURLWildfly() throws Exception {
		testGetPathURIFromURL(
			"vfs:/opt/liferay/tomcat/lib/servlet-api.jar/javax/servlet" +
				"/Servlet.class",
			"/opt/liferay/tomcat/lib/servlet-api.jar/javax/servlet" +
				"/Servlet.class");
		testGetPathURIFromURL(
			"vfs:/opt/with%20space/tomcat/lib/servlet-api.jar/javax/servlet" +
				"/Servlet.class",
			"/opt/with space/tomcat/lib/servlet-api.jar/javax/servlet" +
				"/Servlet.class");
		testGetPathURIFromURL(
			"vfs:/C:/Liferay/tomcat/lib/servlet-api.jar/javax/servlet" +
				"/Servlet.class",
			"/C:/Liferay/tomcat/lib/servlet-api.jar/javax/servlet" +
				"/Servlet.class");
		testGetPathURIFromURL(
			"vfs:/C:/With%20Space/tomcat/lib/servlet-api.jar/javax/servlet" +
				"/Servlet.class",
			"/C:/With Space/tomcat/lib/servlet-api.jar/javax/servlet" +
				"/Servlet.class");
	}

	protected void testGetPathURIFromURL(String url, String expectedPath)
		throws MalformedURLException {

		URI uri = ClassUtil.getPathURIFromURL(new URL(url));

		Assert.assertEquals(expectedPath, uri.getPath());
	}

	private void _testGetClasses(String content, String... expectedClasses)
		throws Exception {

		Set<String> expectedClassNames = new HashSet<>();

		Collections.addAll(expectedClassNames, expectedClasses);

		Set<String> actualClassNames = ClassUtil.getClasses(
			new StringReader(content), null);

		Assert.assertEquals(expectedClassNames, actualClassNames);
	}

}