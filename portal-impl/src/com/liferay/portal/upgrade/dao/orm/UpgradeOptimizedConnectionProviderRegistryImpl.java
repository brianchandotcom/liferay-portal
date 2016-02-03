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

package com.liferay.portal.upgrade.dao.orm;

import com.liferay.portal.kernel.upgrade.dao.orm.UpgradeOptimizedConnectionProvider;
import com.liferay.portal.kernel.upgrade.dao.orm.UpgradeOptimizedConnectionProviderRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author Cristina González
 */
public class UpgradeOptimizedConnectionProviderRegistryImpl
	implements UpgradeOptimizedConnectionProviderRegistry {

	public UpgradeOptimizedConnectionProviderRegistryImpl() {
		_upgradeOptimizedConnectionProviderMap = new HashMap<>();

		Class<UpgradeOptimizedConnectionProviderRegistryImpl>
			providerRegisterClass =
				UpgradeOptimizedConnectionProviderRegistryImpl.class;

		ServiceLoader<UpgradeOptimizedConnectionProvider> serviceLoader =
			ServiceLoader.load(
				UpgradeOptimizedConnectionProvider.class,
				providerRegisterClass.getClassLoader());

		for (UpgradeOptimizedConnectionProvider
				upgradeOptimizedConnectionProvider : serviceLoader) {

			_upgradeOptimizedConnectionProviderMap.put(
				upgradeOptimizedConnectionProvider.getDBProductName(),
				upgradeOptimizedConnectionProvider);
		}
	}

	@Override
	public UpgradeOptimizedConnectionProvider getConnectionProvider(
		String productName) {

		return _upgradeOptimizedConnectionProviderMap.get(productName);
	}

	private final Map<String, UpgradeOptimizedConnectionProvider>
		_upgradeOptimizedConnectionProviderMap;

}