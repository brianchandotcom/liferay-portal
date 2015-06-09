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

package com.liferay.ant.bnd.plugin;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.URLResource;

import aQute.lib.filter.Filter;

import com.liferay.ant.bnd.plugin.BowerAnalyzerPlugin.BowerModule;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class BowerAnalyzerPluginTest {

	@Test
	public void testConversion() throws Exception {
		BowerAnalyzerPlugin bowerAnalyzerPlugin = new BowerAnalyzerPlugin();

		URL url = getResource("dependencies/bower.json");

		BowerModule bowerModule = bowerAnalyzerPlugin.getBowerModule(
			url.openStream());

		Assert.assertNotNull(bowerModule);
		Assert.assertEquals("liferay", bowerModule.name);
		Assert.assertEquals("1.2.4", bowerModule.version);
		Assert.assertTrue(bowerModule.dependencies.containsKey("lodash"));
		Assert.assertEquals("~3.9.3", bowerModule.dependencies.get("lodash"));
	}

	@Test
	public void testParseVersions_basic_1() throws Exception {
		assertVersionFilter("", "(version>=0.0.0)");
	}

	@Test
	public void testParseVersions_basic_2() throws Exception {
		assertVersionFilter("*", "(version>=0.0.0)");
	}

	@Test
	public void testParseVersions_basic_3() throws Exception {
		assertVersionFilter("v2.0.0", "(version=2.0.0)");
	}

	@Test
	public void testParseVersions_carrot_1() throws Exception {
		assertVersionFilter("^1.2.3", "(&(version>=1.2.3)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_carrot_2() throws Exception {
		assertVersionFilter("^0.2.3", "(&(version>=0.2.3)(!(version>=0.3.0)))");
	}

	@Test
	public void testParseVersions_carrot_3() throws Exception {
		assertVersionFilter("^0.0.3", "(&(version>=0.0.3)(!(version>=0.0.4)))");
	}

	@Test
	public void testParseVersions_carrot_4() throws Exception {
		assertVersionFilter(
			"^1.2.3-beta_2", "(&(version>=1.2.3.beta_2)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_carrot_5() throws Exception {
		assertVersionFilter(
			"^0.0.3-beta", "(&(version>=0.0.3.beta)(!(version>=0.0.4)))");
	}

	@Test
	public void testParseVersions_carrot_6() throws Exception {
		assertVersionFilter("^1.2.x", "(&(version>=1.2.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_carrot_7() throws Exception {
		assertVersionFilter("^0.0.x", "(&(version>=0.0.0)(!(version>=0.1.0)))");
	}

	@Test
	public void testParseVersions_carrot_8() throws Exception {
		assertVersionFilter("^0.0", "(&(version>=0.0.0)(!(version>=0.1.0)))");
	}

	@Test
	public void testParseVersions_carrot_9() throws Exception {
		assertVersionFilter("^1.x", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_carrot_10() throws Exception {
		assertVersionFilter("^0.x", "(&(version>=0.0.0)(!(version>=1.0.0)))");
	}

	@Test
	public void testParseVersions_carrot_11() throws Exception {
		assertVersionFilter("^0.*", "(&(version>=0.0.0)(!(version>=1.0.0)))");
	}

	@Test
	public void testParseVersions_carrot_12() throws Exception {
		assertVersionFilter("^0.0.*", "(&(version>=0.0.0)(!(version>=0.1.0)))");
	}

	@Test
	public void testParseVersions_hyphenrange_1() throws Exception {
		assertVersionFilter(
			"1.2.3 - 2.3.4", "(&(version>=1.2.3)(version<=2.3.4))");
	}

	@Test
	public void testParseVersions_hyphenrange_2() throws Exception {
		assertVersionFilter(
			"1.2 - 2.3.4", "(&(version>=1.2.0)(version<=2.3.4))");
	}

	@Test
	public void testParseVersions_hyphenrange_3() throws Exception {
		assertVersionFilter(
			"1.2.3 - 2.3", "(&(version>=1.2.3)(version<=2.4.0))");
	}

	@Test
	public void testParseVersions_hyphenrange_4() throws Exception {
		assertVersionFilter(
			"1.2.3 - 2", "(&(version>=1.2.3)(!(version>=3.0.0)))");
	}

	@Test
	public void testParseVersions_prefix_1() throws Exception {
		assertVersionFilter("<2.1.0", "(!(version>=2.1.0))");
	}

	@Test
	public void testParseVersions_prefix_2() throws Exception {
		assertVersionFilter("<=2.1.0", "(version<=2.1.0)");
	}

	@Test
	public void testParseVersions_prefix_3() throws Exception {
		assertVersionFilter(">2.1.0", "(&(version>=2.1.0)(!(version=2.1.0)))");
	}

	@Test
	public void testParseVersions_prefix_4() throws Exception {
		assertVersionFilter(">=2.1.0", "(version>=2.1.0)");
	}

	@Test
	public void testParseVersions_range_1() throws Exception {
		assertVersionFilter("1.x", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_range_2() throws Exception {
		assertVersionFilter("1.2.x", "(&(version>=1.2.0)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersions_range_3() throws Exception {
		assertVersionFilter("1.*", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_range_4() throws Exception {
		assertVersionFilter("1.2.*", "(&(version>=1.2.0)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersions_range_5() throws Exception {
		assertVersionFilter("1.x.x", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_range_6() throws Exception {
		assertVersionFilter("1", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_range_7() throws Exception {
		assertVersionFilter("1.2", "(&(version>=1.2.0)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersions_range_8() throws Exception {
		assertVersionFilter(
			">=1.2.7 <1.3.0", "(&(version>=1.2.7)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersions_range_9() throws Exception {
		assertVersionFilter(
			">=1.2.7 <1.3", "(&(version>=1.2.7)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersions_sets_1() throws Exception {
		assertVersionFilter(
			"v2.0.0 || v3.0.0", "(|(version=2.0.0)(version=3.0.0))");
	}

	@Test
	public void testParseVersions_sets_2() throws Exception {
		assertVersionFilter(
			"v2.0.0 || v2.2.0 || v2.4.0",
			"(|(version=2.0.0)(version=2.2.0)(version=2.4.0))");
	}

	@Test
	public void testParseVersions_sets_3() throws Exception {
		assertVersionFilter(
			"v2.0.0 || 1.2",
			"(|(version=2.0.0)(&(version>=1.2.0)(!(version>=1.3.0))))");
	}

	@Test
	public void testParseVersions_sets_4() throws Exception {
		assertVersionFilter(
			"v2.0.0 || >2.1.0",
			"(|(version=2.0.0)(&(version>=2.1.0)(!(version=2.1.0))))");
	}

	@Test
	public void testParseVersions_sets_5() throws Exception {
		assertVersionFilter(
			"v2.0.0 || ^2.2.x",
			"(|(version=2.0.0)(&(version>=2.2.0)(!(version>=3.0.0))))");
	}

	@Test
	public void testParseVersions_tilde_1() throws Exception {
		assertVersionFilter("~1.2.3", "(&(version>=1.2.3)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersions_tilde_2() throws Exception {
		assertVersionFilter("~1.2", "(&(version>=1.2.0)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersions_tilde_3() throws Exception {
		assertVersionFilter("~1", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersions_tilde_4() throws Exception {
		assertVersionFilter("~0.2.3", "(&(version>=0.2.3)(!(version>=0.3.0)))");
	}

	@Test
	public void testParseVersions_tilde_5() throws Exception {
		assertVersionFilter("~0.2", "(&(version>=0.2.0)(!(version>=0.3.0)))");
	}

	@Test
	public void testParseVersions_tilde_6() throws Exception {
		assertVersionFilter("~0", "(&(version>=0.0.0)(!(version>=1.0.0)))");
	}

	@Test
	public void testParseVersions_tilde_7() throws Exception {
		assertVersionFilter(
			"~1.2.3-beta_2", "(&(version>=1.2.3.beta_2)(!(version>=1.3.0)))");
	}

	@Test
	public void testPlugin() throws Exception {
		Analyzer analyzer = new Analyzer();

		Jar jar = new Jar("test");

		jar.putResource(
			"bower.json",
			new URLResource(getResource("dependencies/bower.json")));

		analyzer.setJar(jar);

		BowerAnalyzerPlugin bowerAnalyzerPlugin = new BowerAnalyzerPlugin();

		bowerAnalyzerPlugin.analyzeJar(analyzer);

		Assert.assertEquals("1.2.4", analyzer.getBundleVersion());
		Assert.assertEquals(
			"/liferay-1.2.4",
			analyzer.getProperty(BowerAnalyzerPlugin.WEB_CONTEXTPATH));

		String property = analyzer.getProperty(Constants.PROVIDE_CAPABILITY);

		Assert.assertEquals(
			"osgi.webresource;osgi.webresource=liferay;" +
				"version:Version=\"1.2.4\"",
			property);

		property = analyzer.getProperty(Constants.REQUIRE_CAPABILITY);

		Assert.assertEquals(
			"osgi.webresource;filter:=\"(&(osgi.webresource=liferay)" +
				"(&(version>=1.0.0)(!(version>=1.1.0))))\"",
			property);
	}

	@Test
	public void testPluginWithBadVersion() throws Exception {
		Analyzer analyzer = new Analyzer();

		Jar jar = new Jar("test");

		jar.putResource(
			"bower.json",
			new URLResource(
				getResource("dependencies/bower.bad.version.json")));

		analyzer.setJar(jar);

		BowerAnalyzerPlugin bowerAnalyzerPlugin = new BowerAnalyzerPlugin();

		bowerAnalyzerPlugin.analyzeJar(analyzer);

		Assert.assertEquals("0.0.0.1word-cha_rs", analyzer.getBundleVersion());
		Assert.assertEquals(
			"/liferay-0.0.0.1word-cha_rs",
			analyzer.getProperty(BowerAnalyzerPlugin.WEB_CONTEXTPATH));

		String property = analyzer.getProperty(Constants.PROVIDE_CAPABILITY);

		Assert.assertEquals(
			"osgi.webresource;osgi.webresource=liferay;" +
				"version:Version=\"0.0.0.1word-cha_rs\"",
			property);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPluginWithEmptyBower() throws Exception {
		Analyzer analyzer = new Analyzer();

		Jar jar = new Jar("test");

		jar.putResource(
			"bower.json",
			new URLResource(getResource("dependencies/bower.empty.json")));

		analyzer.setJar(jar);

		BowerAnalyzerPlugin bowerAnalyzerPlugin = new BowerAnalyzerPlugin();

		bowerAnalyzerPlugin.analyzeJar(analyzer);
	}

	protected void assertVersionFilter(String version, String expected) {
		BowerAnalyzerPlugin bowerAnalyzerPlugin = new BowerAnalyzerPlugin();

		String filter = bowerAnalyzerPlugin.getBowerVersionFilter(version);

		Assert.assertEquals(expected, filter);

		Filter f = new Filter(filter);

		Assert.assertNull(f.verify());
	}

	protected URL getResource(String path) {
		Class<?> clazz = getClass();

		return clazz.getResource(path);
	}

}