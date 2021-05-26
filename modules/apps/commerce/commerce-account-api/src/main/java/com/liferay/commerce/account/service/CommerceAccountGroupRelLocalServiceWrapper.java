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
 * Provides a wrapper for {@link CommerceAccountGroupRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupRelLocalService
 * @generated
 */
public class CommerceAccountGroupRelLocalServiceWrapper
	implements CommerceAccountGroupRelLocalService,
			   ServiceWrapper<CommerceAccountGroupRelLocalService> {

	public CommerceAccountGroupRelLocalServiceWrapper(
		CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService) {

		_commerceAccountGroupRelLocalService =
			commerceAccountGroupRelLocalService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
		addCommerceAccountGroupRel(
			com.liferay.commerce.account.model.CommerceAccountGroupRel
				commerceAccountGroupRel) {

		return _commerceAccountGroupRelLocalService.addCommerceAccountGroupRel(
			commerceAccountGroupRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
			addCommerceAccountGroupRel(
				String className, long classPK, long commerceAccountGroupId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.addCommerceAccountGroupRel(
			className, classPK, commerceAccountGroupId, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
		deleteCommerceAccountGroupRel(
			com.liferay.commerce.account.model.CommerceAccountGroupRel
				commerceAccountGroupRel) {

		return _commerceAccountGroupRelLocalService.
			deleteCommerceAccountGroupRel(commerceAccountGroupRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
			deleteCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.
			deleteCommerceAccountGroupRel(commerceAccountGroupRelId);
	}

	@Override
	public void deleteCommerceAccountGroupRels(long commerceAccountGroupId) {
		_commerceAccountGroupRelLocalService.deleteCommerceAccountGroupRels(
			commerceAccountGroupId);
	}

	@Override
	public void deleteCommerceAccountGroupRels(String className, long classPK) {
		_commerceAccountGroupRelLocalService.deleteCommerceAccountGroupRels(
			className, classPK);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
		fetchCommerceAccountGroupRel(long commerceAccountGroupRelId) {

		return _commerceAccountGroupRelLocalService.
			fetchCommerceAccountGroupRel(commerceAccountGroupRelId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroupRel
			getCommerceAccountGroupRel(long commerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupRelLocalService.getCommerceAccountGroupRel(
			commerceAccountGroupRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(int start, int end) {

		return _commerceAccountGroupRelLocalService.getCommerceAccountGroupRels(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(
				long commerceAccountGroupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroupRel>
						orderByComparator) {

		return _commerceAccountGroupRelLocalService.getCommerceAccountGroupRels(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroupRel>
			getCommerceAccountGroupRels(
				String className, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroupRel>
						orderByComparator) {

		return _commerceAccountGroupRelLocalService.getCommerceAccountGroupRels(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAccountGroupRelsCount() {
		return _commerceAccountGroupRelLocalService.
			getCommerceAccountGroupRelsCount();
	}

	@Override
	public int getCommerceAccountGroupRelsCount(long commerceAccountGroupId) {
		return _commerceAccountGroupRelLocalService.
			getCommerceAccountGroupRelsCount(commerceAccountGroupId);
	}

	@Override
	public int getCommerceAccountGroupRelsCount(
		String className, long classPK) {

		return _commerceAccountGroupRelLocalService.
			getCommerceAccountGroupRelsCount(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountGroupRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceAccountGroupRelLocalService getWrappedService() {
		return _commerceAccountGroupRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService) {

		_commerceAccountGroupRelLocalService =
			commerceAccountGroupRelLocalService;
	}

	private CommerceAccountGroupRelLocalService
		_commerceAccountGroupRelLocalService;

}