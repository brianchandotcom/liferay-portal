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

import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommercePriceListAccountRel. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceListAccountRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListAccountRelService
 * @generated
 */
public class CommercePriceListAccountRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListAccountRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommercePriceListAccountRel addCommercePriceListAccountRel(
			long commercePriceListId, long commerceAccountId, int order,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommercePriceListAccountRel(
			commercePriceListId, commerceAccountId, order, serviceContext);
	}

	public static void deleteCommercePriceListAccountRel(
			long commercePriceListAccountRelId)
		throws PortalException {

		getService().deleteCommercePriceListAccountRel(
			commercePriceListAccountRelId);
	}

	public static void deleteCommercePriceListAccountRelsByCommercePriceListId(
			long commercePriceListId)
		throws PortalException {

		getService().deleteCommercePriceListAccountRelsByCommercePriceListId(
			commercePriceListId);
	}

	public static CommercePriceListAccountRel fetchCommercePriceListAccountRel(
			long commercePriceListId, long commerceAccountId)
		throws PortalException {

		return getService().fetchCommercePriceListAccountRel(
			commercePriceListId, commerceAccountId);
	}

	public static CommercePriceListAccountRel getCommercePriceListAccountRel(
			long commercePriceListAccountRelId)
		throws PortalException {

		return getService().getCommercePriceListAccountRel(
			commercePriceListAccountRelId);
	}

	public static List<CommercePriceListAccountRel>
			getCommercePriceListAccountRels(long commercePriceListId)
		throws PortalException {

		return getService().getCommercePriceListAccountRels(
			commercePriceListId);
	}

	public static List<CommercePriceListAccountRel>
			getCommercePriceListAccountRels(
				long commercePriceListId, int start, int end,
				OrderByComparator<CommercePriceListAccountRel>
					orderByComparator)
		throws PortalException {

		return getService().getCommercePriceListAccountRels(
			commercePriceListId, start, end, orderByComparator);
	}

	public static List<CommercePriceListAccountRel>
		getCommercePriceListAccountRels(
			long commercePriceListId, String name, int start, int end) {

		return getService().getCommercePriceListAccountRels(
			commercePriceListId, name, start, end);
	}

	public static int getCommercePriceListAccountRelsCount(
			long commercePriceListId)
		throws PortalException {

		return getService().getCommercePriceListAccountRelsCount(
			commercePriceListId);
	}

	public static int getCommercePriceListAccountRelsCount(
		long commercePriceListId, String name) {

		return getService().getCommercePriceListAccountRelsCount(
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

	public static CommercePriceListAccountRelService getService() {
		return _service;
	}

	public static void setService(CommercePriceListAccountRelService service) {
		_service = service;
	}

	private static volatile CommercePriceListAccountRelService _service;

}