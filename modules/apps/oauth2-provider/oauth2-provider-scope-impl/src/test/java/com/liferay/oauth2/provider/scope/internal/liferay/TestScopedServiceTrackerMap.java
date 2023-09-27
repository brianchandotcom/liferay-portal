/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal.liferay;

import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stian Sigvartsen
 */
public class TestScopedServiceTrackerMap<T> {

	public TestScopedServiceTrackerMap(T defaultService) {
		_defaultService = defaultService;
	}

	public T getService(long companyId, String key) {
		String companyIdString = String.valueOf(companyId);

		T services = _servicesByCompanyAndKey.get(
			StringBundler.concat(companyIdString, "-", key));

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

		String companyIdKeyString = StringBundler.concat(
			companyIdString, "-", key);

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
	private final Map<String, T> _servicesByCompany = new HashMap<>();
	private final Map<String, T> _servicesByCompanyAndKey = new HashMap<>();
	private final Map<String, T> _servicesByKey = new HashMap<>();

}