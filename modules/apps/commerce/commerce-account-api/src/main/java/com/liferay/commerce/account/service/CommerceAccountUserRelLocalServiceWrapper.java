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
 * Provides a wrapper for {@link CommerceAccountUserRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountUserRelLocalService
 * @generated
 */
public class CommerceAccountUserRelLocalServiceWrapper
	implements CommerceAccountUserRelLocalService,
			   ServiceWrapper<CommerceAccountUserRelLocalService> {

	public CommerceAccountUserRelLocalServiceWrapper(
		CommerceAccountUserRelLocalService commerceAccountUserRelLocalService) {

		_commerceAccountUserRelLocalService =
			commerceAccountUserRelLocalService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		addCommerceAccountUserRel(
			com.liferay.commerce.account.model.CommerceAccountUserRel
				commerceAccountUserRel) {

		return _commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccountUserRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			addCommerceAccountUserRel(
				long commerceAccountId, long commerceAccountUserId,
				long[] roleIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, roleIds, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			addCommerceAccountUserRel(
				long commerceAccountId, long commerceAccountUserId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.addCommerceAccountUserRel(
			commerceAccountId, commerceAccountUserId, serviceContext);
	}

	@Override
	public void addCommerceAccountUserRels(
			long commerceAccountId, long[] userIds, String[] emailAddresses,
			long[] roleIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelLocalService.addCommerceAccountUserRels(
			commerceAccountId, userIds, emailAddresses, roleIds,
			serviceContext);
	}

	@Override
	public void addDefaultRoles(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelLocalService.addDefaultRoles(userId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		createCommerceAccountUserRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountUserRelPK commerceAccountUserRelPK) {

		return _commerceAccountUserRelLocalService.createCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		deleteCommerceAccountUserRel(
			com.liferay.commerce.account.model.CommerceAccountUserRel
				commerceAccountUserRel) {

		return _commerceAccountUserRelLocalService.deleteCommerceAccountUserRel(
			commerceAccountUserRel);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			deleteCommerceAccountUserRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.deleteCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public void deleteCommerceAccountUserRels(
			long commerceAccountId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountUserRelLocalService.deleteCommerceAccountUserRels(
			commerceAccountId, userIds);
	}

	@Override
	public void deleteCommerceAccountUserRelsByCommerceAccountId(
		long commerceAccountId) {

		_commerceAccountUserRelLocalService.
			deleteCommerceAccountUserRelsByCommerceAccountId(commerceAccountId);
	}

	@Override
	public void deleteCommerceAccountUserRelsByCommerceAccountUserId(
		long userId) {

		_commerceAccountUserRelLocalService.
			deleteCommerceAccountUserRelsByCommerceAccountUserId(userId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		fetchCommerceAccountUserRel(
			com.liferay.commerce.account.service.persistence.
				CommerceAccountUserRelPK commerceAccountUserRelPK) {

		return _commerceAccountUserRelLocalService.fetchCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
			getCommerceAccountUserRel(
				com.liferay.commerce.account.service.persistence.
					CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.getCommerceAccountUserRel(
			commerceAccountUserRelPK);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
			getCommerceAccountUserRels(int start, int end) {

		return _commerceAccountUserRelLocalService.getCommerceAccountUserRels(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
			getCommerceAccountUserRels(long commerceAccountId) {

		return _commerceAccountUserRelLocalService.getCommerceAccountUserRels(
			commerceAccountId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
			getCommerceAccountUserRels(
				long commerceAccountId, int start, int end) {

		return _commerceAccountUserRelLocalService.getCommerceAccountUserRels(
			commerceAccountId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
			getCommerceAccountUserRelsByCommerceAccountUserId(
				long commerceAccountUserId) {

		return _commerceAccountUserRelLocalService.
			getCommerceAccountUserRelsByCommerceAccountUserId(
				commerceAccountUserId);
	}

	@Override
	public int getCommerceAccountUserRelsCount() {
		return _commerceAccountUserRelLocalService.
			getCommerceAccountUserRelsCount();
	}

	@Override
	public int getCommerceAccountUserRelsCount(long commerceAccountId) {
		return _commerceAccountUserRelLocalService.
			getCommerceAccountUserRelsCount(commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountUserRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel inviteUser(
			long commerceAccountId, String emailAddress, long[] roleIds,
			String userExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountUserRelLocalService.inviteUser(
			commerceAccountId, emailAddress, roleIds, userExternalReferenceCode,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountUserRel
		updateCommerceAccountUserRel(
			com.liferay.commerce.account.model.CommerceAccountUserRel
				commerceAccountUserRel) {

		return _commerceAccountUserRelLocalService.updateCommerceAccountUserRel(
			commerceAccountUserRel);
	}

	@Override
	public CommerceAccountUserRelLocalService getWrappedService() {
		return _commerceAccountUserRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountUserRelLocalService commerceAccountUserRelLocalService) {

		_commerceAccountUserRelLocalService =
			commerceAccountUserRelLocalService;
	}

	private CommerceAccountUserRelLocalService
		_commerceAccountUserRelLocalService;

}