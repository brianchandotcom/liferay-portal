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
 * Provides a wrapper for {@link CommerceAccountGroupService}.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupService
 * @generated
 */
public class CommerceAccountGroupServiceWrapper
	implements CommerceAccountGroupService,
			   ServiceWrapper<CommerceAccountGroupService> {

	public CommerceAccountGroupServiceWrapper() {
		this(null);
	}

	public CommerceAccountGroupServiceWrapper(
		CommerceAccountGroupService commerceAccountGroupService) {

		_commerceAccountGroupService = commerceAccountGroupService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			addCommerceAccountGroup(
				long companyId, String name, int type,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.addCommerceAccountGroup(
			companyId, name, type, externalReferenceCode, serviceContext);
	}

	@Override
	public void deleteCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountGroupService.deleteCommerceAccountGroup(
			commerceAccountGroupId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			getCommerceAccountGroup(long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.getCommerceAccountGroup(
			commerceAccountGroupId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
				getCommerceAccountGroups(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.account.model.
							CommerceAccountGroup> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.getCommerceAccountGroups(
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup>
				getCommerceAccountGroupsByCommerceAccountId(
					long commerceAccountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.
			getCommerceAccountGroupsByCommerceAccountId(
				commerceAccountId, start, end);
	}

	@Override
	public int getCommerceAccountGroupsByCommerceAccountIdCount(
			long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.
			getCommerceAccountGroupsByCommerceAccountIdCount(commerceAccountId);
	}

	@Override
	public int getCommerceAccountGroupsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.getCommerceAccountGroupsCount(
			companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountGroupService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<com.liferay.commerce.account.model.CommerceAccountGroup> search(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceAccountsGroupCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.searchCommerceAccountsGroupCount(
			companyId, keywords);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccountGroup
			updateCommerceAccountGroup(
				long commerceAccountGroupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountGroupService.updateCommerceAccountGroup(
			commerceAccountGroupId, name, serviceContext);
	}

	@Override
	public CommerceAccountGroupService getWrappedService() {
		return _commerceAccountGroupService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountGroupService commerceAccountGroupService) {

		_commerceAccountGroupService = commerceAccountGroupService;
	}

	private CommerceAccountGroupService _commerceAccountGroupService;

}