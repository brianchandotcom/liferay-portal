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

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommercePaymentMethodGroupRelQualifier. This utility wraps
 * <code>com.liferay.commerce.payment.service.impl.CommercePaymentMethodGroupRelQualifierServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelQualifierService
 * @generated
 */
public class CommercePaymentMethodGroupRelQualifierServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.payment.service.impl.CommercePaymentMethodGroupRelQualifierServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommercePaymentMethodGroupRelQualifier
			addCommercePaymentMethodGroupRelQualifier(
				String className, long classPK,
				long commercePaymentMethodGroupRelId)
		throws PortalException {

		return getService().addCommercePaymentMethodGroupRelQualifier(
			className, classPK, commercePaymentMethodGroupRelId);
	}

	public static void deleteCommercePaymentMethodGroupRelQualifier(
			long commercePaymentMethodGroupRelQualifierId)
		throws PortalException {

		getService().deleteCommercePaymentMethodGroupRelQualifier(
			commercePaymentMethodGroupRelQualifierId);
	}

	public static void deleteCommercePaymentMethodGroupRelQualifiers(
			String className, long commercePaymentMethodGroupRelId)
		throws PortalException {

		getService().deleteCommercePaymentMethodGroupRelQualifiers(
			className, commercePaymentMethodGroupRelId);
	}

	public static void
			deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId(
				long commercePaymentMethodGroupRelId)
		throws PortalException {

		getService().
			deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId(
				commercePaymentMethodGroupRelId);
	}

	public static CommercePaymentMethodGroupRelQualifier
			fetchCommercePaymentMethodGroupRelQualifier(
				String className, long classPK,
				long commercePaymentMethodGroupRelId)
		throws PortalException {

		return getService().fetchCommercePaymentMethodGroupRelQualifier(
			className, classPK, commercePaymentMethodGroupRelId);
	}

	public static List<CommercePaymentMethodGroupRelQualifier>
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
				long commercePaymentMethodGroupRelId, String keywords,
				int start, int end)
		throws PortalException {

		return getService().
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
				commercePaymentMethodGroupRelId, keywords, start, end);
	}

	public static int
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
				long commercePaymentMethodGroupRelId, String keywords)
		throws PortalException {

		return getService().
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
				commercePaymentMethodGroupRelId, keywords);
	}

	public static CommercePaymentMethodGroupRelQualifier
			getCommercePaymentMethodGroupRelQualifier(
				long commercePaymentMethodGroupRelQualifierId)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRelQualifier(
			commercePaymentMethodGroupRelQualifierId);
	}

	public static List<CommercePaymentMethodGroupRelQualifier>
			getCommercePaymentMethodGroupRelQualifiers(
				long commercePaymentMethodGroupRelId, int start, int end,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRelQualifiers(
			commercePaymentMethodGroupRelId, start, end, orderByComparator);
	}

	public static List<CommercePaymentMethodGroupRelQualifier>
			getCommercePaymentMethodGroupRelQualifiers(
				String className, long commercePaymentMethodGroupRelId)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRelQualifiers(
			className, commercePaymentMethodGroupRelId);
	}

	public static int getCommercePaymentMethodGroupRelQualifiersCount(
			long commercePaymentMethodGroupRelId)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRelQualifiersCount(
			commercePaymentMethodGroupRelId);
	}

	public static List<CommercePaymentMethodGroupRelQualifier>
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
				long commercePaymentMethodGroupRelId, String keywords,
				int start, int end)
		throws PortalException {

		return getService().
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
				commercePaymentMethodGroupRelId, keywords, start, end);
	}

	public static int
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
				long commercePaymentMethodGroupRelId, String keywords)
		throws PortalException {

		return getService().
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
				commercePaymentMethodGroupRelId, keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommercePaymentMethodGroupRelQualifierService getService() {
		return _service;
	}

	private static volatile CommercePaymentMethodGroupRelQualifierService
		_service;

}