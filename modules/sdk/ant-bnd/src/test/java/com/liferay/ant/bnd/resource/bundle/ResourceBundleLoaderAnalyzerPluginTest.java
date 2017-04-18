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

package com.liferay.ant.bnd.resource.bundle;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class ResourceBundleLoaderAnalyzerPluginTest {

	@Test
	public void testAggregateResourceBundlesInstructionEmpty()
		throws Exception {

		ResourceBundleLoaderAnalyzerPlugin resourceBundleLoaderAnalyzerPlugin =
			new ResourceBundleLoaderAnalyzerPlugin();

		InputStream inputStream =
			ResourceBundleLoaderAnalyzerPluginTest.class.getResourceAsStream(
				"dependencies/resources.test-1.jar");

		try (Jar jar = new Jar("dot", inputStream);
			Analyzer analyzer = new Analyzer()) {

			analyzer.setBundleSymbolicName("resources.test");
			analyzer.setJar(jar);
			analyzer.setProperty("-liferay-aggregate-resource-bundles", "");

			Assert.assertTrue(
				resourceBundleLoaderAnalyzerPlugin.analyzeJar(analyzer));

			Parameters requireCapabilityHeaders = new Parameters(
				analyzer.getProperty(Constants.REQUIRE_CAPABILITY));

			Collection<Attrs> requires = requireCapabilityHeaders.values();

			Assert.assertTrue(requires.toString(), requires.isEmpty());

			Parameters provideCapabilityHeaders = new Parameters(
				analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

			Set<Entry<String, Attrs>> provides =
				provideCapabilityHeaders.entrySet();

			Assert.assertEquals(provides.toString(), 1, provides.size());

			List<Entry<String, Attrs>> provideEntries = new ArrayList<>(
				provides);

			Entry<String, Attrs> firstEntry = provideEntries.get(0);

			Assert.assertEquals("liferay.resource.bundle", firstEntry.getKey());

			Attrs firstEntryAttrs = firstEntry.getValue();

			Assert.assertEquals(2, firstEntryAttrs.size());

			Assert.assertEquals(
				"resources.test", firstEntryAttrs.get("bundle.symbolic.name"));
			Assert.assertEquals(
				"content.Language",
				firstEntryAttrs.get("resource.bundle.base.name"));
		}
	}

	@Test
	public void testAggregateResourceBundlesInstructionMultiple()
		throws Exception {

		ResourceBundleLoaderAnalyzerPlugin resourceBundleLoaderAnalyzerPlugin =
			new ResourceBundleLoaderAnalyzerPlugin();

		InputStream inputStream =
			ResourceBundleLoaderAnalyzerPluginTest.class.getResourceAsStream(
				"dependencies/resources.test-1.jar");

		try (Jar jar = new Jar("dot", inputStream);
			Analyzer analyzer = new Analyzer()) {

			analyzer.setBundleSymbolicName("resources.test");
			analyzer.setJar(jar);
			analyzer.setProperty(
				"-liferay-aggregate-resource-bundles",
				"resources.lang1,resources.lang2,resources.lang3");

			Assert.assertTrue(
				resourceBundleLoaderAnalyzerPlugin.analyzeJar(analyzer));

			Parameters requireCapabilityHeaders = new Parameters(
				analyzer.getProperty(Constants.REQUIRE_CAPABILITY));

			Collection<Attrs> requires = requireCapabilityHeaders.values();

			Assert.assertEquals(requires.toString(), 3, requires.size());

			Parameters provideCapabilityHeaders = new Parameters(
				analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

			Set<Entry<String, Attrs>> provides =
				provideCapabilityHeaders.entrySet();

			Assert.assertEquals(provides.toString(), 2, provides.size());

			List<Entry<String, Attrs>> provideEntries = new ArrayList<>(
				provides);

			Entry<String, Attrs> firstEntry = provideEntries.get(0);

			Assert.assertEquals("liferay.resource.bundle", firstEntry.getKey());

			Attrs firstEntryAttrs = firstEntry.getValue();

			Assert.assertEquals(2, firstEntryAttrs.size());

			Assert.assertEquals(
				"resources.test", firstEntryAttrs.get("bundle.symbolic.name"));
			Assert.assertEquals(
				"content.Language",
				firstEntryAttrs.get("resource.bundle.base.name"));

			Entry<String, Attrs> aggregateEntry = provideEntries.get(1);

			Assert.assertEquals(
				"liferay.resource.bundle~", aggregateEntry.getKey());

			Attrs aggregateEntryAttrs = aggregateEntry.getValue();

			Assert.assertEquals(6, aggregateEntryAttrs.size());

			StringBuilder sb = new StringBuilder();

			sb.append(
				"(&(bundle.symbolic.name=resources.test)(!(aggregate=true))),");
			sb.append("(bundle.symbolic.name=resources.lang1),");
			sb.append("(bundle.symbolic.name=resources.lang2),");
			sb.append("(bundle.symbolic.name=resources.lang3)");

			Assert.assertEquals(
				sb.toString(),
				aggregateEntryAttrs.get("resource.bundle.aggregate"));

			Assert.assertEquals(
				"resources.test",
				aggregateEntryAttrs.get("bundle.symbolic.name"));

			Assert.assertEquals(
				"content.Language",
				aggregateEntryAttrs.get("resource.bundle.base.name"));

			Assert.assertEquals(
				"1", aggregateEntryAttrs.get("service.ranking"));

			Assert.assertEquals("true", aggregateEntryAttrs.get("aggregate"));

			Assert.assertEquals(
				"resources.test",
				aggregateEntryAttrs.get("servlet.context.name"));
		}
	}

	@Test
	public void testAggregateResourceBundlesInstructionWebContextPath()
		throws Exception {

		ResourceBundleLoaderAnalyzerPlugin resourceBundleLoaderAnalyzerPlugin =
			new ResourceBundleLoaderAnalyzerPlugin();

		InputStream inputStream =
			ResourceBundleLoaderAnalyzerPluginTest.class.getResourceAsStream(
				"dependencies/blade.language.web.jar");

		try (Jar jar = new Jar("dot", inputStream);
			Analyzer analyzer = new Analyzer()) {

			analyzer.setBundleSymbolicName("blade.language.web");
			analyzer.setJar(jar);
			analyzer.setProperty(
				"-liferay-aggregate-resource-bundles", "blade.language");
			analyzer.setProperty("Web-ContextPath", "/blade-language-web");

			Assert.assertTrue(
				resourceBundleLoaderAnalyzerPlugin.analyzeJar(analyzer));

			Parameters provideCapabilityHeaders = new Parameters(
				analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

			Set<Entry<String, Attrs>> provides =
				provideCapabilityHeaders.entrySet();

			List<Entry<String, Attrs>> provideEntries = new ArrayList<>(
				provides);

			Entry<String, Attrs> aggregateEntry = provideEntries.get(1);

			Attrs aggregateEntryAttrs = aggregateEntry.getValue();

			Assert.assertEquals(
				"blade-language-web",
				aggregateEntryAttrs.get("servlet.context.name"));
		}
	}

	@Test
	public void testProvideLiferayResourceBundleCapabilitiyAdded()
		throws Exception {

		ResourceBundleLoaderAnalyzerPlugin resourceBundleLoaderAnalyzerPlugin =
			new ResourceBundleLoaderAnalyzerPlugin();

		InputStream inputStream =
			ResourceBundleLoaderAnalyzerPluginTest.class.getResourceAsStream(
				"dependencies/resources.test-1.jar");

		try (Jar jar = new Jar("dot", inputStream);
			Analyzer analyzer = new Analyzer()) {

			analyzer.setJar(jar);

			Assert.assertTrue(
				resourceBundleLoaderAnalyzerPlugin.analyzeJar(analyzer));

			Parameters provideCapabilityHeaders = new Parameters(
				analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

			Attrs attrs = provideCapabilityHeaders.get(
				"liferay.resource.bundle");

			Assert.assertNotNull(attrs);

			Assert.assertTrue(attrs.containsKey("bundle.symbolic.name"));

			Assert.assertTrue(attrs.containsKey("resource.bundle.base.name"));
		}
	}

	@Test
	public void testProvideLiferayResourceBundleCapabilitiyNotAdded()
		throws Exception {

		ResourceBundleLoaderAnalyzerPlugin resourceBundleLoaderAnalyzerPlugin =
			new ResourceBundleLoaderAnalyzerPlugin();

		InputStream inputStream =
			ResourceBundleLoaderAnalyzerPluginTest.class.getResourceAsStream(
				"dependencies/resources.test-2.jar");

		try (Jar jar = new Jar("dot", inputStream);
			Analyzer analyzer = new Analyzer()) {

			analyzer.setJar(jar);

			Assert.assertFalse(
				resourceBundleLoaderAnalyzerPlugin.analyzeJar(analyzer));

			Parameters provideCapabilityHeaders = new Parameters(
				analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

			Attrs attrs = provideCapabilityHeaders.get(
				"liferay.resource.bundle");

			Assert.assertNull(attrs);
		}
	}

}