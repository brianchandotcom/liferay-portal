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

package com.liferay.commerce.discount.service;

import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceDiscountRel. This utility wraps
 * <code>com.liferay.commerce.discount.service.impl.CommerceDiscountRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceDiscountRelService
 * @generated
 */
public class CommerceDiscountRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.discount.service.impl.CommerceDiscountRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceDiscountRel addCommerceDiscountRel(
			long commerceDiscountId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceDiscountRel(
			commerceDiscountId, className, classPK, serviceContext);
	}

	public static void deleteCommerceDiscountRel(long commerceDiscountRelId)
		throws PortalException {

		getService().deleteCommerceDiscountRel(commerceDiscountRelId);
	}

	public static CommerceDiscountRel fetchCommerceDiscountRel(
			String className, long classPK)
		throws PortalException {

		return getService().fetchCommerceDiscountRel(className, classPK);
	}

	public static List<CommerceDiscountRel> getCategoriesByCommerceDiscountId(
			long commerceDiscountId, String name, int start, int end)
		throws PortalException {

		return getService().getCategoriesByCommerceDiscountId(
			commerceDiscountId, name, start, end);
	}

	public static int getCategoriesByCommerceDiscountIdCount(
			long commerceDiscountId, String name)
		throws PortalException {

		return getService().getCategoriesByCommerceDiscountIdCount(
			commerceDiscountId, name);
	}

	public static long[] getClassPKs(long commerceDiscountId, String className)
		throws PortalException {

		return getService().getClassPKs(commerceDiscountId, className);
	}

	public static CommerceDiscountRel getCommerceDiscountRel(
			long commerceDiscountRelId)
		throws PortalException {

		return getService().getCommerceDiscountRel(commerceDiscountRelId);
	}

	public static List<CommerceDiscountRel> getCommerceDiscountRels(
			long commerceDiscountId, String className)
		throws PortalException {

		return getService().getCommerceDiscountRels(
			commerceDiscountId, className);
	}

	public static List<CommerceDiscountRel> getCommerceDiscountRels(
			long commerceDiscountId, String className, int start, int end,
			OrderByComparator<CommerceDiscountRel> orderByComparator)
		throws PortalException {

		return getService().getCommerceDiscountRels(
			commerceDiscountId, className, start, end, orderByComparator);
	}

	public static int getCommerceDiscountRelsCount(
			long commerceDiscountId, String className)
		throws PortalException {

		return getService().getCommerceDiscountRelsCount(
			commerceDiscountId, className);
	}

	public static List<CommerceDiscountRel>
			getCommercePricingClassesByCommerceDiscountId(
				long commerceDiscountId, String title, int start, int end)
		throws PortalException {

		return getService().getCommercePricingClassesByCommerceDiscountId(
			commerceDiscountId, title, start, end);
	}

	public static int getCommercePricingClassesByCommerceDiscountIdCount(
			long commerceDiscountId, String title)
		throws PortalException {

		return getService().getCommercePricingClassesByCommerceDiscountIdCount(
			commerceDiscountId, title);
	}

	public static List<CommerceDiscountRel>
			getCPDefinitionsByCommerceDiscountId(
				long commerceDiscountId, String name, String languageId,
				int start, int end)
		throws PortalException {

		return getService().getCPDefinitionsByCommerceDiscountId(
			commerceDiscountId, name, languageId, start, end);
	}

	public static int getCPDefinitionsByCommerceDiscountIdCount(
			long commerceDiscountId, String name, String languageId)
		throws PortalException {

		return getService().getCPDefinitionsByCommerceDiscountIdCount(
			commerceDiscountId, name, languageId);
	}

	public static List<CommerceDiscountRel> getCPInstancesByCommerceDiscountId(
			long commerceDiscountId, String sku, int start, int end)
		throws PortalException {

		return getService().getCPInstancesByCommerceDiscountId(
			commerceDiscountId, sku, start, end);
	}

	public static int getCPInstancesByCommerceDiscountIdCount(
			long commerceDiscountId, String sku)
		throws PortalException {

		return getService().getCPInstancesByCommerceDiscountIdCount(
			commerceDiscountId, sku);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceDiscountRelService getService() {
		return _service;
	}

	private static volatile CommerceDiscountRelService _service;

}