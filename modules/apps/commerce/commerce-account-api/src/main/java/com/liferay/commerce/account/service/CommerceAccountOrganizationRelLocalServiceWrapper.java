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
 * Provides a wrapper for {@link CommerceAccountOrganizationRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelLocalService
 * @generated
 */
public class CommerceAccountOrganizationRelLocalServiceWrapper
	implements CommerceAccountOrganizationRelLocalService,
			   ServiceWrapper<CommerceAccountOrganizationRelLocalService> {

	public CommerceAccountOrganizationRelLocalServiceWrapper(
		CommerceAccountOrganizationRelLocalService
			commerceAccountOrganizationRelLocalService) {

		_commerceAccountOrganizationRelLocalService =
			commerceAccountOrganizationRelLocalService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		addCommerceAccountOrganizationRel(
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				commerceAccountOrganizationRel) {

		return _commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRel(commerceAccountOrganizationRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
			addCommerceAccountOrganizationRel(
				long commerceAccountId, long organizationId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRel(
				commerceAccountId, organizationId, serviceContext);
	}

	@Override
	public void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRels(
				commerceAccountId, organizationIds, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		createCommerceAccountOrganizationRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountOrganizationRelPK
					commerceAccountOrganizationRelPK) {

		return _commerceAccountOrganizationRelLocalService.
			createCommerceAccountOrganizationRel(
				commerceAccountOrganizationRelPK);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		deleteCommerceAccountOrganizationRel(
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				commerceAccountOrganizationRel) {

		return _commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRel(
				commerceAccountOrganizationRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
			deleteCommerceAccountOrganizationRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountOrganizationRelPK
						commerceAccountOrganizationRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRel(
				commerceAccountOrganizationRelPK);
	}

	@Override
	public void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRels(
				commerceAccountId, organizationIds);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByCommerceAccountId(
		long commerceAccountId) {

		_commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRelsByCommerceAccountId(
				commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountOrganizationRelsByOrganizationId(
		long organizationId) {

		_commerceAccountOrganizationRelLocalService.
			deleteCommerceAccountOrganizationRelsByOrganizationId(
				organizationId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		fetchCommerceAccountOrganizationRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountOrganizationRelPK
					commerceAccountOrganizationRelPK) {

		return _commerceAccountOrganizationRelLocalService.
			fetchCommerceAccountOrganizationRel(
				commerceAccountOrganizationRelPK);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
			getCommerceAccountOrganizationRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountOrganizationRelPK
						commerceAccountOrganizationRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRel(commerceAccountOrganizationRelPK);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRels(int start, int end) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRels(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRels(long commerceAccountId) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRels(commerceAccountId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRels(
				long commerceAccountId, int start, int end) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRels(commerceAccountId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
			getCommerceAccountOrganizationRelsByOrganizationId(
				long organizationId, int start, int end) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsByOrganizationId(
				organizationId, start, end);
	}

	@Override
	public int getCommerceAccountOrganizationRelsByOrganizationIdCount(
		long organizationId) {

		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount() {
		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsCount();
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount(long commerceAccountId) {
		return _commerceAccountOrganizationRelLocalService.
			getCommerceAccountOrganizationRelsCount(commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountOrganizationRelLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
		updateCommerceAccountOrganizationRel(
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				commerceAccountOrganizationRel) {

		return _commerceAccountOrganizationRelLocalService.
			updateCommerceAccountOrganizationRel(
				commerceAccountOrganizationRel);
	}

	@Override
	public CommerceAccountOrganizationRelLocalService getWrappedService() {
		return _commerceAccountOrganizationRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountOrganizationRelLocalService
			commerceAccountOrganizationRelLocalService) {

		_commerceAccountOrganizationRelLocalService =
			commerceAccountOrganizationRelLocalService;
	}

	private CommerceAccountOrganizationRelLocalService
		_commerceAccountOrganizationRelLocalService;

}