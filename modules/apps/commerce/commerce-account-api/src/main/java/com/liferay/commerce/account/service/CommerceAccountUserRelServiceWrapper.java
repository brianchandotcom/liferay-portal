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
 * Provides a wrapper for {@link CommerceAccountUserRelService}.
 *
 * @author Marco Leo
 * @see CommerceAccountUserRelService
 * @generated
 */
public class CommerceAccountUserRelServiceWrapper
	implements CommerceAccountUserRelService,
			   ServiceWrapper<CommerceAccountUserRelService> {

	public CommerceAccountUserRelServiceWrapper() {
		this(null);
	}

	public CommerceAccountUserRelServiceWrapper(
		CommerceAccountUserRelService commerceAccountUserRelService) {

		_commerceAccountUserRelService = commerceAccountUserRelService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			addCommerceAccountUserRel(
				long commerceAccountId, long commerceAccountUserId,
				long[] roleIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelService.addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, roleIds, serviceContext);
	}

	@Override
	public void addCommerceAccountUserRels(
			long commerceAccountId, long[] userIds, String[] emailAddresses,
			long[] roleIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelService.addCommerceAccountUserRels(
			commerceAccountId, userIds, emailAddresses, roleIds,
			serviceContext);
	}

	@Override
	public void deleteCommerceAccountUserRel(
			long commerceAccountId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelService.deleteCommerceAccountUserRel(
			commerceAccountId, userId);
	}

	@Override
	public void deleteCommerceAccountUserRels(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelService.deleteCommerceAccountUserRels(
			commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelService.deleteCommerceAccountUserRels(
			commerceAccountId, userIds);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			fetchCommerceAccountUserRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelService.fetchCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			getCommerceAccountUserRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelService.getCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
				getCommerceAccountUserRels(
					long commerceAccountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelService.getCommerceAccountUserRels(
			commerceAccountId, start, end);
	}

	@Override
	public int getCommerceAccountUserRelsCount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelService.getCommerceAccountUserRelsCount(
			commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountUserRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelService.inviteUser(
			commerceAccountId, emailAddress, roleIds, userExternalReferenceCode,
			serviceContext);
	}

	@Override
	public CommerceAccountUserRelService getWrappedService() {
		return _commerceAccountUserRelService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountUserRelService commerceAccountUserRelService) {

		_commerceAccountUserRelService = commerceAccountUserRelService;
	}

	private CommerceAccountUserRelService _commerceAccountUserRelService;

}