/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * Provides the remote service utility for CPConfigurationEntrySetting. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPConfigurationEntrySettingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPConfigurationEntrySettingService
 * @generated
 */
public class CPConfigurationEntrySettingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPConfigurationEntrySettingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPConfigurationEntrySettingService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CPConfigurationEntrySettingService>
		_serviceSnapshot = new Snapshot<>(
			CPConfigurationEntrySettingServiceUtil.class,
			CPConfigurationEntrySettingService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-2847497