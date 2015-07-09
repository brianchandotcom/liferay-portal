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

package com.liferay.ant.bnd.plugin.spring;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;

import aQute.lib.io.IO;

import com.liferay.ant.bnd.plugin.spring.annotation.ServiceReference;

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
	public void testDependenciesDefinedInFileAndAnnotations() throws Exception {
		JarResource jarResource = new JarResource(
			"META-INF/spring/context.dependencies",
			"dependencies/META-INF/spring/context.dependencies");

		Jar jar = analyze(Arrays.asList(_TEST_PACKAGE), jarResource);

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String collect = IO.collect(resource.openInputStream());

		Assert.assertEquals("bar.foo.Dependency\njava.lang.String\n", collect);
	}

	@Test
	public void testDependenciesDefinedOnlyInAnnotations() throws Exception {
		Jar jar = analyze(Arrays.asList(_TEST_PACKAGE));

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String collect = IO.collect(resource.openInputStream());

		Assert.assertEquals("java.lang.String\n", collect);
	}

	@Test
	public void testDependenciesDefinedOnlyInFile() throws Exception {
		JarResource jarResource = new JarResource(
			"META-INF/spring/context.dependencies",
			"dependencies/META-INF/spring/context.dependencies");

		Jar jar = analyze(Collections.<String>emptyList(), jarResource);

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String collect = IO.collect(resource.openInputStream());

		Assert.assertEquals("bar.foo.Dependency\n", collect);
	}

	@Test
	public void testEmptyDependencies() throws Exception {
		JarResource jarResource = new JarResource(
			"META-INF/spring/context.dependencies",
			"dependencies/META-INF/spring/empty.dependencies");

		Jar jar = analyze(Collections.<String>emptyList(), jarResource);

		Resource resource = jar.getResource(
			"OSGI-INF/context/context.dependencies");

		String collect = IO.collect(resource.openInputStream());

		Assert.assertEquals("", collect);
	}

	protected Jar analyze(List<String> packages, JarResource... jarResources)
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

		analyzer.setProperty(
			"-service-dependencies", ServiceReference.class.getName());

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

	private static final String _TEST_PACKAGE =
		"com.liferay.ant.bnd.plugin.spring";

	private static final class JarResource {

		public JarResource(String target, String resource) {
			_target = target;
			_resource = resource;
		}

		public String getTarget() {
			return _target;
		}

		public URL getURL() {
			Class<?> clazz = getClass();

			return clazz.getResource(_resource);
		}

		private final String _resource;
		private final String _target;

	}

}