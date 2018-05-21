/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.util;

/**
 * @author Michael C. Han
 */
public class SPIConfigurationTemplate {

	public static int getConnectorPortMax() {
		return _connectorPortMax;
	}

	public static int getConnectorPortMin() {
		return _connectorPortMin;
	}

	public static String getJavaExecutable() {
		return _javaExecutable;
	}

	public static String getJVMArguments() {
		return _jvmArguments;
	}

	public static int getMaxThreads() {
		return _maxThreads;
	}

	public static int getMinThreads() {
		return _minThreads;
	}

	public static String getSPIAgentClassName() {
		return _spiAgentClassName;
	}

	public static long getSPIPingInterval() {
		return _spiPingInterval;
	}

	public static String getSPIPortalProperties() {
		return _spiPortalProperties;
	}

	public static String getSPIProviderName() {
		return _spiProviderName;
	}

	public static long getSPIRegisterTimeout() {
		return _spiRegisterTimeout;
	}

	public static long getSPIShutdownTimeout() {
		return _spiShutdownTimeout;
	}

	public void setConnectorPortMax(int connectorPortMax) {
		_connectorPortMax = connectorPortMax;
	}

	public void setConnectorPortMin(int connectorPortMin) {
		_connectorPortMin = connectorPortMin;
	}

	public void setJavaExecutable(String javaExecutable) {
		_javaExecutable = javaExecutable;
	}

	public void setJVMArguments(String jvmArguments) {
		_jvmArguments = jvmArguments;
	}

	public void setMaxThreads(int maxThreads) {
		_maxThreads = maxThreads;
	}

	public void setMinThreads(int minThreads) {
		_minThreads = minThreads;
	}

	public void setSPIAgentClassName(String spiAgentClassName) {
		_spiAgentClassName = spiAgentClassName;
	}

	public void setSPIPingInterval(long spiPingInterval) {
		_spiPingInterval = spiPingInterval;
	}

	public void setSPIPortalProperties(String spiPortalProperties) {
		_spiPortalProperties = spiPortalProperties;
	}

	public void setSPIProviderName(String spiProviderName) {
		_spiProviderName = spiProviderName;
	}

	public void setSPIRegisterTimeout(long spiRegisterTimeout) {
		_spiRegisterTimeout = spiRegisterTimeout;
	}

	public void setSPIShutdownTimeout(long spiShutdownTimeout) {
		_spiShutdownTimeout = spiShutdownTimeout;
	}

	private static int _connectorPortMax;
	private static int _connectorPortMin;
	private static String _javaExecutable;
	private static String _jvmArguments;
	private static int _maxThreads;
	private static int _minThreads;
	private static String _spiAgentClassName;
	private static long _spiPingInterval;
	private static String _spiPortalProperties;
	private static String _spiProviderName;
	private static long _spiRegisterTimeout;
	private static long _spiShutdownTimeout;

}