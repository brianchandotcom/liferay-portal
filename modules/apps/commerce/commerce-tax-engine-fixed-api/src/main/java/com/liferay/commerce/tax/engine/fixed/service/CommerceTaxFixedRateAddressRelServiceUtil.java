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

package com.liferay.commerce.tax.engine.fixed.service;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceTaxFixedRateAddressRel. This utility wraps
 * <code>com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelService
 * @generated
 */
public class CommerceTaxFixedRateAddressRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceTaxFixedRateAddressRel
			addCommerceTaxFixedRateAddressRel(
				long groupId, long commerceTaxMethodId, long cpTaxCategoryId,
				long countryId, long regionId, String zip, double rate)
		throws PortalException {

		return getService().addCommerceTaxFixedRateAddressRel(
			groupId, commerceTaxMethodId, cpTaxCategoryId, countryId, regionId,
			zip, rate);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceTaxFixedRateAddressRel
			addCommerceTaxFixedRateAddressRel(
				long commerceTaxMethodId, long cpTaxCategoryId, long countryId,
				long regionId, String zip, double rate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceTaxFixedRateAddressRel(
			commerceTaxMethodId, cpTaxCategoryId, countryId, regionId, zip,
			rate, serviceContext);
	}

	public static void deleteCommerceTaxFixedRateAddressRel(
			long commerceTaxFixedRateAddressRelId)
		throws PortalException {

		getService().deleteCommerceTaxFixedRateAddressRel(
			commerceTaxFixedRateAddressRelId);
	}

	public static CommerceTaxFixedRateAddressRel
			fetchCommerceTaxFixedRateAddressRel(
				long commerceTaxFixedRateAddressRelId)
		throws PortalException {

		return getService().fetchCommerceTaxFixedRateAddressRel(
			commerceTaxFixedRateAddressRelId);
	}

	public static List<CommerceTaxFixedRateAddressRel>
			getCommerceTaxMethodFixedRateAddressRels(
				long groupId, long commerceTaxMethodId, int start, int end,
				OrderByComparator<CommerceTaxFixedRateAddressRel>
					orderByComparator)
		throws PortalException {

		return getService().getCommerceTaxMethodFixedRateAddressRels(
			groupId, commerceTaxMethodId, start, end, orderByComparator);
	}

	public static int getCommerceTaxMethodFixedRateAddressRelsCount(
			long groupId, long commerceTaxMethodId)
		throws PortalException {

		return getService().getCommerceTaxMethodFixedRateAddressRelsCount(
			groupId, commerceTaxMethodId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceTaxFixedRateAddressRel
			updateCommerceTaxFixedRateAddressRel(
				long commerceTaxFixedRateAddressRelId, long countryId,
				long regionId, String zip, double rate)
		throws PortalException {

		return getService().updateCommerceTaxFixedRateAddressRel(
			commerceTaxFixedRateAddressRelId, countryId, regionId, zip, rate);
	}

	public static CommerceTaxFixedRateAddressRelService getService() {
		return _service;
	}

	private static volatile CommerceTaxFixedRateAddressRelService _service;

}