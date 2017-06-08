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

import com.liferay.portal.security.service.access.quota.SAQMetricConfig;

/**
 * @author Stian Sigvartsen
 */
public class SAQMetricConfigImpl implements SAQMetricConfig {

	public SAQMetricConfigImpl(String metricName, String pattern) {
		_metricName = metricName;
		_pattern = pattern;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SAQMetricConfigImpl)) {
			return false;
		}

		SAQMetricConfigImpl config2 = (SAQMetricConfigImpl)obj;

		if (getMetricName() != null) {
			if (!getMetricName().equals(config2.getMetricName())) {
				return false;
			}
		}
		else if (config2.getMetricName() != null) {
			return false;
		}

		if (getPattern() != null) {
			if (!getPattern().equals(config2.getPattern())) {
				return false;
			}
		}
		else if (config2.getPattern() != null) {
			return false;
		}

		return true;
	}

	@Override
	public String getMetricName() {
		return _metricName;
	}

	@Override
	public String getPattern() {
		return _pattern;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public void setName(String metricName) {
		_metricName = metricName;
	}

	public void setPattern(String pattern) {
		_pattern = pattern;
	}

	@Override
	public String toString() {
		return _metricName + (_pattern != null ? "=" + _pattern : "");
	}

	private String _metricName;
	private String _pattern;

}