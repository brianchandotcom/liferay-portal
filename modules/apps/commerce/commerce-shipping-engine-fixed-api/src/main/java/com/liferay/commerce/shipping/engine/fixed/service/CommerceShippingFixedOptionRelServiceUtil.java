/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.service;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceShippingFixedOptionRel. This utility wraps
 * <code>com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelService
 * @generated
 */
public class CommerceShippingFixedOptionRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceShippingFixedOptionRel
			addCommerceShippingFixedOptionRel(
				long groupId, long commerceShippingMethodId,
				long commerceShippingFixedOptionId,
				long commerceInventoryWarehouseId, long countryId,
				long regionId, String zip, double weightFrom, double weightTo,
				java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
		throws PortalException {

		return getService().addCommerceShippingFixedOptionRel(
			groupId, commerceShippingMethodId, commerceShippingFixedOptionId,
			commerceInventoryWarehouseId, countryId, regionId, zip, weightFrom,
			weightTo, fixedPrice, rateUnitWeightPrice, ratePercentage);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceShippingFixedOptionRel
			addCommerceShippingFixedOptionRel(
				long commerceShippingMethodId,
				long commerceShippingFixedOptionId,
				long commerceInventoryWarehouseId, long countryId,
				long regionId, String zip, double weightFrom, double weightTo,
				java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceShippingFixedOptionRel(
			commerceShippingMethodId, commerceShippingFixedOptionId,
			commerceInventoryWarehouseId, countryId, regionId, zip, weightFrom,
			weightTo, fixedPrice, rateUnitWeightPrice, ratePercentage,
			serviceContext);
	}

	public static void deleteCommerceShippingFixedOptionRel(
			long commerceShippingFixedOptionRelId)
		throws PortalException {

		getService().deleteCommerceShippingFixedOptionRel(
			commerceShippingFixedOptionRelId);
	}

	public static CommerceShippingFixedOptionRel
			fetchCommerceShippingFixedOptionRel(
				long commerceShippingFixedOptionRelId)
		throws PortalException {

		return getService().fetchCommerceShippingFixedOptionRel(
			commerceShippingFixedOptionRelId);
	}

	public static List<CommerceShippingFixedOptionRel>
			getCommerceShippingMethodFixedOptionRels(
				long commerceShippingMethodId, int start, int end,
				OrderByComparator<CommerceShippingFixedOptionRel>
					orderByComparator)
		throws PortalException {

		return getService().getCommerceShippingMethodFixedOptionRels(
			commerceShippingMethodId, start, end, orderByComparator);
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
			long commerceShippingMethodId)
		throws PortalException {

		return getService().getCommerceShippingMethodFixedOptionRelsCount(
			commerceShippingMethodId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceShippingFixedOptionRel
			updateCommerceShippingFixedOptionRel(
				long commerceShippingFixedOptionRelId,
				long commerceInventoryWarehouseId, long countryId,
				long regionId, String zip, double weightFrom, double weightTo,
				java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
		throws PortalException {

		return getService().updateCommerceShippingFixedOptionRel(
			commerceShippingFixedOptionRelId, commerceInventoryWarehouseId,
			countryId, regionId, zip, weightFrom, weightTo, fixedPrice,
			rateUnitWeightPrice, ratePercentage);
	}

	public static CommerceShippingFixedOptionRelService getService() {
		return _service;
	}

	public static void setService(
		CommerceShippingFixedOptionRelService service) {

		_service = service;
	}

	private static volatile CommerceShippingFixedOptionRelService _service;

}