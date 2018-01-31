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
				"dependencies/com.liferay.contacts.service.jar");

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