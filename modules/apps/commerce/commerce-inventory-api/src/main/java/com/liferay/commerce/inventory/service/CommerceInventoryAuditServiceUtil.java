/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryAudit;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceInventoryAudit. This utility wraps
 * <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryAuditServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryAuditService
 * @generated
 */
public class CommerceInventoryAuditServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryAuditServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<CommerceInventoryAudit> getCommerceInventoryAudits(
			long companyId, String sku, String unitOfMeasureKey, int start,
			int end)
		throws PortalException {

		return getService().getCommerceInventoryAudits(
			companyId, sku, unitOfMeasureKey, start, end);
	}

	public static int getCommerceInventoryAuditsCount(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		return getService().getCommerceInventoryAuditsCount(
			companyId, sku, unitOfMeasureKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceInventoryAuditService getService() {
		return _service;
	}

	public static void setService(CommerceInventoryAuditService service) {
		_service = service;
	}

	private static volatile CommerceInventoryAuditService _service;

}