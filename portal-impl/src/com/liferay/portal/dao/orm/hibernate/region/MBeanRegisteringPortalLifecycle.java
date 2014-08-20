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

package com.liferay.portal.dao.orm.hibernate.region;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;

import javax.management.MBeanServer;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;

/**
 * @author Shuyang Zhou
 */
public class MBeanRegisteringPortalLifecycle extends BasePortalLifecycle {

	public MBeanRegisteringPortalLifecycle(CacheManager cacheManager) {
		_cacheManager = cacheManager;
	}

	@Override
	protected void doPortalDestroy() {
		_managementService.dispose();
	}

	@Override
	protected void doPortalInit() throws Exception {
		MBeanServer mBeanServer = (MBeanServer)PortalBeanLocatorUtil.locate(
			_MBEAN_SERVER_BEAN_NAME);

		_managementService = new ManagementService(
			_cacheManager, mBeanServer, true, true, true, true);

		_managementService.init();
	}

	private static final String _MBEAN_SERVER_BEAN_NAME =
		"registryAwareMBeanServer";

	private final CacheManager _cacheManager;
	private ManagementService _managementService;

}