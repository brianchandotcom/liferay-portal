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

package com.liferay.portal.security.service.access.quota.impl;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.quota.ServiceAccessQuota;
import com.liferay.portal.security.service.access.quota.ServiceAccessQuotaMetricConfig;
import com.liferay.portal.security.service.access.quota.configuration.SAQConfiguration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.portal.security.service.access.quota.configuration.SAQConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = ServiceAccessQuota.class
)
public class ServiceAccessQuotaImpl implements ServiceAccessQuota {

	public long getIntervalMillis() {
		return _configuration.intervalMillis();
	}

	public int getMax() {
		return _configuration.max();
	}

	public List<ServiceAccessQuotaMetricConfig> getMetrics() {
		if (_metrics == null) {
			synchronized (this) {
				if (_metrics == null) {
					List<ServiceAccessQuotaMetricConfig> metrics =
						new LinkedList<>();

					for (String metricNotation : _configuration.metric()) {
						if (Validator.isNull(metricNotation)) {
							continue;
						}

						String[] parts = metricNotation.split("=");

						String metricName = StringUtil.toLowerCase(parts[0]);

						String metricFilter;

						if (parts.length > 1) {
							metricFilter = parts[1];
						}
						else {
							metricFilter = null;
						}

						metrics.add(
							new ServiceAccessQuotaMetricConfigImpl(
								metricName, metricFilter));
					}

					if (Validator.isNotNull(
							_configuration.serviceSignature())) {

						metrics.add(
							new ServiceAccessQuotaMetricConfigImpl(
								"service", _configuration.serviceSignature()));
					}

					_metrics = metrics;
				}
			}
		}

		return _metrics;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_configuration = ConfigurableUtil.createConfigurable(
			SAQConfiguration.class, properties);

		_metrics = null;
	}

	private volatile SAQConfiguration _configuration;
	private volatile List<ServiceAccessQuotaMetricConfig> _metrics;

}