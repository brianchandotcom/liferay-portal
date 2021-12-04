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
 * Provides a wrapper for {@link CommerceAccountService}.
 *
 * @author Marco Leo
 * @see CommerceAccountService
 * @generated
 */
public class CommerceAccountServiceWrapper
	implements CommerceAccountService, ServiceWrapper<CommerceAccountService> {

	public CommerceAccountServiceWrapper() {
		this(null);
	}

	public CommerceAccountServiceWrapper(
		CommerceAccountService commerceAccountService) {

		_commerceAccountService = commerceAccountService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addBusinessCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, boolean active, String externalReferenceCode,
				long[] userIds, String[] emailAddresses,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.addBusinessCommerceAccount(
			name, parentCommerceAccountId, email, taxId, active,
			externalReferenceCode, userIds, emailAddresses, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, int type, boolean active,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.addCommerceAccount(
			name, parentCommerceAccountId, email, taxId, type, active,
			externalReferenceCode, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addOrUpdateCommerceAccount(
				String name, long parentCommerceAccountId, boolean logo,
				byte[] logoBytes, String email, String taxId, int type,
				boolean active, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.addOrUpdateCommerceAccount(
			name, parentCommerceAccountId, logo, logoBytes, email, taxId, type,
			active, externalReferenceCode, serviceContext);
	}

	@Override
	public void deleteCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountService.deleteCommerceAccount(commerceAccountId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			fetchCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.fetchCommerceAccount(commerceAccountId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.getCommerceAccount(commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getPersonalCommerceAccount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.getPersonalCommerceAccount(userId);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
			getUserCommerceAccounts(
				long userId, long parentCommerceAccountId, int commerceSiteType,
				String keywords, Boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, active,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
			getUserCommerceAccounts(
				long userId, long parentCommerceAccountId, int commerceSiteType,
				String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, start,
			end);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords,
			active);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount setActive(
			long commerceAccountId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.setActive(commerceAccountId, active);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId,
			externalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateDefaultBillingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.updateDefaultBillingAddress(
			commerceAccountId, commerceAddressId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateDefaultShippingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountService.updateDefaultShippingAddress(
			commerceAccountId, commerceAddressId);
	}

	@Override
	public CommerceAccountService getWrappedService() {
		return _commerceAccountService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountService commerceAccountService) {

		_commerceAccountService = commerceAccountService;
	}

	private CommerceAccountService _commerceAccountService;

}