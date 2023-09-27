/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for CommerceVirtualOrderItem. This utility wraps
 * <code>com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemService
 * @generated
 */
public class CommerceVirtualOrderItemServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceVirtualOrderItem fetchCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId)
		throws PortalException {

		return getService().fetchCommerceVirtualOrderItem(
			commerceVirtualOrderItemId);
	}

	public static CommerceVirtualOrderItem
			fetchCommerceVirtualOrderItemByCommerceOrderItemId(
				long commerceOrderItemId)
		throws PortalException {

		return getService().fetchCommerceVirtualOrderItemByCommerceOrderItemId(
			commerceOrderItemId);
	}

	public static java.io.File getFile(long commerceVirtualOrderItemId)
		throws Exception {

		return getService().getFile(commerceVirtualOrderItemId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId, long fileEntryId, String url,
			int activationStatus, long duration, int usages, int maxUsages,
			boolean active)
		throws PortalException {

		return getService().updateCommerceVirtualOrderItem(
			commerceVirtualOrderItemId, fileEntryId, url, activationStatus,
			duration, usages, maxUsages, active);
	}

	public static CommerceVirtualOrderItemService getService() {
		return _service;
	}

	public static void setService(CommerceVirtualOrderItemService service) {
		_service = service;
	}

	private static volatile CommerceVirtualOrderItemService _service;

}