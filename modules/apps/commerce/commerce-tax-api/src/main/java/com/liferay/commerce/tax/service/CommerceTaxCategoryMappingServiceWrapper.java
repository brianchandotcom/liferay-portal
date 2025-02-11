/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxCategoryMappingService}.
 *
 * @author Marco Leo
 * @see CommerceTaxCategoryMappingService
 * @generated
 */
public class CommerceTaxCategoryMappingServiceWrapper
	implements CommerceTaxCategoryMappingService,
			   ServiceWrapper<CommerceTaxCategoryMappingService> {

	public CommerceTaxCategoryMappingServiceWrapper() {
		this(null);
	}

	public CommerceTaxCategoryMappingServiceWrapper(
		CommerceTaxCategoryMappingService commerceTaxCategoryMappingService) {

		_commerceTaxCategoryMappingService = commerceTaxCategoryMappingService;
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxCategoryMapping
			addCommerceTaxCategoryMapping(
				long groupId, long commerceTaxMethodId, long cpTaxCategoryId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxCategoryMappingService.addCommerceTaxCategoryMapping(
			groupId, commerceTaxMethodId, cpTaxCategoryId,
			externalReferenceCode);
	}

	@Override
	public void deleteCommerceTaxCategoryMapping(
			long commerceTaxCategoryMappingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceTaxCategoryMappingService.deleteCommerceTaxCategoryMapping(
			commerceTaxCategoryMappingId);
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxCategoryMapping
			fetchCommerceTaxCategoryMapping(long commerceTaxCategoryMappingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxCategoryMappingService.
			fetchCommerceTaxCategoryMapping(commerceTaxCategoryMappingId);
	}

	@Override
	public int getCommerceTaxCategoryMappingCount(
			long groupId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxCategoryMappingService.
			getCommerceTaxCategoryMappingCount(groupId, commerceTaxMethodId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.tax.model.CommerceTaxCategoryMapping>
				getCommerceTaxCategoryMappings(
					long groupId, long commerceTaxMethodId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.tax.model.
							CommerceTaxCategoryMapping> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxCategoryMappingService.
			getCommerceTaxCategoryMappings(
				groupId, commerceTaxMethodId, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceTaxCategoryMappingService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.tax.model.CommerceTaxCategoryMapping
			updateCommerceTaxCategoryMapping(
				long commerceTaxCategoryMappingId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTaxCategoryMappingService.
			updateCommerceTaxCategoryMapping(
				commerceTaxCategoryMappingId, externalReferenceCode);
	}

	@Override
	public CommerceTaxCategoryMappingService getWrappedService() {
		return _commerceTaxCategoryMappingService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxCategoryMappingService commerceTaxCategoryMappingService) {

		_commerceTaxCategoryMappingService = commerceTaxCategoryMappingService;
	}

	private CommerceTaxCategoryMappingService
		_commerceTaxCategoryMappingService;

}