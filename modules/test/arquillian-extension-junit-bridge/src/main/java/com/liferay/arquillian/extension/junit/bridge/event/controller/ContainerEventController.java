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

package com.liferay.arquillian.extension.junit.bridge.event.controller;

import com.liferay.arquillian.extension.junit.bridge.deployment.BndDeploymentDescriptionUtil;
import com.liferay.arquillian.extension.junit.bridge.remote.manager.Instance;

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

import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Matthew Tambara
 */
public class ContainerEventController {

	public void execute(@Observes AfterClass afterClass) throws Exception {
		_frameworkMBean.uninstallBundle(_bundleId);
	}

	public void execute(@Observes BeforeClass beforeClass) throws Exception {
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

		_bundleId = _installBundle(
			BndDeploymentDescriptionUtil.create(beforeClass.getTestClass()));

		_frameworkMBean.startBundle(_bundleId);
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

	private long _bundleId;
	private FrameworkMBean _frameworkMBean;

	@ApplicationScoped
	@Inject
	private Instance<MBeanServerConnection>
		_mBeanServerConnectionInstanceProducer;

}