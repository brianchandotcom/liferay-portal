/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * Provides the remote service utility for CommerceVirtualOrderItemFileEntry. This utility wraps
 * <code>com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemFileEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemFileEntryService
 * @generated
 */
public class CommerceVirtualOrderItemFileEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemFileEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceVirtualOrderItemFileEntry
			fetchCommerceVirtualOrderItemFileEntry(
				long commerceVirtualOrderItemFileEntryId)
		throws PortalException {

		return getService().fetchCommerceVirtualOrderItemFileEntry(
			commerceVirtualOrderItemFileEntryId);
	}

	public static List<CommerceVirtualOrderItemFileEntry>
			getCommerceVirtualOrderItemFileEntries(
				long commerceVirtualOrderItemId, int start, int end)
		throws PortalException {

		return getService().getCommerceVirtualOrderItemFileEntries(
			commerceVirtualOrderItemId, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceVirtualOrderItemFileEntry
			updateCommerceVirtualOrderItemFileEntry(
				long commerceVirtualOrderItemFileEntryId, long fileEntryId,
				String url, int usages, String version)
		throws PortalException {

		return getService().updateCommerceVirtualOrderItemFileEntry(
			commerceVirtualOrderItemFileEntryId, fileEntryId, url, usages,
			version);
	}

	public static CommerceVirtualOrderItemFileEntryService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CommerceVirtualOrderItemFileEntryService>
		_serviceSnapshot = new Snapshot<>(
			CommerceVirtualOrderItemFileEntryServiceUtil.class,
			CommerceVirtualOrderItemFileEntryService.class);

}