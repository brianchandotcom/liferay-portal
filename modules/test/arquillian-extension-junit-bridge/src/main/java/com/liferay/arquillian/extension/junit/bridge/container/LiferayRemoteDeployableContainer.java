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

package com.liferay.arquillian.extension.junit.bridge.container;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Preston Crary
 */
public class LiferayRemoteDeployableContainer {

	public LiferayRemoteDeployableContainer(
			InstanceProducer<MBeanServerConnection>
				mBeanServerConnectionInstanceProducer,
			Archive<?> archive)
		throws Exception {

		_mBeanServerConnectionInstanceProducer =
			mBeanServerConnectionInstanceProducer;
		_archive = archive;

		JMXConnector jmxConnector = JMXConnectorFactory.connect(
			_liferayJMXServiceURL, _liferayEnv);

		MBeanServerConnection mBeanServerConnection =
			jmxConnector.getMBeanServerConnection();

		_mBeanServerConnectionInstanceProducer.set(mBeanServerConnection);

		Set<ObjectName> names = mBeanServerConnection.queryNames(
			_frameworkObjectName, null);

		Iterator<ObjectName> iterator = names.iterator();

		_frameworkMBean = MBeanServerInvocationHandler.newProxyInstance(
			mBeanServerConnection, iterator.next(), FrameworkMBean.class,
			false);

		_bundleId = _installBundle(_archive);

		_frameworkMBean.startBundle(_bundleId);
	}

	public void undeploy() throws IOException {
		_frameworkMBean.uninstallBundle(_bundleId);
	}

	private long _installBundle(Archive<?> archive) throws Exception {
		Path tempFilePath = Files.createTempFile(null, ".jar");

		ZipExporter zipExporter = archive.as(ZipExporter.class);

		try (InputStream inputStream = zipExporter.exportAsInputStream()) {
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

	private static final ObjectName _frameworkObjectName;
	private static final Map<String, String[]> _liferayEnv =
		Collections.singletonMap(
			JMXConnector.CREDENTIALS, new String[] {"", ""});
	private static final JMXServiceURL _liferayJMXServiceURL;

	static {
		try {
			_frameworkObjectName = new ObjectName("osgi.core:type=framework,*");

			_liferayJMXServiceURL = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:8099/jmxrmi");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final Archive<?> _archive;
	private final long _bundleId;
	private final FrameworkMBean _frameworkMBean;
	private final InstanceProducer<MBeanServerConnection>
		_mBeanServerConnectionInstanceProducer;

}