/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommercePaymentEntry. This utility wraps
 * <code>com.liferay.commerce.payment.service.impl.CommercePaymentEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryService
 * @generated
 */
public class CommercePaymentEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.payment.service.impl.CommercePaymentEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommercePaymentEntry addCommercePaymentEntry(
			long classNameId, long classPK, long commerceChannelId,
			java.math.BigDecimal amount, String callbackURL, String cancelURL,
			String currencyCode, String languageId, String note, String payload,
			String paymentIntegrationKey, int paymentIntegrationType,
			String reasonKey, String transactionCode, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommercePaymentEntry(
			classNameId, classPK, commerceChannelId, amount, callbackURL,
			cancelURL, currencyCode, languageId, note, payload,
			paymentIntegrationKey, paymentIntegrationType, reasonKey,
			transactionCode, type, serviceContext);
	}

	public static CommercePaymentEntry addOrUpdateCommercePaymentEntry(
			String externalReferenceCode, long classNameId, long classPK,
			long commerceChannelId, java.math.BigDecimal amount,
			String callbackURL, String cancelURL, String currencyCode,
			String errorMessages, String languageId, String note,
			String payload, String paymentIntegrationKey,
			int paymentIntegrationType, int paymentStatus, String reasonKey,
			String redirectURL, String transactionCode, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCommercePaymentEntry(
			externalReferenceCode, classNameId, classPK, commerceChannelId,
			amount, callbackURL, cancelURL, currencyCode, errorMessages,
			languageId, note, payload, paymentIntegrationKey,
			paymentIntegrationType, paymentStatus, reasonKey, redirectURL,
			transactionCode, type, serviceContext);
	}

	public static CommercePaymentEntry deleteCommercePaymentEntry(
			long commercePaymentEntryId)
		throws PortalException {

		return getService().deleteCommercePaymentEntry(commercePaymentEntryId);
	}

	public static CommercePaymentEntry fetchCommercePaymentEntry(
			long commercePaymentEntryId)
		throws PortalException {

		return getService().fetchCommercePaymentEntry(commercePaymentEntryId);
	}

	public static CommercePaymentEntry
			fetchCommercePaymentEntryByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchCommercePaymentEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static List<CommercePaymentEntry> getCommercePaymentEntries(
			long companyId, long classNameId, long classPK, int type, int start,
			int end, OrderByComparator<CommercePaymentEntry> orderByComparator)
		throws PortalException {

		return getService().getCommercePaymentEntries(
			companyId, classNameId, classPK, type, start, end,
			orderByComparator);
	}

	public static List<CommercePaymentEntry> getCommercePaymentEntries(
			long companyId, long classNameId, long classPK, int start, int end,
			OrderByComparator<CommercePaymentEntry> orderByComparator)
		throws PortalException {

		return getService().getCommercePaymentEntries(
			companyId, classNameId, classPK, start, end, orderByComparator);
	}

	public static int getCommercePaymentEntriesCount(
			long companyId, long classNameId, long classPK, int type)
		throws PortalException {

		return getService().getCommercePaymentEntriesCount(
			companyId, classNameId, classPK, type);
	}

	public static CommercePaymentEntry getCommercePaymentEntry(
			long commercePaymentEntryId)
		throws PortalException {

		return getService().getCommercePaymentEntry(commercePaymentEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.math.BigDecimal getRefundedAmount(
			long companyId, long classNameId, long classPK)
		throws PortalException {

		return getService().getRefundedAmount(companyId, classNameId, classPK);
	}

	public static List<CommercePaymentEntry> search(
			long companyId, long[] classNameIds, long[] classPKs,
			String[] currencyCodes, String keywords,
			String[] paymentMethodNames, int[] paymentStatuses,
			boolean excludeStatuses, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(
			companyId, classNameIds, classPKs, currencyCodes, keywords,
			paymentMethodNames, paymentStatuses, excludeStatuses, start, end,
			sort);
	}

	public static CommercePaymentEntry updateCommercePaymentEntry(
			String externalReferenceCode, long commercePaymentEntryId,
			long commerceChannelId, java.math.BigDecimal amount,
			String callbackURL, String cancelURL, String currencyCode,
			String errorMessages, String languageId, String note,
			String payload, String paymentIntegrationKey,
			int paymentIntegrationType, int paymentStatus, String reasonKey,
			String redirectURL, String transactionCode, int type)
		throws PortalException {

		return getService().updateCommercePaymentEntry(
			externalReferenceCode, commercePaymentEntryId, commerceChannelId,
			amount, callbackURL, cancelURL, currencyCode, errorMessages,
			languageId, note, payload, paymentIntegrationKey,
			paymentIntegrationType, paymentStatus, reasonKey, redirectURL,
			transactionCode, type);
	}

	public static CommercePaymentEntry updateExternalReferenceCode(
			long commercePaymentEntryId, String externalReferenceCode)
		throws PortalException {

		return getService().updateExternalReferenceCode(
			commercePaymentEntryId, externalReferenceCode);
	}

	public static CommercePaymentEntry updateNote(
			long commercePaymentEntryId, String note)
		throws PortalException {

		return getService().updateNote(commercePaymentEntryId, note);
	}

	public static CommercePaymentEntry updateReasonKey(
			long commercePaymentEntryId, String reasonKey)
		throws PortalException {

		return getService().updateReasonKey(commercePaymentEntryId, reasonKey);
	}

	public static CommercePaymentEntryService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CommercePaymentEntryService>
		_serviceSnapshot = new Snapshot<>(
			CommercePaymentEntryServiceUtil.class,
			CommercePaymentEntryService.class);

}