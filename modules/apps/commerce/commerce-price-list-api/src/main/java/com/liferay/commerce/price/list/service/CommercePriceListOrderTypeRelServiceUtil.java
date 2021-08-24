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

package com.liferay.commerce.price.list.service;

import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommercePriceListOrderTypeRel. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceListOrderTypeRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListOrderTypeRelService
 * @generated
 */
public class CommercePriceListOrderTypeRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListOrderTypeRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommercePriceListOrderTypeRel
			addCommercePriceListOrderTypeRel(
				long commercePriceListId, long commerceOrderTypeId,
				int priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommercePriceListOrderTypeRel(
			commercePriceListId, commerceOrderTypeId, priority, serviceContext);
	}

	public static void deleteCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws PortalException {

		getService().deleteCommercePriceListOrderTypeRel(
			commercePriceListOrderTypeRelId);
	}

	public static CommercePriceListOrderTypeRel
			fetchCommercePriceListOrderTypeRel(
				long commercePriceListId, long commerceOrderTypeId)
		throws PortalException {

		return getService().fetchCommercePriceListOrderTypeRel(
			commercePriceListId, commerceOrderTypeId);
	}

	public static CommercePriceListOrderTypeRel
			getCommercePriceListOrderTypeRel(
				long commercePriceListOrderTypeRelId)
		throws PortalException {

		return getService().getCommercePriceListOrderTypeRel(
			commercePriceListOrderTypeRelId);
	}

	public static List<CommercePriceListOrderTypeRel>
			getCommercePriceListOrderTypeRels(
				long commercePriceListId, String name, int start, int end,
				OrderByComparator<CommercePriceListOrderTypeRel>
					orderByComparator)
		throws PortalException {

		return getService().getCommercePriceListOrderTypeRels(
			commercePriceListId, name, start, end, orderByComparator);
	}

	public static int getCommercePriceListOrderTypeRelsCount(
			long commercePriceListId, String name)
		throws PortalException {

		return getService().getCommercePriceListOrderTypeRelsCount(
			commercePriceListId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommercePriceListOrderTypeRelService getService() {
		return _service;
	}

	private static volatile CommercePriceListOrderTypeRelService _service;

}