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

package com.liferay.portal.kernel.resiliency.spi;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SPIConfigurationTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

		PropsUtil.setProps(new PropsImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReaderImpl = new SAXReaderImpl();

		secureSAXReaderImpl.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReaderImpl);

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		SAXReaderImpl unsecureSAXReaderImpl = new SAXReaderImpl();

		unsecureSAXReaderUtil.setSAXReader(unsecureSAXReaderImpl);
	}

	@Test
	public void testCreationWithCustomValues() {
		String spiId = "spiId";
		String javaExecutable = "/usr/bin/java";
		String jvmArguments = "-Xmx2048m -XX:PermSize=256m";
		String spiAgentClassName = "spiAgentClassName";
		int connectorPort = 8080;
		String baseDir = "baseDir";
		String[] portletIds = {"portlet1", "portlet2"};
		String[] servletContextNames = {"app1", "app2"};
		long pingInterval = 1000;
		long registerTimeout = 5000;
		long shutdownTimeout = 5000;
		String extraSettings = "key1=value1\nkey2=values";

		SPIConfiguration spiConfiguration = new SPIConfiguration(
			spiId, javaExecutable, jvmArguments, spiAgentClassName,
			connectorPort, baseDir, portletIds, servletContextNames,
			pingInterval, registerTimeout, shutdownTimeout, extraSettings);

		Assert.assertEquals(spiId, spiConfiguration.getSPIId());
		Assert.assertEquals(
			javaExecutable, spiConfiguration.getJavaExecutable());
		Assert.assertEquals(
			Arrays.asList(StringUtil.split(jvmArguments, CharPool.SPACE)),
			spiConfiguration.getJVMArguments());
		Assert.assertEquals(
			spiAgentClassName, spiConfiguration.getSPIAgentClassName());
		Assert.assertEquals(connectorPort, spiConfiguration.getConnectorPort());
		Assert.assertEquals(baseDir, spiConfiguration.getBaseDir());
		Assert.assertSame(portletIds, spiConfiguration.getPortletIds());
		Assert.assertSame(
			servletContextNames, spiConfiguration.getServletContextNames());
		Assert.assertEquals(pingInterval, spiConfiguration.getPingInterval());
		Assert.assertEquals(
			registerTimeout, spiConfiguration.getRegisterTimeout());
		Assert.assertEquals(
			shutdownTimeout, spiConfiguration.getShutdownTimeout());
		Assert.assertEquals(extraSettings, spiConfiguration.getExtraSettings());

		StringBundler sb = new StringBundler(7);

		sb.append("{baseDir=baseDir, connectorPort=8080, ");
		sb.append("extraSettings=key1=value1\nkey2=values, ");
		sb.append("javaExecutable=/usr/bin/java, jvmArguments=-Xmx2048m ");
		sb.append("-XX:PermSize=256m, pingInterval=1000, ");
		sb.append("portletIds=[portlet1,portlet2], registerTimeout=5000, ");
		sb.append("servletContextName=[app1,app2], shutdownTimeout=5000, ");
		sb.append("spiAgentClassName=spiAgentClassName, spiId=spiId}");

		Assert.assertEquals(sb.toString(), spiConfiguration.toString());

		sb = new StringBundler(14);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<SPIConfiguration><id>spiId</id>");
		sb.append("<javaExecutable>/usr/bin/java</javaExecutable>");
		sb.append("<jvmArguments>-Xmx2048m -XX:PermSize=256m</jvmArguments>");
		sb.append("<spiAgentClassName>spiAgentClassName</spiAgentClassName>");
		sb.append("<connectorPort>8080</connectorPort>");
		sb.append("<baseDir>baseDir</baseDir>");
		sb.append("<portletIds>portlet1,portlet2</portletIds>");
		sb.append("<servletContextNames>app1,app2</servletContextNames>");
		sb.append("<pingInterval>1000</pingInterval>");
		sb.append("<registerTimeout>5000</registerTimeout>");
		sb.append("<shutdownTimeout>5000</shutdownTimeout>");
		sb.append("<extraSettings>key1=value1\nkey2=values</extraSettings>");
		sb.append("</SPIConfiguration>");

		Assert.assertEquals(sb.toString(), spiConfiguration.toXMLString());
	}

	@Test
	public void testCreationWithDefaultValues() {
		String spiId = "spiId";
		String spiAgentClassName = "spiAgentClassName";
		int connectorPort = 8080;
		String baseDir = "baseDir";
		String[] portletIds = {"portlet1", "portlet2"};
		String[] servletContextNames = {"app1", "app2"};
		String extraSettings = "key1=value1\nkey2=values";

		SPIConfiguration spiConfiguration = new SPIConfiguration(
			spiId, spiAgentClassName, connectorPort, baseDir, portletIds,
			servletContextNames, extraSettings);

		Assert.assertEquals(spiId, spiConfiguration.getSPIId());
		Assert.assertEquals(
			SPIConfiguration.JAVA_EXECUTABLE_DEFAULT,
			spiConfiguration.getJavaExecutable());
		Assert.assertEquals(
			Arrays.asList(
				StringUtil.split(
					SPIConfiguration.JVM_ARGUMENTS_DEFAULT, CharPool.SPACE)),
			spiConfiguration.getJVMArguments());
		Assert.assertEquals(
			spiAgentClassName, spiConfiguration.getSPIAgentClassName());
		Assert.assertEquals(connectorPort, spiConfiguration.getConnectorPort());
		Assert.assertEquals(baseDir, spiConfiguration.getBaseDir());
		Assert.assertSame(portletIds, spiConfiguration.getPortletIds());
		Assert.assertSame(
			servletContextNames, spiConfiguration.getServletContextNames());
		Assert.assertEquals(
			SPIConfiguration.PING_INTERVAL_DEFAULT,
			spiConfiguration.getPingInterval());
		Assert.assertEquals(
			SPIConfiguration.REGISTER_TIMEOUT_DEFAULT,
			spiConfiguration.getRegisterTimeout());
		Assert.assertEquals(
			SPIConfiguration.SHUTDOWN_TIMEOUT_DEFAULT,
			spiConfiguration.getShutdownTimeout());
		Assert.assertEquals(extraSettings, spiConfiguration.getExtraSettings());

		StringBundler sb = new StringBundler(7);

		sb.append("{baseDir=baseDir, connectorPort=8080, ");
		sb.append("extraSettings=key1=value1\nkey2=values, ");
		sb.append("javaExecutable=java, jvmArguments=-Xmx1024m ");
		sb.append("-XX:PermSize=200m, pingInterval=5000, ");
		sb.append("portletIds=[portlet1,portlet2], registerTimeout=10000, ");
		sb.append("servletContextName=[app1,app2], shutdownTimeout=10000, ");
		sb.append("spiAgentClassName=spiAgentClassName, spiId=spiId}");

		Assert.assertEquals(sb.toString(), spiConfiguration.toString());

		sb = new StringBundler(14);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<SPIConfiguration><id>spiId</id>");
		sb.append("<javaExecutable>java</javaExecutable>");
		sb.append("<jvmArguments>-Xmx1024m -XX:PermSize=200m</jvmArguments>");
		sb.append("<spiAgentClassName>spiAgentClassName</spiAgentClassName>");
		sb.append("<connectorPort>8080</connectorPort>");
		sb.append("<baseDir>baseDir</baseDir>");
		sb.append("<portletIds>portlet1,portlet2</portletIds>");
		sb.append("<servletContextNames>app1,app2</servletContextNames>");
		sb.append("<pingInterval>5000</pingInterval>");
		sb.append("<registerTimeout>10000</registerTimeout>");
		sb.append("<shutdownTimeout>10000</shutdownTimeout>");
		sb.append("<extraSettings>key1=value1\nkey2=values</extraSettings>");
		sb.append("</SPIConfiguration>");

		Assert.assertEquals(sb.toString(), spiConfiguration.toXMLString());
	}

	@Test
	public void testFromXMLString() throws DocumentException {
		StringBundler sb = new StringBundler(14);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<SPIConfiguration><id>spiId</id>");
		sb.append("<javaExecutable>/opt/jvm/bin/java</javaExecutable>");
		sb.append("<jvmArguments>-Xmx512m -XX:PermSize=128m</jvmArguments>");
		sb.append("<spiAgentClassName>spiAgentClassName</spiAgentClassName>");
		sb.append("<connectorPort>8081</connectorPort>");
		sb.append("<baseDir>baseDir</baseDir>");
		sb.append("<portletIds>portlet1,portlet2,portlet3</portletIds>");
		sb.append("<servletContextNames>app1,app2,app3</servletContextNames>");
		sb.append("<pingInterval>1000</pingInterval>");
		sb.append("<registerTimeout>1000</registerTimeout>");
		sb.append("<shutdownTimeout>1000</shutdownTimeout>");
		sb.append("<extraSettings>key1=value1\nkey2=values</extraSettings>");
		sb.append("</SPIConfiguration>");

		SPIConfiguration spiConfiguration = SPIConfiguration.fromXMLString(
			sb.toString());

		Assert.assertEquals("spiId", spiConfiguration.getSPIId());
		Assert.assertEquals(
			"/opt/jvm/bin/java", spiConfiguration.getJavaExecutable());
		Assert.assertEquals(
			Arrays.asList("-Xmx512m", "-XX:PermSize=128m"),
			spiConfiguration.getJVMArguments());
		Assert.assertEquals(
			"spiAgentClassName", spiConfiguration.getSPIAgentClassName());
		Assert.assertEquals(8081, spiConfiguration.getConnectorPort());
		Assert.assertEquals("baseDir", spiConfiguration.getBaseDir());
		Assert.assertArrayEquals(
			new String[] {"portlet1", "portlet2", "portlet3"},
			spiConfiguration.getPortletIds());
		Assert.assertArrayEquals(
			new String[] {"app1", "app2", "app3"},
			spiConfiguration.getServletContextNames());
		Assert.assertEquals(1000, spiConfiguration.getPingInterval());

		sb.setStringAt("<connectorPort>808X</connectorPort>", 5);

		try {
			SPIConfiguration.fromXMLString(sb.toString());
		}
		catch (NumberFormatException nfe) {
			Assert.assertEquals("Unable to parse 808X", nfe.getMessage());
		}
	}

}