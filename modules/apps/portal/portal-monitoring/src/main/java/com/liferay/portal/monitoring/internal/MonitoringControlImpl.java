/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.monitoring.Level;
import com.liferay.portal.kernel.monitoring.MonitoringControl;
import com.liferay.portal.monitoring.internal.configuration.MonitoringConfiguration;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Dante Wang
 */
@Component(
	configurationPid = "com.liferay.portal.monitoring.internal.configuration.MonitoringConfiguration",
	service = MonitoringControl.class
)
public class MonitoringControlImpl implements MonitoringControl {

	@Override
	public Level getLevel(String namespace) {
		Level level = _levels.get(namespace);

		if (level == null) {
			return Level.OFF;
		}

		return level;
	}

	@Override
	public Set<String> getNamespaces() {
		return _levels.keySet();
	}

	@Override
	public void setLevel(String namespace, Level level) {
		if (level == Level.OFF) {
			_levels.remove(namespace);
		}
		else {
			_levels.put(namespace, level);
		}

		_refreshComponents(
			_monitoringConfiguration.monitorEnabled() && !_levels.isEmpty());
	}

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_componentContext = componentContext;

		_monitoringConfiguration = ConfigurableUtil.createConfigurable(
			MonitoringConfiguration.class, properties);

		_refreshComponents(
			_monitoringConfiguration.monitorEnabled() && !_levels.isEmpty());
	}

	private List<String> _loadGateKeptServices(Bundle bundle) throws Exception {
		List<String> services = new ArrayList<>();

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Enumeration<URL> enumeration = bundle.findEntries(
			"/OSGI-INF", "*.xml", false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			try (InputStream inputStream = url.openStream()) {
				Document document = documentBuilder.parse(inputStream);

				Element element = document.getDocumentElement();

				String enabled = element.getAttribute("enabled");

				if (!enabled.isEmpty() && !Boolean.valueOf(enabled)) {
					services.add(element.getAttribute("name"));
				}
			}
		}

		return services;
	}

	private void _refreshComponents(boolean enabled) {
		if (_enabled.getAndSet(enabled) == enabled) {
			return;
		}

		BundleContext bundleContext = _componentContext.getBundleContext();

		try {
			List<String> services = _loadGateKeptServices(
				bundleContext.getBundle());

			if (enabled) {
				for (String service : services) {
					_componentContext.enableComponent(service);
				}
			}
			else {
				for (String service : services) {
					_componentContext.disableComponent(service);
				}
			}
		}
		catch (Exception exception) {
			_log.error("Unable to refresh components", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MonitoringControlImpl.class);

	private volatile ComponentContext _componentContext;
	private final AtomicBoolean _enabled = new AtomicBoolean();
	private final Map<String, Level> _levels = new ConcurrentHashMap<>();
	private volatile MonitoringConfiguration _monitoringConfiguration;

}