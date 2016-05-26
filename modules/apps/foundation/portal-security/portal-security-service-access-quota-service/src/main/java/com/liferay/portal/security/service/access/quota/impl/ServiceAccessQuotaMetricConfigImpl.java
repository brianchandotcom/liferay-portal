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

import com.liferay.portal.security.service.access.quota.ServiceAccessQuotaMetricConfig;

/**
 * @author Stian Sigvartsen
 */
public class ServiceAccessQuotaMetricConfigImpl
	implements ServiceAccessQuotaMetricConfig {

	public ServiceAccessQuotaMetricConfigImpl(String name, String pattern) {
		_name = name;
		_pattern = pattern;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ServiceAccessQuotaMetricConfigImpl)) {
			return false;
		}

		ServiceAccessQuotaMetricConfigImpl config2 =
			(ServiceAccessQuotaMetricConfigImpl)obj;

		if (getName() != null) {
			if (!getName().equals(config2.getName())) {
				return false;
			}
		}
		else if (config2.getName() != null) {
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
	public String getName() {
		return _name;
	}

	@Override
	public String getPattern() {
		return _pattern;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPattern(String pattern) {
		_pattern = pattern;
	}

	@Override
	public String toString() {
		return _name + (_pattern != null ? "=" + _pattern : "");
	}

	private String _name;
	private String _pattern;

}