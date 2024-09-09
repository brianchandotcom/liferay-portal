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
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			addCommercePaymentEntry(
				long classNameId, long classPK, long commerceChannelId,
				java.math.BigDecimal amount, String callbackURL,
				String cancelURL, String currencyCode, String languageId,
				String note, String payload, String paymentIntegrationKey,
				int paymentIntegrationType, String reasonKey,
				String transactionCode, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.addCommercePaymentEntry(
			classNameId, classPK, commerceChannelId, amount, callbackURL,
			cancelURL, currencyCode, languageId, note, payload,
			paymentIntegrationKey, paymentIntegrationType, reasonKey,
			transactionCode, type, serviceContext);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			addOrUpdateCommercePaymentEntry(
				String externalReferenceCode, long classNameId, long classPK,
				long commerceChannelId, java.math.BigDecimal amount,
				String callbackURL, String cancelURL, String currencyCode,
				String errorMessages, String languageId, String note,
				String payload, String paymentIntegrationKey,
				int paymentIntegrationType, int paymentStatus, String reasonKey,
				String redirectURL, String transactionCode, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.addOrUpdateCommercePaymentEntry(
			externalReferenceCode, classNameId, classPK, commerceChannelId,
			amount, callbackURL, cancelURL, currencyCode, errorMessages,
			languageId, note, payload, paymentIntegrationKey,
			paymentIntegrationType, paymentStatus, reasonKey, redirectURL,
			transactionCode, type, serviceContext);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			deleteCommercePaymentEntry(long commercePaymentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.deleteCommercePaymentEntry(
			commercePaymentEntryId);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			fetchCommercePaymentEntry(long commercePaymentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.fetchCommercePaymentEntry(
			commercePaymentEntryId);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			fetchCommercePaymentEntryByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.
			fetchCommercePaymentEntryByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry>
				getCommercePaymentEntries(
					long companyId, long classNameId, long classPK, int type,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.payment.model.
							CommercePaymentEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.getCommercePaymentEntries(
			companyId, classNameId, classPK, type, start, end,
			orderByComparator);
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
	public int getCommercePaymentEntriesCount(
			long companyId, long classNameId, long classPK, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.getCommercePaymentEntriesCount(
			companyId, classNameId, classPK, type);
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
	public java.math.BigDecimal getRefundedAmount(
			long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.getRefundedAmount(
			companyId, classNameId, classPK);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.payment.model.CommercePaymentEntry> search(
				long companyId, long[] classNameIds, long[] classPKs,
				String[] currencyCodes, String keywords,
				String[] paymentMethodNames, int[] paymentStatuses,
				boolean excludeStatuses, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.search(
			companyId, classNameIds, classPKs, currencyCodes, keywords,
			paymentMethodNames, paymentStatuses, excludeStatuses, start, end,
			sort);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			updateCommercePaymentEntry(
				String externalReferenceCode, long commercePaymentEntryId,
				long commerceChannelId, java.math.BigDecimal amount,
				String callbackURL, String cancelURL, String currencyCode,
				String errorMessages, String languageId, String note,
				String payload, String paymentIntegrationKey,
				int paymentIntegrationType, int paymentStatus, String reasonKey,
				String redirectURL, String transactionCode, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.updateCommercePaymentEntry(
			externalReferenceCode, commercePaymentEntryId, commerceChannelId,
			amount, callbackURL, cancelURL, currencyCode, errorMessages,
			languageId, note, payload, paymentIntegrationKey,
			paymentIntegrationType, paymentStatus, reasonKey, redirectURL,
			transactionCode, type);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			updateExternalReferenceCode(
				long commercePaymentEntryId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.updateExternalReferenceCode(
			commercePaymentEntryId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry updateNote(
			long commercePaymentEntryId, String note)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.updateNote(
			commercePaymentEntryId, note);
	}

	@Override
	public com.liferay.commerce.payment.model.CommercePaymentEntry
			updateReasonKey(long commercePaymentEntryId, String reasonKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePaymentEntryService.updateReasonKey(
			commercePaymentEntryId, reasonKey);
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