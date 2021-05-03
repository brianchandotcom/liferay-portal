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
 * Provides a wrapper for {@link CommerceAccountGroupLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupLocalService
 * @generated
 */
public class CommerceAccountGroupLocalServiceWrapper
	implements CommerceAccountGroupLocalService,
			   ServiceWrapper<CommerceAccountGroupLocalService> {

	public CommerceAccountGroupLocalServiceWrapper(
		CommerceAccountGroupLocalService commerceAccountGroupLocalService) {

		_commerceAccountGroupLocalService = commerceAccountGroupLocalService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			addCommerceAccountGroup(
				long companyId, String name, int type, boolean system,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.addCommerceAccountGroup(
			companyId, name, type, system, externalReferenceCode,
			serviceContext);
	}

	@Override
	public void checkGuestCommerceAccountGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountGroupLocalService.checkGuestCommerceAccountGroup(
			companyId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			deleteCommerceAccountGroup(
				com.liferay.commerce.account.model.CommerceAccountGroup
					commerceAccountGroup)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.deleteCommerceAccountGroup(
			commerceAccountGroup);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			deleteCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.deleteCommerceAccountGroup(
			commerceAccountGroupId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAccountGroupLocalService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
		fetchCommerceAccountGroup(long commerceAccountGroupId) {

		return _commerceAccountGroupLocalService.fetchCommerceAccountGroup(
			commerceAccountGroupId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			getCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.getCommerceAccountGroup(
			commerceAccountGroupId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
			getCommerceAccountGroups(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroup>
						orderByComparator) {

		return _commerceAccountGroupLocalService.getCommerceAccountGroups(
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
			getCommerceAccountGroupsByCommerceAccountId(
				long commerceAccountId) {

		return _commerceAccountGroupLocalService.
			getCommerceAccountGroupsByCommerceAccountId(commerceAccountId);
	}

	@Override
	public int getCommerceAccountGroupsCount(long companyId) {
		return _commerceAccountGroupLocalService.getCommerceAccountGroupsCount(
			companyId);
	}

	@Override
	public java.util.List<Long> getCommerceAccountUserIdsFromAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end) {

		return _commerceAccountGroupLocalService.
			getCommerceAccountUserIdsFromAccountGroupIds(
				commerceAccountGroupIds, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountGroupLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
				searchCommerceAccountGroups(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.searchCommerceAccountGroups(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceAccountsGroupCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.
			searchCommerceAccountsGroupCount(companyId, keywords);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			updateCommerceAccountGroup(
				long commerceAccountGroupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupLocalService.updateCommerceAccountGroup(
			commerceAccountGroupId, name, serviceContext);
	}

	@Override
	public CommerceAccountGroupLocalService getWrappedService() {
		return _commerceAccountGroupLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountGroupLocalService commerceAccountGroupLocalService) {

		_commerceAccountGroupLocalService = commerceAccountGroupLocalService;
	}

	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

}