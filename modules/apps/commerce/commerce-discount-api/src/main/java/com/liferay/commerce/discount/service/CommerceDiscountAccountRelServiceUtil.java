/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.service;

import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceDiscountAccountRel. This utility wraps
 * <code>com.liferay.commerce.discount.service.impl.CommerceDiscountAccountRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceDiscountAccountRelService
 * @generated
 */
public class CommerceDiscountAccountRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.discount.service.impl.CommerceDiscountAccountRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceDiscountAccountRel addCommerceDiscountAccountRel(
			long commerceDiscountId, long commerceAccountId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceDiscountAccountRel(
			commerceDiscountId, commerceAccountId, serviceContext);
	}

	public static void deleteCommerceDiscountAccountRel(
			long commerceDiscountAccountRelId)
		throws PortalException {

		getService().deleteCommerceDiscountAccountRel(
			commerceDiscountAccountRelId);
	}

	public static void deleteCommerceDiscountAccountRelsByCommerceDiscountId(
			long commerceDiscountId)
		throws PortalException {

		getService().deleteCommerceDiscountAccountRelsByCommerceDiscountId(
			commerceDiscountId);
	}

	public static CommerceDiscountAccountRel fetchCommerceDiscountAccountRel(
			long commerceAccountId, long commerceDiscountId)
		throws PortalException {

		return getService().fetchCommerceDiscountAccountRel(
			commerceAccountId, commerceDiscountId);
	}

	public static CommerceDiscountAccountRel getCommerceDiscountAccountRel(
			long commerceDiscountAccountRelId)
		throws PortalException {

		return getService().getCommerceDiscountAccountRel(
			commerceDiscountAccountRelId);
	}

	public static List<CommerceDiscountAccountRel>
			getCommerceDiscountAccountRels(
				long commerceDiscountId, int start, int end,
				OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws PortalException {

		return getService().getCommerceDiscountAccountRels(
			commerceDiscountId, start, end, orderByComparator);
	}

	public static List<CommerceDiscountAccountRel>
		getCommerceDiscountAccountRels(
			long commerceDiscountId, String name, int start, int end) {

		return getService().getCommerceDiscountAccountRels(
			commerceDiscountId, name, start, end);
	}

	public static int getCommerceDiscountAccountRelsCount(
			long commerceDiscountId)
		throws PortalException {

		return getService().getCommerceDiscountAccountRelsCount(
			commerceDiscountId);
	}

	public static int getCommerceDiscountAccountRelsCount(
		long commerceDiscountId, String name) {

		return getService().getCommerceDiscountAccountRelsCount(
			commerceDiscountId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceDiscountAccountRelService getService() {
		return _service;
	}

	public static void setService(CommerceDiscountAccountRelService service) {
		_service = service;
	}

	private static volatile CommerceDiscountAccountRelService _service;

}