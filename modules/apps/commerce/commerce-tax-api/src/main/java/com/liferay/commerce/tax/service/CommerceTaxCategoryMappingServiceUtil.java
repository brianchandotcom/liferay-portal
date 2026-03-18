/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service;

import com.liferay.commerce.tax.model.CommerceTaxCategoryMapping;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceTaxCategoryMapping. This utility wraps
 * <code>com.liferay.commerce.tax.service.impl.CommerceTaxCategoryMappingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceTaxCategoryMappingService
 * @generated
 */
public class CommerceTaxCategoryMappingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.tax.service.impl.CommerceTaxCategoryMappingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceTaxCategoryMapping addCommerceTaxCategoryMapping(
			String externalReferenceCode, long groupId,
			long commerceTaxMethodId, long cpTaxCategoryId)
		throws PortalException {

		return getService().addCommerceTaxCategoryMapping(
			externalReferenceCode, groupId, commerceTaxMethodId,
			cpTaxCategoryId);
	}

	public static void deleteCommerceTaxCategoryMapping(
			long commerceTaxCategoryMappingId)
		throws PortalException {

		getService().deleteCommerceTaxCategoryMapping(
			commerceTaxCategoryMappingId);
	}

	public static CommerceTaxCategoryMapping fetchCommerceTaxCategoryMapping(
			long commerceTaxCategoryMappingId)
		throws PortalException {

		return getService().fetchCommerceTaxCategoryMapping(
			commerceTaxCategoryMappingId);
	}

	public static int getCommerceTaxCategoryMappingCount(
			long groupId, long commerceTaxMethodId)
		throws PortalException {

		return getService().getCommerceTaxCategoryMappingCount(
			groupId, commerceTaxMethodId);
	}

	public static List<CommerceTaxCategoryMapping>
			getCommerceTaxCategoryMappings(
				long groupId, long commerceTaxMethodId, int start, int end,
				OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws PortalException {

		return getService().getCommerceTaxCategoryMappings(
			groupId, commerceTaxMethodId, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceTaxCategoryMapping updateExternalReferenceCode(
			long commerceTaxCategoryMappingId, String externalReferenceCode)
		throws PortalException {

		return getService().updateExternalReferenceCode(
			commerceTaxCategoryMappingId, externalReferenceCode);
	}

	public static CommerceTaxCategoryMappingService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CommerceTaxCategoryMappingService>
		_serviceSnapshot = new Snapshot<>(
			CommerceTaxCategoryMappingServiceUtil.class,
			CommerceTaxCategoryMappingService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-1795755459