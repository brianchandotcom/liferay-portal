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
import com.liferay.portal.kernel.util.GetterUtil;
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

	public static final String JAVA_EXECUTABLE_DEFAULT = "java";

	public static final String JVM_ARGUMENTS_DEFAULT =
		"-Xmx1024m -XX:PermSize=200m";

	public static final long PING_INTERVAL_DEFAULT = 5000;

	public static final long REGISTER_TIMEOUT_DEFAULT = 10000;

	public static final long SHUTDOWN_TIMEOUT_DEFAULT = 10000;

	public static SPIConfiguration fromXMLString(String xmlString)
		throws DocumentException {

		Document document = SAXReaderUtil.read(xmlString);

		Element rootElement = document.getRootElement();

		String id = rootElement.elementText("id");
		String javaExecutable = rootElement.elementText("javaExecutable");
		String jvmArguments = rootElement.elementText("jvmArguments");
		String spiAgentClassName = rootElement.elementText("spiAgentClassName");
		int connectorPort = GetterUtil.getIntegerStrict(
			rootElement.elementText("connectorPort"));
		String baseDir = rootElement.elementText("baseDir");
		String[] portletIds = StringUtil.split(
			rootElement.elementText("portletIds"));
		String[] servletContextNames = StringUtil.split(
			rootElement.elementText("servletContextNames"));
		long pingInterval = GetterUtil.getLongStrict(
			rootElement.elementText("pingInterval"));
		long registerTimeout = GetterUtil.getLongStrict(
			rootElement.elementText("registerTimeout"));
		long shutdownTimeout = GetterUtil.getLongStrict(
			rootElement.elementText("shutdownTimeout"));

		return new SPIConfiguration(
			id, javaExecutable, jvmArguments, spiAgentClassName, connectorPort,
			baseDir, portletIds, servletContextNames, pingInterval,
			registerTimeout, shutdownTimeout);
	}

	public SPIConfiguration(
		String spiId, String spiAgentClassName, int connectorPort,
		String baseDir, String[] portletIds, String[] servletContextNames) {

		this(
			spiId, JAVA_EXECUTABLE_DEFAULT, JVM_ARGUMENTS_DEFAULT,
			spiAgentClassName, connectorPort, baseDir, portletIds,
			servletContextNames, PING_INTERVAL_DEFAULT,
			REGISTER_TIMEOUT_DEFAULT, SHUTDOWN_TIMEOUT_DEFAULT);
	}

	public SPIConfiguration(
		String spiId, String javaExecutable, String jvmArguments,
		String spiAgentClassName, int connectorPort, String baseDir,
		String[] portletIds, String[] servletContextNames, long pingInterval,
		long registerTimeout, long shutdownTimeout) {

		_spiId = spiId;
		_javaExecutable = javaExecutable;
		_jvmArguments = jvmArguments;
		_spiAgentClassName = spiAgentClassName;
		_connectorPort = connectorPort;
		_baseDir = baseDir;
		_portletIds = portletIds;
		_servletContextNames = servletContextNames;
		_pingInterval = pingInterval;
		_registerTimeout = registerTimeout;
		_shutdownTimeout = shutdownTimeout;
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public int getConnectorPort() {
		return _connectorPort;
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

	public String getSPIAgentClassName() {
		return _spiAgentClassName;
	}

	public String getSPIId() {
		return _spiId;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{baseDir=");
		sb.append(_baseDir);
		sb.append(", connectorPort=");
		sb.append(_connectorPort);
		sb.append(", javaExecutable=");
		sb.append(_javaExecutable);
		sb.append(", jvmArguments=");
		sb.append(_jvmArguments);
		sb.append(", pingInterval=");
		sb.append(_pingInterval);
		sb.append(", portletIds=[");
		sb.append(StringUtil.merge(_portletIds));
		sb.append("], registerTimeout=");
		sb.append(_registerTimeout);
		sb.append(", servletContextName=[");
		sb.append(StringUtil.merge(_servletContextNames));
		sb.append("], shutdownTimeout=");
		sb.append(_shutdownTimeout);
		sb.append(", spiAgentClassName=");
		sb.append(_spiAgentClassName);
		sb.append(", spiId=");
		sb.append(_spiId);
		sb.append("}");

		return sb.toString();
	}

	public String toXMLString() {
		com.liferay.portal.kernel.xml.simple.Element element =
			new com.liferay.portal.kernel.xml.simple.Element(
				"SPIConfiguration");

		element.addElement("id", _spiId);
		element.addElement("javaExecutable", _javaExecutable);
		element.addElement("jvmArguments", _jvmArguments);
		element.addElement("spiAgentClassName", _spiAgentClassName);
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

	private String _baseDir;
	private int _connectorPort;
	private String _javaExecutable;
	private String _jvmArguments;
	private long _pingInterval;
	private String[] _portletIds;
	private long _registerTimeout;
	private String[] _servletContextNames;
	private long _shutdownTimeout;
	private String _spiAgentClassName;
	private String _spiId;

}