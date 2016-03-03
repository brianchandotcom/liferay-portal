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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.counter.kernel.service.CounterLocalServiceWrapper;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.upgrade.BaseUpgradeAdminPortlets;
import com.liferay.portal.upgrade.ServiceWrapperProxyUtil;

import java.io.Closeable;

/**
 * @author Juan Fernández
 */
public class UpgradeAdminPortlets extends BaseUpgradeAdminPortlets {

	@Override
	protected void doUpgrade() throws Exception {
		try (Closeable closeable = ServiceWrapperProxyUtil.createProxy(
				PortalBeanLocatorUtil.locate(
					CounterLocalService.class.getName()),
				SwitchClassNameCounterLocalServiceWrapper.class)) {

			updateAccessInControlPanelPermission("19", "162");
			updateAccessInControlPanelPermission("33", "161");
		}
	}

	private static class SwitchClassNameCounterLocalServiceWrapper
		extends CounterLocalServiceWrapper {

		@Override
		public long increment(String name) {
			if (name.equals(ResourcePermission.class.getName())) {
				name = "com.liferay.portal.model.ResourcePermission";
			}

			return super.increment(name);
		}

		private SwitchClassNameCounterLocalServiceWrapper(
			CounterLocalService counterLocalService) {

			super(counterLocalService);
		}

	}

}