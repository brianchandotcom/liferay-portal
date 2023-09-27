/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePaymentEntryService}.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryService
 * @generated
 */
public class CommercePaymentEntryServiceWrapper
	implements CommercePaymentEntryService,
			   ServiceWrapper<CommercePaymentEntryService> {

	public CommercePaymentEntryServiceWrapper() {
		this(null);
	}

	public CommercePaymentEntryServiceWrapper(
		CommercePaymentEntryService commercePaymentEntryService) {

		_commercePaymentEntryService = commercePaymentEntryService;
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry>
				getCommercePaymentEntries(
					long companyId, long classNameId, long classPK, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.getCommercePaymentEntries(
			companyId, classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			getCommercePaymentEntry(long commercePaymentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.getCommercePaymentEntry(
			commercePaymentEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePaymentEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry> search(
				long companyId, long[] classNameIds, long[] classPKs,
				String[] currencyCodes, String keywords,
				String[] paymentMethodNames, int[] paymentStatuses,
				boolean excludeStatuses, int start, int end,
				String orderByField, boolean reverse)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.search(
			companyId, classNameIds, classPKs, currencyCodes, keywords,
			paymentMethodNames, paymentStatuses, excludeStatuses, start, end,
			orderByField, reverse);
	}

	@Override
	public CommercePaymentEntryService getWrappedService() {
		return _commercePaymentEntryService;
	}

	@Override
	public void setWrappedService(
		CommercePaymentEntryService commercePaymentEntryService) {

		_commercePaymentEntryService = commercePaymentEntryService;
	}

	private CommercePaymentEntryService _commercePaymentEntryService;

}