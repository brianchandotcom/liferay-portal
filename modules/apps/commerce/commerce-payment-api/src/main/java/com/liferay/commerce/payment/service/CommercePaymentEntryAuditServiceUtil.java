/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service;

import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommercePaymentEntryAudit. This utility wraps
 * <code>com.liferay.commerce.payment.service.impl.CommercePaymentEntryAuditServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntryAuditService
 * @generated
 */
public class CommercePaymentEntryAuditServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.payment.service.impl.CommercePaymentEntryAuditServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<CommercePaymentEntryAudit> getCommercePaymentEntries(
			long companyId, long commercePaymentEntryId, int start, int end,
			OrderByComparator<CommercePaymentEntryAudit> orderByComparator)
		throws PortalException {

		return getService().getCommercePaymentEntries(
			companyId, commercePaymentEntryId, start, end, orderByComparator);
	}

	public static CommercePaymentEntryAudit getCommercePaymentEntryAudit(
			long commercePaymentEntryAuditId)
		throws PortalException {

		return getService().getCommercePaymentEntryAudit(
			commercePaymentEntryAuditId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CommercePaymentEntryAudit> search(
			long companyId, long[] commercePaymentEntryIds, String[] logTypes,
			String keywords, int start, int end, String orderByField,
			boolean reverse)
		throws PortalException {

		return getService().search(
			companyId, commercePaymentEntryIds, logTypes, keywords, start, end,
			orderByField, reverse);
	}

	public static CommercePaymentEntryAuditService getService() {
		return _service;
	}

	public static void setService(CommercePaymentEntryAuditService service) {
		_service = service;
	}

	private static volatile CommercePaymentEntryAuditService _service;

}