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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceAccount. This utility wraps
 * <code>com.liferay.commerce.account.service.impl.CommerceAccountServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceAccountService
 * @generated
 */
public class CommerceAccountServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.account.service.impl.CommerceAccountServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.account.model.CommerceAccount
			addBusinessCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, boolean active, String externalReferenceCode,
				long[] userIds, String[] emailAddresses,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addBusinessCommerceAccount(
			name, parentCommerceAccountId, email, taxId, active,
			externalReferenceCode, userIds, emailAddresses, serviceContext);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			addCommerceAccount(
				String name, long parentCommerceAccountId, String email,
				String taxId, int type, boolean active,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAccount(
			name, parentCommerceAccountId, email, taxId, type, active,
			externalReferenceCode, serviceContext);
	}

	public static void deleteCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceAccount(commerceAccountId);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			fetchCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceAccount(commerceAccountId);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAccount(commerceAccountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			getPersonalCommerceAccount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersonalCommerceAccount(userId);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccount>
				getUserCommerceAccounts(
					long userId, long parentCommerceAccountId,
					int commerceSiteType, String keywords, Boolean active,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, active,
			start, end);
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccount>
				getUserCommerceAccounts(
					long userId, long parentCommerceAccountId,
					int commerceSiteType, String keywords, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserCommerceAccounts(
			userId, parentCommerceAccountId, commerceSiteType, keywords, start,
			end);
	}

	public static int getUserCommerceAccountsCount(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords);
	}

	public static int getUserCommerceAccountsCount(
			long userId, long parentCommerceAccountId, int commerceSiteType,
			String keywords, Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserCommerceAccountsCount(
			userId, parentCommerceAccountId, commerceSiteType, keywords,
			active);
	}

	public static com.liferay.commerce.account.model.CommerceAccount setActive(
			long commerceAccountId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().setActive(commerceAccountId, active);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId, serviceContext);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				long defaultBillingAddressId, long defaultShippingAddressId,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			defaultBillingAddressId, defaultShippingAddressId,
			externalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass Default Billing/Shipping Ids
	 */
	@Deprecated
	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				long commerceAccountId, String name, boolean logo,
				byte[] logoBytes, String email, String taxId, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAccount(
			commerceAccountId, name, logo, logoBytes, email, taxId, active,
			serviceContext);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateDefaultBillingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDefaultBillingAddress(
			commerceAccountId, commerceAddressId);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateDefaultShippingAddress(
				long commerceAccountId, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDefaultShippingAddress(
			commerceAccountId, commerceAddressId);
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			upsertCommerceAccount(
				String name, long parentCommerceAccountId, boolean logo,
				byte[] logoBytes, String email, String taxId, int type,
				boolean active, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceAccount(
			name, parentCommerceAccountId, logo, logoBytes, email, taxId, type,
			active, externalReferenceCode, serviceContext);
	}

	public static CommerceAccountService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAccountService, CommerceAccountService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceAccountService.class);

		ServiceTracker<CommerceAccountService, CommerceAccountService>
			serviceTracker =
				new ServiceTracker
					<CommerceAccountService, CommerceAccountService>(
						bundle.getBundleContext(), CommerceAccountService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}