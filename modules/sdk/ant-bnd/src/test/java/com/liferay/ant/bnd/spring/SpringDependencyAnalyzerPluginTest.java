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

package com.liferay.ant.bnd.spring;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;

import aQute.lib.io.IO;

import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class SpringDependencyAnalyzerPluginTest {

	@Test
	public void testDependenciesDefinedInFileAndAnnotation() throws Exception {
		JarResource jarResource = new JarResource(
			"dependencies/META-INF/spring/context.dependencies",
			"META-INF/spring/context.dependencies");

		Jar jar = analyze(
			Arrays.asList(_PACKAGE_NAME_BEAN), "1.0.0.1", jarResource);

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String value = read(resource);

		Assert.assertEquals(
			"bar.foo.Dependency\n" + _RELEASE_INFO + "java.lang.String\n",
			value);
	}

	@Test
	public void testDependenciesDefinedOnlyInAnnotation() throws Exception {
		Jar jar = analyze(Arrays.asList(_PACKAGE_NAME_BEAN), "1.0.0.1");

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String value = read(resource);

		value = value.replace("\r\n", "\n");

		Assert.assertEquals(_RELEASE_INFO + "java.lang.String\n", value);
	}

	@Test
	public void testDependenciesDefinedOnlyInAnnotationWithFilterString()
		throws Exception {

		Jar jar = analyze(Arrays.asList(_PACKAGE_NAME_FILTER), "1.0.0.1");

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String value = read(resource);

		Assert.assertEquals(
			_RELEASE_INFO + "java.lang.String (service.ranking=1)\n", value);
	}

	@Test
	public void testDependenciesDefinedOnlyInAnnotationWithRequireSchemaRange()
		throws Exception {

		Jar jar = analyze(Arrays.asList(_PACKAGE_NAME_BEAN), "[1.0.0,1.1.0)");

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String value = read(resource);

		value = value.replace("\r\n", "\n");

		Assert.assertEquals(_RELEASE_INFO + "java.lang.String\n", value);
	}

	@Test
	public void testDependenciesDefinedOnlyInFile() throws Exception {
		JarResource jarResource = new JarResource(
			"dependencies/META-INF/spring/context.dependencies",
			"META-INF/spring/context.dependencies");

		Jar jar = analyze(
			Collections.<String>emptyList(), "1.0.0.1", jarResource);

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String value = read(resource);

		Assert.assertEquals("bar.foo.Dependency\n" + _RELEASE_INFO, value);
	}

	@Test
	public void testEmptyDependencies() throws Exception {
		JarResource jarResource = new JarResource(
			"dependencies/META-INF/spring/empty.dependencies",
			"META-INF/spring/context.dependencies");

		Jar jar = analyze(
			Collections.<String>emptyList(), "1.0.0.1", jarResource);

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String value = read(resource);

		Assert.assertEquals(_RELEASE_INFO, value);
	}

	protected Jar analyze(
			List<String> packages, String requireSchemaVersion,
			JarResource... jarResources)
		throws Exception {

		JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class);

		for (String pkg : packages) {
			javaArchive.addPackages(true, pkg);
		}

		for (JarResource jarResource : jarResources) {
			javaArchive.addAsResource(
				jarResource.getURL(), jarResource.getTarget());
		}

		Analyzer analyzer = new Analyzer();

		analyzer.setBundleSymbolicName("test.bundle");
		analyzer.setBundleVersion("1.0.0");
		analyzer.setProperty(
			"Liferay-Require-SchemaVersion", requireSchemaVersion);
		analyzer.setProperty(
			"-liferay-spring-dependency", ServiceReference.class.getName());

		ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

		Jar jar = new Jar(
			"Spring Context Dependency Test",
			zipExporter.exportAsInputStream());

		analyzer.setJar(jar);

		analyzer.analyze();

		SpringDependencyAnalyzerPlugin springDependencyAnalyzerPlugin =
			new SpringDependencyAnalyzerPlugin();

		springDependencyAnalyzerPlugin.analyzeJar(analyzer);

		return jar;
	}

	protected String read(Resource resource) throws Exception {
		String value = IO.collect(resource.openInputStream());

		return value.replace("\r\n", "\n");
	}

	private static final String _PACKAGE_NAME_BEAN =
		"com.liferay.ant.bnd.spring.bean";

	private static final String _PACKAGE_NAME_FILTER =
		"com.liferay.ant.bnd.spring.filter";

	private static final String _RELEASE_INFO =
		"com.liferay.portal.kernel.model.Release " +
			"(&(release.bundle.symbolic.name=test.bundle)" +
				"(&(release.schema.version>=1.0.0)" +
					"(!(release.schema.version>=1.1.0)))" +
						"(|(!(release.state=*))(release.state=0)))\n";

	private static final class JarResource {

		public JarResource(String resourceName, String target) {
			_resourceName = resourceName;
			_target = target;
		}

		public String getTarget() {
			return _target;
		}

		public URL getURL() {
			Class<?> clazz = getClass();

			return clazz.getResource(_resourceName);
		}

		private final String _resourceName;
		private final String _target;

	}

}