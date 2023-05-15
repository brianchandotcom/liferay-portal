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

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceInventoryWarehouseRel. This utility wraps
 * <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseRelService
 * @generated
 */
public class CommerceInventoryWarehouseRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceInventoryWarehouseRel
			addCommerceInventoryWarehouseRel(
				String className, long classPK,
				long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().addCommerceInventoryWarehouseRel(
			className, classPK, commerceInventoryWarehouseId);
	}

	public static void deleteCommerceInventoryWarehouseRel(
			long commerceInventoryWarehouseRelId)
		throws PortalException {

		getService().deleteCommerceInventoryWarehouseRel(
			commerceInventoryWarehouseRelId);
	}

	public static void deleteCommerceInventoryWarehouseRels(
			String className, long commerceInventoryWarehouseId)
		throws PortalException {

		getService().deleteCommerceInventoryWarehouseRels(
			className, commerceInventoryWarehouseId);
	}

	public static void
			deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseId(
				long commerceInventoryWarehouseId)
		throws PortalException {

		getService().
			deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId);
	}

	public static CommerceInventoryWarehouseRel
			fetchCommerceInventoryWarehouseRel(
				String className, long classPK,
				long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().fetchCommerceInventoryWarehouseRel(
			className, classPK, commerceInventoryWarehouseId);
	}

	public static CommerceInventoryWarehouseRel
			getCommerceInventoryWarehouseRel(
				long commerceInventoryWarehouseRelId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseRel(
			commerceInventoryWarehouseRelId);
	}

	public static List<CommerceInventoryWarehouseRel>
			getCommerceInventoryWarehouseRels(long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseRels(
			commerceInventoryWarehouseId);
	}

	public static List<CommerceInventoryWarehouseRel>
			getCommerceInventoryWarehouseRels(
				long commerceInventoryWarehouseId, int start, int end,
				OrderByComparator<CommerceInventoryWarehouseRel>
					orderByComparator)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseRels(
			commerceInventoryWarehouseId, start, end, orderByComparator);
	}

	public static int getCommerceInventoryWarehouseRelsCount(
			long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouseRelsCount(
			commerceInventoryWarehouseId);
	}

	public static List<CommerceInventoryWarehouseRel>
			getCommerceOrderTypeCommerceInventoryWarehouseRels(
				long commerceInventoryWarehouseId, String keywords, int start,
				int end)
		throws PortalException {

		return getService().getCommerceOrderTypeCommerceInventoryWarehouseRels(
			commerceInventoryWarehouseId, keywords, start, end);
	}

	public static int getCommerceOrderTypeCommerceInventoryWarehouseRelsCount(
			long commerceInventoryWarehouseId, String keywords)
		throws PortalException {

		return getService().
			getCommerceOrderTypeCommerceInventoryWarehouseRelsCount(
				commerceInventoryWarehouseId, keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceInventoryWarehouseRelService getService() {
		return _service;
	}

	public static void setService(
		CommerceInventoryWarehouseRelService service) {

		_service = service;
	}

	private static volatile CommerceInventoryWarehouseRelService _service;

}