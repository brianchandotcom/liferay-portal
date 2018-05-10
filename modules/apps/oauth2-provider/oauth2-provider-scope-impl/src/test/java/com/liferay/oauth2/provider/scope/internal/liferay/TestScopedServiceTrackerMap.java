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

package com.liferay.oauth2.provider.scope.internal.liferay;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stian Sigvartsen
 */
public class TestScopedServiceTrackerMap<T> {

	public TestScopedServiceTrackerMap(T defaultService) {
		_defaultService = defaultService;

		_servicesByCompany = new HashMap<>();
		_servicesByKey = new HashMap<>();
		_servicesByCompanyAndKey = new HashMap<>();
	}

	public T getService(long companyId, String key) {
		String companyIdString = String.valueOf(companyId);

		T services = _servicesByCompanyAndKey.get(
			String.join("-", companyIdString, key));

		if (services != null) {
			return services;
		}

		services = _servicesByKey.get(key);

		if (services != null) {
			return services;
		}

		services = _servicesByCompany.get(companyIdString);

		if (services != null) {
			return services;
		}

		return _defaultService;
	}

	public void setService(long companyId, String key, T service) {
		String companyIdString = String.valueOf(companyId);

		String companyIdKeyString = String.join("-", companyIdString, key);

		_servicesByCompanyAndKey.put(companyIdKeyString, service);
	}

	public void setService(long companyId, T service) {
		String companyIdString = String.valueOf(companyId);

		_servicesByCompany.put(companyIdString, service);
	}

	public void setService(Long companyId, String key, T service) {
		if ((companyId == null) && (key != null)) {
			setService(key, service);
		}
		else if (key == null) {
			setService(companyId.longValue(), service);
		}
		else {
			setService(companyId.longValue(), key, service);
		}
	}

	public void setService(String key, T service) {
		_servicesByKey.put(key, service);
	}

	private final T _defaultService;
	private final Map<String, T> _servicesByCompany;
	private final Map<String, T> _servicesByCompanyAndKey;
	private final Map<String, T> _servicesByKey;

}