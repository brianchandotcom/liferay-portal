/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.service;

import com.liferay.depot.model.DepotEntryPin;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * Provides the remote service utility for DepotEntryPin. This utility wraps
 * <code>com.liferay.depot.service.impl.DepotEntryPinServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryPinService
 * @generated
 */
public class DepotEntryPinServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryPinServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DepotEntryPin addDepotEntryPin(long userId, long depotEntryId)
		throws PortalException {

		return getService().addDepotEntryPin(userId, depotEntryId);
	}

	public static DepotEntryPin deleteDepotEntryPin(
			long userId, long depotEntryId)
		throws PortalException {

		return getService().deleteDepotEntryPin(userId, depotEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DepotEntryPinService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<DepotEntryPinService> _serviceSnapshot =
		new Snapshot<>(
			DepotEntryPinServiceUtil.class, DepotEntryPinService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-1010640685