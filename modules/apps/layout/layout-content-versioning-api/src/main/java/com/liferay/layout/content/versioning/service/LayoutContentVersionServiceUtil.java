/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.versioning.service;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * Provides the remote service utility for LayoutContentVersion. This utility wraps
 * <code>com.liferay.layout.content.versioning.service.impl.LayoutContentVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Lourdes Fernández Besada
 * @see LayoutContentVersionService
 * @generated
 */
public class LayoutContentVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.content.versioning.service.impl.LayoutContentVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static LayoutContentVersionService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<LayoutContentVersionService>
		_serviceSnapshot = new Snapshot<>(
			LayoutContentVersionServiceUtil.class,
			LayoutContentVersionService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:110697488