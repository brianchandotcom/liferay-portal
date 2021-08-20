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

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CommerceShippingMethod. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceShippingMethodServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodService
 * @generated
 */
public class CommerceShippingMethodServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShippingMethodServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				long groupId, long commerceShippingMethodId, long countryId)
		throws PortalException {

		return getService().addCommerceAddressRestriction(
			groupId, commerceShippingMethodId, countryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				long commerceShippingMethodId, long countryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAddressRestriction(
			commerceShippingMethodId, countryId, serviceContext);
	}

	public static CommerceShippingMethod addCommerceShippingMethod(
			long groupId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			java.io.File imageFile, String engineKey, double priority,
			boolean active)
		throws PortalException {

		return getService().addCommerceShippingMethod(
			groupId, nameMap, descriptionMap, imageFile, engineKey, priority,
			active);
	}

	public static CommerceShippingMethod createCommerceShippingMethod(
			long commerceShippingMethodId)
		throws PortalException {

		return getService().createCommerceShippingMethod(
			commerceShippingMethodId);
	}

	public static void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws PortalException {

		getService().deleteCommerceAddressRestriction(
			commerceAddressRestrictionId);
	}

	public static void deleteCommerceAddressRestrictions(
			long commerceShippingMethodId)
		throws PortalException {

		getService().deleteCommerceAddressRestrictions(
			commerceShippingMethodId);
	}

	public static void deleteCommerceShippingMethod(
			long commerceShippingMethodId)
		throws PortalException {

		getService().deleteCommerceShippingMethod(commerceShippingMethodId);
	}

	public static CommerceShippingMethod fetchCommerceShippingMethod(
			long groupId, String engineKey)
		throws PortalException {

		return getService().fetchCommerceShippingMethod(groupId, engineKey);
	}

	public static List<com.liferay.commerce.model.CommerceAddressRestriction>
			getCommerceAddressRestrictions(
				long commerceShippingMethodId, int start, int end,
				OrderByComparator
					<com.liferay.commerce.model.CommerceAddressRestriction>
						orderByComparator)
		throws PortalException {

		return getService().getCommerceAddressRestrictions(
			commerceShippingMethodId, start, end, orderByComparator);
	}

	public static int getCommerceAddressRestrictionsCount(
			long commerceShippingMethodId)
		throws PortalException {

		return getService().getCommerceAddressRestrictionsCount(
			commerceShippingMethodId);
	}

	public static CommerceShippingMethod getCommerceShippingMethod(
			long commerceShippingMethodId)
		throws PortalException {

		return getService().getCommerceShippingMethod(commerceShippingMethodId);
	}

	public static List<CommerceShippingMethod> getCommerceShippingMethods(
			long groupId)
		throws PortalException {

		return getService().getCommerceShippingMethods(groupId);
	}

	public static List<CommerceShippingMethod> getCommerceShippingMethods(
			long groupId, boolean active)
		throws PortalException {

		return getService().getCommerceShippingMethods(groupId, active);
	}

	public static List<CommerceShippingMethod> getCommerceShippingMethods(
			long groupId, long countryId, boolean active)
		throws PortalException {

		return getService().getCommerceShippingMethods(
			groupId, countryId, active);
	}

	public static int getCommerceShippingMethodsCount(
			long groupId, boolean active)
		throws PortalException {

		return getService().getCommerceShippingMethodsCount(groupId, active);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceShippingMethod setActive(
			long commerceShippingMethodId, boolean active)
		throws PortalException {

		return getService().setActive(commerceShippingMethodId, active);
	}

	public static CommerceShippingMethod updateCommerceShippingMethod(
			long commerceShippingMethodId,
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			java.io.File imageFile, double priority, boolean active)
		throws PortalException {

		return getService().updateCommerceShippingMethod(
			commerceShippingMethodId, nameMap, descriptionMap, imageFile,
			priority, active);
	}

	public static CommerceShippingMethodService getService() {
		return _service;
	}

	private static volatile CommerceShippingMethodService _service;

}