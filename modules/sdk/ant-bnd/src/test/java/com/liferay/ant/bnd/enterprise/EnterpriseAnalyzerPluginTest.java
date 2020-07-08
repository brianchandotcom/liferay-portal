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

package com.liferay.ant.bnd.enterprise;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class EnterpriseAnalyzerPluginTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testAnalyzeJar() throws Exception {
		_testAnalyzeJar(null, null, false);
		_testAnalyzeJar("Test Enterprise APP", null, false);
		_testAnalyzeJar("Test Enterprise APP", "", false);
		_testAnalyzeJar(
			"Test Enterprise APP", "ModulePortalProfile.xml", false);
		_testAnalyzeJar(
			"Test Enterprise APP",
			"OSGI-INF/com.liferay.ant.bnd.enterprise.test.internal." +
				"TestComponent.xml",
			true);
	}

	private void _assertAttribute(
		String attributeValue, String content, boolean existed) {

		if (existed) {
			Assert.assertTrue(
				attributeValue + " does not contain " + content,
				attributeValue.contains(content));

			return;
		}

		if (attributeValue != null) {
			Assert.assertFalse(
				attributeValue + " contains " + content,
				attributeValue.contains(content));
		}
	}

	private void _assertManifest(Manifest manifest, boolean generated) {
		Attributes attributes = manifest.getMainAttributes();

		_assertAttribute(
			attributes.getValue("Import-Package"), "com.liferay.portal.profile",
			generated);

		_assertAttribute(
			attributes.getValue("Private-Package"),
			_TEST_BUNDLE_INTERNAL_PACKAGE_NAME, generated);

		_assertAttribute(
			attributes.getValue("Provide-Capability"),
			"List<String>=\"com.liferay.portal.profile.PortalProfile\"",
			generated);

		_assertAttribute(
			attributes.getValue("Service-Component"),
			_TEST_BUNDLE_INTERNAL_PACKAGE_NAME + ".ModulePortalProfile.xml",
			generated);
	}

	private void _testAnalyzeJar(
			String liferayEnterpriseApp, String serviceComponent,
			boolean generated)
		throws Exception {

		try (Jar jar = new Jar("test.jar");
			Analyzer analyzer = new Analyzer()) {

			analyzer.setJar(jar);

			analyzer.set("Bundle-SymbolicName", _TEST_BUNDLE_SYNBOLIC_NAME);

			if (liferayEnterpriseApp != null) {
				analyzer.setProperty(
					"Liferay-Enterprise-App", liferayEnterpriseApp);
			}

			if (serviceComponent != null) {
				analyzer.setProperty("Service-Component", serviceComponent);
			}

			EnterpriseAnalyzerPlugin enterpriseAnalyzerPlugin =
				new EnterpriseAnalyzerPlugin();

			enterpriseAnalyzerPlugin.analyzeJar(analyzer);

			_assertManifest(analyzer.calcManifest(), generated);
		}
	}

	private static final String _TEST_BUNDLE_INTERNAL_PACKAGE_NAME =
		EnterpriseAnalyzerPluginTest._TEST_BUNDLE_SYNBOLIC_NAME +
			".internal.portal.profile";

	private static final String _TEST_BUNDLE_SYNBOLIC_NAME =
		"com.liferay.ant.bnd.enterprise.test";

}