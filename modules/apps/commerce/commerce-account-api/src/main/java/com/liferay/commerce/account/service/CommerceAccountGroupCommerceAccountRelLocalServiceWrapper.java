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

package com.liferay.commerce.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAccountGroupCommerceAccountRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRelLocalService
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelLocalServiceWrapper
	implements CommerceAccountGroupCommerceAccountRelLocalService,
			   ServiceWrapper
				   <CommerceAccountGroupCommerceAccountRelLocalService> {

	public CommerceAccountGroupCommerceAccountRelLocalServiceWrapper(
		CommerceAccountGroupCommerceAccountRelLocalService
			commerceAccountGroupCommerceAccountRelLocalService) {

		_commerceAccountGroupCommerceAccountRelLocalService =
			commerceAccountGroupCommerceAccountRelLocalService;
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					addCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupId, long commerceAccountId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupId, commerceAccountId, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					addCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupId, long commerceAccountId,
						String externalReferenceCode,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupId, commerceAccountId,
				externalReferenceCode, serviceContext);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					deleteCommerceAccountGroupCommerceAccountRel(
						com.liferay.commerce.account.model.
							CommerceAccountGroupCommerceAccountRel
								commerceAccountGroupCommerceAccountRel)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			deleteCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRel);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public void deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
		long commerceAccountGroupId) {

		_commerceAccountGroupCommerceAccountRelLocalService.
			deleteCommerceAccountGroupCommerceAccountRelByCAccountGroupId(
				commerceAccountGroupId);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
				fetchCommerceAccountGroupCommerceAccountRel(
					long commerceAccountGroupId, long commerceAccountId) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			fetchCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupId, commerceAccountId);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					getCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupCommerceAccountRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRelId);
	}

	@Override
	public
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					getCommerceAccountGroupCommerceAccountRel(
						long commerceAccountGroupId, long commerceAccountId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupId, commerceAccountId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel>
				getCommerceAccountGroupCommerceAccountRels(
					long commerceAccountId) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRels(commerceAccountId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel>
				getCommerceAccountGroupCommerceAccountRels(
					long commerceAccountGroupId, int start, int end) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRels(
				commerceAccountGroupId, start, end);
	}

	@Override
	public int getCommerceAccountGroupCommerceAccountRelsCount(
		long commerceAccountGroupId) {

		return _commerceAccountGroupCommerceAccountRelLocalService.
			getCommerceAccountGroupCommerceAccountRelsCount(
				commerceAccountGroupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountGroupCommerceAccountRelLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceAccountGroupCommerceAccountRelLocalService
		getWrappedService() {

		return _commerceAccountGroupCommerceAccountRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountGroupCommerceAccountRelLocalService
			commerceAccountGroupCommerceAccountRelLocalService) {

		_commerceAccountGroupCommerceAccountRelLocalService =
			commerceAccountGroupCommerceAccountRelLocalService;
	}

	private CommerceAccountGroupCommerceAccountRelLocalService
		_commerceAccountGroupCommerceAccountRelLocalService;

}