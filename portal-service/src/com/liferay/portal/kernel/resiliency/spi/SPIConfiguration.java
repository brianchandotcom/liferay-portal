/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class SPIConfiguration implements Serializable {

	public static final String DEFAULT_JAVA_EXECUTABLE = "java";

	public static final String DEFAULT_JVM_ARGUMENTS =
		"-Xmx1024m -XX:PermSize=200m";

	public static final long DEFAULT_PING_INTERVAL = 5000;

	public static final long DEFAULT_REGISTER_TIMEOUT = 10000;

	public static final long DEFAULT_SHUTDOWN_TIMEOUT = 10000;

	public static SPIConfiguration fromXMLString(String xmlString)
		throws DocumentException {

		Document document = SAXReaderUtil.read(xmlString);

		Element rootElement = document.getRootElement();

		try {
			String id = rootElement.elementText("id");
			String javaExecutable = rootElement.elementText("javaExecutable");
			String jvmArguments = rootElement.elementText("jvmArguments");
			String agentClassName = rootElement.elementText("agentClassName");
			int connectorPort = Integer.parseInt(
				rootElement.elementText("connectorPort"));
			String baseDir = rootElement.elementText("baseDir");
			String[] portletIds = StringUtil.split(
				rootElement.elementText("portletIds"));
			String[] servletContextNames = StringUtil.split(
				rootElement.elementText("servletContextNames"));
			long pingInterval = Long.parseLong(
				rootElement.elementText("pingInterval"));
			long registerTimeout = Long.parseLong(
				rootElement.elementText("registerTimeout"));
			long shutdownTimeout = Long.parseLong(
				rootElement.elementText("shutdownTimeout"));

			return new SPIConfiguration(
				id, javaExecutable, jvmArguments, agentClassName, connectorPort,
				baseDir, portletIds, servletContextNames, pingInterval,
				registerTimeout, shutdownTimeout);
		}
		catch (NumberFormatException nfe) {
			throw new DocumentException(
				"Unable to parse xml to SPIConfiguration", nfe);
		}
	}

	public SPIConfiguration(
		String id, String agentClassName, int connectorPort, String baseDir,
		String[] portletIds, String[] servletContextNames) {

		this(
			id, DEFAULT_JAVA_EXECUTABLE, DEFAULT_JVM_ARGUMENTS, agentClassName,
			connectorPort, baseDir, portletIds, servletContextNames,
			DEFAULT_PING_INTERVAL, DEFAULT_REGISTER_TIMEOUT,
			DEFAULT_SHUTDOWN_TIMEOUT);
	}

	public SPIConfiguration(
		String id, String javaExecutable, String jvmArguments,
		String agentClassName, int connectorPort, String baseDir,
		String[] portletIds, String[] servletContextNames, long pingInterval,
		long registerTimeout, long shutdownTimeout) {

		_id = id;
		_javaExecutable = javaExecutable;
		_jvmArguments = jvmArguments;
		_agentClassName = agentClassName;
		_connectorPort = connectorPort;
		_baseDir = baseDir;
		_portletIds = portletIds;
		_servletContextNames = servletContextNames;
		_pingInterval = pingInterval;
		_registerTimeout = registerTimeout;
		_shutdownTimeout = shutdownTimeout;
	}

	public String getAgentClassName() {
		return _agentClassName;
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public int getConnectorPort() {
		return _connectorPort;
	}

	public String getId() {
		return _id;
	}

	public String getJavaExecutable() {
		return _javaExecutable;
	}

	public List<String> getJVMArguments() {
		return Arrays.asList(StringUtil.split(_jvmArguments, CharPool.SPACE));
	}

	public long getPingInterval() {
		return _pingInterval;
	}

	public String[] getPortletIds() {
		return _portletIds;
	}

	public long getRegisterTimeout() {
		return _registerTimeout;
	}

	public String[] getServletContextNames() {
		return _servletContextNames;
	}

	public long getShutdownTimeout() {
		return _shutdownTimeout;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{id=");
		sb.append(_id);
		sb.append(", javaExecutable=");
		sb.append(_javaExecutable);
		sb.append(", jvmArguments=");
		sb.append(_jvmArguments);
		sb.append(", agentClassName=");
		sb.append(_agentClassName);
		sb.append(", connectorPort=");
		sb.append(_connectorPort);
		sb.append(", baseDir=");
		sb.append(_baseDir);
		sb.append(", portletIds=[");
		sb.append(StringUtil.merge(_portletIds));
		sb.append("], servletContextName=[");
		sb.append(StringUtil.merge(_servletContextNames));
		sb.append("], pingInterval=");
		sb.append(_pingInterval);
		sb.append(", registerTimeout=");
		sb.append(_registerTimeout);
		sb.append(", shutdownTimeout=");
		sb.append(_shutdownTimeout);
		sb.append("}");

		return sb.toString();
	}

	public String toXMLString() {
		com.liferay.portal.kernel.xml.simple.Element element =
			new com.liferay.portal.kernel.xml.simple.Element(
				"SPIConfiguration");

		element.addElement("id", _id);
		element.addElement("javaExecutable", _javaExecutable);
		element.addElement("jvmArguments", _jvmArguments);
		element.addElement("agentClassName", _agentClassName);
		element.addElement("connectorPort", _connectorPort);
		element.addElement("baseDir", _baseDir);
		element.addElement("portletIds", StringUtil.merge(_portletIds));
		element.addElement(
			"servletContextNames", StringUtil.merge(_servletContextNames));
		element.addElement("pingInterval", _pingInterval);
		element.addElement("registerTimeout", _registerTimeout);
		element.addElement("shutdownTimeout", _shutdownTimeout);

		return element.toXMLString();
	}

	private static final long serialVersionUID = 1L;

	private final String _agentClassName;
	private final String _baseDir;
	private final int _connectorPort;
	private final String _id;
	private final String _javaExecutable;
	private final String _jvmArguments;
	private final long _pingInterval;
	private final String[] _portletIds;
	private final long _registerTimeout;
	private final String[] _servletContextNames;
	private final long _shutdownTimeout;

}