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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for CommerceAccount. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceAccountLocalService
 * @generated
 */
public class CommerceAccountLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceAccount addBusinessCommerceAccount(
			String name, long parentCommerceAccountId, String email,
			String taxId, boolean active, String externalReferenceCode,
			long[] userIds, String[] emailAddresses,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addBusinessCommerceAccount(
			name, parentCommerceAccountId, email, taxId, active,
			externalReferenceCode, userIds, emailAddresses, serviceContext);
	}

	public static CommerceAccount addCommerceAccount(
		CommerceAccount commerceAccount) {

		return getService().addCommerceAccount(commerceAccount);
	}

	public static CommerceAccount addCommerceAccount(
			String name, long parentCommerceAccountId, String email,
			String taxId, int type, boolean active,
			String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAccount(
			name, parentCommerceAccountId, email, taxId, type, active,
			externalReferenceCode, serviceContext);
	}

	public static CommerceAccount addOrUpdateCommerceAccount(
			String name, long parentCommerceAccountId, boolean logo,
			byte[] logoBytes, String email, String taxId, int type,
			boolean active, String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCommerceAccount(
			name, parentCommerceAccountId, logo, logoBytes, email, taxId, type,
			active, externalReferenceCode, serviceContext);
	}

	public static CommerceAccount addPersonalCommerceAccount(
			long userId, String taxId, String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addPersonalCommerceAccount(
			userId, taxId, externalReferenceCode, serviceContext);
	}

	public static CommerceAccount createCommerceAccount(
		long commerceAccountId) {

		return getService().createCommerceAccount(commerceAccountId);
	}

	public static CommerceAccount deleteCommerceAccount(
			CommerceAccount commerceAccount)
		throws PortalException {

		return getService().deleteCommerceAccount(commerceAccount);
	}

	public static CommerceAccount deleteCommerceAccount(long commerceAccountId)
		throws PortalException {

		return getService().deleteCommerceAccount(commerceAccountId);
	}

	public static void deleteCommerceAccounts(long companyId)
		throws PortalException {

		getService().deleteCommerceAccounts(companyId);
	}

	public static void deleteLogo(long commerceAccountId)
		throws PortalException {

		getService().deleteLogo(commerceAccountId);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static CommerceAccount fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static CommerceAccount fetchCommerceAccount(long commerceAccountId) {
		return getService().fetchCommerceAccount(commerceAccountId);
	}

	public static CommerceAccount fetchCommerceAccountByReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchCommerceAccountByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static CommerceAccount getCommerceAccount(long commerceAccountId)
		throws PortalException {

		return getService().getCommerceAccount(commerceAccountId);
	}

	public static CommerceAccount getCommerceAccount(
			long userId, long commerceAccountId)
		throws PortalException {

		return getService().getCommerceAccount(userId, commerceAccountId);
	}

	public static com.liferay.portal.kernel.model.Group getCommerceAccountGroup(
			long commerceAccountId)
		throws PortalException {

		return getService().getCommerceAccountGroup(commerceAccountId);
	}

	public static List<CommerceAccount> getCommerceAccounts(
		int start, int end) {

		return getService().getCommerceAccounts(start, end);
	}

	public static int getCommerceAccountsCount() {
		return getService().getCommerceAccountsCount();
	}

	public static CommerceAccount getGuestCommerceAccount(long companyId)
		throws PortalException {

		return getService().getGuestCommerceAccount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceAccount getPersonalCommerceAccount(long userId)
		throws PortalException {

		return getService().getPersonalCommerceAccount(userId);
	}

	public static List<CommerceAccount> getUserCommerceAccounts(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active, int start, int end)
		throws PortalException {

		return getService().getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, active,
			start, end);
	}

	public static List<CommerceAccount> getUserCommerceAccounts(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, int start, int end)
		throws PortalException {

		return getService().getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, start,
			end);
	}

	public static int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws PortalException {

		return getService().getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords);
	}

	public static int getUserCommerceAccountsCount(
			long userId, Long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws PortalException {

		return getService().getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords,
			active);
	}

	public static List<CommerceAccount> search(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(
			companyId, parentCommerceAccountId, keywords, type, active, start,
			end, sort);
	}

	public static int searchCommerceAccountsCount(
			long companyId, long parentCommerceAccountId, String keywords,
			int type, Boolean active)
		throws PortalException {

		return getService().searchCommerceAccountsCount(
			companyId, parentCommerceAccountId, keywords, type, active);
	}

	public static CommerceAccount setActive(
			long commerceAccountId, boolean active)
		throws PortalException {

		return getService().setActive(commerceAccountId, active);
	}

	public static CommerceAccount updateCommerceAccount(
		CommerceAccount commerceAccount) {

		return getService().updateCommerceAccount(commerceAccount);
	}

	public static CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			long defaultBillingAddressId, long defaultShippingAddressId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId, serviceContext);
	}

	public static CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			long defaultBillingAddressId, long defaultShippingAddressId,
			String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId,
			externalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	public static CommerceAccount updateCommerceAccount(
			long commerceAccountId, String name, boolean logo, byte[] logoBytes,
			String email, String taxId, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			serviceContext);
	}

	public static CommerceAccount updateDefaultBillingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException {

		return getService().updateDefaultBillingAddress(
			commerceAccountId, commerceAddressId);
	}

	public static CommerceAccount updateDefaultShippingAddress(
			long commerceAccountId, long commerceAddressId)
		throws PortalException {

		return getService().updateDefaultShippingAddress(
			commerceAccountId, commerceAddressId);
	}

	/**
	 * @bridged
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static CommerceAccount updateStatus(
			long userId, long commerceAccountId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		return getService().updateStatus(
			userId, commerceAccountId, status, serviceContext, workflowContext);
	}

	public static CommerceAccountLocalService getService() {
		return _service;
	}

	public static void setService(CommerceAccountLocalService service) {
		_service = service;
	}

	private static volatile CommerceAccountLocalService _service;

}