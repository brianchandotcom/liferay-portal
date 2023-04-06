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

package com.liferay.commerce.payment.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePaymentEntryAuditService}.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryAuditService
 * @generated
 */
public class CommercePaymentEntryAuditServiceWrapper
	implements CommercePaymentEntryAuditService,
			   ServiceWrapper<CommercePaymentEntryAuditService> {

	public CommercePaymentEntryAuditServiceWrapper() {
		this(null);
	}

	public CommercePaymentEntryAuditServiceWrapper(
		CommercePaymentEntryAuditService commercePaymentEntryAuditService) {

		_commercePaymentEntryAuditService = commercePaymentEntryAuditService;
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntryAudit>
				getCommercePaymentEntries(
					long companyId, long commercePaymentEntryId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentEntryAudit> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryAuditService.getCommercePaymentEntries(
			companyId, commercePaymentEntryId, start, end, orderByComparator);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntryAudit
			getCommercePaymentEntryAudit(long commercePaymentEntryAuditId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryAuditService.getCommercePaymentEntryAudit(
			commercePaymentEntryAuditId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePaymentEntryAuditService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntryAudit> search(
				long companyId, long[] commercePaymentEntryIds,
				String[] logTypes, String keywords, int start, int end,
				String orderByField, boolean reverse)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryAuditService.search(
			companyId, commercePaymentEntryIds, logTypes, keywords, start, end,
			orderByField, reverse);
	}

	@Override
	public CommercePaymentEntryAuditService getWrappedService() {
		return _commercePaymentEntryAuditService;
	}

	@Override
	public void setWrappedService(
		CommercePaymentEntryAuditService commercePaymentEntryAuditService) {

		_commercePaymentEntryAuditService = commercePaymentEntryAuditService;
	}

	private CommercePaymentEntryAuditService _commercePaymentEntryAuditService;

}