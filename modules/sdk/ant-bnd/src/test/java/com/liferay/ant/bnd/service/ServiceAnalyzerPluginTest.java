/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.bnd.service;

import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import org.osgi.framework.Constants;

/**
 * @author Gregory Amerson
 */
public class ServiceAnalyzerPluginTest {

	@Test
	public void testReadCustomServiceXmlLocation() throws Exception {
		InputStream inputStream =
			ServiceAnalyzerPluginTest.class.getResourceAsStream(
				"dependencies/com.liferay.contacts.service2.jar");

		try (Jar jar = new Jar("dot", inputStream);
			Analyzer analyzer = new Analyzer()) {

			analyzer.setJar(jar);

			analyzer.setProperty("Liferay-Service", "true");
			analyzer.setProperty("-liferay-service-xml", "entities.xml");

			ServiceAnalyzerPlugin serviceAnalyzerPlugin =
				new ServiceAnalyzerPlugin();

			serviceAnalyzerPlugin.analyzeJar(analyzer);

			Parameters provideCapabilityHeaders =
				analyzer.getProvideCapability();

			Assert.assertNotNull(provideCapabilityHeaders);

			Assert.assertEquals(
				provideCapabilityHeaders.toString(), 1,
				provideCapabilityHeaders.size());
		}
	}

	@Test
	public void testReadServiceXmlToProvideServiceCapabilities()
		throws Exception {

		InputStream inputStream =
			ServiceAnalyzerPluginTest.class.getResourceAsStream(
				"dependencies/com.liferay.contacts.service1.jar");

		try (Jar jar = new Jar("dot", inputStream);
			Analyzer analyzer = new Analyzer()) {

			analyzer.setJar(jar);

			analyzer.setProperty("Liferay-Service", "true");
			analyzer.setProperty(
				"-liferay-service-xml", "service.xml,**/service.xml");

			Parameters parameters = new Parameters(
				"existing.parameter;saveme=true");

			analyzer.setProperty(
				Constants.PROVIDE_CAPABILITY, parameters.toString());

			ServiceAnalyzerPlugin serviceAnalyzerPlugin =
				new ServiceAnalyzerPlugin();

			serviceAnalyzerPlugin.analyzeJar(analyzer);

			Parameters provideCapabilityHeaders =
				analyzer.getProvideCapability();

			Assert.assertNotNull(provideCapabilityHeaders);

			Assert.assertEquals(
				provideCapabilityHeaders.toString(), 2,
				provideCapabilityHeaders.size());
		}
	}

}