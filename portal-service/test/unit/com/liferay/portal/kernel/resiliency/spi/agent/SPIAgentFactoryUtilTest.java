/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.resiliency.spi.agent;

import com.liferay.portal.kernel.resiliency.PortalResiliencyException;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;

import java.lang.reflect.Field;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SPIAgentFactoryUtilTest {

	@Test
	public void testCreateSPIAgent() {
		SPIAgentFactoryUtil.registerAgentClass(MockSPIAgent.class);
		SPIAgentFactoryUtil.registerAgentClass(BadMockSPIAgent.class);

		SPIConfiguration spiConfiguration = new SPIConfiguration(
			"testId", null, 8081, "", new String[0], new String[0]);

		// 1) Null name

		try {
			SPIAgentFactoryUtil.createSPIAgent(spiConfiguration, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// 2) No such name

		String badName = "badName";

		spiConfiguration = new SPIConfiguration(
			"testId", badName, 8081, "", new String[0], new String[0]);

		try {
			SPIAgentFactoryUtil.createSPIAgent(spiConfiguration, null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Unkown SPIAgent class name " + badName, iae.getMessage());
		}

		// 3) Bad SPIAgent class

		spiConfiguration = new SPIConfiguration(
			"testId", BadMockSPIAgent.class.getName(), 8081, "", new String[0],
			new String[0]);

		try {
			SPIAgentFactoryUtil.createSPIAgent(spiConfiguration, null);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"Failed to create instance of class " + BadMockSPIAgent.class,
				re.getMessage());
		}

		// 4) Success creation

		spiConfiguration = new SPIConfiguration(
			"testId", MockSPIAgent.class.getName(), 8081, "", new String[0],
			new String[0]);

		SPIAgent spiAgent = SPIAgentFactoryUtil.createSPIAgent(
			spiConfiguration, null);

		Assert.assertEquals(MockSPIAgent.class, spiAgent.getClass());
	}

	@Test
	public void testRegisteration() throws Exception {

		// 1) Spring way

		SPIAgentFactoryUtil spiAgentFactoryUtil = new SPIAgentFactoryUtil();

		Set<String> agentClassNames = new HashSet<String>();

		agentClassNames.add(MockSPIAgent.class.getName());
		agentClassNames.add(BadMockSPIAgent.class.getName());

		spiAgentFactoryUtil.setAgentClasses(agentClassNames);

		Map<String, Class<? extends SPIAgent>> spiAgentClassMap =
			_getSpiAgentClassMap();

		Assert.assertEquals(2, spiAgentClassMap.size());
		Assert.assertSame(
			MockSPIAgent.class,
			spiAgentClassMap.get(MockSPIAgent.class.getName()));
		Assert.assertSame(
			BadMockSPIAgent.class,
			spiAgentClassMap.get(BadMockSPIAgent.class.getName()));

		// 2) Get names

		Set<String> spiAgentClassNames =
			SPIAgentFactoryUtil.getAgentClassNames();

		Assert.assertEquals(2, spiAgentClassNames.size());
		Assert.assertTrue(
			spiAgentClassNames.contains(MockSPIAgent.class.getName()));
		Assert.assertTrue(
			spiAgentClassNames.contains(BadMockSPIAgent.class.getName()));

		// 3) Individual unregister/register

		Assert.assertSame(
			MockSPIAgent.class,
			SPIAgentFactoryUtil.unregisterAgentClass(
				MockSPIAgent.class.getName()));
		Assert.assertNull(
			SPIAgentFactoryUtil.registerAgentClass(MockSPIAgent.class));
		Assert.assertSame(
			MockSPIAgent.class,
			SPIAgentFactoryUtil.unregisterAgentClass(
				MockSPIAgent.class.getName()));
		Assert.assertSame(
			BadMockSPIAgent.class,
			SPIAgentFactoryUtil.unregisterAgentClass(
				BadMockSPIAgent.class.getName()));
	}

	private static Map<String, Class<? extends SPIAgent>>
		_getSpiAgentClassMap() throws Exception {

		Field spiAgentClassMapField = ReflectionUtil.getDeclaredField(
			SPIAgentFactoryUtil.class, "_spiAgentClassMap");

		return (Map<String, Class<? extends SPIAgent>>)
			spiAgentClassMapField.get(null);
	}

	public static class BadMockSPIAgent implements SPIAgent {

		public void destroy() {
			throw new UnsupportedOperationException();
		}

		public void init(SPI spi) throws PortalResiliencyException {
			throw new UnsupportedOperationException();
		}

		public HttpServletRequest prepareRequest(HttpServletRequest request)
			throws IOException {

			throw new UnsupportedOperationException();
		}

		public HttpServletResponse prepareResponse(
			HttpServletRequest request, HttpServletResponse response) {

			throw new UnsupportedOperationException();
		}

		public void service(
				HttpServletRequest request, HttpServletResponse response)
			throws PortalResiliencyException {

			throw new UnsupportedOperationException();
		}

		public void transferResponse(
				HttpServletRequest request, HttpServletResponse response,
				Exception e)
			throws IOException {

			throw new UnsupportedOperationException();
		}

	}

}