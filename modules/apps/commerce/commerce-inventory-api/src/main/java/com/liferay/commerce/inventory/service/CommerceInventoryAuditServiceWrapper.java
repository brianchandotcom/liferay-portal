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
					long companyId, String sku, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryAuditService.getCommerceInventoryAudits(
			companyId, sku, start, end);
	}

	@Override
	public int getCommerceInventoryAuditsCount(long companyId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryAuditService.getCommerceInventoryAuditsCount(
			companyId, sku);
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