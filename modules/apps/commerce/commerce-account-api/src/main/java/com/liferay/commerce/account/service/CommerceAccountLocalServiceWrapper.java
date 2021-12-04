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
 * Provides a wrapper for {@link CommerceAccountLocalService}.
 *
 * @author Marco Leo
 * @see CommerceAccountLocalService
 * @generated
 */
public class CommerceAccountLocalServiceWrapper
	implements CommerceAccountLocalService,
			   ServiceWrapper<CommerceAccountLocalService> {

	public CommerceAccountLocalServiceWrapper() {
		this(null);
	}

	public CommerceAccountLocalServiceWrapper(
		CommerceAccountLocalService commerceAccountLocalService) {

		_commerceAccountLocalService = commerceAccountLocalService;
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addBusinessCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, boolean active, String externalReferenceCode,
				long[] userIds, String[] emailAddresses,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.addBusinessCommerceAccount(
			name, parentCommerceAccountId, email, taxId, active,
			externalReferenceCode, userIds, emailAddresses, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		addCommerceAccount(
			com.liferay.commerce.account.model.CommerceAccount
				commerceAccount) {

		return _commerceAccountLocalService.addCommerceAccount(commerceAccount);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, int type, boolean active,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.addCommerceAccount(
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

		return _commerceAccountLocalService.addOrUpdateCommerceAccount(
			name, parentCommerceAccountId, logo, logoBytes, email, taxId, type,
			active, externalReferenceCode, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			addPersonalCommerceAccount(
				long userId, String taxId, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.addPersonalCommerceAccount(
			userId, taxId, externalReferenceCode, serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		createCommerceAccount(long commerceAccountId) {

		return _commerceAccountLocalService.createCommerceAccount(
			commerceAccountId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			deleteCommerceAccount(
				com.liferay.commerce.account.model.CommerceAccount
					commerceAccount)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.deleteCommerceAccount(
			commerceAccount);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			deleteCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.deleteCommerceAccount(
			commerceAccountId);
	}

	@Override
	public void deleteCommerceAccounts(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountLocalService.deleteCommerceAccounts(companyId);
	}

	@Override
	public void deleteLogo(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceAccountLocalService.deleteLogo(commerceAccountId);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceAccountLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAccountLocalService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		fetchCommerceAccount(long commerceAccountId) {

		return _commerceAccountLocalService.fetchCommerceAccount(
			commerceAccountId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		fetchCommerceAccountByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceAccountLocalService.fetchCommerceAccountByReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getCommerceAccount(
			commerceAccountId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(long userId, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getCommerceAccount(
			userId, commerceAccountId);
	}

	@Override
	public com.liferay.portal.kernel.model.Group getCommerceAccountGroup(
			long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getCommerceAccountGroup(
			commerceAccountId);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
		getCommerceAccounts(int start, int end) {

		return _commerceAccountLocalService.getCommerceAccounts(start, end);
	}

	@Override
	public int getCommerceAccountsCount() {
		return _commerceAccountLocalService.getCommerceAccountsCount();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getGuestCommerceAccount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getGuestCommerceAccount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAccountLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			getPersonalCommerceAccount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getPersonalCommerceAccount(userId);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
			getUserCommerceAccounts(
				long userId, Long parentCommerceAccountId, int commerceSiteType,
				String keywords, Boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, active,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
			getUserCommerceAccounts(
				long userId, Long parentCommerceAccountId, int commerceSiteType,
				String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, start,
			end);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords);
	}

	@Override
	public int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords,
			active);
	}

	@Override
	public java.util.List<com.liferay.commerce.account.model.CommerceAccount>
			search(
				long companyId, long parentCommerceAccountId, String keywords,
				int type, Boolean active, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.search(
			companyId, parentCommerceAccountId, keywords, type, active, start,
			end, sort);
	}

	@Override
	public int searchCommerceAccountsCount(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.searchCommerceAccountsCount(
			companyId, parentCommerceAccountId, keywords, type, active);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount setActive(
			long commerceAccountId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.setActive(
			commerceAccountId, active);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
		updateCommerceAccount(
			com.liferay.commerce.account.model.CommerceAccount
				commerceAccount) {

		return _commerceAccountLocalService.updateCommerceAccount(
			commerceAccount);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateCommerceAccount(
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

		return _commerceAccountLocalService.updateCommerceAccount(
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

		return _commerceAccountLocalService.updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateDefaultBillingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateDefaultBillingAddress(
			commerceAccountId, commerceAddressId);
	}

	@Override
	public com.liferay.commerce.account.model.CommerceAccount
			updateDefaultShippingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateDefaultShippingAddress(
			commerceAccountId, commerceAddressId);
	}

	/**
	 * @bridged
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.account.model.CommerceAccount updateStatus(
			long userId, long commerceAccountId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceAccountLocalService.updateStatus(
			userId, commerceAccountId, status, serviceContext, workflowContext);
	}

	@Override
	public CommerceAccountLocalService getWrappedService() {
		return _commerceAccountLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceAccountLocalService commerceAccountLocalService) {

		_commerceAccountLocalService = commerceAccountLocalService;
	}

	private CommerceAccountLocalService _commerceAccountLocalService;

}