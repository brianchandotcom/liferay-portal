/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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