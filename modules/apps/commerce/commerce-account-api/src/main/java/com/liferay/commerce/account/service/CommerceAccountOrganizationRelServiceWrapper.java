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
 * Provides a wrapper for {@link CommerceAccountOrganizationRelService}.
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelService
 * @generated
 */
public class CommerceAccountOrganizationRelServiceWrapper
	implements CommerceAccountOrganizationRelService,
			   ServiceWrapper<CommerceAccountOrganizationRelService> {

	public CommerceAccountOrganizationRelServiceWrapper() {
		this(null);
	}

	public CommerceAccountOrganizationRelServiceWrapper(
		CommerceAccountOrganizationRelService
			commerceAccountOrganizationRelService) {

		_commerceAccountOrganizationRelService =
			commerceAccountOrganizationRelService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
			addCommerceAccountOrganizationRel(
				long commerceAccountId, long organizationId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelService.
			addCommerceAccountOrganizationRel(
				commerceAccountId, organizationId, serviceContext);
	}

	@Override
	public void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountOrganizationRelService.
			addCommerceAccountOrganizationRels(
				commerceAccountId, organizationIds, serviceContext);
	}

	@Override
	public void deleteCommerceAccountOrganizationRel(
			long commerceAccountId, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountOrganizationRelService.
			deleteCommerceAccountOrganizationRel(
				commerceAccountId, organizationId);
	}

	@Override
	public void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountOrganizationRelService.
			deleteCommerceAccountOrganizationRels(
				commerceAccountId, organizationIds);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountOrganizationRel
			fetchCommerceAccountOrganizationRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountOrganizationRelPK
						commerceAccountOrganizationRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelService.
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

		return _commerceAccountOrganizationRelService.
			getCommerceAccountOrganizationRel(commerceAccountOrganizationRelPK);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRels(long commerceAccountId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelService.
			getCommerceAccountOrganizationRels(commerceAccountId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRels(
					long commerceAccountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelService.
			getCommerceAccountOrganizationRels(commerceAccountId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRelsByOrganizationId(
					long organizationId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelService.
			getCommerceAccountOrganizationRelsByOrganizationId(
				organizationId, start, end);
	}

	@Override
	public int getCommerceAccountOrganizationRelsByOrganizationIdCount(
			long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelService.
			getCommerceAccountOrganizationRelsByOrganizationIdCount(
				organizationId);
	}

	@Override
	public int getCommerceAccountOrganizationRelsCount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountOrganizationRelService.
			getCommerceAccountOrganizationRelsCount(commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountOrganizationRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceAccountOrganizationRelService getWrappedService() {
		return _commerceAccountOrganizationRelService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountOrganizationRelService
			commerceAccountOrganizationRelService) {

		_commerceAccountOrganizationRelService =
			commerceAccountOrganizationRelService;
	}

	private CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;

}