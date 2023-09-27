/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.portal.kernel.exception.PortalException;
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
	public static List<CommercePaymentEntry> getCommercePaymentEntries(
			long companyId, long classNameId, long classPK, int start, int end,
			OrderByComparator<CommercePaymentEntry> orderByComparator)
		throws PortalException {

		return getService().getCommercePaymentEntries(
			companyId, classNameId, classPK, start, end, orderByComparator);
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

	public static List<CommercePaymentEntry> search(
			long companyId, long[] classNameIds, long[] classPKs,
			String[] currencyCodes, String keywords,
			String[] paymentMethodNames, int[] paymentStatuses,
			boolean excludeStatuses, int start, int end, String orderByField,
			boolean reverse)
		throws PortalException {

		return getService().search(
			companyId, classNameIds, classPKs, currencyCodes, keywords,
			paymentMethodNames, paymentStatuses, excludeStatuses, start, end,
			orderByField, reverse);
	}

	public static CommercePaymentEntryService getService() {
		return _service;
	}

	public static void setService(CommercePaymentEntryService service) {
		_service = service;
	}

	private static volatile CommercePaymentEntryService _service;

}