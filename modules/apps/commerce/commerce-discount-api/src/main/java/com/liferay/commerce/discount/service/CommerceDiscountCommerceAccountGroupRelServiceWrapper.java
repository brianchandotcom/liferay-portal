/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDiscountCommerceAccountGroupRelService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRelService
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelServiceWrapper
	implements CommerceDiscountCommerceAccountGroupRelService,
			   ServiceWrapper<CommerceDiscountCommerceAccountGroupRelService> {

	public CommerceDiscountCommerceAccountGroupRelServiceWrapper() {
		this(null);
	}

	public CommerceDiscountCommerceAccountGroupRelServiceWrapper(
		CommerceDiscountCommerceAccountGroupRelService
			commerceDiscountCommerceAccountGroupRelService) {

		_commerceDiscountCommerceAccountGroupRelService =
			commerceDiscountCommerceAccountGroupRelService;
	}

	@Override
	public
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					addCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountId, long commerceAccountGroupId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountCommerceAccountGroupRelService.
			addCommerceDiscountCommerceAccountGroupRel(
				commerceDiscountId, commerceAccountGroupId, serviceContext);
	}

	@Override
	public void deleteCommerceDiscountCommerceAccountGroupRel(
			long commerceDiscountCommerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceDiscountCommerceAccountGroupRelService.
			deleteCommerceDiscountCommerceAccountGroupRel(
				commerceDiscountCommerceAccountGroupRelId);
	}

	@Override
	public void
			deleteCommerceDiscountCommerceAccountGroupRelsByCommerceDiscountId(
				long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceDiscountCommerceAccountGroupRelService.
			deleteCommerceDiscountCommerceAccountGroupRelsByCommerceDiscountId(
				commerceDiscountId);
	}

	@Override
	public
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					fetchCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountId, long commerceAccountGroupId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountCommerceAccountGroupRelService.
			fetchCommerceDiscountCommerceAccountGroupRel(
				commerceDiscountId, commerceAccountGroupId);
	}

	@Override
	public
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					getCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountCommerceAccountGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountCommerceAccountGroupRelService.
			getCommerceDiscountCommerceAccountGroupRel(
				commerceDiscountCommerceAccountGroupRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel>
					getCommerceDiscountCommerceAccountGroupRels(
						long commerceDiscountId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.discount.model.
								CommerceDiscountCommerceAccountGroupRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountCommerceAccountGroupRelService.
			getCommerceDiscountCommerceAccountGroupRels(
				commerceDiscountId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel>
				getCommerceDiscountCommerceAccountGroupRels(
					long commerceDiscountId, String name, int start, int end) {

		return _commerceDiscountCommerceAccountGroupRelService.
			getCommerceDiscountCommerceAccountGroupRels(
				commerceDiscountId, name, start, end);
	}

	@Override
	public int getCommerceDiscountCommerceAccountGroupRelsCount(
			long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountCommerceAccountGroupRelService.
			getCommerceDiscountCommerceAccountGroupRelsCount(
				commerceDiscountId);
	}

	@Override
	public int getCommerceDiscountCommerceAccountGroupRelsCount(
		long commerceDiscountId, String name) {

		return _commerceDiscountCommerceAccountGroupRelService.
			getCommerceDiscountCommerceAccountGroupRelsCount(
				commerceDiscountId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountCommerceAccountGroupRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceDiscountCommerceAccountGroupRelService getWrappedService() {
		return _commerceDiscountCommerceAccountGroupRelService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountCommerceAccountGroupRelService
			commerceDiscountCommerceAccountGroupRelService) {

		_commerceDiscountCommerceAccountGroupRelService =
			commerceDiscountCommerceAccountGroupRelService;
	}

	private CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

}