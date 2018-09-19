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

package com.liferay.portal.monitoring.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.monitoring.internal.configuration.MonitoringConfiguration;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

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
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.portal.monitoring.internal.configuration.MonitoringConfiguration",
	immediate = true, service = {}
)
public class MonitoringGateKeeper {

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		BundleContext bundleContext = componentContext.getBundleContext();

		List<String> services = _loadGateKeptServices(
			bundleContext.getBundle());

		MonitoringConfiguration monitoringConfiguration =
			ConfigurableUtil.createConfigurable(
				MonitoringConfiguration.class, properties);

		if (monitoringConfiguration.monitorEnabled()) {
			for (String service : services) {
				componentContext.enableComponent(service);
			}
		}
		else {
			for (String service : services) {
				componentContext.disableComponent(service);
			}
		}
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

			try (InputStream is = url.openStream()) {
				Document document = documentBuilder.parse(is);

				Element element = document.getDocumentElement();

				String enabled = element.getAttribute("enabled");

				if (!enabled.isEmpty() && !Boolean.valueOf(enabled)) {
					services.add(element.getAttribute("name"));
				}
			}
		}

		return services;
	}

}