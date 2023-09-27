/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.bnd.spring;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;

import aQute.lib.io.IO;

import com.liferay.ant.bnd.spring.bean.SampleBean;
import com.liferay.ant.bnd.spring.filter.FilterSampleBean;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

import java.io.InputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class SpringDependencyAnalyzerPluginTest {

	@Test
	public void testDependenciesDefinedInFileAndAnnotation() throws Exception {
		Jar jar = analyze(SampleBean.class, "1.0.0.1", "bar.foo.Dependency");

		String value = read(
			jar.getResource("OSGI-INF/context/context.dependencies"));

		Assert.assertEquals(
			"bar.foo.Dependency\n" + _RELEASE_INFO + "java.lang.String\n",
			value);
	}

	@Test
	public void testDependenciesDefinedOnlyInAnnotation() throws Exception {
		Jar jar = analyze(SampleBean.class, "1.0.0.1", null);

		String value = read(
			jar.getResource("OSGI-INF/context/context.dependencies"));

		value = value.replace("\r\n", "\n");

		Assert.assertEquals(_RELEASE_INFO + "java.lang.String\n", value);
	}

	@Test
	public void testDependenciesDefinedOnlyInAnnotationWithFilterString()
		throws Exception {

		Jar jar = analyze(FilterSampleBean.class, "1.0.0.1", null);

		String value = read(
			jar.getResource("OSGI-INF/context/context.dependencies"));

		Assert.assertEquals(
			_RELEASE_INFO + "java.lang.String (service.ranking=1)\n", value);
	}

	@Test
	public void testDependenciesDefinedOnlyInAnnotationWithRequireSchemaRange()
		throws Exception {

		Jar jar = analyze(SampleBean.class, "[1.0.0,2.0.0)", null);

		String value = read(
			jar.getResource("OSGI-INF/context/context.dependencies"));

		value = value.replace("\r\n", "\n");

		Assert.assertEquals(_RELEASE_INFO + "java.lang.String\n", value);
	}

	@Test
	public void testDependenciesDefinedOnlyInFile() throws Exception {
		Jar jar = analyze(null, "1.0.0.1", "bar.foo.Dependency");

		String value = read(
			jar.getResource("OSGI-INF/context/context.dependencies"));

		Assert.assertEquals("bar.foo.Dependency\n" + _RELEASE_INFO, value);
	}

	@Test
	public void testEmptyDependencies() throws Exception {
		Jar jar = analyze(null, "1.0.0.1", "");

		String value = read(
			jar.getResource("OSGI-INF/context/context.dependencies"));

		Assert.assertEquals(_RELEASE_INFO, value);
	}

	protected Jar analyze(
			Class<?> clazz, String requireSchemaVersion,
			String dependenciesContent)
		throws Exception {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (ZipOutputStream zipOutputStream = new ZipOutputStream(
					unsyncByteArrayOutputStream)) {

				if (clazz != null) {
					String name = clazz.getName();

					name = name.replace(CharPool.PERIOD, CharPool.SLASH);

					name = name.concat(".class");

					zipOutputStream.putNextEntry(new ZipEntry(name));

					try (InputStream inputStream = clazz.getResourceAsStream(
							"/" + name)) {

						StreamUtil.transfer(
							inputStream, zipOutputStream, false);
					}

					zipOutputStream.closeEntry();
				}

				if (dependenciesContent != null) {
					zipOutputStream.putNextEntry(
						new ZipEntry("META-INF/spring/context.dependencies"));

					zipOutputStream.write(
						dependenciesContent.getBytes(StringPool.UTF8));

					zipOutputStream.closeEntry();
				}
			}

			Analyzer analyzer = new Analyzer();

			analyzer.setBundleSymbolicName("test.bundle");
			analyzer.setBundleVersion("1.0.0");
			analyzer.setProperty(
				"Liferay-Require-SchemaVersion", requireSchemaVersion);
			analyzer.setProperty(
				"-liferay-spring-dependency", ServiceReference.class.getName());

			Jar jar = new Jar(
				"Spring Context Dependency Test",
				new UnsyncByteArrayInputStream(
					unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
					unsyncByteArrayOutputStream.size()));

			analyzer.setJar(jar);

			analyzer.analyze();

			SpringDependencyAnalyzerPlugin springDependencyAnalyzerPlugin =
				new SpringDependencyAnalyzerPlugin();

			springDependencyAnalyzerPlugin.analyzeJar(analyzer);

			return jar;
		}
	}

	protected String read(Resource resource) throws Exception {
		String value = IO.collect(resource.openInputStream());

		return value.replace("\r\n", "\n");
	}

	private static final String _RELEASE_INFO =
		"com.liferay.portal.kernel.model.Release " +
			"(&(release.bundle.symbolic.name=test.bundle)" +
				"(&(release.schema.version>=1.0.0)" +
					"(!(release.schema.version>=2.0.0)))" +
						"(|(!(release.state=*))(release.state=0)))\n";

}