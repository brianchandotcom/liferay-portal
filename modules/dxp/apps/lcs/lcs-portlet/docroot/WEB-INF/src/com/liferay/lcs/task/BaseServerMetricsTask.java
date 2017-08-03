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

package com.liferay.lcs.task;

import com.liferay.lcs.management.MBeanServerService;
import com.liferay.lcs.messaging.MetricsMessage;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.pool.metrics.ConnectionPoolMetrics;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.ObjectName;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Riccardo Ferrari
 * @author Mladen Cikara
 */
public abstract class BaseServerMetricsTask implements ServerMetricsTask {

	public void afterPropertiesSet() {
		try {
			Bundle bundle = FrameworkUtil.getBundle(getClass());

			BundleContext bundleContext = bundle.getBundleContext();

			Collection<ServiceReference<ConnectionPoolMetrics>>
				serviceReferences = bundleContext.getServiceReferences(
					ConnectionPoolMetrics.class, null);

			for (ServiceReference<ConnectionPoolMetrics> serviceReference :
					serviceReferences) {

				_connectionPoolMetrics.add(
					bundleContext.getService(serviceReference));
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("JDBC connection pools metrics is not available");
			}

			_currentThreadsMetricsEnabled = false;

			_log.error(e, e);
		}

		try {
			setUpCurrentThreadsObjectNames();

			Map<String, Map<String, Object>> currentThreadsMetrics =
				getCurrentThreadsMetrics();

			if (currentThreadsMetrics.isEmpty()) {
				_currentThreadsMetricsEnabled = false;

				if (_log.isWarnEnabled()) {
					_log.warn("Current threads metrics is not available");
				}
			}
		}
		catch (Exception e) {
			if (e instanceof AttributeNotFoundException ||
				e instanceof InstanceNotFoundException) {

				_currentThreadsMetricsEnabled = false;

				if (_log.isWarnEnabled()) {
					_log.warn("Current threads metrics is not available");
				}
			}
			else {
				_log.error(e, e);
			}
		}

		try {
			_properties = PropsUtil.getProperties("jdbc.default.", true);

			String jndiName = _properties.getProperty("jndi.name");

			if (Validator.isNotNull(jndiName)) {
				setUpJNDIJDBCConnectionPoolsObjectNames();
			}

			Map<String, Map<String, Object>> jdbcConnectionPoolsMetrics =
				getJDBCConnectionPoolsMetrics();

			if (jdbcConnectionPoolsMetrics.isEmpty()) {
				_jdbcConnectionPoolsMetricsEnabled = false;

				if (_log.isWarnEnabled()) {
					_log.warn("JDBC connection pools metrics is not available");
				}
			}
		}
		catch (Exception e) {
			if (e instanceof AttributeNotFoundException ||
				e instanceof InstanceNotFoundException) {

				_jdbcConnectionPoolsMetricsEnabled = false;

				if (_log.isWarnEnabled()) {
					_log.warn("JDBC connection pools metrics is not available");
				}
			}
			else {
				_log.error(e, e);
			}
		}
	}

	public Set<ObjectName> getCurrentThreadsObjectNames() {
		return _currentThreadsObjectNames;
	}

	public Set<ObjectName> getJNDIJDBCConnectionPoolsObjectNames() {
		return _jndiJDBCConnectionPoolsObjectNames;
	}

	@Override
	public Type getType() {
		return Type.LOCAL;
	}

	@Override
	public boolean isCurrentThreadsMetricsEnabled() {
		return _currentThreadsMetricsEnabled;
	}

	@Override
	public boolean isJDBCConnectionPoolsMetricsEnabled() {
		return _jdbcConnectionPoolsMetricsEnabled;
	}

	@Override
	public void run() {
		try {
			doRun();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setCurrentThreadsObjectNames(
		Set<ObjectName> currentThreadsObjectNames) {

		_currentThreadsObjectNames = currentThreadsObjectNames;
	}

	public void setJNDIJDBCConnectionPoolsObjectNames(
		Set<ObjectName> jndiJDBCConnectionPoolsObjectNames) {

		_jndiJDBCConnectionPoolsObjectNames =
			jndiJDBCConnectionPoolsObjectNames;
	}

	@Override
	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	@Override
	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		this.lcsConnectionManager = lcsConnectionManager;
	}

	@Override
	public void setMBeanServerService(MBeanServerService mBeanServerService) {
		this.mBeanServerService = mBeanServerService;
	}

	protected void doRun() throws Exception {
		if (!lcsConnectionManager.isReady()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Waiting for LCS connection manager");
			}

			return;
		}

		MetricsMessage metricsMessage = new MetricsMessage();

		metricsMessage.setCreateTime(System.currentTimeMillis());
		metricsMessage.setKey(keyGenerator.getKey());
		metricsMessage.setMetricsType(MetricsMessage.METRICS_TYPE_SERVER);
		metricsMessage.setPayload(getPayload());

		lcsConnectionManager.sendMessage(metricsMessage);
	}

	protected abstract Map<String, Map<String, Object>>
			getCurrentThreadsMetrics()
		throws Exception;

	protected Map<String, Map<String, Object>> getJDBCConnectionPoolsMetrics()
		throws Exception {

		Map<String, Map<String, Object>> jdbcConnectionPoolsMetrics;

		String jndiName = _properties.getProperty("jndi.name");

		if (Validator.isNotNull(jndiName)) {
			jdbcConnectionPoolsMetrics = getJNDIJDBCConnectionPoolsMetrics();
		}
		else {
			jdbcConnectionPoolsMetrics = new HashMap<>();

			for (ConnectionPoolMetrics connectionPoolMetric :
					_connectionPoolMetrics) {

				Map<String, Object> values = new HashMap<>();

				values.put("numActive", connectionPoolMetric.getNumActive());
				values.put("numIdle", connectionPoolMetric.getNumIdle());

				jdbcConnectionPoolsMetrics.put(
					connectionPoolMetric.getConnectionPoolName(), values);
			}
		}

		return jdbcConnectionPoolsMetrics;
	}

	protected abstract Map<String, Map<String, Object>>
			getJNDIJDBCConnectionPoolsMetrics()
		throws Exception;

	protected Map<String, Object> getPayload() throws Exception {
		Map<String, Object> payload = new HashMap<>();

		if (isCurrentThreadsMetricsEnabled()) {
			payload.put("currentThreadsMetrics", getCurrentThreadsMetrics());
		}

		if (isJDBCConnectionPoolsMetricsEnabled()) {
			payload.put(
				"jdbcConnectionPoolsMetrics", getJDBCConnectionPoolsMetrics());
		}

		return payload;
	}

	protected boolean isDataSourceTargetSourceEnabled(String name) {
		try {
			if (PortalBeanLocatorUtil.locate(name) != null) {
				return true;
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage(), e);
			}
		}

		return false;
	}

	protected abstract void setUpCurrentThreadsObjectNames() throws Exception;

	protected abstract void setUpJNDIJDBCConnectionPoolsObjectNames()
		throws Exception;

	protected KeyGenerator keyGenerator;
	protected LCSConnectionManager lcsConnectionManager;
	protected MBeanServerService mBeanServerService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseServerMetricsTask.class);

	private final Collection<ConnectionPoolMetrics> _connectionPoolMetrics =
		new ArrayList<>();
	private boolean _currentThreadsMetricsEnabled = true;
	private Set<ObjectName> _currentThreadsObjectNames = Collections.emptySet();
	private boolean _jdbcConnectionPoolsMetricsEnabled = true;
	private Set<ObjectName> _jndiJDBCConnectionPoolsObjectNames =
		Collections.emptySet();
	private Properties _properties;

}