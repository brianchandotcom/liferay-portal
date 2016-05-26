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

import com.liferay.portal.security.service.access.quota.internal.SAQMetricConfig;
import com.liferay.portal.security.service.access.quota.internal.ServiceAccessQuota;

import java.util.List;

/**
 * @author Stian Sigvartsen
 */
public class TestServiceAccessQuotaImpl implements ServiceAccessQuota {

	public TestServiceAccessQuotaImpl(
		long intervalMillis, int max, List<SAQMetricConfig> metrics) {

		_intervalMillis = intervalMillis;
		_max = max;
		_metrics = metrics;
	}

	@Override
	public long getIntervalMillis() {
		return _intervalMillis;
	}

	@Override
	public int getMax() {
		return _max;
	}

	@Override
	public List<SAQMetricConfig> getMetricConfigs() {
		return _metrics;
	}

	private final long _intervalMillis;
	private final int _max;
	private final List<SAQMetricConfig> _metrics;

}