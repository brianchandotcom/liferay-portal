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

package com.liferay.arquillian.extension.junit.bridge.container.remote;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.protocol.ProtocolDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.HTTPContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.JMXContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.descriptor.api.Descriptor;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Preston Crary
 */
public class LiferayRemoteDeployableContainer
	implements DeployableContainer<DefaultContainerConfiguration> {

	@Override
	public ProtocolMetaData deploy(Archive<?> archive)
		throws DeploymentException {

		try {
			long bundleId = _installBundle(archive);

			_frameworkMBean.startBundle(bundleId);

			_deployedBundles.put(archive.getName(), bundleId);
		}
		catch (Exception e) {
			throw new DeploymentException(
				"Cannot deploy: " + archive.getName(), e);
		}

		try {
			MBeanServerConnection mBeanServerConnection =
				_getMBeanServerConnection();

			ProtocolMetaData protocolMetaData = new ProtocolMetaData();

			protocolMetaData.addContext(new JMXContext(mBeanServerConnection));

			protocolMetaData.addContext(
				new HTTPContext(
					_LIFERAY_DEFAULT_HTTP_HOST, _LIFERAY_DEFAULT_HTTP_PORT));

			return protocolMetaData;
		}
		catch (IOException ioe) {
			throw new DeploymentException("Unable to deploy " + archive, ioe);
		}
	}

	@Override
	public void deploy(Descriptor descriptor) {
	}

	@Override
	public Class<DefaultContainerConfiguration> getConfigurationClass() {
		return DefaultContainerConfiguration.class;
	}

	@Override
	public ProtocolDescription getDefaultProtocol() {
		return _protocolDescription;
	}

	@Override
	public void setup(
		DefaultContainerConfiguration defaultContainerConfiguration) {
	}

	@Override
	public void start() throws LifecycleException {
		try {
			MBeanServerConnection mBeanServer = _getMBeanServerConnection();

			Set<ObjectName> names = mBeanServer.queryNames(
				_frameworkObjectName, null);

			Iterator<ObjectName> iterator = names.iterator();

			_frameworkMBean = MBeanServerInvocationHandler.newProxyInstance(
				mBeanServer, iterator.next(), FrameworkMBean.class, false);
		}
		catch (IOException ioe) {
			throw new LifecycleException("Unable to start", ioe);
		}
	}

	@Override
	public void stop() {
	}

	@Override
	public void undeploy(Archive<?> archive) {
		long bundleId = _deployedBundles.remove(archive.getName());

		if (bundleId == 0) {
			return;
		}

		try {
			_frameworkMBean.uninstallBundle(bundleId);
		}
		catch (IOException ioe) {
		}
	}

	@Override
	public void undeploy(Descriptor desc) {
	}

	private MBeanServerConnection _getMBeanServerConnection()
		throws IOException {

		String[] credentials = {
			_LIFERAY_DEFAULT_JMX_USERNAME, _LIFERAY_DEFAULT_JMX_PASSWORD
		};

		Map<String, String[]> env = Collections.singletonMap(
			JMXConnector.CREDENTIALS, credentials);

		JMXServiceURL jmxServiceURL = new JMXServiceURL(
			_LIFERAY_DEFAULT_JMX_SERVICE_URL);

		JMXConnector connector = JMXConnectorFactory.connect(
			jmxServiceURL, env);

		return connector.getMBeanServerConnection();
	}

	private long _installBundle(Archive<?> archive) throws Exception {
		Path tempFilePath = Files.createTempFile(null, ".jar");

		ZipExporter exporter = archive.as(ZipExporter.class);

		try (InputStream inputStream = exporter.exportAsInputStream()) {
			Files.copy(
				inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
		}

		URI uri = tempFilePath.toUri();

		URL url = uri.toURL();

		try {
			return _frameworkMBean.installBundleFromURL(
				archive.getName(), url.toExternalForm());
		}
		finally {
			Files.delete(tempFilePath);
		}
	}

	private static final String _LIFERAY_DEFAULT_HTTP_HOST = "localhost";

	private static final int _LIFERAY_DEFAULT_HTTP_PORT = 8080;

	private static final String _LIFERAY_DEFAULT_JMX_PASSWORD = "";

	private static final String _LIFERAY_DEFAULT_JMX_SERVICE_URL =
		"service:jmx:rmi:///jndi/rmi://localhost:8099/jmxrmi";

	private static final String _LIFERAY_DEFAULT_JMX_USERNAME = "";

	private static final ObjectName _frameworkObjectName;
	private static final ProtocolDescription _protocolDescription =
		new ProtocolDescription("jmx-osgi");

	static {
		try {
			_frameworkObjectName = new ObjectName("osgi.core:type=framework,*");
		}
		catch (MalformedObjectNameException mone) {
			throw new ExceptionInInitializerError(mone);
		}
	}

	private final Map<String, Long> _deployedBundles = new HashMap<>();
	private FrameworkMBean _frameworkMBean;

}