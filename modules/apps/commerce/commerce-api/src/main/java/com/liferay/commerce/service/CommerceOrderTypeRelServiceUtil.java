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

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceOrderTypeRel. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceOrderTypeRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRelService
 * @generated
 */
public class CommerceOrderTypeRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceOrderTypeRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceOrderTypeRel addCommerceOrderTypeRel(
			String className, long classPK, long commerceOrderTypeId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceOrderTypeRel(
			className, classPK, commerceOrderTypeId, serviceContext);
	}

	public static CommerceOrderTypeRel deleteCommerceOrderTypeRel(
			long commerceOrderTypeRelId)
		throws PortalException {

		return getService().deleteCommerceOrderTypeRel(commerceOrderTypeRelId);
	}

	public static void deleteCommerceOrderTypeRels(
			String className, long commerceOrderTypeId)
		throws PortalException {

		getService().deleteCommerceOrderTypeRels(
			className, commerceOrderTypeId);
	}

	public static List<CommerceOrderTypeRel>
			getCommerceOrderTypeCommerceChannelRels(
				long commerceOrderTypeId, String keywords, int start, int end)
		throws PortalException {

		return getService().getCommerceOrderTypeCommerceChannelRels(
			commerceOrderTypeId, keywords, start, end);
	}

	public static int getCommerceOrderTypeCommerceChannelRelsCount(
			long commerceOrderTypeId, String keywords)
		throws PortalException {

		return getService().getCommerceOrderTypeCommerceChannelRelsCount(
			commerceOrderTypeId, keywords);
	}

	public static CommerceOrderTypeRel getCommerceOrderTypeRel(
			long commerceOrderTypeRelId)
		throws PortalException {

		return getService().getCommerceOrderTypeRel(commerceOrderTypeRelId);
	}

	public static List<CommerceOrderTypeRel> getCommerceOrderTypeRels(
			String className, long classPK, int start, int end,
			OrderByComparator<CommerceOrderTypeRel> orderByComparator)
		throws PortalException {

		return getService().getCommerceOrderTypeRels(
			className, classPK, start, end, orderByComparator);
	}

	public static int getCommerceOrderTypeRelsCount(
			String className, long classPK)
		throws PortalException {

		return getService().getCommerceOrderTypeRelsCount(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceOrderTypeRelService getService() {
		return _service;
	}

	private static volatile CommerceOrderTypeRelService _service;

}