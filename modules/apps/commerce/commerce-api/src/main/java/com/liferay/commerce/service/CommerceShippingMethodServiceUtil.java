/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			Map<java.util.Locale, String> descriptionMap, boolean active,
			String engineKey, java.io.File imageFile, double priority,
			String trackingURL)
		throws PortalException {

		return getService().addCommerceShippingMethod(
			groupId, nameMap, descriptionMap, active, engineKey, imageFile,
			priority, trackingURL);
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
			long groupId, boolean active, int start, int end,
			OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws PortalException {

		return getService().getCommerceShippingMethods(
			groupId, active, start, end, orderByComparator);
	}

	public static List<CommerceShippingMethod> getCommerceShippingMethods(
			long groupId, int start, int end,
			OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws PortalException {

		return getService().getCommerceShippingMethods(
			groupId, start, end, orderByComparator);
	}

	public static List<CommerceShippingMethod> getCommerceShippingMethods(
			long groupId, long countryId, boolean active)
		throws PortalException {

		return getService().getCommerceShippingMethods(
			groupId, countryId, active);
	}

	public static int getCommerceShippingMethodsCount(long groupId)
		throws PortalException {

		return getService().getCommerceShippingMethodsCount(groupId);
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
			Map<java.util.Locale, String> descriptionMap, boolean active,
			java.io.File imageFile, double priority, String trackingURL)
		throws PortalException {

		return getService().updateCommerceShippingMethod(
			commerceShippingMethodId, nameMap, descriptionMap, active,
			imageFile, priority, trackingURL);
	}

	public static CommerceShippingMethodService getService() {
		return _service;
	}

	public static void setService(CommerceShippingMethodService service) {
		_service = service;
	}

	private static volatile CommerceShippingMethodService _service;

}