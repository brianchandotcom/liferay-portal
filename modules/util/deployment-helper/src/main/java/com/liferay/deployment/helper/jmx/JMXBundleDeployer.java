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

package com.liferay.deployment.helper.jmx;

import com.liferay.deployment.helper.util.DeploymentHelperUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.lang.management.ManagementFactory;

import java.net.URI;
import java.net.URL;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

/**
 * @author Greg Amerson
 * @author David Truong
 */
public class JMXBundleDeployer {

	public JMXBundleDeployer() {
		_mBeanServerConnection = ManagementFactory.getPlatformMBeanServer();
	}

	public String deploy(File file) throws Exception {
		String fileName = file.getName();

		if (fileName.endsWith(".cfg") || fileName.endsWith(".config")) {
			return deployConfig(file);
		}

		return deployPlugin(file);
	}

	public String deployConfig(File file) throws Exception {
		ObjectName mBean = _getMBean(_OBJECT_NAME_CONFIG_ADMIN);

		CompositeType compositeType = new CompositeType(
			"PROPERTY", "X", new String[] {"Key", "Value", "Type"},
			new String[] {"X", "X", "X"},
			new OpenType<?>[] {
				SimpleType.STRING, SimpleType.STRING, SimpleType.STRING
			});

		TabularType tabularType = new TabularType(
			"PROPERTIES", "X", compositeType, new String[] {"Key"});

		TabularData tabularData = new TabularDataSupport(tabularType);

		Properties properties = new Properties();

		properties.load(new FileInputStream(file));

		for (Object key : properties.keySet()) {
			Map<String, Object> data = new HashMap<>();

			data.put("Key", key);
			data.put("Type", "String");
			data.put("Value", properties.getProperty((String)key));

			CompositeData compositeData = new CompositeDataSupport(
				compositeType, data);

			tabularData.put(compositeData);
		}

		String fileName = file.getName();

		String factoryPid = fileName.substring(0, fileName.lastIndexOf("-"));

		String pid = (String)_mBeanServerConnection.invoke(
			mBean, "createFactoryConfiguration", new Object[] {factoryPid},
			new String[] {String.class.getName()});

		_mBeanServerConnection.invoke(
			mBean, "update", new Object[] {pid, tabularData},
			new String[] {String.class.getName(), TabularData.class.getName()});

		return pid;
	}

	public String deployPlugin(File file) throws Exception {
		String fileName = file.getName();

		URI uri = file.toURI();

		URL url = uri.toURL();

		if (fileName.endsWith(".war")) {
			url = DeploymentHelperUtil.getWebBundleURL(url);
		}

		ObjectName mBean = _getMBean(_OBJECT_NAME_FRAMEWORK);

		Object installedBundle = _mBeanServerConnection.invoke(
			mBean, "installBundleFromURL",
			new Object[] {file.getAbsolutePath(), url.toExternalForm()},
			new String[] {String.class.getName(), String.class.getName()});

		String id = installedBundle.toString();

		if (!id.equals("-1")) {
			_mBeanServerConnection.invoke(
				mBean, "startBundle", new Object[] {installedBundle},
				new String[] {"long"});
		}

		return id;
	}

	public void uninstall(String id) throws Exception {
		try {
			uninstallPlugin(Long.parseLong(id));
		}
		catch (Exception e) {
			uninstallConfig(id);
		}
	}

	public void uninstallConfig(String id) throws Exception {
		ObjectName mBean = _getMBean(_OBJECT_NAME_CONFIG_ADMIN);

		_mBeanServerConnection.invoke(
			mBean, "delete", new Object[] {id},
			new String[] {String.class.getName()});
	}

	public void uninstallPlugin(long id) throws Exception {
		ObjectName mBean = _getMBean(_OBJECT_NAME_FRAMEWORK);

		_mBeanServerConnection.invoke(
			mBean, "uninstallBundle", new Object[] {id}, new String[] {"long"});
	}

	private ObjectName _getMBean(String name)
		throws IOException, MalformedObjectNameException {

		ObjectName objectName = new ObjectName(name);

		Set<ObjectName> objectNames = _mBeanServerConnection.queryNames(
			objectName, null);

		if ((objectNames != null) && !objectNames.isEmpty()) {
			Iterator<ObjectName> iterator = objectNames.iterator();

			return iterator.next();
		}

		return null;
	}

	private static final String _OBJECT_NAME_CONFIG_ADMIN =
		"osgi.compendium:service=cm,*";

	private static final String _OBJECT_NAME_FRAMEWORK =
		"osgi.core:type=framework,*";

	private final MBeanServerConnection _mBeanServerConnection;

}