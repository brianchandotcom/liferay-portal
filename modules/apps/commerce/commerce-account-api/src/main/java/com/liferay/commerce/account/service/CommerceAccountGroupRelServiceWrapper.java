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
 * Provides a wrapper for {@link CommerceAccountGroupRelService}.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRelService
 * @generated
 */
public class CommerceAccountGroupRelServiceWrapper
	implements CommerceAccountGroupRelService,
			   ServiceWrapper<CommerceAccountGroupRelService> {

	public CommerceAccountGroupRelServiceWrapper() {
		this(null);
	}

	public CommerceAccountGroupRelServiceWrapper(
		CommerceAccountGroupRelService commerceAccountGroupRelService) {

		_commerceAccountGroupRelService = commerceAccountGroupRelService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
			addCommerceAccountGroupRel(
				String className, long classPK, long commerceAccountGroupId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelService.addCommerceAccountGroupRel(
			className, classPK, commerceAccountGroupId, serviceContext);
	}

	@Override
	public void deleteCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountGroupRelService.deleteCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	@Override
	public void deleteCommerceAccountGroupRels(String className, long classPK) {
		_commerceAccountGroupRelService.deleteCommerceAccountGroupRels(
			className, classPK);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
			getCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelService.getCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
				getCommerceAccountGroupRels(
					long commerceAccountGroupId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.account.model.
							CommerceAccountGroupRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelService.getCommerceAccountGroupRels(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
				getCommerceAccountGroupRels(
					String className, long classPK, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.account.model.
							CommerceAccountGroupRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelService.getCommerceAccountGroupRels(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAccountGroupRelsCount(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelService.getCommerceAccountGroupRelsCount(
			commerceAccountGroupId);
	}

	@Override
	public int getCommerceAccountGroupRelsCount(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelService.getCommerceAccountGroupRelsCount(
			className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountGroupRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceAccountGroupRelService getWrappedService() {
		return _commerceAccountGroupRelService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountGroupRelService commerceAccountGroupRelService) {

		_commerceAccountGroupRelService = commerceAccountGroupRelService;
	}

	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

}