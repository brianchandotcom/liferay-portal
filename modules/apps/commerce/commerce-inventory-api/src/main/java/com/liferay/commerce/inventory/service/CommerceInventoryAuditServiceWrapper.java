/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceInventoryAuditService}.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryAuditService
 * @generated
 */
public class CommerceInventoryAuditServiceWrapper
	implements CommerceInventoryAuditService,
			   ServiceWrapper<CommerceInventoryAuditService> {

	public CommerceInventoryAuditServiceWrapper() {
		this(null);
	}

	public CommerceInventoryAuditServiceWrapper(
		CommerceInventoryAuditService commerceInventoryAuditService) {

		_commerceInventoryAuditService = commerceInventoryAuditService;
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryAudit>
				getCommerceInventoryAudits(
					long companyId, String sku, String unitOfMeasureKey,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryAuditService.getCommerceInventoryAudits(
			companyId, sku, unitOfMeasureKey, start, end);
	}

	@Override
	public int getCommerceInventoryAuditsCount(
			long companyId, String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryAuditService.getCommerceInventoryAuditsCount(
			companyId, sku, unitOfMeasureKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceInventoryAuditService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceInventoryAuditService getWrappedService() {
		return _commerceInventoryAuditService;
	}

	@Override
	public void setWrappedService(
		CommerceInventoryAuditService commerceInventoryAuditService) {

		_commerceInventoryAuditService = commerceInventoryAuditService;
	}

	private CommerceInventoryAuditService _commerceInventoryAuditService;

}