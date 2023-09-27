/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.bnd.npm;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.EmbeddedResource;
import aQute.bnd.osgi.Jar;

import aQute.lib.filter.Filter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		NpmAnalyzerPlugin.NpmModule npmModule = npmAnalyzerPlugin.getNpmModule(
			url.openStream());

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
			new EmbeddedResource(getString("dependencies/package.json"), 0));

		analyzer.setJar(jar);

		NpmAnalyzerPlugin npmAnalyzerPlugin = new NpmAnalyzerPlugin();

		npmAnalyzerPlugin.analyzeJar(analyzer);

		Assert.assertEquals("1.2.4", analyzer.getBundleVersion());
		Assert.assertEquals(
			"/liferay-1.2.4",
			analyzer.getProperty(NpmAnalyzerPlugin.WEB_CONTEXT_PATH));

		Assert.assertEquals(
			"osgi.webresource;osgi.webresource=liferay;" +
				"version:Version=\"1.2.4\"",
			analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

		Assert.assertEquals(
			"osgi.webresource;filter:=\"(&(osgi.webresource=liferay)" +
				"(&(version>=1.0.0)(!(version>=1.1.0))))\"",
			analyzer.getProperty(Constants.REQUIRE_CAPABILITY));
	}

	@Test
	public void testPluginWithBadVersion() throws Exception {
		Analyzer analyzer = new Analyzer();

		Jar jar = new Jar("test");

		jar.putResource(
			"package.json",
			new EmbeddedResource(
				getString("dependencies/package.bad.version.json"), 0));

		analyzer.setJar(jar);

		NpmAnalyzerPlugin npmAnalyzerPlugin = new NpmAnalyzerPlugin();

		npmAnalyzerPlugin.analyzeJar(analyzer);

		Assert.assertEquals("0.0.0.1word-cha_rs", analyzer.getBundleVersion());
		Assert.assertEquals(
			"/liferay-0.0.0.1word-cha_rs",
			analyzer.getProperty(NpmAnalyzerPlugin.WEB_CONTEXT_PATH));

		Assert.assertEquals(
			"osgi.webresource;osgi.webresource=liferay;" +
				"version:Version=\"0.0.0.1word-cha_rs\"",
			analyzer.getProperty(Constants.PROVIDE_CAPABILITY));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPluginWithEmptyBower() throws Exception {
		Analyzer analyzer = new Analyzer();

		Jar jar = new Jar("test");

		jar.putResource("package.json", new EmbeddedResource("", 0));

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

	protected String getString(String path) throws Exception {
		Class<?> clazz = getClass();

		try (InputStream inputStream = clazz.getResourceAsStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader)) {

			Stream<String> stream = bufferedReader.lines();

			return stream.collect(Collectors.joining("\n"));
		}
	}

}