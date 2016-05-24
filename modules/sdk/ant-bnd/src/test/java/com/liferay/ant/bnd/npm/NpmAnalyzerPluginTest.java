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

package com.liferay.ant.bnd.npm;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.URLResource;

import aQute.lib.filter.Filter;

import com.liferay.ant.bnd.npm.NpmAnalyzerPlugin.NpmModule;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class NpmAnalyzerPluginTest {

	@Test
	public void testConversion() throws Exception {
		NpmAnalyzerPlugin npmAnalyzerPlugin = new NpmAnalyzerPlugin();

		URL url = getResource("dependencies/package.json");

		NpmModule npmModule = npmAnalyzerPlugin.getNpmModule(url.openStream());

		Assert.assertNotNull(npmModule);
		Assert.assertEquals("liferay", npmModule.name);
		Assert.assertEquals("1.2.4", npmModule.version);
		Assert.assertTrue(npmModule.dependencies.containsKey("lodash"));
		Assert.assertEquals("~3.9.3", npmModule.dependencies.get("lodash"));
	}

	@Test
	public void testParseVersionsBasic1() throws Exception {
		assertVersionFilter("", "(version>=0.0.0)");
	}

	@Test
	public void testParseVersionsBasic2() throws Exception {
		assertVersionFilter("*", "(version>=0.0.0)");
	}

	@Test
	public void testParseVersionsBasic3() throws Exception {
		assertVersionFilter("v2.0.0", "(version=2.0.0)");
	}

	@Test
	public void testParseVersionsCaret1() throws Exception {
		assertVersionFilter("^1.2.3", "(&(version>=1.2.3)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsCaret2() throws Exception {
		assertVersionFilter("^0.2.3", "(&(version>=0.2.3)(!(version>=0.3.0)))");
	}

	@Test
	public void testParseVersionsCaret3() throws Exception {
		assertVersionFilter("^0.0.3", "(&(version>=0.0.3)(!(version>=0.0.4)))");
	}

	@Test
	public void testParseVersionsCaret4() throws Exception {
		assertVersionFilter(
			"^1.2.3-beta_2", "(&(version>=1.2.3.beta_2)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsCaret5() throws Exception {
		assertVersionFilter(
			"^0.0.3-beta", "(&(version>=0.0.3.beta)(!(version>=0.0.4)))");
	}

	@Test
	public void testParseVersionsCaret6() throws Exception {
		assertVersionFilter("^1.2.x", "(&(version>=1.2.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsCaret7() throws Exception {
		assertVersionFilter("^0.0.x", "(&(version>=0.0.0)(!(version>=0.1.0)))");
	}

	@Test
	public void testParseVersionsCaret8() throws Exception {
		assertVersionFilter("^0.0", "(&(version>=0.0.0)(!(version>=0.1.0)))");
	}

	@Test
	public void testParseVersionsCaret9() throws Exception {
		assertVersionFilter("^1.x", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsCaret10() throws Exception {
		assertVersionFilter("^0.x", "(&(version>=0.0.0)(!(version>=1.0.0)))");
	}

	@Test
	public void testParseVersionsCaret11() throws Exception {
		assertVersionFilter("^0.*", "(&(version>=0.0.0)(!(version>=1.0.0)))");
	}

	@Test
	public void testParseVersionsCaret12() throws Exception {
		assertVersionFilter("^0.0.*", "(&(version>=0.0.0)(!(version>=0.1.0)))");
	}

	@Test
	public void testParseVersionsHyphenRange1() throws Exception {
		assertVersionFilter(
			"1.2.3 - 2.3.4", "(&(version>=1.2.3)(version<=2.3.4))");
	}

	@Test
	public void testParseVersionsHyphenRange2() throws Exception {
		assertVersionFilter(
			"1.2 - 2.3.4", "(&(version>=1.2.0)(version<=2.3.4))");
	}

	@Test
	public void testParseVersionsHyphenRange3() throws Exception {
		assertVersionFilter(
			"1.2.3 - 2.3", "(&(version>=1.2.3)(version<=2.4.0))");
	}

	@Test
	public void testParseVersionsHyphenRange4() throws Exception {
		assertVersionFilter(
			"1.2.3 - 2", "(&(version>=1.2.3)(!(version>=3.0.0)))");
	}

	@Test
	public void testParseVersionsPrefix1() throws Exception {
		assertVersionFilter("<2.1.0", "(!(version>=2.1.0))");
	}

	@Test
	public void testParseVersionsPrefix2() throws Exception {
		assertVersionFilter("<=2.1.0", "(version<=2.1.0)");
	}

	@Test
	public void testParseVersionsPrefix3() throws Exception {
		assertVersionFilter(">2.1.0", "(&(version>=2.1.0)(!(version=2.1.0)))");
	}

	@Test
	public void testParseVersionsPrefix4() throws Exception {
		assertVersionFilter(">=2.1.0", "(version>=2.1.0)");
	}

	@Test
	public void testParseVersionsRange1() throws Exception {
		assertVersionFilter("1.x", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsRange2() throws Exception {
		assertVersionFilter("1.2.x", "(&(version>=1.2.0)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersionsRange3() throws Exception {
		assertVersionFilter("1.*", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsRange4() throws Exception {
		assertVersionFilter("1.2.*", "(&(version>=1.2.0)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersionsRange5() throws Exception {
		assertVersionFilter("1.x.x", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsRange6() throws Exception {
		assertVersionFilter("1", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsRange7() throws Exception {
		assertVersionFilter("1.2", "(&(version>=1.2.0)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersionsRange8() throws Exception {
		assertVersionFilter(
			">=1.2.7 <1.3.0", "(&(version>=1.2.7)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersionsRange9() throws Exception {
		assertVersionFilter(
			">=1.2.7 <1.3", "(&(version>=1.2.7)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersionsSet1() throws Exception {
		assertVersionFilter(
			"v2.0.0 || v3.0.0", "(|(version=2.0.0)(version=3.0.0))");
	}

	@Test
	public void testParseVersionsSet2() throws Exception {
		assertVersionFilter(
			"v2.0.0 || v2.2.0 || v2.4.0",
			"(|(version=2.0.0)(version=2.2.0)(version=2.4.0))");
	}

	@Test
	public void testParseVersionsSet3() throws Exception {
		assertVersionFilter(
			"v2.0.0 || 1.2",
			"(|(version=2.0.0)(&(version>=1.2.0)(!(version>=1.3.0))))");
	}

	@Test
	public void testParseVersionsSet4() throws Exception {
		assertVersionFilter(
			"v2.0.0 || >2.1.0",
			"(|(version=2.0.0)(&(version>=2.1.0)(!(version=2.1.0))))");
	}

	@Test
	public void testParseVersionsSet5() throws Exception {
		assertVersionFilter(
			"v2.0.0 || ^2.2.x",
			"(|(version=2.0.0)(&(version>=2.2.0)(!(version>=3.0.0))))");
	}

	@Test
	public void testParseVersionsTilde1() throws Exception {
		assertVersionFilter("~1.2.3", "(&(version>=1.2.3)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersionsTilde2() throws Exception {
		assertVersionFilter("~1.2", "(&(version>=1.2.0)(!(version>=1.3.0)))");
	}

	@Test
	public void testParseVersionsTilde3() throws Exception {
		assertVersionFilter("~1", "(&(version>=1.0.0)(!(version>=2.0.0)))");
	}

	@Test
	public void testParseVersionsTilde4() throws Exception {
		assertVersionFilter("~0.2.3", "(&(version>=0.2.3)(!(version>=0.3.0)))");
	}

	@Test
	public void testParseVersionsTilde5() throws Exception {
		assertVersionFilter("~0.2", "(&(version>=0.2.0)(!(version>=0.3.0)))");
	}

	@Test
	public void testParseVersionsTilde6() throws Exception {
		assertVersionFilter("~0", "(&(version>=0.0.0)(!(version>=1.0.0)))");
	}

	@Test
	public void testParseVersionsTilde7() throws Exception {
		assertVersionFilter(
			"~1.2.3-beta_2", "(&(version>=1.2.3.beta_2)(!(version>=1.3.0)))");
	}

	@Test
	public void testPlugin() throws Exception {
		Analyzer analyzer = new Analyzer();

		Jar jar = new Jar("test");

		jar.putResource(
			"package.json",
			new URLResource(getResource("dependencies/package.json")));

		analyzer.setJar(jar);

		NpmAnalyzerPlugin npmAnalyzerPlugin = new NpmAnalyzerPlugin();

		npmAnalyzerPlugin.analyzeJar(analyzer);

		Assert.assertEquals("1.2.4", analyzer.getBundleVersion());
		Assert.assertEquals(
			"/liferay-1.2.4",
			analyzer.getProperty(NpmAnalyzerPlugin.WEB_CONTEXT_PATH));

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
			"package.json",
			new URLResource(
				getResource("dependencies/package.bad.version.json")));

		analyzer.setJar(jar);

		NpmAnalyzerPlugin npmAnalyzerPlugin = new NpmAnalyzerPlugin();

		npmAnalyzerPlugin.analyzeJar(analyzer);

		Assert.assertEquals("0.0.0.1word-cha_rs", analyzer.getBundleVersion());
		Assert.assertEquals(
			"/liferay-0.0.0.1word-cha_rs",
			analyzer.getProperty(NpmAnalyzerPlugin.WEB_CONTEXT_PATH));

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
			"package.json",
			new URLResource(getResource("dependencies/package.empty.json")));

		analyzer.setJar(jar);

		NpmAnalyzerPlugin npmAnalyzerPlugin = new NpmAnalyzerPlugin();

		npmAnalyzerPlugin.analyzeJar(analyzer);
	}

	protected void assertVersionFilter(
			String version, String expectedFilterString)
		throws Exception {

		NpmAnalyzerPlugin npmAnalyzerPlugin = new NpmAnalyzerPlugin();

		String filterString = npmAnalyzerPlugin.getNpmVersionFilter(version);

		Assert.assertEquals(expectedFilterString, filterString);

		Filter filter = new Filter(filterString);

		Assert.assertNull(filter.verify());
	}

	protected URL getResource(String path) {
		Class<?> clazz = getClass();

		return clazz.getResource(path);
	}

}