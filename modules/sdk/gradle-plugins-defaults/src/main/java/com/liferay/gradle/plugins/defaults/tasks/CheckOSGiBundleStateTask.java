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

package com.liferay.gradle.plugins.defaults.tasks;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.util.Set;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.CollectionUtils;

import org.osgi.jmx.framework.BundleStateMBean;

/**
 * @author Andrea Di Giorgi
 */
public class CheckOSGiBundleStateTask extends DefaultTask {

	@TaskAction
	public void checkOSGiBundleState() throws Exception {
		String bundleState = getBundleState();

		String bundleSymbolicName = getBundleSymbolicName();

		boolean singleBundle = Validator.isNotNull(bundleSymbolicName);

		JMXServiceURL jmxServiceURL = new JMXServiceURL(
			"service:jmx:rmi:///jndi/rmi://" + getJmxHostName() + ":" +
				getJmxPort() + "/jmxrmi");

		try (JMXConnector jmxConnector = JMXConnectorFactory.connect(
				jmxServiceURL)) {

			MBeanServerConnection mBeanServerConnection =
				jmxConnector.getMBeanServerConnection();

			Set<ObjectName> objectNames = mBeanServerConnection.queryNames(
				ObjectName.getInstance(BundleStateMBean.OBJECTNAME + ",*"),
				null);

			BundleStateMBean bundleStateMBean = JMX.newMBeanProxy(
				mBeanServerConnection, CollectionUtils.first(objectNames),
				BundleStateMBean.class);

			TabularData tabularData = bundleStateMBean.listBundles();

			for (Object object : tabularData.values()) {
				CompositeData compositeData = (CompositeData)object;

				boolean fragment = (Boolean)compositeData.get(
					BundleStateMBean.FRAGMENT);

				if (fragment) {
					continue;
				}

				String symbolicName = (String)compositeData.get(
					BundleStateMBean.SYMBOLIC_NAME);

				if (singleBundle && !bundleSymbolicName.equals(symbolicName)) {
					continue;
				}

				String state = (String)compositeData.get(
					BundleStateMBean.STATE);

				if (!bundleState.equals(state)) {
					throw new GradleException(
						"Bundle \"" + symbolicName + "\" is " + state +
							" while it should be " + bundleState);
				}

				if (singleBundle) {
					return;
				}
			}
		}

		Logger logger = getLogger();

		if (singleBundle) {
			if (logger.isWarnEnabled()) {
				logger.warn(
					"Bundle \"{}\" is a fragment or is not deployed",
					bundleSymbolicName);
			}
		}
		else {
			if (logger.isInfoEnabled()) {
				logger.info(
					"All non-fragment bundles have {} state", bundleState);
			}
		}
	}

	@Input
	public String getBundleState() {
		return GradleUtil.toString(_bundleState);
	}

	@Input
	@Optional
	public String getBundleSymbolicName() {
		return GradleUtil.toString(_bundleSymbolicName);
	}

	@Input
	public String getJmxHostName() {
		return GradleUtil.toString(_jmxHostName);
	}

	@Input
	public int getJmxPort() {
		return GradleUtil.toInteger(_jmxPort);
	}

	public void setBundleState(Object bundleState) {
		_bundleState = bundleState;
	}

	public void setBundleSymbolicName(Object bundleSymbolicName) {
		_bundleSymbolicName = bundleSymbolicName;
	}

	public void setJmxHostName(Object jmxHostName) {
		_jmxHostName = jmxHostName;
	}

	public void setJmxPort(Object jmxPort) {
		_jmxPort = jmxPort;
	}

	private Object _bundleState = BundleStateMBean.ACTIVE;
	private Object _bundleSymbolicName;
	private Object _jmxHostName = "localhost";
	private Object _jmxPort = 8099;

}