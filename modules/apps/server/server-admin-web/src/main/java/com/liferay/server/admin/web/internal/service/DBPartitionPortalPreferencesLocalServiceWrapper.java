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

package com.liferay.server.admin.web.internal.service;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.function.Supplier;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(service = ServiceWrapper.class)
public class DBPartitionPortalPreferencesLocalServiceWrapper
	extends PortalPreferencesLocalServiceWrapper {

	@Override
	public PortalPreferences fetchPortalPreferences(
		long ownerId, int ownerType) {

		return _withCompanyThreadLocal(
			ownerId, ownerType,
			() -> super.fetchPortalPreferences(ownerId, ownerType));
	}

	@Override
	public PortletPreferences getPreferences(long ownerId, int ownerType) {
		return _withCompanyThreadLocal(
			ownerId, ownerType, () -> super.getPreferences(ownerId, ownerType));
	}

	@Override
	public PortletPreferences getPreferences(
		long ownerId, int ownerType, String defaultPreferences) {

		return _withCompanyThreadLocal(
			ownerId, ownerType,
			() -> super.getPreferences(ownerId, ownerType, defaultPreferences));
	}

	private <T> T _withCompanyThreadLocal(
		long ownerId, int ownerType, Supplier<T> supplier) {

		if (DBPartitionUtil.isPartitionEnabled() &&
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) &&
			(ownerId == PortletKeys.PREFS_OWNER_ID_DEFAULT)) {

			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setWithSafeCloseable(ownerId)) {

				return supplier.get();
			}
		}

		return supplier.get();
	}

}