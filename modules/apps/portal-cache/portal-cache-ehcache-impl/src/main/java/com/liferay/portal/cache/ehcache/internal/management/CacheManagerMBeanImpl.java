/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.management;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.ehcache.CacheManager;

/**
 * @author Dante Wang
 */
public class CacheManagerMBeanImpl
	extends StandardMBean implements CacheManagerMBean {

	public CacheManagerMBeanImpl(
			String cacheManagerName, CacheManager cacheManager)
		throws NotCompliantMBeanException {

		super(CacheManagerMBean.class);

		_cacheManagerName = cacheManagerName;
		_cacheManager = cacheManager;
	}

	@Override
	public String getName() {
		return _cacheManagerName;
	}

	@Override
	public String getStatus() {
		return String.valueOf(_cacheManager.getStatus());
	}

	private final CacheManager _cacheManager;
	private final String _cacheManagerName;

}