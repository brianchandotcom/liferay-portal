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

package com.liferay.lcs.messaging;

import com.liferay.lcs.monitoring.statistics.AverageStatistics;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;

import java.io.UnsupportedEncodingException;

import java.lang.reflect.Method;

import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Ivica Cardic
 */
public class PortalMetricsMessageListener implements MessageListener, Runnable {

	public void afterPropertiesSet() {
		activateMonitoring();

		_notLockedCondition = _reentrantLock.newCondition();

		_scheduledExecutorService.scheduleAtFixedRate(
			this, 15, 15, TimeUnit.SECONDS);
	}

	public void destroy() {
		_scheduledExecutorService.shutdown();

		try {
			if (!_scheduledExecutorService.awaitTermination(
					5, TimeUnit.SECONDS)) {

				_scheduledExecutorService.shutdownNow();
			}
		}
		catch (final InterruptedException ie) {
			_scheduledExecutorService.shutdownNow();
		}
	}

	@Override
	public void receive(Message message) {
		if (!_lcsConnectionManager.isReady()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Waiting for LCS connection manager");
			}

			return;
		}

		_reentrantLock.lock();

		try {
			while (_locked) {
				try {
					_notLockedCondition.await();
				}
				catch (InterruptedException ie) {
				}
			}
		}
		finally {
			_reentrantLock.unlock();
		}

		if (message.getPayload() instanceof DataSample) {
			DataSample dataSample = (DataSample)message.getPayload();

			updatePerformanceMetricsMap(
				getPerformanceMetrics(dataSample), getMetricsType(dataSample),
				StringPool.BLANK);
		}
		else {
			List<Map<String, Object>> performanceMetricsList =
				new ArrayList<>();

			List<DataSample> dataSamples =
				(List<DataSample>)message.getPayload();

			for (DataSample dataSample : dataSamples) {
				Map<String, Object> performanceMetrics = getPerformanceMetrics(
					dataSample);

				performanceMetrics.put(
					"metricsType", getMetricsType(dataSample));

				performanceMetricsList.add(performanceMetrics);
			}

			List<String> layoutNames = new ArrayList<>();

			boolean requestTypeRender = true;

			for (Map<String, Object> performanceMetrics :
					performanceMetricsList) {

				String metricsType = (String)performanceMetrics.get(
					"metricsType");

				if (metricsType.equals(MetricsMessage.METRICS_TYPE_LAYOUT)) {
					layoutNames.add((String)performanceMetrics.get("name"));
				}
				else if (metricsType.equals(
							MetricsMessage.METRICS_TYPE_PORTLET)) {

					String requestType = (String)performanceMetrics.get(
						"requestType");

					if (!requestType.equals("RENDER")) {
						requestTypeRender = false;
					}
				}
			}

			for (Map<String, Object> performanceMetrics :
					performanceMetricsList) {

				String metricsType = (String)performanceMetrics.get(
					"metricsType");

				if (metricsType.equals(MetricsMessage.METRICS_TYPE_LAYOUT) &&
					requestTypeRender) {

					updatePerformanceMetricsMap(
						performanceMetrics, metricsType, StringPool.BLANK);
				}
				else if (metricsType.equals(
							MetricsMessage.METRICS_TYPE_PORTLET)) {

					updatePerformanceMetricsMap(
						performanceMetrics, metricsType, StringPool.BLANK);

					for (String layoutName : layoutNames) {
						updatePerformanceMetricsMap(
							performanceMetrics, metricsType, layoutName);
					}
				}
			}
		}
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

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	protected void activateMonitoring() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			Configuration configuration = configurationAdmin.getConfiguration(
				_MONITORING_CONFIGURATION_PID);

			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (properties == null) {
				properties = new Hashtable<>();
			}

			properties.put("monitorPortalRequest", Boolean.TRUE);
			properties.put("monitorPortletActionRequest", Boolean.TRUE);
			properties.put("monitorPortletEventRequest", Boolean.TRUE);
			properties.put("monitorPortletRenderRequest", Boolean.TRUE);
			properties.put("monitorPortletResourceRequest", Boolean.TRUE);
			properties.put("monitorServiceRequest", Boolean.TRUE);

			configuration.update(properties);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to activate monitoring", e);
			}
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	protected void doRun() {
		if (_performanceMetricsMap.size() == 0) {
			return;
		}

		MetricsMessage metricsMessage = new MetricsMessage();

		metricsMessage.setCreateTime(System.currentTimeMillis());
		metricsMessage.setKey(_keyGenerator.getKey());
		metricsMessage.setMetricsType(MetricsMessage.METRICS_TYPE_PORTAL);

		_reentrantLock.lock();

		try {
			_locked = true;

			List<Map<String, Object>> performanceMetricsList =
				new ArrayList<>();

			Set<String> keys = _performanceMetricsMap.keySet();

			for (String key : keys) {
				Map<String, Object> performanceMetrics = new HashMap<>();

				MapUtil.copy(
					_performanceMetricsMap.get(key), performanceMetrics);

				AverageStatistics averageStatistics = _averageStatisticsMap.get(
					key);

				performanceMetrics.put(
					"duration", averageStatistics.getAverageTime());
				performanceMetrics.put(
					"frequency", averageStatistics.getCount());

				performanceMetricsList.add(performanceMetrics);
			}

			metricsMessage.setPayload(performanceMetricsList);

			_averageStatisticsMap.clear();
			_performanceMetricsMap.clear();

			_locked = false;

			_notLockedCondition.signalAll();
		}
		finally {
			_reentrantLock.unlock();
		}

		try {
			_lcsConnectionManager.sendMessage(metricsMessage);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected String getMetricsType(DataSample dataSample) {
		String namespace = dataSample.getNamespace();

		if (namespace.contains("Portal")) {
			return MetricsMessage.METRICS_TYPE_LAYOUT;
		}
		else if (namespace.contains("Portlet")) {
			return MetricsMessage.METRICS_TYPE_PORTLET;
		}
		else {
			return MetricsMessage.METRICS_TYPE_SERVICE;
		}
	}

	protected Map<String, Object> getPerformanceMetrics(DataSample dataSample) {
		Map<String, Object> performanceMetrics = new HashMap<>();

		Class<?> clazz = dataSample.getClass();

		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			String methodName = method.getName();

			if (!methodName.startsWith("get") ||
				methodName.equals("getClass")) {

				continue;
			}

			String name = methodName.substring(3, methodName.length());

			name = TextFormatter.format(name, TextFormatter.I);

			Object value = null;

			try {
				value = method.invoke(dataSample);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}

				continue;
			}

			if ((value != null) && !(value instanceof Number) &&
				!(value instanceof String)) {

				value = String.valueOf(value);
			}

			if (value != null) {
				performanceMetrics.put(name, value);
			}
		}

		return performanceMetrics;
	}

	protected void updatePerformanceMetricsMap(
		Map<String, Object> performanceMetrics, String metricsType,
		String layoutName) {

		Map<String, Object> map = new HashMap<>();

		MapUtil.copy(performanceMetrics, map);

		performanceMetrics = map;

		performanceMetrics.put("metricsType", metricsType);

		AverageStatistics averageStatistics = null;
		String averageStatisticsName = null;
		String key = null;

		if (metricsType.equals(MetricsMessage.METRICS_TYPE_LAYOUT)) {
			String name = (String)performanceMetrics.get("name");

			try {
				name = URLDecoder.decode(name, "UTF-8");
			}
			catch (UnsupportedEncodingException uee) {
				if (_log.isWarnEnabled()) {
					_log.warn(uee.getMessage(), uee);
				}
			}

			if (name.startsWith("/c") || name.endsWith(".jsp") ||
				name.contains("/control_panel")) {

				return;
			}

			if (name.contains(";jsessionid")) {
				int index = name.indexOf(";jsessionid");

				name = name.substring(0, index);
			}

			if (name.endsWith("/")) {
				name = name.substring(0, (name.length() - 1));
			}

			performanceMetrics.put("name", name);

			averageStatisticsName = name;
			key = metricsType.concat(name);
		}
		else if (metricsType.equals(MetricsMessage.METRICS_TYPE_PORTLET)) {
			performanceMetrics.put("layoutName", layoutName);

			String portletId = (String)performanceMetrics.get("portletId");

			if (portletId.endsWith("ControlMenuPortlet") ||
				portletId.endsWith("ProductMenuPortlet")) {

				return;
			}

			if (portletId.contains("_INSTANCE")) {
				portletId = portletId.substring(
					0, portletId.indexOf("_INSTANCE"));

				performanceMetrics.put("portletId", portletId);
			}

			averageStatisticsName = portletId;
			key = metricsType.concat(portletId).concat(layoutName);
		}

		_performanceMetricsMap.put(key, performanceMetrics);

		if (_averageStatisticsMap.containsKey(key)) {
			averageStatistics = _averageStatisticsMap.get(key);
		}
		else {
			averageStatistics = new AverageStatistics(averageStatisticsName);

			_averageStatisticsMap.put(key, averageStatistics);
		}

		long duration = (Long)performanceMetrics.get("duration");

		averageStatistics.addDuration(duration);
	}

	private static final String _MONITORING_CONFIGURATION_PID =
		"com.liferay.portal.monitoring.configuration.MonitoringConfiguration";

	private static Log _log = LogFactoryUtil.getLog(
		PortalMetricsMessageListener.class);

	private Map<String, AverageStatistics> _averageStatisticsMap =
		new ConcurrentHashMap<>();
	private KeyGenerator _keyGenerator;
	private LCSConnectionManager _lcsConnectionManager;
	private boolean _locked;
	private Condition _notLockedCondition;
	private Map<String, Map<String, Object>> _performanceMetricsMap =
		new ConcurrentHashMap<>();
	private ReentrantLock _reentrantLock = new ReentrantLock();
	private ScheduledExecutorService _scheduledExecutorService =
		Executors.newSingleThreadScheduledExecutor();

}