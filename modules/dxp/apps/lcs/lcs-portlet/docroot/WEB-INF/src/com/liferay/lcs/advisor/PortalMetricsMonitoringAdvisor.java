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

package com.liferay.lcs.advisor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Igor Beslic
 */
public class PortalMetricsMonitoringAdvisor implements MonitoringAdvisor {

	@Override
	public void activateMonitoring() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			Configuration configuration = configurationAdmin.getConfiguration(
				_MONITORING_CONFIGURATION_PID, StringPool.QUESTION);

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

			if (_log.isInfoEnabled()) {
				_log.info("Monitoring activated");
			}
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

	private static final String _MONITORING_CONFIGURATION_PID =
		"com.liferay.portal.monitoring.configuration.MonitoringConfiguration";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalMetricsMonitoringAdvisor.class);

}