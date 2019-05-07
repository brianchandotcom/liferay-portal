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

package com.liferay.portal.kernel.resiliency.spi.agent;

import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SPIAgentFactoryUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testCreateSPIAgent() {
		SPIAgentFactoryUtil.registerSPIAgentClass(BadMockSPIAgent.class);
		SPIAgentFactoryUtil.registerSPIAgentClass(MockSPIAgent.class);

		// Null name

		try {
			SPIAgentFactoryUtil.createSPIAgent(
				new SPIConfiguration(
					"testId", null, 8081, "", new String[0], new String[0],
					null),
				null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// Bad name

		String badName = "badName";

		try {
			SPIAgentFactoryUtil.createSPIAgent(
				new SPIConfiguration(
					"testId", badName, 8081, "", new String[0], new String[0],
					null),
				null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Unkown SPI agent class name " + badName, iae.getMessage());
		}

		// Bad SPIAgent class

		try {
			SPIAgentFactoryUtil.createSPIAgent(
				new SPIConfiguration(
					"testId", BadMockSPIAgent.class.getName(), 8081, "",
					new String[0], new String[0], null),
				null);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"Unable to instantiate " + BadMockSPIAgent.class,
				re.getMessage());
		}

		// Success creation

		SPIAgent spiAgent = SPIAgentFactoryUtil.createSPIAgent(
			new SPIConfiguration(
				"testId", MockSPIAgent.class.getName(), 8081, "", new String[0],
				new String[0], null),
			null);

		Assert.assertSame(MockSPIAgent.class, spiAgent.getClass());
	}

	@Test
	public void testRegisteration() throws ClassNotFoundException {

		// Spring registeration

		SPIAgentFactoryUtil spiAgentFactoryUtil = new SPIAgentFactoryUtil();

		Set<String> agentClassNames = new HashSet<>();

		agentClassNames.add(BadMockSPIAgent.class.getName());
		agentClassNames.add(MockSPIAgent.class.getName());

		spiAgentFactoryUtil.setSPIAgentClasses(agentClassNames);

		Map<String, Class<? extends SPIAgent>> spiAgentClasses =
			ReflectionTestUtil.getFieldValue(
				SPIAgentFactoryUtil.class, "_spiAgentClasses");

		Assert.assertEquals(
			spiAgentClasses.toString(), 2, spiAgentClasses.size());
		Assert.assertSame(
			BadMockSPIAgent.class,
			spiAgentClasses.get(BadMockSPIAgent.class.getName()));
		Assert.assertSame(
			MockSPIAgent.class,
			spiAgentClasses.get(MockSPIAgent.class.getName()));

		// Get names

		Set<String> spiAgentClassNames =
			SPIAgentFactoryUtil.getSPIAgentClassNames();

		Assert.assertEquals(
			spiAgentClassNames.toString(), 2, spiAgentClassNames.size());
		Assert.assertTrue(
			spiAgentClassNames.toString(),
			spiAgentClassNames.contains(BadMockSPIAgent.class.getName()));
		Assert.assertTrue(
			spiAgentClassNames.toString(),
			spiAgentClassNames.contains(MockSPIAgent.class.getName()));

		// Individual unregister and register

		Assert.assertSame(
			MockSPIAgent.class,
			SPIAgentFactoryUtil.unregisterSPIAgentClass(
				MockSPIAgent.class.getName()));
		Assert.assertNull(
			SPIAgentFactoryUtil.registerSPIAgentClass(MockSPIAgent.class));
		Assert.assertSame(
			BadMockSPIAgent.class,
			SPIAgentFactoryUtil.unregisterSPIAgentClass(
				BadMockSPIAgent.class.getName()));
		Assert.assertSame(
			MockSPIAgent.class,
			SPIAgentFactoryUtil.unregisterSPIAgentClass(
				MockSPIAgent.class.getName()));
	}

	public static class BadMockSPIAgent implements SPIAgent {

		@Override
		public void destroy() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void init(SPI spi) {
			throw new UnsupportedOperationException();
		}

		@Override
		public HttpServletRequest prepareRequest(
			HttpServletRequest httpServletRequest) {

			throw new UnsupportedOperationException();
		}

		@Override
		public HttpServletResponse prepareResponse(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void transferResponse(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception e) {

			throw new UnsupportedOperationException();
		}

	}

}