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

import com.liferay.petra.string.StringPool;

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
	public void testGetClassesFromAnnotation() throws Exception {
		testGetClassesFromAnnotation("Annotation", "Annotation");
		testGetClassesFromAnnotation(
			"AnnotationClass.Annotation", "AnnotationClass");
	}

	@Test
	public void testGetClassesFromAnnotationsWithArrayParameter()
		throws Exception {

		testGetClassesFromAnnotation("Annotation", "Annotation", "A");
		testGetClassesFromAnnotation("Annotation", "Annotation", "A", "B");
		testGetClassesFromAnnotation("Annotation", "Annotation", "A", "B", "C");

		testGetClassesFromAnnotation(
			"AnnotationClass.Annotation", "AnnotationClass", "A");
		testGetClassesFromAnnotation(
			"AnnotationClass.Annotation", "AnnotationClass", "A", "B");
		testGetClassesFromAnnotation(
			"AnnotationClass.Annotation", "AnnotationClass", "A", "B", "C");
	}

	@Test
	public void testGetPathURIFromURLUnix() throws Exception {
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

	@Test
	public void testGetPathURIFromURLWindows() throws Exception {
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

	protected void testGetClassesFromAnnotation(
			String annotation, String expectedAnnotationClassName,
			String... arrayParameterClassNames)
		throws Exception {

		StringBundler sb = new StringBundler(
			arrayParameterClassNames.length * 3 + 2);

		sb.append(StringPool.AT);
		sb.append(annotation);

		if (arrayParameterClassNames.length > 0) {
			sb.append("({");

			for (int i = 0; i < arrayParameterClassNames.length; i++) {
				sb.append(arrayParameterClassNames[i]);
				sb.append(".class");

				if (i < (arrayParameterClassNames.length - 1)) {
					sb.append(StringPool.COMMA);
				}
			}

			sb.append("})");
		}

		Set<String> actualClassNames = ClassUtil.getClasses(
			new StringReader(sb.toString()), null);

		Set<String> expectedClassNames = new HashSet<>();

		expectedClassNames.add(expectedAnnotationClassName);

		Collections.addAll(expectedClassNames, arrayParameterClassNames);

		Assert.assertEquals(expectedClassNames, actualClassNames);
	}

	protected void testGetPathURIFromURL(String url, String expectedPath)
		throws MalformedURLException {

		URI uri = ClassUtil.getPathURIFromURL(new URL(url));

		Assert.assertEquals(expectedPath, uri.getPath());
	}

}