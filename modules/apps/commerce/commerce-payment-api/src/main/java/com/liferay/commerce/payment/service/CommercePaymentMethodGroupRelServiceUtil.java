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

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CommercePaymentMethodGroupRel. This utility wraps
 * <code>com.liferay.commerce.payment.service.impl.CommercePaymentMethodGroupRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelService
 * @generated
 */
public class CommercePaymentMethodGroupRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.payment.service.impl.CommercePaymentMethodGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				long groupId, long classPK, long countryId)
		throws PortalException {

		return getService().addCommerceAddressRestriction(
			groupId, classPK, countryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceAddressRestriction
			addCommerceAddressRestriction(
				long classPK, long countryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAddressRestriction(
			classPK, countryId, serviceContext);
	}

	public static CommercePaymentMethodGroupRel
			addCommercePaymentMethodGroupRel(
				long groupId, Map<java.util.Locale, String> nameMap,
				Map<java.util.Locale, String> descriptionMap,
				java.io.File imageFile, String engineKey, double priority,
				boolean active)
		throws PortalException {

		return getService().addCommercePaymentMethodGroupRel(
			groupId, nameMap, descriptionMap, imageFile, engineKey, priority,
			active);
	}

	public static void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws PortalException {

		getService().deleteCommerceAddressRestriction(
			commerceAddressRestrictionId);
	}

	public static void deleteCommerceAddressRestrictions(
			long commercePaymentMethodGroupRelId)
		throws PortalException {

		getService().deleteCommerceAddressRestrictions(
			commercePaymentMethodGroupRelId);
	}

	public static void deleteCommercePaymentMethodGroupRel(
			long commercePaymentMethodGroupRelId)
		throws PortalException {

		getService().deleteCommercePaymentMethodGroupRel(
			commercePaymentMethodGroupRelId);
	}

	public static CommercePaymentMethodGroupRel
			fetchCommercePaymentMethodGroupRel(
				long commercePaymentMethodGroupRelId)
		throws PortalException {

		return getService().fetchCommercePaymentMethodGroupRel(
			commercePaymentMethodGroupRelId);
	}

	public static CommercePaymentMethodGroupRel
			fetchCommercePaymentMethodGroupRel(long groupId, String engineKey)
		throws PortalException {

		return getService().fetchCommercePaymentMethodGroupRel(
			groupId, engineKey);
	}

	public static List<com.liferay.commerce.model.CommerceAddressRestriction>
			getCommerceAddressRestrictions(
				long classPK, int start, int end,
				OrderByComparator
					<com.liferay.commerce.model.CommerceAddressRestriction>
						orderByComparator)
		throws PortalException {

		return getService().getCommerceAddressRestrictions(
			classPK, start, end, orderByComparator);
	}

	public static int getCommerceAddressRestrictionsCount(long classPK)
		throws PortalException {

		return getService().getCommerceAddressRestrictionsCount(classPK);
	}

	public static CommercePaymentMethodGroupRel
			getCommercePaymentMethodGroupRel(
				long commercePaymentMethodGroupRelId)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRel(
			commercePaymentMethodGroupRelId);
	}

	public static CommercePaymentMethodGroupRel
			getCommercePaymentMethodGroupRel(long groupId, String engineKey)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRel(
			groupId, engineKey);
	}

	public static List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(long groupId)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRels(groupId);
	}

	public static List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(long groupId, boolean active)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRels(groupId, active);
	}

	public static List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(
				long groupId, boolean active, int start, int end)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRels(
			groupId, active, start, end);
	}

	public static List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(
				long groupId, boolean active, int start, int end,
				OrderByComparator<CommercePaymentMethodGroupRel>
					orderByComparator)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRels(
			groupId, active, start, end, orderByComparator);
	}

	public static List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(
				long groupId, int start, int end,
				OrderByComparator<CommercePaymentMethodGroupRel>
					orderByComparator)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRels(
			groupId, start, end, orderByComparator);
	}

	public static List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(
				long groupId, long countryId, boolean active)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRels(
			groupId, countryId, active);
	}

	public static int getCommercePaymentMethodGroupRelsCount(long groupId)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRelsCount(groupId);
	}

	public static int getCommercePaymentMethodGroupRelsCount(
			long groupId, boolean active)
		throws PortalException {

		return getService().getCommercePaymentMethodGroupRelsCount(
			groupId, active);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommercePaymentMethodGroupRel setActive(
			long commercePaymentMethodGroupRelId, boolean active)
		throws PortalException {

		return getService().setActive(commercePaymentMethodGroupRelId, active);
	}

	public static CommercePaymentMethodGroupRel
			updateCommercePaymentMethodGroupRel(
				long commercePaymentMethodGroupRelId,
				Map<java.util.Locale, String> nameMap,
				Map<java.util.Locale, String> descriptionMap,
				java.io.File imageFile, double priority, boolean active)
		throws PortalException {

		return getService().updateCommercePaymentMethodGroupRel(
			commercePaymentMethodGroupRelId, nameMap, descriptionMap, imageFile,
			priority, active);
	}

	public static CommercePaymentMethodGroupRelService getService() {
		return _service;
	}

	private static volatile CommercePaymentMethodGroupRelService _service;

}